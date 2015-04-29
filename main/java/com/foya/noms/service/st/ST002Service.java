package com.foya.noms.service.st;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.dao.mybatis.model.TbComEqpModel;
import com.foya.dao.mybatis.model.TbComEqpModelExample;
import com.foya.dao.mybatis.model.TbComTown;
import com.foya.dao.mybatis.model.TbComTownExample;
import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbSiteIdPool;
import com.foya.dao.mybatis.model.TbSiteIdPoolExample;
import com.foya.dao.mybatis.model.TbSiteLocation;
import com.foya.dao.mybatis.model.TbSiteMain;
import com.foya.dao.mybatis.model.TbSiteMainExample;
import com.foya.dao.mybatis.model.TbSiteSearchRing;
import com.foya.dao.mybatis.model.TbSiteWork;
import com.foya.dao.mybatis.model.TbSiteWorkExample;
import com.foya.dao.mybatis.model.TbSiteWorkOrder;
import com.foya.dao.mybatis.model.TbSiteWorkOrderExample;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.exception.CreateFailException;
import com.foya.exception.NomsException;
import com.foya.exception.UpdateFailException;
import com.foya.noms.dao.auth.PersonnelDao;
import com.foya.noms.dao.common.CityDao;
import com.foya.noms.dao.common.TownDao;
import com.foya.noms.dao.common.TownDomainTeamDao;
import com.foya.noms.dao.org.DeptDao;
import com.foya.noms.dao.st.LocationDao;
import com.foya.noms.dao.st.ST002Dao;
import com.foya.noms.dao.st.ST002SP1Dao;
import com.foya.noms.dao.st.SearchRingDao;
import com.foya.noms.dao.st.SiteIdPoolDao;
import com.foya.noms.dao.st.SiteMainDao;
import com.foya.noms.dao.st.WorkDao;
import com.foya.noms.dao.st.WorkOrderDao;
import com.foya.noms.dao.system.OrderTypeDao;
import com.foya.noms.dao.system.WorkOrderTypeDao;
import com.foya.noms.dto.common.TownDomainTeamDTO;
import com.foya.noms.dto.st.SiteHuntApplyDTO;
import com.foya.noms.dto.st.SiteIdPoolDTO;
import com.foya.noms.dto.st.SiteWorkDTO;
import com.foya.noms.dto.st.SiteWorkOrderDTO;
import com.foya.noms.dto.system.OrderTypeDTO;
import com.foya.noms.enums.EmailType;
import com.foya.noms.enums.IncludeRange;
import com.foya.noms.enums.OrderStatus;
import com.foya.noms.enums.PurchaseOrderType;
import com.foya.noms.enums.SiteStatus;
import com.foya.noms.enums.WorkStatus;
import com.foya.noms.enums.WorkType;
import com.foya.noms.service.BaseService;
import com.foya.noms.service.common.EmailTemplateService;
import com.foya.noms.service.system.LookupService;
import com.foya.noms.service.system.OrderTypeService;
import com.foya.noms.service.system.RespUserService;
import com.foya.noms.service.system.UniqueSeqService;
import com.foya.noms.service.workflow.WorkflowActionService;

@Service
public class ST002Service extends BaseService {
	@Autowired
	private ST002Dao st002Dao;
	@Autowired
	private TownDao townDao;
	@Autowired
	private ST002SP1Dao sT002SP1Dao;
	@Autowired
	private WorkDao workDao;
	@Autowired
	private SiteMainDao siteMainDao;
	@Autowired
	private LocationDao locationDao;
	@Autowired
	private UniqueSeqService uniqueSeqService;
	@Autowired
	private OrderTypeDao orderTypeDao;
	@Autowired
	private DeptDao deptDao;
	@Autowired
	private OrderTypeService orderTypeService;
	@Autowired
	private WorkOrderDao workOrderDao;
	@Autowired
	private SearchRingDao searchRingDao;
	@Autowired
	private SiteIdPoolDao siteIdPoolDao;
	@Autowired
	private WorkflowActionService workflowActionService;
	@Autowired
	private WorkOrderTypeDao workOrderTypeDao;
	@Autowired
	private TownDomainTeamDao townDomainTeamDao;
	@Autowired
	private SiteStatusLogService siteStatusLogService;
	@Autowired
	private CityDao cityDao;
	@Autowired
	private RespUserService respUserService;
	@Autowired
	private SiteEmailService siteEmailService;
	@Autowired
	private EmailTemplateService emailTemplateService;
	@Autowired
	private LookupService lookupService;
	@Autowired
	private PersonnelDao personnelDao;

	/**
	 * 查詢全部siteWork
	 * 
	 * @param map
	 * @return
	 */
	public List<SiteWorkDTO> getWorkList(Map<String, Object> map) {
		return st002Dao.findWorkList(map);
	}

	/**
	 * 查詢設備機型
	 * 
	 * @param example
	 * @return
	 */
	public List<TbComEqpModel> getEqpModelList(TbComEqpModelExample example) {
		return st002Dao.findEqpModelList(example);
	}

	/**
	 * 用DeptId查詢siteWork
	 * 
	 * @param deptIdList
	 * @return
	 */
	public List<SiteWorkDTO> getWorkListByDeptId(List<String> deptIdList) {
		return st002Dao.findWorkListByDeptId(deptIdList);
	}

	/**
	 * 用TbComTownExample查詢區域
	 * 
	 * @param example
	 * @return
	 */
	public List<TbComTown> getTownByCondition(TbComTownExample example) {
		return townDao.findTownByCondition(example);
	}

	/**
	 * 用workId查詢siteWork
	 * 
	 * @return
	 */
	public SiteWorkDTO getSiteWorkByWorkId(String workId) {
		return st002Dao.findSiteWorkByWorkId(workId);
	}

	/**
	 * 用workId查詢SiteWorkOrder
	 * 
	 * @param workId
	 * @return
	 */
	public List<SiteWorkOrderDTO> getSiteWorkOrderByWorkId(String workId) {
		return st002Dao.findSiteWorkOrderByWorkId(workId);
	}

