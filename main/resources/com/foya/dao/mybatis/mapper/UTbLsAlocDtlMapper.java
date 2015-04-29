package com.foya.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;
import com.foya.noms.dto.ls.SiteAlocDetailDTO;

public interface UTbLsAlocDtlMapper {

	

	/**
	 * 取的站台分攤比明細
	 * @param condition 
	 * @return SiteAlocDetailDTO
	 */
	List<SiteAlocDetailDTO> selectSiteAloc4New(Map<String, Object> record);
	
	/**
	 * 取得已存在資源互換中站台分攤比明細
	 * @param condition 
	 * @return SiteAlocDetailDTO
	 */
	List<SiteAlocDetailDTO> findExchSiteAlocExitByCond(Map<String, String> record);
	/**
	 * 取得已存在資源互換中站台分攤比明細
	 * @param condition 
	 * @return SiteAlocDetailDTO
	 */
	List<SiteAlocDetailDTO> findExchSiteAlocAddedExitByCond(Map<String, String> record);
}