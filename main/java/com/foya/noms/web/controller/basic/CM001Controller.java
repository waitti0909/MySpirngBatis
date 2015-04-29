package com.foya.noms.web.controller.basic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbComCompany;
import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbOrgDeptExample;
import com.foya.noms.dto.auth.PersonnelDTO;
import com.foya.noms.dto.basic.CompanyDTO;
import com.foya.noms.service.auth.PersonnelService;
import com.foya.noms.service.basic.CM001Service;
import com.foya.noms.service.org.ORG002Service;
import com.foya.noms.service.system.UniqueSeqService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.JqGridData;
import com.foya.noms.web.controller.BaseController;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Controller
public class CM001Controller extends BaseController {
	
	@Autowired
	private CM001Service cM001Service;
	
	@Autowired
	private UniqueSeqService uniqueSeqService;
	
	@Autowired
	private ORG002Service org002Service;
	
	@Autowired
	private PersonnelService personnelService;
	
	/**
	 * 初始頁面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/sys/company")
	public String comCompanyInit() {
		return "ajaxPage/basic/CM001";
	}
	
	/**
	 * 廠商查詢
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/searchCompany/")
	public @ResponseBody JqGridData<TbComCompany> searchCompany(HttpServletRequest request) {	
		String ubnNo = request.getParameter("search_CoUbnNo");
		String name = request.getParameter("search_CoName");
		String typeEng = request.getParameter("search_typeEng");
		String typeEqu = request.getParameter("search_typeEqu");
		String typeWh = request.getParameter("search_typeWh");
		String[] type ={typeEng,typeEqu,typeWh};
		
		List<TbComCompany> rsList = cM001Service.searchCompanyByVatName(ubnNo, name, type);
		 
		PageList<TbComCompany> page= (PageList<TbComCompany>) rsList;		
		return new JqGridData<>(page.getPaginator().getTotalCount(), rsList);
	}
	
	/**
	 * 廠商新增與修改頁
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/cM001_AddEdit/")
	public String cM001M(HttpServletRequest request,Map<String, Object> model) {
		//根據button顯示修改或新增
		if(request.getParameter("btnType").equals("add")){
			TbComCompany comCompany =new TbComCompany();
			comCompany.setCO_VAT_NO(uniqueSeqService.getNextCompanyId());
			model.put("mCompany", comCompany);
			model.put("btnType", "add");
			List<PersonnelDTO> psnList = new ArrayList<PersonnelDTO>();
			model.put("comPsnList",psnList);
		}else{ //修改
			TbComCompany company = new TbComCompany();
			String vatNo= request.getParameter("coVatNo");
			company=cM001Service.getCompanyDetailByID(vatNo);
			model.put("mCompany",company);			
			
			List<PersonnelDTO> psnList = personnelService.searchPsnByVatNo(vatNo);
			model.put("comPsnList",psnList);
			
			if(request.getParameter("btnType").equals("edit"))
			model.put("btnType", "edit");
			if(request.getParameter("btnType").equals("view"))
			model.put("btnType", "view");
		}
		return "ajaxPage/basic/CM001M";
	}
	
	/**
	 * 廠商儲存
	 * @param companyForm
	 * @return
	 */
	@RequestMapping(value = "/cM001M_Save/")
	public @ResponseBody BaseJasonObject<CompanyDTO> initUploadPage(@RequestBody CompanyDTO companyForm) {
		try {
			// 新增
			if (companyForm.getBtntype().equals("add")) {
				saveNewCompany(companyForm);
				return new BaseJasonObject<>(true, getMessageDetail("msg.add.success"));
			} else { // 修改
				saveUpdateCompany(companyForm);
				return new BaseJasonObject<>(true, getMessageDetail("msg.update.success"));
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			return new BaseJasonObject<>(false, e.getMessage());
		}
	}
	
	/**
	 * 儲存_廠商新增
	 * @param company
	 */
	public void saveNewCompany(CompanyDTO company) {
		String currentUserName = getLoginUser().getUsername();
		company.setCR_USER(currentUserName); // 建立者
		company.setCR_TIME(new Date()); // 建立時間
		// 啟用
		if (StringUtils.isNotEmpty(company.getACTIVATE())) {
			company.setACTIVATE("Y");
		}else{
			company.setACTIVATE("N");
		}
		// 廠商類別
		String[] typeEngEquArray = company.getEngEuqWh();
			for (int i = 0; i < typeEngEquArray.length; i++) {
				// 工程
				if (typeEngEquArray[i].equals("Eng")) {
					company.setTYPE_ENG("Y");
				}else if(typeEngEquArray[i].equals("Equ")){
					company.setTYPE_EQU("Y");
				}else if(typeEngEquArray[i].equals("Wh")){
					company.setTYPE_WH("Y");
				}else{
					company.setTYPE_ENG("N");
					company.setTYPE_EQU("N");
					company.setTYPE_WH("N");
				}
			}					
			if(StringUtils.isEmpty(company.getTYPE_ENG())){
				company.setTYPE_ENG("N");
			}			
			if(StringUtils.isEmpty(company.getTYPE_EQU())){
				company.setTYPE_EQU("N");
			}	
			if(StringUtils.isEmpty(company.getTYPE_WH())){
				company.setTYPE_WH("N");
			}	
		// 投保金額
		if(company.getINS_AMOUNT()==null){
			company.setINS_AMOUNT(BigDecimal.ZERO);
		}
		// 合併匯款
//		if (StringUtils.isEmpty(company.getMRG_REMIT())) {
//			company.setMRG_REMIT("N");
//		}

		cM001Service.saveNewCompany(company); // 如有重複拋出異常
	}
	
	/**
	 * 儲存_廠商修改
	 * @param company
	 */
	public void saveUpdateCompany(CompanyDTO company){
		String currentUserName = getLoginUser().getUsername();
		company.setMD_USER(currentUserName); //修改者
		company.setMD_TIME(new Date()); //修改時間						
		TbComCompany rsList = cM001Service.getCompanyDetailByID(company.getCO_VAT_NO()); //未修改前company
		company.setCR_USER(rsList.getCR_USER());
		company.setCR_TIME(rsList.getCR_TIME());
		//===============	
		//啟用
		if(StringUtils.isEmpty(company.getACTIVATE())){			
				company.setACTIVATE("N"); 
		}
		//廠商類別 (必)		
		String[] typeEngEquArray = company.getEngEuqWh();
			for (int i = 0; i < typeEngEquArray.length; i++) {
				// 工程
				if (typeEngEquArray[i].equals("Eng")) {
					   company.setTYPE_ENG("Y");
				}else if(typeEngEquArray[i].equals("Equ")){
					company.setTYPE_EQU("Y");
				}else if(typeEngEquArray[i].equals("Wh")){
					company.setTYPE_WH("Y");
				}
			}		
			if(StringUtils.isEmpty(company.getTYPE_ENG())){
				company.setTYPE_ENG("N");
			}			
			if(StringUtils.isEmpty(company.getTYPE_EQU())){
				company.setTYPE_EQU("N");
			}
			if(StringUtils.isEmpty(company.getTYPE_WH())){
				company.setTYPE_WH("N");
			}			
		//合併匯款		
//		if(StringUtils.isEmpty(company.getMRG_REMIT())){
//			company.setMRG_REMIT("N"); 
//		}					
		cM001Service.saveUpdateCompany(company);
	}
	
	/**
	 * 廠商帳號編輯初始化
	 * @param companyAccount
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/cm001/Account/initLoad")
	public String initLoadPage(@RequestBody PersonnelDTO companyAccount, HttpServletRequest request, Map<String, Object> modelMap) {
		modelMap.put("orgDept",this.getDeptKeyValueMap()); // 所有部門資訊
		modelMap.put("companyAccount", companyAccount);
		return "ajaxPage/basic/CM001M_AddUser";
	}
	
	/**
	 * 取得部門ID與名稱
	 * @return
	 */
	public Map<String, String> getDeptKeyValueMap() {
		Map<String, String> map = new LinkedHashMap<>();
		List<TbOrgDept> rows = org002Service.selectAllOrgDept(new TbOrgDeptExample());
		for (TbOrgDept row : rows) {
			map.put(row.getDEPT_ID(), row.getDEPT_NAME());
		}
		return map;
	}
	
	/**
	 * 廠商帳號儲存
	 * @param companyForm
	 * @return
	 */
	@RequestMapping(value = "/cM001M_AddUser/Save/")
	public @ResponseBody BaseJasonObject<PersonnelDTO> companyAccountSave(PersonnelDTO companyAccount,@RequestParam("btnType")String btnType) {
		try {
			// 新增
			if (btnType.equals("add")) {
				addCompanyAccount(companyAccount);
				return new BaseJasonObject<>(true, getMessageDetail("msg.add.success"));
			} else { // 修改
				updateCompanyAccount(companyAccount);
				return new BaseJasonObject<>(true, getMessageDetail("msg.update.success"));
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			return new BaseJasonObject<>(false, e.getMessage());
		}
	}
	
	/**
	 * 廠商帳號新增
	 * @param companyAccount
	 */
	public void addCompanyAccount(PersonnelDTO companyAccount){
		String currentUserName = getLoginUser().getUsername();
		companyAccount.setMD_USER(currentUserName); //修改者
		companyAccount.setMD_TIME(new Date()); //修改時間		
		companyAccount.setCO_VAT_NO(companyAccount.getCO_VAT_NO());
		companyAccount.setPSN_NO(companyAccount.getENG_NM()); //前端帳號為ENG_NM，需再次set給PSN_NO
		companyAccount.setON_JOB(new Date());
		cM001Service.addCompanyAccount(companyAccount);
	}
	
	/**
	 * 廠商帳號編輯
	 * @param companyAccount
	 */
	public void updateCompanyAccount(PersonnelDTO companyAccount){
		String currentUserName = getLoginUser().getUsername();
		companyAccount.setMD_USER(currentUserName); //修改者
		companyAccount.setMD_TIME(new Date()); //修改時間		
		companyAccount.setPSN_NO(companyAccount.getENG_NM()); //前端帳號為ENG_NM，需再次set給PSN_NO
		cM001Service.editCompanyAccount(companyAccount);
	}
	
	/**
	 * 根據vatno查詢廠商所有帳號
	 * @param request
	 * @param model
	 * @param coVatNo
	 * @return
	 */
	@RequestMapping(value="/getCompanyAccountList")
	@ResponseBody
	public BaseJasonObject<PersonnelDTO> getCompanyAccount(HttpServletRequest request, Map<String, Object> model, @RequestParam("coVatNo") String coVatNo) {
		List<PersonnelDTO> psnList = personnelService.searchPsnByVatNo(coVatNo);
		return new BaseJasonObject<>(psnList, AJAX_SUCCESS, AJAX_EMPTY);
	}
	
}
