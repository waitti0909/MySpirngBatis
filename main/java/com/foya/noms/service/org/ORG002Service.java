package com.foya.noms.service.org;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.dao.mybatis.model.TbAuthPersonnelExample;
import com.foya.dao.mybatis.model.TbAuthRoleMenuFunDeptExample;
import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbOrgDeptExample;
import com.foya.dao.mybatis.model.TbOrgDeptPos;
import com.foya.dao.mybatis.model.TbOrgDomain;
import com.foya.dao.mybatis.model.TbOrgDomainExample;
import com.foya.dao.mybatis.model.TbOrgPosition;
import com.foya.dao.mybatis.model.TbOrgPositionExample;
import com.foya.dao.mybatis.model.TbOrgPsnPos;
import com.foya.dao.mybatis.model.TbOrgPsnPosExample;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.dao.mybatis.model.TbSysLookupExample;
import com.foya.exception.DataExistsException;
import com.foya.exception.NomsException;
import com.foya.noms.dao.auth.PersonnelDao;
import com.foya.noms.dao.common.TownDomainTeamDao;
import com.foya.noms.dao.org.DeptDao;
import com.foya.noms.dao.org.DeptPosDao;
import com.foya.noms.dao.org.DomainDao;
import com.foya.noms.dao.org.ORG002Dao;
import com.foya.noms.dao.org.PositionDao;
import com.foya.noms.dao.org.PsnPosDao;
import com.foya.noms.dao.org.RoleMenuFunDeptDao;
import com.foya.noms.dao.system.LookupDao;
import com.foya.noms.dto.JsTreeDTO;
import com.foya.noms.dto.JsTreeStateDTO;
import com.foya.noms.dto.common.TownDomainTeamDTO;
import com.foya.noms.dto.org.DeptDTO;
import com.foya.noms.dto.org.PsnPosDTO;
import com.foya.noms.service.BaseService;
import com.foya.noms.service.auth.PersonnelService;

@Service
public class ORG002Service extends BaseService {

	@Autowired
	private ORG002Dao org002Dao;

	@Autowired
	private DeptDao deptDao;

	@Autowired
	private PersonnelDao personnelDao;

	@Autowired
	private RoleMenuFunDeptDao roleMenuFunDeptDao;

	@Autowired
	private PsnPosDao psnPosDao;

	@Autowired
	private DeptPosDao deptPosDao;

	@Autowired
	private PositionDao positionDao;

	@Autowired
	private DomainDao domainDao;

	@Autowired
	private PersonnelService personnelService;

	@Autowired
	private LookupDao lookupDao;
	
	@Autowired
	private TownDomainTeamDao domainTeamDao;

	public TbOrgDept getByPk(String deptId) {
		return deptDao.findByPk(deptId);
	}

	public List<TbOrgDept> getByCondition(TbOrgDeptExample example) {
		return deptDao.findByCondition(example);
	}

	public Map<String, String> getDeptKeyValues() {
		TbOrgDeptExample example = new TbOrgDeptExample();
		List<TbOrgDept> rows = deptDao.findByCondition(example);
		Map<String, String> keyValue = new LinkedHashMap<String, String>();
		for (TbOrgDept model : rows) {
			keyValue.put(model.getDEPT_ID(), model.getDEPT_NAME());
		}
		return keyValue;
	}

	/**
	 * 用id和name查詢Dept其餘欄位
	 * 
	 * @param deptId
	 * @param deptName
	 * @return
	 */
	public List<DeptDTO> searchDeptByIdName(String deptId, String deptName) {
		return deptDao.searchDeptByIdName(deptId, deptName);
	}

	/**
	 * 用部門ID查詢相對應的職務
	 * 
	 * @param deptId
	 * @return
	 */
	public List<DeptDTO> searchPositionByDeptId(String deptId) {
		return deptDao.searchPositionByDeptId(deptId);
	}

