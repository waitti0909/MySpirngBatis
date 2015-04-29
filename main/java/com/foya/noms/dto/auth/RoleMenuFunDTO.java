package com.foya.noms.dto.auth;

import java.math.BigDecimal;


public class RoleMenuFunDTO {
	
	private String funCode;
	
	private String btnName;
	
	private String btnI18n;
	
	private String btnClass;
	
	private String btnIconClass;
	
	private String displayOrder;

	private BigDecimal ROLE_ID;

    private BigDecimal MENU_FUN_ID;
    
	public BigDecimal getROLE_ID() {
		return ROLE_ID;
	}

	public void setROLE_ID(BigDecimal rOLE_ID) {
		ROLE_ID = rOLE_ID;
	}

	public BigDecimal getMENU_FUN_ID() {
		return MENU_FUN_ID;
	}

	public void setMENU_FUN_ID(BigDecimal mENU_FUN_ID) {
		MENU_FUN_ID = mENU_FUN_ID;
	}

	public String getFunCode() {
		return funCode;
	}

	public void setFunCode(String funCode) {
		this.funCode = funCode;
	}

	public String getBtnName() {
		return btnName;
	}

	public void setBtnName(String btnName) {
		this.btnName = btnName;
	}

	public String getBtnI18n() {
		return btnI18n;
	}

	public void setBtnI18n(String btnI18n) {
		this.btnI18n = btnI18n;
	}

	public String getBtnClass() {
		return btnClass;
	}

	public void setBtnClass(String btnClass) {
		this.btnClass = btnClass;
	}

	public String getBtnIconClass() {
		return btnIconClass;
	}

	public void setBtnIconClass(String btnIconClass) {
		this.btnIconClass = btnIconClass;
	}

	public String getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(String displayOrder) {
		this.displayOrder = displayOrder;
	}

	@Override
	public String toString() {
		return "RoleMenuFunDTO [funCode=" + funCode + ", btnName=" + btnName + ", btnI18n=" + btnI18n + ", btnClass=" + btnClass
				+ ", btnIconClass=" + btnIconClass + ", displayOrder=" + displayOrder + "]";
	}

	
	
	
}
