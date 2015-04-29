package com.foya.noms.service.pay;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.hornetq.utils.json.JSONArray;
import org.hornetq.utils.json.JSONException;
import org.hornetq.utils.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbPayProvisionalAttachment;
import com.foya.dao.mybatis.model.TbPayProvisionalAttachmentExample;
import com.foya.dao.mybatis.model.TbPayProvisionalAttachmentExample.Criteria;
import com.foya.dao.mybatis.model.TbPayProvisionalAttachmentUser;
import com.foya.dao.mybatis.model.TbPayProvisionalAttachmentUserExample;
import com.foya.exception.NomsException;
import com.foya.noms.dao.pay.Pay014Dao;
import com.foya.noms.dto.pay.TbPayProvisionalAttachmentUserDTO;
import com.foya.noms.service.BaseService;
import com.foya.noms.util.DateUtils;
@Service
public class Pay014Service extends BaseService {
	@Inject 
	private Pay014Dao pay014Dao;
	
	public List<TbPayProvisionalAttachment> selectByExample(String docNo,String domain,Date attachStartDate,Date attachEndDate,String attachUserId)throws ParseException{
		TbPayProvisionalAttachmentExample example = new TbPayProvisionalAttachmentExample();
		Criteria criteria = example.createCriteria();
		try{
			if("" != docNo && !docNo.equals(null) && !"null".equals(docNo))
				criteria.andDOCUMENT_NOLike("%"+docNo+"%");
		}catch(NullPointerException e){}			
		if("" != domain)
			criteria.andDOMAINEqualTo(domain);
		if(!attachStartDate.equals(null) && !attachEndDate.equals(null)){
			criteria.andATTACH_BEGIN_DATEGreaterThanOrEqualTo(attachStartDate);
			criteria.andATTACH_END_DATELessThanOrEqualTo(attachEndDate);
		}
		if("" != attachUserId && "null".equals(attachUserId) && !attachUserId.equals(null))
			criteria.andATTACH_USER_IDEqualTo(attachUserId);
		return pay014Dao.selectByExample(example);
	}
	
	public TbPayProvisionalAttachment selectByPrimaryKey(String docNo){
		TbPayProvisionalAttachment pay=pay014Dao.selectByPrimaryKey(docNo);
		return pay;
	}

	public List<TbPayProvisionalAttachmentUserDTO> selectDesc(String docNo){
		return pay014Dao.selectDesc(docNo);
	}
	@Transactional
	public int insertPayAttach(String addDomain,String addDocNo,Date addAttachStartDate,Date addAttachEndDate,
			String attachUser, String appUser,Date appDate,String crUser,String memo,String personId,String attachAmt,
			String maxAmt,String payCheck,Date crTime){
		try{
			this.delData(addDocNo);//先行刪除資料
		}catch(Exception throwable){
			log.error(throwable.getMessage());
			throwable.printStackTrace();
		}
		TbPayProvisionalAttachment record = new TbPayProvisionalAttachment();
		record.setDOMAIN(addDomain);
		record.setDOCUMENT_NO(addDocNo);
		record.setATTACH_BEGIN_DATE(addAttachStartDate);
		record.setATTACH_END_DATE(addAttachEndDate);
		record.setATTACH_USER_ID(attachUser);
		record.setATTACH_USER_NAME(attachUser);
		record.setATTACH_TAX_INCLUSIVE_TOTAL_AMT(BigDecimal.valueOf(Long.valueOf(attachAmt)));
		record.setCREDIT_MAX_AMT_PER_PR(BigDecimal.valueOf(Long.valueOf(maxAmt)));
		record.setIF_HAVE_PAYMENT_USER(payCheck);
		record.setTOTAL_MADE_AMT(BigDecimal.valueOf(0.00));//暫時存0
		record.setMEMO(memo);
		record.setAPP_DATE(appDate);
		record.setAPP_USER(appUser);
		record.setCR_TIME(crTime);
		record.setCR_USER(crUser);
		record.setMD_TIME(crTime);
		record.setMD_USER(crUser);
		return pay014Dao.insertPayAttach(record);
	}
//	@Transactional
//	public int insertPayAttachUser(String docNo,String addPayUser,String addPayUserName,String acctNbr,
//			BigDecimal taxAmt,Short paymentProportion,Date paymentStartDate,Date paymentEndDate,String payMethod,
//			String bankCode,String bankBranch,Date crTime,Integer crUser){
//		TbPayProvisionalAttachmentUser record = new TbPayProvisionalAttachmentUser();
//		record.setDOCUMENT_NO(docNo);
//		record.setPAYMENT_USER_ID(addPayUser);
//		record.setPAYMENT_USER_NAME(addPayUserName);
//		record.setPAYMENT_METHOD(payMethod);
//		record.setBANK_CODE(bankCode);
//		record.setBANK_BRANCH_CODE(bankBranch);
//		record.setACCOUNT_NBR(acctNbr);
//		record.setATTACH_TAX_INCLUSIVE_AMT(taxAmt);
//		record.setPAYMENT_PROPORTION(paymentProportion);
//		record.setTOTAL_MADE_AMT(BigDecimal.valueOf(0.00));
//		record.setPAYMENT_BEGIN_DATE(paymentStartDate);
//		record.setPAYMENT_END_DATE(paymentEndDate);
//		record.setCR_TIME(crTime);
//		record.setCR_USER(String.valueOf(crUser));
//		record.setMD_TIME(crTime);
//		record.setMD_USER(String.valueOf(crUser));
//		return pay014Dao.insertPayAttachUser(record);
//	}
	@Transactional
	public int delMasterData(String docNo){
		return pay014Dao.delMasterData(docNo);
	}
	
	
	public void delData(String docNo) throws NomsException {
		TbPayProvisionalAttachmentUserExample example = new TbPayProvisionalAttachmentUserExample();
		example.createCriteria().andDOCUMENT_NOEqualTo(docNo);
		pay014Dao.delMasterData(docNo);
		pay014Dao.delDetailData(example);
	}
	

