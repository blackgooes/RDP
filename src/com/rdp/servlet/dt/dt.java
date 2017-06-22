package com.rdp.servlet.dt;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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
public class dt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public dt() {
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
			try {
				getList(request,response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		    throws ServletException, IOException, SQLException {
		response.setCharacterEncoding("utf-8");
		ResultSet rs = null;
	    Statement stmt = null;
	    
	    String table = "user";

	    //获取请求次数
	    String draw = "0";
	    draw = request.getParameter("draw");
	    //数据起始位置
	    String start = request.getParameter("start");
	    //数据长度
	    String length = request.getParameter("length");

	    //总记录数
	    String recordsTotal = "0";

	    //过滤后记录数
	    String recordsFiltered = "";

	    //定义列名
	    String[] cols = {"","name", "position", "salary", "start_date", "office", "extn"};
	    //获取客户端需要那一列排序
	    String orderColumn = "0";
	    orderColumn = request.getParameter("order[0][column]");
	    orderColumn = cols[Integer.parseInt(orderColumn)];
	    //获取排序方式 默认为asc
	    String orderDir = "asc";
	    orderDir = request.getParameter("order[0][dir]");

	   /* Map map = request.getParameterMap();
	    Iterator<String> iter = map.keySet().iterator();
	    while (iter.hasNext()) {
	        String key = iter.next();
	        System.out.println("key=" + key);
	        String[] value = (String[]) map.get(key);
	        System.out.print("value=");
	        for (String v : value) {
//	            out.print(v + "  ") ;
	            System.out.println(v + "  ");
	        }
	    }*/

	    //获取用户过滤框里的字符
	    String searchValue = request.getParameter("search[value]");

	    List<String> sArray = new ArrayList<String>();
	    if (!searchValue.equals("")) {
	        sArray.add(" name like '%" + searchValue + "%'");
	        sArray.add(" position like '%" + searchValue + "%'");
	        sArray.add(" salary like '%" + searchValue + "%'");
	        sArray.add(" start_date like '%" + searchValue + "%'");
	        sArray.add(" office like '%" + searchValue + "%'");
	        sArray.add(" extn like '%" + searchValue + "%'");
	    }

	    String individualSearch = "";
	    if (sArray.size() == 1) {
	        individualSearch = sArray.get(0);
	    } else if (sArray.size() > 1) {
	        for (int i = 0; i < sArray.size() - 1; i++) {
	            individualSearch += sArray.get(i) + " or ";
	        }
	        individualSearch += sArray.get(sArray.size() - 1);
	    }

	    List<Dt> dts = new ArrayList<Dt>();
	    Connection conn = DtDBConnection.getConnection();
	    if (conn != null) {
	        String recordsFilteredSql = "select count(1) as recordsFiltered from " + table;
	        stmt = conn.createStatement();
	        //获取数据库总记录数
	        String recordsTotalSql = "select count(1) as recordsTotal from " + table;
	        rs = stmt.executeQuery(recordsTotalSql);
	        while (rs.next()) {
	            recordsTotal = rs.getString("recordsTotal");
	        }


	        String searchSQL = "";
	        String sql = "SELECT * FROM " + table;
	        if (individualSearch != "") {
	            searchSQL = " where " + individualSearch;
	        }
	        sql += searchSQL;
	        recordsFilteredSql += searchSQL;
	        sql += " order by " + orderColumn + " " + orderDir;
	        recordsFilteredSql += " order by " + orderColumn + " " + orderDir;
	        sql += " limit " + start + ", " + length;


	        rs = stmt.executeQuery(sql);
	        while (rs.next()) {
	        	dts.add(new Dt(rs.getString("name"),
	                    rs.getString("position"),
	                    rs.getString("salary"),
	                    rs.getString("start_date"),
	                    rs.getString("office"),
	                    rs.getString("extn")));
	        }

	        if (searchValue != "") {
	            rs = stmt.executeQuery(recordsFilteredSql);
	            while (rs.next()) {
	                recordsFiltered = rs.getString("recordsFiltered");
	            }
	        } else {
	            recordsFiltered = recordsTotal;
	        }
	    }


	    Map<Object, Object> info = new HashMap<Object, Object>();
	    info.put("data", dts);
	    info.put("recordsTotal", recordsTotal);
	    info.put("recordsFiltered", recordsFiltered);
	    info.put("draw", draw);
	    String json = new Gson().toJson(info);
	    rs.close();
	    stmt.close();
	    conn.close();
	    
        PrintWriter out = response.getWriter();
        response.setContentType("application/json; charset=utf-8");  
        response.setHeader("pragma", "no-cache");  
        response.setHeader("cache-control", "no-cache");  
	    out.write(json);
	    out.close();
		
	}
	private void Add(HttpServletRequest request,HttpServletResponse response) 
		    throws ServletException, IOException {
	}
	
	private void Del(HttpServletRequest request,HttpServletResponse response) 
		    throws ServletException, IOException {
	}

	
}
