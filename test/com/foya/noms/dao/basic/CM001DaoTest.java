package com.foya.noms.dao.basic;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.TbComCompany;
import com.foya.noms.GenericTest;
import com.foya.noms.util.DateUtils;

public class CM001DaoTest extends GenericTest {

	@Autowired
	private CM001Dao dao;
	
	@Test
	public void testSearchCompanyByVatName() {
		String[] type = {"Y","N"};
		List<TbComCompany> company = dao.searchCompanyByVatName("12345678", "junitTest", type);
		Assert.assertNotNull(company);
		Assert.assertTrue(company.size()>0);
		Assert.assertEquals(company.get(0).getCO_VAT_NO(), "12345678");
		Assert.assertEquals(company.get(0).getCO_NAME(), "junitTest");
		Assert.assertEquals(company.get(0).getTYPE_ENG(), "Y");
		Assert.assertEquals(company.get(0).getTYPE_EQU(), "N");
	}
	
	@Test
	public void testSelectByPrimaryKey(){
		TbComCompany company = dao.selectByPrimaryKey("12345678");
		Assert.assertNotNull(company);
		Assert.assertEquals(company.getCO_VAT_NO(), "12345678");
	}
	
	@Test
	public void testSaveNewCompany(){
		TbComCompany company = new TbComCompany();
		Date date = DateUtils.time(2014, 9, 30, 10, 50, 0);
		company.setCO_VAT_NO("87654321");
		company.setCO_NAME("junitTest");
		company.setTYPE_ENG("Y");
		company.setTYPE_EQU("N");
		company.setCO_SITE_TYPE("B");
		company.setCO_TEL("02-23810455");
		company.setCO_FAX("02-23810455");
		company.setCONTACT("junit");
		company.setCON_TITLE("junitTitle");
		company.setCON_MOBILE("0912345678");
		company.setCON_EMAIL("junit@gmail.com");
		company.setCTR_RELATION("Y");
		company.setACTIVATE("N");
		company.setCTR_TERM("123");
		company.setINSURANCE("Y");
		company.setINS_AMOUNT(BigDecimal.valueOf(500));
		company.setERP_ID("5");
		company.setCR_TIME(date);
		company.setCR_USER("junit");
		company.setADDR("709 台南市安南區安中路二段 25鄰 17巷 12弄 21衖  117號之1");
		company.setZIP("709");
		company.setCITY("台南市");
		company.setAREA("安南區");
		company.setROAD("安中路二段");
		company.setADJACENT("25");
		company.setLANE("17");
		company.setALLEY("12");
		company.setSUB_ALLEY("21");
		company.setDOOR_NO("117");
		company.setDOOR_NO_EX("1");
		company.setFLOOR_EX("3");
		company.setROOM("1");
		company.setMEMO("junitTest");
		dao.saveNewCompany(company);
		company = dao.getCompanyDetailByID("87654321");
		Assert.assertNotNull(company);
		Assert.assertEquals(company.getCO_VAT_NO(), "87654321");
	}

	@Test
	public void testGetCompanyDetailByID(){
		TbComCompany company = dao.getCompanyDetailByID("12345678");
		Assert.assertNotNull(company);
		Assert.assertEquals(company.getCO_VAT_NO(), "12345678");
	}
	

