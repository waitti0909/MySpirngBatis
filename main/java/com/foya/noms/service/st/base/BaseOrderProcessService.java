package com.foya.noms.service.st.base;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.dao.mybatis.model.TbInvMaterialOpt;
import com.foya.dao.mybatis.model.TbInvMaterialOptExample;
import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbOrgDeptPos;
import com.foya.dao.mybatis.model.TbOrgPsnPos;
import com.foya.dao.mybatis.model.TbSiteAntCfg;
import com.foya.dao.mybatis.model.TbSiteAntCfgExample;
import com.foya.dao.mybatis.model.TbSiteLineApply;
import com.foya.dao.mybatis.model.TbSiteLineApplyExample;
import com.foya.dao.mybatis.model.TbSiteLocation;
import com.foya.dao.mybatis.model.TbSiteMain;
import com.foya.dao.mybatis.model.TbSiteMainExample;
import com.foya.dao.mybatis.model.TbSiteOutsourcing;
import com.foya.dao.mybatis.model.TbSiteOutsourcingExample;
import com.foya.dao.mybatis.model.TbSiteStatusLog;
import com.foya.dao.mybatis.model.TbSiteWork;
import com.foya.dao.mybatis.model.TbSiteWorkAntCfgTemp;
import com.foya.dao.mybatis.model.TbSiteWorkAntCfgTempExample;
import com.foya.dao.mybatis.model.TbSiteWorkLocTemp;
import com.foya.dao.mybatis.model.TbSiteWorkLocTempKey;
import com.foya.dao.mybatis.model.TbSiteWorkOrder;
import com.foya.dao.mybatis.model.TbSiteWorkOrderExample;
import com.foya.dao.mybatis.model.TbSiteWorkSiteTemp;
import com.foya.dao.mybatis.model.TbSiteWorkSiteTempKey;
import com.foya.dao.mybatis.model.TbSysOrderPage;
import com.foya.dao.mybatis.model.TbSysOrderPageExample;
import com.foya.exception.CreateFailException;
import com.foya.exception.NomsException;
import com.foya.exception.UpdateFailException;
import com.foya.noms.dao.auth.PersonnelDao;
import com.foya.noms.dao.common.TownDomainTeamDao;
import com.foya.noms.dao.org.DeptPosDao;
import com.foya.noms.dao.org.PsnPosDao;
import com.foya.noms.dao.st.AntCfgDao;
import com.foya.noms.dao.st.AntCfgTempDao;
import com.foya.noms.dao.st.LocTempDao;
import com.foya.noms.dao.st.LocationDao;
import com.foya.noms.dao.st.MeterialOptDao;
import com.foya.noms.dao.st.OutsourcingDao;
import com.foya.noms.dao.st.SiteMainDao;
import com.foya.noms.dao.st.SiteStatusLogDao;
import com.foya.noms.dao.st.SiteTempDao;
import com.foya.noms.dao.st.WorkDao;
import com.foya.noms.dao.st.WorkOrderDao;
import com.foya.noms.dao.st.base.BaseOrderProcessDao;
import com.foya.noms.dao.st.line.LineApplyDao;
import com.foya.noms.dao.system.OrderTypeDao;
import com.foya.noms.dto.common.TownDomainTeamDTO;
import com.foya.noms.dto.st.MeterialRtnDTO;
import com.foya.noms.dto.st.SiteWorkOrderDTO;
import com.foya.noms.dto.system.OrderTypeDTO;
import com.foya.noms.enums.EmailType;
import com.foya.noms.enums.LineApplyStatus;
import com.foya.noms.enums.LineStatus;
import com.foya.noms.enums.MaterialOptType;
import com.foya.noms.enums.OrderStatus;
import com.foya.noms.enums.OutSourceStatus;
import com.foya.noms.enums.SiteStatus;
import com.foya.noms.enums.WorkStatus;
import com.foya.noms.enums.WorkType;
import com.foya.noms.resources.AppConstants;
import com.foya.noms.service.BaseService;
import com.foya.noms.service.common.EmailTemplateService;
import com.foya.noms.service.inv.INVService;
import com.foya.noms.service.st.SiteEmailService;
import com.foya.noms.service.system.OrderTypeService;
import com.foya.noms.service.system.RespUserService;
import com.foya.noms.service.system.UniqueSeqService;
import com.foya.noms.service.workflow.WorkflowActionService;
import com.foya.workflow.exception.WorkflowException;

/**
 * 
 * 
 * <table>
 * <tr>
 * <th>版本</th>
 * <th>日期</th>
 * <th>詳細說明</th>
 * <th>modifier</th>
 * </tr>
 * <tr>
 * <td>1.0</td>
 * <td>2015/3/27</td>
 * <td>通用工單操作Service</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table>
 * 
 * @author Charlie Woo
 * 
 */
public class BaseOrderProcessService extends BaseService {

	@Autowired
	private BaseOrderProcessDao dao;

	@Autowired
	private WorkDao workDao;

	@Autowired
	private WorkOrderDao workOrderDao;

	@Autowired
	private OutsourcingDao outsourcingDao;

	@Autowired
	private MeterialOptDao meterialOptDao;

	@Autowired
	private LineApplyDao lineApplyDao;

	@Autowired
	private OrderTypeDao orderTypeDao;

	@Autowired
	private SiteStatusLogDao siteStatusLogDao;

	@Autowired
	private TownDomainTeamDao townDomainTeamDao;

	@Autowired
	private SiteTempDao siteTempDao;

	@Autowired
	private SiteMainDao siteMainDao;

	@Autowired
	private LocTempDao locTempDao;

	@Autowired
	private LocationDao locationDao;

	@Autowired
	private AntCfgDao antCfgDao;

	@Autowired
	private AntCfgTempDao antCfgTempDao;

	@Autowired
	private RespUserService respUserService;

	@Autowired
	private SiteEmailService siteEmailService;

	@Autowired
	private WorkflowActionService workflowActionService;

	@Autowired
	private EmailTemplateService emailTemplateService;

	@Autowired
	private UniqueSeqService uniqueSeqService;

	@Autowired
	private OrderTypeService orderTypeService;

	@Autowired
	private INVService invService;

	// /////////////////////////
	@Autowired
	private DeptPosDao deptPosDao;

	@Autowired
	private PsnPosDao psnPosDao;

