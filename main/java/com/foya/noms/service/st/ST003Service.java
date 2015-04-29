package com.foya.noms.service.st;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbOrgDeptExample;
import com.foya.dao.mybatis.model.TbOrgDeptPos;
import com.foya.dao.mybatis.model.TbOrgPsnPos;
import com.foya.dao.mybatis.model.TbSiteAntCfg;
import com.foya.dao.mybatis.model.TbSiteLocation;
import com.foya.dao.mybatis.model.TbSiteMain;
import com.foya.dao.mybatis.model.TbSiteOutsourcing;
import com.foya.dao.mybatis.model.TbSiteOutsourcingExample;
import com.foya.dao.mybatis.model.TbSiteSearch;
import com.foya.dao.mybatis.model.TbSiteSearchExample;
import com.foya.dao.mybatis.model.TbSiteSearchKey;
import com.foya.dao.mybatis.model.TbSiteSrAntCfgTemp;
import com.foya.dao.mybatis.model.TbSiteSrAntCfgTempExample;
import com.foya.dao.mybatis.model.TbSiteSrSiteTemp;
import com.foya.dao.mybatis.model.TbSiteSrSiteTempKey;
import com.foya.dao.mybatis.model.TbSiteWork;
import com.foya.dao.mybatis.model.TbSiteWorkOrder;
import com.foya.dao.mybatis.model.TbSiteWorkOrderExample;
import com.foya.dao.mybatis.model.TbSysOrderPage;
import com.foya.dao.mybatis.model.TbSysOrderPageExample;
import com.foya.exception.CreateFailException;
import com.foya.exception.NomsException;
import com.foya.exception.UpdateFailException;
import com.foya.noms.dao.auth.PersonnelDao;
import com.foya.noms.dao.common.TownDomainTeamDao;
import com.foya.noms.dao.org.DeptDao;
import com.foya.noms.dao.org.DeptPosDao;
import com.foya.noms.dao.org.PsnPosDao;
import com.foya.noms.dao.st.AntCfgDao;
import com.foya.noms.dao.st.LocationDao;
import com.foya.noms.dao.st.OutsourcingDao;
import com.foya.noms.dao.st.ST003Dao;
import com.foya.noms.dao.st.SearchDao;
import com.foya.noms.dao.st.SearchRecordDao;
import com.foya.noms.dao.st.SiteMainDao;
import com.foya.noms.dao.st.SrAntCfgDao;
import com.foya.noms.dao.st.SrSiteTempDao;
import com.foya.noms.dao.st.WorkDao;
import com.foya.noms.dao.st.WorkOrderDao;
import com.foya.noms.dto.auth.UserDTO;
import com.foya.noms.dto.basic.CompanyDTO;
import com.foya.noms.dto.org.DeptDTO;
import com.foya.noms.dto.st.SiteWorkDTO;
import com.foya.noms.dto.st.SiteWorkOrderDTO;
import com.foya.noms.enums.EmailType;
import com.foya.noms.enums.OrderStatus;
import com.foya.noms.enums.OutSourceStatus;
import com.foya.noms.enums.SiteStatus;
import com.foya.noms.enums.SiteType;
import com.foya.noms.enums.WorkStatus;
import com.foya.noms.enums.WorkType;
import com.foya.noms.resources.AppConstants;
import com.foya.noms.service.BaseService;
import com.foya.noms.service.common.EmailTemplateService;
import com.foya.noms.service.system.RespUserService;
import com.foya.noms.service.system.UniqueSeqService;
import com.foya.noms.service.workflow.WorkflowActionService;
import com.foya.noms.util.LabelValueModel;
import com.foya.workflow.exception.WorkflowException;

@Service
public class ST003Service extends BaseService {

	@Autowired
	private ST003Dao st003Dao;

	@Autowired
	private DeptDao deptDao;

	@Autowired
	private DeptPosDao deptPosDao;

	@Autowired
	private PsnPosDao psnPosDao;

	@Autowired
	private PersonnelDao personnelDao;

	@Autowired
	private SearchDao searchDao;

	@Autowired
	private WorkDao workDao;

	@Autowired
	private WorkOrderDao workOrderDao;

	@Autowired
	private LocationDao locationDao;

	@Autowired
	private UniqueSeqService uniqueSeqService;

	@Autowired
	private SrSiteTempDao srSiteTempDao;

	@Autowired
	private SiteMainDao siteMainDao;

	@Autowired
	private SrAntCfgDao srAntCfgDao;

	@Autowired
	private AntCfgDao antCfgDao;

	@Autowired
	private SearchRecordDao searchRecordDao;

	@Autowired
	private OutsourcingDao outsourcingDao;

	@Autowired
	private WorkflowActionService workflowActionService;

	@Autowired
	private RespUserService respUserService;

	@Autowired
	private SiteEmailService siteEmailService;

	@Autowired
	private EmailTemplateService emailTemplateService;

	@Autowired
	private ST002Service sT002Service;

	@Autowired
	private SiteStatusLogService siteStatusLogService;
	@Autowired
	private TownDomainTeamDao townDomainTeamDao;

