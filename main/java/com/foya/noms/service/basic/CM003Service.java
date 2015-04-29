package com.foya.noms.service.basic;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.dao.mybatis.model.TbComItemCat;
import com.foya.dao.mybatis.model.TbComItemCatExample;
import com.foya.dao.mybatis.model.TbComItemMain;
import com.foya.dao.mybatis.model.TbComPoItemExample;
import com.foya.exception.CreateFailException;
import com.foya.exception.DataExistsException;
import com.foya.noms.dao.basic.CM003Dao;
import com.foya.noms.dto.basic.ItemCatDTO;
import com.foya.noms.dto.basic.ItemMainDTO;
import com.foya.noms.poi.Writer;
import com.foya.noms.service.BaseService;
import com.foya.noms.service.common.PoiService;
import com.foya.noms.service.system.UniqueSeqService;

@Service
public class CM003Service extends BaseService{

	@Autowired
	private CM003Dao cM003Dao;
	@Autowired
	private PoiService poiService;
	@Autowired
	private UniqueSeqService usService;
	
	/**
	 * 查詢主項
	 * @param catId
	 * @return
	 */
	public TbComItemCat selectItemCatByPrimaryKey(String catId){
		
		return cM003Dao.selectByPrimaryKey(catId);
	}
	
	/**
	 * 以itemId查詢PO單是否有使用
	 * @param itemId
	 * @return
	 */
	public boolean searchPoItemUsedByItemId(String itemId) {
		TbComPoItemExample tbComPoItemExample = new TbComPoItemExample();
		tbComPoItemExample.createCriteria().andITEM_IDEqualTo(itemId);
		
		return cM003Dao.searchPoItemUsedByItemId(tbComPoItemExample) > 0 ? true : false ;
	}
	
	/**
	 * 查詢主項列表
	 * @return
	 */
	public List<TbComItemCat> searchMainItemList(){
		
		TbComItemCatExample example = new TbComItemCatExample();
		example.createCriteria().andCAT_TYPEEqualTo("0");
		
		return cM003Dao.selectByExample(example);
	}
	
	public TbComItemCat selectItemCatByCatNo(String catNo) {
		TbComItemCatExample example = new TbComItemCatExample();
		example.createCriteria().andCAT_NOEqualTo(catNo);
		List<TbComItemCat> list = cM003Dao.selectByExample(example);
		
		return list.get(0);
	}
	
	/**
	 * 新增TbComItemCat
	 * @return
	 */
	public void saveNewItemCategory(TbComItemCat cat){
		
		TbComItemCatExample example = new TbComItemCatExample();
		example.createCriteria().andCAT_NOEqualTo(cat.getCAT_NO());
		List<TbComItemCat> list = cM003Dao.selectByExample(example);
		
		if (list != null && list.size() > 0) {
			throw new DataExistsException("error已有相同的項次編號存在");
		} else {
			cat.setCAT_ID(Long.parseLong(usService.getNextItemCatId()));
			cat.setCR_TIME(new Date());
			cat.setCR_USER(getLoginUser().getUsername());
			int itemCatCnt = cM003Dao.saveNewItemCategory(cat);
			if(itemCatCnt == 0){
				log.error("insert fail ItemCategory count = " + itemCatCnt + " item_CAT_ID = " + cat.getCAT_ID());
				throw new CreateFailException("errorItemCategory 新增失敗");
			}
		}
		
	}
	
	/**
	 * 新增
	 * @param itemMain
	 * @return
	 */
	public void saveNewMainItem(TbComItemMain itemMain){
		
		if (checkDataExistByEndbledFlag(itemMain)) {
			throw new DataExistsException("error已有啟動的相同項次存在");
		} else {
			int itemMainCnt = cM003Dao.insertItemMain(itemMain);
			
			if(itemMainCnt == 0){
				log.error("insert fail ItemMain count = " + itemMainCnt + " item_ID = " + itemMain.getITEM_ID());
				throw new CreateFailException("errorItemMain 新增失敗");
			}
		}
	}
	
	/**
	 * 修改
	 * @param example
	 * @return
	 */
	public void updateMainItem(TbComItemMain itemMain){
		
		//修改存檔時，只有原本ENABLED為"Y"才能存檔
		if ("Y".equalsIgnoreCase(itemMain.getENABLED())) {
			List<TbComItemMain> list = cM003Dao.getMainItemByItemNo(itemMain.getITEM_NO());
			if (list != null && list.size() > 0) {
				for (TbComItemMain item : list) {
					if (!item.getITEM_ID().equals(itemMain.getITEM_ID())) {
						throw new DataExistsException("error已有啟動的相同項次存在");
					}
				}
			}
		}
		int itemMainCnt = cM003Dao.updateItemMain(itemMain);
		if(itemMainCnt == 0){
			log.error("update fail ItemMain count = " + itemMainCnt + " item_ID = " + itemMain.getITEM_ID());
			throw new CreateFailException("errorItemMain 修改失敗");
		}
	}
	
