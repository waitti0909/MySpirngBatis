package com.foya.dao.mybatis.mapper;

import java.util.HashMap;
import java.util.List;

import com.foya.dao.mybatis.model.TbPayRepartition;
import com.foya.dao.mybatis.model.TbPayRepartitionExample;
import com.foya.noms.dto.pay.TbPayRepartitionDTO;

public interface UTbPayRepartitionMapper {
	
	/* 取得當日最大流水號 */
	TbPayRepartition selerctMaxSeq(HashMap<String, Object> appDate);
	
	List<TbPayRepartitionDTO> selectTbPayRepartitionByPay011(HashMap<String , Object> dataObj);

	List<TbPayRepartitionDTO> selectByExample(TbPayRepartitionExample example);
}