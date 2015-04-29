package com.foya.dao.mybatis.mapper;

import java.util.List;

import com.foya.noms.dto.basic.PoLineIdMapDTO;

public interface UTbComPoLineIdMapMapper extends TbComPoLineIdMapMapper {

	List<PoLineIdMapDTO> selectPoLineIdMapByPoNo(String poNo);
}
