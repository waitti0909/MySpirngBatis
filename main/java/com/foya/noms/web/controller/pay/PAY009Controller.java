package com.foya.noms.web.controller.pay;

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
import com.foya.noms.dto.pay.TbPayPaymentRequestDtlDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestVoucherDTO;
import com.foya.noms.service.auth.PersonnelService;
import com.foya.noms.service.org.DomainService;
import com.foya.noms.service.pay.PAY009Service;
import com.foya.noms.service.pay.PayPublicUtilService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.JqGridData;
import com.foya.noms.web.controller.BaseController;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Controller
@RequestMapping(value = "/pay")
public class PAY009Controller extends BaseController {
	@Autowired
	private PayPublicUtilController payPublicUtilController;
	@Autowired
	private PAY009Service pay009Service;
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
	@RequestMapping(value = "/pay009/init")
	public String init(HttpServletRequest request,
			Map<String, Object> model) throws Exception{
		model.put("domainSelect", domainService.getStandardDomainList() );
		return "ajaxPage/pay/PAY009";
	}	

	/**
	 * 查詢
	 */
	@RequestMapping(value = "/pay009/search")
	@ResponseBody
	public JqGridData<TbPayPaymentRequestVoucherDTO> search(
			@RequestParam(value = "domainSelect", required = false) String domainSelect,
			@RequestParam(value = "voucherNbr", required = false) String voucherNbr,
			@RequestParam(value = "lessorId", required = false) String lessorId,
			@RequestParam(value = "yearMonth", required = false) String yearMonth,
			@RequestParam(value = "locationId", required = false) String locationId) throws Exception{		
		List<TbPayPaymentRequestVoucherDTO> payList = pay009Service.selectPaymentAmount(
				domainSelect, voucherNbr, lessorId, yearMonth, locationId);
				
		PageList<TbPayPaymentRequestVoucherDTO> page = (PageList<TbPayPaymentRequestVoucherDTO>) payList;
		return new JqGridData<TbPayPaymentRequestVoucherDTO>(page.getPaginator().getTotalCount(),payList);
	}
	
