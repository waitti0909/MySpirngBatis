package com.foya.noms.web.controller.st.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.dao.mybatis.model.TbOrgDeptPos;
import com.foya.dao.mybatis.model.TbOrgPsnPos;
import com.foya.dao.mybatis.model.TbSiteWorkOrder;
import com.foya.dao.mybatis.model.TbSysOrderPage;
import com.foya.dao.mybatis.model.TbSysOrderPageExample;
import com.foya.exception.NomsException;
import com.foya.exception.UpdateFailException;
import com.foya.noms.dto.st.SiteWorkOrderDTO;
import com.foya.noms.enums.OrderStatus;
import com.foya.noms.service.st.base.BaseOrderProcessService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.web.controller.BaseController;
import com.foya.workflow.exception.WorkflowException;

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
 * <td>2015/3/27</td>
 * <td>Base on 通用工單處理入口</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table>
 * 
 * @author Charlie Woo
 * 
 */
public class BaseOrderProcessController extends BaseController {

	@Autowired
	private BaseOrderProcessService service;

	/**
	 * 派工 - 負責人 下拉選單
	 * @param request
	 * @param dept
	 * @return
	 * @throws Exception
	 * @author Charlie Woo
	 */
	@RequestMapping(value = "/personnelDept")
	@ResponseBody
	public BaseJasonObject<TbAuthPersonnel> initPersonnelDeptSelect(HttpServletRequest request, String dept) throws Exception {
		// 負責人 下拉選單
		List<TbOrgDeptPos> repUserList = service.findRepUserList(dept);
		List<TbAuthPersonnel> personnel = new ArrayList<TbAuthPersonnel>();
		if (StringUtils.isNotBlank(dept)) {
			for (TbOrgDeptPos temp : repUserList) {
				String deptPosId = temp.getDEPT_POS_ID();
				List<TbOrgPsnPos> psnNoList = service.findPsnNo(deptPosId);

				for (TbOrgPsnPos tempName : psnNoList) {
					String psnNo = tempName.getPSN_NO();
					List<TbAuthPersonnel> authPersonnelList = service.findPsnName(psnNo);
					personnel.addAll(authPersonnelList);
				}
			}
			return new BaseJasonObject<TbAuthPersonnel>(personnel, AJAX_SUCCESS, AJAX_EMPTY);
		} else {
			// 如果沒有職稱，直接使用 接工單位 DeptId 尋找 負責人 預設
			String psnNo = dept;
			List<TbAuthPersonnel> authPersonnelList = service.findPsnName(psnNo);
			return new BaseJasonObject<TbAuthPersonnel>(authPersonnelList, AJAX_SUCCESS, AJAX_EMPTY);
		}
	}

	/**
	 * 派工前檢查尚未完成前置工單數
	 * @param workId
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "/checkPreOrdersNotFinished")
	@ResponseBody
	public Integer checkPreOrdersNotFinished(@RequestParam("workId") String workId, @RequestParam("orderId") String orderId) {
		return service.getNotFinishedOrders(workId, orderId);
	}

	/**
	 * 查詢編輯頁處理頁籤-for各別工單
	 * @param orderId
	 * @param model
	 * @return
	 * @author Charlie Woo
	 */
	@RequestMapping(value = "/tabOrderPage")
	@ResponseBody
	public BaseJasonObject<TbSysOrderPage> tabOrderPage(@RequestParam("orderId") String orderId, Map<String, Object> model) {
		log.debug("getOutSourceTable = " + orderId);

		TbSiteWorkOrder siteWorkOrder = service.getOrderByKey(orderId);
		log.debug("siteWorkOrder = " + siteWorkOrder.getORDER_TYPE());

		TbSysOrderPageExample Pageexample = new TbSysOrderPageExample();
		Pageexample.createCriteria().andOD_TYPE_IDEqualTo(siteWorkOrder.getORDER_TYPE());
		List<TbSysOrderPage> sysOrderPage = service.selectByExample(Pageexample);

		log.info("sysOrderPage = " + sysOrderPage.size());
		return new BaseJasonObject<>(sysOrderPage, AJAX_SUCCESS, AJAX_EMPTY);
	}
	
	/**
	 * 作業內容-儲存
	 * @param orderId
	 * @param endDesc
	 * @param orderDesc
	 * @return
	 * @author Charlie Woo
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public BaseJasonObject<Object> save(@RequestParam("orderId") String orderId, @RequestParam("endDesc") String endDesc,
			@RequestParam("orderDesc") String orderDesc) {

		TbSiteWorkOrder siteWorkOrder = service.getOrderByKey(orderId);
		siteWorkOrder.setORDER_ID(orderId);
		siteWorkOrder.setORDER_DESC(orderDesc);
		siteWorkOrder.setEND_DESC(endDesc);
		siteWorkOrder.setMD_TIME(new Date());
		siteWorkOrder.setMD_USER(getLoginUser().getUsername());
		service.updateSiteWorkOrder(siteWorkOrder);// .updateSiteWorkOrderSelective(siteWorkOrder);

		return new BaseJasonObject<Object>(true, "存檔成功！");
	}

	/**
	 * 派工
	 * @param orderId
	 * @param maintainTeam
	 * @param maintainUser
	 * @param model
	 * @return
	 * @author Charlie Woo
	 */
	@RequestMapping(value = "/assignOrder")
	@ResponseBody
	public BaseJasonObject<Object> assignOrder(@RequestParam("orderId") String orderId, @RequestParam("maintainTeam") String maintainTeam,
			@RequestParam("maintainUser") String maintainUser, Map<String, Object> model) {
		boolean result = false;
		try {
			result = service.assignOrder(orderId, maintainTeam, maintainUser);
		} catch (NomsException nomsException) {
			log.error(nomsException.getMessage());
			return new BaseJasonObject<>(result, "派工失敗！");
		}
		return new BaseJasonObject<>(result, "派工完成！");
	}

