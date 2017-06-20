package com.rdp.dao.table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rdp.entity.User;
import com.rdp.util.DBConnection;

public class UserManagementDao {
	
	public List<User> getList(){
		List<User> userlist = new ArrayList<User>();		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
//		User user = null;
		
		try{
			conn=DBConnection.getConnection();
			String sql = "select * from users";
			ps = conn.prepareStatement(sql);
			rs=ps.executeQuery();
			
			while(rs.next()){
				User user = new User();
				user.setUserid(rs.getString("userid"));
				user.setName(rs.getString("name"));
				user.setUsername(rs.getString("username"));
//				user.setAge(rs.getInt("age"));
				user.setPassword(rs.getString("password"));
				userlist.add(user);
			}
		} catch (SQLException e){
			e.printStackTrace();
			
		}finally{
			DBConnection.close(rs, ps, conn);
		}
		return userlist;
	}

}
