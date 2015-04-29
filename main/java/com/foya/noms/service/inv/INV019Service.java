package com.foya.noms.service.inv;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.dao.mybatis.model.UTbInvInvExample;
import com.foya.dao.mybatis.model.UTbInvSrlExample;
import com.foya.dao.mybatis.model.UTbInvWarehouseExample;
import com.foya.noms.dao.inv.INV019Dao;
import com.foya.noms.dto.inv.TbInvInvDTO;
import com.foya.noms.dto.inv.TbInvSrlDTO;
import com.foya.noms.dto.inv.TbInvWarehouseDTO;
import com.foya.noms.poi.Writer;
import com.foya.noms.service.BaseService;
import com.foya.noms.service.common.PoiService;

@Service
public class INV019Service extends BaseService {
	@Autowired
	private INV019Dao inv019Dao;
	@Autowired
	private PoiService poiService;

	/**
	 * 取得倉庫清單
	 * @param example
	 * @return
	 */
	public List<TbInvWarehouseDTO> selectInvWarehouseByExample(UTbInvWarehouseExample example) {
		return inv019Dao.selectInvWarehouseByExample(example);
	}
	
	/**
	 * 查詢
	 * @param request
	 * @return
	 */
	public List<Object> search(HttpServletRequest request) {
		String mat_no = request.getParameter("matNo"); 				// 料號
		String wh_id = request.getParameter("wh_id"); 				// 倉庫
		String domain = request.getParameter("domain"); 			// 區域
		String dept_id = request.getParameter("dept_id"); 			// 管理單位
		String tagNoFlag = request.getParameter("tagNoFlag");		// 貼標號碼
		String remainFlag = request.getParameter("remainFlag");		// 零庫存

		List<Object> list = new ArrayList<Object>();

		UTbInvInvExample example = new UTbInvInvExample();
		UTbInvInvExample.Criteria cr = example.createCriteria();
		if (mat_no != null && mat_no != "") {
			cr.andMat_noLike("%"+mat_no+"%");
		}
		if (wh_id != null && wh_id != "") {
			cr.andWh_idEqualTo(wh_id);
		}
		if (domain != null && domain != "") {
			cr.andDomainEqualTo(domain);
		}
		if (dept_id != null && dept_id != "") {
			cr.andDept_idEqualTo(dept_id);
		}
		
		List<TbInvInvDTO> result = inv019Dao.selectInvInvBy019(example);
		
		if(tagNoFlag == ""){	//貼標號碼 取ctrl_type = V
			for (int i = result.size() - 1; i >= 0; i--) {
				TbInvInvDTO tb = result.get(i);
				int std = (tb.getStd_qty() == null)? 0: tb.getStd_qty();
				int wrd = (tb.getWrd_qty() == null)? 0: tb.getWrd_qty();
				int wsp = (tb.getWsp_qty() == null)? 0: tb.getWsp_qty();					
				int rd = (tb.getRd_qty() == null)? 0: tb.getRd_qty();
				int rj = (tb.getRj_qty() == null)? 0: tb.getRj_qty();
				int totalCnt = std + wrd + wsp + rd + rj;
				tb.setQty(totalCnt);
				
				if(!tb.getCtrl_type().equals("V")){
					//result.remove(i);
				}else{
					if(remainFlag == ""){
						if(totalCnt <= 0){	
							result.remove(i);
						}
					}
				}
			}
			list.addAll(result);
		}else{//ctrl_type = S、V
			for (int i = result.size() - 1; i >= 0; i--) {				
				TbInvInvDTO tb = result.get(i);
				int std = (tb.getStd_qty() == null)? 0: tb.getStd_qty();
				int wrd = (tb.getWrd_qty() == null)? 0: tb.getWrd_qty();
				int wsp = (tb.getWsp_qty() == null)? 0: tb.getWsp_qty();					
				int totalCnt = std + wrd + wsp;
	
				if(tb.getCtrl_type().equals("V")){
					if(remainFlag == ""){
						if(totalCnt <= 0){
							result.remove(i);
						}
					}					
					tb.setQty(totalCnt);				
					
				}else if(tb.getCtrl_type().equals("S")){
					
					//1:可用品 2:待送修 3:待報廢
					Byte stdStatus = 1;
					Byte wrdStatus = 2;
					Byte wspStatus = 3;
					
					// 取得所有物料狀態的廠商序號、貼標號碼 組sql條件
					UTbInvSrlExample uTbInvSrlExample = new UTbInvSrlExample();
					UTbInvSrlExample.Criteria crSrl = uTbInvSrlExample.createCriteria();
					crSrl.andMat_noEqualTo(tb.getMat_no());
					crSrl.andWh_idEqualTo(tb.getWh_id());
					uTbInvSrlExample.setOrderByClause("mat_status");
					
					List<TbInvSrlDTO> invSrlList = inv019Dao.selectInvSrlByExample(uTbInvSrlExample);
					
					if(remainFlag == ""){
						if(totalCnt <= 0){
							result.remove(i);
						}
					}else{
						if(totalCnt <= 0){
							tb.setQty(totalCnt);
						}
					}					
							
					if(totalCnt > 0){
						result.remove(i);
						log.debug("invSrlList.size():"+invSrlList.size());
						if(invSrlList.size()>0){						   		
					   		for (int k = 0; k < invSrlList.size(); k++) {
								TbInvInvDTO tempInvInvDTO = new TbInvInvDTO();
						   		tempInvInvDTO.setMat_no(tb.getMat_no());
						   		tempInvInvDTO.setMat_name(tb.getMat_name());
						   		tempInvInvDTO.setWh_id(tb.getWh_id());
						   		tempInvInvDTO.setWh_name(tb.getWh_name());
						   		tempInvInvDTO.setCatg1_code(tb.getCatg1_code());
						   		tempInvInvDTO.setCatg1_name(tb.getCatg1_name());
						   		tempInvInvDTO.setCatg2_code(tb.getCatg2_code());
						   		tempInvInvDTO.setCatg2_name(tb.getCatg2_name());
						   		tempInvInvDTO.setCatg3_code(tb.getCatg3_code());
						   		tempInvInvDTO.setCatg3_name(tb.getCatg3_name());
						   		
				   				TbInvSrlDTO tbInvSrlDTO = invSrlList.get(k);
				   		  		tempInvInvDTO.setStd_qty((tbInvSrlDTO.getMat_status().equals(stdStatus))? 1: 0);	// 可用品數量
						   		tempInvInvDTO.setWrd_qty((tbInvSrlDTO.getMat_status().equals(wrdStatus))? 1: 0);	// 待送修數量
						   		tempInvInvDTO.setWsp_qty((tbInvSrlDTO.getMat_status().equals(wspStatus))? 1: 0);	// 待報廢數量
						   		tempInvInvDTO.setVen_sn(tbInvSrlDTO.getVen_sn());	// 廠商序號
						   		tempInvInvDTO.setTag_no(tbInvSrlDTO.getTag_no());	// 貼標號碼
						   		tempInvInvDTO.setQty(1);							// 庫存總量
						   		
						   		list.add(k, tempInvInvDTO);
				   			}						
						}else if(invSrlList.size()==0){							
							for(int invI=0;invI<std;invI++){
								TbInvInvDTO tempInvInvDTO = new TbInvInvDTO();
						   		tempInvInvDTO.setMat_no(tb.getMat_no());
						   		tempInvInvDTO.setMat_name(tb.getMat_name());
						   		tempInvInvDTO.setWh_id(tb.getWh_id());
						   		tempInvInvDTO.setWh_name(tb.getWh_name());
						   		tempInvInvDTO.setCatg1_code(tb.getCatg1_code());
						   		tempInvInvDTO.setCatg1_name(tb.getCatg1_name());
						   		tempInvInvDTO.setCatg2_code(tb.getCatg2_code());
						   		tempInvInvDTO.setCatg2_name(tb.getCatg2_name());
						   		tempInvInvDTO.setCatg3_code(tb.getCatg3_code());
						   		tempInvInvDTO.setCatg3_name(tb.getCatg3_name());
						   		tempInvInvDTO.setStd_qty(1);	// 可用品數量
						   		tempInvInvDTO.setWrd_qty(0);	// 待送修數量
						   		tempInvInvDTO.setWsp_qty(0);	// 待報廢數量
						   		tempInvInvDTO.setQty(1);							// 庫存總量
						   		list.add(invI,tempInvInvDTO);
					   		}
							for(int invI=0;invI<wrd;invI++){
								TbInvInvDTO tempInvInvDTO = new TbInvInvDTO();
						   		tempInvInvDTO.setMat_no(tb.getMat_no());
						   		tempInvInvDTO.setMat_name(tb.getMat_name());
						   		tempInvInvDTO.setWh_id(tb.getWh_id());
						   		tempInvInvDTO.setWh_name(tb.getWh_name());
						   		tempInvInvDTO.setCatg1_code(tb.getCatg1_code());
						   		tempInvInvDTO.setCatg1_name(tb.getCatg1_name());
						   		tempInvInvDTO.setCatg2_code(tb.getCatg2_code());
						   		tempInvInvDTO.setCatg2_name(tb.getCatg2_name());
						   		tempInvInvDTO.setCatg3_code(tb.getCatg3_code());
						   		tempInvInvDTO.setCatg3_name(tb.getCatg3_name());
						   		tempInvInvDTO.setStd_qty(0);	// 可用品數量
						   		tempInvInvDTO.setWrd_qty(1);	// 待送修數量
						   		tempInvInvDTO.setWsp_qty(0);	// 待報廢數量
						   		tempInvInvDTO.setQty(1);							// 庫存總量
						   		list.add(invI,tempInvInvDTO);
					   		}
							for(int invI=0;invI<wsp;invI++){
								TbInvInvDTO tempInvInvDTO = new TbInvInvDTO();
						   		tempInvInvDTO.setMat_no(tb.getMat_no());
						   		tempInvInvDTO.setMat_name(tb.getMat_name());
						   		tempInvInvDTO.setWh_id(tb.getWh_id());
						   		tempInvInvDTO.setWh_name(tb.getWh_name());
						   		tempInvInvDTO.setCatg1_code(tb.getCatg1_code());
						   		tempInvInvDTO.setCatg1_name(tb.getCatg1_name());
						   		tempInvInvDTO.setCatg2_code(tb.getCatg2_code());
						   		tempInvInvDTO.setCatg2_name(tb.getCatg2_name());
						   		tempInvInvDTO.setCatg3_code(tb.getCatg3_code());
						   		tempInvInvDTO.setCatg3_name(tb.getCatg3_name());
						   		tempInvInvDTO.setStd_qty(0);	// 可用品數量
						   		tempInvInvDTO.setWrd_qty(0);	// 待送修數量
						   		tempInvInvDTO.setWsp_qty(1);	// 待報廢數量
						   		tempInvInvDTO.setQty(1);							// 庫存總量
						   		list.add(invI,tempInvInvDTO);
					   		}
						}
					}
				}
			}
			list.addAll(result);
		}
		return list;
	}
	
