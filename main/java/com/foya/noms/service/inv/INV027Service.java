package com.foya.noms.service.inv;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.dao.mybatis.model.TbComEqpModel;
import com.foya.dao.mybatis.model.TbComEqpModelExample;
import com.foya.dao.mybatis.model.TbComEqpType;
import com.foya.dao.mybatis.model.TbComEqpTypeExample;
import com.foya.dao.mybatis.model.TbInvCategory;
import com.foya.dao.mybatis.model.UTbInvBomExample;
import com.foya.dao.mybatis.model.UTbInvCategoryExample;
import com.foya.dao.mybatis.model.UTbInvMaterialExample;
import com.foya.noms.dao.inv.INV027Dao;
import com.foya.noms.dto.ExcelImportDTO;
import com.foya.noms.dto.inv.TbInvBomDTO;
import com.foya.noms.dto.inv.TbInvMaterialDTO;
import com.foya.noms.util.ReadExcelService;

@Service
public class INV027Service extends ReadExcelService<TbInvBomDTO> {

	@Autowired
	private INV027Dao inv027Dao;
	
	/**
	 * 取得設備型態
	 * @param example
	 * @return
	 */
	public List<TbComEqpModel> selectEqpModelByExample(TbComEqpModelExample example) {
		return inv027Dao.selectEqpModelByExample(example);
	}

	/**
	 * 取得設備型態
	 * @param example
	 * @return
	 */
	public List<TbComEqpType> selectEqpTypeByExample(TbComEqpTypeExample example) {
		return inv027Dao.selectEqpTypeByExample(example);
	}

	/**
	 * 取得設備資料Group by
	 * @param example
	 * @return
	 */
	public List<TbInvBomDTO> selectDistinctInvBomByExample(UTbInvBomExample example) {
		return inv027Dao.selectDistinctInvBomByExample(example);
	}

	/**
	 * 取得設備資料
	 * @param example
	 * @return
	 */
	public List<TbInvBomDTO> selectInvBomByExample(UTbInvBomExample example) {
		return inv027Dao.selectInvBomByExample(example);
	}

	/**
	 * 計算設備筆數
	 * @param example
	 * @return
	 */
	public int countInvBomByExample(UTbInvBomExample example) {
		return inv027Dao.countInvBomByExample(example);
	}

	/**
	 * 新增設備資料
	 * @param record
	 * @return
	 */
	public int insertInvBomSelective(TbInvBomDTO record) {
		return inv027Dao.insertInvBomSelective(record);
	}

	/**
	 * 修改設備資料
	 * @param record
	 * @return
	 */
	public int updateInvBomByPrimaryKeySelective(TbInvBomDTO record) {
		return inv027Dao.updateInvBomByPrimaryKeySelective(record);
	}

	/**
	 * 取得料號資料
	 * @param example
	 * @return
	 */
	public List<TbInvMaterialDTO> selectInvMaterialByExample(UTbInvMaterialExample example) {
		return inv027Dao.selectInvMaterialByExample(example);
	}

	/**
	 * 取得設備分類資料
	 * @param example
	 * @return
	 */
	public List<TbInvCategory> selectInvCategoryByExample(UTbInvCategoryExample example) {
		return inv027Dao.selectInvCategoryByExample(example);
	}

	/**
	 * 將Excel資料轉為List
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public List<TbInvBomDTO> readReport(InputStream is) throws Exception {
		List<TbInvBomDTO> results = new ArrayList<TbInvBomDTO>();

		List<ExcelImportDTO<TbInvBomDTO>> models = readExcel(is, 0, 1);
		for (ExcelImportDTO<TbInvBomDTO> model : models) {
			results.add(model.getRecord());
		}

		return results;
	}

	/**
	 * 將值Excel欄位set到UTbInvBom
	 */
	@Override
	protected TbInvBomDTO setEntity(String[] oneRowCellsArray) throws Exception {
		if (oneRowCellsArray.length == 0) {
			return null;
		}

		TbInvBomDTO tb = new TbInvBomDTO();
		tb.setMat_no(oneRowCellsArray[0]);
		tb.setQty(Integer.valueOf(oneRowCellsArray[1]));

		return tb;
	}

	/**
	 * 驗證欄位
	 */
	@Override
	protected String validateModel(Integer rowLine, TbInvBomDTO record)
			throws Exception {
		return null;
	}
}
