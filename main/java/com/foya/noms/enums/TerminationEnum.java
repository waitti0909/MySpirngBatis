package com.foya.noms.enums;

import java.util.ArrayList;
import java.util.List;

import com.foya.noms.util.LabelValueModel;

public class TerminationEnum {
	
	/**
	 * 解約類型<BR>
	 * 	0:拆站 <BR>
	 *	1:一解一簽 <BR>
	 */
	public static enum LsTerminationTypeEnum {

		lsType0("0", "拆站"),
		lsType1("1", "一解一簽"),
		;
		
		private final String lsType;
		private final String lsTypeName;
		
		private LsTerminationTypeEnum(String lsType, String lsTypeName) {
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
			for (LsTerminationTypeEnum element : LsTerminationTypeEnum.values()) {
				labelList.add(new LabelValueModel(element.getLsTypeName(), element.getLsType()));
			}
			return labelList;
		}
		
		public static String detectLabel(String value){
			if(value == null || value.length() == 0){
				return "";
			}
			for (LsTerminationTypeEnum element : LsTerminationTypeEnum.values()) {
				if(element.getLsType().equals(value)){
					return element.getLsTypeName();
				}
			}
			return "";
		}
	}
	
	/**
	 * 解約原因<BR>
	 * 	0:解約 <BR>
	 *	1:到期不續約 <BR>
	 *	2:出租人變更 <BR>
	 *	3:其他 <BR>
	 */
	public static enum LsTerminationEnum {

		lsType0("0", "解約"),
		lsType1("1", "到期不續約"),
		lsType2("2", "出租人變更 ")
		;
		
		private final String lsType;
		private final String lsTypeName;
		
		private LsTerminationEnum(String lsType, String lsTypeName) {
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
			for (LsTerminationEnum element : LsTerminationEnum.values()) {
				labelList.add(new LabelValueModel(element.getLsTypeName(), element.getLsType()));
			}
			return labelList;
		}
		
		public static String detectLabel(String value){
			if(value == null || value.length() == 0){
				return "";
			}
			for (LsTerminationEnum element : LsTerminationEnum.values()) {
				if(element.getLsType().equals(value)){
					return element.getLsTypeName();
				}
			}
			return "";
		}
	}
	
	
}
