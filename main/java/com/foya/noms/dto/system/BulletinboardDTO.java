package com.foya.noms.dto.system;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.foya.noms.util.DateTimeJsonSerializer;

public class BulletinboardDTO {
	
	private BigDecimal bulletinID;
	private Timestamp startDate;
	private Timestamp endDate;
	private String subject;
	private String contents;
	private Integer priority;
	private Integer types;
	private String typesName;
	private String attachment;
	private String deptName;
	private String crUser;
	private String crPsnNO;
	private Date crTime;
	private String mdUser;
	private Date mdTime;
	private String engNM;
	private String chnNM;
	private String deptID;

	
	
	public BigDecimal getBulletinID() {
		return bulletinID;
	}
	public void setBulletinID(BigDecimal bulletinID) {
		this.bulletinID = bulletinID;
	}
	
	@JsonSerialize(using=DateTimeJsonSerializer.class)
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	
	@JsonSerialize(using=DateTimeJsonSerializer.class)
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
	public Integer getTypes() {
		return types;
	}
	public void setTypes(Integer types) {
		this.types = types;
	}
	
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public String getCrUser() {
		return crUser;
	}
	public void setCrUser(String crUser) {
		this.crUser = crUser;
	}
	public Date getCrTime() {
		return crTime;
	}
	public void setCrTime(Date crTime) {
		this.crTime = crTime;
	}
	public String getMdUser() {
		return mdUser;
	}
	public void setMdUser(String mdUser) {
		this.mdUser = mdUser;
	}
	public String getCrPsnNO() {
		return crPsnNO;
	}
	public void setCrPsnNO(String crPsnNO) {
		this.crPsnNO = crPsnNO;
	}
	public Date getMdTime() {
		return mdTime;
	}
	public void setMdTime(Date mdTime) {
		this.mdTime = mdTime;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getTypesName() {
		return typesName;
	}
	public void setTypesName(String typesName) {
		this.typesName = typesName;
	}
	public String getEngNM() {
		return engNM;
	}
	public void setEngNM(String engNM) {
		this.engNM = engNM;
	}
	public String getChnNM() {
		return chnNM;
	}
	public void setChnNM(String chnNM) {
		this.chnNM = chnNM;
	}
	public String getDeptID() {
		return deptID;
	}
	public void setDeptID(String deptID) {
		this.deptID = deptID;
	}

}
