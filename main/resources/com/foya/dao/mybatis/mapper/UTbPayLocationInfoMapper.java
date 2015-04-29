package com.foya.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

import com.foya.noms.dto.ls.SiteAlocDetailDTO;
import com.foya.noms.dto.pay.TbPayLocationInfoDTO;

public interface UTbPayLocationInfoMapper {
	
	List<TbPayLocationInfoDTO> estimateReeData();

	List<SiteAlocDetailDTO> getLsSiteData(Map<String,Object> hashmap);
	
	List<SiteAlocDetailDTO> getLsApplyData(Map<String,Object> hashmap);
}