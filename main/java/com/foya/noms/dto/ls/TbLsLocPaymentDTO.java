package com.foya.noms.dto.ls;

import com.foya.dao.mybatis.model.TbLsLocPayment;

public class TbLsLocPaymentDTO extends TbLsLocPayment{
	
	private String paymentModeName;
	
	private String UNIT_NAME;
	
	private String NICK_NAME;


	public String getPaymentModeName() {
		return paymentModeName;
	}

	public void setPaymentModeName(String paymentModeName) {
		this.paymentModeName = paymentModeName;
	}

	public String getUNIT_NAME() {
		return UNIT_NAME;
	}

	public void setUNIT_NAME(String uNIT_NAME) {
		UNIT_NAME = uNIT_NAME;
	}

	public String getNICK_NAME() {
		return NICK_NAME;
	}

	public void setNICK_NAME(String nICK_NAME) {
		NICK_NAME = nICK_NAME;
	}
	
	
	
}
