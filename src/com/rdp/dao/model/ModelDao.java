package com.rdp.dao.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rdp.entity.Model;
import com.rdp.entity.Role;
import com.rdp.util.DBConnection;
import com.rdp.util.Pager;

public class ModelDao {
	/**
	 * 获取所有模型
	 * @param curPage
	 * @param pageSize
	 * @return 
	 */
	public Pager getPageModel(int curPage,int pageSize){
		List<Model> modellist = new ArrayList<Model>();
		Connection conn = null;
		PreparedStatement ps1 = null, ps2 = null;	
		ResultSet rs1 = null, rs2=null;
		int count=0;
		try{
			conn=DBConnection.getConnection();
			String countsql = "select count(*) as num from model";
			ps1 = conn.prepareStatement(countsql);
		    rs1=ps1.executeQuery();		
		    if(rs1.next()){
				count=rs1.getInt("num");
			}
		    
			String sql = "select * from model order by lv limit "+((curPage-1)*pageSize)+" , "+pageSize;
			ps2 = conn.prepareStatement(sql);
			rs2=ps2.executeQuery();
								
			while(rs2.next()){
				Model m = new Model();
				m.setModelID(rs2.getString("modelid"));
				m.setName(rs2.getString("name"));
				m.setTitle(rs2.getString("title"));
				m.setCreatetime(rs2.getString("createtime"));
				modellist.add(m);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnection.close(rs1, ps1, conn);
			DBConnection.close(rs2, ps2, conn);
		}
		return new Pager(pageSize, curPage, count, modellist);
	}
	
	
	
}
