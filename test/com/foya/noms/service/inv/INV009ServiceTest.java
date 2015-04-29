package com.foya.noms.service.inv;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.TbInvTr;
import com.foya.exception.NomsException;
import com.foya.noms.GenericTest;
import com.foya.noms.dto.inv.TbInvTrActCompleteDTO;
import com.foya.noms.dto.inv.TbInvTrDtlCompleteDTO;
import com.foya.noms.enums.InvEnumCommon;

public class INV009ServiceTest extends GenericTest {

	@Autowired
	private INV009Service inv009Service;

	// -----------------------------------------------------------------------

	@Test
	public void testGetInvTrActDtoData() {

//		List<TbInvTrActCompleteDTO> invTractDtoList = inv009Service
//				.getInvTrActDtoData("A1234", null, null, null);
//
//		assertFalse(sizeJudge(invTractDtoList.size()));
//		assertNotNull(invTractDtoList);
	}

	@Test
	public void testExecutionCallInAction() {

		List<TbInvTrDtlCompleteDTO> trDtlDtoList = new ArrayList<TbInvTrDtlCompleteDTO>();


		TbInvTr invTr = new TbInvTr();
		invTr.setTr_out_wh_id("12456");
		invTr.setTr_in_wh_id("654321");

		TbInvTrDtlCompleteDTO tbInvTrDtlDto = new TbInvTrDtlCompleteDTO();
//		tbInvTrDtlDto.setInputVal(2);
//		tbInvTrDtlDto.setActExportNumber(2);
//		tbInvTrDtlDto.setInvTr(invTr);
		tbInvTrDtlDto.setTr_no("123");
		tbInvTrDtlDto.setMat_no("M001");
		tbInvTrDtlDto.setTr_dtl_no((long) 1234);
		tbInvTrDtlDto.setMat_status((byte) 2);

		trDtlDtoList.add(tbInvTrDtlDto);

//		boolean result = inv009Service.executionCallInAction(trDtlDtoList,
//				"user", "memo", "1",0,0);
		
//		assertTrue(result);
	}

	@Ignore
	public boolean sizeJudge(int size) {

		boolean judge = false;

		if (size > 0) {
			judge = true;
		}

		return judge;
	}
}
