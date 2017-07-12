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

public class UserDao {

	/**
	 * 获取所有角色
	 * @param user
	 * @return
	 */
	public Pager getPageUser(int curPage,int pageSize){
		List<User> userlist = new ArrayList<User>();
		Connection conn = null;
		PreparedStatement ps1 = null, ps2 = null;	
		ResultSet rs1 = null, rs2=null;
		int count=0;
		try{
			conn=DBConnection.getConnection();
			String countsql = "select count(*) as num from users";
			ps1 = conn.prepareStatement(countsql);
		    rs1=ps1.executeQuery();		
		    if(rs1.next()){
				count=rs1.getInt("num");
			}
		    
//			String sql = "select * from users limit "+((curPage-1)*pageSize)+" , "+pageSize;
		    // 两left join拼上了另个表的rolename
		    String sql = "select users.*,roles.name as rolename from users";
			sql += " left join user_role on users.userid = user_role.userid";
			sql += " left join roles on roles.roleid = user_role.roleid limit " +((curPage-1)*pageSize)+" , "+pageSize;
			ps2 = conn.prepareStatement(sql);
			rs2=ps2.executeQuery();
								
			while(rs2.next()){
				User r = new User();
				r.setUserid(rs2.getString("userid"));
				r.setUsername(rs2.getString("username"));
				r.setPassword(rs2.getString("password"));
				r.setName(rs2.getString("name"));
				r.setRemark(rs2.getString("remark"));
				r.setStateFlag(rs2.getInt("stateFlag"));
				r.setCreateTime(rs2.getDate("createTime"));
				r.setGendername(rs2.getInt("gender") == 1 ? "男" : "女");
				r.setTel(rs2.getString("tel"));
				r.setRemark(rs2.getString("remark"));
				r.setRolename(rs2.getString("rolename"));
				userlist.add(r);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnection.close(rs1, ps1, conn);
			DBConnection.close(rs2, ps2, conn);
		}
		return new Pager(pageSize, curPage, count, userlist);
	}
	/**
	 * 批量删除用户
	 * @param roleId
	 * @return
	 */
	public boolean delByUserId(String[] userId){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		int cols=0;
		try{
			conn=DBConnection.getConnection();
			sql.append("DELETE FROM users WHERE ");
			for(int i=0;i<userId.length;i++){
				sql.append("userid = '"+userId[i]+ "' or ");
				
			}
			sql.delete(sql.length() - 4, sql.length());
			ps = conn.prepareStatement(sql.toString());			
			cols = ps.executeUpdate();
			}catch (SQLException e) {
			e.printStackTrace();
			}finally{
			DBConnection.close(rs, ps, conn);
		}
		if(cols > 0){
			return true;
		}
		return false;		
	}
	
	/**
	 * 获取用户权限
	 * @param curPage
	 * @param pageSize
	 * @return
	 */
	public String getRoleByUser(String userID){
		List<User> userlist = new ArrayList<User>();
		Connection conn = null;
		PreparedStatement ps1 = null, ps2 = null;	
		ResultSet rs1 = null, rs2=null;
		int count=0;
		String roleID = "";
		try{
			conn=DBConnection.getConnection();
			String sql = "select count(*) as num from users";
			ps1 = conn.prepareStatement(sql);
		    rs1=ps1.executeQuery();		
		    if(rs1.next()){
		    	roleID=rs1.getString("roleid");
			}		    

		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnection.close(rs1, ps1, conn);
			DBConnection.close(rs2, ps2, conn);
		}
		return roleID;
	}
	
	/**
	 * 获取用户的角色名称
	 * @param curPage
	 * @param pageSize
	 * @return roleName
	 */
	public String getRoleNameByUser(String userID){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String roleName = "";
		StringBuffer sql = new StringBuffer();
		try{
			conn=DBConnection.getConnection();
			sql.append("SELECT ");
			sql.append("name ");
			sql.append("FROM ");
			sql.append("roles ");
			sql.append("WHERE ");
			sql.append("user_role.RoleId IN ( ");
			sql.append("SELECT ");
			sql.append("user_role.RoleId ");
			sql.append("FROM ");
			sql.append("user_role ");
			sql.append("WHERE ");
			sql.append("user_role.UserId = \""+ userID +"\" ");

			ps = conn.prepareStatement(sql.toString());
			rs=ps.executeQuery();

				while(rs.next()){
					roleName = rs.getString("name");
				} 
			}catch (SQLException e) {
			e.printStackTrace();
			}finally{
			DBConnection.close(rs, ps, conn);
		}		
		return roleName;		
	}
	
	/**
	 * 启用/禁用用户
	 * @param role
	 * @return
	 */
	public boolean chgFlag(String userID,int flag){
		Connection conn = null;
		PreparedStatement ps = null,psCount = null;
		ResultSet rsCount = null;
		int count = 0;

		try{
			conn=DBConnection.getConnection();
//			String countsql = "select count(*) as num from roles where name='" + role.getName() +"'";
//			psCount = conn.prepareStatement(countsql);
//			System.out.println(psCount);
//			rsCount=psCount.executeQuery();		
//		    if(rsCount.next()){
//				count=rsCount.getInt("num");
//			}
		    
//		    if(count==0){
			    String sql = "update users set stateflag=? where userid='" + userID +"'";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, flag);

				ps.executeUpdate();
//		    }
			}catch (SQLException e) {
			e.printStackTrace();
			}finally{
			DBConnection.close(rsCount, ps, conn);
		}		
		return true;		
	}
}
