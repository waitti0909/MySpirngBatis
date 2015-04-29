/**
 * 日期：2014/11/14
 * 功能說明：調出作業與查詢DAO
 * 程式人員：Arvin
 */
package com.foya.noms.dao.inv;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbAuthMenuMapper;
import com.foya.dao.mybatis.mapper.TbInvInvMapper;
import com.foya.dao.mybatis.mapper.TbInvMaterialMapper;
import com.foya.dao.mybatis.mapper.TbInvOnhandMapper;
import com.foya.dao.mybatis.mapper.TbInvSrlMapper;
import com.foya.dao.mybatis.mapper.TbInvTrActMapper;
import com.foya.dao.mybatis.mapper.TbInvTrMapper;
import com.foya.dao.mybatis.mapper.TbInvTxnMapper;
import com.foya.dao.mybatis.mapper.TbInvWarehouseMapper;
import com.foya.dao.mybatis.mapper.TbOrgDeptMapper;
import com.foya.dao.mybatis.mapper.UTbInvBookingMapper;
import com.foya.dao.mybatis.mapper.UTbInvInvMapper;
import com.foya.dao.mybatis.mapper.UTbInvSrlMapper;
import com.foya.dao.mybatis.mapper.UTbInvTrActMapper;
import com.foya.dao.mybatis.mapper.UTbInvTrDtlMapper;
import com.foya.dao.mybatis.mapper.UTbInvTrMapper;
import com.foya.dao.mybatis.model.TbAuthMenu;
import com.foya.dao.mybatis.model.TbAuthMenuExample;
import com.foya.dao.mybatis.model.TbInvInv;
import com.foya.dao.mybatis.model.TbInvInvExample;
import com.foya.dao.mybatis.model.TbInvOnhand;
import com.foya.dao.mybatis.model.TbInvSrl;
import com.foya.dao.mybatis.model.TbInvSrlExample;
import com.foya.dao.mybatis.model.TbInvTr;
import com.foya.dao.mybatis.model.TbInvTrAct;
import com.foya.dao.mybatis.model.TbInvTrExample;
import com.foya.dao.mybatis.model.TbInvTxn;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.inv.TbInvTrActCompleteDTO;
import com.foya.noms.dto.inv.TbInvTrCompleteDTO;
import com.foya.noms.dto.inv.TbInvTrDtlCompleteDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Repository
public class INV008Dao extends BaseDao {

	@Autowired
	private TbInvWarehouseMapper warehouseMapper;

	@Autowired
	private TbOrgDeptMapper orgDeptMapper;

	@Autowired
	private UTbInvTrDtlMapper uTrDetailMapper;

	@Autowired
	private TbInvTxnMapper txnMapper;

	@Autowired
	private TbInvSrlMapper invSrlMapper;

	@Autowired
	private UTbInvSrlMapper uInvSrlMapper;

	@Autowired
	private TbInvTrActMapper trActMapper;

	@Autowired
	private TbInvOnhandMapper onHandMapper;

	@Autowired
	private TbInvInvMapper invInvMapper;

	@Autowired
	private UTbInvInvMapper uInvInvMapper;

	@Autowired
	private TbInvTrMapper invTrMapper;

	@Autowired
	private TbInvMaterialMapper materialMapper;

	@Autowired
	private UTbInvBookingMapper uInvBookingMapper;

	@Autowired
	private UTbInvTrActMapper uTbInvTrActMapper;
	
	@Autowired
	private UTbInvTrMapper uTbInvTrMapper;
	
	@Autowired
	private TbAuthMenuMapper tbAuthMenuMapper;
	
	public List<TbAuthMenu> getMenuId(String menuCode){
		TbAuthMenuExample example = new TbAuthMenuExample();
		
		example.createCriteria().andPGM_PATHEqualTo(menuCode);
		
		return tbAuthMenuMapper.selectByExample(example);
		
	}
	
