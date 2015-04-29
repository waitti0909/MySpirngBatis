package com.foya.noms.service.st;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbComSiteFeq;
import com.foya.dao.mybatis.model.TbComSiteFeqExample;
import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbOrgDeptExample;
import com.foya.dao.mybatis.model.TbSiteAntCfg;
import com.foya.dao.mybatis.model.TbSiteAntCfgExample;
import com.foya.dao.mybatis.model.TbSiteLocation;
import com.foya.dao.mybatis.model.TbSiteMain;
import com.foya.dao.mybatis.model.TbSiteStatusLog;
import com.foya.dao.mybatis.model.TbSiteWork;
import com.foya.dao.mybatis.model.TbSiteWorkAntCfgTemp;
import com.foya.dao.mybatis.model.TbSiteWorkExample;
import com.foya.dao.mybatis.model.TbSiteWorkLocTemp;
import com.foya.dao.mybatis.model.TbSiteWorkOrder;
import com.foya.dao.mybatis.model.TbSiteWorkOrderExample;
import com.foya.dao.mybatis.model.TbSiteWorkSiteTemp;
import com.foya.dao.mybatis.model.TbSysOrderType;
import com.foya.dao.mybatis.model.TbSysOrderTypeExample;
import com.foya.dao.mybatis.model.TbSysWorkOrderType;
import com.foya.dao.mybatis.model.TbSysWorkOrderTypeExample;
import com.foya.exception.CreateFailException;
import com.foya.exception.NomsException;
import com.foya.exception.UpdateFailException;
import com.foya.noms.dao.common.TownDomainTeamDao;
import com.foya.noms.dao.org.DeptDao;
import com.foya.noms.dao.st.AntCfgDao;
import com.foya.noms.dao.st.AntCfgTempDao;
import com.foya.noms.dao.st.LocTempDao;
import com.foya.noms.dao.st.LocationDao;
import com.foya.noms.dao.st.ST009Dao;
import com.foya.noms.dao.st.SiteMainDao;
import com.foya.noms.dao.st.SiteStatusLogDao;
import com.foya.noms.dao.st.SiteTempDao;
import com.foya.noms.dao.st.WorkDao;
import com.foya.noms.dao.st.WorkOrderDao;
import com.foya.noms.dao.system.OrderTypeDao;
import com.foya.noms.dao.system.WorkOrderTypeDao;
import com.foya.noms.dto.common.TownDomainTeamDTO;
import com.foya.noms.dto.st.SiteBuildApplyDTO;
import com.foya.noms.dto.st.SiteWorkDTO;
import com.foya.noms.dto.system.OrderTypeDTO;
import com.foya.noms.enums.EmailType;
import com.foya.noms.enums.OrderStatus;
import com.foya.noms.enums.PurchaseOrderType;
import com.foya.noms.enums.SiteStatus;
import com.foya.noms.enums.WorkStatus;
import com.foya.noms.enums.WorkType;
import com.foya.noms.resources.AppConstants;
import com.foya.noms.service.BaseService;
import com.foya.noms.service.common.EmailTemplateService;
import com.foya.noms.service.system.OrderTypeService;
import com.foya.noms.service.system.RespUserService;
import com.foya.noms.service.system.UniqueSeqService;
import com.foya.noms.service.workflow.WorkflowActionService;
import com.foya.noms.util.LabelValueModel;

@Service
public class ST009Service extends BaseService{
	
	@Autowired
	private WorkDao workDao;
	@Autowired
	private ST009Dao sT009Dao;
	@Autowired
	private DeptDao deptDao;
	@Autowired
	private WorkOrderTypeDao workOrderTypeDao;
	@Autowired
	private TownDomainTeamDao townDomainTeamDao;
	@Autowired
	private UniqueSeqService uniqueSeqService;
	@Autowired
	private SiteMainDao siteMainDao;
	@Autowired
	private LocationDao locationDao;
	@Autowired
	private OrderTypeDao orderTypeDao;
	@Autowired
	private OrderTypeService orderTypeService;
	@Autowired
	private WorkOrderDao workOrderDao;
	@Autowired
	private EmailTemplateService emailTemplateService;
	@Autowired
	private WorkflowActionService workflowActionService;
	@Autowired
	private SiteEmailService siteEmailService;
	@Autowired
	private LocTempDao locTempDao;
	@Autowired
	private AntCfgDao antCfgDao;
	@Autowired
	private SiteTempDao siteTempDao;
	@Autowired
	private AntCfgTempDao antCfgTempDao;
	@Autowired
	private RespUserService respUserService;
	@Autowired
	private SiteStatusLogDao siteStatusLogDao;
	@Autowired
	private SiteStatusLogService siteStatusLogService;
	
	/**
	 * 查詢頁面-申請單位下拉選單
	 * 
	 * @param deptList
	 * @return
	 */
	public List<LabelValueModel> getAppDeptAll(List<String> deptIdList) {
		
		List<SiteWorkDTO> allDeptTemp = sT009Dao.findDeptAll();
		List<LabelValueModel> deptTemp = new ArrayList<LabelValueModel>();
		
		if (deptIdList != null && !deptIdList.isEmpty()) {
			List<SiteWorkDTO> allDeptList = new ArrayList<SiteWorkDTO>();
			for(SiteWorkDTO appDept : allDeptTemp) {
				for(String deptId : deptIdList){
					if(StringUtils.equals(appDept.getAPP_DEPT(), deptId)) { 
						allDeptList.add(appDept);
					}
				}
			}
			TbOrgDeptExample example = new TbOrgDeptExample();
			List<TbOrgDept> list = deptDao.findByCondition(example);
			
			for (TbOrgDept temp : list) {
				for (SiteWorkDTO dept : allDeptList) {
					if(StringUtils.equals(temp.getDEPT_ID(), dept.getAPP_DEPT())) {
						deptTemp.add(new LabelValueModel(temp.getDEPT_NAME(), temp.getDEPT_ID()));
					}
				}
			}
			return deptTemp;					
		} else {
			
			TbOrgDeptExample example = new TbOrgDeptExample();
			List<TbOrgDept> list = deptDao.findByCondition(example);
			
			for (TbOrgDept temp : list) {
				for (SiteWorkDTO dept : allDeptTemp) {
					if(StringUtils.equals(temp.getDEPT_ID(), dept.getAPP_DEPT())) {
						deptTemp.add(new LabelValueModel(temp.getDEPT_NAME(), temp.getDEPT_ID()));
					}
				}
			}
			return deptTemp;	
		}
	}
	
