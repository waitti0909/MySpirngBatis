package com.foya.noms.service.st.line;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.hornetq.utils.json.JSONArray;
import org.hornetq.utils.json.JSONObject;
import org.junit.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbComEqpType;
import com.foya.dao.mybatis.model.TbSiteLineApply;
import com.foya.dao.mybatis.model.TbSiteLineApplyExample;
import com.foya.dao.mybatis.model.TbSiteLineSiteData;
import com.foya.dao.mybatis.model.TbSiteLineSiteDataExample;
import com.foya.dao.mybatis.model.TbSiteMain;
import com.foya.dao.mybatis.model.TbSiteMainExample;
import com.foya.dao.mybatis.model.TbSiteWork;
import com.foya.dao.mybatis.model.TbSiteWorkOrder;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.dao.mybatis.model.TbSysLookupExample;
import com.foya.exception.NomsException;
import com.foya.noms.dao.st.MeterialOptDao;
import com.foya.noms.dao.st.ST002Dao;
import com.foya.noms.dao.st.SiteMainDao;
import com.foya.noms.dao.st.line.LineApplyDao;
import com.foya.noms.dao.st.line.LineSiteDataDao;
import com.foya.noms.dto.st.MeterialRtnDTO;
import com.foya.noms.dto.st.SiteDTO;
import com.foya.noms.dto.st.SiteLineSiteDataDTO;
import com.foya.noms.dto.st.SiteLocationDTO;
import com.foya.noms.dto.st.SiteWorkDTO;
import com.foya.noms.dto.st.line.SiteLineApplyDTO;
import com.foya.noms.enums.EmailType;
import com.foya.noms.enums.LineApplyStatus;
import com.foya.noms.enums.LineStatus;
import com.foya.noms.resources.AppConstants;
import com.foya.noms.service.BaseService;
import com.foya.noms.service.common.EmailTemplateService;
import com.foya.noms.service.st.SiteEmailService;
import com.foya.noms.service.system.UniqueSeqService;
import com.foya.noms.service.workflow.WorkflowActionService;
import com.foya.noms.util.DateUtils;
import com.foya.noms.util.LabelValueModel;
import com.foya.workflow.exception.WorkflowException;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Service
public class LineApplyService extends BaseService {

	@Autowired
	private LineApplyDao lineApplyDao;

	@Autowired
	private LineSiteDataDao lineSiteDataDao;

	@Autowired
	private SiteMainDao siteMainDao;

	@Autowired
	private ST002Dao st002Dao;

	@Autowired
	private UniqueSeqService uniqueSeqService;

	@Autowired
	private WorkflowActionService workflowActionService;

	@Autowired
	private EmailTemplateService emailTemplateService;

	@Autowired
	private MeterialOptDao meterialOptDao;

	@Autowired
	private SiteEmailService siteEmailService;

	/**
	 * 提供請款回壓專線-移機安裝費
	 * 
	 * @param lineId
	 * @param insFee
	 * @return
	 * @author Charlie Woo
	 */
	public String feedbackFromPayment(String lineId, Long insFee) {
		int count = 0;
		if (StringUtils.isNotEmpty(lineId) && insFee != null) {
			TbSiteLineApplyExample example = new TbSiteLineApplyExample();
			example.createCriteria().andLINE_IDEqualTo(lineId)
					.andLINE_STATUSEqualTo(LineStatus.L02.name());
			List<TbSiteLineApply> lines = lineApplyDao.findByConditions(example);
			for (TbSiteLineApply line : lines) {
				line.setINS_FEE(insFee);
				count = lineApplyDao.update(line);
			}
			Assert.assertEquals("failed, 有兩筆以上使用中的相同專線：" + lineId, 1, count);
		} else {
			throw new NomsException("failed, 專線編號或移機安裝費錯誤，請確認！");
		}
		return "success, 更新資料筆數：" + count;
	}

	public List<TbSiteLineApply> selectTbSiteLineApplyByOrderId(String orderId) {
		TbSiteLineApplyExample example = new TbSiteLineApplyExample();
		example.createCriteria().andORDER_IDEqualTo(orderId);
		return lineApplyDao.findByConditions(example);
	}

	/**
	 * 甲端線數
	 */
	public String selectTbSiteLineApplyByALocAndLineStatus(String aLocId, String lineStatus) {
		return selectTbSiteLineApplyByLocAndLineStatus(aLocId, null, lineStatus);
	}

	/**
	 * 乙端線數
	 */
	public String selectTbSiteLineApplyByBLocAndLineStatus(String bLocId, String lineStatus) {
		return selectTbSiteLineApplyByLocAndLineStatus(null, bLocId, lineStatus);
	}

