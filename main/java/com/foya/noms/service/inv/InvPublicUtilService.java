package com.foya.noms.service.inv;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.dao.mybatis.model.TbInvCategory;
import com.foya.dao.mybatis.model.TbInvInv;
import com.foya.dao.mybatis.model.TbInvInvExample;
import com.foya.dao.mybatis.model.TbInvMaterial;
import com.foya.dao.mybatis.model.TbInvWarehouse;
import com.foya.dao.mybatis.model.TbInvWarehouseExample;
import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbOrgDeptExample;
import com.foya.dao.mybatis.model.TbOrgDomain;
import com.foya.dao.mybatis.model.TbOrgDomainExample;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.dao.mybatis.model.UTbInvWarehouseExample;
import com.foya.noms.dao.auth.PersonnelDao;
import com.foya.noms.dao.inv.InvPublicUtilDao;
import com.foya.noms.dto.auth.UserDTO;
import com.foya.noms.dto.inv.TbInvMaterialOptDTO;
import com.foya.noms.dto.inv.TbInvWarehouseDTO;
import com.foya.noms.dto.org.DeptDTO;
import com.foya.noms.dto.st.SiteDTO;
import com.foya.noms.service.BaseService;
@Service
public class InvPublicUtilService extends BaseService{

	@Autowired
	public InvPublicUtilDao invPublicUtilDao;
	@Autowired
	public PersonnelDao personnelDao;
	
	/*查詢類別*/
	public List<TbInvCategory> getAllCategory() {
		return invPublicUtilDao.getAllCategory();
	}
	/*查詢類別*/
	public List<TbInvCategory> getCategoryByCatgcode1(String catgCode1) {
		return invPublicUtilDao.getCategoryByCatgcode1(catgCode1);
	}
	/*查詢中類別以大類別*/
	public List<TbInvCategory> getCategoryMidByCatgcode1(String catgCode1) {
		return invPublicUtilDao.getCategoryMidByCatgcode1(catgCode1);
	}
	/*查詢小類別以大類別*/
	public List<TbInvCategory> getCategorySmlByCatgcode1(String catgCode1,String catgCode2) {
		return invPublicUtilDao.getCategorySmlByCatgcode1(catgCode1,catgCode2);
	}
	
	/*查詢倉庫*/
	public List<TbInvWarehouseDTO> getAllWareHouse() {
		return invPublicUtilDao.getAllWareHouse();
	}
	
	public TbInvWarehouse getWareHouseByPkey(String whId) {
		return invPublicUtilDao.getWareHouseByPkey(whId);
	}
	/*查詢倉庫以domain*/
	public List<TbInvWarehouse> getWareHouseByDomainDept(String domain,String deptId) {
		TbInvWarehouseExample example = new TbInvWarehouseExample();
		com.foya.dao.mybatis.model.TbInvWarehouseExample.Criteria criteria=example.createCriteria();
		if(!"".equals(domain))
			criteria.andDomainEqualTo(domain);
		if(!"".equals(deptId))
			criteria.andDept_idEqualTo(deptId);
		List<TbInvWarehouse> list= invPublicUtilDao.getWareHouseByDomain(example);
		 if(!"HQ".equals(domain)){			
				UTbInvWarehouseExample exampleHQ = new UTbInvWarehouseExample();
				exampleHQ.createCriteria().andWh_typeEqualTo("C").andWh_attributeEqualTo("M").andDomainEqualTo("HQ");
				exampleHQ.isDistinct();
				List<TbInvWarehouseDTO> fullList =invPublicUtilDao.getWareHouseByDomain(exampleHQ);
				list.add(fullList.get(0));
			}
		return list;
	}
	/*查詢所有倉庫is active*/
	public List<TbInvWarehouseDTO> getAllWareHouseIsActive() {
		return invPublicUtilDao.getAllWareHouseIsActive();
	}
	
	/*查詢倉庫以部門編號*/
	public List<TbInvWarehouseDTO> getWareHouseByDept(String deptId) {
		return invPublicUtilDao.getWareHouseByDept(deptId);
	}
	
	/*查詢庫存可用品料號*/
	public List<TbInvInv> getInvMaterial(String whId) {
		TbInvInvExample example=new TbInvInvExample();
		example.createCriteria().andStd_qtyGreaterThan(0).andWh_idEqualTo(whId);
		return invPublicUtilDao.getInvMaterial(example);
	}
	
	/*查詢料號*/
	public List<TbInvMaterial> getAllMaterial() {
		return invPublicUtilDao.getAllMaterial();
	}
	/*查詢料號以類別*/
	public List<TbInvMaterial> getAllMaterialByCatgCode(HashMap<String,Object> dataObj) {
		return invPublicUtilDao.getAllMaterialbyCatgCode(dataObj);
	}
	
