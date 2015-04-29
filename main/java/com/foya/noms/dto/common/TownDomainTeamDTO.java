package com.foya.noms.dto.common;

import com.foya.dao.mybatis.model.TbComTownDomainTeam;

public class TownDomainTeamDTO extends TbComTownDomainTeam {
	private String domainName;

	private String cityName;
	
	private String townName;
	
	
	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}
	
	
}