	/**
	 * 查詢siteIdPool
	 * 
	 * @param map
	 * @return
	 */
	public List<SiteIdPoolDTO> getUnuseBTSSiteId(Map<String, String> map) {
		return siteIdPoolDao.findUnuseBTSSiteId(map);
	}

	/**
	 * 驗證SiteHunt 資料是否正確
	 * 
	 * @param siteHuntApply
	 * @return
	 */
	public String doValidate(SiteHuntApplyDTO siteHuntApply) {
		String errorMessages = "";

		String feqType = siteHuntApply.getFeqId().split(",")[1];
		String feqId = siteHuntApply.getFeqId().split(",")[0];
		String eqpTypeId = siteHuntApply.getEqpTypeId();
		String btsSiteId = StringUtils.trimToNull(siteHuntApply.getBtsSiteId());
		String domain = townDomainTeamDao.getTownDomainTeamByCityIdTownId(siteHuntApply.getCity(),
				siteHuntApply.getArea()).getDOMAIN();
		domain = domain.substring(0, 1);
		String coverageType = siteHuntApply.getCoverageType();
		String srId = siteHuntApply.getSrId();
		// 驗證btsSiteId
		if (btsSiteId != null || !WorkType.NSR.name().equals(siteHuntApply.getWorkType())) {
			errorMessages += doValidateBTSSiteId(eqpTypeId, feqType, domain, btsSiteId,
					coverageType);
		}
		if (WorkType.NSR.name().equals(siteHuntApply.getWorkType()) && btsSiteId != null) {
			TbSiteMainExample example = new TbSiteMainExample();
			example.createCriteria().andBTS_SITE_IDEqualTo(btsSiteId);
			List<TbSiteMain> siteMain = sT002SP1Dao.findWorkSiteListByExample(example);
			if (siteMain.size() != 0) {
				errorMessages += "基站編號重複\n";
			}
		} else if (WorkType.SH.name().equals(siteHuntApply.getWorkType())) {
			TbSiteMainExample example = new TbSiteMainExample();
			example.createCriteria().andBTS_SITE_IDEqualTo(btsSiteId);
			List<TbSiteMain> siteMain = sT002SP1Dao.findWorkSiteListByExample(example);
			if (siteMain.size() != 0) {
				if (!SiteStatus.S02.name().equals(siteMain.get(0).getSITE_STATUS())) {
					errorMessages += "基站編號重複，必須是" + SiteStatus.S02.getLocalName() + "狀態才可使用\n";
				}
			}
		}
		// 驗證srIdFeqId與基站頻段是否相同 Search Ring的基站頻段與尋點的基站頻段不符
		TbSiteSearchRing searchRing = searchRingDao.findSearchRingByPrimaryKey(srId);
		if (StringUtils.isNotEmpty(siteHuntApply.getSrId())) {
			if (!feqId.equals(searchRing.getFEQ_ID()) && !"all".equals(searchRing.getFEQ_ID())) {
				errorMessages += "Search Ring的基站頻段與尋點的基站頻段不符";
			}
		}
		return errorMessages;
	}

