package com.foya.noms.dao.profile;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.UTodoOrdersMapper;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.profile.TodoOrdersDTO;

@Repository
public class TodoOrdersDao extends BaseDao {

	@Autowired
	private UTodoOrdersMapper mapper;
	
	public List<TodoOrdersDTO> selectOR03orders(Map<String, String> map) {
		return mapper.selectOR03orders(map);
	}
	
	public List<TodoOrdersDTO> selectOR04orders(Map<String, String> map) {
		return mapper.selectOR04orders(map);
	}
	
	public List<TodoOrdersDTO> selectOS04orders(Map<String, String> map) {
		return mapper.selectOS04orders(map);
	}
	
	public List<TodoOrdersDTO> selectLA04LineApply(Map<String, String> map) {
		return mapper.selectLA04LineApply(map);
	}
}