	/**
	 * 查詢頁面-基站頻段下拉選單
	 * 
	 * @param deptList
	 * @return
	 */
	public List<TbComSiteFeq> getComSiteFeqAll(TbComSiteFeqExample example) {
		return sT009Dao.findComSiteFeqAll(example);	
	}
	
	/**
	 * 查詢頁面-工單類別下拉選單
	 * 
	 * @param deptList
	 * @return
	 */
	public List<TbSysOrderType> getSysOrderTypes(TbSysOrderTypeExample example) {
		return sT009Dao.findSysOrderTypeAll(example);	
	}
	
	/**
	 * 查詢頁面-工單類別下拉選單
	 * 
	 * @param deptList
	 * @return
	 */
	public List<TbSysWorkOrderType> getSysWorkType(TbSysWorkOrderTypeExample example) {
		return workOrderTypeDao.findByConditions(example);	
	}
	
	/**
	 * 查詢 單張工單
	 * @return
	 */
	public List<SiteWorkDTO> getWorkSgnAll(Map<String ,Object> map){
		return sT009Dao.findWorkSgnAll(map);	
	}
	
	/**
	 * 查詢domainTeam
	 */
	public TownDomainTeamDTO domainTeam(String city, String area){
		return townDomainTeamDao.getTownDomainTeamByCityIdTownId(city, area);
	}
	
	@Transactional
	public TbSiteWork insert(SiteBuildApplyDTO singleOrderApply, String loginUserDept) throws NomsException, CreateFailException, Exception {
		
		Date crTime = new Date();
		String loginUser = getLoginUser().getUsername();
		// 取得工單編號
		String workId = uniqueSeqService.getNextWorkId();
		
		TbSiteMain main = siteMainDao.findByPk(singleOrderApply.getSiteId());
		// 站台作業資料
		TbSiteWork siteWork = this.getSiteWorkBySiteBuildApplyDTO(singleOrderApply, workId);
		siteWork.setCR_USER(loginUser);
		siteWork.setCR_TIME(crTime);
		siteWork.setMD_USER(loginUser);
		siteWork.setMD_TIME(crTime);
		siteWork.setEQP_MODEL_ID(main.getEQP_MODEL_ID());
		siteWork.setCOVERAGE_TYPE(main.getCOVERAGE_TYPE());
		siteWork.setBTS_CONFIG(main.getBTS_CONFIG());
		siteWork.setDONOR_SITE(main.getDONOR_SITE());
		siteWork.setFEEDERLESS(main.getFEEDERLESS());
		siteWork.setMASTER_SITE(main.getMASTER_SITE());	
		siteWork.setOS_TYPE(PurchaseOrderType.P.name());
		siteWork.setAPP_DEPT(loginUserDept);
		int siteWorkItem = workDao.insert(siteWork);
		if (siteWorkItem == 0) {
			log.error("siteWorkItem update count= " + siteWorkItem + " , workId = "
					+ siteWork.getWORK_ID());
			throw new CreateFailException("新增失敗");
		}
		
		// 工單資料
		//List<OrderTypeDTO> orderTypeList = orderTypeService.getOrderTypesByWkTypeId(singleOrderApply.getWorkType());
		TownDomainTeamDTO domainTeam = townDomainTeamDao.getTownDomainTeamByCityIdTownId(
				singleOrderApply.getCITY(), singleOrderApply.getAREA());
		//for (OrderTypeDTO orderType : orderTypeList) {
			try{
				TbSiteWorkOrder siteWorkOrder = this.getSiteWorkOrderBySiteBuildApply(singleOrderApply,
						workId, OrderStatus.OR01.name(), uniqueSeqService.getNextOrderId(workId),
						null, domainTeam);
				siteWorkOrder.setCR_USER(loginUser);
				siteWorkOrder.setCR_TIME(crTime);
				siteWorkOrder.setMD_USER(loginUser);
				siteWorkOrder.setMD_TIME(crTime);
				int workOrderItem = workOrderDao.insert(siteWorkOrder);
				if (workOrderItem == 0) {
					log.error("workOrderItem update count= " + workOrderItem + " , orderId = "
							+ siteWorkOrder.getORDER_ID());
					throw new CreateFailException("新增失敗");
				}
			}catch(Exception e){
				throw new NomsException(e.getMessage());
			}
		//}
		
		return siteWork;
		
	}
	
