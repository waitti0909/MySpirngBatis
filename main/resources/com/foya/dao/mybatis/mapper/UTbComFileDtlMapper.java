package com.foya.dao.mybatis.mapper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.foya.noms.dto.common.FileDtlDTO;

public interface UTbComFileDtlMapper {
	
	List<FileDtlDTO> findByMapCondition(HashMap<String, String> condition);
	
	List<FileDtlDTO> findByDocNo(List<String> docNos);
	
	String findFullPathByFileId(@Param("fileId") BigDecimal fileId);
	
}
