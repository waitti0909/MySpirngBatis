package com.foya.noms.service.pay;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.foya.dao.mybatis.mapper.TbAuthPersonnelMapper;
import com.foya.dao.mybatis.mapper.TbLsLessorMapper;
import com.foya.dao.mybatis.mapper.TbNomsApBankInterfaceMapper;
import com.foya.dao.mybatis.mapper.TbNomsApInvoiceLinesTempMapper;
import com.foya.dao.mybatis.mapper.TbNomsApInvoiceTempMapper;
import com.foya.dao.mybatis.mapper.TbNomsGvInterfaceTempMapper;
import com.foya.dao.mybatis.mapper.TbNomsVoidedCheckTempMapper;
import com.foya.dao.mybatis.mapper.TbPayCashRequirementMapper;
import com.foya.dao.mybatis.mapper.TbPayCheckDisregardMapper;
import com.foya.dao.mybatis.mapper.TbPayElectricityMapper;
import com.foya.dao.mybatis.mapper.TbPayPaymentRequestVoucherMapper;
import com.foya.dao.mybatis.mapper.TbPayVoucherCreditMapper;
import com.foya.dao.mybatis.mapper.UTbPayPaymentMapper;
import com.foya.dao.mybatis.model.TbPayCashRequirement;
import com.foya.noms.GenericTest;
import com.foya.noms.dto.pay.TbPayPaymentDTO;

public class PayInsertERPServiceTest  extends GenericTest {
	@Autowired
	private PayInsertERPService payInsertERPService;
	@Autowired
	private PayPublicUtilService payPublicUtilService;
	@Autowired
	private TbPayCashRequirementMapper tbPayCashRequirementMapper;
	@Autowired
	private UTbPayPaymentMapper utbPayPaymentMapper;
	@Autowired
	private TbPayElectricityMapper tbPayElectricityMapper;
	@Autowired
	private TbLsLessorMapper tbLsLessorMapper;
	@Autowired
	private TbPayVoucherCreditMapper tbPayVoucherCreditMapper;
	@Autowired
	private TbPayPaymentRequestVoucherMapper tbPayPaymentRequestVoucherMapper;
	@Autowired
	private TbNomsApInvoiceTempMapper tbNomsApInvoiceTempMapper;
	@Autowired
	private TbNomsApInvoiceLinesTempMapper tbNomsApInvoiceLinesTempMapper;
	@Autowired
	private TbNomsApBankInterfaceMapper tbNomsApBankInterfaceMapper;
	@Autowired
	private TbNomsGvInterfaceTempMapper tbNomsGvInterfaceTempMapper;
	@Autowired
	private TbPayCheckDisregardMapper tbPayCheckDisregardMapper;
	@Autowired
	private TbAuthPersonnelMapper tbAuthPersonnelMapper;
	@Autowired
	private TbNomsVoidedCheckTempMapper tbNomsVoidedCheckTempMapper;
	
	//@Test
	public void selectByPrimaryKey() {
		TbPayCashRequirement tbPayCashRequirement = tbPayCashRequirementMapper.selectByPrimaryKey("ELP201503180003");
	    System.out.println("~" + tbPayCashRequirement.getYEAR_MONTH());
	}
	
    
	
	//@Test
	//@Rollback(false)
	public void insertERP1() {
		TbPayCashRequirement tbPayCashRequirement = tbPayCashRequirementMapper.selectByPrimaryKey("ELP201503180003");
		List<TbPayPaymentDTO> list = utbPayPaymentMapper.selectByCashReqNo1("ELP201503180003");
		for (int i = 0; i < list.size(); i++) {
			payInsertERPService.insertNomsApInvoiceTemp(tbPayCashRequirement,list,i);
		}
		System.out.println("~" + list.size());
	}
	
	//@Test
	//@Rollback(false)
	public void insertERP2() {
		TbPayCashRequirement tbPayCashRequirement = tbPayCashRequirementMapper.selectByPrimaryKey("ELP201503180003");
		List<TbPayPaymentDTO> list = utbPayPaymentMapper.selectByCashReqNo1("ELP201503180003");
		for (int i = 0; i < list.size(); i++) {
			payInsertERPService.insertNomsApInvoiceLinesTemp(tbPayCashRequirement,list,i);
		}
		System.out.println("~" + list.size());
	}
	
	//@Test
	//@Rollback(false)
	public void insertERP3() {
		TbPayCashRequirement tbPayCashRequirement = tbPayCashRequirementMapper.selectByPrimaryKey("REP201502100010");
		List<TbPayPaymentDTO> list = utbPayPaymentMapper.selectByCashReqNo2("REP201502100010");
		for (int i = 0; i < list.size(); i++) {
			payInsertERPService.insertNomsApBankInterface(tbPayCashRequirement,list,i);
		}
		System.out.println("~" + list.size());
	}
}
