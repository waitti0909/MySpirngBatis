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

import com.foya.noms.dao.inv.INV022Dao;
import com.foya.noms.dto.inv.TbInvMaterialDTO;
import com.foya.noms.poi.Writer;
import com.foya.noms.service.BaseService;
import com.foya.noms.service.common.PoiService;

@Service
public class INV022Service extends BaseService {
	@Autowired
	private INV022Dao inv022Dao;
	@Autowired
	private PoiService poiService;

	/**
	 * 
	 * @param dataObj
	 * @return
	 */
	public List<TbInvMaterialDTO> search(HashMap<String,String> dataObj){
		return inv022Dao.search(dataObj);
	}
	
	/**
	 * 
	 * @param dataObj
	 * @return
	 */
	public List<TbInvMaterialDTO> getMaterialDtoData(HashMap<String,Object> dataObj){
		
		return inv022Dao.getMaterialDtoData(dataObj);
	}
	
	public void exportExcel(HttpServletResponse response, String sheetName, String fileName, List<TbInvMaterialDTO> result) {
		HSSFSheet worksheet = poiService.exportXLS(response, sheetName, fileName, 0, 0, 12);
		// 建立報表的 標題、日期、欄位名稱
		buildExcel(worksheet, result);
		// 輸出流
		Writer.write(poiService.setResponse(response,fileName+".xls"), worksheet);
	}
	
	/** 
     * 建立欄位名稱與樣式
     */  
    private static void buildExcel(HSSFSheet worksheet, List<TbInvMaterialDTO> result) {  
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
        String[] title = {"類別代碼", "類別", "中分類代碼", "中分類","小分類代碼","小分類","料號","品名","單位","控管方式","認列資產","資料類別"};
        
        // 建立欄位名稱 
		HSSFRow header = worksheet.createRow(0);
		for(int i=0; i<title.length; i++){
			header.createCell(i).setCellValue(title[i]);
		}
		
		//寫入資料
		int rowIdx = 1;
		for(int i=0; i<result.size(); i++){
			HSSFRow excelRow = worksheet.createRow(rowIdx);		
			
			TbInvMaterialDTO material = (TbInvMaterialDTO) result.get(i);			
			excelRow.createCell(0).setCellValue(material.getCatg1_code());
			excelRow.createCell(1).setCellValue(material.getCatg1_name());
			excelRow.createCell(2).setCellValue(material.getCatg2_code());
			excelRow.createCell(3).setCellValue(material.getCatg2_name());
			excelRow.createCell(4).setCellValue(material.getCatg3_code());
			excelRow.createCell(5).setCellValue(material.getCatg3_name());
			excelRow.createCell(6).setCellValue(material.getMat_no());
			excelRow.createCell(7).setCellValue(material.getMat_name());
			excelRow.createCell(8).setCellValue(material.getUnit());
			excelRow.createCell(9).setCellValue(material.getCtrl_type_dscr());
			excelRow.createCell(10).setCellValue(material.getIs_asset_dscr());
			//excelRow.createCell(11).setCellValue(material.getAsset_type_dscr());
			rowIdx++;
		}	
    }
	
}
