package com.foya.noms.dto.auth;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.foya.dao.mybatis.model.TbAuthMenu;
import com.foya.noms.util.DateTimeJsonSerializer;

@SuppressWarnings("serial")
public class MenuDTO extends TbAuthMenu implements Serializable{

	private String PARENT_NAME;
	
	public String getPARENT_NAME() {
		return PARENT_NAME;
	}

	public void setPARENT_NAME(String pARENT_NAME) {
		PARENT_NAME = pARENT_NAME;
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
	public String getMENU_NAME() {
		return super.getMENU_NAME();
	}

	@Override
	public void setMENU_NAME(String MENU_NAME) {
		super.setMENU_NAME(MENU_NAME);
	}

	@Override
	public String getMENU_DESC() {
		return super.getMENU_DESC();
	}

	@Override
	public void setMENU_DESC(String MENU_DESC) {
		super.setMENU_DESC(MENU_DESC);
	}

	@Override
	public Integer getMENU_SORT() {
		return super.getMENU_SORT();
	}

	@Override
	public void setMENU_SORT(Integer MENU_SORT) {
		super.setMENU_SORT(MENU_SORT);
	}

	@Override
	public String getIS_FODR() {
		return super.getIS_FODR();
	}

	@Override
	public void setIS_FODR(String IS_FODR) {
		super.setIS_FODR(IS_FODR);
	}

	@Override
	public String getIS_USE() {
		return super.getIS_USE();
	}

	@Override
	public void setIS_USE(String IS_USE) {
		super.setIS_USE(IS_USE);
	}

	@Override
	public BigDecimal getPARENT_ID() {
		return super.getPARENT_ID();
	}

	@Override
	public void setPARENT_ID(BigDecimal PARENT_ID) {
		super.setPARENT_ID(PARENT_ID);
	}

	@Override
	public String getPGM_PATH() {
		return super.getPGM_PATH();
	}

	@Override
	public void setPGM_PATH(String PGM_PATH) {
		super.setPGM_PATH(PGM_PATH);
	}


	@Override
	public String getCR_USER() {
		return super.getCR_USER();
	}

	@Override
	public void setCR_USER(String CR_USER) {
		super.setCR_USER(CR_USER);
	}

	@JsonSerialize(using=DateTimeJsonSerializer.class)
	@Override
	public Date getCR_TIME() {
		return super.getCR_TIME();
	}

	@Override
	public void setCR_TIME(Date CR_TIME) {
		super.setCR_TIME(CR_TIME);
	}
	

	@Override
	public String getMD_USER() {
		return super.getMD_USER();
	}

	@Override
	public void setMD_USER(String MD_USER) {
		super.setMD_USER(MD_USER);
	}
	
	@JsonSerialize(using=DateTimeJsonSerializer.class)
	@Override
	public Date getMD_TIME() {
		return super.getMD_TIME();
	}
	
	@Override
	public void setMD_TIME(Date MD_TIME) {
		super.setMD_TIME(MD_TIME);
	}

}
