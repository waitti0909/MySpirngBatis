package com.foya.dao.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.foya.dao.mybatis.model.TbLsResExch;
import com.foya.noms.dto.ls.TbLsResExchDTO;

public interface UTbLsResExchMapper extends TbLsResExchMapper {

	List<TbLsResExchDTO> loadTbLsResExchList(@Param("lsNo") String lsNo, @Param("lsVer") String lsVer,@Param("locId") String locId);

	List<TbLsResExch> getLsResExchByLsNoVer(@Param("lsNo") String lsNo, @Param("lsVer") String lsVer,@Param("locId") String locId);
	
	List<TbLsResExchDTO> loadTbLsResExchAddedList(@Param("appSeq") String appSeq,@Param("locId") String locId);
	
	TbLsResExchDTO getMaxLsVer(@Param("lsNo") String lsNo);
}
