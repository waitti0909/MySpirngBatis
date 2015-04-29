package com.foya.noms.service.st;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hornetq.utils.json.JSONArray;
import org.hornetq.utils.json.JSONException;
import org.hornetq.utils.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbComTownDomainTeam;
import com.foya.dao.mybatis.model.TbComTownDomainTeamExample;
import com.foya.dao.mybatis.model.TbInvBooking;
import com.foya.dao.mybatis.model.TbInvBookingExample;
import com.foya.dao.mybatis.model.TbInvInsRemD;
import com.foya.dao.mybatis.model.TbInvInv;
import com.foya.dao.mybatis.model.TbInvInvExample;
import com.foya.dao.mybatis.model.TbInvMaterialOpt;
import com.foya.dao.mybatis.model.TbInvMaterialOptExample;
import com.foya.dao.mybatis.model.TbInvMaterialOptExample.Criteria;
import com.foya.dao.mybatis.model.TbInvMrD;
import com.foya.dao.mybatis.model.TbInvMrDExample;
import com.foya.dao.mybatis.model.TbInvRtD;
import com.foya.dao.mybatis.model.TbInvWarehouse;
import com.foya.dao.mybatis.model.TbInvWarehouseExample;
import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbSiteLocation;
import com.foya.dao.mybatis.model.TbSiteOutsourcing;
import com.foya.dao.mybatis.model.TbSiteWork;
import com.foya.dao.mybatis.model.TbSiteWorkOrder;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.dao.mybatis.model.TbSysLookupExample;
import com.foya.exception.NomsException;
import com.foya.noms.dao.auth.PersonnelDao;
import com.foya.noms.dao.org.DeptDao;
import com.foya.noms.dao.st.MeterialOptDao;
import com.foya.noms.dto.inv.TbInvMaterialOptDTO;
import com.foya.noms.dto.st.MeterialRtnDTO;
import com.foya.noms.dto.st.SiteMtDetailImportDTO;
import com.foya.noms.enums.EmailType;
import com.foya.noms.enums.PurchaseOrderType;
import com.foya.noms.poi.Writer;
import com.foya.noms.resources.AppConstants;
import com.foya.noms.service.BaseService;
import com.foya.noms.service.common.EmailTemplateService;
import com.foya.noms.service.common.PoiService;
import com.foya.noms.service.inv.INVService;
import com.foya.noms.service.system.UniqueSeqService;
import com.foya.noms.service.workflow.WorkflowActionService;
import com.foya.workflow.exception.WorkflowException;

@Service
public class MeterialOptService extends BaseService {

	@Autowired
	private MeterialOptDao meterialOptDao;

	@Autowired
	private DeptDao deptDao;

	@Autowired
	private PersonnelDao personnelDao;

	@Autowired
	private UniqueSeqService uniqueSeqService;

	@Autowired
	private PoiService poiService;

	@Autowired
	private WorkflowActionService workflowActionService;

	@Autowired
	private EmailTemplateService emailTemplateService;

	@Autowired
	private INVService invService;

	public TbInvMaterialOpt getByPk(String optId) {
		TbInvMaterialOpt tbInvMaterialOpt = meterialOptDao.findByPk(optId);
		if (tbInvMaterialOpt == null) {
			return new TbInvMaterialOpt();
		}
		return tbInvMaterialOpt;
	}

	/**
	 * 使用工單編號查詢物料操作單資料
	 * 
	 * @param orderId 工單編號
	 * @return
	 */
	public List<TbInvMaterialOpt> getInvMaterialOptByOrderId(String orderId) {
		TbInvMaterialOptExample example = new TbInvMaterialOptExample();
		example.createCriteria().andORDER_IDEqualTo(orderId);
		return meterialOptDao.findInvMaterialOptByOrderId(example);
	}

	public List<TbInvMaterialOptDTO> selectMaterialOptByOsId(String osId) {
		TbInvMaterialOptDTO tbInvMaterialOptDto = new TbInvMaterialOptDTO();
		tbInvMaterialOptDto.setOS_ID(osId);
		return meterialOptDao.selectMaterialOptByCondistions(tbInvMaterialOptDto);
	}

	public TbInvMaterialOptDTO selectForExportMaterialExeclTitle(String orderId) {
		TbInvMaterialOptDTO tbInvMaterialOptDto = new TbInvMaterialOptDTO();
		tbInvMaterialOptDto.setORDER_ID(orderId);
		List<TbInvMaterialOptDTO> list = meterialOptDao
				.selectForExportMaterialExeclTitle(tbInvMaterialOptDto);
		if (list == null || list.isEmpty()) {
			return new TbInvMaterialOptDTO();
		}
		return list.get(0);
	}

	/**
	 * 使用申請單編號查詢物料操作單資料
	 * 
	 * @param orderId 工單編號
	 * @return
	 */
	// public List<TbInvMaterialOpt> getInvMaterialOptById(String optId){
	// TbInvMaterialOptExample example= new TbInvMaterialOptExample();
	// example.createCriteria().andOPT_IDEqualTo(optId);
	// return meterialOptDao.findInvMaterialOptByOrderId(example);
	// }

