package com.foya.noms.dto.basic;

import com.foya.dao.mybatis.model.TbComItemCat;

public class ItemCatDTO extends TbComItemCat {

	private String parentCatName;
	
	private String parentCatNo;

	public String getParentCatName() {
		return parentCatName;
	}

	public void setParentCatName(String parentCatName) {
		this.parentCatName = parentCatName;
	}

	public String getParentCatNo() {
		return parentCatNo;
	}

	public void setParentCatNo(String parentCatNo) {
		this.parentCatNo = parentCatNo;
	}
}
