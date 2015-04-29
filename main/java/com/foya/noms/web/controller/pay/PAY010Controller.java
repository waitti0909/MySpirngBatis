package com.foya.noms.web.controller.pay;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.foya.dao.mybatis.model.TbPayCheckDisregard;
import com.foya.dao.mybatis.model.TbPayCheckDisregardKey;
import com.foya.dao.mybatis.model.TbPayLookupCode;
import com.foya.dao.mybatis.model.TbPayPayment;
import com.foya.exception.NomsException;
import com.foya.noms.dto.pay.TbPayCheckDisregardDTO;
import com.foya.noms.dto.pay.TbPayPaymentDTO;
import com.foya.noms.resources.AppConstants;
import com.foya.noms.service.auth.PersonnelService;
import com.foya.noms.service.org.DomainService;
import com.foya.noms.service.pay.Pay010Service;
import com.foya.noms.service.pay.PayPublicUtilService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.JqGridData;
import com.foya.noms.web.controller.BaseController;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Controller
@RequestMapping(value = "/pay")
public class PAY010Controller extends BaseController {
	@Autowired
	private PayPublicUtilController payPublicUtilController;
	@Autowired
	private DomainService domainService;
	@Autowired
	private PersonnelService personnelService;
	@Autowired
	private Pay010Service pay010Service;
	@Autowired 
	private PayPublicUtilService payPublicUtilService;
	/**
	 * 初始頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay010/init")
	public String init(HttpServletRequest request,
			Map<String, Object> model) throws Exception{
		model.put("domainSelect", domainService.getStandardDomainList());
		model.put("cashReqStatus", payPublicUtilController.getPayLookupByType(request, "CKD_STATUS"));
		return "ajaxPage/pay/PAY010";
	}	
	
	/**
	 * 新增頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay010/addPage")
	public String addPage(HttpServletRequest request,
			Map<String, Object> model) throws Exception{
		model.put("domainSelect", domainService.getStandardDomainList());
		model.put("appUserName", getLoginUser().getChName());
		model.put("appUser", getLoginUser().getUsername());
		model.put("checkReason", payPublicUtilController.getPayLookupByType(request, "CHECK_DISREGARD_REASON"));
		model.put("paymentSelect", pay010Service.selectPyamentByExample());
		return "ajaxPage/pay/PAY010A";
	}

	/**
	 * 取得付款資料
	 */
	@RequestMapping(value = "/pay010/getPaymentData")
	@ResponseBody
	public BaseJasonObject<TbPayPayment> getPaymentData(
			@RequestParam("checkNbr") String checkNbr){
		List<TbPayPayment> returnList = null;
		try{
			 returnList = pay010Service.selectPyamentByExampleCheckNbr(checkNbr);
		}catch(Exception e){return new BaseJasonObject<TbPayPayment>(false,"取得付款資料時錯誤 :"+e.getMessage());}
		return new BaseJasonObject<>(returnList, AJAX_SUCCESS, AJAX_EMPTY);
	}
	/**
	 * 取得作廢單號
	 */
	@RequestMapping(value = "/pay010/getPayCheckDisregardNo")
	@ResponseBody
	public BaseJasonObject<Object> getPayCheckDisregardNo(){
		return new BaseJasonObject<Object>(true,pay010Service.getPayCheckDisregardNo());
	}
	/**
	 * 新增
	 */
	@RequestMapping(value = "/pay010/saveDisData")
	@ResponseBody
	public boolean saveDisData(@RequestBody List<TbPayCheckDisregard> exportList) {
		Date today=payPublicUtilService.getToday();
			try{
				pay010Service.insertPayCheckDisregard(exportList,today);
			}catch (NomsException throwable) {
				log.error(throwable.getMessage());
				throwable.printStackTrace();
				return false;
			}
			catch(Exception e){
				log.error(e.getMessage());
				e.printStackTrace();
				return false;
			}
		return true;
	}
	
	/**
	 * 查詢
	 */
	@RequestMapping(value = "/pay010/search")
	@ResponseBody
	public JqGridData<TbPayCheckDisregardDTO> search(
			@RequestParam(value = "domain", required = false) String domain,
			@RequestParam(value = "disregardNo", required = false) String disregardNo,
			@RequestParam(value = "appStartDate", required = false) Date appStartDate,
			@RequestParam(value = "appEndDate", required = false) Date appEndDate,
			@RequestParam(value = "crStartDate", required = false) Date crStartDate,
			@RequestParam(value = "crEndDate", required = false) Date crEndDate,
			@RequestParam(value = "status", required = false) String status) throws Exception{
		String[] statusStr = status.split(",");
		Set<String> statusList = new HashSet<String>();
		for (int g=0; g<statusStr.length; g++) {
			statusList.add(statusStr[g]);
		}
		List<TbPayCheckDisregardDTO> payList = pay010Service.selectTbPayCheckDisregard(domain,disregardNo,appStartDate,appEndDate,crStartDate,crEndDate,statusList);
		PageList<TbPayCheckDisregardDTO> page = (PageList<TbPayCheckDisregardDTO>) payList;
		return new JqGridData<TbPayCheckDisregardDTO>(page.getPaginator().getTotalCount(),payList);
	}
	
