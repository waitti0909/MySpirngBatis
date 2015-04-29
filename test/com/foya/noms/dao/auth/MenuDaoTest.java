package com.foya.noms.dao.auth;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.TbAuthMenu;
import com.foya.dao.mybatis.model.TbAuthMenuExample;
import com.foya.noms.GenericTest;
import com.foya.noms.dto.auth.MenuDTO;
import com.foya.noms.security.model.Menu;
import com.foya.noms.util.DateUtils;

public class MenuDaoTest extends GenericTest {

	@Autowired
	private MenuDao dao;
	


	@Test
	public void GetRootMenuByPsnId() {
		List<Menu> menus = dao.getRootMenuByPsnId(3);
		Assert.assertNotNull(menus);
		Assert.assertTrue(menus.size()>0);
	}

	@Test
	public void testGetRootMenuByRoleId() {
		List<Menu> menus = dao.getRootMenuByRoleId(116);
		Assert.assertNotNull(menus);
		Assert.assertTrue(menus.size()>0);
	}

	@Test
	public void testGetChildMenuByParentIdRoldId() {
		List<Menu> menus = dao.getChildMenuByParentIdRoldId(3,386);
		Assert.assertNotNull(menus);
		Assert.assertTrue(menus.size()>0);
	}

	@Test
	public void testGetAllMenu() {
		List<Menu> menus = dao.getAllMenu();
		Assert.assertNotNull(menus);
		Assert.assertTrue(menus.size()>0);
	}

	@Test
	public void testGetNonFolderMenu() {
		List<Menu> menus =dao.getNonFolderMenu();
		Assert.assertNotNull(menus);
		Assert.assertTrue(menus.size()>0);
	}
	
	@Test	
	public void testGetMenuByMenuId(){
		TbAuthMenu menu =dao.getMenuByMenuId(440);
		Assert.assertNotNull(menu);
		Assert.assertEquals(menu.getMENU_ID(), BigDecimal.valueOf(440));
	}


	@Test	
	public void testSelectByExample(){
		List<TbAuthMenu> menus = dao.selectByExample(new TbAuthMenuExample());
		Assert.assertNotNull(menus);
		Assert.assertTrue(menus.size()>0);
	}
	
	@Test	
	public void testSelectByGrid(){
		List<TbAuthMenu> menus =dao.selectByGrid(new TbAuthMenuExample());
		Assert.assertNotNull(menus);
		Assert.assertTrue(menus.size()>0);
	}

	
	@Test	
	public void testSelectDTOByGrid(){
		List<MenuDTO> menus =dao.selectDTOByGrid(new TbAuthMenuExample());
		Assert.assertNotNull(menus);
		Assert.assertTrue(menus.size()>0);
	}
	@Test	
	public void testSelectByPrimaryKey(){
		
		TbAuthMenu menu =dao.selectByPrimaryKey(BigDecimal.valueOf(6));
		Assert.assertNotNull(menu);
		Assert.assertEquals(menu.getMENU_ID(), BigDecimal.valueOf(6));
	}
	@Test	
	public void testSelectDTOByPrimaryKeyGrid(){
		List<MenuDTO> menus =dao.selectDTOByPrimaryKeyGrid(BigDecimal.valueOf(1));
		Assert.assertNotNull(menus);
		Assert.assertEquals(menus.size(), 1);
		Assert.assertEquals(menus.get(0).getMENU_ID(),BigDecimal.valueOf(1));
	}
	
