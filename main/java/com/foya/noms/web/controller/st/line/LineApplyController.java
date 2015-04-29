package com.foya.noms.web.controller.st.line;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hornetq.utils.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.foya.dao.mybatis.model.TbAuthPersonnelExample;
import com.foya.dao.mybatis.model.TbSiteLineApply;
import com.foya.dao.mybatis.model.TbSiteLineSiteData;
import com.foya.dao.mybatis.model.TbSiteLocation;
import com.foya.dao.mybatis.model.TbSiteMain;
import com.foya.dao.mybatis.model.TbSiteWork;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.exception.NomsException;
import com.foya.exception.UpdateFailException;
import com.foya.noms.dto.st.SiteLineSiteDataDTO;
import com.foya.noms.dto.st.SiteLocationDTO;
import com.foya.noms.dto.st.line.SiteLineApplyDTO;
import com.foya.noms.enums.LineApplyStatus;
import com.foya.noms.enums.LineStatus;
import com.foya.noms.enums.WorkType;
import com.foya.noms.resources.AppConstants;
import com.foya.noms.service.auth.PersonnelService;
import com.foya.noms.service.st.ST005Service;
import com.foya.noms.service.st.line.LineApplyService;
import com.foya.noms.service.system.LookupService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.JqGridData;
import com.foya.noms.util.LabelValueModel;
import com.foya.noms.web.controller.BaseController;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Controller
@RequestMapping("/st/line/lineApply")
public class LineApplyController extends BaseController {

	@Autowired
	private LineApplyService lineApplyService;

	@Autowired
	private LookupService lookupService;

	@Autowired
	private PersonnelService personnelService;

	@Autowired
	private ST005Service sT005Service;

	@RequestMapping("/init")
	@ResponseBody
	public BaseJasonObject<Map<String, Object>> initLineApplyMPage(Map<String, Object> model) {
		model.put("lineUseTypeList",
				lineApplyService.findSysLookupByType(AppConstants.LINE_USE_TYPE));
		model.put("lineApplyTypeList",
				lineApplyService.findSysLookupByType(AppConstants.LINE_APPLY_TYPE));
		model.put("sharecomList", lineApplyService.findSysLookupByType(AppConstants.LINE_ISP));
		model.put("linePurposeList",
				lineApplyService.findSysLookupByType(AppConstants.LINE_PURPOSE));
		model.put("lineTypeList", lineApplyService.findSysLookupByType(AppConstants.LINE_TYPE));
		return new BaseJasonObject<>(model, AJAX_SUCCESS);
	}

	/**
	 * 查詢專線
	 * 
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/searchLineApply")
	@ResponseBody
	public BaseJasonObject<SiteLineApplyDTO> searchLineApply(String orderId) {
		return new BaseJasonObject<>(lineApplyService.selectSiteLineApplyDTOByOrderId(orderId),
				AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 依電路類取得速率
	 * 
	 * @param lineType
	 * @return
	 */
	@RequestMapping("/getLineSpeedList")
	@ResponseBody
	public BaseJasonObject<List<TbSysLookup>> getLineSpeedList(String lineType) {

		String endStr = StringUtils.upperCase(lineType);

		List<TbSysLookup> sysLookList = null;
		switch (StringUtils.trimToEmpty(endStr)) {
		case "ADSL":
			sysLookList = lineApplyService.findSysLookupByType(AppConstants.LINE_TYPE_ADSL);
			break;
		case "SDH":
			sysLookList = lineApplyService.findSysLookupByType(AppConstants.LINE_TYPE_SDH);
			break;
		case "VPN":
			sysLookList = lineApplyService.findSysLookupByType(AppConstants.LINE_TYPE_VPN);
			break;
		case "FIB":
			sysLookList = lineApplyService.findSysLookupByType(AppConstants.LINE_TYPE_FIB);
			break;
		case "WAVE":
			sysLookList = lineApplyService.findSysLookupByType(AppConstants.LINE_TYPE_WAVE);
			break;
		case "ELL":
			sysLookList = lineApplyService.findSysLookupByType(AppConstants.LINE_TYPE_ELL);
			break;
		default:
			sysLookList = new ArrayList<TbSysLookup>();
		}

		return new BaseJasonObject<List<TbSysLookup>>(sysLookList, AJAX_SUCCESS);
	}