	/**
	 * 修改siteBuildApply
	 * 
	 * @param siteBuildApply
	 * @return
	 */
	@Transactional
	public void update(SiteBuildApplyDTO siteBuildApply, String[] orderIdArray, String mdUser,
			String siteStatus) throws UpdateFailException, Exception {
		Date mdTime = new Date();
		String workId = siteBuildApply.getWorkId();
		for(String orderId : orderIdArray){
			log.debug("orderIdArray : "+orderId);
		}

		// 更新作業主檔
		TbSiteMain main = siteMainDao.findByPk(siteBuildApply.getSiteId());
		TbSiteWork siteWorkTarget = workDao.findByPk(workId);
		TbSiteWork siteWork = this.getSiteWorkBySiteBuildApplyDTO(siteBuildApply, workId);
		siteWork.setMD_USER(mdUser);
		siteWork.setMD_TIME(mdTime);
		siteWork.setEQP_MODEL_ID(main.getEQP_MODEL_ID());
		siteWork.setCOVERAGE_TYPE(main.getCOVERAGE_TYPE());
		siteWork.setBTS_CONFIG(main.getBTS_CONFIG());
		siteWork.setDONOR_SITE(main.getDONOR_SITE());
		siteWork.setFEEDERLESS(main.getFEEDERLESS());
		siteWork.setMASTER_SITE(main.getMASTER_SITE());	
		siteWork.setOS_TYPE(PurchaseOrderType.P.name());
		String[] ignoreProperties = { "WORK_TYPE", "CR_USER", "CR_TIME", "APP_DEPT", "APL_USER",
				"APL_TIME", "END_TIME", "SR_ID", "SR_COVER_RANG", "END_DATE", "CPL_NO",
				"PERMIT_NO", "LICENSE_NO" };
		BeanUtils.copyProperties(siteWork, siteWorkTarget, ignoreProperties);
		int siteWorkItem = workDao.update(siteWorkTarget);
		if (siteWorkItem == 0) {
			log.error("siteWorkItem update count= " + siteWorkItem + " , workId = "
					+ siteWork.getWORK_ID());
			throw new UpdateFailException("更新失敗");
		}
		
		// 更新工單主檔
		if (orderIdArray != null) {
			TbSiteWorkOrderExample example = new TbSiteWorkOrderExample();
			example.createCriteria().andWORK_IDEqualTo(workId);
			List<TbSiteWorkOrder> workOrderList = workOrderDao.findByConditions(example);
			List<String> orderIdList_source = new ArrayList<String>();
			for(TbSiteWorkOrder workOrder : workOrderList){
				orderIdList_source.add(workOrder.getORDER_ID());
			}
			List<String> orderIdList_target = new ArrayList<String>();
			//頁面上有勾選的isActive=Y
			for (String orderId : orderIdArray) {
				TbSiteWorkOrder siteWorkOrder = workOrderDao.findOrderByPk(orderId);
				siteWorkOrder.setIS_ACTIVE("Y");
				siteWorkOrder.setORDER_ID(orderId);
				siteWorkOrder.setPRIORITY(siteBuildApply.getPriority());
				siteWorkOrder.setMD_USER(mdUser);
				siteWorkOrder.setMD_TIME(mdTime);
				siteWorkOrder.setORDER_TYPE(siteBuildApply.getSiteOrderType());
				int workOrderItem = workOrderDao.updateSelective(siteWorkOrder);
				if (workOrderItem == 0) {
					log.error("workOrderItem update count= " + workOrderItem + " , orderId = "
							+ siteWorkOrder.getORDER_ID());
					throw new UpdateFailException("更新失敗");
				}
				orderIdList_target.add(orderId);
			}
			//DB比頁面多表示isActive=N
			List<String> orderIdList_isNotActive = new ArrayList<String>(orderIdList_source);
			orderIdList_isNotActive.removeAll(orderIdList_target);
			//頁面上沒有勾選的isActive=N
			if(orderIdList_isNotActive.size() > 0){
				for(String orderId : orderIdList_isNotActive){
					TbSiteWorkOrder siteWorkOrder = workOrderDao.findOrderByPk(orderId);
					siteWorkOrder.setIS_ACTIVE("N");
					siteWorkOrder.setORDER_ID(orderId);
					siteWorkOrder.setPRIORITY(siteBuildApply.getPriority());
					siteWorkOrder.setMD_USER(mdUser);
					siteWorkOrder.setMD_TIME(mdTime);
					int workOrderItem = workOrderDao.updateSelective(siteWorkOrder);
					if (workOrderItem == 0) {
						log.error("workOrderItem update count= " + workOrderItem + " , orderId = "
								+ siteWorkOrder.getORDER_ID());
						throw new UpdateFailException("更新失敗");
					}
				}
			}
		}		
		// 更新站台主檔
		TbSiteMain siteMain = this.getSiteMainBySiteWork(siteWork, mdTime, siteStatus);
		int mainItem = 0;
		siteMain.setMD_USER(mdUser);
		siteMain.setMD_TIME(mdTime);
		mainItem = siteMainDao.update(siteMain);
		if (mainItem == 0) {
			log.error("mainItem update count= " + mainItem + " , siteId = " + siteMain.getSITE_ID());
			throw new UpdateFailException("更新失敗");
		}
	}
	
	
	private TbSiteMain getSiteMainBySiteWork(TbSiteWork siteWork, Date startTime, String siteStatus) {
		TbSiteMain siteMain = siteMainDao.findByPk(siteWork.getSITE_ID());
		siteMain.setSITE_ID(siteWork.getSITE_ID());
		siteMain.setFEQ_ID(siteWork.getFEQ_ID());
		siteMain.setBTS_SITE_ID(StringUtils.trimToNull(siteWork.getBTS_SITE_ID()));
		siteMain.setPURPOSE(siteWork.getPURPOSE());
		siteMain.setSITE_NAME(siteWork.getSITE_NAME());
		siteMain.setLOCATION_ID(StringUtils.trimToNull(siteWork.getLOCATION_ID()));
		siteMain.setEQP_TYPE_ID(StringUtils.trimToNull(siteWork.getEQP_TYPE_ID()));
		siteMain.setEQP_MODEL_ID(StringUtils.trimToNull(siteWork.getEQP_MODEL_ID()));
		siteMain.setCOVERAGE_TYPE(StringUtils.trimToNull(siteWork.getCOVERAGE_TYPE()));
		siteMain.setBTS_CONFIG(StringUtils.trimToNull(siteWork.getBTS_CONFIG()));
		siteMain.setDONOR_SITE(StringUtils.trimToNull(siteWork.getDONOR_SITE()));
		siteMain.setFEEDERLESS(StringUtils.trimToNull(siteWork.getFEEDERLESS()));
		siteMain.setMASTER_SITE(StringUtils.trimToNull(siteWork.getMASTER_SITE()));
		siteMain.setLON(siteWork.getSR_LON());
		siteMain.setLAT(siteWork.getSR_LAT());
		// String siteStatus = SiteStatus.S05.toString();
		if (StringUtils.isNotEmpty(siteStatus)) {
			siteMain.setSITE_STATUS(siteStatus);
		}
		siteMain.setLICENSE_NO(StringUtils.trimToNull(siteWork.getLICENSE_NO()));
		siteMain.setPERMIT_NO(StringUtils.trimToNull(siteWork.getPERMIT_NO()));
		siteMain.setCPL_NO(StringUtils.trimToNull(siteWork.getCPL_NO()));
		return siteMain;
	}
	
	
	private TbSiteWork getSiteWorkBySiteBuildApplyDTO(SiteBuildApplyDTO siteBuildApply,
			String workId) {
		TbSiteWork siteWork = workDao.findByPk(siteBuildApply.getWorkId());
		if (siteWork == null) {
			siteWork = new TbSiteWork();
		}
		//設備型態
		siteWork.setEQP_TYPE_ID(siteBuildApply.getEqpTypeId());
		// 作業編號
		siteWork.setWORK_ID(workId);
		// 作業類別
		siteWork.setWORK_TYPE(WorkType.SGL.name());
		// 作業狀態
		siteWork.setWORK_STATUS(siteBuildApply.getWorkStatus());
		// 經度
		siteWork.setSR_LON(siteBuildApply.getLon());
		// 緯度
		siteWork.setSR_LAT(siteBuildApply.getLat());
		// 重要等級
		siteWork.setPRIORITY(StringUtils.trimToNull(siteBuildApply.getPriority()));
		// WORK_DESC
		siteWork.setWORK_DESC(StringUtils.trimToNull(siteBuildApply.getWorkDesc()));
		// SITE_ID
		siteWork.setSITE_ID(siteBuildApply.getSiteId());
		// 站台編號
		siteWork.setBTS_SITE_ID(StringUtils.trimToNull(siteBuildApply.getBtsSiteId()));
		// 站台頻段
		if (StringUtils.isNotEmpty(siteBuildApply.getFeqId())) {
			siteWork.setFEQ_ID(siteBuildApply.getFeqId().split(",")[0]);
		}	
		// 站台名稱
		siteWork.setSITE_NAME(siteBuildApply.getSiteName());
		// 預計完工日
		siteWork.setPREDICE_END_DATE(siteBuildApply.getPrediceEndDate());

		// 站點編號
		siteWork.setLOCATION_ID(siteBuildApply.getLocationId());

		// 地址相關欄位
		siteWork.setADDR(siteBuildApply.getADDR());
		siteWork.setCITY(siteBuildApply.getCITY());
		siteWork.setAREA(siteBuildApply.getAREA());
		siteWork.setZIP(siteBuildApply.getZIP());
		siteWork.setVILLAGE(siteBuildApply.getVILLAGE());
		siteWork.setADJACENT(siteBuildApply.getADJACENT());
		siteWork.setROAD(siteBuildApply.getROAD());
		siteWork.setLANE(siteBuildApply.getLANE());
		siteWork.setALLEY(siteBuildApply.getALLEY());
		siteWork.setSUB_ALLEY(siteBuildApply.getSUB_ALLEY());
		siteWork.setDOOR_NO(siteBuildApply.getDOOR_NO());
		siteWork.setDOOR_NO_EX(siteBuildApply.getDOOR_NO_EX());
		siteWork.setFLOOR(siteBuildApply.getFLOOR());
		siteWork.setFLOOR_EX(siteBuildApply.getFLOOR_EX());
		siteWork.setROOM(siteBuildApply.getROOM());
		siteWork.setADD_OTHER(siteBuildApply.getADD_OTHER());

		// 工程類別
		siteWork.setOS_TYPE(siteBuildApply.getOsType());

		// 申請部門(先偷塞以方便列表查詢)，SA分析並不影響後續作業面
		siteWork.setAPP_DEPT(getLoginUser().getDeptId());

		siteWork.setLICENSE_NO(siteBuildApply.getLicenseNo());
		siteWork.setCPL_NO(siteBuildApply.getCplNo());
		siteWork.setPERMIT_NO(siteBuildApply.getPermitNo());
		siteWork.setEQP_TYPE_ID(siteBuildApply.getEqpTypeId());
		if (StringUtils.equals(siteBuildApply.getStartSiteStatus(), "S02") ||
				StringUtils.equals(siteBuildApply.getStartSiteStatus(), "S04")) {
			siteWork.setEND_SITE_STATUS(siteBuildApply.getSiteStatusValue());
		} else {			
			siteWork.setEND_SITE_STATUS(siteBuildApply.getAllSiteStatus());
		}
		
		siteWork.setSTART_SITE_STATUS(siteBuildApply.getStartSiteStatus());

		return siteWork;
	}
	
