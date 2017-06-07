package com.rdp.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.rdp.dao.user.UserDao;
import com.rdp.entity.User;

import net.sf.json.JSONObject;
public class Login_Do extends HttpServlet {
	String name = "";
	String password = "";
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); 
		response.setCharacterEncoding("utf-8");
		name= new String(request.getParameter("name").getBytes("ISO-8859-1"),"utf-8") ; 
	    //System.out.println(name);
	    password = request.getParameter("password");
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		String msg = ""; 
		try {
			json.put("msg",login());
			out.print(json.toString());
			HttpSession session = request.getSession();
			session.setAttribute("name", login());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	public String login() throws SQLException{
		UserDao userDao = new UserDao();
		User u = new User(name,password);
		u = userDao.login(u);
		if(u.getName()!=null){
			return u.getName();
		}
		else
			return null;
	}
}
