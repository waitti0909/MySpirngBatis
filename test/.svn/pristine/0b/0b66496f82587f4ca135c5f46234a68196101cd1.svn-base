package com.foya.noms.dao.org;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbOrgDeptExample;
import com.foya.noms.GenericTest;
import com.foya.noms.dto.org.DeptDTO;

public class DeptDaoTest extends GenericTest {

	@Autowired
	private DeptDao dao;
	
	@Test
	public void testFindByPk() {
		TbOrgDept dept = dao.findByPk("123456");
		Assert.assertNotNull(dept);
		Assert.assertEquals(dept.getDEPT_ID(), "123456");
	}
	
	@Test
	public void testFindByCondition() {
		TbOrgDeptExample example = new TbOrgDeptExample();
		List<TbOrgDept> deptList = dao.findByCondition(example);
		Assert.assertNotNull(deptList);
		Assert.assertTrue(deptList.size()>0);
	}

	@Test
	public void testUpdateDeptManagerByDeptPosId() {
		TbOrgDept dept = dao.findByPk("123456");
		String manager = dept.getMANAGER();
		int item = dao.updateDeptManagerByDeptPosId("123456__aaa", "123456__junitId");
		dept = dao.findByPk("123456");
		Assert.assertTrue(item > 0);
		Assert.assertNotNull(dept);
		Assert.assertEquals(dept.getMANAGER(), "123456__junitId");
		item = dao.updateDeptManagerByDeptPosId("123456__junitId", manager);
		dept = dao.findByPk("123456");
		Assert.assertTrue(item > 0);
		Assert.assertNotNull(dept);
		Assert.assertEquals(dept.getMANAGER(), manager);
	}

	@Test
	public void testGetDeptById() {
		TbOrgDept dept = dao.getDeptById("123456");
		Assert.assertNotNull(dept);
		Assert.assertEquals(dept.getDEPT_ID(), "123456");
	}

	@Test
	public void testSelectAllOrgDept() {
		TbOrgDeptExample example = new TbOrgDeptExample();
		List<TbOrgDept> deptList = dao.selectAllOrgDept(example);
		Assert.assertNotNull(deptList);
		Assert.assertTrue(deptList.size()>0);
	}

	@Test
	public void testSelectOrgDeptById() {
		TbOrgDept dept = dao.selectOrgDeptById("123456");
		Assert.assertNotNull(dept);
		Assert.assertEquals(dept.getDEPT_ID(), "123456");
	}

	@Test
	public void testSelectByPrimaryKey() {
		TbOrgDept dept = dao.selectByPrimaryKey("123456");
		Assert.assertNotNull(dept);
		Assert.assertEquals(dept.getDEPT_ID(), "123456");
	}

	@Test
	public void testSaveNewDept() {
		TbOrgDept orgDept = new TbOrgDept();
		orgDept.setDEPT_ID("123457");
		orgDept.setDEPT_LEVEL("RO");
		orgDept.setDEPT_NAME("junitTestDept");
		orgDept.setDOMAIN("HQ");
		orgDept.setHR_DEPT_ID("1111");
		orgDept.setMANAGER("123457__junitId");
		orgDept.setPARENT_DEPT_ID("123456");
		orgDept.setTYPE("D");
		dao.saveNewDept(orgDept);
		orgDept = dao.getDeptById("123457");
		Assert.assertNotNull(orgDept);
		Assert.assertEquals(orgDept.getDEPT_ID(), "123457");
		Assert.assertEquals(orgDept.getDEPT_LEVEL(), "RO");
		Assert.assertEquals(orgDept.getDEPT_NAME(), "junitTestDept");
		Assert.assertEquals(orgDept.getDOMAIN(), "HQ");
		Assert.assertEquals(orgDept.getHR_DEPT_ID(), "1111");
		Assert.assertEquals(orgDept.getMANAGER(), "123457__junitId");
		Assert.assertEquals(orgDept.getPARENT_DEPT_ID(), "123456");
		Assert.assertEquals(orgDept.getTYPE(), "D");
	}

