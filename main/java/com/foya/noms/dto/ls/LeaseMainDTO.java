package com.foya.noms.dto.ls;

import java.util.ArrayList;
import java.util.List;

import com.foya.dao.mybatis.model.TbLsLessor;
import com.foya.dao.mybatis.model.TbLsMain;
import com.foya.dao.mybatis.model.TbLsReward;

public class LeaseMainDTO extends TbLsMain {
	
	
//	private TbLsApp tbLsApp;
	private List<TbLsLessor> lessors = new ArrayList<>();
	private List<TbLsReward> rewardList  = new ArrayList<>();
	private String jsonLessors;//出租人
	private String jsonClause;//增修條文
	private String APP_USER_DEPT_NAME;
	private String APP_USER_NAME;
	
	private String LS_TYPE;
	private String APP_SEQ;
	private String CONTI_EXTEND_TYPE;
	
	
//	public TbLsApp getTbLsApp() {
//		return tbLsApp;
//	}
//	public void setTbLsApp(TbLsApp tbLsApp) {
//		this.tbLsApp = tbLsApp;
//	}
	public List<TbLsLessor> getLessors() {
		return lessors;
	}
	public void setLessors(List<TbLsLessor> lessors) {
		this.lessors = lessors;
	}
	public List<TbLsReward> getRewardList() {
		return rewardList;
	}
	public void setRewardList(List<TbLsReward> rewardList) {
		this.rewardList = rewardList;
	}
	public String getJsonLessors() {
		return jsonLessors;
	}
	public void setJsonLessors(String jsonLessors) {
		this.jsonLessors = jsonLessors;
	}
	public String getJsonClause() {
		return jsonClause;
	}
	public void setJsonClause(String jsonClause) {
		this.jsonClause = jsonClause;
	}
	public String getAPP_USER_DEPT_NAME() {
		return APP_USER_DEPT_NAME;
	}
	public void setAPP_USER_DEPT_NAME(String aPP_USER_DEPT_NAME) {
		APP_USER_DEPT_NAME = aPP_USER_DEPT_NAME;
	}
	public String getAPP_USER_NAME() {
		return APP_USER_NAME;
	}
	public void setAPP_USER_NAME(String aPP_USER_NAME) {
		APP_USER_NAME = aPP_USER_NAME;
	}
	public String getLS_TYPE() {
		return LS_TYPE;
	}
	public void setLS_TYPE(String lS_TYPE) {
		LS_TYPE = lS_TYPE;
	}
	public String getAPP_SEQ() {
		return APP_SEQ;
	}
	public void setAPP_SEQ(String APP_SEQ) {
		this.APP_SEQ = APP_SEQ;
	}
	public String getCONTI_EXTEND_TYPE() {
		return CONTI_EXTEND_TYPE;
	}
	public void setCONTI_EXTEND_TYPE(String cONTI_EXTEND_TYPE) {
		CONTI_EXTEND_TYPE = cONTI_EXTEND_TYPE;
	}

	
}
