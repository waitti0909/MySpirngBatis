package com.foya.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

import com.foya.dao.mybatis.model.TbComItemMain;
import com.foya.noms.dto.basic.ItemMainDTO;


public interface UTbComItemMainMapper {
	List<ItemMainDTO>  selectItemMainItem(Map<String, String> map);
	
	ItemMainDTO  selectMainItem(Map<String, String> map);
	
	List<TbComItemMain>  selectItemMainItemByItemId(Map<String, String> map);
	
	List<ItemMainDTO> searchByCat(Map<String, String> map);
	
}
