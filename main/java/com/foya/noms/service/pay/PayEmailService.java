package com.foya.noms.service.pay;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.dao.mybatis.mapper.TbAuthPersonnelMapper;
import com.foya.dao.mybatis.mapper.TbPayCashRequirementMapper;
import com.foya.dao.mybatis.mapper.TbSysLookupMapper;
import com.foya.dao.mybatis.mapper.TbSysSignLogMapper;
import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.dao.mybatis.model.TbAuthPersonnelExample;
import com.foya.dao.mybatis.model.TbPayCashRequirement;
import com.foya.dao.mybatis.model.TbPayCashRequirementExample;
import com.foya.dao.mybatis.model.TbSysEmailTemplate;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.dao.mybatis.model.TbSysLookupExample;
import com.foya.dao.mybatis.model.TbSysSignLog;
import com.foya.dao.mybatis.model.TbSysSignLogExample;
import com.foya.exception.NomsException;
import com.foya.noms.service.system.MailService;
import com.foya.noms.util.Mail;

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
public class PayEmailService extends MailService {
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TbSysLookupMapper tbSysLookupMapper;
	@Autowired
	private TbSysSignLogMapper tbSysSignLogMapper;
	@Autowired
	private TbAuthPersonnelMapper tbAuthPersonnelMapper;
	@Autowired
	private TbPayCashRequirementMapper tbPayCashRequirementMapper;
	/**
	 * pay駁回 發mail
	 * @param workId
	 */
	public void sendMail(TbPayCashRequirement exportObject,TbSysEmailTemplate tbSysEmailTemplate) throws NomsException {
		
		String mail = "";
			// 準備EMAIL參數內容
			String subject = tbSysEmailTemplate.getSUBJECT();
			String content = tbSysEmailTemplate.getCONTENT()
					.replace("${cashReqNo}", exportObject.getCASH_REQ_NO())
					.replace("${reject_reason}", exportObject.getREJECT_REASON())
					.replace("${reject_reason_desc}", exportObject.getREJECT_MEMO());
		//處理類別不是專線時
		if (!exportObject.getPROCESS_TYPE().equals("LIN")){
			
			String posId = "";
			String sign_user = "";
			String principal_user = "";
			
			//取得posId
			TbSysLookupExample tbSysLookupExample2 = new TbSysLookupExample();
			com.foya.dao.mybatis.model.TbSysLookupExample.Criteria criteria2= tbSysLookupExample2.createCriteria();
			criteria2.andTYPEEqualTo("ORG_SPECIFIC_POS_ID");
			//租金/電費/雜項/支票作廢
			if (exportObject.getPROCESS_TYPE().equals("REN")) {
				criteria2.andCODEEqualTo("WF_PAY_RENT_CHARGE");
			} else if (exportObject.getPROCESS_TYPE().equals("ELE")) {
				criteria2.andCODEEqualTo("WF_PAY_ELECTRIC_BILL_CHARGE");
			} else if (exportObject.getPROCESS_TYPE().equals("MIS")) {
				criteria2.andCODEEqualTo("WF_PAY_OTHERS_CHARGE");
			} else {
				criteria2.andCODEEqualTo("WF_PAY_VOIDED_CHECK_CHARGE");
			}
			List<TbSysLookup> list2 = tbSysLookupMapper.selectByExample(tbSysLookupExample2);
			if(!list2.isEmpty()){
				posId = list2.get(0).getVALUE1();
			}
			log.debug("posId~~" + posId);
			//取得要通知的sign_user,principal_user
			TbSysSignLogExample tbSysSignLogExample = new TbSysSignLogExample();
			com.foya.dao.mybatis.model.TbSysSignLogExample.Criteria criteria3= tbSysSignLogExample.createCriteria();
			criteria3.andSIGN_POSEqualTo(posId);
			
			//租金/電費/雜項/支票作廢
			if (exportObject.getPROCESS_TYPE().equals("REN")) {
				criteria3.andPROCESS_TYPEEqualTo("PayRent");
				criteria3.andAPPLY_NOEqualTo(exportObject.getCASH_REQ_NO());
			} else if (exportObject.getPROCESS_TYPE().equals("ELE")) {
				criteria3.andPROCESS_TYPEEqualTo("PayElectricBill");
				criteria3.andAPPLY_NOEqualTo(exportObject.getCASH_REQ_NO());
			} else if (exportObject.getPROCESS_TYPE().equals("MIS")) {
				criteria3.andPROCESS_TYPEEqualTo("PayOthers");
				criteria3.andAPPLY_NOEqualTo(exportObject.getCASH_REQ_NO());
			} else {
				criteria3.andPROCESS_TYPEEqualTo("PayVoidedCheck");
				criteria3.andAPPLY_NOEqualTo(exportObject.getCASH_REQ_NO());
			}
			List<TbSysSignLog> list3 = tbSysSignLogMapper.selectByExample(tbSysSignLogExample);
			if(!list3.isEmpty()){
				sign_user = list3.get(0).getSIGN_USER();
				principal_user = list3.get(0).getPRINCIPAL_USER();
			}
			log.debug("sign_user~~" + sign_user);
			log.debug("principal_user~~" + principal_user);
			
			//取得mail
			TbAuthPersonnelExample tbAuthPersonnelExample = new TbAuthPersonnelExample();
			com.foya.dao.mybatis.model.TbAuthPersonnelExample.Criteria criteria4= tbAuthPersonnelExample.createCriteria();
			List<String> user= new ArrayList<String>();
			user.add(sign_user);
			user.add(principal_user);
			criteria4.andPSN_NOIn(user);
			List<TbAuthPersonnel> list4 = tbAuthPersonnelMapper.selectByExample(tbAuthPersonnelExample);
			if(!list4.isEmpty()){
				mail = list4.get(0).getE_MAIL();
			}
			log.debug("mail~~" + mail);
		//專線	
		} else {
			//請款單申請人代碼
			String app_user = "";
			TbPayCashRequirementExample tbPayCashRequirementExample = new TbPayCashRequirementExample();
			com.foya.dao.mybatis.model.TbPayCashRequirementExample.Criteria criteria= tbPayCashRequirementExample.createCriteria();
			criteria.andCASH_REQ_NOEqualTo(exportObject.getCASH_REQ_NO());
			List<TbPayCashRequirement> list = tbPayCashRequirementMapper.selectByExample(tbPayCashRequirementExample);
			if(!list.isEmpty()){
				app_user = list.get(0).getAPP_USER();
			}
			log.debug("app_user~~" + app_user);
			//取得mail
			TbAuthPersonnelExample tbAuthPersonnelExample = new TbAuthPersonnelExample();
			com.foya.dao.mybatis.model.TbAuthPersonnelExample.Criteria criteria4= tbAuthPersonnelExample.createCriteria();
			criteria4.andPSN_NOEqualTo(app_user);
			List<TbAuthPersonnel> list4 = tbAuthPersonnelMapper.selectByExample(tbAuthPersonnelExample);
			if(!list4.isEmpty()){
				mail = list4.get(0).getE_MAIL();
			}
			log.debug("mail~~" + mail);
		}
		
		List<String> mailTo = new ArrayList<String>();
		mailTo.add(mail);
		// Send Mail
		sendMail(Mail.by(mailTo, subject, content));		
		
			
		
	}
	
	
}