	/**
	 * 同甲乙端線數
	 */
	public String selectTbSiteLineApplyByLocAndLineStatus(String aLocId, String bLocId,
			String lineStatus) {
		TbSiteLineApply siteLineApply = new TbSiteLineApply();

		siteLineApply.setA_LOC(aLocId);
		siteLineApply.setB_LOC(bLocId);
		siteLineApply.setLINE_STATUS(lineStatus);
		SiteLineApplyDTO dto = lineApplyDao.selectLineNumber(siteLineApply);
		if (StringUtils.isEmpty(dto.getLINE_NUMBER())) {
			return "0";
		}
		return dto.getLINE_NUMBER();
	}

	public TbSiteLineApply getOldLineInUseByLineId(String appId, String lineId) {
		TbSiteLineApplyExample example = new TbSiteLineApplyExample();
		example.createCriteria().andLINE_IDEqualTo(lineId).andAPP_IDNotEqualTo(appId)
				.andLINE_STATUSEqualTo(LineStatus.L02.name());
		List<TbSiteLineApply> tbSiteLineApplys = lineApplyDao.findByConditions(example);
		if (tbSiteLineApplys.isEmpty()) {
			return null;
		}
		return tbSiteLineApplys.get(0);
	}

	public List<SiteLineApplyDTO> selectSiteLineApplyDTOByOrderId(String orderId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderId", orderId);
		return lineApplyDao.selectLineApply(map);
	}

	public SiteLineApplyDTO selectSiteLineApplyDTOByAppId(String appId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("appId", appId);
		List<SiteLineApplyDTO> list = lineApplyDao.selectLineApply(map);
		if (list == null || list.size() == 0) {
			return new SiteLineApplyDTO();
		}
		return list.get(0);
	}

	public SiteLineApplyDTO selectSiteLineApplyDTOByLineId(String lineId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("lineId", lineId);
		List<SiteLineApplyDTO> list = lineApplyDao.selectLineApply(map);
		if (list == null || list.size() == 0) {
			return new SiteLineApplyDTO();
		}
		return list.get(0);
	}

	public List<TbSysLookup> findSysLookupByType(String type) {
		TbSysLookupExample example = new TbSysLookupExample();
		if (StringUtils.equals(type, AppConstants.LINE_PURPOSE)) {
			example.createCriteria().andTYPEEqualTo(type).andVALUE1EqualTo("ST");
		} else {
			example.createCriteria().andTYPEEqualTo(type);
		}
		return lineApplyDao.selectByExample(example);

	}

	public List<TbSiteMain> findTbSiteMainByLocationId(String locationId) {
		TbSiteMainExample example = new TbSiteMainExample();
		example.createCriteria().andLOCATION_IDEqualTo(locationId);
		return lineApplyDao.selectByExample(example);
	}

	public SiteLocationDTO findTbSiteLocationByPrimaryKey(String locationId) {
		return lineApplyDao.selectByExample(locationId);
	}

	public List<TbSiteLineSiteData> findTbSiteLineSiteDataByAppIdAndLocationId(String appId,
			String locationID) {
		TbSiteLineSiteDataExample example = new TbSiteLineSiteDataExample();
		example.createCriteria().andAPP_IDEqualTo(appId).andLOC_IDEqualTo(locationID);
		List<TbSiteLineSiteData> result = lineSiteDataDao.findByCondition(example);

		if (result == null) {
			return new ArrayList<TbSiteLineSiteData>();
		}

		return result;
	}

	public TbSiteLineSiteData findTbSiteLineSiteDataByPrimaryKey(String appId, String siteId) {
		TbSiteLineSiteData tbSiteLineSiteData = new TbSiteLineSiteData();
		tbSiteLineSiteData.setAPP_ID(appId);
		tbSiteLineSiteData.setSITE_ID(siteId);
		TbSiteLineSiteData result = lineSiteDataDao.findByPk(tbSiteLineSiteData);

		if (result == null) {
			return new TbSiteLineSiteData();
		}

		return result;
	}

	public TbSiteLineSiteData findTbSiteLineSiteDataByPrimaryKey(String appId) {
		TbSiteLineSiteData tbSiteLineSiteData = new TbSiteLineSiteData();
		tbSiteLineSiteData.setAPP_ID(appId);
		TbSiteLineSiteData result = lineSiteDataDao.findByPk(tbSiteLineSiteData);

		if (result == null) {
			return new TbSiteLineSiteData();
		}

		return result;
	}