	/**
	 * 使用工單編號查詢物料操作單資料
	 * 
	 * @param osId 工單編號
	 * @return
	 */
	public List<TbInvWarehouse> getInvWareHouse(String orderId, String osId) {

		List<TbInvWarehouse> list = null;
		TbInvWarehouseExample warehouseExample = new TbInvWarehouseExample();

		TbSiteWorkOrder workOrder = meterialOptDao.selectWorkorder(orderId);
		TbSiteWork siteWork = meterialOptDao.selectSiteWork(workOrder.getWORK_ID());
		TbSiteLocation siteLocation = meterialOptDao.selectSiteLocation(siteWork.getLOCATION_ID());
		TbSiteOutsourcing  siteOutsourcing = meterialOptDao.selectByPrimaryKey(osId);
		
		if(siteLocation == null){
			return new ArrayList<TbInvWarehouse>();
		}
		
		TbComTownDomainTeamExample example=new TbComTownDomainTeamExample();
		example.createCriteria().andCITY_IDEqualTo(siteLocation.getCITY()).andTOWN_IDEqualTo(siteLocation.getAREA());
		List<TbComTownDomainTeam> tbComTownDomainTeamList=meterialOptDao.selectTbComTownDomainTeam(example);
		String domain =  StringUtils.trimToEmpty(tbComTownDomainTeamList.get(0).getDOMAIN());
		domain = StringUtils.substring(domain, 0 , 1);
		
		
		com.foya.dao.mybatis.model.TbInvWarehouseExample.Criteria criteria = warehouseExample.createCriteria();
		
		List<String> domainList = new ArrayList<String>();
		domainList.add(domain);
		domainList.add("HQ");
		criteria.andDomainIn(domainList);
		
		if(StringUtils.equals(orderId, osId) ){ //未有委外工單時
			criteria.andWh_attributeEqualTo("A"); //實體倉
			criteria.andWh_typeEqualTo("C");//公司
			list=meterialOptDao.selectWarehouse(warehouseExample); //一般(非委外):公司實體倉
		} else if(PurchaseOrderType.P.name().equals(siteWork.getOS_TYPE())){
			criteria.andWh_attributeEqualTo("A"); // 實體倉
			criteria.andCo_vat_noEqualTo(siteOutsourcing.getCO_VAT_NO()); //NOMS-956 1.建議廠商領料只能下載新進物料區和自家庫房領料單
			list=meterialOptDao.selectWarehouse(warehouseExample); //一般(委外):實體倉
		}else if(PurchaseOrderType.T.name().equals(siteWork.getOS_TYPE())){
			criteria.andWh_attributeEqualTo("T"); //暫收倉
			criteria.andWh_typeEqualTo("V"); //廠商
			criteria.andCo_vat_noEqualTo(siteOutsourcing.getCO_VAT_NO()); //NOMS-956 1.建議廠商領料只能下載新進物料區和自家庫房領料單
			list=meterialOptDao.selectWarehouse(warehouseExample); //統包(委外):暫收倉
		}
		return list;

	}


	public TbSiteWork getbSiteWork(String orderId) {
		TbSiteWorkOrder workOrder = meterialOptDao.selectWorkorder(orderId);
		TbSiteWork siteWork = meterialOptDao.selectSiteWork(workOrder.getWORK_ID());
		return siteWork;
	}

	/**
	 * 查詢物料操作單明細資料
	 * 
	 * @param Example
	 * @return
	 */
	public List<SiteMtDetailImportDTO> getItemMainQuery(Map<String, String> map) {
		return meterialOptDao.selectItemMainById(map);
	}

	/**
	 * 查詢安裝物料操作單-安裝&退料在途查詢
	 * 
	 * @param Example
	 * @return
	 */
	public List<MeterialRtnDTO> getRemInsItemQuery(HashMap<String, Object> map) {
		return meterialOptDao.selectRemInsItemQuery(map);
	}

	/**
	 * 查詢拆除物料操作單-站上數量查詢
	 * 
	 * @param Example
	 * @return
	 */
	public List<MeterialRtnDTO> getRemItemQuery(HashMap<String, Object> map) {
		return meterialOptDao.selectRemItemQuery(map);
	}

	/**
	 * 查詢退料物料操作單明細資料
	 * 
	 * @param Example
	 * @return
	 */
	public List<MeterialRtnDTO> getRtntrItemByOptId(HashMap<String, Object> map) {
		return meterialOptDao.selectRtntrItemByOptId(map);
	}

	/**
	 * 查詢安裝物料操作單明細資料
	 * 
	 * @param Example
	 * @return
	 */
	public List<MeterialRtnDTO> getRemInsItemByOptId(HashMap<String, Object> map) {
		return meterialOptDao.selectRemInsItemByOptId(map);
	}

	/**
	 * 查詢拆除物料操作單明細資料
	 * 
	 * @param Example
	 * @return
	 */
	public List<MeterialRtnDTO> getRemItemByOptId(HashMap<String, Object> map) {
		return meterialOptDao.selectRemItemByOptId(map);
	}

