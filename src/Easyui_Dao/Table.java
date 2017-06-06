package Easyui_Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Easyui_DB.DBConnection;

public class Table {
	String name;
	int age;
	String phone;
	String email;
	//int id;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	/*public void setId(int id){
		this.id = id;
	}
	public int getId(int id){
		return id;
	}*/
	public Table(String name, int age, String phone, String email) {
		super();
		this.name = name;
		this.age = age;
		this.phone = phone;
		this.email = email;
	}
	public Table(){
		
	}
	/*public Table(int id){
		this.id = id;
	}*/
	
	public boolean add() throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		Boolean flag = false;
		ResultSet rs = null;
		try{
			conn = DBConnection.getConnection();
			String sql = "insert into TAB (name,age,phone,email) values(?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1,name);
			ps.setInt(2,age);
			ps.setString(3,phone);
			ps.setString(4,email);   
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