	/**
	 * 驗證btsSiteId是否符合規範
	 * 
	 * @param eqpTypeId
	 * @param feqType
	 * @param domain
	 * @param btsSiteId
	 * @param coverageType
	 * @return
	 */
	public String doValidateBTSSiteId(String eqpTypeId, String feqType, String domain,
			String btsSiteId, String coverageType) {
		log.debug(" IncludeRange.B.name() : " + IncludeRange.B.name());
		log.debug("eqpTypeId : " + eqpTypeId);
		log.debug("feqType : " + feqType);
		log.debug("domain : " + domain);
		log.debug("btsSiteId : " + btsSiteId);
		log.debug("coverageType : " + coverageType);
		String errorMessages = "";
		if ("1".equals(eqpTypeId) && "U".equals(feqType)) {
			String msg = this.dovalidateBtsSiteId(domain, btsSiteId, "^1[0-9]{4,4}$",
					"^1[0-9]{4,4}$", "^2[0-9]{4,4}$", "^3[0-9]{4,4}$");
			String msg2 = this.dovalidateBtsSiteId(domain, btsSiteId, "^4[0-9]{4,4}$",
					"^4[0-9]{4,4}$", "^5[0-9]{4,4}$", "^0[0-9]{4,4}$");
			if (!msg.isEmpty() && !msg2.isEmpty()) {
				return errorMessages += msg;
			}
		} else if ("2".equals(eqpTypeId)) {
			TbSiteIdPoolExample siteIdPoolExample = new TbSiteIdPoolExample();
			siteIdPoolExample.createCriteria().andBTS_SITE_IDEqualTo(btsSiteId)
					.andDOMAINEqualTo(domain);
			List<TbSiteIdPool> siteIdPoolList = siteIdPoolDao
					.findSiteIdPoolByConditions(siteIdPoolExample);
			if (siteIdPoolList.size() == 0) {
				errorMessages += "基站編號格式錯誤或SR ID區域不符\n";
			} else {
				TbSiteMainExample example = new TbSiteMainExample();
				example.createCriteria().andBTS_SITE_IDEqualTo(btsSiteId);
				List<TbSiteMain> siteList = sT002SP1Dao.findWorkSiteListByExample(example);
				if (siteList.size() < 0) {
					errorMessages += "基站編號重複\n";
				}
			}
		} else if ("3".equals(eqpTypeId)) {
			if (IncludeRange.O.name().equals(coverageType)) {
				if ("U".equals(feqType)) {
					String msg = this.dovalidateBtsSiteId(domain, btsSiteId,
							"[R,N]{2,2}[0001-5000]{4,4}$", "[R,M]{2,2}[0001-5000]{4,4}$",
							"[R,C]{2,2}[0001-5000]{4,4}$", "[R,S]{2,2}[0001-5000]{4,4}$");
					if (!msg.isEmpty()) {
						return errorMessages += msg;
					}
				} else {
					String msg = this.dovalidateBtsSiteId(domain, btsSiteId,
							"[L,R,N]{3,3}[0][0001-5000]{4,4}$", "[L,R,M]{3,3}[0][0001-5000]{4,4}$",
							"[L,R,C]{3,3}[0][0001-5000]{4,4}$", "[L,R,S]{3,3}[0][0001-5000]{4,4}$");
					if (!msg.isEmpty()) {
						return errorMessages += msg;
					}
				}
			} else {
				if ("U".equals(feqType)) {
					String msg = this.dovalidateBtsSiteId(domain, btsSiteId,
							"[R,N]{2,2}[5001-9999]{4,4}$", "[R,M]{2,2}[5001-9999]{4,4}$",
							"[R,C]{2,2}[5001-9999]{4,4}$", "[R,S]{2,2}[5001-9999]{4,4}$");
					if (!msg.isEmpty()) {
						return errorMessages += msg;
					}
				} else {
					String msg = this.dovalidateBtsSiteId(domain, btsSiteId,
							"[L,R,N]{3,3}[0][5001-9999]{4,4}$", "[L,R,M]{3,3}[0][5001-9999]{4,4}$",
							"[L,R,C]{3,3}[0][5001-9999]{4,4}$", "[L,R,S]{3,3}[0][5001-9999]{4,4}$");
					if (!msg.isEmpty()) {
						return errorMessages += msg;
					}
				}
			}
		} else if ("4".equals(eqpTypeId)) {
			if ("U".equals(feqType)) {
				String msg = this.dovalidateBtsSiteId(domain, btsSiteId, "[B,N]{2,2}[0-9]{4,4}$",
						"[B,M]{2,2}[0-9]{4,4}$", "[B,C]{2,2}[0-9]{4,4}$", "[B,S]{2,2}[0-9]{4,4}$");
				if (!msg.isEmpty()) {
					return errorMessages += msg;
				}
			} else {
				String msg = this.dovalidateBtsSiteId(domain, btsSiteId,
						"[L,B,N]{3,3}[0][0-9]{4,4}$", "[L,B,M]{3,3}[0][0-9]{4,4}$",
						"[L,B,C]{3,3}[0][0-9]{4,4}$", "[L,B,S]{3,3}[0][0-9]{4,4}$");
				if (!msg.isEmpty()) {
					return errorMessages += msg;
				}
			}
		} else if ("5".equals(eqpTypeId)) {
			if (IncludeRange.O.name().equals(coverageType)) {
				if ("U".equals(feqType)) {
					String msg = this.dovalidateBtsSiteId(domain, btsSiteId,
							"[X,N]{2,2}[0001-5000]{4,4}$", "[X,M]{2,2}[0001-5000]{4,4}$",
							"[X,C]{2,2}[0001-5000]{4,4}$", "[X,S]{2,2}[0001-5000]{4,4}$");
					if (!msg.isEmpty()) {
						return errorMessages += msg;
					}
				} else {
					String msg = this.dovalidateBtsSiteId(domain, btsSiteId,
							"[L,X,N]{3,3}[0][0001-5000]{4,4}$", "[L,X,M]{3,3}[0][0001-5000]{4,4}$",
							"[L,X,C][0][0001-5000]{4,4}$", "[L,X,S]{3,3}[0][0001-5000]{4,4}$");
					if (!msg.isEmpty()) {
						return errorMessages += msg;
					}
				}
			} else {
				if ("U".equals(feqType)) {
					String msg = this.dovalidateBtsSiteId(domain, btsSiteId,
							"[X,N]{2,2}[5001-9999]{4,4}$", "[X,M]{2,2}[5001-9999]{4,4}$",
							"[X,C]{2,2}[5001-9999]{4,4}$", "[X,S]{2,2}[5001-9999]{4,4}$");
					if (!msg.isEmpty()) {
						return errorMessages += msg;
					}
				} else {
					String msg = this.dovalidateBtsSiteId(domain, btsSiteId,
							"[L,X,N]{3,3}[0][5001-9999]{4,4}$", "[L,X,M]{3,3}[0][5001-9999]{4,4}$",
							"[L,X,C]{3,3}[0][5001-9999]{4,4}$", "[L,X,S]{3,3}[0][5001-9999]{4,4}$");
					if (!msg.isEmpty()) {
						return errorMessages += msg;
					}
				}
			}
		} else {
			errorMessages += "設備型態錯誤\n";
			return errorMessages;
		}

		return errorMessages;
	}

	/**
	 * 新增siteHuntApply
	 * 
	 * @param siteHuntApply
	 * @return
	 */
	@Transactional
	public TbSiteWork insert(SiteHuntApplyDTO siteHuntApply, String loginUserDept)
			throws NomsException, CreateFailException, Exception {
		String siteId = uniqueSeqService.getNextSiteId();
		Date date = new Date();
		String user = getLoginUser().getUsername();
		String workId = uniqueSeqService.getNextWorkId();
		TbSiteWork siteWork = this.getSiteWorkBySiteHuntApplyDTO(siteHuntApply, workId, siteId);
		siteWork.setCR_USER(user);
		siteWork.setCR_TIME(date);
		siteWork.setMD_USER(user);
		siteWork.setMD_TIME(date);
		siteWork.setAPP_DEPT(loginUserDept);
		int siteWorkItem = workDao.insert(siteWork);
		if (siteWorkItem == 0) {
			log.error("siteWorkItem update count= " + siteWorkItem + " , workId = "
					+ siteWork.getWORK_ID());
			throw new CreateFailException("新增失敗");
		}
		List<OrderTypeDTO> orderTypeList = orderTypeDao.findOrderTypeByWorkTypeId(
				siteHuntApply.getWorkType(), null);
		TownDomainTeamDTO domainTeam = townDomainTeamDao.getTownDomainTeamByCityIdTownId(
				siteHuntApply.getCity(), siteHuntApply.getArea());
		for (OrderTypeDTO orderType : orderTypeList) {
			TbSiteWorkOrder siteWorkOrder = this.getSiteWorkOrderBySiteHuntApplyDTO(siteHuntApply,
					workId, OrderStatus.OR01.name(), uniqueSeqService.getNextOrderId(workId),
					orderType, domainTeam);
			siteWorkOrder.setCR_USER(user);
			siteWorkOrder.setCR_TIME(date);
			siteWorkOrder.setMD_USER(user);
			siteWorkOrder.setMD_TIME(date);
			int workOrderItem = workOrderDao.insert(siteWorkOrder);
			if (workOrderItem == 0) {
				log.error("workOrderItem update count= " + workOrderItem + " , orderId = "
						+ siteWorkOrder.getORDER_ID());
				throw new NomsException("新增失敗");
			}
		}
		return siteWork;
	}

