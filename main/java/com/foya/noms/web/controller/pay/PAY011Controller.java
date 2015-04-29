package com.foya.noms.web.controller.pay;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.foya.dao.mybatis.model.TbLsLocElec;
import com.foya.noms.dto.pay.TbPayElectricityDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestDtlDTO;
import com.foya.noms.dto.pay.TbPayRepartitionDTO;
import com.foya.noms.service.org.DomainService;
import com.foya.noms.service.pay.Pay011Service;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.JqGridData;
import com.foya.noms.web.controller.BaseController;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Controller
@RequestMapping(value = "/pay")
public class PAY011Controller extends BaseController {
	
	@Autowired
	private DomainService domainService;
	@Autowired
	private PayPublicUtilController payPublicUtilController;
	@Autowired
	private Pay011Service pay011Service;
	
	/**
	 * 初始頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay011/init")
	public String init(HttpServletRequest request,Map<String, Object> model) throws Exception{
		model.put("domainSelect", domainService.getStandardDomainList());
		model.put("electricityTypeSelect", payPublicUtilController.getPayLookupByType(request, "ELECTRICITY_TYPE"));
		return "ajaxPage/pay/PAY011";
	}

	/**
	 * 查詢
	 */
	@RequestMapping(value = "/pay011/search")
	@ResponseBody
	public JqGridData<TbPayElectricityDTO> search(
			@RequestParam(value = "domain", required = false) String domain,
			@RequestParam(value = "electricityType", required = false) String electricityType,
			@RequestParam(value = "btsSiteId", required = false) String btsSiteId,
			@RequestParam(value = "electricityMeterNbr", required = false) String electricityMeterNbr,
			@RequestParam(value = "paymentReqStartDate", required = false) Date paymentReqStartDate,
			@RequestParam(value = "paymentReqEndDate", required = false) Date paymentReqEndDate)
			throws Exception {
		List<TbPayElectricityDTO> payList = pay011Service.selectTbPayElectricity(domain, electricityType, btsSiteId,
						electricityMeterNbr, paymentReqStartDate,paymentReqEndDate);
		PageList<TbPayElectricityDTO> page = (PageList<TbPayElectricityDTO>) payList;
		return new JqGridData<TbPayElectricityDTO>(page.getPaginator().getTotalCount(), payList);
	}
	
	/**
	 * 查詢固定金額資料
	 */
	@RequestMapping(value = "/pay011/searchAmtData")
	@ResponseBody
	public JqGridData<TbPayPaymentRequestDtlDTO> searchAmtData(
			@RequestParam("contractNo") String contractNo,
			@RequestParam("electricityMeterNbr") String electricityMeterNbr,
			@RequestParam("appDate") String appDate,
			@RequestParam("paymentReqNo") String paymentReqNo)throws Exception {
		List<TbPayPaymentRequestDtlDTO> payList = pay011Service.selectTbPayPaymentRequestDtlByPAY011(contractNo, electricityMeterNbr, appDate, paymentReqNo);
		PageList<TbPayPaymentRequestDtlDTO> page = (PageList<TbPayPaymentRequestDtlDTO>) payList;
		return new JqGridData<TbPayPaymentRequestDtlDTO>(page.getPaginator().getTotalCount(),payList);
	}

