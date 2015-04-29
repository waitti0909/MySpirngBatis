package com.foya.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.foya.noms.dto.ls.LeaseDTO;

public interface UTbLsAppMapper extends TbLsAppMapper {

	List<LeaseDTO> searchByCond(Map<String, Object> map);
	
	LeaseDTO selectMaxVerForTbLsApp(@Param("appSeq")String appSeq);
}
