package com.foya.dao.mybatis.mapper;

import java.util.List;

import com.foya.dao.mybatis.model.TbSysLookup;

public interface UTbSysLookupMapper {
	
	//取得所有公告類型
	List<TbSysLookup> selectBulletinType();

}
