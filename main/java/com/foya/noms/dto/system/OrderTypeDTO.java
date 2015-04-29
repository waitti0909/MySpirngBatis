package com.foya.noms.dto.system;

import com.foya.dao.mybatis.model.TbSysOrderType;

public class OrderTypeDTO extends TbSysOrderType{
	private Integer orderSeq;
	private String workTypeId;
	public Integer getOrderSeq() {
		return orderSeq;
	}
	public void setOrderSeq(Integer orderSeq) {
		this.orderSeq = orderSeq;
	}
	public String getWorkTypeId() {
		return workTypeId;
	}
	public void setWorkTypeId(String workTypeId) {
		this.workTypeId = workTypeId;
	}
	@Override
	public String toString() {
		return "OrderTypeDTO [orderSeq=" + orderSeq + ", workTypeId="
				+ workTypeId + ", getOD_TYPE_ID()=" + getOD_TYPE_ID()
				+ ", getOD_TYPE_NAME()=" + getOD_TYPE_NAME()
				+ ", getSHOW_PAGES()=" + getSHOW_PAGES() + ", getDEPT_DESC()="
				+ getDEPT_DESC() + ", getDEPT_KEY()=" + getDEPT_KEY()
				+ ", getMEMO()=" + getMEMO() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
}