	/**
	 * 專用於給機房頁面呼叫取得專線資料
	 * 
	 * @param siteId
	 * @return
	 */
	public List<TbSiteLineApply> getLinDatasBySiteId(String siteId) {
		List<TbSiteLineSiteData> siteLineList = lineApplyDao.findLineDataBySiteId(siteId);
		List<TbSiteLineApply> lineApplyList = new ArrayList<TbSiteLineApply>();
		for (TbSiteLineSiteData siteLine : siteLineList) {
			TbSiteLineApply LineApply = lineApplyDao.findLineApplyByPK(siteLine.getAPP_ID());
			SiteLineApplyDTO lineApplyDto = new SiteLineApplyDTO();
			BeanUtils.copyProperties(LineApply, lineApplyDto);
			TbSiteMainExample example = new TbSiteMainExample();
			example.createCriteria().andLOCATION_IDEqualTo(LineApply.getA_LOC());
			List<TbSiteMain> a_siteMainList = siteMainDao.findByConditions(example);
			example = new TbSiteMainExample();
			example.createCriteria().andLOCATION_IDEqualTo(LineApply.getB_LOC());
			List<TbSiteMain> b_siteMainList = siteMainDao.findByConditions(example);
			String a_btsSiteId = "";
			String b_btsSiteId = "";
			String b_siteId = "";
			int index = 0;
			for (TbSiteMain a_siteMain : a_siteMainList) {
				index += 1;
				if (index == a_siteMainList.size()) {
					a_btsSiteId += a_siteMain.getBTS_SITE_ID();
				} else {
					a_btsSiteId += a_siteMain.getBTS_SITE_ID() + ",";
				}
			}
			index = 0;
			for (TbSiteMain b_siteMain : b_siteMainList) {
				index += 1;
				if (index == b_siteMainList.size()) {
					b_btsSiteId += b_siteMain.getBTS_SITE_ID();
				} else {
					b_btsSiteId += b_siteMain.getBTS_SITE_ID() + ",";
				}

			}
			index = 0;
			for (TbSiteMain b_siteMain : b_siteMainList) {
				index += 1;
				if (index == b_siteMainList.size()) {
					b_siteId += b_siteMain.getSITE_ID();
				} else {
					b_siteId += b_siteMain.getSITE_ID() + ",";
				}

			}
			lineApplyDto.setA_BTS_SITE_ID(a_btsSiteId);
			lineApplyDto.setB_BTS_SITE_ID(b_btsSiteId);
			lineApplyDto.setB_SITE_ID(b_siteId);
			lineApplyList.add(lineApplyDto);

		}
		return lineApplyList;
	}

	/**
	 * 核網設定Service
	 * 
	 * @param appId
	 * @param lineApplyStatus
	 * @return
	 * @author Charlie Woo
	 */
	@Transactional
	public String setupLineNetwork(String appId) {
		try {
			Date today = new Date();
			TbSiteLineApply app = lineApplyDao.findByPk(appId);
			app.setAPP_STATUS(LineApplyStatus.LA05.name());
			app.setMD_TIME(today);
			app.setMD_USER(getLoginUser().getUsername());
			app.setNET_CFG_DATE(today); // 核網設定日期
			update(app);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new NomsException(e.getMessage());
		}
		return AppConstants.SUCCESS;
	}

	/**
	 * 取消申請專線
	 * 
	 * @param appId
	 * @return
	 * @author Charlie Woo
	 */
	@Transactional
	public String cancelLineApply(String appId) {
		try {
			update(appId, LineApplyStatus.LA07.name(), LineStatus.L05.name(), getLoginUser()
					.getUsername());
			siteEmailService.sendLineCancelMail(appId);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new NomsException(e.getMessage());
		}
		return AppConstants.SUCCESS;
	}

	/**
	 * 竣工Service
	 * 
	 * @param appId
	 * @return
	 * @author Charlie Woo
	 */
	@Transactional
	public String completeLine(String appId) {
		try {
			TbSiteLineApply tbSiteLineApply = findTbSiteLineApplyByPrimaryKey(appId);
			String oldLineId = tbSiteLineApply.getO_LINE_ID();
			if (StringUtils.isNotBlank(oldLineId)) {
				// 將舊有專線狀態異動
				TbSiteLineApply oldLineApply = getOldLineInUseByLineId(appId, oldLineId);
				if (oldLineApply != null) {
					updateByLineStatus(oldLineApply.getAPP_ID(), LineStatus.L03.name(),
							getLoginUser().getUsername());
				}
			}
			update(appId, LineApplyStatus.LA06.name(), LineStatus.L02.name(), getLoginUser()
					.getUsername());
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new NomsException(e.getMessage());
		}
		return AppConstants.SUCCESS;
	}

