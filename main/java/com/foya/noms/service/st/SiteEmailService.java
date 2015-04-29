package com.foya.noms.service.st;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.dao.mybatis.model.TbComCompany;
import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbOrgPsnPos;
import com.foya.dao.mybatis.model.TbSiteLineApply;
import com.foya.dao.mybatis.model.TbSiteOutsourcing;
import com.foya.dao.mybatis.model.TbSiteWork;
import com.foya.dao.mybatis.model.TbSiteWorkOrder;
import com.foya.dao.mybatis.model.TbSiteWorkOrderExample;
import com.foya.dao.mybatis.model.TbSysEmailTemplate;
import com.foya.dao.mybatis.model.TbSysLookupExample;
import com.foya.exception.NomsException;
import com.foya.noms.dao.auth.PersonnelDao;
import com.foya.noms.dao.basic.CM001Dao;
import com.foya.noms.dao.common.EmailTemplateDao;
import com.foya.noms.dao.org.DeptDao;
import com.foya.noms.dao.org.PsnPosDao;
import com.foya.noms.dao.st.OutsourcingDao;
import com.foya.noms.dao.st.WorkDao;
import com.foya.noms.dao.st.WorkOrderDao;
import com.foya.noms.dao.st.line.LineApplyDao;
import com.foya.noms.dao.system.LookupDao;
import com.foya.noms.dao.system.OrderTypeDao;
import com.foya.noms.enums.EmailType;
import com.foya.noms.enums.WorkType;
import com.foya.noms.resources.AppConstants;
import com.foya.noms.service.org.ORG002Service;
import com.foya.noms.service.system.MailService;
import com.foya.noms.util.Mail;
import com.foya.noms.util.MailResponse;
import com.foya.noms.util.ParserUtil;

/**
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
 * <td>2015/1/12</td>
 * <td>站台EMAIL相關SERVICE</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 */
@Service
public class SiteEmailService extends MailService {

	@Autowired
	private EmailTemplateDao emailDao;
	@Autowired
	private DeptDao deptDao;
	@Autowired
	private PsnPosDao psnPosDao;
	@Autowired
	private PersonnelDao personnelDao;
	@Autowired
	private CM001Dao companyDao;
	@Autowired
	private LookupDao lookupDao;
	@Autowired
	private WorkDao workDao;	
	@Autowired
	private WorkOrderDao workOrderDao;	
	@Autowired
	private OrderTypeDao orderTypeDao;	
	@Autowired
	private OutsourcingDao outsourcingDao;	
	@Autowired
	private LineApplyDao lineApplyDao;
	@Autowired
	private ORG002Service org002Service;
	
	/**
	 * 作業申請通知(各工單負責主管)
	 * @param workId
	 */
	public void sendWorkApplyMail(String workId) throws NomsException {
		if (StringUtils.isEmpty(workId)) return;
		
		TbSysEmailTemplate emailModel = emailDao.findByPk(EmailType.WORK_APPLY.name());
		TbSiteWork work = workDao.findByPk(workId);
		
		TbSiteWorkOrderExample example = new TbSiteWorkOrderExample();
		example.createCriteria().andWORK_IDEqualTo(workId).andIS_ACTIVEEqualTo("Y");
		List<TbSiteWorkOrder> orders = workOrderDao.findByConditions(example);
		
		Map<String, Object> varMap = new HashMap<>();
		for (TbSiteWorkOrder order : orders) {
			// 準備替換變數
			varMap.put("orderType", orderTypeDao.findByPk(order.getORDER_TYPE()).getOD_TYPE_NAME());
			varMap.put("workType", WorkType.detectLabel(work.getWORK_TYPE()));
			varMap.put("workId", workId);
			varMap.put("appDept", deptDao.findByPk(work.getAPP_DEPT()).getDEPT_NAME());
			varMap.put("appUser", personnelDao.searchByPsnNo(work.getAPL_USER()).getCHN_NM());
			varMap.put("orderId", order.getORDER_ID());
			
			// 準備EMAIL參數內容
			String subject = ParserUtil.getParseStringByMap(varMap, emailModel.getSUBJECT());
			String content = ParserUtil.getParseStringByMap(varMap, emailModel.getCONTENT());
			
			List<TbOrgPsnPos> psnPos = psnPosDao.selectPsnNoList(order.getREP_DEPT() + "__WF_DEPT_MANAGER");
			List<String> mailTo = new ArrayList<String>();
			for (TbOrgPsnPos p : psnPos) {
				TbAuthPersonnel psn =  personnelDao.searchByPsnNo(p.getPSN_NO());
				if(psn != null){
					getEmailPersonList(mailTo,psn.getE_MAIL(), SEMICOLON);	// 各工單負責主管
				}
				
			}
			// Send Mail
			sendMail(Mail.by(mailTo, subject, content));			
		}
	}
	
