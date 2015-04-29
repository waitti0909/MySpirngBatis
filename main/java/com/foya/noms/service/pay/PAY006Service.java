package com.foya.noms.service.pay;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.dao.mybatis.model.TbAuthPersonnelExample;
import com.foya.dao.mybatis.model.TbComCompany;
import com.foya.dao.mybatis.model.TbComCompanyExample;
import com.foya.dao.mybatis.model.TbComItemMain;
import com.foya.dao.mybatis.model.TbComItemMainExample;
import com.foya.dao.mybatis.model.TbComPoMain;
import com.foya.dao.mybatis.model.TbComPoMainExample;
import com.foya.dao.mybatis.model.TbLsSite;
import com.foya.dao.mybatis.model.TbLsSiteExample;
import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbOrgDeptExample;
import com.foya.dao.mybatis.model.TbPayAcceptanceOrderKey;
import com.foya.dao.mybatis.model.TbPayLookupCode;
import com.foya.dao.mybatis.model.TbPayLookupCodeExample;
import com.foya.dao.mybatis.model.TbPayVoucherCredit;
import com.foya.dao.mybatis.model.TbPayVoucherCreditExample;
import com.foya.dao.mybatis.model.TbSiteOsItem;
import com.foya.dao.mybatis.model.UTbPayAcceptanceOrderExample;
import com.foya.dao.mybatis.model.UTbPayOutsourceAcceptanceDtlExample;
import com.foya.dao.mybatis.model.UTbPayOutsourceAcceptanceExample;
import com.foya.dao.mybatis.model.UTbPayPaymentRequestVoucherExample;
import com.foya.dao.mybatis.model.UTbSiteOsItemExample;
import com.foya.dao.mybatis.model.UTbSiteOutsourcingExample;
import com.foya.exception.NomsException;
import com.foya.noms.dao.pay.PAY006Dao;
import com.foya.noms.dto.pay.TbPayAcceptanceOrderDTO;
import com.foya.noms.dto.pay.TbPayOutsourceAcceptanceDTO;
import com.foya.noms.dto.pay.TbPayOutsourceAcceptanceDtlDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestVoucherDTO;
import com.foya.noms.dto.st.TbSiteOsItemDTO;
import com.foya.noms.dto.st.TbSiteOutsourcingDTO;
import com.foya.noms.poi.Writer;
import com.foya.noms.service.BaseService;
import com.foya.noms.service.common.PoiService;
import com.foya.noms.service.st.OutsourcingService;

@Service
public class PAY006Service extends BaseService {

	@Autowired
	private PAY006Dao pay006Dao;

	@Autowired
	private PoiService poiService;

	@Autowired
	private OutsourcingService outsourcingService;

	/**
	 * 取得廠商清單
	 * @param example
	 * @return
	 */
	public List<TbComCompany> selectComCompanyByExample(TbComCompanyExample example) {
		return pay006Dao.selectComCompanyByExample(example);
	}

	/**
	 * 取得參數設定資料
	 * @param example
	 * @return
	 */
	public List<TbPayLookupCode> selectPayLookupCodeByExample(TbPayLookupCodeExample example) {
		return pay006Dao.selectPayLookupCodeByExample(example);
	}

	/**
	 * 取得人員清單
	 * @param example
	 * @return
	 */
	public List<TbAuthPersonnel> selectAuthPersonnelByExample(TbAuthPersonnelExample example) {
		return pay006Dao.selectAuthPersonnelByExample(example);
	}

	/**
	 * 取得驗收清單資料
	 * 
	 * @param example
	 * @return
	 */
	public List<TbPayOutsourceAcceptanceDTO> selectPayOutsourceAcceptanceByExamplePage(
			UTbPayOutsourceAcceptanceExample example) {
		return pay006Dao.selectPayOutsourceAcceptanceByExamplePage(example);
	}

	/**
	 * 取得驗收清單資料by key
	 * @param ap_no
	 * @return
	 */
	public TbPayOutsourceAcceptanceDTO selectPayOutsourceAcceptanceByPrimaryKey(String ap_no) {
		return pay006Dao.selectPayOutsourceAcceptanceByPrimaryKey(ap_no);
	}

	/**
	 * 修改驗收清單資料by key
	 * @param record
	 * @return
	 */
	public int updatePayOutsourceAcceptanceByPrimaryKeySelective(TbPayOutsourceAcceptanceDTO record) {
		return pay006Dao.updatePayOutsourceAcceptanceByPrimaryKeySelective(record);
	}

