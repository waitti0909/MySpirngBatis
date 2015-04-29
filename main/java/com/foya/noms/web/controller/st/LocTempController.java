package com.foya.noms.web.controller.st;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbSiteWork;
import com.foya.dao.mybatis.model.TbSiteWorkLocTemp;
import com.foya.dao.mybatis.model.TbSiteWorkLocTempKey;
import com.foya.dao.mybatis.model.TbSiteWorkShareTempExample;
import com.foya.dao.mybatis.model.TbSiteWorkShareTempKey;
import com.foya.noms.dto.st.SiteDTO;
import com.foya.noms.enums.InBuildingType;
import com.foya.noms.enums.NSLevel;
import com.foya.noms.enums.RoomType;
import com.foya.noms.enums.SiteType;
import com.foya.noms.service.st.LocTempService;
import com.foya.noms.service.st.ST001Service;
import com.foya.noms.service.st.ST002SP1Service;
import com.foya.noms.service.system.LookupService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.web.controller.BaseController;

@Controller
public class LocTempController extends BaseController {

	@Autowired
	private LookupService lookupService;
	@Autowired
	private ST002SP1Service sT002SP1Service;
	@Autowired
	private ST001Service sT001Service;
	@Autowired
	private LocTempService locTempService;
	
	

	/**
	 * 站點資訊
	 */
	@RequestMapping(value = "/st/locTemp/init")
	@ResponseBody
	public BaseJasonObject<Map<String,Object>> processPage(Map<String, Object> model,
			@RequestParam("workId") String workId,
			@RequestParam("orderId") String orderId) {
		
		TbSiteWork siteWork = locTempService.getSiteWork(workId);
		String locationId = "";
		log.info("siteWorkList = " + siteWork.getLOCATION_ID());
		if(siteWork != null){
			locationId = siteWork.getLOCATION_ID();
		}
		
		List<SiteDTO> siteList = sT002SP1Service.getSiteByLocationId(locationId);
		String isOnAir = "";
		for (SiteDTO site : siteList) {
//			site.setSITE_STATUS(SiteStatus.detectLabel(site.getSITE_STATUS()));
//			site.setCOVERAGE_TYPE(IncludeRange.detectLabel(site.getCOVERAGE_TYPE()));
//			site.setFEEDERLESS(Feederless.detectLabel(site.getFEEDERLESS()));
			if("Y".equals(site.getNOIS_ON_AIR())){
				isOnAir = "Y";
				break;
			}
		}
		//更改 shareCom 從 tb_site_work_share_temp撈出
		TbSiteWorkShareTempExample shareTemp = new TbSiteWorkShareTempExample();
		shareTemp.createCriteria().andWORK_IDEqualTo(workId).andLOCATION_IDEqualTo(locationId);
		List<TbSiteWorkShareTempKey> siteTempList = locTempService.getSelectByExample(shareTemp);
		String shareComTemp="";
		
		if (siteTempList.size() > 0){
			
			for (int i = 0; i < siteTempList.size(); i++){
				shareComTemp += siteTempList.get(i).getSHARE_COM()+",";
			}
		}
		                                             
		//TbSiteLocation tbSiteLocation = sT001Service.getSiteLocationByLocationId(locationId);
		TbSiteWorkLocTempKey key = new TbSiteWorkLocTempKey();
		key.setWORK_ID(workId);
		key.setLOCATION_ID(locationId);
		TbSiteWorkLocTemp tbSiteLocation =  locTempService.selectByPrimaryKey(key);
		model.put("isOnAir", isOnAir);
		
		model.put("shareComTemp", shareComTemp);
		model.put("siteLocation", tbSiteLocation);
		model.put("allSiteTypes", SiteType.getLabelValueList());
		model.put("allRoomTypes", RoomType.getLabelValueList());
		model.put("allInBuildingTypes", InBuildingType.getLabelValueList());
		model.put("allNSLevels", NSLevel.getLabelValueList());
		model.put("allShareCom", lookupService.getLabelValueList("SHARECOM"));
		model.put("siteLocEvent", "siteLocUpdate");
		model.put("eventType", "eidt");
		model.put("siteList", siteList);
		model.put("isMultibandLocation", siteList.size());
		
		return  new BaseJasonObject<>(model, AJAX_SUCCESS);
	}
	
