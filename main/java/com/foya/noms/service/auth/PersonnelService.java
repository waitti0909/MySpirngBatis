package com.foya.noms.service.auth;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbAuthMenu;
import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.dao.mybatis.model.TbAuthPersonnelExample;
import com.foya.dao.mybatis.model.TbOrgPsnPos;
import com.foya.dao.mybatis.model.TbOrgPsnPosExample;
import com.foya.dao.mybatis.model.TbOrgPsnPosKey;
import com.foya.exception.DataExistsException;
import com.foya.noms.dao.auth.PersonnelDao;
import com.foya.noms.dao.org.PsnPosDao;
import com.foya.noms.dto.ExcelImportDTO;
import com.foya.noms.dto.auth.PersonnelDTO;
import com.foya.noms.dto.auth.UserDTO;
import com.foya.noms.dto.org.DeptDTO;
import com.foya.noms.poi.FillColumnManager;
import com.foya.noms.poi.Layouter;
import com.foya.noms.poi.Writer;
import com.foya.noms.service.common.PoiService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.DateUtils;
import com.foya.noms.util.ReadExcelService;

@Service
public class PersonnelService extends ReadExcelService<TbAuthPersonnel> {

	@Autowired
	private PersonnelDao personnelDao;
	@Autowired
	private PoiService poiService;
	@Autowired
	private PsnPosDao psnPosDao;

	/**
	 * 查詢所有使用者
	 * 
	 * @return
	 */
	public List<TbAuthPersonnel> getAllPsn() {
		return personnelDao.selectAllPsn();
	}

	/**
	 * 透過 psn no 取得 user dto
	 * 
	 * @param psnNo
	 * @return UserDTO
	 */
	public UserDTO getUserDto(String psnNo) {
		return personnelDao.getUserDtoByPsnNo(psnNo);
	}

	/**
	 * 透過 psn no 取得list of user dto
	 * 
	 * @param psnNos
	 * @return list of UserDTO
	 */
	public List<UserDTO> getUserDto(Collection<String> psnNos) {
		return personnelDao.getUserDtoByPsnNos(psnNos);
	}

	/**
	 * 查詢使用者透過 部門代號
	 * 
	 * @param deptId
	 * @return list of TbAuthPersonnel
	 */
	public List<TbAuthPersonnel> selectByDeptIdLike(String deptId) {
		return personnelDao.selectByDeptIdLike(deptId);
	}

	/**
	 * 根據 dept pos id 查詢用戶
	 * 
	 * @param deptPosId
	 * @return list of UserDTO
	 */
	public List<UserDTO> getUserDtoByDeptPosId(String deptPosId) {
		return personnelDao.getUserDtoByDeptPosId(deptPosId);
	}

	/**
	 * 查詢使用者透過 collection of user no
	 * 
	 * @param psnNos
	 * @return list of TbAuthPersonnel
	 */
	public List<TbAuthPersonnel> findPsnByNos(Collection<String> psnNos) {
		TbAuthPersonnelExample example = new TbAuthPersonnelExample();
		if (psnNos != null && !psnNos.isEmpty()) {
			example.createCriteria().andPSN_NOIn(new ArrayList<String>(psnNos));
			return personnelDao.getPersonnelsByExample(example);
		} else {
			return new ArrayList<>(0);
		}
	}

	public List<TbAuthPersonnel> getPersonnelsByExample(TbAuthPersonnelExample example) {
		return personnelDao.getPersonnelsByExample(example);
	}

	/**
	 * 用psn mo, chn name, email 查詢 psn
	 * 
	 * @param psnNo
	 * @param chnName
	 * @param email
	 * @return list of TbAuthPersonnel
	 */
	public List<TbAuthPersonnel> searchPsnByNoChnNameEmail(String psnNo, String chnName,
			String email) {
		return personnelDao.searchPsnByNoChnNameEmail(psnNo, chnName, email);
	}

	/**
	 * 用 psn Id 查詢 Personnel
	 * 
	 * @param PSN_ID
	 * @return
	 */
	public TbAuthPersonnel findPersonnelByPsnId(BigDecimal PSN_ID) {
		return personnelDao.selectPersonnelByPsnId(PSN_ID);
	}

