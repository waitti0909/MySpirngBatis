package com.foya.noms.dao.st;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbInvMaterialOptMapper;
import com.foya.dao.mybatis.model.TbInvMaterialOpt;
import com.foya.dao.mybatis.model.TbInvMaterialOptExample;
import com.foya.noms.dao.BaseDao;

@Repository
public class MaterialOptDao extends BaseDao {

	@Autowired
	private TbInvMaterialOptMapper mapper;
	
	public TbInvMaterialOpt findByPk(String optId) {
		return mapper.selectByPrimaryKey(optId);
	}
	/**
	 * 依照條件查詢 MaterialOpt
	 * @param example
	 * @return
	 */
	public List<TbInvMaterialOpt> findMaterialOptByConditions(TbInvMaterialOptExample example){
		return mapper.selectByExample(example);
	}
}
