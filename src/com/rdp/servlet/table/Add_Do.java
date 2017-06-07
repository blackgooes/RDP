package com.rdp.servlet.table;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.rdp.dao.table.TableDao;
import com.rdp.entity.Table;

public class Add_Do extends HttpServlet {
	String name;
	int age;
	String phone;
	String email;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		name = request.getParameter("name");
		age = Integer.parseInt(request.getParameter("age"));
		phone = request.getParameter("phone");
		email = request.getParameter("email");
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		String msg = ""; 
		try {
			json.put("msg",add());
			out.print(json.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public String add() throws SQLException {
		
		Table t = new Table(name,age,phone,email);
		TableDao tbDao = new TableDao();
		boolean b = tbDao.add(t);
		if(b){
			return "添加成功";
		}
		else
			return "添加失败";
	}
}