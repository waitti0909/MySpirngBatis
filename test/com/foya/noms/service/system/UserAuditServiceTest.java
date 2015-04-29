package com.foya.noms.service.system;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.TbSysUserAudit;
import com.foya.noms.GenericTest;

public class UserAuditServiceTest extends GenericTest {

	@Autowired
	private UserAuditService service;
	
	@Test
	public void insert() {
		TbSysUserAudit user = new TbSysUserAudit();
		user.setPSN_ID(BigDecimal.valueOf(3));
		user.setPSN_NAME("junit");
		user.setLOG_DESCRIPTION("_search=false&nd=1407404304946&rows=20&page=1&sidx=&sord=asc&selectID=");
		user.setLOG_TIME(new Date());
		user.setLOG_TYPE("SystemMenuController.selectMenuList");
		user.setSESSION_ID("51B124AF9F2D59036EADFBDB585B20EE");
		user.setURL_PATH("http://localhost:8080/noms/auth/systemMenu/select_list");
		user.setUSER_IP("127.0.0.1");
		service.insert(user);
	}
}
