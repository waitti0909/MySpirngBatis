package com.foya.noms.dao.common;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.TbComFile;
import com.foya.dao.mybatis.model.TbComFileExample;
import com.foya.noms.GenericTest;

public class FileDaoTest extends GenericTest {

	@Autowired
	private FileDao dao;

	@Test
	public void testFindByCondition() {
		List<TbComFile> files = dao.findByCondition(new TbComFileExample());
		Assert.assertNotNull(files);
		Assert.assertTrue(files.size() > 0);
	}

	@Test
	public void testInsert() {
		TbComFile file = new TbComFile();
		file.setDOC_NO("junitTest");
		file.setTYPE_PATH("BULLETIN");
		int item = dao.insert(file);
		Assert.assertEquals(item, 1);
	}

	@Test
	public void testUpdate() {
		TbComFileExample fileExample = new TbComFileExample();
		fileExample.createCriteria().andDOC_NOEqualTo("junit").andTYPE_PATHEqualTo("BULLETIN");
		List<TbComFile> files = dao.findByCondition(fileExample);
		TbComFile file = files.get(0);
		String docNo = file.getDOC_NO();
		String typePath = file.getTYPE_PATH();
		file.setDOC_NO("junitTestNo");
		file.setTYPE_PATH("junitTestPath");
		int item = dao.update(file, fileExample);
		fileExample = new TbComFileExample();
		fileExample.createCriteria().andDOC_NOEqualTo("junitTestNo").andTYPE_PATHEqualTo("junitTestPath");
		files = dao.findByCondition(fileExample);
		file = files.get(0);
		Assert.assertNotNull(files);
		Assert.assertEquals(item, 1);
		Assert.assertEquals(file.getDOC_NO(),"junitTestNo");
		Assert.assertEquals(file.getTYPE_PATH(),"junitTestPath");
		file.setDOC_NO(docNo);
		file.setTYPE_PATH(typePath);
		item = dao.update(file, fileExample);
		fileExample = new TbComFileExample();
		fileExample.createCriteria().andDOC_NOEqualTo("junit").andTYPE_PATHEqualTo("BULLETIN");
		files = dao.findByCondition(fileExample);
		file = files.get(0);
		Assert.assertNotNull(files);
		Assert.assertEquals(item, 1);
		Assert.assertEquals(file.getDOC_NO(),docNo);
		Assert.assertEquals(file.getTYPE_PATH(),typePath);
	}

	@Test
	public void testDelete() {
		TbComFile file = new TbComFile();
		file.setDOC_NO("junitTest");
		file.setTYPE_PATH("BULLETIN");
		dao.insert(file);
		TbComFileExample fileExample = new TbComFileExample();
		fileExample.createCriteria().andDOC_NOEqualTo("junitTest").andTYPE_PATHEqualTo("BULLETIN");
		int item = dao.delete(fileExample);
		Assert.assertEquals(item, 1);
	}
}
