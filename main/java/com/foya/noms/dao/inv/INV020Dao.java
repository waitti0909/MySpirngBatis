package com.foya.noms.dao.inv;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbInvCategoryMapper;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.inv.TbInvSrlDTO;
import com.foya.noms.dto.inv.TbInvTxnDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;


@Repository
public class INV020Dao extends BaseDao {
	
	@Autowired
	private TbInvCategoryMapper tbInvCategoryMapper;

	public List<TbInvSrlDTO> search(HashMap<String,String> dataObj){
		PageBounds pageBounds = getPageBounds("srl_no");

		List<TbInvSrlDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbInvSrlMapper.searchInvSrlJoinMaterial", dataObj,  pageBounds);

		PageList<TbInvSrlDTO> pageList = (PageList<TbInvSrlDTO>) list;
		
		return pageList;		
	}
	/**
	 * 查詢 detail資料
	 * @param dataObj
	 * @return
	 */
	public List<TbInvTxnDTO> searchDetail(HashMap<String, String> dataObj) {
		PageBounds pageBounds = getPageBounds("txn_no");

		List<TbInvTxnDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbInvTxnMapper.searchInvTxnJoinSiteTxn", dataObj,  pageBounds);

		PageList<TbInvTxnDTO> pageList = (PageList<TbInvTxnDTO>) list;
		
		return pageList;	
	}

	
	/**
	 * 取得料號
	 * @param example
	 * @return
	 */
	/*
	public List<TbInvCategory> selectCategory(TbInvCategoryExample example) {
		PageBounds pageBounds = getPageBounds();
		List<TbInvCategory> list = this.sqlSession.selectList(
						"com.foya.dao.mybatis.mapper.TbInvCategoryMapper.selectByExample", example, pageBounds);

		PageList<TbInvCategory> pageList = (PageList<TbInvCategory>) list;
		return pageList;
	}
	*/

}
