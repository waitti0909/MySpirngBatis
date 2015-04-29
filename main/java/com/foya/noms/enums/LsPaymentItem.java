package com.foya.noms.enums;

import java.util.ArrayList;
import java.util.List;

import com.foya.noms.enums.LsEnumCommon.AddTypeEnum;
import com.foya.noms.util.LabelValueModel;

/**
 * 合約-增補協議類別
 * @author AHisen
 */
public enum LsPaymentItem {

	R("R", "租金"),
	RD("RD", "租金押金"),
	E("E", "用電"),
	ED("ED", "用電押金")
	;
	
	private final String itemType;
	private final String itemTypeName;
	
	private LsPaymentItem(String itemType, String itemTypeName) {
		this.itemType = itemType;
		this.itemTypeName = itemTypeName;
	}
	
	public String getItemType() {
		return itemType;
	}

	public String getItemTypeName() {
		return itemTypeName;
	}

	
	public static LsPaymentItem[] getRentItemType() {
		LsPaymentItem[] rentItems = new LsPaymentItem[]{R,RD};
		return rentItems;
	}

	public static LsPaymentItem[] getElecItemType() {
		LsPaymentItem[] elecItems = new LsPaymentItem[]{E,ED};
		return elecItems;
	}
	
	public static LsPaymentItem[] getLsPaymentItem() {
		LsPaymentItem[] elecItems = new LsPaymentItem[]{R,RD,E,ED};
		return elecItems;
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
	
	
	public static boolean isRentItem(String itemValue){
		for(LsPaymentItem rent:getRentItemType()){
			if(rent.getItemType().equalsIgnoreCase(itemValue)){
				return true;
			}
		}
		return false;
	}
	
	public static boolean isElecItem(String itemValue){
		for(LsPaymentItem rent:getElecItemType()){
			if(rent.getItemType().equalsIgnoreCase(itemValue)){
				return true;
			}
		}
		return false;
	}
	
	
	
}