	/**
	 * 取得調撥主檔By日期
	 * 
	 * @param StrDemandDate
	 *            需求日期 (開始)
	 * @param EndDemandDate
	 *            需求日期 (結束)
	 * @param applyNumber
	 *            調撥單號
	 * @param applyState
	 *            調撥進度
	 * @param outDepot
	 *            調出倉庫
	 * @param inDepot
	 *            調入倉庫
	 * @param demandDept
	 *            需求單位
	 * @param applyDept
	 *            申請單位
	 * @param applicant
	 *            申請人
	 * @return List<TbInvTr> Page model
	 */
	public List<TbInvTrCompleteDTO> getInvTrDataDtoByDate(HashMap<String, Object> map) {

		PageBounds pageBounds = getPageBounds("need_date");
		List<TbInvTrCompleteDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbInvTrMapper.getInvTrDataDtoDate", map, pageBounds);

		// 搜尋調入倉庫 And 申請單位 (因XML多重Table搜尋太過於複雜固獨立出來)
		// for (TbInvTrCompleteDTO completeDto : list) {
		//
		// TbInvWarehouseExample example = new TbInvWarehouseExample();
		// Criteria wareHouseCriteria = example.createCriteria();
		// wareHouseCriteria.andWh_idEqualTo(completeDto.getTr_in_wh_id());
		//
		// TbOrgDeptExample deptExaple = new TbOrgDeptExample();
		// deptExaple.createCriteria().andDEPT_IDEqualTo(completeDto.getApp_dept_id());
		//
		// List<TbInvWarehouse> warehouseList =
		// warehouseMapper.selectByExample(example);
		// List<TbOrgDept> orgDeptList =
		// orgDeptMapper.selectByExample(deptExaple);
		//
		// // 調入倉庫
		// if (warehouseList.size() > 0) {
		// completeDto.setInvWarehouseByeIn(warehouseList.get(0));
		// }
		//
		// // 申請單位
		// if (orgDeptList.size() > 0) {
		// completeDto.setTbOrgApplyDept(orgDeptList.get(0));
		// }
		//
		// }

		return (PageList<TbInvTrCompleteDTO>) list;
	}

	/**
	 * 取得調撥主檔By日期
	 * 
	 * @param StrDemandDate
	 *            需求日期 (開始)
	 * @param EndDemandDate
	 *            需求日期 (結束)
	 * @param applyNumber
	 *            調撥單號
	 * @param applyState
	 *            調撥進度
	 * @param outDepot
	 *            調出倉庫
	 * @param inDepot
	 *            調入倉庫
	 * @param demandDept
	 *            需求單位
	 * @param applyDept
	 *            申請單位
	 * @param applicant
	 *            申請人
	 * @return List<TbInvTr> Page model
	 */
	public List<TbInvTrCompleteDTO> getInvTrDataDtoForToDo(HashMap<String, Object> map) {

//		List<TbInvTrCompleteDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbInvTrMapper.getInvTrDataDtoDate", map, pageBounds);
		
		return uTbInvTrMapper.getInvTrDataDtoDate(map);
		// 搜尋調入倉庫 And 申請單位 (因XML多重Table搜尋太過於複雜固獨立出來)
		// for (TbInvTrCompleteDTO completeDto : list) {
		//
		// TbInvWarehouseExample example = new TbInvWarehouseExample();
		// Criteria wareHouseCriteria = example.createCriteria();
		// wareHouseCriteria.andWh_idEqualTo(completeDto.getTr_in_wh_id());
		//
		// TbOrgDeptExample deptExaple = new TbOrgDeptExample();
		// deptExaple.createCriteria().andDEPT_IDEqualTo(completeDto.getApp_dept_id());
		//
		// List<TbInvWarehouse> warehouseList =
		// warehouseMapper.selectByExample(example);
		// List<TbOrgDept> orgDeptList =
		// orgDeptMapper.selectByExample(deptExaple);
		//
		// // 調入倉庫
		// if (warehouseList.size() > 0) {
		// completeDto.setInvWarehouseByeIn(warehouseList.get(0));
		// }
		//
		// // 申請單位
		// if (orgDeptList.size() > 0) {
		// completeDto.setTbOrgApplyDept(orgDeptList.get(0));
		// }
		//
		// }

//		return (PageList<TbInvTrCompleteDTO>) list;
	}
	
