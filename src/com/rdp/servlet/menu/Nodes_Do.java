package com.rdp.servlet.menu;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rdp.dao.menu.TreeDao;
import com.rdp.entity.Tree;

import net.sf.json.JSONObject;
public class Nodes_Do extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
//		String theIdString = request.getParameter("id");//父节点id
		// 父节点id		
		String id = request.getParameter("id");	
		//System.out.println(theIdString);
//		int id=Integer.parseInt(theIdString);
		List<Tree> list=getMenu(id);
		//System.out.println(list.get(1));
        String sc="[";
        for(Tree tree:list){
        	sc+="{";
        	List<Tree> l=getMenu(tree.getId());
        	if(l.size()!=0){
        	  sc+=String.format("\"id\": \"%s\", \"text\": \"%s\", \"state\": \"closed\"", tree.getId() ,tree.getText());
        	}else{
        		sc+=String.format("\"id\": \"%s\", \"text\": \"%s\", \"attributes\":  \"{%s}\" , \"state\": \"\"", tree.getId() ,tree.getText(),tree.getAttributes());
        	}
        	sc+="},";
        }
        sc=sc.substring(0, sc.length() - 1);
        sc+="]";
        JSONObject  jobj = new JSONObject();
		jobj.put("msg",sc);
		response.getWriter().write(jobj.toString());
		//System.out.println(jobj.toString());
	}
    public List<Tree> getMenu(String id){
    	Tree t = new Tree();
    	t.setId(id);
    	TreeDao treeDao = new TreeDao();
    	return treeDao.getMenuTree(t);
    }
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
}