	/**
	 * 匯出Personnel空報表
	 * 
	 * @param response
	 * @param sheetName
	 *            (分頁名稱)
	 * @param fileName
	 *            (檔案名稱)
	 * @param startRowIndex
	 *            (起始列)
	 * @param startColIndex
	 *            (起始欄)
	 * @param maxColumn
	 *            (最大欄數，用於設定欄寬)
	 */
	public void exportPSNXLSEmpty(HttpServletResponse response, String sheetName, String fileName,
			int startRowIndex, int startColIndex, int maxColumn) {
		HSSFSheet worksheet = poiService.exportXLS(response, sheetName, fileName, startRowIndex,
				startColIndex, maxColumn);
		// 建立報表的 標題、日期、欄位名稱
		Layouter.buildPSNReport(worksheet, startRowIndex, startColIndex, maxColumn);
		// 輸出流
		Writer.write(poiService.setResponse(response, fileName), worksheet);
	}

	/**
	 * 匯出Personnel excel報表
	 * 
	 * @param response
	 * @param sheetName
	 * @param fileName
	 * @param startRowIndex
	 * @param startColIndex
	 * @param maxColumn
	 */
	public void exportPSNXLS(HttpServletResponse response, String sheetName, String fileName,
			int startRowIndex, int startColIndex, int maxColumn) {
		HSSFSheet worksheet = poiService.exportXLS(response, sheetName, fileName, startRowIndex,
				startColIndex, maxColumn);
		// 建立報表的 標題、日期、欄位名稱
		Layouter.buildPSNReport(worksheet, startRowIndex, startColIndex, maxColumn);
		// 設定儲存格的值
		FillColumnManager.fillPSNReport(worksheet, startRowIndex, startColIndex,
				getPersonnelsByExample(new TbAuthPersonnelExample()));
		Writer.write(poiService.setResponse(response, fileName), worksheet);
	}

	// ===============================匯入報表Start===============================

	/**
	 * 讀取報表後插入DB(Personnel)
	 * 
	 * @param list
	 */
	@Transactional
	public void insertTbAuthPersonnelDB(List<TbAuthPersonnel> list) {
		TbAuthPersonnel record = new TbAuthPersonnel();
		for (int i = 0; i < list.size(); i++) {
			record = list.get(i);
			personnelDao.insertComputer(record);
		}
	}

