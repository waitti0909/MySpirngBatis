package com.foya.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

import com.foya.noms.dto.profile.TodoOrdersDTO;

public interface UTodoOrdersMapper {

	public List<TodoOrdersDTO> selectOR03orders(Map<String, String> map);
	
	public List<TodoOrdersDTO> selectOR04orders(Map<String, String> map);

	public List<TodoOrdersDTO> selectOS04orders(Map<String, String> map);

	public List<TodoOrdersDTO> selectLA04LineApply(Map<String, String> map);
}
