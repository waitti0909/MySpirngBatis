package com.foya.noms.dao.pay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.ProcedureNomsMapper;
import com.foya.dao.mybatis.mapper.TbLsLocPaymentMapper;
import com.foya.dao.mybatis.mapper.TbLsMainMapper;
import com.foya.dao.mybatis.mapper.TbPayCashRequirementMapper;
import com.foya.dao.mybatis.mapper.TbPayCheckDisregardMapper;
import com.foya.dao.mybatis.mapper.TbPayPaymentMapper;
import com.foya.dao.mybatis.mapper.TbPayPaymentRequestDtlMapper;
import com.foya.dao.mybatis.mapper.TbPayPaymentRequestMapper;
import com.foya.dao.mybatis.mapper.TbSiteMainMapper;
import com.foya.dao.mybatis.mapper.UTbPayCheckDisregardMapper;
import com.foya.dao.mybatis.model.TbLsLocPayment;
import com.foya.dao.mybatis.model.TbLsLocPaymentExample;
import com.foya.dao.mybatis.model.TbLsMain;
import com.foya.dao.mybatis.model.TbLsMainExample;
import com.foya.dao.mybatis.model.TbPayCashRequirement;
import com.foya.dao.mybatis.model.TbPayCheckDisregard;
import com.foya.dao.mybatis.model.TbPayCheckDisregardExample;
import com.foya.dao.mybatis.model.TbPayCheckDisregardKey;
import com.foya.dao.mybatis.model.TbPayPayment;
import com.foya.dao.mybatis.model.TbPayPaymentExample;
import com.foya.dao.mybatis.model.TbPayPaymentRequest;
import com.foya.dao.mybatis.model.TbPayPaymentRequestDtl;
import com.foya.dao.mybatis.model.TbPayPaymentRequestDtlKey;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.pay.TbPayCheckDisregardDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Repository
public class Pay010Dao extends BaseDao {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private TbPayPaymentMapper tbPayPaymentMapper;
	@Autowired
	private TbPayCashRequirementMapper tbCashRequirementMapper;
	@Autowired
	private TbPayPaymentRequestMapper tbPayPaymentRequestMapper;
	@Autowired
	private TbPayPaymentRequestDtlMapper tbPayPaymentRequestDtlMapper;
	@Autowired
	private TbPayCheckDisregardMapper tbPayCheckDisregardMapper;
	@Autowired
	private UTbPayCheckDisregardMapper uTbPayCheckDisregardMapper;
	@Autowired
	private TbLsMainMapper tbLsMainMapper;
	@Autowired
	private TbLsLocPaymentMapper tbLsLocPaymentMapper;
	@Autowired
	private TbSiteMainMapper tbSiteMainMapper;
	@Autowired
	private ProcedureNomsMapper procedureNomsMapper;
	public void payPcGetSeqnoByMap(Map<String, Object> map) {
		procedureNomsMapper.PAY_PC_GET_SEQNO(map);
	}
	public List<TbPayPayment> selectPyamentByExample(){
		TbPayPaymentExample example = new TbPayPaymentExample();
		example.createCriteria().andPAYMENT_METHODEqualTo("C");
		example.setDistinct(true);
		List<TbPayPayment> list = tbPayPaymentMapper.selectByExample(example);
		return list;
	}
	
	public List<TbPayPayment> selectPyamentByExampleCheckNbr(String checkNbr){
		TbPayPaymentExample example = new TbPayPaymentExample();
		example.createCriteria().andPAYMENT_METHODEqualTo("C").andCHECK_NBREqualTo(checkNbr);
		example.setDistinct(true);
		List<TbPayPayment> list = tbPayPaymentMapper.selectByExample(example);
		return list;
	}
	
	public TbPayPayment selectPyamentByExampleSeq(Long seqNo){
		TbPayPaymentExample example = new TbPayPaymentExample();
		example.createCriteria().andSEQ_NBREqualTo(seqNo);
		TbPayPayment payment = tbPayPaymentMapper.selectByPrimaryKey(seqNo);
		return payment;
	}
	
