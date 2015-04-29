package com.foya.noms.dao.inv;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.TbInvLot;
import com.foya.dao.mybatis.model.UTbInvSiteTxnExample;
import com.foya.noms.GenericTest;
import com.foya.noms.dto.inv.TbInvMaterialDTO;
import com.foya.noms.dto.inv.TbInvSiteTxnDTO;
/**
 * 料號查詢Dao Junit
 */
public class INV022DaoTest extends GenericTest {

	@Autowired
	private INV022Dao inv022Dao;
	
	@Test
	public void testSearch() {
//		Map<String,String> dataObj = new HashMap<String,String>();
//		dataObj.put("catgCode1", "31");
//		dataObj.put("catgCode2", "001");
//		dataObj.put("catgCode3", "002");
//		dataObj.put("matNo", "M003");
//		List<TbInvMaterialDTO> recordList = inv022Dao.search(dataObj);
//		Assert.assertNotNull(recordList);
//		Assert.assertTrue(recordList.size() > 0);
	}
	
	
}