	private TbSiteWorkOrder getSiteWorkOrderBySiteBuildApply(SiteBuildApplyDTO siteBuildApply,
			String workId, String orderStatus, String orderId, OrderTypeDTO orderType,
			TownDomainTeamDTO domainTeam) throws Exception {
		TbSiteWorkOrder siteWorkOrder = new TbSiteWorkOrder();
		siteWorkOrder.setREP_DEPT(siteBuildApply.getSiteResponsibleUnit());
		//siteWorkOrder.setREP_TEAM(domainTeam.getTEAM());
		siteWorkOrder.setORDER_ID(orderId);
		siteWorkOrder.setWORK_ID(workId);
		siteWorkOrder.setODR_STATUS(orderStatus);
		siteWorkOrder.setPRIORITY(siteBuildApply.getPriority());
		siteWorkOrder.setODR_SEQ(1);
		siteWorkOrder.setORDER_TYPE(siteBuildApply.getSiteOrderType());
		siteWorkOrder.setIS_ACTIVE("Y");
/*		TbOrgDept dept = orderTypeService.getOrderTypeForWorkArea(orderType.getDEPT_KEY(),
				domainTeam);*/
		siteWorkOrder.setREP_DEPT(siteBuildApply.getSiteResponsibleUnit());

		return siteWorkOrder;
	}
	
