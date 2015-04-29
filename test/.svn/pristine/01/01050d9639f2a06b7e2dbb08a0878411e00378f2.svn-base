package com.foya.noms.dao.system;

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
import com.foya.noms.GenericTest;
import com.foya.noms.dto.system.BulletinboardDTO;
import com.foya.noms.util.DateUtils;

public class BulletinboardDaoTest extends GenericTest {

	@Autowired
	private BulletinboardDao dao;
	@Autowired
	private TbSysBulletinboardMapper tbSysBulletinboardMapper;
	
	@Test
	public void testSelectBulletinboardByDate(){
		String time= new SimpleDateFormat("2014-09-05 00:00:00").format(new Date());
		Timestamp ts = Timestamp.valueOf(time);
		List<BulletinboardDTO> sysBulletinboard = dao.selectBulletinboardByDate(ts);
		Assert.assertNotNull(sysBulletinboard);
		Assert.assertTrue(sysBulletinboard.size()>0);
	}
	
	@Test
	public void testSelectBulletinboardById(){
		BulletinboardDTO sysBulletinboard =dao.selectBulletinboardById(new BigDecimal(60));
		Assert.assertNotNull(sysBulletinboard);
		Assert.assertEquals(sysBulletinboard.getBulletinID(), new BigDecimal(60));
	}
	
	@Test
	public void testSelectBulletinboardByCond(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("subject","junit");
		map.put("startDate","2014/09/05");
		map.put("endDate","2014/09/10");
		map.put("types",null);
		List<BulletinboardDTO> sysBulletinboard = dao.selectBulletinboardByCond(map);
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
	public void testAddBulletinboard(){		
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
		int item = dao.addBulletinboard(sysBulletinboard);
		Assert.assertEquals(item , 1);
		Assert.assertTrue(sysBulletinboard.getBULLETIN_ID().intValue()>0);
	}
	
	@Test
	public void testEditBulletinboard(){		
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
		int item = dao.editBulletinboard(sysBulletinboard);
		sysBulletinboard = tbSysBulletinboardMapper.selectByPrimaryKey(new BigDecimal(60));
		Assert.assertEquals(item , 1);
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
		item = dao.editBulletinboard(sysBulletinboard);
		sysBulletinboard = tbSysBulletinboardMapper.selectByPrimaryKey(new BigDecimal(60));
		Assert.assertNotNull(sysBulletinboard);
		Assert.assertEquals(item , 1);
		Assert.assertEquals(sysBulletinboard.getSUBJECT(), subject);
		Assert.assertEquals(sysBulletinboard.getSTARTDATE(), startDate);
		Assert.assertEquals(sysBulletinboard.getENDDATE(), endDate);
		Assert.assertEquals(sysBulletinboard.getCONTENTS(), contents);
		Assert.assertEquals(sysBulletinboard.getPRIORITY(), priority);
		Assert.assertEquals(sysBulletinboard.getTYPES(), types);
	}
	
	@Test
	public void testDeleteBulletinboard(){		
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
		dao.addBulletinboard(sysBulletinboard);
		dao.deleteBulletinboard(sysBulletinboard.getBULLETIN_ID());
	}
	
}
