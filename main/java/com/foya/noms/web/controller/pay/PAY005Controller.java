package com.foya.noms.web.controller.pay;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.dao.mybatis.model.TbAuthPersonnelExample;
import com.foya.dao.mybatis.model.TbPayCashRequirement;
import com.foya.exception.NomsException;
import com.foya.noms.dto.pay.TbPayCashRequirementDTO;
import com.foya.noms.dto.pay.TbPayPaymentDTO;
import com.foya.noms.dto.pay.TbPayRepartitionDTO;
import com.foya.noms.resources.AppConstants;
import com.foya.noms.service.auth.PersonnelService;
import com.foya.noms.service.org.DomainService;
import com.foya.noms.service.pay.Pay005Service;
import com.foya.noms.service.pay.PayPublicUtilService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.JqGridData;
import com.foya.noms.web.controller.BaseController;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Controller
@RequestMapping(value = "/pay")
public class PAY005Controller extends BaseController {
	@Autowired
	private PayPublicUtilController payPublicUtilController;
	@Autowired
	private DomainService domainService;
	@Autowired
	private PersonnelService personnelService;
	@Autowired
	private Pay005Service pay005Service;
	@Autowired 
	private PayPublicUtilService payPublicUtilService;
	/**
	 * 初始頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay005/init")
	public String init(HttpServletRequest request,
			Map<String, Object> model) throws Exception{
		model.put("domainList", domainService.getStandardDomainList());
		model.put("statusList", payPublicUtilService.getPayLookupByType("CASH_REQ_STATUS")); // 申請狀態
		return "ajaxPage/pay/PAY005";
	}	
	/**
	 * 修改頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	
	@RequestMapping(value = "/pay005/editPage")
	public String editPage(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam("cashNo") String cashNo,
			@RequestParam("appUser") String appUser,
			@RequestParam("appUserName") String appUserName,
			@RequestParam("appDate") Date appDate,
			@RequestParam("yearMonth") String yearMonth,
			@RequestParam("domain") String domain) throws Exception{
		model.put("cashNo", cashNo);
		model.put("appUser", appUser); 
		model.put("appUserName", appUserName);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		String dateString = formatter.format(appDate);
		model.put("appDate", dateString); 
		model.put("yearMonth", yearMonth); 
		model.put("yearMonthSlash", yearMonth.substring(0, yearMonth.length()-2)+"/"+yearMonth.substring(yearMonth.length()-2, yearMonth.length())); 
		model.put("domainId", domain); 
		model.put("domainList", domainService.getStandardDomainList());
		return "ajaxPage/pay/PAY005E";
	}
	/**
	 * 新增頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay005/addPage")
	public String addPage(HttpServletRequest request,
			Map<String, Object> model) throws Exception{
		model.put("domainSelect", domainService.getStandardDomainList());
		//model.put("prItemList", payPublicUtilService.getPayLookupByType("PR_ITEM")); // 雜項項目
		model.put("prItemList", pay005Service.getPaymentItem()); // 雜項項目
		model.put("voucherList", payPublicUtilService.getPayLookupByType("VOUCHER_TYPE")); // 憑證類別
		model.put("paymentList", payPublicUtilService.getPayLookupByType("PAYMENT_METHOD")); // 付款方式
		//model.put("bankList", payPublicUtilService.getPayLookupByType("BANK_CODE")); // 銀行
		//model.put("branchList", payPublicUtilService.getPayLookupByType("BANK_BRANCH_CODE")); //分行
		model.put("bankList", payPublicUtilService.getTbLsCollectUnit()); // 銀行
		return "ajaxPage/pay/PAY005A";
	}

	/**
	 * 基站頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay005/share")
	public String share(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value = "backFun", required = false) String backFun,
			@RequestParam(value = "gridId", required = false) String gridId,
			@RequestParam(value = "totalAmt", required = false) String totalAmt,
			@RequestParam(value = "edit", required = false) String edit,
			@RequestParam(value = "paymentReqNo", required = false) String paymentReqNo
			) throws Exception{
		model.put("gridId",gridId);
		model.put("totalAmt",totalAmt);
		model.put("backFun",backFun);
		model.put("edit",edit);
		return "ajaxPage/pay/PAY005SP1";
	}
	
	/**
	 * 查詢
	 */ 
	@RequestMapping(value = "/pay005/search")
	@ResponseBody
	public JqGridData<TbPayCashRequirementDTO> search(
			@RequestParam(value = "domain", required = false) String domain,
			@RequestParam(value = "cashReqNo", required = false) String cashReqNo,
			@RequestParam(value = "crStartDate", required = false) Date crStartDate,
			@RequestParam(value = "crEndDate", required = false) Date crEndDate,
			@RequestParam(value = "status", required = false) String status) throws Exception{
		String[] statusStr = status.split(",");
		List<String> statusList = new ArrayList<String>();
		for (int g=0; g<statusStr.length; g++) {
			statusList.add(statusStr[g]);
		}
		List<TbPayCashRequirementDTO> cashList=null;
		try{
			cashList=pay005Service.selectCashAllData(cashReqNo, domain, crStartDate, crEndDate, statusList);
			PageList<TbPayCashRequirementDTO> page = (PageList<TbPayCashRequirementDTO>) cashList;
			return new JqGridData<TbPayCashRequirementDTO>(page.getPaginator().getTotalCount(),cashList);
		}catch(NullPointerException e){
			return new JqGridData<TbPayCashRequirementDTO>(10,cashList);
		}
	}