	/**
	 * 申請
	 * 
	 * @param aplUser
	 * @param workId
	 * @param appDept
	 * @return
	 * @throws Exception
	 * @throws
	 * @throws UpdateFailException
	 */
	@Transactional
	public TbSiteWork updateApplyInfo(String aplUser, SiteBuildApplyDTO siteBuildApply,
			String appDept, String singleOrderApplyEvent, String[] orderIdArray) throws Exception {
		try{
			if(StringUtils.equals(singleOrderApplyEvent, "insert")) {
				//新增
				TbSiteWork siteWork = this.insert(siteBuildApply, appDept);
				siteBuildApply.setWorkId(siteWork.getWORK_ID());
				log.debug("insert siteWork : " + siteWork.toString());
			} else {
				// 儲存
				update(siteBuildApply, orderIdArray, aplUser, SiteStatus.S05.name());				
			}
		}catch(Exception e){
			throw new NomsException(e.getMessage());
		}
		
		// 更新TbSiteWork
		String workId = siteBuildApply.getWorkId();
		TbSiteWork siteWork = workDao.findByPk(workId);
		siteWork.setWORK_ID(workId);
		siteWork.setAPL_USER(aplUser);
		siteWork.setAPL_TIME(new Date());
		siteWork.setAPP_DEPT(appDept);
		siteWork.setWORK_STATUS(WorkStatus.W03.name());
		siteWork.setOS_TYPE(PurchaseOrderType.P.name());
		int workItem = workDao.updateByPrimaryKey(siteWork);
		siteWork = workDao.findByPk(workId);
		
		//回寫siteMain 基站狀態
		String siteId = siteBuildApply.getSiteId();
		
		//寫入status Log
		TbSiteStatusLog statusLog = new TbSiteStatusLog();
		statusLog.setSTART_TIME(new Date());
		SiteStatus startStatus = SiteStatus.valueOf(siteWork.getSTART_SITE_STATUS());
		SiteStatus midStatus = startStatus;
		//起始狀態 => 結束狀態 : 處理中狀態
		//S06 => S08 : S07  
		if(SiteStatus.S06.name().equals(siteWork.getSTART_SITE_STATUS()) && 
				SiteStatus.S08.name().equals(siteWork.getEND_SITE_STATUS())) {
			midStatus = SiteStatus.S07;	
		} 
		//S08 => S06 : S09
		else if(SiteStatus.S08.name().equals(siteWork.getSTART_SITE_STATUS()) && 
				SiteStatus.S06.name().equals(siteWork.getEND_SITE_STATUS())) {
			midStatus = SiteStatus.S09;	
		} 
		//S06 => S11 : S10
		//S08 => S11 : S10
		else if(SiteStatus.S11.name().equals(siteWork.getEND_SITE_STATUS())) {
			midStatus = SiteStatus.S10;	
		} 
		siteMainDao.updateSiteStatusBySiteId(siteId , midStatus);	
		siteStatusLogService.updateSiteStatusLog(siteId, siteWork.getBTS_SITE_ID(), startStatus, midStatus, new Date());
		
		// 更新TbSiteWorkOrder
		//if (orderIdArray != null) {
		TbSiteWorkOrderExample example = new TbSiteWorkOrderExample();
		example.createCriteria().andWORK_IDEqualTo(workId); 
		List<TbSiteWorkOrder> orderList = workOrderDao.findByConditions(example);
		for (TbSiteWorkOrder orderId : orderList) {
			int workOrderItem = workOrderDao.updateStatusByOrder(orderId.getORDER_ID(), OrderStatus.OR02);
			if (workOrderItem == 0) {
				log.error("workOrderItem update count =" + workOrderItem + ", ORDER_ID = "
						+ orderId);
				throw new NomsException("申請失敗");
			}
		}
		//}
		
		// 申請建站作業申請flow
		if (workItem == 1) {
			workflowActionService.apply("SiteBuildSingleWorkApply", siteWork.getWORK_ID(),
					WorkType.detectLabel(siteWork.getWORK_TYPE()) + " 作業申請",
					emailTemplateService.getMailVarMapForWorkflow(
							EmailType.WORKFLOW_TODO_WORK_APPLY, siteWork.getWORK_ID()));

			siteEmailService.sendWorkApplyMail(workId);
		} else {
			log.error("workItem update count =" + workItem + " ,workId = " + workId);
			throw new NomsException("申請失敗");
		}
		return siteWork;
	}
	
	/**
	 * 取消申請
	 * 
	 * @param workId
	 * @return
	 * @throws NomsException
	 */
	@Transactional
	public TbSiteWork cancelApplyInfo(String workId, String mdUser)
			throws NomsException {
		TbSiteWork siteWork = workDao.findByPk(workId);
		siteWork.setMD_USER(getLoginUser().getUsername());
		siteWork.setMD_TIME(new Date());
		siteWork.setWORK_STATUS(WorkStatus.W08.toString());
		int updateWorkItem = workDao.update(siteWork);
		if (updateWorkItem == 0 ) {
			log.error("updateWorkItem update count = " + updateWorkItem
					+  " , workId = " + workId);
			throw new NomsException("取消申請失敗");
		}
		// 更新TbSiteWorkOrder
		TbSiteWorkOrderExample example = new TbSiteWorkOrderExample();
		example.createCriteria().andWORK_IDEqualTo(workId);
		TbSiteWorkOrder siteWorkOrder = new TbSiteWorkOrder();
		siteWorkOrder.setIS_ACTIVE("N");
		siteWorkOrder.setMD_USER(mdUser);
		siteWorkOrder.setMD_TIME(new Date());
		int updateWorkOrderItem = workOrderDao.updateByExample(siteWorkOrder, example);
		if (updateWorkItem == 0 || updateWorkOrderItem == 0) {
			log.error("updateWorkOrderItem = " + updateWorkOrderItem + " , workId = "
					+ workId);
			throw new NomsException("取消申請失敗");
		}
		
		return siteWork;
	}
	
