package com.foya.noms.web.controller.inv;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.dao.mybatis.model.TbInvCategory;
import com.foya.dao.mybatis.model.TbInvMaterial;
import com.foya.dao.mybatis.model.TbInvWarehouse;
import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbOrgDeptExample;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.noms.dto.auth.UserDTO;
import com.foya.noms.dto.inv.TbInvWarehouseDTO;
import com.foya.noms.dto.org.DeptDTO;
import com.foya.noms.service.auth.PersonnelService;
import com.foya.noms.service.inv.InvPublicUtilService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.web.controller.BaseController;

@Controller
@RequestMapping(value = "/ajax/inv/public")
public class InvPublicUtilController extends BaseController {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	@Inject
	private PersonnelService personnelService;
	@Inject 
	private InvPublicUtilService invPublicUtilService;

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
		
		List<TbAuthPersonnel> authPersonnelList = invPublicUtilService.selectPersonnelByDeptId(dept);
		return new BaseJasonObject<TbAuthPersonnel>(authPersonnelList, AJAX_SUCCESS, AJAX_EMPTY);
	}
	
	/*取得後勤管理部人員的倉庫別*/
	@RequestMapping(value = "/wareHouseByDept")
	public List<TbInvWarehouseDTO> initWareHouseDeptSelect(HttpServletRequest reques,String userId) throws Exception{
		UserDTO user=invPublicUtilService.getPersonnelDeptId(userId);
		List<TbSysLookup> lookupList=invPublicUtilService.getLookupByTypeAndCode("ORG_SPECIFIC_DEPT_ID", "LOGISTICS_DEPT_ID");
		TbSysLookup data=(TbSysLookup)lookupList.get(0);
		String deptId=user.getHrDeptId();
		if(StringUtils.equals(data.getVALUE1(), deptId))
			return invPublicUtilService.getAllWareHouse();
		else
			return invPublicUtilService.getWareHouseByDept(deptId);
	}
	
	public List<TbInvWarehouse> selectWareHouseByDomainDept(HttpServletRequest reques,String userId,String domain) throws Exception{
		return invPublicUtilService.getWareHouseByDomainDept(domain, "");
	}
	
	/*類別選單*/
	@RequestMapping(value = "/category")
	public List<TbInvCategory> initCategorySelect(HttpServletRequest reques) throws Exception{
		return invPublicUtilService.getAllCategory();
	}
	/*大/中/小類別選單*/
	@RequestMapping(value = "/categoryCode1")
	@ResponseBody
	public BaseJasonObject<TbInvCategory> initCategoryCatgCode1Select(HttpServletRequest reques,String catgCode1) throws Exception{
		return new BaseJasonObject<TbInvCategory>(invPublicUtilService.getCategoryByCatgcode1(catgCode1), AJAX_SUCCESS, AJAX_EMPTY);
	}
	/*中類別選單*/
	@RequestMapping(value = "/categoryCode2")
	@ResponseBody
	public BaseJasonObject<TbInvCategory> initCategoryCatgCode2Select(HttpServletRequest reques,String catgCode1) throws Exception{
		return new BaseJasonObject<TbInvCategory>(invPublicUtilService.getCategoryMidByCatgcode1(catgCode1), AJAX_SUCCESS, AJAX_EMPTY);
	}
	/*小類別選單*/
	@RequestMapping(value = "/categoryCode3")
	@ResponseBody
	public BaseJasonObject<TbInvCategory> initCategoryCatgCode3Select(HttpServletRequest reques,String catgCode1,String catgCode2) throws Exception{
		return new BaseJasonObject<TbInvCategory>(invPublicUtilService.getCategorySmlByCatgcode1(catgCode1,catgCode2), AJAX_SUCCESS, AJAX_EMPTY);
	}
	
	/*區域選單*/

	/*管理單位選單 dept*/
	@RequestMapping(value = "/dept")
	public List<DeptDTO> initDeptSelect(HttpServletRequest reques) throws Exception{
		return invPublicUtilService.getAllFourthDept();
	}
	public List<TbOrgDept> initDeptSelectByDomain(HttpServletRequest reques,String domain) throws Exception{
		return invPublicUtilService.getAllFourthDept(domain);
	}
	/*廠商選單*/
	
	/*料號選單*/	
	@RequestMapping(value = "/material")
	public List<TbInvMaterial> initMaterialSelect(HttpServletRequest reques) throws Exception{
		return invPublicUtilService.getAllMaterial();
	}
	@RequestMapping(value = "/materialByCateCode")
	@ResponseBody
	public BaseJasonObject<TbInvMaterial> materialSelectByCateCode(HttpServletRequest reques,String catgCode1,String catgCode2,String catgCode3) throws Exception{
		HashMap<String,Object> dataObj=new HashMap<String,Object>();
		dataObj.put("catgCode1", catgCode1);
		dataObj.put("catgCode2", catgCode2);
		dataObj.put("catgCode3", catgCode3);
		return new BaseJasonObject<TbInvMaterial>(invPublicUtilService.getAllMaterialByCatgCode(dataObj), AJAX_SUCCESS, AJAX_EMPTY);
	}
	
	/*倉庫選單*/
	@RequestMapping(value = "/warehouse")
	public List<TbInvWarehouseDTO> initWareHouseSelect(HttpServletRequest reques) throws Exception{
		return invPublicUtilService.getAllWareHouse();
	}
	
	/*倉庫選單 is Active*/
	@RequestMapping(value = "/warehouseAllIsActive")
	public List<TbInvWarehouseDTO> initWareHouseSelectIsActive(HttpServletRequest reques) throws Exception{
		return invPublicUtilService.getAllWareHouseIsActive();
	}
	
	/*以Type,Code查詢系統設定資料*/
	@RequestMapping(value = "/lookupByTypeCode")
	public List<TbSysLookup> getLookupByTypeAndCode(String type, String code) {
		return invPublicUtilService.getLookupByTypeAndCode(type, code);
	}
	
	/*以Type查詢系統設定資料*/
	@RequestMapping(value = "/lookupByType")
	public List<TbSysLookup> getLookupByType(String type) {
		return invPublicUtilService.getLookupByType(type);	
	}
	
	/*以區域查詢管理單位 */
	@RequestMapping(value = "/deptByDomain")
	@ResponseBody
	public BaseJasonObject<TbOrgDept> domainByDeptSelect(HttpServletRequest reques, @RequestParam("domain") String domain) throws Exception {
		TbOrgDeptExample example = new TbOrgDeptExample();
		if (!domain.equals("HQ")) {
			TbOrgDeptExample.Criteria cr = example.createCriteria();
			cr.andDOMAINEqualTo(domain);
		}
		
		return new BaseJasonObject<TbOrgDept>(invPublicUtilService.getDeptByDomain(example), AJAX_SUCCESS, AJAX_EMPTY);
	}
	/*以Type查詢系統設定資料*/
	@RequestMapping(value = "/lookupByTypeName")
	public List<TbSysLookup> getLookupByTypeName(String type,String name) {
		return invPublicUtilService.getLookupByTypeAndName(type, name);	
	}
	/*取得時間*/
	@RequestMapping(value = "/getToday")
	@ResponseBody
	public BaseJasonObject<String> getToday() {		
		return new BaseJasonObject<String>( invPublicUtilService.getTodayString());
	}
	
	/*取得倉庫別 以區域別*/
	@RequestMapping(value = "/getWareHouseByDomain")
	@ResponseBody
	public BaseJasonObject<TbInvWarehouseDTO> getWareHouseByDomain(HttpServletRequest reques,String id) {
		if("HQ".equals(id))
			return new BaseJasonObject<TbInvWarehouseDTO>(invPublicUtilService.getAllWareHouseIsActive(), AJAX_SUCCESS, AJAX_EMPTY);
		else
			return new BaseJasonObject<TbInvWarehouseDTO>(invPublicUtilService.getWareHouseByDomain(id), AJAX_SUCCESS, AJAX_EMPTY);
	}
	
}