	/**
	 * 修改siteHuntApply
	 * 
	 * @param siteHuntApply
	 * @return
	 */
	@Transactional
	public TbSiteWork update(SiteHuntApplyDTO siteHuntApply, String[] orderIdArray, String mdUser)
			throws UpdateFailException {
		Date mdTime = new Date();
		boolean hasChangeDomain = false;
		TownDomainTeamDTO domainTeam = null;
		String workId = siteHuntApply.getWorkId();
		TbSiteWork siteWorkTarget = workDao.findByPk(workId);
		
		// 判斷是否重選基站而改變了區域，工單負責的區域也要跟著變動 add by Charlie 20150415
		if (!StringUtils.equals(siteHuntApply.getArea(), siteWorkTarget.getAREA())) {
			hasChangeDomain = true;
			domainTeam = townDomainTeamDao.getTownDomainTeamByCityIdTownId(
					siteHuntApply.getCity(), siteHuntApply.getArea());
		}
		
		TbSiteWork siteWork = this.getSiteWorkBySiteHuntApplyDTO(siteHuntApply, workId,
				siteHuntApply.getSiteId());
		siteWork.setMD_USER(mdUser);
		siteWork.setMD_TIME(mdTime);
		String[] ignoreProperties = { "CR_USER", "CR_TIME", "APP_DEPT", "APL_USER", "APL_TIME",
				"END_TIME", "OS_VEN", "END_DATE", "CPL_NO", "PERMIT_NO", "LICENSE_NO", "OS_TYPE" };
		BeanUtils.copyProperties(siteWork, siteWorkTarget, ignoreProperties);
		int siteWorkItem = workDao.update(siteWorkTarget);
		if (siteWorkItem == 0) {
			log.error("siteWorkItem update count= " + siteWorkItem + " , workId = "
					+ siteWork.getWORK_ID());
			throw new UpdateFailException("更新失敗");
		}
		for (String orderId : orderIdArray) {
			TbSiteWorkOrder siteWorkOrder = workOrderDao.findOrderByPk(orderId);
			siteWorkOrder.setORDER_ID(orderId);
			siteWorkOrder.setPRIORITY(siteHuntApply.getPriority());
			siteWorkOrder.setMD_USER(mdUser);
			siteWorkOrder.setMD_TIME(mdTime);
			if (hasChangeDomain) {	// 依DOMAIN重新尋找各張工單負責單位 add by Charlie 20150415
				String deptKey = orderTypeService.getOrdersDeptKey(siteWorkOrder.getORDER_TYPE(), domainTeam.getDOMAIN(), siteWorkTarget.getEQP_TYPE_ID());
				TbOrgDept newNept = orderTypeService.getOrderTypeForWorkArea(deptKey, domainTeam);
				siteWorkOrder.setREP_DEPT(newNept.getDEPT_ID());
			}
			int workOrderItem = workOrderDao.updateSelective(siteWorkOrder);
			if (workOrderItem == 0) {
				log.error("workOrderItem update count= " + workOrderItem + " , orderId = "
						+ siteWorkOrder.getORDER_ID());
				throw new UpdateFailException("更新失敗");
			}
		}
		return siteWorkTarget;
	}

