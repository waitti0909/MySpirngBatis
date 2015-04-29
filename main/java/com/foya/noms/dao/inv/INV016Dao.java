package com.foya.noms.dao.inv;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbComCompanyMapper;
import com.foya.dao.mybatis.mapper.UTbInvWarehouseMapper;
import com.foya.dao.mybatis.model.TbComCompany;
import com.foya.dao.mybatis.model.TbComCompanyExample;
import com.foya.dao.mybatis.model.UTbInvWarehouseExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.inv.TbInvWarehouseDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Repository
public class INV016Dao extends BaseDao {

	@Autowired
	private UTbInvWarehouseMapper uTbInvWarehouseMapper;
	@Autowired
	private TbComCompanyMapper tbComCompanyMapper;

	/**
	 * 取得倉庫清單
	 * @param example
	 * @return
	 */
	public List<TbInvWarehouseDTO> selectInvWarehouseByExample(UTbInvWarehouseExample example) {
		return uTbInvWarehouseMapper.selectByExample(example);
	}

	/**
	 * 取得倉庫資料
	 * @param example
	 * @return
	 */
	public List<TbInvWarehouseDTO> selectWarehouse(UTbInvWarehouseExample example) {
		PageBounds pageBounds = getPageBounds();
		List<TbInvWarehouseDTO> list = this.sqlSession.selectList(
						"com.foya.dao.mybatis.mapper.UTbInvWarehouseMapper.selectByExample",
						example, pageBounds);

		PageList<TbInvWarehouseDTO> pageList = (PageList<TbInvWarehouseDTO>) list;
		return pageList;
	}

	/**
	 * 取得倉庫資料by key
	 * @param wh_id
	 * @return
	 */
	public TbInvWarehouseDTO selectWarehouseByPrimaryKey(String wh_id) {
		return uTbInvWarehouseMapper.selectByPrimaryKey(wh_id);
	}
	
	/**
	 * 新增倉庫資料
	 * @param record
	 * @return
	 */
	public int insertInvWarehouseSelective(TbInvWarehouseDTO record) {
		return uTbInvWarehouseMapper.insertSelective(record);
	}

	/**
	 * 修改倉庫資料
	 * @param record
	 * @return
	 */
	public int updateByPrimaryKeySelective(TbInvWarehouseDTO record) {
		return uTbInvWarehouseMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 取得廠商清單
	 * @param example
	 * @return
	 */
	public List<TbComCompany> selectComCompanyByExample(TbComCompanyExample example) {
		return tbComCompanyMapper.selectByExample(example);
	}

	/**
	 * 取得資料筆數
	 * @param example
	 */
	public int countInvWarehouseByExample(UTbInvWarehouseExample example) {
		return uTbInvWarehouseMapper.countByExample(example);
	}
}
