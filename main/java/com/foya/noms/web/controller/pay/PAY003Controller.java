package com.foya.noms.web.controller.pay;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.dao.mybatis.model.TbAuthPersonnelExample;
import com.foya.dao.mybatis.model.TbPayCashRequirement;
import com.foya.dao.mybatis.model.TbPayCashRequirementExample;
import com.foya.dao.mybatis.model.TbPayLookupCode;
import com.foya.dao.mybatis.model.TbPayLookupCodeExample;
import com.foya.dao.mybatis.model.TbPayPaymentRequest;
import com.foya.dao.mybatis.model.UTbPayPaymentRequestVoucherExample;
import com.foya.exception.NomsException;
import com.foya.noms.dto.pay.TbLsSiteDTO;
import com.foya.noms.dto.pay.TbPayCashRequirementDTO;
import com.foya.noms.dto.pay.TbPayElectricityDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestVoucherDTO;
import com.foya.noms.service.org.DomainService;
import com.foya.noms.service.pay.PAY003Service;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.JqGridData;
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
 * <td>2016/02/01</td>
 * <td>新建檔案</td>
 * <td>Smile</td>
 * </tr>
 * </table>
 * 
 * @author Smile
 * 
 */
@Controller
@RequestMapping(value = "/pay")
public class PAY003Controller extends BaseController {

	@Autowired
	private PAY003Service pay003Service;

	@Autowired
	private DomainService domainService;