	/**
	 * 申請
	 * 
	 * @param aplUser
	 * @param workId
	 * @param appDept
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public TbSiteWork updateApplyInfo(String aplUser, SiteHuntApplyDTO siteHuntApply,
			String appDept, String siteHuntApplyEvent, String[] orderIdArray) throws NomsException,
			Exception {
		try {
			if ("insert".equals(siteHuntApplyEvent)) {
				TbSiteWork siteWork = this.insert(siteHuntApply, appDept);
				siteHuntApply.setWorkId(siteWork.getWORK_ID());
				log.debug("insert siteWork : " + siteWork.toString());
			} else {
				this.update(siteHuntApply, orderIdArray, aplUser);
			}
		} catch (Exception e) {
			throw new NomsException(e.getMessage());
		}
		String workId = siteHuntApply.getWorkId();
		TbSiteWork siteWork = workDao.findByPk(workId);
		siteWork.setWORK_ID(workId);
		siteWork.setAPL_USER(aplUser);
		siteWork.setAPL_TIME(new Date());
		siteWork.setAPP_DEPT(appDept);
		siteWork.setWORK_STATUS(WorkStatus.W03.name());
		int workItem = workDao.update(siteWork);
		siteWork = workDao.findByPk(workId);
		log.debug("update siteWork : " + siteWork.toString());
		Date crTime = new Date();
		TbSiteMain siteMain = this.getSiteMainBySiteWork(siteWork, crTime);
		int mainItem = 0;
		if (siteMainDao.findByPk(siteMain.getSITE_ID()) == null) {
			siteMain.setCR_USER(aplUser);
			siteMain.setCR_TIME(crTime);
			mainItem = siteMainDao.insert(siteMain);
		} else {
			siteMain.setMD_USER(aplUser);
			siteMain.setMD_TIME(crTime);
			mainItem = siteMainDao.update(siteMain);
		}

		TbSiteWorkOrderExample example = new TbSiteWorkOrderExample();
		example.createCriteria().andWORK_IDEqualTo(workId); // fix bug add by
															// Charlie
															// 2015/01/05
		List<TbSiteWorkOrder> orderList = workOrderDao.findByConditions(example);
		for (TbSiteWorkOrder order : orderList) {
			int workOrderItem = workOrderDao.updateStatusByOrder(order.getORDER_ID(),
					OrderStatus.OR02);
			if (workOrderItem == 0) {
				log.error("workOrderItem update count =" + workOrderItem + ", ORDER_ID = "
						+ order.getORDER_ID());
				throw new NomsException("申請失敗");
			}
		}

		// 寄發作業申請通知
		siteEmailService.sendWorkApplyMail(workId);

		if (workItem == 1 && mainItem == 1) {
			// 呼叫workflow進入簽核程序
			if ("NSR".equals(siteWork.getWORK_TYPE())) {
				workflowActionService.apply("SearchSiteApplyNSR", siteWork.getWORK_ID(),
						"尋點申請New Site Request", emailTemplateService.getMailVarMapForWorkflow(
								EmailType.WORKFLOW_TODO_WORK_APPLY, siteWork.getWORK_ID()));
			} else {
				workflowActionService.apply("SearchSiteApplySH", siteWork.getWORK_ID(),
						"尋點申請Site Hunt", emailTemplateService.getMailVarMapForWorkflow(
								EmailType.WORKFLOW_TODO_WORK_APPLY, siteWork.getWORK_ID()));
			}
		} else {
			log.error("workItem update count =" + workItem + " , mainItem update count = "
					+ mainItem + " , workId = " + workId);
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
	public TbSiteWork cancelApplyInfo(String workId, String mdUser) throws NomsException {
		TbSiteWork siteWork = workDao.findByPk(workId);
		siteWork.setMD_USER(getLoginUser().getUsername());
		siteWork.setMD_TIME(new Date());
		siteWork.setWORK_STATUS(WorkStatus.W08.toString());
		int updateWorkItem = workDao.update(siteWork);
		TbSiteWorkOrderExample example = new TbSiteWorkOrderExample();
		example.createCriteria().andWORK_IDEqualTo(workId);
		TbSiteWorkOrder siteWorkOrder = new TbSiteWorkOrder();
		siteWorkOrder.setIS_ACTIVE("N");
		siteWorkOrder.setMD_USER(mdUser);
		siteWorkOrder.setMD_TIME(new Date());
		int updateWorkOrderItem = workOrderDao.updateByExample(siteWorkOrder, example);
		if (updateWorkItem == 0 || updateWorkOrderItem == 0) {
			log.error("updateWorkItem update count = " + updateWorkItem
					+ " , updateWorkOrderItem = " + updateWorkOrderItem + " , workId = "
					+ siteWork.getWORK_ID());
			throw new NomsException("取消申請失敗");
		}
		// modify by Kevin said 待尋點取消站台一律暫由單張工單發起 20150410
//		TbSiteMain siteMain = siteMainDao.findByPk(siteWork.getSITE_ID());
//		if(siteMain != null){
//			int updateSiteStatusItme = siteMainDao.updateSiteStatusBySiteId(siteMain.getSITE_ID(), SiteStatus.S11);
//			if(updateSiteStatusItme == 0){
//				log.error("取消申請失敗" + "updateSiteStatusItme update count =" + updateSiteStatusItme
//						+" , siteID = " + siteWork.getSITE_ID());
//				throw new NomsException("取消申請失敗");
//			}
//		}
		return siteWork;
	}

	/**
	 * 簽核
	 * 
	 * @param orderId
	 * @param result
	 * @return
	 * @throws NomsException
	 */
	@Transactional
	public void updateApprResult(String workId, boolean result) throws NomsException, Exception {
		// boolean isTrue = false;
		TbSiteWork siteWork = workDao.findByPk(workId);
		if (siteWork == null) {
			String msg = "Not found TbSiteWork, workId = " + workId;
			log.error(msg);
			throw new NomsException(msg);
		}
		Date endTime = new Date();
		TbSiteWorkOrderExample example = new TbSiteWorkOrderExample();
		example.createCriteria().andWORK_IDEqualTo(siteWork.getWORK_ID());
		List<TbSiteWorkOrder> siteWorkOrderList = workOrderDao.findByConditions(example);
		if (WorkType.NSR.name().equals(siteWork.getWORK_TYPE())) {
			int updateSiteMainItem = siteMainDao.updateSiteStatusBySiteId(siteWork.getSITE_ID(),
					SiteStatus.S02);
			if (updateSiteMainItem == 0) {
				throw new NomsException("更新基站狀態失敗");
			}
			// siteStatusLogService.updateSiteStatusLog(siteWork.getSITE_ID(),SiteStatus.S01,
			// endTime);
			// siteStatusLogService.insertSiteStatusLogBySiteWork(siteWork,
			// SiteStatus.S02, new Date());
			siteStatusLogService.updateSiteStatusLog(siteWork.getSITE_ID(),
					siteWork.getBTS_SITE_ID(), SiteStatus.S01, SiteStatus.S02, new Date());
		}
		if (result) {
			int workUpdateItem = 0;
			int workOrderUpdateItem = 0;
			// 如果是射頻優化部又是new site request直接完工
			TbSysLookup lookup = lookupService.getByTypeAndCode("ORG_SPECIFIC_DEPT_ID",
					"SEARCH_RING_DEPT_ID");
			boolean isTrue = false;
			if (lookup.getVALUE1() != null) {
				String[] deptArray = lookup.getVALUE1().split(",");
				for (String dept : deptArray) {
					if (dept.equals(siteWork.getAPP_DEPT())
							&& WorkType.NSR.name().equals(siteWork.getWORK_TYPE())) {
						isTrue = true;
						break;
					}
				}
			}
			if (isTrue) {
				TbSiteWork record = workDao.findByPk(workId);
				record.setWORK_STATUS(WorkStatus.W07.name());
				record.setEND_TIME(endTime);
				workUpdateItem = workDao.updateByPrimaryKey(record);
				workOrderUpdateItem = workOrderDao.updateStatusByOrder(siteWorkOrderList.get(0)
						.getORDER_ID(), OrderStatus.OR09);
				try {
					this.completionNewSiteRequest(siteWork, isTrue);
				} catch (Exception e) {
					throw new NomsException(e.getMessage());
				}

			} else {
				TbSiteWork record = workDao.findByPk(siteWork.getWORK_ID());// new
																			// TbSiteWork();
				record.setWORK_ID(siteWork.getWORK_ID());
				record.setWORK_STATUS(WorkStatus.W05.name());
				workUpdateItem = workDao.updateByPrimaryKey(record);
				workOrderUpdateItem = workOrderDao.updateStatusByWork(siteWork.getWORK_ID(),
						OrderStatus.OR03);

				// add by Charlie 20150109 增加系統自動派工
				TbSiteWorkOrderExample criteria = new TbSiteWorkOrderExample();
				criteria.createCriteria().andWORK_IDEqualTo(workId).andIS_ACTIVEEqualTo("Y")
						.andODR_STATUSEqualTo(OrderStatus.OR03.name());
				criteria.setOrderByClause("ODR_SEQ"); // 先指派順序優先的工單
				List<TbSiteWorkOrder> orders = workOrderDao.findByConditions(criteria);
				int odr = 0;
				log.debug("取得要派工工單筆數:" + orders.size());
				for (TbSiteWorkOrder order : orders) {
					log.debug("準備派工工單號==>" + order.getORDER_ID() + ",order.getODR_SEQ()="
							+ order.getODR_SEQ());
					if (odr == 0 || odr == order.getODR_SEQ()) { // first time
																	// in.
						odr = order.getODR_SEQ();
						boolean autoAssign = respUserService.autoAssignOrder(siteWork.getAREA(),
								order); // 是否自動派工成功
						if (autoAssign) {
							siteEmailService.sendOrderAutoAssignMail(order.getORDER_ID());
							siteEmailService.sendOrderPickupMail(order.getORDER_ID());
						} else {
							siteEmailService.sendOrderAssignMail(order.getORDER_ID());

						}
					} else {
						break;
					}
				}
			}
			if (workUpdateItem == 0 || workOrderUpdateItem == 0) {
				log.error("簽核失敗" + "workUpdateItem update count =" + workUpdateItem
						+ ", workOrderUpdateItem update count = " + workOrderUpdateItem
						+ " , workId = " + siteWork.getWORK_ID());
				throw new NomsException("簽核失敗");
			}
		} else {
			int workOrderUpdateItem = workOrderDao.updateStatusByOrder(siteWorkOrderList.get(0)
					.getORDER_ID(), OrderStatus.OR01);
			TbSiteWork record = new TbSiteWork();
			record.setWORK_ID(siteWork.getWORK_ID());
			record.setWORK_STATUS(WorkStatus.W04.name());
			int workUpdateItem = workDao.updateByPrimaryKey(record);
			TbSiteWorkExample workExample = new TbSiteWorkExample();
			workExample.createCriteria().andWORK_IDNotEqualTo(siteWork.getWORK_ID())
					.andSITE_IDEqualTo(siteWork.getSITE_ID())
					.andWORK_TYPEEqualTo(WorkType.NSR.name());
			List<TbSiteWork> workList = workDao.findByConditions(workExample);
			if(workList.size() > 0){
				int updateSiteMainItem = siteMainDao.updateSiteStatusBySiteId(siteWork.getSITE_ID(), SiteStatus.S02);
				if (updateSiteMainItem == 0) {
					log.error("簽核失敗" + "updateSiteMainItem update count =" + updateSiteMainItem + " ,siteId = "
							+ siteWork.getSITE_ID());
					throw new NomsException("簽核失敗");
				}
			}else{
				int mainDeleteItem = siteMainDao.deleteByPrimaryKey(siteWork.getSITE_ID());
				if (mainDeleteItem == 0) {
					log.error("簽核失敗" + "mainDeleteItem delete count =" + mainDeleteItem + " ,siteId = "
							+ siteWork.getSITE_ID());
					throw new NomsException("簽核失敗");
				}
			}
			if (workOrderUpdateItem == 0 || workUpdateItem == 0) {
				log.error("簽核失敗" + "workUpdateItem update count =" + workUpdateItem
						+ ", workOrderUpdateItem update count = " + workOrderUpdateItem
						+ " , workId = " + siteWork.getWORK_ID());
				throw new NomsException("簽核失敗");
			}
		}
	}

