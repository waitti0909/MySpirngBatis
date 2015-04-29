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
 * <td>2014/10/23</td>
 * <td>作業處理狀態</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 */
public enum WorkStatus {

	W01("草稿"),
	W02("待辦"),
	W03("審核中"),
	W04("駁回"),
	W05("處理中"),
	W06("已退工"),
	W07("完工"),
	W08("已取消")
	;
	
	private final String localName;
	
	private WorkStatus(String localName) {
		this.localName = localName;
	}
	
	public String getLocalName() {
		return localName;
	}
	
	public static List<LabelValueModel> getLabelValueList() {
		List<LabelValueModel> labelList = new ArrayList<LabelValueModel>();
		for (WorkStatus element : WorkStatus.values()) {
			labelList.add(new LabelValueModel(element.getLocalName(), element.name()));
		}
		return labelList;
	}
	
	public static String detectLabel(String value){
		if(value==null || value.length()==0){
			return "";
		}
		for (WorkStatus element : WorkStatus.values()) {
			if(element.name().equals(value)){
				return element.getLocalName(); 
			}
		}
		return "";
	}
}
