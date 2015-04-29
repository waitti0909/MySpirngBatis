package com.foya.noms.dao.inv;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.UTbInvWarehouseMapper;
import com.foya.dao.mybatis.model.UTbInvBookingExample;
import com.foya.dao.mybatis.model.UTbInvInvExample;
import com.foya.dao.mybatis.model.UTbInvWarehouseExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.inv.TbInvBookingDTO;
import com.foya.noms.dto.inv.TbInvInvDTO;
import com.foya.noms.dto.inv.TbInvWarehouseDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Repository
public class INV024Dao extends BaseDao {

	@Autowired
	private UTbInvWarehouseMapper uTbInvWarehouseMapper;

	/**
	 * 取得倉庫清單
	 * @param example
	 * @return
	 */
	public List<TbInvWarehouseDTO> selectInvWarehouseByExample(UTbInvWarehouseExample example) {
		return uTbInvWarehouseMapper.selectByExample(example);
	}

	/**
	 * 取得Booking主檔資料
	 * 
	 * @param example
	 * @return
	 */
	public List<TbInvInvDTO> selectInvInvByExampleAndGroupBy(UTbInvInvExample example) {
		PageBounds pageBounds = getPageBounds();
		List<TbInvInvDTO> list = this.sqlSession.selectList(
				"com.foya.dao.mybatis.mapper.UTbInvInvMapper.selectByExampleAndGroupBy",
				example, pageBounds);

		PageList<TbInvInvDTO> pageList = (PageList<TbInvInvDTO>) list;
		return pageList;
	}

	/**
	 * 取得Booking明細資料
	 * 
	 * @param example
	 * @return
	 */
	public List<TbInvBookingDTO> selectInvBookingByExample(UTbInvBookingExample example) {
		PageBounds pageBounds = getPageBounds();
		List<TbInvBookingDTO> list = this.sqlSession.selectList(
						"com.foya.dao.mybatis.mapper.UTbInvBookingMapper.selectByExample",
						example, pageBounds);

		PageList<TbInvBookingDTO> pageList = (PageList<TbInvBookingDTO>) list;
		return pageList;
	}
}
