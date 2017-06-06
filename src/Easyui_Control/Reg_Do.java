package Easyui_Control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import Easyui_Dao.Dao;
/**
 * 
 * @author D_xiao
 *
 */
public class Reg_Do extends HttpServlet {
	String name;
	String password;
	int age;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); 
		response.setCharacterEncoding("UTF-8");
		name= new String(request.getParameter("name").getBytes("ISO-8859-1"),"utf-8") ; 
	    password = request.getParameter("password");
	    age = Integer.parseInt(request.getParameter("age"));
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		String msg = "";
		try {
			json.put("msg",reg());
			out.print(json.toString());
			//System.out.println(name);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public String reg() throws SQLException{
		Dao u= new Dao(name,password,age);
		boolean b = u.register();
		String s = String.valueOf(b);
		return s;
	}
}