package com.foya.noms.dao.system;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.TbSysUserAudit;
import com.foya.noms.GenericTest;
import com.foya.noms.util.DateUtils;

public class SysUserAuditDaoTest extends GenericTest {

	@Autowired
	private SysUserAuditDao dao;
	
//	@Test
	public void testSelectById() {
		
	}
	
	@Test	
	public void testInsert() {
		TbSysUserAudit user = new TbSysUserAudit();
		Date date = DateUtils.time(2014, 9, 12, 7, 15, 0);
		user.setLOG_DESCRIPTION("DESCRIPTION");
		user.setLOG_TIME(date);
		user.setLOG_TYPE("LoginController.loginSuccess");
		user.setPSN_ID(BigDecimal.valueOf(1));
		user.setPSN_NAME("junit");
		user.setSESSION_ID("460ADBF4490B26656A5F50B0AD03B60F");
		user.setURL_PATH("http://localhost:8080/noms/auth/systemMenu/select_list");
		user.setUSER_IP("127.0.0.1");
		dao.insert(user);
	}
	
//	@Test
	public void testUpdate() {
		
	}
	
//	@Test
	public void testDelete() {
		
	}

}
