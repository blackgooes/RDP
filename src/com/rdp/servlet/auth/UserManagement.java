package com.rdp.servlet.auth;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rdp.dao.auth.AuthDao;
import com.rdp.dao.auth.UserDao;
import com.rdp.entity.User;
import com.rdp.util.Pager;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class UserManagement
 */
public class UserManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserManagement() {
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
		
		if(method.equals("list")){
			getList(request,response);
		}else if(method.equals("add")){
			add(request,response);
		}else if(method.equals("del")){
			del(request,response);
		}else if(method.equals("update")){
			update(request,response);
		}else if(method.equals("chgFlag")){
			changeFlag(request,response);
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
    
		UserDao uDao = new UserDao();
		// 获取全部角色
//		List<Role> rolelist = aDao.getRole();
		Pager pager = uDao.getPageUser(curPage, pageSize);
		PrintWriter out = response.getWriter();
		List<User> userlist = pager.getResultList();
		
//		String rolename = uDao.getRoleNameByUser(userID);
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
		    info.put("data", userlist);
	    }
	    else
	    {
//	    	data += "{\"success\": false}]";
	    	info.put("success", "false");
	    }
	    // pager 信息
//	    request.setAttribute("pager", pager);

	    Gson gson = new GsonBuilder()  
	    		  .setDateFormat("yyyy-MM-dd HH:mm:ss")  
	    		  .create();  
//		String json = new Gson().toJson(info);
	    String json = gson.toJson(info);
        response.setContentType("application/json; charset=utf-8");  
        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");  
		out.print(json);
		out.close();
		
	}
	private void add(HttpServletRequest request,HttpServletResponse response) 
		    throws ServletException, IOException {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		String roleName = request.getParameter("roleName");
		String remark = request.getParameter("remark");
		// 获取checkbox
		String[] auth = request.getParameterValues("user-Character-0-0");
	}
	
	private void del(HttpServletRequest request,HttpServletResponse response) 
		    throws ServletException, IOException {
		
		String[] delList = request.getParameterValues("delList[]");
		UserDao authDao=new UserDao();
		if(authDao.delByUserId(delList) == true){
			getList(request,response);
		}else{
			// 定位至错误页面
		}	
		
	}

	private void update(HttpServletRequest request,HttpServletResponse response) 
		    throws ServletException, IOException {
	}
	
	/**
	 * 更改启用状态
	 * @param request
	 * @param response
	 */
	private void changeFlag(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String userID = request.getParameter("id");
		int flag=0;
		// 因为flag默认可能为空 0:启用 1：禁用
		if(request.getParameter("flag") ==null || request.getParameter("flag").isEmpty()){
			flag = Integer.parseInt(request.getParameter("flag"));
		}else{
			flag = Integer.parseInt(request.getParameter("flag"));
		}
					
		UserDao uDao = new UserDao();
		uDao.chgFlag(userID, flag);
		
		
		
	}
}