	/**
	 * 專線申請REST(簽核)
	 * 
	 * @param appId
	 * @param action
	 * @author Charlie Woo
	 */
	@Transactional
	public void finishSiteLineApply(String appId, String action) throws NomsException {
		try {
			TbSiteLineApply tbSiteLineApply = findTbSiteLineApplyByPrimaryKey(appId);
			tbSiteLineApply.setMD_USER(getLoginUser().getUsername());
			tbSiteLineApply.setMD_TIME(new Date());
			if (AppConstants.WORKFLOW_REST_REJECT.equals(action)) { // 駁回
				log.info("簽核(駁回) 申請..........");
				update(appId, LineApplyStatus.LA03.name(), getLoginUser().getUsername());
				log.info("END-----簽核(駁回) 申請..........");
			} else { // 核可
				if (StringUtils.equalsIgnoreCase("CAN", tbSiteLineApply.getAPP_TYPE())) {
					String oldLineId = tbSiteLineApply.getO_LINE_ID();
					log.info("簽核(核可) 退租..........");
					log.info("appId : " + appId + ", lineId : " + tbSiteLineApply.getLINE_ID()
							+ " ,oLineId :" + oldLineId);

					if (StringUtils.isNotBlank(oldLineId)) {
						// 將舊有專線狀態異動
						TbSiteLineApply oldLineApply = getOldLineInUseByLineId(appId, oldLineId);
						if (oldLineApply != null) {
							updateByLineStatus(oldLineApply.getAPP_ID(), LineStatus.L04.name(),
									getLoginUser().getUsername());
						}
					}
					update(appId, LineApplyStatus.LA06.name(), LineStatus.L04.name(),
							getLoginUser().getUsername());
					log.info("END------簽核(核可) 退租..........");
				} else {
					log.info("簽核(核可) 非退租申請..........");
					update(appId, LineApplyStatus.LA04.name(), getLoginUser().getUsername());
					siteEmailService.sendLineSetupMail(appId);
					log.info("END-----簽核(核可) 非退租申請..........");
				}
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new NomsException(e.getMessage());
		}
	}

	/**
	 * 用DeptId查詢專線申請資料
	 * 
	 * @param deptIdList
	 * @return
	 */
	public List<SiteLineApplyDTO> getLineApplyListByDeptId(List<String> deptIdList) {
		return lineApplyDao.findLineApplyListByDeptId(deptIdList);
	}

	/**
	 * 查詢專線資料
	 * 
	 * @param map
	 * @return
	 */
	public List<SiteLineApplyDTO> getByConditions(Map<String, String> map) {
		return lineApplyDao.findByConditions(map);
	}

	public List<SiteLineApplyDTO> findLineByConditions(Map<String, String> map) {
		return lineApplyDao.findBySelectLine(map);
	}

	public int update(TbSiteLineApply record) {
		return lineApplyDao.update(record);
	}

	public int update(String appId, String lineApplyStatus, String mdUser) {
		return update(appId, lineApplyStatus, null, mdUser);
	}

	public int update(String appId, String lineApplyStatus, String lineStatus, String mdUser) {
		Date cruurent = new Date();
		TbSiteLineApply record = lineApplyDao.findByPk(appId);
		record.setAPP_STATUS(lineApplyStatus);
		if (StringUtils.isNotEmpty(lineStatus)) {
			record.setLINE_STATUS(lineStatus);
		}
		record.setMD_TIME(cruurent);
		record.setMD_USER(mdUser);
		return lineApplyDao.update(record);
	}

	public int updateByLineStatus(String appId, String lineStatus, String mdUser) {
		return update(appId, null, lineStatus, mdUser);
		// Date cruurent = new Date();
		// TbSiteLineApply record = lineApplyDao.findByPk(appId);
		// record.setLINE_STATUS(lineStatus);
		// record.setMD_TIME(cruurent);
		// record.setMD_USER(mdUser);
		// return lineApplyDao.update(record);
	}

	/**
	 * 用appId查詢專線資料
	 * 
	 * @return
	 */
	public SiteLineApplyDTO getLineApplyByAppId(String appId) {
		return lineApplyDao.findLineApplyByAppId(appId);
	}

	/**
	 * 新增非工務專線維護
	 * 
	 * @param siteLineApply
	 * @return
	 */
	@Transactional
	public TbSiteLineApply insertNonLine(SiteLineApplyDTO siteLineApply) throws NomsException,
			Exception {

		Date crTime = new Date();
		String crUser = getLoginUser().getUsername();
		// 取得申請單號
		String appId = uniqueSeqService.getNextNonStLineAppId();

		// 新增申請單號
		TbSiteLineApply tbSiteLineApp = this.getSiteLineApply(siteLineApply);
		tbSiteLineApp.setAPP_TIME(crTime);
		tbSiteLineApp.setAPP_ID(appId);
		tbSiteLineApp.setAPP_USER(crUser);
		tbSiteLineApp.setAPP_DEPT(getLoginUser().getDeptId());
		tbSiteLineApp.setCR_USER(crUser);
		tbSiteLineApp.setCR_TIME(crTime);
		tbSiteLineApp.setMD_USER(crUser);
		tbSiteLineApp.setMD_TIME(crTime);
		int siteLineAppItem = lineApplyDao.insert(tbSiteLineApp);
		if (siteLineAppItem == 0) {
			throw new NomsException("新增失敗");
		}

		return tbSiteLineApp;
	}

	/**
	 * 更新非工務專線維護
	 * 
	 * @param siteLineApply
	 * @return
	 */
	@Transactional
	public TbSiteLineApply updateNonLine(SiteLineApplyDTO siteLineApply) throws NomsException,
			Exception {

		Date mdTime = new Date();
		String mdUser = getLoginUser().getUsername();

		// 新增申請單號
		TbSiteLineApply tbSiteLineApp = this.getSiteLineApply(siteLineApply);
		tbSiteLineApp.setAPP_ID(siteLineApply.getAPP_ID());
		tbSiteLineApp.setMD_USER(mdUser);
		tbSiteLineApp.setMD_TIME(mdTime);

		int siteLineAppItem = lineApplyDao.update(tbSiteLineApp);
		if (siteLineAppItem == 0) {
			throw new NomsException("更新失敗");
		}

		return tbSiteLineApp;
	}

	/**
	 * 用appId查詢專線資料
	 * 
	 * @return
	 */
	public SiteLineApplyDTO getLineApplyByExcel(String appId) {
		return lineApplyDao.findLineApplyByExcel(appId);
	}

	public HSSFWorkbook generateWorkbook(String sheetName, SiteLineApplyDTO siteLineApple) {
		// 建立一個 workbook
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 建立一個worksheet
		HSSFSheet worksheet = workbook.createSheet(sheetName);
		// 欄位字體
		Font font = worksheet.getWorkbook().createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 加粗字體

		// 儲存格樣式 headerCellStyle
		HSSFCellStyle headerCellStyle = worksheet.getWorkbook().createCellStyle();
		headerCellStyle.setFillBackgroundColor(HSSFColor.RED.index);
		headerCellStyle.setFillPattern(CellStyle.FINE_DOTS);
		headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER); // 左右置中
		headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 垂直置中
		headerCellStyle.setWrapText(true);
		headerCellStyle.setFont(font);
		headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN); // 設定儲存格為粗邊框

