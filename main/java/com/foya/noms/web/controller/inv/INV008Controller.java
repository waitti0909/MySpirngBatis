/**
 * 日期：2015/03/06
 * 功能說明：調出作業與查詢
 * 程式人員：Arvin
 */
package com.foya.noms.web.controller.inv;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hornetq.utils.json.JSONArray;
import org.hornetq.utils.json.JSONException;
import org.hornetq.utils.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbAuthMenu;
import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.dao.mybatis.model.TbInvSrl;
import com.foya.exception.NomsException;
import com.foya.noms.dto.auth.RoleMenuFunDTO;
import com.foya.noms.dto.inv.TbInvTrActCompleteDTO;
import com.foya.noms.dto.inv.TbInvTrCompleteDTO;
import com.foya.noms.dto.inv.TbInvTrDtlCompleteDTO;
import com.foya.noms.dto.inv.TbInvWarehouseDTO;
import com.foya.noms.dto.profile.TodoOrdersDTO;
import com.foya.noms.enums.InvEnumCommon;
import com.foya.noms.service.auth.PersonnelService;
import com.foya.noms.service.auth.RoleMenuFunService;
import com.foya.noms.service.inv.INV008Service;
import com.foya.noms.service.inv.InvPublicUtilService;
import com.foya.noms.service.org.DomainService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.JqGridData;
import com.foya.noms.web.controller.BaseController;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Controller
public class INV008Controller extends BaseController {

	// ----------------------------------------------------------------
	/**
	 * controller
	 */
	@Autowired
	private InvPublicUtilController publicUtilController;

	/**
	 * 
	 */
	@Autowired
	private DomainService domainService;

	/**
	 * 
	 */
	@Autowired
	private InvPublicUtilService invPublicUtilService;

	/**
	 * INV008Service
	 */
	@Autowired
	private INV008Service inv008Service;

	/**
	 * 
	 */
	@Autowired
	private PersonnelService personnelService;

	@Autowired
	private RoleMenuFunService roleMenuFunService;
	// ----------------------------------------------------------------
	/**
	 * 
	 */

	// ----------------------------------------------------------------
	/**
	 * Init
	 * 
	 * @param request
	 *            request
	 * @param model
	 *            model
	 * @return URL
	 * @throws Exception
	 */
	@RequestMapping(value = "/inv/inv008/init")
	public String init(HttpServletRequest request, Map<String, Object> model) throws Exception {

		// 調撥進度
		model.put("applyState", publicUtilController.getLookupByType("INV_TR_STATUS"));

		// 調出(入)倉庫
		model.put("outDepot", publicUtilController.getWareHouseByDomain(null, getLoginUser().getDomain().getID()).getRows());

		// 需求(申請)單位
		model.put("demandDept", publicUtilController.domainByDeptSelect(null, getLoginUser().getDomain().getID()).getRows());

		// 登入者單位
		model.put("deptId", getLoginUser().getHrDeptId());

		// 登入者
		model.put("psnId", getLoginUser().getUserId());

		// 區域
		model.put("domainSelect", domainService.getAccessDomainByUser(getLoginUser().getDomain(), true));

		return "ajaxPage/inv/INV008";
	}