	/**
	 * 用cityId,townId查詢MaintainArea
	 * 
	 * @param cityId
	 * @param townId
	 * @return
	 */
	@RequestMapping(value = "/st/locTemp/maintainArea")
	@ResponseBody
	public Map<String, String> getMaintainAreaByCityIdTownId(@RequestParam("cityId") String cityId,
			@RequestParam("townId") String townId) {
		Map<String, String> map = sT001Service.getMaintainAreaByCityIdTownId(cityId, townId);
		return map;
	}
	
	/**
	 * 用domain查詢MaintainDept
	 * 
	 * @param domain
	 * @return
	 */
	@RequestMapping(value = "/st/locTemp/maintainDept")
	@ResponseBody
	public Map<String, String> getMaintainDeptByDomain(@RequestParam("domain") String domain) {
		Map<String, String> map = sT001Service.getMaintainDeptByDomain(domain);
		return map;
	}

	/**
	 * 用domain查詢MaintainDept
	 * 
	 * @param domain
	 * @return
	 */
	@RequestMapping(value = "/st/locTemp/maintainTeam")
	@ResponseBody
	public Map<String, String> getMaintainTeamByDeptId(@RequestParam("deptId") String deptId) {
		log.debug("deptId : *" + deptId);
		Map<String, String> map = sT001Service.getMaintainTeamByDeptId(deptId);
		return map;
	}

	/**
	 * 用deptId查詢MaintainUser
	 * 
	 * @param deptId
	 * @return
	 */
	@RequestMapping(value = "/st/locTemp/maintainUser")
	@ResponseBody
	public Map<String, String> getMaintainUserByDeptId(@RequestParam("deptId") String deptId) {
		Map<String, String> map = sT001Service.getMaintainUserByDeptId(deptId);
		return map;
	}
	
	/**
	 * 編輯-新增
	 * 
	 * @param workId
	 * @param model
	 * @return
	 */
	
