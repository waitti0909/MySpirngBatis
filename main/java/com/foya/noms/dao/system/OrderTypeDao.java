package com.foya.noms.dao.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbSysOrderTypeMapper;
import com.foya.dao.mybatis.mapper.UTbSysOrderTypeMapper;
import com.foya.dao.mybatis.model.TbSysOrderType;
import com.foya.dao.mybatis.model.TbSysOrderTypeExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.system.OrderTypeDTO;

@Repository
public class OrderTypeDao extends BaseDao {

	@Autowired
	private TbSysOrderTypeMapper mapper;
	
	@Autowired
	private UTbSysOrderTypeMapper uMapper;
	
	public TbSysOrderType findByPk(String orderTypeId) {
		return mapper.selectByPrimaryKey(orderTypeId);
	}
	
	public List<TbSysOrderType> findByConditions(TbSysOrderTypeExample example) {
		return mapper.selectByExample(example);
	}
	
	/**
	 * 用workTypeId查詢OrderType
	 * @param workTypeId
	 * @return
	 */
	public List<OrderTypeDTO> findOrderTypeByWorkTypeId(String workTypeId, Integer odrSeq){
		Map<String, String> map = new HashMap<>();
		map.put("workTypeId", workTypeId);
		if (odrSeq != null) map.put("odrSeq", odrSeq.toString());
		return uMapper.selectOrderTypeByWorkTypeId(map);
	}

	public List<OrderTypeDTO> findAllOrderTypesForBuild() {
		return uMapper.selectOrderTypesForBuild();
	}
}