	/**
	 * 查詢調出清單
	 * 
	 * @param request
	 *            request
	 */
	@RequestMapping(value = "/inv/inv008/detailSearch")
	@ResponseBody
	public JqGridData<TbInvTrCompleteDTO> detailSearch(HttpServletRequest request) {

		String trNo = request.getParameter("trNo");
		String domain = request.getParameter("domain");
		String outWhId = request.getParameter("outWhId");
		String inWhId = request.getParameter("inWhId");
		String needDept = request.getParameter("needDept");
		String applyDept = request.getParameter("applyDept");
		String applyDateStr = request.getParameter("applyDateStr");
		String applyDateEnd = request.getParameter("applyDateEnd");
		String applicant = request.getParameter("applicant");
		String applyState = request.getParameter("applyState");
		String wareHouseType = request.getParameter("wareHouseType");

		ArrayList<String> wareHouseIdList = null;

		if (outWhId == null || outWhId == "") {

			wareHouseIdList = getWareHouseByDomain(domain);
		}

		Object[] stateList;

		// 第一次載入，自動搜尋倉庫狀態為 "已核可" 及 "部份調出"
		// if (first) {
		// stateList = new Object[] {
		// InvEnumCommon.Allocationstatus.APPROVED.getCode(),
		// InvEnumCommon.Allocationstatus.PART_EXIT.getCode() };
		// } else {
		stateList = applyState == "" ? InvEnumCommon.Allocationstatus.getCodeList() : new Object[] { InvEnumCommon.Allocationstatus.toSource(
				Integer.parseInt(applyState)).getCode() };
		// }

		List<TbInvTrCompleteDTO> trList = inv008Service.getInvTrDataPageByDate(applyDateStr, applyDateEnd, outWhId, wareHouseIdList, inWhId, trNo,
				needDept, applyDept, applicant, stateList, wareHouseType);

		PageList<TbInvTrCompleteDTO> page = (PageList<TbInvTrCompleteDTO>) trList;
		return new JqGridData<>(page.getPaginator().getTotalCount(), trList);
	}

	/**
	 * 執行調出作業
	 * 
	 * @param model
	 *            model
	 * @param trNo
	 *            調撥單號
	 * @return URL
	 */
	@RequestMapping(value = "/inv/inv008/exportDataDetailByDto")
	public String exportDataDetailByDto(Map<String, Object> model, @RequestBody TbInvTrCompleteDTO tbInvTrCompleteDTO) {
		
		List<TbAuthMenu> menuList = inv008Service.getMenuId("/inv/inv008/init");
		int menuId = 0;
		if(menuList.size()>0){
			menuId = menuList.get(0).getMENU_ID().intValue();
		}
		
		List<RoleMenuFunDTO> list = roleMenuFunService.getFuncitonByPsnAndMenuId(getLoginUser().getUserId(), menuId);
		boolean butCompetence = false;
		
		for(RoleMenuFunDTO dto : list){
			if(dto.getBtnName().equals("調出")){
				butCompetence = true;
			}
		}
		model.put("butCompetence", butCompetence);
		
		model.put("invTrDto", tbInvTrCompleteDTO);

		return "ajaxPage/inv/INV008M";
	}

	/**
	 * 資料檢視
	 * 
	 * @param model
	 *            model
	 * @param trNo
	 *            調撥單號
	 * @return URL
	 */
	@RequestMapping(value = "/inv/inv008/exportDataDetailByView")
	public String exportDataDetailByView(HttpServletRequest request, Map<String, Object> model, @RequestBody TbInvTrCompleteDTO tbInvTrCompleteDTO) {

		model.put("invTrDto", tbInvTrCompleteDTO);

		return "ajaxPage/inv/INV008D";
	}

	/**
	 * 執行調出作業
	 * 
	 * @param model
	 *            model
	 * @param trNo
	 *            調撥單號
	 * @return URL
	 */
	@RequestMapping(value = "/inv/inv008/exportDataDetailByTrNo")
	public String exportDataDetail(Map<String, Object> model, @RequestParam("trNo") String trNo) {
		
		List<TbInvTrCompleteDTO> trList = inv008Service.getInvTrDataPageToDo(null, null, null, null, null, trNo, null, null, null,
				InvEnumCommon.Allocationstatus.getCodeList(), "out");
		
		model.put("invTrDto", trList.size() > 0 ? trList.get(0) : new TbInvTrCompleteDTO());

		return "ajaxPage/inv/INV008M";
	}

