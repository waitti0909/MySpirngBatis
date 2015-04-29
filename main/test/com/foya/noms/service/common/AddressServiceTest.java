package com.foya.noms.service.common;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.mapper.TbComCityMapper;
import com.foya.dao.mybatis.mapper.TbComTownMapper;
import com.foya.dao.mybatis.model.TbComCity;
import com.foya.dao.mybatis.model.TbComCityExample;
import com.foya.dao.mybatis.model.TbComRoad;
import com.foya.dao.mybatis.model.TbComTown;
import com.foya.dao.mybatis.model.TbComTownExample;
import com.foya.noms.GenericTest;
import com.foya.noms.dto.common.AddressDTO;

public class AddressServiceTest extends GenericTest {

	@Autowired
	private AddressService service;
	
	@Autowired
	private TbComCityMapper tbComCityMapper;
	
	@Autowired
	private TbComTownMapper tbComTownMapper;
	
	@Test
	public void testGetCityKeyValueMap() {
		Map<String, String> address = service.getCityKeyValueMap();
		Assert.assertNotNull(address);
		Assert.assertTrue(address.size()>0);
	}
	
	@Test
	public void testGetTownKeyValueMap() {
		List<TbComTown> address = service.getTownByCondition("50000001");
		Assert.assertNotNull(address);
		Assert.assertTrue(address.size()>0);
		
	}
	
	@Test
	public void testGetCityByCondition() {
		TbComCityExample example = new TbComCityExample();
		example.setOrderByClause("CITY_ID");
		example.createCriteria().andCITY_IDEqualTo("50000001");
		List<TbComCity> areaList =tbComCityMapper.selectByExample(example);
		Assert.assertNotNull(areaList);
		Assert.assertTrue(areaList.size()>0);
		Assert.assertEquals(areaList.get(0).getCITY_NAME(), "台北市");
	}
	
	@Test
	public void testGetTownByCondition() {
		TbComTownExample example = new TbComTownExample();
		example.setOrderByClause("CITY_ID");
		example.createCriteria().andCITY_IDEqualTo("50000001");
		List<TbComTown> areaList =tbComTownMapper.selectByExample(example);
		Assert.assertNotNull(areaList);
		Assert.assertTrue(areaList.size()>0);
	}
	
	@Test
	public void testGetRoadKeyValueMap() {
		List<TbComRoad> rows = service.getRoadByCondition("50000001", "50000006");
		Assert.assertNotNull(rows);
		Assert.assertTrue(rows.size()>0);
	}
	
	@Test
	public void testGetZipFromTown() {
		TbComTownExample example = new TbComTownExample();
		example.createCriteria().andCITY_IDEqualTo("50000001").andTOWN_IDEqualTo("50000006");		
		List<TbComTown> areaList = tbComTownMapper.selectByExample(example);
		Assert.assertNotNull(areaList);
		Assert.assertTrue(areaList.size()>0);
		Assert.assertEquals(areaList.get(0).getZIP_CODE(), "108");
	}
	
	@Test
	public void testGetRoadByCondition() {
		List<TbComRoad> roadList = service.getRoadByCondition("50000001", "50000006");
		Assert.assertNotNull(roadList);
		Assert.assertTrue(roadList.size()>0);
	}
	
	@Test
	public void testCombineAddress() {
		AddressDTO dto = new AddressDTO();
		dto.setZip("437");
		dto.setCityName("台中市");
		dto.setAreaName("大甲區");
		dto.setRoad("大甲南北三路");
		dto.setAdjacent("32");
		dto.setLane("1");
		dto.setAlley("56");
		dto.setSubAlley("27");
		dto.setDoorNo("247");
		dto.setDoorNoEx("1");
		String address = service.combineAddress(dto);
		Assert.assertNotNull(address);
		Assert.assertTrue(address.length()>0);
		Assert.assertEquals(address, "４３７ 台中市大甲區大甲南北三路 ３２鄰 １巷 ５６弄 ２７衖  ２４７號之１ ");
	}
}
