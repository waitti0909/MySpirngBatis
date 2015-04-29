package com.foya.noms.service.auth;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.TbAuthMenu;
import com.foya.dao.mybatis.model.TbAuthMenuExample;
import com.foya.dao.mybatis.model.TbAuthMenuFun;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.dao.mybatis.model.TbSysLookupExample;
import com.foya.noms.GenericTest;
import com.foya.noms.dto.auth.MenuDTO;
import com.foya.noms.dto.auth.MenuFunDTO;
import com.foya.noms.util.DateUtils;

public class MenuServiceTest extends GenericTest {

	@Autowired
	private MenuService service;
	
	@Test
	public void testCreateMenu(){
		TbAuthMenu menu = new TbAuthMenu();
		menu.setMENU_NAME("menuName");
		menu.setMENU_DESC("menuDesc");
		menu.setMENU_SORT(1);
		menu.setIS_FODR("0");
		menu.setPGM_PATH("/path");
		menu.setCR_TIME(new Date());
		menu.setCR_USER("junit");
		String[] array ={"btn_reset,btn_delete"};
		service.createMenu(menu, array, "junit");
		Assert.assertTrue(menu.getMENU_ID().intValue()>0);
	}
	
	@Test 
	public void testSelectAllLookupButtons(){
		List<TbSysLookup> buttons =service.selectAllLookupButtons(new TbSysLookupExample());
		Assert.assertNotNull(buttons);
		Assert.assertTrue(buttons.size()>0);
	}
	
	@Test
	public void testSelectByPrimaryKey(){
		TbAuthMenu menu = service.selectByPrimaryKey(BigDecimal.valueOf(386));
		Assert.assertNotNull(menu);
		Assert.assertEquals(menu.getMENU_ID(), BigDecimal.valueOf(386));
	}
	
	@Test
	public void testSelectDTOByPrimaryKeyGrid(){
		List<MenuDTO> menu = service.selectDTOByPrimaryKeyGrid(BigDecimal.valueOf(386));
		Assert.assertNotNull(menu);
		Assert.assertTrue(menu.size()==1);
		Assert.assertEquals(menu.get(0).getMENU_ID(), BigDecimal.valueOf(386));
	}
	@Test
	public void testUpdateByPrimaryKeySelective(){
		TbAuthMenu menu = service.selectByPrimaryKey(BigDecimal.valueOf(440));
		Date date = DateUtils.time(2014, 9, 15, 10, 50, 20);
		String name = menu.getMENU_NAME();
		String desc = menu.getMENU_DESC();
		Integer sort = menu.getMENU_SORT();
		String isFodr = menu.getIS_FODR();
		String path = menu.getPGM_PATH();
		String mdUser = menu.getMD_USER();
		Date mdTime = menu.getMD_TIME();
		menu.setMENU_NAME("junitTest");
		menu.setMENU_DESC("junitTest");
		menu.setMENU_SORT(2);
		menu.setIS_FODR("0");
		menu.setPGM_PATH("/junitTest");
		menu.setMD_USER("junitTest");
		menu.setMD_TIME(date);
		int item = service.updateByPrimaryKeySelective(menu);
		menu = service.selectByPrimaryKey(BigDecimal.valueOf(440));
		Assert.assertNotNull(menu);
		Assert.assertEquals(item, 1);
		Assert.assertEquals(menu.getMENU_NAME(), "junitTest");
		Assert.assertEquals(menu.getMENU_DESC(), "junitTest");
		Assert.assertEquals(menu.getMENU_SORT(), new Integer(2));
		Assert.assertEquals(menu.getIS_FODR(), "0");
		Assert.assertEquals(menu.getPGM_PATH(), "/junitTest");
		Assert.assertEquals(menu.getMD_USER(), "junitTest");
		Assert.assertEquals(menu.getMD_TIME(), date);
		menu.setMENU_NAME(name);
		menu.setMENU_DESC(desc);
		menu.setMENU_SORT(sort);
		menu.setIS_FODR(isFodr);
		menu.setPGM_PATH(path);
		menu.setMD_USER(mdUser);
		menu.setMD_TIME(mdTime);
		item = service.updateByPrimaryKeySelective(menu);
		menu = service.selectByPrimaryKey(BigDecimal.valueOf(440));
		Assert.assertNotNull(menu);
		Assert.assertEquals(item, 1);
		Assert.assertEquals(menu.getMENU_NAME(), name);
		Assert.assertEquals(menu.getMENU_DESC(), desc);
		Assert.assertEquals(menu.getMENU_SORT(), sort);
		Assert.assertEquals(menu.getIS_FODR(), isFodr);
		Assert.assertEquals(menu.getPGM_PATH(), path);
		Assert.assertEquals(menu.getMD_USER(), mdUser);
		Assert.assertEquals(menu.getMD_TIME(), mdTime);
	}
	
