package com.foya.dao.mybatis.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.foya.noms.dto.ls.TbLsLocationAddedDTO;

public interface UTbLsLocationAddedMapper {

	/**
	 * 撈取 tb_Location_Added 資料
	 * @param dataObj
	 * @return
	 */
	List<TbLsLocationAddedDTO> getLsLocationAddedByAppSeq(HashMap<String, String > dataObj);
}