	/**
	 * 以 itemNo + ENBLED:Y 查詢 MainItem 是否有啟動的相同項次存在
	 * @param itemMain
	 * @return
	 */
	private boolean checkDataExistByEndbledFlag(TbComItemMain itemMain) {
		//只有ENABLED為"Y"才需檢查是否有啟動的項次存在
		if ("Y".equalsIgnoreCase(itemMain.getENABLED())) {
			List<TbComItemMain> list = cM003Dao.getMainItemByItemNo(itemMain.getITEM_NO());
			if (list != null && list.size() > 0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 查詢中項
	 * @param parentCatId
	 * @return
	 */
	public List<TbComItemCat> searchSubItem(String parentCatId){

		TbComItemCat parent = selectItemCatByPrimaryKey(parentCatId);
		
		TbComItemCatExample example = new TbComItemCatExample();
		example.createCriteria().andCAT_TYPEEqualTo("1").andPARENT_CATEqualTo(parent.getCAT_ID());
		
		return cM003Dao.selectByExample(example);
	}
	
	public TbComItemMain selectItemMainItemByItemId(String itemId) {
		
		return cM003Dao.selectItemMainItemByItemId(itemId);
	}
	
	/**
	 * 查詢工項清單
	 * @param dataObj
	 * @return
	 */
	public List<ItemMainDTO> searchByCat(HashMap<String,String> dataObj) {
		
		return cM003Dao.searchByCat(dataObj);
	}
	
	/**
	 * 匯出工項至excel清單
	 * @param dataObj
	 * @return
	 */
	public List<ItemMainDTO> searchByCatForExcel(HashMap<String,String> dataObj) {
		
		return cM003Dao.searchByCatForExcel(dataObj);
	}
	
	/**
	 * 匯出成Excel檔案
	 * @param response
	 * @param sheetName
	 * @param fileName
	 * @param result
	 */
	public void exportExcel(HttpServletResponse response, String sheetName, String fileName, List<ItemMainDTO> result) {
		HSSFSheet worksheet = poiService.exportXLS(response, sheetName, fileName, 0, 0, 12);
		// 建立報表的 標題、日期、欄位名稱
		buildExcel(worksheet, result);
		// 輸出流
		Writer.write(poiService.setResponse(response,fileName+".xls"), worksheet);
	}
	
	/** 
     * 建立欄位名稱與樣式
     */  
    private void buildExcel(HSSFSheet worksheet, List<ItemMainDTO> result) {  
        // 欄位字體  
    	HSSFWorkbook workbook = worksheet.getWorkbook();
    	Font font = workbook.createFont();  
        font.setBoldweight(Font.BOLDWEIGHT_NORMAL);  //字體普通粗細
        
        //設定欄寬
        worksheet.setColumnWidth(0,9*256);    
        worksheet.setColumnWidth(1,42*256);
        worksheet.setColumnWidth(2,9*256);
        worksheet.setColumnWidth(3,9*256);
        worksheet.setColumnWidth(4,9*256);
        worksheet.setColumnWidth(5,55*256);
        
        HashMap<String, HSSFCellStyle> cellStyleMap = new HashMap<String, HSSFCellStyle>();
        cellStyleMap.put("header", poiService.getCM003HeadCellStyle(workbook, font));
        cellStyleMap.put("mainItem", poiService.getCM003MainItemCellStyle(workbook, font));
        cellStyleMap.put("subItem", poiService.getCM003SubItemCellStyle(workbook, font));
        cellStyleMap.put("item", poiService.getCM003ItemCellStyle(workbook, font));
        
        //標題
        String[] title = {"項目編號", "項目名稱", "單位工時", "單位","投標單價","備註"};
        
        // 建立欄位名稱 
		HSSFRow header = worksheet.createRow(0);
		for(int i=0; i<title.length; i++){
			HSSFCell headerCell = header.createCell(i);
			headerCell.setCellValue(title[i]);
			headerCell.setCellStyle(cellStyleMap.get("header"));
		}
		
		DecimalFormat df = new DecimalFormat("###0.##"); 
		//寫入資料
		int rowIdx = 1;
		long mainItem = 0;
		long subItem= 0;
		for(int i=0; i < result.size(); i++){
			ItemMainDTO item = (ItemMainDTO) result.get(i);
			//排除未啟用的資料
			if (StringUtils.equalsIgnoreCase("N", item.getENABLED())) {
				continue;
			}
			HSSFRow excelRow = worksheet.createRow(rowIdx);
			if (item.getMAIN_ITEM() != mainItem) {
				TbComItemCat cat = cM003Dao.selectByPrimaryKey(String.valueOf(item.getMAIN_ITEM()));
				String[] cellValue = {cat.getCAT_NO() + ".0.0", cat.getCAT_NAME(), "", "", "", ""};
				//產生內容及儲存格樣式
				buildCell(excelRow, cellValue, header.getLastCellNum(), cellStyleMap.get("mainItem"));
				mainItem = item.getMAIN_ITEM();
				i--;
			} else if (item.getSUB_ITEM() != subItem) {
				TbComItemCat cat = cM003Dao.selectByPrimaryKey(String.valueOf(item.getSUB_ITEM()));
				String[] cellValue = {cat.getCAT_NO() + ".0", cat.getCAT_NAME(), "", "", "", ""};
				buildCell(excelRow, cellValue, header.getLastCellNum(), cellStyleMap.get("subItem"));
				subItem = item.getSUB_ITEM();
				i--;
			} else {
				String[] cellValue = {item.getITEM_NO(), item.getITEM_NAME(),
						df.format(item.getUNIT_HOUR()), item.getUNIT(),
						df.format(item.getPRICE()), item.getMEMO() };
				buildCell(excelRow, cellValue, header.getLastCellNum(), cellStyleMap.get("item"));
			}
			
			rowIdx++;
		}	
    }
    
    
    /**
     * 產生內容及儲存格樣式
     * @param excelRow
     * @param cellValue
     * @param cellNum
     * @param cellStyleMap
     */
	private void buildCell(HSSFRow excelRow, String[] cellValue,
			short cellNum, HSSFCellStyle cellStyle) {
		for (int index = 0; index < cellNum; index++) {
			HSSFCell cell = excelRow.createCell(index);
			cell.setCellValue(cellValue[index]);
			cell.setCellStyle(cellStyle);
		}
	}
	
	/**
	 * 查詢中項列表
	 * @return
	 */
	public List<ItemCatDTO> getAllSubItemCat(){
		
		return cM003Dao.getAllSubItemCat();
	}
}