	/**
	 * 取得所有部門的階層樹
	 * 
	 * @return
	 */
	public List<JsTreeDTO> getDeptTree(String deptId) {
		List<DeptDTO> depts = deptDao.getDeptTree();
		return toBuildDeptTree(depts, deptId);
	}

	/**
	 * 新增部門職稱並儲存
	 * 
	 * @param dptPos
	 */
	@Transactional
	public boolean saveNewDeptPos(TbOrgDeptPos dptPos) throws DataExistsException {
		boolean result = false;
		List<TbOrgDeptPos> deptPosList = deptPosDao.getDeptPosByDeptPos(dptPos.getDEPT_ID(),
				dptPos.getPOS_ID());
		if (deptPosList.size() == 0) {
			dptPos.setDEPT_POS_ID(dptPos.getDEPT_ID() + "__" + dptPos.getPOS_ID());
			if("all".equals(dptPos.getPOS_TYPE())){
				String posType = positionDao.findPositionByPrimaryKey(dptPos.getPOS_ID()).getPOS_TYPE();
				dptPos.setPOS_TYPE(posType);
			}
			deptPosDao.saveNewDeptPos(dptPos);
			result = true;
		} else {
			throw new DataExistsException("部門名稱與職稱代號重複，無法新增");
		}
		return result;
	}

	/**
	 * 修改部門職稱
	 * 
	 * @param newDptPos
	 * @param oldDeptPosId
	 * @return
	 * @throws DataExistsException
	 */
	@Transactional
	public boolean saveUpdateDeptPos(TbOrgDeptPos newDptPos, String oldDeptPosId)
			throws DataExistsException {
		boolean result = false;
		List<TbOrgDeptPos> deptPosList = deptPosDao.getDeptPosByDeptPos(newDptPos.getDEPT_ID(),
				newDptPos.getPOS_ID());
		if (newDptPos.getDEPT_POS_ID().equals(oldDeptPosId) || deptPosList.size() == 0) {
			org002Dao.updatePsnPosByDeptPosId(oldDeptPosId, newDptPos.getDEPT_POS_ID());
			org002Dao.updatePsnPrimaryJobByDeptPosId(oldDeptPosId, newDptPos.getDEPT_POS_ID());
			deptDao.updateDeptManagerByDeptPosId(oldDeptPosId, newDptPos.getDEPT_POS_ID());
			deptPosDao.saveUpdateDeptPos(newDptPos, oldDeptPosId);
			result = true;
		} else {
			throw new DataExistsException("部門名稱與職稱代號重複，無法更新");
		}
		return result;
	}

	/**
	 * 刪除部門職稱
	 * 
	 * @param deptPosId
	 * @return
	 */
	@Transactional
	public boolean deleteByDeptPos(String deptPosId) throws NomsException {
		boolean result = false;
		String[] deptPosIdArray = deptPosId.split(",");
		for (String deptPosIdStr : deptPosIdArray) {
			String deptId = deptPosIdStr.split("__")[0];
			List<TbOrgPsnPos> psnPosList = org002Dao.getPosPersonByDeptPosId(deptPosIdStr);
			if (psnPosList.isEmpty()) {
				TbOrgDept dept = deptDao.getDeptById(deptId);
				if (!deptPosIdStr.equals(dept.getMANAGER())) {
					org002Dao.deletePsnPosByDeptPosId(deptPosIdStr);
					org002Dao.updatePsnPrimaryJobByDeptPosId(deptPosIdStr, null);
					int item = deptPosDao.deleteDeptPosByPrimaryKey(deptPosIdStr);
					if (item == 1) {
						result = true;
					}
				} else {
					throw new NomsException("msg.delete.isManager");
				}
			} else {
				throw new NomsException("msg.delete.personExistDept");
			}
		}
		return result;
	}

	/**
	 * 用deptPosId查詢DeptPos其餘欄位
	 * 
	 * @param deptPosId
	 * @return
	 */
	public TbOrgDeptPos serachDeptPosByDeptPosId(String deptPosId) {
		return deptPosDao.serachDeptPosByDeptPosId(deptPosId);
	}

