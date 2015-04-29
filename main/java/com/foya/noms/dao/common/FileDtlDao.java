package com.foya.noms.dao.common;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbComFileDtlMapper;
import com.foya.dao.mybatis.mapper.UTbComFileDtlMapper;
import com.foya.dao.mybatis.model.TbComFileDtl;
import com.foya.dao.mybatis.model.TbComFileDtlExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.common.FileDtlDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Repository
public class FileDtlDao extends BaseDao {

	@Autowired
	private TbComFileDtlMapper mapper;
	
	@Autowired
	private UTbComFileDtlMapper uMapper;
	
	public TbComFileDtl findByPk(BigDecimal FILE_DTL_SEQ_ID) {
		return mapper.selectByPrimaryKey(FILE_DTL_SEQ_ID);
	}
	
	public List<TbComFileDtl> findByCondition(TbComFileDtlExample example) {
		return mapper.selectByExample(example);
	}
	
	public int insert(TbComFileDtl record) {
		return mapper.insertSelective(record);
	}
	
	public int update(TbComFileDtl record) {
		return mapper.updateByPrimaryKeySelective(record);
	}
	
	public int update(TbComFileDtl record, TbComFileDtlExample example) {
		return mapper.updateByExampleSelective(record, example);
	}
	
	public int delete(BigDecimal FILE_DTL_SEQ_ID) {
		return mapper.deleteByPrimaryKey(FILE_DTL_SEQ_ID);
	}
	
	public int delete(TbComFileDtlExample example) {
		return mapper.deleteByExample(example);
	}
	
	public List<FileDtlDTO> findByMapCondition(HashMap<String, String> condition) {
		PageBounds pageBounds = getPageBounds(condition.get("sort")+"."+condition.get("order"));
		List<FileDtlDTO> list =this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbComFileDtlMapper.findByMapCondition",condition, pageBounds);
		PageList<FileDtlDTO> pageList = (PageList<FileDtlDTO>) list;
		log.debug("totaql="+pageList.getPaginator().getTotalCount());
		return pageList;
	}
	
	public List<FileDtlDTO> findByDocNo(HashMap<String, String> condition, List<String> docNos) {
		PageBounds pageBounds = getPageBounds(condition.get("sort")+"."+condition.get("order"));
		List<FileDtlDTO> list =this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbComFileDtlMapper.findByDocNo", docNos, pageBounds);
		PageList<FileDtlDTO> pageList = (PageList<FileDtlDTO>) list;
		log.debug("totaql="+pageList.getPaginator().getTotalCount());
		return pageList;
	}
	
	public String findFullPathByFileId(BigDecimal fileId) {
		return uMapper.findFullPathByFileId(fileId);
	}
}
