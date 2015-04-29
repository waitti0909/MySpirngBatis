package com.foya.noms.dto.pay;

import java.util.Date;
import com.foya.dao.mybatis.model.OriginalPowerBill;

public class OriginalPowerBillDTO extends OriginalPowerBill {
	
	private Long SEQ_NBR;
	private Date RECORD_YYYYMMDD;
	private Date LAST_RECORD_YYYYMMDD;
	
	public Long getSEQ_NBR() {
		return SEQ_NBR;
	}
	public void setSEQ_NBR(Long sEQ_NBR) {
		SEQ_NBR = sEQ_NBR;
	}
	public Date getRECORD_YYYYMMDD() {
		return RECORD_YYYYMMDD;
	}
	public void setRECORD_YYYYMMDD(Date rECORD_YYYYMMDD) {
		RECORD_YYYYMMDD = rECORD_YYYYMMDD;
	}
	public Date getLAST_RECORD_YYYYMMDD() {
		return LAST_RECORD_YYYYMMDD;
	}
	public void setLAST_RECORD_YYYYMMDD(Date lAST_RECORD_YYYYMMDD) {
		LAST_RECORD_YYYYMMDD = lAST_RECORD_YYYYMMDD;
	}
	
}
