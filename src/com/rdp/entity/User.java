package com.rdp.entity;

public class User {
	private String name;
	private String username;
	private String password;
//	private int age;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public User(String name,String password,int age){
		this.name = name;
		this.password = password;
//		this.age = age;
	}
	public User(){
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
