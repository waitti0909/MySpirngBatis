package com.foya.dao.mybatis.mapper;

import java.util.HashMap;
import java.util.List;

import com.foya.noms.dto.ls.TbLsRewardAddedDTO;

public interface UTbLsRewardAddedMapper {

	/**
	 * 撈取 tb_Ls_Reward_Added 資料
	 * @param dataObj
	 * @return
	 */
	List<TbLsRewardAddedDTO> getLsRewardAddedByAppSeq(HashMap<String, String > dataObj);
}