	@Test	
	public void testUpdateByPrimaryKeySelective(){
		TbAuthMenu menu = dao.selectByPrimaryKey(BigDecimal.valueOf(440));
		Date date = DateUtils.time(2014, 9, 15, 9,25, 30);
		String name = menu.getMENU_NAME();
		String desc = menu.getMENU_DESC();
		Integer sort = menu.getMENU_SORT();
		String isFodr = menu.getIS_FODR();
		String path = menu.getPGM_PATH();
		Date mdTime = menu.getMD_TIME();
		String mdUser = menu.getCR_USER();
		menu.setMENU_NAME("junitTest");
		menu.setMENU_DESC("junitTest");
		menu.setMENU_SORT(2);
		menu.setIS_FODR("0");
		menu.setPGM_PATH("/junitTest");
		menu.setMD_USER("junit");
		menu.setMD_TIME(date);
		int item = dao.updateByPrimaryKeySelective(menu);
		menu = dao.selectByPrimaryKey(BigDecimal.valueOf(440));
		Assert.assertNotNull(menu);
		Assert.assertEquals(item, 1);
		Assert.assertEquals(menu.getMENU_NAME(), "junitTest");
		Assert.assertEquals(menu.getMENU_DESC(), "junitTest");
		Assert.assertEquals(menu.getMENU_SORT(), new Integer(2));
		Assert.assertEquals(menu.getIS_FODR(), "0");
		Assert.assertEquals(menu.getPGM_PATH(), "/junitTest");
		Assert.assertEquals(menu.getMD_USER(), "junit");
		Assert.assertEquals(menu.getMD_TIME(), date);
		menu.setMENU_NAME(name);
		menu.setMENU_DESC(desc);
		menu.setMENU_SORT(sort);
		menu.setIS_FODR(isFodr);
		menu.setPGM_PATH(path);
		menu.setMD_TIME(mdTime);
		menu.setMD_USER(mdUser);
		item = dao.updateByPrimaryKeySelective(menu);
		menu = dao.selectByPrimaryKey(BigDecimal.valueOf(440));
		Assert.assertNotNull(menu);
		Assert.assertEquals(item, 1);
		Assert.assertEquals(menu.getMENU_NAME(), name);
		Assert.assertEquals(menu.getMENU_DESC(), desc);
		Assert.assertEquals(menu.getMENU_SORT(), sort);
		Assert.assertEquals(menu.getIS_FODR(), isFodr);
		Assert.assertEquals(menu.getPGM_PATH(), path);
		Assert.assertEquals(menu.getMD_TIME(), mdTime);
		Assert.assertEquals(menu.getMD_USER(), mdUser);
	}
	
	@Test	
	public void testDeleteByPrimaryKey(){
		TbAuthMenu menu = new TbAuthMenu();
		menu.setMENU_NAME("menuName");
		menu.setMENU_DESC("menuDesc");
		menu.setMENU_SORT(1);
		menu.setIS_FODR("0");
		menu.setPGM_PATH("/path");
		menu.setCR_USER("junit");
		menu.setCR_TIME(new Date(System.currentTimeMillis()));
		dao.insertSelective(menu);
		int item = dao.deleteByPrimaryKey(menu.getMENU_ID());
		Assert.assertEquals(item,1);
	}
	
	@Test	
	public void testInsertSelective(){
		TbAuthMenu menu = new TbAuthMenu();
		menu.setMENU_NAME("menuName");
		menu.setMENU_DESC("menuDesc");
		menu.setMENU_SORT(1);
		menu.setIS_FODR("0");
		menu.setPGM_PATH("/path");
		menu.setCR_USER("junit");
		menu.setCR_TIME(new Date(System.currentTimeMillis()));
		int item = dao.insertSelective(menu);
		Assert.assertEquals(item,1);
		Assert.assertTrue(menu.getMENU_ID().intValue()>0);
	}
	
	@Test
	public void testSelectAllMenuTreeByRole(){
		List<Menu> menus =dao.selectAllMenuTreeByRole(3);
		Assert.assertNotNull(menus);
		Assert.assertTrue(menus.size()> 0);
	}
	
	@Test
	public void testSelectAllMenuTree(){
		List<Menu> menus =dao.selectAllMenuTree();
		Assert.assertNotNull(menus);
		Assert.assertTrue(menus.size()> 0);
	};
	
	@Test
	public  void testSelectMenuTreeByRole(){
		List<Menu> menus =dao.selectMenuTreeByRole(116);
		Assert.assertNotNull(menus);
		Assert.assertTrue(menus.size()> 0);
	};
	
	@Test
	public void testSelectIsFolderMenu(){
		List<MenuDTO> menus =dao.selectIsFolderMenu();
		Assert.assertNotNull(menus);
		Assert.assertTrue(menus.size()> 0);
	}
}
