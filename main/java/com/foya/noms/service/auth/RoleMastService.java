package com.foya.noms.service.auth;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbAuthPsnRole;
import com.foya.dao.mybatis.model.TbAuthRoleMast;
import com.foya.exception.NomsException;
import com.foya.noms.dao.auth.PsnRoleDao;
import com.foya.noms.dao.auth.RoleMastDao;
import com.foya.noms.dto.auth.RoleDTO;
import com.foya.noms.dto.auth.RoleMastDTO;
import com.foya.noms.service.BaseService;

@Service
public class RoleMastService extends BaseService {

	@Autowired
	private RoleMastDao roleDao;

	@Autowired
	private PsnRoleDao userRoleDao;

	public List<RoleDTO> getAll() {
		return roleDao.selectAll();
	}

	public TbAuthRoleMast getByRoleId(Integer roleId) {
		return roleDao.selectByRoleId(new BigDecimal(roleId));
	}

	// 查詢
	public List<RoleMastDTO> getById(Integer roleId) {
		return roleDao.selectById(roleId);
	}

	// 新增
	public void addRole(TbAuthRoleMast record) {
		roleDao.insert(record);
	}

	// 更新
	public void editRole(TbAuthRoleMast role) {
		roleDao.update(role);
	}

	// 刪除
	@Transactional
	public void delete(String[] roleIdArray) throws NomsException {
//		boolean isdelete=true;
		//判斷是否可刪除系統角色
		for (String rId : roleIdArray) {
			BigDecimal roleId = BigDecimal.valueOf(Long.parseLong(rId));
			if (!(checkDelete(roleId))) {
				throw new NomsException("Can not deleted");
//				isdelete=false;
//				break;
			}
		}
		
		//刪除或拋異常
//		if(isdelete){
			for (String rId : roleIdArray) {
				BigDecimal roleId = BigDecimal.valueOf(Long.parseLong(rId));
				roleDao.delete(roleId);
			}
//		}else{
//			  throw new NomsException("Can not deleted");
//		}
	}

	/**
	 * 判斷是否可刪除系統角色
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	public boolean checkDelete(BigDecimal roleId) {
		List<TbAuthPsnRole> user_role = userRoleDao.selectAllPsnRole();
		for (int i = 0; i < user_role.size(); i++) {
			if ((user_role.get(i).getROLE_ID()).compareTo(roleId) == 0) {
				return false;
			}
		}
		return true;
	}
}
