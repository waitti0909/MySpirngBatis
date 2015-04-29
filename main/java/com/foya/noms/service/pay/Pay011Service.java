package com.foya.noms.service.pay;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.foya.dao.mybatis.model.TbLsLocElec;
import com.foya.exception.NomsException;
import com.foya.noms.dao.pay.Pay011Dao;
import com.foya.noms.dto.pay.TbLsLocElecDTO;
import com.foya.noms.dto.pay.TbPayElectricityDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestDtlDTO;
import com.foya.noms.dto.pay.TbPayRepartitionDTO;
import com.foya.noms.poi.Writer;
import com.foya.noms.service.BaseService;
import com.foya.noms.service.common.PoiService;

@Service
public class Pay011Service extends BaseService {
	@Inject 
	private Pay011Dao pay011Dao;
	@Autowired
	private PoiService poiService;
	
	public List<TbPayElectricityDTO> selectTbPayElectricity(String domain,String electricityType, String btsSiteId, String electricityMeterNbr,
			Date paymentReqStartDate, Date paymentReqEndDate)throws ParseException {
		HashMap<String, Object> dataObj = new HashMap<String, Object>();
		dataObj.put("domain", domain);
		dataObj.put("electricityType", electricityType);
		dataObj.put("btsSiteId", btsSiteId);
		dataObj.put("electricityMeterNbr",electricityMeterNbr);
		dataObj.put("paymentReqStartDate", paymentReqStartDate);
		dataObj.put("paymentReqEndDate", paymentReqEndDate);
		List<TbPayElectricityDTO> list = pay011Dao.selectTbPayElectricity(dataObj);
		return list;
	}
	
	public List<TbPayPaymentRequestDtlDTO> selectTbPayPaymentRequestDtlByPAY011(String contractNo, String electricityMeterNbr, String appDate,
			String paymentReqNo) throws ParseException {
		HashMap<String, Object> dataObj = new HashMap<String, Object>();
		dataObj.put("contractNo", contractNo);
		dataObj.put("electricityMeterNbr", electricityMeterNbr);
		dataObj.put("appDate", appDate);
		dataObj.put("paymentReqNo", paymentReqNo);
		List<TbPayPaymentRequestDtlDTO> list = pay011Dao.selectTbPayPaymentRequestDtlByPAY011(dataObj);
		return list;
	}
	
	// 依租約編號、電錶號碼、申請日期取得基站編號下拉選單資料
	public List<TbLsLocElecDTO> getSiteIdList(String contractNo, String electricityMeterNbr, String appDate) throws ParseException {
		HashMap<String, Object> dataObj = new HashMap<String, Object>();
		dataObj.put("contractNo", contractNo);
		dataObj.put("electricityMeterNbr", electricityMeterNbr);
		dataObj.put("appDate", appDate);
		return pay011Dao.getSiteIdList(dataObj);
	}
	
	// 取得用電地址、戶名及計算營業稅額所需站點代碼
	public TbLsLocElec getTbLsLocElec(String spContractNo, String spSiteId, String spAppDate, String spElectricityMeterNbr)throws ParseException  {
		HashMap<String, Object> dataObj = new HashMap<String, Object>();
		dataObj.put("contractNo", spContractNo);
		dataObj.put("siteId", spSiteId);
		dataObj.put("appDate", spAppDate);
		dataObj.put("electricityMeterNbr", spElectricityMeterNbr);
		return pay011Dao.getTbLsLocElec(dataObj);
	}
	
