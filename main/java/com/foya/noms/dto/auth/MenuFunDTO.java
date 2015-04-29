package com.foya.noms.dto.auth;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.foya.dao.mybatis.model.TbAuthMenuFun;

public class MenuFunDTO extends TbAuthMenuFun{
	
	@Override
	public BigDecimal getMENU_FUN_ID() {
		
		return super.getMENU_FUN_ID();
	}

	@Override
	public void setMENU_FUN_ID(BigDecimal MENU_FUN_ID) {
	
		super.setMENU_FUN_ID(MENU_FUN_ID);
	}

	@Override
	public BigDecimal getMENU_ID() {
	
		return super.getMENU_ID();
	}

	@Override
	public void setMENU_ID(BigDecimal MENU_ID) {
	
		super.setMENU_ID(MENU_ID);
	}

	@Override
	public String getFUN_CODE() {
	
		return super.getFUN_CODE();
	}

	@Override
	public void setFUN_CODE(String FUN_CODE) {
		
		super.setFUN_CODE(FUN_CODE);
	}

	@Override
	public BigDecimal getDISPLAY_ORDER() {
		
		return super.getDISPLAY_ORDER();
	}

	@Override
	public void setDISPLAY_ORDER(BigDecimal DISPLAY_ORDER) {
		
		super.setDISPLAY_ORDER(DISPLAY_ORDER);
	}

	@Override
	public String getMD_USER() {
		return super.getMD_USER();
	}

	@Override
	public void setMD_USER(String MD_USER) {
		super.setMD_USER(MD_USER);
	}

	

	@Override
	public Date getMD_TIME() {
		return super.getMD_TIME();
	}

	@Override
	public void setMD_TIME(Date MD_TIME) {
		super.setMD_TIME(MD_TIME);
	}

	@Override
	public String toString() {
		return "MenuFunDTO [getMENU_FUN_ID()=" + getMENU_FUN_ID()
				+ ", getMENU_ID()=" + getMENU_ID() + ", getFUN_CODE()="
				+ getFUN_CODE() + ", getDISPLAY_ORDER()=" + getDISPLAY_ORDER()
				+ ", getMD_USER()=" + getMD_USER() + ", getMD_TIME()="
				+ getMD_TIME() + "]";
	}

	@Override
	public boolean equals(Object obj) {
		  if (obj instanceof MenuFunDTO == false)  
	      {  
	        return false;  
	      }  
	      if (this == obj)  
	      {  
	         return true;  
	      }  
	     final MenuFunDTO otherObject = (MenuFunDTO) obj;  
		return new EqualsBuilder().append(getMENU_ID(), otherObject.getMENU_ID()).
				append(getFUN_CODE(), otherObject.getFUN_CODE()).isEquals();
	}
	
	
	
	
	
}
