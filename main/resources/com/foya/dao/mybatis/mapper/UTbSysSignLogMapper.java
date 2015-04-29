package com.foya.dao.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.foya.noms.dto.system.SignLogDTO;

public interface UTbSysSignLogMapper {

	List<SignLogDTO> selectSignHistory(@Param("processTypes") List<String> processTypes, @Param("applyNo") String applyNo);
}
