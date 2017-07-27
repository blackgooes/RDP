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
	 * 获取所有用户
	 * @param 
	 * @return user
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
				r.setCreateTime(rs2.getString("createTime"));
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
		StringBuffer sql1 = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();
		int cols1=0;
		int cols2=0;
		sql1.append("DELETE FROM user_role WHERE ");	
		for(int i=0;i<userId.length;i++){
			sql1.append("userid = '"+userId[i]+ "' or ");
			
		}
		sql1.delete(sql1.length() - 4, sql1.length());
		
		sql2.append("DELETE FROM users WHERE ");	
		for(int i=0;i<userId.length;i++){
			sql2.append("userid = '"+userId[i]+ "' or ");
			
		}
		sql2.delete(sql2.length() - 4, sql2.length());		
		
		try{
			conn=DBConnection.getConnection();
			

			ps = conn.prepareStatement(sql1.toString());			
			cols1 = ps.executeUpdate();
			
			ps = conn.prepareStatement(sql2.toString());			
			cols2 = ps.executeUpdate();
						
			}catch (SQLException e) {
			e.printStackTrace();
			}finally{
			DBConnection.close(rs, ps, conn);
		}
		if(cols1 > 0 && cols2>0){
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
	
	
	/**
	 * 搜索角色
	 * @param user
	 * @return
	 */
	public Pager searchPageUser(int curPage,int pageSize,String userName,String roleName){
		List<User> userlist = new ArrayList<User>();
		Connection conn = null;
		PreparedStatement ps1 = null, ps2 = null;	
		ResultSet rs1 = null, rs2=null;
		int count=0;
		try{
			conn=DBConnection.getConnection();
			String countsql = "select count(*) as num from users";
			countsql += " left join user_role on users.userid = user_role.userid";
			countsql += " left join roles on roles.roleid = user_role.roleid";
			countsql += " where users.name like ? and roles.name = ?";
			ps1 = conn.prepareStatement(countsql);
			ps1.setString(1, "%"+userName+"%");
			ps1.setString(2, roleName);
		    rs1=ps1.executeQuery();		
		    if(rs1.next()){
				count=rs1.getInt("num");
			}
//		    if(count==0){
//		    	return new Pager(pageSize, curPage, 0, userlist);
//		    }
//			String sql = "select * from users limit "+((curPage-1)*pageSize)+" , "+pageSize;
		    // 两left join拼上了另个表的rolename
		    String sql = "select users.*,roles.name as rolename from users";
			sql += " left join user_role on users.userid = user_role.userid";
			sql += " left join roles on roles.roleid = user_role.roleid";
			sql += " where users.name like ? and roles.name = ? limit " +((curPage-1)*pageSize)+" , "+pageSize;
			ps2 = conn.prepareStatement(sql);
			ps2.setString(1, "%"+userName+"%");
			ps2.setString(2, roleName);
			rs2=ps2.executeQuery();
								
			while(rs2.next()){
				User r = new User();
				r.setUserid(rs2.getString("userid"));
				r.setUsername(rs2.getString("username"));
				r.setPassword(rs2.getString("password"));
				r.setName(rs2.getString("name"));
				r.setRemark(rs2.getString("remark"));
				r.setStateFlag(rs2.getInt("stateFlag"));
				r.setCreateTime(rs2.getString("createTime"));
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
	 * 搜索角色
	 * @param user
	 * @return
	 */
	public User getEditForm(String userid){
		User userlist = new User();
		Connection conn = null;
		PreparedStatement ps = null;	
		ResultSet rs = null;
		int count=0;
		try{
			conn=DBConnection.getConnection();
			String sql = "select users.*,roles.name as rolename from users";
			sql += " left join user_role on users.userid = user_role.userid";
			sql += " left join roles on roles.roleid = user_role.roleid";
			sql += " where users.userid = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, userid);
		    rs=ps.executeQuery();		
		    if(rs.next()){
				User r = new User();
				r.setUserid(rs.getString("userid"));
				r.setUsername(rs.getString("username"));
				r.setPassword(rs.getString("password"));
				r.setName(rs.getString("name"));
				r.setRemark(rs.getString("remark"));
				r.setStateFlag(rs.getInt("stateFlag"));
				r.setCreateTime(rs.getString("createTime"));
				r.setGendername(rs.getInt("gender") == 1 ? "男" : "女");
				r.setTel(rs.getString("tel"));
				r.setRemark(rs.getString("remark"));
				r.setRolename(rs.getString("rolename"));
				userlist=r;
			}

		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnection.close(rs, ps, conn);

		}
		return userlist;
	}
	
	/**
	 * 修改
	 * @param user
	 * @return
	 */
	public boolean editUser(User user){
		Connection conn = null;
		PreparedStatement ps1 = null, ps2 = null;	
		ResultSet rs1 = null, rs2=null;
		int count = 0;
		String sql1 = "update roles set name = '" +user.getRolename() +"' where roleid in (select roleid from user_role where userid ='"+ user.getUserid() +"')";
	    StringBuffer sql2 = new StringBuffer();
	    sql2.append("UPDATE ");
	    sql2.append("users ");
	    sql2.append("SET ");
	    sql2.append("username=? ,");
	    sql2.append("password=? ,");
	    sql2.append("name=? ,");
	    sql2.append("stateflag=? ,");
	    sql2.append("gender=? ,");
	    sql2.append("tel=? , ");
	    sql2.append("remark=? ");	
	    sql2.append("WHERE ");
	    sql2.append("userid=?");
		
		try{
					
			conn=DBConnection.getConnection();
			// 手动提交（出错可以回滚）
			conn.setAutoCommit(false);		
		    ps1 = conn.prepareStatement(sql1);
		    ps1.executeUpdate();
		    
			ps2 = conn.prepareStatement(sql2.toString());
			ps2.setString(1, user.getUsername());
			ps2.setString(2, user.getPassword());
			ps2.setString(3, user.getName());
			ps2.setInt(4, user.getStateFlag());
			ps2.setInt(5, user.getGender());
			ps2.setString(6, user.getTel());
			ps2.setString(7, user.getRemark());
			ps2.setString(8, user.getUserid());
			
			ps2.executeUpdate();
			conn.commit();
//		    }
			}catch (SQLException e) {
				e.printStackTrace();
				try{
					conn.rollback();
				}catch (SQLException e1){
					e1.printStackTrace();
				}
			}finally{
				DBConnection.close(rs1, ps1, conn);
				DBConnection.close(rs2, ps2, conn);
		}		
		return true;		
	}
	
	/**
	 * 增加用户
	 * @param user
	 * @return
	 */
	public boolean addUser(User user){
		Connection conn = null;
		PreparedStatement ps1 = null, ps2 = null;	
		ResultSet rs1 = null, rs2=null;
		int count = 0;		
		
		String sql1 = "insert into users values(?,?,?,?,?,?,?,?,?)";
		// 这句要在最后插入数据，不然会违反外键约束
		String sql2 = "insert into user_role(userid,roleid) select '"+user.getUserid() +"' as userid,roleid from roles where name='" +user.getRolename()+"'";

		//String sql2 = "update roles set name = '" +user.getRolename() +"' where roleid in (select roleid from user_role where userid ='"+ user.getUserid() +"')";


		
		try{
					
			conn=DBConnection.getConnection();
			// 手动提交（出错可以回滚）
			conn.setAutoCommit(false);		
		    ps1 = conn.prepareStatement(sql1);
		   
		    ps1.setString(1, user.getUserid());
			ps1.setString(2, user.getUsername());
			ps1.setString(3, user.getPassword());
			ps1.setString(4, user.getName());
			ps1.setInt(5, user.getStateFlag());
			ps1.setString(6, user.getCreateTime());
			ps1.setInt(7, user.getGender());
			ps1.setString(8, user.getTel());
			ps1.setString(9, user.getRemark());		
		    ps1.executeUpdate();
			
			ps2 = conn.prepareStatement(sql2);

			
			ps2.executeUpdate();
			conn.commit();
//		    }
			}catch (SQLException e) {
				e.printStackTrace();
				try{
					conn.rollback();
				}catch (SQLException e1){
					e1.printStackTrace();
				}
			}finally{
				DBConnection.close(rs1, ps1, conn);
				DBConnection.close(rs2, ps2, conn);
		}		
		return true;		
	}
}
