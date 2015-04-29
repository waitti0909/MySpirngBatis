package com.foya.noms.web.controller.st;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hornetq.utils.json.JSONArray;
import org.hornetq.utils.json.JSONException;
import org.hornetq.utils.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbComAntenna;
import com.foya.dao.mybatis.model.TbComAntennaExample;
import com.foya.dao.mybatis.model.TbComEqpModel;
import com.foya.dao.mybatis.model.TbComEqpModelExample;
import com.foya.dao.mybatis.model.TbSiteLocation;
import com.foya.dao.mybatis.model.TbSiteLocationExample;
import com.foya.dao.mybatis.model.TbSiteMain;
import com.foya.dao.mybatis.model.TbSiteMainExample;
import com.foya.dao.mybatis.model.TbSiteSearch;
import com.foya.dao.mybatis.model.TbSiteSearchExample;
import com.foya.dao.mybatis.model.TbSiteShareSearchExample;
import com.foya.dao.mybatis.model.TbSiteShareSearchKey;
import com.foya.dao.mybatis.model.TbSiteSrAntCfgTemp;
import com.foya.dao.mybatis.model.TbSiteSrAntCfgTempExample;
import com.foya.dao.mybatis.model.TbSiteSrSiteTemp;
import com.foya.dao.mybatis.model.TbSiteSrSiteTempExample;
import com.foya.dao.mybatis.model.TbSiteWork;
import com.foya.dao.mybatis.model.TbSiteWorkExample;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.dao.mybatis.model.TbSysLookupExample;
import com.foya.exception.NomsException;
import com.foya.noms.dto.st.SiteSearchDTO;
import com.foya.noms.enums.AntCfgCellType;
import com.foya.noms.enums.AntInstallWay;
import com.foya.noms.enums.AntSignal;
import com.foya.noms.enums.FeederCableType;
import com.foya.noms.enums.Feederless;
import com.foya.noms.enums.InBuildingType;
import com.foya.noms.enums.IncludeRange;
import com.foya.noms.enums.RoomType;
import com.foya.noms.enums.SiteStatus;
import com.foya.noms.resources.AppConstants;
import com.foya.noms.service.st.ST002SP1Service;
import com.foya.noms.service.st.SearchRecordService;
import com.foya.noms.service.system.UniqueSeqService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.DateUtils;
import com.foya.noms.util.LabelValueModel;
import com.foya.noms.web.controller.BaseController;

@Controller
@RequestMapping(value = "/st/searchRecord")
public class SearchRecordController extends BaseController {

	@Autowired
	SearchRecordService searchRecordService;

	@Autowired
	UniqueSeqService uniqueSeqService;

	@Autowired
	private ST002SP1Service sT002SP1Service;

