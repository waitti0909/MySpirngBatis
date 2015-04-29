package com.foya.noms.dao.inv;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbSysLookupMapper;
import com.foya.dao.mybatis.mapper.UTbInvAssetMapper;
import com.foya.dao.mybatis.mapper.UTbInvInvMapper;
import com.foya.dao.mybatis.mapper.UTbInvOnhandMapper;
import com.foya.dao.mybatis.mapper.UTbInvSrlMapper;
import com.foya.dao.mybatis.mapper.UTbInvWarehouseMapper;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.dao.mybatis.model.TbSysLookupExample;
import com.foya.dao.mybatis.model.UTbInvAssetExample;
import com.foya.dao.mybatis.model.UTbInvInvExample;
import com.foya.dao.mybatis.model.UTbInvOnhandExample;
import com.foya.dao.mybatis.model.UTbInvSrlExample;
import com.foya.dao.mybatis.model.UTbInvWarehouseExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.inv.TbInvAssetDTO;
import com.foya.noms.dto.inv.TbInvInvDTO;
import com.foya.noms.dto.inv.TbInvOnhandDTO;
import com.foya.noms.dto.inv.TbInvSrlDTO;
import com.foya.noms.dto.inv.TbInvWarehouseDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@Repository
public class INV021Dao extends BaseDao {

	@Autowired
	private TbSysLookupMapper tbSysLookupMapper;

	@Autowired
	private UTbInvWarehouseMapper uTbInvWarehouseMapper;

	@Autowired
	private UTbInvAssetMapper uTbInvAssetMapper;
	
	@Autowired
	private UTbInvInvMapper uTbInvInvMapper;

	@Autowired
	private UTbInvOnhandMapper uTbInvOnhandMapper;

	@Autowired
	private UTbInvSrlMapper uTbInvSrlMapper;

	/**
	 * 取得參數資料
	 * @param example
	 * @return
	 */
	public List<TbSysLookup> selectSysLookupByExample(TbSysLookupExample example) {
		return tbSysLookupMapper.selectByExample(example);
	}

	/**
	 * 取得倉庫清單
	 * @param example
	 * @return
	 */
	public List<TbInvWarehouseDTO> selectInvWarehouseByExample(UTbInvWarehouseExample example) {
		return uTbInvWarehouseMapper.selectByExample(example);
	}

	/**
	 * 查詢資產分布資料(站點)
	 * @param example
	 * @return
	 */
	public List<TbInvAssetDTO> selectInvAssetByExample(UTbInvAssetExample example) {
		return uTbInvAssetMapper.selectByExample(example);
	}

	/**
	 * 查詢資產分布資料(庫存)
	 * @param example
	 * @param maxRowNum 限制筆數
	 * @return
	 */
	public List<TbInvInvDTO> selectInvInvByExampleForLimit(UTbInvInvExample example, int maxRowNum) {
		PageBounds pageBounds = new PageBounds(1, maxRowNum);
		List<TbInvInvDTO> list = this.sqlSession.selectList(
						"com.foya.dao.mybatis.mapper.UTbInvInvMapper.selectByExample",
						example, pageBounds);
		return list;
	}

	/**
	 * 查詢資產分布資料(在途)
	 * @param example
	 * @return
	 */
	public List<TbInvOnhandDTO> selectInvOnhandByExample(UTbInvOnhandExample example) {
		return uTbInvOnhandMapper.selectByExample(example);
	}

	/**
	 * 查詢序號資料
	 * @param example
	 * @return
	 */
	public List<TbInvSrlDTO> selectInvSrlByExample(UTbInvSrlExample example) {
		return uTbInvSrlMapper.selectByExample(example);
	}
}