	// 取得憑證號碼:組成規則'OTH'+今天日期(yyyymmdd)＋9999(四位數流水號，每日從0001開始,左補零)
	public String selectVoucherNbrToday(String toDay) throws NomsException {
		// 產生憑證單號
		Map<String, Object> seqNoMap = new HashMap<String, Object>();
		seqNoMap.put("PI_PREFIX", "OTH");
		seqNoMap.put("PI_APP_DATE", toDay);
		seqNoMap.put("PO_SEQNO", null);
		seqNoMap.put("PO_ERR_CDE", null);
		seqNoMap.put("PO_ERR_MSG", null);
		pay011Dao.callPayPcGetSeqnoByMap(seqNoMap);
		if (!seqNoMap.get("PO_ERR_CDE").equals("00")) {
			log.error("自動產生憑證處理單號Call PAY_PC_GET_SEQNO Error：ERR_CDE="
					+ seqNoMap.get("PO_ERR_CDE") + ", PO_ERR_MSG="
					+ seqNoMap.get("PO_ERR_MSG"));
			throw new NomsException("99", "自動產生憑證處理單號執行錯誤：" + seqNoMap.get("PO_ERR_MSG"));
		}
		String returnVucherNbr = (String) seqNoMap.get("PO_SEQNO");
		return returnVucherNbr;
	}

	// 取得Excel報表資料
	public List<TbPayRepartitionDTO> selectTbPayRepartitionByPay011(String spContractNo, String locationId, String spPaymentReqNo,String spSiteId)throws ParseException {
		HashMap<String, Object> dataObj = new HashMap<String, Object>();
		dataObj.put("contractNo", spContractNo);
		dataObj.put("locationId", locationId);
		dataObj.put("paymentReqNo", spPaymentReqNo);
		dataObj.put("siteId", spSiteId);
		List<TbPayRepartitionDTO> list = pay011Dao.selectTbPayRepartitionByPay011(dataObj);
		return list;
	}
	
	public void exportExcel(HttpServletResponse response, String sheetName, String fileName, String elecAddr, String elecCustName,
			 String spElectricityMeterNbr, String siteIdDscr, String voucherNbr, List<TbPayRepartitionDTO> result) {
		HSSFSheet worksheet = poiService.exportXLS(response, sheetName, fileName, 0, 0, 4);
		// 建立報表的 標題、日期、欄位名稱
		buildExcel(worksheet, result, elecAddr, elecCustName, spElectricityMeterNbr, siteIdDscr , voucherNbr);
		// 輸出流
		Writer.write(poiService.setResponse(response, fileName), worksheet);
	}
	
