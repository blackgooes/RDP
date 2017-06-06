package Easyui_Control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Easyui_Dao.Table;
import net.sf.json.JSONArray;
public class Table_Do extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response){
		response.setCharacterEncoding("utf-8");
		String rows = request.getParameter("rows");
		String page = request.getParameter("page");
		//System.out.println(rows+page);
		int p = Integer.parseInt(page);
		int r = Integer.parseInt(rows);
		int begin = (p-1)*r;
		int count = r;
		int num = begin;
		Table t = new Table();
		try {
			ArrayList<Table> list = t.listAll();
			ArrayList<Table> newList = new ArrayList<Table>();
			while(count>0&&num<list.size()){
				newList.add(list.get(num)); 
				num++;
				count--;
			}
			int total = list.size();
			//System.out.println(newList.get(1));
			PrintWriter out = response.getWriter();
			String json = "{\"total\":"+total+" , \"rows\":"+JSONArray.fromObject(newList).toString()+"}";
			//System.out.println(json.toString());
			out.print(json.toString());
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
