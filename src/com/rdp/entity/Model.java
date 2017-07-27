package com.rdp.entity;

public class Model {

	private String modelID;
	private String name;
	private String title;
	private String createtime;
	
	public String getModelID() {
		return modelID;
	}
	public void setModelID(String modelID) {
		this.modelID = modelID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	
	public Model(String modelID, String name, String title, String createtime) {
		super();
		this.modelID = modelID;
		this.name = name;
		this.title = title;
		this.createtime = createtime;
	}
	public Model(){
	}
}
