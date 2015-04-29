package com.foya.noms.enums;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.foya.noms.util.LabelValueModel;

public class LsEnumCommon {

	/**
	 * 合約型態<BR>
	 * 	0:新約 <BR>
	 *	1:續約 <BR>
	 *	2:換約 <BR>
	 *	3:一解一簽 <BR>
	 *	4:解約 <BR>
	 *	5:增補協議 <BR>
	 */
	public static enum LsTypeEnum {

		NewLease("0", "新約"),
		ContinueLease("1", "續約"),
		ChangeLease("2", "換約 "),
		ReSignLease("3", "一解一簽"),
		CancelLease("4", "解約"),
		ExtraLease("5", "增補協議")
		;
		
		private final String lsType;
		private final String lsTypeName;
		
		private LsTypeEnum(String lsType, String lsTypeName) {
			this.lsType = lsType;
			this.lsTypeName = lsTypeName;
		}
		
		public String getLsType() {
			return lsType;
		}
		
		public String getLsTypeName() {
			return lsTypeName;
		}
		
		public static List<LabelValueModel> getLabelValueList() {
			List<LabelValueModel> labelList = new ArrayList<LabelValueModel>();
			for (LsTypeEnum element : LsTypeEnum.values()) {
				labelList.add(new LabelValueModel(element.getLsTypeName(), element.getLsType()));
			}
			return labelList;
		}
		
		public static String detectLabel(String value){
			if(value == null || value.length() == 0){
				return "";
			}
			for (LsTypeEnum element : LsTypeEnum.values()) {
				if(element.getLsType().equals(value)){
					return element.getLsTypeName();
				}
			}
			return "";
		}
		
		public static String detectType(String lsType){
			String processType = "";
			if(StringUtils.isBlank(lsType)){
				return processType;
			}
			if(LsTypeEnum.NewLease.getLsType().equals(lsType)){//新約
				processType = "NewLease"; 
			}else if (LsTypeEnum.ContinueLease.getLsType().equals(lsType)){//續約
				processType = "ContinueLease"; 
			}else if(LsTypeEnum.ChangeLease.getLsType().equals(lsType)){//換約
				processType ="ChangeLease"; 
			}else if(LsTypeEnum.ReSignLease.getLsType().equals(lsType)){//一解一簽
				processType = "ReSignLease"; 
			}else if(LsTypeEnum.ExtraLease.getLsType().equals(lsType)){//增補協議
				processType = "ExtraLease";
			}
			return processType;
		}
	}
	
	/**
	 * 申請狀態<BR>
	 * 	0:草稿 <BR>
	 *	1:送審中 <BR>
	 *	2:核可 <BR>
	 *	9:駁回 <BR>
	 */
	public static enum AppStatusEnum {

		appStatus0("0", "草稿"),
		appStatus1("1", "送審中"),
		appStatus2("2", "核可 "),
		appStatus4("4", "作廢 "),
		appStatus9("9", "駁回")
		;
		
		private final String appStatus;
		private final String appStatusName;
		
		private AppStatusEnum(String appStatus, String appStatusName) {
			this.appStatus = appStatus;
			this.appStatusName = appStatusName;
		}
		
		public String getAppStatus() {
			return appStatus;
		}

		public String getAppStatusName() {
			return appStatusName;
		}

		public static List<LabelValueModel> getLabelValueList() {
			List<LabelValueModel> labelList = new ArrayList<LabelValueModel>();
			for (AppStatusEnum element : AppStatusEnum.values()) {
				labelList.add(new LabelValueModel(element.getAppStatusName(), element.getAppStatus()));
			}
			return labelList;
		}
		
		public static String detectLabel(String value){
			if(value == null || value.length() == 0){
				return "";
			}
			for (AppStatusEnum element : AppStatusEnum.values()) {
				if(element.getAppStatus().equals(value)){
					return element.getAppStatusName();
				}
			}
			return "";
		}
	}
	
	/**
	 * 合約狀態<BR>
	 * 	0:未生效 <BR>
	 *	1:生效 <BR>
	 *	9:失效 <BR>
	 */
	public static enum LsStatusEnum {

		lsStatus0("0", "未生效"),
		lsStatus01("1", "生效 "),
		lsStatus09("9", "失效")
		;
		
		private final String lsStatus;
		private final String lsStatusName;
		
		private LsStatusEnum(String lsStatus, String lsStatusName) {
			this.lsStatus = lsStatus;
			this.lsStatusName = lsStatusName;
		}
		
		public String getLsStatus() {
			return lsStatus;
		}

		public String getLsStatusName() {
			return lsStatusName;
		}

		public static List<LabelValueModel> getLabelValueList() {
			List<LabelValueModel> labelList = new ArrayList<LabelValueModel>();
			for (LsStatusEnum element : LsStatusEnum.values()) {
				labelList.add(new LabelValueModel(element.getLsStatusName(), element.getLsStatus()));
			}
			return labelList;
		}
		
		public static String detectLabel(String value){
			if(value == null || value.length() == 0){
				return "";
			}
			for (LsStatusEnum element : LsStatusEnum.values()) {
				if(element.getLsStatus().equals(value)){
					return element.getLsStatusName();
				}
			}
			return "";
		}
	}
	
	/**
	 * 增補協議類別<BR>
	 * 	0:借電類 <BR>
	 *	1:租金調整類 <BR>
	 *	2:加裝設備類 <BR>
	 *	3:出租人及付款對象 <BR>
	 *	4:異動類 <BR>
	 */
	public static enum AddTypeEnum {

		addType0("0", "借電類"),
		addType1("1", "租金調整類"),
		addType2("2", "加裝設備類 "),
		addType3("3", "出租人及付款對象"),
		addType4("4", "異動類")
		;
		
		private final String addType;
		private final String addTypeName;
		
		private AddTypeEnum(String addType, String addTypeName) {
			this.addType = addType;
			this.addTypeName = addTypeName;
		}
		
		public String getAddType() {
			return addType;
		}

		public String getAddTypeName() {
			return addTypeName;
		}

		public static List<LabelValueModel> getLabelValueList() {
			List<LabelValueModel> labelList = new ArrayList<LabelValueModel>();
			for (AddTypeEnum element : AddTypeEnum.values()) {
				labelList.add(new LabelValueModel(element.getAddTypeName(), element.getAddType()));
			}
			return labelList;
		}
		
		public static String detectLabel(String value){
			if(value == null || value.length() == 0){
				return "";
			}
			for (AddTypeEnum element : AddTypeEnum.values()) {
				if(element.getAddType().equals(value)){
					return element.getAddTypeName();
				}
			}
			return "";
		}
	}
}
