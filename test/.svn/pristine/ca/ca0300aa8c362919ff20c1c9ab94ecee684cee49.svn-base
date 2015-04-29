package com.foya.noms.dao.common;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.TbComRoad;
import com.foya.dao.mybatis.model.TbComRoadExample;
import com.foya.noms.GenericTest;

public class RoadDaoTest extends GenericTest {

	@Autowired
	private RoadDao dao;

	@Test
	public void testFindByCondition() {
		List<TbComRoad> roadList = dao.findByCondition(new TbComRoadExample());
		Assert.assertNotNull(roadList);
		Assert.assertTrue(roadList.size() > 0);
	}

	@Test
	public void testInsert() {
		TbComRoad road = new TbComRoad();
		road.setAREA("大肚區_junitTest");
		road.setCITY("台中市_junitTest");
		road.setROAD("沙田路_junitTest");
		road.setZIP("321");
		int item = dao.insert(road);
		Assert.assertEquals(item, 1);
	}

	@Test
	public void testUpdate() {
		TbComRoadExample example = new TbComRoadExample();
		example.createCriteria().andCITYEqualTo("台中市_junit")
				.andAREAEqualTo("大肚區_junit").andROADEqualTo("沙田路_junit");
		List<TbComRoad> roadList = dao.findByCondition(example);
		TbComRoad road = roadList.get(0);
		String area = road.getAREA();
		String city = road.getCITY();
		String getRoad = road.getROAD();
		String zip = road.getZIP();
		road.setAREA("大肚區_junitTest");
		road.setCITY("台中市_junitTest");
		road.setROAD("沙田路_junitTest");
		road.setZIP("321");
		int item = dao.update(road, example);
		example = new TbComRoadExample();
		example.createCriteria().andCITYEqualTo("台中市_junitTest")
				.andAREAEqualTo("大肚區_junitTest")
				.andROADEqualTo("沙田路_junitTest");
		roadList = dao.findByCondition(example);
		Assert.assertEquals(item, 1);
		Assert.assertEquals(road.getAREA(), "大肚區_junitTest");
		Assert.assertEquals(road.getCITY(), "台中市_junitTest");
		Assert.assertEquals(road.getROAD(), "沙田路_junitTest");
		Assert.assertEquals(road.getZIP(), "321");
		road.setAREA(area);
		road.setCITY(city);
		road.setROAD(getRoad);
		road.setZIP(zip);
		item = dao.update(road, example);
		example = new TbComRoadExample();
		example.createCriteria().andCITYEqualTo(city).andAREAEqualTo(area)
				.andROADEqualTo(getRoad);
		roadList = dao.findByCondition(example);
		Assert.assertEquals(item, 1);
		Assert.assertEquals(road.getAREA(), area);
		Assert.assertEquals(road.getCITY(), city);
		Assert.assertEquals(road.getROAD(), getRoad);
		Assert.assertEquals(road.getZIP(), zip);
	}

	@Test
	public void testDelete() {
		TbComRoadExample example = new TbComRoadExample();
		example.createCriteria().andCITYEqualTo("台中市_junitTest")
				.andAREAEqualTo("大肚區_junitTest")
				.andROADEqualTo("沙田路_junitTest");
		TbComRoad road = new TbComRoad();
		road.setAREA("大肚區_junitTest");
		road.setCITY("台中市_junitTest");
		road.setROAD("沙田路_junitTest");
		road.setZIP("321");
		int item = dao.insert(road);
		Assert.assertEquals(item, 1);
		item = dao.delete(example);
		Assert.assertEquals(item, 1);
	}
}