	/**
	 * 待派工通知(工單負責主管)
	 * @param orderId
	 */
	public void sendOrderAssignMail(String orderId) throws NomsException{
		if (StringUtils.isEmpty(orderId)) return;
		
		TbSysEmailTemplate emailModel = emailDao.findByPk(EmailType.ORDER_START.name());
		TbSiteWorkOrder order = workOrderDao.findOrderByPk(orderId);
		TbSiteWork work = workDao.findByPk(order.getWORK_ID());
		Map<String, Object> varMap = new HashMap<>();
		
		// 準備替換變數
		varMap.put("orderType", orderTypeDao.findByPk(order.getORDER_TYPE()).getOD_TYPE_NAME());
		varMap.put("workType", WorkType.detectLabel(work.getWORK_TYPE()));
		varMap.put("appDept", deptDao.findByPk(work.getAPP_DEPT()).getDEPT_NAME());
		varMap.put("appUser", personnelDao.searchByPsnNo(work.getAPL_USER()).getCHN_NM());
		varMap.put("orderId", order.getORDER_ID());
		
		// 準備EMAIL參數內容
		String subject = ParserUtil.getParseStringByMap(varMap, emailModel.getSUBJECT());
		String content = ParserUtil.getParseStringByMap(varMap, emailModel.getCONTENT());
		List<TbOrgPsnPos> psnPos = psnPosDao.selectPsnNoList(order.getREP_DEPT() + "__WF_DEPT_MANAGER");
		List<String> mailTo = new ArrayList<String>();
		for (TbOrgPsnPos p : psnPos) {
			TbAuthPersonnel psn =  personnelDao.searchByPsnNo(p.getPSN_NO());
			if(psn != null){
				getEmailPersonList(mailTo, personnelDao.searchByPsnNo(p.getPSN_NO()).getE_MAIL(), SEMICOLON);	// 工單負責主管
			}
		}
		
		// Send Mail
		sendMail(Mail.by(mailTo, subject, content));
	}
	
	/**
	 * 自動派工通知(工單負責主管)
	 * @param orderId
	 */
	public void sendOrderAutoAssignMail(String orderId) throws NomsException {
		if (StringUtils.isEmpty(orderId)) return;
		
		TbSysEmailTemplate emailModel = emailDao.findByPk(EmailType.ORDER_INFORM_M.name());
		TbSiteWorkOrder order = workOrderDao.findOrderByPk(orderId);
		TbSiteWork work = workDao.findByPk(order.getWORK_ID());
		Map<String, Object> varMap = new HashMap<>();
		
		// 準備替換變數
		varMap.put("orderType", orderTypeDao.findByPk(order.getORDER_TYPE()).getOD_TYPE_NAME());
		varMap.put("workType", WorkType.detectLabel(work.getWORK_TYPE()));
		varMap.put("orderId", order.getORDER_ID());
		varMap.put("appDept", deptDao.findByPk(work.getAPP_DEPT()).getDEPT_NAME());
		varMap.put("appUser", personnelDao.searchByPsnNo(work.getAPL_USER()).getCHN_NM());
		varMap.put("repUser", personnelDao.searchByPsnNo(order.getREP_USER()).getCHN_NM());
		
		// 準備EMAIL參數內容
		String subject = ParserUtil.getParseStringByMap(varMap, emailModel.getSUBJECT());
		String content = ParserUtil.getParseStringByMap(varMap, emailModel.getCONTENT());
		List<TbOrgPsnPos> psnPos = psnPosDao.selectPsnNoList(order.getREP_DEPT() + "__WF_DEPT_MANAGER");
		List<String> mailTo = new ArrayList<String>();
		for (TbOrgPsnPos p : psnPos) {
			TbAuthPersonnel psn =  personnelDao.searchByPsnNo(p.getPSN_NO());
			if(psn != null){
				getEmailPersonList(mailTo, personnelDao.searchByPsnNo(p.getPSN_NO()).getE_MAIL(), SEMICOLON);	// 工單負責主管
			}
		}
		
		// Send Mail
		sendMail(Mail.by(mailTo, subject, content));
	}
	