	/** 
     * 建立欄位名稱與樣式
     */
	private static void buildExcel(HSSFSheet worksheet, List<TbPayRepartitionDTO> result, String elecAddr,
			String elecCustName, String spElectricityMeterNbr, String siteIdDscr, String voucherNbr) {
		// 創建row & cell
		HSSFRow row;
		HSSFCell cell;
    	/**********************************設置Font Style Start******************************************/
    	// 字型設定1:標楷體、粗體
		Font font1 = worksheet.getWorkbook().createFont();
		font1.setFontHeightInPoints((short)12);
		font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 粗體
		font1.setFontName("標楷體");
		
    	// 字型設定2:標楷體、粗體、字體藍色
		Font font2 = worksheet.getWorkbook().createFont();
		font2.setFontHeightInPoints((short)12);
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 粗體
		font2.setFontName("標楷體");
		font2.setColor(HSSFColor.BLUE.index);
		
    	// 字型設定3:標楷體、粗體、字體紅色
		Font font3 = worksheet.getWorkbook().createFont();
		font3.setFontHeightInPoints((short)12);
		font3.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 粗體
		font3.setFontName("標楷體");
		font3.setColor(HSSFColor.RED.index);
		
    	// 字型設定4:標楷體
		Font font4 = worksheet.getWorkbook().createFont();
		font4.setFontHeightInPoints((short)12);
		font4.setFontName("標楷體");
		
    	// 字型設定5:標楷體、字體10
		Font font5 = worksheet.getWorkbook().createFont();
		font5.setFontHeightInPoints((short)10);
		font5.setFontName("標楷體");
		
		// 表頭儲存格樣式設定1:標楷體、置中
		HSSFCellStyle headStyle1 = worksheet.getWorkbook().createCellStyle();
		headStyle1.setAlignment(CellStyle.ALIGN_CENTER);
		headStyle1.setFont(font1);
		
		// 表頭儲存格樣式設定2:標楷體、左靠
		HSSFCellStyle headStyle2 = worksheet.getWorkbook().createCellStyle();
		headStyle2.setAlignment(CellStyle.ALIGN_LEFT);
		headStyle2.setFont(font1);
		
		// 表頭儲存格樣式設定3:標楷體、右靠
		HSSFCellStyle headStyle3 = worksheet.getWorkbook().createCellStyle();
		headStyle3.setAlignment(CellStyle.ALIGN_RIGHT);
		headStyle3.setFont(font1);
		
		// 表頭儲存格樣式設定4:標楷體、左靠、字體藍色
		HSSFCellStyle headStyle4 = worksheet.getWorkbook().createCellStyle();
		headStyle4.setAlignment(CellStyle.ALIGN_LEFT);
		headStyle4.setFont(font2);
		
		// 表頭儲存格樣式設定5:標楷體、左靠、字體紅色
		HSSFCellStyle headStyle5 = worksheet.getWorkbook().createCellStyle();
		headStyle5.setAlignment(CellStyle.ALIGN_LEFT);
		headStyle5.setFont(font3);
		
		// 表頭儲存格樣式設定6:標楷體、左靠、字體10
		HSSFCellStyle headStyle6 = worksheet.getWorkbook().createCellStyle();
		headStyle6.setAlignment(CellStyle.ALIGN_LEFT);
		headStyle6.setFont(font5);
		
		// 標題儲存格樣式設定1:標楷體、粗體、置中對齊、框線左上粗框
		HSSFCellStyle titleStyle1 = worksheet.getWorkbook().createCellStyle();
		titleStyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		titleStyle1.setFont(font1);
		titleStyle1.setBorderTop((short) 2);
		titleStyle1.setBorderLeft((short) 2);
		titleStyle1.setBorderBottom((short) 1);
		titleStyle1.setBorderRight((short) 1);
		
		// 標題儲存格樣式設定2:標楷體、粗體、置中對齊、框線上粗框
		HSSFCellStyle titleStyle2 = worksheet.getWorkbook().createCellStyle();
		titleStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		titleStyle2.setFont(font1);
		titleStyle2.setBorderTop((short) 2);
		titleStyle2.setBorderLeft((short) 1);
		titleStyle2.setBorderBottom((short) 1);
		titleStyle2.setBorderRight((short) 1);

		// 標題儲存格樣式設定3:標楷體、粗體、置中對齊、框線右上粗框
		HSSFCellStyle titleStyle3 = worksheet.getWorkbook().createCellStyle();
		titleStyle3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		titleStyle3.setFont(font1);
		titleStyle3.setBorderTop((short) 2);
		titleStyle3.setBorderLeft((short) 1);
		titleStyle3.setBorderBottom((short) 1);
		titleStyle3.setBorderRight((short) 2);
		
		// 資料儲存格樣式設定1:標楷體、置中對齊、框線左粗框
		HSSFCellStyle dataStyle1 = worksheet.getWorkbook().createCellStyle();
		dataStyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		dataStyle1.setFont(font4);
		dataStyle1.setBorderTop((short) 1);
		dataStyle1.setBorderLeft((short) 2);
		dataStyle1.setBorderBottom((short) 1);
		dataStyle1.setBorderRight((short) 1);
		
		// 資料儲存格樣式設定2:標楷體、置中對齊、框線
		HSSFCellStyle dataStyle2 = worksheet.getWorkbook().createCellStyle();
		dataStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		dataStyle2.setFont(font4);
		dataStyle2.setBorderTop((short) 1);
		dataStyle2.setBorderLeft((short) 1);
		dataStyle2.setBorderBottom((short) 1);
		dataStyle2.setBorderRight((short) 1);

		// 資料儲存格樣式設定3:標楷體、靠右、框線、數字格式化
		HSSFCellStyle dataStyle3 = worksheet.getWorkbook().createCellStyle();
		dataStyle3.setAlignment(CellStyle.ALIGN_RIGHT);
		dataStyle3.setFont(font4);
		dataStyle3.setBorderTop((short) 1);
		dataStyle3.setBorderLeft((short) 1);
		dataStyle3.setBorderBottom((short) 1);
		dataStyle3.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));    

		// 資料儲存格樣式設定4:標楷體、置中對齊、框線右粗框
		HSSFCellStyle dataStyle4 = worksheet.getWorkbook().createCellStyle();
		dataStyle4.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		dataStyle4.setFont(font4);
		dataStyle4.setBorderTop((short) 1);
		dataStyle4.setBorderLeft((short) 1);
		dataStyle4.setBorderBottom((short) 1);
		dataStyle4.setBorderRight((short) 2);
		
		// 資料儲存格樣式設定5:標楷體、置中對齊、框線左粗框
		HSSFCellStyle dataStyle5 = worksheet.getWorkbook().createCellStyle();
		dataStyle5.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		dataStyle5.setFont(font1);
		dataStyle5.setBorderTop((short) 1);
		dataStyle5.setBorderLeft((short) 2);
		dataStyle5.setBorderBottom((short) 1);
		dataStyle5.setBorderRight((short) 1);
		
		// 資料儲存格樣式設定6:標楷體、置中對齊、框線左下粗框
		HSSFCellStyle dataStyle6 = worksheet.getWorkbook().createCellStyle();
		dataStyle6.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		dataStyle6.setFont(font1);
		dataStyle6.setBorderTop((short) 1);
		dataStyle6.setBorderLeft((short) 2);
		dataStyle6.setBorderBottom((short) 2);
		dataStyle6.setBorderRight((short) 1);
		
		// 資料儲存格樣式設定7:標楷體、置中對齊、框線下粗框、右無框線
		HSSFCellStyle dataStyle7 = worksheet.getWorkbook().createCellStyle();
		dataStyle7.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		dataStyle7.setFont(font1);
		dataStyle7.setBorderTop((short) 1);
		dataStyle7.setBorderLeft((short) 1);
		dataStyle7.setBorderBottom((short) 2);
		
		// 資料儲存格樣式設定8:標楷體、置中對齊、框線下粗框、左右無框線
		HSSFCellStyle dataStyle8 = worksheet.getWorkbook().createCellStyle();
		dataStyle8.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		dataStyle8.setFont(font1);
		dataStyle8.setBorderTop((short) 1);
		dataStyle8.setBorderBottom((short) 2);
		
		// 資料儲存格樣式設定9:標楷體、置中對齊、框線右下粗框、左無框線
		HSSFCellStyle dataStyle9 = worksheet.getWorkbook().createCellStyle();
		dataStyle9.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		dataStyle9.setFont(font1);
		dataStyle9.setBorderTop((short) 1);
		dataStyle9.setBorderBottom((short) 2);
		dataStyle9.setBorderRight((short) 2);

		// 設定表頭、表格資料合併儲存格
		worksheet.addMergedRegion(CellRangeAddress.valueOf("C2" + ":" + "G2"));
		worksheet.addMergedRegion(CellRangeAddress.valueOf("C3" + ":" + "D3"));
		worksheet.addMergedRegion(CellRangeAddress.valueOf("C4" + ":" + "D4"));
		worksheet.addMergedRegion(CellRangeAddress.valueOf("C5" + ":" + "D5"));
		worksheet.addMergedRegion(CellRangeAddress.valueOf("F5" + ":" + "G5"));
		worksheet.addMergedRegion(CellRangeAddress.valueOf("C6" + ":" + "E6"));
		worksheet.addMergedRegion(CellRangeAddress.valueOf("C7" + ":" + "D7"));
		worksheet.addMergedRegion(CellRangeAddress.valueOf("F7" + ":" + "H7"));
		
		// 設定欄位寬
		worksheet.setColumnWidth(1, 256 * 13);
		worksheet.setColumnWidth(2, 256 * 12);
		worksheet.setColumnWidth(3, 256 * 12);
		worksheet.setColumnWidth(4, 256 * 15);
		worksheet.setColumnWidth(5, 256 * 12);
		worksheet.setColumnWidth(6, 256 * 12);
		worksheet.setColumnWidth(7, 256 * 12);
        /**********************************設置Font Style End******************************************/
		// 填入表頭
		row = worksheet.createRow(1);
		cell = row.createCell(2);
		cell.setCellValue("_________年度分攤電費明細表");
		cell.setCellStyle(headStyle1);
        
		row = worksheet.createRow(2);
		cell = row.createCell(1);
		cell.setCellValue("用電戶名：");
		cell.setCellStyle(headStyle3);
		cell = row.createCell(2);
		cell.setCellValue(elecCustName);
		cell.setCellStyle(headStyle4);
		
		row = worksheet.createRow(3);
		cell = row.createCell(1);
		cell.setCellValue("用電地址：");
		cell.setCellStyle(headStyle3);
		cell = row.createCell(2);
		cell.setCellValue(elecAddr);
		cell.setCellStyle(headStyle4);
		
		row = worksheet.createRow(4);
		cell = row.createCell(1);
		cell.setCellValue("電號：");
		cell.setCellStyle(headStyle3);
		cell = row.createCell(2);
		cell.setCellValue(spElectricityMeterNbr);
		cell.setCellStyle(headStyle4);
		cell = row.createCell(4);
		cell.setCellValue("Site ID：");
		cell.setCellStyle(headStyle3);
		cell = row.createCell(5);
		cell.setCellValue(siteIdDscr);
		cell.setCellStyle(headStyle4);
		
		row = worksheet.createRow(5);
		cell = row.createCell(1);
		cell.setCellValue("公司名稱：");
		cell.setCellStyle(headStyle3);
		cell = row.createCell(2);
		cell.setCellValue("台灣之星股份有限公司");
		cell.setCellStyle(headStyle2);
		
		row = worksheet.createRow(6);
		cell = row.createCell(1);
		cell.setCellValue("統一編號：");
		cell.setCellStyle(headStyle3);
		cell = row.createCell(2);
		cell.setCellValue("70769567");
		cell.setCellStyle(headStyle2);
		cell = row.createCell(5);
		cell.setCellValue("憑證號碼："+ voucherNbr);
		cell.setCellStyle(headStyle5);
		
		// 填入標題
		String[] title = { "項次", "期間(起)", "期間(迄)", "金額(未稅)", "營業稅額", "合計(含稅)","備註" };
		row = worksheet.createRow(7);
		cell = row.createCell(1);// 項次
		cell.setCellValue(title[0]);
		cell.setCellStyle(titleStyle1);
		for (int i = 1; i < 6; i++) {
			cell = row.createCell(i + 1);
			cell.setCellValue(title[i]);
			cell.setCellStyle(titleStyle2);
		}
		cell = row.createCell(7);// 備註
		cell.setCellValue(title[6]);
		cell.setCellStyle(titleStyle3);

		// 填入資料
		int rowIdx = 8;
		int dataIdx = 1 ;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		for (int j = 0; j < result.size(); j++) {
			row = worksheet.createRow(rowIdx);
			TbPayRepartitionDTO pay = (TbPayRepartitionDTO) result.get(j);
			cell = row.createCell(1);
			cell.setCellValue(dataIdx++);// 項次
			cell.setCellStyle(dataStyle1);
			for (int k = 0; k < title.length; k++) {
				cell = row.createCell(2);
				cell.setCellValue(sdf.format(pay.getPaymentReqBeginDate()));
				cell.setCellStyle(dataStyle2);
				cell = row.createCell(3);
				cell.setCellValue(sdf.format(pay.getPaymentReqEndDate()));
				cell.setCellStyle(dataStyle2);
				cell = row.createCell(4);
				cell.setCellValue(pay.getREPARTITION_AMT().intValue());// 金額(未稅)
				cell.setCellStyle(dataStyle3);
				cell = row.createCell(5);
				cell.setCellValue(pay.getBusinessTaxAmt().intValue());// 營業稅額=金額(未稅)*營業稅率
				cell.setCellStyle(dataStyle3);
				cell = row.createCell(6);
				cell.setCellValue((pay.getREPARTITION_AMT().intValue())+(pay.getBusinessTaxAmt().intValue()));// 合計(含稅)=金額(未稅)+營業稅額
				cell.setCellStyle(dataStyle3);
				cell = row.createCell(7);
				cell.setCellValue("");
				cell.setCellStyle(dataStyle4);
			}
			rowIdx++;
		}
		
		// 計算總計顯示位置
		int totalIndex = 8 + result.size();
		// 填入總計
		row = worksheet.createRow(totalIndex);
		for (int t = 1; t < 8; t++) {
			if (t == 1) {// 第2列
				cell = row.createCell(t);
				cell.setCellValue("總計");
				cell.setCellStyle(dataStyle5);
			} else if (t < 4) {// 3~4列
				cell = row.createCell(t);
				cell.setCellStyle(dataStyle2);
			} else if (t ==4) {// 5~7列
				cell = row.createCell(t);
				cell.setCellFormula("SUM(E9:E"+totalIndex+")");
				cell.setCellStyle(dataStyle3);
			}  else if (t ==5) {// 5~7列
				cell = row.createCell(t);
				cell.setCellFormula("SUM(F9:F"+totalIndex+")");
				cell.setCellStyle(dataStyle3);
			} else if (t ==6) {// 5~7列
				cell = row.createCell(t);
				cell.setCellFormula("SUM(G9:G"+totalIndex+")");
				cell.setCellStyle(dataStyle3);
			} else {// 第8列
				cell = row.createCell(t);
				cell.setCellStyle(dataStyle4);
			} 
		}
		
		// 計算新台幣顯示位置
		int lastIndex = totalIndex+1;
		row = worksheet.createRow(lastIndex);
		cell = row.createCell(1);
		cell.setCellValue("新台幣");
		cell.setCellStyle(dataStyle6);
		cell = row.createCell(2);
		cell.setCellValue("拾    元整");
		cell.setCellStyle(dataStyle7);
		for (int s = 3;s < 7; s++) {
			if (s < 7) {// 4~7列
				cell = row.createCell(s);
				cell.setCellValue("");
				cell.setCellStyle(dataStyle8);
			} 
		}
		cell = row.createCell(7);
		cell.setCellValue("");
		cell.setCellStyle(dataStyle9);

		// 計算用電戶名、章、統一編號顯示位置
		int sealIndex = totalIndex+3;
		// 填入表尾
		row = worksheet.createRow(sealIndex);
		cell = row.createCell(4);
		cell.setCellValue("用電戶名：");
		cell.setCellStyle(headStyle3);
		cell = row.createCell(7);
		cell.setCellValue("章");
		cell.setCellStyle(headStyle3);
		
		// 計算統一編號顯示位置
		int nbrIndex = totalIndex+4;
		row = worksheet.createRow(nbrIndex);
		cell = row.createCell(4);
		cell.setCellValue("統一編號：");
		cell.setCellStyle(headStyle3);

		// 計算註釋顯示位置
		int noteIndex = totalIndex+5;
		row = worksheet.createRow(noteIndex);
		cell = row.createCell(1);
		cell.setCellValue("註：每站每年度簽訂本分攤表一張。");
		cell.setCellStyle(headStyle6);
    }

}