	/**
	 * 查詢全部siteWorkOrder
	 * 
	 * @param map
	 * @return
	 */
	public List<SiteWorkOrderDTO> getWorkLOrderist(Map<String, Object> map) {
		return st003Dao.findWorkOrderList(map);
	}

	/**
	 * 查詢頁面-接工單位下拉選單
	 * 
	 * @param deptList
	 * @return
	 */
	public List<LabelValueModel> getDeptAll(List<String> deptIdList) {
		// TbSiteWorkOrderExample example = new TbSiteWorkOrderExample();
		List<String> allDeptTemp = new ArrayList<>();

		List<String> deptList = st003Dao.findDeptAll();
		List<String> teamList = st003Dao.findTeamAll();
		// 將兩個單位合併
		allDeptTemp.addAll(teamList);
		allDeptTemp.addAll(deptList);

		// 搜尋出 交集 的 接工 & 負責單位
		Set<String> allDeptSet = new HashSet<>(allDeptTemp);
		Set<String> deptIdSet = new HashSet<>(deptIdList);
		allDeptSet.retainAll(deptIdSet);

		// 利用 查詢出來的 交集條件 組合出 代碼中文
		TbOrgDeptExample example = new TbOrgDeptExample();
		example.createCriteria().andDEPT_IDIn(deptIdList);
		List<TbOrgDept> list = deptDao.findByCondition(example);
		List<LabelValueModel> deptTemp = new ArrayList<LabelValueModel>();
		for (TbOrgDept temp : list) {
			deptTemp.add(new LabelValueModel(temp.getDEPT_NAME(), temp.getDEPT_ID()));
		}
		return deptTemp;
	}

	/**
	 * 派工頁-負責人 (利用 deptId 找到 職稱資料)
	 */
	public List<TbOrgDeptPos> findRepUserList(String repDept) {
		return deptPosDao.getDeptPosByDeptId(repDept);
	}

	/**
	 * 派工頁-負責人 (利用findRepUserList 找到的 DeptPosID職稱 找到 人員選單)
	 */
	public List<TbOrgPsnPos> findPsnNo(String deptPosID) {
		return psnPosDao.selectPsnNoList(deptPosID);
	}

	/**
	 * 派工頁-負責人 (利用 psnNo找出 ChnNM)
	 */
	public List<TbAuthPersonnel> findPsnName(String psnNo) {
		return personnelDao.searchPsnsByPsnNo(psnNo);
	}

	/**
	 * 派工頁-負責人 (利用 deptId找出 ChnNM)
	 */
	public List<TbAuthPersonnel> findDeptIdByPsnName(String deptId) {
		return personnelDao.selectByDeptId(deptId);
	}

	/**
	 * 派工頁-負責單位
	 * 
	 * @param deptList
	 * @return
	 */
	public List<TbOrgDept> findResponsibleUnitList(String hierarchy) {
		TbOrgDeptExample example = new TbOrgDeptExample();
		example.createCriteria().andDEPT_LEVELEqualTo("TM").andHIERARCHYLike(hierarchy + "%");
		example.setOrderByClause("DEPT_NAME ASC");
		List<TbOrgDept> depts = deptDao.findByCondition(example);
		// List<DeptDTO> sortDepts =
		// st003Dao.findResponsibleUnitList(hierarchy);
		TreeMap<Integer, TbOrgDept> map = new TreeMap<>();
		for (TbOrgDept dept : depts) {
			String team = StringUtils.split(dept.getDEPT_NAME(), "-")[1].toUpperCase();
			team = StringUtils.replace(team, "TEAM", "");
			map.put(Integer.valueOf(team), dept);
		}
		List<TbOrgDept> sortDepts = new LinkedList<>();
		sortDepts.addAll(map.values());
		return sortDepts;
	}

	/**
	 * 委外廠商
	 * 
	 * @return
	 */
	public List<CompanyDTO> getCompany(String coVatNo) {
		return st003Dao.findCompany(coVatNo);
	}

	/**
	 * 委外廠商
	 * 
	 * @return
	 */
	public List<CompanyDTO> getCompanyAll() {
		return st003Dao.findCompanyAll();
	}

	/**
	 * 用workId查詢siteWork
	 * 
	 * @return
	 */
	public SiteWorkDTO getSiteWorkByWorkId(String workId) {
		return st003Dao.findSiteWorkByWorkId(workId);
	}

	/**
	 * 用deptID查詢Hierarchy
	 */
	public DeptDTO getDeptIdByHierarchy(String repDept) {
		return st003Dao.searchDeptById(repDept);
	}

	/**
	 * 派工
	 */
	@Transactional
	public boolean assignOrder(Map<String, Object> map) {
		try {
			map.put("mdTime", new Date());
			map.put("assignTime", new Date());
			map.put("mdUser", getLoginUser().getUsername());

			// 若無選擇負責單位，則與接工部門相同 modify by Charlie 20150204
			if (map.get("maintainTeam") == null) {
				map.put("maintainTeam", workOrderDao.findOrderByPk((String) map.get("orderId")).getREP_DEPT());
			}

			st003Dao.updateAssignOrder(map);

			// send email add by Charlie 20150121
			siteEmailService.sendOrderPickupMail((String) map.get("orderId"));

		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new NomsException(ExceptionUtils.getFullStackTrace(e));
		}

		return true;
	}

