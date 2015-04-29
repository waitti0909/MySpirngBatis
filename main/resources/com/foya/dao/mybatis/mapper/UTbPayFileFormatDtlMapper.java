package com.foya.dao.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.foya.dao.mybatis.model.TbPayFileFormatDtl;
import com.foya.noms.dto.pay.TbPayFileFormatDtlDTO;

public interface UTbPayFileFormatDtlMapper {

	/**
	 * 取的業者檔案格式
	 * 
	 * @param mstSeqNbr
	 *            業者代碼
	 * 
	 * @return List<TbPayFileFormatDtlDTO>
	 */
	List<TbPayFileFormatDtlDTO> getFileFormatDtlData(@Param("mstSeqNbr") String mstSeqNbr);

	List<TbPayFileFormatDtl> getParsingRuleList();
}