	/**
	 * 用部門ID查詢部門其餘欄位
	 * 
	 * @param deptId
	 * @return
	 */
	public List<TbOrgDept> searchDeptByDeptId(String deptId) {
		TbOrgDeptExample deptExample = new TbOrgDeptExample();
		deptExample.createCriteria().andDEPT_IDEqualTo(deptId);
		return deptDao.findByCondition(deptExample);
	}

	/**
	 * 修改部門主管
	 * 
	 * @param deptId
	 * @param dpetPosId
	 * @return
	 */
	@Transactional
	public boolean saveUpdateDeptManager(String deptId, String dpetPosId) {
		boolean result = false;
		int item = deptDao.saveUpdateDeptManager(deptId, dpetPosId);
		if (item == 1) {
			result = true;
		}
		return result;
	}

	/**
	 * 組合部門的階層樹
	 * 
	 * @param depts
	 * @param defaultSelectedDept
	 * @return
	 */
	private List<JsTreeDTO> toBuildDeptTree(List<DeptDTO> depts, String defaultSelectedDept) {
		List<JsTreeDTO> jsTreeDto = new ArrayList<JsTreeDTO>();

		// 尋找所有父節點
		String[] parentArry = this.getParentByChild(defaultSelectedDept);

		if (CollectionUtils.isNotEmpty(depts)) {
			for (DeptDTO dept : depts) {
				JsTreeDTO dto = new JsTreeDTO();
				JsTreeStateDTO state = new JsTreeStateDTO();
				dto.setId(dept.getDEPT_ID());
				dto.setParent(StringUtils.isNotEmpty(dept.getPARENT_DEPT_ID()) ? dept
						.getPARENT_DEPT_ID() : "#");
				dto.setText(dept.getDEPT_NAME() + "(" + dept.getDEPT_LEVEL() + ")");

				// if (!StringUtils.equals("1", dept.getLevel())) {
				// state.setOpened(false); // 因版面狹小，暫時僅展開第一階層
				// }
				state.setOpened(false);
				// 開啟點選節點
				if (parentArry.length != 0) {
					for (int i = 1; i < parentArry.length; i++) {
						if (StringUtils.equals(parentArry[i], dept.getDEPT_ID())) {
							state.setOpened(true);
						}
					}
				}
				dto.setState(state);
				jsTreeDto.add(dto);
			}
		}
		return jsTreeDto;
	}

	 /**
	 * 取得所有職稱
	 * @return
	 */
	 public List<TbOrgPosition> selectAllOrgPosition(TbOrgPositionExample example){
	 return positionDao.selectOrgPosition(example);
	 }

	/**
	 * 取得所有區域
	 * 
	 * @return
	 */
	public List<TbOrgDomain> selectAllOrgDomain() {
		return domainDao.selectOrgDomain(new TbOrgDomainExample());
	}

	/**
	 * 取得所有部門
	 * 
	 * @return
	 */
	public List<TbOrgDept> selectAllOrgDept(TbOrgDeptExample example) {
		return deptDao.selectAllOrgDept(example);
	}

	/**
	 * 根據ID取得部門Info
	 * 
	 * @return
	 */
	public TbOrgDept selectOrgDeptById(String deptID) {
		return deptDao.selectOrgDeptById(deptID);
	}

	/**
	 * 儲存_Dept新增(org_drpt)
	 * 
	 * @param orgDept
	 * @throws DataExistsException
	 */
	public void saveNewDept(TbOrgDept orgDept) throws DataExistsException {
		String deptID = orgDept.getDEPT_ID();
		TbOrgDept isDupID = null;
		isDupID = deptDao.selectByPrimaryKey(deptID);
		TbOrgDept isDupName = null;
		isDupName = deptDao.selectByPrimaryKey(orgDept.getDEPT_NAME());
		// 代號與名稱是否重複
		if (isDupID != null) {
			throw new NomsException("部門代號已重複，無法新增!");
		} else if (isDupName != null) {
			throw new NomsException("部門名稱已重複，無法新增!");
		} else {
			deptDao.saveNewDept(orgDept);
		}
	}

