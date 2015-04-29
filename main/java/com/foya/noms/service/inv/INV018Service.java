package com.foya.noms.service.inv;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbInvTxn;
import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbOrgDeptExample;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.dao.mybatis.model.TbSysLookupExample;
import com.foya.dao.mybatis.model.UTbInvBookingExample;
import com.foya.dao.mybatis.model.UTbInvInvExample;
import com.foya.dao.mybatis.model.UTbInvSrlExample;
import com.foya.dao.mybatis.model.UTbInvStatTranExample;
import com.foya.dao.mybatis.model.UTbInvWarehouseExample;
import com.foya.exception.NomsException;
import com.foya.noms.dao.inv.INV018Dao;
import com.foya.noms.dao.org.DeptDao;
import com.foya.noms.dto.inv.TbInvBookingDTO;
import com.foya.noms.dto.inv.TbInvInvDTO;
import com.foya.noms.dto.inv.TbInvSrlDTO;
import com.foya.noms.dto.inv.TbInvStatTranDTO;
import com.foya.noms.dto.inv.TbInvWarehouseDTO;
import com.foya.noms.resources.AppConstants;
import com.foya.noms.service.BaseService;
import com.foya.noms.service.workflow.WorkflowActionService;
import com.foya.workflow.exception.WorkflowException;

@Service
public class INV018Service extends BaseService {

	@Autowired
	private INV018Dao inv018Dao;

	@Autowired
	private WorkflowActionService workflowActionService;

	@Autowired
	private DeptDao deptDao;

	/**
	 * 取得所有管理單位
	 * 
	 * @param example
	 * @return
	 */
	public List<TbOrgDept> selectOrgDeptByExample(TbOrgDeptExample example) {
		return deptDao.selectAllOrgDept(example);
	}

	/**
	 * 取得倉庫清單
	 * @param example
	 * @return
	 */
	public List<TbInvWarehouseDTO> selectInvWarehouseByExample(UTbInvWarehouseExample example) {
		return inv018Dao.selectInvWarehouseByExample(example);
	}

	/**
	 * 取得參數資料
	 * @param example
	 * @return
	 */
	public List<TbSysLookup> selectSysLookupByExample(TbSysLookupExample example) {
		return inv018Dao.selectSysLookupByExample(example);
	}

	/**
	 * 查詢庫存狀態資料
	 * @param example
	 * @return
	 */
	public List<TbInvStatTranDTO> selectInvStatTranByExample(UTbInvStatTranExample example) {
		return inv018Dao.selectInvStatTranByExample(example);
	}

	/**
	 * 查詢庫存筆數
	 * @param example
	 * @return
	 */
	public int countInvStatTranByExample(UTbInvStatTranExample example) {
		return inv018Dao.countInvStatTranByExample(example);
	}

	/**
	 * 更新庫存狀態資料
	 * @param record
	 * @return
	 */
	public int updateInvStatTranByPrimaryKeySelective(TbInvStatTranDTO record) {
		return inv018Dao.updateInvStatTranByPrimaryKeySelective(record);
	}

	/**
	 * 更新庫存狀態資料
	 * @param invTranNos
	 * @param userName
	 * @return
	 * @throws NomsException
	 */
	@Transactional
	public int cancelApply(String[] invTranNos, String userName)
			throws NomsException {
		for (int i = 0; i < invTranNos.length; i++) {
			TbInvStatTranDTO record = new TbInvStatTranDTO();
			record.setInv_tran_no(invTranNos[i]);
			record.setInv_tran_audit_status(Byte.valueOf("6")); // 狀態：刪除
			record.setMd_user(userName);
			record.setMd_time(new Date());
			inv018Dao.updateInvStatTranByPrimaryKeySelective(record);
		}
		return 1;
	}

	/**
	 * 新增庫存狀態資料
	 * @param record
	 * @return
	 */
	public int insertInvStatTranSelective(TbInvStatTranDTO record) {
		return inv018Dao.insertInvStatTranSelective(record);
	}

