package com.foya.noms.enums;

import java.util.ArrayList;
import java.util.List;

import com.foya.noms.enums.LsEnumCommon.AddTypeEnum;
import com.foya.noms.util.LabelValueModel;

/**
 * 合約-增補協議類別
 * @author AHisen
 */
public enum LsAddType {

	addType0("0", "借電變更"),
	addType1("1", "租金變更"),
	addType2("2", "加裝設備 "),
	addType3("3", "出租人及付款對象變更"),
	addType4("4", "資料異動")
	;
	
	private final String addType;
	private final String addTypeName;
	
	private LsAddType(String addType, String addTypeName) {
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