	// NSR完工狀態才會呼叫
	// @Transactional
	public void completionNewSiteRequest(TbSiteWork siteWork, boolean isSearchRingDept)
			throws NomsException, Exception {
		TownDomainTeamDTO domainTeam = townDomainTeamDao.getTownDomainTeamByCityIdTownId(
				siteWork.getCITY(), siteWork.getAREA());
		Date date = new Date();
		String appUser = siteWork.getAPL_USER();
		TbOrgDept orgDept = deptDao.getDeptById(siteWork.getAPP_DEPT());
		TbAuthPersonnel personnel = personnelDao.searchByPsnNo(appUser);
		siteWork.setWORK_DESC("NSR單號:" + siteWork.getWORK_ID() + "\n申請單位:" + orgDept.getDEPT_NAME()
				+ "\n申請人:" + personnel.getCHN_NM());
		String work_Id = uniqueSeqService.getNextWorkId();
		siteWork.setWORK_ID(work_Id);
		siteWork.setWORK_TYPE(WorkType.SH.toString());
		siteWork.setWORK_STATUS(WorkStatus.W02.toString());
		siteWork.setCR_USER(appUser); // modify by Kevin 待辦建立者預設為原單申請人 20150330
		siteWork.setCR_TIME(date);
		siteWork.setMD_USER(appUser);
		siteWork.setMD_TIME(date);
		siteWork.setAPL_USER(null);
		siteWork.setAPL_TIME(null);
		siteWork.setEND_TIME(null);

		// 20150303 modify by Charlie NSR完工後待辦的申請部門並非系設，而是預設為各區無線優化部
		if (isSearchRingDept) {
			TbOrgDept srDept = orderTypeService.getOrderTypeForWorkArea("無線優化部", domainTeam);
			if (srDept != null)
				siteWork.setAPP_DEPT(srDept.getDEPT_ID());
		}
		int siteWorkItem = workDao.insert(siteWork);

		List<OrderTypeDTO> workOrderTypeList = orderTypeDao.findOrderTypeByWorkTypeId(
				WorkType.SH.name(), null);
		for (OrderTypeDTO workOrderType : workOrderTypeList) {
			TbSiteWorkOrder workOrder = new TbSiteWorkOrder();
			workOrder.setORDER_ID(uniqueSeqService.getNextOrderId(work_Id));
			workOrder.setODR_STATUS(OrderStatus.OR01.toString());
			workOrder.setWORK_ID(work_Id);
			workOrder.setODR_SEQ(workOrderType.getOrderSeq());
			workOrder.setORDER_TYPE(workOrderType.getOD_TYPE_ID());
			workOrder.setCR_USER(appUser); // modify by Kevin 待辦建立者預設為原單申請人 20150330
			workOrder.setCR_TIME(date);
			workOrder.setMD_USER(appUser);
			workOrder.setMD_TIME(date);
			workOrder.setIS_ACTIVE("Y"); // add by Charlie 20150126 新增的工單需設定IS_ACTIVE=Y
			String deptKey = orderTypeService.getOrdersDeptKey(workOrderType.getOD_TYPE_ID(), domainTeam.getDOMAIN(), siteWork.getEQP_TYPE_ID());
			TbOrgDept dept = orderTypeService.getOrderTypeForWorkArea(deptKey, domainTeam);
			workOrder.setREP_DEPT(dept.getDEPT_ID());

			int orderItem = workOrderDao.insert(workOrder);
			if (orderItem == 0) {
				log.error("簽核失敗" + "orderItem update count =" + orderItem + " , workId = "
						+ work_Id);
				throw new NomsException("簽核失敗");
			}
		}
		if (siteWorkItem == 0) {
			log.error("簽核失敗" + "siteWorkItem update count =" + siteWorkItem + " , workId = "
					+ work_Id);
			throw new NomsException("簽核失敗");
		}
	}

