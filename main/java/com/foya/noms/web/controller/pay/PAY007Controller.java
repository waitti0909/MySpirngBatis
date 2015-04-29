package com.foya.noms.web.controller.pay;

import java.util.Date;
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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.foya.dao.mybatis.model.TbPayCashRequirement;
import com.foya.dao.mybatis.model.TbPayLookupCode;
import com.foya.exception.NomsException;
import com.foya.noms.dto.pay.TbPayCashRequirementDTO;
import com.foya.noms.service.org.DomainService;
import com.foya.noms.service.pay.Pay007Service;
import com.foya.noms.service.pay.PayPublicUtilService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.JqGridData;
import com.foya.noms.web.controller.BaseController;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Controller
@RequestMapping(value = "/pay")
public class PAY007Controller extends BaseController {

	@Autowired
	private DomainService domainService;
	@Autowired
	private PayPublicUtilController payPublicUtilController;
	@Autowired
	private Pay007Service pay007Service;
	@Autowired
	private PayPublicUtilService payPublicUtilService;
	
	/**
	 * 初始頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay007/init")
	public String init(HttpServletRequest request,Map<String, Object> model) throws Exception{
		model.put("domainSelect", domainService.getStandardDomainList());
		model.put("processTypeSelect", payPublicUtilController.getPayLookupByType(request, "ACCOUNT_APPROVAL_TYPE"));
		return "ajaxPage/pay/PAY007";
	}
	
	/**
	 * 查詢
	 */
	@RequestMapping(value = "/pay007/search")
	@ResponseBody
	public JqGridData<TbPayCashRequirementDTO> search(
			@RequestParam(value = "domain", required = false) String domain,
			@RequestParam(value = "processType", required = false) String processType,
			@RequestParam(value = "appUser", required = false) String appUser,
			@RequestParam(value = "appStartDate", required = false) Date appStartDate,
			@RequestParam(value = "appEndDate", required = false) Date appEndDate,
			@RequestParam(value = "cashReqNo", required = false) String cashReqNo,
			@RequestParam(value = "yearMonth", required = false) String yearMonth)throws Exception {
		List<TbPayCashRequirementDTO> payList = pay007Service.selectTbPayCashRequirement(domain, processType, appUser,
				appStartDate, appEndDate,cashReqNo,yearMonth);
		PageList<TbPayCashRequirementDTO> page = (PageList<TbPayCashRequirementDTO>) payList;
		return new JqGridData<TbPayCashRequirementDTO>(page.getPaginator().getTotalCount(), payList);
	}

	/**
	 * 取得駁回原因下拉選單資料
	 */
	@RequestMapping(value = "/pay007/lookupByType")
	@ResponseBody
	public BaseJasonObject<TbPayLookupCode> lookupByType(
			HttpServletRequest request,Map<String, Object> model,
			@RequestParam(value = "rejectReason", required = false) String rejectReason)throws Exception {
		return new BaseJasonObject<TbPayLookupCode>(payPublicUtilService.getPayLookupByType("REJECT_REASON"),
				AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 更新TB_PAY_CASHREQUIREMENT資料
	 */
	@RequestMapping(value = "/pay007/updateData")
	@ResponseBody
	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonProperty("wrapper")
	public boolean updateData(@RequestBody List<TbPayCashRequirement> updateArray) {
		Date today = payPublicUtilService.getToday();
		try{
			pay007Service.updatePayCashRequirement(updateArray,today);
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

}
