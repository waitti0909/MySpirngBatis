package com.foya.noms.dto.ls;

import com.foya.dao.mybatis.model.TbLsReward;

public class TbLsRewardDTO extends TbLsReward {

	public String resnDesc;
	
	public String getDate;

	public String getResnDesc() {
		return resnDesc;
	}

	public void setResnDesc(String resnDesc) {
		this.resnDesc = resnDesc;
	}

	public String getGetDate() {
		return getDate;
	}

	public void setGetDate(String getDate) {
		this.getDate = getDate;
	}
	
}
