package com.foya.noms.service.pay;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.UTbPayAcceptanceOrderExample;
import com.foya.noms.GenericTest;
import com.foya.noms.dto.pay.TbPayAcceptanceOrderDTO;

public class PAY006ServiceTest  extends GenericTest {

	@Autowired
	private PAY006Service pay006Service;

	@Test
	public void testSelectComCompanyByExample() {
		fail("Not yet implemented");
	}

	@Test
	public void testSelectPayLookupCodeByExample() {
		fail("Not yet implemented");
	}

	@Test
	public void testSelectPayOutsourceAcceptanceByExample() {
		fail("Not yet implemented");
	}

	@Test
	public void testSelectPayOutsourceAcceptanceByPrimaryKey() {
		fail("Not yet implemented");
	}

	@Test
	public void testSelectPayAcceptanceOrderByExample() {
		UTbPayAcceptanceOrderExample example = new UTbPayAcceptanceOrderExample();
		UTbPayAcceptanceOrderExample.Criteria cr = example.createCriteria();
		cr.andMST_AP_NOEqualTo("SN20150115001");
		cr.andPO_NOEqualTo("0001");
//		List<TbPayAcceptanceOrderDTO> list = pay006Service.pageSelectPayAcceptanceOrderByExample(example);
//		Assert.assertNotNull(list);
//		Assert.assertTrue(list.size() > 0);
	}

}
