package com.foya.noms.dto.common;

import java.util.List;

import com.foya.dao.mybatis.model.TbComPoLineId;
import com.foya.dao.mybatis.model.TbComPoMain;

public class PoMainDTO extends TbComPoMain {
	
	private String callBackFun;
	private String coName;
	private String catId;
	private String catName;
	private String poLineId;
	private List<TbComPoLineId> poLineIdOptions;
	public String getCoName() {
		return coName;
	}

	public void setCoName(String coName) {
		this.coName = coName;
	}

	public String getCallBackFun() {
		return callBackFun;
	}

	public void setCallBackFun(String callBackFun) {
		this.callBackFun = callBackFun;
	}

	public String getCatId() {
		return catId;
	}

	public void setCatId(String catId) {
		this.catId = catId;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public String getPoLineId() {
		return poLineId;
	}

	public void setPoLineId(String poLineId) {
		this.poLineId = poLineId;
	}

	public List<TbComPoLineId> getPoLineIdOptions() {
		return poLineIdOptions;
	}

	public void setPoLineIdOptions(List<TbComPoLineId> poLineIdOptions) {
		this.poLineIdOptions = poLineIdOptions;
	}
}