	@RequestMapping(value = "/st/locTemp/save")
	@ResponseBody
	public BaseJasonObject<Object> save(
			@RequestParam("workId")      String workId,  
			@RequestParam("locationId")   String locationId,    
			@RequestParam("locType")      String locType,     
			@RequestParam("shareType")    String shareType,  
			@RequestParam("locName")      String locName,       
			@RequestParam("baseType")     String baseType,      
			@RequestParam("indoorOption") String indoorOption,  
			@RequestParam("city")         String city,          
			@RequestParam("siteLocArea")  String siteLocArea,   
			@RequestParam("lon")          BigDecimal lon,           
			@RequestParam("lat")          BigDecimal lat,           
			@RequestParam("addr")         String addr,          
			@RequestParam("maintArea")    String maintArea,     
			@RequestParam("maintDept")    String maintDept,     
			@RequestParam("maintTeam")    String maintTeam,     
			@RequestParam("maintUser")    String maintUser,     
			@RequestParam("nsLeavel")     String nsLeavel,      
			@RequestParam("buildingHeight") BigDecimal buildingHeight,
			@RequestParam("shareCom")      String shareCom,
			@RequestParam("spaceRoom")    String spaceRoom,     
			@RequestParam("spaceRoof")    String spaceRoof,     
			@RequestParam("spaceTop")     String spaceTop,      
			@RequestParam("spacePlatform") String spacePlatform, 
			@RequestParam("spaceLand")    String spaceLand,     
			@RequestParam("spaceIndoor")  String spaceIndoor,   
			@RequestParam("spaceOutdoor") String spaceOutdoor,  
			@RequestParam("footage")      BigDecimal footage,       
			@RequestParam("empRelation")  String empRelation,   
			@RequestParam("isFreeenter")  String isFreeenter,   
			@RequestParam("isKey")        String isKey,         
			@RequestParam("isIndoor")     String isIndoor,      
			@RequestParam("needMiscLic")  String needMiscLic,   
			@RequestParam("hasMiscLic")   String hasMiscLic,    
			@RequestParam("locDesc")      String locDesc,   
			
			Map<String, Object> model) {
		
		TbSiteWorkLocTemp siteWorkLocTemp = new TbSiteWorkLocTemp();
		siteWorkLocTemp.setWORK_ID(workId);
		siteWorkLocTemp.setLOCATION_ID(locationId);
		siteWorkLocTemp.setLOC_TYPE(locType);
		siteWorkLocTemp.setSHARE_TYPE(shareType);
		siteWorkLocTemp.setLOC_NAME(locName);
		siteWorkLocTemp.setBASE_TYPE(baseType);
		siteWorkLocTemp.setINDOOR_OPTION(indoorOption);
		siteWorkLocTemp.setCITY(city);
		siteWorkLocTemp.setAREA(siteLocArea);
		siteWorkLocTemp.setLON(lon);
		siteWorkLocTemp.setLAT(lat);
		siteWorkLocTemp.setADDR(addr);
		siteWorkLocTemp.setMAINT_AREA(maintArea);
		siteWorkLocTemp.setMAINT_DEPT(maintDept);
		siteWorkLocTemp.setMAINT_TEAM(maintTeam);
		siteWorkLocTemp.setMAINT_PSN(maintUser);
		siteWorkLocTemp.setNS_LEVEL(nsLeavel);
		if (shareCom != null && !"".equals(shareCom)){
			shareCom = shareCom.substring(0, shareCom.length()-1);
		}
		//siteWorkLocTemp.setSHARE_COM(shareCom);
		siteWorkLocTemp.setBUILDING_HEIGHT(buildingHeight);
		siteWorkLocTemp.setSPACE_ROOM(spaceRoom); //空間
		siteWorkLocTemp.setSPACE_ROOF(spaceRoof);//空間
		siteWorkLocTemp.setSPACE_TOP(spaceTop);//空間
		siteWorkLocTemp.setSPACE_PLATFORM(spacePlatform);//空間
		siteWorkLocTemp.setSPACE_LAND(spaceLand);//空間
		siteWorkLocTemp.setSPACE_INDOOR(spaceIndoor);//空間
		siteWorkLocTemp.setSPACE_OUTDOOR(spaceOutdoor);//空間
		siteWorkLocTemp.setFOOTAGE(footage);
		siteWorkLocTemp.setEMP_RELATION(empRelation);
		siteWorkLocTemp.setIS_FREEENTER(isFreeenter);//其他資訊
		siteWorkLocTemp.setIS_KEY(isKey);//其他資訊
		siteWorkLocTemp.setIS_INDOOR(isIndoor);//其他資訊
		siteWorkLocTemp.setNEED_MISC_LIC(needMiscLic);//其他資訊
		siteWorkLocTemp.setHAS_MISC_LIC(hasMiscLic);//其他資訊
		siteWorkLocTemp.setLOC_DESC(locDesc);
        locTempService.updatelocTemp(siteWorkLocTemp);
        
        TbSiteWorkShareTempExample shareTemp = new TbSiteWorkShareTempExample();
    	shareTemp.createCriteria().andWORK_IDEqualTo(workId);
		shareTemp.createCriteria().andLOCATION_IDEqualTo(locationId);
    	locTempService.deleteByExample(shareTemp);
    	
        if (StringUtils.isNotEmpty(shareCom)){
        	String shareComTemp[]=shareCom.split(",");
        	for (int i = 0; i < shareComTemp.length; i++){
        		TbSiteWorkShareTempKey key = new TbSiteWorkShareTempKey();
        		key.setWORK_ID(workId);
        		key.setLOCATION_ID(locationId);
        		key.setSHARE_COM(shareComTemp[i]);
            	locTempService.insert(key);
        	}
        	
        }
		
		return new BaseJasonObject<Object>(true, "存檔成功！");
		
		
		
	}

	
}
