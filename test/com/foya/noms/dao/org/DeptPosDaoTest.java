package com.foya.noms.dao.org;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.TbOrgDeptPos;
import com.foya.noms.GenericTest;
import com.foya.noms.dto.org.DeptPosDTO;

public class DeptPosDaoTest extends GenericTest {

	@Autowired
	private DeptPosDao dao;
	
	@Test
	public void testGetDeptPosByDeptId() {
		List<TbOrgDeptPos> deptPosList = dao.getDeptPosByDeptId("123456");
		Assert.assertNotNull(deptPosList);
		Assert.assertTrue(deptPosList.size()>0);
		
	}

	@Test
	public void testGetDeptPosDtoByDeptPosId() {
		List<DeptPosDTO> deptPosList = dao.getDeptPosDtoByDeptPosId("123456__junitId");
		Assert.assertNotNull(deptPosList);
		Assert.assertTrue(deptPosList.size()>0);
	}

	@Test
	public void testGetDeptPosDtoByDeptId() {
		List<DeptPosDTO> deptPosList = dao.getDeptPosDtoByDeptId("123456");
		Assert.assertNotNull(deptPosList);
		Assert.assertTrue(deptPosList.size()>0);
		
	}

	@Test
	public void testGetDeptPosDtoByPosId() {
		List<DeptPosDTO> deptPosList = dao.getDeptPosDtoByPosId("junitId");
		Assert.assertNotNull(deptPosList);
		Assert.assertTrue(deptPosList.size()>0);
	}

	@Test
	public void testGetDeptPosDtoByDeptIdAndPosId() {
		List<DeptPosDTO> deptPosList = dao.getDeptPosDtoByDeptIdAndPosId("123456", "junitId");
		Assert.assertNotNull(deptPosList);
		Assert.assertTrue(deptPosList.size()==1);
	}

	@Test
	public void testGetDeptPosByDeptPos() {
		List<TbOrgDeptPos> deptPosList = dao.getDeptPosByDeptPos("123456", "junitId");
		Assert.assertNotNull(deptPosList);
		Assert.assertTrue(deptPosList.size()==1);
	}

	@Test
	public void testSaveNewDeptPos() {
		TbOrgDeptPos deptPos = new TbOrgDeptPos();
		deptPos.setDEPT_ID("123458");
		deptPos.setDEPT_POS_ID("123458__junitId");
		deptPos.setPOS_ID("junitId");
		deptPos.setPOS_TYPE("N");
		int item  = dao.saveNewDeptPos(deptPos);
		Assert.assertTrue(item == 1);
	}

	@Test
	public void testDeleteDeptPosByPrimaryKey() {
		TbOrgDeptPos deptPos = new TbOrgDeptPos();
		deptPos.setDEPT_ID("123458");
		deptPos.setDEPT_POS_ID("123458__junitId");
		deptPos.setPOS_ID("junitId");
		deptPos.setPOS_TYPE("N");
		int item = dao.saveNewDeptPos(deptPos);
		Assert.assertTrue(item == 1);
		item  = dao.deleteDeptPosByPrimaryKey("123458__junitId");
		Assert.assertTrue(item == 1);
	}

	@Test
	public void testSaveUpdateDeptPos() {
		
	}

	@Test
	public void testSerachDeptPosByDeptPosId() {
		
	}

}
