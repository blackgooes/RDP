package com.rdp.entity;

public class Field {

	private String id;
	private String name;
	private int type;
	private int length;
	private int isNull;
	private int defaultValue;
	private int isShow;
	private String modelID;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getIsNull() {
		return isNull;
	}
	public void setIsNull(int isNull) {
		this.isNull = isNull;
	}
	public int getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(int defaultValue) {
		this.defaultValue = defaultValue;
	}
	public int getIsShow() {
		return isShow;
	}
	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}
	
	public String getModelID() {
		return modelID;
	}
	public void setModelID(String modelID) {
		this.modelID = modelID;
	}
	
	public Field(String id, String name, int type, int length, int isNull, int defaultValue, int isShow,
			String modelID) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.length = length;
		this.isNull = isNull;
		this.defaultValue = defaultValue;
		this.isShow = isShow;
		this.modelID = modelID;
	}
	public Field(){}
}
