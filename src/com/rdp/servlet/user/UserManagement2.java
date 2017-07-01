package com.rdp.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.rdp.dao.auth.AuthDao;
import com.rdp.dao.table.UserManagementDao;
import com.rdp.dao.user.UserDao;
import com.rdp.entity.Role;
import com.rdp.entity.Table;
import com.rdp.entity.User;
import com.rdp.util.Pager;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class UserManagement
 */
public class UserManagement2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserManagement2() {
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
			add(request,response);
		}else if(method.equals("del")){
			del(request,response);
		}else if(method.equals("update")){
			update(request,response);
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
		response.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int curPage = Integer.parseInt(request.getParameter("curPage"));
       
		AuthDao aDao = new AuthDao();
		// 获取全部角色
//		List<Role> rolelist = aDao.getRole();
		Pager pager = aDao.getPageRole(curPage, pageSize);
		PrintWriter out = response.getWriter();
		List<Role> rolelist = pager.getResultList();
		// 当前页pagesize
//		pageSize = pager.getEndIndex() - pager.getStartIndex();       
        int totalRows = pager.getRowCount();
        Object flag = "true";
	    Map<Object, Object> info = new HashMap<Object, Object>();
	    String data="";
	    if(totalRows != 0)
	    {	    	
		    info.put("success", flag);
//		    info.put("pageSizeForGrid", pageSize);
		    info.put("totalRows", totalRows);
		    info.put("curPage", curPage);
		    info.put("data", rolelist);
	    }
	    else
	    {
	    	data += "{\"success\": false}]";
//	    	info.put("success", "false");
	    }
	    // pager 信息
//	    request.setAttribute("pager", pager);

		String json = new Gson().toJson(info);
        response.setContentType("application/json; charset=utf-8");  
        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");  
		out.print(json);
		out.close();
		
	}
	private void add(HttpServletRequest request,HttpServletResponse response) 
		    throws ServletException, IOException {
	}
	
	private void del(HttpServletRequest request,HttpServletResponse response) 
		    throws ServletException, IOException {
	}

	private void update(HttpServletRequest request,HttpServletResponse response) 
		    throws ServletException, IOException {
	}
}