	/**
	 * 查詢-請款清單
	 */
	@RequestMapping(value = "/pay009/searchDetail")
	@ResponseBody
	public JqGridData<TbPayPaymentRequestVoucherDTO> searchDetail(
			@RequestParam("voucherNo") String voucherNo) throws Exception{	
		List<TbPayPaymentRequestVoucherDTO> list=pay009Service.selectPayPaymentRequestVoucherByVoucherNo(voucherNo);
		return new JqGridData<TbPayPaymentRequestVoucherDTO>(list.size(),list);
	}	
	/**
	 * 查詢Detail -初始頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay009/searchDtl")
	public String searchDtl(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam("voucherNo") String voucherNo) throws Exception{
		model.put("voucherNo", voucherNo);
		model.put("type", "D");
		return "ajaxPage/pay/PAY009M";
	}	
	/**
	 * 查詢Detail -請款清單-Table1
	 */
	@RequestMapping(value = "/pay009/searchDetail1")
	@ResponseBody
	public JqGridData<TbPayPaymentRequestVoucherDTO> searchDetail1(
			@RequestParam("voucherNo") String voucherNo) throws Exception{	
		List<TbPayPaymentRequestVoucherDTO> list=pay009Service.selectPayPaymentRequestVoucherByVoucherNo(voucherNo);
		return new JqGridData<TbPayPaymentRequestVoucherDTO>(list.size(),list);
	}	
	/**
	 * 新增頁面 - 請款單 - Detail2所需資料
	 */
	@RequestMapping(value = "/pay009/searchDetail2")
	@ResponseBody
	public JqGridData<TbPayPaymentRequestDtlDTO> searchDetail2(
			 @RequestParam("mstSeqNbr") Long mstSeqNbr) throws Exception{	
		List<TbPayPaymentRequestDtlDTO> list=pay009Service.select4Pay009Detail2( mstSeqNbr);
		return new JqGridData<TbPayPaymentRequestDtlDTO>(list.size(),list);
	}
	/**
	 * 修改頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay009/editPage")
	public String editPage(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam("voucherNo") String voucherNo) throws Exception{
		try{			
			model.put("prTypeSelect", payPublicUtilController.getPayLookupByType(request, "PR_TYPE"));
			model.put("locationIdSelect", pay009Service.selectPayPaymentRequestDtlLocationId());
		}catch(Exception e){}
		model.put("type", "E");
		model.put("voucherNo", voucherNo);
		return "ajaxPage/pay/PAY009M";
	}
	/**
	 * 新增頁面 - 新增動作
	 */
	@RequestMapping(value = "/pay009/edit")
	@ResponseBody
	@JsonIgnoreProperties(ignoreUnknown = true)
	public BaseJasonObject<Object> edit(
			@RequestBody List<TbPayPaymentRequestVoucherDTO> voucherList){	
		BaseJasonObject<Object> json=new BaseJasonObject<Object>(true, null);
		try{
			pay009Service.edit(voucherList);
			json = new BaseJasonObject<Object>(true, "憑證處理  變更儲存成功!");			
		}catch(Exception e){
			json=new BaseJasonObject<Object>(false,"憑證處理  儲存失敗:"+e.getMessage());			
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
	@RequestMapping(value = "/pay009/addPage")
	public String addPage(HttpServletRequest request,
			Map<String, Object> model) throws Exception{
		try{
			model.put("prTypeSelect", payPublicUtilController.getPayLookupByType(request, "PR_TYPE"));
			model.put("voucherTypeSelect", payPublicUtilController.getPayLookupByType(request, "VOUCHER_TYPE"));
			model.put("locationIdSelect", pay009Service.selectPayPaymentRequestDtlLocationId());
		}catch(Exception e){}
		model.put("type", "A");
		return "ajaxPage/pay/PAY009M";
	}
	
	/**
	 * 新增頁面 - 查詢 - Detail2 所需相關金額 (PayPaymentRequestDtl.Tax Amount)
	 * 
	 * @param request
	 * @param prType 請款類別代碼
	 * @param siteId 基站代碼
	 */
	@RequestMapping(value = "/pay009/getDtlTaxAmt")
	@ResponseBody
	public TbPayPaymentRequestDtlDTO getDtlTaxAmt(HttpServletRequest request
			,String prType, String locationId) throws Exception{
		return pay009Service.selectPayPaymentRequestDtlTaxSumAmt(prType, locationId);
	}
	/**
	 * 新增頁面 - 請款單 - Detail3 所需資料
	 */
	@RequestMapping(value = "/pay009/searchDetail3")
	@ResponseBody
	public JqGridData<TbPayPaymentRequestDtlDTO> searchDetail3(
			@RequestParam(value = "mstSeqNbr", required = false) Long mstSeqNbr,
			@RequestParam(value = "locationId", required = false) String locationId,
			@RequestParam(value = "prType", required = false) String prType) throws Exception{	
		List<TbPayPaymentRequestDtlDTO> list=pay009Service.select4Pay009Detail3(
				mstSeqNbr, locationId, prType);
		return new JqGridData<TbPayPaymentRequestDtlDTO>(list.size(),list);
	}
	/**
	 * 新增頁面 - 新增動作
	 */
	@RequestMapping(value = "/pay009/add")
	@ResponseBody
	@JsonIgnoreProperties(ignoreUnknown = true)
	public BaseJasonObject<Object> add(
			@RequestBody List<TbPayPaymentRequestVoucherDTO> voucherList){	
		BaseJasonObject<Object> json=new BaseJasonObject<Object>(true, null);
		String vucherNbr = null;
		try{
			vucherNbr = pay009Service.insert(voucherList);
			json = new BaseJasonObject<Object>(true, "憑證處理單號 "+vucherNbr+" 新增成功!");			
		}catch(Exception e){
			json=new BaseJasonObject<Object>(false,"憑證處理單號 "+vucherNbr+" 儲存失敗:"+e.getMessage());			
		}		
		return json;
	}
}
