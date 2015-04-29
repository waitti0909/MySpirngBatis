package com.foya.noms.dto.ls;

import com.foya.dao.mybatis.model.TbLsLocationAdded;

public class TbLsLocationAddedDTO extends TbLsLocationAdded {
	private String lsPayBeginDate;
	private String lsEndDate;	
	private String lsEffDate;
	
	public String getLsPayBeginDate() {
		return lsPayBeginDate;
	}
	public void setLsPayBeginDate(String lsPayBeginDate) {
		this.lsPayBeginDate = lsPayBeginDate;
	}
	public String getLsEndDate() {
		return lsEndDate;
	}
	public void setLsEndDate(String lsEndDate) {
		this.lsEndDate = lsEndDate;
	}
	public String getLsEffDate() {
		return lsEffDate;
	}
	public void setLsEffDate(String lsEffDate) {
		this.lsEffDate = lsEffDate;
	}
	
	
}
