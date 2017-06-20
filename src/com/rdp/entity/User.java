package com.rdp.entity;

public class User {
	private String userid;
	private String username;
	private String password;
	private String name;

//	private int age;
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
//	public int getAge() {
//		return age;
//	}
//	public void setAge(int age) {
//		this.age = age;
//	}
	
	public User(String name,String password){
		this.name = name;
		this.password = password;
	}
	
	public User(String name,String username,String password){
		this.name = name;
		this.username = username;
		this.password = password;		
	}

//		this.age = age;
	
	public User(){
	}

}
