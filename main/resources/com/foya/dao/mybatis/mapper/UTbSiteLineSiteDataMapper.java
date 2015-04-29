package com.foya.dao.mybatis.mapper;

import java.util.List;

import com.foya.noms.dto.st.SiteLineSiteDataDTO;

public interface UTbSiteLineSiteDataMapper {
	/**
	 * 依照siteId查詢SiteLineSiteDate
	 * @param siteIdList
	 * @return
	 */
	List<SiteLineSiteDataDTO> selectSiteLineSiteDateBySiteId(List<String> siteIdList);
}