	/**
	 * 物料操作-領料申請(與工單物料轉出共用)
	 * @param optId
	 * @param optType
	 * @param orderId
	 * @param osId
	 * @param whId
	 * @param reqDate
	 * @param optDesc
	 * @param jsonArrayStr
	 * @return
	 * @throws NomsException
	 */
	@Transactional
	public TbInvMaterialOpt mtApply(String optId, String optType, String orderId,
			String osId, String whId, Date reqDate, String optDesc, String status,
			String jsonArrayStr) throws NomsException {
		
		TbInvMaterialOpt tbInvMaterialOpt = null;
		log.info("=====> 領料申請  <=======");
		String crUser = this.getLoginUser().getUsername();
		Date date = new Date();
		TbSiteWorkOrder workOrder = meterialOptDao.selectWorkorder(orderId);
		TbSiteWork siteWork = meterialOptDao.selectSiteWork(workOrder.getWORK_ID());
		TbOrgDept tbOrgDept = meterialOptDao.selectDeptById(this.getLoginUser().getDeptId());

		try {
			if (StringUtils.isNotBlank(optId) && !StringUtils.equalsIgnoreCase("MR02", status)) {  //配合工單物料轉出，過濾 MR02(已核可)
				log.info("將已存在的 optId = " + optId + " =====> 重新送 領料申請  <=======");
				/** Update TB_INV_MATERIAL_OPT **/
				tbInvMaterialOpt = meterialOptDao.findByPk(optId);
				tbInvMaterialOpt.setMD_TIME(new Date());
				tbInvMaterialOpt.setMD_USER(getLoginUser().getUsername());
				tbInvMaterialOpt.setSTATUS(status);
				meterialOptDao.updateByPrimaryKeySelective(tbInvMaterialOpt);

				TbInvMrDExample example = new TbInvMrDExample();
				example.createCriteria().andOPT_IDEqualTo(optId);
				meterialOptDao.deleteByExample(example);
			} else {
				tbInvMaterialOpt = new TbInvMaterialOpt();
				optId = uniqueSeqService.getNextMaterialOptId(orderId);

				/** Insert TB_INV_MATERIAL_OPT 操作單主檔 **/
				tbInvMaterialOpt.setOPT_ID(optId);
				tbInvMaterialOpt.setOPT_TYPE(optType);
				tbInvMaterialOpt.setSITE_ID(siteWork.getSITE_ID());
				tbInvMaterialOpt.setORDER_ID(orderId);
				tbInvMaterialOpt.setWORK_TYPE(siteWork.getWORK_TYPE());
				tbInvMaterialOpt.setAPP_USER(crUser);
				tbInvMaterialOpt.setAPP_TIME(date);
				tbInvMaterialOpt.setSTATUS(status);
				tbInvMaterialOpt.setOPT_DESC(optDesc);
				tbInvMaterialOpt.setWH_ID(whId);
				tbInvMaterialOpt.setOS_ID(osId);
				tbInvMaterialOpt.setREQ_DATE(reqDate);
				tbInvMaterialOpt.setCR_USER(crUser);
				tbInvMaterialOpt.setCR_TIME(date);
				tbInvMaterialOpt.setMD_USER(crUser);
				tbInvMaterialOpt.setMD_TIME(date);
				meterialOptDao.insert(tbInvMaterialOpt);
			}
			/** Insert TB_INV_MD_R 領料單明細 **/
			JSONArray jsonArray = new JSONArray(jsonArrayStr);
			JSONObject jsonObject = null;
			for (int i = 0; i < jsonArray.length(); i++) {
				// log.info("start....===== > 第 " + (i+1) + " 筆 物料 <======");
				jsonObject = jsonArray.getJSONObject(i);
				// log.info(jsonObject.toString(i));

				int qty = jsonObject.optInt("qty");
				String matNo = jsonObject.optString("matNo");
				if (StringUtils.equalsIgnoreCase("S",jsonObject.optString("ctrlType"))) // 若為序號件料
				{
					TbInvInvExample example = new TbInvInvExample();
					example.createCriteria().andWh_idEqualTo(whId)
							.andMat_noEqualTo(matNo);
					List<TbInvInv> tbInvInvList = meterialOptDao.selectInv(example);

					if (qty > 1) {
						for (int j = 0; j < qty; j++) {
							TbInvMrD tbInvMrD = new TbInvMrD();
							tbInvMrD.setOPT_ID(optId);
							tbInvMrD.setMAT_NO(matNo);
							tbInvMrD.setQTY(1);
							if (tbInvInvList.size() > 0) {
								if (tbInvInvList.get(0).getStd_qty() < qty) {
									tbInvMrD.setLACK_QTY(qty - tbInvInvList.get(0).getStd_qty());
								}
							} else {
								tbInvMrD.setLACK_QTY(qty);
							}
							tbInvMrD.setCR_TIME(date);
							tbInvMrD.setCR_USER(crUser);
							tbInvMrD.setMD_USER(crUser);
							tbInvMrD.setMD_TIME(date);
							meterialOptDao.insert(tbInvMrD);
						}
					} else {
						TbInvMrD tbInvMrD = new TbInvMrD();
						tbInvMrD.setOPT_ID(optId);
						tbInvMrD.setMAT_NO(matNo);
						tbInvMrD.setQTY(qty);
						if (tbInvInvList.size() > 0) {
							if (tbInvInvList.get(0).getStd_qty() < qty) {
								tbInvMrD.setLACK_QTY(qty - tbInvInvList.get(0).getStd_qty());
							}
						}
						tbInvMrD.setCR_TIME(date);
						tbInvMrD.setCR_USER(crUser);
						tbInvMrD.setMD_USER(crUser);
						tbInvMrD.setMD_TIME(date);
						meterialOptDao.insert(tbInvMrD);
					}
				} else { // 非序號件料
					TbInvMrD tbInvMrD = new TbInvMrD();
					tbInvMrD.setOPT_ID(optId);
					tbInvMrD.setMAT_NO(matNo);
					tbInvMrD.setQTY(qty);
					tbInvMrD.setCR_TIME(date);
					tbInvMrD.setCR_USER(crUser);
					tbInvMrD.setMD_USER(crUser);
					tbInvMrD.setMD_TIME(date);
					meterialOptDao.insert(tbInvMrD);
				}

				/** Insert TB_INV_BOOKING Booking歷程檔 **/
				TbInvBooking tbInvBooking = new TbInvBooking();
				tbInvBooking.setAct_no(optId);
				tbInvBooking.setAct_type(Byte.parseByte("1"));
				tbInvBooking.setBooking_qty(qty);
				tbInvBooking.setSite_id(siteWork.getSITE_ID());
				tbInvBooking.setOrder_no(orderId);

				if (tbOrgDept != null) {
					tbInvBooking.setDomain(tbOrgDept.getDOMAIN());
				}
				tbInvBooking.setDept_id(this.getLoginUser().getDeptId());
				tbInvBooking.setWh_id(whId);
				tbInvBooking.setMat_no(matNo);
				tbInvBooking.setCr_user(crUser);
				tbInvBooking.setCr_time(date);
				tbInvBooking.setMd_user(crUser);
				tbInvBooking.setMd_time(date);
				meterialOptDao.insert(tbInvBooking);

				// 更新物料主檔
				HashMap<String, Object> data = new HashMap<String, Object>();
				data.put("bookingQty", qty);
				data.put("mdUser", crUser);
				data.put("mdTime", date);
				data.put("whId", whId);
				data.put("matNO", matNo);
				meterialOptDao.updateAppInv(data);
				// log.info("end....===== > 第 " + (i+1) + " 筆 物料 <======");
			}
			
			if(StringUtils.equals("MR01", status)){ //狀態(status)為領料申請審核時
				Map<String, Object> emailVars = emailTemplateService.getMailVarMapForWorkflow(
						EmailType.WORKFLOW_TODO_WORK_MATERIAL_APPLY, optId);
				// 呼叫領料申請workFlow
				workflowActionService.apply("SiteBuildMaterialApply", optId, "領料申請", emailVars);
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new NomsException(e.getMessage());
		}

		return tbInvMaterialOpt;
	}

	
	public void mtApply(TbInvMaterialOpt tbInvMaterialOpt, JSONArray jsonArray)
			throws JSONException, WorkflowException {

		TbOrgDept tbOrgDept = meterialOptDao.selectDeptById(this.getLoginUser().getDeptId());
		meterialOptDao.insert(tbInvMaterialOpt);
		JSONObject obj = null;

		for (int i = 0; i < jsonArray.length(); i++) {
			obj = jsonArray.getJSONObject(i);
			TbInvInvExample example = new TbInvInvExample();
			example.createCriteria().andWh_idEqualTo(tbInvMaterialOpt.getWH_ID())
					.andMat_noEqualTo(obj.optString("matNo"));
			List<TbInvInv> tbInvInvList = meterialOptDao.selectInv(example);
			int qty = obj.optInt("qty");

			if (qty > 1) {
				for (int j = 0; j < qty; j++) {
					TbInvMrD tbInvMrD = new TbInvMrD();
					tbInvMrD.setOPT_ID(tbInvMaterialOpt.getOPT_ID());
					tbInvMrD.setMAT_NO(obj.optString("matNo"));
					tbInvMrD.setQTY(1);
					if (tbInvInvList.size() > 0) {
						if (tbInvInvList.get(0).getStd_qty() < qty) {
							tbInvMrD.setLACK_QTY(qty - tbInvInvList.get(0).getStd_qty());
						}
					} else {
						tbInvMrD.setLACK_QTY(qty);
					}
					tbInvMrD.setCR_TIME(tbInvMaterialOpt.getCR_TIME());
					tbInvMrD.setCR_USER(tbInvMaterialOpt.getCR_USER());
					tbInvMrD.setMD_USER(tbInvMaterialOpt.getMD_USER());
					tbInvMrD.setMD_TIME(tbInvMaterialOpt.getMD_TIME());

					meterialOptDao.insert(tbInvMrD);
				}
			} else {
				TbInvMrD tbInvMrD = new TbInvMrD();
				tbInvMrD.setOPT_ID(tbInvMaterialOpt.getOPT_ID());
				tbInvMrD.setMAT_NO(obj.optString("matNo"));
				tbInvMrD.setQTY(qty);
				if (tbInvInvList.size() > 0) {
					if (tbInvInvList.get(0).getStd_qty() < qty) {
						tbInvMrD.setLACK_QTY(qty - tbInvInvList.get(0).getStd_qty());
					}
				}
				tbInvMrD.setCR_TIME(tbInvMaterialOpt.getCR_TIME());
				tbInvMrD.setCR_USER(tbInvMaterialOpt.getCR_USER());
				tbInvMrD.setMD_USER(tbInvMaterialOpt.getMD_USER());
				tbInvMrD.setMD_TIME(tbInvMaterialOpt.getMD_TIME());

				meterialOptDao.insert(tbInvMrD);
			}

			TbInvBooking tbInvBooking = new TbInvBooking();
			tbInvBooking.setAct_no(tbInvMaterialOpt.getOPT_ID());
			tbInvBooking.setAct_type(Byte.parseByte("1"));
			tbInvBooking.setBooking_qty(qty);
			tbInvBooking.setSite_id(tbInvMaterialOpt.getSITE_ID());
			tbInvBooking.setOrder_no(tbInvMaterialOpt.getORDER_ID());

			if (tbOrgDept != null) {
				tbInvBooking.setDomain(tbOrgDept.getDOMAIN());
			}

			tbInvBooking.setDept_id(getLoginUser().getDeptId());
			tbInvBooking.setWh_id(tbInvMaterialOpt.getWH_ID());
			tbInvBooking.setMat_no(obj.optString("matNo"));
			tbInvBooking.setCr_user(tbInvMaterialOpt.getCR_USER());
			tbInvBooking.setCr_time(tbInvMaterialOpt.getCR_TIME());
			tbInvBooking.setMd_user(tbInvMaterialOpt.getMD_USER());
			tbInvBooking.setMd_time(tbInvMaterialOpt.getMD_TIME());
			meterialOptDao.insert(tbInvBooking);

			// 更新物料主檔
			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("bookingQty", qty);
			data.put("mdUser", tbInvMaterialOpt.getMD_USER());
			data.put("mdTime", tbInvMaterialOpt.getMD_TIME());
			data.put("whId", tbInvMaterialOpt.getWH_ID());
			data.put("matNO", obj.optString("matNo"));

			meterialOptDao.updateAppInv(data);
		}
		// workflowActionService.apply("SiteBuildMaterialApply", tbInvMaterialOpt.getOPT_ID(),
		// "領料申請");
	}

	/**
	 * 物料操作-安裝
	 * @param optType
	 * @param orderId
	 * @param osId
	 * @param reqDate
	 * @param optDesc
	 * @param jsonArrayString
	 * @return
	 * @throws JSONException 
	 * @throws NomsException
	 */
	@Transactional
	public TbInvMaterialOpt mtSetup(String optType, String orderId, String osId, Date reqDate, String optDesc,
			String jsonArrayString) throws NomsException, JSONException {
		
		TbInvMaterialOpt tbInvMaterialOpt = new TbInvMaterialOpt();
		String optId = uniqueSeqService.getNextMaterialOptId(orderId);
		TbSiteWorkOrder workOrder = meterialOptDao.selectWorkorder(orderId);
		TbSiteWork siteWork = meterialOptDao.selectSiteWork(workOrder.getWORK_ID());
		String crUser = this.getLoginUser().getUsername();
		Date date = new Date();

		// 申請主檔
		tbInvMaterialOpt.setOPT_ID(optId);
		tbInvMaterialOpt.setOPT_TYPE(optType);
		tbInvMaterialOpt.setSITE_ID(siteWork.getSITE_ID());
		tbInvMaterialOpt.setORDER_ID(orderId);
		tbInvMaterialOpt.setWORK_TYPE(siteWork.getWORK_TYPE());
		tbInvMaterialOpt.setAPP_USER(crUser);
		tbInvMaterialOpt.setAPP_TIME(date);
		tbInvMaterialOpt.setOPT_DESC(optDesc);
		tbInvMaterialOpt.setOS_ID(osId);
		tbInvMaterialOpt.setREQ_DATE(reqDate);
		tbInvMaterialOpt.setCR_USER(crUser);
		tbInvMaterialOpt.setCR_TIME(date);
		tbInvMaterialOpt.setMD_USER(crUser);
		tbInvMaterialOpt.setMD_TIME(date);
		meterialOptDao.insert(tbInvMaterialOpt);
		
		JSONArray jsonArray = new JSONArray(jsonArrayString);
		JSONObject jsonObject  = null;
		
		String matNo = null;
		Long srlNo = 0L;
		Integer qty = 0;
		
		// 安裝明細檔
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			
			matNo = jsonObject.optString("matNo"); //料號
			srlNo = jsonObject.optLong("srlNo"); //序號
			qty = jsonObject.optInt("qty"); //安裝數
			
			TbInvInsRemD tbInvInsRemD = new TbInvInsRemD();
			tbInvInsRemD.setOPT_ID(optId);
			tbInvInsRemD.setMAT_NO(matNo);
			tbInvInsRemD.setSRL_NO(srlNo);
			tbInvInsRemD.setQTY(qty);
			tbInvInsRemD.setCR_USER(crUser);
			tbInvInsRemD.setCR_TIME(date);
			tbInvInsRemD.setMD_USER(crUser);
			tbInvInsRemD.setMD_TIME(date);
			meterialOptDao.insert(tbInvInsRemD);
			
			invService.setup(siteWork.getOS_TYPE(),
					tbInvMaterialOpt.getOPT_ID(), 
					siteWork.getLOCATION_ID(),
					tbInvMaterialOpt.getSITE_ID(),
					osId, 
					matNo,  //料號
					qty,  //安裝數
					jsonObject.getString("faNo"), //資產編號
					srlNo,//序號
					crUser, crUser);
		}

		return tbInvMaterialOpt;
	}


