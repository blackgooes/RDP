package com.rdp.dao.table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.rdp.entity.Table;
import com.rdp.util.DBConnection;

public class TableDao {
	
	/*public Table(int id){
		this.id = id;
	}*/
	
	public boolean add(Table tb) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		Boolean flag = false;
		ResultSet rs = null;

		try{
			conn = DBConnection.getConnection();
			String sql = "insert into TAB (name,age,phone,email) values(?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1,tb.getName());
			ps.setInt(2,tb.getAge());
			ps.setString(3,tb.getPhone());
			ps.setString(4,tb.getEmail());   
			ps.executeUpdate();
			flag=true;
		}finally{
			DBConnection.close(rs, ps, conn);
		}
		return flag;
	}
	
	public ArrayList<Table> listAll() throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		ArrayList<Table> list = new ArrayList<Table>();
		try{
			conn=DBConnection.getConnection();
			String sql = "select * from TAB";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				Table user = new Table();				
				user.setName(rs.getString("name"));
				user.setAge(rs.getInt("age"));
				user.setPhone(rs.getString("phone"));
				user.setEmail(rs.getString("email"));
				list.add(user);
		}
		}finally{
			DBConnection.close(rs, ps, conn);
		}
		//System.out.println(list.get(1));
		return list;
	}
	
	public boolean delete(String name) throws SQLException{
		boolean flag = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn = DBConnection.getConnection();
			String sql = "delete from TAB where name = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1,name);
			ps.executeUpdate();
		}finally{
			DBConnection.close(rs, ps, conn);
		}
		flag = true;
		return flag;
	}
	
	
}
