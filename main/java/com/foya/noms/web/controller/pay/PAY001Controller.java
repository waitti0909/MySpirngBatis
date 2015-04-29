package com.foya.noms.web.controller.pay;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.foya.dao.mybatis.model.TbPayCashRequirement;
import com.foya.exception.NomsException;
import com.foya.noms.dto.pay.TbPayCashRequirementDTO;
import com.foya.noms.dto.pay.TbPayPaymentDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestDtlDTO;
import com.foya.noms.service.auth.PersonnelService;
import com.foya.noms.service.org.DomainService;
import com.foya.noms.service.pay.PAY001Service;
import com.foya.noms.service.pay.PayPublicUtilService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.JqGridData;
import com.foya.noms.web.controller.BaseController;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Controller
@RequestMapping(value = "/pay")
public class PAY001Controller extends BaseController {
	@Autowired
	private PayPublicUtilController payPublicUtilController;
	@Autowired
	private PAY001Service pay001Service;
	@Autowired
	private DomainService domainService;
	@Autowired
	private PersonnelService personnelService;
	@Autowired
	private PayPublicUtilService payPublicUtilService;
	/**
	 * 初始頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay001/init")
	public String init(HttpServletRequest request,
			Map<String, Object> model) throws Exception{
		model.put("domainSelect", domainService.getStandardDomainList() );
		model.put("processTypeSelect", payPublicUtilController.getPayLookupByTypeAndCodeLike(request, "PROCESS_TYPE", "R"));
		model.put("cashReqStatus", payPublicUtilController.getPayLookupByType(request, "CASH_REQ_STATUS"));
		return "ajaxPage/pay/PAY001";
	}	

	/**
	 * 查詢
	 */
	@RequestMapping(value = "/pay001/search")
	@ResponseBody
	public JqGridData<TbPayCashRequirementDTO> search(
			@RequestParam(value = "domainSelect", required = false) String domainSelect,
			@RequestParam(value = "processType", required = false) String processType,
			@RequestParam(value = "paymentPeriod", required = false) String paymentPeriod,
			@RequestParam(value = "cashReqNo", required = false) String cashReqNo,
			@RequestParam(value = "appDateStart", required = false) Date appDateStart,
			@RequestParam(value = "appDateEnd", required = false) Date appDateEnd,
			@RequestParam(value = "status", required = false) String[] status) throws Exception{		
		List<TbPayCashRequirementDTO> payList = pay001Service.selectByExample(
				domainSelect, processType, paymentPeriod, cashReqNo, appDateStart, appDateEnd, status);
		PageList<TbPayCashRequirementDTO> page = (PageList<TbPayCashRequirementDTO>) payList;
		return new JqGridData<TbPayCashRequirementDTO>(page.getPaginator().getTotalCount(),payList);
	}
	
	/**
	 * 查詢-請款清單
	 */
	@RequestMapping(value = "/pay001/searchDetail1")
	@ResponseBody
	public JqGridData<TbPayPaymentRequestDTO> searchDetail1(
			@RequestParam("cashReqNo") String cashReqNo,
			@RequestParam("processTypeDesc") String processTypeDesc,
			@RequestParam("appDate") String appDate) throws Exception{	
		List<TbPayPaymentRequestDTO> list=pay001Service.selectDetail1(cashReqNo, appDate, processTypeDesc);
		return new JqGridData<TbPayPaymentRequestDTO>(list.size(),list);
	}	
	
