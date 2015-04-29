package com.foya.noms.web.controller.st;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import com.foya.dao.mybatis.model.TbSiteLocation;
import com.foya.dao.mybatis.model.TbSiteMain;
import com.foya.exception.CreateFailException;
import com.foya.exception.UpdateFailException;
import com.foya.noms.dao.st.LocationDao;
import com.foya.noms.dao.st.SiteMainDao;
import com.foya.noms.dto.st.SiteDTO;
import com.foya.noms.dto.st.SiteLocationDTO;
import com.foya.noms.enums.InBuildingType;
import com.foya.noms.enums.NSLevel;
import com.foya.noms.enums.RoomType;
import com.foya.noms.enums.SiteStatus;
import com.foya.noms.enums.SiteType;
import com.foya.noms.service.common.AddressService;
import com.foya.noms.service.st.EngineRoomService;
import com.foya.noms.util.JqGridData;
import com.foya.noms.util.LabelValueModel;
import com.foya.noms.web.controller.BaseController;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Controller
@RequestMapping(value = "/st/engineRoom")
public class EngineRoomController extends BaseController {
	@Autowired
	private EngineRoomService engineRoomService;
	@Autowired
	private AddressService addressService;
	@Autowired
	private LocationDao locationDao;
	@Autowired
	private SiteMainDao siteMainDao;
	/**
	 * 初始頁面
	 * 
	 * @param request
	 * @param model
	 * @param site
	 * @return
	 */
	@RequestMapping(value = "/init")
	public String initEngineRoomPage(HttpServletRequest request, Map<String, Object> model,
			SiteDTO site) {
		model.put("allSiteStatus", SiteStatus.getLabelValueList("SB"));
		model.put("allSiteType", SiteType.getLabelValueList("EngineRoom"));
		return "/ajaxPage/st/engine/EngineRoomL";
	}

	/**
	 * 查詢
	 * 
	 * @param btsSiteId
	 * @param siteName
	 * @param feqName
	 * @param eqpName
	 * @param siteCity
	 * @param siteTown
	 * @return
	 */
	@RequestMapping(value = "/search")
	@ResponseBody
	public JqGridData<SiteLocationDTO> search(@RequestParam("btsSiteId") String btsSiteId,
			@RequestParam("engineRoomStatus") String engineRoomStatus,
			@RequestParam("engineRoomName") String engineRoomName,
			@RequestParam("engineRoomType") String engineRoomType,
			@RequestParam("engineRoomCity") String engineRoomCity,
			@RequestParam("engineRoomArea") String engineRoomArea) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("btsSiteId", btsSiteId);
		map.put("engineRoomStatus", engineRoomStatus);
		map.put("engineRoomName", engineRoomName);
		map.put("engineRoomType", engineRoomType);
		map.put("engineRoomCity", engineRoomCity);
		map.put("engineRoomArea", engineRoomArea);
		List<SiteLocationDTO> engineRoomList = engineRoomService.getByCondition(map);
		for (SiteLocationDTO engineRoom : engineRoomList) {
			engineRoom.setSiteStatus(SiteStatus.detectLabel(engineRoom.getSiteStatus()));
			engineRoom.setLOC_TYPE(SiteType.detectLabel(engineRoom.getLOC_TYPE()));
		}
		
		PageList<SiteLocationDTO> page = (PageList<SiteLocationDTO>) engineRoomList;
		return new JqGridData<>(page.getPaginator().getTotalCount(), engineRoomList);
	}
	
	/**
	 * 新增頁面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/insertPage")
	public String insertEngineRoomPage(HttpServletRequest request, Map<String, Object> model) {
		model.put("allSiteTypes", SiteType.getLabelValueList("EngineRoom"));
		model.put("allRoomTypes", RoomType.getLabelValueList());
		model.put("allInBuildingTypes", InBuildingType.getLabelValueList());
		model.put("allNSLevels", NSLevel.getLabelValueList());
		model.put("allSiteStatus", engineRoomStatus());
		model.put("engineRoomEvent", "engineRoomInsert");
		return "/ajaxPage/st/engine/EngineRoomM";
	}

	/**
	 * 修改頁面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/updatePage")
	public String updateEngineRoomPage(HttpServletRequest request, Map<String, Object> model,
			@RequestParam("locationId")String locationId,@RequestParam("siteId")String siteId,
			@RequestParam("eventType")String eventType) {
		TbSiteLocation location = locationDao.findByPk(locationId);
		TbSiteMain site = siteMainDao.findByPk(siteId);
		model.put("location",location);
		model.put("site",site);
		model.put("allSiteTypes", SiteType.getLabelValueList("EngineRoom"));
		model.put("allRoomTypes", RoomType.getLabelValueList());
		model.put("allInBuildingTypes", InBuildingType.getLabelValueList());
		model.put("allNSLevels", NSLevel.getLabelValueList());
		model.put("siteStatusName", SiteStatus.detectLabel(site.getSITE_STATUS()));
		model.put("engineRoomEvent", "engineRoomUpdate");
		model.put("eventType",eventType);
		model.put("allSiteStatus", engineRoomStatus());
		return "/ajaxPage/st/engine/EngineRoomM";
	}
	
	/**
	 * 儲存
	 * 
	 * @param engineRoom
	 * @param shareComArray
	 * @param engineRoomEvent
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(
			@RequestParam("engineRoomEvent") String engineRoomEvent,
			SiteLocationDTO engineRoom) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.equals("engineRoomInsert", engineRoomEvent)) {
				map.put("engineRoom",
						engineRoomService.insert(engineRoom, this.getLoginUser().getUsername()));
			}else{
				Date onAirDate = siteMainDao.findByPk(engineRoom.getSiteId()).getONAIR_DATE();
				engineRoom.setOnAirDate(onAirDate);
				engineRoomService.update(engineRoom, this.getLoginUser().getUsername());
			}
			map.put("result", true);
		} catch (UpdateFailException ex) {
			map.put("result", this.getMessageDetail(ex.getErrCode()));
			log.error(ExceptionUtils.getFullStackTrace(ex));
		} catch (CreateFailException e) {
			map.put("result", this.getMessageDetail(e.getErrCode()));
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		map.put("engineRoomEvent", engineRoomEvent);
		return map;
	}
	
	
	public static List<LabelValueModel> engineRoomStatus(){
		List<LabelValueModel> statusList = new ArrayList<LabelValueModel>();
		for(LabelValueModel status : SiteStatus.getLabelValueList("SB")){
			if(SiteStatus.S06.name().equals(status.getValue()) || SiteStatus.S08.name().equals(status.getValue())){
				statusList.add(status);
			}
		}
		return statusList;
	}
}
