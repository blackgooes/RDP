package com.rdp.servlet.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.rdp.dao.model.ModelDao;
import com.rdp.entity.Model;
import com.rdp.util.Pager;

/**
 * Servlet implementation class ModelManagement
 */
public class ModelManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModelManagement() {
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
		String rolename = request.getParameter("modelName");




		// ��ȡ�û����������Ϣ
		if(method.equals("list")){
			getList(request,response);
		// ��ȡ��ɫ����������
		}
//		else if(method.equals("getrolename")){
//			getRoleName(request,response);
//		}else if(method.equals("getEditForm")){
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
		ModelDao mDao = new ModelDao();
		// ��ȡȫ����ɫ
//		List<Role> modellist = aDao.getRole();
		Pager pager = mDao.getPageModel(curPage, pageSize);
		PrintWriter out = response.getWriter();
		List<Model> modellist = pager.getResultList();
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
		    info.put("data", modellist);
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

}
