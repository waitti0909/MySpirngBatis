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
 * <td>2015/01/20</td>
 * <td>antCfgCellType</td>
 * <td>Edison chen</td>
 * </tr>
 * </table> 
 *
 * @author Edison chen
 *
 */
public enum AntCfgCellType {
	A("A"),
	AA("AA"),
	AB("AB"),
	B("B"),
	BA("BA"),
	BB("BB"),
	C("C"),
	CA("CA"),
	CB("CB"),
	D("D"),
	DA("DA"),
	DB("DB"),
	E("E"),
	EA("EA"),
	EB("EB"),
	F("F"),
	FA("FA"),
	FB("FB")
	;
	
	private final String localName;
	
	private AntCfgCellType(String localName) {
		this.localName = localName;
	}
	
	public String getLocalName() {
		return localName;
	}
	
	public static List<LabelValueModel> getLabelValueList() {
		List<LabelValueModel> labelList = new ArrayList<LabelValueModel>();
		for (AntCfgCellType element : AntCfgCellType.values()) {
			labelList.add(new LabelValueModel(element.getLocalName(), element.name()));
		}
		return labelList;
	}
	
	public static String detectLabel(String value){
		if(value==null || value.length()==0){
			return "";
		}
		for (AntCfgCellType element : AntCfgCellType.values()) {
			if(element.name().equals(value)){
				return element.getLocalName(); 
			}
		}
		return "";
	}
}
