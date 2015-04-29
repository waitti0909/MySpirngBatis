package com.foya.noms.dao.inv;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbInvInvMapper;
import com.foya.dao.mybatis.mapper.TbInvWarehouseMapper;
import com.foya.dao.mybatis.mapper.TbOrgDeptMapper;
import com.foya.dao.mybatis.mapper.TbOrgDomainMapper;
import com.foya.dao.mybatis.mapper.TbSysLookupMapper;
import com.foya.dao.mybatis.mapper.UTbAuthPersonnelMapper;
import com.foya.dao.mybatis.mapper.UTbInvCategoryMapper;
import com.foya.dao.mybatis.mapper.UTbInvMaterialMapper;
import com.foya.dao.mybatis.mapper.UTbInvMaterialOptMapper;
import com.foya.dao.mybatis.mapper.UTbInvWarehouseMapper;
import com.foya.dao.mybatis.mapper.UTbOrgDeptMapper;
import com.foya.dao.mybatis.mapper.UTbSiteMainMapper;
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
import com.foya.dao.mybatis.model.TbSysLookupExample;
import com.foya.dao.mybatis.model.UTbInvWarehouseExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.auth.UserDTO;
import com.foya.noms.dto.inv.TbInvMaterialOptDTO;
import com.foya.noms.dto.inv.TbInvWarehouseDTO;
import com.foya.noms.dto.org.DeptDTO;
import com.foya.noms.dto.st.SiteDTO;
@Repository
public class InvPublicUtilDao extends BaseDao {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UTbInvWarehouseMapper tbInvWarehouseMapper;
	@Autowired
	private UTbInvCategoryMapper tbInvCategoryMapper;
	@Autowired
	private UTbInvMaterialMapper tbInvMaterialMapper;
	@Autowired
	private UTbOrgDeptMapper uTbOrgDeptMapper;
	@Autowired
	private UTbAuthPersonnelMapper uTbAuthPersonnelMapper;
	@Autowired
	private TbSysLookupMapper tbSysLookupMapper;
	@Autowired
	private TbOrgDomainMapper tbOrgDomainMapper;
	@Autowired
	private TbOrgDeptMapper tbOrgDeptMapper;
	@Autowired
	private UTbInvMaterialOptMapper uTbInvMaterialOptMapper;
	@Autowired
	private TbInvWarehouseMapper oTbInvWarehouseMapper;
	@Autowired
	private UTbSiteMainMapper uTbSiteMainMapper;
	@Autowired
	private TbInvInvMapper tbInvInvMapper;
	/*查詢類別*/
	public List<TbInvCategory> getAllCategory() {
		return tbInvCategoryMapper.selectInvCategory();
	}
	/*查詢類別以類別*/
	public List<TbInvCategory> getCategoryByCatgcode1(String catgCode1) {
		return tbInvCategoryMapper.searchInvCategoryByCatgcode1(catgCode1);
	}
	/*查詢中類別以大類別*/
	public List<TbInvCategory> getCategoryMidByCatgcode1(String catgCode1) {
		return tbInvCategoryMapper.searchInvCategoryMidByCatgcode1(catgCode1);
	}
	/*查詢小類別以大類別*/
	public List<TbInvCategory> getCategorySmlByCatgcode1(String catgCode1,String catgCode2) {
		return tbInvCategoryMapper.searchInvCategorySmlByCatgcode1(catgCode1,catgCode2);
	}
	/*查詢倉庫*/
	public List<TbInvWarehouseDTO> getAllWareHouse() {		
		return tbInvWarehouseMapper.selectWareHouse();
	}
	/*查詢倉庫 is Active*/
	public List<TbInvWarehouseDTO> getAllWareHouseIsActive() {		
		return tbInvWarehouseMapper.selectWareHouseIsActive();
	}
	/*查詢倉庫以部門編號*/
	public List<TbInvWarehouseDTO> getWareHouseByDept(String deptId) {		
		return tbInvWarehouseMapper.selectWareHouseByDept(deptId);
	}
	/*查詢倉庫以部門編號*/
	public List<TbInvWarehouseDTO> getWareHouseByDept(String deptId,String domain) {		
		return tbInvWarehouseMapper.selectWareHouseByDept(deptId);
	}
	/*查詢料號*/
	public List<TbInvMaterial> getAllMaterial() {
		return tbInvMaterialMapper.selectMaterial();
	}
	/*查詢庫存可用品料號*/
	public List<TbInvInv> getInvMaterial(TbInvInvExample example) {
		return tbInvInvMapper.selectByExample(example);
	}
	/*查詢料號*/
	public List<TbInvMaterial> getAllMaterialbyCatgCode(HashMap<String,Object> dataObj) {
		return tbInvMaterialMapper.selectMaterialByCatgCode(dataObj);
	}
	/*查詢網路服務事業部組織清單*/
	public List<DeptDTO> getAllFourthDept() {
		return uTbOrgDeptMapper.searchDeptByIdLike();
	}
	/*查詢網路服務事業部組織清單*/
	public List<TbOrgDept> getAllFourthDeptByDomain(TbOrgDeptExample example) {
		return tbOrgDeptMapper.selectByExample(example);
	}
	/*查詢登入用戶的部門編號*/
	public UserDTO getPersonnelDeptId(String userId) {
		return uTbAuthPersonnelMapper.selectByPsn(userId);
	}
	
