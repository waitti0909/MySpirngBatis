package com.foya.noms.service.inv;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbOrgDeptExample;
import com.foya.dao.mybatis.model.TbOrgDomain;
import com.foya.dao.mybatis.model.TbOrgDomainExample;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.dao.mybatis.model.TbSysLookupExample;
import com.foya.dao.mybatis.model.UTbInvAssetExample;
import com.foya.dao.mybatis.model.UTbInvInvExample;
import com.foya.dao.mybatis.model.UTbInvOnhandExample;
import com.foya.dao.mybatis.model.UTbInvWarehouseExample;
import com.foya.noms.dao.inv.INV021Dao;
import com.foya.noms.dao.org.DeptDao;
import com.foya.noms.dao.org.DomainDao;
import com.foya.noms.dto.inv.TbInvAssetDTO;
import com.foya.noms.dto.inv.TbInvInvDTO;
import com.foya.noms.dto.inv.TbInvOnhandDTO;
import com.foya.noms.dto.inv.TbInvWarehouseDTO;
import com.foya.noms.poi.Writer;
import com.foya.noms.service.BaseService;
import com.foya.noms.service.common.PoiService;

@Service
public class INV021Service extends BaseService {

	@Autowired
	private INV021Dao inv021Dao;

	@Autowired
	private DomainDao domainDao;

	@Autowired
	private DeptDao deptDao;

	@Autowired
	private PoiService poiService;

	/**
	 * 取得參數資料
	 * @param example
	 * @return
	 */
	public List<TbSysLookup> selectSysLookupByExample(TbSysLookupExample example) {
		return inv021Dao.selectSysLookupByExample(example);
	}

	/**
	 * 取得倉庫清單
	 * @param example
	 * @return
	 */
	public List<TbInvWarehouseDTO> selectInvWarehouseByExample(UTbInvWarehouseExample example) {
		return inv021Dao.selectInvWarehouseByExample(example);
	}

	/**
	 * 取得所有區域
	 * @param example
	 * @return
	 */
	public List<TbOrgDomain> selectOrgDomainByExample(TbOrgDomainExample example) {
		return domainDao.selectOrgDomain(example);
	}

	/**
	 * 取得所有管理單位
	 * 
	 * @param example
	 * @return
	 */
	public List<TbOrgDept> selectOrgDeptByExample(TbOrgDeptExample example) {
		return deptDao.selectAllOrgDept(example);
	}

	/**
	 * 查詢資產分布資料(站點)
	 * 
	 * @param example
	 * @return
	 */
	public List<TbInvAssetDTO> selectInvAssetByExample(UTbInvAssetExample example) {
		return inv021Dao.selectInvAssetByExample(example);
	}

	/**
	 * 查詢資產分布資料(庫存)
	 * @param example
	 * @param mat_status 物料狀態
	 * @param max_row_num 最大查詢筆數
	 * @return
	 * @throws CloneNotSupportedException
	 */
	public List<TbInvInvDTO> selectInvInvByExample(UTbInvInvExample example,
			String mat_status, String max_row_num)
			throws CloneNotSupportedException {

		int maxRowNum = Integer.valueOf(max_row_num == null || max_row_num.trim().equals("") ? "35001" : max_row_num);
		List<TbInvInvDTO> list = inv021Dao.selectInvInvByExampleForLimit(example, maxRowNum);

		// 取得物料狀態
		TbSysLookupExample lookupExample = new TbSysLookupExample();
		lookupExample.createCriteria().andTYPEEqualTo("MAT_STATUS");
		List<TbSysLookup> matStatusList = this.selectSysLookupByExample(lookupExample);
		Map<String, String> matStatus = new HashMap<String, String>();
		for (int i = 0; i < matStatusList.size(); i++) {
			matStatus.put(matStatusList.get(i).getCODE(), matStatusList.get(i).getNAME());
		}

		for (int i = list.size() - 1; i >= 0; i--) {
			TbInvInvDTO tb = list.get(i);
			int stdQty = tb.getStd_qty();
			int wrdQty = tb.getWrd_qty();
			int wspQty = tb.getWsp_qty();
			if (tb.getCtrl_type().equals("V")) {
				list.remove(i);
				if (mat_status.indexOf("3") >= 0 && wspQty > 0) { // 待報廢
					TbInvInvDTO wspTb = tb.clone();
					wspTb.setQty(wspQty);
					wspTb.setMat_status("3");
					wspTb.setMat_status_dscr(matStatus.get("3"));
					list.add(i, wspTb);
				}
				if (mat_status.indexOf("2") >= 0 && wrdQty > 0) { // 待送修
					TbInvInvDTO wrdTb = tb.clone();
					wrdTb.setQty(wrdQty);
					wrdTb.setMat_status("2");
					wrdTb.setMat_status_dscr(matStatus.get("2"));
					list.add(i, wrdTb);
				}
				if (mat_status.indexOf("1") >= 0 && stdQty > 0) { // 可用品
					TbInvInvDTO stdTb = tb.clone();
					stdTb.setQty(stdQty);
					stdTb.setMat_status("1");
					stdTb.setMat_status_dscr(matStatus.get("1"));
					list.add(i, stdTb);
				}
			}
		}

		return list;
	}

