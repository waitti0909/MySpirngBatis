package com.foya.noms.dao.auth;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbAuthMenuMapper;
import com.foya.dao.mybatis.mapper.UTbAuthMenuMapper;
import com.foya.dao.mybatis.model.TbAuthMenu;
import com.foya.dao.mybatis.model.TbAuthMenuExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.auth.MenuDTO;
import com.foya.noms.security.model.Menu;
import com.foya.noms.security.persistence.MenuMapper;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

/**
 * The Class MenuDao.
 */
@Repository
public class MenuDao extends BaseDao{

	/** The log. */
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	/** The menu mapper. */
	@Autowired
	private MenuMapper menuMapper;
	/** The menu mapper. */
	@Autowired
	private TbAuthMenuMapper tbAuthMenuMapper;
	
	/** The u tb auth menu mapper. */
	@Autowired
	private UTbAuthMenuMapper uTbAuthMenuMapper;
	
		
	/**
	 * 根據使用者id 取得最上層menu.
	 *
	 * @param psnId the psn id
	 * @return the root menu by psn id
	 */
	public List<Menu> getRootMenuByPsnId(Integer psnId){
		return menuMapper.getRootMenuByPsnId(psnId);
	}
	
	/**
	 * 根據角色id 取得最上層menu.
	 *
	 * @param roleId the role id
	 * @return the root menu by role id
	 */
	public List<Menu> getRootMenuByRoleId(Integer... roleId){
		return menuMapper.getRootMenuByRoleId(roleId);
	}
	
	/**
	 * 根據使用者id 取得menu底下的清單.
	 *
	 * @param psnId the psn id
	 * @param parentMenuId the parent menu id
	 * @return the child menu by parent id rold id
	 */
	public List<Menu> getChildMenuByParentIdRoldId(@Param("psnId")Integer psnId,@Param("parentMenuId")Integer parentMenuId){
		return menuMapper.getChildMenuByParentIdRoldId(psnId, parentMenuId);
	}
	
	/**
	 * 取得全部menu.
	 *
	 * @return the all menu
	 */
	public List<Menu> getAllMenu(){
		return menuMapper.getAllMenu();
	}

	
	/**
	 * 取得非Folder屬性 的所有 menu.
	 *
	 * @return the non folder menu
	 */
	public List<Menu> getNonFolderMenu(){
		return menuMapper.getNonFolderMenu();
	}

	
	/**
	 * Gets the menu by menu id.
	 *
	 * @param menuId the menu id
	 * @return the menu by menu id
	 */
	public TbAuthMenu getMenuByMenuId(Integer menuId){
		return tbAuthMenuMapper.selectByPrimaryKey(new BigDecimal(menuId));
	}

	/**


	/**
	 * 取得所有 menu.
	 *
	 * @param example the example
	 * @return the list
	 */
	public List<TbAuthMenu> selectByExample(TbAuthMenuExample example){
		return tbAuthMenuMapper.selectByExample(example);
	}
	
	/**
	 * 取得所有 menu.(分頁)
	 *
	 * @param example the example
	 * @return the list
	 */
	public List<TbAuthMenu> selectByGrid(TbAuthMenuExample example){
		PageBounds pageBounds = getPageBounds();
		List<TbAuthMenu> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.TbAuthMenuMapper.selectByExample", new TbAuthMenuExample(), pageBounds);
		PageList<TbAuthMenu> pageList = (PageList<TbAuthMenu>)list;
		return pageList;
	}
	
//	/**
//	 * Dto select by example.
//	 *
//	 * @param example the example
//	 * @return the list
//	 */
//	public List<MenuDTO> dtoSelectByExample(TbAuthMenuExample example){
//		return uTbAuthMenuMapper.selectByExample(example);
//	}
	
	/**
	 * Dto select by grid.
	 *
	 * @param tbAuthMenuExample the tb auth menu example
	 * @return the list
	 */
	public List<MenuDTO> selectDTOByGrid(TbAuthMenuExample tbAuthMenuExample){
		PageBounds pageBounds = getPageBounds();
		List<MenuDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbAuthMenuMapper.selectByExample", new TbAuthMenuExample(), pageBounds);
		PageList<MenuDTO> pageList = (PageList<MenuDTO>)list;
		return pageList;
	}
	
	/**
	 * 用id取得指定menu其餘的欄位.
	 *
	 * @param MENU_ID the menu id
	 * @return the tb auth menu
	 */
	public TbAuthMenu selectByPrimaryKey(BigDecimal MENU_ID){
		return tbAuthMenuMapper.selectByPrimaryKey(MENU_ID);
	}
	
	/**
	 * Dto select by primary key grid.
	 *
	 * @param MENU_ID the menu id
	 * @return the list
	 */
	public List<MenuDTO> selectDTOByPrimaryKeyGrid(BigDecimal MENU_ID){
		PageBounds pageBounds = getPageBounds();
		log.debug("pageBounds : "+pageBounds.toString());
		List<MenuDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbAuthMenuMapper.selectByPrimaryKey", MENU_ID, pageBounds);
		PageList<MenuDTO> pageList = (PageList<MenuDTO>)list;
		return pageList;
	}
	
	/**
	 * 修改原有的menu.
	 *
	 * @param record the record
	 * @return the int
	 */
	public int updateByPrimaryKeySelective(TbAuthMenu record){
		return tbAuthMenuMapper.updateByPrimaryKeySelective(record);
	}
	
	/**
	 * 修改原有的menu.
	 *
	 * @param record the record
	 * @return the int
	 */
	public int updateByPrimaryKey(TbAuthMenu record){
		return tbAuthMenuMapper.updateByPrimaryKey(record);
	}
	
	/**
	 * 刪除原有的menu.
	 *
	 * @param MENU_ID the menu id
	 * @return the int
	 */
	public int deleteByPrimaryKey(BigDecimal MENU_ID){
		return tbAuthMenuMapper.deleteByPrimaryKey(MENU_ID);
	}
	
	/**
	 * 新增menu.
	 *
	 * @param menu the menu
	 * @return the int
	 */
	public int insertSelective(TbAuthMenu menu){
		return tbAuthMenuMapper.insertSelective(menu);
	}
	
	/**
	 * Select all child menu tree.
	 *
	 * @param roleId the psn id
	 * @param rootMenuId the root menu id
	 * @return the list
	 */
	public List<Menu> selectAllMenuTree(){
		
		return menuMapper.selectAllMenuTree();
	};
	
	/**
	 * Select all child menu tree by user root menu.
	 *
	 * @param roleId the psn id
	 * @param rootMenuId the root menu id
	 * @return the list
	 */
	public List<Menu> selectAllMenuTreeByRole(Integer roleId){
		
		return menuMapper.selectAllMenuTreeByRole(roleId);
	};
	
	/**
	 * Select child menu tree by user root menu.
	 *
	 * @param roleId the psn id
	 * @param rootMenuId the root menu id
	 * @return the list
	 */
	public List<Menu> selectMenuTreeByRole(Integer roleId){
		
		return menuMapper.selectMenuTreeByRole(roleId);
	};
	
	/**
	 * 查詢所有是資料夾的menu
	 * @return
	 */
	public List<MenuDTO> selectIsFolderMenu(){
		return uTbAuthMenuMapper.selectIsFolderMenu();
	}
}
