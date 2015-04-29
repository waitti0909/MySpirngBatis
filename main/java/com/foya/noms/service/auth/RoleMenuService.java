package com.foya.noms.service.auth;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbAuthMenu;
import com.foya.dao.mybatis.model.TbAuthRoleMenu;
import com.foya.dao.mybatis.model.TbAuthRoleMenuFun;
import com.foya.dao.mybatis.model.TbAuthRoleMenuFunDeptExample;
import com.foya.dao.mybatis.model.TbAuthRoleMenuFunExample;
import com.foya.dao.mybatis.model.TbAuthRoleMenuKey;
import com.foya.noms.dao.auth.MenuDao;
import com.foya.noms.dao.auth.RoleMenuDao;
import com.foya.noms.dao.auth.RoleMenuFunDao;
import com.foya.noms.dao.org.RoleMenuFunDeptDao;
import com.foya.noms.dto.auth.RoleMenuFunDepDTO;
import com.foya.noms.service.BaseService;
import com.github.miemiedev.mybatis.paginator.domain.PageList;


/**
 * The Class RoleMenuService.
 */
@Service

public class RoleMenuService extends BaseService {

	/** The role menu dao. */
	@Autowired
	private RoleMenuDao roleMenuDao;
	@Autowired
	private MenuDao menuDao;	
	@Autowired
	private RoleMenuFunDao roleMenuFunDao;
	@Autowired
	private RoleMenuFunDeptDao roleMenuFunDeptDao;
	
	/**
	 * Select role menu dto by role id.
	 *
	 * @param roleId the role id
	 * @return the list
	 */
	public List<RoleMenuFunDepDTO> selectRoleMenuFunDepDTOByRoleId(Integer roleId){
		
		List<RoleMenuFunDepDTO> result = roleMenuDao.selectRoleMenuByRoleId(roleId);// roleMenuFunDao.findRoleMenuFunByRoleId(roleId);
		PageList<RoleMenuFunDepDTO> a= (PageList<RoleMenuFunDepDTO>) result;
//		log.debug("RoleMenuFunDepDTO size="+result.size());
		log.debug("RoleMenuFunDepDTO total size="+a.getPaginator().getTotalCount());
//		
		return result;
	}
	
	
	
	/**
	 * Select role menu dto by menu id.
	 *
	 * @param menuId the menu id
	 * @return the list
	 */
	public List<RoleMenuFunDepDTO> selectRoleMenuFunDepDTOByMenuId(Integer menuId){
		
		List<RoleMenuFunDepDTO> result = roleMenuDao.selectRoleMenuByMenuId(menuId);// roleMenuFunDao.findRoleMenuFunByMenuId(menuId);
		PageList<RoleMenuFunDepDTO> a= (PageList<RoleMenuFunDepDTO>) result;
//		log.debug("RoleMenuFunDepDTO size="+result.size());
		log.debug("RoleMenuFunDepDTO total size="+a.getPaginator().getTotalCount());
//		
		return result;
	}
	
	/**
	 * Search non owned menu by role id.
	 *
	 * @param roleId the role id
	 * @return the list
	 */
	public List<RoleMenuFunDepDTO> searchNonOwnedMenuByRoleId( Integer roleId){
		
		List<RoleMenuFunDepDTO> result = roleMenuDao.searchNonOwnedMenuByRoleId(roleId);
		
		return result;
	}
	
	

	/**
	 * Search non owned role by menu id.
	 *
	 * @param menuId the menu id
	 * @return the list
	 */
	public List<RoleMenuFunDepDTO> searchNonOwnedRoleByMenuId( Integer menuId){
		
		List<RoleMenuFunDepDTO> result = roleMenuDao.searchNonOwnedRoleByMenuId(menuId);
		
		return result;
	}
	
	/**
	 * Search owned menu by role id.
	 *
	 * @param roleId the role id
	 * @return the list
	 */
	public List<RoleMenuFunDepDTO> getSelectedMenuByRoleId( Integer roleId){
		
		List<RoleMenuFunDepDTO> result = roleMenuDao.findSelectedMenuByRoleId(roleId);
		
		return result;
	}
	
	private void searchParentMenu(BigDecimal roldId, BigDecimal menuId, List<TbAuthRoleMenu> roleMenuList, 
			String currentUserName, Date now, Set<BigDecimal> preparedToSaveMenu){
		
		TbAuthMenu currentMenu = menuDao.getMenuByMenuId(menuId.intValue());
		if(currentMenu!=null){			
			if(currentMenu.getPARENT_ID()!=null && currentMenu.getPARENT_ID().intValue()>0){
				log.debug("searchParentMenu "+currentMenu.getPARENT_ID());
				 searchParentMenu( roldId,currentMenu.getPARENT_ID(),roleMenuList,currentUserName,now,preparedToSaveMenu);
			}
			
			// 檢查SET中是否已經有該筆MENU_ID存在，有就跳過
			if (preparedToSaveMenu.contains(currentMenu.getMENU_ID())) {
				return;
			}
			
			log.debug("Add Menu");
			TbAuthRoleMenu tbAuthRoleMenu = new TbAuthRoleMenu();
			tbAuthRoleMenu.setROLE_ID(roldId);
			tbAuthRoleMenu.setMENU_ID(currentMenu.getMENU_ID());
			tbAuthRoleMenu.setMD_USER(currentUserName);
			tbAuthRoleMenu.setMD_TIME(now);
			roleMenuList.add(tbAuthRoleMenu);
			
			// 將準備儲存的MENU記錄至SET中
			preparedToSaveMenu.add(currentMenu.getMENU_ID());
		}
	}
	