	/**
	 * 執行庫存狀態異動
	 * @param request
	 */
	@Transactional
	public int updateInvStatTranStatus(String[] invTranNos, String userName)
			throws NomsException {

		for (int i = 0; i < invTranNos.length; i++) {
			// 取得庫存資料
			UTbInvStatTranExample example = new UTbInvStatTranExample();
			example.createCriteria().andInv_tran_noEqualTo(invTranNos[i]);
			List<TbInvStatTranDTO> list = this.selectInvStatTranByExample(example);
			if (list.size() == 0) {
				throw new NomsException("99", "error查無對應的庫存資料，無法執行庫存狀態異動");
			}

			// 更新庫存狀態
			TbInvStatTranDTO record = new TbInvStatTranDTO();
			record.setInv_tran_no(invTranNos[i]);
			record.setInv_tran_audit_status(Byte.valueOf("5"));
			record.setMd_user(userName);
			record.setMd_time(new Date());
			this.updateInvStatTranByPrimaryKeySelective(record);

			// 取得資產分布資料(庫存)
			UTbInvInvExample invInvExample = new UTbInvInvExample();
			UTbInvInvExample.Criteria invInvCr = invInvExample.createCriteria();
			invInvCr.andWh_idEqualTo(list.get(0).getWh_id());
			invInvCr.andMat_noEqualTo(list.get(0).getMat_no());
			List<TbInvInvDTO> invInvList = inv018Dao.selectInvInvByExample(invInvExample);
			if (invInvList.size() == 0) {
				throw new NomsException("99", "error查無對應的資產分布資料，無法執行庫存狀態異動");
			}

			int stdQty = invInvList.get(0).getStd_qty();
			int wtdQty = invInvList.get(0).getWrd_qty();
			int wspQty = invInvList.get(0).getWsp_qty();
			int bookingQty = invInvList.get(0).getBooking_qty();
			Byte oldMatStatus = list.get(0).getOld_mat_status(); // 異動前狀態
			Byte newMatStatus = list.get(0).getNew_mat_status(); // 異動後狀態

			if (oldMatStatus.equals(Byte.valueOf("1"))
					&& stdQty - list.get(0).getTran_qty() < 0) {
				throw new NomsException("99", "error異動數量超過庫存數量請重新選擇!");
			}
			if (oldMatStatus.equals(Byte.valueOf("2"))
					&& wtdQty - list.get(0).getTran_qty() < 0) {
				throw new NomsException("99", "error異動數量超過庫存數量請重新選擇!");
			}
			if (oldMatStatus.equals(Byte.valueOf("3"))
					&& wspQty - list.get(0).getTran_qty() < 0) {
				throw new NomsException("99", "error異動數量超過庫存數量請重新選擇!");
			}

			// 更新Booking資料
			TbInvInvDTO tbInvInvDTO = new TbInvInvDTO();
			if (oldMatStatus.equals(Byte.valueOf("1"))) {
				UTbInvBookingExample invBookingExample = new UTbInvBookingExample();
				UTbInvBookingExample.Criteria cr = invBookingExample.createCriteria();
				cr.andAct_noEqualTo(invTranNos[i]);
				cr.andMat_noEqualTo(list.get(0).getMat_no());
				cr.andWh_idEqualTo(list.get(0).getWh_id());
				cr.andAct_typeEqualTo(Byte.valueOf("3"));
				List<TbInvBookingDTO> invBookinglist = inv018Dao.selectInvBookingByExample(invBookingExample);
				if (invBookinglist.size() == 0) {
					throw new NomsException("99", "error查無對應的Booking資料，無法執行庫存狀態異動");
				}

				for (int j = 0; j < invBookinglist.size(); j++) {
					invBookingExample = new UTbInvBookingExample();
					cr = invBookingExample.createCriteria();
					cr.andEqualTo("act_no", invTranNos[i]);
					cr.andEqualTo("mat_no", list.get(0).getMat_no());
					cr.andEqualTo("wh_id", list.get(0).getWh_id());
					cr.andEqualTo("act_type", Byte.valueOf("3"));
					
					TbInvBookingDTO tbInvBookingDTO = new TbInvBookingDTO();
					tbInvBookingDTO.setBooking_qty(invBookinglist.get(j).getBooking_qty() - list.get(0).getTran_qty());
					tbInvBookingDTO.setMd_user(userName);
					tbInvBookingDTO.setMd_time(new Date());
					inv018Dao.updateInvBookingByExampleSelective(tbInvBookingDTO, invBookingExample);
				}

				tbInvInvDTO.setStd_qty(stdQty - list.get(0).getTran_qty());
				tbInvInvDTO.setBooking_qty(bookingQty - list.get(0).getTran_qty());
				if (newMatStatus.equals(Byte.valueOf("2"))) {
					// 異動前狀態是可用品，異動後狀態是待送修
					tbInvInvDTO.setWrd_qty(wtdQty + list.get(0).getTran_qty());
				} else if (newMatStatus.equals(Byte.valueOf("3"))) {
					// 異動前狀態是可用品，異動後狀態是待報廢
					tbInvInvDTO.setWsp_qty(wspQty + list.get(0).getTran_qty());
				}
			} else if (oldMatStatus.equals(Byte.valueOf("2"))) {
				tbInvInvDTO.setWrd_qty(wtdQty - list.get(0).getTran_qty());
				if (newMatStatus.equals(Byte.valueOf("1"))) {
					// 異動前狀態是待送修，異動後狀態是可用品
					tbInvInvDTO.setStd_qty(stdQty + list.get(0).getTran_qty());
				} else if (newMatStatus.equals(Byte.valueOf("3"))) {
					// 異動前狀態是待送修，異動後狀態是待報廢
					tbInvInvDTO.setWsp_qty(wspQty + list.get(0).getTran_qty());
				}
			} else if (oldMatStatus.equals(Byte.valueOf("3"))) {
				tbInvInvDTO.setWsp_qty(wspQty - list.get(0).getTran_qty());
				if (newMatStatus.equals(Byte.valueOf("1"))) {
					// 異動前狀態是待報廢，異動後狀態是可用品
					tbInvInvDTO.setStd_qty(stdQty + list.get(0).getTran_qty());
				} else if (newMatStatus.equals(Byte.valueOf("2"))) {
					// 異動前狀態是待報廢，異動後狀態是待送修
					tbInvInvDTO.setWrd_qty(wtdQty + list.get(0).getTran_qty());
				}
			}
			// 更新資產分布資料(庫存)
			invInvExample = new UTbInvInvExample();
			invInvCr = invInvExample.createCriteria();
			invInvCr.andEqualTo("wh_id", list.get(0).getWh_id());
			invInvCr.andEqualTo("mat_no", list.get(0).getMat_no());
			inv018Dao.updateInvInvByExampleSelective(tbInvInvDTO, invInvExample);

			// 新增交易記錄檔
			TbInvTxn tbInvTxn = new TbInvTxn();
			tbInvTxn.setTxn_type(Byte.valueOf("5"));
			tbInvTxn.setTxn_no(list.get(0).getInv_tran_no());
			tbInvTxn.setWh_id(list.get(0).getWh_id());
			tbInvTxn.setMat_no(list.get(0).getMat_no());
			tbInvTxn.setMat_status(list.get(0).getNew_mat_status());
			tbInvTxn.setQty(list.get(0).getTran_qty());
			tbInvTxn.setFa_no(list.get(0).getFa_no());
			tbInvTxn.setSrl_no(list.get(0).getSrl_no());
			tbInvTxn.setCr_user(userName);
			tbInvTxn.setCr_time(new Date());
			inv018Dao.insertInvTxnSelective(tbInvTxn);
		}

		return 1;
	}