	/**
	 * 儲存_Dept修改
	 * 
	 * @param orgDept
	 */
	@Transactional
	public void saveUpdateDept(TbOrgDept orgDept) {
		TbOrgDeptExample example = new TbOrgDeptExample();
		example.createCriteria().andDEPT_NAMEEqualTo(orgDept.getDEPT_NAME());
		List<TbOrgDept> isDupName = deptDao.findByCondition(example);
		// 名稱是否重複
		if (isDupName.size() > 1) {
			throw new NomsException("部門名稱已重複，無法修改!");
		} else {
			TbOrgDept rsList = deptDao.selectOrgDeptById(orgDept.getDEPT_ID()); // 未修改前dept
			// 從屬部門
			if (StringUtils.equals(orgDept.getPARENT_DEPT_ID(), rsList.getPARENT_DEPT_ID())) {
				orgDept.setHIERARCHY(rsList.getHIERARCHY());
				deptDao.saveUpdateDept(orgDept);
			} else {
				if (StringUtils.isNotEmpty(orgDept.getPARENT_DEPT_ID())) {
					String[] fatherNode = this.getParentByChild(orgDept.getPARENT_DEPT_ID());
					String hierarchy = this.mergerHierarchy(fatherNode);
					hierarchy = hierarchy + "_" + orgDept.getDEPT_ID();
					orgDept.setHIERARCHY(hierarchy);
				} else {
					orgDept.setHIERARCHY(orgDept.getDEPT_ID());
				}
				deptDao.saveUpdateDept(orgDept);
				// 此部門的子部門都要修改HIERARCHY
				List<TbOrgDept> toEditNode = this.getSubDept(orgDept.getDEPT_ID());
				for (int i = 0; i < toEditNode.size(); i++) {
					String[] editFatherNode = this.getParentByChild(toEditNode.get(i).getDEPT_ID());
					String editHierarchy = this.mergerHierarchy(editFatherNode);
					this.updateHierarchy(toEditNode.get(i).getDEPT_ID(), editHierarchy);
				}
			}
		}
	}

	/**
	 * Dept刪除
	 * 
	 * @param deptId
	 * @throws NomsException
	 */
	@Transactional
	public void deleteDept(String deptId) throws NomsException {
		Date deleteDate = new Date();
		List<TbOrgDept> childDept = deptDao.getChildDeptsById(deptId);
		// 有無子部門
		if (childDept.size() != 0) {
			throw new NomsException("此部門包含子部門，不可刪除!");
		}
		// 部門下有無職稱
		List<TbOrgDeptPos> deptPos = deptPosDao.getDeptPosByDeptId(deptId);
		if (deptPos.size() != 0) {
			throw new NomsException("此部門包含職稱，不可刪除!");
		}

		// 必要順序
		// 1.刪除Psn_Pos(從personnel取得此部門psn)
		List<TbAuthPersonnel> psnInDept = personnelDao.selectPersonnelByDeptId(deptId);
		for (int i = 0; i < psnInDept.size(); i++) {
			// 刪除Psn_Pos中此部門之資料(ByPsn)
			TbOrgPsnPosExample psnPosexample = new TbOrgPsnPosExample();
			psnPosexample.createCriteria().andPSN_NOEqualTo(psnInDept.get(i).getPSN_NO());
			psnPosDao.deletePsnPosByDeptId(psnPosexample);
		}
		// 2.更新Personal的職稱(dept_id、RIMARY_DEPT_POS_ID放空)(修改者、時間)
		personnelDao.updatePsnDeptByDeptId(getLoginUser().getUsername(), deleteDate, null, null,
				deptId);
		// 3.刪除Role_Menu_Fun_Dept
		TbAuthRoleMenuFunDeptExample roleMenuFunDeptexample = new TbAuthRoleMenuFunDeptExample();
		roleMenuFunDeptexample.createCriteria().andDEPT_IDEqualTo(deptId);
		roleMenuFunDeptDao.deleteByExample(roleMenuFunDeptexample);
		// 4.刪除Org_Dept
		deptDao.deleteDeptById(deptId);
	}

