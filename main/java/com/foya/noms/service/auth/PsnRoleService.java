package com.foya.noms.service.auth;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbAuthPsnRole;
import com.foya.dao.mybatis.model.TbAuthRoleMast;
import com.foya.noms.dao.auth.PsnRoleDao;
import com.foya.noms.dao.auth.RoleMastDao;
import com.foya.noms.service.BaseService;

@Service
public class PsnRoleService extends BaseService {
	
	@Autowired
	private PsnRoleDao userRoleDao;
	
	@Autowired
	private RoleMastDao roleDao;
	
	//取得所有使用者的角色(distinct)
	public List<TbAuthPsnRole> getAllPsnRole() {
		return userRoleDao.selectAllPsnRole();
	}
	
	//新增、修改、刪除
	@Transactional
	public void addPsnRole(TbAuthRoleMast role,String[] userIdArray,String currentUserId) {
		TbAuthPsnRole roleUser = new TbAuthPsnRole();
		Date nowtime = new Date();
		
		//修改RoleMast
		if(!(role.getROLE_DESC().equals("noedit"))){
			//進行更新
			role.setMD_TIME(nowtime);
			roleDao.update(role);
		}
		
		//新刪PsnRole
		List<TbAuthPsnRole> userSelected = userRoleDao.searchUserByRole(role.getROLE_ID());
		//Psn全部新增
		if(userSelected.size()==0){
			for (String userid : userIdArray) { 
				roleUser.setPSN_ID(BigDecimal.valueOf(Integer.parseInt(userid)));
				roleUser.setROLE_ID(role.getROLE_ID());
				roleUser.setMD_USER(currentUserId);
				roleUser.setMD_TIME(nowtime);
				log.debug("新增"+BigDecimal.valueOf(Integer.parseInt(userid)));
				userRoleDao.addUserRole(roleUser);
			 }
		}else{
			//Psn部分新增
			for (String userid : userIdArray) {
				boolean addincluded = false; // 頁面選取是否在原始選取中
				BigDecimal userId = BigDecimal.valueOf(Long.parseLong(userid));
				for(int i=0;i<userSelected.size();i++) {
					if(userId.equals(userSelected.get(i).getPSN_ID())){						
						addincluded = true;
				        break;	
					}		
				}
				//頁面選取不在原始選取中，則新增
				 if (!addincluded) {
					 if(!(userId.equals(BigDecimal.ZERO))) {
						 roleUser.setPSN_ID(userId);
						 roleUser.setROLE_ID(role.getROLE_ID());
						 roleUser.setMD_USER(currentUserId);
						 roleUser.setMD_TIME(nowtime);
						 log.debug("新增"+userId);
						 userRoleDao.addUserRole(roleUser);
					 }
				 }
			}
			//Psn部分刪除
			for(int i=0;i<userSelected.size();i++) {
				boolean deleteincluded = false; // 原始選取是否在頁面選取中
			for (String userid : userIdArray) {
				BigDecimal userId = BigDecimal.valueOf(Long.parseLong(userid));
				if((userSelected.get(i).getPSN_ID().equals(userId))){
					deleteincluded = true;
			        break;	
				}				
				}
			//原始選取不在頁面選取中，則刪除
			 if (!deleteincluded) {
				 log.debug("刪除"+userSelected.get(i).getPSN_ID());
				 roleUser.setPSN_ID(userSelected.get(i).getPSN_ID());
				 roleUser.setROLE_ID(role.getROLE_ID());
				 userRoleDao.removeUserRole(roleUser);
			 }		
			}
		}
	}
	
	
	
	//根據角色id查詢使用者
	public List<TbAuthPsnRole> getUserByRole(BigDecimal roleId) {
		return userRoleDao.searchUserByRole(roleId);
	}
}