	/**
	 * 接工
	 */
	public int updatePackWork(Map<String, String> map) {
		return st003Dao.updatePickWork(map);
	}

	/**
	 * 接工(儲存)
	 */
	public int updateSaveTab1(String orderId, String endDesc, TbSiteWork siteWork, UserDTO user) {
		TbSiteWorkOrder record = workOrderDao.findOrderByPk(orderId);
		record.setEND_DESC(endDesc);
		record.setMD_USER(user.getUsername());
		record.setMD_TIME(new Date());
		workOrderDao.update(record);

		TbSiteWork work = workDao.findByPk(record.getWORK_ID());
		work.setSR_ID(siteWork.getSR_ID());
		work.setSR_LON(siteWork.getSR_LON());
		work.setSR_LAT(siteWork.getSR_LAT());
		work.setCITY(siteWork.getCITY());
		work.setAREA(siteWork.getAREA());
		work.setSR_COVER_RANG(siteWork.getSR_COVER_RANG());
		workDao.update(work);
		return workOrderDao.update(record);
	}

	/**
	 * 退工
	 * 
	 * @param orderId
	 * @param endDesc
	 * @param mdUser
	 * @throws NomsException
	 * @throws UpdateFailException
	 */
	@Transactional
	public void dropOrder(String orderId, String endDesc, String mdUser) throws NomsException {

		Date crurrent = new Date();
		List<String> osStatusList = new ArrayList<String>();
		osStatusList.add(OutSourceStatus.OS07.name());
		osStatusList.add(OutSourceStatus.OS08.name());

		TbSiteOutsourcingExample example = new TbSiteOutsourcingExample();
		example.createCriteria().andORDER_IDEqualTo(orderId).andSTATUSNotIn(osStatusList);
		List<TbSiteOutsourcing> list = outsourcingDao.findSiteOutSoureByExample(example);

		// modify by Charlie 20150309
		if (!list.isEmpty()) {
			throw new NomsException("工單編號 : " + orderId + " 委外工單有非" + OutSourceStatus.OS07.getLocalName() + "或" + OutSourceStatus.OS08.getLocalName());
		}

		TbSiteWorkOrder tbSiteWorkOrder = workOrderDao.findOrderByPk(orderId);
		tbSiteWorkOrder.setORDER_ID(orderId);
		tbSiteWorkOrder.setODR_STATUS(OrderStatus.OR07.name());
		tbSiteWorkOrder.setEND_DESC(endDesc);
		tbSiteWorkOrder.setMD_TIME(crurrent);
		tbSiteWorkOrder.setMD_USER(mdUser);
		int tbSiteWorkOrderUpdate = workOrderDao.updateSelective(tbSiteWorkOrder);

		TbSiteWork tbSiteWork = new TbSiteWork();
		tbSiteWork.setWORK_ID(tbSiteWorkOrder.getWORK_ID());
		tbSiteWork.setWORK_STATUS(WorkStatus.W06.name());
		tbSiteWork.setMD_TIME(crurrent);
		tbSiteWork.setMD_USER(mdUser);
		int tbSiteWorkUpdate = workDao.updateByPrimaryKey(tbSiteWork);

		if (tbSiteWorkUpdate == 0 || tbSiteWorkOrderUpdate == 0) {
			throw new UpdateFailException("tbSiteWorkUpdate = " + tbSiteWorkUpdate + ",tbSiteWorkOrderUpdate=" + tbSiteWorkOrderUpdate);
		}
	}

	/**
	 * 完工(送審核)
	 * 
	 * @throws WorkflowException
	 */
	@Transactional
	public void verifyOrder(String orderId, String workId, String mdUser) throws WorkflowException {

		Date currentDate = new Date();
		TbSiteWorkOrder tbSiteWorkOrder = workOrderDao.findOrderByPk(orderId);// new
																				// TbSiteWorkOrder();
		TbSiteWork work = workDao.findByPk(tbSiteWorkOrder.getWORK_ID());
		tbSiteWorkOrder.setORDER_ID(orderId);
		tbSiteWorkOrder.setODR_STATUS(OrderStatus.OR08.name());
		tbSiteWorkOrder.setMD_USER(mdUser);
		tbSiteWorkOrder.setMD_TIME(currentDate);
		st003Dao.updateByPrimaryKeySelective(tbSiteWorkOrder);

		if (StringUtils.equals(WorkType.NSR.name(), work.getWORK_TYPE())) {
			workflowActionService.apply("SearchSiteCompletionNSR", orderId, "尋點完工New Site Request",
					emailTemplateService.getMailVarMapForWorkflow(EmailType.WORKFLOW_TODO_WORK_COMPLETION_APPLY, orderId));
		} else if (StringUtils.equals(WorkType.SH.name(), work.getWORK_TYPE())) {
			workflowActionService.apply("SearchSiteCompletionSH", orderId, "尋點完工Site Hunt",
					emailTemplateService.getMailVarMapForWorkflow(EmailType.WORKFLOW_TODO_WORK_COMPLETION_APPLY, orderId));
		}
	}

