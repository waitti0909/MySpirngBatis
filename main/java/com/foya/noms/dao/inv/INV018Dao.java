package com.foya.noms.dao.inv;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbInvTxnMapper;
import com.foya.dao.mybatis.mapper.TbOrgDeptMapper;
import com.foya.dao.mybatis.mapper.TbSysLookupMapper;
import com.foya.dao.mybatis.mapper.UTbInvBookingMapper;
import com.foya.dao.mybatis.mapper.UTbInvInvMapper;
import com.foya.dao.mybatis.mapper.UTbInvSrlMapper;
import com.foya.dao.mybatis.mapper.UTbInvStatTranMapper;
import com.foya.dao.mybatis.mapper.UTbInvWarehouseMapper;
import com.foya.dao.mybatis.model.TbInvTxn;
import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.dao.mybatis.model.TbSysLookupExample;
import com.foya.dao.mybatis.model.UTbInvBookingExample;
import com.foya.dao.mybatis.model.UTbInvInvExample;
import com.foya.dao.mybatis.model.UTbInvSrlExample;
import com.foya.dao.mybatis.model.UTbInvStatTranExample;
import com.foya.dao.mybatis.model.UTbInvWarehouseExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.inv.TbInvBookingDTO;
import com.foya.noms.dto.inv.TbInvInvDTO;
import com.foya.noms.dto.inv.TbInvSrlDTO;
import com.foya.noms.dto.inv.TbInvStatTranDTO;
import com.foya.noms.dto.inv.TbInvWarehouseDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Repository
public class INV018Dao extends BaseDao {

	@Autowired
	private UTbInvWarehouseMapper uTbInvWarehouseMapper;

	@Autowired
	private TbSysLookupMapper tbSysLookupMapper;

	@Autowired
	private UTbInvStatTranMapper uTbInvStatTranMapper;

	@Autowired
	private UTbInvBookingMapper uTbInvBookingMapper;

	@Autowired
	private UTbInvInvMapper uTbInvInvMapper;

	@Autowired
	private TbInvTxnMapper tbInvTxnMapper;

	@Autowired
	private UTbInvSrlMapper uTbInvSrlMapper;

	@Autowired
	private TbOrgDeptMapper tbOrgDeptMapper;

	/**
	 * 取得倉庫清單
	 * @param example
	 * @return
	 */
	public List<TbInvWarehouseDTO> selectInvWarehouseByExample(UTbInvWarehouseExample example) {
		return uTbInvWarehouseMapper.selectByExample(example);
	}

	/**
	 * 取得參數資料
	 * @param example
	 * @return
	 */
	public List<TbSysLookup> selectSysLookupByExample(TbSysLookupExample example) {
		return tbSysLookupMapper.selectByExample(example);
	}

	/**
	 * 查詢庫存狀態資料
	 * @param example
	 * @return
	 */
	public List<TbInvStatTranDTO> selectInvStatTranByExample(UTbInvStatTranExample example) {
		PageBounds pageBounds = getPageBounds();
		List<TbInvStatTranDTO> list = this.sqlSession.selectList(
						"com.foya.dao.mybatis.mapper.UTbInvStatTranMapper.selectByExample",
						example, pageBounds);

		PageList<TbInvStatTranDTO> pageList = (PageList<TbInvStatTranDTO>) list;
		return pageList;
	}

	/**
	 * 查詢庫存筆數
	 * @param example
	 * @return
	 */
	public int countInvStatTranByExample(UTbInvStatTranExample example) {
		return uTbInvStatTranMapper.countByExample(example);
	}

	/**
	 * 更新庫存狀態資料
	 * @param record
	 * @return
	 */
	public int updateInvStatTranByPrimaryKeySelective(TbInvStatTranDTO record) {
		return uTbInvStatTranMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 新增庫存狀態資料
	 * @param record
	 * @return
	 */
	public int insertInvStatTranSelective(TbInvStatTranDTO record) {
		return uTbInvStatTranMapper.insertSelective(record);
	}

	/**
	 * 查詢Booking資料
	 * @param example
	 * @return
	 */
	public List<TbInvBookingDTO> selectInvBookingByExample(UTbInvBookingExample example) {
		return uTbInvBookingMapper.selectByExample(example);
	}

	/**
	 * 更新Booking資料
	 * @param record
	 * @param example
	 * @return
	 */
	public int updateInvBookingByExampleSelective(TbInvBookingDTO record, UTbInvBookingExample example) {
		return uTbInvBookingMapper.updateByExampleSelective(record, example);
	}

	/**
	 * 新增Booking資料
	 * @param record
	 * @return
	 */
	public int insertInvBookingSelective(TbInvBookingDTO record) {
		return uTbInvBookingMapper.insertSelective(record);
	}

	/**
	 * 刪除Booking資料
	 * @param example
	 * @return
	 */
	public int deleteInvBookingByExample(UTbInvBookingExample example) {
		return uTbInvBookingMapper.deleteByExample(example);
	}

	/**
	 * 查詢資產分布資料(庫存)
	 * @param example
	 */
	public List<TbInvInvDTO> selectInvInvByExample(UTbInvInvExample example) {
		return uTbInvInvMapper.selectByExample(example);
	}

	/**
	 * 更新資產分布資料(庫存)
	 * @param record
	 * @param example
	 */
	public int updateInvInvByExampleSelective(TbInvInvDTO record, UTbInvInvExample example) {
		return uTbInvInvMapper.updateByExampleSelective(record, example);
	}

	/**
	 * 新增交易記錄檔
	 * @param record
	 */
	public int insertInvTxnSelective(TbInvTxn record) {
		return tbInvTxnMapper.insertSelective(record);
	}

	/**
	 * 查詢序號資料
	 * @param example
	 * @return
	 */
	public List<TbInvSrlDTO> selectInvSrlByExample(UTbInvSrlExample example) {
		return uTbInvSrlMapper.selectByExample(example);
	}

	/**
	 * 查詢部門資料
	 * @param deptId
	 * @return
	 */
	public TbOrgDept selectOrgDeptByPrimaryKey(String deptId) {
		return tbOrgDeptMapper.selectByPrimaryKey(deptId);
	}
}
