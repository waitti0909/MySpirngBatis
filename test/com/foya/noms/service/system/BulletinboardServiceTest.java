package com.foya.noms.service.system;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.mapper.TbSysBulletinboardMapper;
import com.foya.dao.mybatis.model.TbSysBulletinboard;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.noms.GenericTest;
import com.foya.noms.dto.system.BulletinboardDTO;
import com.foya.noms.util.DateUtils;

public class BulletinboardServiceTest extends GenericTest {

	@Autowired
	private BulletinboardService service;
	@Autowired
	private TbSysBulletinboardMapper tbSysBulletinboardMapper;
	@Test
	public void testGetBulletinboardByLoginDate() {
		String time= new SimpleDateFormat("2014-09-05 00:00:00").format(new Date());
		Timestamp ts = Timestamp.valueOf(time);
		List<BulletinboardDTO> sysBulletinboard = service.getBulletinboardByLoginDate(ts);
		Assert.assertNotNull(sysBulletinboard);
		Assert.assertTrue(sysBulletinboard.size()>0);
	}	
	
	@Test 
	public void testGetBulletinboardById() {
		BulletinboardDTO sysBulletinboard =service.getBulletinboardById(new BigDecimal(60));
		Assert.assertNotNull(sysBulletinboard);
		Assert.assertEquals(sysBulletinboard.getBulletinID(), new BigDecimal(60));
	}
		
	@Test
	public void testGetBulletinboardByCond() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("subject","junit");
		map.put("startDate","2014/09/05");
		map.put("endDate","2014/09/10");
		map.put("types",null);
		List<BulletinboardDTO> sysBulletinboard = service.getBulletinboardByCond(map);
		String time= new SimpleDateFormat("2014-09-05 00:00:00").format(new Date());
		Timestamp ts = Timestamp.valueOf(time);
		Assert.assertNotNull(sysBulletinboard);
		Assert.assertTrue(sysBulletinboard.size()>0);
		Assert.assertEquals(sysBulletinboard.get(0).getSubject(), "junit");
		Assert.assertEquals(sysBulletinboard.get(0).getBulletinID(), new BigDecimal(60));
		Assert.assertEquals(sysBulletinboard.get(0).getStartDate(),ts);
		time = new SimpleDateFormat("2014-09-10 00:00:00").format(new Date());
		ts = Timestamp.valueOf(time);
		Assert.assertEquals(sysBulletinboard.get(0).getEndDate(),ts);
	}
	
	@Test
	public void testAddBulletinboard() {
		TbSysBulletinboard sysBulletinboard = new TbSysBulletinboard();
		Date startDate = DateUtils.dayStart(2014, 9, 5);
		Date endDate = DateUtils.dayStop(2014, 9, 15);
		sysBulletinboard.setSUBJECT("編輯測試");
		sysBulletinboard.setSTARTDATE(startDate);
		sysBulletinboard.setENDDATE(endDate);
		sysBulletinboard.setCONTENTS("<em>公告類型測試</em><br />公告類型測試<br />公告類型測試");
		sysBulletinboard.setCR_USER("junit");
		sysBulletinboard.setPRIORITY(1);
		sysBulletinboard.setCR_TIME(new Date(System.currentTimeMillis()));
		sysBulletinboard.setTYPES(null);
		service.addBulletinboard(sysBulletinboard);
		Assert.assertTrue(sysBulletinboard.getBULLETIN_ID().intValue()>0);
	}
	
	@Test 
	public void testEditBulletinboard() {
		TbSysBulletinboard sysBulletinboard = tbSysBulletinboardMapper.selectByPrimaryKey(new BigDecimal(60));
		Date newStartDate = DateUtils.dayStart(2014, 9, 15);
		Date newEndDate = DateUtils.dayStop(2014, 9, 20);
		String subject = sysBulletinboard.getSUBJECT();
		Date startDate = sysBulletinboard.getSTARTDATE();
		Date endDate = sysBulletinboard.getENDDATE();
		String contents = sysBulletinboard.getCONTENTS();
		Integer priority = sysBulletinboard.getPRIORITY();
		Integer types = sysBulletinboard.getTYPES();
		sysBulletinboard.setSUBJECT("編輯測試");
		sysBulletinboard.setSTARTDATE(newStartDate);
		sysBulletinboard.setENDDATE(newEndDate);
		sysBulletinboard.setCONTENTS("<em>公告類型測試</em><br />公告類型測試<br />公告類型測試");
		sysBulletinboard.setPRIORITY(2);
		sysBulletinboard.setTYPES(null);
		service.editBulletinboard(sysBulletinboard);
		sysBulletinboard = tbSysBulletinboardMapper.selectByPrimaryKey(new BigDecimal(60));
		Assert.assertNotNull(sysBulletinboard);
		Assert.assertEquals(sysBulletinboard.getSUBJECT(), "編輯測試");
		Assert.assertEquals(sysBulletinboard.getSTARTDATE(), newStartDate);
		Assert.assertEquals(sysBulletinboard.getENDDATE(), newEndDate);
		Assert.assertEquals(sysBulletinboard.getCONTENTS(), "<em>公告類型測試</em><br />公告類型測試<br />公告類型測試");
		Assert.assertEquals(sysBulletinboard.getPRIORITY(), new Integer(2));
		Assert.assertEquals(sysBulletinboard.getTYPES(), null);
		
		sysBulletinboard.setSUBJECT(subject);
		sysBulletinboard.setSTARTDATE(startDate);
		sysBulletinboard.setENDDATE(endDate);
		sysBulletinboard.setCONTENTS(contents);
		sysBulletinboard.setPRIORITY(priority);
		sysBulletinboard.setTYPES(types);
		service.editBulletinboard(sysBulletinboard);
		sysBulletinboard = tbSysBulletinboardMapper.selectByPrimaryKey(new BigDecimal(60));
		Assert.assertNotNull(sysBulletinboard);
		Assert.assertEquals(sysBulletinboard.getSUBJECT(), subject);
		Assert.assertEquals(sysBulletinboard.getSTARTDATE(), startDate);
		Assert.assertEquals(sysBulletinboard.getENDDATE(), endDate);
		Assert.assertEquals(sysBulletinboard.getCONTENTS(), contents);
		Assert.assertEquals(sysBulletinboard.getPRIORITY(), priority);
		Assert.assertEquals(sysBulletinboard.getTYPES(), types);
	}
	
	@Test
	public void testDeleteBulletinboard() {
		TbSysBulletinboard sysBulletinboard = new TbSysBulletinboard();
		Date startDate = DateUtils.dayStart(2014, 9, 5);
		Date endDate = DateUtils.dayStop(2014, 9, 15);
		sysBulletinboard.setSUBJECT("編輯測試");
		sysBulletinboard.setSTARTDATE(startDate);
		sysBulletinboard.setENDDATE(endDate);
		sysBulletinboard.setCONTENTS("<em>公告類型測試</em><br />公告類型測試<br />公告類型測試");
		sysBulletinboard.setCR_USER("junit");
		sysBulletinboard.setPRIORITY(1);
		sysBulletinboard.setCR_TIME(new Date(System.currentTimeMillis()));
		sysBulletinboard.setTYPES(null);
		service.addBulletinboard(sysBulletinboard);
		String[] bulletinIds = {String.valueOf(sysBulletinboard.getBULLETIN_ID())};
		service.deleteBulletinboard(bulletinIds);
	}
	
	@Test
	public void testGetBulletinType() {
		List<TbSysLookup> lookup = service.getBulletinType();
		Assert.assertNotNull(lookup);
		Assert.assertTrue(lookup.size()>0);
	}
		
	
}
