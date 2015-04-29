package com.foya.dao.mybatis.mapper;

import java.util.HashMap;
import java.util.List;

import com.foya.noms.dto.pay.TbPayCheckDisregardDTO;


public interface UTbPayCheckDisregardMapper {
	
	List<TbPayCheckDisregardDTO> searchWithCnt(HashMap<String , Object> dataObj);
	
	TbPayCheckDisregardDTO selectDisregardCount(String disregardNo);
}