	/**
	 * 查詢資產分布資料(在途)
	 * 
	 * @param example
	 * @return
	 */
	public List<TbInvOnhandDTO> selectInvOnhandByExample(UTbInvOnhandExample example) {
		List<TbInvOnhandDTO> list = inv021Dao.selectInvOnhandByExample(example);
		// 在途不顯示放置地點
		for (int i = 0; i < list.size(); i++) {
			TbInvOnhandDTO tb = list.get(i);
			tb.setWh_id(null);
			tb.setWh_name(null);
		}
		return list;
	}

	/**
	 * 整合資產位置資料查詢功能(站點、庫存、在途)
	 * @param map
	 * @return
	 * @throws CloneNotSupportedException 
	 */
	public List<Object> selectAllAssetData(Map<String, String> map) throws CloneNotSupportedException {
		String max_row_num = map.get("max_row_num"); // 最大查詢筆數for TbInvInv
		String mat_no = map.get("mat_no"); // 料號
		String tag_no = map.get("tag_no"); // 貼標號碼
		String assetLocation = map.get("asset_location"); // 資產位置
		String wh_id = map.get("wh_id"); // 倉庫
		String domain = map.get("domain"); // 區域
		String mat_status = map.get("mat_status"); // 物料狀態
		String dept_id = map.get("dept_id"); // 工程維護單位
		String bts_site_id = map.get("bts_site_id"); // 基站編號
		String over_day = map.get("over_day"); // 在途超過天數
		String[] siteStatus = { "S01", "S02", "S03", "S04", "S05", "S06", "S07", "S08" };
		List<String> siteStatusList = new ArrayList<String>();
		for (int i = 0; i < siteStatus.length; i++) {
			siteStatusList.add(siteStatus[i]);
		}

		List<Object> list = new ArrayList<Object>();
		if (assetLocation.indexOf("S") >= 0 && mat_status.indexOf("1") >= 0) {
			UTbInvAssetExample example = new UTbInvAssetExample();
			UTbInvAssetExample.Criteria cr = example.createCriteria();
			cr.andAsset_typeEqualTo("M");
			if (mat_no != null && mat_no != "") {
				cr.andItem_noLike("%" + mat_no + "%");
			}
			if (bts_site_id != null && bts_site_id != "") {
				cr.andEqualTo("g.bts_site_id", bts_site_id);
				cr.andIn("g.site_status", siteStatusList);
			}
			if (tag_no != null && tag_no != "") {
				cr.andTag_noLike("%" + tag_no + "%");
			}
			if (dept_id != null && dept_id != "") {
				cr.andMaint_deptEqualTo(dept_id);
			}
			if (domain != null && domain != "") {
				cr.andDomainLike(domain.substring(0, 1) + "%");
			}
			cr.andQtyGreaterThan(0);

			list.addAll(this.selectInvAssetByExample(example));
		}
		if (assetLocation.indexOf("W") >= 0) {
			UTbInvInvExample example = new UTbInvInvExample();
			UTbInvInvExample.Criteria cr = example.createCriteria();
			if (mat_no != null && mat_no != "") {
				cr.andMat_noLike("%" + mat_no + "%");
			}
			if (wh_id != null && wh_id != "") {
				cr.andWh_idEqualTo(wh_id);
			}
			if (domain != null && domain != "") {
				cr.andDomainLike(domain.substring(0, 1) + "%");
			}
			if (dept_id != null && dept_id != "") {
				cr.andDept_idEqualTo(dept_id);
			}
			if (mat_status != null && mat_status != "") {
				String[] ms = mat_status.split(",");
				List<Byte> matStatusList = new ArrayList<Byte>();
				for (int i = 0; i < ms.length; i++) {
					matStatusList.add(Byte.valueOf(ms[i]));
				}
				matStatusList.add(Byte.valueOf("99"));
				cr.andIn("isnull(e.mat_status, 99)", matStatusList);
			}
			if (tag_no != null && tag_no != "") {
				cr.andTag_noLike("%" + tag_no + "%");
			}
			cr.andGreaterThan("(a.std_qty + isnull(a.wrd_qty, 0) + isnull(a.wsp_qty, 0))", 0);

			list.addAll(this.selectInvInvByExample(example, mat_status, max_row_num));
		}
		if (assetLocation.indexOf("O") >= 0) {
			UTbInvOnhandExample example = new UTbInvOnhandExample();
			UTbInvOnhandExample.Criteria cr = example.createCriteria();
			if (mat_no != null && mat_no != "") {
				cr.andMat_noLike("%" + mat_no + "%");
			}
			if (wh_id != null && wh_id != "") {
				cr.andWh_idEqualTo(wh_id);
			}
			if (bts_site_id != null && bts_site_id != "") {
				cr.andEqualTo("j.bts_site_id", bts_site_id);
				cr.andIn("j.site_status", siteStatusList);
			}
			if (mat_status != null && mat_status != "") {
				String[] ms = mat_status.split(",");
				List<Byte> matStatusList = new ArrayList<Byte>();
				for (int i = 0; i < ms.length; i++) {
					matStatusList.add(Byte.valueOf(ms[i]));
				}
				cr.andMat_statusIn(matStatusList);
			}
			if (tag_no != null && tag_no != "") {
				cr.andTag_noLike("%" + tag_no + "%");
			}
			if (dept_id != null && dept_id != "") {
				cr.andRep_deptEqualTo(dept_id);
			}
			if (domain != null && domain != "") {
				cr.andDomainLike(domain.substring(0, 1) + "%");
			}
			if (over_day != null && over_day != "") {
				cr.andOver_dayGreaterThanOrEqualTo(Long.valueOf(over_day));
			}
			cr.andOnhand_qtyGreaterThan(0);

			list.addAll(this.selectInvOnhandByExample(example));
		}

		return list;
	}

