package com.foya.noms.web.controller.st.line;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class ViewExcel extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> obj,HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//file name
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String fileName = "lineApplyID-"+sdf.format(new Date())+ ".xls";
		response.setContentType("application/vnd.ms-excel");       
		response.setHeader("Content-disposition", "attachment;filename=" + fileName);       
		OutputStream ouputStream = response.getOutputStream();       
		workbook.write(ouputStream);       
		ouputStream.flush();       
		ouputStream.close();   
	}

}
