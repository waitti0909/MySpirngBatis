package com.foya.noms.dao.pay;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.ProcedureNomsMapper;
import com.foya.dao.mybatis.mapper.TbPayCashRequirementMapper;
import com.foya.dao.mybatis.mapper.TbPayPaymentItemMapper;
import com.foya.dao.mybatis.mapper.TbPayPaymentMapper;
import com.foya.dao.mybatis.mapper.TbPayPaymentRequestDtlMapper;
import com.foya.dao.mybatis.mapper.TbPayPaymentRequestMapper;
import com.foya.dao.mybatis.mapper.TbPayPaymentRequestVoucherMapper;
import com.foya.dao.mybatis.mapper.TbPayRepartitionMapper;
import com.foya.dao.mybatis.mapper.TbPayVoucherCreditMapper;
import com.foya.dao.mybatis.mapper.TbSiteMainMapper;
import com.foya.dao.mybatis.mapper.UTbPayPaymentMapper;
import com.foya.dao.mybatis.mapper.UTbPayPaymentRequestDtlMapper;
import com.foya.dao.mybatis.mapper.UTbPayPaymentRequestMapper;
import com.foya.dao.mybatis.mapper.UTbPayPaymentRequestVoucherMapper;
import com.foya.dao.mybatis.mapper.UTbPayVoucherCreditMapper;
import com.foya.dao.mybatis.model.TbPayCashRequirement;
import com.foya.dao.mybatis.model.TbPayCashRequirementExample;
import com.foya.dao.mybatis.model.TbPayPayment;
import com.foya.dao.mybatis.model.TbPayPaymentExample;
import com.foya.dao.mybatis.model.TbPayPaymentItem;
import com.foya.dao.mybatis.model.TbPayPaymentItemExample;
import com.foya.dao.mybatis.model.TbPayPaymentRequest;
import com.foya.dao.mybatis.model.TbPayPaymentRequestDtl;
import com.foya.dao.mybatis.model.TbPayPaymentRequestDtlExample;
import com.foya.dao.mybatis.model.TbPayPaymentRequestExample;
import com.foya.dao.mybatis.model.TbPayPaymentRequestVoucher;
import com.foya.dao.mybatis.model.TbPayPaymentRequestVoucherExample;
import com.foya.dao.mybatis.model.TbPayRepartition;
import com.foya.dao.mybatis.model.TbPayRepartitionExample;
import com.foya.dao.mybatis.model.TbPayVoucherCredit;
import com.foya.dao.mybatis.model.TbPayVoucherCreditExample;
import com.foya.dao.mybatis.model.TbSiteMain;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.pay.TbPayPaymentDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestVoucherDTO;
import com.foya.noms.dto.pay.TbPayRepartitionDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Repository
public class Pay005Dao extends BaseDao {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private TbPayCashRequirementMapper tbPayCashRequirementMapper;
	@Autowired
	private TbPayPaymentRequestMapper tbPayPaymentRequestMapper;
	@Autowired
	private UTbPayPaymentRequestMapper uTbPayPaymentRequestMapper;
	@Autowired
	private TbPayPaymentMapper tbPayPaymentMapper;
	@Autowired
	private TbPayPaymentRequestDtlMapper tbPayPaymentRequestDtlMapper;
	@Autowired
	private TbPayVoucherCreditMapper tbPayVoucherCreditMapper;
	@Autowired
	private UTbPayVoucherCreditMapper uTbPayVoucherCreditMapper;
	@Autowired
	private TbPayPaymentRequestVoucherMapper tbPayPaymentRequestVoucherMapper;
	@Autowired
	private UTbPayPaymentRequestDtlMapper uTbPayPaymentRequestDtlMapper;
	@Autowired
	private UTbPayPaymentMapper uTbPayPaymentMapper;
	@Autowired
	private UTbPayPaymentRequestVoucherMapper uTbPayPaymentRequestVoucherMapper;
	@Autowired
	private TbPayRepartitionMapper tbPayRepartitionMapper;
	@Autowired
	private TbSiteMainMapper tbSiteMainMapper;
	@Autowired
	private TbPayPaymentItemMapper tbPayPaymentItemMapper;
	@Autowired
	private ProcedureNomsMapper procedureNomsMapper;
	public List<TbPayCashRequirement> selectPayCashRequirementByExample(TbPayCashRequirementExample example){
		List<TbPayCashRequirement> list = tbPayCashRequirementMapper.selectByExample(example);
		return list;
	}
	
