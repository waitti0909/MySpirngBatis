package com.foya.noms.service.inv;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.dao.mybatis.model.UTbInvSiteTxnExample;
import com.foya.noms.dao.inv.INV026Dao;
import com.foya.noms.dto.inv.TbInvSiteTxnDTO;
import com.foya.noms.poi.Writer;
import com.foya.noms.service.BaseService;
import com.foya.noms.service.common.PoiService;

@Service
public class INV026Service extends BaseService {

	@Autowired
	private INV026Dao inv026Dao;
	@Autowired
	private PoiService poiService;
	
	/**
	 * 查詢資材歷程
	 * @param example
	 * @param maxRowNum 最大查詢筆數
	 * @return
	 */
	public List<TbInvSiteTxnDTO> selectInvSiteTxnByExampleForLimit(UTbInvSiteTxnExample example, int maxRowNum) {
		return inv026Dao.selectInvSiteTxnByExampleForLimit(example, maxRowNum);
	}

	/**
	 * 匯出資材歷程Excel報表
	 * @param response
	 * @param fileName
	 * @param list
	 */
	public void exportExcel(HttpServletResponse response, String fileName,
			List<TbInvSiteTxnDTO> list) {
		HSSFSheet sheet = poiService.exportXLS(response, "資材歷程", fileName, 0, 0, 12);

		 // 欄位字體  
        Font font = sheet.getWorkbook().createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗字體

		// 儲存格樣式 headerCellStyle
		HSSFCellStyle headerCellStyle = sheet.getWorkbook().createCellStyle();
		headerCellStyle.setFillBackgroundColor(HSSFColor.RED.index);
		headerCellStyle.setFillPattern(CellStyle.FINE_DOTS);
		headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER); // 左右置中
		headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 垂直置中
		headerCellStyle.setWrapText(true);
		headerCellStyle.setFont(font);
		headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN); // 設定儲存格為粗邊框

		// 標題
		String[] title = { "異動原因", "單號", "單位", "料號", "品名", "廠商序號", "貼標號碼",
				"數量", "日期", "人員", "站台" };

		// 建立欄位名稱 
		int rowIndex = 0;
		HSSFRow header = sheet.createRow(rowIndex++);
		for (int i = 0; i < title.length; i++) {
			header.createCell(i).setCellValue(title[i]);
		}

		// 寫入資料
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		for (int i = 0; i < list.size(); i++) {
			HSSFRow excelRow = sheet.createRow(rowIndex++);
			int cellIndex = 0;

			TbInvSiteTxnDTO tb = list.get(i);
			excelRow.createCell(cellIndex++).setCellValue(tb.getTxn_type_name());
			excelRow.createCell(cellIndex++).setCellValue(tb.getTxn_no());
			excelRow.createCell(cellIndex++).setCellValue(tb.getDept_name());
			excelRow.createCell(cellIndex++).setCellValue(tb.getMat_no());
			excelRow.createCell(cellIndex++).setCellValue(tb.getMat_name());
			excelRow.createCell(cellIndex++).setCellValue(tb.getVen_sn());
			excelRow.createCell(cellIndex++).setCellValue(tb.getTag_no());
			excelRow.createCell(cellIndex++).setCellValue(tb.getQty());
			excelRow.createCell(cellIndex++).setCellValue(sdf.format(tb.getCr_time()));
			excelRow.createCell(cellIndex++).setCellValue(tb.getCr_user_name());
			excelRow.createCell(cellIndex++).setCellValue(tb.getSite_name());
		}

		// 調整欄位寬度
		for (int i = 0; i < sheet.getRow(0).getLastCellNum(); i++) {
			sheet.setColumnWidth(i, 256 * 16);
		}

		// 匯出EXCEL檔案
		Writer.write(poiService.setResponse(response,fileName), sheet);
	}
}