	/**
	 * 初始頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay003/init")
	public String init(HttpServletRequest request, Map<String, Object> model) {
		HttpSession session = request.getSession();

		session.setAttribute("pay003_orgDomainList", domainService.getStandardDomainList()); // 維運區
		TbPayLookupCodeExample example = new TbPayLookupCodeExample();
		example.createCriteria().andLOOKUP_TYPEEqualTo("PROCESS_TYPE").andLOOKUP_CODELike("E%");
		session.setAttribute("pay003_processList", pay003Service.selectPayLookupCodeByExample(example)); // 處理類別
		session.setAttribute("pay003_authPersonnelList", pay003Service.selectAuthPersonnelByExample(new TbAuthPersonnelExample())); // 申請人
		
		example.clear();
		example.createCriteria().andLOOKUP_TYPEEqualTo("cash_req_status");
		session.setAttribute("pay003_statusList", pay003Service.selectPayLookupCodeByExample(example)); // 申請狀態

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -7);
		model.put("cr_time_s", cal.getTime()); // 申請起日
		model.put("cr_time_e", new Date()); // 申請迄日

		return "ajaxPage/pay/PAY003";
	}

	/**
	 * 查詢驗收清單
	 * @param request
	 * @param domain 維運區
	 * @param coVatNo 業者
	 * @param poNo PO
	 * @param crTimeS 申請起日
	 * @param crTimeE 申請迄日
	 * @param status 申請狀態
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pay003/query")
	@ResponseBody
	public JqGridData<TbPayCashRequirementDTO> query(@RequestParam(value = "domain", required = false) String domain,
			@RequestParam(value = "processType", required = false) String processType,
			@RequestParam(value = "paymentPeriod", required = false) String paymentPeriod,
			@RequestParam(value = "appStartDate", required = false) Date appStartDate,
			@RequestParam(value = "appEndDate", required = false) Date appEndDate,
			@RequestParam(value = "cashReqNo", required = false) String cashReqNo,
			@RequestParam(value = "status", required = false) String status) throws Exception {

		log.debug("輸入值domain:" + domain + ",processType:"+ processType + ",paymentPeriod:" + paymentPeriod
				+ ",appStartDate:" + appStartDate+ ",appEndDate:" + appEndDate
				+ ",cashReqNo:" + cashReqNo + ",status:" + status);
		String[] statusStr = status.split(",");
		Set<String> statusList = new HashSet<String>();
		for (int g=0; g<statusStr.length; g++) {
			statusList.add(statusStr[g]);
		}
		List<TbPayCashRequirementDTO> payList = pay003Service.searchForPay003(domain, processType, paymentPeriod, appStartDate, appEndDate, cashReqNo, statusList);
		
		PageList<TbPayCashRequirementDTO> page = (PageList<TbPayCashRequirementDTO>) payList;
		return new JqGridData<TbPayCashRequirementDTO>(page.getPaginator().getTotalCount(), payList);
	}
	
	/**
	 * 明細頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay003/searchDtl")
	public String searchDtl(Map<String, Object> model,
			@RequestParam(value = "cashReqNo", required = false) String cashReqNo) throws Exception{
		TbPayCashRequirementExample exampleCash = new TbPayCashRequirementExample();
		exampleCash.createCriteria().andCASH_REQ_NOEqualTo(cashReqNo);
		List<TbPayCashRequirement> list = pay003Service.checkDataCount(exampleCash);
		TbAuthPersonnelExample exampleAuth = new TbAuthPersonnelExample();
		exampleAuth.createCriteria().andPSN_NOEqualTo(list.get(0).getAPP_USER());
		List<TbAuthPersonnel> listAuth = pay003Service.selectTbAuthPersonnel(exampleAuth);
		model.put("domainSelect", domainService.getStandardDomainList());
		TbPayLookupCodeExample example = new TbPayLookupCodeExample();
		example.createCriteria().andLOOKUP_TYPEEqualTo("PROCESS_TYPE").andLOOKUP_CODELike("E%");
		model.put("processSelect", pay003Service.selectPayLookupCodeByExample(example));
		model.put("authPersonnelSelect", pay003Service.selectAuthPersonnelByExample(new TbAuthPersonnelExample()));
		model.put("appDate", list.get(0).getAPP_DATE()== null? "":list.get(0).getAPP_DATE());
		model.put("appUserName", listAuth.get(0).getCHN_NM()== null? "":listAuth.get(0).getCHN_NM());
		model.put("cashReqNo", cashReqNo); 
		model.put("domain", list.get(0).getDOMAIN()== null? "":list.get(0).getDOMAIN()); 
		model.put("processType", list.get(0).getPROCESS_TYPE()== null? "":list.get(0).getPROCESS_TYPE());
		model.put("yearMonth", list.get(0).getYEAR_MONTH() == null? "": list.get(0).getYEAR_MONTH().substring(0, 4) + "/" + list.get(0).getYEAR_MONTH().substring(list.get(0).getYEAR_MONTH().length()-2, list.get(0).getYEAR_MONTH().length()));
		return "ajaxPage/pay/PAY003D";
	}
	
	/**
	 * 修改
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay003/edit")
	public String edit(Map<String, Object> model,
			@RequestParam(value = "cashReqNo", required = false) String cashReqNo,
			@RequestParam(value = "domain", required = false) String domain,
			@RequestParam(value = "processType", required = false) String processType,
			@RequestParam(value = "appUser", required = false) String appUser,
			@RequestParam(value = "appUserName", required = false) String appUserName,
			@RequestParam(value = "appDate", required = false) Date appDate,
			@RequestParam(value = "yearMonth", required = false) String yearMonth) throws Exception{
		log.debug("ajaxPage/pay/PAY003E");
		model.put("domainSelect", domainService.getStandardDomainList());
		TbPayLookupCodeExample example = new TbPayLookupCodeExample();
		example.createCriteria().andLOOKUP_TYPEEqualTo("PROCESS_TYPE").andLOOKUP_CODELike("E%");
		model.put("processSelect", pay003Service.selectPayLookupCodeByExample(example));
		model.put("authPersonnelSelect", pay003Service.selectAuthPersonnelByExample(new TbAuthPersonnelExample()));
		model.put("appDate", appDate);
		model.put("appUser", appUser);
		model.put("appUserName", appUserName);
		model.put("cashReqNo", cashReqNo); 
		model.put("domain", domain); 
		model.put("processType", processType);
		model.put("yearMonth", yearMonth.substring(0, 4) + "/" + yearMonth.substring(yearMonth.length()-2, yearMonth.length()));
		return "ajaxPage/pay/PAY003E";
	}
	
	/**
	 * 新增頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay003/addPage")
	public String addPage(HttpServletRequest request,
			Map<String, Object> model) throws Exception{
		model.put("domainSelect", domainService.getStandardDomainList());
		TbPayLookupCodeExample example = new TbPayLookupCodeExample();
		example.createCriteria().andLOOKUP_TYPEEqualTo("PROCESS_TYPE").andLOOKUP_CODELike("E%");
		model.put("processSelect", pay003Service.selectPayLookupCodeByExample(example));
		model.put("appDate", new Date());
		model.put("yearMonth", new SimpleDateFormat("yyyyMM").format(new Date()));
		return "ajaxPage/pay/PAY003A";
	}
	
	
	/**
	 * 新增-資料處理動作-請款清單
	 */
	@RequestMapping(value = "/pay003/searchDetail1ByProcess")
	@ResponseBody
	public JqGridData<TbPayPaymentRequestDTO> searchDetail1ByProcess(
			@RequestParam("domain") String domain,
			@RequestParam("processType") String processType,
			@RequestParam("appUser") String appUser,
			@RequestParam("appDate") Date appDate,
			@RequestParam("yearMonth") String yearMonth,
			@RequestParam("paymentPeriod") String paymentPeriod){	
		List<TbPayPaymentRequestDTO> list=pay003Service.selectProcess(domain, processType, appUser, appDate, yearMonth,paymentPeriod);
		
		return new JqGridData<TbPayPaymentRequestDTO>(list.size(),list);
	}
	
