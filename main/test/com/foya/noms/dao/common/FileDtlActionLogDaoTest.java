package com.foya.noms.dao.common;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.TbComFileDtlActionLog;
import com.foya.dao.mybatis.model.TbComFileDtlActionLogExample;
import com.foya.noms.GenericTest;
import com.foya.noms.util.DateUtils;

public class FileDtlActionLogDaoTest extends GenericTest {

	@Autowired
	private FileDtlActionLogDao dao;

	@Test
	public void testFindByCondition() {
		TbComFileDtlActionLogExample example = new TbComFileDtlActionLogExample();
		List<TbComFileDtlActionLog> fileLogs = dao.findByCondition(example);
		Assert.assertNotNull(fileLogs);
		Assert.assertTrue(fileLogs.size() > 0);
	}

	@Test
	public void testInsert() {
		TbComFileDtlActionLog fileLog = new TbComFileDtlActionLog();
		fileLog.setACTION("UPLOAD");
		fileLog.setACTION_TIME(new Date());
		fileLog.setACTION_USER("junit");
		fileLog.setDOC_NO("junit_NO");
		fileLog.setFILE_NAME("junitTest");
		fileLog.setFILE_TYPE(null);
		fileLog.setREMOTE_IP("127.0.0.1");
		fileLog.setTYPE_PATH("test");
		int item = dao.insert(fileLog);
		Assert.assertEquals(item, 1);
	}

	@Test
	public void testUpdate() {
		TbComFileDtlActionLogExample example = new TbComFileDtlActionLogExample();
		Date date =DateUtils.time(2014, 9, 12, 0, 0, 0);
		example.createCriteria().andACTIONEqualTo("UPLOAD")
				.andACTION_TIMEEqualTo(date).andACTION_USEREqualTo("junit")
				.andDOC_NOEqualTo("junit").andFILE_NAMEEqualTo("junitTest")
				.andREMOTE_IPEqualTo("127.0.0.1").andTYPE_PATHEqualTo("junit");
		List<TbComFileDtlActionLog> fileLogs = dao.findByCondition(example);
		TbComFileDtlActionLog fileLog = fileLogs.get(0);
		String action = fileLog.getACTION();
		Date time = fileLog.getACTION_TIME();
		String user = fileLog.getACTION_USER();
		String docNo = fileLog.getDOC_NO();
		String fileName = fileLog.getFILE_NAME();
		String remoteIp = fileLog.getREMOTE_IP();
		String typePath = fileLog.getTYPE_PATH();
		Date actionTime =DateUtils.time(2015, 10, 20, 5, 10, 0);
		fileLog.setACTION("UPLOAD");
		fileLog.setACTION_TIME(actionTime);
		fileLog.setACTION_USER("junit_user");
		fileLog.setDOC_NO("junit_no");
		fileLog.setFILE_NAME("junit_name");
		fileLog.setREMOTE_IP("localhost");
		fileLog.setTYPE_PATH("junit_path");
		int item = dao.update(fileLog, example);
		example = new TbComFileDtlActionLogExample();
		date =actionTime;
		example.createCriteria().andACTIONEqualTo("UPLOAD")
				.andACTION_TIMEEqualTo(date).andACTION_USEREqualTo("junit_user")
				.andDOC_NOEqualTo("junit_no").andFILE_NAMEEqualTo("junit_name")
				.andREMOTE_IPEqualTo("localhost").andTYPE_PATHEqualTo("junit_path");
		fileLogs = dao.findByCondition(example);
		fileLog = fileLogs.get(0);
		Assert.assertNotNull(fileLogs);
		Assert.assertEquals(item, 1);
		Assert.assertEquals( fileLog.getACTION(), "UPLOAD");
		Assert.assertEquals( fileLog.getACTION_TIME(), actionTime);
		Assert.assertEquals( fileLog.getACTION_USER(), "junit_user");
		Assert.assertEquals( fileLog.getDOC_NO(), "junit_no");
		Assert.assertEquals( fileLog.getFILE_NAME(), "junit_name");
		Assert.assertEquals( fileLog.getREMOTE_IP(), "localhost");
		Assert.assertEquals( fileLog.getTYPE_PATH(), "junit_path");
		fileLog.setACTION(action);
		fileLog.setACTION_TIME(time);
		fileLog.setACTION_USER(user);
		fileLog.setDOC_NO(docNo);
		fileLog.setFILE_NAME(fileName);
		fileLog.setREMOTE_IP(remoteIp);
		fileLog.setTYPE_PATH(typePath);
		item = dao.update(fileLog, example);
		example = new TbComFileDtlActionLogExample();
		date =actionTime;
		example.createCriteria().andACTIONEqualTo(action)
				.andACTION_TIMEEqualTo(time).andACTION_USEREqualTo(user)
				.andDOC_NOEqualTo(docNo).andFILE_NAMEEqualTo(fileName)
				.andREMOTE_IPEqualTo(remoteIp).andTYPE_PATHEqualTo(typePath);
		fileLogs = dao.findByCondition(example);
		fileLog = fileLogs.get(0);
		Assert.assertNotNull(fileLogs);
		Assert.assertEquals(item, 1);
		Assert.assertEquals( fileLog.getACTION(), action);
		Assert.assertEquals( fileLog.getACTION_TIME(), time);
		Assert.assertEquals( fileLog.getACTION_USER(), user);
		Assert.assertEquals( fileLog.getDOC_NO(), docNo);
		Assert.assertEquals( fileLog.getFILE_NAME(), fileName);
		Assert.assertEquals( fileLog.getREMOTE_IP(), remoteIp);
		Assert.assertEquals( fileLog.getTYPE_PATH(), typePath);
	}

	@Test
	public void testDelete() {
		TbComFileDtlActionLog fileLog = new TbComFileDtlActionLog();
		Date date =DateUtils.time(2014, 9, 12, 5, 12, 50);
		fileLog.setACTION("UPLOAD");
		fileLog.setACTION_TIME(date);
		fileLog.setACTION_USER("junit");
		fileLog.setDOC_NO("junit_NO");
		fileLog.setFILE_NAME("junitTest");
		fileLog.setFILE_TYPE(null);
		fileLog.setREMOTE_IP("127.0.0.1");
		fileLog.setTYPE_PATH("test");
		dao.insert(fileLog);
		TbComFileDtlActionLogExample example = new TbComFileDtlActionLogExample();
		example.createCriteria().andACTIONEqualTo("UPLOAD")
				.andACTION_TIMEEqualTo(date).andACTION_USEREqualTo("junit")
				.andDOC_NOEqualTo("junit_NO").andFILE_NAMEEqualTo("junitTest")
				.andREMOTE_IPEqualTo("127.0.0.1").andTYPE_PATHEqualTo("test");
		int item = dao.delete(example);
		Assert.assertEquals(item, 1);
	}
}
