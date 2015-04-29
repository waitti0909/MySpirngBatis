package com.foya.noms.dto.common;

import com.foya.dao.mybatis.model.TbComPoQuota;

public class PoQuotaDTO extends TbComPoQuota {
	private String poDomainId;
	private String poDomainName;
	private String poQuota;
	private Long unUsedQuota;
	private Long tempBookingQuota;
	public String getPoDomainId() {
		return poDomainId;
	}
	public void setPoDomainId(String poDomainId) {
		this.poDomainId = poDomainId;
	}
	public String getPoDomainName() {
		return poDomainName;
	}
	public void setPoDomainName(String poDomainName) {
		this.poDomainName = poDomainName;
	}
	public String getPoQuota() {
		return poQuota;
	}
	public void setPoQuota(String poQuota) {
		this.poQuota = poQuota;
	}
	public Long getUnUsedQuota() {
		return unUsedQuota;
	}
	public void setUnUsedQuota(Long unUsedQuota) {
		this.unUsedQuota = unUsedQuota;
	}
	public Long getTempBookingQuota() {
		return tempBookingQuota;
	}
	public void setTempBookingQuota(Long tempBookingQuota) {
		this.tempBookingQuota = tempBookingQuota;
	}
	
}
