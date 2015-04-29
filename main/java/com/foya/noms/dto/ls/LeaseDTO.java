package com.foya.noms.dto.ls;

import com.foya.dao.mybatis.model.TbLsApp;
import com.foya.noms.enums.LsEnumCommon;

public class LeaseDTO extends TbLsApp {
	
	private String LS_STATUS;
	private String LS_TYPE_NAME;
	private String LS_STATUS_NAME;
	private String APP_STATUS_NAME;
	private String ADD_TYPE_NAME;
	private String DEAL_USER;
	private String DEAL_USER_NAME;
	private String APP_USER_NAME;
	
	public String getLS_STATUS() {
		return LS_STATUS;
	}

	public void setLS_STATUS(String lS_STATUS) {
		this.LS_STATUS = lS_STATUS;
	}

	public String getDEAL_USER() {
		return DEAL_USER;
	}

	public void setDEAL_USER(String DEAL_USER) {
		this.DEAL_USER = DEAL_USER;
	}

	public void setLS_TYPE_NAME(String LS_TYPE_NAME) {
		this.LS_TYPE_NAME = LS_TYPE_NAME;
	}

	public void setLS_STATUS_NAME(String LS_STATUS_NAME) {
		this.LS_STATUS_NAME = LS_STATUS_NAME;
	}

	public void setAPP_STATUS_NAME(String APP_STATUS_NAME) {
		this.APP_STATUS_NAME = APP_STATUS_NAME;
	}

	public void setADD_TYPE_NAME(String ADD_TYPE_NAME) {
		this.ADD_TYPE_NAME = ADD_TYPE_NAME;
	}
	
	/**
	 * 取得合約型態
	 * @return
	 */
	public String getLS_TYPE_NAME() {
		return LsEnumCommon.LsTypeEnum.detectLabel(getLS_TYPE());
	}
	
	/**
	 * 取得申請狀態
	 * @return
	 */
	public String getAPP_STATUS_NAME() {
		return LsEnumCommon.AppStatusEnum.detectLabel(getAPP_STATUS());
	}
	
	/**
	 * 取得合約狀態
	 * @return
	 */
	public String getLS_STATUS_NAME() {
		return LsEnumCommon.LsStatusEnum.detectLabel(getLS_STATUS());
	}
	
	/**
	 * 取得增補協議類別
	 * @return
	 */
	public String getADD_TYPE_NAME() {
		return LsEnumCommon.AddTypeEnum.detectLabel(getADD_TYPE());
	}

	public String getDEAL_USER_NAME() {
		return DEAL_USER_NAME;
	}

	public void setDEAL_USER_NAME(String DEAL_USER_NAME) {
		this.DEAL_USER_NAME = DEAL_USER_NAME;
	}

	public String getAPP_USER_NAME() {
		return APP_USER_NAME;
	}

	public void setAPP_USER_NAME(String APP_USER_NAME) {
		this.APP_USER_NAME = APP_USER_NAME;
	}


}
