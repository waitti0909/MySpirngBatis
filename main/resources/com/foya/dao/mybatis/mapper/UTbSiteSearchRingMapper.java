package com.foya.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

import com.foya.noms.dto.st.SearchRingDTO;

public interface UTbSiteSearchRingMapper {
	/**
	 * 查詢所有search Ring
	 * @param map
	 * @return
	 */
   List<SearchRingDTO> selectSearchRingList(Map<String ,String> map);

}