package com.rdp.dao.menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rdp.entity.Tree;
import com.rdp.entity.User;
import com.rdp.util.DBConnection;

public class TreeDao {
	/**
	 * 获取菜单信息
	 * @param tree
	 * @return
	 */
	public List<Tree> getMenuTree(Tree tree,User user) {
		List<Tree> list = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null; 

		StringBuffer sql = new StringBuffer(); 
			try {
				list = new ArrayList<Tree>();
				conn = DBConnection.getConnection();
				
//				sql = "SELECT menusid,pid,text,attributes FROM menus where pid= \""+tree.getId()+" \"order by ordernums";
				
				// id是字符串 搜索的时候要+“” \" 转义为"
				
//				sql.append("SELECT menusid,pid,text,attributes ");
//				sql.append("FROM menus ");
//				sql.append("WHERE pid= \""+tree.getId()+"\" order by ordernums ");
//				sql.append("AND  ");
//				sql.append("ORDER BY ordernums ");
//				sql.toUpperCase();
				sql.append("SELECT menusid,pid,text,attributes ");
				sql.append("FROM ");
				sql.append("menus  ");
				sql.append("WHERE  ");
				sql.append("pid=\""+tree.getId()+"\" AND menus.menusId IN ( ");
				sql.append("SELECT ");
				sql.append("menusId ");
				sql.append("FROM ");
				sql.append("menuauthorization ");
				sql.append("WHERE ");
				sql.append("menuauthorization.RoleId IN ( ");
				sql.append("SELECT ");
				sql.append("roles.RoleId ");
				sql.append("FROM ");
				sql.append("roles ");
				sql.append("WHERE ");
				sql.append("roles.RoleId IN ( ");
				sql.append("SELECT ");
				sql.append("user_role.RoleId ");
				sql.append("FROM ");
				sql.append("user_role ");
				sql.append("WHERE ");
				sql.append("user_role.UserId = ( ");
				sql.append("SELECT ");
				sql.append("users.UserId ");
				sql.append("FROM ");
				sql.append("users ");
				sql.append("WHERE ");
				sql.append("users.UserName = \""+user.getUsername()+"\" ");
				sql.append(") ");
				sql.append(") ");
				sql.append(")) ");
				sql.append("ORDER BY ordernums");
				
				ps = conn.prepareStatement(sql.toString());
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
