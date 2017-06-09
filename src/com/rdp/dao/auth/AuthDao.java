package com.rdp.dao.auth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rdp.entity.Role;
import com.rdp.entity.Tree;
import com.rdp.entity.User;
import com.rdp.util.DBConnection;

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
	
	
}
