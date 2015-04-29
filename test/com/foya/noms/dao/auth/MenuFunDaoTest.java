package com.foya.noms.dao.auth;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.TbAuthMenuFun;
import com.foya.dao.mybatis.model.TbAuthMenuFunExample;
import com.foya.noms.GenericTest;
import com.foya.noms.dto.auth.MenuFunDTO;
import com.foya.noms.util.DateUtils;

public class MenuFunDaoTest extends GenericTest {

	@Autowired
	private MenuFunDao dao;
	
	
	@Test
	public void testInsertSelective(){
		TbAuthMenuFun menuFun = new TbAuthMenuFun();
		menuFun.setMENU_ID(BigDecimal.valueOf(442));
		menuFun.setFUN_CODE("btn_search");
		menuFun.setDISPLAY_ORDER(BigDecimal.valueOf(1));
		int item =dao.insertSelective(menuFun);
		Assert.assertEquals(item, 1);
	}
	
	@Test
	public void testSelectByMenuID(){
		List<MenuFunDTO> menuFuns=dao.selectByMenuID(BigDecimal.valueOf(440));
		Assert.assertNotNull(menuFuns);
		Assert.assertEquals(menuFuns.get(0).getMENU_ID(), new BigDecimal(440));
	}
	
	@Test
	public void testDeleteByMenuID(){
		TbAuthMenuFun menuFun = new TbAuthMenuFun();
		menuFun.setMENU_ID(BigDecimal.valueOf(442));
		menuFun.setFUN_CODE("btn_search");
		menuFun.setDISPLAY_ORDER(BigDecimal.valueOf(1));
		dao.insertSelective(menuFun);
		int item=dao.deleteByMenuID(menuFun.getMENU_ID());
		Assert.assertEquals(item,1);
	}
	
	@Test
	public void testSelectByExample(){
		TbAuthMenuFunExample tbAuthMenuFunExample = new TbAuthMenuFunExample();
		List<TbAuthMenuFun> menuFuns = dao.selectByExample(tbAuthMenuFunExample);
		Assert.assertNotNull(menuFuns);
		Assert.assertTrue(menuFuns.size()>0);
	}
	
	@Test
	public void testFindFunctionsByMenuId() {
		List<MenuFunDTO> menuFuns = dao.findFunctionsByMenuId(BigDecimal.valueOf(441));
		Assert.assertNotNull(menuFuns);
		Assert.assertTrue(menuFuns.size()>0);
	}
	
	@Test
	public void testDeleteByMenuIDAndFunCode(){
		TbAuthMenuFun menuFun = new TbAuthMenuFun();
		menuFun.setMENU_ID(BigDecimal.valueOf(442));
		menuFun.setFUN_CODE("btn_search");
		menuFun.setDISPLAY_ORDER(BigDecimal.valueOf(1));
		dao.insertSelective(menuFun);
		Map<String ,Object> menuFunDelete = new HashMap<String ,Object>();
		menuFunDelete.put("MENU_ID",menuFun.getMENU_ID());
		menuFunDelete.put("FUN_CODE",menuFun.getFUN_CODE());
		int item = dao.deleteByMenuIDAndFunCode(menuFunDelete);
		Assert.assertEquals(item, 1);
	}
	
	@Test
	public void testUpdateMenuFun(){
		List<MenuFunDTO> menuFuns =dao.selectByMenuID(BigDecimal.valueOf(441));
		MenuFunDTO menuFun = menuFuns.get(0);
		BigDecimal order =menuFun.getDISPLAY_ORDER();
		String code = menuFun.getFUN_CODE();
		Date mdTime = menuFun.getMD_TIME();
		String mdUser = menuFun.getMD_USER();
		Date date = DateUtils.time(2014, 9, 15, 9,25, 30);
		menuFun.setDISPLAY_ORDER(BigDecimal.valueOf(6));
		menuFun.setFUN_CODE("btn_search");
		menuFun.setMD_TIME(date);
		menuFun.setMD_USER("junit");
		int item = dao.updateMenuFun(menuFun);
		menuFuns =dao.selectByMenuID(BigDecimal.valueOf(441));
		menuFun = menuFuns.get(0);
		Assert.assertEquals(item, 1);
		Assert.assertEquals(menuFun.getDISPLAY_ORDER(), BigDecimal.valueOf(6));
		Assert.assertEquals(menuFun.getFUN_CODE(), "btn_search");
		Assert.assertEquals(menuFun.getMD_TIME(), date);
		Assert.assertEquals(menuFun.getMD_USER(), "junit");
		menuFun.setDISPLAY_ORDER(order);
		menuFun.setFUN_CODE(code);
		menuFun.setMD_TIME(mdTime);
		menuFun.setMD_USER(mdUser);
		item = dao.updateMenuFun(menuFun);
		menuFuns =dao.selectByMenuID(BigDecimal.valueOf(441));
		menuFun = menuFuns.get(0);
		Assert.assertEquals(item, 1);
		Assert.assertEquals(menuFun.getDISPLAY_ORDER(), order);
		Assert.assertEquals(menuFun.getFUN_CODE(), code);
		Assert.assertEquals(menuFun.getMD_TIME(), mdTime);
		Assert.assertEquals(menuFun.getMD_USER(), mdUser);
	}
}