	/**
	 * 完工送審前驗證
	 * 
	 * @param workId
	 * @param orderId
	 * @return
	 */
	public String doValidate(String workId, String orderId) {

		TbSiteSearchExample tbSiteSearchExample = new TbSiteSearchExample();
		tbSiteSearchExample.createCriteria().andWORK_IDEqualTo(workId);
		List<TbSiteSearch> tbSiteSerchList = searchRecordDao.selectByExample(tbSiteSearchExample);

		TbSiteWork work = workDao.findByPk(workId);
		if (StringUtils.equals(WorkType.SH.name(), work.getWORK_TYPE()) && (tbSiteSerchList == null || tbSiteSerchList.isEmpty())) {
			return "作業編號：" + workId + " ,無資料！至少有一筆探勘記錄！";
		}

		List<String> status = new ArrayList<String>();
		status.add(OutSourceStatus.OS07.name());
		status.add(OutSourceStatus.OS08.name());

		TbSiteOutsourcingExample example = new TbSiteOutsourcingExample();
		example.createCriteria().andORDER_IDEqualTo(orderId).andSTATUSNotIn(status);
		List<TbSiteOutsourcing> list = outsourcingDao.findSiteOutSoureByExample(example);
		if (list != null && list.size() > 0) {
			return "工單編號：" + orderId + " ,委外派工申請單須皆已驗收或已取消!";
		}
		return null;
	}

	/**
	 * 尋點點完工簽核New Site Request
	 * 
	 * @param orderId
	 * @param result
	 * @throws NomsException
	 * @throws Exception
	 */
	@Transactional
	public void finishNewSiteRequestOrder(String orderId, String result) throws NomsException, Exception {
		TbSiteWorkOrder siteWorkOrder = workOrderDao.findOrderByPk(orderId);

		if (AppConstants.WORKFLOW_REST_APPROVAL.equals(result)) {
			TbSiteWork siteWork = workDao.findByPk(siteWorkOrder.getWORK_ID());
			siteWork.setWORK_STATUS(WorkStatus.W07.name());
			siteWork.setEND_TIME(new Date());
			int siteWorkItem = workDao.updateByPrimaryKey(siteWork);
			int siteWorkOrderItem = workOrderDao.updateStatusByOrder(siteWorkOrder.getORDER_ID(), OrderStatus.OR09);
			if (siteWorkItem == 0 || siteWorkOrderItem == 0) {
				log.error("update fail siteWorkItem count=" + siteWorkItem + " workId =" + siteWorkOrder.getWORK_ID() + " , "
						+ "update fail siteWorkOrderItem count=" + siteWorkOrderItem + " orderId =" + siteWorkOrder.getORDER_ID());
				throw new UpdateFailException("update fail");
			}
			sT002Service.completionNewSiteRequest(siteWork, false); // 因為不是系設發起才走到這裡完工
		} else {
			TbSiteOutsourcingExample example = new TbSiteOutsourcingExample();
			example.createCriteria().andORDER_IDEqualTo(siteWorkOrder.getORDER_ID());
			List<TbSiteOutsourcing> outsourcing = outsourcingDao.findSiteOutSoureByExample(example);
			if (outsourcing.size() == 0) {
				workOrderDao.updateStatusByOrder(siteWorkOrder.getORDER_ID(), OrderStatus.OR05);
			} else {
				workOrderDao.updateStatusByOrder(siteWorkOrder.getORDER_ID(), OrderStatus.OR07);
			}
		}
	}

