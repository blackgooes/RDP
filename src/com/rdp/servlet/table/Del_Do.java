package com.rdp.servlet.table;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rdp.dao.table.TableDao;

import net.sf.json.JSONObject;

public class Del_Do extends HttpServlet {
	int id;
	String name;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//id = Integer.parseInt(request.getParameter("id"))+1;
		name= new String(request.getParameter("name").getBytes("ISO-8859-1"),"utf-8") ; 
		//System.out.println(name);
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		String msg = ""; 
		try {
			json.put("msg",del());
			out.print(json.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}
	public String del() throws SQLException {
		TableDao t = new TableDao();
		boolean b = t.delete(name);
		if(b){
			return "添加成功";
		}
		else
			return "添加失败";
	}
}