package com.foya.noms.dao.common;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.TbComFileDtl;
import com.foya.dao.mybatis.model.TbComFileDtlExample;
import com.foya.noms.GenericTest;
import com.foya.noms.dto.common.FileDtlDTO;

public class FileDtlDaoTest extends GenericTest {

	@Autowired
	private FileDtlDao dao;
	
	@Test
	public void testFindByPk() {
		TbComFileDtl file = dao.findByPk(BigDecimal.valueOf(445));
		Assert.assertNotNull(file);
		Assert.assertEquals(file.getFILE_DTL_SEQ_ID(), BigDecimal.valueOf(445));
	}
	
	@Test
	public void testFindByCondition() {
		TbComFileDtlExample example = new TbComFileDtlExample();
		List<TbComFileDtl> files = dao.findByCondition(example);
		Assert.assertNotNull(files);
		Assert.assertTrue(files.size()>0);
	}
	
	@Test
	public void testInsert() {
		TbComFileDtl file =new TbComFileDtl();
		file.setCR_TIME(new Date());
		file.setCR_USER("junit_test");
		file.setDOC_NO("junit_no");
		file.setFILE_NAME("junit_name");
		file.setFILE_TYPE("TYPE2");
		file.setTYPE_PATH("junit_path");
		int item = dao.insert(file);
		Assert.assertEquals(item, 1);
		Assert.assertTrue(file.getFILE_DTL_SEQ_ID().intValue()>0);
	}
	
	@Test
	public void testUpdate() {
		TbComFileDtl file = dao.findByPk(BigDecimal.valueOf(445));
		String docNo = file.getDOC_NO();
		String fileName = file.getFILE_NAME();
		String fileType = file.getFILE_TYPE();
		String typePath = file.getTYPE_PATH();
		file.setDOC_NO("junit_no");
		file.setFILE_NAME("junit_name");
		file.setFILE_TYPE("junit_type");
		file.setTYPE_PATH("junit_path");
		int item = dao.update(file);
		file = dao.findByPk(BigDecimal.valueOf(445));
		Assert.assertNotNull(file);
		Assert.assertEquals(item, 1);
		Assert.assertEquals(file.getDOC_NO(), "junit_no");
		Assert.assertEquals(file.getFILE_NAME(), "junit_name");
		Assert.assertEquals(file.getFILE_TYPE(), "junit_type");
		Assert.assertEquals(file.getTYPE_PATH(), "junit_path");
		file.setDOC_NO(docNo);
		file.setFILE_NAME(fileName);
		file.setFILE_TYPE(fileType);
		file.setTYPE_PATH(typePath);
		item = dao.update(file);
		file = dao.findByPk(BigDecimal.valueOf(445));
		Assert.assertNotNull(file);
		Assert.assertEquals(item, 1);
		Assert.assertEquals(file.getDOC_NO(), docNo);
		Assert.assertEquals(file.getFILE_NAME(), fileName);
		Assert.assertEquals(file.getFILE_TYPE(), fileType);
		Assert.assertEquals(file.getTYPE_PATH(), typePath);
	}
	
	@Test
	public void testUpdateByExample() {
		TbComFileDtl file = dao.findByPk(BigDecimal.valueOf(445));
		String docNo = file.getDOC_NO();
		String fileName = file.getFILE_NAME();
		String fileType = file.getFILE_TYPE();
		String typePath = file.getTYPE_PATH();
		file.setDOC_NO("junit_no");
		file.setFILE_NAME("junit_name");
		file.setFILE_TYPE("junit_type");
		file.setTYPE_PATH("junit_path");
		file.setFILE_DTL_SEQ_ID(null);
		TbComFileDtlExample example = new TbComFileDtlExample();
		example.createCriteria().andFILE_DTL_SEQ_IDEqualTo(BigDecimal.valueOf(445));
		int item = dao.update(file,example);
		file = dao.findByPk(BigDecimal.valueOf(445));
		Assert.assertNotNull(file);
		Assert.assertEquals(item, 1);
		Assert.assertEquals(file.getDOC_NO(), "junit_no");
		Assert.assertEquals(file.getFILE_NAME(), "junit_name");
		Assert.assertEquals(file.getFILE_TYPE(), "junit_type");
		Assert.assertEquals(file.getTYPE_PATH(), "junit_path");
		file.setDOC_NO(docNo);
		file.setFILE_NAME(fileName);
		file.setFILE_TYPE(fileType);
		file.setTYPE_PATH(typePath);
		file.setFILE_DTL_SEQ_ID(null);
		item = dao.update(file,example);
		file = dao.findByPk(BigDecimal.valueOf(445));
		Assert.assertNotNull(file);
		Assert.assertEquals(item, 1);
		Assert.assertEquals(file.getDOC_NO(), docNo);
		Assert.assertEquals(file.getFILE_NAME(), fileName);
		Assert.assertEquals(file.getFILE_TYPE(), fileType);
		Assert.assertEquals(file.getTYPE_PATH(), typePath);
	}
	
	@Test
	public void testDelete() {
		TbComFileDtl file =new TbComFileDtl();
		file.setCR_TIME(new Date());
		file.setCR_USER("junit_test");
		file.setDOC_NO("junit_no");
		file.setFILE_NAME("junit_name");
		file.setFILE_TYPE("TYPE2");
		file.setTYPE_PATH("junit_path");
		dao.insert(file);
		TbComFileDtlExample example = new TbComFileDtlExample();
		example.createCriteria().andFILE_DTL_SEQ_IDEqualTo(file.getFILE_DTL_SEQ_ID());
		int item = dao.delete(example);
		Assert.assertEquals(item, 1);
	}
	
	@Test
	public void testDeleteByFileId() {
		TbComFileDtl file =new TbComFileDtl();
		file.setCR_TIME(new Date());
		file.setCR_USER("junit_test");
		file.setDOC_NO("junit_no");
		file.setFILE_NAME("junit_name");
		file.setFILE_TYPE("TYPE2");
		file.setTYPE_PATH("junit_path");
		dao.insert(file);
		int item = dao.delete(file.getFILE_DTL_SEQ_ID());
		Assert.assertEquals(item, 1);
	}
	
	@Test
	public void testFindByTypepathAndDocno() {
		HashMap<String, String> condition = new HashMap<String, String>();
		condition.put("typePath", "BULLETIN");
		condition.put("fileDoc", "junit");
		condition.put("fileType", "TYPE1");
		List<FileDtlDTO> files = dao.findByMapCondition(condition);
		Assert.assertNotNull(files);
		Assert.assertEquals(files.get(0).getTYPE_PATH(), "BULLETIN");
		Assert.assertEquals(files.get(0).getDOC_NO(), "junit");
		Assert.assertEquals(files.get(0).getFILE_TYPE(), "TYPE1");
	}
	
	@Test
	public void testFindFullPathByFileId() {
		String result = dao.findFullPathByFileId(BigDecimal.valueOf(445));
		Assert.assertNotNull(result);
		Assert.assertTrue(result.length()>0);
	}
}
