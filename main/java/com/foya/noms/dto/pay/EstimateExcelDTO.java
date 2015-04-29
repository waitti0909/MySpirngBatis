package com.foya.noms.dto.pay;

public class EstimateExcelDTO {
	private String contractNo;//合約編號
	private String locationId;//站點編號
	private String locType;//類型
	private String locTypeDesc;//類型說明
	private String thirdGSiteId;//3G基站編號
	private String thirdGstatusDscr;//3G狀態
	private String fourthGSiteId;//4G基站編號
	private String fourthGstatusDscr;//4G狀態
	private String paymentPeriod;//付款週期(月) 
	private String leaseValidBeginDate;//租約生效日
	private String leaseValidEndDate;//租約終止日
	private String domain;//區域
	private String domainDesc;//區域說明
	private String eqpName;//temp Equipment
	private String eqpModel;//temp 設備型態
	private String thirdEqpName;//3G Equipment
	private String thirdEqpModel;//3G 設備型態
	private String fourthEqpName;//4G Equipment
	private String fourthEqpModel;//4G 設備型態
	private String city;//縣市
	private String area;//鄉鎮區
	private String addr;//地址
	private Double monthRentAmt;//租金未稅
	private Double monthRentTaxAmt;//租金應稅
	private String totalTaxAmt;//總金額
	private String yearMonth;//年月
	//專線
	private String vendor;//業者別
	private String vendorName;//業者別名稱
	private String lineType;//專線類型
	private String lineTypeDesc;//專線類型說明
	private String velocity;//速率
	private String velocityDesc;//速率說明
	private String lineSDate;//專線生效日
	private String lineEDate;//專線終止日
	//電費
	private String electricityMeterNbr;//電錶號碼
	private String electricityTypeDesc;//電費型態
	private String siteId;//基站編號
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getLocType() {
		return locType;
	}
	public void setLocType(String locType) {
		this.locType = locType;
	}
	public String getLocTypeDesc() {
		return locTypeDesc;
	}
	public void setLocTypeDesc(String locTypeDesc) {
		this.locTypeDesc = locTypeDesc;
	}
	public String getThirdGSiteId() {
		return thirdGSiteId;
	}
	public void setThirdGSiteId(String thirdGSiteId) {
		this.thirdGSiteId = thirdGSiteId;
	}
	public String getThirdGstatusDscr() {
		return thirdGstatusDscr;
	}
	public void setThirdGstatusDscr(String thirdGstatusDscr) {
		this.thirdGstatusDscr = thirdGstatusDscr;
	}
	public String getFourthGSiteId() {
		return fourthGSiteId;
	}
	public void setFourthGSiteId(String fourthGSiteId) {
		this.fourthGSiteId = fourthGSiteId;
	}
	public String getFourthGstatusDscr() {
		return fourthGstatusDscr;
	}
	public void setFourthGstatusDscr(String fourthGstatusDscr) {
		this.fourthGstatusDscr = fourthGstatusDscr;
	}
	public String getPaymentPeriod() {
		return paymentPeriod;
	}
	public void setPaymentPeriod(String paymentPeriod) {
		this.paymentPeriod = paymentPeriod;
	}
	public String getLeaseValidBeginDate() {
		return leaseValidBeginDate;
	}
	public void setLeaseValidBeginDate(String leaseValidBeginDate) {
		this.leaseValidBeginDate = leaseValidBeginDate;
	}
	public String getLeaseValidEndDate() {
		return leaseValidEndDate;
	}
	public void setLeaseValidEndDate(String leaseValidEndDate) {
		this.leaseValidEndDate = leaseValidEndDate;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getDomainDesc() {
		return domainDesc;
	}
	public void setDomainDesc(String domainDesc) {
		this.domainDesc = domainDesc;
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
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public Double getMonthRentAmt() {
		return monthRentAmt;
	}
	public void setMonthRentAmt(Double monthRentAmt) {
		this.monthRentAmt = monthRentAmt;
	}
	public Double getMonthRentTaxAmt() {
		return monthRentTaxAmt;
	}
	public void setMonthRentTaxAmt(Double monthRentTaxAmt) {
		this.monthRentTaxAmt = monthRentTaxAmt;
	}
	public String getTotalTaxAmt() {
		return totalTaxAmt;
	}
	public void setTotalTaxAmt(String totalTaxAmt) {
		this.totalTaxAmt = totalTaxAmt;
	}
	public String getYearMonth() {
		return yearMonth;
	}
	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getLineType() {
		return lineType;
	}
	public void setLineType(String lineType) {
		this.lineType = lineType;
	}
	public String getLineTypeDesc() {
		return lineTypeDesc;
	}
	public void setLineTypeDesc(String lineTypeDesc) {
		this.lineTypeDesc = lineTypeDesc;
	}
	public String getVelocity() {
		return velocity;
	}
	public void setVelocity(String velocity) {
		this.velocity = velocity;
	}
	public String getVelocityDesc() {
		return velocityDesc;
	}
	public void setVelocityDesc(String velocityDesc) {
		this.velocityDesc = velocityDesc;
	}
	public String getLineSDate() {
		return lineSDate;
	}
	public void setLineSDate(String lineSDate) {
		this.lineSDate = lineSDate;
	}
	public String getLineEDate() {
		return lineEDate;
	}
	public void setLineEDate(String lineEDate) {
		this.lineEDate = lineEDate;
	}
	public String getElectricityMeterNbr() {
		return electricityMeterNbr;
	}
	public void setElectricityMeterNbr(String electricityMeterNbr) {
		this.electricityMeterNbr = electricityMeterNbr;
	}
	public String getElectricityTypeDesc() {
		return electricityTypeDesc;
	}
	public void setElectricityTypeDesc(String electricityTypeDesc) {
		this.electricityTypeDesc = electricityTypeDesc;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getEqpName() {
		return eqpName;
	}
	public void setEqpName(String eqpName) {
		this.eqpName = eqpName;
	}
	public String getEqpModel() {
		return eqpModel;
	}
	public void setEqpModel(String eqpModel) {
		this.eqpModel = eqpModel;
	}
	public String getThirdEqpName() {
		return thirdEqpName;
	}
	public void setThirdEqpName(String thirdEqpName) {
		this.thirdEqpName = thirdEqpName;
	}
	public String getThirdEqpModel() {
		return thirdEqpModel;
	}
	public void setThirdEqpModel(String thirdEqpModel) {
		this.thirdEqpModel = thirdEqpModel;
	}
	public String getFourthEqpName() {
		return fourthEqpName;
	}
	public void setFourthEqpName(String fourthEqpName) {
		this.fourthEqpName = fourthEqpName;
	}
	public String getFourthEqpModel() {
		return fourthEqpModel;
	}
	public void setFourthEqpModel(String fourthEqpModel) {
		this.fourthEqpModel = fourthEqpModel;
	}
	
	
}
