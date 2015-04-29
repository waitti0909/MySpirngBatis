package com.foya.noms.poi;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;

import com.foya.dao.mybatis.model.TbAuthPersonnel;
  
public class FillColumnManager {  
  
	
    /** 
     * set data from db (設定儲存格的值)
     */
	public static void fillPSNReport(HSSFSheet worksheet, int startRowIndex,  
            int startColIndex, List<TbAuthPersonnel> datasource) {  
  
        // 設定起始行+1開始填入值
        startRowIndex += 1;  
  
        // 建立儲存格樣式  
        HSSFCellStyle bodyCellStyle = worksheet.getWorkbook().createCellStyle();  
        bodyCellStyle.setAlignment(CellStyle.ALIGN_CENTER);  //水平置中
        bodyCellStyle.setWrapText(false); //自動換行  
        
        // 設定儲存格的日期的樣式
        CellStyle dateCellStyle = worksheet.getWorkbook().createCellStyle();
        CreationHelper createHelper = worksheet.getWorkbook().getCreationHelper();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy/mm/dd hh:mm:ss"));
        
  
        // 設定儲存格的值與樣式  
        for (int i=startRowIndex; i+startRowIndex-1< datasource.size()+1; i++) {  
            // 建立新的行(row)  
            HSSFRow row = worksheet.createRow((short) i);  
  
            // 設定儲存格PSN_ID的值與樣式  
            HSSFCell cell1 = row.createCell(startColIndex+0); 
            int cc = (datasource.get(i-1).getPSN_ID()).intValue();
            cell1.setCellValue(cc);  
            cell1.setCellStyle(bodyCellStyle);   
            
            // 設定儲存格PSN_NO的值與樣式  
            HSSFCell cell2 = row.createCell(startColIndex+1);  
            cell2.setCellValue(datasource.get(i-1).getPSN_NO());  
            cell2.setCellStyle(bodyCellStyle);  
            
            //PSN_PWD
            HSSFCell cell3 = row.createCell(startColIndex+2);  
            cell3.setCellValue(datasource.get(i-1).getPSN_PWD());  
            cell3.setCellStyle(bodyCellStyle);  
  
            // CHN_NM
            HSSFCell cell4 = row.createCell(startColIndex+3);  
            cell4.setCellValue(datasource.get(i-1).getCHN_NM());  
            cell4.setCellStyle(bodyCellStyle);  
  
            // ENG_NM
            HSSFCell cell5 = row.createCell(startColIndex+4);  
            cell5.setCellValue(datasource.get(i-1).getENG_NM());  
            cell5.setCellStyle(bodyCellStyle);  
          
            // DEPTID
            HSSFCell cell6 = row.createCell(startColIndex+5);  
            cell6.setCellValue(datasource.get(i-1).getDEPT_ID());  
            cell6.setCellStyle(bodyCellStyle);
            
            // JOB_TTL
            HSSFCell cell7 = row.createCell(startColIndex+6);  
            cell7.setCellValue(datasource.get(i-1).getJOB_TTL());  
            cell7.setCellStyle(bodyCellStyle);
            
            // CELLULAR
            HSSFCell cell8 = row.createCell(startColIndex+7);  
            cell8.setCellValue(datasource.get(i-1).getCELLULAR());  
            cell8.setCellStyle(bodyCellStyle);
            
            // E_MAIL
            HSSFCell cell9 = row.createCell(startColIndex+8);  
            cell9.setCellValue(datasource.get(i-1).getE_MAIL());  
            cell9.setCellStyle(bodyCellStyle);
            
            // EXT_NO 
            HSSFCell cell10 = row.createCell(startColIndex+9);  
            cell10.setCellValue(datasource.get(i-1).getEXT_NO());  
            cell10.setCellStyle(bodyCellStyle);
            
            // ON_JOB 
            HSSFCell cell11 = row.createCell(startColIndex+10); 
            if(datasource.get(i-1).getON_JOB()!=null){
            	cell11.setCellValue(datasource.get(i-1).getON_JOB());
            }else{
            	cell11.setCellValue("");
            }             
            cell11.setCellStyle(dateCellStyle);
            
            // DISMISSION
            HSSFCell cell12 = row.createCell(startColIndex+11);  
            cell12.setCellValue(datasource.get(i-1).getDISMISSION());  
            cell12.setCellStyle(bodyCellStyle);
            
            // EMP_USER_ID  
            HSSFCell cell13 = row.createCell(startColIndex+12);  
            cell13.setCellValue(datasource.get(i-1).getEMP_USER_ID());  
            cell13.setCellStyle(bodyCellStyle);
            
            // MD_USER  
            HSSFCell cell14 = row.createCell(startColIndex+13);  
            cell14.setCellValue(datasource.get(i-1).getMD_USER());  
            cell14.setCellStyle(bodyCellStyle);
            
            // MD_TIME
            HSSFCell cell15 = row.createCell(startColIndex+14);
            if(datasource.get(i-1).getMD_TIME()!=null){
            	cell15.setCellValue(datasource.get(i-1).getMD_TIME());
            }else{
            	cell15.setCellValue("");            	 
            }
            cell15.setCellStyle(dateCellStyle);                        
        }  
    }  
}