	/**
	 * 查詢-租約站點清單
	 */
	@RequestMapping(value = "/pay001/searchDetail2")
	@ResponseBody
	public JqGridData<TbPayPaymentRequestDtlDTO> searchDetail2(
			@RequestParam("contractNo") String contractNo, 
			@RequestParam("paymentReqNo") String paymentReqNo,
			@RequestParam("appDate") String appDate) throws Exception{	
		List<TbPayPaymentRequestDtlDTO> list=pay001Service.selectDetail2(contractNo, paymentReqNo, appDate);
				
		return new JqGridData<TbPayPaymentRequestDtlDTO>(list.size(),list);
	}	
	/**
	 * 查詢 -基站請款記錄
	 */
	@RequestMapping(value = "/pay001/searchDetail3")
	@ResponseBody
	public JqGridData<TbPayPaymentDTO> searchDetail3(
			@RequestParam("locationId") String locationId,
			@RequestParam("contractNo") String contractNo, 
			@RequestParam("paymentReqNo") Long paymentReqNo,
			@RequestParam("appDate") String appDate,
			@RequestParam("itemNo") int itemNo,
			@RequestParam("processType") String processType) throws Exception{		
		List<TbPayPaymentDTO> list=pay001Service.selectDetail3(locationId, contractNo, paymentReqNo, appDate, itemNo, processType);
				
		return new JqGridData<TbPayPaymentDTO>(list.size(),list);
	}	
	/**
	 * pay 001D.jsp
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay001/searchDtl")
	public String searchDtl(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam("cashReqNo") String cashReqNo) throws Exception{
		try{
			TbPayCashRequirement pay = pay001Service.selectByPrimaryKey(cashReqNo);
			model.put("master", pay);
			model.put("personnelSelect", payPublicUtilService.searchByPsnNo(pay.getAPP_USER()));			
			model.put("domainSelect", domainService.getStandardDomainList() );
			model.put("processTypeSelect", payPublicUtilController.getPayLookupByTypeAndCodeLike(request, "PROCESS_TYPE", "R"));
		}catch(Exception e){}
		model.put("type", "D");
		return "ajaxPage/pay/PAY001M";
	}

	/**
	 * 修改頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay001/editPage")
	public String editPage(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value = "cashReqNo", required = false) String cashReqNo) throws Exception{
		try{
			TbPayCashRequirement pay = pay001Service.selectByPrimaryKey(cashReqNo);
			model.put("master", pay);
			model.put("personnelSelect", payPublicUtilService.searchByPsnNo(pay.getAPP_USER()));			
			model.put("domainSelect", domainService.getStandardDomainList() );
			model.put("processTypeSelect", payPublicUtilController.getPayLookupByTypeAndCodeLike(request, "PROCESS_TYPE", "R"));
		}catch(Exception e){}
		model.put("type", "E");
		return "ajaxPage/pay/PAY001M";
	}
	
	/**
	 * 修改-儲存動作
	 */
	@RequestMapping(value = "/pay001/update")
	@ResponseBody
	public BaseJasonObject<Object> update(
			@RequestParam("paymentReqNo") String[] paymentReqNo,
			@RequestParam("cashReqNo") String cashReqNo){	
		BaseJasonObject<Object> json=new BaseJasonObject<Object>(true, cashReqNo);
		try{
			pay001Service.update(paymentReqNo, cashReqNo);
			json = new BaseJasonObject<Object>(true, getMessageDetail("msg.update.success"));			
		}catch(Exception e){
			json=new BaseJasonObject<Object>(false,"請款單號 "+cashReqNo+" 變更儲存失敗:"+e.getMessage());			
		}		
		return json;
	}
	
	/**
	 * 修改-送審動作
	 */
	@RequestMapping(value = "/pay001/approved")
	@ResponseBody
	public BaseJasonObject<Object> approved(
			@RequestParam("paymentReqNo") String[] paymentReqNo,
			@RequestParam("cashReqNo") String cashReqNo){	
		BaseJasonObject<Object> json=new BaseJasonObject<Object>(true, cashReqNo);
		try{
			pay001Service.approved(paymentReqNo, cashReqNo);
			json = new BaseJasonObject<Object>(true, getMessageDetail("msg.update.success"));			
		}catch(Exception e){
			json=new BaseJasonObject<Object>(false,"請款單號 "+cashReqNo+" 變更儲存及送審失敗:"+e.getMessage());			
		}		
		return json;
	}	
	/**
	 * 新增頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay001/addPage")
	public String addPage(HttpServletRequest request,
			Map<String, Object> model) throws Exception{
		TbPayCashRequirement pay = new TbPayCashRequirement();
		try{
			model.put("domainSelect", domainService.getStandardDomainList() );
			model.put("processTypeSelect", payPublicUtilController.getPayLookupByTypeAndCodeLike(request, "PROCESS_TYPE", "R"));
			pay.setAPP_DATE(new Date());
			pay.setYEAR_MONTH(new SimpleDateFormat("yyyyMM").format(new Date()));
			model.put("master", pay);
		}catch(Exception e){}
		model.put("type", "A");
		return "ajaxPage/pay/PAY001M";
	}
	/**
	 * 新增-資料處理動作-檢查是否有重覆資料
	 */
	@RequestMapping(value = "/pay001/checkDataExists")
	@ResponseBody
	public BaseJasonObject<String> checkDataExists(
			@RequestParam("domain") String domain,
			@RequestParam("processType") String processType,
			@RequestParam("yearMonth") String yearMonth){	
		boolean isExists=pay001Service.checkDataExists(domain, processType, yearMonth);
		String msg = "維運區="+domain +", 處理類別="+ processType +", 請款年月="+ yearMonth +" 已有請款資料,請重新操作!";
		return new BaseJasonObject<String>(isExists?msg:"");
	}
	/**
	 * 新增-資料處理動作-請款清單
	 */
	@RequestMapping(value = "/pay001/searchDetail1ByProcess")
	@ResponseBody
	public JqGridData<TbPayPaymentRequestDTO> searchDetail1ByProcess(
			@RequestParam("domain") String domain,
			@RequestParam("paymentPeriod") String paymentPeriod,
			@RequestParam("processTypeDesc") String processTypeDesc,
			@RequestParam("processType") String processType,
			@RequestParam("yearMonth") String yearMonth){	
		List<TbPayPaymentRequestDTO> list=pay001Service.selectProcess(processType, domain, paymentPeriod, processTypeDesc, yearMonth);
		
		return new JqGridData<TbPayPaymentRequestDTO>(list.size(),list);
	}

