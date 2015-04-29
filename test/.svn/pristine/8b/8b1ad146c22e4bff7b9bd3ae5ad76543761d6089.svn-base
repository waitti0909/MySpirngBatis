package com.foya.noms.dao.inv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.UTbInvAssetExample;
import com.foya.dao.mybatis.model.UTbInvInvExample;
import com.foya.dao.mybatis.model.UTbInvOnhandExample;
import com.foya.dao.mybatis.model.UTbInvSrlExample;
import com.foya.dao.mybatis.model.UTbInvWarehouseExample;
import com.foya.noms.GenericTest;
import com.foya.noms.dto.inv.TbInvAssetDTO;
import com.foya.noms.dto.inv.TbInvInvDTO;
import com.foya.noms.dto.inv.TbInvOnhandDTO;
import com.foya.noms.dto.inv.TbInvSrlDTO;
import com.foya.noms.dto.inv.TbInvTxnDTO;
import com.foya.noms.dto.inv.TbInvWarehouseDTO;

public class INV020DaoTest extends GenericTest {

	@Autowired
	private INV020Dao inv020Dao;

	@Test
	public void tesQuery() {

		HashMap<String, String> dataObj=new HashMap();
		dataObj.put("tag_no","%M%" );
		dataObj.put("site_id",null);
		dataObj.put("mat_no","M001");
		dataObj.put("mat_name",null);
		List<TbInvSrlDTO> list =(List<TbInvSrlDTO>) inv020Dao.search(dataObj);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

	@Test
	public void testSearchDetail() {
		HashMap<String, String> dataObj=new HashMap();
		dataObj.put("srl_no", "2");
		dataObj.put("crStartDate", "2014/10/11");
		dataObj.put("crEndDate", "2014/12/01");
		
		List<TbInvTxnDTO> list =inv020Dao.searchDetail(dataObj);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

	

}
