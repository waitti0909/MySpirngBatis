package com.foya.noms.dto.common;

import com.foya.dao.mybatis.model.TbComFileDtl;

public class FileDtlDTO extends TbComFileDtl {
	
	private String FILE_TYPE_NAME;
	
	private String CR_TIME_STR;
	
	private String WORK_TYPE_NAME;
	
	private String CR_USER_NAME;

	public String getFILE_TYPE_NAME() {
		return FILE_TYPE_NAME;
	}

	public void setFILE_TYPE_NAME(String fILE_TYPE_NAME) {
		FILE_TYPE_NAME = fILE_TYPE_NAME;
	}

	public String getCR_TIME_STR() {
		return CR_TIME_STR;
	}

	public void setCR_TIME_STR(String cR_TIME_STR) {
		CR_TIME_STR = cR_TIME_STR;
	}

	public String getWORK_TYPE_NAME() {
		return WORK_TYPE_NAME;
	}

	public void setWORK_TYPE_NAME(String wORK_TYPE_NAME) {
		WORK_TYPE_NAME = wORK_TYPE_NAME;
	}

	public String getCR_USER_NAME() {
		return CR_USER_NAME;
	}

	public void setCR_USER_NAME(String cR_USER_NAME) {
		CR_USER_NAME = cR_USER_NAME;
	}
}
