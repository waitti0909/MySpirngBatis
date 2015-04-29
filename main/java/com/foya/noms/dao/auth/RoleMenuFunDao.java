package com.foya.noms.dao.auth;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbAuthRoleMenuFunMapper;
import com.foya.dao.mybatis.mapper.UTbAuthRoleMenuFunMapper;
import com.foya.dao.mybatis.model.TbAuthRoleMenuFun;
import com.foya.dao.mybatis.model.TbAuthRoleMenuFunExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.auth.RoleMenuFunDTO;
import com.foya.noms.dto.auth.RoleMenuFunDepDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

/**
 * The Class RoleMenuFunDao.
 */
@Repository
public class RoleMenuFunDao extends BaseDao {

	/** The mapper. */
	@Autowired
	private TbAuthRoleMenuFunMapper mapper;
	@Autowired
	private UTbAuthRoleMenuFunMapper uMapper;
	
	public int insert(TbAuthRoleMenuFun record) {
		return mapper.insertSelective(record);
	}
	
	public int delete(BigDecimal roleMenuFunId) {
		return mapper.deleteByPrimaryKey(roleMenuFunId);
	}
	
	public int delete(TbAuthRoleMenuFunExample example) {
		return mapper.deleteByExample(example);
	}
	
	public int update(TbAuthRoleMenuFun record) {
		return mapper.updateByPrimaryKey(record);
	}
	
	public List<TbAuthRoleMenuFun> selectByExample(TbAuthRoleMenuFunExample example) {
		return mapper.selectByExample(example);
	}
	
	/**
	 * 根據用戶id獲取角色集合.
	 *
	 * @param condition the condition
	 * @return the list
	 */
	public List<RoleMenuFunDTO> findFunctionByPsnAndMenuId(Map<String, Integer> condition){
		return uMapper.findFunctionByPsnAndMenuId(condition);
	}
	
	/**
	 * Find role menu fun by menu id.
	 *
	 * @param menuId the menu id
	 * @return the list
	 */
	public List<RoleMenuFunDepDTO> findRoleMenuFunByMenuId(Integer menuId){
		String sortString = "MENU_ID";
		PageBounds pageBounds = getPageBounds(sortString);
		List<RoleMenuFunDepDTO> list =this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbAuthRoleMenuFunMapper.findRoleMenuByMenuId",menuId, pageBounds);
		PageList<RoleMenuFunDepDTO> pageList = (PageList<RoleMenuFunDepDTO>)list;
//		log.debug("totaql="+pageList.getPaginator().getTotalCount());
//		this.sqlSession.close();
		return pageList;
		
	}
	
	
	/**
	 * Find role menu fun by role id.
	 *
	 * @param roleId the role id
	 * @return the list
	 */
	public List<RoleMenuFunDepDTO> findRoleMenuFunByRoleId(Integer roleId){
		String sortString = "MENU_ID";
		PageBounds pageBounds = getPageBounds(sortString);
		
		List<RoleMenuFunDepDTO> list =this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbAuthRoleMenuFunMapper.findRoleMenuByRoleId",roleId, pageBounds);
		PageList<RoleMenuFunDepDTO> pageList = (PageList<RoleMenuFunDepDTO>)list;
//		log.debug("totaql="+pageList.getPaginator().getTotalCount());
//		this.sqlSession.close();
		return pageList;
	
	}
	
	public List<RoleMenuFunDepDTO> findFuncDeptByRoleAndMenuForGrid(HashMap<String, Integer> map) {
		String sortString = "MENU_ID, DISPLAY_ORDER";
		PageBounds pageBounds = getPageBounds(sortString);
		
		List<RoleMenuFunDepDTO> list =this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbAuthRoleMenuFunMapper.findFuncDeptByRoleAndMenu", map, pageBounds);
		PageList<RoleMenuFunDepDTO> pageList = (PageList<RoleMenuFunDepDTO>)list;
//		log.debug("totaql="+pageList.getPaginator().getTotalCount());
//		this.sqlSession.close();
		return pageList;
		
	}
	
	public List<RoleMenuFunDepDTO> findFuncDeptByRoleAndMenu(HashMap<String, Integer> map) {
		return uMapper.findFuncDeptByRoleAndMenu(map);
		
	}
	
	public List<RoleMenuFunDepDTO> findRoleMenuByMenuId(Integer menuId){
		
		return uMapper.findRoleMenuByMenuId(menuId);
		
	}
	
	/**
	 * 用menuID找roleMenuFun對應menuFun的fun_code
	 * @param menuId
	 * @return
	 */
	public List<RoleMenuFunDTO> selectRoleMenuFunByMenuId(Integer menuId){
		return uMapper.selectRoleMenuFunByMenuId(menuId);
	}
}
