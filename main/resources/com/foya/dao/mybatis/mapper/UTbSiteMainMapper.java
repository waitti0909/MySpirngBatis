package com.foya.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.foya.noms.dto.st.SiteDTO;

public interface UTbSiteMainMapper {

	/**
	 * 	查詢全部Site
	 * @param map
	 * @return
	 */
	List<SiteDTO> selectWorkSiteList(Map<String ,String> map);
	
	/**
	 * 用locationId查詢Site
	 * @param locationId
	 * @return
	 */
	List<SiteDTO> selectSiteByLocationId(String locationId);
	
	/**
	 * 依siteId查詢基店
	 * @param siteId
	 * @return
	 */
	SiteDTO selectSiteBySiteId(String siteId);
	
	/**
	 *  用專線申請locationId查詢Site
	 * @param locId
	 * @return
	 */
	List<SiteDTO> selectSiteMainByLocid(String locId);
	
	
	/**
	 *  用locationId SiteStatus查詢
	 * @param locId
	 * @return
	 */
	List<SiteDTO> selectSiteMainByLocIdSiteStatus(@Param(value = "locId") String locId,@Param(value = "siteStatusList")String[] siteStatusList);
	
	
	List<SiteDTO> selectSiteMainByLocIdSiteIdStatus(@Param(value = "locId") String locId,@Param(value = "siteStatusList")String[] siteStatusList,@Param(value = "siteIdList")List<String> siteIdList);

	
	List<SiteDTO> selectSiteMainBySiteIdList(@Param(value = "siteIdList")String[] siteIdList);

	
	
	/**
	 * 依siteId查詢基店
	 * @param siteId
	 * @return
	 */
	SiteDTO selectSiteMainByBtsSiteId(String btsSiteId);
	
}