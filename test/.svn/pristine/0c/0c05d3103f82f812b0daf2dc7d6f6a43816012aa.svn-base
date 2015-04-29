package com.foya.noms.dao.common;

import java.util.List;




import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.TbComCity;
import com.foya.dao.mybatis.model.TbComCityExample;
import com.foya.noms.GenericTest;

public class CityDaoTest extends GenericTest {

	@Autowired
	private CityDao dao;

	@Test
	public void testFindCityByCondition() {
		TbComCityExample example = new TbComCityExample();
		List<TbComCity> cityList = dao.findCityByCondition(example);
		Assert.assertNotNull(cityList);
		Assert.assertTrue(cityList.size()>0);
	}

}
