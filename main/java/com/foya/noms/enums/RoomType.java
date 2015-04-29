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
 * <td>機房型態</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 */
public enum RoomType {

	RTA("Room type(自建)"),
	RTB("Room type(使用同業)"),
	RTC("Room type(共構)"),
	ST("Shelter type(自建)"),
	IT("Inbuilding type(室內開放空間)"),
	OT("Outdoor type(露天)")
	;

	private final String localName;
	
	private RoomType(String localName) {
		this.localName = localName;
	}
	
	public String getLocalName() {
		return localName;
	}
	
	public static List<LabelValueModel> getLabelValueList() {
		List<LabelValueModel> labelList = new ArrayList<LabelValueModel>();
		for (RoomType element : RoomType.values()) {
			labelList.add(new LabelValueModel(element.getLocalName(), element.name()));
		}
		return labelList;
	}
	
	public static String detectLabel(String value){
		if(value==null || value.length()==0){
			return "";
		}
		for (RoomType element : RoomType.values()) {
			if(element.name().equals(value)){
				return element.getLocalName(); 
			}
		}
		return "";
	}
}