	@Test
	public void testSaveUpdateDept() {
		TbOrgDept orgDept = dao.selectByPrimaryKey("123456");
		String deptLevel = orgDept.getDEPT_LEVEL();
		String deptName = orgDept.getDEPT_NAME();
		String domain = orgDept.getDOMAIN();
		String hrDeptId = orgDept.getHR_DEPT_ID();
		String manager = orgDept.getMANAGER();
		String parentDeptId = orgDept.getPARENT_DEPT_ID();
		String type = orgDept.getTYPE();
		orgDept.setDEPT_LEVEL("DP");
		orgDept.setDEPT_NAME("junitTestDept");
		orgDept.setDOMAIN("N1");
		orgDept.setHR_DEPT_ID("1111");
		orgDept.setMANAGER("123457__junitId");
		orgDept.setPARENT_DEPT_ID("123456");
		orgDept.setTYPE("S");
		dao.saveUpdateDept(orgDept);
		orgDept = dao.getDeptById("123456");
		Assert.assertNotNull(orgDept);
		Assert.assertEquals(orgDept.getDEPT_LEVEL(), "DP");
		Assert.assertEquals(orgDept.getDEPT_NAME(), "junitTestDept");
		Assert.assertEquals(orgDept.getDOMAIN(), "N1");
		Assert.assertEquals(orgDept.getHR_DEPT_ID(), "1111");
		Assert.assertEquals(orgDept.getMANAGER(), "123457__junitId");
		Assert.assertEquals(orgDept.getPARENT_DEPT_ID(), "123456");
		Assert.assertEquals(orgDept.getTYPE(), "S");
		orgDept.setDEPT_LEVEL(deptLevel);
		orgDept.setDEPT_NAME(deptName);
		orgDept.setDOMAIN(domain);
		orgDept.setHR_DEPT_ID(hrDeptId);
		orgDept.setMANAGER(manager);
		orgDept.setPARENT_DEPT_ID(parentDeptId);
		orgDept.setTYPE(type);
		orgDept = dao.getDeptById("123456");
		Assert.assertNotNull(orgDept);
		Assert.assertEquals(orgDept.getDEPT_LEVEL(),deptLevel);
		Assert.assertEquals(orgDept.getDEPT_NAME(), deptName);
		Assert.assertEquals(orgDept.getDOMAIN(), domain);
		Assert.assertEquals(orgDept.getHR_DEPT_ID(), hrDeptId);
		Assert.assertEquals(orgDept.getMANAGER(), manager);
		Assert.assertEquals(orgDept.getPARENT_DEPT_ID(), parentDeptId);
		Assert.assertEquals(orgDept.getTYPE(), type);
	}

	@Test
	public void testGetChildDeptsById() {
		List<TbOrgDept> deptList = dao.getChildDeptsById("123456");
		Assert.assertNotNull(deptList);
		Assert.assertTrue(deptList.size()>0);
	}

	@Test
	public void testDeleteDeptById() {
		TbOrgDept orgDept = new TbOrgDept();
		orgDept.setDEPT_ID("123457");
		orgDept.setDEPT_LEVEL("RO");
		orgDept.setDEPT_NAME("junitTestDept");
		orgDept.setDOMAIN("HQ");
		orgDept.setHR_DEPT_ID("1111");
		orgDept.setMANAGER("123457__junitId");
		orgDept.setPARENT_DEPT_ID("123456");
		orgDept.setTYPE("D");
		dao.saveNewDept(orgDept);
		dao.deleteDeptById("123457");
		Assert.assertNull(dao.getDeptById("123457"));
	}

	@Test
	public void testSearchPositionByDeptId() {
		List<DeptDTO> dpetposList = dao.searchPositionByDeptId("123456");
		Assert.assertNotNull(dpetposList);
		Assert.assertTrue(dpetposList.size()>0);
	}

	@Test
	public void testSaveUpdateDeptManager() {
		TbOrgDept dept = dao.findByPk("123456");
		String manager = dept.getMANAGER();
		int item = dao.saveUpdateDeptManager("123456", "123456__junitId");
		dept = dao.findByPk("123456");
		Assert.assertTrue(item==1);
		Assert.assertEquals(dept.getMANAGER(), "123456__junitId");
		item = dao.saveUpdateDeptManager("123456", manager);
		dept = dao.findByPk("123456");
		Assert.assertTrue(item==1);
		Assert.assertEquals(dept.getMANAGER(), manager);
	}

	@Test
	public void testGetDeptTree() {
		List<DeptDTO> deptList = dao.getDeptTree();
		Assert.assertNotNull(deptList);
		Assert.assertTrue(deptList.size()>0);
	}

	@Test
	public void testSearchParentByChild() {
		List<DeptDTO> deptList = dao.searchParentByChild("123456");
		Assert.assertNotNull(deptList);
		Assert.assertTrue(deptList.size()>0);
	}

}
