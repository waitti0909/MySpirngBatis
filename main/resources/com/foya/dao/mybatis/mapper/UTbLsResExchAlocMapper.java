package com.foya.dao.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.foya.dao.mybatis.model.TbLsResExchAloc;

public interface UTbLsResExchAlocMapper {
  
	
	List<TbLsResExchAloc> getLsResExchAlocByLsNoVer(@Param("lsNo") String lsNo, @Param("lsVer") String lsVer,@Param("locId") String locId);
	
	TbLsResExchAloc getMaxVer(@Param("lsNo") String lsNo);
}