	/**
	 * 物料操作-拆下
	 * @param optType
	 * @param orderId
	 * @param osId
	 * @param reqDate
	 * @param optDesc
	 * @param jsonArrayString
	 * @return
	 * @throws NomsException
	 * @throws JSONException
	 */
	@Transactional
	public TbInvMaterialOpt mtUnload(String optType, String orderId, String osId, Date reqDate, String optDesc,
			String jsonArrayString) throws NomsException, JSONException {
		TbInvMaterialOpt tbInvMaterialOpt = new TbInvMaterialOpt();
		String optId = uniqueSeqService.getNextMaterialOptId(orderId);
		TbSiteWorkOrder workOrder = meterialOptDao.selectWorkorder(orderId);
		TbSiteWork siteWork = meterialOptDao.selectSiteWork(workOrder.getWORK_ID());
		String crUser = this.getLoginUser().getUsername();
		Date date = new Date();

		// 申請主檔
		tbInvMaterialOpt.setOPT_ID(optId);
		tbInvMaterialOpt.setOPT_TYPE(optType);
		tbInvMaterialOpt.setSITE_ID(siteWork.getSITE_ID());
		tbInvMaterialOpt.setORDER_ID(orderId);
		tbInvMaterialOpt.setWORK_TYPE(siteWork.getWORK_TYPE());
		tbInvMaterialOpt.setAPP_USER(crUser);
		tbInvMaterialOpt.setAPP_TIME(date);
		tbInvMaterialOpt.setOPT_DESC(optDesc);
		tbInvMaterialOpt.setOS_ID(osId);
		tbInvMaterialOpt.setREQ_DATE(reqDate);
		tbInvMaterialOpt.setCR_USER(crUser);
		tbInvMaterialOpt.setCR_TIME(date);
		tbInvMaterialOpt.setMD_USER(crUser);
		tbInvMaterialOpt.setMD_TIME(date);
		meterialOptDao.insert(tbInvMaterialOpt);

		JSONArray jsonArray = new JSONArray(jsonArrayString);
		JSONObject jsonObject  = null;
		
		String matNo = null;
		Long srlNo = 0L;
		Integer qty = 0;
		
		// 拆除明細檔
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			matNo = jsonObject.optString("matNo"); //料號
			srlNo = jsonObject.optLong("srlNo"); //序號
			qty = jsonObject.optInt("qty"); //折下數
			
			TbInvInsRemD tbInvInsRemD = new TbInvInsRemD();
			tbInvInsRemD.setOPT_ID(optId);
			tbInvInsRemD.setMAT_NO(matNo);
			tbInvInsRemD.setSRL_NO(srlNo);
			tbInvInsRemD.setQTY(qty);
			tbInvInsRemD.setCR_USER(crUser);
			tbInvInsRemD.setCR_TIME(date);
			tbInvInsRemD.setMD_USER(crUser);
			tbInvInsRemD.setMD_TIME(date);
			meterialOptDao.insert(tbInvInsRemD);
			
			invService.unload(siteWork.getOS_TYPE(), tbInvMaterialOpt.getOPT_ID(),
					tbInvMaterialOpt.getSITE_ID(), osId, 
					matNo, //料號
					qty, //折下數
					jsonObject.getString("faNo"), //資產編號
					srlNo, //序號
					qty, //折下數
					crUser);
			
		}
		return tbInvMaterialOpt;
	}


	/**
	 * 物料操作-退料申請
	 * @param optType
	 * @param orderId
	 * @param osId
	 * @param whId
	 * @param reqDate
	 * @param optDesc
	 * @param jsonArrayString
	 * @return
	 * @throws JSONException
	 */
	@Transactional
	public TbInvMaterialOpt mtReturn(String optType, String orderId, String osId, String whId, String optDesc,
			String jsonArrayString) throws JSONException {
		// 寫入申請主檔
		TbInvMaterialOpt tbInvMaterialOpt = new TbInvMaterialOpt();
		String optId = uniqueSeqService.getNextMaterialOptId(orderId);
		TbSiteWorkOrder workOrder = meterialOptDao.selectWorkorder(orderId);
		TbSiteWork siteWork = meterialOptDao.selectSiteWork(workOrder.getWORK_ID());
		String crUser = this.getLoginUser().getUsername();
		Date date = new Date();

		tbInvMaterialOpt.setOPT_ID(optId);

		tbInvMaterialOpt.setOPT_TYPE(optType);
		tbInvMaterialOpt.setSITE_ID(siteWork.getSITE_ID());
		tbInvMaterialOpt.setORDER_ID(orderId);
		tbInvMaterialOpt.setWORK_TYPE(siteWork.getWORK_TYPE());
		tbInvMaterialOpt.setAPP_USER(crUser);
		tbInvMaterialOpt.setAPP_TIME(date);
		tbInvMaterialOpt.setSTATUS("RT01");
		tbInvMaterialOpt.setOPT_DESC(optDesc);
		tbInvMaterialOpt.setWH_ID(whId);
		tbInvMaterialOpt.setOS_ID(osId);
		tbInvMaterialOpt.setCR_USER(crUser);
		tbInvMaterialOpt.setCR_TIME(date);
		tbInvMaterialOpt.setMD_USER(crUser);
		tbInvMaterialOpt.setMD_TIME(date);
		meterialOptDao.insert(tbInvMaterialOpt);

		JSONArray jsonArray = new JSONArray(jsonArrayString);
		JSONObject jsonObject  = null;
		
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			
			TbInvRtD tbInvRtD = new TbInvRtD();
			tbInvRtD.setOPT_ID(optId);
			tbInvRtD.setMAT_NO(jsonObject.getString("matNo")); //料號
			tbInvRtD.setSRL_NO(jsonObject.optLong("srlNo")); //序號
			tbInvRtD.setQTY(jsonObject.optInt("qty")); //退料數
			tbInvRtD.setMAT_STATUS(jsonObject.optString("matStatus"));
			tbInvRtD.setRTN_REASON(jsonObject.optString("trnReason"));
			tbInvRtD.setCR_USER(crUser);
			tbInvRtD.setCR_TIME(date);
			tbInvRtD.setMD_USER(crUser);
			tbInvRtD.setMD_TIME(date);
			meterialOptDao.insert(tbInvRtD);
		}

		return tbInvMaterialOpt;
	}

	public List<SiteMtDetailImportDTO> selectMeterialApplyExcel(Map<String, String> data) {
		return meterialOptDao.selectMeterialApplyExcel(data);
	}

	public void exportExcel(HttpServletResponse response, String sheetName, String fileName,
			String tempFilePath, List<SiteMtDetailImportDTO> mDetailList,
			TbInvMaterialOptDTO tbInvMaterialOptDTO) {
		try {
			FileInputStream fs = new FileInputStream(tempFilePath);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			fs.close();
			HSSFSheet worksheet = wb.getSheetAt(0);
			// 建立報表的 標題、日期、欄位名稱
			buildExcel(worksheet, mDetailList, tbInvMaterialOptDTO);
			// // 輸出流
			Writer.write(poiService.setResponse(response, fileName), worksheet);
		} catch (Exception ex) {
			log.error(ex.toString());
		}

	}

	/**
	 * 轉換領料明細資料
	 * 
	 * @param datas
	 * @return
	 */
	public List<SiteMtDetailImportDTO> transform(String datas) {
		List<SiteMtDetailImportDTO> list = new ArrayList<SiteMtDetailImportDTO>();
		String[] tmp1 = datas.split("@");
		for (int i = 0; i < tmp1.length; i++) {
			String[] tmp2 = tmp1[i].split(",");
			SiteMtDetailImportDTO siteMtDetail = new SiteMtDetailImportDTO();
			for (int j = 0; j < tmp2.length; j++) {
				String[] tmp3 = tmp2[j].split(":");
				if (tmp3[0].equals("itemNo")) {
					siteMtDetail.setItemNo(tmp3[1].equals("null") ? "" : tmp3[1]);
				} else if (tmp3[0].equals("itemName")) {
					siteMtDetail.setMatName(tmp3[1].equals("null") ? "" : tmp3[1]);
				} else if (tmp3[0].equals("qty")) {
					siteMtDetail.setQty(tmp3[1].equals("null") ? null : Integer.valueOf(tmp3[1]));
				} else if (tmp3[0].equals("actQty")) {
					siteMtDetail
							.setActQty(tmp3[1].equals("null") ? null : Integer.valueOf(tmp3[1]));
				} else if (tmp3[0].equals("ctrlType")) {
					siteMtDetail.setCtrlType(tmp3[1].equals("null") ? "" : tmp3[1]);
				}
			}
			list.add(siteMtDetail);
		}
		return list;
	}


	public List<TbSysLookup> findMeterStatus(String type) {
		TbSysLookupExample example = new TbSysLookupExample();
		example.createCriteria().andTYPEEqualTo(type);
		return meterialOptDao.selectMeterStatus(example);
	}

	public TbOrgDept findOrgDeptById(String deptId) {
		return deptDao.selectOrgDeptById(deptId);
	}

	/**
	 * 建立欄位名稱與樣式
	 */
	private static void buildExcel(HSSFSheet worksheet, List<SiteMtDetailImportDTO> mDetailList,
			TbInvMaterialOptDTO tbInvMaterialOptDTO) {

		HSSFRow excelRow = worksheet.getRow(3);
		excelRow.getCell(2).setCellValue(tbInvMaterialOptDTO.getDEPT_NAME());// 負責單位
		excelRow.getCell(6).setCellValue(tbInvMaterialOptDTO.getCO_NAME());// 施工承包商
		
		excelRow = worksheet.getRow(4);
		excelRow.getCell(2).setCellValue(tbInvMaterialOptDTO.getWORK_TYPE_NAME());// 工務類別
		excelRow.getCell(6).setCellValue(tbInvMaterialOptDTO.getOS_ID()); // 派工單號
		
		excelRow = worksheet.getRow(5);
		excelRow.getCell(2).setCellValue(tbInvMaterialOptDTO.getBTS_SITE_ID());// SITE_ID
		excelRow.getCell(6).setCellValue(tbInvMaterialOptDTO.getADDR()); //Site address
		
		
		excelRow = worksheet.getRow(6);
		excelRow.getCell(2).setCellValue(tbInvMaterialOptDTO.getEQP_NAME());// 設備型態
		excelRow.getCell(6).setCellValue(tbInvMaterialOptDTO.getEQP_MODEL()); // 設備機型

		excelRow = worksheet.getRow(7);
		excelRow.getCell(2).setCellValue(
				tbInvMaterialOptDTO.getWH_NAME() + "(" + tbInvMaterialOptDTO.getWH_ID() + ")"); // 領料倉庫
		// 設定框線
		HSSFCellStyle hssfCellStyle = worksheet.getWorkbook().createCellStyle();
		short style = HSSFCellStyle.BORDER_THIN;
		hssfCellStyle.setBorderBottom(style);
		hssfCellStyle.setBorderLeft(style);
		hssfCellStyle.setBorderRight(style);
		hssfCellStyle.setBorderTop(style);

		// 寫入資料
		int rowIdx = 10;
		HSSFCell cell = null;
		for (int i = 0; i < mDetailList.size(); i++) {
			excelRow = worksheet.createRow(rowIdx);
			SiteMtDetailImportDTO SiteMtDetail = (SiteMtDetailImportDTO) mDetailList.get(i);
			cell = excelRow.createCell(0);
			cell.setCellStyle(hssfCellStyle);
			cell.setCellValue(i + 1);  //項次

			cell = excelRow.createCell(1);
			cell.setCellStyle(hssfCellStyle);
			cell.setCellValue(SiteMtDetail.getItemNo()); //料號

			cell = excelRow.createCell(2);
			cell.setCellValue(SiteMtDetail.getItemCatMain());//大項
			cell.setCellStyle(hssfCellStyle);

			cell = excelRow.createCell(3);
			cell.setCellValue(SiteMtDetail.getItemCatSub()); //中項
			cell.setCellStyle(hssfCellStyle);

			cell = excelRow.createCell(4);
			cell.setCellValue(SiteMtDetail.getItemDetail()); //小項
			cell.setCellStyle(hssfCellStyle);

			cell = excelRow.createCell(5);
			cell.setCellValue(SiteMtDetail.getMatName()); //品名
			cell.setCellStyle(hssfCellStyle);
			
			
			cell = excelRow.createCell(6);
			cell.setCellValue(SiteMtDetail.getUnit()); //單位
			cell.setCellStyle(hssfCellStyle);

			cell = excelRow.createCell(7);
			cell.setCellValue(SiteMtDetail.getCtrlType()); //序號件/非序號件
			cell.setCellStyle(hssfCellStyle);

			cell = excelRow.createCell(8);
			cell.setCellValue(SiteMtDetail.getTotalQty()); //庫存量
			cell.setCellStyle(hssfCellStyle);
			
			cell = excelRow.createCell(9);
			cell.setCellValue(SiteMtDetail.getQty()); //領料數量
			cell.setCellStyle(hssfCellStyle);
			rowIdx++;
		}
	}

	/**
	 * 領料申請簽核REST
	 * 
	 * @param optId
	 * @param action
	 * @throws NomsException
	 * @author Charlie Woo
	 */
	@Transactional
	public void finishMtApply(String optId, String action) throws NomsException {

		if (StringUtils.isNotEmpty(optId)) {
			Date currentTime = new Date();
			TbInvMaterialOpt materialOpt = meterialOptDao.findByPk(optId);

			if (StringUtils.equals(AppConstants.WORKFLOW_REST_APPROVAL, action)) { // 核可
				materialOpt.setSTATUS("MR02");

			} else { // 駁回
				materialOpt.setSTATUS("MR03");

				// 刪除BOOKING歷程檔
				TbInvBookingExample bookingExample = new TbInvBookingExample();
				bookingExample.createCriteria().andAct_noEqualTo(optId);
				meterialOptDao.delete(bookingExample);

				// 扣回INV庫存量
				HashMap<String, Object> data = new HashMap<String, Object>();
				TbInvMrDExample invMrdExample = new TbInvMrDExample();
				invMrdExample.createCriteria().andOPT_IDEqualTo(optId);
				List<TbInvMrD> items = meterialOptDao.selectItemsByExample(invMrdExample);
				for (TbInvMrD item : items) {
					data.put("bookingQty", item.getQTY());
					data.put("mdUser", materialOpt.getAPP_USER());
					data.put("mdTime", currentTime);
					data.put("whId", materialOpt.getWH_ID());
					data.put("matNO", item.getMAT_NO());
					meterialOptDao.updateRejInv(data);
				}
			}

			// Update TB_INV_MATERIAL_OPT
			materialOpt.setMD_TIME(new Date());
			materialOpt.setMD_USER(getLoginUser().getUsername());
			meterialOptDao.updateByPrimaryKeySelective(materialOpt);

		} else {
			throw new NomsException("No receipt OPT_ID key for rest url.");
		}
	}

	public TbInvWarehouse selectWarehouseByPrimaryKey(String whId) {
		return meterialOptDao.selectWarehouseByPrimaryKey(whId);
	}

	public TbInvMaterialOpt selectTbInvMaterialOptByOrderId(String orderId) {
		TbInvMaterialOptExample example = new TbInvMaterialOptExample();
		Criteria criteria = example.createCriteria();// .andORDER_IDEqualTo(orderId).;
		criteria.andORDER_IDEqualTo(orderId);
		example.or(criteria);
		criteria = example.createCriteria();
		criteria.andOS_IDEqualTo(orderId);
		example.or(criteria);
		List<TbInvMaterialOpt> list = getByConditions(example);
		if (list == null || list.isEmpty()) {
			return new TbInvMaterialOpt();
		}

		return list.get(0);
	}

	public List<TbInvMaterialOpt> getByConditions(TbInvMaterialOptExample example) {
		return meterialOptDao.selectByConditions(example);
	}
}