	/**
	 * 取得請款單號
	 */
	@RequestMapping(value = "/pay005/getPayCashReqNo")
	@ResponseBody
	public BaseJasonObject<Object> getPayCashReqNo(@RequestParam("initWord") String initWord){
		return new BaseJasonObject<Object>(true,pay005Service.getPayCashReqNo(initWord));
	}
	/**
	 * 取得請款單號
	 */
	@RequestMapping(value = "/pay005/getPayVoucherNo")
	@ResponseBody
	public BaseJasonObject<Object> getPayVoucherNo(@RequestParam("initWord") String initWord){
		return new BaseJasonObject<Object>(true,pay005Service.getPayCashReqNo(initWord));
	}
	
	/**
	 * 新增CashRequirement PaymentRequest PaymentRequestVoucher TB_PAY_PAYMENT_REQUEST_DTL TB_PAY_PAYMENT TB_PAY_VOUCHER_CREDIT
	 */
	@RequestMapping(value = "/pay005/saveToTable")
	@ResponseBody
	public BaseJasonObject<Object> saveToTable(@RequestParam("paymentVou") String paymentVou,
			@RequestParam("paymentReq") String paymentReq,
			@RequestParam("cashReq") String cashReq,
			@RequestParam("paymentDTO") String paymentDTO,
			@RequestParam("cashReqNo") String cashReqNo,
			@RequestParam("yearMonth") String yearMonth) {
		Date today=payPublicUtilService.getToday();
			try{
				pay005Service.insertToTable(paymentVou, paymentReq, cashReq,paymentDTO, today,cashReqNo,yearMonth);
			}catch (NomsException throwable) {
				log.error(throwable.getMessage());
				throwable.printStackTrace();
				return new BaseJasonObject<Object>(false,throwable.getMessage());
			}
			catch(Exception e){
				log.error(e.getMessage());
				e.printStackTrace();
				return new BaseJasonObject<Object>(false,e.getMessage());
			}
			return new BaseJasonObject<Object>(true,"");
	}
	
	/**
	 * 更新CashRequirement
	 */
	@RequestMapping(value = "/pay005/updateToTable")
	@ResponseBody
	public BaseJasonObject<Object> updateToTable(@RequestParam("cashReqNo") String cashReqNo,@RequestParam("status") String status) {
		Date today=payPublicUtilService.getToday();
			try{
				pay005Service.updateToTable(cashReqNo, today,status);
			}catch (NomsException throwable) {
				log.error(throwable.getMessage());
				throwable.printStackTrace();
				return new BaseJasonObject<Object>(false,throwable.getMessage());
			}
			catch(Exception e){
				log.error(e.getMessage());
				e.printStackTrace();
				return new BaseJasonObject<Object>(false,e.getMessage());
			}
			return new BaseJasonObject<Object>(true,"");
	}
	
	/**
	 * 計算稅率  1:所得稅/2:健保費/3: 營業稅
	 */
	@RequestMapping(value = "/pay005/getAmt")
	@ResponseBody
	public BaseJasonObject<Object> getAmt(@RequestParam("oriAmt") BigDecimal oriAmt) {
		HashMap<String,Object> map=new HashMap<String,Object>();
		try{
				map.put("incomeTax", payPublicUtilService.payFnGetTax("1", oriAmt,"N"));
				map.put("nhiAmt", payPublicUtilService.payFnGetTax("2", oriAmt,"N"));
				map.put("businessTax", payPublicUtilService.payFnGetTax("3", oriAmt,"N"));
			}catch (NomsException throwable) {
				log.error(throwable.getMessage());
				throwable.printStackTrace();
				return new BaseJasonObject<>(false,throwable.getMessage());
			}
			catch(Exception e){
				log.error(e.getMessage());
				e.printStackTrace();
				return new BaseJasonObject<>(false,e.getMessage());
			}
		return new BaseJasonObject<>(map, AJAX_SUCCESS, AJAX_EMPTY);
	}
	
