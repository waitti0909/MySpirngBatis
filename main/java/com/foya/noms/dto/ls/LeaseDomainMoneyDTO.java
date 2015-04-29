package com.foya.noms.dto.ls;

import java.math.BigDecimal;

public class LeaseDomainMoneyDTO  {
	
	private String domain;
	
	//租金
	private BigDecimal rent;

	
	//押金
	private BigDecimal pledge;
	
	
	private String kind;

	private String domainName;

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public BigDecimal getRent() {
		return rent;
	}

	public void setRent(BigDecimal rent) {
		this.rent = rent;
	}

	public BigDecimal getPledge() {
		return pledge;
	}

	public void setPledge(BigDecimal pledge) {
		this.pledge = pledge;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	
	
	
	
	
}
