package com.foya.noms.dao.inv;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbInvCategoryMapper;
import com.foya.dao.mybatis.mapper.UTbInvMaterialOptMapper;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.inv.TbInvMaterialOptDTO;
import com.foya.noms.dto.inv.TbInvTxnDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Repository
public class INV023Dao extends BaseDao {

	@Autowired
	private TbInvCategoryMapper tbInvCategoryMapper;

	@Autowired
	private UTbInvMaterialOptMapper uMaterialOptMapper;
	
	public List<TbInvMaterialOptDTO> search(HashMap<String, String> dataObj) {
		PageBounds pageBounds = getPageBounds("OPT_ID");

		List<TbInvMaterialOptDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbInvMaterialOptMapper.searchJoinMrD", dataObj,
				pageBounds);

		PageList<TbInvMaterialOptDTO> pageList = (PageList<TbInvMaterialOptDTO>) list;

		return pageList;
	}

	/**
	 * 查詢 detail資料
	 * 
	 * @param dataObj
	 * @return
	 */
	public List<TbInvTxnDTO> searchDetail(HashMap<String, String> dataObj) {
		PageBounds pageBounds = getPageBounds("txn_no");

		List<TbInvTxnDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbInvTxnMapper.searchInvTxnJoinSiteTxn", dataObj,
				pageBounds);

		PageList<TbInvTxnDTO> pageList = (PageList<TbInvTxnDTO>) list;

		return pageList;
	}

	/**
	 * 取得料號
	 * 
	 * @param example
	 * @return
	 */
	/*
	 * public List<TbInvCategory> selectCategory(TbInvCategoryExample example) {
	 * PageBounds pageBounds = getPageBounds(); List<TbInvCategory> list =
	 * this.sqlSession.selectList(
	 * "com.foya.dao.mybatis.mapper.TbInvCategoryMapper.selectByExample",
	 * example, pageBounds);
	 * 
	 * PageList<TbInvCategory> pageList = (PageList<TbInvCategory>) list; return
	 * pageList; }
	 */

	public List<TbInvMaterialOptDTO> getMaterialOptDtoData(HashMap<String, Object> dataObj) {

		return uMaterialOptMapper.searchJoinMrD(dataObj);
	}

}