	/**
	 * 明細頁面清單  grid2 ADD
	 */ 
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/pay003/selectDtl4PAY003ATb2")
	@ResponseBody
	public BaseJasonObject<Map<String, Object>> selectDtl4PAY003ATb2(@RequestParam(value = "contractNo", required = false)String contractNo,
			@RequestParam(value = "domain", required = false) String domain,
			@RequestParam(value = "paymentReqBeginDate", required = false) Date paymentReqBeginDate,
			@RequestParam(value = "paymentReqEndDate", required = false) Date paymentReqEndDate,
			@RequestParam(value = "appDate", required = false) Date appDate,
			@RequestParam(value = "paymentMonth", required = false) String paymentMonth,
			@RequestParam(value = "processType", required = false) String processType
			){

		Map<String, Object> map = pay003Service.selectDtl4PAY003ATb2(contractNo, domain, paymentReqBeginDate, paymentReqEndDate, appDate, paymentMonth, processType);

			return new BaseJasonObject(map, AJAX_SUCCESS, AJAX_EMPTY);
			
	}
	
	/**
	 * 新增-資料處理動作-租約站點清單 & 基站請款記錄 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/pay003/searchDetail2ByProcess")
	@ResponseBody
	public BaseJasonObject<Map<String, Object>> searchDetail2ByProcess(
			@RequestParam("contractNo") String contractNo,
			@RequestParam("domain") String domain,
			@RequestParam("paymentReqBeginDate") Date paymentReqBeginDate,
			@RequestParam("paymentReqEndDate") Date paymentReqEndDate,
			@RequestParam("appDate") Date appDate,
			@RequestParam("paymentMonth") String paymentMonth,
			@RequestParam("processType") String processType){	
		Map<String, Object> map=pay003Service.selectDetail2ByProcess(
				contractNo, domain, paymentReqBeginDate, paymentReqEndDate, appDate, paymentMonth, processType)	;	 
		
		return new BaseJasonObject(map, AJAX_SUCCESS, AJAX_EMPTY);
	}	
//	/**
//	 * 新增-資料處理動作-租約站點清單 & 基站請款記錄 (全取)
//	 */
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	@RequestMapping(value = "/pay001/searchDetail2AllByProcess")
//	@ResponseBody
//	@JsonIgnoreProperties(ignoreUnknown = true)
//	public BaseJasonObject<Map<String, Object>> searchDetail2AllByProcess(
//			@RequestBody List<TbPayPaymentRequestDTO> list){
//		Map<String, Object> map = null;
//		Map<Integer, Object> grid2map = new HashMap<Integer, Object>();
//		Map<Object, Object> returnMap = new HashMap<Object, Object>();
//		List<TbPayPaymentDTO> grid3List = new ArrayList<TbPayPaymentDTO>();
//		for(TbPayPaymentRequestDTO vo : list){
//			map=pay001Service.selectDetail2ByProcess(
//					vo.getCONTRACT_NO(), vo.getDomain(), 
//					vo.getPAYMENT_REQ_BEGIN_DATE(), vo.getPAYMENT_REQ_END_DATE(), 
//					vo.getAppDate(), vo.getYearMonth(), vo.getProcessType());	
//			grid2map.put(vo.get_id(), map.get("grid2"));
//			grid3List.addAll((List<TbPayPaymentDTO>) map.get("grid3"));
//		}
//		returnMap.put("grid2All", grid2map);
//		returnMap.put("grid3", grid3List);
//		return new BaseJasonObject(returnMap, AJAX_SUCCESS, AJAX_EMPTY);
//	}	
	