	/**
	 * 查詢調撥申請明細
	 * 
	 * @param request
	 *            request
	 * @param trNo
	 *            調撥單號
	 * @return JqGridData<TbInvTrDtlCompleteDTO>
	 */
	@RequestMapping(value = "/inv/inv008/applyDetailSearch")
	@ResponseBody
	public BaseJasonObject<TbInvTrDtlCompleteDTO> applyDetailSearch(@RequestParam("trNo") String trNo, @RequestParam("whId") String whId) {

		List<TbInvTrDtlCompleteDTO> trDetailList = null;

		try {

			trDetailList = inv008Service.getInvTrDtlDTo(trNo, whId);

		} catch (Throwable throwable) {

			log.error("[INV008][/inv/inv008/applyDetailSearch] ERROR :" + throwable.getMessage());
			throwable.printStackTrace();
		}

		return new BaseJasonObject<TbInvTrDtlCompleteDTO>(trDetailList, "讀取成功", AJAX_SUCCESS);
	}

	/**
	 * Booking 頁面跳轉
	 * 
	 * @param model
	 *            model
	 * @param trNo
	 *            調撥單號
	 * @return URL
	 */
	@RequestMapping(value = "/inv/inv008M/bookingSearch")
	@ResponseBody
	public BaseJasonObject<HashMap<String, String>> bookingSearch(@RequestParam("matNo") String matNo, @RequestParam("whId") String whId) {

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("paramMatNo", matNo);
		map.put("paramWhId", whId);

		return new BaseJasonObject<HashMap<String, String>>(map, AJAX_SUCCESS);
	}

	/**
	 * 查看已選廠商序號
	 * 
	 */
	@RequestMapping(value = "/inv/inv008M/showVenSnListView")
	public String showVenSnListView(Map<String, Object> model, @RequestBody TbInvTrDtlCompleteDTO tbInvTrCompleteDto) {

		model.put("tbInvTrDtlDto", tbInvTrCompleteDto);

		return "ajaxPage/inv/INV008SP1";
	}

	/**
	 * 取得廠商編號
	 * 
	 * @param request
	 *            request
	 * @param trNo
	 *            調撥單號
	 * @return JqGridData<TbInvTrDtlCompleteDTO>
	 */
	@RequestMapping(value = "/inv/inv008M/getTrSrlData")
	@ResponseBody
	public BaseJasonObject<TbInvSrl> getTrSrlData(@RequestParam("matNo") String matNo, @RequestParam("whId") String whId,
			@RequestParam("matStatus") String matStatus) {

		List<TbInvSrl> invSrlList = inv008Service.getTbInvSrlList(matNo, whId, Byte.valueOf(matStatus));

		return new BaseJasonObject<TbInvSrl>(invSrlList, "讀取成功", AJAX_SUCCESS);
	}

	/**
	 * 執行調出作業
	 * 
	 * @throws JSONException
	 */
	@RequestMapping(value = "/inv/inv008M/executionExportWork")
	@ResponseBody
	public BaseJasonObject<JSONObject> executionExportWork(@RequestParam("invTrDtoWork") String invTrDtoWork,
			@RequestParam("TbInvTrDtlDto") String TbInvTrDtlDto) {

		try {
			JSONObject jsonObj = new JSONObject(invTrDtoWork);
			JSONArray jsonArray = new JSONArray(TbInvTrDtlDto);
			return inv008Service.executionExportWork(jsonObj, jsonArray);
		} catch (JSONException e) {

			log.error("[INV008M][/inv/inv008M/executionExportWork] ERROR : JSON 資訊轉換錯誤。");
			e.printStackTrace();
		} catch (NomsException nomsException) {

			log.error("[INV008M][/inv/inv008M/executionExportWork] ERROR : 執行調出錯誤 。");
			nomsException.printStackTrace();
		}

		return new BaseJasonObject<JSONObject>(false, "調出失敗。");
	}