	private TbSiteWork getSiteWorkBySiteHuntApplyDTO(SiteHuntApplyDTO siteHuntApply, String workId,
			String siteId) {
		TbSiteWork siteWork = workDao.findByPk(siteHuntApply.getWorkId());
		if (siteWork == null) {
			siteWork = new TbSiteWork();
		}
		siteWork.setWORK_ID(workId);
		siteWork.setWORK_TYPE(siteHuntApply.getWorkType());
		siteWork.setWORK_STATUS(siteHuntApply.getWorkStatus());
		siteWork.setSR_LAT(siteHuntApply.getLat());
		siteWork.setSR_LON(siteHuntApply.getLon());
		siteWork.setSR_COVER_RANG(siteHuntApply.getCoverRange());
		siteWork.setSITE_ID(siteId);
		siteWork.setBTS_SITE_ID(StringUtils.trimToNull(siteHuntApply.getBtsSiteId()));
		siteWork.setPURPOSE(siteHuntApply.getPurpose());
		siteWork.setSITE_NAME(siteHuntApply.getSiteName());
		siteWork.setPREDICE_END_DATE(siteHuntApply.getPrediceEndDate());
		siteWork.setCITY(siteHuntApply.getCity());
		siteWork.setAREA(siteHuntApply.getArea());
		siteWork.setFEQ_ID(siteHuntApply.getFeqId().split(",")[0]);
		siteWork.setEQP_TYPE_ID(StringUtils.trimToNull(siteHuntApply.getEqpTypeId()));
		siteWork.setEQP_MODEL_ID(StringUtils.trimToNull(siteHuntApply.getEqpModelId()));
		siteWork.setCOVERAGE_TYPE(StringUtils.trimToNull(siteHuntApply.getCoverageType()));
		if (StringUtils.isNotEmpty(siteHuntApply.getLocationId())) {
			siteWork.setLOCATION_ID(siteHuntApply.getLocationId());
			TbSiteLocation location = locationDao.findByPk(siteWork.getLOCATION_ID());
			if (location != null) {
				siteWork.setADDR(location.getADDR());
				siteWork.setZIP(location.getZIP());
				siteWork.setVILLAGE(location.getVILLAGE());
				siteWork.setADJACENT(location.getADJACENT());
				siteWork.setROAD(location.getROAD());
				siteWork.setLANE(location.getLANE());
				siteWork.setALLEY(location.getALLEY());
				siteWork.setSUB_ALLEY(location.getSUB_ALLEY());
				siteWork.setDOOR_NO(location.getDOOR_NO());
				siteWork.setDOOR_NO_EX(location.getDOOR_NO_EX());
				siteWork.setFLOOR(location.getFLOOR());
				siteWork.setFLOOR_EX(location.getFLOOR_EX());
				siteWork.setROOM(location.getROOM());
				siteWork.setADD_OTHER(location.getADD_OTHER());
			}
		}
		siteWork.setPRIORITY(StringUtils.trimToNull(siteHuntApply.getPriority()));
		siteWork.setWORK_DESC(StringUtils.trimToNull(siteHuntApply.getWorkDesc()));
		siteWork.setSR_ID(StringUtils.trimToNull(siteHuntApply.getSrId()));
		siteWork.setBTS_CONFIG(StringUtils.trimToNull(StringUtils.trimToNull(siteHuntApply
				.getBtsConfig())));
		siteWork.setDONOR_SITE(StringUtils.trimToNull(siteHuntApply.getDonorSite()));
		siteWork.setFEEDERLESS(StringUtils.trimToNull(siteHuntApply.getFeederless()));
		siteWork.setMASTER_SITE(StringUtils.trimToNull(siteHuntApply.getMasterSite()));
		siteWork.setOS_TYPE(PurchaseOrderType.P.name()); // add by Charlie
															// 20141217
															// 尋點時工程類別預設為 一般
		log.debug("siteWork : " + siteWork.toString());
		log.debug("siteWork : " + siteWork.getFEQ_ID());
		return siteWork;
	}

