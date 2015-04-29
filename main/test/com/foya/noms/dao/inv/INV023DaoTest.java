package com.foya.noms.dao.inv;

import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.noms.GenericTest;
import com.foya.noms.dto.inv.TbInvMaterialOptDTO;

public class INV023DaoTest extends GenericTest {

	@Autowired
	private INV023Dao inv023Dao;

	@Test
	public void tesQuery() {

		HashMap<String, String> dataObj = new HashMap<>();
		dataObj.put("wh_id", "12345678");
		dataObj.put("dept_id", "400000");
		dataObj.put("mat_no", "%M%");
		dataObj.put("psn_no", "P07083198");
		dataObj.put("reqStartDateStr", "2014/01/01");
		dataObj.put("reqEndDateStr", "2014/12/11");
		dataObj.put("appStartDateStr", "2014/01/01");
		dataObj.put("appEndDateStr", "2014/12/11");

		List<TbInvMaterialOptDTO> list = (List<TbInvMaterialOptDTO>) inv023Dao.search(dataObj);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

}