		// 標題
		String[] title = { "項次", "區域", "甲端ID", "甲端地址", "甲端聯絡人", "甲端連絡電話", "乙端ID", "乙端地址", "乙端聯絡人",
				"乙端連絡電話", "頻寬", "申請日期", "專線編號" };

		// 建立欄位名稱
		HSSFRow header = worksheet.createRow(0);
		for (int i = 0; i < title.length; i++) {
			header.createCell(i).setCellValue(title[i]);
		}
		//

		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
		String dateFormat = sdFormat.format(siteLineApple.getAPP_TIME());

		// 寫入資料
		int rowIdx = 1;
		HSSFRow excelRow = worksheet.createRow(rowIdx);
		excelRow.createCell(0).setCellValue("1");
		excelRow.createCell(1).setCellValue(siteLineApple.area);
		excelRow.createCell(2).setCellValue(siteLineApple.getLocNameA());
		excelRow.createCell(3).setCellValue(siteLineApple.getLocAddrA());
		excelRow.createCell(4).setCellValue(siteLineApple.getCntPsnA());
		excelRow.createCell(5).setCellValue(siteLineApple.getCntTelA());
		excelRow.createCell(6).setCellValue(siteLineApple.getSiteId());
		excelRow.createCell(7).setCellValue(siteLineApple.getLocAddrB());
		excelRow.createCell(8).setCellValue(siteLineApple.getCntPsnB());
		excelRow.createCell(9).setCellValue(siteLineApple.getCntTelB());
		excelRow.createCell(10).setCellValue(siteLineApple.getLINE_SPEED());
		excelRow.createCell(11).setCellValue(dateFormat);
		excelRow.createCell(12).setCellValue(siteLineApple.getLINE_ID());