	@Autowired
	private PersonnelDao personnelDao;

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
	 * 取尚未完成的工單數量
	 * 
	 * @param workId
	 * @param orderId
	 * @return
	 * @author Charlie Woo
	 */
	public Integer getNotFinishedOrders(String workId, String orderId) {
		TbSiteWorkOrder order = workOrderDao.findOrderByPk(orderId);
		List<String> notFinishedStatus = OrderStatus.getNotFinishedStatus();

		// 取得除了尚未完成的有效前置工單
		TbSiteWorkOrderExample example = new TbSiteWorkOrderExample();
		example.createCriteria().andWORK_IDEqualTo(workId).andIS_ACTIVEEqualTo("Y").andODR_SEQLessThan(order.getODR_SEQ())
				.andODR_STATUSIn(notFinishedStatus);
		List<TbSiteWorkOrder> orders = workOrderDao.findByConditions(example);
		return orders.isEmpty() ? 0 : orders.size();
	}

	/**
	 * 派工
	 */
	@Transactional
	public boolean assignOrder(String orderId, String maintainTeam, String maintainUser) {
		try {
			Date currentDate = new Date();
			TbSiteWorkOrder record = workOrderDao.findOrderByPk(orderId);
			// 若無選擇負責單位，則與接工部門相同 modify by Charlie 20150204
			if (StringUtils.isEmpty(maintainTeam)) {
				maintainTeam = record.getREP_DEPT();
			}

			record.setODR_STATUS(OrderStatus.OR04.name());
			record.setREP_TEAM(maintainTeam);
			record.setREP_USER(maintainUser);
			record.setASSIGN_USER(getLoginUser().getUsername());
			record.setASSIGN_TIME(currentDate);
			record.setMD_USER(getLoginUser().getUsername());
			record.setMD_TIME(currentDate);
			workOrderDao.updateSelective(record);

			// send email add by Charlie 20150121
			siteEmailService.sendOrderPickupMail(orderId);

		} catch (Exception e) {
			throw new NomsException(ExceptionUtils.getFullStackTrace(e));
		}

		return true;
	}

	/**
	 * 接工
	 * 
	 * @param siteWorkOrder
	 * @return
	 * @author Charlie Woo
	 */
	public int pickupOrder(String orderId) {
		TbSiteWorkOrder siteWorkOrder = new TbSiteWorkOrder();
		siteWorkOrder.setORDER_ID(orderId);
		siteWorkOrder.setODR_STATUS(OrderStatus.OR05.name());
		siteWorkOrder.setPICK_TIME(new Date());
		siteWorkOrder.setPICK_USER(getLoginUser().getUsername());
		siteWorkOrder.setMD_TIME(new Date());
		siteWorkOrder.setMD_USER(getLoginUser().getUsername());
		return workOrderDao.updateSelective(siteWorkOrder);
	}

	/**
	 * 退工
	 * 
	 * @param workId
	 * @return
	 * @throws NomsException
	 * @throws UpdateFailException
	 */
	@Transactional
	public List<String> dropOrder(String workId, String endDesc) throws UpdateFailException {
		TbSiteWorkOrderExample workOrderExample = new TbSiteWorkOrderExample();
		workOrderExample.createCriteria().andWORK_IDEqualTo(workId).andIS_ACTIVEEqualTo("Y");
		List<String> errorMessageListe = new ArrayList<String>();
		List<String> osStatusList = new ArrayList<String>();
		osStatusList.add(OutSourceStatus.OS07.name());
		osStatusList.add(OutSourceStatus.OS08.name());
		List<TbSiteWorkOrder> workOrderList = workOrderDao.findByConditions(workOrderExample);
		for (TbSiteWorkOrder workOrder : workOrderList) {
			// 退工時需檢核其下之委外工單(TB_SITE_OUTSOURCING)是否OS07已驗收或OS08取消。
			TbSiteOutsourcingExample example = new TbSiteOutsourcingExample();
			example.createCriteria().andORDER_IDEqualTo(workOrder.getORDER_ID()).andSTATUSNotIn(osStatusList);
			List<TbSiteOutsourcing> outSourcingList = outsourcingDao.findSiteOutSoureByExample(example);
			if (outSourcingList.size() != 0) {
				errorMessageListe.add("工單編號 : " + workOrder.getORDER_ID() + " 有委外工單未" + OutSourceStatus.OS07.getLocalName() + "或"
						+ OutSourceStatus.OS08.getLocalName());
			}
			// 退工時需檢核其下之領料之料件(TB_INV_MATERIAL_OPT)需全數拆下再進行退料申請(狀態為RT02退料完成)
			int installs = meterialOptDao.findInsItemOnSiteForOrder(workOrder.getORDER_ID());
			if (installs > 0) {
				errorMessageListe.add("工單編號 : " + workOrder.getORDER_ID() + " 有已安裝之物料尚未進行拆卸");
			} else {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("orderId", workOrder.getORDER_ID());
				List<MeterialRtnDTO> list = meterialOptDao.selectRemInsItemQuery(map);
				for (MeterialRtnDTO model : list) {
					if (model.getAssetQty() > 0) { // 若尚有一筆未處理完的在途物料
						errorMessageListe.add("工單編號 : " + workOrder.getORDER_ID() + " 尚有在途物料未處理，請先行退料");
						break;
					}
				}

				TbInvMaterialOptExample materialOptexample = new TbInvMaterialOptExample();
				materialOptexample.createCriteria().andORDER_IDEqualTo(workOrder.getORDER_ID()).andOPT_TYPEEqualTo(MaterialOptType.MRQ.name())
						.andSTATUSNotEqualTo("MR05").andSTATUSNotEqualTo("MR03");
				List<TbInvMaterialOpt> materialOptList = meterialOptDao.findInvMaterialOptByOrderId(materialOptexample);
				if (materialOptList.size() != 0) {
					errorMessageListe.add("工單編號 : " + workOrder.getORDER_ID() + " 有物料領料單尚未完成結案(發料完成)");
				}

				materialOptexample.clear();
				materialOptexample.createCriteria().andORDER_IDEqualTo(workOrder.getORDER_ID()).andOPT_TYPEEqualTo(MaterialOptType.RTN.name())
						.andSTATUSNotEqualTo("RT02");
				materialOptList = meterialOptDao.findInvMaterialOptByOrderId(materialOptexample);
				if (materialOptList.size() != 0) {
					errorMessageListe.add("工單編號 : " + workOrder.getORDER_ID() + " 有物料操作單未退料完成");
				}
			}
			// 退工時需檢核其下之專線申請(TB_SITE_LINE_APPLY)需全數狀態為:LA07已取消
			TbSiteLineApplyExample lineApplyExample = new TbSiteLineApplyExample();
			lineApplyExample.createCriteria().andORDER_IDEqualTo(workOrder.getORDER_ID()).andAPP_STATUSNotEqualTo(LineApplyStatus.LA07.name());
			List<TbSiteLineApply> lineApplyList = lineApplyDao.findByConditions(lineApplyExample);
			if (lineApplyList.size() != 0) {
				errorMessageListe.add("工單編號 : " + workOrder.getORDER_ID() + " 其下之專線申請尚未全數取消");
			}
		}
		if (errorMessageListe.size() == 0) {
			// Update TB_SITE_WORK_ORDER to OR07 where IS_ACTIVE=Y by workId
			int workOrderItem = workOrderDao.updateStatusByWork(workId, OrderStatus.OR07);
			if (workOrderItem == 0) {
				log.error("update fail workOrderItem count = " + workOrderItem + " , workId = " + workId);
				throw new UpdateFailException("更新失敗");
			}
			// Update TB_SITE_WORK set status to W06 by workId
			int updateWorkItem = workDao.updateStatusByWork(workId, WorkStatus.W06);
			if (updateWorkItem == 0) {
				log.error("update fail updateWorkItem count = " + updateWorkItem + " , workId = " + workId);
				throw new UpdateFailException("更新失敗");
			}
			// update TB_SITE_MAIN set status to S04
			TbSiteWork work = workDao.findByPk(workId);
			int updateSiteItem = siteMainDao.updateSiteStatusBySiteId(work.getSITE_ID(), SiteStatus.valueOf(work.getSTART_SITE_STATUS())); // 轉為取得作業前狀態
																																			// modify
																																			// by
																																			// Charlie
																																			// 201250318
			if (updateSiteItem == 0) {
				log.error("update fail updateSiteItem count = " + updateSiteItem + " , siteId = " + work.getSITE_ID());
				throw new UpdateFailException("更新失敗");
			}
		}
		return errorMessageListe;
	}