	/**
	 * 明細頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay005/dtlPage")
	public String dtlPage(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value = "cashReqNo", required = false) String cashReqNo) throws Exception{
		TbPayCashRequirement data =pay005Service.selectPayCashRequirementByPK(cashReqNo);
		try{
			TbAuthPersonnelExample example = new TbAuthPersonnelExample();
			example.createCriteria().andPSN_NOEqualTo(data.getAPP_USER());
			List<TbAuthPersonnel> person=personnelService.getPersonnelsByExample(example);
			TbAuthPersonnel chnNm=(TbAuthPersonnel)person.get(0);
			model.put("appUserName", chnNm.getCHN_NM());
		}catch(NullPointerException e){}
		model.put("domain", payPublicUtilService.selectTbOrgDomainByPrimaryKey(data.getDOMAIN()));
		model.put("master", data);
		return "ajaxPage/pay/PAY005D";
	}
	

	/**
	 * 明細頁面清單 Master grid
	 */
	@RequestMapping(value = "/pay005/searchPayment")
	@ResponseBody
	public JqGridData<TbPayPaymentDTO> searchPayment(
			@RequestParam(value = "cashReqNo", required = false) String cashReqNo) throws Exception{
		List<TbPayPaymentDTO> list=pay005Service.selectDtlAllData(cashReqNo);
		PageList<TbPayPaymentDTO> page = (PageList<TbPayPaymentDTO>) list;
		//return new JqGridData<TbPayPaymentDTO>(page.getPaginator().getTotalCount(),list);
		try{
			return new JqGridData<TbPayPaymentDTO>(page.getPaginator().getTotalCount(),list);
		}catch(NullPointerException e){
			return new JqGridData<TbPayPaymentDTO>(10, list);
		}
	}
	

	/**
	 * 明細頁面清單 Master grid
	 */
	@RequestMapping(value = "/pay005/searchForSP")
	@ResponseBody
	public JqGridData<TbPayRepartitionDTO> searchForSP(
			@RequestParam(value = "paymentReqNo", required = false) Long paymentReqNo) throws Exception{
		List<TbPayRepartitionDTO> list=pay005Service.getRepartion(paymentReqNo);
		PageList<TbPayRepartitionDTO> page = (PageList<TbPayRepartitionDTO>) list;
		return new JqGridData<TbPayRepartitionDTO>(page.getPaginator().getTotalCount(),list);
	}
	
	/**
	 * 新增 TB_PAY_REPARTITION TB_PAY_PAYMENT_REQUEST_DTL TB_PAY_PAYMENT TB_PAY_VOUCHER_CREDIT
	 */
	@RequestMapping(value = "/pay005/saveSP")
	@ResponseBody
	public BaseJasonObject<Object> saveSP(@RequestParam("spPaymentReqDtlArray") String spPaymentReqDtlArray,
			@RequestParam("spPaymentArray") String spPaymentArray,
			@RequestParam("spPaymentReqVouCreditArray") String spPaymentReqVouCreditArray,
			@RequestParam("spRepartitionArray") String spRepartitionArray,
			@RequestParam("paymentReqNo") Long paymentReqNo) {
		Date today=payPublicUtilService.getToday();	
			try{
				pay005Service.spSaveToTable(spPaymentReqDtlArray, spPaymentArray, spPaymentReqVouCreditArray,spRepartitionArray,paymentReqNo,today);
			}catch (NomsException throwable) {
				log.error(throwable.getMessage());
				throwable.printStackTrace();
				return new BaseJasonObject<Object>(false,throwable.getMessage());
			}
			catch(Exception e){
				log.error(e.getMessage());
				e.printStackTrace();
				return new BaseJasonObject<Object>(false,e.getMessage());
			}
			return new BaseJasonObject<Object>(true,"");
	}

	 /**
     * 供WORKFLOW審核呼叫
     * @param orderId 庫存單號或調撥單號
     * @param action  SUCCESS/REJECT
     * @return String
     */
    @RequestMapping(value = "/pay005/applyByWF")
    @ResponseBody
    public String applyByWF( @RequestParam("orderId")String orderId, @RequestParam("action") String action )throws NomsException{
    	log.debug("orderId : "+orderId+",action : "+action);   
    	String msg = "success",status="";
    	Date today=payPublicUtilService.getToday();
    	if(action.equals(AppConstants.WORKFLOW_REST_APPROVAL)){ //審核通過,狀態改核可             
    		status="C";
		}else if(action.equals(AppConstants.WORKFLOW_REST_REJECT)){
			status="D";
		}
    	try{
    		pay005Service.updateToTable(orderId, today, status); 
		}catch(Exception e){
			msg = "fail";	
		}		
        return msg;
    }
}
