package com.foya.noms.dao.pay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.ProcedureNomsMapper;
import com.foya.dao.mybatis.mapper.TbPayElectricityMapper;
import com.foya.dao.mybatis.mapper.TbPayImpFileContentMapper;
import com.foya.dao.mybatis.mapper.TbPayLookupCodeMapper;
import com.foya.dao.mybatis.mapper.UOriginalPowerBillMapper;
import com.foya.dao.mybatis.mapper.UTbLsLocElecMapper;
import com.foya.dao.mybatis.mapper.UTbLsMainMapper;
import com.foya.dao.mybatis.mapper.UTbPayElectricityMapper;
import com.foya.dao.mybatis.mapper.UTbPayFileFormatDtlMapper;
import com.foya.dao.mybatis.mapper.UTbPayImpFileMapper;
import com.foya.dao.mybatis.model.TbLsLocElec;
import com.foya.dao.mybatis.model.TbLsMain;
import com.foya.dao.mybatis.model.TbPayElectricity;
import com.foya.dao.mybatis.model.TbPayFileFormatDtl;
import com.foya.dao.mybatis.model.TbPayImpFile;
import com.foya.dao.mybatis.model.TbPayImpFileContent;
import com.foya.dao.mybatis.model.TbPayLookupCode;
import com.foya.dao.mybatis.model.TbPayLookupCodeExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.pay.TbLsLocElecDTO;
import com.foya.noms.dto.pay.TbPayElectricityDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Repository
public class Pay002Dao extends BaseDao {

	@Autowired
	private UTbLsLocElecMapper uTbLsLocElecMapper;
	@Autowired
	private TbPayElectricityMapper tbPayElectricityMapper;
	@Autowired
	private UTbLsMainMapper uTbLsMainMapper;
	@Autowired
	private TbPayLookupCodeMapper tbPayLookupCodeMapper;
	@Autowired
	private UTbPayElectricityMapper uTbPayElectricityMapper;
	@Autowired
	private UTbPayFileFormatDtlMapper uTbPayFileFormatDtlMapper;
	@Autowired
	private UTbPayImpFileMapper uTbPayImpFileMapper;
	@Autowired
	private TbPayImpFileContentMapper tbPayImpFileContentMapper;
	@Autowired
	private UOriginalPowerBillMapper uOriginalPowerBillMapper;
	@Autowired
	private ProcedureNomsMapper procedureNomsMapper;
	
	public List<TbPayElectricityDTO> selectTbPayElectricity(HashMap<String,Object> dataObj){
		PageBounds pageBounds = getPageBounds("cashReqNo");// 設定排序欄位
		List<TbPayElectricityDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbPayElectricityMapper.selectTbPayElectricityByPay002",dataObj,  pageBounds);
		PageList<TbPayElectricityDTO> pageList = (PageList<TbPayElectricityDTO>)list;
		return pageList;
	}

	public List<TbPayElectricityDTO> selectTbPayElectricityDtl(HashMap<String,Object> dataObj){
		PageBounds pageBounds = getPageBounds("CONTRACT_NO");// 設定排序欄位
		List<TbPayElectricityDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbPayElectricityMapper.selectTbPayElectricityByPay002Dtl",dataObj,  pageBounds);
		PageList<TbPayElectricityDTO> pageList = (PageList<TbPayElectricityDTO>)list;
		return pageList;
	}
	
	/**
	 * 取得用電度數、用電天數、上期度數 
	 * @param map
	 */
	public void callPayUsedDegreeMDayMap(Map<String, Object> map) {
		uTbPayElectricityMapper.PAY_USED_DEGREE_M_DAY(map);
	}
	
	public TbLsLocElecDTO getChrgMode(HashMap<String,Object> dataObj) {
		return uTbLsLocElecMapper.getChrgMode(dataObj);
	}
	
	public TbLsLocElecDTO getLocationId(HashMap<String,Object> dataObj) {
		return uTbLsLocElecMapper.getLocationId(dataObj);
	}

	public int updateTbPayElectricity(TbPayElectricity record){
		return tbPayElectricityMapper.updateByPrimaryKeySelective(record);
	}

	public List<TbLsLocElec> getLsNoList(){
		return uTbLsLocElecMapper.getLsNoList();
	}

	public List<TbLsLocElec> getEnergyMeterList(String contractNo) {
		return uTbLsLocElecMapper.getEnergyMeterList(contractNo);
	}
	
	public List<TbLsMain> getContractValue(String contractNo) {
		return uTbLsMainMapper.getContractValue(contractNo);
	}

	/*以Type查詢Pay lookup code in*/
	public List<TbPayLookupCode> getPayLookupByTypeAndCodeIn(String type, List<String> values) {
		TbPayLookupCodeExample example = new TbPayLookupCodeExample();
		example.createCriteria().andLOOKUP_TYPEEqualTo(type).andLOOKUP_CODEIn(values);
		return tbPayLookupCodeMapper.selectByExample(example);
	}
	
	public int insertTbPayElectricity(TbPayElectricity record){
		return tbPayElectricityMapper.insertSelective(record);
	}
	
	public List<TbPayFileFormatDtl> getParsingRuleList() {
		return uTbPayFileFormatDtlMapper.getParsingRuleList();
	}
	
	public int insertTbPayImpFile(TbPayImpFile record){
		return uTbPayImpFileMapper.insertSelective(record);
	}
	
	public int insertTbPayImpFileContent(TbPayImpFileContent record){
		return tbPayImpFileContentMapper.insertSelective(record);
	}
	
	public void insertOriginalPowerBill(HashMap<String, Object> originalMap){
		 uOriginalPowerBillMapper.insertSelective(originalMap);
	}
	
	public List<TbLsLocElec> getContractNo(String electricityMeterNbr) {
		return uTbLsLocElecMapper.getContractNo(electricityMeterNbr);
	}
	
	/**
	 * 用電方式=借電_抄表時 取得請款金額-
	 * @param map
	 */
	public void payPcGetElecAmt(Map<String, Object> map)  throws Exception{
		procedureNomsMapper.PAY_PC_GET_ELEC_AMT(map);
		if (!map.get("PO_ERR_CDE").equals("00") && !map.get("PO_ERR_CDE").equals("000")) {
			 log.error("請款金額 Call PAY_PC_GET_ELEC_AMT Error：ERR_CDE="
						+ map.get("PO_ERR_CDE") + ", PO_ERR_MSG="
						+ map.get("PO_ERR_MSG"));
			 throw new Exception("請款金額取得錯誤："+ map.get("PO_ERR_MSG"));
		}
	}
	
}