	/**
	 * 探勘頁 -- 機房型態 下拉選單
	 * 
	 * @return
	 */
	@RequestMapping(value = "/baseType")
	@ResponseBody
	public BaseJasonObject<LabelValueModel> initSearchRecordPage(HttpServletRequest request, Map<String, Object> model,
			@RequestParam("workId") String workId) {
		List<LabelValueModel> labelModelList = RoomType.getLabelValueList();
		return new BaseJasonObject<>(labelModelList, AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 探勘頁 -- 室內場所分類 下拉選單
	 * 
	 * @return
	 */
	@RequestMapping(value = "/inBuilding")
	@ResponseBody
	public BaseJasonObject<LabelValueModel> initInBuildingPage(HttpServletRequest request, Map<String, Object> model,
			@RequestParam("workId") String workId) {
		List<LabelValueModel> labelModelList = InBuildingType.getLabelValueList();
		return new BaseJasonObject<>(labelModelList, AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 探勘頁 -- 天線支架 下拉選單
	 * 
	 * @return
	 */
	@RequestMapping(value = "/antStand")
	@ResponseBody
	public BaseJasonObject<LabelValueModel> initAntStandPage(HttpServletRequest request, Map<String, Object> model,
			@RequestParam("workId") String workId) {
		List<LabelValueModel> labelModelList = AntInstallWay.getLabelValueList();
		return new BaseJasonObject<>(labelModelList, AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 探勘頁 -- 設備型態 下拉選單
	 * 
	 * @return
	 */
	@RequestMapping(value = "/siteHuntEqp")
	@ResponseBody
	public BaseJasonObject<LabelValueModel> initSiteHuntEqpPage(HttpServletRequest request, Map<String, Object> model,
			@RequestParam("workId") String workId) {
		List<LabelValueModel> labelModelList = sT002SP1Service.getEqpTypes();
		return new BaseJasonObject<>(labelModelList, AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 探勘頁 -- 設備機型-下拉選單
	 * 
	 * @return
	 */
	@RequestMapping(value = "/siteHuntEqpType")
	@ResponseBody
	public BaseJasonObject<TbComEqpModel> initEqpTypePage(HttpServletRequest request, Map<String, Object> model,
			@RequestParam("eqpType") String eqpType) {
		TbComEqpModelExample example = new TbComEqpModelExample();
		if(StringUtils.isNotBlank(eqpType)) {
			example.createCriteria().andEQP_TYPE_IDEqualTo(eqpType);
		}
		List<TbComEqpModel> labelModelList = searchRecordService.getEqpModelByExample(example);
		return new BaseJasonObject<>(labelModelList, AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 探勘頁 -- Feederless 下拉選單
	 * 
	 * @return
	 */
	@RequestMapping(value = "/feederless")
	@ResponseBody
	public BaseJasonObject<LabelValueModel> initFeederlessPage(HttpServletRequest request, Map<String, Object> model,
			@RequestParam("workId") String workId) {
		List<LabelValueModel> labelModelList = Feederless.getLabelValueList();
		return new BaseJasonObject<>(labelModelList, AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 探勘頁 -- 涵蓋類別 下拉選單
	 * 
	 * @return
	 */
	@RequestMapping(value = "/includeRange")
	@ResponseBody
	public BaseJasonObject<LabelValueModel> initIncludeRangePage(HttpServletRequest request, Map<String, Object> model,
			@RequestParam("workId") String workId) {
		List<LabelValueModel> labelModelList = IncludeRange.getLabelValueList();
		return new BaseJasonObject<>(labelModelList, AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 探勘頁 -- 訊號源 下拉選單
	 * 
	 * @return
	 */
	@RequestMapping(value = "/antSignal")
	@ResponseBody
	public BaseJasonObject<LabelValueModel> initAntSignalPage(HttpServletRequest request, Map<String, Object> model,
			@RequestParam("workId") String workId) {
		List<LabelValueModel> labelModelList = AntSignal.getLabelValueList();
		return new BaseJasonObject<>(labelModelList, AJAX_SUCCESS, AJAX_EMPTY);
	}
	
	/**
	 * 探勘頁 -- cell 下拉選單
	 * 
	 * @return
	 */
	@RequestMapping(value = "/antCell")
	@ResponseBody
	public BaseJasonObject<LabelValueModel> initAntCellPage(HttpServletRequest request, Map<String, Object> model,
			@RequestParam("workId") String workId) {
		List<LabelValueModel> labelModelList = AntCfgCellType.getLabelValueList();
		return new BaseJasonObject<>(labelModelList, AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 探勘頁 -- 安裝方式 下拉選單
	 * 
	 * @return
	 */
	@RequestMapping(value = "/antInstallWay")
	@ResponseBody
	public BaseJasonObject<LabelValueModel> initAntInstallWayPage(HttpServletRequest request, Map<String, Object> model,
			@RequestParam("workId") String workId) {
		List<LabelValueModel> labelModelList = AntInstallWay.getLabelValueList();
		return new BaseJasonObject<>(labelModelList, AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 探勘頁 -- 天線型號 下拉選單
	 * 
	 * @return
	 */
	@RequestMapping(value = "/antenna")
	@ResponseBody
	public BaseJasonObject<TbComAntenna> initAntennaPage(HttpServletRequest request, Map<String, Object> model, @RequestParam("workId") String workId) {
		TbComAntennaExample example = new TbComAntennaExample();
		List<TbComAntenna> labelModelList = searchRecordService.getAntennaByExample(example);
		return new BaseJasonObject<>(labelModelList, AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 探勘頁 -- FeederCableType 下拉選單
	 * 
	 * @return
	 */
	@RequestMapping(value = "/feederCableType")
	@ResponseBody
	public BaseJasonObject<LabelValueModel> initFeederCableTypePage(HttpServletRequest request, Map<String, Object> model,
			@RequestParam("workId") String workId) {
		List<LabelValueModel> labelModelList = FeederCableType.getLabelValueList();
		return new BaseJasonObject<>(labelModelList, AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 共站業者
	 * 
	 * @param request
	 * @param model
	 * @param workId
	 * @return
	 */
	@RequestMapping(value = "/lookup")
	@ResponseBody
	public BaseJasonObject<TbSysLookup> initLookupPage(HttpServletRequest request, Map<String, Object> model, @RequestParam("workId") String workId) {
		List<TbSysLookup> labelModelList = searchRecordService.getLookupAll(new TbSysLookupExample());
		return new BaseJasonObject<>(labelModelList, AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 探勘序號
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/uniqueSeq")
	@ResponseBody
	public BaseJasonObject<Object> initUniqueSeq(HttpServletRequest request, Map<String, Object> model, @RequestParam("srId") String srId) {
		String temp = uniqueSeqService.getNextSrId(srId);
		List<Object> labelModelList = new ArrayList<Object>();
		labelModelList.add(temp);
		return new BaseJasonObject<>(labelModelList, AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 查詢 探勘記錄
	 * 
	 * @param workId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/searchRecordList")
	@ResponseBody
	public BaseJasonObject<SiteSearchDTO> getOutSourceTable(@RequestParam("workId") String workId, Map<String, Object> model) {
		TbSiteSearchExample example = new TbSiteSearchExample();	
		example.createCriteria().andWORK_IDEqualTo(workId);
		List<SiteSearchDTO> searchRecordList = searchRecordService.getSiteSearchByExample(example);
		for (SiteSearchDTO temp : searchRecordList) {
			if (StringUtils.equals(temp.getSR_EVAL(), "1")) {
				temp.setEvalName("可用");
			} else {
				temp.setEvalName("建議取消");
			}
		}
		return new BaseJasonObject<>(searchRecordList, AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 基站狀態查詢
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/siteStatusId")
	@ResponseBody
	public BaseJasonObject<Object> initSiteStatusId(HttpServletRequest request, Map<String, Object> model, @RequestParam("searchSiteId") String searchSiteId) {
		TbSiteMain temp = searchRecordService.getSiteStatusIdByExample(searchSiteId);
		String statusName = SiteStatus.detectLabel(temp.getSITE_STATUS());

		List<Object> labelModelList = new ArrayList<Object>();
		labelModelList.add(statusName);
		return new BaseJasonObject<>(labelModelList, AJAX_SUCCESS, AJAX_EMPTY);

	}

	/**
	 * 點選上方選單資料 利用探勘序號 srSeq 查詢 出 該筆資料
	 * 
	 * @param srSeq
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/searchTable")
	@ResponseBody
	public BaseJasonObject<SiteSearchDTO> getSiteSearchTable(@RequestParam("srId") String srId, @RequestParam("srSeq") String srSeq,
			Map<String, Object> model) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("srSeq", srSeq);
		map.put("srId", srId);
		log.debug("探勘帶入選擇資料" + srSeq + "，" + srId);
		List<SiteSearchDTO> searchTableList = searchRecordService.getSearchTableByExample(map);
		log.debug("<--------------"+searchTableList.get(0).getFLOOR());
		return new BaseJasonObject<>(searchTableList, AJAX_SUCCESS, AJAX_EMPTY);
	}
	
	/**
	 * 點選上方選單資料 利用探勘序號 srSeq 查詢 出 共站業者
	 * 
	 * @param srSeq
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/shareSearch")
	@ResponseBody
	public BaseJasonObject<TbSiteShareSearchKey> getSiteSharSearchTable(@RequestParam("srId") String srId, @RequestParam("srSeq") String srSeq) {
		TbSiteShareSearchExample example = new TbSiteShareSearchExample();
		example.createCriteria().andSR_IDEqualTo(srId).andSR_SEQEqualTo(srSeq);
		List<TbSiteShareSearchKey> shareSearchList = searchRecordService.getShareSearchByExample(example);

		return new BaseJasonObject<>(shareSearchList, AJAX_SUCCESS, AJAX_EMPTY);
	}	

	/**
	 * 點選上方選單資料 利用探勘序號 srSeq 查詢 出 天氣組態資料
	 * 
	 * @param srSeq
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/siteSrAntCfgTemp")
	@ResponseBody
	public BaseJasonObject<TbSiteSrAntCfgTemp> getSiteSrAntCfgTemp(@RequestParam("srId") String srId, @RequestParam("srSeq") String srSeq) {
		TbSiteSrAntCfgTempExample example = new TbSiteSrAntCfgTempExample();
		example.createCriteria().andSR_IDEqualTo(srId).andSR_SEQEqualTo(srSeq);
		List<TbSiteSrAntCfgTemp> srAntCfgList = searchRecordService.getSiteSrAntCfgTempByExample(example);
		for (TbSiteSrAntCfgTemp model : srAntCfgList) {
			log.debug("model.getJ_CABLE_LEN()=============>" + model.getJ_CABLE_LEN());
		}
		return new BaseJasonObject<>(srAntCfgList, AJAX_SUCCESS, AJAX_EMPTY);
	}
	
	/**
	 * 探勘新增儲存
	 */
	@RequestMapping(value="saveNewSiteSearch")
	@ResponseBody
	public boolean assignNewOrder(Map<String, Object> map,
			@RequestParam("workId") String workId, 
			@RequestParam("srId") String srId, 
			@RequestParam("srSeq") String srSeq, 
			@RequestParam("searchSiteId") String  searchSiteId, @RequestParam("siteTempName") String siteTempName,
			@RequestParam("srPsn") String srPsn, @RequestParam("srDate") String srDate,
			@RequestParam("srEval") String srEval, @RequestParam("address") String address,
			@RequestParam("siteLon") String siteLon, @RequestParam("siteLat") String siteLat,
			@RequestParam("baseType") String baseType, @RequestParam("height") String height,
			@RequestParam("floor") String floor, @RequestParam("inBuilding") String inBuilding,
			@RequestParam("isFreeEnter") String isFreeEnter, @RequestParam("isKey") String isKey,
			@RequestParam("isInDoor") String isInDoor, @RequestParam("needMiscLic") String needMiscLic,
			@RequestParam("hasMiscLic") String hasMiscLic, @RequestParam("lineReachable") String lineReachable,
			@RequestParam("expectRent") String expectRent, @RequestParam("shareType") String shareType,
			@RequestParam("antStand") String antStand, @RequestParam("antSHight") String antSHight,
			@RequestParam("antCHight") String antCHight, @RequestParam("lookup") String lookup,		
			@RequestParam("spaceRoom") String spaceRoom, @RequestParam("spaceRoof") String spaceRoof,
			@RequestParam("spaceTop") String spaceTop, @RequestParam("spacePlatform") String spacePlatform,
			@RequestParam("spaceLand") String spaceLand, @RequestParam("spaceIndoor") String spaceIndoor,
			@RequestParam("spaceOutdoor") String spaceOutdoor, @RequestParam("footAge") String footAge,
			@RequestParam("empRelation") String empRelation, @RequestParam("locDesc") String locDesc,
			@RequestParam("qq_zip") String qq_zip,@RequestParam("qq_city") String qq_city,
			@RequestParam("qq_area") String qq_area,@RequestParam("qq_village") String qq_village,
			@RequestParam("qq_adjacent") String qq_adjacent,@RequestParam("qq_road") String qq_road,
			@RequestParam("qq_lane") String qq_lane,@RequestParam("qq_alley") String qq_alley,
			@RequestParam("qq_subAlley") String qq_subAlley,@RequestParam("qq_doorNo") String qq_doorNo,
			@RequestParam("qq_doorNoEx") String qq_doorNoEx,@RequestParam("qq_floor") String qq_floor,
			@RequestParam("qq_floorEx") String qq_floorEx,@RequestParam("qq_room") String qq_room,
			@RequestParam("qq_remark") String qq_remark,@RequestParam("siteHuntEqpTemp") String siteHuntEqpTemp,
			@RequestParam("siteHuntEqpModelTemp") String siteHuntEqpModelTemp, @RequestParam("feederlessTeam") String feederlessTeam,
			@RequestParam("masterSiteTemp") String masterSiteTemp, @RequestParam("configuration") String configuration,
//			@RequestParam("speedUpload") String speedUpload, @RequestParam("speedDownload") String speedDownload,
			@RequestParam("donorBts") String donorBts, @RequestParam("includeRange") String includeRange,
			@RequestParam("coverageInFloor") String coverageInFloor, @RequestParam("power") String power, @RequestParam("cluster") String cluster,
			@RequestParam("rncBts") String rncBts, @RequestParam("coverageIndoor") String coverageIndoor,
			@RequestParam("hidden") String hidden,@RequestParam("noisOnAir") String noisOnAir,@RequestParam("l2Switch") String l2Switch,
			@RequestParam("exportList") String exportJson) {
		try{
			log.debug("=====SearchRecordSave====");
			TbSiteSearch siteRecord = new TbSiteSearch();
			TbSiteSrSiteTemp record = new TbSiteSrSiteTemp();
			
			
			//====================================================================================
			TbSiteWorkExample work = new TbSiteWorkExample();
			work.createCriteria().andWORK_IDEqualTo(workId);
			List<TbSiteWork> workByDate = searchRecordService.getselectByExampleWork(work);
			
			TbSiteMainExample example = new TbSiteMainExample();
			example.createCriteria().andSITE_IDEqualTo(workByDate.get(0).getSITE_ID());
			List<TbSiteMain> mainByData = searchRecordService.getselectByExampleMain(example);
			
			TbSiteLocationExample location = new TbSiteLocationExample();
			String locId = mainByData.get(0).getLOCATION_ID();
			if (locId != null && !locId.isEmpty()) {
				location.createCriteria().andLOCATION_IDEqualTo(locId);
			}
			List<TbSiteLocation> locByData = searchRecordService.getselectByExampleLoc(location);
			
			//=====================================================================================
			siteRecord.setSR_ID(srId);
			siteRecord.setSR_SEQ(srSeq);
			siteRecord.setWORK_ID(workId);
			if(locId != null && !locId.isEmpty()) {
				siteRecord.setMAINT_AREA(locByData.get(0).getMAINT_AREA());
				siteRecord.setMAINT_DEPT(locByData.get(0).getMAINT_DEPT());
				siteRecord.setMAINT_TEAM(locByData.get(0).getMAINT_TEAM());
				siteRecord.setMAINT_USER(locByData.get(0).getMAINT_USER());
				siteRecord.setNS_LEVEL(locByData.get(0).getNS_LEVEL());
				siteRecord.setLOC_TYPE(locByData.get(0).getLOC_TYPE());
				siteRecord.setLOCATION_ID(locByData.get(0).getLOCATION_ID());
			}
			siteRecord.setSITE_ID(mainByData.get(0).getSITE_ID());
			siteRecord.setLOC_NAME(siteTempName);
			siteRecord.setSR_PSN(srPsn);
			siteRecord.setSR_DATE(DateUtils.paserDate(srDate, AppConstants.DATE_PATTERN));
			siteRecord.setSR_EVAL(srEval);
			siteRecord.setADDR(address);
			siteRecord.setLON(new BigDecimal(siteLon));
			siteRecord.setLAT(new BigDecimal(siteLat));
			siteRecord.setBASE_TYPE(baseType);
			if(height !=null && !height.isEmpty()) {
				siteRecord.setBUILDING_HEIGHT(new BigDecimal(height));
			}
			siteRecord.setBUILDING_FLOOR(floor);
			siteRecord.setINDOOR_OPTION(inBuilding);
			siteRecord.setIS_FREEENTER(isFreeEnter);
			siteRecord.setIS_KEY(isKey);
			siteRecord.setIS_INDOOR(isInDoor);
			siteRecord.setNEED_MISC_LIC(needMiscLic);
			siteRecord.setHAS_MISC_LIC(hasMiscLic);
			siteRecord.setLINE_REACHABLE(lineReachable);
			if(expectRent !=null && !expectRent.isEmpty()) {
				siteRecord.setEXPECT_RENT(new BigDecimal(expectRent));
			}
			siteRecord.setSHARE_TYPE(shareType);
			siteRecord.setANT_STAND(antStand);
			if(antCHight !=null && !antCHight.isEmpty()) {
				siteRecord.setANT_C_HIGHT(new Integer(antCHight));
			}
			if(antSHight !=null && !antSHight.isEmpty()) {
				siteRecord.setANT_S_HIGHT(new Integer(antSHight));
			}
			//siteRecord.setSHARE_COM(lookup);
			siteRecord.setSPACE_ROOM(spaceRoom);
			siteRecord.setSPACE_ROOF(spaceRoof);
			siteRecord.setSPACE_TOP(spaceTop);
			siteRecord.setSPACE_PLATFORM(spacePlatform);
			siteRecord.setSPACE_OUTDOOR(spaceOutdoor);
			siteRecord.setSPACE_LAND(spaceLand);
			siteRecord.setSPACE_INDOOR(spaceIndoor);
			if(footAge !=null && !footAge.isEmpty()) {
				siteRecord.setFOOTAGE(new BigDecimal(footAge));
			}
			siteRecord.setEMP_RELATION(empRelation);
			siteRecord.setLOC_DESC(locDesc);
			siteRecord.setZIP(qq_zip);
			siteRecord.setCITY(qq_city);
			siteRecord.setAREA(qq_area);
			siteRecord.setVILLAGE(qq_village);
			siteRecord.setADJACENT(qq_adjacent);
			siteRecord.setROAD(qq_road);
			siteRecord.setALLEY(qq_alley);
			siteRecord.setSUB_ALLEY(qq_subAlley);
			siteRecord.setDOOR_NO(qq_doorNo);
			siteRecord.setDOOR_NO_EX(qq_doorNoEx);
			siteRecord.setFLOOR(qq_floor);
			siteRecord.setFLOOR_EX(qq_floorEx);
			siteRecord.setROOM(qq_room);
			siteRecord.setADD_OTHER(qq_remark);
			siteRecord.setLANE(qq_lane);
			
			record.setSR_ID(srId);
			record.setSR_SEQ(srSeq);
			record.setSITE_ID(mainByData.get(0).getSITE_ID());
			record.setFEQ_ID(mainByData.get(0).getFEQ_ID());
			record.setBTS_SITE_ID(mainByData.get(0).getBTS_SITE_ID());
			record.setLOCATION_ID(locId);
			record.setSITE_NAME(mainByData.get(0).getSITE_NAME());
			record.setPURPOSE(mainByData.get(0).getPURPOSE());
			record.setNIOS_RPT_STATUS(mainByData.get(0).getNIOS_RPT_STATUS());
			record.setCELL_STATUS(mainByData.get(0).getCELL_STATUS());
			record.setOMCU_OBJ(mainByData.get(0).getOMCU_OBJ());
			record.setCST_ID_CARD_NUM(mainByData.get(0).getCST_ID_CARD_NUM());
			record.setCST_ID(mainByData.get(0).getCST_ID());
			record.setCST_MOBILE(mainByData.get(0).getCST_MOBILE());
			record.setCOVERAGE_INDOOR(mainByData.get(0).getCOVERAGE_INDOOR());
			record.setHIDDEN(mainByData.get(0).getHIDDEN());
			record.setNOIS_ON_AIR(mainByData.get(0).getNOIS_ON_AIR());
			record.setL2_SWITCH(mainByData.get(0).getL2_SWITCH());
			record.setCOVERAGE_IN_FLOOR(mainByData.get(0).getCOVERAGE_IN_FLOOR());
			record.setEQP_TYPE_ID(siteHuntEqpTemp);
			record.setEQP_MODEL_ID(siteHuntEqpModelTemp);
			record.setFEEDERLESS(feederlessTeam);
			record.setMASTER_SITE(masterSiteTemp);
			record.setBTS_CONFIG(configuration);
//			record.setSPEED_UPLOAD(speedUpload);
//			record.setSPEED_DOWNLOAD(speedDownload);
			record.setDONOR_SITE(donorBts);
			record.setCOVERAGE_TYPE(includeRange);
			record.setPOWER(power);
			record.setCLUSTER(cluster);
			record.setRNC_BTS(rncBts);
			record.setCOVERAGE_IN_FLOOR(coverageInFloor);
			record.setCOVERAGE_INDOOR(coverageIndoor);
			record.setHIDDEN(hidden);
			record.setNOIS_ON_AIR(noisOnAir);
			record.setL2_SWITCH(l2Switch);	
			
			
			//=====共站業者 lookup
			List<TbSiteShareSearchKey> shareSearchList = new ArrayList<TbSiteShareSearchKey>();
			if(StringUtils.isNotBlank(lookup)) {
				String[] shareTemp = lookup.split(",");
				for (int i = 0; i < shareTemp.length; i++) {
					TbSiteShareSearchKey shareSearchKey = new TbSiteShareSearchKey();
					shareSearchKey.setSR_ID(srId);
					shareSearchKey.setSR_SEQ(srSeq);
					shareSearchKey.setSHARE_COM(shareTemp[i]);
					shareSearchList.add(shareSearchKey);
				}
			}
			//=====
			
			// ========================================TB_SITE_SR_ANT_CFG_TEMP
			List<TbSiteSrAntCfgTemp> tempList = new ArrayList<TbSiteSrAntCfgTemp>();

			JSONArray jsonArrayTemp = new JSONArray(exportJson);

			for (int i = 0; i < jsonArrayTemp.length(); i++) {
				TbSiteSrAntCfgTemp temp = new TbSiteSrAntCfgTemp();
				JSONObject objTemp = jsonArrayTemp.getJSONObject(i);
				temp.setSR_ID(srId);
				temp.setSR_SEQ(srSeq);
				temp.setSITE_ID(mainByData.get(0).getSITE_ID());
				temp.setANT_NO(String.valueOf(objTemp.get("siteLine2")));//天線編號
				temp.setCELL_ID(String.valueOf(objTemp.get("siteLine2")));//cell
				temp.setSEG_SOURCE(String.valueOf(objTemp.get("siteLine3")));//訊號源
				temp.setP_CODE(String.valueOf(objTemp.get("siteLine4")));//p-code
				temp.setANT_MODEL(String.valueOf(objTemp.get("siteLine5")));//天線型號
				if(!objTemp.get("siteLine6").equals(null) && !objTemp.get("siteLine6").equals("")) {
					temp.setANT_HIGH(BigDecimal.valueOf(Double.parseDouble(String.valueOf(objTemp.get("siteLine6")))));//安裝高度
				}
				if(!objTemp.get("siteLine7").equals(null) && !objTemp.get("siteLine7").equals("")) {
					temp.setANT_ORIENT(BigDecimal.valueOf(Double.parseDouble(String.valueOf(objTemp.get("siteLine7")))));//方向角
				}
				if(!objTemp.get("siteLine8").equals(null) && !objTemp.get("siteLine8").equals("")) {
					temp.setE_TILT(BigDecimal.valueOf(Double.parseDouble(String.valueOf(objTemp.get("siteLine8")))));//E_TILT
				}
				if(!objTemp.get("siteLine9").equals(null) && !objTemp.get("siteLine9").equals("")) {
					temp.setM_TILT(BigDecimal.valueOf(Double.parseDouble(String.valueOf(objTemp.get("siteLine9")))));//MTILT
				}	
				temp.setSETUP_STYLE(String.valueOf(objTemp.get("siteLine10")));//安裝方式
				
				if(!objTemp.get("siteLine11").equals(null) && !objTemp.get("siteLine11").equals("")) {
					temp.setFLOOR_LEVEL(new BigDecimal(String.valueOf(objTemp.get("siteLine11"))));//樓高
				}
				if(!objTemp.get("siteLine12").equals(null) && !objTemp.get("siteLine12").equals("")) {
					temp.setTOWER_HEIGHT(new BigDecimal(String.valueOf(objTemp.get("siteLine12"))));//鐵塔	
				}
				if(!objTemp.get("siteLine13").equals(null) && !objTemp.get("siteLine13").equals("")) {
					temp.setPENTHOUSE_HEIGHT(new BigDecimal(String.valueOf(objTemp.get("siteLine13"))));//屋突
				}
				temp.setADDR(String.valueOf(objTemp.get("siteLine14"))); //地址
				if(!objTemp.get("siteLine15").equals(null) && !objTemp.get("siteLine15").equals("")) {
					temp.setLON(new BigDecimal(String.valueOf(objTemp.get("siteLine15"))));//經度	
				}
				if(!objTemp.get("siteLine16").equals(null) && !objTemp.get("siteLine16").equals("")) {
					temp.setLAT(new BigDecimal(String.valueOf(objTemp.get("siteLine16"))));//緯度
				}
				temp.setF_CABLE_TYPE(String.valueOf(objTemp.get("siteLine17")));//Feeder cable type	
				if(!objTemp.get("siteLine18").equals(null) && !objTemp.get("siteLine18").equals("")) {
					temp.setF_CABLE_LEN(new BigDecimal(String.valueOf(objTemp.get("siteLine18"))));//Feeder cable 長度
				}
				if(!objTemp.get("siteLine19").equals(null) && !objTemp.get("siteLine19").equals("")) {
					temp.setJ_CABLE_LEN(new BigDecimal(String.valueOf(objTemp.get("siteLine19"))));//Jumper cable 長度
				}	
				temp.setCOVER_REG(String.valueOf(objTemp.get("siteLine20")));//覆蓋區域
				
				temp.setZIP(String.valueOf(objTemp.get("siteLine21")));
				temp.setCITY(String.valueOf(objTemp.get("siteLine22")));
				temp.setAREA(String.valueOf(objTemp.get("siteLine23")));
				temp.setVILLAGE(String.valueOf(objTemp.get("siteLine24")));
				temp.setROAD(String.valueOf(objTemp.get("siteLine25")));
				temp.setADJACENT(String.valueOf(objTemp.get("siteLine26")));
				temp.setLANE(String.valueOf(objTemp.get("siteLine27")));
				temp.setALLEY(String.valueOf(objTemp.get("siteLine28")));
				temp.setSUB_ALLEY(String.valueOf(objTemp.get("siteLine29")));
				temp.setDOOR_NO(String.valueOf(objTemp.get("siteLine30")));
				temp.setDOOR_NO_EX(String.valueOf(objTemp.get("siteLine31")));
				temp.setFLOOR(String.valueOf(objTemp.get("siteLine32")));
				temp.setFLOOR_EX(String.valueOf(objTemp.get("siteLine33")));
				temp.setROOM(String.valueOf(objTemp.get("siteLine34")));
				temp.setADD_OTHER(String.valueOf(objTemp.get("siteLine35")));
				tempList.add(temp);
			}
			
			// ========================================TB_SITE_SR_ANT_CFG_TEMP
			// End			
			searchRecordService.saveNewSearchRecord(siteRecord, record, tempList, shareSearchList);
		}catch (NomsException nomsException){
			log.error(nomsException.getMessage());
			nomsException.printStackTrace();
			return false;
		}catch (JSONException e) {
			e.printStackTrace();
		}		
		return true;
	}	

	/**
	 * 探勘儲存
	 */
	@RequestMapping(value = "saveSiteSearch")
	@ResponseBody
	public boolean assignOrder(
			Map<String, Object> map,TbSiteSrSiteTemp siteTemp,
			// @RequestBody SiteSearchDTO siteSearch, HttpServletRequest
			// request,
			@RequestParam("srId") String srId, @RequestParam("srSeq") String srSeq, @RequestParam("srPsn") String srPsn,
			@RequestParam("srDate") Date srDate, @RequestParam("srEval") String srEval, @RequestParam("address") String address,
			@RequestParam("siteLon") String siteLon, @RequestParam("siteLat") String siteLat, @RequestParam("baseType") String baseType,
			@RequestParam("height") String height, @RequestParam("floor") String floor, @RequestParam("inBuilding") String inBuilding,
			@RequestParam("isFreeEnter") String isFreeEnter, @RequestParam("isKey") String isKey, @RequestParam("isInDoor") String isInDoor,
			@RequestParam("needMiscLic") String needMiscLic, @RequestParam("hasMiscLic") String hasMiscLic,
			@RequestParam("lineReachable") String lineReachable, @RequestParam("expectRent") String expectRent,
			@RequestParam("shareType") String shareType, @RequestParam("antStand") String antStand, @RequestParam("antSHight") String antSHight,
			@RequestParam("antCHight") String antCHight, @RequestParam("lookup") String lookup, @RequestParam("spaceRoom") String spaceRoom,
			@RequestParam("spaceRoof") String spaceRoof, @RequestParam("spaceTop") String spaceTop,
			@RequestParam("spacePlatform") String spacePlatform, @RequestParam("spaceLand") String spaceLand,
			@RequestParam("spaceIndoor") String spaceIndoor, @RequestParam("spaceOutdoor") String spaceOutdoor,
			@RequestParam("footAge") String footAge, @RequestParam("empRelation") String empRelation, @RequestParam("locDesc") String locDesc,
			
/*			@RequestParam("siteTempName") String siteTempName, @RequestParam("siteHuntEqpTemp") String siteHuntEqpTemp,
			@RequestParam("siteHuntEqpModelTemp") String siteHuntEqpModelTemp, @RequestParam("feederlessTeam") String feederlessTeam,
			@RequestParam("masterSiteTemp") String masterSiteTemp, @RequestParam("configuration") String configuration,
			@RequestParam("speedUpload") String speedUpload, @RequestParam("speedDownload") String speedDownload,
			@RequestParam("donorBts") String donorBts, @RequestParam("includeRange") String includeRange,
			@RequestParam("coverageInFloor") String coverageInFloor, @RequestParam("power") String power, @RequestParam("cluster") String cluster,
			@RequestParam("rncBts") String rncBts,*/ 
			@RequestParam("exportList") String exportJson, @RequestParam("searchSiteId") String  searchSiteId,
			@RequestParam("qq_zip") String qq_zip,@RequestParam("qq_city") String qq_city,
			@RequestParam("qq_area") String qq_area,@RequestParam("qq_village") String qq_village,
			@RequestParam("qq_adjacent") String qq_adjacent,@RequestParam("qq_road") String qq_road,
			@RequestParam("qq_lane") String qq_lane,@RequestParam("qq_alley") String qq_alley,
			@RequestParam("qq_subAlley") String qq_subAlley,@RequestParam("qq_doorNo") String qq_doorNo,
			@RequestParam("qq_doorNoEx") String qq_doorNoEx,@RequestParam("qq_floor") String qq_floor,
			@RequestParam("qq_floorEx") String qq_floorEx,@RequestParam("qq_room") String qq_room,
			@RequestParam("qq_remark") String qq_remark
			/*			,@RequestParam("coverageIndoor") String coverageIndoor,
			@RequestParam("hidden") String hidden,@RequestParam("noisOnAir") String noisOnAir,@RequestParam("l2Switch") String l2Switch
			,
			@RequestParam("feqId") String feqId,@RequestParam("btsSiteTempId") String btsSiteTempId*/
			) {
		try {
			log.debug("=====SearchRecordSave====");
			map.put("srId", srId);
			map.put("srSeq", srSeq);
			// ========================================Tb_site_Search
			map.put("srPsn", srPsn);
			map.put("srDate", srDate);
			map.put("srEval", srEval);
			map.put("address", address);
			map.put("siteLon", new BigDecimal(siteLon));
			map.put("siteLat", new BigDecimal(siteLat));
			map.put("baseType", baseType);
			if(height == null) {
				map.put("height", null);
			} else {
				map.put("height", new BigDecimal(height));
			}
			map.put("floor", floor);
			map.put("inBuilding", inBuilding);
			map.put("isFreeEnter", isFreeEnter);
			map.put("isKey", isKey);
			map.put("isInDoor", isInDoor);
			map.put("needMiscLic", needMiscLic);
			map.put("hasMiscLic", hasMiscLic);
			map.put("lineReachable", lineReachable);
			if(expectRent == null) {
				map.put("expectRent", null);
			} else {
				map.put("expectRent", new BigDecimal(expectRent));
			}
			map.put("shareType", shareType);
			map.put("antStand", antStand);
			if(antSHight == null) {
				map.put("antSHight", null);
			} else {
				map.put("antSHight", antSHight);
			}
			if(antCHight == null) {
				map.put("antCHight", null);
			} else {
				map.put("antCHight", antCHight);
			}
			map.put("lookup", lookup);
			map.put("spaceRoom", spaceRoom);
			map.put("spaceRoof", spaceRoof);
			map.put("spaceTop", spaceTop);
			map.put("spacePlatform", spacePlatform);
			map.put("spaceLand", spaceLand);
			map.put("spaceIndoor", spaceIndoor);
			map.put("spaceOutdoor", spaceOutdoor);
			if(footAge == null) {
				map.put("footAge", null);
			} else {
				map.put("footAge", new BigDecimal(footAge));
			}
			map.put("empRelation", empRelation);
			map.put("locDesc", locDesc);
			map.put("siteTempName", siteTemp.getSITE_NAME());
			
			map.put("qq_zip", qq_zip); 
			map.put("qq_city", qq_city); 
			map.put("qq_area", qq_area); 
			map.put("qq_village", qq_village); 
			map.put("qq_adjacent", qq_adjacent); 
			map.put("qq_road", qq_road); 
			map.put("qq_alley", qq_alley); 
			map.put("qq_subAlley", qq_subAlley); 
			map.put("qq_doorNo", qq_doorNo); 
			map.put("qq_doorNoEx", qq_doorNoEx); 
			map.put("qq_floor", qq_floor); 
			map.put("qq_floorEx", qq_floorEx); 
			map.put("qq_room", qq_room); 
			map.put("qq_remark", qq_remark); 
			map.put("qq_lane", qq_lane);
			// ========================================Tb_site_Search End

			// ========================================TB_SITE_SR_SITE_TEMP
			String feq = siteTemp.getFEQ_ID().split(",")[0];
			siteTemp.setFEQ_ID(feq);
			siteTemp.setSR_ID(srId);
			siteTemp.setSR_SEQ(srSeq);
			siteTemp.setSITE_ID(searchSiteId);
/*			TbSiteSrSiteTemp record = new TbSiteSrSiteTemp();
			String feq =feqId.split(",")[0];
			record.setSR_ID(srId);
			record.setSR_SEQ(srSeq);
			record.setSITE_ID(searchSiteId);
			record.setFEQ_ID(feq);
			record.setBTS_SITE_ID(btsSiteTempId);
			record.setSITE_NAME(siteTempName);
			record.setEQP_TYPE_ID(siteHuntEqpTemp);
			record.setEQP_MODEL_ID(siteHuntEqpModelTemp);
			record.setFEEDERLESS(feederlessTeam);
			record.setMASTER_SITE(masterSiteTemp);
			record.setBTS_CONFIG(configuration);
			record.setSPEED_UPLOAD(speedUpload);
			record.setSPEED_DOWNLOAD(speedDownload);
			record.setDONOR_SITE(donorBts);
			record.setCOVERAGE_TYPE(includeRange);
			record.setPOWER(power);
			record.setCLUSTER(cluster);
			record.setRNC_BTS(rncBts);
			record.setCOVERAGE_IN_FLOOR(coverageInFloor);
			record.setCOVERAGE_INDOOR(coverageIndoor);
			record.setHIDDEN(hidden);
			record.setNOIS_ON_AIR(noisOnAir);
			record.setL2_SWITCH(l2Switch);*/	
			// 利用 srId & srSeq 更新該筆資料
			TbSiteSrSiteTempExample example = new TbSiteSrSiteTempExample();
			example.createCriteria().andSR_IDEqualTo(srId).andSR_SEQEqualTo(srSeq);
			// ========================================TB_SITE_SR_SITE_TEMP End
			
			//=====共站業者 lookup
			List<TbSiteShareSearchKey> shareSearchList = new ArrayList<TbSiteShareSearchKey>();
			if(StringUtils.isNotBlank(lookup)) {
				String[] shareTemp = lookup.split(",");
				
				for (int i = 0; i < shareTemp.length; i++) {
					TbSiteShareSearchKey shareSearchKey = new TbSiteShareSearchKey();
					shareSearchKey.setSR_ID(srId);
					shareSearchKey.setSR_SEQ(srSeq);
					shareSearchKey.setSHARE_COM(shareTemp[i]);
					shareSearchList.add(shareSearchKey);
				}
			}
			
			TbSiteShareSearchExample exampleShare = new TbSiteShareSearchExample();
			exampleShare.createCriteria().andSR_IDEqualTo(srId).andSR_SEQEqualTo(srSeq);
			//=====

			// ========================================TB_SITE_SR_ANT_CFG_TEMP
			TbSiteSearchExample searchSite = new TbSiteSearchExample();
			searchSite.createCriteria().andSR_IDEqualTo(srId).andSR_SEQEqualTo(srSeq);
			List<SiteSearchDTO> dateSite = searchRecordService.getSiteSearchByExample(searchSite);
			
			List<TbSiteSrAntCfgTemp> tempList = new ArrayList<TbSiteSrAntCfgTemp>();

			JSONArray jsonArray = new JSONArray(exportJson);

			for (int i = 0; i < jsonArray.length(); i++) {
				TbSiteSrAntCfgTemp temp = new TbSiteSrAntCfgTemp();
				JSONObject obj = jsonArray.getJSONObject(i);
				temp.setSR_ID(srId);
				temp.setSR_SEQ(srSeq);
				temp.setSITE_ID(dateSite.get(0).getSITE_ID());
				temp.setANT_NO(String.valueOf(obj.get("siteLine2")));//天線編號
				temp.setCELL_ID(String.valueOf(obj.get("siteLine2")));//cell
				temp.setSEG_SOURCE(String.valueOf(obj.get("siteLine3")));//訊號源
				temp.setP_CODE(String.valueOf(obj.get("siteLine4")));//p-code
				temp.setANT_MODEL(String.valueOf(obj.get("siteLine5")));//天線型號
				if(!obj.get("siteLine6").equals(null) && !obj.get("siteLine6").equals("")) {
					temp.setANT_HIGH(new BigDecimal(String.valueOf(obj.get("siteLine6"))));//安裝高度
				}
				if(!obj.get("siteLine7").equals(null) && !obj.get("siteLine7").equals("")) {
					temp.setANT_ORIENT(new BigDecimal(String.valueOf(obj.get("siteLine7"))));//方向角
				}
				if(!obj.get("siteLine8").equals(null) && !obj.get("siteLine8").equals("")) {
					temp.setE_TILT(new BigDecimal(String.valueOf(obj.get("siteLine8"))));//E_TILT
				}
				if(!obj.get("siteLine9").equals(null) && !obj.get("siteLine9").equals("")) {
					temp.setM_TILT(new BigDecimal(String.valueOf(obj.get("siteLine9"))));//MTILT
				}
				temp.setSETUP_STYLE(String.valueOf(obj.get("siteLine10")));//安裝方式
				if(!obj.get("siteLine11").equals(null) && !obj.get("siteLine11").equals("")) {
					temp.setFLOOR_LEVEL(new BigDecimal(String.valueOf(obj.get("siteLine11"))));//樓高
				}
				if(!obj.get("siteLine12").equals(null) && !obj.get("siteLine12").equals("")) {
					temp.setTOWER_HEIGHT(new BigDecimal(String.valueOf(obj.get("siteLine12"))));//鐵塔	
				}	
				if(!obj.get("siteLine13").equals(null) && !obj.get("siteLine13").equals("")) {
					temp.setPENTHOUSE_HEIGHT(new BigDecimal(String.valueOf(obj.get("siteLine13"))));//屋突
				}
				temp.setADDR(String.valueOf(obj.get("siteLine14"))); //地址
				if(!obj.get("siteLine15").equals(null) && !obj.get("siteLine15").equals("")) {
					temp.setLON(new BigDecimal(String.valueOf(obj.get("siteLine15"))));//經度	
				}
				if(!obj.get("siteLine16").equals(null) && !obj.get("siteLine16").equals("")) {
					temp.setLAT(new BigDecimal(String.valueOf(obj.get("siteLine16"))));//緯度
				}
				temp.setF_CABLE_TYPE(String.valueOf(obj.get("siteLine17")));//Feeder cable type	
				if(!obj.get("siteLine18").equals(null) && !obj.get("siteLine18").equals("")) {
					temp.setF_CABLE_LEN(new BigDecimal(String.valueOf(obj.get("siteLine18"))));//Feeder cable 長度	
				}
				if(!obj.get("siteLine19").equals(null) && !obj.get("siteLine19").equals("")) {
					temp.setJ_CABLE_LEN(new BigDecimal(String.valueOf(obj.get("siteLine19"))));//Jumper cable 長度
				}
				temp.setCOVER_REG(String.valueOf(obj.get("siteLine20")));//覆蓋區域
				temp.setZIP(String.valueOf(obj.get("siteLine21")));//郵遞區號
				temp.setCITY(String.valueOf(obj.get("siteLine22")));//地址-縣市
				temp.setAREA(String.valueOf(obj.get("siteLine23")));//地址-鄉鎮區
				temp.setVILLAGE(String.valueOf(obj.get("siteLine24")));//地址-村里
				temp.setROAD(String.valueOf(obj.get("siteLine25")));//地址-路(街)
				temp.setADJACENT(String.valueOf(obj.get("siteLine26")));//地址-鄰
				temp.setLANE(String.valueOf(obj.get("siteLine27")));//地址-巷
				temp.setALLEY(String.valueOf(obj.get("siteLine28")));//地址-弄
				temp.setSUB_ALLEY(String.valueOf(obj.get("siteLine29")));//地址-衖
				temp.setDOOR_NO(String.valueOf(obj.get("siteLine30")));//地址-門牌
				temp.setDOOR_NO_EX(String.valueOf(obj.get("siteLine31")));//地址-門牌(之)
				temp.setFLOOR(String.valueOf(obj.get("siteLine32")));//地址-樓層
				temp.setFLOOR_EX(String.valueOf(obj.get("siteLine33")));//地址-樓層(之)
				temp.setROOM(String.valueOf(obj.get("siteLine34")));//地址(室)
				temp.setADD_OTHER(String.valueOf(obj.get("siteLine35")));//地址-其他			
				tempList.add(temp);
			}
			
			TbSiteSrAntCfgTempExample exampleAnt = new TbSiteSrAntCfgTempExample();
			exampleAnt.createCriteria().andSR_IDEqualTo(srId).andSR_SEQEqualTo(srSeq);
			// ========================================TB_SITE_SR_ANT_CFG_TEMP
			// End
			searchRecordService.saveSearchRecord(map, siteTemp, example, tempList, exampleAnt, shareSearchList, exampleShare);
		} catch (NomsException nomsException) {
			log.error(nomsException.getMessage());
			nomsException.printStackTrace();
			return false;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return true;
	}
}
