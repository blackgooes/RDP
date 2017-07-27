package com.rdp.dao.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rdp.entity.Field;
import com.rdp.entity.Model;
import com.rdp.entity.User;
import com.rdp.util.DBConnection;
import com.rdp.util.Pager;

public class FieldDao {
	/**
	 * ��ȡģ�����ֶ�
	 * @param curPage
	 * @param pageSize
	 * @return 
	 */
	public Pager getPageField(int curPage,int pageSize){
		List<Field> fieldlist = new ArrayList<Field>();
		Connection conn = null;
		PreparedStatement ps1 = null, ps2 = null;	
		ResultSet rs1 = null, rs2=null;
		int count=0;
		try{
			conn=DBConnection.getConnection();
			String countsql = "select count(*) as num from tmp_field";
			ps1 = conn.prepareStatement(countsql);
		    rs1=ps1.executeQuery();		
		    if(rs1.next()){
				count=rs1.getInt("num");
			}
		    
			String sql = "select * from field order by lv limit "+((curPage-1)*pageSize)+" , "+pageSize;
			ps2 = conn.prepareStatement(sql);
			rs2=ps2.executeQuery();
								
			while(rs2.next()){
				Field f = new Field();
				f.setId(rs2.getString("id"));
				f.setName(rs2.getString("name"));
				f.setType(rs2.getInt("type"));
				f.setLength(rs2.getInt("length"));
				f.setIsNull(rs2.getInt("Isnull"));
				f.setDefaultValue(rs2.getInt("DefaultValue"));
				f.setIsShow(rs2.getInt("IsShow"));
				
				fieldlist.add(f);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnection.close(rs1, ps1, conn);
			DBConnection.close(rs2, ps2, conn);
		}
		return new Pager(pageSize, curPage, count, fieldlist);
	}
	
	/**
	 * ��ȡģ�����ֶ�
	 * @param curPage
	 * @param pageSize
	 * @return 
	 */
	public Pager getPageFieldByID(int curPage,int pageSize,String id){
		List<Field> fieldlist = new ArrayList<Field>();
		Connection conn = null;
		PreparedStatement ps1 = null, ps2 = null;	
		ResultSet rs1 = null, rs2=null;
		int count=0;
		try{
			conn=DBConnection.getConnection();
			String countsql = "select count(*) as num from field where modelid='"+ id +"'";
			ps1 = conn.prepareStatement(countsql);
		    rs1=ps1.executeQuery();		
		    if(rs1.next()){
				count=rs1.getInt("num");
			}
		    
			String sql = "select * from field where modelid='"+ id +"' order by lv limit "+((curPage-1)*pageSize)+" , "+pageSize;
			ps2 = conn.prepareStatement(sql);
			rs2=ps2.executeQuery();
								
			while(rs2.next()){
				Field f = new Field();
				f.setId(rs2.getString("id"));
				f.setName(rs2.getString("name"));
				f.setType(rs2.getInt("type"));
				f.setLength(rs2.getInt("length"));
				f.setIsNull(rs2.getInt("Isnull"));
				f.setDefaultValue(rs2.getInt("DefaultValue"));
				f.setIsShow(rs2.getInt("IsShow"));
				
				fieldlist.add(f);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnection.close(rs1, ps1, conn);
			DBConnection.close(rs2, ps2, conn);
		}
		return new Pager(pageSize, curPage, count, fieldlist);
	}
	
	public boolean addField(Field f){
		Connection conn = null;
		PreparedStatement ps1 = null, ps2 = null;	
		ResultSet rs1 = null, rs2=null;
		int count = 0;		
		
		String sql1 = "insert into field values(?,?,?,?,?,?,?,?,?)";
		// ���Ҫ�����������ݣ���Ȼ��Υ�����Լ��
//		String sql2 = "insert into user_role(userid,roleid) select '"+user.getUserid() +"' as userid,roleid from roles where name='" +user.getRolename()+"'";

		//String sql2 = "update roles set name = '" +user.getRolename() +"' where roleid in (select roleid from user_role where userid ='"+ user.getUserid() +"')";

		
		try{
					
			conn=DBConnection.getConnection();
			// �ֶ��ύ��������Իع���
			conn.setAutoCommit(false);		
		    ps1 = conn.prepareStatement(sql1);
		   
		    ps1.setString(1, f.getId());
		    ps1.setString(2,f.getName());
		    ps1.setInt(3,f.getType());
		    ps1.setInt(4,f.getLength());
		    ps1.setInt(5,f.getIsNull());
		    ps1.setInt(6,f.getDefaultValue());

		    ps1.executeUpdate();
			
//			ps2 = conn.prepareStatement(sql2);

			
			ps2.executeUpdate();
			conn.commit();
//		    }
			}catch (SQLException e) {
				e.printStackTrace();
				try{
					conn.rollback();
				}catch (SQLException e1){
					e1.printStackTrace();
				}
			}finally{
				DBConnection.close(rs1, ps1, conn);
				DBConnection.close(rs2, ps2, conn);
		}		
		return true;		
	}
	
	public int getRoleNameByUser(String userID){
		Connection conn = null;
		PreparedStatement ps = null;
		int result = 0;
		String roleName = "";
		StringBuffer sql = new StringBuffer();
		try{
			conn=DBConnection.getConnection();
			sql.append(" CREATE TEMPORARY TABLE tmp_field (");
			sql.append(" `modelID` varchar(32) NOT NULL, ");
			sql.append(" `name` varchar(255) DEFAULT NULL COMMENT '�ֶα�ʶ',");
			sql.append(" `type` int(11) DEFAULT NULL COMMENT '�ֶ�����',");
			sql.append(" `length` int(11) DEFAULT NULL COMMENT '����',");
			sql.append(" `isNull` tinyint(1) DEFAULT NULL COMMENT '�Ƿ����',");
			sql.append(" `defaultValue` varchar(255) DEFAULT NULL COMMENT 'Ĭ��ֵ',");
			sql.append(" `isShow` tinyint(1) DEFAULT NULL COMMENT '�б���ʾ',");
			sql.append(" `lv` int(11) NOT NULL AUTO_INCREMENT,");
			sql.append(" UNIQUE KEY `lv` (`lv`),");
			sql.append(" KEY `id` (`id`)");
			sql.append(" ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;");
			
			
			ps = conn.prepareStatement(sql.toString());
			result=ps.executeUpdate();


			}catch (SQLException e) {
			e.printStackTrace();
			}finally{
			DBConnection.close(null,ps, conn);
		}		
		return result;		
	}
	

}