	/**
	 * 尋點點完工簽核Site Hunt
	 * 
	 * @param srId
	 * @param srSeq
	 * @param result
	 * @throws UpdateFailException
	 * @throws NomsException
	 */
	@Transactional
	public void finishSiteHuntOrder(String orderId, String srId, String srSeq, String result) throws UpdateFailException, NomsException,
			CreateFailException {
		Date date = new Date();
		TbSiteSearchKey key = new TbSiteSearchKey();
		key.setSR_ID(srId);
		key.setSR_SEQ(srSeq);
		if (AppConstants.WORKFLOW_REST_APPROVAL.equals(result)) {
			TbSiteSearch search = searchDao.findByPk(key);
			TbSiteWorkOrder order = workOrderDao.findOrderByPk(orderId);
			TbSiteWork workRecord = workDao.findByPk(search.getWORK_ID());
			search.setSEARCH_RESULT("Y");
			int updateSearchItem = searchRecordDao.updateSearchRecordByPrimaryKeySelective(search);
			if (updateSearchItem == 0) {
				log.error("update fail updateSearchItem count =" + updateSearchItem + " ,srId =" + srId + " ,srSeq = " + srSeq);
				throw new UpdateFailException("update fail");
			}
			int updateStatusItem = workOrderDao.updateStatusByWork(search.getWORK_ID(), OrderStatus.OR09);
			if (updateStatusItem == 0) {
				log.error("update fail updateStatusItem count =" + updateStatusItem + " workID =" + search.getWORK_ID());
				throw new UpdateFailException("update fail");
			}
			String locationId = "";
			if (StringUtils.isEmpty(search.getLOCATION_ID())) {
				// 如果沒有站點就產生新的站點
				locationId = uniqueSeqService.getNextLocationId(SiteType.B.name(), search.getZIP());
				search.setLOCATION_ID(locationId);
				TbSiteLocation location = this.getLocationBySearch(search, locationId);
				location.setLOC_TYPE(SiteType.B.name());
				location.setMAINT_AREA(townDomainTeamDao.getTownDomainTeamByCityIdTownId(workRecord.getCITY(), workRecord.getAREA()).getDOMAIN());
				location.setMAINT_DEPT(order.getREP_DEPT());
				location.setMAINT_TEAM(order.getREP_TEAM());
				location.setMAINT_USER(order.getREP_USER());
				location.setCR_USER(this.getLoginUser().getUsername());
				location.setCR_TIME(date);
				location.setMD_USER(this.getLoginUser().getUsername());
				location.setMD_TIME(date);
				int insetLocItem = locationDao.insert(location);
				if (insetLocItem == 0) {
					log.error("insert fail insetLocItem count=" + insetLocItem + " locationId =" + locationId);
					throw new CreateFailException("insert fail");
				}
			} else {
				// 有既有的站點就修改
				TbSiteLocation location = this.getLocationBySearch(search, search.getLOCATION_ID());
				location.setMD_USER(this.getLoginUser().getUsername());
				location.setMD_TIME(date);
				int updateLocItem = locationDao.update(location);
				if (updateLocItem == 0) {
					log.error("update fail updateLocItem count=" + updateLocItem + " locationId =" + search.getLOCATION_ID());
					throw new UpdateFailException("update fail");
				}
			}
			TbSiteSrSiteTempKey srSiteTempKey = new TbSiteSrSiteTempKey();
			srSiteTempKey.setSITE_ID(search.getSITE_ID());
			srSiteTempKey.setSR_ID(srId);
			srSiteTempKey.setSR_SEQ(srSeq);
			TbSiteSrSiteTemp srSiteTemp = srSiteTempDao.findByPk(srSiteTempKey);
			if (StringUtils.isNotEmpty(locationId)) {
				srSiteTemp.setLOCATION_ID(locationId);
			}
			TbSiteMain siteMain = this.getSiteMainBySrSiteTemp(srSiteTemp);
			log.debug("siteMain : " + siteMain.toString());
			siteMain.setSITE_STATUS(SiteStatus.S04.name());
			siteMain.setMD_USER(this.getLoginUser().getUsername());
			siteMain.setMD_TIME(date);
			int updateSiteMainItem = siteMainDao.update(siteMain);
			if (updateSiteMainItem == 0) {
				log.error("update fail updateSiteMainItem count=" + updateSiteMainItem + " siteId =" + siteMain.getSITE_ID());
				throw new UpdateFailException("update fail");
			}
			siteStatusLogService.updateSiteStatusLog(siteMain.getSITE_ID(), siteMain.getBTS_SITE_ID(), SiteStatus.S03, SiteStatus.S04, new Date());
			// TbSiteWork workRecord = workDao.findByPk(search.getWORK_ID());
			log.debug("search.getLOCATION_ID() : " + search.getLOCATION_ID());
			workRecord.setLOCATION_ID(search.getLOCATION_ID());
			int workUpdateItem = workDao.update(workRecord);
			if (workUpdateItem == 0) {
				log.error("update fail workUpdateItem count=" + workUpdateItem + " workId =" + search.getWORK_ID());
				throw new UpdateFailException("update fail");
			}
			// 天線
			TbSiteSrAntCfgTempExample example = new TbSiteSrAntCfgTempExample();
			example.createCriteria().andSITE_IDEqualTo(search.getSITE_ID()).andSR_SEQEqualTo(search.getSR_SEQ());
			List<TbSiteSrAntCfgTemp> srAntCfgTempList = srAntCfgDao.findByExample(example);
			for (TbSiteSrAntCfgTemp srAntCfgTemp : srAntCfgTempList) {
				TbSiteAntCfg antCfg = this.getSrAntCfgTempByAntCfg(srAntCfgTemp);
				int antCfgItem = antCfgDao.insert(antCfg);
				if (antCfgItem == 0) {
					log.error("insert fail insetLocItem count=" + antCfgItem + " siteId =" + antCfg.getSITE_ID());
					throw new CreateFailException("insert fail");
				}
			}

			// 檢查是否有下一張排隊工單，若後續還有工單則進行自動派工與通知，反之則作業完工W07
			// TbSiteWorkOrder order = workOrderDao.findOrderByPk(orderId);
			TbSiteWorkOrderExample orderExample = new TbSiteWorkOrderExample();
			orderExample.createCriteria().andODR_SEQGreaterThanOrEqualTo(order.getODR_SEQ()).andWORK_IDEqualTo(order.getWORK_ID())
					.andIS_ACTIVEEqualTo("Y").andORDER_IDNotEqualTo(order.getORDER_ID());
			List<TbSiteWorkOrder> orderList = workOrderDao.findByConditions(orderExample);
			if (orderList.size() != 0) {
				boolean isSameOrderSeqFinish = true;
				// 檢查同一層是否完工
				for (TbSiteWorkOrder orderObj : orderList) {
					if (orderObj.getODR_SEQ() == order.getODR_SEQ()) {
						if (!OrderStatus.OR09.name().equals(orderObj.getODR_STATUS())) {
							isSameOrderSeqFinish = false;
							break;
						}
					}
				}
				// 如果同層完工檢查下一層
				if (isSameOrderSeqFinish) {
					for (TbSiteWorkOrder orderObj : orderList) {
						if (orderObj.getODR_SEQ() == order.getODR_SEQ() + 1) {
							// 如果是0R03就派工
							if (OrderStatus.OR03.name().equals(orderObj.getODR_STATUS())) {
								boolean autoAssign = respUserService.autoAssignOrder(search.getAREA(), orderObj); // 是否自動派工成功
								if (autoAssign) {
									siteEmailService.sendOrderAutoAssignMail(orderObj.getORDER_ID());
									siteEmailService.sendOrderPickupMail(orderObj.getORDER_ID());
								} else {
									siteEmailService.sendOrderAssignMail(orderObj.getORDER_ID());
								}
							}
						}
					}
				}

			} else {// 是最後一層就驗證全部工單是否完工
				orderExample = new TbSiteWorkOrderExample();
				orderExample.createCriteria().andWORK_IDEqualTo(order.getWORK_ID()).andODR_STATUSNotEqualTo(OrderStatus.OR09.name())
						.andIS_ACTIVEEqualTo("Y");
				orderList = workOrderDao.findByConditions(orderExample);
				log.debug("orderList.size() = " + orderList.size());
				if (orderList.size() == 0) {
					TbSiteWork record = workDao.findByPk(search.getWORK_ID());
					if (record != null) {
						record.setWORK_STATUS(WorkStatus.W07.name());
						record.setEND_TIME(date);
						record.setEND_DATE(date);
						int updateApprItem = workDao.updateByPrimaryKey(record);
						if (updateApprItem != 0) {
							siteEmailService.sendWorkApplyFinishMail(record.getWORK_ID());
						} else {
							log.error("update fail updateApprItem count=" + updateApprItem + " workId =" + record.getWORK_ID());
							throw new UpdateFailException("update fail");
						}
					} else {
						log.error("SiteWork is null ,workId = " + search.getWORK_ID());
						throw new NomsException("更新狀態失敗");
					}
				}
			}

		} else if (StringUtils.equals(AppConstants.WORKFLOW_REST_REJECT, result)) {
			TbSiteOutsourcingExample example = new TbSiteOutsourcingExample();
			example.createCriteria().andORDER_IDEqualTo(orderId);
			List<TbSiteOutsourcing> outSourcingList = outsourcingDao.findSiteOutSoureByExample(example);
			int updateStatusItem = 0;
			if (outSourcingList.size() == 0) {
				updateStatusItem = workOrderDao.updateStatusByOrder(orderId, OrderStatus.OR05);
			} else {
				updateStatusItem = workOrderDao.updateStatusByOrder(orderId, OrderStatus.OR06);
			}
			if (updateStatusItem == 0) {
				log.error("update fail updateStatusItem count=" + updateStatusItem + " orderId =" + "orderId");
				throw new UpdateFailException("update fail");
			}
		} else {
			throw new NomsException("Unvaliable action value:" + result + ", Please check flow result.");
		}
	}