	/**
	 * 單張工單作業申請-簽核
	 * 
	 * @param workId
	 *            作業編號
	 * @return
	 * @throws NomsException
	 */
	@Transactional
	public void finishSiteBuildApply(String workId, String action) throws NomsException {
		Date crTime = new Date();
		String crUser = getLoginUser().getUsername();

		TbSiteWork siteWork = workDao.findByPk(workId);
		if (siteWork == null) {
			String msg = "建站作業申請資料不存在, workId = " + workId;
			log.error(msg);
			throw new NomsException(msg);
		}

		log.info("workId : " + workId);
		log.info("action : " + action);

		// 查詢建站作業申請相關工單
		TbSiteWorkOrderExample example = new TbSiteWorkOrderExample();
		example.createCriteria().andWORK_IDEqualTo(siteWork.getWORK_ID()).andIS_ACTIVEEqualTo("Y");
		List<TbSiteWorkOrder> siteWorkOrderList = workOrderDao.findByConditions(example);

		int workUpdateItem = 0;

		if (StringUtils.equals(AppConstants.WORKFLOW_REST_APPROVAL, action)) {
			// 簽核允許
			log.info("APPROVAL Start");
			// 1.更新建站作業資料: 狀態(W03→W05)
			TbSiteWork record = workDao.findByPk(siteWork.getWORK_ID());// new TbSiteWork();
			record.setWORK_ID(siteWork.getWORK_ID());
			record.setWORK_STATUS(WorkStatus.W05.name());
//			record.setEND_TIME(new Date());
			workUpdateItem = workDao.updateByPrimaryKey(record);
			List<String> orderList = new ArrayList<String>();

			// 2.更新作業工單處理狀態(OR02→OR03)
			// for (int i = 0; i <= siteWorkOrderList.size(); i++) {
			boolean isSiteHuntOrder = false;
			for (TbSiteWorkOrder siteWorkOrder : siteWorkOrderList) {	// 單張工單只會有一筆
				log.info("ODR_STATUS:" + siteWorkOrder.getODR_STATUS());
				log.info("ORDER_ID:" + siteWorkOrder.getORDER_ID());
				if (siteWorkOrder.getODR_STATUS().equals("OR02")) {
					workOrderDao.updateStatusByOrder(siteWorkOrder.getORDER_ID(), OrderStatus.OR03);
					orderList.add(siteWorkOrder.getORDER_ID());
					if (StringUtils.equals("CL", siteWorkOrder.getORDER_TYPE())) {
						isSiteHuntOrder = true;
						break;
					}
				}
			}

			if (!isSiteHuntOrder) {		
				// 3.寫入站點暫存檔
				TbSiteLocation siteLoc = locationDao.findByPk(siteWork.getLOCATION_ID());
				TbSiteWorkLocTemp siteWorkLocTemp = this.getSiteWorkLocTemp(siteLoc, siteWork);
				siteWorkLocTemp.setCR_USER(crUser);
				siteWorkLocTemp.setCR_TIME(crTime);
				siteWorkLocTemp.setMD_USER(crUser);
				siteWorkLocTemp.setMD_TIME(crTime);
				locTempDao.insert(siteWorkLocTemp);
			}	
			
			// 4.寫入站台暫存檔
			TbSiteMain siteMain = siteMainDao.findByPk(siteWork.getSITE_ID());
			TbSiteWorkSiteTemp siteWorkSiteTemp = this.getSiteWorkSiteTemp(siteMain, siteWork);
			siteWorkSiteTemp.setCR_USER(crUser);
			siteWorkSiteTemp.setCR_TIME(crTime);
			siteWorkSiteTemp.setMD_USER(crUser);
			siteWorkSiteTemp.setMD_TIME(crTime);
			siteTempDao.insert(siteWorkSiteTemp);
			
			if (!isSiteHuntOrder) {	
				// 5.寫入天線暫存檔
				TbSiteAntCfgExample example1 = new TbSiteAntCfgExample();
				example1.createCriteria().andSITE_IDEqualTo(siteWork.getSITE_ID());
				List<TbSiteAntCfg> siteAntCfgList = antCfgDao.findByCondition(example1);
				log.info("寫入天線暫存檔 : " + siteWork.getWORK_ID() + " / " + siteWork.getSITE_ID() + " / ");
				for (TbSiteAntCfg siteAntCfg : siteAntCfgList) {
					TbSiteWorkAntCfgTemp antCfgTemp = this.getSiteSrAntCfgTemp4(siteAntCfg, workId);
					antCfgTemp.setCR_USER(crUser);
					antCfgTemp.setCR_TIME(crTime);
					antCfgTemp.setMD_USER(crUser);
					antCfgTemp.setMD_TIME(crTime);
					antCfgTempDao.insert(antCfgTemp);
				}
			}

			// 系統自動派工
			if (orderList.size() > 0) {
				TbSiteWorkOrderExample criteria = new TbSiteWorkOrderExample();
				criteria.createCriteria().andWORK_IDEqualTo(workId).andORDER_IDIn(orderList).andIS_ACTIVEEqualTo("Y"); // 先指派順序優先的工單
				criteria.setOrderByClause("ODR_SEQ");
				List<TbSiteWorkOrder> orders = workOrderDao.findByConditions(criteria);
				int odr = 0;
				for (TbSiteWorkOrder order : orders) {
					if (odr == 0 || odr == order.getODR_SEQ()) {
						boolean autoAssign = respUserService.autoAssignOrder(siteWork.getAREA(),
								order); // 是否自動派工成功
						if (autoAssign) {
							siteEmailService.sendOrderAutoAssignMail(order.getORDER_ID());
							siteEmailService.sendOrderPickupMail(order.getORDER_ID());
						} else {
							siteEmailService.sendOrderAssignMail(order.getORDER_ID());
						}
					}
				}
				if (workUpdateItem == 0) {
					log.error("簽核失敗  workId = " + siteWork.getWORK_ID());
					throw new NomsException("簽核失敗");
				}
			}

		} else if (AppConstants.WORKFLOW_REST_REJECT.equals(action)) {
			// 簽核駁回
			// 1.更新作業工單處理狀態(OR02→OR01)
			log.info("REJECT Start");
			for (TbSiteWorkOrder siteWorkOrder : siteWorkOrderList) {
				log.info("ODR_STATUS:" + siteWorkOrder.getODR_STATUS());
				log.info("ORDER_ID:" + siteWorkOrder.getORDER_ID());
				if (StringUtils.equals(OrderStatus.OR02.name(), siteWorkOrder.getODR_STATUS())) {
					workOrderDao.updateStatusByOrder(siteWorkOrder.getORDER_ID(), OrderStatus.OR01);
				}
			}

			// 2.更新建站作業資料: 狀態(W03→W04)
			TbSiteWork record = new TbSiteWork();
			record.setWORK_ID(siteWork.getWORK_ID());
			record.setWORK_STATUS(WorkStatus.W04.name());
			workDao.updateByPrimaryKey(record);
			
			// 3. update sitemain
			TbSiteMain recordMain = siteMainDao.findByPk(siteWork.getSITE_ID());
			recordMain.setSITE_STATUS(siteWork.getSTART_SITE_STATUS());
			int updateSiteMainItem = siteMainDao.updateByPrimaryKeySelective(recordMain);
			//int updateSiteMainItem = siteMainDao.updateSiteStatusBySiteId(siteWork.getSITE_ID(), SiteStatus.S06);
			if(updateSiteMainItem == 0){
				log.error("update fail ,updateSiteMainItem = "+updateSiteMainItem+" ,siteId = "+siteWork.getSITE_ID());
				throw new NomsException("簽核失敗");
			}
		} else {
			throw new NomsException("Unvaliable action value:" + action
					+ ", Please check flow result.");
		}
	}
	
