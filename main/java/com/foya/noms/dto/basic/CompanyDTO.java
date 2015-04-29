package com.foya.noms.dto.basic;

import com.foya.dao.mybatis.model.TbComCompany;

public class CompanyDTO extends TbComCompany{
	
	private String btntype;
	private String[] EngEuqWh;
	private String disabledFields;
	private String callBackFun;
	public String getBtntype() {
		return btntype;
	}
	public void setBtntype(String btntype) {
		this.btntype = btntype;
	}
	public String[] getEngEuqWh() {
		return EngEuqWh;
	}
	public void setEngEuqWh(String[] engEuqWh) {
		EngEuqWh = engEuqWh;
	}
	public String getCallBackFun() {
		return callBackFun;
	}
	public void setCallBackFun(String callBackFun) {
		this.callBackFun = callBackFun;
	}
	public String getDisabledFields() {
		return disabledFields;
	}
	public void setDisabledFields(String disabledFields) {
		this.disabledFields = disabledFields;
	}

}
