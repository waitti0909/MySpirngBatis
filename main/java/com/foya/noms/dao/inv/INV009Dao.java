package com.foya.noms.dao.inv;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbInvInvMapper;
import com.foya.dao.mybatis.mapper.TbInvSrlMapper;
import com.foya.dao.mybatis.mapper.TbInvTrActMapper;
import com.foya.dao.mybatis.mapper.TbInvTrMapper;
import com.foya.dao.mybatis.mapper.TbInvTxnMapper;
import com.foya.dao.mybatis.mapper.UTbInvBookingMapper;
import com.foya.dao.mybatis.mapper.UTbInvInvMapper;
import com.foya.dao.mybatis.mapper.UTbInvOnhandMapper;
import com.foya.dao.mybatis.mapper.UTbInvSrlMapper;
import com.foya.dao.mybatis.mapper.UTbInvTrActMapper;
import com.foya.dao.mybatis.model.TbInvInv;
import com.foya.dao.mybatis.model.TbInvSrl;
import com.foya.dao.mybatis.model.TbInvSrlExample;
import com.foya.dao.mybatis.model.TbInvSrlExample.Criteria;
import com.foya.dao.mybatis.model.TbInvTr;
import com.foya.dao.mybatis.model.TbInvTrAct;
import com.foya.dao.mybatis.model.TbInvTxn;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.inv.TbInvTrActCompleteDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@Repository
public class INV009Dao extends BaseDao {

	@Autowired
	private UTbInvTrActMapper uInvTrActMapper;

	@Autowired
	private UTbInvInvMapper uTbInvInvMapper;

	@Autowired
	private TbInvSrlMapper invSrlMapper;

	@Autowired
	private UTbInvSrlMapper uTbInvSrlMapper;

	@Autowired
	private UTbInvOnhandMapper uTbInvOnhandMapper;

	@Autowired
	private TbInvTxnMapper invTxnMapper;

	@Autowired
	private TbInvTrActMapper invTrActMapper;

	@Autowired
	private TbInvTrMapper invTrMapper;

	@Autowired
	private TbInvInvMapper invInvMapper;
	
	@Autowired
	private UTbInvBookingMapper uTbInvBookingMapper;
	
	// /**
	// * 查詢調撥紀錄檔
	// *
	// * @param trNo
	// * 調撥申請單號
	// * @param matNo
	// * 料號代碼
	// * @param srlNo
	// * 序號控管ID
	// * @param trIn
	// * 是否調入(0:否，1:是)
	// *
	// * @return List<TbInvTrAct>
	// */
	// public List<TbInvTrActCompleteDTO> getInvTrActDtoData(String trNo,
	// String matNo, Integer srlNo, String trIn) {
	//
	// HashMap<String, Object> map = new HashMap<String, Object>();
	// map.put("trNo", trNo);
	// map.put("matNo", matNo);
	// map.put("srlNo", srlNo);
	// map.put("trIn", trIn);
	// map.put("trDtlNo", 0);
	//
	// PageBounds pageBounds = getPageBounds("");
	// List<TbInvTrActCompleteDTO> list = this.sqlSession
	// .selectList(
	// "com.foya.dao.mybatis.mapper.UTbInvTrActMapper.getInvTrActDtoData",
	// map, pageBounds);
	//
	// for (TbInvTrActCompleteDTO trActDto : list) {
	//
	// Integer callInNumber = uInvTrActMapper.getActExportNumber(
	// trActDto.getTr_no(), trActDto.getMat_no(), 1,
	// trActDto.getTr_dtl_no());
	//
	// // 搜尋已調入數
	// trActDto.setCallInNumber(callInNumber == null ? 0 : callInNumber);
	//
	// // 搜尋調入歷程
	// trActDto.setTrActDtoHistoryList(getInvTrActDtoData(
	// trActDto.getTr_no(), trActDto.getMat_no(), null, "1",
	// trActDto.getTr_dtl_no()));
	//
	// }
	//
	// return list;
	// }

	/**
	 * 查詢調撥資料
	 * 
	 * @param trNo
	 *            調撥申請單號
	 * 
	 * @return List<TbInvTrAct>
	 */
	public List<TbInvTrActCompleteDTO> getInvTrExportDtlData(String trNo) {

		return uInvTrActMapper.getInvTrExportDtlData(trNo);
	}