	public TbPayCashRequirement selectPayCashRequirementByPK(String cashReqNo){
		TbPayCashRequirement data = tbPayCashRequirementMapper.selectByPrimaryKey(cashReqNo);
		return data;
	}
	
	public int updatePayCashRequirementByPK(TbPayCashRequirement record){
		return tbPayCashRequirementMapper.updateByPrimaryKeySelective(record);
	}
	
	public List<TbPayPaymentRequest> selectPaymentRequestByExample(TbPayPaymentRequestExample example){
		List<TbPayPaymentRequest> list = tbPayPaymentRequestMapper.selectByExample(example);
		return list;
	}
	
	public int countPaymentRequestByExample(TbPayPaymentRequestExample example){
		return tbPayPaymentRequestMapper.countByExample(example);
	}
	
	public int countPaymentRequestDtlByExample(TbPayPaymentRequestDtlExample example){
		return tbPayPaymentRequestDtlMapper.countByExample(example);
	}
	public int countPaymentByExample(TbPayPaymentExample example){
		return tbPayPaymentMapper.countByExample(example);
	}
	public int countPayVoucherCreditByExample(TbPayVoucherCreditExample example){
		return tbPayVoucherCreditMapper.countByExample(example);
	}
	public List<TbPayPayment> selectPaymentByExample(TbPayPaymentExample example){
		List<TbPayPayment> list = tbPayPaymentMapper.selectByExample(example);
		return list;
	}
	
	public List<TbPayPaymentRequestDtl> selectPaymentRequestDtlByExample(TbPayPaymentRequestDtlExample example){
		List<TbPayPaymentRequestDtl> list = tbPayPaymentRequestDtlMapper.selectByExample(example);
		return list;
	}
	
	public List<TbPayVoucherCredit> selectPayVoucherCreditByExample(TbPayVoucherCreditExample example){
		List<TbPayVoucherCredit> list = tbPayVoucherCreditMapper.selectByExample(example);
		return list;
	}
	
	public int countPayPaymentRequestVoucherByExample(TbPayPaymentRequestVoucherExample example){
		return tbPayPaymentRequestVoucherMapper.countByExample(example);
	}
	public void payPcGetSeqnoByMap(Map<String, Object> map) {
		procedureNomsMapper.PAY_PC_GET_SEQNO(map);
	}
	public List<TbPayCashRequirement> getPayCashReqNo(String dateString,String cashReqNo){
		TbPayCashRequirementExample example = new TbPayCashRequirementExample();
		example.createCriteria().andCASH_REQ_NOLike("%"+cashReqNo+"%").andCASH_REQ_NOLike("%"+dateString+"%");
		example.setDistinct(true);
		example.setOrderByClause("cash_req_no");
		return tbPayCashRequirementMapper.selectByExample(example);
	}
	public List<TbPayRepartition> getRepartionNo(String dateString,String repartionNo){
		TbPayRepartitionExample example = new TbPayRepartitionExample();
		example.createCriteria().andREPARTITION_NOLike("%"+repartionNo+"%").andREPARTITION_NOLike("%"+dateString+"%");
		example.setDistinct(true);
		example.setOrderByClause("REPARTITION_NO");
		return tbPayRepartitionMapper.selectByExample(example);
	}
	public List<TbPayRepartitionDTO> getRepartion(TbPayRepartitionExample example){
		PageBounds pageBounds = getPageBounds("PAYMENT_REQ_NO");
		List<TbPayRepartitionDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbPayRepartitionMapper.selectByExample",example,  pageBounds);
		PageList<TbPayRepartitionDTO> pageList = (PageList<TbPayRepartitionDTO>)list;
		return pageList;
	}
	public List<TbPayPaymentRequestVoucher> getPayPaymentRequestVoucherNo(String dateString,String voucherNo){
		TbPayPaymentRequestVoucherExample example = new TbPayPaymentRequestVoucherExample();
		example.createCriteria().andVOUCHER_NOLike("%"+voucherNo+"%").andVOUCHER_NOLike("%"+dateString+"%");
		example.setDistinct(true);
		example.setOrderByClause("voucher_no");
		return tbPayPaymentRequestVoucherMapper.selectByExample(example);
	}
	
	public int insertTbPayPaymentRequestVoucher(TbPayPaymentRequestVoucherDTO record){
		return uTbPayPaymentRequestVoucherMapper.insertSelective(record);
	}
	
