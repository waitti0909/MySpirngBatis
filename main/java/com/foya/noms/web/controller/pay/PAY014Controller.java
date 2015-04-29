package com.foya.noms.web.controller.pay;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbPayProvisionalAttachment;
import com.foya.exception.NomsException;
import com.foya.noms.dto.auth.UserDTO;
import com.foya.noms.dto.pay.TbPayProvisionalAttachmentUserDTO;
import com.foya.noms.service.auth.PersonnelService;
import com.foya.noms.service.org.DomainService;
import com.foya.noms.service.pay.Pay014Service;
import com.foya.noms.service.pay.PayPublicUtilService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.JqGridData;
import com.foya.noms.web.controller.BaseController;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Controller
@RequestMapping(value = "/pay")
public class PAY014Controller extends BaseController {
	@Autowired
	private PayPublicUtilController payPublicUtilController;
	@Autowired
	private Pay014Service pay014Service;
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
	@RequestMapping(value = "/pay014/init")
	public String init(HttpServletRequest request,
			Map<String, Object> model) throws Exception{
		model.put("domainSelect", domainService.getStandardDomainList() );
		return "ajaxPage/pay/PAY014";
	}	

	/**
	 * 查詢
	 */
	@RequestMapping(value = "/pay014/search")
	@ResponseBody
	public JqGridData<TbPayProvisionalAttachment> search(
			@RequestParam(value = "docNo", required = false)String docNo,
			@RequestParam(value = "domain", required = false) String domain,
			@RequestParam(value = "attachStartDate", required = false) Date attachStartDate,
			@RequestParam(value = "attachEndDate", required = false) Date attachEndDate,
			@RequestParam(value = "attachUserId", required = false) String attachUserId) throws Exception{		
		List<TbPayProvisionalAttachment> payList = pay014Service.selectByExample(docNo,domain,attachStartDate,attachEndDate,attachUserId);
		PageList<TbPayProvisionalAttachment> page = (PageList<TbPayProvisionalAttachment>) payList;
		return new JqGridData<TbPayProvisionalAttachment>(page.getPaginator().getTotalCount(),payList);
	}
	
	/**
	 * 查詢User資料
	 */
	@RequestMapping(value = "/pay014/searchUser")
	@ResponseBody
	public JqGridData<TbPayProvisionalAttachmentUserDTO> searchUser(
			@RequestParam("docNo") String docNo) throws Exception{	
		List<TbPayProvisionalAttachmentUserDTO> list=pay014Service.selectDesc(docNo);
		PageList<TbPayProvisionalAttachmentUserDTO> page = (PageList<TbPayProvisionalAttachmentUserDTO>) list;
		return new JqGridData<TbPayProvisionalAttachmentUserDTO>(page.getPaginator().getTotalCount(),list);
	}	
	
	/**
	 * pay 014D.jsp
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay014/searchDtl")
	public String searchDtl(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam("docNo") String docNo) throws Exception{
		TbPayProvisionalAttachment pay = pay014Service.selectByPrimaryKey(docNo);
		model.put("master", pay);
		try{
			UserDTO person=personnelService.getUserDto(pay.getAPP_USER());
			model.put("appUserName", person.getChName());
		}catch(Exception e){}	
		model.put("domainSelect", domainService.getStandardDomainList() );
		return "ajaxPage/pay/PAY014D";
	}
	
	/**
	 * 新增頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay014/addPage")
	public String addPage(HttpServletRequest request,
			Map<String, Object> model) throws Exception{
		model.put("domainSelect", domainService.getStandardDomainList());
		model.put("payMethodSelect", payPublicUtilController.getPayLookupByType(request, "PAYMENT_METHOD"));
		model.put("bankCodeSelect", payPublicUtilService.getTbLsCollectUnit());
//		model.put("branchCodeSelect", payPublicUtilController.getPayLookupByType(request, "bank_branch_code"));
		model.put("appUserName", getLoginUser().getChName());
		model.put("appUser", getLoginUser().getUsername());
		model.put("closeFlag", "N");
		return "ajaxPage/pay/PAY014A";
	}
	
	/**
	 * 新增
	 */
	@RequestMapping(value = "/pay014/saveToTable")
	@ResponseBody
	public BaseJasonObject<Object> saveToTable(@RequestParam("payAttach") String payAttach,
			@RequestParam("payAttachUser") String payAttachUser,
			@RequestParam("addDocNo") String addDocNo) {
		Date today=payPublicUtilService.getToday();
		BaseJasonObject<Object> json=new BaseJasonObject<Object>(AJAX_SUCCESS,addDocNo);
			try{
				pay014Service.insertToTable(payAttach, payAttachUser, today,addDocNo);
			}catch (NomsException throwable) {
				log.error(throwable.getMessage());
				throwable.printStackTrace();
				json=new BaseJasonObject<Object>(false,"新增假扣押錯誤錯誤 原因:"+throwable.getMessage());
			}
			catch(Exception e){
				log.error(e.getMessage());
				e.printStackTrace();
				json=new BaseJasonObject<Object>(false,"新增假扣押錯誤錯誤 原因:"+e.getMessage());
			}
		return json;
	}
	
	
	/**
	 * 新增/更新 主檔資料
	 */
	@RequestMapping(value = "/pay014/saveMasterData")
	@ResponseBody
	public BaseJasonObject<Object> saveMasterData(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value = "addDomain", required = false)  String addDomain,
			@RequestParam(value = "addDocNo", required = false)  String addDocNo,
			@RequestParam(value = "addAttachStartDate", required = false)  Date addAttachStartDate,
			@RequestParam(value = "addAttachEndDate", required = false)  Date addAttachEndDate,
			@RequestParam(value = "attachUser", required = false)  String attachUser,
			@RequestParam(value = "appUser", required = false)  String appUser,
			@RequestParam(value = "appDate", required = false)  Date appDate,
			@RequestParam(value = "memo", required = false)  String memo,
			@RequestParam(value = "crTime", required = false)  Date crTime,
			@RequestParam(value = "personId", required = false)  String personId,
			@RequestParam(value = "attachAmt", required = false)  String attachAmt,
			@RequestParam(value = "maxAmt", required = false)  String maxAmt,
			@RequestParam(value = "payCheck", required = false)  String payCheck){
		BaseJasonObject<Object> json=new BaseJasonObject<Object>(true,addDocNo);
		String crUser=getLoginUser().getUsername();
		try{
			pay014Service.insertPayAttach(addDomain, addDocNo, addAttachStartDate, addAttachEndDate, attachUser, appUser, appDate,
					crUser, memo, personId, attachAmt, maxAmt, payCheck, crTime);
		}catch(Exception e){json=new BaseJasonObject<Object>(false,"新增假扣押資料錯誤:"+e.getMessage());}				
		return json;
	}
	
