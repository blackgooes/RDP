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
public class UserManagement2_bak6_29 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserManagement2_bak6_29() {
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
		int pagezise = pager.getStartIndex() - pager.getEndIndex();
		

		//String json = new Gson().toJson(rolelist);
		//System.out.println(json.toString());
//		out.print(json.toString());
        
        int totalRows = pager.getRowCount();
        Object flag = "true";
	    Map<Object, Object> info = new HashMap<Object, Object>();
	    String data="";
	    if(totalRows != 0)
	    {
//	    	data += "{\"success\": true, \"totalRows\": " +totalRows+ ",\"curPage\": 1, \"data\": ";
//	    	data += JSONArray.fromObject(rolelist);
//	    	data += "}"	
	    	
		    info.put("success", flag);
//		    info.put("pageSize", pagezise);
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
	    request.setAttribute("pager", pager);
	    Object jsons = new Object();
        JSONArray json = new JSONArray();

        
//        json = JSONArray.fromObject(info);

        
        
        
        
		String json2 = new Gson().toJson(info);
        response.setContentType("application/json; charset=utf-8");  
        response.setHeader("pragma", "no-cache");  
        response.setHeader("cache-control", "no-cache");  
		out.print(json2);
		out.close();
		
//			response.setCharacterEncoding("utf-8");
//			String rows = request.getParameter("rows");
//			String page = request.getParameter("page");
//			//System.out.println(rows+page);
//			// 获取当前页数
//			int p = Integer.parseInt(page);
//			// 当前页查出最大页数
//			int r = Integer.parseInt(rows);
//			// 从第几页开始
//			int begin = (p-1)*r;
//			// 控制当前页数最大数据
//			int count = r;
//			int num = begin;
//
//			try {
//				HttpSession session = request.getSession();
//				Table u = (Table)session.getAttribute("user");
//				Table userTb = new Table();
//				ArrayList<Table> list = new ArrayList<Table>();
////				u.setName(name);
//				list.add(u);
//				
//				ArrayList<Table> newList = new ArrayList<Table>();
//				while(count>0&&num<list.size()){
//					newList.add(list.get(num)); 
//					num++;
//					count--;
//				}
//				int total = list.size();
//				//System.out.println(newList.get(1));
//				PrintWriter out = response.getWriter();
//				String json = "{\"total\":"+total+" , \"rows\":"+JSONArray.fromObject(newList).toString()+"}";
//				//System.out.println(json.toString());
//				out.print(json.toString());
////			} catch (SQLException e1) {
////				e1.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		
	}
	private void Add(HttpServletRequest request,HttpServletResponse response) 
		    throws ServletException, IOException {
	}
	
	private void Del(HttpServletRequest request,HttpServletResponse response) 
		    throws ServletException, IOException {
	}

	
}
