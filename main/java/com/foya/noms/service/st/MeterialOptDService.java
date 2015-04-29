package com.foya.noms.service.st;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.foya.noms.dto.ExcelImportDTO;
import com.foya.noms.dto.st.SiteMtDetailImportDTO;

@Service
public class MeterialOptDService extends MaterialOptExcelService<SiteMtDetailImportDTO> {

	/**
	 * 讀取匯入報表
	 * 
	 * @param inp
	 * @return
	 * @throws IOException
	 */
	public List<SiteMtDetailImportDTO> readReport(InputStream inp) throws Exception {
		List<SiteMtDetailImportDTO> itemList = new ArrayList<SiteMtDetailImportDTO>(); // 正常資料List

		// call read excel api(讀取報表與驗證、將值set到SiteMtDetailImportDTO)
		List<ExcelImportDTO<SiteMtDetailImportDTO>> models = readExcel(inp, 0, 10);

		for (ExcelImportDTO<SiteMtDetailImportDTO> model : models) {
			itemList.add(model.getRecord());
		}
		return  itemList;
	}
	
	@Override
	protected SiteMtDetailImportDTO setEntity(String[] oneRowCellsArray)
			throws Exception {
		
		if (Integer.valueOf(oneRowCellsArray[9]) == 0) return null;	// 若申請數量為0，則不import
		
		SiteMtDetailImportDTO mtDetail = new SiteMtDetailImportDTO();
		mtDetail.setItemNo(oneRowCellsArray[1]);
		mtDetail.setItemCatMain(oneRowCellsArray[2]);
		mtDetail.setItemCatSub(oneRowCellsArray[3]);
		mtDetail.setItemDetail(oneRowCellsArray[4]);
		mtDetail.setMatName(oneRowCellsArray[5]);
		mtDetail.setUnit(oneRowCellsArray[6]);
		mtDetail.setCtrlType(oneRowCellsArray[7]);
		mtDetail.setTotalQty(Integer.valueOf(oneRowCellsArray[8]));
		mtDetail.setQty(Integer.valueOf(oneRowCellsArray[9]));
		mtDetail.setWhId(oneRowCellsArray[10]);
		return mtDetail;
	}

	@Override
	protected String validateModel(Integer rowLine, SiteMtDetailImportDTO record)
			throws Exception {
		return null;
	}
	
}