	/**
	 * 庫存異動申請
	 * @param record
	 * @throws NomsException
	 */
	@Transactional
	public int apply(TbInvStatTranDTO record, String userName) throws NomsException {
		try {
			if (record.getBtn_type().equals("add")) {
				record.setTran_user(getLoginUser().getUsername());
				record.setTran_date(new Date());
				record.setMd_user(getLoginUser().getUsername());
				record.setMd_time(new Date());

				// 取得廠商序號資料
				if (record.getTag_no() != null) {
					UTbInvSrlExample example = new UTbInvSrlExample();
					UTbInvSrlExample.Criteria cr = example.createCriteria();
					cr.andSrl_noEqualTo(record.getSrl_no());
					List<TbInvSrlDTO> list = this.selectInvSrlByExample(example);
					if (list.size() == 0) {
						throw new Exception("error無法取得序號資料");
					}
				}

				// 新增
				// 取得使用者部門所屬區域
				TbOrgDept orgDept = this.selectOrgDeptByPrimaryKey(getLoginUser().getDeptId());
				String domain = orgDept.getDOMAIN().substring(0, 1);

				// 產生庫存序號
				// 1.先檢查當天是否已有產生過流水號
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String invTranNo = "IA" + domain + sdf.format(new Date());

				UTbInvStatTranExample example = new UTbInvStatTranExample();
				UTbInvStatTranExample.Criteria cr = example.createCriteria();
				cr.andInv_tran_noLike(invTranNo + "%");
				sdf = new SimpleDateFormat("yyyy/MM/dd");
				String today = sdf.format(new Date());
				sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
				cr.andCr_timeBetween(sdf.parse(today + " 00:00:00.000"), sdf.parse(today + " 23:59:59.999"));
				int count = this.countInvStatTranByExample(example);

				// 2.產生庫存序號
				invTranNo += String.format("%03d", count + 1);

				// 新增資料
				record.setInv_tran_no(invTranNo); // IA+區碼1碼 + YYMMDD + 流水號3碼
				record.setCr_user(getLoginUser().getUsername());
				record.setCr_time(new Date());
				this.insertInvStatTranSelective(record);
			}

			// 檢查倉庫是否存在料號
			UTbInvInvExample invInvExample = new UTbInvInvExample();
			UTbInvInvExample.Criteria invInvCr = invInvExample.createCriteria();
			invInvCr.andWh_idEqualTo(record.getWh_id());
			invInvCr.andMat_noEqualTo(record.getMat_no());
			List<TbInvInvDTO> invlist = inv018Dao.selectInvInvByExample(invInvExample);
			if (invlist.size() <= 0) {
				throw new NomsException("99", "error倉庫(" + record.getWh_id() + ")無此料號(" + record.getMat_no() + ")請重新選擇!");
			}
			if (record.getOld_mat_status().equals(Byte.valueOf("1"))
					&& invlist.get(0).getStd_qty() - record.getTran_qty() < 0) {
				throw new NomsException("99", "error異動數量超過庫存數量請重新選擇!");
			}
			if (record.getOld_mat_status().equals(Byte.valueOf("2"))
					&& invlist.get(0).getWrd_qty() - record.getTran_qty() < 0) {
				throw new NomsException("99", "error異動數量超過庫存數量請重新選擇!");
			}
			if (record.getOld_mat_status().equals(Byte.valueOf("3"))
					&& invlist.get(0).getWsp_qty() - record.getTran_qty() < 0) {
				throw new NomsException("99", "error異動數量超過庫存數量請重新選擇!");
			}

			// 儲存前狀態：草稿、異動前狀態：可用品
			if (record.getInv_tran_audit_status().equals(Byte.valueOf("1"))
					&& record.getOld_mat_status().equals(Byte.valueOf("1"))) {
				// 新增Booking資料
				TbInvBookingDTO bookingRecord = new TbInvBookingDTO();
				bookingRecord.setWh_id(record.getWh_id());
				bookingRecord.setMat_no(record.getMat_no());
				bookingRecord.setAct_no(record.getInv_tran_no());
				bookingRecord.setAct_type(Byte.valueOf("3"));
				bookingRecord.setBooking_qty(record.getTran_qty());
				bookingRecord.setDept_id(invlist.get(0).getDept_id());
				bookingRecord.setDomain(invlist.get(0).getDomain());
				bookingRecord.setCr_user(userName);
				bookingRecord.setCr_time(new Date());
				bookingRecord.setMd_user(userName);
				bookingRecord.setMd_time(new Date());
				inv018Dao.insertInvBookingSelective(bookingRecord);

				// 更新資產分布資料(庫存)
				invInvExample = new UTbInvInvExample();
				invInvCr = invInvExample.createCriteria();
				invInvCr.andEqualTo("wh_id", record.getWh_id());
				invInvCr.andEqualTo("mat_no", record.getMat_no());

				TbInvInvDTO invInvRecord = new TbInvInvDTO();
				invInvRecord.setBooking_qty(invlist.get(0).getBooking_qty() + record.getTran_qty());
				invInvRecord.setMd_user(userName);
				invInvRecord.setMd_time(new Date());
				inv018Dao.updateInvInvByExampleSelective(invInvRecord, invInvExample);
			}

			// 更新庫存狀態
			record.setInv_tran_audit_status(Byte.valueOf("2")); // 狀態：送審中
			record.setTran_user(userName);
			record.setTran_date(new Date());
			record.setMd_user(userName);
			record.setMd_time(new Date());
			this.updateInvStatTranByPrimaryKeySelective(record);

			// 啟動簽核
			workflowActionService.apply("InventoryStatusChange", record.getInv_tran_no(), "庫存異動申請");

			return 1;
		} catch (WorkflowException e) {
			throw new NomsException(e.getMessage());
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new NomsException(e.getMessage());
		}
	}

