package com.foya.noms.service.org;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.dao.mybatis.model.TbAuthRoleMenuFunDept;
import com.foya.noms.dao.org.RoleMenuFunDeptDao;
import com.foya.noms.service.BaseService;

@Service
public class RoleMenuFunDeptService extends BaseService {
	
	@Autowired
	private RoleMenuFunDeptDao roleMenuFunDeptDao;
	
	/**
	 * 依照角色ID+選單ID取得目前可使用的部門資料限制
	 * @param roleId
	 * @param menuId
	 * @return
	 */
	public List<TbAuthRoleMenuFunDept> getUsedDeptByRoleMenu(String roleId, String menuId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("roleId", roleId);
		map.put("menuId", menuId);
		return roleMenuFunDeptDao.findUsedDeptByRoleMenu(map);
	}
}
