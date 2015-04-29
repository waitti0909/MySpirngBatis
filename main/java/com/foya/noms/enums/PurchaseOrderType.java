package com.foya.noms.enums;

import java.util.ArrayList;
import java.util.List;

import com.foya.noms.util.LabelValueModel;


public enum PurchaseOrderType {

	T("統包"),
	P("一般");	

	private final String localName;
	
	private PurchaseOrderType(String localName) {
		this.localName = localName;
	}
	
	public String getLocalName() {
		return localName;
	}
	
	public static List<LabelValueModel> getLabelValueList() {
		List<LabelValueModel> labelList = new ArrayList<LabelValueModel>();
		for (PurchaseOrderType element : PurchaseOrderType.values()) {
			labelList.add(new LabelValueModel(element.getLocalName(), element.name()));
		}
		return labelList;
	}
	
	
	public static String detectLabel(String value){
		if(value==null || value.length()==0){
			return "";
		}
		for (PurchaseOrderType element : PurchaseOrderType.values()) {
			if(element.name().equals(value)){
				return element.getLocalName(); 
			}
		}
		return "";
	}
}
