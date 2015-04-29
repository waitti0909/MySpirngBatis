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
 * <td>2014/10/20</td>
 * <td>工務類型</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 */
public enum WorkType {

	NSR("ST002", "New Site Request", "新站請求"),
	SH("ST002", "Site Hunt", "尋找站點"), 
	
	SB("ST004", "Site Build", "基站建站"),
	SBH("ST004", "Site Build", "HUB機房建站"),
	SBN("ST004", "Site Build Network", "核網機房建站"),
	
	SGL("ST009", "Single Order", "單張工單")
	;
	
	private final String module;
	private final String localName;
	private final String chName;
	
	private WorkType(String module, String localName, String chName) {
		this.module = module;
		this.localName = localName;
		this.chName = chName;
	}
	
	public String getModule() {
		return module;
	}
	
	public String getLocalName() {
		return localName;
	}
	
	public String getChName() {
		return chName;
	}
	
	public static List<LabelValueModel> getLabelValueList() {
		return getLabelValueList(null);
	}
	
	public static List<LabelValueModel> getLabelValueList(String module) {
		List<LabelValueModel> labelList = new ArrayList<LabelValueModel>();
		for (WorkType element : WorkType.values()) {
			if (module == null || StringUtils.equals(module, element.getModule())) {				
				labelList.add(new LabelValueModel(element.getChName(), element.name()));
			}
		}
		return labelList;
	}
	
	public static String detectLabel(String value){
		if(value==null || value.length()==0){
			return "";
		}
		for (WorkType element : WorkType.values()) {
			if(element.name().equals(value)){
				return element.getChName(); 
			}
		}
		return "";
	}
}
