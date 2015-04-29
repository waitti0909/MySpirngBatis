package com.foya.noms.poi;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
    
public class Layouter {  
  	
    /** 
     * 建立報表 
     */  
    public static void buildPSNReport(HSSFSheet worksheet, int startRowIndex,  int startColIndex, int maxColumn) { 	
        // 設定行的寬度  
    	for(int i=0;i<maxColumn;i++){
    		worksheet.setColumnWidth(i, 5000);  
    	}        
        //建立報表標題與日期
       // buildPSNTitle(worksheet, startRowIndex, startColIndex);  
        //建立欄位名稱
         buildPSNHeaders(worksheet, startRowIndex, startColIndex);    
    }  
  
//    /** 
//     * 建立報表標題與日期 
//     */  
//    private static void buildPSNTitle(HSSFSheet worksheet, int startRowIndex, int startColIndex) {  
//        // 設定標題字體  
//        Font fontTitle = worksheet.getWorkbook().createFont();  
//        fontTitle.setBoldweight(Font.BOLDWEIGHT_BOLD);  //加粗字體
//        fontTitle.setFontHeight((short) 280);  //字體大小
//  
//        // 標題儲存格樣式  cellStyleTitle
//        HSSFCellStyle cellStyleTitle = worksheet.getWorkbook().createCellStyle();  
//        cellStyleTitle.setAlignment(CellStyle.ALIGN_CENTER);  //水平置中
//        cellStyleTitle.setWrapText(true);  //自動換行
//        cellStyleTitle.setFont(fontTitle);  //設定字體
//  
//        // 報表標題  
//        HSSFRow rowTitle = worksheet.createRow((short) startRowIndex);
//        rowTitle.setHeight((short) 500);  //設定行高
//        HSSFCell cellTitle = rowTitle.createCell(startColIndex);  //建立儲存格
//        cellTitle.setCellValue("Personnel Table");  
//        cellTitle.setCellStyle(cellStyleTitle);  //設定儲存格樣式
//  
//        // 標題_合併儲存格  
//        worksheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 14));  
//  
//        // 日期標題  
//        HSSFRow dateTitle = worksheet.createRow((short) startRowIndex + 1);
//        HSSFCell cellDate = dateTitle.createCell(startColIndex);  
//        cellDate.setCellValue("報表建立時間 : " + DateUtils.currentTime());  
//    }  
  
    /** 
     * 建立欄位名稱與樣式
     */  
    private static void buildPSNHeaders(HSSFSheet worksheet, int startRowIndex, int startColIndex) {  
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
  
        // 建立欄位名稱  
        //HSSFRow rowHeader = worksheet.createRow((short) startRowIndex + 2);  
        HSSFRow rowHeader = worksheet.createRow((short) startRowIndex); 
        rowHeader.setHeight((short) 500);  
  
        HSSFCell cell1 = rowHeader.createCell(startColIndex + 0);  
        cell1.setCellValue("PSN_ID");  
        cell1.setCellStyle(headerCellStyle);  
  
        HSSFCell cell2 = rowHeader.createCell(startColIndex + 1);  
        cell2.setCellValue("PSN_NO");  
        cell2.setCellStyle(headerCellStyle);  
  
        HSSFCell cell3 = rowHeader.createCell(startColIndex + 2);  
        cell3.setCellValue("PSN_PWD");  
        cell3.setCellStyle(headerCellStyle);  
  
        HSSFCell cell4 = rowHeader.createCell(startColIndex + 3);  
        cell4.setCellValue("CHN_NM");  
        cell4.setCellStyle(headerCellStyle);  
  
        HSSFCell cell5 = rowHeader.createCell(startColIndex + 4);  
        cell5.setCellValue("ENG_NM");  
        cell5.setCellStyle(headerCellStyle);  
  
        HSSFCell cell6 = rowHeader.createCell(startColIndex + 5);  
        cell6.setCellValue("DEPTID");  
        cell6.setCellStyle(headerCellStyle);
        
        HSSFCell cell7 = rowHeader.createCell(startColIndex + 6);  
        cell7.setCellValue("JOB_TTL");  
        cell7.setCellStyle(headerCellStyle);
        
        HSSFCell cell8 = rowHeader.createCell(startColIndex + 7);  
        cell8.setCellValue("MBL_NBR");  
        cell8.setCellStyle(headerCellStyle);
        
        HSSFCell cell9 = rowHeader.createCell(startColIndex + 8);  
        cell9.setCellValue("E_MAIL");  
        cell9.setCellStyle(headerCellStyle);
        
        HSSFCell cell10 = rowHeader.createCell(startColIndex + 9);  
        cell10.setCellValue("EXT_NO");  
        cell10.setCellStyle(headerCellStyle);
        
        HSSFCell cell11 = rowHeader.createCell(startColIndex + 10);  
        cell11.setCellValue("NO_JOB");  
        cell11.setCellStyle(headerCellStyle);
        
        HSSFCell cell12 = rowHeader.createCell(startColIndex + 11);  
        cell12.setCellValue("DISMISSION");  
        cell12.setCellStyle(headerCellStyle);
        
        HSSFCell cell13 = rowHeader.createCell(startColIndex + 12);  
        cell13.setCellValue("EMP_USER_ID");  
        cell13.setCellStyle(headerCellStyle);
        
        HSSFCell cell14 = rowHeader.createCell(startColIndex + 13);  
        cell14.setCellValue("MDY_USER_ID");  
        cell14.setCellStyle(headerCellStyle);
        
        HSSFCell cell15 = rowHeader.createCell(startColIndex + 14);  
        cell15.setCellValue("MDY_TSTMP");  
        cell15.setCellStyle(headerCellStyle);  
    }   
}
