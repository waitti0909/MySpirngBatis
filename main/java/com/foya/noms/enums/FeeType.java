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
 * <td>基站狀態</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 */
public enum FeeType {

	C("CAPEX"),
	O("OPEX");	

	private final String localName;
	
	private FeeType(String localName) {
		this.localName = localName;
	}
	
	public String getLocalName() {
		return localName;
	}
	
	public static List<LabelValueModel> getLabelValueList() {
		List<LabelValueModel> labelList = new ArrayList<LabelValueModel>();
		for (FeeType element : FeeType.values()) {
			labelList.add(new LabelValueModel(element.getLocalName(), element.name()));
		}
		return labelList;
	}
	
	
	public static String detectLabel(String value){
		if(value==null || value.length()==0){
			return "";
		}
		for (FeeType element : FeeType.values()) {
			if(element.name().equals(value)){
				return element.getLocalName(); 
			}
		}
		return "";
	}
}