	public int insertTbPayPaymentRequest(TbPayPaymentRequest record){
		return uTbPayPaymentRequestMapper.insertSelective(record);
	}
	public int updateTbPayPaymentRequestVoucher(TbPayPaymentRequestVoucher record,
			TbPayPaymentRequestVoucherExample example){
		return tbPayPaymentRequestVoucherMapper.updateByExampleSelective(record, example);
	}
	public int insertTbPayPaymentRequestDtl(TbPayPaymentRequestDtl record){
		return tbPayPaymentRequestDtlMapper.insertSelective(record);
	}
	public int insertTbPayPayment(TbPayPayment record){
		return uTbPayPaymentMapper.insertSelective(record);
	}
	public int updateTbPayPaymentRequestDtl(HashMap<String , Object> dataObj){
		return uTbPayPaymentRequestDtlMapper.updatePlusTax(dataObj);
	}
	public int updateByExampleSelectiveWithPlus(HashMap<String , Object> dataObj){
		return uTbPayPaymentMapper.updateByExampleSelectiveWithPlus(dataObj);
	}
	public int insertTbPayCashRequirement(TbPayCashRequirement record){
		return tbPayCashRequirementMapper.insertSelective(record);
	}
	public int updateTbPayVoucherCreditSelectiveWithPlus(HashMap<String , Object> dataObj){
		return uTbPayVoucherCreditMapper.updatePlusTax(dataObj);
	}
	public List<TbPayPaymentRequest> selectTbPayPaymentRequest(TbPayPaymentRequestExample record){
		return tbPayPaymentRequestMapper.selectByExample(record);
	}
	public int insertTbPayVoucherCredit(TbPayVoucherCredit record){
		return tbPayVoucherCreditMapper.insertSelective(record);
	}
	public void deleteSpData(Long paymentReqNo){
		TbPayRepartitionExample repartitionExample = new TbPayRepartitionExample();
		repartitionExample.createCriteria().andPAYMENT_REQ_NOEqualTo(paymentReqNo);
		tbPayRepartitionMapper.deleteByExample(repartitionExample);
		TbPayPaymentRequestDtlExample PaymentRequestDtlExample=new TbPayPaymentRequestDtlExample();
		PaymentRequestDtlExample.createCriteria().andPAYMENT_REQ_NOEqualTo(paymentReqNo);
		tbPayPaymentRequestDtlMapper.deleteByExample(PaymentRequestDtlExample);
		TbPayPaymentExample paymentExample = new TbPayPaymentExample();
		paymentExample.createCriteria().andPAYMENT_REQ_NOEqualTo(paymentReqNo);
		tbPayPaymentMapper.deleteByExample(paymentExample);
		TbPayVoucherCreditExample voucherCreditExample=new TbPayVoucherCreditExample();
		voucherCreditExample.createCriteria().andPAYMENT_REQ_NOEqualTo(paymentReqNo);
		tbPayVoucherCreditMapper.deleteByExample(voucherCreditExample);
		
	}	
	public List<TbPayPaymentDTO> searchDtl(HashMap<String,Object> dataObj){
		PageBounds pageBounds = getPageBounds("PAYMENT_REQ_NO");
		List<TbPayPaymentDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbPayCashRequirementMapper.searchWithPayment",dataObj,  pageBounds);
		PageList<TbPayPaymentDTO> pageList = (PageList<TbPayPaymentDTO>)list;
		return pageList;
	}
	public TbSiteMain getTbSiteMain(String siteId){
		return tbSiteMainMapper.selectByPrimaryKey(siteId);
	}	
	
	public int insertTbPayRepartition(TbPayRepartition record){
		return tbPayRepartitionMapper.insertSelective(record);
	}	
	public List<TbPayPaymentRequestVoucherDTO> getWithVoucherCredit(HashMap<String,Object> dataObj){
		List<TbPayPaymentRequestVoucherDTO> list = uTbPayPaymentRequestVoucherMapper.getWithVoucherCredit(dataObj);
		return list;
	}
	
	public List<TbPayPaymentDTO> getPaymentAmt(HashMap<String,Object> dataObj){
		return uTbPayPaymentMapper.getPaymentAmt(dataObj);
	}
	
	public List<TbPayPaymentItem> getPaymentItem(TbPayPaymentItemExample example){
		return tbPayPaymentItemMapper.selectByExample(example);		
	}
}