	/**
	 * 執行調出歷史
	 * 
	 * @throws JSONException
	 */
	@RequestMapping(value = "/inv/inv008M/exportHistorySearch")
	@ResponseBody
	public JqGridData<TbInvTrActCompleteDTO> exportHistorySearch(@RequestParam("trNo") String trNo, @RequestParam("matNo") String matNo,
			@RequestParam("dtlNo") String dtlNo, @RequestParam("trIn") String trIn, @RequestParam("outActNo") String outActNo) {

		List<TbInvTrActCompleteDTO> invTrActList = inv008Service.getExportHistory(trNo, matNo, trIn, Integer.parseInt(dtlNo),
				Integer.parseInt(outActNo));

		PageList<TbInvTrActCompleteDTO> page = (PageList<TbInvTrActCompleteDTO>) invTrActList;
		return new JqGridData<>(page.getPaginator().getTotalCount(), invTrActList);
	}

	/**
	 * 供待辦事項呼叫
	 */
	@RequestMapping(value = "/inv/inv008/getTodoOrders")
	@ResponseBody
	public List<TodoOrdersDTO> getTodoOrders() {

		List<TodoOrdersDTO> todoDtoList = new ArrayList<TodoOrdersDTO>();
		
		List<TbAuthMenu> menuList = inv008Service.getMenuId("/inv/inv008/init");
		int menuId = 0;
		if(menuList.size()>0){
			menuId = menuList.get(0).getMENU_ID().intValue();
		}
		
		List<RoleMenuFunDTO> list = roleMenuFunService.getFuncitonByPsnAndMenuId(getLoginUser().getUserId(), menuId);
		boolean butCompetence = false;
		
		for(RoleMenuFunDTO dto : list){
			if(dto.getBtnName().equals("調出")){
				butCompetence = true;
			}
		}
		
		if(butCompetence){
			Object[] stateList = new Object[] { InvEnumCommon.Allocationstatus.APPROVED.getCode(), InvEnumCommon.Allocationstatus.PART_EXIT.getCode() };

			List<TbInvTrCompleteDTO> trList = inv008Service.getInvTrDataPageToDo(null, null, null, getWareHouseByDomain(getLoginUser().getDomain().getID()), null, null, null, null, null,
					stateList, "out");
			
			for (TbInvTrCompleteDTO dto : trList) {

				// 取的姓名
				List<TbAuthPersonnel> personnelList = personnelService.searchPsnByPsnNo(dto.getCr_user());

				TodoOrdersDTO ordersDto = new TodoOrdersDTO();
				ordersDto.setOrderId(dto.getTr_no());
				ordersDto.setOrderInfo(dto.getOutWhName());
				ordersDto.setOrderTypeDesc("調出");
				ordersDto.setAppUser(dto.getCr_user());
				ordersDto.setAppUserName(personnelList.size() > 0 ? personnelList.get(0).getCHN_NM() : "");
				ordersDto.setAppTime(new Timestamp(dto.getCr_time().getTime()));
				ordersDto.setOrderStatus(String.valueOf(dto.getStatus()));
				ordersDto.setOrderStatusDesc(InvEnumCommon.Allocationstatus.toSource(dto.getStatus()).getValue());
				ordersDto.setLinkURL("/inv/inv008/exportDataDetailByTrNo?trNo=" + dto.getTr_no());

				todoDtoList.add(ordersDto);
			}
		}

		return todoDtoList;
	}

	/************************************************************************************/
	/**
	 * 取得登入使用者所負責的倉庫
	 */
	private ArrayList<String> getWareHouseByDomain(String domain) {

		ArrayList<String> wareHouseIdList = new ArrayList<String>();

		List<TbInvWarehouseDTO> depotList = invPublicUtilService.getWareHouseByDomain(domain);

		for (TbInvWarehouseDTO dto : depotList) {
			if (dto.getDomain().toUpperCase().equals(domain)) {
				wareHouseIdList.add(dto.getWh_id());
			}
		}

		return wareHouseIdList;
	}

}