	/**
	 * 新增(取得相關初始資料)
	 * 
	 * @param orderId
	 * @param model
	 * @return
	 */
	@RequestMapping("/insert")
	@ResponseBody
	public BaseJasonObject<Map<String, Object>> insert(String orderId, Map<String, Object> model) {

		model.put("appStatusName", LineApplyStatus.LA01.getLocalName());
		model.put("appStatus", LineApplyStatus.LA01);

		model.put("lineStatusName", LineStatus.L01.getLocalName());
		model.put("lineStatus", LineStatus.L01);

		model.put("deptName", getLoginUser().getDeptName()); // 申請單位名稱
		model.put("deptId", getLoginUser().getHrDeptId()); // 申請單位ID

		model.put("chName", getLoginUser().getChName()); // 申請人
		model.put("userName", getLoginUser().getUsername()); // 申請人ID

		return new BaseJasonObject<>(model, AJAX_SUCCESS);
	}

	/**
	 * 專線申請存檔
	 * 
	 * @param orderId
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public BaseJasonObject<String> save(TbSiteLineApply tSiteLineApply, String jsonArrStr) {
		String username = getLoginUser().getUsername();
		try {
			lineApplyService.insertLineApply(jsonArrStr, tSiteLineApply, username, new Date());
		} catch (JSONException je) {
			log.error(je.getMessage(), je);
			return new BaseJasonObject<>(false, "VLAN資料錯誤！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new BaseJasonObject<>(false, "儲存資料失敗！");
		}

		return new BaseJasonObject<>(true, "儲存資料成功！");
	}

	/**
	 * 取站台、站點資料
	 * 
	 * @param locationId
	 * @param model
	 * @return
	 */
	@RequestMapping("/getSiteLocationData")
	@ResponseBody
	public BaseJasonObject<Map<String, Object>> getSiteLocationData(String appId,
			String locationId, String ab, Map<String, Object> model) {

		if (StringUtils.isEmpty(locationId)) {
			log.info("Location id is NULL................");
			return new BaseJasonObject<>(model, AJAX_FAILED);
		}

		TbSiteLineApply tbSiteLineApply = lineApplyService.findTbSiteLineApplyByPrimaryKey(appId);

		String siteNum = "0"; // 甲乙端線數
		if ("a".equals(StringUtils.trimToEmpty(ab))) {
			if (tbSiteLineApply == null) {
				siteNum = lineApplyService.selectTbSiteLineApplyByALocAndLineStatus(locationId,
						LineStatus.L01.name()); // 甲端線數
			} else {
				siteNum = lineApplyService.selectTbSiteLineApplyByALocAndLineStatus(locationId,
						tbSiteLineApply.getLINE_STATUS()); // 甲端線數
			}

		} else {
			if (tbSiteLineApply == null) {
				siteNum = lineApplyService.selectTbSiteLineApplyByBLocAndLineStatus(locationId,
						LineStatus.L01.name()); // 乙端線數
			} else {
				siteNum = lineApplyService.selectTbSiteLineApplyByBLocAndLineStatus(locationId,
						tbSiteLineApply.getLINE_STATUS()); // 乙端線數
			}
		}

		String sameLineNum = "0"; // 甲乙端相同線數
		if (tbSiteLineApply != null) {
			sameLineNum = lineApplyService.selectTbSiteLineApplyByLocAndLineStatus(
					tbSiteLineApply.getA_LOC(), tbSiteLineApply.getB_LOC(),
					tbSiteLineApply.getLINE_STATUS()); // 甲乙端相同線數
		}

		List<TbSiteMain> tbSiteMainList = lineApplyService.findTbSiteMainByLocationId(locationId);
		SiteLocationDTO siteLocationDto = lineApplyService.findTbSiteLocationByPrimaryKey(locationId);
		model.put("siteLocationDto", siteLocationDto);

		String btsSiteIds[] = new String[tbSiteMainList.size()];
		TbSiteLineSiteData tbSiteLineSiteDatas[] = new TbSiteLineSiteData[tbSiteMainList.size()];

		List<LabelValueModel> meterialList = new ArrayList<LabelValueModel>();
		int i = 0;
		for (TbSiteMain tbSiteMain : tbSiteMainList) {
			btsSiteIds[i] = tbSiteMain.getBTS_SITE_ID();
			tbSiteLineSiteDatas[i] = lineApplyService.findTbSiteLineSiteDataByPrimaryKey(appId,
					tbSiteMain.getSITE_ID());
				meterialList.addAll(lineApplyService.selectRemItemQuery(tbSiteMain.getSITE_ID()));
			i++;
		}

		model.put("meterialList", meterialList); // 甲/乙端設備
		model.put("tbSiteMainList", tbSiteMainList);
		model.put("siteNum", siteNum); // 甲/乙端線數
		model.put("sameLineNum", sameLineNum); // 甲乙端相同線數
		model.put("btsSiteIds", btsSiteIds);
		model.put("tbSiteLineSiteDatas", tbSiteLineSiteDatas);

		return new BaseJasonObject<>(model, AJAX_SUCCESS);
	}