	/**
	 * 取得調撥申請明細
	 * 
	 * @param trNo
	 *            單據編號
	 * @return List<TbInvTrDtl>
	 */
	public List<TbInvTrDtlCompleteDTO> getInvTrDtlDTo(String trNo, String whId) {

		List<TbInvTrDtlCompleteDTO> list = uTrDetailMapper.getInvTrExportDetailDto(trNo, whId);

		// PageBounds pageBounds = getPageBounds("");
		// List<TbInvTrDtlCompleteDTO> list = this.sqlSession.selectList(
		// "com.foya.dao.mybatis.mapper.UTbInvTrDtlMapper.getInvTrDetailDto",
		// map, pageBounds);

		// for (TbInvTrDtlCompleteDTO detailDto : list) {
		//
		// // MyBaits Join 問題，所以分開放入
		// TbInvTrExample invTrExample = new TbInvTrExample();
		// invTrExample.createCriteria().andTr_noEqualTo(detailDto.getTr_no());
		//
		// List<TbInvTr> invTrList = invTrMapper.selectByExample(invTrExample);
		//
		// if (invTrList.size() > 0) {
		// detailDto.setInvTr(invTrList.get(0));
		// } else {
		// list.remove(detailDto);
		// continue;
		// }
		//
		// TbInvMaterialExample materialExample = new TbInvMaterialExample();
		// materialExample.createCriteria().andMat_noEqualTo(detailDto.getMat_no());
		//
		// List<TbInvMaterial> materialList =
		// materialMapper.selectByExample(materialExample);
		//
		// if (materialList.size() > 0) {
		// detailDto.setMaterial(materialList.get(0));
		// } else {
		// materialList.remove(detailDto);
		// continue;
		// }
		//
		// // 搜尋BookIng數量
		// detailDto.setBookingNumber(getBookingNumber(detailDto.getMat_no(),
		// detailDto.getInvTr().getTr_out_wh_id()));
		//
		// // if(detailDto.getMaterial().getCtrl_type().equals("S")){
		// // 搜尋貼標號碼，因TB_INV_TR_DTL和TB_INV_SRL資料都無互相關聯關係，額外再多搜尋
		// detailDto.setInvSrlList(getTbInvSrlList(detailDto.getMat_no(),
		// detailDto.getInvTr().getTr_out_wh_id(), detailDto.getMat_status()));
		// // }
		//
		// // 搜尋調出歷程
		// detailDto.setExportHistoryTrActDtoList(getInvTrActDtoData(detailDto.getTr_no(),
		// detailDto.getMat_no(), null, "0",
		// detailDto.getTr_dtl_no()));
		//
		// // 搜尋調入歷程
		// detailDto.setCallInHistoryTrActDtoList(getInvTrActDtoData(detailDto.getTr_no(),
		// detailDto.getMat_no(), null, "1",
		// detailDto.getTr_dtl_no()));
		//
		// // 搜尋此筆清單紀錄是否有廠商序號 for 檢視畫面
		// detailDto.setInvSrl(getTbInvSrl(detailDto.getTr_no(),
		// detailDto.getMat_no(), detailDto.getTr_dtl_no()));
		//
		// }

		return list;
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
	 * 取的Booking數量
	 * 
	 * @param matNo
	 *            料號
	 * @param whId
	 *            調出倉ID
	 * @return booking_qty
	 */
	public int getBookingNumber(String matNo, String whId) {

		TbInvInvExample invExample = new TbInvInvExample();

		com.foya.dao.mybatis.model.TbInvInvExample.Criteria criteria = invExample.createCriteria();

		criteria.andMat_noEqualTo(matNo);
		criteria.andWh_idEqualTo(whId);

		List<TbInvInv> invList = invInvMapper.selectByExample(invExample);

		return invList.size() == 0 ? 0 : invList.get(0).getBooking_qty();
	}

	/**
	 * 更新調撥申請單
	 * 
	 * @param invTr
	 *            TbInvTr
	 * @return Int
	 */
	public boolean updateTbInvTr(TbInvTr invTr, String trNo) {

		TbInvTrExample example = new TbInvTrExample();
		example.createCriteria().andTr_noEqualTo(trNo);

		return invTrMapper.updateByExampleSelective(invTr, example) == 0 ? false : true;
	}

	/**
	 * 更新序號檔
	 * 
	 * @param invTr
	 *            TbInvTr
	 * @return Int
	 */
	public boolean updateTbInvSrl(TbInvSrl invSrl, long srlNo) {

		TbInvSrlExample example = new TbInvSrlExample();
		example.createCriteria().andSrl_noEqualTo(srlNo);

		return invSrlMapper.updateByExampleSelective(invSrl, example) == 0 ? false : true;
	}

	/**
	 * 
	 * @param actNo
	 *            調撥單號
	 * @param actType
	 *            異動單據類型
	 * @param matNo
	 *            料號
	 * @param whId
	 *            倉庫代碼
	 * @param presentExportSum
	 *            本次調出數
	 * @return boolean
	 */
	public boolean updateBookingQty(String actNo, String actType, String matNo, String whId, int presentExportSum) {

		return uInvBookingMapper.updateBookingQty(actNo, actType, matNo, whId, presentExportSum) == 0 ? false : true;
	}

	/**
	 * 
	 * @param stockType
	 *            物料狀態
	 * @param matNo
	 *            料號
	 * @param whId
	 *            倉庫代碼
	 * @param bookingQty
	 *            Booking 數量
	 * @param stdQty
	 *            StdQty 數量
	 * @param wrdQty
	 *            wrdQty 數量
	 * @param wspQty
	 *            wspQty 數量
	 * @return int
	 */
	public boolean updateStockByExport(int stockType, String matNo, String whId, int presentExportSum) {

		return uInvInvMapper.updateStockByExport(stockType, matNo, whId, presentExportSum) == 0 ? false : true;
	}

	/**
	 * 搜尋貼標號碼(序號檔)
	 * 
	 * @param matNo
	 *            料號
	 * @param whId
	 *            調出倉ID
	 * @return List<TbInvSrl>
	 */
	public List<TbInvSrl> getTbInvSrlList(String matNo, String whId, byte matStatus) {

		TbInvSrlExample srlExample = new TbInvSrlExample();

		com.foya.dao.mybatis.model.TbInvSrlExample.Criteria criteria = srlExample.createCriteria();

		criteria.andMat_noEqualTo(matNo);
		criteria.andWh_idEqualTo(whId);
		criteria.andMat_statusEqualTo(matStatus);

		return invSrlMapper.selectByExample(srlExample);

	}

	/**
	 * 取得標籤號碼 & 廠商序號
	 * 
	 * @param trNo
	 *            調撥單號
	 * @param matNo
	 *            料號
	 * @param dtlNo
	 *            調撥清單SeqId
	 * 
	 * @return List<TbInvSrl>
	 */
	public TbInvSrl getTbInvSrl(String trNo, String matNo, long dtlNo) {

		return uInvSrlMapper.getInvTrSrlTagNo(trNo, matNo, dtlNo);
	}

	/**
	 * 新增庫存主黨
	 * 
	 * @param invTxn
	 *            invTxn
	 * @return int
	 */
	public int insertInvTxn(TbInvTxn invTxn) {

		return txnMapper.insertSelective(invTxn);
	}

	/**
	 * 新增調撥紀錄檔
	 * 
	 * @param invTrAct
	 *            TbInvTrAct
	 * @return int
	 */
	public int insertTrAct(TbInvTrAct invTrAct) {

		return trActMapper.insertSelective(invTrAct);
	}

	/**
	 * 取得調撥記錄檔
	 * 
	 * @param trNo
	 *            單據編號
	 * @param matNo
	 *            料號
	 * 
	 * @return List<TbInvTrAct>
	 * 
	 */
	public List<TbInvTrActCompleteDTO> getExportHistory(HashMap<String, Object> map) {

		PageBounds pageBounds = getPageBounds("");
		List<TbInvTrActCompleteDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbInvTrActMapper.getExportHistory", map,
				pageBounds);

		return list;
	}

	/**
	 * 新增在途檔
	 * 
	 * @param invTrAct
	 *            TbInvTrAct
	 * @return int
	 */
	public int insertOnHand(TbInvOnhand onHand) {

		return onHandMapper.insertSelective(onHand);
	}
	
	public List<TbInvSrl> getInvTrSrl(String venSn){
		
		TbInvSrlExample example = new TbInvSrlExample();
		example.createCriteria().andVen_snEqualTo(venSn);
		
		return invSrlMapper.selectByExample(example);
	}
}