		return workbook;
	}

	private TbSiteLineApply getSiteLineApply(SiteLineApplyDTO siteLineApply) {
		TbSiteLineApply siteLineApp = new TbSiteLineApply();
		siteLineApp.setORDER_ID(StringUtils.trimToEmpty(siteLineApp.getORDER_ID())); // modify by
																						// Charlie
																						// 20150127
		siteLineApp.setAPP_DEPT(siteLineApply.getAPP_DEPT());
		siteLineApp.setAPP_TYPE(siteLineApply.getAPP_TYPE());
		siteLineApp.setAPP_STATUS(siteLineApply.getAPP_STATUS()); // modify by Charlie 20150127
		siteLineApp.setLINE_STATUS(siteLineApply.getLINE_STATUS());
		siteLineApp.setAPP_TIME(siteLineApply.getAPP_TIME());
		siteLineApp.setO_LINE_ID(siteLineApply.getO_LINE_ID());
		siteLineApp.setB_LOC(siteLineApply.getB_LOC());
		siteLineApp.setB_NAME(siteLineApply.getB_NAME());
		siteLineApp.setLINE_ID(siteLineApply.getLINE_ID());
		siteLineApp.setB_TEL(siteLineApply.getB_TEL());
		siteLineApp.setRCP_NUM(siteLineApply.getRCP_NUM());
		siteLineApp.setVENDOR(siteLineApply.getVENDOR());
		siteLineApp.setB_ADDR(siteLineApply.getB_ADDR());
		siteLineApp.setCNS_DATE(StringUtils.isBlank(siteLineApply.getLineCnsDate()) ? null
				: DateUtils.parse(siteLineApply.getLineCnsDate(), "yyyy/MM/dd HH:mm:ss"));
		siteLineApp.setLINE_PURPOSE(siteLineApply.getLINE_PURPOSE());
		siteLineApp.setCNT_PSN(siteLineApply.getCNT_PSN());
		siteLineApp.setSTART_DATE(StringUtils.isBlank(siteLineApply.getLineStartDate()) ? null
				: DateUtils.parse(siteLineApply.getLineStartDate(), "yyyy/MM/dd HH:mm:ss"));
		siteLineApp.setLINE_TYPE(siteLineApply.getLINE_TYPE());
		siteLineApp.setCNT_TEL(siteLineApply.getCNT_TEL());
		siteLineApp.setRENT_END_DATE(StringUtils.isBlank(siteLineApply.getLineRentEndDate()) ? null
				: DateUtils.parse(siteLineApply.getLineRentEndDate(), "yyyy/MM/dd HH:mm:ss"));
		siteLineApp.setLINE_SPEED(siteLineApply.getLINE_SPEED());
		siteLineApp.setMON_FEE(siteLineApply.getMON_FEE());
		siteLineApp.setEND_DATE(StringUtils.isBlank(siteLineApply.getLineEndDate()) ? null
				: DateUtils.parse(siteLineApply.getLineEndDate(), "yyyy/MM/dd HH:mm:ss"));
		siteLineApp.setAPP_DESC(siteLineApply.getAPP_DESC());

		return siteLineApp;
	}

	/**
	 * 新增專線申請資料
	 * 
	 * @param jsonArrStr
	 * @param tbSiteLineApply
	 * @param userName
	 * @param currentDate
	 * @throws NomsException
	 * @throws Exception
	 */
	@Transactional
	public void insertLineApply(String jsonArrStr, TbSiteLineApply tbSiteLineApply,
			String userName, Date currentDate) throws NomsException, Exception {
		insertOrUpdate(jsonArrStr, tbSiteLineApply, userName, currentDate);

	}

	private void insertOrUpdate(String jsonArrStr, TbSiteLineApply tbSiteLineApply,
			String userName, Date currentDate) throws NomsException, Exception {

		String applyId = tbSiteLineApply.getAPP_ID();

		if (StringUtils.isBlank(applyId)) {
			applyId = uniqueSeqService.getNextLineAppId(tbSiteLineApply.getORDER_ID());
			tbSiteLineApply.setMD_USER(userName);
			tbSiteLineApply.setMD_TIME(currentDate);
			tbSiteLineApply.setCR_USER(userName);
			tbSiteLineApply.setCR_TIME(currentDate);
			tbSiteLineApply.setAPP_ID(applyId);
			lineApplyDao.insert(tbSiteLineApply);
		} else {
			// 維護畫面可修改資料, 因update 會判斷是否為null, 需改為空白才會update欄位成功.
			String rcpNum = tbSiteLineApply.getRCP_NUM();
			String aPortPos = tbSiteLineApply.getA_PORT_POS();
			String bPortPos = tbSiteLineApply.getB_PORT_POS();
			tbSiteLineApply.setRCP_NUM(rcpNum == null ? "" : rcpNum);
			tbSiteLineApply.setA_PORT_POS(aPortPos == null ? "" : aPortPos);
			tbSiteLineApply.setB_PORT_POS(bPortPos == null ? "" : bPortPos);

			tbSiteLineApply.setMD_USER(userName);
			tbSiteLineApply.setMD_TIME(currentDate);
			lineApplyDao.update(tbSiteLineApply);
		}

		TbSiteLineSiteDataExample example = new TbSiteLineSiteDataExample();
		example.createCriteria().andAPP_IDEqualTo(applyId);
		lineApplyDao.delete(example);

		TbSiteLineSiteData tbSiteLineSiteData = null;
		JSONObject obj = null;
		JSONArray jsonArray = new JSONArray(jsonArrStr);

		for (int i = 0; i < jsonArray.length(); i++) {
			obj = jsonArray.getJSONObject(i);
			tbSiteLineSiteData = new TbSiteLineSiteData();
			tbSiteLineSiteData.setAPP_ID(tbSiteLineApply.getAPP_ID());
			tbSiteLineSiteData.setSITE_ID(obj.optString("SITE_ID"));
			tbSiteLineSiteData.setLOC_ID(obj.optString("LOC_ID"));
			tbSiteLineSiteData.setVLAN(obj.optString("VLAN"));
			tbSiteLineSiteData.setIP(obj.optString("IP"));
			tbSiteLineSiteData.setGATEWAY(obj.optString("GATEWAY"));
			tbSiteLineSiteData.setSUBMASK(obj.optString("SUBMASK"));
			tbSiteLineSiteData.setSHARE_RATE(obj.optInt("SHARE_RATE"));
			tbSiteLineSiteData.setMD_TIME(tbSiteLineApply.getMD_TIME());
			tbSiteLineSiteData.setMD_USER(tbSiteLineApply.getMD_USER());
			tbSiteLineSiteData.setCR_TIME(tbSiteLineApply.getMD_TIME());
			tbSiteLineSiteData.setCR_USER(tbSiteLineApply.getMD_USER());
			lineApplyDao.insert(tbSiteLineSiteData);
		}

	}

	/**
	 * 專線申請-申請(送審)
	 * 
	 * @param jsonArrStr
	 * @param tbSiteLineApply
	 * @param userName
	 * @param currentDate
	 * @throws NomsException
	 * @throws WorkflowException
	 * @throws Exception
	 */
	@Transactional
	public void apply(String jsonArrStr, TbSiteLineApply tbSiteLineApply, String userName,
			Date currentDate) throws NomsException, WorkflowException, Exception {

		tbSiteLineApply.setAPP_STATUS(LineApplyStatus.LA02.name());
		tbSiteLineApply.setAPP_TIME(currentDate);
		insertOrUpdate(jsonArrStr, tbSiteLineApply, userName, currentDate);

		String appId = tbSiteLineApply.getAPP_ID();
		log.info("送出 專線申請workflow.... : " + appId);

		Map<String, Object> emailVars = emailTemplateService.getMailVarMapForWorkflow(
				EmailType.WORKFLOW_TODO_WORK_LEASE_LINE_APPLY, appId);
		workflowActionService.apply("SiteBuildLeaseLineApply", appId, "專線申請", emailVars);

		log.info("成功！！！！送出 專線申請workflow.... : " + tbSiteLineApply.getAPP_ID());
	}

	/**
	 * 依照example查詢SiteLineSiteDate
	 * 
	 * @param example
	 * @return
	 */
	public List<TbSiteLineSiteData> getSiteLineSiteDateByCondition(TbSiteLineSiteDataExample example) {
		return lineSiteDataDao.findByCondition(example);
	}

	/**
	 * 依照siteId查詢SiteLineSiteDate
	 * 
	 * @param siteIdList
	 * @return
	 */
	public List<SiteLineSiteDataDTO> getSiteLineSiteDateBySiteId(List<String> siteIdList) {
		return lineSiteDataDao.findSiteLineSiteDateBySiteId(siteIdList);
	}

	public PageList<SiteLineApplyDTO> findBySelectLine(Map<String, String> map) {
		return (PageList<SiteLineApplyDTO>) lineApplyDao.findBySelectLine(map);
	}

	public List<SiteWorkDTO> findWorkListByDeptId(List<String> deptIdList) {
		return st002Dao.findWorkListByDeptId(deptIdList);
	}

	public HSSFWorkbook generateWorkbook(String sheetName, String appId) {
		// 建立一個 workbook
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 建立一個worksheet
		HSSFSheet worksheet = workbook.createSheet(sheetName);
		// 欄位字體
		Font font = worksheet.getWorkbook().createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 加粗字體

		// 儲存格樣式 headerCellStyle
		HSSFCellStyle headerCellStyle = worksheet.getWorkbook().createCellStyle();
		headerCellStyle.setFillBackgroundColor(HSSFColor.RED.index);
		headerCellStyle.setFillPattern(CellStyle.FINE_DOTS);
		headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER); // 左右置中
		headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 垂直置中
		headerCellStyle.setWrapText(true);
		headerCellStyle.setFont(font);
		headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN); // 設定儲存格為粗邊框

		// 標題
		String[] title = { "項次", "區域", "甲端ID", "甲端地址", "甲端聯絡人", "甲端連絡電話", "乙端ID", "乙端地址", "乙端聯絡人",
				"乙端連絡電話", "頻寬", "申請日期", "專線編號" };

		// 建立欄位名稱
		HSSFRow header = worksheet.createRow(0);
		for (int i = 0; i < title.length; i++) {
			header.createCell(i).setCellValue(title[i]);
		}
		//

		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");

		int rowIdx = 1;
		SiteLineApplyDTO siteLineApplyDTO = lineApplyDao.selectByExcelExport(appId);
		String dateFormat = sdFormat.format(siteLineApplyDTO.getAPP_TIME());
		// 寫入資料
		HSSFRow excelRow = worksheet.createRow(rowIdx);
		excelRow.createCell(0).setCellValue(rowIdx);
		excelRow.createCell(1).setCellValue(siteLineApplyDTO.getMAINT_AREA());
		excelRow.createCell(2).setCellValue(siteLineApplyDTO.getA_LOC_NAME());
		excelRow.createCell(3).setCellValue(siteLineApplyDTO.getA_ADDR());
		excelRow.createCell(4).setCellValue(siteLineApplyDTO.getA_CNT_NAME());
		excelRow.createCell(5).setCellValue(siteLineApplyDTO.getA_CNT_TEL());
		excelRow.createCell(6).setCellValue(siteLineApplyDTO.getB_LOC_NAME());
		excelRow.createCell(7).setCellValue(siteLineApplyDTO.getB_ADDR());
		excelRow.createCell(8).setCellValue(siteLineApplyDTO.getB_CNT_NAME());
		excelRow.createCell(9).setCellValue(siteLineApplyDTO.getB_CNT_TEL());
		excelRow.createCell(10).setCellValue(siteLineApplyDTO.getLINE_SPEED_NAME());
		excelRow.createCell(11).setCellValue(dateFormat);
		excelRow.createCell(12).setCellValue(siteLineApplyDTO.getLINE_ID());
		return workbook;
	}

	/**
	 * 查詢站台主檔資訊By LocId
	 * 
	 * @param locId
	 * @return
	 */
	public List<SiteDTO> findSiteMainByLocid(String locId) {
		return lineApplyDao.selectSiteMainByLocid(locId);
	}

	public TbSiteWork getSiteWork(String orderId) {
		TbSiteWorkOrder siteWorkOrder = lineApplyDao.selectSiteWorkOrderByPrimaryKey(orderId);
		if (siteWorkOrder == null) {
			return new TbSiteWork();
		}
		TbSiteWork siteWork = lineApplyDao.selectSiteWorkByPrimaryKey(siteWorkOrder.getWORK_ID());

		if (siteWork == null) {
			return new TbSiteWork();
		}

		return siteWork;
	}

	/**
	 * 取得設備型態名稱
	 * 
	 * @param eqpTypeId
	 * @return
	 */
	public String getEqpTypeLabel(String eqpTypeId) {
		TbComEqpType tbComEqpType = lineApplyDao.selectTbComEqpTypeByPrimaryKey(eqpTypeId);
		if (tbComEqpType == null) {
			return null;
		}
		return tbComEqpType.getEQP_NAME();
	}

	public TbSiteLineApply findTbSiteLineApplyByPrimaryKey(String appId) {
		return lineApplyDao.findByPk(appId);
	}

	/**
	 * 查詢設備資料
	 * 
	 * @param siteId
	 * @return
	 */
	public List<LabelValueModel> selectRemItemQuery(String siteId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("siteId", siteId);
		map.put("ctrlType", "S");
		List<LabelValueModel> meterialValueModel = new ArrayList<LabelValueModel>();
		for (MeterialRtnDTO dto : meterialOptDao.selectRemItemQuery(map)) {
			meterialValueModel.add(new LabelValueModel(dto.getMatName() + "-" + StringUtils.trimToEmpty(dto.getTagNo()), dto.getSrlNo()));
		}

		return meterialValueModel;
	}
}