//	/**
//	 * 新增/更新 明細資料
//	 */
//	@RequestMapping(value = "/pay014/saveDetailData")
//	@ResponseBody
//	public BaseJasonObject<Object> saveDetailData(HttpServletRequest request,
//			Map<String, Object> model,
//			@RequestParam(value = "docNo", required = false) String docNo,
//			@RequestParam(value = "addPayUser", required = false) String addPayUser,
//			@RequestParam(value = "addPayUserName", required = false) String addPayUserName,
//			@RequestParam(value = "acctNbr", required = false) String acctNbr,
//			@RequestParam(value = "taxAmt", required = false) BigDecimal taxAmt,
//			@RequestParam(value = "paymentProportion", required = false) Short paymentProportion,
//			@RequestParam(value = "paymentStartDate", required = false) Date paymentStartDate,
//			@RequestParam(value = "paymentEndDate", required = false) Date paymentEndDate,
//			@RequestParam(value = "payMethod", required = false)  String payMethod,
//			@RequestParam(value = "bankCode", required = false)  String bankCode,
//			@RequestParam(value = "bankBranch", required = false)  String bankBranch,
//			@RequestParam(value = "crTime", required = false)  Date crTime) {
//		BaseJasonObject<Object> json=new BaseJasonObject<Object>(true,docNo);
//		Integer crUser=getLoginUser().getUserId();		
//		try{
//			pay014Service.insertPayAttachUser(docNo, addPayUser, addPayUserName, acctNbr, taxAmt, paymentProportion, 
//					paymentStartDate, paymentEndDate, payMethod, bankCode, bankBranch, crTime, crUser);
//		}catch(Exception e){json=new BaseJasonObject<Object>(false,"新增付款人資料明細錯誤:"+e.getMessage());}
//		return json;
//	}

	/**
	 * 修改頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay014/editPage")
	public String editPage(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value = "docNo", required = false) String docNo) throws Exception{
		TbPayProvisionalAttachment pay=pay014Service.selectByPrimaryKey(docNo);
		model.put("master", pay);
		try{
			UserDTO person=personnelService.getUserDto(pay.getAPP_USER());
			model.put("appUserName", person.getChName());
		}catch(Exception e){}	
		model.put("domainSelect", domainService.getStandardDomainList());
		model.put("payMethodSelect", payPublicUtilController.getPayLookupByType(request, "PAYMENT_METHOD"));
		model.put("bankCodeSelect", payPublicUtilService.getTbLsCollectUnit());
//		model.put("branchCodeSelect", payPublicUtilController.getPayLookupByType(request, "bank_branch_code"));
		return "ajaxPage/pay/PAY014M";
	}
	
	/**
	 * 刪除主檔資料
	 */
	@RequestMapping(value = "/pay014/delMasterData")
	@ResponseBody
	public BaseJasonObject<Object> delMasterData(
			@RequestParam("docNo") String docNo){	
		BaseJasonObject<Object> json=new BaseJasonObject<Object>(true,docNo);
		try{
			pay014Service.delMasterData(docNo);
		}catch(Exception e){json=new BaseJasonObject<Object>(false,"刪除假扣押資料錯誤:"+e.getMessage());}
		return json;
	}
	
	/**
	 * 刪除主檔資料
	 */
	@RequestMapping(value = "/pay014/delAllData")
	@ResponseBody
	public BaseJasonObject<Object> delAllData(
			@RequestParam("docNo") String docNo) {
		BaseJasonObject<Object> json=new BaseJasonObject<Object>(true,docNo);
		try{
			pay014Service.delData(docNo);
		}catch(Exception e){json=new BaseJasonObject<Object>(false,"刪除假扣押資料錯誤:"+e.getMessage());}
		return json;
	}
}