	/**
	 * 待接工通知(工單負責人)
	 * @param orderId
	 */
	public void sendOrderPickupMail(String orderId) throws NomsException {
		if (StringUtils.isEmpty(orderId)) return;
		
		TbSysEmailTemplate emailModel = emailDao.findByPk(EmailType.ORDER_INFORM.name());
		TbSiteWorkOrder order = workOrderDao.findOrderByPk(orderId);
		TbSiteWork work = workDao.findByPk(order.getWORK_ID());
		Map<String, Object> varMap = new HashMap<>();
		
		// 準備替換變數
		varMap.put("orderType", orderTypeDao.findByPk(order.getORDER_TYPE()).getOD_TYPE_NAME());
		varMap.put("workType", WorkType.detectLabel(work.getWORK_TYPE()));
		varMap.put("orderId", order.getORDER_ID());
		varMap.put("appDept", deptDao.findByPk(work.getAPP_DEPT()).getDEPT_NAME());
		varMap.put("appUser", personnelDao.searchByPsnNo(work.getAPL_USER()).getCHN_NM());
		
		// 準備EMAIL參數內容
		String subject = ParserUtil.getParseStringByMap(varMap, emailModel.getSUBJECT());
		String content = ParserUtil.getParseStringByMap(varMap, emailModel.getCONTENT());
		List<String> mailTo = getEmailPersonList(personnelDao.searchByPsnNo(order.getREP_USER()).getE_MAIL());
		
		// Send Mail
		sendMail(Mail.by(mailTo, subject, content));
	}
	
	/**
	 * 作業完工通知(作業申請人)
	 * @param workId
	 */
	public void sendWorkApplyFinishMail(String workId) throws NomsException {
		if (StringUtils.isEmpty(workId)) return;
		
		TbSysEmailTemplate emailModel = emailDao.findByPk(EmailType.WORK_FINISH.name());
		TbSiteWork work = workDao.findByPk(workId);		
		TbSiteWorkOrderExample example = new TbSiteWorkOrderExample();
		example.createCriteria().andWORK_IDEqualTo(workId).andIS_ACTIVEEqualTo("Y");
		TbAuthPersonnel appUser = personnelDao.searchByPsnNo(work.getAPL_USER());
		
		Map<String, Object> varMap = new HashMap<>();
		// 準備替換變數
		varMap.put("workType", WorkType.detectLabel(work.getWORK_TYPE()));
		varMap.put("workId", workId);
		varMap.put("appDept", deptDao.findByPk(work.getAPP_DEPT()).getDEPT_NAME());
		varMap.put("appUser", appUser.getCHN_NM());
		
		// 準備EMAIL參數內容
		String subject = ParserUtil.getParseStringByMap(varMap, emailModel.getSUBJECT());
		String content = ParserUtil.getParseStringByMap(varMap, emailModel.getCONTENT());
		List<String> mailTo = getEmailPersonList(appUser.getE_MAIL());
		
		// Send Mail
		sendMail(Mail.by(mailTo, subject, content));
	}
	
	/**
	 * 委外待派工通知(廠商聯絡人)
	 * @param osId
	 */
	public MailResponse sendAssignOsMail(String osId) throws NomsException {
		if (StringUtils.isEmpty(osId)) return null;
		
		TbSysEmailTemplate emailModel = emailDao.findByPk(EmailType.OUTSOURCE_START.name());		
		TbSiteOutsourcing outsource = outsourcingDao.findByPrimaryKey(osId);
		TbSiteWorkOrder order = workOrderDao.findOrderByPk(outsource.getORDER_ID());
		TbSiteWork work = workDao.findByPk(order.getWORK_ID());
		TbComCompany company = companyDao.selectByPrimaryKey(outsource.getCO_VAT_NO());
		
		Map<String, Object> varMap = new HashMap<>();
		// 準備替換變數
		varMap.put("workType", WorkType.detectLabel(work.getWORK_TYPE()));
		varMap.put("osId", outsource.getOS_ID());
		varMap.put("company", company.getCO_NAME());
		varMap.put("appDept", deptDao.findByPk(outsource.getAPL_DEPT()).getDEPT_NAME());
		varMap.put("appUser", personnelDao.searchByPsnNo(outsource.getAPL_USER()).getCHN_NM());
				
		// 準備EMAIL參數內容
		String subject = ParserUtil.getParseStringByMap(varMap, emailModel.getSUBJECT());
		String content = ParserUtil.getParseStringByMap(varMap, emailModel.getCONTENT());
		List<String> mailTo = getEmailPersonList(company.getCON_EMAIL());
		List<String> mailCc = getEmailPersonList(company.getCON_EMAIL2());
		
		// Send Mail
		return sendMail(Mail.by(mailTo, mailCc, subject, content));
	}
	