	@Test
	public void testUpdateMenu(){	
		TbAuthMenu menu = service.selectByPrimaryKey(BigDecimal.valueOf(440));
		String name = menu.getMENU_NAME();
		String desc = menu.getMENU_DESC();
		Integer sort = menu.getMENU_SORT();
		String isFodr = menu.getIS_FODR();
		String path = menu.getPGM_PATH();
		String crID = menu.getCR_USER();
		menu.setMENU_NAME("junitTest");
		menu.setMENU_DESC("junitTest");
		menu.setMENU_SORT(2);
		menu.setIS_FODR("0");
		menu.setPGM_PATH("/junitTest");
		menu.setCR_USER("2");
		String[] lookupButtons = {"btn_edit","btn_member"};
		boolean result = service.updateMenu(menu, lookupButtons,"junit");
		List<MenuFunDTO> menuFuns = service.selectMenuFunByMenuID(BigDecimal.valueOf(440));
		menu = service.selectByPrimaryKey(BigDecimal.valueOf(440));
		Assert.assertNotNull(menu);
		Assert.assertTrue(result);
		Assert.assertEquals(menu.getMENU_NAME(), "junitTest");
		Assert.assertEquals(menu.getMENU_DESC(), "junitTest");
		Assert.assertEquals(menu.getMENU_SORT(), new Integer(2));
		Assert.assertEquals(menu.getIS_FODR(), "0");
		Assert.assertEquals(menu.getPGM_PATH(), "/junitTest");
		Assert.assertEquals(menu.getCR_USER(), "2");
		Assert.assertTrue(menuFuns.size()==2 );
		menu.setMENU_NAME(name);
		menu.setMENU_DESC(desc);
		menu.setMENU_SORT(sort);
		menu.setIS_FODR(isFodr);
		menu.setPGM_PATH(path);
		menu.setCR_USER(crID);
		String[] buttons = {};
		result = service.updateMenu(menu, buttons,"junit");
		menu = service.selectByPrimaryKey(BigDecimal.valueOf(440));
		menuFuns = service.selectMenuFunByMenuID(BigDecimal.valueOf(440));
		Assert.assertNotNull(menu);
		Assert.assertEquals(menu.getMENU_NAME(), name);
		Assert.assertEquals(menu.getMENU_DESC(), desc);
		Assert.assertEquals(menu.getMENU_SORT(), sort);
		Assert.assertEquals(menu.getIS_FODR(), isFodr);
		Assert.assertEquals(menu.getPGM_PATH(), path);
		Assert.assertEquals(menu.getCR_USER(), crID);
		Assert.assertTrue(menuFuns.size()==0 );
		
	}
	@Test
	public void testDeleteByPrimaryKey(){
		TbAuthMenu menu = new TbAuthMenu();
		menu.setMENU_NAME("menuName");
		menu.setMENU_DESC("menuDesc");
		menu.setMENU_SORT(1);
		menu.setIS_FODR("0");
		menu.setPGM_PATH("/path");
		menu.setCR_TIME(new Date());
		menu.setCR_USER("junit");
		String[] array ={"btn_delete","btn_add"};
		service.createMenu(menu, array, "junitTest");
		log.debug("menuID : "+menu.getMENU_ID());
		int item = service.deleteByPrimaryKey(menu.getMENU_ID());
		Assert.assertEquals(item, 1);
	}
	@Test
	public void testDeleteMenu(){
//		TbAuthMenu menu = new TbAuthMenu();
//		menu.setMENU_NAME("menuName");
//		menu.setMENU_DESC("menuDesc");
//		menu.setMENU_SORT(1);
//		menu.setIS_FODR("0");
//		menu.setPGM_PATH("/path");
//		String[] array ={"btn_reset","btn_delete"};
//		service.createMenu(menu, array, "1");
//		TbAuthMenu menu2 = new TbAuthMenu();
//		menu2.setMENU_NAME("menuName2");
//		menu2.setMENU_DESC("menuDesc2");
//		menu2.setMENU_SORT(2);
//		menu2.setIS_FODR("0");
//		menu2.setPGM_PATH("/path2");
//		String[] array2 ={"btn_search"};
//		service.createMenu(menu2, array2, "2");
		//用createMenu會因為呼叫有分頁的方法而發生junit無法順利執行完畢的狀況
		TbAuthMenu menu = service.selectByPrimaryKey(BigDecimal.valueOf(386));
		String[] menuIDs = {String.valueOf(menu.getMENU_ID())};
		try {
			boolean result = service.deleteMenu(menuIDs);
			Assert.assertTrue(result);
		} catch (Exception e) {
			log.error("ExceptionUtils : "+ExceptionUtils.getFullStackTrace(e));
		}
	}
	
	@Test
	public void testSelectByExample(){
		List<TbAuthMenu> menus = service.selectByExample(new TbAuthMenuExample());
		Assert.assertNotNull(menus);
		Assert.assertTrue(menus.size()>0);
	}
	
	@Test
	public void testInsertLookupButton(){
		String[] lookupButtons = {"btn_member","btn_reset"};
		TbAuthMenuFun menuFun = new TbAuthMenuFun();
		int item =0;
		for(int i =0;i<=lookupButtons.length-1;i++){
			log.debug(i);
			menuFun.setMENU_ID(BigDecimal.valueOf(442));
			menuFun.setFUN_CODE(lookupButtons[i]);
			menuFun.setDISPLAY_ORDER(BigDecimal.valueOf(i+1));
			item += service.insertLookupButton(menuFun);
		}
		List<MenuFunDTO> menuFuns = service.selectMenuFunByMenuID(new BigDecimal(442));
		Assert.assertNotNull(menuFuns);
		Assert.assertEquals(item, 2);
		Assert.assertTrue(menuFuns.size()==2);
	}
	
	@Test
	public void testSelectMenuFunByMenuID(){
		List<MenuFunDTO> menuFuns = service.selectMenuFunByMenuID(new BigDecimal(5));
		Assert.assertNotNull(menuFuns);
		Assert.assertTrue(menuFuns.size()>0);
	}
	
	@Test
	public void testSelectIsFolderMenu(){
		List<MenuDTO> menus = service.selectIsFolderMenu();
		Assert.assertNotNull(menus);
		Assert.assertTrue(menus.size()>0);
	}
	
}
