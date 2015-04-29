package com.foya.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

import com.foya.noms.dto.system.OrderTypeDTO;

public interface UTbSysOrderTypeMapper {
    
	List<OrderTypeDTO> selectOrderByWorkType(String wkTypeId);
	/**
	 * 用workTypeId查詢OrderType
	 * @param workTypeId
	 * @return
	 */
	List<OrderTypeDTO> selectOrderTypeByWorkTypeId(Map<String, String> map);
	
	List<OrderTypeDTO> selectOrderTypesForBuild();
}