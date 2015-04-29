package com.foya.noms.dao.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbComPoMainMapper;
import com.foya.dao.mybatis.model.TbComPoMain;
import com.foya.dao.mybatis.model.TbComPoMainExample;
import com.foya.noms.dao.BaseDao;

@Repository
public class PoMainDao extends BaseDao {
	
	/** The tb com po main mapper. */
	@Autowired
	private TbComPoMainMapper tbComPoMainMapper;
	
	/**
	 * 
	 * @param poMainExample
	 * @return
	 * @author Charlie Woo
	 */
	public List<TbComPoMain> selectByExample(TbComPoMainExample poMainExample) {
		return tbComPoMainMapper.selectByExample(poMainExample);
	}
	
	/**
	 * 主鍵查詢 po單主檔.
	 * 
	 * @param poId
	 *            the po id
	 * @return the tb com po main
	 */
	public TbComPoMain selectByPrimaryKey(Long poId) {
		return tbComPoMainMapper.selectByPrimaryKey(poId);
	}
	
	/**
	 * 由主鍵更新 po單主檔.
	 * 
	 * @param tbComPoMain
	 *            the tb com po main
	 * @return the int
	 */
	public int updateByPrimaryKeySelective(TbComPoMain tbComPoMain) {
		return tbComPoMainMapper.updateByPrimaryKeySelective(tbComPoMain);
	}
	
	public TbComPoMain getTbComPoMain(Long po_ID) {
		return tbComPoMainMapper.selectByPrimaryKey(po_ID);
	}
	
}
