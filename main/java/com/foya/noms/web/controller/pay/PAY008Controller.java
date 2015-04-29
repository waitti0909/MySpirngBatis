package com.foya.noms.web.controller.pay;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbPayCashRequirement;
import com.foya.dao.mybatis.model.TbPayLookupCode;
import com.foya.noms.dto.inv.TbInvTrDtlCompleteDTO;
import com.foya.noms.dto.ls.TbLsLocationCompleteDTO;
import com.foya.noms.dto.pay.TbPayPaymentCompleteDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestCompleteDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestDtlCompleteDTO;
import com.foya.noms.service.org.DomainService;
import com.foya.noms.service.pay.PAY008Service;
import com.foya.noms.service.pay.PayPublicUtilService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.JqGridData;
import com.foya.noms.web.controller.BaseController;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Controller
@RequestMapping(value = "/pay")
public class PAY008Controller extends BaseController {

	// --------------------------------------------------------------------
	/**
	 * 
	 */
	@Autowired
	private PayPublicUtilController payPublicUtilController;

	/**
	 * 
	 */
	@Autowired
	private DomainService domainService;

	/**
	 * 
	 */
	@Autowired
	private PAY008Service pay008Service;

	@Inject
	private PayPublicUtilService payPublicUtilService;

	// --------------------------------------------------------------------
	// --------------------------------------------------------------------
	// --------------------------------------------------------------------

	/**
	 * 初始頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay008/init")
	public String init(HttpServletRequest request, Map<String, Object> model) throws Exception {

		/*************************************************************************************
		 * 此功能尚未完成，無足夠正確資料測試。<BR>
		 * 文件需求尚未明確。<BR>
		 *************************************************************************************/

		model.put("standardDomainList", domainService.getStandardDomainList());
		model.put("payLookUpList", payPublicUtilController.getPayLookupByType(request, "pr_type"));
		model.put("reasonList", stopPaymentList(payPublicUtilController.getPayLookupByType(request, "suspend_reason")));

		return "ajaxPage/pay/PAY008";
	}

	/**
	 * 止付原因 組裝
	 */
	private String stopPaymentList(List<TbPayLookupCode> list) {

		StringBuffer buffer = new StringBuffer();

		for (TbPayLookupCode lookUp : list) {
			buffer.append(lookUp.getLOOKUP_CODE() + ":" + lookUp.getLOOKUP_CODE_DESC() + ";");
		}

		buffer.deleteCharAt(buffer.length() - 1);

		return buffer.toString();
	}

	/**
	 * 查詢止付清單
	 * 
	 * @param request
	 *            request
	 */
	@RequestMapping(value = "/pay008/getStopPayData")
	@ResponseBody
	public BaseJasonObject<TbPayPaymentRequestCompleteDTO> getStopPayData(HttpServletRequest request) {

		String cashReqNo = request.getParameter("cashReqNo");
		String domain = request.getParameter("domain");

		List<TbPayPaymentRequestCompleteDTO> cashRequirementDtoList = pay008Service.getPayRequirementData(cashReqNo, null, null, null);
		// List<TbPayPaymentRequestCompleteDTO> cashRequirementDtoList =
		// pay008Service.getPayRequirementData(cashReqNo, domain, "F", "N");

		// PageList<TbPayPaymentRequestCompleteDTO> page =
		// (PageList<TbPayPaymentRequestCompleteDTO>) cashRequirementDtoList;
		return new BaseJasonObject<TbPayPaymentRequestCompleteDTO>(cashRequirementDtoList, "讀取成功", AJAX_SUCCESS);
	}

	/**
	 * 取得請款單號
	 * 
	 * @param payType
	 *            止付類型
	 * @param domain
	 *            維運區
	 * @return List<TbPayCashRequirement>
	 */
	@RequestMapping(value = "/pay008/getPayNumber")
	@ResponseBody
	public BaseJasonObject<TbPayCashRequirement> getPayNumber(@RequestParam("payType") String payType, @RequestParam("domain") String domain) {

		List<TbPayCashRequirement> payNumberList = pay008Service.getPayRequirementNo(payType, domain);

		return new BaseJasonObject<TbPayCashRequirement>(payNumberList, AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 取得合約站台檔
	 * 
	 * @param contractNO
	 *            租約編號
	 * @param paymentReqNo
	 *            請款編號
	 * @param appDate
	 *            申請日期
	 * @return BaseJasonObject<TbLsLocationCompleteDTO>
	 */
	@RequestMapping(value = "/pay008/getLsLocationData")
	@ResponseBody
	public BaseJasonObject<TbPayPaymentRequestDtlCompleteDTO> getLsLocationData(@RequestParam("contractNO") String contractNO, @RequestParam("paymentReqNo") String paymentReqNo,
			@RequestParam("appDate") String appDate) {

		Date date = new Date(Long.parseLong(appDate));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		System.out.println(contractNO + " , " + paymentReqNo + " , " + sdf.format(date));

		// List<TbLsLocationCompleteDTO> lsLocationDtoList =
		// pay008Service.getLsLocationData(contractNO, paymentReqNo,
		// sdf.format(date));
		List<TbPayPaymentRequestDtlCompleteDTO> requestDtoList = pay008Service.getContractDetailData(contractNO, paymentReqNo, sdf.format(date));

		// if (lsLocationDtoList.size() == 0) {
		// for (int i = 0; i < 1; i++) {
		// TbLsLocationCompleteDTO dto = new TbLsLocationCompleteDTO();
		// dto.setLOCATION_ID("123");
		// dto.setPAY_BEGIN_DATE(new Date());
		// dto.setEND_DATE(new Date());
		// dto.setRENT_AMT(new BigDecimal(12684));
		// dto.setPayLocationInfoEndDate(new Date());
		// dto.setPrepaidBalanceSum(12345678);
		// lsLocationDtoList.add(dto);
		// }
		// }

		return new BaseJasonObject<TbPayPaymentRequestDtlCompleteDTO>(requestDtoList, AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 取得合約站台檔
	 * 
	 * @param contractNo
	 *            租約編號
	 * @param locationId
	 *            站點代碼
	 * @param paymentReqNo
	 *            請款編號
	 * @param appDate
	 *            申請日期
	 * @return BaseJasonObject<TbPayPaymentCompleteDTO>
	 */
	@RequestMapping(value = "/pay008/getPayPaymentData")
	@ResponseBody
	public BaseJasonObject<TbPayPaymentCompleteDTO> getPayPaymentData(@RequestParam("contractNo") String contractNo, @RequestParam("locationId") String locationId,
			@RequestParam("paymentReqNo") String paymentReqNo, @RequestParam("appDate") String appDate, @RequestParam("itemNo") String itemNo) {

		Date date = new Date(Long.parseLong(appDate));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		return new BaseJasonObject<TbPayPaymentCompleteDTO>(pay008Service.getPayPaymentData(contractNo, locationId, paymentReqNo, sdf.format(date),itemNo), AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 更新請款資料
	 * 
	 */
	@RequestMapping(value = "/pay008/updatePaymentRequest")
	@ResponseBody
	public boolean updatePaymentRequest(@RequestParam("rowDataList") String rowData) throws Exception {
		try {
			Date today = payPublicUtilService.getToday();
			pay008Service.updatePaymentRequest(rowData, today);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
