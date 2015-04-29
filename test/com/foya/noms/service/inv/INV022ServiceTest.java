package com.foya.noms.service.inv;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.UTbInvSiteTxnExample;
import com.foya.noms.GenericTest;
import com.foya.noms.dto.inv.TbInvMaterialDTO;
import com.foya.noms.dto.inv.TbInvSiteTxnDTO;
/**
 * 料號查詢之Service Junit
 */
public class INV022ServiceTest extends GenericTest {

	@Autowired
	private INV022Service inv022Service;

	@Test
	public void testSearch() {
//		Map<String,String> dataObj = new HashMap<String,String>();
//		dataObj.put("catgCode1", "31");
//		dataObj.put("catgCode2", "001");
//		dataObj.put("catgCode3", "002");
//		dataObj.put("matNo", "M003");
//		
//		List<TbInvMaterialDTO> recordList = inv022Service.search(dataObj);		
//		Assert.assertNotNull(recordList);
//		Assert.assertTrue(recordList.size() > 0);
	}

}
