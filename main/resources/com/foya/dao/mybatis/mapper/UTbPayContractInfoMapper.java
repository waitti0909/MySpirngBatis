package com.foya.dao.mybatis.mapper;

import java.util.HashMap;
import java.util.List;

import com.foya.noms.dto.pay.EstimateExcelDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestDTO;

public interface UTbPayContractInfoMapper {
	
	List<TbPayPaymentRequestDTO> selectPayContractInfo4REN01(HashMap<String , Object> dataObj);
	
	List<TbPayPaymentRequestDTO> selectPayContractInfo4REN02(HashMap<String , Object> dataObj);
	
	List<TbPayPaymentRequestDTO> selectPayContractInfo4REN03(HashMap<String , Object> dataObj);
	
	List<TbPayPaymentRequestDTO> selectPayContractInfo4REN04(HashMap<String , Object> dataObj);
	
	//pay003使用
	
	List<TbPayPaymentRequestDTO> selectPayContractInfo4ELE01(HashMap<String , Object> dataObj);
	
	List<TbPayPaymentRequestDTO> selectPayContractInfo4ELE05(HashMap<String , Object> dataObj);
	
	List<TbPayPaymentRequestDTO> selectPayContractInfo4ELEOther(HashMap<String , Object> dataObj);
	
	//費用預估作業 基站資料
	EstimateExcelDTO selectSiteEqpName(HashMap<String , Object> dataObj);

	EstimateExcelDTO selectSiteEqpModel(HashMap<String , Object> dataObj);
}