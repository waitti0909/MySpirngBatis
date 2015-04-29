package com.foya.noms.dao.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbComTownDomainTeamMapper;
import com.foya.dao.mybatis.mapper.UTbComTownDomainTeamMapper;
import com.foya.dao.mybatis.model.TbComTownDomainTeam;
import com.foya.dao.mybatis.model.TbComTownDomainTeamExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.common.TownDomainTeamDTO;

/**
 * The Class TownDomainTeamDao.
 */
@Repository
public class TownDomainTeamDao extends BaseDao{

	@Autowired
	private TbComTownDomainTeamMapper mapper;
	/** The u tb com town domain tea mapper. */
	@Autowired
	private UTbComTownDomainTeamMapper uTbComTownDomainTeaMapper;
	
	public List<TbComTownDomainTeam> findByCondition(TbComTownDomainTeamExample example) {
		return mapper.selectByExample(example);
	}
	
	/**
	 * 用cityId,townId查詢TbComTownDomainTeam.
	 *
	 * @param cityId the city id
	 * @param townId the town id
	 * @return the town domain team by city id town id
	 */
	public TownDomainTeamDTO getTownDomainTeamByCityIdTownId(String cityId ,String townId){
		return uTbComTownDomainTeaMapper.getTownDomainTeamByCityIdTownId(cityId,townId);
	}
	
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<TownDomainTeamDTO>  getAll(){
	
		return uTbComTownDomainTeaMapper.getAllTownDomainTeam();
	}
	
	
	
}
