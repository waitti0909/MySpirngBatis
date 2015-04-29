package com.foya.noms.dto.system;

import java.sql.Timestamp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.foya.dao.mybatis.model.TbSysSignLog;
import com.foya.noms.util.DateTimeJsonSerializer;

public class SignLogDTO extends TbSysSignLog{

	private Timestamp SIGNDATE;
	private String RESOLUTION;
	
	public String getRESOLUTION() {
		return RESOLUTION;
	}
	public void setRESOLUTION(String RESOLUTION) {
		this.RESOLUTION = RESOLUTION;
	}
	@JsonSerialize(using=DateTimeJsonSerializer.class)
	public Timestamp getSIGNDATE() {
		return SIGNDATE;
	}
	public void setSIGNDATE(Timestamp sIGNDATE) {
		SIGNDATE = sIGNDATE;
	}
	
}
