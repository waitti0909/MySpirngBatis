package com.foya.dao.mybatis.mapper;

import java.util.HashMap;
import java.util.List;

import com.foya.noms.dto.st.MeterialRtnDTO;

public interface UTbInvInsRemDMapper {
	
	List<MeterialRtnDTO> selectRemInsItemQuery(HashMap<String,Object>  dataObj);
	
	List<MeterialRtnDTO> selectRemInsItemByOptId(HashMap<String,Object>  dataObj);
	
	List<MeterialRtnDTO> selectRemItemQuery(HashMap<String,Object>  dataObj);
	
	List<MeterialRtnDTO> selectRemItemByOptId(HashMap<String,Object>  dataObj);
}