	public List<TbPayCheckDisregard> getPayCheckDisregardNo(String disregardNo){
		TbPayCheckDisregardExample example = new TbPayCheckDisregardExample();
		example.createCriteria().andDISREGARD_NOLike("%"+disregardNo+"%");
		example.setDistinct(true);
		example.setOrderByClause("disregard_no");
		return tbPayCheckDisregardMapper.selectByExample(example);
	}
	
	public List<TbPayCheckDisregard> getPayCheckDisregardNoEqual(String disregardNo){
		TbPayCheckDisregardExample example = new TbPayCheckDisregardExample();
		example.createCriteria().andDISREGARD_NOEqualTo(disregardNo);
		example.setDistinct(true);
		PageBounds pageBounds = getPageBounds("disregard_no");
		List<TbPayCheckDisregard> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.TbPayCheckDisregardMapper.selectByExample",example,  pageBounds);
		PageList<TbPayCheckDisregard> pageList = (PageList<TbPayCheckDisregard>)list;
		return pageList;
	}
	
	public int insertPayCheckDisregard(TbPayCheckDisregard record){
		return tbPayCheckDisregardMapper.insertSelective(record);
	}
	
	public List<TbPayCheckDisregardDTO> selectTbPayCheckDisregard(HashMap<String,Object> dataObj){
		PageBounds pageBounds = getPageBounds("disregard_no");
		List<TbPayCheckDisregardDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbPayCheckDisregardMapper.searchWithCnt",dataObj,  pageBounds);
		PageList<TbPayCheckDisregardDTO> pageList = (PageList<TbPayCheckDisregardDTO>)list;
		return pageList;
	}
	
	public TbPayPaymentRequest selectPyamentRequsetBypk(Long reqNo){
		TbPayPaymentRequest payment = tbPayPaymentRequestMapper.selectByPrimaryKey(reqNo);
		return payment;
	}
	
	public List<TbPayPayment> selectPyamentByExampleReqNo(Long reqNo,Short reqItemNo,Long seqNo){
		TbPayPaymentExample example = new TbPayPaymentExample();
		example.createCriteria().andPAYMENT_REQ_NOEqualTo(reqNo).andPAYMENT_REQ_ITEM_NOEqualTo(reqItemNo).andSEQ_NBREqualTo(seqNo);
		List<TbPayPayment> list = tbPayPaymentMapper.selectByExample(example);
		return list;
	}	

	public TbPayCheckDisregardDTO selectPayCheckDisregardBypk(String disregardNo){
		return uTbPayCheckDisregardMapper.selectDisregardCount(disregardNo);
	}
	
	public TbPayPaymentRequestDtl selectPyamentRequsetDtlBypk(Long reqNo,Short itemNo){
		TbPayPaymentRequestDtlKey key = new TbPayPaymentRequestDtlKey();
		key.setITEM_NO(itemNo);
		key.setPAYMENT_REQ_NO(reqNo);
		TbPayPaymentRequestDtl payment = tbPayPaymentRequestDtlMapper.selectByPrimaryKey(key);
		return payment;
	}
	
	public int updatePayCheckDisregard(TbPayCheckDisregard record){
		return tbPayCheckDisregardMapper.updateByPrimaryKeySelective(record);
	}
	
	public int updatePayCheckDisregardByExample(TbPayCheckDisregard record,TbPayCheckDisregardExample example){
		return tbPayCheckDisregardMapper.updateByExample(record, example);
	}
	
	public int deletePayCheckDisregard(TbPayCheckDisregardKey key){
		return tbPayCheckDisregardMapper.deleteByPrimaryKey(key);
	}
	public List<TbLsMain> selectLsMainByExample(TbLsMainExample example){
		return tbLsMainMapper.selectByExample(example);
	}
	public List<TbLsLocPayment> selectLsLocPaymentByExample(TbLsLocPaymentExample example){
		return tbLsLocPaymentMapper.selectByExample(example);
		
	}
	public TbPayCashRequirement selectCashRequirement(String cashReqNo){
		return tbCashRequirementMapper.selectByPrimaryKey(cashReqNo);
	}
	
}
