package com.foya.noms.dto.profile;

import java.sql.Timestamp;

/**
 * 
 * 
 * <table>
 * <tr>
 * <th>版本</th>
 * <th>日期</th>
 * <th>詳細說明</th>
 * <th>modifier</th>
 * </tr>
 * <tr>
 * <td>1.0</td>
 * <td>2014/12/19</td>
 * <td>待辦工單事項</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table>
 *
 * @author Charlie Woo
 *
 */
public class TodoOrdersDTO {

	// 作業編號(工單用)
	private String workId;

	// 文件單號/工單號碼
	private String orderId;

	// 單號帶入資訊, example : site id, 倉庫
	private String orderInfo;

	// 工單順序
	private Integer orderSeq;

	// 工單類型(代碼)
	private String orderType;

	// 工單類型
	private String orderTypeDesc;

	// 申請/建立/指派人員
	private String appUser;

	// 申請/建立/指派人員名稱
	private String appUserName;

	// 申請/建立/指派時間
	private Timestamp appTime;

	// 文件/工單狀態(代碼)
	private String orderStatus;

	// 文件/工單狀態
	private String orderStatusDesc;

	// 單號明細連結的URL
	private String linkURL;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderTypeDesc() {
		return orderTypeDesc;
	}

	public void setOrderTypeDesc(String orderTypeDesc) {
		this.orderTypeDesc = orderTypeDesc;
	}

	public String getAppUser() {
		return appUser;
	}

	public void setAppUser(String appUser) {
		this.appUser = appUser;
	}

	public String getAppUserName() {
		return appUserName;
	}

	public void setAppUserName(String appUserName) {
		this.appUserName = appUserName;
	}

	public Timestamp getAppTime() {
		return appTime;
	}

	public void setAppTime(Timestamp appTime) {
		this.appTime = appTime;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderStatusDesc() {
		return orderStatusDesc;
	}

	public void setOrderStatusDesc(String orderStatusDesc) {
		this.orderStatusDesc = orderStatusDesc;
	}

	public String getLinkURL() {
		return linkURL;
	}

	public void setLinkURL(String linkURL) {
		this.linkURL = linkURL;
	}

	public Integer getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(Integer orderSeq) {
		this.orderSeq = orderSeq;
	}

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}
}
