package Easyui_Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Easyui_DB.DBConnection;

public class Tree {
	private String id;
	/**
	 * ¸¸½Úµã
	 */
	private String pid;
	private String text;
	/**
	 * URL
	 */
	private String attributes;
	/**
	 * ÅÅÐò
	 */
	private int order;

public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getAttributes(){
		return attributes;
	}
	public void setAttributes(String attributes){
		this.attributes = attributes;
	}
    public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public List<Tree> SchoolTree(Tree tree) {
		List<Tree> list = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null; 
		String sql = ""; 
		
			try {
				list = new ArrayList<Tree>();
				conn = DBConnection.getConnection();
				sql = "SELECT menusid,pid,text,attributes FROM menus where pid= \""+tree.getId()+" \"order by ordernums";
				sql.toUpperCase();
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				Tree s = null;
				while (rs.next()) {
					s = new Tree();
					s.setId(rs.getString("menusid"));
					s.setPid(rs.getString("pid"));
					s.setText(rs.getString("text"));
					s.setAttributes(rs.getString("attributes"));
					list.add(s);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
			DBConnection.close(rs, ps, conn);
			} 
		return list;
	}
}
