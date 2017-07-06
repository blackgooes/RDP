package com.rdp.servlet.auth;

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
public class RoleManagement_curd_bak extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RoleManagement_curd_bak() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String method = request.getParameter("method");
		String idd = request.getQueryString(); 
		String id = request.getParameter("id");
		String[] delList = request.getParameterValues("delList[]");
		// ����Ϊ�յ��ж�ֵҪд���棬��Ȼ��NULLPoint
		if(("list").equals(method)){
			getList(request,response);
		}
		if((id ==null || id.isEmpty())&(method == null|| method.isEmpty())){
			add(request,response);
		}else if(method == null|| method.isEmpty()){
			update(request,response,id);
		}else if(("del").equals(method)){
			del(request,response);
		}
//		}else if(method.equals("update")){
//			update(request,response);
//		}
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
		// ��ȡȫ����ɫ
//		List<Role> rolelist = aDao.getRole();
		Pager pager = aDao.getPageRole(curPage, pageSize);
		PrintWriter out = response.getWriter();
		List<Role> rolelist = pager.getResultList();
		// ��ǰҳpagesize
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
	    // pager ��Ϣ
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
		String a = request.getParameter("ID");
	}

	private void update(HttpServletRequest request,HttpServletResponse response,String id) 
		    throws ServletException, IOException {
		
		String s = id;
		String roleName = request.getParameter("roleName");
		String remark = request.getParameter("remark");
		String[] auth = request.getParameterValues("user-Character-0-0");
	}
}
