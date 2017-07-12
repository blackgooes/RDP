package com.rdp.entity;

import java.util.Date;

public class User {
	private String userid;
	private String username;
	private String password;
	private String name;
	private int stateFlag;
	private Date createTime;
	private int gender;
	private String gendername;


	private String tel;
	private String remark;
	// role表的角色名称
	private String rolename;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	

	
	public int getStateFlag() {
		return stateFlag;
	}
	public void setStateFlag(int stateFlag) {
		this.stateFlag = stateFlag;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getGendername() {
		return gendername;
	}
	public void setGendername(String gendername) {
		this.gendername = gendername;
	}	
	
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	
	public User(String userid, String username, String password, String name, int stateFlag, Date createTime,
			String gendername, String tel, String remark) {
		super();
		this.userid = userid;
		this.username = username;
		this.password = password;
		this.name = name;
		this.stateFlag = stateFlag;
		this.createTime = createTime;
		this.gendername = gendername;
		this.tel = tel;
		this.remark = remark;
	}
	
	public User(String name,String username,String password){
		this.name = name;
		this.username = username;
		this.password = password;		
	}

	public User(String name,String password){
		this.name = name;
		this.password = password;
	}
	
	public User(){
	}

}
