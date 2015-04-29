package com.foya.noms.dao.system;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.dao.mybatis.model.TbSysLookupExample;
import com.foya.noms.GenericTest;

public class LookupDaoTest extends GenericTest {

	@Autowired
	private LookupDao dao;
	
	@Test
	public void testSelectBulletinType(){		
		List<TbSysLookup> buttons = dao.selectBulletinType();
		Assert.assertNotNull(buttons);
		Assert.assertTrue(buttons.size()>0);
	}
	
	@Test
	public void testSelectByExample(){
		List<TbSysLookup> buttons = dao.selectByExample(new TbSysLookupExample());
		Assert.assertNotNull(buttons);
		Assert.assertTrue(buttons.size()>0);
	}
}
