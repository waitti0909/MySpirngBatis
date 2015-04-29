package com.foya.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.foya.dao.mybatis.model.TbSiteSearchExample;
import com.foya.noms.dto.st.SiteSearchDTO;

public interface UTbSiteSearchMapper {
	
	
	SiteSearchDTO selectByPk(@Param("srId") String srId,@Param("srSeq") String srSeq);
	
	/**
	 * 探勘記錄
	 * @param example
	 * @return
	 */
	List<SiteSearchDTO> selectByExample(TbSiteSearchExample example);
	
	/**
	 * SRSEQ BY 探勘記錄
	 * 利用探勘序號 srSeq 查詢 出 該筆資料 
	 * @param example
	 * @return
	 */
	List<SiteSearchDTO> selectSearchTable(Map<String, String> map);

	/**
	 * 探勘 更新
	 */
	int updateSearchRecord(Map<String, Object> map);
}