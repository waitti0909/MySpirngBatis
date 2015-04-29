package com.foya.noms.dto.basic;

import com.foya.dao.mybatis.model.TbComPoLineIdMap;

public class PoLineIdMapDTO extends TbComPoLineIdMap {

	String catName;

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}
	
	
}