	/**
	 * 核網設定通知(該區核網維運部人員)
	 * @param appId
	 */
	public void sendLineSetupMail(String appId) throws NomsException {
		if (StringUtils.isEmpty(appId)) return;
		
		TbSysEmailTemplate emailModel = emailDao.findByPk(EmailType.LINE_SETUP.name());
		TbSiteLineApply line = lineApplyDao.findByPk(appId);
		TbSiteWorkOrder order = workOrderDao.findOrderByPk(line.getORDER_ID());
		TbSiteWork work = workDao.findByPk(order.getWORK_ID());
		TbSysLookupExample example = new TbSysLookupExample();
		example.createCriteria().andTYPEEqualTo(AppConstants.LINE_APPLY_TYPE).andCODEEqualTo(line.getAPP_TYPE());
		
		TbOrgDept networkDept = org002Service.getLineNetworkDept(work.getCITY(), work.getAREA());
		if (networkDept == null) return;
		
		Map<String, Object> varMap = new HashMap<>();
		// 準備替換變數
		varMap.put("workType", WorkType.detectLabel(work.getWORK_TYPE()));
		varMap.put("appId", appId);
		varMap.put("appType", lookupDao.selectByExample(example).get(0).getNAME());
		varMap.put("appDept", deptDao.findByPk(line.getAPP_DEPT()).getDEPT_NAME());
		varMap.put("appUser", personnelDao.searchByPsnNo(line.getAPP_USER()).getCHN_NM());
		
		// 準備EMAIL參數內容
		String subject = ParserUtil.getParseStringByMap(varMap, emailModel.getSUBJECT());
		String content = ParserUtil.getParseStringByMap(varMap, emailModel.getCONTENT());
		List<TbOrgPsnPos> psnPos = psnPosDao.selectPsnNoList(networkDept.getDEPT_ID() + "__OD_LINE_IP_SETING");
		List<String> mailTo = new ArrayList<String>();
		for (TbOrgPsnPos p : psnPos) {
			getEmailPersonList(mailTo, personnelDao.searchByPsnNo(p.getPSN_NO()).getE_MAIL(), SEMICOLON);
		}
		
		// Send Mail
		sendMail(Mail.by(mailTo, subject, content));
	}
	
	/**
	 * 專線取消通知(該區核網維運人員)
	 * @param appId
	 */
	public void sendLineCancelMail(String appId) throws NomsException {
		if (StringUtils.isEmpty(appId)) return;
		
		TbSysEmailTemplate emailModel = emailDao.findByPk(EmailType.LINE_CANCEL.name());
		TbSiteLineApply line = lineApplyDao.findByPk(appId);
		TbSiteWorkOrder order = workOrderDao.findOrderByPk(line.getORDER_ID());
		TbSiteWork work = workDao.findByPk(order.getWORK_ID());
		TbSysLookupExample example = new TbSysLookupExample();
		example.createCriteria().andTYPEEqualTo(AppConstants.LINE_APPLY_TYPE).andCODEEqualTo(line.getAPP_TYPE());
		
		TbOrgDept networkDept = org002Service.getLineNetworkDept(work.getCITY(), work.getAREA());
		if (networkDept == null) return;
		
		Map<String, Object> varMap = new HashMap<>();
		// 準備替換變數
		varMap.put("workType", WorkType.detectLabel(work.getWORK_TYPE()));
		varMap.put("appId", appId);
		varMap.put("appType", lookupDao.selectByExample(example).get(0).getNAME());
		varMap.put("appDept", deptDao.findByPk(line.getAPP_DEPT()).getDEPT_NAME());
		varMap.put("appUser", personnelDao.searchByPsnNo(line.getAPP_USER()).getCHN_NM());
		
		// 準備EMAIL參數內容
		String subject = ParserUtil.getParseStringByMap(varMap, emailModel.getSUBJECT());
		String content = ParserUtil.getParseStringByMap(varMap, emailModel.getCONTENT());
		List<TbOrgPsnPos> psnPos = psnPosDao.selectPsnNoList(networkDept.getDEPT_ID() + "__OD_LINE_IP_SETING");
		List<String> mailTo = new ArrayList<String>();
		for (TbOrgPsnPos p : psnPos) {
			getEmailPersonList(mailTo, personnelDao.searchByPsnNo(p.getPSN_NO()).getE_MAIL(), SEMICOLON);
		}
		
		// Send Mail
		sendMail(Mail.by(mailTo, subject, content));
	}
	
}
