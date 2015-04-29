package com.foya.noms.security.service;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.noms.GenericTest;
import com.foya.noms.dto.JsTreeDTO;
import com.foya.noms.security.model.Menu;
import com.foya.noms.security.model.Tree;

public class MenuServiceTest extends GenericTest {

	@Autowired
	private MenuTreeService service;
	
	@Before
	public void printStartLog() {
		log.info("==== Test Start ====");
	}
	
	@After
	public void printAfterLog() {
		log.info("==== Test After ====");
	}
	
	@Test
	public void testGetTrees() {
		 Map<String, Tree> menuTree = service.getTrees("admin");
		 Assert.assertNotNull(menuTree);
		 Assert.assertTrue(menuTree.size()>0);
	}
	
	@Test
	public void testGetMenuByPsnId() {
		List<Menu> menus =service.getMenuByPsnId(3);
		Assert.assertNotNull(menus);
		Assert.assertTrue(menus.size()>0);
	}
	
	@Test
	public void testGetMenuByRoleId() {
		List<Menu> menus = service.getMenuByRoleId(15,36);
		Assert.assertNotNull(menus);
		Assert.assertTrue(menus.size()>0);
	}
	
	@Test
	public void testGetChildMenuByParentIdRoldId(){
		List<Menu> menus = service.getChildMenuByParentIdRoldId(386, 3);
		Assert.assertNotNull(menus);
		Assert.assertTrue(menus.size()>0);
	}
	
	@Test
	public void testGetAllMenu(){
		List<Menu> menus = service.getAllMenu();
		Assert.assertNotNull(menus);
		Assert.assertTrue(menus.size()>0);
	}
	
	@Test
	public void testGetNonFolderMenu(){
		List<Menu> menus = service.getNonFolderMenu();
		Assert.assertNotNull(menus);
		Assert.assertTrue(menus.size()>0);
	}	
	
	@Test
	public void testGetAllMenuTree() {
		List<JsTreeDTO> tree = service.getAllMenuTree();
		Assert.assertNotNull(tree);
		Assert.assertTrue(tree.size()>0);
	}
	
	@Test
	public void testGetAllMenuTreeByRole() {
		List<JsTreeDTO> tree = service.getAllMenuTreeByRole(1);
		Assert.assertNotNull(tree);
		Assert.assertTrue(tree.size()>0);
	}
	
	@Test 
	public void testGetMenuTreeByRole() {
		List<JsTreeDTO> tree = service.getMenuTreeByRole(1,1);
		Assert.assertNotNull(tree);
		Assert.assertTrue(tree.size()>0);
	}
	
}