	/**
	 * 取尚未完成的工單數量
	 * 
	 * @param workId
	 * @param orderId
	 * @return
	 * @author Charlie Woo
	 */
	public Integer getNotFinishedOrders(String workId, String orderId) {
		TbSiteWorkOrder order = workOrderDao.findOrderByPk(orderId);
		Integer lastOrderSeq = order.getODR_SEQ() - 1;
		List<String> notFinishedStatus = OrderStatus.getNotFinishedStatus();

		// 取得除了尚未完成的有效前置工單
		TbSiteWorkOrderExample example = new TbSiteWorkOrderExample();
		example.createCriteria().andWORK_IDEqualTo(workId).andIS_ACTIVEEqualTo("Y").andODR_SEQEqualTo(lastOrderSeq)
				.andODR_STATUSIn(notFinishedStatus);
		List<TbSiteWorkOrder> orders = workOrderDao.findByConditions(example);
		return orders.isEmpty() ? 0 : orders.size();
	}

	private TbSiteAntCfg getSrAntCfgTempByAntCfg(TbSiteSrAntCfgTemp srAntCfgTemp) {
		TbSiteAntCfg antCfg = new TbSiteAntCfg();
		antCfg.setSITE_ID(srAntCfgTemp.getSITE_ID());
		antCfg.setANT_NO(srAntCfgTemp.getANT_NO());
		antCfg.setCELL_ID(srAntCfgTemp.getCELL_ID());
		antCfg.setSECTOR_ID(srAntCfgTemp.getSECTOR_ID());
		antCfg.setP_CODE(srAntCfgTemp.getP_CODE());
		antCfg.setSEG_SOURCE(srAntCfgTemp.getSEG_SOURCE());
		antCfg.setANT_MODEL(srAntCfgTemp.getANT_MODEL());
		antCfg.setANT_HIGH(srAntCfgTemp.getANT_HIGH());
		antCfg.setANT_ORIENT(srAntCfgTemp.getANT_ORIENT());
		antCfg.setE_TILT(srAntCfgTemp.getE_TILT());
		antCfg.setM_TILT(srAntCfgTemp.getM_TILT());
		antCfg.setSETUP_STYLE(srAntCfgTemp.getSETUP_STYLE());
		antCfg.setCELL_RADIUS(srAntCfgTemp.getCELL_RADIUS());
		antCfg.setANT_SIZE(srAntCfgTemp.getANT_SIZE());
		antCfg.setANT_MFGR(srAntCfgTemp.getANT_MFGR());
		antCfg.setDOWNTILT_R(srAntCfgTemp.getDOWNTILT_R());
		antCfg.setFLOOR_LEVEL(srAntCfgTemp.getFLOOR_LEVEL());
		antCfg.setTOWER_HEIGHT(srAntCfgTemp.getTOWER_HEIGHT());
		antCfg.setPENTHOUSE_HEIGHT(srAntCfgTemp.getPENTHOUSE_HEIGHT());
		antCfg.setADDR(srAntCfgTemp.getADDR());
		antCfg.setLON(srAntCfgTemp.getLON());
		antCfg.setLAT(srAntCfgTemp.getLAT());
		antCfg.setF_CABLE_TYPE(srAntCfgTemp.getF_CABLE_TYPE());
		antCfg.setF_CABLE_LEN(srAntCfgTemp.getF_CABLE_LEN());
		antCfg.setJ_CABLE_LEN(srAntCfgTemp.getJ_CABLE_LEN());
		antCfg.setCOVER_REG(srAntCfgTemp.getCOVER_REG());
		antCfg.setZIP(srAntCfgTemp.getZIP());
		antCfg.setCITY(srAntCfgTemp.getCITY());
		antCfg.setAREA(srAntCfgTemp.getAREA());
		antCfg.setVILLAGE(srAntCfgTemp.getVILLAGE());
		antCfg.setADJACENT(srAntCfgTemp.getADJACENT());
		antCfg.setROAD(srAntCfgTemp.getROAD());
		antCfg.setLANE(srAntCfgTemp.getLANE());
		antCfg.setALLEY(srAntCfgTemp.getALLEY());
		antCfg.setSUB_ALLEY(srAntCfgTemp.getSUB_ALLEY());
		antCfg.setDOOR_NO(srAntCfgTemp.getDOOR_NO());
		antCfg.setDOOR_NO_EX(srAntCfgTemp.getDOOR_NO_EX());
		antCfg.setFLOOR(srAntCfgTemp.getFLOOR());
		antCfg.setFLOOR_EX(srAntCfgTemp.getFLOOR_EX());
		antCfg.setROOM(srAntCfgTemp.getROOM());
		antCfg.setADD_OTHER(srAntCfgTemp.getADD_OTHER());
		return antCfg;
	}