	@Test
	public void testSaveUpdateCompany(){
		TbComCompany company = dao.getCompanyDetailByID("12345678");
		Date date = DateUtils.time(2014, 9, 30, 10, 50, 0);
		String coName = company.getCO_NAME();
		String typeEng = company.getTYPE_ENG();
		String typeEqu = company.getTYPE_EQU();
		String coTel = company.getCO_TEL();
		String coFax = company.getCO_FAX();
		String coSiteType = company.getCO_SITE_TYPE();
		String contact = company.getCONTACT();
		String conTitle = company.getCON_TITLE();
		String conMobile = company.getCON_MOBILE();
		String conEmail = company.getCON_EMAIL();
		String ctrRelation = company.getCTR_RELATION();
		String activate = company.getACTIVATE();
		String ctrTerm = company.getCTR_TERM();
		String insurance = company.getINSURANCE();
		BigDecimal insAmount =company.getINS_AMOUNT();
		String erpId = company.getERP_ID();
		Date crTime = company.getCR_TIME();
		String crUser = company.getCR_USER();
		String addr = company.getADDR();
		String zip = company.getZIP();
		String city = company.getCITY();
		String area = company.getAREA();
		String road = company.getROAD();
		String adjacent = company.getADJACENT();
		String lane = company.getLANE();
		String alley = company.getALLEY();
		String subAlley = company.getSUB_ALLEY();
		String doorNo = company.getDOOR_NO();
		String doorNoEx = company.getDOOR_NO_EX();
		String floor = company.getFLOOR();
		String floorEx = company.getFLOOR_EX();
		String room = company.getROOM();
		String memo = company.getMEMO();
		company.setCO_NAME("junitTest2");
		company.setTYPE_ENG("N");
		company.setTYPE_EQU("Y");
		company.setCO_SITE_TYPE("S");
		company.setCO_TEL("02-23456789");
		company.setCO_FAX("02-23810455");
		company.setCONTACT("junitContact");
		company.setCON_TITLE("junitTitle");
		company.setCON_MOBILE("0987654321");
		company.setCON_EMAIL("junitTest@gmail.com");
		company.setCTR_RELATION("Y");
		company.setACTIVATE("N");
		company.setCTR_TERM("111");
		company.setINSURANCE("N");
		company.setINS_AMOUNT(BigDecimal.valueOf(100));
		company.setERP_ID("3");
		company.setCR_TIME(date);
		company.setCR_USER("junitTest");
		company.setADDR("709 台南市安南區安中路二段 25鄰 17巷 12弄 21衖  117號之1");
		company.setZIP("709");
		company.setCITY("台南市");
		company.setAREA("安南區");
		company.setROAD("安中路二段");
		company.setADJACENT("25");
		company.setLANE("17");
		company.setALLEY("12");
		company.setSUB_ALLEY("21");
		company.setDOOR_NO("117");
		company.setDOOR_NO_EX("1");
		company.setFLOOR("1");
		company.setFLOOR_EX("2");
		company.setROOM("1");
		company.setMEMO("junitTest");
		dao.saveUpdateCompany(company);
		company = dao.getCompanyDetailByID("12345678");
		Assert.assertNotNull(company);
		Assert.assertEquals(company.getCO_VAT_NO(), "12345678");
		Assert.assertEquals(company.getCO_NAME(), "junitTest2");
		Assert.assertEquals(company.getTYPE_ENG(), "N");
		Assert.assertEquals(company.getTYPE_EQU(), "Y");
		Assert.assertEquals(company.getCO_SITE_TYPE(), "S");
		Assert.assertEquals(company.getCO_TEL(), "02-23456789");
		Assert.assertEquals(company.getCO_FAX(), "02-23810455");
		Assert.assertEquals(company.getCONTACT(), "junitContact");
		Assert.assertEquals(company.getCON_TITLE(), "junitTitle");
		Assert.assertEquals(company.getCON_MOBILE(), "0987654321");
		Assert.assertEquals(company.getCON_EMAIL(), "junitTest@gmail.com");
		Assert.assertEquals(company.getCTR_RELATION(), "Y");
		Assert.assertEquals(company.getACTIVATE(), "N");
		Assert.assertEquals(company.getCTR_TERM(), "111");
		Assert.assertEquals(company.getINSURANCE(), "N");
		Assert.assertEquals(company.getINS_AMOUNT(), BigDecimal.valueOf(100));
		Assert.assertEquals(company.getERP_ID(), "3");
		Assert.assertEquals(company.getCR_TIME(), date);
		Assert.assertEquals(company.getCR_USER(), "junitTest");
		Assert.assertEquals(company.getADDR(), "709 台南市安南區安中路二段 25鄰 17巷 12弄 21衖  117號之1");
		Assert.assertEquals(company.getZIP(), "709");
		Assert.assertEquals(company.getCITY(), "台南市");
		Assert.assertEquals(company.getAREA(), "安南區");
		Assert.assertEquals(company.getROAD(), "安中路二段");
		Assert.assertEquals(company.getADJACENT(), "25");
		Assert.assertEquals(company.getLANE(), "17");
		Assert.assertEquals(company.getALLEY(), "12");
		Assert.assertEquals(company.getSUB_ALLEY(), "21");
		Assert.assertEquals(company.getDOOR_NO(), "117");
		Assert.assertEquals(company.getDOOR_NO_EX(), "1");
		Assert.assertEquals(company.getFLOOR(), "1");
		Assert.assertEquals(company.getFLOOR_EX(), "2");
		Assert.assertEquals(company.getROOM(), "1");
		Assert.assertEquals(company.getMEMO(), "junitTest");
		company.setCO_NAME(coName);
		company.setTYPE_ENG(typeEng);
		company.setTYPE_EQU(typeEqu);
		company.setCO_SITE_TYPE(coSiteType);
		company.setCO_TEL(coTel);
		company.setCO_FAX(coFax);
		company.setCONTACT(contact);
		company.setCON_TITLE(conTitle);
		company.setCON_MOBILE(conMobile);
		company.setCON_EMAIL(conEmail);
		company.setCTR_RELATION(ctrRelation);
		company.setACTIVATE(activate);
		company.setCTR_TERM(ctrTerm);
		company.setINSURANCE(insurance);
		company.setINS_AMOUNT(insAmount);
		company.setERP_ID(erpId);
		company.setCR_TIME(crTime);
		company.setCR_USER(crUser);
		company.setADDR(addr);
		company.setZIP(zip);
		company.setCITY(city);
		company.setAREA(area);
		company.setROAD(road);
		company.setADJACENT(adjacent);
		company.setLANE(lane);
		company.setALLEY(alley);
		company.setSUB_ALLEY(subAlley);
		company.setDOOR_NO(doorNo);
		company.setDOOR_NO_EX(doorNoEx);
		company.setFLOOR(floor);
		company.setFLOOR_EX(floorEx);
		company.setROOM(room);
		company.setMEMO(memo);
		dao.saveUpdateCompany(company);
		company = dao.getCompanyDetailByID("12345678");
		Assert.assertNotNull(company);
		Assert.assertEquals(company.getCO_VAT_NO(), "12345678");
		Assert.assertEquals(company.getCO_NAME(), coName);
		Assert.assertEquals(company.getTYPE_ENG(), typeEng);
		Assert.assertEquals(company.getTYPE_EQU(), typeEqu);
		Assert.assertEquals(company.getCO_SITE_TYPE(), coSiteType);
		Assert.assertEquals(company.getCO_TEL(), coTel);
		Assert.assertEquals(company.getCO_FAX(), coFax);
		Assert.assertEquals(company.getCONTACT(), contact);
		Assert.assertEquals(company.getCON_TITLE(), conTitle);
		Assert.assertEquals(company.getCON_MOBILE(), conMobile);
		Assert.assertEquals(company.getCON_EMAIL(), conEmail);
		Assert.assertEquals(company.getCTR_RELATION(), ctrRelation);
		Assert.assertEquals(company.getACTIVATE(), activate);
		Assert.assertEquals(company.getCTR_TERM(), ctrTerm);
		Assert.assertEquals(company.getINSURANCE(), insurance);
		Assert.assertEquals(company.getINS_AMOUNT(), insAmount);
		Assert.assertEquals(company.getERP_ID(), erpId);
		Assert.assertEquals(company.getCR_TIME(), crTime);
		Assert.assertEquals(company.getCR_USER(), crUser);
		Assert.assertEquals(company.getADDR(), addr);
		Assert.assertEquals(company.getZIP(), zip);
		Assert.assertEquals(company.getCITY(), city);
		Assert.assertEquals(company.getAREA(), area);
		Assert.assertEquals(company.getROAD(), road);
		Assert.assertEquals(company.getADJACENT(), adjacent);
		Assert.assertEquals(company.getLANE(), lane);
		Assert.assertEquals(company.getALLEY(), alley);
		Assert.assertEquals(company.getSUB_ALLEY(), subAlley);
		Assert.assertEquals(company.getDOOR_NO(), doorNo);
		Assert.assertEquals(company.getDOOR_NO_EX(), doorNoEx);
		Assert.assertEquals(company.getFLOOR(), floor);
		Assert.assertEquals(company.getFLOOR_EX(), floorEx);
		Assert.assertEquals(company.getROOM(), room);
		Assert.assertEquals(company.getMEMO(), memo);
	}

}
