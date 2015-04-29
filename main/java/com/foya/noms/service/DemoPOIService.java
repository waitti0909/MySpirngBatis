package com.foya.noms.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.noms.dto.ExcelImportDTO;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.DateUtils;
import com.foya.noms.util.ReadExcelService;

public class DemoPOIService extends ReadExcelService<TbAuthPersonnel> {

	public BaseJasonObject<TbAuthPersonnel> importXls(InputStream is) throws Exception {
		// ready
		String errorMsg = "";
		Map<String, List<TbAuthPersonnel>> mapList = new HashMap<String, List<TbAuthPersonnel>>();
		List<TbAuthPersonnel> personnelList = new ArrayList<TbAuthPersonnel>(); // 正常資料List
		List<TbAuthPersonnel> errorList = new ArrayList<TbAuthPersonnel>(); // 錯誤資料List
		
		// call read excel api
		List<ExcelImportDTO<TbAuthPersonnel>> models = readExcel(is, 0, 1);
		for (ExcelImportDTO<TbAuthPersonnel> model : models) {
			if (StringUtils.isNotEmpty(model.getErrorMsgs())) {
				// with some error
				errorList.add(model.getRecord());
				errorMsg += model.getErrorMsgs(); 
			} else {
				personnelList.add(model.getRecord());
			}
		}
		
		mapList.put("normal", personnelList);
		mapList.put("error", errorList);
		
		return new BaseJasonObject<>(mapList, StringUtils.trimToNull(errorMsg));		
	}
	
	@Override
	protected TbAuthPersonnel setEntity(String[] oneRowCellsArray) throws Exception {
		TbAuthPersonnel personnel = new TbAuthPersonnel();		
		if (oneRowCellsArray.length == 0) return null;
		int arrayIndex = 0;
		personnel.setPSN_ID(null);	// maybe no need
		personnel.setPSN_NO(oneRowCellsArray[++arrayIndex]);
		personnel.setPSN_PWD(oneRowCellsArray[++arrayIndex]);
		personnel.setCHN_NM(oneRowCellsArray[++arrayIndex]);
		personnel.setENG_NM(oneRowCellsArray[++arrayIndex]);
		personnel.setDEPT_ID(oneRowCellsArray[++arrayIndex]);
		personnel.setJOB_TTL(oneRowCellsArray[++arrayIndex]);
		personnel.setCELLULAR(oneRowCellsArray[++arrayIndex]);
		personnel.setE_MAIL(oneRowCellsArray[++arrayIndex]);
		personnel.setEXT_NO(oneRowCellsArray[++arrayIndex]);
		personnel.setON_JOB(DateUtils.paserDate(oneRowCellsArray[++arrayIndex], "yyyy/MM/dd HH:mm:ss"));
		personnel.setDISMISSION(oneRowCellsArray[++arrayIndex]);
		personnel.setEMP_USER_ID(oneRowCellsArray[++arrayIndex]);
		personnel.setMD_USER(oneRowCellsArray[++arrayIndex]);
		personnel.setMD_TIME(DateUtils.paserDate(oneRowCellsArray[++arrayIndex], "yyyy/MM/dd HH:mm:ss"));
		
		return personnel;
	}

	@Override
	protected String validateModel(Integer rowLine, TbAuthPersonnel record) throws Exception {
		String errorMsg = "";
		
		// 必填欄位PSN_NO是否為空
		if (StringUtils.isEmpty(record.getPSN_NO())) {
			errorMsg += "第" + rowLine + "行PSN_NO空白；";
		}
		// 驗證電子郵件
		if (StringUtils.isNotEmpty(record.getE_MAIL())) {
			if (!(record.getE_MAIL().matches("^[_a-z0-9-]+([.][_a-z0-9-]+)*@[a-z0-9-]+([.][a-z0-9-]+)*$"))) {
				errorMsg += "第" + rowLine + "行電子郵件格式錯誤；";
			}
		}
		return errorMsg;
	}
}
