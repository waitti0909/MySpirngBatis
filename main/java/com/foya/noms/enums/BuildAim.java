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
 * <td>2014/10/31</td>
 * <td>建站目的</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 */
public enum BuildAim {

	NEW("新站補點"),
	EXP("到期補點"),
	REJ("抗爭補點"),
	OPT("優化補點"),
	CON("共構補點")
	;
	
	private final String localName;
	
	private BuildAim(String localName) {
		this.localName = localName;
	}
	
	public String getLocalName() {
		return localName;
	}
	
	public static List<LabelValueModel> getLabelValueList() {
		List<LabelValueModel> labelList = new ArrayList<LabelValueModel>();
		for (BuildAim element : BuildAim.values()) {
			labelList.add(new LabelValueModel(element.getLocalName(), element.name()));
		}
		return labelList;
	}
	
	public static String detectLabel(String value){
		if(value==null || value.length()==0){
			return "";
		}
		for (BuildAim element : BuildAim.values()) {
			if(element.name().equals(value)){
				return element.getLocalName(); 
			}
		}
		return "";
	}
}
