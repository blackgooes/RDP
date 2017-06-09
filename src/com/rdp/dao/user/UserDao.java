package com.rdp.dao.user;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.rdp.entity.User;
import com.rdp.util.DBConnection;
/**
 * 
 * @author D_xiao
 *
 */

public class UserDao {

	//1、用户登录使用，判断是否存在用户
	public User login(User user) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
//		User user = null;
		try{
			conn=DBConnection.getConnection();
			String sql = "select * from users where username = ? and password = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1,user.getName());
			ps.setString(2,user.getPassword());
			rs=ps.executeQuery();
			user = new User();
			while(rs.next()){
				user.setName(rs.getString("name"));
				user.setUsername(rs.getString("username"));
//				user.setAge(rs.getInt("age"));
				user.setPassword(rs.getString("password"));
			}
		}finally{
			DBConnection.close(rs, ps, conn);
		}
		return user;
	}
/*
	//1.1管理员登录使用，判断是否存在
	public Dao loginGl() throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Dao user = null;
		
		try{
			conn=DBConnection.getConnection();
			String sql = "select * from user2 where name = ? and password = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1,name);
			ps.setString(2,password);
			rs=ps.executeQuery();
			while(rs.next()){
				user = new Dao();
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
			}
		}finally{
			DBConnection.close(rs, ps, conn);
		}
		
		return user;
	}
	*/
	//2、用户注册：插入一条数据
	public boolean register(User user) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		Boolean flag = false;
		ResultSet rs = null;
		try{
			conn = DBConnection.getConnection();
			String sql = "insert into easyuiTest (name,password,age) values(?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1,user.getName());
			ps.setString(2,user.getPassword());
//			ps.setInt(3,age);
			ps.executeUpdate();
			flag=true;
		}finally{
			DBConnection.close(rs, ps, conn);
		}
		return flag;
	}
	/*
	//3、用户个人信息修改：更新一条记录
	public boolean update() throws SQLException{
		Connection conn = null;
		ResultSet rs = null;
		Boolean flag = null;
		PreparedStatement ps = null;
		try{
			conn=DBConnection.getConnection();
			String sql = "update [user] set name = ?,password = ?,age = ? ,email = ?,birthday = ? where id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1,name);
			ps.setString(2,password);
//			ps.setInt(3,age);
			ps.executeUpdate();
			flag = true;
		}finally{
			DBConnection.close(rs, ps, conn);
		}
		return flag;
	}
	
	//4、管理员登陆后查看所有用户列表
	public ArrayList<Dao> listAll() throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		ArrayList<Dao> all = new ArrayList<Dao>();
		try{
		conn=DBConnection.getConnection();
		String sql = "select * from [user]";
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		while(rs.next()){
			Dao user = new Dao();
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
//			user.setAge(rs.getInt("age"));
			all.add(user);
		}
		}finally{
			DBConnection.close(rs, ps, conn);
		}
		
		return all;
	}
	*/
	/*//5、管理员根据姓名name模糊查询
	public ArrayList<User> listAllByName() throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Dao> all = new ArrayList<User>();
		try{
		conn=DBConnection.getConnection();
		String sql = "select * from [user] where name lile ?";
		ps = conn.prepareStatement(sql);
		ps.setString(1,"%" + name + "%");
		rs = ps.executeQuery();
		while(rs.next()){
			User user = new User();
			user.setId(rs.getInt("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
			user.setEmail(rs.getString("email"));
			user.setAge(rs.getInt("age"));
			user.setBirthday(rs.getDate("birthday"));
			all.add(user);
		}
		}finally{
			DBConnection.close(rs, ps, conn);
		}
		return all;
	}
	//6、管理员根据id删除一个用户
	public boolean delete(int id) throws SQLException{
		boolean flag = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn = DBConnection.getConnection();
			String sql = "delete from [user] where id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1,id);
			ps.executeUpdate();
		}finally{
			DBConnection.close(rs, ps, conn);
		}
		flag = true;
		return flag;
	}
	//7、根据用户id得到User对象（查询）
	public User get(int id) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = new User();
		try{
		conn = DBConnection.getConnection();
		String sql = "select * from [user] where id = ?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		rs = ps.executeQuery();
		while(rs.next()){
			user.setId(rs.getInt("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
			user.setEmail(rs.getString("email"));
			user.setAge(rs.getInt("age"));
			user.setBirthday(rs.getDate("birthday"));
		}
		}finally{
			DBConnection.close(rs, ps, conn);
		}
		
		return user;
	}
	
	// 8、得到user表的全部记录数
		public int getAllCount() throws SQLException {
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			int count = 0;
			try {
				conn = DBConnection.getConnection();
				String sql = "SELECT COUNT(id) from [user]";
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				if (rs.next()) {
					count = rs.getInt(1);
				}
			} finally {
				DBConnection.close(rs, ps, conn);
			}
			return count;
		}

		// 9、得到user表中符合条件的记录数
		public int getByLikeCount(String cond) throws SQLException {
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			int count = 0;
			try {
				conn = DBConnection.getConnection();
				String sql = "SELECT COUNT(id) from [user] WHERE name LIKE ? ";
				ps = conn.prepareStatement(sql);
				ps.setString(1, "%" + cond + "%");
				rs = ps.executeQuery();
				if (rs.next()) {
					count = rs.getInt(1);
				}
			} finally {
				DBConnection.close(rs, ps, conn);
			}
			return count;
		}

		// 10、查询数据库，得到当前页currentPage要显示的lineSize个记录数
		public List listAll(int currentPage, int lineSize) throws SQLException {
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			List all = new ArrayList();
			int counts = this.getAllCount();
			int ntop = counts - (currentPage - 1) * lineSize;
			try {
				conn = DBConnection.getConnection();
				String sql = "select top " + lineSize + " u.* from (select top "
						+ ntop + " * from [user] order by id desc) u order by id";
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				while (rs.next()) {
					User user = new User();
					user.setId(rs.getInt("id"));
					user.setName(rs.getString("name"));
					user.setPassword(rs.getString("password"));
					user.setEmail(rs.getString("email"));
					user.setAge(rs.getInt("age"));
					user.setBirthday(rs.getDate("birthday"));
					all.add(user);
				}
			} finally {
				DBConnection.close(rs, ps, conn);
			}
			return all;
		}

		// 11、查询数据库，根据keyWord得到当前页currentPage要显示的lineSize个记录数
		public List listByLike(String keyWord, int currentPage, int lineSize)
				throws SQLException {
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			List all = new ArrayList();
			int counts = this.getByLikeCount(keyWord);
			int ntop = counts - (currentPage - 1) * lineSize;
			if (ntop <= 0) {
				currentPage = (counts + lineSize - 1) / lineSize;
				ntop = counts - (currentPage - 1) * lineSize;
			}
			try {
				conn = DBConnection.getConnection();
				String sql = "select top "
						+ lineSize
						+ " u.* from (select top "
						+ ntop
						+ " * from [user] where name LIKE ? order by id desc) u order by id";
				ps = conn.prepareStatement(sql);
				ps.setString(1, "%" + keyWord + "%");
				rs = ps.executeQuery();
				while (rs.next()) {
					User user = new User();
					user.setId(rs.getInt("id"));
					user.setName(rs.getString("name"));
					user.setPassword(rs.getString("password"));
					user.setEmail(rs.getString("email"));
					user.setAge(rs.getInt("age"));
					user.setBirthday(rs.getDate("birthday"));
					all.add(user);
				}
			} finally {
				DBConnection.close(rs, ps, conn);
			}
			return all;
		}
	*/

  /* //main函数测试方法：
   public static void main(String[] args){
		
	   //测试 login()
	   User u = new User("sa","sa");
	   try {
		u = u.login();
		System.out.println(u.getName()+"用户存在");
	   } catch (SQLException e) {
		e.printStackTrace();
	   }
	}*/

}