	/**
	 * 新增-請款
	 */
	@RequestMapping(value = "/pay003/money")
	@ResponseBody
	@JsonIgnoreProperties(ignoreUnknown = true)
	public BaseJasonObject<Object> money(
			@RequestBody TbPayCashRequirementDTO tbPayCashRequirementDTO){
		BaseJasonObject<Object> json=new BaseJasonObject<Object>(true, null);
		String cashReqNo = null;
		try{
			TbPayCashRequirementExample example = new TbPayCashRequirementExample();
			example.createCriteria().andYEAR_MONTHEqualTo(tbPayCashRequirementDTO.getYEAR_MONTH()).andDOMAINEqualTo(tbPayCashRequirementDTO.getDOMAIN()).andPROCESS_TYPEEqualTo(tbPayCashRequirementDTO.getPROCESS_TYPE()).andCASH_REQ_NOLike("ELP%");
			List<TbPayCashRequirement> list = pay003Service.checkDataCount(example);
			if(list.size() > 0){
				//"維運區= "+ cashReqNo +", 處理類別=" + + ", 請款年月="+tbPayCashRequirementDTO.getYEAR_MONTH()+
			json =	new BaseJasonObject<Object>(true, "已有請款資料,請重新操作!");	
			}else{
			cashReqNo = pay003Service.money(tbPayCashRequirementDTO);
			json = new BaseJasonObject<Object>(true, "請款單號 "+ cashReqNo +" 待審中!");	
			}
		}catch(Exception e){
			json=new BaseJasonObject<Object>(false, e.getMessage());			
		}		
		return json;
	}	
	
	/**
	 * 輸入憑證初始頁面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/pay003/sp1/init")
	public String sp1Init(HttpServletRequest request,
			Map<String, Object> model, @RequestParam("sPcashReqNo") String sPcashReqNo,
			@RequestParam("sPcontractNo") String sPcontractNo,
			@RequestParam("sPlsName") String sPlsName,
			@RequestParam("sPsumAmt") String sPsumAmt) {
		log.debug("ajaxPage/pay/PAY003SP1");
		model.put("sPcashReqNo", sPcashReqNo);
		model.put("sPcontractNo", sPcontractNo);
		model.put("sPlsName", sPlsName);
		model.put("sPsumAmt", sPsumAmt);
		return "ajaxPage/pay/PAY003SP1";
	}
	
	/**
	 * 取得憑證類別
	 * 
	 * @param deptId
	 * @return
	 */
	@RequestMapping(value = "/pay003/search/voucherType")
	@ResponseBody
	public Map<String, String> getVoucherType() {
		TbPayLookupCodeExample example = new TbPayLookupCodeExample();
		example.createCriteria().andLOOKUP_TYPEEqualTo("VOUCHER_TYPE");
		List<TbPayLookupCode> list = pay003Service.selectPayLookupCodeByExample(example);

		Map<String, String> map = new LinkedHashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			TbPayLookupCode tb = list.get(i);
			map.put(tb.getLOOKUP_CODE(), tb.getLOOKUP_CODE_DESC());
		}

