package com.foya.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.foya.dao.mybatis.model.TbLsReward;
import com.foya.noms.dto.ls.TbLsRewardDTO;

public interface UTbLsRewardMapper {

	public List<TbLsRewardDTO> getLsRewardByLsNoMaxVer(Map<String, String>  dataObj);

	public List<TbLsReward> getLsRewardByLsNoVer(Map<String, String>  dataObj);
	
	public List<TbLsReward> getLsRewardAddedByAppSeq(@Param("appSeq")String appSeq, @Param("rewardType")String rewardType);
	
	public List<TbLsReward> selectMaxVerbyLsNo(@Param("lsNo")String lsNo);
}
