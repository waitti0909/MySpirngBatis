package com.foya.noms.service.auth;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbAuthRoleMenuFun;
import com.foya.dao.mybatis.model.TbAuthRoleMenuFunDept;
import com.foya.dao.mybatis.model.TbAuthRoleMenuFunDeptExample;
import com.foya.dao.mybatis.model.TbAuthRoleMenuFunExample;
import com.foya.noms.dao.auth.RoleMenuFunDao;
import com.foya.noms.dao.org.RoleMenuFunDeptDao;
import com.foya.noms.dto.auth.RoleMenuFunDTO;
import com.foya.noms.dto.auth.RoleMenuFunDepDTO;
import com.foya.noms.service.BaseService;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Service
public class RoleMenuFunService extends BaseService {
	
	@Autowired
	private RoleMenuFunDao roleMenuFunDao;
	@Autowired
	private RoleMenuFunDeptDao roleMenuFunDeptDao;
	
	public List<RoleMenuFunDTO> getFuncitonByPsnAndMenuId(Integer psnId, Integer menuId) {
		
		Map<String, Integer> condition = new HashMap<String, Integer>();
		condition.put("psnId", psnId);
		condition.put("menuId", menuId);
		return roleMenuFunDao.findFunctionByPsnAndMenuId(condition);
	}
	
	/**
	 * Select role menu dto by menu id.
	 *
	 * @param menuId the menu id
	 * @return the list
	 */
	public List<RoleMenuFunDepDTO> selectFuncDepDTOByRoleAndMenuForGrid(Integer roleId, Integer menuId){
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("roleId", roleId);
		map.put("menuId", menuId);
		List<RoleMenuFunDepDTO> result = roleMenuFunDao.findFuncDeptByRoleAndMenuForGrid(map);
		PageList<RoleMenuFunDepDTO> a = (PageList<RoleMenuFunDepDTO>) result;
//		log.debug("RoleMenuFunDepDTO size="+result.size());
		log.debug("RoleMenuFunDepDTO total size="+a.getPaginator().getTotalCount());
//		
		return result;
	}
	
	
	public List<RoleMenuFunDepDTO> getFuncDepByRoleAndMenu(Integer roleId, Integer menuId) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("roleId", roleId);
		map.put("menuId", menuId);
		return roleMenuFunDao.findFuncDeptByRoleAndMenu(map);
	}

	@Transactional
	public void saveRoleMenuFun(BigDecimal roleId, BigDecimal menuId, BigDecimal[] menuFunIds, String[] deptIds, String user) {
		// 1. delete functions by role and menu.
		TbAuthRoleMenuFunExample example = new TbAuthRoleMenuFunExample();
		example.createCriteria().andROLE_IDEqualTo(roleId).andMENU_IDEqualTo(menuId);
		List<TbAuthRoleMenuFun> rows = roleMenuFunDao.selectByExample(example);
		roleMenuFunDao.delete(example);
		
		// 2. delete depts by role and menu.
		for (TbAuthRoleMenuFun row : rows) {			
			TbAuthRoleMenuFunDeptExample example2 = new TbAuthRoleMenuFunDeptExample();
			example2.createCriteria().andROLE_MENU_FUN_IDEqualTo(row.getROLE_MENU_FUN_ID());
			roleMenuFunDeptDao.delete(example2);
		}
		
		Date nowTime = new Date();
		// 3. insert functions
		if (ArrayUtils.isNotEmpty(menuFunIds)) {			
			for (BigDecimal menuFunId : menuFunIds) {
				TbAuthRoleMenuFun menuFunModel = new TbAuthRoleMenuFun();
				menuFunModel.setROLE_ID(roleId);
				menuFunModel.setMENU_ID(menuId);
				menuFunModel.setMENU_FUN_ID(menuFunId);
				menuFunModel.setMD_USER(user);
				menuFunModel.setMD_TIME(nowTime);
				
				roleMenuFunDao.insert(menuFunModel);
				
				// 4. insert departments
				if (ArrayUtils.isNotEmpty(deptIds)) {	
					for (String deptId : deptIds) {
						TbAuthRoleMenuFunDept funDeptModel = new TbAuthRoleMenuFunDept();
						funDeptModel.setROLE_MENU_FUN_ID(menuFunModel.getROLE_MENU_FUN_ID());
						funDeptModel.setDEPT_ID(deptId);
						funDeptModel.setMD_USER(user);
						funDeptModel.setMD_TIME(nowTime);
						
						roleMenuFunDeptDao.insert(funDeptModel);
					}
				}
			}
		}
		
			
	}
}