	/**
	 * 用電明細頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay011/searchDtl")
	public String searchDtl(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value = "useTypeDscr", required = false) String useTypeDscr,
			@RequestParam(value = "yearMonth", required = false) String yearMonth,
			@RequestParam(value = "cashReqNo", required = false) String cashReqNo,
			@RequestParam(value = "contractNo", required = false) String contractNo,
			@RequestParam(value = "electricityMeterNbr", required = false) String electricityMeterNbr,
			@RequestParam(value = "contractName", required = false) String contractName,
			@RequestParam(value = "electricityDscr", required = false) String electricityDscr,
			@RequestParam(value = "siteName", required = false) String siteName,
			@RequestParam(value = "paymentReqBeginDate", required = false) Date paymentReqBeginDate,
			@RequestParam(value = "paymentReqEndDate", required = false) Date paymentReqEndDate,
			@RequestParam(value = "totalUsedDegree", required = false) String totalUsedDegree,
			@RequestParam(value = "usedDegree", required = false) String usedDegree,
			@RequestParam(value = "paymentReqAmt", required = false) String paymentReqAmt,
			@RequestParam(value = "memo", required = false) String memo,
			@RequestParam(value = "ifAutoDeductDscr", required = false) String ifAutoDeductDscr,
			@RequestParam(value = "ifNoSiteMappingDscr", required = false) String ifNoSiteMappingDscr) throws Exception{
		model.put("useTypeDscr", useTypeDscr);
		model.put("yearMonth", yearMonth);
		model.put("cashReqNo", cashReqNo);
		model.put("contractNo", contractNo);
		model.put("electricityMeterNbr", electricityMeterNbr);
		model.put("contractName", contractName);
		model.put("electricityDscr", electricityDscr);
		model.put("siteName", siteName);
		model.put("paymentReqBeginDate", paymentReqBeginDate);
		model.put("paymentReqEndDate", paymentReqEndDate);
		model.put("totalUsedDegree", totalUsedDegree);
		model.put("usedDegree", usedDegree);
		model.put("paymentReqAmt", paymentReqAmt);
		model.put("memo", memo);
		model.put("ifAutoDeductDscr", ifAutoDeductDscr);
		model.put("ifNoSiteMappingDscr", ifNoSiteMappingDscr);
		return "ajaxPage/pay/PAY011D";
	}

	/**
	 * 用電清單列印頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay011/openPrintView")
	public String openPrintView(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value = "contractNo", required = false) String contractNo,
			@RequestParam(value = "electricityMeterNbr", required = false) String electricityMeterNbr,
			@RequestParam(value = "paymentReqNo", required = false) String paymentReqNo,
			@RequestParam(value = "appDate", required = false) String appDate)throws Exception {
		model.put("spContractNo", contractNo);
		model.put("spPaymentReqNo", paymentReqNo);
		model.put("spAppDate", appDate);
		model.put("spElectricityMeterNbr", electricityMeterNbr);
		model.put("spSiteIdSelect", pay011Service.getSiteIdList(contractNo,electricityMeterNbr,appDate));// 基站編號下拉選單
		return "ajaxPage/pay/PAY011SP";
	}

	/**
	 * 取得excel所需資料
	 * 
	 * @return
	 */
	@RequestMapping(value = "/pay011/getExcelData")
	@ResponseBody
	public BaseJasonObject<Object> getExcelData(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model,
			@RequestParam("spContractNo") String spContractNo,
			@RequestParam("spPaymentReqNo") String spPaymentReqNo,
			@RequestParam("spAppDate") String spAppDate,
			@RequestParam("spElectricityMeterNbr") String spElectricityMeterNbr,
			@RequestParam("spSiteId") String spSiteId) throws Exception {
		HttpSession session = request.getSession();
        String locationId = "";
		// 移除session資料
		session.removeAttribute("pay011_excelMasterData");
		session.removeAttribute("pay011_excelDetailList");
		
		// 取得表頭用電地址、戶名及計算營業稅額所需站點代碼
		TbLsLocElec masterData = new TbLsLocElec();
		try {
			masterData = pay011Service.getTbLsLocElec(spContractNo, spSiteId,spAppDate, spElectricityMeterNbr);
			locationId = masterData.getLOCATION_ID();
		} catch (NullPointerException e) {
			return new BaseJasonObject<Object>(false, "查無資料!");
		}
		session.setAttribute("pay011_excelMasterData", masterData);
		
		// 取得報表內容資料from TB_PAY_REPARTITION
		List<TbPayRepartitionDTO> payList = pay011Service.selectTbPayRepartitionByPay011(spContractNo, locationId, spPaymentReqNo, spSiteId);

		// 有資料才產EXCEL報表,無資料則show查無資料
		if (payList.size() != 0) {
			session.setAttribute("pay011_excelDetailList", payList);
		} else {
			return new BaseJasonObject<Object>(false, "查無資料!");
		}
		return new BaseJasonObject<Object>(true, "");
	}
	
	/**
	 * 匯出excel
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/pay011/genExcel", method = RequestMethod.GET) 
	public void genExcel(HttpServletRequest request,HttpServletResponse response,
			Map<String, Object> model,
			@RequestParam("spElectricityMeterNbr") String spElectricityMeterNbr,
			@RequestParam("spSiteIdDscr") String spSiteIdDscr) throws Exception { 
		HttpSession session = request.getSession();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String toDay = sdf.format(new Date());
        String elecAddr = "";
        String elecCustName = "";
        
		// 設定file name
		String fileName = "PAY011-" + toDay + ".xls";
		
		// 取得表頭所需資料:用電戶名、地址、電號、SiteIdDscr
		TbLsLocElec masterData =(TbLsLocElec) session.getAttribute("pay011_excelMasterData");
		elecAddr = masterData.getELEC_ADDR();
		elecCustName = masterData.getELEC_CUST_NAME();
        String siteIdDscr = new String(spSiteIdDscr.getBytes("ISO-8859-1"), "UTF-8");
        
		// 取得憑證單號Call PAY_PC_GET_SEQNO
		String voucherNbr = pay011Service.selectVoucherNbrToday(toDay);
		
		// 重新取得報表內容資料
		List<TbPayRepartitionDTO> payList = (List<TbPayRepartitionDTO>) session.getAttribute("pay011_excelDetailList");

		// 執行產Excel
		pay011Service.exportExcel(response, "sheet", fileName, elecAddr,elecCustName, spElectricityMeterNbr, siteIdDscr, voucherNbr, payList);
    }
	
}