	/**
	 * 完工送審前驗證
	 * 
	 * @param workId
	 * @param orderId
	 * @return
	 */
	public String doValidate(String workId, String orderId) {

		log.debug("送完工前驗證：workId=" + workId + ", orderId=" + orderId);
		TbSiteWork work = workDao.findByPk(workId);
		TbSiteWorkOrder order = workOrderDao.findOrderByPk(orderId);
		// modify by Charlie 20150306
		Integer notFinished = getNotFinishedOrders(workId, orderId);
		if (notFinished > 0) {
			return "尚有前置工單未完工！";
		}

		// 料件在途檢核
		boolean stillOnhand = false;
		Integer odrSeq = order.getODR_SEQ();		
		if (odrSeq == 99) { // 物料處理工單
			String mtOrderId = getOriginalMaterialOrderId(order); // 取得舊有土木工單的ORDER_ID
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("orderId", mtOrderId);
			List<MeterialRtnDTO> list = meterialOptDao.selectRemInsItemQuery(map);
			stillOnhand = !list.isEmpty();
			
		} else if (StringUtils.equals(WorkType.SGL.name(), work.getWORK_TYPE())) { // 單張工單
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("orderId", orderId);
			List<MeterialRtnDTO> list = meterialOptDao.selectRemInsItemQuery(map);
			stillOnhand = !list.isEmpty();
		} 
		if (stillOnhand) {				
			log.debug("****物料處理工單尚有在途料件**** orderId=" + order.getORDER_ID());
			return "工單編號：" + orderId + ", 所屬物料操作單尚有在途物料未處理完畢！";
		}
		
		// 領料單申請 已發料完成(STATUS=MR05)+已駁回(STATUS=MR03)
		TbInvMaterialOptExample optExampleA = new TbInvMaterialOptExample();
		optExampleA.createCriteria().andORDER_IDEqualTo(orderId).andOPT_TYPEEqualTo(MaterialOptType.MRQ.name()).andSTATUSNotEqualTo("MR05").andSTATUSNotEqualTo("MR03");
		List<TbInvMaterialOpt> optListMRQ = meterialOptDao.findInvMaterialOptByOrderId(optExampleA);// .findMaterialOptByConditions(optExampleA);
		// 退料單申請 已退料完成(STATUS=RT02)
		TbInvMaterialOptExample optExampleR = new TbInvMaterialOptExample();
		optExampleR.createCriteria().andORDER_IDEqualTo(orderId).andOPT_TYPEEqualTo(MaterialOptType.RTN.name()).andSTATUSNotEqualTo("RT02");
		List<TbInvMaterialOpt> optListRTN = meterialOptDao.findInvMaterialOptByOrderId(optExampleR);// .findMaterialOptByConditions(optExampleR);

		log.debug("驗證物料需全數發料OR退料：MRQ=" + optListMRQ.size() + ", RTN=" + optListRTN.size());
		if (!optListMRQ.isEmpty() || !optListRTN.isEmpty()) {
			return "工單編號：" + orderId + ", 所屬物料操作單需全數發料或退料完成！";
		}
		
		// 委外申請
		List<String> status = new ArrayList<String>();
		status.add(OutSourceStatus.OS07.name());
		status.add(OutSourceStatus.OS08.name());
		TbSiteOutsourcingExample example = new TbSiteOutsourcingExample();
		example.createCriteria().andORDER_IDEqualTo(orderId).andSTATUSNotIn(status);
		List<TbSiteOutsourcing> list = outsourcingDao.findSiteOutSoureByExample(example);
		if (list != null && list.size() > 0) {
			return "工單編號：" + orderId + ", 所屬委外派工申請單須皆已驗收或已取消！";
		}

		// 專線需要皆已竣工LA06或已取消LA07 add by Charlie 20150313
		List<String> appStatus = new ArrayList<String>();
		appStatus.add(LineApplyStatus.LA06.name());
		appStatus.add(LineApplyStatus.LA07.name());
		TbSiteLineApplyExample lineCriteria = new TbSiteLineApplyExample();
		lineCriteria.createCriteria().andORDER_IDEqualTo(orderId).andAPP_STATUSNotIn(appStatus);
		List<TbSiteLineApply> lines = lineApplyDao.findByConditions(lineCriteria);
		if (lines != null && lines.size() > 0) {
			return "工單編號：" + orderId + ", 所屬專線申請單須皆已竣工或已取消！";
		}

		return null;
	}

