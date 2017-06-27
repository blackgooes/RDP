package com.rdp.servlet.dt;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.rdp.dao.table.UserManagementDao;

import com.rdp.entity.Dt;
import com.rdp.entity.Table;
import com.rdp.util.DtDBConnection;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class UserManagement
 */
public class DtEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DtEdit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String method = request.getParameter("method");
		if(method.equals("list")){
				getList(request,response);

		}else if(method.equals("add")){
			Add(request,response);
		}else if(method.equals("del")){
			Del(request,response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	private void getList(HttpServletRequest request,HttpServletResponse response) 
		    throws ServletException, IOException {
	}

	private void Add(HttpServletRequest request,HttpServletResponse response) 
		    throws ServletException, IOException {
	}
	
	private void Del(HttpServletRequest request,HttpServletResponse response) 
		    throws ServletException, IOException {
	}

	
}
