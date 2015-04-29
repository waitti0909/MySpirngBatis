package com.foya.noms.web.controller.pay;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.dao.mybatis.model.TbLsCollectUnit;
import com.foya.dao.mybatis.model.TbLsCollectUnitSub;
import com.foya.dao.mybatis.model.TbLsLessor;
import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbOrgDeptExample;
import com.foya.dao.mybatis.model.TbOrgDomain;
import com.foya.dao.mybatis.model.TbPayLookupCode;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.noms.dto.org.DeptDTO;
import com.foya.noms.service.auth.PersonnelService;
import com.foya.noms.service.pay.PayPublicUtilService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.web.controller.BaseController;

@Controller
@RequestMapping(value = "/ajax/pay/public")
public class PayPublicUtilController extends BaseController {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	@Inject
	private PersonnelService personnelService;
	@Inject 
	private PayPublicUtilService payPublicUtilService;

	public List<TbOrgDomain> getAllDomain() {
		return payPublicUtilService.getAllDomain();
	}
	/*人員下拉選單*/
	@RequestMapping(value = "/personnel")
	public List<TbAuthPersonnel> initPersonnelSelect(HttpServletRequest reques) throws Exception{
		return personnelService.getAllPsn();
	}
	/*人員下拉選單以部門別分Like*/
	@RequestMapping(value = "/personnelDeptlike")
	public List<TbAuthPersonnel> initPersonnelDeptSelectlike(HttpServletRequest reques,String dept) throws Exception{
		return personnelService.selectByDeptIdLike(dept);
	}
	/*人員下拉選單以部門別分*/
	@RequestMapping(value = "/personnelDept")
	@ResponseBody
	public BaseJasonObject<TbAuthPersonnel> initPersonnelDeptSelect(HttpServletRequest reques,String dept) throws Exception{
		
		List<TbAuthPersonnel> authPersonnelList = payPublicUtilService.selectPersonnelByDeptId(dept);
		return new BaseJasonObject<TbAuthPersonnel>(authPersonnelList, AJAX_SUCCESS, AJAX_EMPTY);
	}
	
	/*申請人下拉選單以維運區區分*/
	@RequestMapping(value = "/appUserDomain")
	@ResponseBody
	public BaseJasonObject<TbAuthPersonnel> initappUserDomainSelect(HttpServletRequest reques,String domain) throws Exception{
		
		List<TbAuthPersonnel> authPersonnelList = payPublicUtilService.selectAppUserByDomain(domain);
		return new BaseJasonObject<TbAuthPersonnel>(authPersonnelList, AJAX_SUCCESS, AJAX_EMPTY);
	}
	
	/*管理單位選單 dept*/
	@RequestMapping(value = "/dept")
	public List<DeptDTO> initDeptSelect(HttpServletRequest reques) throws Exception{
		return payPublicUtilService.getAllFourthDept();
	}
	
	
	/*以Type,Code查詢系統設定資料*/
	@RequestMapping(value = "/lookupByTypeCode")
	public List<TbSysLookup> getLookupByTypeAndCode(String type, String code) {
		return payPublicUtilService.getLookupByTypeAndCode(type, code);
	}
	
	/*以Type查詢系統設定資料*/
	@RequestMapping(value = "/lookupByType")
	public List<TbSysLookup> getLookupByType(String type) {
		return payPublicUtilService.getLookupByType(type);	
	}
	
	/*以區域查詢管理單位 */
	@RequestMapping(value = "/deptByDomain")
	@ResponseBody
	public BaseJasonObject<TbOrgDept> domainByDeptSelect(HttpServletRequest reques, @RequestParam("domain") String domain) throws Exception {
		TbOrgDeptExample example = new TbOrgDeptExample();
		TbOrgDeptExample.Criteria cr = example.createCriteria();
		cr.andDOMAINEqualTo(domain);
		return new BaseJasonObject<TbOrgDept>(payPublicUtilService.getDeptByDomain(example), AJAX_SUCCESS, AJAX_EMPTY);
	}
	/*以Type查詢系統設定資料*/
	@RequestMapping(value = "/lookupByTypeName")
	public List<TbSysLookup> getLookupByTypeName(String type,String name) {
		return payPublicUtilService.getLookupByTypeAndName(type, name);	
	}
	/*取得時間*/
	@RequestMapping(value = "/getToday")
	@ResponseBody
	public BaseJasonObject<String> getToday() {		
		return new BaseJasonObject<String>( payPublicUtilService.getTodayString());
	}
	/*pay設定檔*/
	@RequestMapping(value = "/payLookupCode") 
	public List<TbPayLookupCode> getPayLookupByTypeAndName(HttpServletRequest reques
			,String type, String code) throws Exception{
		return payPublicUtilService.getPayLookupByTypeAndName(type, code);
	}
	
	/*pay設定檔*/
	@RequestMapping(value = "/payLookupCodeByType") 
	public List<TbPayLookupCode> getPayLookupByType(HttpServletRequest reques
			,String type) throws Exception{
		return payPublicUtilService.getPayLookupByType(type);
	}
	
	/*pay設定檔*/
	@RequestMapping(value = "/payLookupCodeByTypeAndCodeLike") 
	public List<TbPayLookupCode> getPayLookupByTypeAndCodeLike(HttpServletRequest reques
			,String type, String code) throws Exception{
		return payPublicUtilService.getPayLookupByTypeAndCodeLike(type, code);
	}	
	
	/*證號查詢*/
	@RequestMapping(value = "/checkLessor")
	@ResponseBody
	public BaseJasonObject<TbLsLessor> getTbLsLessor(HttpServletRequest reques
			,String attachUserId) throws Exception{
	 return new BaseJasonObject<TbLsLessor>( payPublicUtilService.getTbLsLessor(attachUserId), AJAX_SUCCESS, AJAX_EMPTY);
	}
	/*銀行查詢*/
	@RequestMapping(value = "/getBank")
	@ResponseBody
	public BaseJasonObject<TbLsCollectUnit> getBank(HttpServletRequest reques) throws Exception{
	 return new BaseJasonObject<TbLsCollectUnit>( payPublicUtilService.getTbLsCollectUnit(), AJAX_SUCCESS, AJAX_EMPTY);
	}
	
	/*分行查詢*/
	@RequestMapping(value = "/getBranch")
	@ResponseBody
	public BaseJasonObject<TbLsCollectUnitSub> getBranch(HttpServletRequest reques
			,String bankCode) throws Exception{
	 return new BaseJasonObject<TbLsCollectUnitSub>( payPublicUtilService.getTbLsCollectUnitSub(bankCode), AJAX_SUCCESS, AJAX_EMPTY);
	}
	
	/**
	 * 計算所得稅/健保費/營業稅  Call PAY_FN_GET_TAX
	 * 
	 * @param request
	 * @param type 1:所得稅/2:健保費/3: 營業稅
	 * @param amount 傳入金額
	 * @return
	 */
	@RequestMapping(value = "/payFnGetTax")
	@ResponseBody
	public BaseJasonObject<String> payFnGetTax(HttpServletRequest request
			,String type, BigDecimal amount, String includeTax) throws Exception{
		return new BaseJasonObject<String>(payPublicUtilService.payFnGetTax(type, amount, includeTax).toString());
	}
}