	/**
	 * 接工
	 * @param orderId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/changePickWork")
	@ResponseBody
	public BaseJasonObject<Object> pickupOrder(@RequestParam("orderId") String orderId, Map<String, Object> model) {

		try {
			
			service.pickupOrder(orderId);

			return new BaseJasonObject<>(true, "接工完成！");
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
		}

		return new BaseJasonObject<>(false, "接工失敗！");
	}

	/**
	 * 完工(送審核)
	 * @param orderId
	 * @author Charlie Woo
	 * @throws Exception
	 */
	@RequestMapping("verifyOrder")
	@ResponseBody
	public BaseJasonObject<Object> verifyOrder(String workId, String orderId) throws Exception {
		try {
			String msg = service.doValidate(workId, orderId);
			if (msg != null) {
				return new BaseJasonObject<>(false, msg);
			}
			service.verifyOrder(orderId, workId, getLoginUser().getUsername());
		} catch (WorkflowException wfe) {
			log.error(wfe.getMessage(), wfe);
			return new BaseJasonObject<>(true, "工單編號：" + workId + "完工送審失敗！" + wfe.getMessage());
		}

		return new BaseJasonObject<>(true, "工單編號：" + workId + "完工送審成功！");
	}

	/**
	 * 退工
	 * @param workId
	 * @param endDesc
	 * @author Charlie Woo
	 */
	@RequestMapping(value = "/dropOrder")
	@ResponseBody
	public BaseJasonObject<Object> dropOrder(String workId, String endDesc) {
		BaseJasonObject<Object> jsonObj = new BaseJasonObject<>();
		try {
			List<String> errorMessageList = service.dropOrder(workId, endDesc);
			if (errorMessageList.isEmpty()) {
				jsonObj.setSuccess(true);
				jsonObj.setMsg("退工完成！");
			} else {
				jsonObj.setSuccess(false);
				jsonObj.setMsg("退工失敗！");
				jsonObj.setErrors(errorMessageList);
			}
		} catch (UpdateFailException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			jsonObj.setSuccess(false);
			jsonObj.setMsg("退工失敗！");
		} catch (NomsException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			jsonObj.setSuccess(false);
			jsonObj.setMsg("退工失敗！");
		}
		return jsonObj;
	}

	/**
	 * 重啟工單頁面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/reopenOrderPage")
	public String showReopenOrderPage(HttpServletRequest request, Map<String, Object> model) {
		model.put("targetFrameId", request.getParameter("targetFrameId"));
		return "/ajaxPage/st/ReopenOrder";
	}

	/**
	 * 重啟工單
	 * @param orderId
	 * @author Charlie Woo
	 */
	@RequestMapping(value = "/update/reopenOrder")
	@ResponseBody
	public Map<String, Object> reopenOrder(@RequestParam("orderId") String orderId, @RequestParam("reopenOrderDesc") String reopenOrderDesc,
			@RequestParam("workType") String workType) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			service.reopenOrder(orderId, reopenOrderDesc, workType);
			map.put("result", true);
		} catch (UpdateFailException e) {
			map.put("result", this.getMessageDetail(e.getErrCode()));
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return map;
	}
	
	/**
	 * 重啟工單(查工單列表)
	 * @param workId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/search/workOrder")
	@ResponseBody
	public BaseJasonObject<SiteWorkOrderDTO> getWorkOrderByWorkId(@RequestParam("workId") String workId, Map<String, Object> model) {
		List<SiteWorkOrderDTO> siteWorkOrderList = service.getSiteWorkOrderByWorkIdAndIsActive(workId, "Y");
		for (SiteWorkOrderDTO siteWorkOrder : siteWorkOrderList) {
			siteWorkOrder.setOrderStatusName(OrderStatus.detectLabel(siteWorkOrder.getODR_STATUS()));
		}
		model.put("siteWorkOrder", siteWorkOrderList);
		return new BaseJasonObject<>(siteWorkOrderList, AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 重啟工單(點選工單)
	 * @param workId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/search/workOrderObj")
	@ResponseBody
	public TbSiteWorkOrder getWorkOrderByOrderId(@RequestParam("orderId") String orderId, Map<String, Object> model) {
		TbSiteWorkOrder workOrder = service.getOrderByKey(orderId);
		return workOrder;
	}
}
