package com.foya.noms.enums;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

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
 * <td>站點類別項目</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 */
public enum SiteType {
	
	B("Location", "基站"),
	A("Location", "行動車"),
	M("EngineRoom", "傳輸中繼或是同業機房"),
	H("EngineRoom", "公司Hub機房"),
	C("EngineRoom", "公司核心網路機房")
	;
	
	private final String type;
	private final String localName;
	
	private SiteType(String type, String localName) {
		this.type = type;
		this.localName = localName;
	}
	
	public String getType() {
		return type;
	}
	
	public String getLocalName() {
		return localName;
	}
	
	public static List<LabelValueModel> getLabelValueList() {
		return getLabelValueList(null);
	}
	
	public static List<LabelValueModel> getLabelValueList(String siteType) {
		List<LabelValueModel> labelList = new ArrayList<LabelValueModel>();
		for (SiteType element : SiteType.values()) {
			if (siteType == null || StringUtils.equals(element.getType(), siteType)) {				
				labelList.add(new LabelValueModel(element.getLocalName(), element.name()));
			}
		}
		return labelList;
	}
	
	public static String detectLabel(String value){
		if(value==null || value.length()==0){
			return "";
		}
		for (SiteType element : SiteType.values()) {
			if(element.name().equals(value)){
				return element.getLocalName(); 
			}
		}
		return "";
	}
	
}
