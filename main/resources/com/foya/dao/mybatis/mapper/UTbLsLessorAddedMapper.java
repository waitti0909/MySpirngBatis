package com.foya.dao.mybatis.mapper;

import java.util.HashMap;
import java.util.List;

import com.foya.dao.mybatis.model.TbLsLessor;
import com.foya.noms.dto.ls.LessorChDTO;

public interface UTbLsLessorAddedMapper {

	public List<LessorChDTO> searchLsLessorAddByAppSeqState(HashMap<String, String > dataObj);
	
	public List<TbLsLessor> searchLsLessorAddByAppSeq(HashMap<String, String > dataObj);
}