	/**
	 * 讀取匯入報表
	 * 
	 * @param inp
	 * @return
	 * @throws IOException
	 */
	public BaseJasonObject<TbAuthPersonnel> readReport(InputStream inp) throws Exception {
		// ready
		String errorMsg = "";
		Map<String, List<TbAuthPersonnel>> mapList = new HashMap<String, List<TbAuthPersonnel>>();
		List<TbAuthPersonnel> personnelList = new ArrayList<TbAuthPersonnel>(); // 正常資料List
		List<TbAuthPersonnel> errorList = new ArrayList<TbAuthPersonnel>(); // 錯誤資料List

		// call read excel api(讀取報表與驗證、將值set到TbAuthPersonnel)
		List<ExcelImportDTO<TbAuthPersonnel>> models = readExcel(inp, 0, 1);

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

	/*
	 * 將值set到TbAuthPersonnel
	 * 
	 * @see com.foya.noms.util.ReadExcelService#setEntity(java.lang.String[])
	 */
	@Override
	protected TbAuthPersonnel setEntity(String[] oneRowCellsArray) throws Exception {
		TbAuthPersonnel personnel = new TbAuthPersonnel();
		String mduser = getLoginUser().getUsername();
		Date nowTime = DateUtils.today();
		if (oneRowCellsArray.length == 0)
			return null;

		personnel.setPSN_NO(oneRowCellsArray[0]);
		personnel.setPSN_PWD(oneRowCellsArray[1]);
		personnel.setCHN_NM(oneRowCellsArray[2]);
		personnel.setENG_NM(oneRowCellsArray[3]);
		personnel.setDEPT_ID(oneRowCellsArray[4]);
		personnel.setJOB_TTL(oneRowCellsArray[5]);
		personnel.setCELLULAR(oneRowCellsArray[6]);
		personnel.setE_MAIL(oneRowCellsArray[7]);
		personnel.setEXT_NO(oneRowCellsArray[8]);
		personnel.setON_JOB(DateUtils.paserDate(oneRowCellsArray[9], "yyyy/MM/dd HH:mm:ss"));
		personnel.setDISMISSION(oneRowCellsArray[10]);
		personnel.setEMP_USER_ID(oneRowCellsArray[11]);
		personnel.setMD_USER(mduser); // 修改者
		// personnel.setMD_TIME(DateUtils.paserDate(oneRowCellsArray[13],
		// "yyyy/MM/dd HH:mm:ss"));
		personnel.setMD_TIME(nowTime);

		return personnel;
	}

	/**
	 * 驗證欄位
	 */
	@Override
	protected String validateModel(Integer rowLine, TbAuthPersonnel record) throws Exception {
		String errorMsg = "";
		// 必填欄位PSN_NO是否為空
		if (StringUtils.isEmpty(record.getPSN_NO())) {
			errorMsg += "第" + rowLine + "行PSN_NO空白；";
		}
		// 驗證電子郵件
		if (StringUtils.isNotEmpty(record.getE_MAIL())) {
			if (!(record.getE_MAIL()
					.matches("^[_a-z0-9-]+([.][_a-z0-9-]+)*@[a-z0-9-]+([.][a-z0-9-]+)*$"))) {
				errorMsg += "第" + rowLine + "行電子郵件格式錯誤；";
			}
		}
		return errorMsg;
	}

	// ===============================匯入報表End=============================

	/**
	 * 用psn no 查詢psn
	 * 
	 * @param psnNo
	 * @return list of TbAuthPersonnel
	 */
	public List<TbAuthPersonnel> searchPsnByPsnNo(String psnNo) {
		return personnelDao.searchPsnsByPsnNo(psnNo);
	}

	/**
	 * 根據vatNO查詢Personnel
	 * 
	 * @param vatNo
	 * @return
	 */
	public List<PersonnelDTO> searchPsnByVatNo(String vatNo) {
		return personnelDao.searchPsnByVatNo(vatNo);
	}

	/**
	 * 根據查詢條件查詢Personnel(使用者維護)
	 * 
	 * @param psnNo
	 * @param chnName
	 * @param email
	 * @return
	 */
	public List<PersonnelDTO> searchPsnByCondition(String psnNO, String engNM, String email) {
		return personnelDao.searchPsnByCondition(psnNO, engNM, email);
	}

	/**
	 * 使用者帳號新增
	 * 
	 * @param personnel
	 * @throws DataExistsException
	 */
	@Transactional
	public void saveNewPersonnel(TbAuthPersonnel personnel) throws DataExistsException {
		TbAuthPersonnelExample examplePsnNO = new TbAuthPersonnelExample();
		examplePsnNO.createCriteria().andPSN_NOEqualTo(personnel.getPSN_NO());
		List<TbAuthPersonnel> PsnNO = personnelDao.getPersonnelsByExample(examplePsnNO);

		// 員工編號是否重複
		if (PsnNO.size() > 0) {
			throw new DataExistsException("無法新增，員工編號已重複!");
		} else {
			TbAuthPersonnelExample example = new TbAuthPersonnelExample();
			example.createCriteria().andENG_NMEqualTo(personnel.getENG_NM());
			List<TbAuthPersonnel> result = personnelDao.getPersonnelsByExample(example);
			if (result.size() > 0) {
				throw new DataExistsException("無法新增，帳號已重複!");
			} else {

				String crUser = getLoginUser().getUsername();
				Date crDate = new Date();
				personnel.setMD_USER(crUser); // 建立者
				personnel.setMD_TIME(crDate); // 建立時間
				personnel.setPRIMARY_DEPT_POS_ID(personnel.getDEPT_ID() + "__"
						+ personnel.getJOB_TTL());
				personnel.setPSN_TYPE("E"); // 員工類型

				TbOrgPsnPos record = new TbOrgPsnPos();
				record.setPSN_NO(personnel.getPSN_NO());
				record.setDEPT_POS_ID(personnel.getPRIMARY_DEPT_POS_ID());
				record.setMD_USER(crUser);
				record.setMD_TIME(crDate);
				personnelDao.saveNewPersonnel(personnel);
				psnPosDao.insertOrgPsnPos(record);
			}
		}
	}

	/**
	 * 使用者帳號修改
	 * 
	 * @param personnel
	 */
	@Transactional
	public void saveUpdatePersonnel(TbAuthPersonnel personnel) {
		String mdUser = getLoginUser().getUsername();
		Date mdDate = new Date();

		personnel.setMD_USER(mdUser);
		personnel.setMD_TIME(mdDate);
		String primaryDeptPosId = personnel.getDEPT_ID() + "__" + personnel.getJOB_TTL();
		personnel.setPRIMARY_DEPT_POS_ID(primaryDeptPosId);

		// 未修改前之psn
		TbAuthPersonnel firstPsn = personnelDao.selectPersonnelByPsnId(personnel.getPSN_ID());
		if (StringUtils.isNotEmpty(firstPsn.getPRIMARY_DEPT_POS_ID())) {
			// 為避免無限增加PsnPos，針對psnPos查詢未修改與欲修改之PRIMARY_DEPT_POS_ID是否存在
			List<String> primaryDept = new ArrayList<String>();
			primaryDept.add(firstPsn.getPRIMARY_DEPT_POS_ID());
			primaryDept.add(personnel.getPRIMARY_DEPT_POS_ID());

			TbOrgPsnPosExample psoExample = new TbOrgPsnPosExample();
			TbOrgPsnPosExample.Criteria firstCriteria = psoExample.createCriteria();
			firstCriteria.andPSN_NOEqualTo(personnel.getPSN_NO()).andDEPT_POS_IDIn(primaryDept);

			List<TbOrgPsnPos> result = psnPosDao.selectByExample(psoExample);

			// 以psn未修改前之PRIMARY_DEPT_POS_ID與欲修改之PRIMARY_DEPT_POS_ID當查詢條件，針對psnPos查詢。
			// 當2筆，代表未修改與欲修改之PRIMARY_DEPT_POS_ID皆存在於psnPos，則需針對psnPos
			// delete未修改之PRIMARY_DEPT_POS_ID，欲修改之PRIMARY_DEPT_POS_ID已存在，不動作。
			// 當1筆，正常流程下，必為未修改之PRIMARY_DEPT_POS_ID，因此，需針對psnPos
			// update未修改之PRIMARY_DEPT_POS_ID為欲修改之PRIMARY_DEPT_POS_ID。
			if (result.size() == 2) {
				TbOrgPsnPosKey key = new TbOrgPsnPosKey();
				key.setPSN_NO(personnel.getPSN_NO());
				key.setDEPT_POS_ID(firstPsn.getPRIMARY_DEPT_POS_ID());
				psnPosDao.deleteByPrimaryKey(key);
			} else if (result.size() == 1) {
				TbOrgPsnPos recordPOS = new TbOrgPsnPos();
				recordPOS.setPSN_NO(personnel.getPSN_NO());
				recordPOS.setDEPT_POS_ID(personnel.getPRIMARY_DEPT_POS_ID());
				recordPOS.setMD_USER(mdUser);
				recordPOS.setMD_TIME(mdDate);

				TbOrgPsnPosExample example = new TbOrgPsnPosExample();
				example.createCriteria().andPSN_NOEqualTo(personnel.getPSN_NO())
						.andDEPT_POS_IDEqualTo(firstPsn.getPRIMARY_DEPT_POS_ID());
				psnPosDao.updateByExample(recordPOS, example);
			}
		} else {
			TbOrgPsnPosExample example = new TbOrgPsnPosExample();
			example.createCriteria().andPSN_NOEqualTo(personnel.getPSN_NO());
			List<TbOrgPsnPos> result = psnPosDao.selectByExample(example);
			boolean isexist = false;
			for (int i = 0; i < result.size(); i++) {
				if (result.get(i).getDEPT_POS_ID().equals(primaryDeptPosId)) {
					isexist = true;
					break;
				}
			}
			if (!isexist) {
				TbOrgPsnPos record = new TbOrgPsnPos();
				record.setPSN_NO(personnel.getPSN_NO());
				record.setDEPT_POS_ID(primaryDeptPosId);
				record.setMD_USER(mdUser);
				record.setMD_TIME(mdDate);
				psnPosDao.insertOrgPsnPos(record);
			}
		}
		TbAuthPersonnel personnelSrc = personnelDao.selectPersonnelByPsnId(personnel.getPSN_ID());
		String[] ignoreProperties = { "PSN_ID","PSN_PWD", "EXT_NO",
				"PRIMARY_DEPT_POS_ID", "WORK_SATAUS", "HR_PSN_ID", "PSN_TYPE", "CO_VAT_NO",
				"EMP_USER_ID" };
		BeanUtils.copyProperties(personnel, personnelSrc, ignoreProperties);
		personnelDao.saveUpdatePersonnelByPK(personnelSrc);
	}

	/**
	 * 根據domain取得使用者
	 * 
	 * @param dominList
	 */
	public List<TbAuthPersonnel> getOnJobUserByDomainList(List<String> domainList) {

		return personnelDao.selectOnJobUserByDomain(domainList);

	}

	/**
	 * 用部門ID查詢POS_TYPE='N'相對應的職務
	 * 
	 * @param deptId
	 * @return DeptDTO list
	 */
	public List<DeptDTO> selecPosByDept(String deptId) {
		return personnelDao.selecPosByDept(deptId);
	}

	/**
	 * 查詢登入者所擁有的權限
	 * 
	 * @param psnId
	 *            登入者ID
	 * @return List<TbAuthMenu>
	 */
	public List<TbAuthMenu> getLimitsOfAuthority(int psnId) {

		return personnelDao.getLimitsOfAuthority(psnId);
	}

	/**
	 * 取得部門下所有職稱之人員
	 * 
	 * @param deptID
	 * @return
	 */
	public List<PersonnelDTO> getDeptPersonnels(String deptID) {
		return personnelDao.getDeptPersonnels(deptID);
	}

}