	/**
	 * 新增-資料處理動作-租約站點清單 & 基站請款記錄 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/pay001/searchDetail2ByProcess")
	@ResponseBody
	public BaseJasonObject<Map<String, Object>> searchDetail2ByProcess(
			@RequestParam(value = "key", required = false) String key,
			@RequestParam("contractNo") String contractNo,
			@RequestParam("domain") String domain,
			@RequestParam("paymentPeriod") String paymentPeriod,
			@RequestParam("appDate") Date appDate,
			@RequestParam("paymentMonth") String paymentMonth,
			@RequestParam("processType") String processType){	
		Map<String, Object> map=pay001Service.selectDetail2ByProcess(
				key, domain, contractNo, appDate, paymentPeriod, paymentMonth, processType)	;	 
		
		return new BaseJasonObject(map, AJAX_SUCCESS, AJAX_EMPTY);
	}	
	/**
	 * 新增-資料處理動作-租約站點清單 & 基站請款記錄 (全取)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/pay001/searchDetail2AllByProcess")
	@ResponseBody
	@JsonIgnoreProperties(ignoreUnknown = true)
	public BaseJasonObject<Map<String, Object>> searchDetail2AllByProcess(
			@RequestBody List<TbPayPaymentRequestDTO> list){
		Map<String, Object> map = null;
		Map<Integer, Object> grid2map = new HashMap<Integer, Object>();
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		List<TbPayPaymentDTO> grid3List = new ArrayList<TbPayPaymentDTO>();
		String timestampKey = null;
		for(TbPayPaymentRequestDTO vo : list){
			map=pay001Service.selectDetail2ByProcess(vo.get_key(),
					vo.getDomain(), vo.getCONTRACT_NO(), 
					vo.getAppDate(), vo.getPaymentPeriod(),
					vo.getYearMonth(), vo.getProcessType());	
			grid2map.put(vo.get_id(), map.get("grid2"));
			grid3List.addAll((List<TbPayPaymentDTO>) map.get("grid3"));
			timestampKey =  map.get("key").toString();
		}
		returnMap.put("grid2All", grid2map);
		returnMap.put("grid3", grid3List);
		returnMap.put("key", timestampKey);
		return new BaseJasonObject(returnMap, AJAX_SUCCESS, AJAX_EMPTY);
	}	
	/**
	 * 新增-請款
	
	@RequestMapping(value = "/pay001/money")
	@ResponseBody
	@JsonIgnoreProperties(ignoreUnknown = true)
	public BaseJasonObject<Object> money(
			@RequestBody TbPayCashRequirementDTO tbPayCashRequirementDTO){
		BaseJasonObject<Object> json=new BaseJasonObject<Object>(true, null);
		String cashReqNo = null;
		try{
			cashReqNo = pay001Service.money_(tbPayCashRequirementDTO);
			json = new BaseJasonObject<Object>(true, "請款單號 "+ cashReqNo +" 待審中!");			
		}catch(Exception e){
			json=new BaseJasonObject<Object>(false, e.getMessage());			
		}		
		return json;
	} */
	/**
	 * 新增-資料處理動作-租約站點清單 & 基站請款記錄 
	 */
	@RequestMapping(value = "/pay001/money")
	@ResponseBody
	@JsonIgnoreProperties(ignoreUnknown = true)
	public BaseJasonObject<Object> money(
			@RequestParam("key") String key,
			@RequestParam("paymentPeriod") String paymentPeriod,
			@RequestParam("appDate") Date appDate,
			@RequestParam("paymentMonth") String paymentMonth,
			@RequestParam("processType") String processType){	
		String cashReqNo = null;
		BaseJasonObject<Object> json = null;
		try{
			cashReqNo = pay001Service.money(key, processType, paymentPeriod, paymentMonth, appDate);
			json = new BaseJasonObject<Object>(true, "請款單號 "+ cashReqNo +" 待審中!");			
		}catch(Exception e){
			json=new BaseJasonObject<Object>(false, e.getMessage());			
		}		
		return json;
	}	
	 /**
     * 供WORKFLOW審核呼叫
     * @param orderId 庫存單號或調撥單號
     * @param action  SUCCESS/REJECT
     * @return String
     */
    @RequestMapping(value = "/pay001/applyByWF")
    @ResponseBody
    public String applyByWF( @RequestParam("orderId")String orderId, @RequestParam("action") String action )throws NomsException{
    	log.debug("orderId : "+orderId+",action : "+action);   
    	String msg = "success";
    	try{
    		pay001Service.applyByWF(orderId, action); 
		}catch(Exception e){
			msg = "fail";	
		}		
        return msg;
    }
}
