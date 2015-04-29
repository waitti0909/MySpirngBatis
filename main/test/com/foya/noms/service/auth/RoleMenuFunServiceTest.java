package com.foya.noms.service.auth;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.noms.GenericTest;
import com.foya.noms.dto.auth.RoleMenuFunDTO;
import com.foya.noms.dto.auth.RoleMenuFunDepDTO;

public class RoleMenuFunServiceTest extends GenericTest {

	@Autowired
	private RoleMenuFunService service;
	
	@Test
	public void testGetFuncitonByPsnAndMenuId(){
		List<RoleMenuFunDTO> roleMenuFuns = service.getFuncitonByPsnAndMenuId(3,441);
		Assert.assertNotNull(roleMenuFuns);
		Assert.assertTrue(roleMenuFuns.size()>0);
	}
		
	@Test
	public void testSelectFuncDepDTOByRoleAndMenuForGrid(){
		List<RoleMenuFunDepDTO> roleMenuFuns = service.selectFuncDepDTOByRoleAndMenuForGrid(116, 441);
		Assert.assertNotNull(roleMenuFuns);
		Assert.assertTrue(roleMenuFuns.size()>0);
		Assert.assertEquals(roleMenuFuns.get(0).getMenuId(),new Integer(441));
	}
	
	@Test
	public void testGetFuncDepByRoleAndMenu() {
		 List<RoleMenuFunDepDTO> roleMenuFuns = service.getFuncDepByRoleAndMenu(116,441);
		 Assert.assertNotNull(roleMenuFuns);
		 Assert.assertTrue(roleMenuFuns.size()>0);
		 Assert.assertEquals(roleMenuFuns.get(0).getMenuId(),new Integer(441));
	}

	@Test 
	public void testSaveRoleMenuFun() {
		BigDecimal[] menuFunIds = new BigDecimal[5];
		menuFunIds[0] =new BigDecimal(696);
		menuFunIds[2] =new BigDecimal(698);
		menuFunIds[3] =new BigDecimal(699);
		menuFunIds[4] =new BigDecimal(700);
		String[] deptIds = new String[2];
		deptIds[0] = "110000";
		deptIds[1] = "210200";
		service.saveRoleMenuFun(BigDecimal.valueOf(116),BigDecimal.valueOf(441),menuFunIds,deptIds,"admin");
		List<RoleMenuFunDepDTO> roleMenuFuns = service.getFuncDepByRoleAndMenu(116,441);
		Assert.assertTrue(roleMenuFuns.size()==4);
	}
}