	/**
	 * 專線申請-申請
	 * 
	 * @param tSiteLineApply
	 * @param jsonArrStr
	 * @return
	 */
	@RequestMapping("/apply")
	@ResponseBody
	public BaseJasonObject<Object> apply(TbSiteLineApply tSiteLineApply, String jsonArrStr) {
		String username = getLoginUser().getUsername();

		try {
			lineApplyService.apply(jsonArrStr, tSiteLineApply, username, new Date());
		} catch (JSONException je) {
			log.info("專線申請 - 申請錯誤....VLAN資料錯誤！");
			log.error(je.getMessage(), je);
			return new BaseJasonObject<>(false, "VLAN資料錯誤！");
		} catch (Exception e) {
			log.info("專線申請 - 申請錯誤....專線申請失敗！");
			log.error(e.getMessage(), e);
			return new BaseJasonObject<>(false, e.getMessage() + "\n專線申請失敗！");
		}

		return new BaseJasonObject<>(true, "專線申請成功！");
	}

	/**
	 * 專線申請-核網申請(包含mail通知)
	 * 
	 * @param appId
	 * @return
	 */
	@RequestMapping("/setupNetwork")
	@ResponseBody
	public BaseJasonObject<Object> setupNetwork(@RequestParam("appId") String appId) {
		try {
			lineApplyService.setupLineNetwork(appId);
		} catch (NomsException ne) {
			log.error(ne.getMessage(), ne);
			return new BaseJasonObject<>(false, "操作單號：" + appId + " 核網設定失敗！");
		}
		return new BaseJasonObject<>(true, "操作單號：" + appId + " 核網設定完成！");
	}

	/**
	 * 專線申請取消(包含mail通知)
	 * 
	 * @param appId
	 * @return
	 */
	@RequestMapping("/cancelApply")
	@ResponseBody
	public BaseJasonObject<Object> lineCancelMail(@RequestParam("appId") String appId) {
		try {
			lineApplyService.cancelLineApply(appId);
		} catch (NomsException ne) {
			log.error(ne.getMessage(), ne);
			return new BaseJasonObject<>(false, "申請單號: " + appId + " 取消申請失敗！");
		}
		return new BaseJasonObject<>(true, "申請單號: " + appId + " 取消申請完成！");
	}

	/**
	 * 專線申請 -竣工
	 * 
	 * @param appId
	 * @return
	 */
	@RequestMapping("/complete")
	@ResponseBody
	public BaseJasonObject<Object> complete(@RequestParam("appId") String appId) {
		try {
			lineApplyService.completeLine(appId);
		} catch (NomsException ne) {
			log.error(ne.getMessage(), ne);
			return new BaseJasonObject<>(false, "申請單號: " + appId + " 取消申請失敗！");
		}
		return new BaseJasonObject<>(true, "申請單號: " + appId + " 竣工！");
	}

	/**
	 * 專線查詢(初始頁)
	 * 
	 * @param model
	 * @param callBackFun
	 * @param targetFrameId
	 * @param currentMenuId
	 * @return
	 */
	@RequestMapping("/searchLine")
	public String searchLine(Map<String, Object> model, String callBackFun, String targetFrameId,
			String currentMenuId) {

		List<String> list = new ArrayList<String>();

		// 取得施工單位選項
		Set<String> tempDeptList = getLoginUser().getAccessDeptListByMenuID(
				Integer.valueOf(currentMenuId));

		if (tempDeptList != null) {
			list.addAll(tempDeptList);
		}

		List<LabelValueModel> deptList = sT005Service.getDeptAll(list);
		model.put("allRepDept", deptList);
		model.put("lineStatusList", LineStatus.getLabelValueList());
		model.put("targetFrameId", targetFrameId);
		model.put("callBackFun", callBackFun);

		return "/ajaxPage/st/line/LineQuery";
	}