	/**
	 * 查詢調撥數量
	 * 
	 * @param trNo
	 *            調撥申請單號
	 * 
	 * @return List<TbInvTrAct>
	 */
	public List<TbInvTrActCompleteDTO> getApplyTotal(String trNo) {

		return uInvTrActMapper.getApplyTotal(trNo);
	}

	/**
	 * 查詢調出歷程
	 * 
	 * @param trNo
	 *            調撥申請單號
	 * @param matNo
	 *            料號代碼
	 * @param srlNo
	 *            序號控管ID
	 * @param trIn
	 *            是否調入(0:否，1:是)
	 * 
	 * @return List<TbInvTrAct>
	 */
	public List<TbInvTrActCompleteDTO> getInvTrActDtoData(String trNo, String matNo, Integer srlNo, String trIn, long trDtlNo) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("trNo", trNo);
		map.put("matNo", matNo);
		map.put("srlNo", srlNo);
		map.put("trIn", trIn);
		map.put("trDtlNo", trDtlNo);

		PageBounds pageBounds = getPageBounds("");
		List<TbInvTrActCompleteDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbInvTrActMapper.getInvTrActDtoData", map,
				pageBounds);

		return list;
	}

	/**
	 * 
	 * @param stockType
	 *            物料狀態
	 * @param matNo
	 *            料號
	 * @param whId
	 *            倉庫代碼
	 * @param stdQty
	 *            StdQty 數量
	 * @param wrdQty
	 *            wrdQty 數量
	 * @param wspQty
	 *            wspQty 數量
	 * @return int
	 */
	public boolean updateStockByCallIn(int stockType, String matNo, String whId, int presentExportSum) {

		return uTbInvInvMapper.updateStockByCallIn(stockType, matNo, whId, presentExportSum) == 0 ? false : true;
	}

	/**
	 * 更新INV_SRL
	 * 
	 * @param tbInvSrl
	 *            TbInvSrl
	 * @return boolean
	 */
	public boolean updateInvSrlByKey(TbInvSrl tbInvSrl) {

		return invSrlMapper.updateByPrimaryKey(tbInvSrl) == 0 ? false : true;
	}

	/**
	 * 更新在途檔
	 * 
	 * @param onhandQty
	 *            在途數量
	 * @param nowTime
	 *            現在時間
	 * @param userNo
	 *            登入者
	 * @param trNo
	 *            單號
	 * @param matNo
	 *            料號
	 * @return boolean
	 */
	public boolean updateOnhandQty(int onhandQty, Date nowTime, String userNo, String trNo, String matNo, long trDtlNo) {

		return uTbInvOnhandMapper.updateOnhandQty(onhandQty, nowTime, userNo, trNo, matNo, trDtlNo) == 0 ? false : true;

	}

	/**
	 * 新增庫存主黨
	 * 
	 * @param invInv
	 *            invInv
	 */
	public boolean insertInvTxn(TbInvTxn invTxn) {

		return invTxnMapper.insertSelective(invTxn) == 0 ? false : true;
	}

	/**
	 * 新增庫存主黨
	 * 
	 * @param invInv
	 *            invInv
	 */
	public boolean insertInvInv(TbInvInv invInv) {

		return invInvMapper.insertSelective(invInv) == 0 ? false : true;
	}

	/**
	 * 新增調撥紀錄檔
	 * 
	 * @param invTrAct
	 *            TbInvTrAct
	 * @return boolean
	 */
	public boolean insertInvTrAct(TbInvTrAct invTrAct) {

		return invTrActMapper.insertSelective(invTrAct) == 0 ? false : true;
	}

	/**
	 * 更新調撥申請單
	 * 
	 * @param invTr
	 *            TbInvTr
	 * @return boolean
	 */
	public boolean updateTbInvTrByKey(TbInvTr invTr) {

		return invTrMapper.updateByPrimaryKeySelective(invTr) == 0 ? false : true;
	}

	/**
	 * 
	 * @return
	 */
	public int updateTbInvSrl(TbInvSrl tbInvSrl) {

		return uTbInvSrlMapper.updateTbInvSrl(tbInvSrl);
	}

}
