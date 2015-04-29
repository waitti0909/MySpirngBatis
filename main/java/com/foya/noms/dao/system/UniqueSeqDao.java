package com.foya.noms.dao.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbSysUniqueSeqMapper;
import com.foya.dao.mybatis.model.TbSysUniqueSeq;
import com.foya.dao.mybatis.model.TbSysUniqueSeqKey;
import com.foya.noms.dao.BaseDao;

@Repository
public class UniqueSeqDao extends BaseDao {

	@Autowired
	private TbSysUniqueSeqMapper mapper;
	
	public TbSysUniqueSeq findByKey(TbSysUniqueSeqKey key) {
		return mapper.selectByPrimaryKey(key);
	}
	
	private int insert(TbSysUniqueSeq record) {
		return mapper.insert(record);
	}
	
	private int update(TbSysUniqueSeq record) {
		return mapper.updateByPrimaryKey(record);
	}
	
	@SuppressWarnings("rawtypes")
	public synchronized Integer getNextSequence(Enum uniqueSeqType, String prev) {
		TbSysUniqueSeqKey key = new TbSysUniqueSeqKey();
		key.setSEQ_TYPE(uniqueSeqType.name());
		key.setSEQ_PREV(prev);
		TbSysUniqueSeq record = findByKey(key);
		
		if (record == null || record.getSEQ_NUM() == null) {
			record = new TbSysUniqueSeq();
			record.setSEQ_TYPE(uniqueSeqType.name());
			record.setSEQ_PREV(prev);
			record.setSEQ_NUM(1);
			insert(record);
		} else {
			record.setSEQ_NUM(record.getSEQ_NUM() + 1);
			update(record);
		}
		
		return record.getSEQ_NUM();
	}
}
