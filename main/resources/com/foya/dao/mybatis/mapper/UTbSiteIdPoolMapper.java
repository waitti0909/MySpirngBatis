package com.foya.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

import com.foya.noms.dto.st.SiteIdPoolDTO;

public interface UTbSiteIdPoolMapper {
	/**
	 * 查詢siteIdPool
	 * @param map
	 * @return
	 */
   List<SiteIdPoolDTO> selectUnuseBTSSiteId(Map<String ,String> map);
}