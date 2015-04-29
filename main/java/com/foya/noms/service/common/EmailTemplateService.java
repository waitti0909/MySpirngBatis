package com.foya.noms.service.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.dao.mybatis.model.TbComCompany;
import com.foya.dao.mybatis.model.TbInvMaterialOpt;
import com.foya.dao.mybatis.model.TbSiteLineApply;
import com.foya.dao.mybatis.model.TbSiteOutsourcing;
import com.foya.dao.mybatis.model.TbSiteWork;
import com.foya.dao.mybatis.model.TbSiteWorkOrder;
import com.foya.dao.mybatis.model.TbSysEmailTemplate;
import com.foya.dao.mybatis.model.TbSysEmailTemplateExample;
import com.foya.dao.mybatis.model.TbSysLookupExample;
import com.foya.noms.dao.auth.PersonnelDao;
import com.foya.noms.dao.basic.CM001Dao;
import com.foya.noms.dao.common.EmailTemplateDao;
import com.foya.noms.dao.org.DeptDao;
import com.foya.noms.dao.st.MaterialOptDao;
import com.foya.noms.dao.st.OutsourcingDao;
import com.foya.noms.dao.st.WorkDao;
import com.foya.noms.dao.st.WorkOrderDao;
import com.foya.noms.dao.st.line.LineApplyDao;
import com.foya.noms.dao.system.LookupDao;
import com.foya.noms.dao.system.OrderTypeDao;
import com.foya.noms.enums.EmailType;
import com.foya.noms.enums.WorkType;
import com.foya.noms.service.BaseService;

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
 * <td>2015/4/7</td>
 * <td>Email Inform with WorkFlowApply Service</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 */
@Service
public class EmailTemplateService extends BaseService {

	@Autowired
	private EmailTemplateDao emailTemplateDao;
	@Autowired
	private OrderTypeDao orderTypeDao;
	@Autowired
	private WorkDao workDao;
	@Autowired
	private WorkOrderDao workOrderDao;
	@Autowired
	private DeptDao deptDao;
	@Autowired
	private PersonnelDao personnelDao;
	@Autowired
	private LineApplyDao lineApplyDao;
	@Autowired
	private LookupDao lookupDao;
	@Autowired
	private CM001Dao companyDao;
	@Autowired
	private OutsourcingDao outsourcingDao;
	@Autowired
	private MaterialOptDao materialOptDao;

