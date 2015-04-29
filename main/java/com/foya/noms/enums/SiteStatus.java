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
 * <td>基站狀態</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 */
public enum SiteStatus {

	S01("SH", "規劃中"),
	S02("SH", "待尋點"),
	S03("SH", "尋點中"),
	S04("SH", "待建設"),
	
	S05("SB", "建設中"),
	S06("SB", "On Air"),
	
	S07("", "關站中"),
	S08("", "停用"),
	S09("", "復站中"),
	S10("", "拆站中"),
	S11("", "拆站")
	;	

	private final String module;
	private final String localName;
	
	private SiteStatus(String module, String localName) {
		this.module = module;
		this.localName = localName;
	}
	
	public String getModule() {
		return module;
	}
	
	public String getLocalName() {
		return localName;
	}
	
	public static List<LabelValueModel> getLabelValueList() {
		return getLabelValueList(null);
	}
	
	public static List<LabelValueModel> getLabelValueList(String module) {
		List<LabelValueModel> labelList = new ArrayList<LabelValueModel>();
		for (SiteStatus element : SiteStatus.values()) {
			if (module == null || StringUtils.equals(module, element.getModule())) {				
				labelList.add(new LabelValueModel(element.getLocalName(), element.name()));
			}
		}
		return labelList;
	}
	
	
	public static String detectLabel(String value){
		if(value==null || value.length()==0){
			return "";
		}
		for (SiteStatus element : SiteStatus.values()) {
			if(element.name().equals(value)){
				return element.getLocalName(); 
			}
		}
		return "";
	}
	
	/*
	 * 取得可能的站台 status S01 ~ S06
	 * */
	public static String[] getAvailableSiteStatus(){
		
		return new String[]{S01.toString(),S02.toString(),S03.toString(),S04.toString(),S05.toString(),S06.toString()};
		
	}
}
