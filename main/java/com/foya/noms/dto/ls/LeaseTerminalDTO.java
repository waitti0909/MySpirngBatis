package com.foya.noms.dto.ls;

import java.util.ArrayList;
import java.util.List;

import com.foya.dao.mybatis.model.TbLsTrmn;
import com.foya.dao.mybatis.model.TbLsTrmnElec;

public class LeaseTerminalDTO extends TbLsTrmn  {
	
	private List<TbLsTrmnElec> trmnElecList = new ArrayList<>();

	public List<TbLsTrmnElec> getTrmnElecList() {
		return trmnElecList;
	}

	public void setTrmnElecList(List<TbLsTrmnElec> trmnElecList) {
		this.trmnElecList = trmnElecList;
	}
	
	
	
	
}