	private TbSiteWorkLocTemp getSiteWorkLocTemp(TbSiteLocation siteLoc, TbSiteWork siteWork) {
		TbSiteWorkLocTemp siteWorkLocTemp = new TbSiteWorkLocTemp();
		siteWorkLocTemp.setWORK_ID(siteWork.getWORK_ID());
		siteWorkLocTemp.setLOCATION_ID(siteWork.getLOCATION_ID());
		siteWorkLocTemp.setLOC_NAME(siteLoc.getLOC_NAME());
		siteWorkLocTemp.setLOC_TYPE(siteLoc.getLOC_TYPE());
		siteWorkLocTemp.setLON(siteWork.getSR_LON());
		siteWorkLocTemp.setLAT(siteWork.getSR_LAT());
		siteWorkLocTemp.setMAINT_AREA(siteLoc.getMAINT_AREA());
		siteWorkLocTemp.setMAINT_DEPT(siteLoc.getMAINT_DEPT());
		siteWorkLocTemp.setMAINT_TEAM(siteLoc.getMAINT_TEAM());
		siteWorkLocTemp.setMAINT_PSN(siteLoc.getMAINT_USER());
		siteWorkLocTemp.setLOC_DESC(siteLoc.getLOC_DESC());
		siteWorkLocTemp.setBASE_TYPE(siteLoc.getBASE_TYPE());
		siteWorkLocTemp.setINDOOR_OPTION(siteLoc.getINDOOR_OPTION());
		siteWorkLocTemp.setNS_LEVEL(siteLoc.getNS_LEVEL());
		siteWorkLocTemp.setBUILDING_HEIGHT(siteLoc.getBUILDING_HEIGHT());
		siteWorkLocTemp.setSHARE_TYPE(siteLoc.getSHARE_TYPE());
		// siteWorkLocTemp.setSHARE_COM(siteLoc.getSHARE_COM());
		siteWorkLocTemp.setIS_FREEENTER(siteLoc.getIS_FREEENTER());
		siteWorkLocTemp.setIS_KEY(siteLoc.getIS_KEY());
		siteWorkLocTemp.setIS_INDOOR(siteLoc.getIS_INDOOR());
		siteWorkLocTemp.setNEED_MISC_LIC(siteLoc.getNEED_MISC_LIC());
		siteWorkLocTemp.setHAS_MISC_LIC(siteLoc.getHAS_MISC_LIC());
		siteWorkLocTemp.setADDR(siteLoc.getADDR());
		siteWorkLocTemp.setADD_OTHER(siteLoc.getADD_OTHER());
		siteWorkLocTemp.setZIP(siteLoc.getZIP());
		siteWorkLocTemp.setCITY(siteLoc.getCITY());
		siteWorkLocTemp.setAREA(siteLoc.getAREA());
		siteWorkLocTemp.setVILLAGE(siteLoc.getVILLAGE());
		siteWorkLocTemp.setADJACENT(siteLoc.getADJACENT());
		siteWorkLocTemp.setROAD(siteLoc.getROAD());
		siteWorkLocTemp.setLANE(siteLoc.getLANE());
		siteWorkLocTemp.setALLEY(siteLoc.getALLEY());
		siteWorkLocTemp.setSUB_ALLEY(siteLoc.getSUB_ALLEY());
		siteWorkLocTemp.setDOOR_NO(siteLoc.getDOOR_NO());
		siteWorkLocTemp.setDOOR_NO_EX(siteLoc.getDOOR_NO_EX());
		siteWorkLocTemp.setFLOOR(siteLoc.getFLOOR());
		siteWorkLocTemp.setFLOOR_EX(siteLoc.getFLOOR_EX());
		siteWorkLocTemp.setROOM(siteLoc.getROOM());
		siteWorkLocTemp.setADD_OTHER(siteLoc.getADD_OTHER());
		siteWorkLocTemp.setSPACE_ROOM(siteLoc.getSPACE_ROOM());
		siteWorkLocTemp.setSPACE_ROOF(siteLoc.getSPACE_ROOF());
		siteWorkLocTemp.setSPACE_TOP(siteLoc.getSPACE_TOP());
		siteWorkLocTemp.setSPACE_PLATFORM(siteLoc.getSPACE_PLATFORM());
		siteWorkLocTemp.setSPACE_LAND(siteLoc.getSPACE_LAND());
		siteWorkLocTemp.setSPACE_INDOOR(siteLoc.getSPACE_INDOOR());
		siteWorkLocTemp.setSPACE_OUTDOOR(siteLoc.getSPACE_OUTDOOR());
		siteWorkLocTemp.setFOOTAGE(siteLoc.getFOOTAGE());
		siteWorkLocTemp.setEMP_RELATION(siteLoc.getEMP_RELATION());

		return siteWorkLocTemp;
	}

	private TbSiteWorkSiteTemp getSiteWorkSiteTemp(TbSiteMain siteMain, TbSiteWork siteWork) {
		TbSiteWorkSiteTemp siteWorkSiteTemp = new TbSiteWorkSiteTemp();
		siteWorkSiteTemp.setWORK_ID(siteWork.getWORK_ID());
		siteWorkSiteTemp.setSITE_ID(siteMain.getSITE_ID());
		siteWorkSiteTemp.setFEQ_ID(siteMain.getFEQ_ID());
		if (siteMain.getSR_ID() == null) {
			siteWorkSiteTemp.setSR_ID("");
		} else {
			siteWorkSiteTemp.setSR_ID(siteMain.getSR_ID());
		}
		siteWorkSiteTemp.setBTS_SITE_ID(siteMain.getBTS_SITE_ID());
		siteWorkSiteTemp.setSITE_NAME(siteMain.getSITE_NAME());
		siteWorkSiteTemp.setLOCATION_ID(StringUtils.trimToNull(siteMain.getLOCATION_ID()));
		siteWorkSiteTemp.setPURPOSE(siteMain.getPURPOSE());
		siteWorkSiteTemp.setEQP_TYPE_ID(StringUtils.trimToNull(siteMain.getEQP_TYPE_ID()));
		siteWorkSiteTemp.setEQP_MODEL_ID(StringUtils.trimToNull(siteMain.getEQP_MODEL_ID()));
		siteWorkSiteTemp.setCOVERAGE_TYPE(StringUtils.trimToNull(siteMain.getCOVERAGE_TYPE()));
		siteWorkSiteTemp.setBTS_CONFIG(StringUtils.trimToNull(siteMain.getBTS_CONFIG()));
		siteWorkSiteTemp.setDONOR_SITE(StringUtils.trimToNull(siteMain.getDONOR_SITE()));
		siteWorkSiteTemp.setFEEDERLESS(StringUtils.trimToNull(siteMain.getFEEDERLESS()));
		siteWorkSiteTemp.setMASTER_SITE(StringUtils.trimToNull(siteMain.getMASTER_SITE()));
		siteWorkSiteTemp.setCLUSTER(StringUtils.trimToNull(siteMain.getMASTER_SITE()));
		siteWorkSiteTemp.setRNC_BTS(StringUtils.trimToNull(siteMain.getRNC_BTS()));
		siteWorkSiteTemp.setNIOS_RPT_STATUS(StringUtils.trimToNull(siteMain.getNIOS_RPT_STATUS()));
		siteWorkSiteTemp.setCELL_STATUS(StringUtils.trimToNull(siteMain.getCELL_STATUS()));
		siteWorkSiteTemp.setOMCU_OBJ(StringUtils.trimToNull(siteMain.getOMCU_OBJ()));
		siteWorkSiteTemp.setSPEED_UPLOAD(StringUtils.trimToNull(siteMain.getSPEED_UPLOAD()));
		siteWorkSiteTemp.setSPEED_DOWNLOAD(StringUtils.trimToNull(siteMain.getSPEED_DOWNLOAD()));
		siteWorkSiteTemp.setPOWER(StringUtils.trimToNull(siteMain.getPOWER()));
		siteWorkSiteTemp.setCST_ID_CARD_NUM(StringUtils.trimToNull(siteMain.getCST_ID_CARD_NUM()));
		siteWorkSiteTemp.setCST_MOBILE(StringUtils.trimToNull(siteMain.getCST_MOBILE()));
		siteWorkSiteTemp.setCST_ID(StringUtils.trimToNull(siteMain.getCST_ID()));
		siteWorkSiteTemp.setCOVERAGE_INDOOR(StringUtils.trimToNull(siteMain.getCOVERAGE_INDOOR()));
		siteWorkSiteTemp.setHIDDEN(StringUtils.trimToNull(siteMain.getHIDDEN()));
		siteWorkSiteTemp.setNOIS_ON_AIR(StringUtils.trimToNull(siteMain.getNOIS_ON_AIR()));
		siteWorkSiteTemp.setL2_SWITCH(StringUtils.trimToNull(siteMain.getL2_SWITCH()));
		return siteWorkSiteTemp;
	}