	/**
	 * 送請款
	 * @param map
	 * @throws NomsException
	 */
	@Transactional
	public void paymentRequest(Map<String, String> map) throws NomsException {
		try {
			// 更新狀態
			TbPayOutsourceAcceptanceDTO record = new TbPayOutsourceAcceptanceDTO();
			record.setAP_NO(map.get("ap_no"));
			record.setSTATUS("I");
			record.setMD_USER(getLoginUser().getUsername());
			record.setMD_TIME(new Date());
			this.updatePayOutsourceAcceptanceByPrimaryKeySelective(record);

			// 回壓站台委外申請記錄實際請款金額資料
			UTbPayAcceptanceOrderExample example = new UTbPayAcceptanceOrderExample();
			UTbPayAcceptanceOrderExample.Criteria cr = example.createCriteria();
			cr.andMST_AP_NOEqualTo(map.get("ap_no"));
			cr.andPO_NOEqualTo(map.get("po_no"));
			List<TbPayAcceptanceOrderDTO> list = pay006Dao.selectPayAcceptanceOrderByExample(example);

			for (int i = 0; i < list.size(); i++) {
				TbPayAcceptanceOrderDTO orderTb = list.get(i);

				UTbPayOutsourceAcceptanceDtlExample dtlExample = new UTbPayOutsourceAcceptanceDtlExample();
				UTbPayOutsourceAcceptanceDtlExample.Criteria dtlCr = dtlExample.createCriteria();
				dtlCr.andMST_AP_NOEqualTo(map.get("ap_no"));
				dtlCr.andPO_NOEqualTo(map.get("po_no"));
				dtlCr.andORDER_NOEqualTo(orderTb.getORDER_NO());
				List<TbPayOutsourceAcceptanceDtlDTO> dtlList = pay006Dao.selectPayOutsourceAcceptanceDtlByExample(dtlExample);

				List<TbSiteOsItem> siteOsItemList = new ArrayList<TbSiteOsItem>();
				for (int j = 0; j < dtlList.size(); j++) {
					TbPayOutsourceAcceptanceDtlDTO dtlTb = dtlList.get(j);

					TbSiteOsItem siteOsItemRecord = new TbSiteOsItem();
					siteOsItemRecord.setITEM_ID(dtlTb.getORDER_ITEM());
					siteOsItemRecord.setPAY_NUMBER(new BigDecimal(dtlTb.getREAL_AP_QTY().toString()));
					siteOsItemRecord.setPAY_AMOUNT(dtlTb.getAP_AMT());
					siteOsItemList.add(siteOsItemRecord);
				}

				// 取得OS_ID
				UTbSiteOutsourcingExample siteOutsourcingExample = new UTbSiteOutsourcingExample();
				UTbSiteOutsourcingExample.Criteria siteOutsourcingCr = siteOutsourcingExample.createCriteria();
				siteOutsourcingCr.andORDER_IDEqualTo(orderTb.getORDER_NO());
				siteOutsourcingCr.andCO_VAT_NOEqualTo(map.get("co_vat_no"));
				siteOutsourcingCr.andEqualTo("B.PO_NO", map.get("po_no"));
				siteOutsourcingCr.andSTATUSEqualTo("OS07"); // 已驗收
				siteOutsourcingCr.andPAY_FLGEqualTo("Y");
				List<TbSiteOutsourcingDTO> siteOutsourcingList = pay006Dao.selectSiteOutsourcingByExamplePage(siteOutsourcingExample);
				if (siteOutsourcingList.size() == 0) {
					throw new NomsException("99", "無法取得OS_ID");
				}
				// 測試Log
				System.out.println("OS_ID = " + siteOutsourcingList.get(0).getOS_ID());
				System.out.println("AP_NO = " + map.get("ap_no"));
				for (int x = 0; x < siteOsItemList.size(); x++) {
					System.out.println("ITEM_ID = " + siteOsItemList.get(x).getITEM_ID());
					System.out.println("PAY_NUMBER = " + siteOsItemList.get(x).getPAY_NUMBER());
					System.out.println("PAY_AMOUNT = " + siteOsItemList.get(x).getPAY_AMOUNT());
				}
				outsourcingService.feedbackFromPayment(siteOutsourcingList.get(0).getOS_ID(), map.get("ap_no"), siteOsItemList); // XXX
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new NomsException("99", e.getMessage());
		}
	}

	/**
	 * 取得工程驗收清單資料
	 * @param example
	 * @return
	 */
	public List<TbPayAcceptanceOrderDTO> selectPayAcceptanceOrderByExamplePage(UTbPayAcceptanceOrderExample example) {
		return pay006Dao.selectPayAcceptanceOrderByExamplePage(example);
	}

	/**
	 * 取得取得工程驗收工單資料 by page
	 * @param example
	 * @return
	 */
	public List<TbPayOutsourceAcceptanceDtlDTO> selectPayOutsourceAcceptanceDtlByExamplePage(
			UTbPayOutsourceAcceptanceDtlExample example) {
		return pay006Dao.selectPayOutsourceAcceptanceDtlByExamplePage(example);
	}

	/**
	 * 取得工程驗收工單資料
	 * @param example
	 * @return
	 */
	public List<TbPayOutsourceAcceptanceDtlDTO> selectPayOutsourceAcceptanceDtlByExample(
			UTbPayOutsourceAcceptanceDtlExample example) {
		return pay006Dao.selectPayOutsourceAcceptanceDtlByExample(example);
	}

	/**
	 * 刪除工程驗收資料
	 * @param map
	 * @return
	 */
	@Transactional
	public boolean deleteAcceptanceOrder(Map<String, String> map) throws NomsException {
		try {
			boolean noDataFlag = false; // 是否刪除了驗收主檔資料

			String[] order_nos = map.get("order_nos").split(",");
			String[] os_ids = map.get("os_ids").split(",");
			for (int i = 0; i < order_nos.length; i++) {
				// 刪除工程驗收主檔資料
				TbPayAcceptanceOrderKey key = new TbPayAcceptanceOrderKey();
				key.setMST_AP_NO(map.get("mst_ap_no"));
				key.setPO_NO(map.get("po_no"));
				key.setORDER_NO(order_nos[i]);
				key.setOS_ID(os_ids[i]);
				pay006Dao.deletePayAcceptanceOrderByPrimaryKey(key);

				// 刪除工程驗收工單明細資料
				UTbPayOutsourceAcceptanceDtlExample example = new UTbPayOutsourceAcceptanceDtlExample();
				UTbPayOutsourceAcceptanceDtlExample.Criteria cr = example.createCriteria();
				cr.andMST_AP_NOEqualTo(map.get("mst_ap_no"));
				cr.andPO_NOEqualTo(map.get("po_no"));
				cr.andORDER_NOEqualTo(order_nos[i]);
				cr.andOS_IDEqualTo(os_ids[i]);
				pay006Dao.deletePayOutsourceAcceptanceDtlByExample(example);
			}

			// 若已無工程驗收資料，則刪除驗收主檔資料
			UTbPayAcceptanceOrderExample example = new UTbPayAcceptanceOrderExample();
			UTbPayAcceptanceOrderExample.Criteria cr = example.createCriteria();
			cr.andMST_AP_NOEqualTo(map.get("mst_ap_no"));
			cr.andPO_NOEqualTo(map.get("po_no"));
			int count = pay006Dao.countPayAcceptanceOrderByExample(example);
			if (count == 0) {
				UTbPayOutsourceAcceptanceExample example2 = new UTbPayOutsourceAcceptanceExample();
				UTbPayOutsourceAcceptanceExample.Criteria cr2 = example2.createCriteria();
				cr2.andEqualTo("AP_NO", map.get("mst_ap_no"));
				pay006Dao.deletePayOutsourceAcceptanceByExample(example2);
				noDataFlag = true;
			}
			return noDataFlag;
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new NomsException("99", e.getMessage());
		}
	}

	/**
	 * 修改工單實際驗收數量
	 * @param map
	 * @return
	 */
	@Transactional
	public int updateRealApQty(Map<String, String> map) throws NomsException {
		try {
			TbPayOutsourceAcceptanceDtlDTO record = new TbPayOutsourceAcceptanceDtlDTO();
			record.setMST_AP_NO(map.get("mst_ap_no"));
			record.setPO_NO(map.get("po_no"));
			record.setORDER_NO(map.get("order_no"));
			record.setORDER_ITEM(map.get("order_item"));
			record.setOS_ID(map.get("os_id"));
			record.setREAL_AP_QTY(Integer.valueOf(map.get("real_ap_qty")));
			record.setMD_USER(getLoginUser().getUsername());
			record.setMD_TIME(new Date());

			// 未稅金額tax_exclusive_amt = 單價unit_price * 實際驗收數量real_ap_qty * (1 + 管理費設定值)
			TbComItemMainExample example = new TbComItemMainExample();
			example.createCriteria().andITEM_IDEqualTo(record.getORDER_ITEM());
			List<TbComItemMain> comItemMainList = pay006Dao.selectComItemMainByExample(example);
			if (comItemMainList.size() == 0) {
				throw new NomsException("99", "無法取得工單為" + record.getORDER_ITEM() + "的管理費設定值");
			}
			Integer mgrFee = comItemMainList.get(0).getMGR_FEE() == null ? 0 : comItemMainList.get(0).getMGR_FEE(); // 管理費設定值
			Integer unitPrice = Integer.valueOf(map.get("unit_price")); // 單價
			Integer taxExclusiveAmt = unitPrice * record.getREAL_AP_QTY() * (1 + mgrFee); // 未稅金額
			record.setTAX_EXCLUSIVE_AMT(BigDecimal.valueOf(taxExclusiveAmt));

			// 取得營業稅business_tax
			Map<String, Object> taxRateMap = new HashMap<String, Object>();
			taxRateMap.put("PI_TYPE", "3"); // 計算營業稅
			taxRateMap.put("PI_AMT", record.getTAX_EXCLUSIVE_AMT()); // 傳入未稅金額tax_exclusive_amt
			taxRateMap.put("PI_INCLUDE_TAX", "N"); // 傳入金額是否含稅N:未稅
			pay006Dao.callPayFnGetTax(taxRateMap);
			record.setBUSINESS_TAX(new BigDecimal(((Integer) taxRateMap.get("RETURN_VALUE")).toString()));

			return pay006Dao.updatePayOutsourceAcceptanceDtlByPrimaryKeySelective(record);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new NomsException("99", e.getMessage());
		}
	}

	/**
	 * 取得憑證資料 by page
	 * @param example
	 * @return
	 */
	public List<TbPayPaymentRequestVoucherDTO> selectPayPaymentRequestVoucherByExamplePage(UTbPayPaymentRequestVoucherExample example) {
		return pay006Dao.selectPayPaymentRequestVoucherByExamplePage(example);
	}

	/**
	 * 儲存憑證資料異動結果
	 * @param map
	 * @throws NomsException
	 */
	@Transactional
	public void creditSave(Map<String, String> map) throws NomsException {
		try {
			String mst_ap_no = map.get("mst_ap_no");
			String mst_po_no = map.get("mst_po_no");
			String[] id = map.get("id").split(",");
			String[] voucher_type = map.get("voucher_type").split(",");
			String[] voucher_nbr = map.get("voucher_nbr").split(",");
			String[] voucher_date = map.get("voucher_date").split(",");
			String[] vat_number = map.get("vat_number").split(",");
			String[] tax_exclusive_amt = map.get("tax_exclusive_amt").split(",");
			String voucherNo = null; // 憑證處理單號

			// 刪除憑證資料，並取得已存在的憑證處理單號
			UTbPayPaymentRequestVoucherExample example = new UTbPayPaymentRequestVoucherExample();
			UTbPayPaymentRequestVoucherExample.Criteria cr = example.createCriteria();
			cr.andPROCESS_TYPEEqualTo("OSV");
			cr.andEqualTo("B.CASH_REQ_AP_NO", mst_ap_no);
			List<TbPayPaymentRequestVoucherDTO> list = pay006Dao.selectPayPaymentRequestVoucherByExample(example);
			for (int i = 0; list != null && i < list.size(); i++) {
				if (i == 0) {
					voucherNo = list.get(i).getVOUCHER_NO();
				}
				creditDelete(list.get(i).getSEQ_NBR());
			}

			// 若不存在憑證處理單號，則自動產生
			if (voucherNo == null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				Map<String, Object> seqNoMap = new HashMap<String, Object>();
				seqNoMap.put("PI_PREFIX", "OSA");
				seqNoMap.put("PI_APP_DATE", sdf.format(new Date()));
				seqNoMap.put("PO_SEQNO", null);
				seqNoMap.put("PO_ERR_CDE", null);
				seqNoMap.put("PO_ERR_MSG", null);
				pay006Dao.callPayPcGetSeqnoByMap(seqNoMap);
				if (!seqNoMap.get("PO_ERR_CDE").equals("00")) {
					log.error("自動產生憑證處理單號Call PAY_PC_GET_SEQNO Error：ERR_CDE=" + seqNoMap.get("PO_ERR_CDE")
							+ ", PO_ERR_MSG=" + seqNoMap.get("PO_ERR_MSG"));
					throw new NomsException("99", "自動產生憑證處理單號執行錯誤：" + seqNoMap.get("PO_ERR_MSG"));
				}
				voucherNo = (String) seqNoMap.get("PO_SEQNO");
			}
			
			for (int i = 0; i < id.length; i++) {
				Map<String, String> insMap = new HashMap<String, String>();
				insMap.put("ap_no", mst_ap_no);
				insMap.put("po_no", mst_po_no);
				insMap.put("voucher_no", voucherNo);
				insMap.put("voucher_type", voucher_type[i]);
				insMap.put("voucher_nbr", voucher_nbr[i]);
				insMap.put("voucher_date", voucher_date[i]);
				insMap.put("vat_number", vat_number[i]);
				insMap.put("tax_exclusive_amt", tax_exclusive_amt[i]);
				this.creditAdd(insMap);
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new NomsException("99", e.getMessage());
		}
	}

	/**
	 * 新增憑證資料
	 * @param map
	 * @throws NomsException
	 */
	private void creditAdd(Map<String, String> map) throws NomsException {
		try {
			// 新增憑證主檔資料
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			TbPayPaymentRequestVoucherDTO voucherRecord = new TbPayPaymentRequestVoucherDTO();
			voucherRecord.setPROCESS_TYPE("OSV");
			voucherRecord.setVOUCHER_NO(map.get("voucher_no"));
			voucherRecord.setVOUCHER_TYPE(map.get("voucher_type"));
			voucherRecord.setVOUCHER_NBR(map.get("voucher_nbr"));
			voucherRecord.setVOUCHER_DATE(sdf.parse(map.get("voucher_date")));
			voucherRecord.setVAT_NUMBER(map.get("vat_number"));
			voucherRecord.setTAX_EXCLUSIVE_AMT(new BigDecimal(map.get("tax_exclusive_amt")));

			// 取得營業稅business_tax
			Map<String, Object> taxRateMap = new HashMap<String, Object>();
			taxRateMap.put("PI_TYPE", "3"); // 計算營業稅
			taxRateMap.put("PI_AMT", voucherRecord.getTAX_EXCLUSIVE_AMT()); // 傳入未稅金額tax_exclusive_amt
			taxRateMap.put("PI_INCLUDE_TAX", "N"); // 傳入金額是否含稅N:未稅
			pay006Dao.callPayFnGetTax(taxRateMap);
			voucherRecord.setBUSINESS_TAX(new BigDecimal(((Integer) taxRateMap.get("RETURN_VALUE")).toString()));

			voucherRecord.setCREDIT_MADE_AMT(voucherRecord.getTAX_EXCLUSIVE_AMT());
			voucherRecord.setCREDIT_MADE_TAX(voucherRecord.getBUSINESS_TAX());
			voucherRecord.setCR_USER(getLoginUser().getUsername());
			voucherRecord.setCR_TIME(new Date());
			voucherRecord.setMD_USER(getLoginUser().getUsername());
			voucherRecord.setMD_TIME(new Date());
			pay006Dao.insertPayPaymentRequestVoucherSelective(voucherRecord);
			if (voucherRecord.getSEQ_NBR() == null) {
				throw new NomsException("99", "無法取得新增後的憑證主檔SEQ_NO");
			}

			// 新增憑證明細資料
			TbPayVoucherCredit creditRecord = new TbPayVoucherCredit();
			creditRecord.setMST_SEQ_NBR(voucherRecord.getSEQ_NBR());
			creditRecord.setCASH_REQ_AP_NO(map.get("ap_no"));
			creditRecord.setCONTRACT_PO_NO(map.get("po_no"));
			creditRecord.setCREDIT_AMT(voucherRecord.getTAX_EXCLUSIVE_AMT());
			creditRecord.setCREDIT_TAX(voucherRecord.getBUSINESS_TAX());
			creditRecord.setCREDIT_DATE(new Date());
			creditRecord.setCR_USER(getLoginUser().getUsername());
			creditRecord.setCR_TIME(new Date());
			creditRecord.setMD_USER(getLoginUser().getUsername());
			creditRecord.setMD_TIME(new Date());
			pay006Dao.insertPayVoucherCreditSelective(creditRecord);
		} catch (ParseException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new NomsException("99", e.getMessage());
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new NomsException("99", e.getMessage());
		}
	}

	/**
	 * 刪除憑證資料
	 * @param seq_nbr 憑證流水號
	 * @throws NomsException
	 */
	private void creditDelete(long seq_nbr) throws NomsException {
		try {
			// 刪除憑證主檔資料
			UTbPayPaymentRequestVoucherExample voucherExample = new UTbPayPaymentRequestVoucherExample();
			voucherExample.createCriteria().andEqualTo("SEQ_NBR", seq_nbr);
			pay006Dao.deletePayPaymentRequestVoucherByExample(voucherExample);

			// 刪除憑證明細資料
			TbPayVoucherCreditExample creditExample = new TbPayVoucherCreditExample();
			creditExample.createCriteria().andMST_SEQ_NBREqualTo(seq_nbr);
			pay006Dao.deletePayVoucherCreditByExample(creditExample);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new NomsException("99", e.getMessage());
		}
	}

	/**
	 * 取得合約資料
	 * @param example
	 * @return
	 */
	public List<TbComPoMain> selectComPoMainByExample(TbComPoMainExample example) {
		return pay006Dao.selectComPoMainByExample(example);
	}

	/**
	 * 匯出資材歷程Excel報表
	 * @param session
	 * @param response
	 * @param fileName 檔案名稱
	 * @param map 表頭標題資料
	 * @param list1 已請款記錄
	 * @param list2 本次請款
	 * @throws IOException
	 */
	public void exportExcel(HttpSession session, HttpServletResponse response,
			String fileName, Map<String, String> map,
			List<TbPayOutsourceAcceptanceDtlDTO> list1,
			List<TbPayOutsourceAcceptanceDtlDTO> list2) throws IOException {
		HSSFSheet sheet = poiService.exportXLS(response, "請款累計紀錄表", fileName, 0, 0, 12);

		// 欄位字體(表頭)
		Font headerFont = sheet.getWorkbook().createFont();
		headerFont.setFontName("標楷體");
		headerFont.setFontHeightInPoints((short) 20); // 字型大小

		// 欄位字體(明細)
		Font dtlFont = sheet.getWorkbook().createFont();
		dtlFont.setFontName("標楷體");

		// 欄位字體(簽章)
		Font signFont = sheet.getWorkbook().createFont();
		signFont.setFontName("標楷體");
		signFont.setFontHeightInPoints((short) 12); // 字型大小

		// 儲存格樣式(表頭)
		HSSFCellStyle headerCellStyle = sheet.getWorkbook().createCellStyle();
		headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER); // 左右置中
		headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 垂直置中
		headerCellStyle.setWrapText(true);
		headerCellStyle.setFont(headerFont);

		// 儲存格樣式(明細表頭)
		HSSFCellStyle dtlHeaderCellStyle = sheet.getWorkbook().createCellStyle();
		dtlHeaderCellStyle.setAlignment(CellStyle.ALIGN_CENTER); // 左右置中
		dtlHeaderCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 垂直置中
		dtlHeaderCellStyle.setWrapText(true);
		dtlHeaderCellStyle.setFont(dtlFont);
		dtlHeaderCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  // 設置可填充儲存格底色
		dtlHeaderCellStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index); // 底色
		dtlHeaderCellStyle.setBorderTop((short) 1); // 上框線
		dtlHeaderCellStyle.setBorderLeft((short) 1); // 左框線
		dtlHeaderCellStyle.setBorderBottom((short) 1); // 下框線
		dtlHeaderCellStyle.setBorderRight((short) 1); // 右框線

		// 儲存格樣式(明細)(左右置左)
		HSSFCellStyle dtlLeftCellStyle = sheet.getWorkbook().createCellStyle();
		dtlLeftCellStyle.setAlignment(CellStyle.ALIGN_LEFT); // 左右置左
		dtlLeftCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 垂直置中
		dtlLeftCellStyle.setWrapText(true);
		dtlLeftCellStyle.setFont(dtlFont);
		dtlLeftCellStyle.setBorderTop((short) 1); // 上框線
		dtlLeftCellStyle.setBorderLeft((short) 1); // 左框線
		dtlLeftCellStyle.setBorderBottom((short) 1); // 下框線
		dtlLeftCellStyle.setBorderRight((short) 1); // 右框線

		// 儲存格樣式(明細)(左右置中)
		HSSFCellStyle dtlCenterCellStyle = sheet.getWorkbook().createCellStyle();
		dtlCenterCellStyle.setAlignment(CellStyle.ALIGN_CENTER); // 左右置中
		dtlCenterCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 垂直置中
		dtlCenterCellStyle.setWrapText(true);
		dtlCenterCellStyle.setFont(dtlFont);
		dtlCenterCellStyle.setBorderTop((short) 1); // 上框線
		dtlCenterCellStyle.setBorderLeft((short) 1); // 左框線
		dtlCenterCellStyle.setBorderBottom((short) 1); // 下框線
		dtlCenterCellStyle.setBorderRight((short) 1); // 右框線

		// 儲存格樣式(表尾標題)
		HSSFCellStyle footerTitleCellStyle = sheet.getWorkbook().createCellStyle();
		footerTitleCellStyle.setAlignment(CellStyle.ALIGN_CENTER); // 左右置中
		footerTitleCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 垂直置中
		footerTitleCellStyle.setWrapText(true);
		footerTitleCellStyle.setFont(dtlFont);

		// 儲存格樣式(表尾內容)
		HSSFCellStyle footerDtlCellStyle = sheet.getWorkbook().createCellStyle();
		footerDtlCellStyle.setAlignment(CellStyle.ALIGN_LEFT); // 左右置左
		footerDtlCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 垂直置中
		footerDtlCellStyle.setWrapText(true);
		footerDtlCellStyle.setFont(dtlFont);

		// 儲存格樣式(簽章)
		HSSFCellStyle signCellStyle = sheet.getWorkbook().createCellStyle();
		signCellStyle.setAlignment(CellStyle.ALIGN_LEFT); // 左右置左
		signCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 垂直置中
		signCellStyle.setWrapText(true);
		signCellStyle.setFont(signFont);

		// 第1行(表頭)
		int rowIndex = 0;
		int cellIndex = 0;
		HSSFRow row = sheet.createRow(rowIndex++);
		row.setHeight((short) ((short) 15 * 60));
		HSSFCell cell = row.createCell(cellIndex++);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue("請款累計紀錄表");
		// 合併儲存格(firstRow, endRow, firstCell, endCell)
		sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1, rowIndex - 1, cellIndex - 1, 12));

		// 第2行(空格行)
		cellIndex = 0;
		row = sheet.createRow(rowIndex++);
		row.setHeight((short) ((short) 15 * 60));
		cell = row.createCell(cellIndex++);
		// 合併儲存格(firstRow, endRow, firstCell, endCell)
		sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1, rowIndex - 1, cellIndex - 1, 12));

		// 第3行
		cellIndex = 0;
		row = sheet.createRow(rowIndex++);
		row.setHeight((short) ((short) 15 * 60));
		String[] line = { "合約名稱：", map.get("po_name"), "", "", "採購單號：", map.get("po_no"), "採購總價(含稅)：", "",
				"", "", "", "", "" };
		for (int i = 0; i < line.length; i++) {
			cell = row.createCell(cellIndex++);
			if (line[i] == null || line[i].equals("")) {
				cell.setCellStyle(dtlLeftCellStyle);
			} else if (i == 1 || i == 5) {
				cell.setCellStyle(dtlLeftCellStyle);
				cell.setCellValue(line[i]);
			} else {
				cell.setCellStyle(dtlHeaderCellStyle);
				cell.setCellValue(line[i]);
			}
		}
		// 合併儲存格(firstRow, endRow, firstCell, endCell)
		sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1, rowIndex - 1, 1, 3));
		sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1, rowIndex - 1, 6, 7));
		sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1, rowIndex - 1, 8, 12));

		// 第4行
		cellIndex = 0;
		row = sheet.createRow(rowIndex++);
		row.setHeight((short) ((short) 15 * 60));
		String cellText = "1.☐支票:(☐二年期票, ☐70天期票, ☐90天期票, ☐其他:__________)"
				+ "\r\n2.☐電匯, 帳號:__________" + "\r\n3.☐LC";
		line = new String[] { "付款條件：", "", "", "", "廠商名稱：", map.get("co_name"), "付款方式：", "",
				cellText, "", "", "", "" };
		for (int i = 0; i < line.length; i++) {
			cell = row.createCell(cellIndex++);
			if (line[i] == null || line[i].equals("")) {
				cell.setCellStyle(dtlLeftCellStyle);
			} else if (i == 5) {
				cell.setCellStyle(dtlLeftCellStyle);
				cell.setCellValue(line[i]);
			} else if (line[i].indexOf("\r\n") >= 0) {
				cell.setCellStyle(dtlLeftCellStyle);
				cell.setCellValue(new HSSFRichTextString(line[i]));
			} else {
				cell.setCellStyle(dtlHeaderCellStyle);
				cell.setCellValue(line[i]);
			}
		}
		// 合併儲存格(firstRow, endRow, firstCell, endCell)
		sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1, rowIndex - 1, 1, 3));
		sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1, rowIndex - 1, 6, 7));
		sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1, rowIndex - 1, 8, 12));

		// 第6行
		rowIndex += 1;
		cellIndex = 9;
		row = sheet.createRow(rowIndex++);
		line = new String[]{ "第一次請款", "", "第二次請款", "" };
		for (int i = 0; i < line.length; i++) {
			cell = row.createCell(cellIndex++);
			cell.setCellStyle(dtlHeaderCellStyle);
			if (!line[i].equals("")) {
				cell.setCellValue(line[i]);
			}
		}
		// 合併儲存格(firstRow, endRow, firstCell, endCell)
		sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1, rowIndex - 1, 9, 10));
		sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1, rowIndex - 1, 11, 12));

		// 第7行
		cellIndex = 0;
		row = sheet.createRow(rowIndex++);
		line = new String[]{ "請款類別", "請款日期", "發票日期", "發票號碼", "請款項目說明", "",
				"單價NT$(含稅)", "請款數量", "請款總額NT$(含稅)", "百分比", "金額(含稅)", "百分比",
				"金額(含稅)" };
		for (int i = 0; i < line.length; i++) {
			cell = row.createCell(cellIndex++);
			cell.setCellStyle(dtlHeaderCellStyle);
			if (!line[i].equals("")) {
				cell.setCellValue(line[i]);
			}
		}
		// 合併儲存格(firstRow, endRow, firstCell, endCell)
		sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1, rowIndex - 1, 4, 5));

		// 第8行
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		for (int i = 0; i < list1.size(); i++) {
			cellIndex = 0;
			row = sheet.createRow(rowIndex++);
			TbPayOutsourceAcceptanceDtlDTO tb = list1.get(i);
			line = new String[] {
					"已請款記錄",
					tb.getSUBPOENA_DATE() == null ? "" : sdf.format(tb.getSUBPOENA_DATE()),
					tb.getVOUCHER_DATE() == null ? "" : sdf.format(tb.getVOUCHER_DATE()), tb.getVOUCHER_NBR(),
					tb.getORDER_ITEM_DESC(), "", String.valueOf(tb.getUNIT_PRICE().intValue()),
					tb.getAPP_QTY().toString(), String.valueOf(tb.getAP_AMT().intValue()), "",
					"", "", "" };
			for (int j = 0; j < line.length; j++) {
				cell = row.createCell(cellIndex++);
				if (j == 0) {
					cell.setCellStyle(dtlHeaderCellStyle);
					cell.setCellValue(line[j]);
				} else if (line[j] == null || line[j].equals("")) {
					cell.setCellStyle(dtlLeftCellStyle);
				} else {
					cell.setCellStyle(dtlLeftCellStyle);
					cell.setCellValue(line[j]);
				}
			}
			// 合併儲存格(firstRow, endRow, firstCell, endCell)
			sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1, rowIndex - 1, 4, 5));
		}

		// 第9行
		for (int i = 0; i < list2.size(); i++) {
			cellIndex = 0;
			row = sheet.createRow(rowIndex++);
			TbPayOutsourceAcceptanceDtlDTO tb = list2.get(i);
			line = new String[] {
					"本次請款",
					tb.getSUBPOENA_DATE() == null ? "" : sdf.format(tb.getSUBPOENA_DATE()),
					tb.getVOUCHER_DATE() == null ? "" : sdf.format(tb.getVOUCHER_DATE()), tb.getVOUCHER_NBR(),
					tb.getORDER_ITEM_DESC(), "",
					String.valueOf(tb.getUNIT_PRICE().intValue()),
					tb.getAPP_QTY().toString(),
					String.valueOf(tb.getAP_AMT().intValue()), "", "", "", "" };
			for (int j = 0; j < line.length; j++) {
				cell = row.createCell(cellIndex++);
				if (j == 0) {
					cell.setCellStyle(dtlHeaderCellStyle);
					cell.setCellValue(line[j]);
				}else if (line[j] == null || line[j].equals("")) {
					cell.setCellStyle(dtlLeftCellStyle);
				} else {
					cell.setCellStyle(dtlLeftCellStyle);
					cell.setCellValue(line[j]);
				}
			}
			// 合併儲存格(firstRow, endRow, firstCell, endCell)
			sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1, rowIndex - 1, 4, 5));
		}

		// 第10行
		cellIndex = 0;
		row = sheet.createRow(rowIndex++);
		line = new String[] { "", "", "", "", "", "", "", "", "累計：", "", "",
				"", "" };
		for (int i = 0; i < line.length; i++) {
			cell = row.createCell(cellIndex++);
			if (line[i].equals("")) {
				cell.setCellStyle(dtlLeftCellStyle);
			} else {
				cell.setCellStyle(dtlCenterCellStyle);
				cell.setCellValue(line[i]);
			}
		}
		// 合併儲存格(firstRow, endRow, firstCell, endCell)
		sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1, rowIndex - 1, 9, 10));
		sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1, rowIndex - 1, 11, 12));

		// 第11行
		cellIndex = 0;
		row = sheet.createRow(rowIndex++);
		line = new String[] { "", "", "", "", "", "", "", "", "總計：", "", "",
				"", "" };
		for (int i = 0; i < line.length; i++) {
			cell = row.createCell(cellIndex++);
			if (line[i].equals("")) {
				cell.setCellStyle(dtlLeftCellStyle);
			} else {
				cell.setCellStyle(dtlCenterCellStyle);
				cell.setCellValue(line[i]);
			}
		}
		// 合併儲存格(firstRow, endRow, firstCell, endCell)
		sheet.addMergedRegion(new CellRangeAddress(rowIndex - 2, rowIndex - 1, 0, 7));
		sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1, rowIndex - 1, 9, 12));

		// 第12行(表尾)
		cellIndex = 0;
		row = sheet.createRow(rowIndex++);
		line = new String[] { "PS：", "1. 設備請款需填寫「單價」欄位", "", "", "", "", "", "", "", "", "",
				"", "" };
		for (int i = 0; i < line.length; i++) {
			cell = row.createCell(cellIndex++);
			if (i == 0) {
				cell.setCellStyle(footerTitleCellStyle);
				cell.setCellValue(line[i]);
			} else if (line[i].equals("")) {
				cell.setCellStyle(footerDtlCellStyle);
			} else {
				cell.setCellStyle(footerDtlCellStyle);
				cell.setCellValue(line[i]);
			}
		}
		// 合併儲存格(firstRow, endRow, firstCell, endCell)
		sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1, rowIndex - 1, 1, 12));

		// 第13行(表尾)
		cellIndex = 0;
		row = sheet.createRow(rowIndex++);
		line = new String[] { "", "2. 工程請款因各基站之施工項目及請款不同，故不需填寫「單價」欄位", "", "", "", "", "", "", "", "", "",
				"", "" };
		for (int i = 0; i < line.length; i++) {
			cell = row.createCell(cellIndex++);
			if (line[i].equals("")) {
				cell.setCellStyle(footerTitleCellStyle);
			} else {
				cell.setCellStyle(footerDtlCellStyle);
				cell.setCellValue(line[i]);
			}
		}
		// 合併儲存格(firstRow, endRow, firstCell, endCell)
		sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1, rowIndex - 1, 1, 12));

		// 第15行(簽章)
		rowIndex += 1;
		cellIndex = 0;
		row = sheet.createRow(rowIndex++);
		line = new String[] { "", "部級主管：", "", "", "", "", "", "經辦人：", "", "",
				"", "", "" };
		for (int i = 0; i < line.length; i++) {
			cell = row.createCell(cellIndex++);
			cell.setCellStyle(signCellStyle);
			if (!line[i].equals("")) {
				cell.setCellValue(line[i]);
			}
		}
		// 合併儲存格(firstRow, endRow, firstCell, endCell)
		sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1, rowIndex - 1, 1, 2));

		// 調整欄位寬度
		for (int i = 0; i < 13; i++) {
			if (i == 6) {
				sheet.setColumnWidth(i, 256 * 16);
			} else if (i == 8) {
				sheet.setColumnWidth(i, 256 * 18);
			} else {
				sheet.setColumnWidth(i, 256 * 12);
			}
		}

		// 匯入圖檔
		String imageLocation = "/resources/img/tstarlogo.png"; // 圖檔位置
		exportImageToExcel(session, imageLocation, sheet.getWorkbook(), sheet, 0, 0); // 匯出PNG圖檔至Excel

		// 匯出EXCEL檔案
		Writer.write(poiService.setResponse(response,fileName), sheet);
	}

	/**
	 * 匯出PNG圖檔至Excel
	 * @param session HttpSession
	 * @param imageLocation PNG圖檔路徑
	 * @param wb HSSFWorkbook
	 * @param sheet HSSFSheet
	 * @param row 圖檔放置行數
	 * @param col 圖檔放置列數
	 * @throws IOException
	 */
	private void exportImageToExcel(HttpSession session, String imageLocation,
			HSSFWorkbook wb, HSSFSheet sheet, int row, int col)
			throws IOException {
		// Add picture data to this workbook
		InputStream inputStream = session.getServletContext().getResourceAsStream(imageLocation);
		byte[] bytes = IOUtils.toByteArray(inputStream);
		int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
		inputStream.close();

		// Create the drawing patriarch. This is the top level container for all shapes
		Drawing drawing = sheet.createDrawingPatriarch();
		CreationHelper helper = wb.getCreationHelper();

		// Add a picture shape
		ClientAnchor anchor = helper.createClientAnchor();
		anchor.setRow1(row);
		anchor.setCol1(col);
		Picture pict = drawing.createPicture(anchor, pictureIdx);

		// Auto-size picture relative to its top-left corner
		pict.resize();
	}

	/**
	 * 取得站點工程資料
	 * @param example
	 * @return
	 */
	public List<TbSiteOutsourcingDTO> selectSiteOutsourcingByExamplePage(UTbSiteOutsourcingExample example) {
		return pay006Dao.selectSiteOutsourcingByExamplePage(example);
	}

	/**
	 * 取得站點工程明細資料
	 * @param example
	 * @return
	 */
	public List<TbSiteOsItemDTO> selectSiteOsItemByExample(UTbSiteOsItemExample example) {
		return pay006Dao.selectSiteOsItemByExample(example);
	}

	/**
	 * 取得站點工程相關金額加總
	 * @param example
	 * @return
	 */
	public TbSiteOutsourcingDTO selectSiteOutsourcingSumAmountByExample(UTbSiteOutsourcingExample example) {
		return pay006Dao.selectSiteOutsourcingSumAmountByExample(example);
	}

	/**
	 * 新增站點工程驗收資料
	 * @param map
	 */
	@Transactional
	public void insertSiteOutsourcing(Map<String, String> map) throws NomsException {
		try {
			// 產生憑證處理單號
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Map<String, Object> seqNoMap = new HashMap<String, Object>();
			seqNoMap.put("PI_PREFIX", "OSA");
			seqNoMap.put("PI_APP_DATE", sdf.format(new Date()));
			seqNoMap.put("PO_SEQNO", null);
			seqNoMap.put("PO_ERR_CDE", null);
			seqNoMap.put("PO_ERR_MSG", null);
			pay006Dao.callPayPcGetSeqnoByMap(seqNoMap);
			if (!seqNoMap.get("PO_ERR_CDE").equals("00")) {
				log.error("自動產生憑證處理單號Call PAY_PC_GET_SEQNO Error：ERR_CDE=" + seqNoMap.get("PO_ERR_CDE")
						+ ", PO_ERR_MSG=" + seqNoMap.get("PO_ERR_MSG"));
				throw new NomsException("99", "自動產生憑證處理單號執行錯誤：" + seqNoMap.get("PO_ERR_MSG"));
			}
			String apNo = (String) seqNoMap.get("PO_SEQNO");

			// 新增驗收主檔資料
			sdf = new SimpleDateFormat("yyyy/MM/dd");
			TbPayOutsourceAcceptanceDTO record = new TbPayOutsourceAcceptanceDTO();
			record.setAP_NO(apNo);
			record.setDOMAIN(map.get("domain"));
			record.setCO_VAT_NO(map.get("co_vat_no"));
			record.setPO_NO(map.get("po_no"));
			record.setLEASE_NO(null); // XXX 來自租約模組待確認
			record.setAP_DATE(sdf.parse(map.get("apl_time")));
			record.setAPP_USER(map.get("apl_user"));
			record.setAPP_DATE(sdf.parse(map.get("apl_time")));
			record.setSTATUS("A");
			record.setCR_USER(getLoginUser().getUsername());
			record.setCR_TIME(new Date());
			record.setMD_USER(getLoginUser().getUsername());
			record.setMD_TIME(new Date());
			pay006Dao.insertPayOutsourceAcceptanceSelective(record);

			// 新增工程驗收主檔資料
			String[] orderIds = map.get("order_ids").split(",");
			String[] siteIds = map.get("site_ids").split(",");
			String[] osIds = map.get("os_ids").split(",");
			for (int i = 0; i < orderIds.length; i++) {
				// 先檢查是否已有Primary Key資料
				UTbPayAcceptanceOrderExample exampleOrder = new UTbPayAcceptanceOrderExample();
				UTbPayAcceptanceOrderExample.Criteria cr = exampleOrder.createCriteria();
				cr.andMST_AP_NOEqualTo(apNo);
				cr.andPO_NOEqualTo(map.get("po_no"));
				cr.andORDER_NOEqualTo(orderIds[i]);
				int count = pay006Dao.countPayAcceptanceOrderByExample(exampleOrder);
				if (count > 0) {
					log.error("Acceptance Order資料已存在無法新增：MST_AP_NO=" + apNo
							+ ", PO_NO=" + map.get("po_no") + ", ORDER_NO="
							+ orderIds[i]);
					throw new NomsException("99", "Acceptance Order資料已存在無法新增");
				}

				// 取得租約編號
				String leaseNo = null;
				String payBeginDate = sdf.format(new Date());
				String lsEDate = sdf.format(new Date());
				sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
				TbLsSiteExample exampleLsSite = new TbLsSiteExample();
				TbLsSiteExample.Criteria crLsSite = exampleLsSite.createCriteria();
				crLsSite.andSITE_IDEqualTo(siteIds[i]);
				crLsSite.andPAY_BEGIN_DATEBetween(sdf.parse(payBeginDate + " 00:00:00.000"), sdf.parse(payBeginDate + " 23:59:59.999"));
				crLsSite.andLS_E_DATEBetween(sdf.parse(lsEDate + " 00:00:00.000"), sdf.parse(lsEDate + " 23:59:59.999"));
				List<TbLsSite> lsSiteList = pay006Dao.selectLsSiteByExample(exampleLsSite);
				if (lsSiteList.size() > 0) {
					leaseNo = lsSiteList.get(0).getLS_NO();
				}
				
				// 新增資料
				TbPayAcceptanceOrderDTO recordOrder = new TbPayAcceptanceOrderDTO();
				recordOrder.setMST_AP_NO(apNo);
				recordOrder.setPO_NO(map.get("po_no"));
				recordOrder.setORDER_NO(orderIds[i]);
				recordOrder.setSITE_ID(siteIds[i]);
				recordOrder.setLEASE_NO(leaseNo);
				recordOrder.setOS_ID(osIds[i]);
				recordOrder.setCR_USER(getLoginUser().getUsername());
				recordOrder.setCR_TIME(new Date());
				recordOrder.setMD_USER(getLoginUser().getUsername());
				recordOrder.setMD_TIME(new Date());
				pay006Dao.insertPayAcceptanceOrderSelective(recordOrder);
			}

			// 新增工程驗收明細資料
			String[] mstOrderIds = map.get("mst_order_ids").split(",");
			String[] itemIds = map.get("item_ids").split(",");
			String[] prices = map.get("prices").split(",");
			String[] numbers = map.get("numbers").split(",");
			String[] apNumbers = map.get("ap_numbers").split(",");
			String[] realApNumbers = map.get("real_ap_numbers").split(",");
			for (int i = 0; i < mstOrderIds.length; i++) {
				// 先檢查是否已有Primary Key資料
				UTbPayOutsourceAcceptanceDtlExample exampleDtl = new UTbPayOutsourceAcceptanceDtlExample();
				UTbPayOutsourceAcceptanceDtlExample.Criteria cr = exampleDtl.createCriteria();
				cr.andMST_AP_NOEqualTo(apNo);
				cr.andPO_NOEqualTo(map.get("po_no"));
				cr.andORDER_NOEqualTo(orderIds[i]);
				cr.andORDER_ITEMEqualTo(itemIds[i]);
				cr.andOS_IDEqualTo(osIds[i]);
				List<TbPayOutsourceAcceptanceDtlDTO> dtlList = pay006Dao.selectPayOutsourceAcceptanceDtlByExample(exampleDtl);
				if (dtlList.size() > 0) {
					log.error("Outsource Acceptance Dtl資料已存在無法新增：MST_AP_NO="
							+ apNo + ", PO_NO=" + map.get("po_no")
							+ ", ORDER_NO=" + orderIds[i] + ", ORDER_ITEM="
							+ itemIds[i]);
					throw new NomsException("99", "Outsource Acceptance Dtl資料已存在無法新增");
				}

				// 新增資料
				TbPayOutsourceAcceptanceDtlDTO recordDtl = new TbPayOutsourceAcceptanceDtlDTO();
				recordDtl.setMST_AP_NO(apNo);
				recordDtl.setPO_NO(map.get("po_no"));
				recordDtl.setORDER_NO(mstOrderIds[i]);
				recordDtl.setORDER_ITEM(itemIds[i]);
				recordDtl.setOS_ID(osIds[i]);
				recordDtl.setUNIT_PRICE(new BigDecimal(prices[i]));
				recordDtl.setAPP_QTY(Integer.valueOf(numbers[i]));
				recordDtl.setAP_QTY(Integer.valueOf(apNumbers[i]));
				recordDtl.setREAL_AP_QTY(Integer.valueOf(realApNumbers[i]));

				// 取得管理費設定值
				TbComItemMainExample comItemMainExample = new TbComItemMainExample();
				comItemMainExample.createCriteria().andITEM_IDEqualTo(itemIds[i]);
				List<TbComItemMain> comItemMainList = pay006Dao.selectComItemMainByExample(comItemMainExample);
				if (comItemMainList.size() == 0) {
					throw new NomsException("99", "無法取得工單為" + itemIds[i] + "的管理費設定值");
				}
				Integer mgrFee = comItemMainList.get(0).getMGR_FEE() == null ? 0 : comItemMainList.get(0).getMGR_FEE(); // 管理費設定值

				// 計算未稅金額
				Integer taxExclusiveAmt = Integer.valueOf(prices[i]) * recordDtl.getREAL_AP_QTY() * (1 + mgrFee);
				recordDtl.setTAX_EXCLUSIVE_AMT(BigDecimal.valueOf(taxExclusiveAmt));

				// 取得營業稅business_tax
				Map<String, Object> taxRateMap = new HashMap<String, Object>();
				taxRateMap.put("PI_TYPE", "3"); // 計算營業稅
				taxRateMap.put("PI_AMT", recordDtl.getTAX_EXCLUSIVE_AMT()); // 傳入未稅金額tax_exclusive_amt
				taxRateMap.put("PI_INCLUDE_TAX", "N"); // 傳入金額是否含稅N:未稅
				pay006Dao.callPayFnGetTax(taxRateMap);
				recordDtl.setBUSINESS_TAX(new BigDecimal(((Integer) taxRateMap.get("RETURN_VALUE")).toString()));

				recordDtl.setCR_USER(getLoginUser().getUsername());
				recordDtl.setCR_TIME(new Date());
				recordDtl.setMD_USER(getLoginUser().getUsername());
				recordDtl.setMD_TIME(new Date());
				pay006Dao.insertPayOutsourceAcceptanceDtlSelective(recordDtl);
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new NomsException("99", e.getMessage());
		}
	}

	/**
	 * 取得部門資料
	 * @param example
	 * @return
	 */
	public List<TbOrgDept> selectOrgDeptByExample(TbOrgDeptExample example) {
		return pay006Dao.selectOrgDeptByExample(example);
	}

	/**
	 * 取得所得稅/健保費/營業稅
	 * @param map
	 */
	public void callPayFnGetTax(Map<String, Object> map) {
		pay006Dao.callPayFnGetTax(map);
	}
}