		return map;
	}
	
	/**
	 * 取得工程驗收清單資料
	 * @param request
	 * @param apNo 驗收單號
	 * @param poNo
	 * @return
	 */
	@RequestMapping(value = "/pay003/sp1/queryPaymentRequestVoucher")
	@ResponseBody
	public JqGridData<TbPayPaymentRequestVoucherDTO> queryPaymentRequestVoucher(
			HttpServletRequest request, @RequestParam("ap_no") String apNo) {

		UTbPayPaymentRequestVoucherExample example = new UTbPayPaymentRequestVoucherExample();
		UTbPayPaymentRequestVoucherExample.Criteria cr = example.createCriteria();
		cr.andPROCESS_TYPEEqualTo("CRQ").andEqualTo("B.CASH_REQ_AP_NO", apNo);

		List<TbPayPaymentRequestVoucherDTO> list = pay003Service.selectPayPaymentRequestVoucherByExamplePage(example);
		PageList<TbPayPaymentRequestVoucherDTO> page = (PageList<TbPayPaymentRequestVoucherDTO>) list;
		return new JqGridData<TbPayPaymentRequestVoucherDTO>(page.getPaginator().getTotalCount(), list);
	}
	
	/**
	 * 明細頁面清單 Master grid
	 */ 
	@RequestMapping(value = "/pay003/searchRequiRement")
	@ResponseBody
	public JqGridData<TbPayPaymentRequestDTO> searchRequiRement(
			@RequestParam(value = "cashReqNo", required = false) String cashReqNo,
			@RequestParam(value = "processType", required = false) String processType
			) throws Exception{
			log.debug("輸入值cashreqno:" + cashReqNo + ",processType:"+ processType);

			List<TbPayPaymentRequestDTO> payList = pay003Service.searchForPay003DtlMaster(cashReqNo, processType);
			
			PageList<TbPayPaymentRequestDTO> page = (PageList<TbPayPaymentRequestDTO>) payList;
			return new JqGridData<TbPayPaymentRequestDTO>(page.getPaginator().getTotalCount(), payList);
	}
	
	/**
	 * 明細頁面清單 Master grid
	 */ 
	@RequestMapping(value = "/pay003/searchRequiRement2")
	@ResponseBody
	public JqGridData<TbPayElectricityDTO> searchRequiRement2(
			@RequestParam(value = "contractNo", required = false) String contractNo,
			@RequestParam(value = "yearMonth", required = false) String yearMonth,
			@RequestParam(value = "paymentReqNo", required = false) String paymentReqNo
			) throws Exception{
			log.debug("Table2輸入值contractNo:" + contractNo + ",yearMonth:"+ yearMonth.substring(0, 4) + yearMonth.substring(yearMonth.length()-2, yearMonth.length())+",paymentReqNo:" + paymentReqNo);

			List<TbPayElectricityDTO> payList = pay003Service.searchForPay003DtlTable2(contractNo, yearMonth.substring(0, 4) + yearMonth.substring(yearMonth.length()-2),paymentReqNo);

			PageList<TbPayElectricityDTO> page = (PageList<TbPayElectricityDTO>) payList;
			return new JqGridData<TbPayElectricityDTO>(page.getPaginator().getTotalCount(), payList);
			
	}
	

	
	/**
	 * 明細頁面清單 Master grid
	 */ 
	@RequestMapping(value = "/pay003/searchRequiRement3")
	@ResponseBody
	public JqGridData<TbLsSiteDTO> searchRequiRement3(
			@RequestParam(value = "contractNo", required = false) String contractNo,
			@RequestParam(value = "electricityMeterNbr", required = false) String electricityMeterNbr,
			@RequestParam(value = "paymentReqBeginDate", required = false) Date paymentReqBeginDate
			) throws Exception{
			log.debug("Table3輸入值contractNo:" + contractNo + ",electricityMeterNbr:"+ electricityMeterNbr + ",paymentReqBeginDate:"+ paymentReqBeginDate);

			List<TbLsSiteDTO> payList = pay003Service.selectDtlForPAY003Table3(contractNo,electricityMeterNbr,paymentReqBeginDate);
			
			PageList<TbLsSiteDTO> page = (PageList<TbLsSiteDTO>) payList;
			return new JqGridData<TbLsSiteDTO>(page.getPaginator().getTotalCount(), payList);
			
	}
	
	/**
	 * 新增、修改憑證資料
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pay003/sp1/creditAddEdit")
	@ResponseBody
	public BaseJasonObject<Object> creditAddEdit(HttpServletRequest request) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("mst_ap_no", request.getParameter("mst_ap_no")); // 驗收單號
			map.put("mst_po_no", request.getParameter("mst_po_no")); // 驗收PO
			map.put("id", request.getParameter("id")); // jgGrid資料ID
			map.put("voucher_type", request.getParameter("voucher_type")); // 憑證類別
			map.put("voucher_nbr", request.getParameter("voucher_nbr")); // 憑証號碼
			map.put("voucher_date", request.getParameter("voucher_date")); // 憑證日期
			map.put("vat_number", request.getParameter("vat_number")); // 統一編號
			map.put("tax_exclusive_amt", request.getParameter("tax_exclusive_amt")); // 未稅金額
				pay003Service.creditSave(map,"E");
				return new BaseJasonObject<Object>(true, getMessageDetail("msg.add.success"));

		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			return new BaseJasonObject<Object>(false, e.getMessage());
		}
	}

	
	/**
	 * 送審
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/pay003/updateData")
	@ResponseBody
	@Transactional
	public BaseJasonObject<Object> updateData(@RequestParam(value = "cashReqNo", required = false) String cashReqNo) {
		try {
			pay003Service.updateByPAY003(cashReqNo,getLoginUser().getUsername());
			return new BaseJasonObject<Object>(true, "請款單號 : " + cashReqNo+"送審中");
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			return new BaseJasonObject<Object>(true, e.getMessage());
		}
	}
	
	/**
	 * 刪除
	 */
	@RequestMapping(value = "/pay003/deleteData")
	@ResponseBody
	@Transactional
	public BaseJasonObject<Object> deleteData(@RequestBody List<TbPayPaymentRequest> delArray) {
			try{
				if(delArray.get(0).getPAYMENT_REQ_NO() != null){
				pay003Service.deletePayPaymentRequestDtl(delArray);
				}
				return new BaseJasonObject<Object>(true, "請款單號 : " + delArray.get(0).getCASH_REQ_NO()+"變更儲存成功!");
			} catch (Exception e) {
				log.error(ExceptionUtils.getFullStackTrace(e));
				return new BaseJasonObject<Object>(true, e.getMessage());
			}
	}
	
	 /**
     * 供WORKFLOW審核呼叫
     * @param orderId 庫存單號或調撥單號
     * @param action  SUCCESS/REJECT
     * @return String
     */
    @RequestMapping(value = "/pay003/applyByWF")
	@ResponseBody
    public String applyByWF( @RequestParam("orderId")String orderId, @RequestParam("action") String action )throws NomsException{
    	log.debug("orderId : "+orderId+",action : "+action);   
    	String msg = "success";
    	try{
    		pay003Service.applyByWF(orderId, action); 
		}catch(Exception e){
			msg = "fail";	
		}		
        return msg;
    }
}
