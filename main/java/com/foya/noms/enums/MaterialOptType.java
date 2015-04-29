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
 * <td>2015/1/19</td>
 * <td>物料單操作類別</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 */
public enum MaterialOptType {

	MRQ("領料申請"),
	RTN("退料申請"),
	INS("安裝"),
	REM("拆下"),
	TRO("轉出")
	;
	
	private final String localName;
	
	private MaterialOptType(String localName) {
		this.localName = localName;
	}
	
	public String getLocalName() {
		return localName;
	}
	
	public static List<LabelValueModel> getLabelValueList() {
		List<LabelValueModel> labelList = new ArrayList<LabelValueModel>();
		for (MaterialOptType element : MaterialOptType.values()) {
			if (element != MaterialOptType.TRO) {				
				labelList.add(new LabelValueModel(element.getLocalName(), element.name()));
			}
		}
		return labelList;
	}
	
	public static String detectLabel(String value){
		if(value==null || value.length()==0){
			return "";
		}
		for (MaterialOptType element : MaterialOptType.values()) {
			if(element.name().equals(value)){
				return element.getLocalName(); 
			}
		}
		return "";
	}
	
}
