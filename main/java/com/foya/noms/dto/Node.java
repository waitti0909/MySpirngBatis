package com.foya.noms.dto;

public class Node {

	private Integer id;
	private Integer pId;
	private String name;
	private Boolean open;
	
	public Node(Integer id, Integer pId, String name) {
		this.id = id;
		this.pId = pId;
		this.name = name;
	}
	public Node(Integer id, Integer pId, String name, Boolean open) {
		this(id, pId, name);
		this.open = open;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getpId() {
		return pId;
	}
	public void setpId(Integer pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getOpen() {
		return open;
	}
	public void setOpen(Boolean open) {
		this.open = open;
	}
	

}
