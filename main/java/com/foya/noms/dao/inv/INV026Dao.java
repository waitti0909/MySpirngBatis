package com.foya.noms.dao.inv;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.UTbInvSiteTxnMapper;
import com.foya.dao.mybatis.model.UTbInvSiteTxnExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.inv.TbInvSiteTxnDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@Repository
public class INV026Dao extends BaseDao {

	@Autowired
	private UTbInvSiteTxnMapper uTbInvSiteTxnMapper;

	/**
	 * 查詢資材歷程
	 * @param example
	 * @param maxRowNum 限制筆數
	 * @return
	 */
	public List<TbInvSiteTxnDTO> selectInvSiteTxnByExampleForLimit(UTbInvSiteTxnExample example, int maxRowNum) {
		PageBounds pageBounds = new PageBounds(1, maxRowNum);
		List<TbInvSiteTxnDTO> list = this.sqlSession.selectList(
						"com.foya.dao.mybatis.mapper.UTbInvSiteTxnMapper.selectByExample",
						example, pageBounds);
		return list;
	}
}