	private TbSiteWorkAntCfgTemp getSiteSrAntCfgTemp4(TbSiteAntCfg siteAntCfg, String workId) {
		TbSiteWorkAntCfgTemp antCfgTemp = new TbSiteWorkAntCfgTemp();
		log.info("寫入天線暫存檔 antCfgTemp : " + antCfgTemp.getWORK_ID() + " / " + siteAntCfg.getSITE_ID() + " / " + siteAntCfg.getANT_NO());
		antCfgTemp.setWORK_ID(workId);
		antCfgTemp.setSITE_ID(siteAntCfg.getSITE_ID());
		antCfgTemp.setANT_NO(siteAntCfg.getANT_NO());
		antCfgTemp.setCELL_ID(siteAntCfg.getCELL_ID());
		antCfgTemp.setSECTOR_ID(siteAntCfg.getSECTOR_ID());
		antCfgTemp.setP_CODE(siteAntCfg.getP_CODE());
		antCfgTemp.setSEG_SOURCE(siteAntCfg.getSEG_SOURCE());
		antCfgTemp.setANT_MODEL(siteAntCfg.getANT_MODEL());
		antCfgTemp.setANT_HIGH(siteAntCfg.getANT_HIGH());
		antCfgTemp.setANT_ORIENT(siteAntCfg.getANT_ORIENT());
		antCfgTemp.setE_TILT(siteAntCfg.getE_TILT());
		antCfgTemp.setM_TILT(siteAntCfg.getM_TILT());
		antCfgTemp.setSETUP_STYLE(siteAntCfg.getSETUP_STYLE());
		antCfgTemp.setCELL_RADIUS(siteAntCfg.getCELL_RADIUS());
		antCfgTemp.setANT_SIZE(siteAntCfg.getANT_SIZE());
		antCfgTemp.setANT_MFGR(siteAntCfg.getANT_MFGR());
		antCfgTemp.setDOWNTILT_R(siteAntCfg.getDOWNTILT_R());
		antCfgTemp.setFLOOR_LEVEL(siteAntCfg.getFLOOR_LEVEL());
		antCfgTemp.setTOWER_HEIGHT(siteAntCfg.getTOWER_HEIGHT());
		antCfgTemp.setPENTHOUSE_HEIGHT(siteAntCfg.getPENTHOUSE_HEIGHT());
		antCfgTemp.setADDR(siteAntCfg.getADDR());
		antCfgTemp.setLON(siteAntCfg.getLON());
		antCfgTemp.setLAT(siteAntCfg.getLAT());
		antCfgTemp.setF_CABLE_TYPE(siteAntCfg.getF_CABLE_TYPE());
		antCfgTemp.setF_CABLE_LEN(siteAntCfg.getF_CABLE_LEN());
		antCfgTemp.setJ_CABLE_LEN(siteAntCfg.getJ_CABLE_LEN());
		antCfgTemp.setCOVER_REG(siteAntCfg.getCOVER_REG());
		return antCfgTemp;
	}
	
	/**
	 * 驗證siteBuildApply 資料是否正確
	 * 
	 * @param siteBuildApply
	 * @return
	 */
	public String doValidate(SiteBuildApplyDTO siteBuildApply) {
		String errorMessage = "";
		List<String> workStatusList = new ArrayList<String>();
		workStatusList.add(WorkStatus.W06.name());
		workStatusList.add(WorkStatus.W07.name());
		workStatusList.add(WorkStatus.W08.name());
		TbSiteWorkExample example = new TbSiteWorkExample();
		if (StringUtils.isEmpty(siteBuildApply.getWorkId())) {
			example.createCriteria().andSITE_IDEqualTo(siteBuildApply.getSiteId())
			.andWORK_STATUSNotIn(workStatusList)
			.andWORK_TYPEEqualTo(siteBuildApply.getWorkType());
		}else{
			example.createCriteria().andSITE_IDEqualTo(siteBuildApply.getSiteId())
					.andWORK_STATUSNotIn(workStatusList)
					.andWORK_TYPEEqualTo(siteBuildApply.getWorkType())
					.andWORK_IDNotEqualTo(siteBuildApply.getWorkId());
		}
		List<TbSiteWork> siteWorkList = workDao.findByConditions(example);
		if (siteWorkList.size() > 0) {
			for (TbSiteWork siteWork : siteWorkList) {
				log.debug("siteWorkList : " + siteWork.toString());
			}
			errorMessage = "基站編號已被作業編號為"+siteWorkList.get(0).getWORK_ID()+"使用";
		}
		return errorMessage;
	}
}