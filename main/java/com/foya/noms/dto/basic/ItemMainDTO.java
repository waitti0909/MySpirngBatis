package com.foya.noms.dto.basic;

import com.foya.dao.mybatis.model.TbComItemMain;

public class ItemMainDTO extends TbComItemMain {

	private String mainItemName;
	private String subItemName;
	private String itemIdName;
	
	
	public String getMainItemName() {
		return mainItemName;
	}
	public void setMainItemName(String mainItemName) {
		this.mainItemName = mainItemName;
	}
	public String getSubItemName() {
		return subItemName;
	}
	public void setSubItemName(String subItemName) {
		this.subItemName = subItemName;
	}
	public String getItemIdName() {
		return itemIdName;
	}
	public void setItemIdName(String itemIdName) {
		this.itemIdName = itemIdName;
	}
	
	
}
