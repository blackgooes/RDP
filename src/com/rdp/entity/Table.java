package com.rdp.entity;

public class Table {
	String name;
	int age;
	String phone;
	String email;
	//int id;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	/*public void setId(int id){
		this.id = id;
	}
	public int getId(int id){
		return id;
	}*/
	public Table(String name, int age, String phone, String email) {
		super();
		this.name = name;
		this.age = age;
		this.phone = phone;
		this.email = email;
	}
	public Table(){
		
	}
}
