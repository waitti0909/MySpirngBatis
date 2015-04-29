package com.foya.noms.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hornetq.utils.json.JSONException;
import org.hornetq.utils.json.JSONObject;

public class ParserUtil {

	/**
	 * 取得 parser 後的字串
	 * 
	 * @param varMap 要 parser 的變數Map
	 * @param sourceString 要 parser 的字串
	 * @return parser 後的字串
	 */
	public static String getParseStringByMap(Map<String, Object> varMap, String sourceString) {
		if (sourceString == null) {
			return "";
		}
		if (varMap == null) {
			return sourceString;
		}
		for (Map.Entry<String, Object> entry : varMap.entrySet()) {
			String key = String.valueOf(entry.getKey());
			String value = escapeString(String.valueOf(entry.getValue()));
			sourceString = sourceString.replaceAll("\\$\\{" + key + "\\}", value);
		}
		return sourceString;
	}

	private static String escapeString(String str) {
		if (StringUtils.isBlank(str)) {
			return "";
		}
		return str.replaceAll("\\$", "\\\\\\$");
	}

	/**
	 * 取得 Map 透過 json 字串
	 * 
	 * @param json 字串
	 * @return map
	 * @throws JSONException the JSON exception
	 */
	public static Map<String, String> getJsonMapByJsonString(String jsonStr) throws JSONException {
		if (StringUtils.isBlank(jsonStr)) {
			return new HashMap<String, String>(0);
		}
		JSONObject jsonObj = new JSONObject(jsonStr);
		Map<String, String> jsonMap = new HashMap<>();
		@SuppressWarnings("rawtypes")
		Iterator it = jsonObj.keys();
		while (it.hasNext()) {
			Object keyObj = it.next();
			String key = keyObj == null ? "" : keyObj.toString();
			Object valueObj = jsonObj.get(key);
			String value = valueObj == null ? "" : valueObj.toString();
			jsonMap.put(key, value);
		}
		return jsonMap;
	}

	/**
	 * 取得 escape xml 後的字串
	 * 
	 * @param sourceStr
	 * @return escape xml 後的字串
	 */
	public static String getEscapeXmlString(String sourceStr) {
		if (StringUtils.isBlank(sourceStr)) {
			return "";
		}
		return sourceStr.replaceAll("&", "&amp;").replaceAll("'", "&apos;")
				.replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}

	public static String getDeptIdByDeptPosId(String deptPosId) {
		if (StringUtils.isBlank(deptPosId)) {
			return "";
		}
		String[] deptPosIdArray = deptPosId.split("__");
		if (deptPosIdArray.length > 0) {
			return deptPosIdArray[0];
		}
		return "";
	}

	public static String getPosIdByDeptPosId(String deptPosId) {
		if (StringUtils.isBlank(deptPosId)) {
			return "";
		}
		String[] deptPosIdArray = deptPosId.split("__");
		if (deptPosIdArray.length > 1) {
			return deptPosIdArray[1];
		}
		return "";
	}
}