	/**
	 * save role menu.
	 *
	 * @param roleMenuList the role menu list
	 */	
	@Transactional
	public void saveRoleMenu(List<TbAuthRoleMenu> roleMenuList) throws RuntimeException{
		
		List<TbAuthRoleMenu> saveList = new ArrayList<>();	
		Set<BigDecimal> preparedToSaveMenu = new HashSet<BigDecimal>(); // 需要一個SET來記錄遞迴中準備要儲存的MENU，以免重複寫入
		Set<BigDecimal> existMenu = new HashSet<BigDecimal>(); // 目前資料庫已有的MENU
		BigDecimal role_ID = null;
		
		// 遞迴找出上層menu
		for(TbAuthRoleMenu roleMenu:roleMenuList){
			log.debug("now menuId is " + roleMenu.getMENU_ID());
			// 先過濾該節點ID是不是FOLDER，if true then continue
			TbAuthMenu menu = menuDao.getMenuByMenuId(roleMenu.getMENU_ID().intValue());
			if (StringUtils.equals("1", menu.getIS_FODR())) continue;
			
			role_ID = roleMenu.getROLE_ID();
			searchParentMenu(roleMenu.getROLE_ID(), roleMenu.getMENU_ID(), saveList, roleMenu.getMD_USER(), 
					roleMenu.getMD_TIME(),preparedToSaveMenu);
		}
	
		// 找出已存在DB的清單(準備過濾用)
		List<RoleMenuFunDepDTO> existList = roleMenuDao.findSelectedMenuByRoleId(role_ID.intValue());
		for (RoleMenuFunDepDTO dto : existList) {
			existMenu.add(BigDecimal.valueOf(dto.getMenuId()));
		}
				
		// delete if remove role menu authentication
		Set<BigDecimal> deleteMenu = new HashSet<>(existMenu);
		deleteMenu.removeAll(preparedToSaveMenu);
		for (RoleMenuFunDepDTO dto : existList) {
//			boolean deleteFlag = true;
//			for (TbAuthRoleMenu roleMenu : saveList) {
//				if (roleMenu.getMENU_ID().intValue() == dto.getMenuId().intValue()) {
//					deleteFlag = false;
//				}
//			}
//			if(deleteFlag) {
			if (deleteMenu.contains(BigDecimal.valueOf(dto.getMenuId()))) {
				log.debug("remove menu id ___" + dto.getMenuId().intValue());
				// 1. remove TbAuthRoleMenu
				TbAuthRoleMenuKey key = new TbAuthRoleMenuKey();
				key.setROLE_ID(role_ID);
				key.setMENU_ID(new BigDecimal(dto.getMenuId()));
				roleMenuDao.delete(key);
				
				// 2. remove TbAuthRoleMenuFun
				TbAuthRoleMenuFunExample example = new TbAuthRoleMenuFunExample();
				example.createCriteria().andROLE_IDEqualTo(role_ID).andMENU_IDEqualTo(new BigDecimal(dto.getMenuId()));
				
				List<TbAuthRoleMenuFun> rows = roleMenuFunDao.selectByExample(example);
				roleMenuFunDao.delete(example);
				
				// 3. remove TbAuthRoleMenuFunDept
				TbAuthRoleMenuFunDeptExample example2 = new TbAuthRoleMenuFunDeptExample();
				for (TbAuthRoleMenuFun row : rows) {
					example2.createCriteria().andROLE_MENU_FUN_IDEqualTo(row.getROLE_MENU_FUN_ID());
					roleMenuFunDeptDao.delete(example2);
				}
			}
		}
		
		// insert if add role menu authentication
		Set<BigDecimal> insertMenu = new HashSet<>(preparedToSaveMenu);
		insertMenu.removeAll(existMenu);
		for (TbAuthRoleMenu roleMenu:saveList){
//					boolean insertFlag = true;
//					for (RoleMenuFunDepDTO dto:existList){
//						if(dto.getMenuId().intValue() == roleMenu.getMENU_ID().intValue()){
//							insertFlag = false;
//							break;
//						}
//					}
//					if(insertFlag){
			if (insertMenu.contains(roleMenu.getMENU_ID())) {
				log.debug("insert menu id ___" + roleMenu.getMENU_ID().intValue());
				roleMenuDao.insertAddRoleMenu(roleMenu);
			}
		}
		
	}
	
	@Transactional
	public void delete(List<TbAuthRoleMenuKey> keys) {
		for(TbAuthRoleMenuKey key : keys){
			if(key != null){
				// 1. remove TbAuthRoleMenu
				roleMenuDao.delete(key);
				
				// 2. remove TbAuthRoleMenuFun
				TbAuthRoleMenuFunExample example = new TbAuthRoleMenuFunExample();
				example.createCriteria().andROLE_IDEqualTo(key.getROLE_ID()).andMENU_IDEqualTo(key.getMENU_ID());
				
				List<TbAuthRoleMenuFun> rows = roleMenuFunDao.selectByExample(example);
				roleMenuFunDao.delete(example);
				
				// 3. remove TbAuthRoleMenuFunDept
				TbAuthRoleMenuFunDeptExample example2 = new TbAuthRoleMenuFunDeptExample();
				for (TbAuthRoleMenuFun row : rows) {
					example2.createCriteria().andROLE_MENU_FUN_IDEqualTo(row.getROLE_MENU_FUN_ID());
					roleMenuFunDeptDao.delete(example2);
				}
			}
		}
	}
}
