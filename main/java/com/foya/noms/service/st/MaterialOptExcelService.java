package com.foya.noms.service.st;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.foya.noms.dto.ExcelImportDTO;
import com.foya.noms.util.DateUtils;
import com.foya.noms.util.ReadExcelService;

public abstract class MaterialOptExcelService<T> extends ReadExcelService<T> {

	/**
	 * The public method use by File
	 * @param file
	 * @return
	 * @throws Exception 
	 * @throws BiffException
	 */
	public List<ExcelImportDTO<T>> readExcel(File file, int sheetIndex, int startRowIndex) throws Exception {
		return readAndParseExcel(WorkbookFactory.create(file), sheetIndex, startRowIndex);
	}
	
	/**
	 * The public method use by InputStream
	 * @param is
	 * @return
	 * @throws Exception 
	 * @throws BiffException
	 */
	public List<ExcelImportDTO<T>> readExcel(InputStream is, int sheetIndex, int startRowIndex) throws Exception {
		return readAndParseExcel(WorkbookFactory.create(is), sheetIndex, startRowIndex);
	}
	
	
	/**
	 * Get the String[] list and loop each
	 * @param HSSFWorkbook
	 * @return
	 * @throws Exception 
	 * @throws BiffException
	 */
	/**
	 * @param HSSFWorkbook
	 * @param sheetIndex
	 * @param startRowIndex
	 * @return
	 * @throws Exception
	 */
	private List<ExcelImportDTO<T>> readAndParseExcel(Workbook HSSFWorkbook, int sheetIndex, int startRowIndex) throws Exception {
		
		List<String[]> strArrays = readExcel(HSSFWorkbook, sheetIndex, startRowIndex);
		
		List<ExcelImportDTO<T>> models = new ArrayList<ExcelImportDTO<T>>();
		int count = startRowIndex;
		for (String[] oneRowCellsArray : strArrays) {  // for each model
			T obj = setEntity(oneRowCellsArray);
			if (obj == null) continue;
			
			ExcelImportDTO<T> dto = new ExcelImportDTO<T>();
			dto.setRowLine(count);
			dto.setRecord(obj);
			dto.setErrorMsgs(StringUtils.trimToNull(validateModel(count + 1, dto.getRecord())));
			models.add(dto);
			count++;
		}
		return models;
	}
	
	/**
	 * Read excel and return String[] list
	 * @param workbook
	 * @return
	 * @throws Exception 
	 */
	private List<String[]> readExcel(Workbook workbook, int sheetIndex, int startRowIndex) throws Exception {
		List<String[]> models = new LinkedList<String[]>();
		try {
			// 取得sheet
			Sheet sheet = workbook.getSheetAt(sheetIndex);	// 要匯入的第一個表
			// 顯示sheet資訊
			log.debug("Sheet=(Rows : " + sheet.getLastRowNum() + "), Name=" + sheet.getSheetName());

			//倉庫ID
			String whId = StringUtils.trimToEmpty(sheet.getRow(7).getCell(2).getStringCellValue());
			if(whId.indexOf("(") != -1 && whId.indexOf(")") != -1){
				whId = StringUtils.substring(whId, whId.indexOf("(")+1, whId.indexOf(")"));
			}
			
			// 將每行Cell內容轉成String Array的集合
			String[] contents = null;
			for (int r = startRowIndex; r <= sheet.getLastRowNum(); r++) {
				Row row = sheet.getRow(r);
				contents = new String[row.getLastCellNum() + 1];  //預留一個位子給倉庫ID
				for (int c = 0; c < row.getLastCellNum(); c++) {		
					Cell cell = row.getCell(c);
					if (cell != null) {
						contents[c] = convertCellStr(cell);
					}
				}
				contents[contents.length - 1] = whId;
				
				//檢核數量
				double qty = Double.parseDouble(contents[contents.length - 2]); // 檢核數量欄位
				
				if(qty < 0 ||  (qty - (int) qty) != 0){
					throw new NumberFormatException();
				}
				
				models.add(contents);
				contents = null;
			}			
		}catch(NumberFormatException nfe){
			throw new NumberFormatException("數量欄位格式錯誤！");
		} catch (Exception ex) {
			log.error(ExceptionUtils.getFullStackTrace(ex));
			throw new Exception(ex);
		}
		return models;
	}

	/**
	 * 把儲存格內的類型轉換成String
	 * 
	 * @param cell
	 * @return
	 */
	private String convertCellStr(Cell cell) {

		if (cell == null) return null;
		
		String cellStr = null;
		switch (cell.getCellType()) {

			case Cell.CELL_TYPE_STRING :
				// 讀取String
				cellStr = cell.getStringCellValue().toString();
				break;
	
			case Cell.CELL_TYPE_BOOLEAN :
				// 得到Boolean對象的方法
				cellStr = String.valueOf(cell.getBooleanCellValue());
				break;
	
			case Cell.CELL_TYPE_NUMERIC :
				// 是否為日期格式
				if (DateUtil.isCellDateFormatted(cell)) {
					cellStr = DateUtils.formatDateUS(cell.getDateCellValue(), "yyyy/MM/dd HH:mm:ss");
					// cellStr = cell.getDateCellValue().toString();
				} else { // 讀取數字
					double number = cell.getNumericCellValue();
					// 是否為int型態
					if (number - (int) number < Double.MIN_VALUE) {
						cellStr = Integer.toString((int) number);
					} else { // 是否為double型
						cellStr = Double.toString(cell.getNumericCellValue());
					}
				}
				break;
	
			case Cell.CELL_TYPE_BLANK : // 空值
				cellStr = null;
				break;
		}
		return cellStr;
	}
}