	private TbSiteLocation getLocationBySearch(TbSiteSearch search, String locationId) {
		TbSiteLocation location = new TbSiteLocation();
		location.setLOCATION_ID(locationId);
		location.setLOC_NAME(search.getLOC_NAME());
		location.setLOC_TYPE(search.getLOC_TYPE());
		location.setLOC_DESC(search.getLOC_DESC());
		location.setLON(search.getLON());
		location.setLAT(search.getLAT());
		location.setMAINT_AREA(search.getMAINT_AREA());
		location.setMAINT_DEPT(search.getMAINT_DEPT());
		location.setMAINT_TEAM(search.getMAINT_TEAM());
		location.setMAINT_USER(search.getMAINT_USER());
		location.setBASE_TYPE(search.getBASE_TYPE());
		location.setINDOOR_OPTION(search.getINDOOR_OPTION());
		location.setNS_LEVEL(search.getNS_LEVEL());
		location.setBUILDING_HEIGHT(search.getBUILDING_HEIGHT());
		location.setSHARE_TYPE(search.getSHARE_TYPE());
		location.setIS_FREEENTER(search.getIS_FREEENTER());
		location.setIS_KEY(search.getIS_KEY());
		location.setIS_INDOOR(search.getIS_INDOOR());
		location.setNEED_MISC_LIC(search.getNEED_MISC_LIC());
		location.setHAS_MISC_LIC(search.getHAS_MISC_LIC());
		location.setSR_ID(search.getSR_ID());
		location.setADDR(search.getADDR());
		location.setZIP(search.getZIP());
		location.setCITY(search.getCITY());
		location.setAREA(search.getAREA());
		location.setVILLAGE(search.getVILLAGE());
		location.setADJACENT(search.getADJACENT());
		location.setROAD(search.getROAD());
		location.setLANE(search.getLANE());
		location.setALLEY(search.getALLEY());
		location.setSUB_ALLEY(search.getSUB_ALLEY());
		location.setDOOR_NO(search.getDOOR_NO());
		location.setDOOR_NO_EX(search.getDOOR_NO_EX());
		location.setFLOOR(search.getFLOOR());
		location.setFLOOR_EX(search.getFLOOR_EX());
		location.setROOM(search.getROOM());
		location.setADD_OTHER(search.getADD_OTHER());
		location.setSPACE_INDOOR(search.getSPACE_INDOOR());
		location.setSPACE_LAND(search.getSPACE_LAND());
		location.setSPACE_OUTDOOR(search.getSPACE_OUTDOOR());
		location.setSPACE_PLATFORM(search.getSPACE_PLATFORM());
		location.setSPACE_ROOF(search.getSPACE_ROOF());
		location.setSPACE_ROOM(search.getSPACE_ROOM());
		location.setSPACE_TOP(search.getSPACE_TOP());
		location.setFOOTAGE(search.getFOOTAGE());
		location.setEMP_RELATION(search.getEMP_RELATION());
		return location;
	}