	/*查詢網路服務事業部組織清單*/
	public List<DeptDTO> getAllFourthDept() {
		return invPublicUtilDao.getAllFourthDept();
	}
	/*查詢網路服務事業部組織清單以domain*/
	public List<TbOrgDept> getAllFourthDept(String domain) {
		TbOrgDeptExample example = new TbOrgDeptExample();
		example.createCriteria().andDOMAINEqualTo(domain).andDEPT_IDLike("4%");
		return invPublicUtilDao.getAllFourthDeptByDomain(example);
	}
	/*查詢組織清單以domain*/
	public List<TbOrgDept> getAllDeptByDomain(String domain) {
		TbOrgDeptExample example = new TbOrgDeptExample();
		example.createCriteria().andDOMAINLike(domain+"%");
		return invPublicUtilDao.getAllFourthDeptByDomain(example);
	}
	/*查詢登入用戶的部門編號*/
	public UserDTO getPersonnelDeptId(String userId) {
		return invPublicUtilDao.getPersonnelDeptId(userId);
	}
	
	/*以Type,Code查詢系統設定資料*/
	public List<TbSysLookup> getLookupByTypeAndCode(String type, String code) {
		return invPublicUtilDao.getLookupByTypeAndCode(type, code);
	}
	
	/*以Type查詢系統設定資料*/
	public List<TbSysLookup> getLookupByType(String type) {
		return invPublicUtilDao.getLookupByType(type);	
	}
	
	public List<TbAuthPersonnel> selectPersonnelByDeptId(String deptId){
		return personnelDao.selectPersonnelByDeptId(deptId);
	}
	
	/*以區域查詢管理單位*/
	public List<TbOrgDept> getDeptByDomain(TbOrgDeptExample example){
		return invPublicUtilDao.getDeptByDomain(example);
	}
	
	/*以Type,name查詢系統設定資料*/
	public List<TbSysLookup> getLookupByTypeAndName(String type, String name) {
		return invPublicUtilDao.getLookupByTypeAndName(type, name);
	}
	/*取得時間*/
	public Date getToday() {
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 0);
		date = calendar.getTime();
		return date;
	}
	/*取得時間字串格式*/
	public String getTodayString() {
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 0);
		date = calendar.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String dateString = formatter.format(date);
		return dateString;
	}
	
	public List<TbInvMaterialOptDTO> searchTodo(String optType,Set<String> status,String userId,String type){
		HashMap<String,Object> dataObj=new HashMap<String,Object>();
		dataObj.put("optType", optType);
		dataObj.put("loginUser", userId);
		dataObj.put("status", status);
		dataObj.put("type", type);
		return invPublicUtilDao.searchTodo(dataObj);
	}
	
	public List<TbOrgDomain> selectTbOrgDomainByParent(String parent){
		TbOrgDomainExample example =  new TbOrgDomainExample();
		example.createCriteria().andPARENTEqualTo(parent);
		return invPublicUtilDao.selectTbOrgDomainByParent(example);
		
	}
	
	public List<TbInvWarehouseDTO> getWareHouseByDomain(String id) {
		UTbInvWarehouseExample example = new UTbInvWarehouseExample();
		example.createCriteria().andIs_effectiveEqualTo(true).andDomainEqualTo(id);
		List<TbInvWarehouseDTO> list = invPublicUtilDao.getWareHouseByDomain(example);
		if(!"HQ".equals(id)){			
			UTbInvWarehouseExample exampleHQ = new UTbInvWarehouseExample();
			exampleHQ.createCriteria().andIs_effectiveEqualTo(true).andWh_typeEqualTo("C").andWh_attributeEqualTo("M").andDomainEqualTo("HQ");
			exampleHQ.isDistinct();
			List<TbInvWarehouseDTO> fullList =invPublicUtilDao.getWareHouseByDomain(exampleHQ);
			list.add(fullList.get(0));
		}
		return list;
	}
	public List<TbInvWarehouseDTO> getWareHouseLikeDomain(String id) {
		UTbInvWarehouseExample example = new UTbInvWarehouseExample();
		example.createCriteria().andIs_effectiveEqualTo(true).andDomainLike(id);
		List<TbInvWarehouseDTO> list = invPublicUtilDao.getWareHouseByDomain(example);
//		if(!"HQ".equals(id)){			
//			UTbInvWarehouseExample exampleHQ = new UTbInvWarehouseExample();
//			exampleHQ.createCriteria().andIs_effectiveEqualTo(true).andWh_typeEqualTo("C").andWh_attributeEqualTo("M").andDomainEqualTo("HQ");
//			exampleHQ.isDistinct();
//			List<TbInvWarehouseDTO> fullList =invPublicUtilDao.getWareHouseByDomain(exampleHQ);
//			list.add(fullList.get(0));
//		}
		return list;
	}
	public SiteDTO getSiteId(String btsSiteId){
		return invPublicUtilDao.getSiteId(btsSiteId);
	}
}
