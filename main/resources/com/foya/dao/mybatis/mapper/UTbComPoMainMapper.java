package com.foya.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

import com.foya.dao.mybatis.model.TbComPoMainExample;
import com.foya.noms.dto.common.PoMainDTO;

public interface UTbComPoMainMapper {

	List<PoMainDTO> searchPoByCond(Map<String ,String>map);
	
	List<PoMainDTO> selectTbComPoMain(Map<String ,String>map);
	
	List<PoMainDTO> selectTbComPoMainByPoId(TbComPoMainExample poMainExample);

	int updateApplicationCancelByTbComPoMain(Map<String, String> map);
	
	List<PoMainDTO> selectMinYear();
	
}