	private TbSiteMain getSiteMainBySrSiteTemp(TbSiteSrSiteTemp srSiteTemp) {
		TbSiteMain siteMain = siteMainDao.findByPk(srSiteTemp.getSITE_ID());
		if (siteMain == null) {
			siteMain = new TbSiteMain();
		}
		siteMain.setSITE_ID(srSiteTemp.getSITE_ID());
		siteMain.setFEQ_ID(srSiteTemp.getFEQ_ID());
		siteMain.setSR_ID(srSiteTemp.getSR_ID());
		siteMain.setBTS_SITE_ID(srSiteTemp.getBTS_SITE_ID());
		siteMain.setSITE_NAME(srSiteTemp.getSITE_NAME());
		siteMain.setLOCATION_ID(srSiteTemp.getLOCATION_ID());
		siteMain.setPURPOSE(srSiteTemp.getPURPOSE());
		siteMain.setEQP_TYPE_ID(srSiteTemp.getEQP_TYPE_ID());
		siteMain.setEQP_MODEL_ID(srSiteTemp.getEQP_MODEL_ID());
		siteMain.setCOVERAGE_TYPE(srSiteTemp.getCOVERAGE_TYPE());
		siteMain.setBTS_CONFIG(srSiteTemp.getBTS_CONFIG());
		siteMain.setDONOR_SITE(srSiteTemp.getDONOR_SITE());
		siteMain.setFEEDERLESS(srSiteTemp.getFEEDERLESS());
		siteMain.setMASTER_SITE(srSiteTemp.getMASTER_SITE());
		siteMain.setCLUSTER(srSiteTemp.getCLUSTER());
		siteMain.setRNC_BTS(srSiteTemp.getRNC_BTS());
		siteMain.setNIOS_RPT_STATUS(srSiteTemp.getNIOS_RPT_STATUS());
		siteMain.setCELL_STATUS(srSiteTemp.getCELL_STATUS());
		siteMain.setOMCU_OBJ(srSiteTemp.getOMCU_OBJ());
		siteMain.setSPEED_DOWNLOAD(srSiteTemp.getSPEED_DOWNLOAD());
		siteMain.setSPEED_UPLOAD(srSiteTemp.getSPEED_UPLOAD());
		siteMain.setPOWER(srSiteTemp.getPOWER());
		siteMain.setCST_ID_CARD_NUM(srSiteTemp.getCST_ID_CARD_NUM());
		siteMain.setCST_MOBILE(srSiteTemp.getCST_MOBILE());
		siteMain.setCST_ID(srSiteTemp.getCST_ID());
		siteMain.setSITE_NAME(srSiteTemp.getSITE_NAME());
		siteMain.setCOVERAGE_INDOOR(srSiteTemp.getCOVERAGE_INDOOR());
		siteMain.setHIDDEN(srSiteTemp.getHIDDEN());
		siteMain.setNOIS_ON_AIR(srSiteTemp.getNOIS_ON_AIR());
		siteMain.setL2_SWITCH(srSiteTemp.getL2_SWITCH());
		siteMain.setCOVERAGE_IN_FLOOR(srSiteTemp.getCOVERAGE_IN_FLOOR());
		return siteMain;
	}

	public TbSiteWorkOrder selectByPrimaryKey(String orderId) {
		return st003Dao.selectByPrimaryKey(orderId);
	}

	public TbOrgDept selectOrgDept(String rep_DEPT) {
		return st003Dao.selectOrgDept(rep_DEPT);
	}

	public List<TbSysOrderPage> selectByExample(TbSysOrderPageExample example) {
		return outsourcingDao.selectByExample(example);
	}

}
