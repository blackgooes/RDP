package com.rdp.dao.auth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rdp.entity.Role;
import com.rdp.entity.User;
import com.rdp.util.DBConnection;
import com.rdp.util.Pager;


public class AuthDao {
	
	/**
	 * 根据用户获取角色信息
	 * @param user
	 * @return role
	 * @throws SQLException
	 */
	public Role getRoleByUser(User user){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Role role = null;
		StringBuffer sql = new StringBuffer(); 
		try{
			conn=DBConnection.getConnection();
			sql.append("SELECT ");
			sql.append("* ");
			sql.append("FROM ");
			sql.append("roles ");
			sql.append("WHERE ");
			sql.append("roles.RoleId IN ( ");
			sql.append("SELECT ");
			sql.append("user_role.RoleId ");
			sql.append("FROM ");
			sql.append("user_role ");
			sql.append("WHERE ");
			sql.append("user_role.UserId = ( ");
			sql.append("SELECT ");
			sql.append("users.UserId ");
			sql.append("FROM ");
			sql.append("users ");
			sql.append("WHERE ");
			sql.append("users.UserName = \""+user.getName()+"\" ");
			sql.append(") ");
			ps = conn.prepareStatement(sql.toString());
			rs=ps.executeQuery();

				while(rs.next()){
					Role r = new Role();
					r.setRoleId(rs.getString("roleid"));
					r.setName(rs.getString("name"));
	//				user.setAge(rs.getInt("age"));
					role = r;
				} 
			}catch (SQLException e) {
			e.printStackTrace();
			}finally{
			DBConnection.close(rs, ps, conn);
		}		
		return role;		
	}
	
	/**
	 * 获取所有角色
	 * @param user
	 * @return
	 */
	public List<Role> getRole(){
		List<Role> rolelist = new ArrayList<Role>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn=DBConnection.getConnection();
			String sql = "select * from roles order by lv";
			ps = conn.prepareStatement(sql);
			rs=ps.executeQuery();			
			while(rs.next()){
				Role r = new Role();
				r.setRoleId(rs.getString("roleid"));
				r.setName(rs.getString("name"));
				r.setLv(rs.getInt("lv"));
				r.setRemark(rs.getString("remark"));
				rolelist.add(r);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnection.close(rs, ps, conn);
		}
		return rolelist;
	}
	
	/**
	 * 获取所有角色
	 * @param user
	 * @return
	 */
	public Pager getPageRole(int curPage,int pageSize){
		List<Role> rolelist = new ArrayList<Role>();
		Connection conn = null;
		PreparedStatement ps1 = null, ps2 = null;	
		ResultSet rs1 = null, rs2=null;
		int count=0;
		try{
			conn=DBConnection.getConnection();
			String countsql = "select count(*) as num from roles";
			ps1 = conn.prepareStatement(countsql);
		    rs1=ps1.executeQuery();		
		    if(rs1.next()){
				count=rs1.getInt("num");
			}
		    
			String sql = "select * from roles  order by lv limit "+((curPage-1)*pageSize)+" , "+pageSize;
			ps2 = conn.prepareStatement(sql);
			rs2=ps2.executeQuery();
								
			while(rs2.next()){
				Role r = new Role();
				r.setRoleId(rs2.getString("roleid"));
				r.setName(rs2.getString("name"));
				r.setLv(rs2.getInt("lv"));
				r.setRemark(rs2.getString("remark"));
				rolelist.add(r);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnection.close(rs1, ps1, conn);
			DBConnection.close(rs2, ps2, conn);
		}
		return new Pager(pageSize, curPage, count, rolelist);
	}
}
