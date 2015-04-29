package com.foya.noms.service.org;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.dao.mybatis.model.TbSysLookupExample;
import com.foya.noms.dao.org.DeptDao;
import com.foya.noms.dao.org.DeptPosDao;
import com.foya.noms.dao.org.PsnPosDao;
import com.foya.noms.dao.system.LookupDao;
import com.foya.noms.dto.auth.UserDTO;
import com.foya.noms.dto.org.DeptDTO;
import com.foya.noms.dto.org.DeptPosDTO;
import com.foya.noms.service.BaseService;
import com.foya.noms.service.auth.PersonnelService;

@Service
public class OrgWorkflowService extends BaseService {

	@Autowired
	private DeptDao deptDao;
	@Autowired
	private PsnPosDao psnPosDao;
	@Autowired
	private DeptPosDao deptPosDao;
	@Autowired
	PersonnelService personnelService;
	// @Autowired
	// LsSignCondDao lsSignCondDao;
	@Autowired
	LookupDao lookupDao;

	public TbOrgDept getTbOrgDept(String deptId) {
		return deptDao.findByPk(deptId);
	}

	/**
	 * Search parent by child.
	 *
	 * @param deptId the dept id
	 * @return the list
	 */
	public List<DeptDTO> searchParentByChild(String deptId) {
		return deptDao.searchParentByChild(deptId);
	}

	/**
	 * Gets the dept pos dto by dept pos id.
	 *
	 * @param deptPosId the dept pos id
	 * @return the dept pos dto by dept pos id
	 */
	public List<DeptPosDTO> getDeptPosDtoByDeptPosId(String deptPosId) {
		return deptPosDao.getDeptPosDtoByDeptPosId(deptPosId);
	}

	/**
	 * Gets the dept pos dto by dept id.
	 *
	 * @param deptId the dept id
	 * @return the dept pos dto by dept id
	 */
	public List<DeptPosDTO> getDeptPosDtoByDeptId(String deptId) {
		return deptPosDao.getDeptPosDtoByDeptId(deptId);
	}

	/**
	 * Gets the dept pos dto by dept id and pos id.
	 *
	 * @param deptId the dept id
	 * @param posId the pos id
	 * @return the dept pos dto by dept id and pos id
	 */
	public DeptPosDTO getDeptPosDtoByDeptIdAndPosId(String deptId, String posId) {
		List<DeptPosDTO> deptPosDtos = deptPosDao.getDeptPosDtoByDeptIdAndPosId(deptId, posId);
		if (deptPosDtos == null || deptPosDtos.isEmpty()) {
			return null;
		}
		return deptPosDtos.get(0);
	}

	/**
	 * Gets the dept pos dto by pos id.
	 *
	 * @param posId the pos id
	 * @return the dept pos dto by pos id
	 */
	public List<DeptPosDTO> getDeptPosDtoByPosId(String posId) {
		return deptPosDao.getDeptPosDtoByPosId(posId);
	}

	/**
	 * Gets the dept all under dept pos by dept id.
	 *
	 * @param deptId the dept id
	 * @return the dept all under dept pos by dept id
	 */
	public List<DeptPosDTO> getDeptAllUnderDeptPosByDeptId(String deptId) {
		return deptPosDao.getDeptAllUnderDeptPosByDeptId(deptId);
	}

	/**
	 * Gets the user by dept pos id.
	 *
	 * @param deptPosId the dept pos id
	 * @return the user by dept pos id
	 */
	public List<UserDTO> getUserByDeptPosId(String deptPosId) {
		return personnelService.getUserDtoByDeptPosId(deptPosId);
	}

	/**
	 * Gets the user dto.
	 *
	 * @param psnNo the psn no
	 * @return the user dto
	 */
	public UserDTO getUserDTO(String psnNo) {
		return personnelService.getUserDto(psnNo);
	}

	/**
	 * Find ls sign cond.
	 *
	 * @return the list
	 */
	// TODO release 資材
	// public List<TbLsSignCond> findLsSignCond() {
	// return lsSignCondDao.findLsSignCond();
	// }

	public List<TbSysLookup> getSysLookupByType(Collection<String> types) {
		if (types == null || types.isEmpty()) {
			return new ArrayList<>(0);
		}
		TbSysLookupExample example = new TbSysLookupExample();
		example.createCriteria().andTYPEIn(new ArrayList<String>(types));
		return lookupDao.selectByExample(example);
	}
}
