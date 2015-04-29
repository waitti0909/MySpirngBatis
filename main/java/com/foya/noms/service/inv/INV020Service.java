package com.foya.noms.service.inv;

import java.util.HashMap;
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

import com.foya.noms.dao.inv.INV020Dao;
import com.foya.noms.dto.inv.TbInvSrlDTO;
import com.foya.noms.dto.inv.TbInvTxnDTO;
import com.foya.noms.poi.Writer;
import com.foya.noms.service.BaseService;
import com.foya.noms.service.common.PoiService;

@Service
public class INV020Service extends BaseService {
	@Autowired
	private INV020Dao inv020Dao;
	@Autowired
	private PoiService poiService;

	/**
	 * 
	 * @param dataObj
	 * @return
	 */
	public List<TbInvSrlDTO> search(HashMap<String,String> dataObj){
		return inv020Dao.search(dataObj);
	}
	
	/**
	 * 
	 * @param dataObj
	 * @return
	 */
	public List<TbInvTxnDTO> searchDetail(HashMap<String,String> dataObj){
		return inv020Dao.searchDetail(dataObj);
	}
	public void exportExcel(HttpServletResponse response, String fileName, List<TbInvSrlDTO> resultMaster,List<TbInvSrlDTO> resultdetail) {
		HSSFSheet worksheet = poiService.exportXLS(response, "sheetName", fileName, 0, 0, 12);
		// 建立報表的 標題、日期、欄位名稱
		buildExcel(worksheet, resultMaster);
		// 輸出流
		Writer.write(poiService.setResponse(response,fileName), worksheet);
	}
	
	/** 
     * 建立欄位名稱與樣式
     */  
    private static void buildExcel(HSSFSheet worksheet, List<TbInvSrlDTO> result) {  
        // 欄位字體  
        Font font = worksheet.getWorkbook().createFont();  
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);  //加粗字體
  
        // 儲存格樣式 headerCellStyle
        HSSFCellStyle headerCellStyle = worksheet.getWorkbook().createCellStyle(); 
        headerCellStyle.setFillBackgroundColor(HSSFColor.RED.index);  
        headerCellStyle.setFillPattern(CellStyle.FINE_DOTS);  
        headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);  //左右置中
        headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);  //垂直置中
        headerCellStyle.setWrapText(true);  
        headerCellStyle.setFont(font);  
        headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);  //設定儲存格為粗邊框 
        

        //標題
        String[] title = {"料號", "廠商序號", "貼標號碼","品名","目前保管地"};
        
        // 建立欄位名稱 
		HSSFRow header = worksheet.createRow(0);
		for(int i=0; i<title.length; i++){
			header.createCell(i).setCellValue(title[i]);
		}
		
		//寫入資料a
		int rowIdx = 1;
		for(int i=0; i<result.size(); i++){
			HSSFRow excelRow = worksheet.createRow(rowIdx);		
			
			TbInvSrlDTO tbinvsrl = (TbInvSrlDTO) result.get(i);			
			excelRow.createCell(0).setCellValue(tbinvsrl.getMat_no());
			excelRow.createCell(1).setCellValue(tbinvsrl.getVen_sn());
			excelRow.createCell(2).setCellValue(tbinvsrl.getTag_no());
			excelRow.createCell(3).setCellValue(tbinvsrl.getMat_name());
			excelRow.createCell(4).setCellValue(tbinvsrl.getS_site_name());
			rowIdx++;
		}	
    }
	
}
