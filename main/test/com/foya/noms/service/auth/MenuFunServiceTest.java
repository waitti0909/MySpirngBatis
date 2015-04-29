package com.foya.noms.service.auth;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.noms.GenericTest;
import com.foya.noms.dto.auth.MenuFunDTO;

public class MenuFunServiceTest extends GenericTest {

	@Autowired
	private MenuFunService service;
	
	@Test
	public void testSelectByMenuID(){
		List<MenuFunDTO> menuFuns = service.selectByMenuID(BigDecimal.valueOf(441));
		Assert.assertNotNull(menuFuns);
		Assert.assertTrue(menuFuns.size()>0);
		
	}
}
