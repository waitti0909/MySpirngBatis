package com.foya.noms.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.hornetq.utils.json.JSONException;
import org.hornetq.utils.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParserUtilTest {
	
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	
	Map<String, Object> varMap = new HashMap<>();

	@Before
	public void makeData() {
		varMap.put("processName", "尋點申請");
		varMap.put("applyNo", "201501010001");
	}

	@Test
	public void testGetParseString() {
		String orginalStr = "${processName}待簽核通知 - ${applyNo}";
		String sourceString = "${processName}\n待簽核通知 - ${applyNo}";
		for (Map.Entry<String, Object> entry : varMap.entrySet()) {
			String key = String.valueOf(entry.getKey());
			String value = String.valueOf(entry.getValue()).replaceAll("\\$", "\\\\\\$");
			sourceString = sourceString.replaceAll("\\$\\{" + key + "\\}", value);
		}
		log.debug("orginal str = " + orginalStr + ", after str = " + sourceString);
		Assert.assertNotEquals(orginalStr, sourceString);
	}

	@Test
	public void testParseJsonString() {
		String json = "{'key1':'value1','key2':''}";
		try {
			JSONObject jsonObj = new JSONObject(json);
			@SuppressWarnings("rawtypes")
			Iterator it = jsonObj.keys();
			while (it.hasNext()) {
				Object keyObj = it.next();
				String key = keyObj == null ? "" : keyObj.toString();
				Object valueObj = jsonObj.get(key);
				String value = valueObj == null ? "" : valueObj.toString();
				log.debug("key = " + key + ", value = " + value);
			}
		} catch (JSONException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testEscapeXmlString() {
		String sourceStr = "<王&>'\"";
		String targetStr = sourceStr.replaceAll("&", "&amp;").replaceAll("'", "&apos;")
				.replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		log.debug("src = " + sourceStr + ",target =" + targetStr);
	}
	@Test
	public void testGetDeptIdByDeptPosId(){
		String deptPosId = "410000__WF_DIV_MANAGER";
		String[] deptPosIdArray = deptPosId.split("__");
		String posId = null;
		if(deptPosIdArray.length>0){
			posId = deptPosIdArray[0];
		}
		Assert.assertEquals("410000", posId);
	}
}
