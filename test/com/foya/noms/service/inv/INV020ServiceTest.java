package com.foya.noms.service.inv;

import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.noms.GenericTest;
import com.foya.noms.dto.inv.TbInvSrlDTO;
import com.foya.noms.dto.inv.TbInvTxnDTO;
import com.foya.noms.service.inv.INV020Service;

public class INV020ServiceTest extends GenericTest {

	@Autowired
	private INV020Service inv020Service;

	@Test
	public void tesQuery() {

		HashMap<String, String> dataObj = new HashMap<>();
		dataObj.put("tag_no", "%M%");
		dataObj.put("site_id", null);
		dataObj.put("mat_no", "SN0000009");
		dataObj.put("mat_name", null);
		List<TbInvSrlDTO> list = (List<TbInvSrlDTO>) inv020Service.search(dataObj);

		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

	@Test
	public void testSearchDetail() {
		HashMap<String, String> dataObj = new HashMap<>();
		dataObj.put("srl_no", "2");
		dataObj.put("crStartDate", "2014/10/11");
		dataObj.put("crEndDate", "2014/12/01");

		List<TbInvTxnDTO> list = inv020Service.searchDetail(dataObj);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

}
