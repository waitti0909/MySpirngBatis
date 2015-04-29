package com.foya.noms.dao.auth;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.dao.mybatis.model.TbAuthPersonnelExample;
import com.foya.noms.GenericTest;
import com.foya.noms.dto.auth.PersonnelDTO;
import com.foya.noms.dto.auth.UserDTO;

public class PersonnelDaoTest extends GenericTest {

	@Autowired
	private PersonnelDao dao;

	@Test
	public void testSelectAllPsn() {
		List<TbAuthPersonnel> users = dao.selectAllPsn();
		Assert.assertNotNull(users);
		Assert.assertTrue(users.size() > 0);
	}

	@Test
	public void testGetPersonnel() {
		List<TbAuthPersonnel> users = dao.getPersonnelsByExample(new TbAuthPersonnelExample());
		Assert.assertNotNull(users);
		Assert.assertTrue(users.size() > 0);
	}

	@Test
	public void testGetUserDtoByPsnNo() {
		UserDTO user = dao.getUserDtoByPsnNo("admin");
		Assert.assertNotNull(user);
	}

	@Test
	public void testGetUserDtosByPsnNos() {
		Set<String> psnNos = new HashSet<>();
		psnNos.add("admin");
		psnNos.add("junit");
		List<UserDTO> users = dao.getUserDtoByPsnNos(psnNos);
		Assert.assertNotNull(users);
		Assert.assertTrue(users.size() > 0);
	}

	@Test
	public void testGetUserDtosByDeptPosId() {
		String deptPosId = "110000__aaa";
		List<UserDTO> users = dao.getUserDtoByDeptPosId(deptPosId);
		Assert.assertNotNull(users);
		Assert.assertTrue(users.size() > 0);
	}

	@Test
	public void testInsertComputer() {
		TbAuthPersonnel record = new TbAuthPersonnel();
		dao.insertComputer(record);
	}

	@Test
	public void testGetPsnDeptById() {
		PersonnelDTO psn = dao.getPsnDeptById(3);
		Assert.assertNotNull(psn);
		Assert.assertEquals(psn.getPSN_ID().intValue(), 3);
	}

	@Test
	public void testSearchPsnByNoChnNameEmail() {
		List<TbAuthPersonnel> psn = dao.searchPsnByNoChnNameEmail("junit", "junitCHN_NM",
				"junit@junit.com");
		Assert.assertNotNull(psn);
		Assert.assertTrue(psn.size() > 0);
		Assert.assertEquals(psn.get(0).getCHN_NM(), "junitCHN_NM");
		Assert.assertEquals(psn.get(0).getPSN_NO(), "junit");
		Assert.assertEquals(psn.get(0).getE_MAIL(), "junit@junit.com");
	}
}
