package com.foya.noms.service.system;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbOrgPosition;
import com.foya.dao.mybatis.model.TbOrgPositionExample;
import com.foya.dao.mybatis.model.TbSysSignLog;
import com.foya.dao.mybatis.model.TbWorkflowCfg;
import com.foya.noms.dao.org.DeptDao;
import com.foya.noms.dao.org.PositionDao;
import com.foya.noms.dao.system.SignLogDao;
import com.foya.noms.dao.workflow.WorkflowCfgDao;
import com.foya.noms.dto.auth.UserDTO;
import com.foya.noms.dto.system.SignLogDTO;
import com.foya.noms.dto.workflow.WorkflowWebDTO;
import com.foya.noms.service.BaseService;
import com.foya.noms.util.DateUtils;
import com.foya.noms.util.ParserUtil;

@Service
public class SignLogService extends BaseService {
	@Autowired
	private SignLogDao signLogDao;
	@Autowired
	private WorkflowCfgDao workflowCfgDao;
	@Autowired
	private DeptDao deptDao;
	@Autowired
	private PositionDao positionDao;

	/**
	 * 填寫簽核記錄檔
	 * 
	 * @param workflowBtnDtos
	 * @param approved
	 */
	@Transactional
	public void insertSignLogList(Collection<WorkflowWebDTO> workflowWebDtos, String approved) {
		Date signTime = DateUtils.today();
		Map<String, String> deptIdName = new HashMap<>();
		Map<String, String> posIdName = new HashMap<>();
		for (WorkflowWebDTO workflowWebDto : workflowWebDtos) {
			String deptPosId = workflowWebDto.getTaskOwnerGroupId();
			String deptId = null;
			String deptName = null;
			String posId = null;
			if (StringUtils.isBlank(deptPosId)) {
				UserDTO user = getLoginUser();
				deptPosId = user.getPrimaryDeptPosId();
				deptId = user.getDeptId();
				deptName = user.getDeptName();
				posId = ParserUtil.getPosIdByDeptPosId(deptPosId);
			} else {
				deptId = ParserUtil.getDeptIdByDeptPosId(deptPosId);
				deptName = deptIdName.get(deptId);
				if (deptName == null) {
					deptName = getDeptName(deptId);
					deptIdName.put(deptId, deptName);
				}
				posId = ParserUtil.getPosIdByDeptPosId(deptPosId);

			}
			String posName = posIdName.get(posId);
			if (posName == null) {
				posName = getPosName(posId);
				posIdName.put(posId, posName);
			}
			
			TbSysSignLog record = new TbSysSignLog();
			record.setPROCESS_TYPE(workflowWebDto.getProcessType());
			record.setAPPLY_NO(workflowWebDto.getApplyNo());
			record.setSIGN_USER(getLoginUser().getUsername());
			record.setSIGN_USER_NAME(getLoginUser().getChName());
			record.setSIGN_DEPT(deptId);
			record.setSIGN_DEPT_NAME(deptName);
			record.setSIGN_POS(posId);
			record.setSIGN_POS_NAME(posName);

			record.setAPPROVED(approved);
			record.setCOMMENT(workflowWebDto.getComment());
			// record.setPREV_USER(...);
			Date taskStatrTime = workflowWebDto.getTaskStartTime();
			record.setTASK_START_TIME(taskStatrTime);
			record.setSIGN_TIME(signTime);
			// 處理時間(毫秒)
			long procTime = (signTime.getTime() - taskStatrTime.getTime());
			record.setPROC_TIME(procTime);
			record.setPRINCIPAL_USER(workflowWebDto.getTaskOwnerId());
			signLogDao.insert(record);
		}
	}

	/**
	 * 填寫簽核記錄檔(單筆)
	 * 
	 * @param WorkflowWebDTO
	 * @param approved
	 * @param signTime
	 */
	@Transactional
	public void insertSignLog(WorkflowWebDTO workflowWebDto, String approved) {

		String deptPosId = workflowWebDto.getTaskOwnerGroupId();
		String deptId = null;
		String deptName = null;
		String posId = null;
		if (StringUtils.isBlank(deptPosId)) {
			UserDTO user = getLoginUser();
			deptPosId = user.getPrimaryDeptPosId();
			deptId = user.getDeptId();
			deptName = user.getDeptName();
			posId = ParserUtil.getPosIdByDeptPosId(deptPosId);
		} else {
			deptId = ParserUtil.getDeptIdByDeptPosId(deptPosId);
			deptName = getDeptName(deptId);
			posId = ParserUtil.getPosIdByDeptPosId(deptPosId);
		}
		String posName = getPosName(posId);
		
		TbSysSignLog record = new TbSysSignLog();
		record.setPROCESS_TYPE(workflowWebDto.getProcessType());
		record.setAPPLY_NO(workflowWebDto.getApplyNo());
		record.setSIGN_USER(getLoginUser().getUsername());
		record.setSIGN_USER_NAME(getLoginUser().getChName());
		record.setSIGN_DEPT(deptId);
		record.setSIGN_DEPT_NAME(deptName);
		record.setSIGN_POS(posId);
		record.setSIGN_POS_NAME(posName);

		record.setAPPROVED(approved);
		record.setCOMMENT(workflowWebDto.getComment());
		// record.setPREV_USER(...);
		Date taskStatrTime = workflowWebDto.getTaskStartTime();
		record.setTASK_START_TIME(taskStatrTime);
		Date signTime = DateUtils.today();
		record.setSIGN_TIME(signTime);
		// 處理時間(毫秒)
		long procTime = (signTime.getTime() - taskStatrTime.getTime());
		record.setPROC_TIME(procTime);
		record.setPRINCIPAL_USER(workflowWebDto.getTaskOwnerId());
		signLogDao.insert(record);
	}

	public String getDeptName(String deptId) {
		TbOrgDept dept = deptDao.findByPk(deptId);
		if (dept == null) {
			return "";
		}
		return dept.getDEPT_NAME();
	}

	public String getPosName(String posId) {
		TbOrgPositionExample example = new TbOrgPositionExample();
		example.createCriteria().andPOS_IDEqualTo(posId);
		List<TbOrgPosition> tbOrgPosition = positionDao.selectOrgPosition(example);
		if (tbOrgPosition == null || tbOrgPosition.isEmpty()) {
			return "";
		}
		return tbOrgPosition.get(0).getPOS_NAME();
	}

	public List<SignLogDTO> getSignHistory(String processType, String applyNo) {
		if (StringUtils.isBlank(processType) || StringUtils.isBlank(applyNo)) {
			return new ArrayList<>(0);
		}
		List<TbWorkflowCfg> cfgs = workflowCfgDao.findSameGroupByProcessType(processType);
		List<String> processTypes = null;
		if (cfgs.isEmpty()) {
			processTypes = new ArrayList<>();
			processTypes.add(processType);
		} else {
			processTypes = new ArrayList<>(cfgs.size());
			for (TbWorkflowCfg cfg : cfgs) {
				processTypes.add(cfg.getPROCESS_TYPE());
			}
		}
		return signLogDao.getSignHistory(processTypes, applyNo);
	}

}
