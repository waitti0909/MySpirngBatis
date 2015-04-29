package com.foya.noms.dto.st;

import java.util.Date;
import java.util.List;

import com.foya.dao.mybatis.model.TbSiteAntCfg;
import com.foya.dao.mybatis.model.TbSiteLocation;
import com.foya.dao.mybatis.model.TbSiteMain;
import com.foya.noms.enums.Feederless;
import com.foya.noms.enums.IncludeRange;
import com.foya.noms.enums.SiteStatus;

public class SiteDTO extends TbSiteMain{
	private String eqpName;
	private String feqName;
	private String locName;
	private String locAddr;
	private String callBackFun;
	private String locRoad;
	private String eqpModel;
	private Date signDate;
	private TbSiteLocation location;
	private String siteStatusName;
	private String locZip;
	private String locCity;
	private String locArea;
	
	private String locVillage;
	private String locAdjacent;
	private String locLane;
	private String locAlley;
	private String locSub_Alley;
	private String locDoor_No;
	private String locDoor_No_Ex;
	private String locFloor;
	private String locFloor_Ex;
	private String locRoom;
	private String locAdd_Other;
	
	
	//add for 合約
	private boolean hasContract;
	private String maintArea;
	
	
	private List<TbSiteAntCfg> antList;
	
	public String getEqpName() {
		return eqpName;
	}
	public void setEqpName(String eqpName) {
		this.eqpName = eqpName;
	}
	public String getFeqName() {
		return feqName;
	}
	public void setFeqName(String feqName) {
		this.feqName = feqName;
	}
	public String getLocName() {
		return locName;
	}
	public void setLocName(String locName) {
		this.locName = locName;
	}
	public String getLocAddr() {
		return locAddr;
	}
	public void setLocAddr(String locAddr) {
		this.locAddr = locAddr;
	}
	public String getCallBackFun() {
		return callBackFun;
	}
	public void setCallBackFun(String callBackFun) {
		this.callBackFun = callBackFun;
	}
	public String getLocRoad() {
		return locRoad;
	}
	public void setLocRoad(String locRoad) {
		this.locRoad = locRoad;
	}
	public String getEqpModel() {
		return eqpModel;
	}
	public void setEqpModel(String eqpModel) {
		this.eqpModel = eqpModel;
	}
	
	public String getSiteStatusLabel(){
		return SiteStatus.detectLabel(getSITE_STATUS());
	}
	
	public String getCoverageTypeLabel(){
		return IncludeRange.detectLabel(getCOVERAGE_TYPE());
	}
	
	public String getFeederlessLabel(){
		return Feederless.detectLabel(getFEEDERLESS());
	}

	public String getSiteStatusName() {
		if(siteStatusName==null || siteStatusName.equals("")){
			siteStatusName=SiteStatus.detectLabel(getSITE_STATUS());
		}
		return siteStatusName;
	}

	public String getFeederlessName(){
		return Feederless.detectLabel(getFEEDERLESS());
	}
	
	public String getCoverageTypeName(){
		return IncludeRange.detectLabel(getCOVERAGE_TYPE());
	}
	
	public TbSiteLocation getLocation() {
		return location;
	}
	public void setLocation(TbSiteLocation location) {
		this.location = location;
	}
	public List<TbSiteAntCfg> getAntList() {
		return antList;
	}
	public void setAntList(List<TbSiteAntCfg> antList) {
		this.antList = antList;
	}
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	public void setSiteStatusName(String siteStatusName) {
		this.siteStatusName = siteStatusName;
	}
	public String getLocZip() {
		return locZip;
	}
	public void setLocZip(String locZip) {
		this.locZip = locZip;
	}
	public boolean isHasContract() {
		return hasContract;
	}
	public void setHasContract(boolean hasContract) {
		this.hasContract = hasContract;
	}
	public String getMaintArea() {
		return maintArea;
	}
	public void setMaintArea(String maintArea) {
		this.maintArea = maintArea;
	}
	public String getLocCity() {
		return locCity;
	}
	public void setLocCity(String locCity) {
		this.locCity = locCity;
	}
	public String getLocArea() {
		return locArea;
	}
	public void setLocArea(String locArea) {
		this.locArea = locArea;
	}
	public String getLocVillage() {
		return locVillage;
	}
	public void setLocVillage(String locVillage) {
		this.locVillage = locVillage;
	}
	public String getLocAdjacent() {
		return locAdjacent;
	}
	public void setLocAdjacent(String locAdjacent) {
		this.locAdjacent = locAdjacent;
	}
	public String getLocLane() {
		return locLane;
	}
	public void setLocLane(String locLane) {
		this.locLane = locLane;
	}
	public String getLocAlley() {
		return locAlley;
	}
	public void setLocAlley(String locAlley) {
		this.locAlley = locAlley;
	}
	public String getLocSub_Alley() {
		return locSub_Alley;
	}
	public void setLocSub_Alley(String locSub_Alley) {
		this.locSub_Alley = locSub_Alley;
	}
	public String getLocDoor_No() {
		return locDoor_No;
	}
	public void setLocDoor_No(String locDoor_No) {
		this.locDoor_No = locDoor_No;
	}
	public String getLocDoor_No_Ex() {
		return locDoor_No_Ex;
	}
	public void setLocDoor_No_Ex(String locDoor_No_Ex) {
		this.locDoor_No_Ex = locDoor_No_Ex;
	}
	public String getLocFloor() {
		return locFloor;
	}
	public void setLocFloor(String locFloor) {
		this.locFloor = locFloor;
	}
	public String getLocFloor_Ex() {
		return locFloor_Ex;
	}
	public void setLocFloor_Ex(String locFloor_Ex) {
		this.locFloor_Ex = locFloor_Ex;
	}
	public String getLocRoom() {
		return locRoom;
	}
	public void setLocRoom(String locRoom) {
		this.locRoom = locRoom;
	}
	public String getLocAdd_Other() {
		return locAdd_Other;
	}
	public void setLocAdd_Other(String locAdd_Other) {
		this.locAdd_Other = locAdd_Other;
	}
	
	
}
