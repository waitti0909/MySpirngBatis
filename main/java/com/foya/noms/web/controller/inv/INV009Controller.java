/**
 * 日期：2014/11/28
 * 功能說明：調入作業與查詢
 * 程式人員：Arvin
 */
package com.foya.noms.web.controller.inv;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
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
import com.foya.exception.NomsException;
import com.foya.noms.dto.auth.RoleMenuFunDTO;
import com.foya.noms.dto.inv.TbInvTrActCompleteDTO;
import com.foya.noms.dto.inv.TbInvTrCompleteDTO;
import com.foya.noms.dto.inv.TbInvWarehouseDTO;
import com.foya.noms.dto.profile.TodoOrdersDTO;
import com.foya.noms.enums.InvEnumCommon;
import com.foya.noms.service.auth.PersonnelService;
import com.foya.noms.service.auth.RoleMenuFunService;
import com.foya.noms.service.inv.INV008Service;
import com.foya.noms.service.inv.INV009Service;
import com.foya.noms.service.inv.InvPublicUtilService;
import com.foya.noms.service.org.DomainService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.web.controller.BaseController;

@Controller
public class INV009Controller extends BaseController {

	// ------------------------------------------------------------
	/**
	 * controller
	 */
	@Inject
	private InvPublicUtilController publicUtilController;

	/**
	 * INV009Service
	 */
	@Inject
	private INV009Service inv009Service;

	/**
	 * INV008Service
	 */
	@Inject
	private INV008Service inv008Service;

	/**
	 * 
	 */
	@Inject
	private PersonnelService personnelService;

	/**
	 * 
	 */
	@Inject
	private DomainService domainService;

	/**
	 * 
	 */
	@Autowired
	private RoleMenuFunService roleMenuFunService;
	// ------------------------------------------------------------
	// ------------------------------------------------------------
	@Inject
	private InvPublicUtilService invPublicUtilService;

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
	@RequestMapping(value = "/inv/inv009/init")
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

