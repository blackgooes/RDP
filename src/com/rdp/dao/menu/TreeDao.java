package com.rdp.dao.menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rdp.entity.Tree;
import com.rdp.util.DBConnection;

public class TreeDao {

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
