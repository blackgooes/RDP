package com.rdp.servlet.auth;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.rdp.entity.Role;
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
		String username = request.getParameter("username");
		String rolename = request.getParameter("roleName");

		
		
		
		// 获取用户管理界面信息
		if(method.equals("list")){
			if(rolename.equals("-1")){
				getList(request,response);	
			}			
			else{	
				searchList(request,response);
			}	
		// 获取角色名（下拉框）
		}else if(method.equals("getrolename")){
			getRoleName(request,response);
		}else if(method.equals("getEditForm")){
			getEditForm(request,response);
		}else if(method.equals("edit") && ("null".equals(id))){
			add(request,response);
		}else if(method.equals("del")){
			del(request,response);
		}else if(method.equals("edit")){
			edit(request,response);
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

	/**
	 * 获取下拉框角色名
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void getRoleName(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("utf-8");
		AuthDao authDao = new AuthDao();
		authDao.getRole();
		List<Role> rolelist = new ArrayList<Role>();
		rolelist = authDao.getRole();
		
		Gson gson = new Gson();
		String json = gson.toJson(rolelist);
		PrintWriter out = response.getWriter();
        response.setContentType("application/json; charset=utf-8");  
        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");  
		out.print(json);
		out.close();
	}
	
	/**
	 * 获取用户管理界面信息
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
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
	
	
	/**
	 * 获取用户管理界面信息
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void searchList(HttpServletRequest request,HttpServletResponse response) 
		    throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int curPage = Integer.parseInt(request.getParameter("curPage"));
		String username = request.getParameter("username");
		String rolename = request.getParameter("roleName");
    
		UserDao uDao = new UserDao();
		List<User> userlist = new ArrayList<User>();
		// 获取全部角色
//		List<Role> rolelist = aDao.getRole();
		Pager pager = uDao.searchPageUser(curPage, pageSize,username,rolename);
		PrintWriter out = response.getWriter();

		
//		String rolename = uDao.getRoleNameByUser(userID);
		// 当前页pagesize
//		pageSize = pager.getEndIndex() - pager.getStartIndex();       
        int totalRows = pager.getRowCount();
		if(totalRows != 0){
			userlist = pager.getResultList();
		}
        Object flag = "true";
	    Map<Object, Object> info = new HashMap<Object, Object>();
	    String data="";
    	
		    info.put("success", flag);
//		    info.put("pageSizeForGrid", pageSize);
		    info.put("totalRows", totalRows);
		    info.put("curPage", curPage);
		    info.put("data", userlist);
	    

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
	
	/**
	 * 编辑用户信息
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void getEditForm(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("utf-8");
		String userid = request.getParameter("id");
		
		UserDao uDao = new UserDao();
	    Gson gson = new GsonBuilder()  
	    		  .setDateFormat("yyyy-MM-dd HH:mm:ss")  
	    		  .create();  
	    User u = uDao.getEditForm(userid);

		String json = gson.toJson(u);
        response.setContentType("application/json; charset=utf-8");  
        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");  
        PrintWriter out = response.getWriter();
		out.print(json);
		out.close();
	}
	
	private void add(HttpServletRequest request,HttpServletResponse response) 
		    throws ServletException, IOException {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		// 获取当前时间
		String nowTime = refFormatNowDate();
		
		String username = request.getParameter("userName");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String password1 = request.getParameter("password1");
		int sex = Integer.parseInt(request.getParameter("sex"));
		String phone = request.getParameter("phone");
		String rolename = request.getParameter("roleName");
		int stateFlag = Integer.parseInt(request.getParameter("start"));
		
		User user = new User();
		user.setUserid(uuid);
		user.setCreateTime(nowTime);
		user.setUsername(username);
		user.setName(name);		
		if(password.equals(password1)){
			user.setPassword(password);
		}
		else{
			// 写个报错
		}
		user.setGender(sex);
		user.setTel(phone);
		user.setRolename(rolename);
		user.setStateFlag(stateFlag);
		
		
		UserDao uDao = new UserDao();
		uDao.addUser(user);
		
		
	}
	
	private void del(HttpServletRequest request,HttpServletResponse response) 
		    throws ServletException, IOException {
		
		String[] delList = request.getParameterValues("delList[]");
		UserDao authDao=new UserDao();
		if(authDao.delByUserId(delList) == true){
			// 显示成功
		}else{
			// 显示删除 
		}	
		
	}

	private void edit(HttpServletRequest request,HttpServletResponse response) 
		    throws ServletException, IOException {
		String userID = request.getParameter("id");
		String username = request.getParameter("userName");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String password1 = request.getParameter("password1");
		int sex = Integer.parseInt(request.getParameter("sex"));
		String phone = request.getParameter("phone");
		String rolename = request.getParameter("roleName");
		int stateFlag = Integer.parseInt(request.getParameter("start"));
		
		User user = new User();
		user.setUserid(userID);
		user.setUsername(username);
		user.setName(name);		
		if(password.equals(password1)){
			user.setPassword(password);
		}
		else{
			// 写个报错
		}
		user.setGender(sex);
		user.setTel(phone);
		user.setRolename(rolename);
		user.setStateFlag(stateFlag);
		

		UserDao uDao = new UserDao();
		uDao.editUser(user);
		
		
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
	
	public String refFormatNowDate() {
		  Date nowTime = new Date(System.currentTimeMillis());
		  SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  String retStrFormatNowDate = sdFormatter.format(nowTime);		  
		  return retStrFormatNowDate;
		}
}