	private TbSiteMain getSiteMainBySiteWork(TbSiteWork siteWork, Date startTime) {
		TbSiteMain siteMain = new TbSiteMain();
		siteMain.setSITE_ID(siteWork.getSITE_ID());
		siteMain.setFEQ_ID(siteWork.getFEQ_ID());
		siteMain.setBTS_SITE_ID(StringUtils.trimToNull(siteWork.getBTS_SITE_ID()));
		siteMain.setPURPOSE(siteWork.getPURPOSE());
		siteMain.setSITE_NAME(siteWork.getSITE_NAME());
		siteMain.setLOCATION_ID(StringUtils.trimToNull(siteWork.getLOCATION_ID()));
		siteMain.setSR_ID(StringUtils.trimToNull(siteWork.getSR_ID()));
		siteMain.setEQP_TYPE_ID(StringUtils.trimToNull(siteWork.getEQP_TYPE_ID()));
		siteMain.setEQP_MODEL_ID(StringUtils.trimToNull(siteWork.getEQP_MODEL_ID()));
		siteMain.setCOVERAGE_TYPE(StringUtils.trimToNull(siteWork.getCOVERAGE_TYPE()));
		siteMain.setBTS_CONFIG(StringUtils.trimToNull(siteWork.getBTS_CONFIG()));
		siteMain.setDONOR_SITE(StringUtils.trimToNull(siteWork.getDONOR_SITE()));
		siteMain.setFEEDERLESS(StringUtils.trimToNull(siteWork.getFEEDERLESS()));
		siteMain.setMASTER_SITE(StringUtils.trimToNull(siteWork.getMASTER_SITE()));
		siteMain.setLAT(siteWork.getSR_LAT());
		siteMain.setLON(siteWork.getSR_LON());
		if (WorkType.NSR.name().equals(siteWork.getWORK_TYPE())) {
			siteMain.setSITE_STATUS(SiteStatus.S01.name());
			// siteStatusLogService.insertSiteStatusLogBySiteWork(siteWork,
			// SiteStatus.S01, startTime);
			siteStatusLogService.updateSiteStatusLog(siteWork.getSITE_ID(),
					siteWork.getBTS_SITE_ID(), SiteStatus.S01, SiteStatus.S01, new Date());
		} else if (WorkType.SH.name().equals(siteWork.getWORK_TYPE())) {
			siteMain.setSITE_STATUS(SiteStatus.S03.name());
			// siteStatusLogService.insertSiteStatusLogBySiteWork(siteWork,
			// SiteStatus.S03, startTime);
			siteStatusLogService.updateSiteStatusLog(siteWork.getSITE_ID(),
					siteWork.getBTS_SITE_ID(), SiteStatus.S03, SiteStatus.S03, new Date());
		}
		return siteMain;
	}

	private TbSiteWorkOrder getSiteWorkOrderBySiteHuntApplyDTO(SiteHuntApplyDTO siteHuntApply,
			String workId, String orderStatus, String orderId, OrderTypeDTO orderType,
			TownDomainTeamDTO domainTeam) throws Exception {
		TbSiteWorkOrder siteWorkOrder = new TbSiteWorkOrder();
		siteWorkOrder.setORDER_ID(orderId);
		siteWorkOrder.setWORK_ID(workId);
		siteWorkOrder.setODR_STATUS(orderStatus);
		siteWorkOrder.setPRIORITY(siteHuntApply.getPriority());
		siteWorkOrder.setODR_SEQ(orderType.getOrderSeq());
		siteWorkOrder.setORDER_TYPE(orderType.getOD_TYPE_ID());
		siteWorkOrder.setIS_ACTIVE("Y");
		String deptKey = orderTypeService.getOrdersDeptKey(orderType.getOD_TYPE_ID(), domainTeam.getDOMAIN(), siteHuntApply.getEqpTypeId());
		TbOrgDept dept = orderTypeService.getOrderTypeForWorkArea(deptKey, domainTeam);
		siteWorkOrder.setREP_DEPT(dept.getDEPT_ID());

		return siteWorkOrder;
	}

	private String dovalidateBtsSiteId(String domain, String btsSiteId, String nRegex,
			String mRegex, String cRegex, String sRegex) {
		btsSiteId = btsSiteId.toUpperCase();
		String errorMessages = "";
		String msg = "基站編號格式錯誤或SR ID區域不符\n";
		// try{
		if ("N".equals(domain)) {
			if (!btsSiteId.matches(nRegex)) {
				errorMessages += msg;
				log.debug("errorMessages : " + errorMessages);
			}
		} else if ("M".equals(domain)) {
			if (!btsSiteId.matches(mRegex)) {
				errorMessages += msg;
				log.debug("errorMessages : " + errorMessages);
			}
		} else if ("C".equals(domain)) {
			if (!btsSiteId.matches(cRegex)) {
				errorMessages += msg;
				log.debug("errorMessages : " + errorMessages);
			}
		} else if ("S".equals(domain)) {
			if (!btsSiteId.matches(sRegex)) {
				errorMessages += msg;
				log.debug("errorMessages : " + errorMessages);
			}
		} else {
			errorMessages += "區域錯誤\n";
		}
		return errorMessages;
	}

	/**
	 * 使用 order id 查工單
	 * 
	 * @param orderId
	 * @return
	 */
	public TbSiteWorkOrder selectSiteWorkOrderByOrderId(String orderId) {
		return st002Dao.selectSiteWorkOrderByOrderId(orderId);
	}

	/**
	 * 更新工單
	 * 
	 * @param record
	 * @return
	 */
	public int update(TbSiteWorkOrder record) {
		return workOrderDao.update(record);
	}
	
	/**
	 * 查詢siteWork
	 * @param example
	 * @return
	 */
	public List<TbSiteWork> getSiteWorkByConditions(TbSiteWorkExample example){
		return workDao.findByConditions(example);
	}
}
