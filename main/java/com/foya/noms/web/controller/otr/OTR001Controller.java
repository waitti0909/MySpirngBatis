package com.foya.noms.web.controller.otr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hornetq.utils.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbInvWarehouse;
import com.foya.dao.mybatis.model.TbSiteWork;
import com.foya.dao.mybatis.model.TbSiteWorkOrder;
import com.foya.exception.NomsException;
import com.foya.noms.dto.inv.TbInvMaterialOptDTO;
import com.foya.noms.dto.st.MeterialRtnDTO;
import com.foya.noms.dto.st.SiteWorkDTO;
import com.foya.noms.enums.OrderStatus;
import com.foya.noms.enums.OutSourceStatus;
import com.foya.noms.service.auth.PersonnelService;
import com.foya.noms.service.org.DeptService;
import com.foya.noms.service.otr.OTR001Service;
import com.foya.noms.service.st.MeterialOptService;
import com.foya.noms.service.st.ST011Service;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.JqGridData;
import com.foya.noms.web.controller.BaseController;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Controller
@RequestMapping("/otr/otr001")
public class OTR001Controller extends BaseController {

	@Autowired
	private OTR001Service otr001Service;
	
	@Autowired
	private MeterialOptService meterialOptService;
	
	@Autowired
	private ST011Service st011Service; 
	
	@Autowired
	private PersonnelService personnelService;
	
	@Autowired
	private DeptService deptService;
	
	/**
	 * 工單物料轉出查詢頁
	 * @param model
	 * @return
	 */
	@RequestMapping("init")
	public String initOTR001L(Map<String ,Object> model){
		Set<String> deptList = getLoginUser().getAccessDeptListByMenuID(getCurrentMenuId());
		model.put("deptList", otr001Service
				.selectWorkOrderDeptInUserDept(new ArrayList<String>(deptList)));
		
		return "ajaxPage/otr/OTR001L";
	} 
	
	/**
	 * 工單物料轉出查詢
	 * @param deptId
	 * @param orderId
	 * @param orderIdIn
	 * @param btsSiteIdOut
	 * @param btsSiteIn
	 * @param siteNameIn
	 * @param siteNameOut
	 * @param crTimeStart
	 * @param crTimeEnd
	 * @return
	 */
	@RequestMapping("search")
	@ResponseBody
	public JqGridData<TbInvMaterialOptDTO> search(String deptId, String orderId, String orderIdIn,
			String btsSiteIdOut, String btsSiteIdIn, String siteNameIn,
			String siteNameOut, String crTimeStart, String crTimeEnd) {
		
		PageList<TbInvMaterialOptDTO> materialOptList = otr001Service.findMaterialTransferByConditions(deptId, orderId,
				orderIdIn, btsSiteIdOut, btsSiteIdIn, siteNameIn, siteNameOut,
				crTimeStart, crTimeEnd);
		return new  JqGridData<TbInvMaterialOptDTO>(materialOptList.getPaginator().getTotalCount(), materialOptList);
	}
	
	/**
	 * 工單物轉出編輯頁及檢視頁
	 * @param map
	 * @param optId
	 * @return
	 */
	@RequestMapping("add")
	public String add(Map<String, Object> map,String optId){
		if(StringUtils.isNotBlank(optId)){
			//轉出
			TbInvMaterialOptDTO MaterialOpt = otr001Service.selectTroHistory(optId);
			map.put("materialOptTRO", MaterialOpt);
			map.put("whNameOut", otr001Service.selectWhNameByWhId(MaterialOpt.getWH_ID()));
			map.put("chName", MaterialOpt.getCHN_NM());
			map.put("deptName", MaterialOpt.getDEPT_NAME());
			map.put("siteOut",st011Service.selectSiteBySiteId(MaterialOpt.getSITE_ID()));
			map.put("materialTroDetail", otr001Service.selectTroDetail(optId));
			//轉出
			String desc = StringUtils.trimToEmpty(MaterialOpt.getOPT_DESC());
			MaterialOpt.setOPT_DESC(desc.replaceAll("(\n|\r\n)", "\\\\n"));
			TbSiteWorkOrder workOrder = otr001Service.selectWorkorder(MaterialOpt.getORDER_ID_IN());
			TbSiteWork tbSiteWork =  otr001Service.selectSiteWorkByWorkId(workOrder.getWORK_ID());
			map.put("siteIn",st011Service.selectSiteBySiteId(tbSiteWork.getSITE_ID()));
			
			map.put("PageType", "view");
		} 
		return  "ajaxPage/otr/OTR001A";
	}
	
