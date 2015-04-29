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
 * <td>2014/12/10</td>
 * <td>工單狀態</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 */
public enum OrderStatus {

	OR01("草稿"),
	OR02("審核中"),
	OR03("待派工"),
	OR04("已派工"),
	OR05("已接工"),
	OR06("已委外"),
	OR07("已退工"),
	OR08("完工審核"),
	OR09("已完工")
	;
	
	private final String localName;
	
	private OrderStatus(String localName) {
		this.localName = localName;
	}
	
	public String getLocalName() {
		return localName;
	}
	
	public static List<LabelValueModel> getLabelValueList() {
		return getLabelValueListOnProcess(false);
	}
	
	public static List<LabelValueModel> getLabelValueListOnProcess(boolean onProcess) {
		List<LabelValueModel> labelList = new ArrayList<LabelValueModel>();
		for (OrderStatus element : OrderStatus.values()) {
			if (onProcess && (OrderStatus.OR01 == element)) {
				continue;
			}			
			labelList.add(new LabelValueModel(element.getLocalName(), element.name()));
		}
		return labelList;
	}
	
	public static String detectLabel(String value){
		if(value==null || value.length()==0){
			return "";
		}
		for (OrderStatus element : OrderStatus.values()) {
			if(element.name().equals(value)){
				return element.getLocalName(); 
			}
		}
		return "";
	}
	
	public static List<String> getNotFinishedStatus() {
		List<String> noFinished = new ArrayList<String>();
		noFinished.add(OrderStatus.OR03.name());		
		noFinished.add(OrderStatus.OR04.name());
		noFinished.add(OrderStatus.OR05.name());
		noFinished.add(OrderStatus.OR06.name());
		noFinished.add(OrderStatus.OR08.name());
		return noFinished;
	}
}