	/**
	 * 查詢序號資料
	 * @param example
	 * @return
	 */
	public List<TbInvSrlDTO> selectInvSrlByExample(UTbInvSrlExample example) {
		return inv018Dao.selectInvSrlByExample(example);
	}

	/**
	 * 查詢部門資料
	 * @param deptId
	 * @return
	 */
	public TbOrgDept selectOrgDeptByPrimaryKey(String deptId) {
		return inv018Dao.selectOrgDeptByPrimaryKey(deptId);
	}

	/**
	 * 供WORKFLOW審核呼叫
	 * @param invTranNo 庫存單號或調撥單號
	 * @param action SUCCESS/REJECT
	 * @return
	 */
	@Transactional
	public int applyByWF(String invTranNo, String action) {
		String status = "4"; // 駁回
		// 審核通過，狀態改核可
		if (action.equals(AppConstants.WORKFLOW_REST_APPROVAL)) {
			status = "3"; // 核可
		}

		if (status.equals("4")) {
			// 恢復先前有變動的booking
			// 取得該庫存異動單號資料
			UTbInvStatTranExample example = new UTbInvStatTranExample();
			example.createCriteria().andInv_tran_noEqualTo(invTranNo);
			List<TbInvStatTranDTO> list = this.selectInvStatTranByExample(example);
			for (int i = 0; i < list.size(); i++) {
				TbInvStatTranDTO tb = list.get(i);
				// 只有當異動前狀態=1(可用品)的料號才會有booking，才需處理
				if (tb.getOld_mat_status().equals(Byte.valueOf("1"))) {
					// 1.刪除Booking資料
					UTbInvBookingExample invBookingExample = new UTbInvBookingExample();
					UTbInvBookingExample.Criteria cr = invBookingExample.createCriteria();
					cr.andEqualTo("act_no", tb.getInv_tran_no());
					cr.andEqualTo("wh_id", tb.getWh_id());
					cr.andEqualTo("mat_no", tb.getMat_no());
					cr.andEqualTo("act_type", Byte.valueOf("3"));
					inv018Dao.deleteInvBookingByExample(invBookingExample);

					// 2.恢復資產分布資料
					// 取得資產分布資料(庫存)
					UTbInvInvExample invInvExample = new UTbInvInvExample();
					UTbInvInvExample.Criteria invInvCr = invInvExample.createCriteria();
					invInvCr.andWh_idEqualTo(tb.getWh_id());
					invInvCr.andMat_noEqualTo(tb.getMat_no());
					List<TbInvInvDTO> invInvList = inv018Dao.selectInvInvByExample(invInvExample);
					if (invInvList.size() == 0) {
						throw new NomsException("99", "error查無對應的資產分布資料，無法執行庫存狀態異動");
					}

					// 更新資產分布資料(庫存)
					invInvExample = new UTbInvInvExample();
					invInvCr = invInvExample.createCriteria();
					invInvCr.andEqualTo("wh_id", tb.getWh_id());
					invInvCr.andEqualTo("mat_no", tb.getMat_no());

					TbInvInvDTO invInvRecord = new TbInvInvDTO();
					invInvRecord.setBooking_qty(invInvList.get(0).getBooking_qty() - tb.getTran_qty());
					invInvRecord.setMd_user("system");
					invInvRecord.setMd_time(new Date());
					inv018Dao.updateInvInvByExampleSelective(invInvRecord, invInvExample);
				}
			}
		}

		TbInvStatTranDTO record = new TbInvStatTranDTO();
		record.setInv_tran_no(invTranNo);
		record.setInv_tran_audit_status(Byte.valueOf(status));
		record.setTran_user("system");
		record.setTran_date(new Date());
		record.setMd_user("system");
		record.setMd_time(new Date());
		return this.updateInvStatTranByPrimaryKeySelective(record);
	}
}
