package com.foya.noms.dao.pay;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.ProcedureNomsMapper;
import com.foya.dao.mybatis.mapper.TbLsMainMapper;
import com.foya.dao.mybatis.mapper.TbPayContractInfoMapper;
import com.foya.dao.mybatis.mapper.TbPayElectricityMapper;
import com.foya.dao.mybatis.mapper.TbPayExpenseEstimateDtlMapper;
import com.foya.dao.mybatis.mapper.TbPayExpenseEstimateMapper;
import com.foya.dao.mybatis.mapper.TbSiteLineApplyMapper;
import com.foya.dao.mybatis.mapper.TbSiteLocationMapper;
import com.foya.dao.mybatis.mapper.UTbLsMainMapper;
import com.foya.dao.mybatis.mapper.UTbPayContractInfoMapper;
import com.foya.dao.mybatis.mapper.UTbPayLocationInfoMapper;
import com.foya.dao.mybatis.mapper.UTbPayPaymentRequestMapper;
import com.foya.dao.mybatis.model.TbLsMain;
import com.foya.dao.mybatis.model.TbLsMainExample;
import com.foya.dao.mybatis.model.TbPayContractInfo;
import com.foya.dao.mybatis.model.TbPayContractInfoKey;
import com.foya.dao.mybatis.model.TbPayElectricity;
import com.foya.dao.mybatis.model.TbPayElectricityExample;
import com.foya.dao.mybatis.model.TbPayExpenseEstimate;
import com.foya.dao.mybatis.model.TbPayExpenseEstimateDtl;
import com.foya.dao.mybatis.model.TbPayExpenseEstimateExample;
import com.foya.dao.mybatis.model.TbSiteLineApply;
import com.foya.dao.mybatis.model.TbSiteLineApplyExample;
import com.foya.dao.mybatis.model.TbSiteLocation;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.ls.LeaseMainDTO;
import com.foya.noms.dto.ls.SiteAlocDetailDTO;
import com.foya.noms.dto.pay.EstimateExcelDTO;
import com.foya.noms.dto.pay.TbPayLocationInfoDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestDTO;

@Repository
public class Pay012Dao extends BaseDao {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private TbPayExpenseEstimateDtlMapper tbPayExpenseEstimateDtlMapper;
	@Autowired
	private TbPayExpenseEstimateMapper tbPayExpenseEstimateMapper;
	@Autowired
	private UTbPayLocationInfoMapper uTbPayLocationInfoMapper;
	@Autowired
	private TbSiteLocationMapper tbSiteLocationMapper;
	@Autowired
	private TbLsMainMapper tbLsMainMapper;
	@Autowired
	private UTbPayPaymentRequestMapper uTbPayPaymentRequestMapper;
	@Autowired
	private UTbLsMainMapper uTbLsMainMapper;
	@Autowired
	private TbPayElectricityMapper tbPayElectricityMapper;
	@Autowired
	private TbPayContractInfoMapper tbPayContractInfoMapper;
	@Autowired
	private UTbPayContractInfoMapper uTbPayContractInfoMapper;
	@Autowired
	private TbSiteLineApplyMapper tbSiteLineApplyMapper;
	@Autowired
	private ProcedureNomsMapper procedureNomsMapper;
	public void payPcGetSeqnoByMap(Map<String, Object> map) {
		procedureNomsMapper.PAY_PC_GET_SEQNO(map);
	}
	public List<TbPayExpenseEstimate> selectEstimate(TbPayExpenseEstimateExample example) {
		return tbPayExpenseEstimateMapper.selectByExample(example);
	}
	
	public int deleteEstimate(String ESTIMATE_NO){
		return tbPayExpenseEstimateMapper.deleteByPrimaryKey(ESTIMATE_NO);
	}
	
	public int insertDtl(TbPayExpenseEstimateDtl record) {
		return tbPayExpenseEstimateDtlMapper.insertSelective(record);
	}
	
	public int insertMas(TbPayExpenseEstimate record) {
		return tbPayExpenseEstimateMapper.insertSelective(record);
	}
	
	public List<TbPayLocationInfoDTO> selectReeData() {
		return uTbPayLocationInfoMapper.estimateReeData();
	}
	
	public List<SiteAlocDetailDTO> getLsSiteData(HashMap<String,Object> map) {
		return uTbPayLocationInfoMapper.getLsSiteData(map);
	}
	
	public List<SiteAlocDetailDTO> getLsApplyData(HashMap<String,Object> map) {
		return uTbPayLocationInfoMapper.getLsApplyData(map);
	}
	
	public List<TbPayElectricity> getElectricityData(TbPayElectricityExample example) {
		return tbPayElectricityMapper.selectByExample(example);
	}
	
	public TbSiteLocation getLocationByKey(String locationId) {
		return tbSiteLocationMapper.selectByPrimaryKey(locationId);
	}
	
	public List<TbLsMain> getLsMainByExample(TbLsMainExample example) {
		return tbLsMainMapper.selectByExample(example);
	}
	
	public List<TbPayPaymentRequestDTO> selectEleData() {
		return uTbPayPaymentRequestMapper.selectEleData();
	}
	public List<TbPayPaymentRequestDTO> selectLieData() {
		return uTbPayPaymentRequestMapper.selectLieData();
	}
	public LeaseMainDTO getMaxEndDate(String lsNo) {
		return uTbLsMainMapper.getMaxEndDate(lsNo);
	}
	
	public List<TbPayPaymentRequestDTO> getEleAmt(HashMap<String,Object> dataObj) {
		return uTbPayPaymentRequestMapper.getEleAmt(dataObj);
	}
	
	public List<TbPayPaymentRequestDTO> getLieAmt(HashMap<String,Object> dataObj) {
		return uTbPayPaymentRequestMapper.getLieAmt(dataObj);
	}
	
	public List<TbSiteLineApply> getSiteLineApplyData(TbSiteLineApplyExample example) {
		return tbSiteLineApplyMapper.selectByExample(example);
	}
	public TbPayContractInfo selectContractInfo(TbPayContractInfoKey key) {
		return tbPayContractInfoMapper.selectByPrimaryKey(key);
	}
	public EstimateExcelDTO selectSiteEqpName(HashMap<String , Object> dataObj) {
		return uTbPayContractInfoMapper.selectSiteEqpName(dataObj);
	}
	public EstimateExcelDTO selectSiteEqpModel(HashMap<String , Object> dataObj) {
		return uTbPayContractInfoMapper.selectSiteEqpModel(dataObj);
	}
}
