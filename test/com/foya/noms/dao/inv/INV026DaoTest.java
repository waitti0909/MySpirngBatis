package com.foya.noms.dao.inv;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.UTbInvSiteTxnExample;
import com.foya.noms.GenericTest;
import com.foya.noms.dto.inv.TbInvSiteTxnDTO;

public class INV026DaoTest extends GenericTest {

	@Autowired
	private INV026Dao inv026Dao;

	@Test
	public void testSelectInvSiteTxnByExample() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
		UTbInvSiteTxnExample example = new UTbInvSiteTxnExample();
		UTbInvSiteTxnExample.Criteria cr = example.createCriteria();
		cr.andSite_idLike("%T2014%");
		try {
			cr.andMd_timeBetween(sdf.parse("2014/11/21 00:00:00.000"), sdf.parse("2014/11/21 23:59:59.999"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<TbInvSiteTxnDTO> list = inv026Dao.selectInvSiteTxnByExampleForLimit(example, 10);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

}