	public TbSysEmailTemplate getEmailTemplate(String type) {
		if (StringUtils.isBlank(type)) {
			return null;
		}
		TbSysEmailTemplateExample example = new TbSysEmailTemplateExample();
		example.createCriteria().andEMAIL_TYPEEqualTo(type);
		List<TbSysEmailTemplate> list = emailTemplateDao.findByCondition(example);
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	public List<TbSysEmailTemplate> getEmailTemplates(Collection<String> types) {
		if (types == null) {
			return new ArrayList<>(0);
		}
		TbSysEmailTemplateExample example = new TbSysEmailTemplateExample();
		example.createCriteria().andEMAIL_TYPEIn(new ArrayList<>(types));
		return emailTemplateDao.findByCondition(example);
	}

	/**
	 * 取得簽核EMAIL所需要的變數集合
	 * 
	 * @param type
	 * @param docNo
	 * @return
	 * @author Charlie Woo
	 */
	public Map<String, Object> getMailVarMapForWorkflow(EmailType type, String docNo) {

		Map<String, Object> varMap = new HashMap<>();

		if (type == EmailType.WORKFLOW_TODO_WORK_APPLY) { // 作業申請待簽核通知
			TbSiteWork work = workDao.findByPk(docNo);
			varMap.put("workType", WorkType.detectLabel(work.getWORK_TYPE()));
			varMap.put("workId", docNo);
			varMap.put("appDept", deptDao.findByPk(work.getAPP_DEPT()).getDEPT_NAME());
			varMap.put("appUser", personnelDao.searchByPsnNo(work.getAPL_USER()).getCHN_NM());

		} else if (type == EmailType.WORKFLOW_TODO_WORK_COMPLETION_APPLY) { // 工單完工申請待簽核通知
			TbSiteWorkOrder order = workOrderDao.findOrderByPk(docNo);
			TbSiteWork work = workDao.findByPk(order.getWORK_ID());

			varMap.put("orderType", orderTypeDao.findByPk(order.getORDER_TYPE()).getOD_TYPE_NAME());
			varMap.put("workType", WorkType.detectLabel(work.getWORK_TYPE()));
			varMap.put("appDept", deptDao.findByPk(work.getAPP_DEPT()).getDEPT_NAME());
			varMap.put("appUser", personnelDao.searchByPsnNo(work.getAPL_USER()).getCHN_NM());
			varMap.put("orderId", order.getORDER_ID());

		} else if (type == EmailType.WORKFLOW_TODO_WORK_LEASE_LINE_APPLY) { // 專線申請待簽核通知
			TbSiteLineApply line = lineApplyDao.findByPk(docNo);
			TbSiteWorkOrder order = workOrderDao.findOrderByPk(line.getORDER_ID());
			TbSiteWork work = workDao.findByPk(order.getWORK_ID());
			TbSysLookupExample example = new TbSysLookupExample();
			example.createCriteria().andTYPEEqualTo("LINE_APPLY_TYPE")
					.andCODEEqualTo(line.getAPP_TYPE());

			varMap.put("workType", WorkType.detectLabel(work.getWORK_TYPE()));
			varMap.put("appId", docNo);
			varMap.put("appType", lookupDao.selectByExample(example).get(0).getNAME());
			varMap.put("appDept", deptDao.findByPk(line.getAPP_DEPT()).getDEPT_NAME());
			varMap.put("appUser", personnelDao.searchByPsnNo(line.getAPP_USER()).getCHN_NM());

		} else if (type == EmailType.WORKFLOW_TODO_WORK_MATERIAL_APPLY) { // 領料申請待簽核通知
			TbInvMaterialOpt opt = materialOptDao.findByPk(docNo);
			TbSiteWorkOrder order = workOrderDao.findOrderByPk(opt.getORDER_ID());
			TbSiteWork work = workDao.findByPk(order.getWORK_ID());
			TbSiteOutsourcing outsource = outsourcingDao.findByPrimaryKey(opt.getOS_ID());
			String coName = "";
			if(outsource != null){
				TbComCompany company = 	companyDao.selectByPrimaryKey(outsource.getCO_VAT_NO());
				coName = company == null ? "":company.getCO_NAME();
			}
			varMap.put("workType", WorkType.detectLabel(work.getWORK_TYPE()));
			varMap.put("osId", opt.getOS_ID());
			varMap.put("company", coName);
			varMap.put("optId", docNo);
			varMap.put("appDept", deptDao.findByPk(order.getREP_DEPT()).getDEPT_NAME());
			varMap.put("appUser", personnelDao.searchByPsnNo(opt.getAPP_USER()).getCHN_NM());

		} else if (type == EmailType.WORKFLOW_TODO_WORK_OUTSOURCING_ACCEPT // 委外派工驗收待簽核通知
				|| type == EmailType.WORKFLOW_TODO_WORK_OUTSOURCING_APPLY) { // 委外派工申請待簽核通知
			TbSiteOutsourcing outsource = outsourcingDao.findByPrimaryKey(docNo);
			TbSiteWorkOrder order = workOrderDao.findOrderByPk(outsource.getORDER_ID());
			TbSiteWork work = workDao.findByPk(order.getWORK_ID());
			String coName = "";
			if(outsource != null){
				TbComCompany company = 	companyDao.selectByPrimaryKey(outsource.getCO_VAT_NO());
				coName = company == null ? "":company.getCO_NAME();
			}
			varMap.put("workType", WorkType.detectLabel(work.getWORK_TYPE()));
			varMap.put("osId", outsource.getOS_ID());
			varMap.put("company", coName);
			varMap.put("appDept", deptDao.findByPk(outsource.getAPL_DEPT()).getDEPT_NAME());
			varMap.put("appUser", personnelDao.searchByPsnNo(outsource.getAPL_USER()).getCHN_NM());
		}

		return varMap;
	}
}