	/**
	 * 以物料操作工單取得原土木工單單號
	 * 
	 * @param siteWorkOrder
	 * @return
	 * @author Charlie Woo
	 */
	public String getOriginalMaterialOrderId(TbSiteWorkOrder siteWorkOrder) {
		Map<String, String> materialOrderMap = new HashMap<>();
		materialOrderMap.put("MO", "SC");
		materialOrderMap.put("MOH", "SCH");
		materialOrderMap.put("MON", "SCN");

		if (materialOrderMap.containsKey(siteWorkOrder.getORDER_TYPE())) {
			String toSearchOrderType = materialOrderMap.get(siteWorkOrder.getORDER_TYPE());
			TbSiteWorkOrderExample orderExample = new TbSiteWorkOrderExample();
			orderExample.createCriteria().andWORK_IDEqualTo(siteWorkOrder.getWORK_ID()).andORDER_TYPEEqualTo(toSearchOrderType);
			List<TbSiteWorkOrder> mtOrders = getOrderByCondition(orderExample); // 同張作業下照理只有一張獨立TYPE種類工單
			if (!mtOrders.isEmpty()) {
				return mtOrders.get(0).getORDER_ID();
			}
		}
		return "";
	}

	/**
	 * 完工送審
	 * 
	 * @param workId
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void verifyOrder(String orderId, String workId, String mdUser) {
		Date currentDate = new Date();

		try {
			TbSiteWorkOrder order = workOrderDao.findOrderByPk(orderId);
			order.setORDER_ID(orderId);
			order.setODR_STATUS(OrderStatus.OR08.name());
			order.setMD_USER(mdUser);
			order.setMD_TIME(currentDate);
			workOrderDao.updateSelective(order);

			workflowActionService.apply("SiteBuildCompletionApply", orderId, "建站工單完工申請",
					emailTemplateService.getMailVarMapForWorkflow(EmailType.WORKFLOW_TODO_WORK_COMPLETION_APPLY, orderId));
		} catch (WorkflowException e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * 重啟工單
	 * 
	 * @param orderId
	 * @param reopenOrderDesc
	 * @return
	 */
	@Transactional
	public void reopenOrder(String orderId, String reopenOrderDesc, String workType) throws UpdateFailException {
		TbSiteWorkOrder workOrder = workOrderDao.findOrderByPk(orderId);
		workOrder.setODR_STATUS(OrderStatus.OR04.name());
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String reponeUserInfo = "重啟工單者: " + this.getLoginUser().getChName() + " ,重啟時間 :" + sdFormat.format(new Date());
		if (StringUtils.isNotEmpty(reopenOrderDesc)) {
			workOrder.setORDER_DESC(reopenOrderDesc + "\n" + reponeUserInfo);
		} else {
			String orderType = orderTypeDao.findByPk(workOrder.getORDER_TYPE()).getOD_TYPE_NAME();
			workOrder.setORDER_DESC(WorkType.detectLabel(workType) + "-" + orderType + "\n" + reponeUserInfo);
		}
		int workOrderItem = workOrderDao.updateSelective(workOrder);
		if (workOrderItem == 0) {
			log.error("update fail workOrderItem count = " + workOrderItem + ", orderId = " + orderId);
			throw new UpdateFailException("更新失敗");
		}
	}

	/**
	 * 建站作業完工-簽核
	 * 
	 * @param orderId
	 *            工單編號
	 * @return
	 * @throws NomsException
	 */
	@Transactional
	public void finishSiteBuildOrder(String orderId, String action, String mdUser) throws NomsException {
		Date mdTime = new Date();
		try {
			TbSiteWorkOrder order = workOrderDao.findOrderByPk(orderId);
			TbSiteWork work = workDao.findByPk(order.getWORK_ID());

			if (StringUtils.equals(action, AppConstants.WORKFLOW_REST_REJECT)) { // 完工駁回

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

			} else { // 完工核可
				// 1.更新成 已完工
				order.setODR_STATUS(OrderStatus.OR09.name());
				order.setMD_USER(mdUser);
				order.setMD_TIME(mdTime);
				order.setEND_TIME(mdTime);
				order.setEND_USER(mdUser);
				workOrderDao.updateSelective(order);

				// 2.該工單類型若為土木工單類(ORDER_TYPE=SC、SCH、SCN)時，完工時額外檢查工單下若有在途物料，則新增99處理工單
				boolean isNewAnOrder = false;
				if (order.getODR_SEQ() != 99 && !StringUtils.equals("SGL", work.getWORK_TYPE())) {
					// 非單張工單和非物料操作單狀況下，才需要新增
					isNewAnOrder = toNewMaterialOptionOrder(work, order);
					log.debug("====>> 是否有新增土木物料處理工單：" + isNewAnOrder);
				}

				// 檢查是否有下一張待施作工單(STATUS=OR03、NEXT_ODR_SEQ)，若有待施作工單，則以先行透過系統自動派工方式
				boolean hasNext = checkHasNextOrder(orderId, work.getAREA(), work.getWORK_ID());

				// 若無待施作工單則進行作業完工
				if (!hasNext && !isNewAnOrder) doFinalCheckOrdersAllFinished(work.getWORK_ID());
			}

		} catch (NomsException e) {
			log.error(e.getMessage(), e);
			throw e;
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new NomsException(ExceptionUtils.getFullStackTrace(ex));
		}
	}

