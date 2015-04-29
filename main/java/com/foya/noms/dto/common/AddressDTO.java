package com.foya.noms.dto.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressDTO {

	private String zip;
	private String city;
	private String cityName;
	private String area;
	private String areaName;
	private String village;
	private String road;
	private String adjacent;
	private String lane;
	private String alley;
	private String subAlley;
	private String doorNo;
	private String doorNoEx;
	private String floor;
	private String floorEx;
	private String room;
	private String remark;
	private String fullAddress;
	private String disabledFields;
	private String callBackFun;
	
	
	
	
	
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getRoad() {
		return road;
	}
	public void setRoad(String road) {
		this.road = road;
	}
	public String getAdjacent() {
		return adjacent;
	}
	public void setAdjacent(String adjacent) {
		this.adjacent = adjacent;
	}
	public String getLane() {
		return lane;
	}
	public void setLane(String lane) {
		this.lane = lane;
	}
	public String getAlley() {
		return alley;
	}
	public void setAlley(String alley) {
		this.alley = alley;
	}
	public String getSubAlley() {
		return subAlley;
	}
	public void setSubAlley(String subAlley) {
		this.subAlley = subAlley;
	}
	public String getDoorNo() {
		return doorNo;
	}
	public void setDoorNo(String doorNo) {
		this.doorNo = doorNo;
	}
	public String getDoorNoEx() {
		return doorNoEx;
	}
	public void setDoorNoEx(String doorNoEx) {
		this.doorNoEx = doorNoEx;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getFloorEx() {
		return floorEx;
	}
	public void setFloorEx(String floorEx) {
		this.floorEx = floorEx;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	@Override
	public String toString() {
		return "AddressDTO [zip=" + zip + ", city=" + city + ", area=" + area + ", village=" + village + ", road=" + road + ", adjacent=" + adjacent + ", lane="
				+ lane + ", alley=" + alley + ", subAlley=" + subAlley + ", doorNo=" + doorNo + ", doorNoEx=" + doorNoEx + ", floor="
				+ floor + ", floorEx=" + floorEx + ", room=" + room + "]";
	}
	public String getDisabledFields() {
		return disabledFields;
	}
	public void setDisabledFields(String disabledFields) {
		this.disabledFields = disabledFields;
	}
	public String getCallBackFun() {
		return callBackFun;
	}
	public void setCallBackFun(String callBackFun) {
		this.callBackFun = callBackFun;
	}
	public String getFullAddress() {
		return fullAddress;
	}
	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getVillage() {
		return village;
	}
	public void setVillage(String village) {
		this.village = village;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}	

	public String getZip1(){
		
		if(zip!=null && zip.length()>3){
			return zip.substring(0, 3);
		}
		return zip;
	}
	
	public String getZip2(){
		if(zip!=null && zip.length()>3){
			if(zip.length()==4){
				return "0"+zip.substring(3, 4);
			}else{
				return zip.substring(3, 5);	
			}
		}
		return "";
	}
	
	
	
}
