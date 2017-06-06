package Easyui_DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBConnection {
	private static String driverName = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/rdp";
	private static String user = "root";
	private static String password = "blackwing";
	
	private DBConnection(){
	}
		static {
			try {
				System.out.println(driverName);
				Class.forName(driverName);
			} catch (ClassNotFoundException e) {
				System.out.println("找不到驱动类");
			}
		}
		public static Connection getConnection() throws SQLException{
			return DriverManager.getConnection(url,user,password);
		}
		public static void close(ResultSet rs,Statement st,Connection conn){
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}finally{
					if(st!=null){
						try {
							st.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}finally{
							if(conn!=null){
								try {
									conn.close();
								} catch (SQLException e) {
									e.printStackTrace();
								}
							}
					    }
					}
				}
			}
		}
}