	// 最後再檢查是否有下一張待施作工單(STATUS=OR03, NEXT_ODR_SEQ)，若有待施作工單，則以先行透過系統自動派工方式
	private boolean checkHasNextOrder(String orderId, String area, String workId) {
		boolean hasNext = true;

		// 檢查是否有下一張排隊工單，若後續還有工單則進行自動派工與通知，反之則作業完工W07
		TbSiteWorkOrder order = workOrderDao.findOrderByPk(orderId);
		TbSiteWorkOrderExample orderExample = new TbSiteWorkOrderExample();
		orderExample.createCriteria().andODR_SEQGreaterThanOrEqualTo(order.getODR_SEQ()).andWORK_IDEqualTo(order.getWORK_ID())
				.andIS_ACTIVEEqualTo("Y").andORDER_IDNotEqualTo(order.getORDER_ID());
		List<TbSiteWorkOrder> orderList = workOrderDao.findByConditions(orderExample);
		if (!orderList.isEmpty()) {
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
							boolean autoAssign = respUserService.autoAssignOrder(area, orderObj); // 是否自動派工成功
							if (autoAssign) {
								siteEmailService.sendOrderAutoAssignMail(orderObj.getORDER_ID());
								siteEmailService.sendOrderPickupMail(orderObj.getORDER_ID());
							} else {
								siteEmailService.sendOrderAssignMail(orderObj.getORDER_ID());
							}
						}
					}
				}
				hasNext = false;
			}

		} else {
			hasNext = false;
		}
		return hasNext;
	}

	// 是最後一層就驗證全部工單是否完工
	private boolean doFinalCheckOrdersAllFinished(String workId) {
		Date mdTime = new Date();
		String mdUser = getLoginUser().getUsername();
		TbSiteWorkOrderExample orderExample = new TbSiteWorkOrderExample();
		orderExample.createCriteria().andWORK_IDEqualTo(workId).andODR_STATUSNotEqualTo(OrderStatus.OR09.name()).andIS_ACTIVEEqualTo("Y");
		List<TbSiteWorkOrder> orderList = workOrderDao.findByConditions(orderExample);
		log.debug("orderList.size() = " + orderList.size());
		TbSiteWork work = workDao.findByPk(workId);

		if (orderList.isEmpty()) {
			// 3.寫入站點主檔存檔
			log.debug("=====站點主檔 start =====");
			TbSiteWorkLocTempKey key = new TbSiteWorkLocTempKey();
			key.setWORK_ID(work.getWORK_ID());
			key.setLOCATION_ID(work.getLOCATION_ID());
			TbSiteWorkLocTemp locationTemp = locTempDao.selectByPrimaryKey(key);
			TbSiteLocation siteLoc = this.getLocationModel(locationTemp);
			siteLoc.setMD_TIME(mdTime);
			siteLoc.setMD_USER(mdUser);
			int locationData = locationDao.update(siteLoc);

			if (locationData == 0) {
				log.error("站點主檔 update fail workLoc count=" + locationData + " locationId =" + work.getLOCATION_ID());
				throw new UpdateFailException("update fail");
			}
			log.debug("=====站點主檔 end =====");

			// 4.寫入站台主檔
			log.debug("=====站台主檔 start =====");
			TbSiteWorkSiteTempKey siteKey = new TbSiteWorkSiteTempKey();
			siteKey.setWORK_ID(work.getWORK_ID());
			siteKey.setSITE_ID(work.getSITE_ID());
			TbSiteWorkSiteTemp siteTempData = siteTempDao.findByPrimaryKey(siteKey);
			TbSiteMain siteMain = this.getSiteMainModel(siteTempData, work);
			siteMain.setSITE_STATUS(work.getEND_SITE_STATUS()); 
			if (StringUtils.equals(work.getEND_SITE_STATUS(), SiteStatus.S06.name())) {
				siteMain.setONAIR_DATE(mdTime);
			}
			// 停用狀態的話 回押 停用日期
			if (StringUtils.equals(work.getEND_SITE_STATUS(), SiteStatus.S08.name())) {
				siteMain.setSUSPEND_DATE(mdTime);
			}
			// 拆站狀態的話 回押 拆站日期
			if (StringUtils.equals(work.getEND_SITE_STATUS(), SiteStatus.S11.name())) {
				siteMain.setDUMP_DATE(mdTime);
			}
			siteMain.setMD_USER(mdUser);
			siteMain.setMD_TIME(mdTime);
			int main = siteMainDao.updateByPrimaryKeySelective(siteMain);

			if (main == 0) {
				log.error("站台主檔存檔 update fail workSite count=" + main + " siteId =" + work.getSITE_ID());
				throw new UpdateFailException("update fail");
			}
			log.debug("=====站台主檔存檔 end =====");

			// 5.寫入天線主檔
			log.debug("=====天線主檔存檔 start =====");
			TbSiteWorkAntCfgTempExample cfgExample = new TbSiteWorkAntCfgTempExample();
			cfgExample.createCriteria().andSITE_IDEqualTo(work.getSITE_ID()).andWORK_IDEqualTo(work.getWORK_ID());
			List<TbSiteWorkAntCfgTemp> siteAntCfgTempList = antCfgTempDao.findByCondition(cfgExample);

			// 先將 主檔 資料刪除後，再將 暫存檔資料新增
			TbSiteAntCfgExample antCfgExample = new TbSiteAntCfgExample();
			antCfgExample.createCriteria().andSITE_IDEqualTo(work.getSITE_ID());
			antCfgDao.deleteSiteAntCfg(antCfgExample);
			for (TbSiteWorkAntCfgTemp cfgTempList : siteAntCfgTempList) {

				TbSiteAntCfg tbSiteAntCfg = this.getSiteAntCfgModel(cfgTempList);
				tbSiteAntCfg.setCR_USER(mdUser);
				tbSiteAntCfg.setCR_TIME(mdTime);
				tbSiteAntCfg.setMD_USER(mdUser);
				tbSiteAntCfg.setMD_TIME(mdTime);
				int siteSrAntCfg = antCfgDao.insert(tbSiteAntCfg);
				if (siteSrAntCfg == 0) {
					log.error("天線主檔 insert fail siteSrAntCfg count=" + siteSrAntCfg + " siteId =" + work.getSITE_ID());
					throw new CreateFailException("insert fail");
				}
			}
			log.debug("=====天線主檔存檔 end =====");

			// 若為拆站作業時，專線需檢查是否閒置
			log.debug("=====檢查閒置專線 start =====");
			if (siteMain != null) {
				// add by Charlie 拆站需檢查並更新站台下專線的狀態=>L06閒置中( TO FIXME Phase II時需將拆站作業搬出新建的Service實作)
				if (StringUtils.equals(SiteStatus.S11.name(), siteMain.getSITE_STATUS())) {
					// check all sites dump or not in the same location
					TbSiteMainExample example = new TbSiteMainExample();
					example.createCriteria().andLOCATION_IDEqualTo(siteMain.getLOCATION_ID()).andSITE_STATUSNotEqualTo(SiteStatus.S11.name());
					List<TbSiteMain> otherSites = siteMainDao.findByConditions(example);
					if (otherSites.isEmpty()) {
						// all sites dump
						TbSiteLineApplyExample lineExample = new TbSiteLineApplyExample();
						lineExample.createCriteria().andA_LOCEqualTo(siteMain.getLOCATION_ID()).andLINE_STATUSEqualTo(LineStatus.L02.name());
						List<TbSiteLineApply> aLines = lineApplyDao.findByConditions(lineExample);

						lineExample.clear();
						lineExample.createCriteria().andB_LOCEqualTo(siteMain.getLOCATION_ID()).andLINE_STATUSEqualTo(LineStatus.L02.name());
						List<TbSiteLineApply> bLines = lineApplyDao.findByConditions(lineExample);

						aLines.addAll(bLines); // add both
						for (TbSiteLineApply line : aLines) {
							line.setLINE_STATUS(LineStatus.L06.name());
							lineApplyDao.update(line);
						}
					}
				}
			}
			log.debug("=====檢查閒置專線 end =====");

			// TB_SITE_STATUS_LOG
			log.debug("=====更改 STATUS LOG start =====");
			TbSiteStatusLog statusLog = new TbSiteStatusLog();
			statusLog.setBTS_SITE_ID(work.getBTS_SITE_ID());
			statusLog.setSITE_ID(work.getSITE_ID());
			statusLog.setSITE_STATUS(work.getEND_SITE_STATUS()); 
			statusLog.setSTART_TIME(mdTime);
			statusLog.setEND_TIME(mdTime);
			siteStatusLogDao.insert(statusLog);
			log.debug("=====更改 STATUS LOG end =====");

			TbSiteWork record = workDao.findByPk(workId);
			if (record != null) {
				record.setWORK_STATUS(WorkStatus.W07.name());
				record.setEND_TIME(mdTime);
				record.setEND_DATE(mdTime);
				int updateApprItem = workDao.updateByPrimaryKey(record);

				if (updateApprItem != 0) {
					siteEmailService.sendWorkApplyFinishMail(record.getWORK_ID());
				} else {
					log.error("update fail updateApprItem count=" + updateApprItem + " workId =" + record.getWORK_ID());
					throw new UpdateFailException("update fail");
				}
			} else {
				log.error("SiteWork is null ,workId = " + workId);
				throw new NomsException("更新狀態失敗");
			}

			// 呼叫物料安裝完工API(INVService.completion)
			orderExample.clear();
			orderExample.createCriteria().andWORK_IDEqualTo(workId).andIS_ACTIVEEqualTo("Y");
			List<TbSiteWorkOrder> allFinishOrders = workOrderDao.findByConditions(orderExample);
			TbSiteOutsourcingExample example = new TbSiteOutsourcingExample();
			for (TbSiteWorkOrder order : allFinishOrders) {
				example.clear();
				example.createCriteria().andORDER_IDEqualTo(order.getORDER_ID()).andSTATUSEqualTo(OutSourceStatus.OS07.name());
				List<TbSiteOutsourcing> outs = outsourcingDao.findSiteOutSoureByExample(example);
				if (!outs.isEmpty()) {
					for (TbSiteOutsourcing out : outs) {
						boolean isSuccess = invService.completion(work.getOS_TYPE(), out.getOS_ID(), work.getLOCATION_ID(), work.getSITE_ID(),
								getLoginUser().getUsername(), null, out.getAP_DATE(), out.getAP_AMOUNT(), null, out.getOS_ID());
						if (!isSuccess)
							log.info("物料完工API：osID=" + out.getOS_ID() + ", 呼叫驗收失敗！");
					}
				}
			}
		}

		log.debug("全部工單完工？" + orderList.isEmpty());
		return orderList.isEmpty();
	}

	private boolean toNewMaterialOptionOrder(TbSiteWork work, TbSiteWorkOrder order) {
		boolean allMaterialHandled = true; // 先預設所有物料皆已處理完畢
		String hasMtOptOrderType = "SC,SCH,SCN";
		if (StringUtils.contains(hasMtOptOrderType, order.getORDER_TYPE())) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("orderId", order.getORDER_ID());
			List<MeterialRtnDTO> list = meterialOptDao.selectRemInsItemQuery(map);
			for (MeterialRtnDTO model : list) {
				if (model.getAssetQty() > 0) { // 若尚有一筆未處理完的在途物料
					allMaterialHandled = false;
					log.debug("****土木工單尚有在途料件**** orderId=" + order.getORDER_ID());
					break;
				}
			}
		}
		if (!allMaterialHandled) {
			// 若有在途物料，新增一筆物料處理工單，作業順序為99
			List<OrderTypeDTO> orderList = orderTypeDao.findOrderTypeByWorkTypeId(work.getWORK_TYPE(), 99);
			if (!orderList.isEmpty()) {
				TownDomainTeamDTO domainTeam = townDomainTeamDao.getTownDomainTeamByCityIdTownId(work.getCITY(), work.getAREA());
				for (OrderTypeDTO orderType : orderList) {
					TbSiteWorkOrder siteWorkOrder = new TbSiteWorkOrder();
					siteWorkOrder.setORDER_ID(uniqueSeqService.getNextOrderId(work.getWORK_ID()));
					siteWorkOrder.setWORK_ID(work.getWORK_ID());
					siteWorkOrder.setODR_STATUS(OrderStatus.OR03.name());
					siteWorkOrder.setPRIORITY(work.getPRIORITY());
					siteWorkOrder.setODR_SEQ(orderType.getOrderSeq());
					siteWorkOrder.setORDER_TYPE(orderType.getOD_TYPE_ID());
					siteWorkOrder.setIS_ACTIVE("Y");
					TbOrgDept dept = orderTypeService.getOrderTypeForWorkArea(orderType.getDEPT_KEY(), domainTeam);
					siteWorkOrder.setREP_DEPT(dept.getDEPT_ID());

					siteWorkOrder.setCR_USER(work.getAPL_USER());
					siteWorkOrder.setCR_TIME(new Date());
					siteWorkOrder.setMD_USER(work.getAPL_USER());
					siteWorkOrder.setMD_TIME(new Date());
					workOrderDao.insert(siteWorkOrder);
				}
			}
		}
		return !allMaterialHandled;
	}

	/**
	 * 包裝Location主檔
	 * @param siteLocTemp
	 * @return
	 * @author Charlie Woo
	 */
	private TbSiteLocation getLocationModel(TbSiteWorkLocTemp siteLocTemp) {
		TbSiteLocation tbSiteLocation = new TbSiteLocation();
		// siteWorkLocTemp.setWORK_ID(siteWork.getWORK_ID());
		tbSiteLocation.setLOCATION_ID(siteLocTemp.getLOCATION_ID());
		tbSiteLocation.setLOC_NAME(siteLocTemp.getLOC_NAME());
		tbSiteLocation.setLOC_TYPE(siteLocTemp.getLOC_TYPE());
		tbSiteLocation.setLON(siteLocTemp.getLON());
		tbSiteLocation.setLAT(siteLocTemp.getLAT());
		tbSiteLocation.setMAINT_AREA(siteLocTemp.getMAINT_AREA());
		tbSiteLocation.setMAINT_DEPT(siteLocTemp.getMAINT_DEPT());
		tbSiteLocation.setMAINT_TEAM(siteLocTemp.getMAINT_TEAM());
		tbSiteLocation.setMAINT_USER(siteLocTemp.getMAINT_PSN());
//		tbSiteLocation.setCNT_PSN(siteLocTemp.getc);
		tbSiteLocation.setLOC_DESC(siteLocTemp.getLOC_DESC());
		tbSiteLocation.setBASE_TYPE(siteLocTemp.getBASE_TYPE());
		tbSiteLocation.setINDOOR_OPTION(siteLocTemp.getINDOOR_OPTION());
		tbSiteLocation.setNS_LEVEL(siteLocTemp.getNS_LEVEL());
		tbSiteLocation.setBUILDING_HEIGHT(siteLocTemp.getBUILDING_HEIGHT());
		tbSiteLocation.setSHARE_TYPE(siteLocTemp.getSHARE_TYPE());
		// siteWorkLocTemp.setSHARE_COM(siteLoc.getSHARE_COM());
		tbSiteLocation.setIS_FREEENTER(siteLocTemp.getIS_FREEENTER());
		tbSiteLocation.setIS_KEY(siteLocTemp.getIS_KEY());
		tbSiteLocation.setIS_INDOOR(siteLocTemp.getIS_INDOOR());
		tbSiteLocation.setNEED_MISC_LIC(siteLocTemp.getNEED_MISC_LIC());
		tbSiteLocation.setHAS_MISC_LIC(siteLocTemp.getHAS_MISC_LIC());
		tbSiteLocation.setADDR(siteLocTemp.getADDR());
		tbSiteLocation.setADD_OTHER(siteLocTemp.getADD_OTHER());
		tbSiteLocation.setZIP(siteLocTemp.getZIP());
		tbSiteLocation.setCITY(siteLocTemp.getCITY());
		tbSiteLocation.setAREA(siteLocTemp.getAREA());
		tbSiteLocation.setVILLAGE(siteLocTemp.getVILLAGE());
		tbSiteLocation.setADJACENT(siteLocTemp.getADJACENT());
		tbSiteLocation.setROAD(siteLocTemp.getROAD());
		tbSiteLocation.setLANE(siteLocTemp.getLANE());
		tbSiteLocation.setALLEY(siteLocTemp.getALLEY());
		tbSiteLocation.setSUB_ALLEY(siteLocTemp.getSUB_ALLEY());
		tbSiteLocation.setDOOR_NO(siteLocTemp.getDOOR_NO());
		tbSiteLocation.setDOOR_NO_EX(siteLocTemp.getDOOR_NO_EX());
		tbSiteLocation.setFLOOR(siteLocTemp.getFLOOR());
		tbSiteLocation.setFLOOR_EX(siteLocTemp.getFLOOR_EX());
		tbSiteLocation.setROOM(siteLocTemp.getROOM());
		tbSiteLocation.setADD_OTHER(siteLocTemp.getADD_OTHER());
		tbSiteLocation.setSPACE_ROOM(siteLocTemp.getSPACE_ROOM());
		tbSiteLocation.setSPACE_ROOF(siteLocTemp.getSPACE_ROOF());
		tbSiteLocation.setSPACE_TOP(siteLocTemp.getSPACE_TOP());
		tbSiteLocation.setSPACE_PLATFORM(siteLocTemp.getSPACE_PLATFORM());
		tbSiteLocation.setSPACE_LAND(siteLocTemp.getSPACE_LAND());
		tbSiteLocation.setSPACE_INDOOR(siteLocTemp.getSPACE_INDOOR());
		tbSiteLocation.setSPACE_OUTDOOR(siteLocTemp.getSPACE_OUTDOOR());
		tbSiteLocation.setFOOTAGE(siteLocTemp.getFOOTAGE());
		tbSiteLocation.setEMP_RELATION(siteLocTemp.getEMP_RELATION());
		return tbSiteLocation;
	}

	/**
	 * 包裝SiteMain主檔
	 * @param siteTemp
	 * @param siteWork
	 * @return
	 * @author Charlie Woo
	 */
	private TbSiteMain getSiteMainModel(TbSiteWorkSiteTemp siteTemp, TbSiteWork siteWork) {
		TbSiteMain main = siteMainDao.findByPk(siteWork.getSITE_ID());
		// siteWorkSiteTemp.setWORK_ID(siteWork.getWORK_ID());
		main.setSITE_ID(siteTemp.getSITE_ID());
		main.setFEQ_ID(siteTemp.getFEQ_ID());
		if (siteTemp.getSR_ID() == null) {
			main.setSR_ID("");
		} else {
			main.setSR_ID(siteTemp.getSR_ID());
		}
		main.setBTS_SITE_ID(siteTemp.getBTS_SITE_ID());
		main.setSITE_NAME(siteTemp.getSITE_NAME());
		main.setLOCATION_ID(StringUtils.trimToNull(siteTemp.getLOCATION_ID()));
		main.setPURPOSE(siteTemp.getPURPOSE());
		main.setEQP_TYPE_ID(StringUtils.trimToNull(siteTemp.getEQP_TYPE_ID()));
		main.setEQP_MODEL_ID(StringUtils.trimToNull(siteTemp.getEQP_MODEL_ID()));
		main.setCOVERAGE_TYPE(StringUtils.trimToNull(siteTemp.getCOVERAGE_TYPE()));
		main.setBTS_CONFIG(StringUtils.trimToNull(siteTemp.getBTS_CONFIG()));
		main.setDONOR_SITE(StringUtils.trimToNull(siteTemp.getDONOR_SITE()));
		main.setFEEDERLESS(StringUtils.trimToNull(siteTemp.getFEEDERLESS()));
		main.setMASTER_SITE(StringUtils.trimToNull(siteTemp.getMASTER_SITE()));
		main.setCLUSTER(StringUtils.trimToNull(siteTemp.getCLUSTER()));
		main.setCOVERAGE_IN_FLOOR(StringUtils.trimToNull(siteTemp.getCOVERAGE_IN_FLOOR()));
		main.setRNC_BTS(StringUtils.trimToNull(siteTemp.getRNC_BTS()));
		main.setNIOS_RPT_STATUS(StringUtils.trimToNull(siteTemp.getNIOS_RPT_STATUS()));
		main.setCELL_STATUS(StringUtils.trimToNull(siteTemp.getCELL_STATUS()));
		main.setOMCU_OBJ(StringUtils.trimToNull(siteTemp.getOMCU_OBJ()));
		main.setSPEED_UPLOAD(StringUtils.trimToNull(siteTemp.getSPEED_UPLOAD()));
		main.setSPEED_DOWNLOAD(StringUtils.trimToNull(siteTemp.getSPEED_DOWNLOAD()));
		main.setPOWER(StringUtils.trimToNull(siteTemp.getPOWER()));
		main.setCST_ID_CARD_NUM(StringUtils.trimToNull(siteTemp.getCST_ID_CARD_NUM()));
		main.setCST_MOBILE(StringUtils.trimToNull(siteTemp.getCST_MOBILE()));
		main.setCST_ID(StringUtils.trimToNull(siteTemp.getCST_ID()));
		main.setCOVERAGE_INDOOR(StringUtils.trimToNull(siteTemp.getCOVERAGE_INDOOR()));
		main.setHIDDEN(StringUtils.trimToNull(siteTemp.getHIDDEN()));
		main.setNOIS_ON_AIR(StringUtils.trimToNull(siteTemp.getNOIS_ON_AIR()));
		main.setL2_SWITCH(StringUtils.trimToNull(siteTemp.getL2_SWITCH()));
		return main;
	}

	/**
	 * 包裝天線組態主檔
	 * @param siteAntCfgTemp
	 * @return
	 * @author Charlie Woo
	 */
	private TbSiteAntCfg getSiteAntCfgModel(TbSiteWorkAntCfgTemp siteAntCfgTemp) {
		TbSiteAntCfg tbSiteWorkAntCfg = new TbSiteAntCfg();
		tbSiteWorkAntCfg.setSITE_ID(siteAntCfgTemp.getSITE_ID());
		tbSiteWorkAntCfg.setANT_NO(siteAntCfgTemp.getANT_NO());
		tbSiteWorkAntCfg.setCELL_ID(siteAntCfgTemp.getCELL_ID());
		tbSiteWorkAntCfg.setSECTOR_ID(siteAntCfgTemp.getSECTOR_ID());
		tbSiteWorkAntCfg.setP_CODE(siteAntCfgTemp.getP_CODE());
		tbSiteWorkAntCfg.setSEG_SOURCE(siteAntCfgTemp.getSEG_SOURCE());
		tbSiteWorkAntCfg.setANT_MODEL(siteAntCfgTemp.getANT_MODEL());
		tbSiteWorkAntCfg.setANT_HIGH(siteAntCfgTemp.getANT_HIGH());
		tbSiteWorkAntCfg.setANT_ORIENT(siteAntCfgTemp.getANT_ORIENT());
		tbSiteWorkAntCfg.setE_TILT(siteAntCfgTemp.getE_TILT());
		tbSiteWorkAntCfg.setM_TILT(siteAntCfgTemp.getM_TILT());
		tbSiteWorkAntCfg.setSETUP_STYLE(siteAntCfgTemp.getSETUP_STYLE());
		tbSiteWorkAntCfg.setCELL_RADIUS(siteAntCfgTemp.getCELL_RADIUS());
		tbSiteWorkAntCfg.setANT_SIZE(siteAntCfgTemp.getANT_SIZE());
		tbSiteWorkAntCfg.setANT_MFGR(siteAntCfgTemp.getANT_MFGR());
		tbSiteWorkAntCfg.setDOWNTILT_R(siteAntCfgTemp.getDOWNTILT_R());
		tbSiteWorkAntCfg.setFLOOR_LEVEL(siteAntCfgTemp.getFLOOR_LEVEL());
		tbSiteWorkAntCfg.setTOWER_HEIGHT(siteAntCfgTemp.getTOWER_HEIGHT());
		tbSiteWorkAntCfg.setPENTHOUSE_HEIGHT(siteAntCfgTemp.getPENTHOUSE_HEIGHT());
		tbSiteWorkAntCfg.setADDR(siteAntCfgTemp.getADDR());
		tbSiteWorkAntCfg.setLON(siteAntCfgTemp.getLON());
		tbSiteWorkAntCfg.setLAT(siteAntCfgTemp.getLAT());
		tbSiteWorkAntCfg.setF_CABLE_TYPE(siteAntCfgTemp.getF_CABLE_TYPE());
		tbSiteWorkAntCfg.setF_CABLE_LEN(siteAntCfgTemp.getF_CABLE_LEN());
		tbSiteWorkAntCfg.setJ_CABLE_LEN(siteAntCfgTemp.getJ_CABLE_LEN());
		tbSiteWorkAntCfg.setCOVER_REG(siteAntCfgTemp.getCOVER_REG());
		return tbSiteWorkAntCfg;
	}

	public List<TbSysOrderPage> selectByExample(TbSysOrderPageExample example) {
		return outsourcingDao.selectByExample(example);
	}

	public TbSiteWorkOrder getOrderByKey(String orderId) {
		return workOrderDao.findOrderByPk(orderId);
	}

	public int updateSiteWorkOrder(TbSiteWorkOrder record) {
		return workOrderDao.update(record);
	}

	public List<TbSiteWorkOrder> getOrderByCondition(TbSiteWorkOrderExample example) {
		return workOrderDao.findByConditions(example);
	}

	/**
	 * 用workId和isActive查詢SiteWorkOrder
	 * 
	 * @param workId
	 * @param isActive
	 * @return
	 */
	public List<SiteWorkOrderDTO> getSiteWorkOrderByWorkIdAndIsActive(String workId, String isActive) {
		return dao.findSiteWorkOrderByWorkIdAndIsActive(workId, isActive);
	}

}