		return "ajaxPage/inv/INV009";
	}

	// /**
	// * 查詢調出清單
	// *
	// * @param request
	// * request
	// */
	// @RequestMapping(value = "/inv/inv009/detailSearch")
	// @ResponseBody
	// public JqGridData<TbInvTrCompleteDTO> detailSearch(HttpServletRequest
	// request) {
	//
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	//
	// String applyNumber = request.getParameter("applyNumber").trim();
	// String applyState = request.getParameter("applyState").trim();
	// String outDepot = request.getParameter("outDepot").trim();
	// String inDepot = request.getParameter("inDepot").trim();
	// String demandDept = request.getParameter("demandDept").trim();
	// String str_demandDate = request.getParameter("STR_demandDate").trim();
	// String end_demandDate = request.getParameter("END_demandDate").trim();
	// String applyDept = request.getParameter("applyDept").trim();
	// String applicant = request.getParameter("applicant").trim();
	// boolean first = request.getParameter("first").equals("true") ? true :
	// false;
	// String domain = request.getParameter("domain").trim();
	// List<TbInvTrCompleteDTO> trList = null;
	//
	// ArrayList<String> inDepotIdList = null;
	//
	// if (outDepot == null || outDepot == "") {
	// // 取的此登入者所負責的調出倉
	// List<TbInvWarehouseDTO> depotList =
	// invPublicUtilService.getWareHouseByDomain(domain);
	//
	// inDepotIdList = new ArrayList<String>();
	//
	// for (TbInvWarehouseDTO dto : depotList) {
	// inDepotIdList.add(dto.getWh_id());
	// }
	// }
	//
	// try {
	//
	// Object[] stateList;
	//
	// // 第一次載入，自動搜尋倉庫狀態為 "調出" 及 "部份調出"及 "調入"
	// if (first) {
	// stateList = new Object[] { InvEnumCommon.Allocationstatus.EXIT.getCode(),
	// InvEnumCommon.Allocationstatus.PART_EXIT.getCode(),
	// InvEnumCommon.Allocationstatus.IN.getCode() };
	// } else {
	// stateList = applyState == "" ?
	// InvEnumCommon.Allocationstatus.getCodeList() : new Object[] {
	// InvEnumCommon.Allocationstatus.toSource(
	// Integer.parseInt(applyState)).getCode() };
	// }
	//
	// // trList = inv008Service.getInvTrDataPageByDate(userNo,
	// // sdf.parse(str_demandDate), sdf.parse(end_demandDate),
	// // applyNumber, outDepot,
	// // inDepot, demandDept, applyDept, applicant, stateList, null,
	// // inDepotIdList, "in");
	//
	// first = false;
	//
	// // 掛載InvTrDtoMap
	// mountInvTrDtoMap(trList);
	//
	// } catch (NumberFormatException e) {
	//
	// log.error("[INV009][/inv/inv009/detailSearch] ERROR :STR_demandDate = " +
	// str_demandDate + "，END_demandDate = " + end_demandDate);
	// e.printStackTrace();
	// } catch (Throwable throwable) {
	//
	// log.error("[INV009][/inv/inv009/detailSearch] ERROR :" +
	// throwable.getMessage());
	// throwable.printStackTrace();
	// }
	//
	// PageList<TbInvTrCompleteDTO> page = (PageList<TbInvTrCompleteDTO>)
	// trList;
	// return new JqGridData<>(page.getPaginator().getTotalCount(), trList);
	// }

	/**
	 * 查詢申請明細
	 * 
	 * @param request
	 *            request
	 * @param trNo
	 *            調撥單號
	 * @return JqGridData<TbInvTrDtlCompleteDTO>
	 */
	@RequestMapping(value = "/inv/inv009M/getInvTrExportDtlData")
	@ResponseBody
	public BaseJasonObject<TbInvTrActCompleteDTO> applyDetailSearch(@RequestParam("trNo") String trNo) {

		List<TbInvTrActCompleteDTO> trActDetailList = null;

		try {

			trActDetailList = inv009Service.getInvTrExportDtlData(trNo);
			// setTrDtlDtoMap(trDetailList);

		} catch (Throwable throwable) {

			log.error("[INV009][/inv/inv009/getInvTrExportDtlData] ERROR :" + throwable.getMessage());
			throwable.printStackTrace();
		}

		// PageList<TbInvTrActCompleteDTO> page =
		// (PageList<TbInvTrActCompleteDTO>) trDetailList;
		// return new JqGridData<>(page.getPaginator().getTotalCount(),
		// trDetailList);
		return new BaseJasonObject<TbInvTrActCompleteDTO>(trActDetailList, "讀取成功", AJAX_SUCCESS);
	}

	/**
	 * 查詢此單號申請數
	 * 
	 * @param request
	 *            request
	 * @param trNo
	 *            調撥單號
	 * @return JqGridData<TbInvTrDtlCompleteDTO>
	 */
	@RequestMapping(value = "/inv/inv009M/getInvTrApplyNumber")
	@ResponseBody
	public BaseJasonObject<TbInvTrActCompleteDTO> getInvTrApplyNumber(@RequestParam("trNo") String trNo) {

		List<TbInvTrActCompleteDTO> invTrApplyList = null;

		try {

			invTrApplyList = inv009Service.getApplyTotal(trNo);
			// setTrDtlDtoMap(trDetailList);

		} catch (Throwable throwable) {

			log.error("[INV009M][/inv/inv009M/getInvTrApplyNumber] ERROR :" + throwable.getMessage());
			throwable.printStackTrace();
		}

		return new BaseJasonObject<TbInvTrActCompleteDTO>(invTrApplyList, "讀取成功", AJAX_SUCCESS);
	}

	// /**
	// * 調入明細歷程搜尋
	// */
	// @RequestMapping(value = "/inv/inv009M/exportHistorySearch")
	// @ResponseBody
	// private JqGridData<TbInvTrActCompleteDTO>
	// exportHistorySearch(@RequestParam("trDtlNo") String trDtlNo) {
	//
	// List<TbInvTrActCompleteDTO> actDto = null;
	//
	// TbInvTrDtlCompleteDTO trDtlDto = (TbInvTrDtlCompleteDTO)
	// invTrDtlDtoMap.get(trDtlNo);
	//
	// actDto = trDtlDto.getCallInHistoryTrActDtoList();
	//
	// PageList<TbInvTrActCompleteDTO> page = (PageList<TbInvTrActCompleteDTO>)
	// actDto;
	// return new JqGridData<>(page.getPaginator().getTotalCount(), actDto);
	// }

	/**
	 * 調入修改
	 * 
	 * @param callInObjectList
	 *            View Request Object
	 */
	@RequestMapping(value = "/inv/inv009M/executionImportWork")
	@ResponseBody
	private BaseJasonObject<JSONObject> executionImportWork(@RequestParam("action") String action, @RequestParam("invTrDto") String invTrDto,
			@RequestParam("TbInvTrActDto") String TbInvTrActDto) {

		try {
			JSONObject jsonObj = new JSONObject(invTrDto);
			JSONArray jsonArray = new JSONArray(TbInvTrActDto);

			return inv009Service.executionImportWork(action, jsonObj, jsonArray);
		} catch (JSONException e) {

			log.error("[INV009M][/inv/inv009M/executionImportWork] ERROR : JSON 資訊轉換錯誤。");
			e.printStackTrace();
		} catch (NomsException nomsException) {

			log.error("[INV009M][/inv/inv009M/executionImportWork] ERROR : 執行調出錯誤 。");
			nomsException.printStackTrace();
		}

		return new BaseJasonObject<JSONObject>(false, "調入失敗。");

		// String action = request.getParameter("action");
		//
		// String memo = null;
		//
		// // 如果是別的指令進入則不進入調入 [1=調入，2=結案，3=強制結案]
		// if ("1".equals(action) || "2".equals(action) || "3".equals(action)) {
		//
		// List<TbInvTrDtlCompleteDTO> trDtlDtoList = new
		// ArrayList<TbInvTrDtlCompleteDTO>();
		//
		// for (int i = 0; i < callInObjectList.size(); i++) {
		//
		// CallInObject callInObj = callInObjectList.get(i);
		//
		// TbInvTrDtlCompleteDTO trDtltDto = (TbInvTrDtlCompleteDTO)
		// invTrDtlDtoMap.get(callInObj.getTrDtlNo());
		//
		// trDtltDto.setInputVal(callInObj.getInputVal());
		// trDtlDtoList.add(trDtltDto);
		//
		// if (i == 0) {
		// // 取得備註
		// memo = callInObj.getMemo();
		// }
		// }
		//
		// // 資料修改
		// try {
		//
		// inv009Service.executionCallInAction(trDtlDtoList, userNo, memo,
		// action, callInMax, alreadyCallIntMax);
		// } catch (NomsException exception) {
		//
		// log.error(exception.getMessage());
		//
		// return false;
		// }
		//
		// } else {
		//
		// log.error("[INV009M][/inv/inv009M/executionCallInAction] ERROR : 執行調入錯誤 。Action Code = ["
		// + action + "] 命令外動作");
		//
		// return false;
		// }

	}

	/**
	 * 執行調入作業
	 * 
	 * @param model
	 *            model
	 * @param trNo
	 *            調撥單號
	 * @return URL
	 */
	@RequestMapping(value = "/inv/inv009/exportDataDetailByTrNo")
	public String exportDataDetailByTrNo(Map<String, Object> model, @RequestParam("trNo") String trNo) {

		List<TbInvTrCompleteDTO> trList = inv008Service.getInvTrDataPageToDo(null, null, null, null, null, trNo, null, null, null, InvEnumCommon.Allocationstatus.getCodeList(),
				"in");

		model.put("invTrDto", trList.size() > 0 ? trList.get(0) : new TbInvTrCompleteDTO());

		return "ajaxPage/inv/INV009M";
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
	@RequestMapping(value = "/inv/inv009/exportDataDetail")
	public String exportDataDetailByDto(Map<String, Object> model, @RequestBody TbInvTrCompleteDTO tbInvTrCompleteDTO) {

		model.put("invTrDto", tbInvTrCompleteDTO);

		return "ajaxPage/inv/INV009M";
	}

	/**
	 * 查看調入詳細資料
	 * 
	 * @param model
	 *            model
	 * @param trNo
	 *            調撥單號
	 * @return URL
	 */
	@RequestMapping(value = "/inv/inv009/callInDetailView")
	public String callInDetailView(Map<String, Object> model, @RequestBody TbInvTrCompleteDTO tbInvTrCompleteDTO) {

		model.put("invTrDto", tbInvTrCompleteDTO);

		return "ajaxPage/inv/INV009D";
	}

	/**
	 * 供待辦事項呼叫
	 */
	@RequestMapping(value = "/inv/inv009/getTodoOrders")
	@ResponseBody
	public List<TodoOrdersDTO> getTodoOrders() {

		List<TodoOrdersDTO> todoDtoList = new ArrayList<TodoOrdersDTO>();

		List<TbAuthMenu> menuList = inv008Service.getMenuId("/inv/inv009/init");
		int menuId = 0;
		if (menuList.size() > 0) {
			menuId = menuList.get(0).getMENU_ID().intValue();
		}

		List<RoleMenuFunDTO> list = roleMenuFunService.getFuncitonByPsnAndMenuId(getLoginUser().getUserId(), menuId);
		boolean butCompetence = false;

		for (RoleMenuFunDTO dto : list) {

			if (dto.getBtnName().equals("調入")) {
				butCompetence = true;
			}
		}

		if (butCompetence) {
			Object[] stateList = new Object[] { InvEnumCommon.Allocationstatus.EXIT.getCode(), InvEnumCommon.Allocationstatus.PART_EXIT.getCode(),
					InvEnumCommon.Allocationstatus.IN.getCode() };

			List<TbInvTrCompleteDTO> trList = inv008Service.getInvTrDataPageToDo(null, null, null, getWareHouseByDomain(getLoginUser().getDomain().getID()), null, null, null,
					null, null, stateList, "in");

			for (TbInvTrCompleteDTO dto : trList) {

				// 取的姓名
				List<TbAuthPersonnel> personnelList = personnelService.searchPsnByPsnNo(dto.getCr_user());

				TodoOrdersDTO ordersDto = new TodoOrdersDTO();
				ordersDto.setOrderId(dto.getTr_no());
				ordersDto.setOrderInfo(dto.getInWhName());
				ordersDto.setOrderTypeDesc("調入");
				ordersDto.setAppUser(dto.getCr_user());
				ordersDto.setAppUserName(personnelList.size() > 0 ? personnelList.get(0).getCHN_NM() : "");
				ordersDto.setAppTime(new Timestamp(dto.getCr_time().getTime()));
				ordersDto.setOrderStatus(String.valueOf(dto.getStatus()));
				ordersDto.setOrderStatusDesc(InvEnumCommon.Allocationstatus.toSource(dto.getStatus()).getValue());
				ordersDto.setLinkURL("/inv/inv009/exportDataDetailByTrNo?trNo=" + dto.getTr_no());

				todoDtoList.add(ordersDto);
			}
		}

		return todoDtoList;
	}

	/********************************************************************************/
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

// class CallInObject {
//
// private String trDtlNo;
//
// private int inputVal;
//
// private String memo;
//
// public String getTrDtlNo() {
// return trDtlNo;
// }
//
// public void setTrDtlNo(String trDtlNo) {
// this.trDtlNo = trDtlNo;
// }
//
// public int getInputVal() {
// return inputVal;
// }
//
// public void setInputVal(int inputVal) {
// this.inputVal = inputVal;
// }
//
// public String getMemo() {
// return memo;
// }
//
// public void setMemo(String memo) {
// this.memo = memo;
// }
//
// }
