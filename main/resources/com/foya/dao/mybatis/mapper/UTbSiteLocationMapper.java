package com.foya.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

import com.foya.noms.dto.st.SiteLocationDTO;


public interface UTbSiteLocationMapper {
	
	/**
	 * 查詢siteLocation
	 * @param map
	 * @return
	 */
	List<SiteLocationDTO> selectCondition(Map<String ,String> map);
	/**
	 * 
	 * @param locationId
	 * @return
	 */
	SiteLocationDTO selectByPrimaryKey(String locationId);
	
	/**
	 * 機房查詢
	 * @param map
	 * @return
	 */
	List<SiteLocationDTO> selectByCondition(Map<String ,String> map);

}