package com.rdp.servlet.model;

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

import com.google.gson.Gson;
import com.rdp.dao.auth.UserDao;
import com.rdp.dao.model.FieldDao;
import com.rdp.entity.Field;
import com.rdp.entity.User;
import com.rdp.util.Pager;

/**
 * Servlet implementation class FieldManagement
 */
public class FieldManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FieldManagement() {
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
		String id = request.getParameter("ID");





		// 获取用户管理界面信息
		if(method.equals("list")){
			getList(request,response);
		// 获取角色名（下拉框）
		}
		else if(method.equals("addField")){
			addFieldToModel(request,response);
		}
//			else if(method.equals("getEditForm")){
//			getEditForm(request,response);
//		}else if(method.equals("edit") && ("null".equals(id))){
//			add(request,response);
//		}else if(method.equals("del")){
//			del(request,response);
//		}else if(method.equals("edit")){
//			edit(request,response);
//		}else if(method.equals("chgFlag")){
//			changeFlag(request,response);
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
        String id = request.getParameter("ID");
        
        FieldDao fDao = new FieldDao();
        Pager pager = new Pager();
        // 存在ID时，只查询相关ID字段
        if("null".equals(id)){
        	pager = fDao.getPageField(curPage, pageSize);
        }
        else{
        	pager = fDao.getPageFieldByID(curPage, pageSize,id);
        }
		PrintWriter out = response.getWriter();
		List<Field> fieldlist = pager.getResultList();
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
		    info.put("data", fieldlist);
	    }
	    else
	    {
		    info.put("success", flag);
		    info.put("totalRows", totalRows);
		    info.put("curPage", curPage);
		    info.put("data", "[]");
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
	
	private void addFieldToModel(HttpServletRequest request,HttpServletResponse response) 
		    throws ServletException, IOException {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		// 获取当前时间

		
		String fieldName = request.getParameter("fieldName");
		String fieldLabel = request.getParameter("fieldLabel");
		String fieldType = request.getParameter("fieldType");
		int fieldLength = Integer.parseInt(request.getParameter("fieldLength"));
		int notnull = Integer.parseInt(request.getParameter("notnull"));
		int autoValue = Integer.parseInt(request.getParameter("autoValue"));
		int show = Integer.parseInt(request.getParameter("show"));
		int type = 0;
		int isnull = 0;
		
		Field f = new Field();
		f.setId(fieldName);
		f.setName(fieldLabel);
		f.setType(type);
		f.setLength(fieldLength);
		f.setIsNull(isnull);
		f.setDefaultValue(autoValue);
		f.setIsShow(show);
		FieldDao fDao = new FieldDao();
		fDao.addField(f);
		
		
	}

}