	/**
	 * 匯出資產分布Excel報表
	 * 
	 * @param response
	 * @param fileName
	 * @param list
	 */
	public void exportExcel(HttpServletResponse response, String fileName,
			List<Object> list) {
		HSSFSheet sheet = poiService.exportXLS(response, "資產分布", fileName, 0, 0, 12);

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
		String[] title = { "大分類代碼", "大分類", "中分類代碼", "中分類", "小分類代碼", "小分類",
				"料號", "品名", "廠商序號", "貼標號碼", "批號", "數量", "放置地點", "放置日期", "物料狀態",
				"區域", "工程維護單位" };

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

			if (list.get(i) instanceof TbInvAssetDTO) {
				TbInvAssetDTO tb = (TbInvAssetDTO) list.get(i);
				excelRow.createCell(cellIndex++).setCellValue(tb.getCatg1_code());
				excelRow.createCell(cellIndex++).setCellValue(tb.getCatg1_name());
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getCatg2_code());
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getCatg2_name());
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getCatg3_code());
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getCatg3_name());
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getMat_no());
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getMat_name());
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getVen_sn());
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getTag_no());
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getFa_no());
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getQty());
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getWh_name());
		   		excelRow.createCell(cellIndex++).setCellValue(sdf.format(tb.getCr_time()));
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getMat_status_dscr());
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getDomain_name());
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getDept_name());
			} else if (list.get(i) instanceof TbInvInvDTO) {
				TbInvInvDTO tb = (TbInvInvDTO) list.get(i);
				excelRow.createCell(cellIndex++).setCellValue(tb.getCatg1_code());
				excelRow.createCell(cellIndex++).setCellValue(tb.getCatg1_name());
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getCatg2_code());
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getCatg2_name());
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getCatg3_code());
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getCatg3_name());
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getMat_no());
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getMat_name());
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getVen_sn());
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getTag_no());
		   		excelRow.createCell(cellIndex++).setCellValue("");
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getQty());
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getWh_name());
		   		excelRow.createCell(cellIndex++).setCellValue(sdf.format(tb.getCr_time()));
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getMat_status_dscr());
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getDomain_name());
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getDept_name());
			} else if (list.get(i) instanceof TbInvOnhandDTO) {
				TbInvOnhandDTO tb = (TbInvOnhandDTO) list.get(i);
				excelRow.createCell(cellIndex++).setCellValue(tb.getCatg1_code());
				excelRow.createCell(cellIndex++).setCellValue(tb.getCatg1_name());
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getCatg2_code());
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getCatg2_name());
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getCatg3_code());
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getCatg3_name());
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getMat_no());
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getMat_name());
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getVen_sn());
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getTag_no());
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getFa_no());
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getQty());
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getWh_name());
		   		excelRow.createCell(cellIndex++).setCellValue(sdf.format(tb.getCr_time()));
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getMat_status_dscr());
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getDomain_name());
		   		excelRow.createCell(cellIndex++).setCellValue(tb.getDept_name());
			}
		}

		// 調整欄位寬度
		for (int i = 0; i < sheet.getRow(0).getLastCellNum(); i++) {
			sheet.setColumnWidth(i, 256 * 16);
		}

		// 匯出EXCEL檔案
		Writer.write(poiService.setResponse(response,fileName), sheet);
	}
}