	public void exportExcel(HttpServletResponse response, String sheetName, String fileName, List<Object> result) {
		HSSFSheet worksheet = poiService.exportXLS(response, sheetName, fileName, 0, 0, 12);
		// 建立報表的 標題、日期、欄位名稱
		buildExcel(worksheet, result);
		// 輸出流
		Writer.write(poiService.setResponse(response,fileName+".xls"), worksheet);
	}
	
	/** 
     * 建立欄位名稱與樣式
     */  
    private static void buildExcel(HSSFSheet worksheet, List<Object> result) {  
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
        String[] title = {"倉庫","大分類代碼","大分類","中分類代碼","中分類","小分類代碼","小分類","料號","品名","廠商序號","貼標號碼","可用品","待送修","待報廢","庫存/總量"};
        
        // 建立欄位名稱 
		HSSFRow header = worksheet.createRow(0);
		for(int i=0; i<title.length; i++){
			header.createCell(i).setCellValue(title[i]);
		}
		
		//寫入資料
		int rowIdx = 1;
		for(int i=0; i<result.size(); i++){
			HSSFRow excelRow = worksheet.createRow(rowIdx);		
			TbInvInvDTO inv = (TbInvInvDTO) result.get(i);		
			excelRow.createCell(0).setCellValue(inv.getWh_name());
			excelRow.createCell(1).setCellValue(inv.getCatg1_code());
			excelRow.createCell(2).setCellValue(inv.getCatg1_name());
			excelRow.createCell(3).setCellValue(inv.getCatg2_code());
			excelRow.createCell(4).setCellValue(inv.getCatg2_name());
			excelRow.createCell(5).setCellValue(inv.getCatg3_code());
			excelRow.createCell(6).setCellValue(inv.getCatg3_name());
			excelRow.createCell(7).setCellValue(inv.getMat_no());
			excelRow.createCell(8).setCellValue(inv.getMat_name());
			excelRow.createCell(9).setCellValue(inv.getVen_sn());
			excelRow.createCell(10).setCellValue(inv.getTag_no());

			if(inv.getStd_qty() == null){
				excelRow.createCell(11).setCellValue("");
			}else{
				excelRow.createCell(11).setCellValue(inv.getStd_qty());				
			}
			if(inv.getWrd_qty() == null){
				excelRow.createCell(12).setCellValue("");
			}else{
				excelRow.createCell(12).setCellValue(inv.getWrd_qty());
			}
			if(inv.getWsp_qty() == null){
				excelRow.createCell(13).setCellValue("");
			}else{
				excelRow.createCell(13).setCellValue(inv.getWsp_qty());
			}
			if(inv.getQty() == null){
				excelRow.createCell(14).setCellValue("");
			}else{
				excelRow.createCell(14).setCellValue(inv.getQty());
			}
			rowIdx++;
		}	
    }
	
}