	@Transactional
	public void insertToTable(String paymentAttach,String paymentAttachUser,Date today,String docNo) throws NomsException ,Exception{
			try{
				this.delData(docNo);//先行刪除資料
			}catch(Exception throwable){
				log.error(throwable.getMessage());
				throwable.printStackTrace();
			}
			JSONArray paymentAttachArray = new JSONArray(paymentAttach);
			JSONArray paymentAttachUserArray = new JSONArray(paymentAttachUser);
			for (int i = 0; i < paymentAttachUserArray.length(); i++) {
				JSONObject obj = paymentAttachUserArray.getJSONObject(i);
				TbPayProvisionalAttachmentUser attachUserData = this.makeTbPayProvisionalAttachmentUser(obj, today);
				pay014Dao.insertPayAttachUser(attachUserData);
				}
			for (int iAttach = 0; iAttach < paymentAttachArray.length(); iAttach++) {
				JSONObject obj = paymentAttachArray.getJSONObject(iAttach);
				TbPayProvisionalAttachment attachData = this.makeTbPayProvisionalAttachment(obj, today);
				 pay014Dao.insertPayAttach(attachData);
				}
	}
	
	public TbPayProvisionalAttachment makeTbPayProvisionalAttachment(JSONObject obj,Date today) throws JSONException{
		TbPayProvisionalAttachment record = new TbPayProvisionalAttachment();
		try{
		record.setDOMAIN(obj.getString("addDomain"));
		record.setDOCUMENT_NO(obj.getString("addDocNo"));
		record.setATTACH_BEGIN_DATE(DateUtils.parse(obj.getString("addAttachStartDate"), "yyyy/MM/dd"));
		record.setATTACH_END_DATE(DateUtils.parse(obj.getString("addAttachEndDate"), "yyyy/MM/dd"));
		record.setATTACH_USER_ID(obj.getString("attachUser"));
		record.setATTACH_USER_NAME(obj.getString("attachUserName"));
		record.setATTACH_TAX_INCLUSIVE_TOTAL_AMT(BigDecimal.valueOf(Long.valueOf(obj.getString("attachAmt"))));
		record.setCREDIT_MAX_AMT_PER_PR(BigDecimal.valueOf(Long.valueOf(obj.getString("maxAmt"))));
		record.setIF_HAVE_PAYMENT_USER(obj.getString("payCheck"));
		record.setTOTAL_MADE_AMT(BigDecimal.valueOf(Long.valueOf(obj.getString("attachAmt"))));
		record.setMEMO(obj.getString("memo"));
		record.setAPP_DATE(DateUtils.parse(obj.getString("appDate"), "yyyy/MM/dd"));
		record.setAPP_USER(obj.getString("appUser"));
		record.setCR_TIME(today);
		record.setCR_USER(getLoginUser().getUsername());
		record.setMD_TIME(today);
		record.setMD_USER(getLoginUser().getUsername());
		}catch(JSONException e){
			throw new NomsException(e.getMessage());
		}
		return record;
	}
	
	public TbPayProvisionalAttachmentUser makeTbPayProvisionalAttachmentUser(JSONObject obj,Date today) throws JSONException{
		TbPayProvisionalAttachmentUser record = new TbPayProvisionalAttachmentUser();
		try{
		record.setDOCUMENT_NO(obj.getString("docNo"));
		record.setPAYMENT_USER_ID(obj.getString("addPayUser"));
		record.setPAYMENT_USER_NAME(obj.getString("addPayUserName"));
		record.setPAYMENT_METHOD(obj.getString("payMethod"));
		record.setBANK_CODE(obj.getString("bankCode"));
		record.setBANK_BRANCH_CODE(obj.getString("bankBranch"));
		record.setACCOUNT_NBR(obj.getString("acctNbr"));
		record.setATTACH_TAX_INCLUSIVE_AMT(BigDecimal.valueOf(Long.valueOf(obj.getString("taxAmt"))));
		record.setPAYMENT_PROPORTION(Short.valueOf(obj.getString("paymentProportion")));
		record.setTOTAL_MADE_AMT(BigDecimal.valueOf(Long.valueOf(obj.getString("attachAmt"))));
		record.setPAYMENT_BEGIN_DATE(DateUtils.parse(obj.getString("paymentStartDate"), "yyyy/MM/dd"));
		record.setPAYMENT_END_DATE(DateUtils.parse(obj.getString("paymentEndDate"), "yyyy/MM/dd"));
		record.setCHECK_ADDR("checkAddr");
		record.setCR_TIME(today);
		record.setCR_USER(getLoginUser().getUsername());
		record.setMD_TIME(today);
		record.setMD_USER(getLoginUser().getUsername());
		}catch(JSONException e){
			throw new NomsException(e.getMessage());
		}
		return record;
	}
}
