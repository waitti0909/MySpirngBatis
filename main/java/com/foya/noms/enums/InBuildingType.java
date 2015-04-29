package com.foya.noms.enums;

import java.util.ArrayList;
import java.util.List;

import com.foya.noms.util.LabelValueModel;

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
 * <td>2014/10/9</td>
 * <td>室內場所類型</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 */
public enum InBuildingType {

	ENT("娛樂"),
	SPT("運動休閒"),
	TRV("觀光旅遊"),
	DEP("百貨商場"),
	RES("餐飲"),
	HOS("醫護院所"),
	TRP("交通"),
	FIN("金融"),
	EDU("文教"),
	REL("宗教"),
	OFI("辦公處所"),
	LIV("住宅宿舍")
	;
	
	private final String localName;
	
	private InBuildingType(String localName) {
		this.localName = localName;
	}
	
	public String getLocalName() {
		return localName;
	}
	
	public static List<LabelValueModel> getLabelValueList() {
		List<LabelValueModel> labelList = new ArrayList<LabelValueModel>();
		for (InBuildingType element : InBuildingType.values()) {
			labelList.add(new LabelValueModel(element.getLocalName(), element.name()));
		}
		return labelList;
	}
	
	public static String detectLabel(String value){
		if(value==null || value.length()==0){
			return "";
		}
		for (InBuildingType element : InBuildingType.values()) {
			if(element.name().equals(value)){
				return element.getLocalName(); 
			}
		}
		return "";
	}
}