	/**
	 * 明細頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay010/searchDtl")
	public String searchDtl(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value = "domain", required = false) String domain,
			@RequestParam(value = "disregardNo", required = false) String disregardNo,
			@RequestParam(value = "appUserName", required = false) String appUserName,
			@RequestParam(value = "appDate", required = false) Date appDate) throws Exception{
		model.put("domainSelect", domainService.getStandardDomainList());
		model.put("disregardNo", disregardNo);
		model.put("appUserName", appUserName);
		model.put("domain", domain);
		model.put("appDate", appDate);
		return "ajaxPage/pay/PAY010D";
	}
	
	/**
	 * 明細頁面清單 Master grid
	 */
	@RequestMapping(value = "/pay010/searchPayment")
	@ResponseBody
	public JqGridData<TbPayCheckDisregardDTO> searchPayment(
			@RequestParam(value = "disregardNo", required = false) String disregardNo) throws Exception{
		List<TbPayCheckDisregardDTO> payList=pay010Service.getPayCheckDisregardNoEqual(disregardNo);
		int totalPage=1;
		try{
			if(payList.size()>AppConstants.DEFAULTDATASIZEPERPAGE){
				totalPage=(int) Math.floor(payList.size()/AppConstants.DEFAULTDATASIZEPERPAGE);
			}
			return new JqGridData<TbPayCheckDisregardDTO>(totalPage,payList);
		}catch(NullPointerException e){
			return new JqGridData<TbPayCheckDisregardDTO>(totalPage,payList);}
	}
	
	/**
	 * 明細頁面清單 Detail grid
	 */
	@RequestMapping(value = "/pay010/searchPaymentDtl")
	@ResponseBody
	public JqGridData<TbPayPaymentDTO> searchPaymentDtl(
			@RequestParam(value = "reqNo", required = false) Long reqNo,
			@RequestParam(value = "reqItemNo", required = false) Short reqItemNo,
			@RequestParam(value = "seqNo", required = false) Long seqNo) throws Exception{
		List<TbPayPaymentDTO> payList=pay010Service.selectPyamentByExampleReqNo(reqNo, reqItemNo,seqNo);
		PageList<TbPayPaymentDTO> page = (PageList<TbPayPaymentDTO>) payList;
		try{
			return new JqGridData<TbPayPaymentDTO>(page.getPaginator().getTotalCount(),payList);
		}catch(NullPointerException e){return new JqGridData<TbPayPaymentDTO>(0, payList);}
	}
	

	/**
	 * 明細頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay010/editPage")
	public String editPage(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value = "domain", required = false) String domain,
			@RequestParam(value = "disregardNo", required = false) String disregardNo,
			@RequestParam(value = "appUserName", required = false) String appUserName,
			@RequestParam(value = "appDate", required = false) Date appDate,
			@RequestParam(value = "appUser", required = false) String appUser) throws Exception{
		model.put("domainSelect", domainService.getStandardDomainList());
		model.put("disregardNo", disregardNo);
		model.put("appUserName", appUserName);
		model.put("appUser", appUser);
		model.put("domain", domain);
		model.put("appDate", appDate);
		return "ajaxPage/pay/PAY010E";
	}
	
	/**
	 * 取作廢原因
	 */
	@RequestMapping(value = "/pay010/lookupByType")
	@ResponseBody
	public BaseJasonObject<TbPayLookupCode> lookupByType(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value="type", required = false) String type) throws Exception{
		return new BaseJasonObject<TbPayLookupCode>(payPublicUtilService.getPayLookupByType(type), AJAX_SUCCESS, AJAX_EMPTY);
	}
	
	/**
	 * 更新
	 */
	@RequestMapping(value = "/pay010/updateData")
	@ResponseBody
	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonProperty("wrapper") 
	public boolean updateData(@RequestBody List<TbPayCheckDisregard> editArray) {
		Date today=payPublicUtilService.getToday();
		try{
				pay010Service.updatePayCheckDisregard(editArray,today);
			}catch (NomsException throwable) {
				log.error(throwable.getMessage());
				throwable.printStackTrace();
				return false;
			}
			catch(Exception e){
				log.error(e.getMessage());
				e.printStackTrace();
				return false;
			}
		return true;
	}
	
	/**
	 * 刪除
	 */
	@RequestMapping(value = "/pay010/deleteData")
	@ResponseBody
	public boolean deleteData(@RequestBody List<TbPayCheckDisregardKey> delArray) {
			try{
				pay010Service.deletePayCheckDisregard(delArray);
			}catch (NomsException throwable) {
				log.error(throwable.getMessage());
				throwable.printStackTrace();
				return false;
			}
			catch(Exception e){
				log.error(e.getMessage());
				e.printStackTrace();
				return false;
			}
		return true;
	}
	 /**
     * 供WORKFLOW審核呼叫
     * @param orderId 庫存單號或調撥單號
     * @param action  SUCCESS/REJECT
     * @return String
     */
    @RequestMapping(value = "/pay010/applyByWF")
    @ResponseBody
    public String applyByWF( @RequestParam("orderId")String orderId, @RequestParam("action") String action )throws NomsException{
    	log.debug("orderId : "+orderId+",action : "+action);   
    	String msg = "success";
    	try{
    		pay010Service.applyByWF(orderId, action); 
		}catch(Exception e){
			msg = "fail";	
		}		
        return msg;
    }
}
