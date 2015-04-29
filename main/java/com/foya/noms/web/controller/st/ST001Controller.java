package com.foya.noms.web.controller.st;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbComEqpModel;
import com.foya.dao.mybatis.model.TbComEqpModelExample;
import com.foya.dao.mybatis.model.TbComEqpType;
import com.foya.dao.mybatis.model.TbComEqpTypeExample;
import com.foya.dao.mybatis.model.TbComSiteFeq;
import com.foya.dao.mybatis.model.TbComSiteFeqExample;
import com.foya.dao.mybatis.model.TbSiteLocation;
import com.foya.dao.mybatis.model.TbSiteMain;
import com.foya.dao.mybatis.model.TbSiteMainExample;
import com.foya.dao.mybatis.model.TbSiteShareExample;
import com.foya.dao.mybatis.model.TbSiteShareKey;
import com.foya.dao.mybatis.model.TbSiteWork;
import com.foya.dao.mybatis.model.TbSiteWorkExample;
import com.foya.exception.CreateFailException;
import com.foya.exception.NomsException;
import com.foya.exception.UpdateFailException;
import com.foya.noms.dto.st.SiteDTO;
import com.foya.noms.dto.st.SiteLocationDTO;
import com.foya.noms.enums.Feederless;
import com.foya.noms.enums.InBuildingType;
import com.foya.noms.enums.IncludeRange;
import com.foya.noms.enums.NSLevel;
import com.foya.noms.enums.RoomType;
import com.foya.noms.enums.SiteStatus;
import com.foya.noms.enums.SiteType;
import com.foya.noms.enums.WorkStatus;
import com.foya.noms.service.common.AddressService;
import com.foya.noms.service.st.ST001Service;
import com.foya.noms.service.st.ST002SP1Service;
import com.foya.noms.service.st.ST002Service;
import com.foya.noms.service.system.LookupService;
import com.foya.noms.service.system.UniqueSeqService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.JqGridData;
import com.foya.noms.util.LabelValueModel;
import com.foya.noms.web.controller.BaseController;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

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
 * <td>站點維護作業</td>
 * <td>Leo Lin</td>
 * </tr>
 * </table>
 * 
 * @author Leo Lin
 * 
 */
@Controller
@RequestMapping(value = "/st/st001")
public class ST001Controller extends BaseController {

	@Autowired
	private ST001Service sT001Service;
	@Autowired
	private AddressService addressService;
	@Autowired
	private LookupService lookupService;
	@Autowired
	private ST002SP1Service sT002SP1Service;
	@Autowired
	private ST002Service sT002Service;
	@Autowired
	private UniqueSeqService uniqueSeqService;