	/**
	 * 開啟工單查詢頁
	 * @param model
	 * @param callBackFun
	 * @param targetFrameId
	 * @param currentMenuId
	 * @return
	 */
	@RequestMapping("initOTR001SP1")
	public String initOTR001SP1(Map<String, Object> model, String callBackFun,
			String targetFrameId, Integer currentMenuId) {
		
		model.put("targetFrameId" ,targetFrameId);
		model.put("callBackFun",callBackFun);
		model.put("orderStatusList", OrderStatus.getLabelValueList());
		Set<String> deptList = getLoginUser().getAccessDeptListByMenuID(currentMenuId);
		model.put("deptList", otr001Service
				.selectWorkOrderDeptInUserDept(new ArrayList<String>(deptList)));
		
		return  "ajaxPage/otr/OTR001SP1";
	}
	
	/**
	 * 工單查詢
	 * @param depId
	 * @param orderStatus
	 * @param btsSiteId
	 * @param siteName
	 * @return
	 */
	@RequestMapping("searchWorkOrder")
	@ResponseBody
	public JqGridData<SiteWorkDTO> searchWorkOrder( String depId, String orderStatus, String btsSiteId, String siteName){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("depId", depId);
		map.put("orderStatus", orderStatus);
		map.put("siteName", siteName);
		map.put("btsSiteId", btsSiteId);
		PageList<SiteWorkDTO> siteList = otr001Service.selectSiteWorkByConditions(map);
		return new  JqGridData<SiteWorkDTO>(siteList.getPaginator().getTotalCount(), siteList);
	}
	
	/**
	 * 目的派工單號
	 * @param map
	 * @param orderId
	 * @return
	 */
	@RequestMapping("searchOutSourcing")
	@ResponseBody
	public BaseJasonObject<Map<String, Object>> searchOutSourcing(Map<String, Object> map, String orderId){
		map.put("outSourcingList", otr001Service.findOutSourcingByOrderIdAndStatus(orderId, OutSourceStatus.OS05.name()));
		
		return new BaseJasonObject<>(map, AJAX_SUCCESS);
	}
	
	/**
	 * 領/退 料倉庫
	 * @param map
	 * @param domain
	 * @return
	 */
	@RequestMapping("searchWareHourse")
	@ResponseBody
	public BaseJasonObject<TbInvWarehouse> searchWateHourse(Map<String, Object> map, String orderId, String osId){
		return new BaseJasonObject<>(meterialOptService.getInvWareHouse(orderId,osId), AJAX_SUCCESS,AJAX_EMPTY);
	}
	
	/**
	 * 轉出明細(序號件)
	 * @param siteId
	 * @return
	 */
	@RequestMapping("getTransOutDetail")
	@ResponseBody
	public BaseJasonObject<List<MeterialRtnDTO>> getTransOutDetail(String osId){
		return new BaseJasonObject<>(otr001Service.getTransOutDetail(osId, "S"),AJAX_SUCCESS);
	}
	
	/**
	 * 物料轉出(存檔)
	 * @param orderIdIn 目的工單
	 * @param siteIdIn 目的站台
	 * @param osIdIn 目的派工單
	 * @param whIdIn 領料倉庫別
	 * @param workTypeIn 目的工務類別
	 * @param jsonArrStrOut
	 * @param orderIdOut 轉出工單
	 * @param siteIdOut 轉出站台
	 * @param osIdOut 轉出派工單
	 * @param whIdOut 退領料倉庫別
	 * @param workTypeOut 轉出工務類別
	 * @param optDesc 轉出說明
	 * @return
	 */
	@RequestMapping("MTTransOut")
	@ResponseBody
	public BaseJasonObject<Object> MTTransOut(String orderIdIn,
			String workTypeIn, String siteIdIn, String osIdIn, String whIdIn,
			String jsonArrStrOut, String orderIdOut, String siteIdOut,
			String osIdOut, String whIdOut, String workTypeOut, String optDesc) {

		try {
			otr001Service.materialTransfer(orderIdIn, siteIdIn, osIdIn, whIdIn, workTypeIn,
					jsonArrStrOut, orderIdOut, siteIdOut, osIdOut, whIdOut,
					workTypeOut, optDesc);
		} catch (NomsException e) {
			log.error(e.getMessage(), e);
			return new BaseJasonObject<>(false, "工單物料轉出失敗！！\r\n"+e.getMessage());
		} catch (JSONException e) {
			log.error(e.getMessage(), e);
			return new BaseJasonObject<>(false,"工單物料轉出失敗！！\r\n"+e.getMessage());
		} catch (Exception e){
			log.error(e.getMessage(), e);
			return new BaseJasonObject<>(false,"工單物料轉出失敗！！\r\n"+e.getMessage());
		}
		
		return new BaseJasonObject<>(true,"工單物料轉出成功！！");
	}
	
}
