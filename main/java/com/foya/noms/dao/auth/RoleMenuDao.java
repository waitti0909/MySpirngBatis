package com.foya.noms.dao.auth;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbAuthRoleMenuMapper;
import com.foya.dao.mybatis.mapper.UTbAuthRoleMenuMapper;
import com.foya.dao.mybatis.model.TbAuthRoleMenu;
import com.foya.dao.mybatis.model.TbAuthRoleMenuKey;
import com.foya.exception.DbException;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.auth.RoleMenuFunDepDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

/**
 * The Class RoleMenuDao.
 */
@Repository
public class RoleMenuDao extends BaseDao{

	/** The log. */
	private Logger log = LoggerFactory.getLogger(this.getClass());

	
	/** The tb auth role menu mapper. */
	@Autowired
	private UTbAuthRoleMenuMapper uTbAuthRoleMenuMapper;
	
	/** The tb auth role menu mapper. */
	@Autowired
	private TbAuthRoleMenuMapper tbAuthRoleMenuMapper;
	
	
	/**
	 * Select role menu by role id.
	 *
	 * @param roleId the role id
	 * @return the list
	 */
	
	public List<RoleMenuFunDepDTO> selectRoleMenuByRoleId(Integer roleId){
		
		PageBounds pageBounds = getPageBounds();
		List<RoleMenuFunDepDTO> list =this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbAuthRoleMenuMapper.selectRoleMenuByRoleId",roleId, pageBounds);
		PageList<RoleMenuFunDepDTO> pageList = (PageList<RoleMenuFunDepDTO>)list;
		log.debug("totaql="+pageList.getPaginator().getTotalCount());

		return pageList;
		
		
//		
//		String sql = "SELECT rm.menu_id, rm.mdy_user_id, rm.mdy_tstmp, rm.role_id, m.menu_name, m.menu_desc, r.role_name, r.role_desc "
//+"FROM public.tb_auth_role_menu rm, public.tb_auth_menu m, public.tb_auth_role_mast r  "
//+"WHERE rm.menu_id = m.menu_id AND  rm.role_id = r.role_id and rm.role_id=? ";
//		return this.jdbcTemplate.query(sql,new Object[]{roleId} ,new BeanPropertyRowMapper<RoleMenuFunDepDTO>(
//				RoleMenuFunDepDTO.class));
		
	}
	
	
	

	/**
	 * Select role menu by menu id.
	 *
	 * @param menuId the menu id
	 * @return the list
	 */
	
	public List<RoleMenuFunDepDTO> selectRoleMenuByMenuId(Integer menuId){
		
		PageBounds pageBounds = getPageBounds();
		List<RoleMenuFunDepDTO> list =this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbAuthRoleMenuMapper.selectRoleMenuByMenuId",menuId, pageBounds);
		PageList<RoleMenuFunDepDTO> pageList = (PageList<RoleMenuFunDepDTO>)list;
		log.debug("totaql="+pageList.getPaginator().getTotalCount());
//		this.sqlSession.close();
		return pageList;
		
		
//		
//		String sql = "SELECT rm.menu_id, rm.mdy_user_id, rm.mdy_tstmp, rm.role_id, m.menu_name, m.menu_desc, r.role_name, r.role_desc "
//+"FROM public.tb_auth_role_menu rm, public.tb_auth_menu m, public.tb_auth_role_mast r  "
//+"WHERE rm.menu_id = m.menu_id AND  rm.role_id = r.role_id and rm.role_id=? ";
//		return this.jdbcTemplate.query(sql,new Object[]{roleId} ,new BeanPropertyRowMapper<RoleMenuFunDepDTO>(
//				RoleMenuFunDepDTO.class));
		
	}
	
	
	
	/**
	 * Search non owned menu by role id.
	 *
	 * @param roleId the role id
	 * @return the list
	 */
	public List<RoleMenuFunDepDTO> searchNonOwnedMenuByRoleId(Integer roleId){
		
		return uTbAuthRoleMenuMapper.searchNonOwnedMenuByRoleId(roleId);
	}
	
	
	
	/**
	 * Search non owned role by menu id.
	 *
	 * @param menuId the menu id
	 * @return the list
	 */
	public List<RoleMenuFunDepDTO> searchNonOwnedRoleByMenuId(Integer menuId){
		
		return uTbAuthRoleMenuMapper.searchNonOwnedRoleByMenuId(menuId);
	}
	
	/**
	 * Search owned menu by role id.
	 *
	 * @param roleId the role id
	 * @return the list
	 */
	public List<RoleMenuFunDepDTO> findSelectedMenuByRoleId(Integer roleId){
		
		return uTbAuthRoleMenuMapper.findSelectedMenuByRoleId(roleId);
	}
	
	
	/**
	 * Insert add role menu.
	 *
	 * @param roleMenu the role menu
	 * @return the int
	 */
	public int insertAddRoleMenu(TbAuthRoleMenu roleMenu){
		
		try {
		
			 tbAuthRoleMenuMapper.insert(roleMenu);
	
			return 0;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DbException("Add RoleMenu Fail:"+e.getMessage());
		}
		
	}
	
	public void delete(TbAuthRoleMenuKey key) {
		tbAuthRoleMenuMapper.deleteByPrimaryKey(key);
	}
	
}