	/**
	 * 初始頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/init")
	public String st001Init(HttpServletRequest request, Map<String, Object> model) {
		model.put("allSiteTypes", SiteType.getLabelValueList());
		model.put("allSiteStatus", SiteStatus.getLabelValueList());
		model.put("allSiteFeq", sT002SP1Service.getSiteFeqs());
		log.debug("current ID is " + getCurrentMenuId());
		return "ajaxPage/st/ST001L";
	}

	/**
	 * 查詢
	 * 
	 * @param locationId
	 * @param btsSiteId
	 * @param locName
	 * @param locType
	 * @param locCity
	 * @param locTown
	 * @return
	 */
	@RequestMapping(value = "/search")
	@ResponseBody
	public JqGridData<SiteLocationDTO> search(@RequestParam("locationId") String locationId,
			@RequestParam("btsSiteId") String btsSiteId, @RequestParam("locName") String locName,
			@RequestParam("locType") String locType, @RequestParam("locCity") String locCity,
			@RequestParam("locTown") String locTown, @RequestParam("siteStatus") String siteStatus,
			@RequestParam("siteFeq") String siteFeq) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("locationId", locationId);
		map.put("btsSiteId", btsSiteId);
		map.put("locName", locName);
		map.put("locType", locType);
		map.put("locCity", locCity);
		map.put("locTown", locTown);
		map.put("siteStatus", siteStatus);
		map.put("siteFeq", siteFeq);
		List<SiteLocationDTO> siteLocationList = sT001Service.getByCondition(map);
		for (SiteLocationDTO siteLocation : siteLocationList) {
			siteLocation.setLocTypeName(SiteType.detectLabel(siteLocation.getLOC_TYPE()));
			log.debug("SiteLocationDTO : " + siteLocation.getLOCATION_ID());
		}
		PageList<SiteLocationDTO> page = (PageList<SiteLocationDTO>) siteLocationList;
		return new JqGridData<>(page.getPaginator().getTotalCount(), siteLocationList);
	}

	/**
	 * 用cityId,townId查詢MaintainArea
	 * 
	 * @param cityId
	 * @param townId
	 * @return
	 */
	@RequestMapping(value = "/search/maintainArea")
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
	@RequestMapping(value = "/search/maintainDept")
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
	@RequestMapping(value = "/search/maintainTeam")
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
	@RequestMapping(value = "/search/maintainUser")
	@ResponseBody
	public Map<String, String> getMaintainUserByDeptId(@RequestParam("deptId") String deptId) {
		Map<String, String> map = sT001Service.getMaintainUserByDeptId(deptId);
		return map;
	}

	/**
	 * 新增頁面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/insertPage")
	public String insertPage(Map<String, Object> model) {
		List<LabelValueModel> siteTypes = SiteType.getLabelValueList("Location");
		model.put("allSiteTypes", siteTypes);
		model.put("allRoomTypes", RoomType.getLabelValueList());
		model.put("allInBuildingTypes", InBuildingType.getLabelValueList());
		model.put("allNSLevels", NSLevel.getLabelValueList());
		model.put("allShareCom", lookupService.getLabelValueList("SHARECOM"));
		model.put("siteLocEvent", "siteLocInsert");
		return "ajaxPage/st/ST001M";
	}

	/**
	 * 修改頁面
	 * 
	 * @param locationId
	 * @return
	 */
	@RequestMapping(value = "/updatePage")
	public String updatePage(@RequestParam("locationId") String locationId,
			@RequestParam("eventType") String eventType, Map<String, Object> model) {
		List<SiteDTO> siteList = sT002SP1Service.getSiteByLocationId(locationId);
		TbSiteShareExample example = new TbSiteShareExample();
		example.createCriteria().andLOCATION_IDEqualTo(locationId);
		String isOnAir = "";
		for (SiteDTO site : siteList) {
			if (SiteStatus.S06.name().equals(site.getSITE_STATUS())) {
				isOnAir = "Y";
			}
			site.setSITE_STATUS(SiteStatus.detectLabel(site.getSITE_STATUS()));
			site.setCOVERAGE_TYPE(IncludeRange.detectLabel(site.getCOVERAGE_TYPE()));
			site.setFEEDERLESS(Feederless.detectLabel(site.getFEEDERLESS()));
			// if("Y".equals(site.getNOIS_ON_AIR())){
			// isOnAir = "Y";
			// }

		}
		String shareCom = "";
		for (TbSiteShareKey share : sT001Service.getSiteShareByConditions(example)) {
			shareCom += share.getSHARE_COM() + ",";
		}
		model.put("isOnAir", isOnAir);
		model.put("siteLocation", sT001Service.getSiteLocationByLocationId(locationId));
		model.put("allSiteTypes", SiteType.getLabelValueList());
		model.put("allRoomTypes", RoomType.getLabelValueList());
		model.put("allInBuildingTypes", InBuildingType.getLabelValueList());
		model.put("allNSLevels", NSLevel.getLabelValueList());
		model.put("allShareCom", lookupService.getLabelValueList("SHARECOM"));
		model.put("siteLocEvent", "siteLocUpdate");
		model.put("eventType", eventType);
		model.put("siteList", siteList);
		// model.put("siteShareList", siteShareDao.findByConditions(example));
		model.put("shareCom", shareCom);
		model.put("isMultibandLocation", siteList.size());
		return "ajaxPage/st/ST001M";
	}

	/**
	 * 儲存SiteLocation
	 * 
	 * @param siteLocation
	 * @param shareComArray
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(TbSiteLocation siteLocation,
			@RequestParam("shareComArray") String[] shareComArray,
			@RequestParam("siteLocEvent") String siteLocEvent) {

		Map<String, Object> map = new HashMap<String, Object>();
		log.debug("shareComArray : " + shareComArray.toString());
		for (String shareCom : shareComArray) {
			log.debug("shareCom : " + shareCom);
		}
		// siteLocation.setSHARE_COM(shareComArray);
		try {
			if (StringUtils.equals("siteLocInsert", siteLocEvent)) {
				map.put("location", sT001Service.insert(siteLocation, shareComArray, this
						.getLoginUser().getUsername()));
			} else {
				map.put("location", sT001Service.update(siteLocation, shareComArray));
			}
			map.put("result", true);
		} catch (UpdateFailException ex) {
			map.put("result", this.getMessageDetail(ex.getErrCode()));
			log.error(ExceptionUtils.getFullStackTrace(ex));
		} catch (CreateFailException e) {
			map.put("result", this.getMessageDetail(e.getErrCode()));
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		map.put("siteLocEvent", siteLocEvent);
		return map;
	}

	/**
	 * 建立基站頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/addSitePage")
	public String addSitePage(HttpServletRequest request, Map<String, Object> model,
			@RequestParam("siteType") String siteType, @RequestParam("feqId") String feqId) {
		if (SiteType.A.name().endsWith(siteType)) {
			model.put("eqpTypeList", sT002SP1Service.getEqpTypes());

		} else {
			List<String> eqpTypeIdList = new ArrayList<String>();
			eqpTypeIdList.add("3");
			eqpTypeIdList.add("4");
			TbComEqpTypeExample example = new TbComEqpTypeExample();
			example.createCriteria().andEQP_TYPE_IDIn(eqpTypeIdList);
			List<TbComEqpType> eqpTypes = sT002SP1Service.getEqpTypesByExample(example);
			List<LabelValueModel> labelValuemodelList = new ArrayList<LabelValueModel>();
			for (TbComEqpType eqpType : eqpTypes) {
				labelValuemodelList.add(LabelValueModel.getLabelValueModel(eqpType.getEQP_NAME(),
						eqpType.getEQP_TYPE_ID()));
			}
			model.put("eqpTypeList", labelValuemodelList);
		}
		List<TbComSiteFeq> siteFeqList = sT002SP1Service
				.getSiteFeqsByExample(new TbComSiteFeqExample());
		if (feqId != null && feqId != "") {
			String[] feqIdArray = feqId.split(",");
			Iterator<TbComSiteFeq> siteFeqIt = siteFeqList.iterator();
			while (siteFeqIt.hasNext()) {
				TbComSiteFeq siteFeq = siteFeqIt.next();
				for (String feq : feqIdArray) {
					if (siteFeq.getFEQ_ID().equals(feq)) {
						siteFeqIt.remove();
						break;
					}
				}
			}
		}
		Map<String, LabelValueModel> map = new HashMap<String, LabelValueModel>();
		for (TbComSiteFeq siteFeq : siteFeqList) {
			LabelValueModel labelValue = LabelValueModel.getLabelValueModel(siteFeq.getFEQ_NAME(),
					siteFeq.getFEQ_TYPE());
			map.put(siteFeq.getFEQ_ID(), labelValue);
		}
		model.put("siteFeqList", map);
		model.put("targetFrameId", request.getParameter("targetFrameId"));
		return "ajaxPage/st/AddSite";
	}

	/**
	 * 查詢設備機型
	 * 
	 * @param eqpTypeId
	 * @return
	 */
	@RequestMapping(value = "/search/eqpModel")
	@ResponseBody
	public Map<String, String> getEqpModel(@RequestParam("eqpTypeId") String eqpTypeId) {
		Map<String, String> map = new HashMap<String, String>();
		if (StringUtils.isNotEmpty(eqpTypeId)) {
			TbComEqpModelExample example = new TbComEqpModelExample();
			example.createCriteria().andEQP_TYPE_IDEqualTo(eqpTypeId);
			List<TbComEqpModel> eqpModelList = sT002Service.getEqpModelList(example);
			for (TbComEqpModel eqpModel : eqpModelList) {
				map.put(eqpModel.getEQP_MODEL_ID(), eqpModel.getEQP_MODEL());
			}
		}
		return map;
	}

	/**
	 * 儲存新建立的基站
	 * 
	 * @param siteMain
	 * @param locationId
	 * @param city
	 * @param area
	 * @return
	 */
	@RequestMapping(value = "/addSite")
	@ResponseBody
	public Map<String, Object> addSite(TbSiteMain siteMain,
			@RequestParam("locationId") String locationId, @RequestParam("city") String city,
			@RequestParam("area") String area, @RequestParam("coverageType") String coverageType) {
		Map<String, Object> map = new HashMap<String, Object>();
		String[] siteFeq = siteMain.getFEQ_ID().split(",");
		siteMain.setFEQ_ID(siteFeq[0]);
		siteMain.setLOCATION_ID(locationId);
		siteMain.setCOVERAGE_TYPE(coverageType);
		// if ("L".equals(siteFeq[1]) && "2".equals(siteMain.getEQP_TYPE_ID()))
		// {
		// TbComTownExample example = new TbComTownExample();
		// example.createCriteria().andCITY_IDEqualTo(city).andTOWN_IDEqualTo(area);
		// List<TbComTown> town = sT002Service.getTownByCondition(example);
		// siteMain.setBTS_SITE_ID(uniqueSeqService.getNextNodeB4G(town.get(0).getZIP_CODE()));
		// }
		String errorMessage = sT001Service.doValidate(siteMain.getEQP_TYPE_ID(), siteFeq[1],
				siteMain.getBTS_SITE_ID(), city, area, coverageType);
		if (errorMessage.isEmpty()) {
			try {
				sT001Service.insertSiteMain(siteMain, this.getLoginUser().getUsername());
				map.put("result", true);
			} catch (CreateFailException ex) {
				map.put("result", this.getMessageDetail(ex.getMessage()));
			}
		} else {
			map.put("result", errorMessage);
		}
		return map;
	}

	/**
	 * 刪除基站
	 * 
	 * @param siteId
	 * @return
	 */
	@RequestMapping(value = "/deleteSite")
	@ResponseBody
	public BaseJasonObject<String> deleteSite(String siteId) {
		String resultMsg = this.getMessageDetail("msg.delete.success");
		TbSiteWorkExample example = new TbSiteWorkExample();
		example.createCriteria().andSITE_IDEqualTo(siteId)
				.andWORK_STATUSNotEqualTo(WorkStatus.W08.name());
		List<TbSiteWork> workList = sT001Service.getSiteWorkByConditions(example);

		List<String> engineRoomEqpTypeIds = new ArrayList<String>();
		engineRoomEqpTypeIds.add(SiteType.C.name());
		engineRoomEqpTypeIds.add(SiteType.M.name());
		engineRoomEqpTypeIds.add(SiteType.H.name());
		TbSiteMainExample siteMainExample = new TbSiteMainExample();
		siteMainExample.createCriteria().andSITE_IDEqualTo(siteId)
				.andEQP_TYPE_IDNotIn(engineRoomEqpTypeIds).andSITE_STATUSEqualTo(SiteStatus.S04.name());
		List<TbSiteMain> siteMainList = sT001Service.getSiteMainByConditions(siteMainExample);
		if (workList.size() == 0 && siteMainList.size() > 0) {
			try {
//				sT001Service.deleteSiteMain(siteId);
				sT001Service.updateSiteStatusBySiteId(siteId, SiteStatus.S11);
			} catch (NomsException e) {
				resultMsg = this.getMessageDetail("msg.delete.fail");
			}
		} else {
				resultMsg = "此基站已經被使用無法刪除";
		}
		return new BaseJasonObject<String>(true, resultMsg);
	}
}
