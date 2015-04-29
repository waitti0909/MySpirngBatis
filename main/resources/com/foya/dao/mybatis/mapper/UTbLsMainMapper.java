package com.foya.dao.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.foya.dao.mybatis.model.TbLsMain;
import com.foya.noms.dto.ls.LeaseDomainMoneyDTO;
import com.foya.noms.dto.ls.LeaseMainDTO;

public interface UTbLsMainMapper {

	LeaseMainDTO getLsMainByNo(@Param("lsNo") String lsNo, @Param("lsVer") String lsVer);

	LeaseMainDTO getLsMainAddedByAppSeq(@Param("appSeq") String appSeq);
	
	List<TbLsMain> selectLsMainBylsNoVerMax(@Param("lsNo") String lsNo);
	List<TbLsMain> selectEffectivesMainBylsNoVerMax(@Param("lsNo") String lsNo);
	
	LeaseMainDTO getMaxEndDate(@Param("lsNo") String lsNo);
	
	List<TbLsMain> getContractValue(@Param("contractNo") String contractNo);
	
	List<LeaseDomainMoneyDTO> getLeaseAllRentPledgeByDomain(@Param("lsNo") String lsNo, @Param("lsVer") String lsVer);
}
