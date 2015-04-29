package com.foya.dao.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.foya.noms.dto.common.TownDomainTeamDTO;


public interface UTbComTownDomainTeamMapper {
	/**
	 * 用cityId,townId查詢TbComTownDomainTeam
	 * @param cityId
	 * @param townId
	 * @return
	 */
	TownDomainTeamDTO getTownDomainTeamByCityIdTownId(@Param("cityId")String cityId, @Param("townId")String townId);
	
	
	List<TownDomainTeamDTO> getAllTownDomainTeam();
	
	
	
}