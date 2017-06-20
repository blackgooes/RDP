package com.rdp.entity;

public class Tree {
	private String id;
	// 父节点
	private String pid;
	// 菜单名称
	private String text;
	// URL
	private String attributes;
	// 排序
	private int order;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getAttributes(){
		return attributes;
	}
	public void setAttributes(String attributes){
		this.attributes = attributes;
	}
    public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
}
