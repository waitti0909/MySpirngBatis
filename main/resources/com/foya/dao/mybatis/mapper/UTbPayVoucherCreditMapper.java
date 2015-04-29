package com.foya.dao.mybatis.mapper;

import java.util.HashMap;

import com.foya.dao.mybatis.model.TbPayVoucherCredit;

public interface UTbPayVoucherCreditMapper {
	
	int updatePlusTax(HashMap<String , Object> dataObj);
	
	int updatePlusTaxByRecord(TbPayVoucherCredit record);
	
	int insertSelectiveTaxFromFn(TbPayVoucherCredit record);
}