	/**
	 * 用deptPosId查詢Personnel
	 * 
	 * @param deptPosId
	 * @return
	 */
	public List<PsnPosDTO> getPsnPosDTOByDeptPosId(String deptPosId) {
		return org002Dao.getPsnPosDTOByDeptPosId(deptPosId);
	}

	/**
	 * 新增psnPos
	 * 
	 * @param deptPosId
	 * @param psnPosList
	 * @return
	 */
	@Transactional
	public boolean saveNewPsnPos(String deptPosId, List<TbOrgPsnPos> psnPosList)
			throws DataExistsException {
		boolean result = true;
		List<TbOrgPsnPos> psnPos_source = org002Dao.getPsnPosByDeptPosId(deptPosId);
		List<TbOrgPsnPos> psnPos_exist = new ArrayList<TbOrgPsnPos>(psnPosList);
		// psnPos_exist.retainAll(psnPos_source);
		// for(TbOrgPsnPos psnPos : psnPos_exist){
		// log.debug("psnPos : "+psnPos.getPSN_NO());
		// }
		psnPos_exist.retainAll(psnPos_source);
		if (psnPos_exist.size() > 0) {
			throw new DataExistsException("職稱人員重複，無法更新");
		}
		psnPosList.removeAll(psnPos_source);
		for (TbOrgPsnPos psnPos : psnPosList) {
			psnPos.setMD_TIME(new Date());
//			psnPos.setMD_USER(BigDecimal.valueOf(getLoginUser().getUserId()));
			psnPos.setMD_USER(getLoginUser().getUsername());
			int item = org002Dao.saveNewPsnPos(psnPos);
			if (item == 0) {
				result = false;
				throw new NomsException("新增失敗");
			}
		}
		return result;
	}

	/**
	 * 刪除PosPsn
	 * 
	 * @param deptPosId
	 * @param psnNo
	 * @return
	 */
	@Transactional
	public void deletePosPsnByPrimary(String deptPosId, String[] psnNo,String posType) throws NomsException {
//		if(!"W".equals(posType)){
//			for (String psn_No : psnNo) {
//				org002Dao.updatePrimaryJobByDeptPosIdPSNNo(null, psn_No, deptPosId);
//			}
//		}
		int deleteItem = org002Dao.deletePosPsnByPrimary(deptPosId, psnNo);
		if (deleteItem != psnNo.length) {
			throw new NomsException("刪除失敗");
		}
	}

	/**
	 * 更新主要職務
	 * 
	 * @param deptPosId
	 * @param posNo
	 * @return
	 */
	@Transactional
	public boolean updatePrimaryJobByDeptPosIdPSNNo(String deptPosId, String psnNo) {
		boolean result = false;
		int item = org002Dao.updatePrimaryJobByDeptPosIdPSNNo(deptPosId, psnNo, null);
		if (item == 1) {
			result = true;
		}
		return result;
	}

	/**
	 * 取得部門層級資訊
	 * 
	 * @return
	 */
	public List<TbSysLookup> getDeptLevel() {
		TbSysLookupExample example = new TbSysLookupExample();
		example.createCriteria().andTYPEEqualTo("DEPTLEVEL");
		example.setOrderByClause("DISPLAY_ORDER");
		return lookupDao.selectByExample(example);
	}

	/**
	 * 尋找所有父節點
	 * 
	 * @param defaultSelectedDept
	 * @return
	 */
	public String[] getParentByChild(String defaultSelectedDept) {
		List<DeptDTO> clanResult = deptDao.searchParentByChild(defaultSelectedDept);
		String[] parentArry = new String[clanResult.size()];
		for (int i = 0; i < clanResult.size(); i++) {
			parentArry[i] = clanResult.get(i).getDEPT_ID();
		}
		return parentArry;
	}

