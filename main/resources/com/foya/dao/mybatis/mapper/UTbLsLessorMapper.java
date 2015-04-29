package com.foya.dao.mybatis.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.foya.dao.mybatis.model.TbLsLessor;
import com.foya.noms.dto.ls.LessorChDTO;

public interface UTbLsLessorMapper {
	
	List<TbLsLessor> searchLsLessorByNoVer(@Param("lsNo")String lsNo,@Param("lsVer")String lsVer);
	
	List<LessorChDTO> serarchLsLessorBylsNoLessorId(HashMap<String, String > dataObj);
	List<TbLsLessor> searchLsLessorAddedByAppSeq(@Param("appSeq")String appSeq);
}
