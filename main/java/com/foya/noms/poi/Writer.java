package com.foya.noms.poi;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
  
public class Writer {  
	
    public static void write(HttpServletResponse response, HSSFSheet worksheet) {  
  
//    	System.out.println("Writing report to the stream");  
        try {  
            // Retrieve the output stream  
            ServletOutputStream outputStream = response.getOutputStream();  
            // Write to the output stream  
            worksheet.getWorkbook().write(outputStream);  
            // 清除緩衝區資料  
            outputStream.flush();  
  
        } catch (Exception e) {  
        	System.err.println("匯入失敗!" + ExceptionUtils.getFullStackTrace(e));  
        }  
    }  
  
}  