	/*以Type,Code查詢系統設定資料*/
	public List<TbSysLookup> getLookupByTypeAndCode(String type, String code) {
		TbSysLookupExample example = new TbSysLookupExample();
		example.createCriteria().andTYPEEqualTo(type).andCODEEqualTo(code);
		return tbSysLookupMapper.selectByExample(example);
	}
	
	/*以Type查詢系統設定資料*/
	public List<TbSysLookup> getLookupByType(String type) {
		TbSysLookupExample example = new TbSysLookupExample();
		example.createCriteria().andTYPEEqualTo(type);
		example.setOrderByClause("DISPLAY_ORDER");
		return tbSysLookupMapper.selectByExample(example);
	}
	
	/*查詢Dept資料*/
	public DeptDTO searchDeptById(String deptId) {
		return uTbOrgDeptMapper.searchDeptById(deptId);
	}
	
	public TbOrgDomain selectTbOrgDomainByPrimaryKey(String id){
		return tbOrgDomainMapper.selectByPrimaryKey(id);
	}
	
	/*以Type,Name查詢系統設定資料*/
	public List<TbSysLookup> getLookupByTypeAndName(String type, String name) {
		TbSysLookupExample example = new TbSysLookupExample();
		example.createCriteria().andTYPEEqualTo(type).andNAMEEqualTo(name);
		return tbSysLookupMapper.selectByExample(example);
	}
	
	public List<TbOrgDept> getDeptByDomain(TbOrgDeptExample example){
		return tbOrgDeptMapper.selectByExample(example);
	}
	
	public List<TbInvMaterialOptDTO> searchTodo(HashMap<String,Object> dataObj){
		return uTbInvMaterialOptMapper.searchTodo(dataObj);
	}
	public List<TbOrgDomain> selectTbOrgDomainByParent(TbOrgDomainExample example){
		return tbOrgDomainMapper.selectByExample(example);
	}
	
	/*查詢倉庫以部門編號*/
	public List<TbInvWarehouseDTO> getWareHouseByDomain(UTbInvWarehouseExample example) {		
		return tbInvWarehouseMapper.selectByExample(example);
	}
	
	/*查詢倉庫以部門編號*/
	public List<TbInvWarehouse> getWareHouseByDomain(TbInvWarehouseExample example) {		
		return oTbInvWarehouseMapper.selectByExample(example);
	}
	
	public TbInvWarehouse getWareHouseByPkey(String whId) {		
		return oTbInvWarehouseMapper.selectByPrimaryKey(whId);
	}
	public SiteDTO getSiteId(String btsSiteId){
		return uTbSiteMainMapper.selectSiteMainByBtsSiteId(btsSiteId);
	}
}