	/**
	 * 組合Hierarchy
	 * 
	 * @param fatherNode
	 * @param defaulthl
	 * @return
	 */
	public String mergerHierarchy(String[] fatherNode) {
		String hierarchy = null;
		if (fatherNode.length != 0) {
			hierarchy = fatherNode[0];
			for (int j = 1; j < fatherNode.length; j++) {
				hierarchy = fatherNode[j] + "_" + hierarchy;
			}
		}
		return hierarchy;
	}

	/**
	 * 更新Hierarchy
	 * 
	 * @param thnode
	 * @param Hierarchy
	 */
	public void updateHierarchy(String thnode, String Hierarchy) {
		TbOrgDept record = new TbOrgDept();
		TbOrgDeptExample example = new TbOrgDeptExample();
		record.setHIERARCHY(Hierarchy);
		example.createCriteria().andDEPT_IDEqualTo(thnode);
		deptDao.updateDept(record, example);
	}

	/**
	 * 取得此部門的所有子部門
	 * 
	 * @param subDept
	 * @return
	 */
	public List<TbOrgDept> getSubDept(String subDept) {
		TbOrgDeptExample example = new TbOrgDeptExample();
		example.createCriteria().andPARENT_DEPT_IDEqualTo(subDept);
		return deptDao.findByCondition(example);
	}

	/**
	 * 取HIERARCHY為空者(塞HIERARCHY用)
	 * 
	 * @return
	 */
	public List<TbOrgDept> getHierarchy() {
		TbOrgDeptExample example = new TbOrgDeptExample();
		example.createCriteria().andHIERARCHYIsNull();
		return deptDao.findByCondition(example);
	}
	
	/**
	 * 依縣市取得該區域所屬核網維運部
	 * @param cityId
	 * @param townId
	 * @return
	 * @author Charlie Woo
	 */
	public TbOrgDept getLineNetworkDept(String cityId, String townId) {
		TownDomainTeamDTO domainTeam = getTownDomainTeam(cityId, townId);
		String domain = StringUtils.equals("S", domainTeam.getDOMAIN()) ? domainTeam.getDOMAIN() : "HQ";
		TbOrgDeptExample example2 = new TbOrgDeptExample();
		example2.createCriteria().andDOMAINEqualTo(domain).andDEPT_LEVELEqualTo("DP").andDEPT_NAMELike("%核網維運%");
		List<TbOrgDept> depts = deptDao.findByCondition(example2);
		if (depts.isEmpty()) {
			log.info("找不到該區核網維運部, cityId=" + cityId + ", townId=" + townId);
			return null;
		}
		return depts.get(0);
	}
	
	public TownDomainTeamDTO getTownDomainTeam(String cityId, String townId) {
		return domainTeamDao.getTownDomainTeamByCityIdTownId(cityId, townId);
	}
	/**
	 * 依照條件查詢position
	 * @param example
	 * @return
	 */
	public List<TbOrgPosition> getPositionByCondition(TbOrgPositionExample example){
		return positionDao.selectOrgPosition(example);
	}
	
	/**
	 * 驗證此人員的主要工作
	 * @param psnNo
	 * @param deptPosId
	 * @return
	 */
	public String doValidatePrimaryJob(String psnNo ,String deptPosId){
		String message = "";
		TbAuthPersonnelExample example = new TbAuthPersonnelExample();
		example.createCriteria().andPSN_NOEqualTo(psnNo).andPRIMARY_DEPT_POS_IDEqualTo(deptPosId);
		List<TbAuthPersonnel> personnel = personnelDao.getPersonnelsByExample(example);
		if(personnel.size() > 0){
			message = "無法刪除主要工作的人員";
		}
		return message;
	}
}