	/**
	 * 專線查詢
	 * 
	 * @param request
	 * @param lineId
	 * @param lineStatus
	 * @param aBtsSiteId
	 * @param bBtsSiteId
	 * @param applyDept
	 * @return
	 */
	@RequestMapping("/searchApplyLineData")
	@ResponseBody
	public JqGridData<SiteLineApplyDTO> searchApplyLineData(HttpServletRequest request,
			String lineId, String lineStatus, String aBtsSiteId, String bBtsSiteId, String applyDept) {

		Map<String, String> map = new HashMap<String, String>();
		map.put("lineId", lineId);
		map.put("lineStatus", lineStatus);
		map.put("aBtsSiteId", aBtsSiteId);
		map.put("bBtsSiteId", bBtsSiteId);
		map.put("appDept", applyDept);

		PageList<SiteLineApplyDTO> siteLineApplyDTOList = lineApplyService.findBySelectLine(map);
		return new JqGridData<SiteLineApplyDTO>(
				siteLineApplyDTOList.getPaginator().getTotalCount(), siteLineApplyDTOList);

	}

	/**
	 * 專線申請-匯出excel
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @param appId
	 * @return
	 */
	@RequestMapping(value = "/exportLineApplyExcel", method = RequestMethod.GET)
	public ModelAndView exportLineApplyExcel(ModelMap model, HttpServletRequest request,
			HttpServletResponse response, String appId) {
		ViewExcel viewExcel = new ViewExcel();
		HSSFWorkbook workbook = lineApplyService.generateWorkbook("sheet", appId);
		try {
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=lineApplyID-" + appId
					+ ".xls");
			OutputStream ouputStream = response.getOutputStream();
			workbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();

		} catch (Exception e) {
			log.error(e.getLocalizedMessage(), e);
		}
		return new ModelAndView(viewExcel, model);
	}

	/**
	 * 查詢lineId
	 * 
	 * @param poNo
	 * @return
	 */
	@RequestMapping(value = "/search/lineInfoTable")
	@ResponseBody
	public BaseJasonObject<TbSiteLineApply> getLineInfoTable(@RequestParam("siteId") String siteId) {
		List<TbSiteLineApply> lineApplyList = lineApplyService.getLinDatasBySiteId(siteId);
		return new BaseJasonObject<>(lineApplyList, AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 初始頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/initLineApply")
	public String st004Init(HttpServletRequest request, Map<String, Object> model) {
		Set<String> set = getLoginUser().getAccessDeptListByMenuID(
				Integer.valueOf(request.getParameter("menuId")));
		List<String> list = new ArrayList<String>();
		if (set != null) {
			list = new ArrayList<String>(set);
		}

		// 申請單位
		List<SiteLineApplyDTO> siteWorkList = lineApplyService.getLineApplyListByDeptId(list);
		model.put("applyDeptList", siteWorkList);

		// 申請類別
		model.put("appleTypeList", lookupService.getLabelValueList("LINE_APPLY_TYPE"));

		// 申請狀態
		model.put("appleStateList", LineApplyStatus.getLabelValueList());

		// 專線狀態
		model.put("lineTypeList", LineStatus.getLabelValueList());

		// 業者別
		model.put("allWorkType", lookupService.getLabelValueList(AppConstants.LINE_ISP));

		return "ajaxPage/st/line/LineApplyL";
	}

	/**
	 * 查詢
	 * 
	 * @return
	 */
	@RequestMapping(value = "/search")
	@ResponseBody
	public JqGridData<SiteLineApplyDTO> search(@RequestParam("workId") String workId,
			@RequestParam("applicationId") String applicationId,
			@RequestParam("baseStationId") String baseStationId,
			@RequestParam("btsSiteName") String btsSiteName, @RequestParam("lineId") String lineId,
			@RequestParam("applyDept") String applyDept,
			@RequestParam("appleType") String appleType,
			@RequestParam("appleState") String appleState,
			@RequestParam("worksType") String worksType, @RequestParam("lineType") String lineType) {

		Map<String, String> map = new HashMap<String, String>();
		map.put("workId", workId);
		map.put("applicationId", applicationId);
		map.put("baseStationId", baseStationId);
		map.put("btsSiteName", btsSiteName);
		map.put("lineId", lineId);
		map.put("applyDept", applyDept);
		map.put("appleType", appleType);
		map.put("appleState", appleState);
		map.put("worksType", worksType);
		map.put("lineType", lineType);

		List<SiteLineApplyDTO> siteLineList = lineApplyService.getByConditions(map);

		// 電路用途
		List<LabelValueModel> circuitList = lookupService
				.getLabelValueList(AppConstants.LINE_PURPOSE);

		Map<String, List<TbSiteMain>> locSiteMainMap = new HashMap<>();

		List<LabelValueModel> worksTypeValues = lookupService
				.getLabelValueList(AppConstants.LINE_ISP);

		for (SiteLineApplyDTO siteLine : siteLineList) {
			// 業者
			for (LabelValueModel worksTypeValue : worksTypeValues) {
				if (StringUtils.equals(worksTypeValue.getValue(), siteLine.getVENDOR())) {
					siteLine.setVENDOR(worksTypeValue.getLabel());
				}
			}

			// 速率
			if (siteLine.getLINE_SPEED() != null && !siteLine.getLINE_SPEED().equals("")) {
				for (LabelValueModel speed : lookupService.getLabelValueList("CIRCUIT_TYPE_"
						+ siteLine.getLINE_TYPE())) {
					if (siteLine.getLINE_SPEED().equals(speed.getValue().replace("SPEED", "SP"))) {
						siteLine.setLINE_SPEED(speed.getLabel());
					}
				}
			}

			// 電路用途
			for (LabelValueModel circuit : circuitList) {
				if (circuit.getValue().equals(siteLine.getLINE_PURPOSE())) {
					siteLine.setLINE_PURPOSE_NAME(circuit.getLabel());
				}
			}
			// 甲端端點
			String siteA = siteLine.getA_LOC();
			if (StringUtils.isNotBlank(siteA)) {
				List<TbSiteMain> siteMains = locSiteMainMap.get(siteA);
				if (siteMains == null) {
					siteMains = lineApplyService.findTbSiteMainByLocationId(siteLine.getA_LOC());
					locSiteMainMap.put(siteA, siteMains);
				}
				siteLine.setA_LOC(getLocationIdString(siteMains));
			}

			// 乙端端點
			String siteB = siteLine.getB_LOC();
			if (StringUtils.isNotBlank(siteB)) {
				List<TbSiteMain> siteMains = locSiteMainMap.get(siteB);
				if (siteMains == null) {
					siteMains = lineApplyService.findTbSiteMainByLocationId(siteLine.getB_LOC());
					locSiteMainMap.put(siteB, siteMains);
				}
				siteLine.setB_LOC(getLocationIdString(siteMains));
			}
		}
		PageList<SiteLineApplyDTO> page = (PageList<SiteLineApplyDTO>) siteLineList;
		return new JqGridData<>(page.getPaginator().getTotalCount(), siteLineList);
	}

	private String getLocationIdString(List<TbSiteMain> siteMains) {
		if (siteMains == null || siteMains.isEmpty()) {
			return "";
		}
		StringBuffer str = new StringBuffer();
		for (TbSiteMain siteMain : siteMains) {
			String btsSiteId = siteMain.getBTS_SITE_ID();
			if (StringUtils.isNotBlank(btsSiteId)) {
				str.append(btsSiteId).append(",");
			}
		}
		if (str.length() > 0) {
			str.deleteCharAt(str.length() - 1);
		}
		return str.toString();
	}

	/**
	 * 新增非工務專線維護
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/insertNonLine")
	public String insertNonLine(HttpServletRequest request, Map<String, Object> model) {
		model.put("showBtn", true);

		// 工作類型
		model.put("applyEvent", "new");

		// 專線狀態
		model.put("lineStatusList", LineStatus.getLabelValueList());

		// 維護類別
		model.put("appleTypeList", lookupService.getLabelValueList(AppConstants.LINE_APPLY_TYPE));

		// 業者別
		model.put("allWorkType", lookupService.getLabelValueList(AppConstants.LINE_ISP));

		// 電路用途
		List<LabelValueModel> List = new ArrayList<LabelValueModel>();
		for (LabelValueModel circuit : lookupService.getLabelValueList(AppConstants.LINE_PURPOSE)) {
			if (circuit.getValue().equals("D") || circuit.getValue().equals("V")
					|| circuit.getValue().equals("S") || circuit.getValue().equals("O")) {
				List.add(circuit);
			}
		}
		model.put("purposeList", List);

		// 電路類別
		model.put("lineTypeList", lookupService.getLabelValueList(AppConstants.LINE_TYPE));

		// 網路種類
		model.put("lineSpeedList", lookupService.getLabelValueList(AppConstants.LINE_TYPE_SDH));

		// 申請人員
		TbAuthPersonnelExample example = new TbAuthPersonnelExample();
		example.createCriteria().andPSN_NOEqualTo(getLoginUser().getUsername());
		model.put("applyUserName", personnelService.getPersonnelsByExample(example).get(0)
				.getCHN_NM());

		model.put("applyUserDeptName", this.getLoginUser().getDeptName());

		return "ajaxPage/st/line/LineApplyM2";
	}

	/**
	 * 修改頁面
	 * 
	 * @param workId
	 * @return
	 */
	@RequestMapping(value = "/updateNonLine")
	public String getLineApplyByAppId(Map<String, Object> model,
			@RequestParam("appId") String appId, @RequestParam("status") String status) {

		// 專線資訊
		SiteLineApplyDTO siteLineApple = lineApplyService.getLineApplyByAppId(appId);
		String appDesc = siteLineApple.getAPP_DESC();
		if (StringUtils.isNotBlank(appDesc)) {
			siteLineApple.setAPP_DESC(siteLineApple.getAPP_DESC().replaceAll("<br>", "\n"));
		}

		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");

		if (siteLineApple.getCNS_DATE() != null) {
			siteLineApple.setLineCnsDate(sdFormat.format(siteLineApple.getCNS_DATE()));
		}
		if (siteLineApple.getSTART_DATE() != null) {
			siteLineApple.setLineStartDate(sdFormat.format(siteLineApple.getSTART_DATE()));
		}
		if (siteLineApple.getRENT_END_DATE() != null) {
			siteLineApple.setLineRentEndDate(sdFormat.format(siteLineApple.getRENT_END_DATE()));
		}
		if (siteLineApple.getEND_DATE() != null) {
			siteLineApple.setLineEndDate(sdFormat.format(siteLineApple.getEND_DATE()));
		}

		//
		model.put("showBtn", true);
		model.put("status", status);
		// 專線狀態
		model.put("lineStatusList", LineStatus.getLabelValueList());

		// 維護類別
		model.put("appleTypeList", lookupService.getLabelValueList(AppConstants.LINE_APPLY_TYPE));

		// 業者別
		model.put("allWorkType", lookupService.getLabelValueList(AppConstants.LINE_ISP));

		// 電路用途
		List<LabelValueModel> List = new ArrayList<LabelValueModel>();
		for (LabelValueModel circuit : lookupService.getLabelValueList(AppConstants.LINE_PURPOSE)) {
			if (circuit.getLabel().equals("辦公室") || circuit.getLabel().equals("門市")
					|| circuit.getLabel().equals("加值") || circuit.getLabel().equals("其他")) {
				List.add(circuit);
			}
		}
		model.put("purposeList", List);

		// 電路類別
		model.put("lineTypeList", lookupService.getLabelValueList(AppConstants.LINE_TYPE));

		//
		model.put("siteLineApple", siteLineApple);

		model.put("applyEvent", "edit");

		// 申請人員
		if (!StringUtils.isEmpty(siteLineApple.getAPP_USER())) {
			TbAuthPersonnelExample example = new TbAuthPersonnelExample();
			example.createCriteria().andPSN_NOEqualTo(siteLineApple.getAPP_USER());
			model.put("applyUserName", personnelService.getPersonnelsByExample(example).get(0)
					.getCHN_NM());
		}

		model.put("applyUserDeptName", this.getLoginUser().getDeptName());

		return "ajaxPage/st/line/LineApplyM2";
	}

	/**
	 * 儲存
	 * 
	 * @param SiteBuildApply
	 * @param siteBuildApplyEvent
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "/saveByNonLine")
	@ResponseBody
	public Map<String, Object> saveByNonLine(SiteLineApplyDTO siteLineApple,
			@RequestParam("applyEvent") String applyEvent) {
		Map<String, Object> map = new HashMap<String, Object>();
		// String errMsg;

		if ("new".equals(applyEvent)) {
			try {
				TbSiteLineApply lineApply = lineApplyService.insertNonLine(siteLineApple);
				map.put("result", true);
				map.put("lineApply", lineApply);
			} catch (Exception ex) {
				log.error("ex.getMessage() : " + ex.getMessage());
				log.error(ExceptionUtils.getFullStackTrace(ex));
				map.put("result", ex.getMessage());
			}

		} else {
			try {
				TbSiteLineApply lineApply = lineApplyService.updateNonLine(siteLineApple);
				map.put("result", true);
				map.put("lineApply", lineApply);
			} catch (UpdateFailException ex) {
				map.put("result", this.getMessageDetail(ex.getErrCode()));
			} catch (Exception ex) {
				map.put("result", ex.getMessage());
			}
		}
		return map;
	}

	/**
	 * 匯出excel
	 * 
	 * @return
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	@RequestMapping(value = "/getExcel", method = RequestMethod.GET)
	public ModelAndView exportExcel(ModelMap model, HttpServletRequest request,
			HttpServletResponse response, @RequestParam("appId") String appId) {
		ViewExcel viewExcel = new ViewExcel();
		Map<String, Object> obj = null;

		// //專線資訊
		SiteLineApplyDTO siteLineApple = lineApplyService.getLineApplyByExcel(appId);
		HSSFWorkbook workbook = lineApplyService.generateWorkbook("sheet", siteLineApple);
		try {
			viewExcel.buildExcelDocument(obj, workbook, request, response);
		} catch (Exception e) {
			log.error("ex.getMessage() : " + e.getMessage());
		}
		return new ModelAndView(viewExcel, model);
	}

	/**
	 * 查詢網路種類
	 * 
	 * @param eqpTypeId
	 * @return
	 */
	@RequestMapping(value = "/search/lineSpeed")
	@ResponseBody
	public List<LabelValueModel> getLineSpeed(@RequestParam("circuitType") String circuitType) {
		return lookupService.getLabelValueList("CIRCUIT_TYPE_" + circuitType);
	}

	/**
	 * Network Page
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/lineNetworkPage")
	public String showLineNetworkPage(HttpServletRequest request, Map<String, Object> model) {
		return "ajaxPage/st/line/LineNetworkD";
	}

	/**
	 * 機房專線資訊lineNetworkTable
	 * 
	 * @param b_siteId
	 * @return
	 */
	@RequestMapping(value = "/search/lineNetworkTable")
	@ResponseBody
	public BaseJasonObject<SiteLineSiteDataDTO> getLineNetworkTable(
			@RequestParam("b_siteId") String b_siteId) {
		log.debug("b_siteId : " + b_siteId);
		List<String> b_siteIdList = new ArrayList<String>();
		if (b_siteId != null) {
			for (String siteId : b_siteId.split(",")) {
				b_siteIdList.add(siteId);
			}
		}
		List<SiteLineSiteDataDTO> siteLineDTOList = lineApplyService
				.getSiteLineSiteDateBySiteId(b_siteIdList);
		return new BaseJasonObject<>(siteLineDTOList, AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 專線維護編輯、檢視、申請簽核
	 * 
	 * @param model
	 * @param appId
	 * @param viewType
	 * @return
	 */
	@RequestMapping("/initLineApplyM")
	public String initLineApplyM(Map<String, Object> model, String appId, String viewType) {
		try {
			// EQP_TYPE_ID 設備型態
			SiteLineApplyDTO siteLineApplyDTO = lineApplyService
					.selectSiteLineApplyDTOByAppId(appId);
			String AppDesc = StringUtils.trimToEmpty(siteLineApplyDTO.getAPP_DESC());
			siteLineApplyDTO.setAPP_DESC(AppDesc.replaceAll("(\n|\r\n)", "\\\\n"));
			TbSiteWork tbSiteWork = lineApplyService.getSiteWork(siteLineApplyDTO.getORDER_ID());

			String oldEndDate = ""; // 舊專線退租日期
			if (StringUtils.isNotEmpty(siteLineApplyDTO.getO_LINE_ID())) {
				SiteLineApplyDTO dto = lineApplyService
						.selectSiteLineApplyDTOByLineId(siteLineApplyDTO.getO_LINE_ID());
				if (dto != null && dto.getEND_DATE() != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
					oldEndDate = sdf.format(dto.getEND_DATE());
				}
			}

			model.put("siteLineApplyDTO", siteLineApplyDTO);
			model.put("workTypeLabel", WorkType.detectLabel(tbSiteWork.getWORK_TYPE()));
			model.put("workTypeValue", tbSiteWork.getWORK_TYPE());
			model.put("eqpTypeLabel", lineApplyService.getEqpTypeLabel(tbSiteWork.getEQP_TYPE_ID()));
			model.put("oldEndDate", oldEndDate); // 舊專線退租日期
			model.put("fromLineMaintain", true);
			model.put("view", StringUtils.trimToEmpty(viewType).equalsIgnoreCase("view")); // 檢視
			model.put("sign", StringUtils.trimToEmpty(viewType).equalsIgnoreCase("sign")); // 簽核
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return "ajaxPage/st/line/LineApplyM";
		}

		return "ajaxPage/st/line/LineApplyM";
	}

	/**
	 * 檢視舊專線資訊
	 * 
	 * @param model
	 * @param oLineId
	 * @param fromLineMaintain
	 * @param targetFrameId
	 * @return
	 */
	@RequestMapping("/viewOldLineApply")
	public String viewOldLineApply(HashMap<String, Object> model, String oLineId,
			Boolean fromLineMaintain, String targetFrameId) {

		SiteLineApplyDTO viewSiteLineApplyDTO = lineApplyService
				.selectSiteLineApplyDTOByLineId(oLineId);

		// 取得舊專線退租日期
		SiteLineApplyDTO oldLine = lineApplyService
				.selectSiteLineApplyDTOByLineId(viewSiteLineApplyDTO.getO_LINE_ID());
		String aSite[] = getLocationInfo(model, viewSiteLineApplyDTO.getA_LOC(), "a"); // 甲端
		String bSite[] = getLocationInfo(model, viewSiteLineApplyDTO.getB_LOC(), "b"); // 乙端

		int sameSiteCount = 0;
		for (int i = 0; i < aSite.length; i++) {
			for (int j = 0; j < bSite.length; j++) {
				if (StringUtils.equals(aSite[i], bSite[j])) {
					bSite = (String[]) ArrayUtils.remove(bSite, j);
					sameSiteCount++;
					break;
				}
			}
		}

		model.put("sameSiteCount", sameSiteCount); // 甲乙端相同站數
		model.put("oldLineEndDate", oldLine.getEND_DATE()); // 舊專線退租日期
		model.put("viewSiteLineApplyDTO", viewSiteLineApplyDTO); // 專線維護-編輯-舊專線檢視資料
		model.put("targetFrameId", targetFrameId);
		model.put("fromLineMaintain", fromLineMaintain);
		model.put("oldLineView", true);
		return "ajaxPage/st/line/LineDetail";
	}

	/**
	 * 甲乙端站台資訊
	 * 
	 * @param model
	 * @param locationId
	 * @param ab a:甲端 b:乙端
	 * @return
	 */
	private String[] getLocationInfo(Map<String, Object> model, String locationId, String ab) {
		if (StringUtils.isEmpty(locationId)) {
			return new String[0];
		}
		List<TbSiteMain> tbSiteMainList = lineApplyService.findTbSiteMainByLocationId(locationId);
		TbSiteLocation tbSiteLocation = lineApplyService.findTbSiteLocationByPrimaryKey(locationId);
		model.put(ab + "ViewTbSiteLocation", tbSiteLocation);
		String siteId[] = new String[tbSiteMainList.size()];
		int i = 0;
		StringBuffer site = new StringBuffer();
		for (TbSiteMain tbSiteMain : tbSiteMainList) {
			siteId[i] = tbSiteMain.getBTS_SITE_ID();
			if (i == 0) {
				site.append(siteId[i]);
			} else {
				site.append("," + siteId[i]);
			}
			i++;
		}

		model.put(ab + "SiteIds", site.toString());

		return siteId;
	}

}
