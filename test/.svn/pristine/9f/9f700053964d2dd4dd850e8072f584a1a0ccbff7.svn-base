package com.foya.noms.dao.common;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.TbComTown;
import com.foya.dao.mybatis.model.TbComTownExample;
import com.foya.noms.GenericTest;

public class TownDaoTest extends GenericTest {

	@Autowired
	private TownDao dao;

	@Test
	public void testFindTownByCondition() {
		TbComTownExample example = new TbComTownExample();
		List<TbComTown> cityList = dao.findTownByCondition(example);
		Assert.assertNotNull(cityList);
		Assert.assertTrue(cityList.size()>0);
	}

}
