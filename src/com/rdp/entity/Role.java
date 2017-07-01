package com.rdp.entity;

public class Role {

	// ��ɫID
	private String roleId;
	// ��ɫ����
	private String name;
	// ����
	private int lv;
	// ��ע
	private String remark;
	
	public Role(String roleId, String name, int lv ,String remark){
		this.roleId = roleId;
		this.name = name;
		this.lv = lv;		
		this.remark = remark;
	}
	public Role(){}
	
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String menusId) {
		this.roleId = menusId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLv() {
		return lv;
	}
	public void setLv(int lv) {
		this.lv = lv;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
