package com.foya.noms.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlBuilder {

	private static final Logger log = LoggerFactory.getLogger(SqlBuilder.class);

	public static String getSqlByPK(String table, String pk,StringBuffer condition) {
		int size = PagerContext.getSize();
		int page = PagerContext.getPage();
		String order = PagerContext.getOrder();
		String sort = PagerContext.getSort();

		//log.debug("[LOG][sort]" + sort);
		//log.debug("[LOG][order]" + order);
		StringBuffer sb = new StringBuffer();

		sb.append(" SELECT TOP " + size * page);
		sb.append(" * FROM " + table);
		sb.append(" WHERE " + pk + " NOT IN (SELECT TOP  " + size * (page - 1));
		sb.append(" " + pk + " FROM " + table);
		if (StringUtils.isNotBlank(sort)) {
			sb.append(" ORDER BY " + sort + " " + order);
		}
		sb.append(" ) ");
		
		sb.append(condition);
		
		if (StringUtils.isNotBlank(sort)) {
			sb.append(" ORDER BY " + sort + " " + order + " ");
		}
		
		log.debug("[LOG][分頁sql]" + sb);

		return sb.toString();
	}

}
