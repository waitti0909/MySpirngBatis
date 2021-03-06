package com.foya.noms.service.st;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.TbSiteLocation;
import com.foya.noms.GenericTest;
import com.foya.noms.dto.st.SiteLocationDTO;

public class ST001ServiceTest extends GenericTest {

	@Autowired
	private ST001Service service;

	@Test
	public void testGetByCondition(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("locationId", "S20142014001");
		map.put("btsSiteId", "BTS201420141");
		map.put("locName", "測試Location");
		map.put("locType", "B");
		map.put("locCity", "50000001");
		map.put("locTown", "50000006");
		List<SiteLocationDTO> siteLocationList = service.getByCondition(map);
		SiteLocationDTO siteLocation = siteLocationList.get(0);
		Assert.assertTrue(siteLocationList.size()>0);
		Assert.assertEquals(siteLocation.getLOCATION_ID(), "S20142014001");
		Assert.assertEquals(siteLocation.getBtsSiteId(), "BTS201420141");
		Assert.assertEquals(siteLocation.getLOC_NAME(), "測試Location");
		Assert.assertEquals(siteLocation.getLOC_TYPE(), "B");
		Assert.assertEquals(siteLocation.getCITY(), "50000001");
		Assert.assertEquals(siteLocation.getAREA(), "50000006");
	}
	
	@Test
	public void testGetSiteLocationByLocationId(){
		TbSiteLocation siteLocation = service.getSiteLocationByLocationId("S20142014001");
		Assert.assertNotNull(siteLocation);
		Assert.assertEquals(siteLocation.getLOCATION_ID(), "S20142014001");
	}
	
	@Test
	public void testGetMaintainAreaByCityIdTownId(){
		Map<String, String> map = service.getMaintainAreaByCityIdTownId("50000001", "50000006");
		Assert.assertTrue(map.size()>0);
	}
	
	@Test
	public void testGetMaintainDeptByDomain(){
		Map<String, String> map = service.getMaintainDeptByDomain("N1");
		Assert.assertTrue(map.size()>0);
	}
	
	@Test
	public void testGetMaintainTeamByDeptId(){
		Map<String, String> map = service.getMaintainTeamByDeptId("440300");
		Assert.assertTrue(map.size()>0);
	}
	
	@Test
	public void testGetMaintainUserByDeptId() {
		Map<String, String> map = service.getMaintainUserByDeptId("440300");
		Assert.assertTrue(map.size()>0);
	}
	
	@Test
	public void testInsert(){
		String[] shareComArray = {"CHT","FET","TWM","APT"};
		TbSiteLocation record = new TbSiteLocation();
		record.setLOC_NAME("junitTest");
		record.setLOC_TYPE("M");
		record.setLAT(BigDecimal.valueOf(123.123456));
		record.setLON(BigDecimal.valueOf(123.123456));
		record.setMAINT_AREA("N1");
		record.setMAINT_DEPT("440300");
		record.setMAINT_TEAM("G2100");
		record.setMAINT_USER("P07123320");
		record.setLOC_DESC("補充說明");
		record.setBASE_TYPE("RTB");
		record.setINDOOR_OPTION("SPT");
		record.setNS_LEVEL("VA");
		record.setBUILDING_HEIGHT(BigDecimal.valueOf(123.1));
		record.setSHARE_TYPE("1");
		
		record.setIS_FREEENTER("Y");
		record.setIS_KEY("Y");
		record.setINDOOR_OPTION("Y");
		record.setNEED_MISC_LIC("Y");
		record.setHAS_MISC_LIC("Y");
		record.setSR_ID("SE20152015");
		record.setADDR("１０８ 台北市萬華區長沙街２段 ９６號 ３樓");
		record.setZIP("108");
		record.setCITY("50000001");
		record.setAREA("50000006");
		record.setVILLAGE("村里");
		record.setADJACENT("1");
		record.setROAD("土圍路");
		record.setLANE("2");
		record.setALLEY("3");
		record.setSUB_ALLEY("4");
		record.setDOOR_NO("5");
		record.setDOOR_NO_EX("6");
		record.setFLOOR("7");
		record.setFLOOR_EX("8");
		record.setROOM("9");
		record.setADD_OTHER("其他資訊");
		TbSiteLocation location = service.insert(record,shareComArray,"junit");
		Assert.assertTrue(location!=null);
	}
	
	@Test
	public void testUpdate(){
		TbSiteLocation record = service.getSiteLocationByLocationId("A70920140001");
		String name = record.getLOC_NAME();
		String locType = record.getLOC_TYPE();
		BigDecimal lat = record.getLAT();
		BigDecimal lon =record.getLON();
		String maintArea = record.getMAINT_AREA();
		String maintDept = record.getMAINT_DEPT();
		String maintTeam = record.getMAINT_TEAM();
		String maintUser = record.getMAINT_USER();
		String desc = record.getLOC_DESC();
		String baseType= record.getBASE_TYPE();
		String indoorOption = record.getINDOOR_OPTION();
		String nsLevel = record.getNS_LEVEL();
		BigDecimal buildingHeight = record.getBUILDING_HEIGHT();
		String shareType = record.getSHARE_TYPE();
		String isFreeenter = record.getIS_FREEENTER();
		String isKey = record.getIS_KEY();
		String isIndoor = record.getIS_INDOOR();
		String needMiscLic = record.getNEED_MISC_LIC();
		String hasMiscLic = record.getHAS_MISC_LIC();
		String srId = record.getSR_ID();
		String addr = record.getADDR();
		String zip = record.getZIP();
		String city = record.getCITY();
		String area = record.getAREA();
		String village = record.getVILLAGE();
		String adjacent = record.getADJACENT();
		String road = record.getROAD();
		String lane = record.getLANE();
		String alley = record.getALLEY();
		String subAlley = record.getSUB_ALLEY();
		String doorNo = record.getDOOR_NO();
		String doorNoEx = record.getDOOR_NO_EX();
		String floor = record.getFLOOR();
		String floorEx = record.getFLOOR_EX();
		String room = record.getROOM();
		String addOther = record.getADD_OTHER();
		record.setLOC_NAME("junitTest2");
		record.setLOC_TYPE("M");
		record.setLAT(BigDecimal.valueOf(123.654321));
		record.setLON(BigDecimal.valueOf(123.654321));
		record.setMAINT_AREA("N1");
		record.setMAINT_DEPT("440300");
		record.setMAINT_TEAM("G2100");
		record.setMAINT_USER("P07123320");
		record.setLOC_DESC("補充說明");
		record.setBASE_TYPE("RTC");
		record.setINDOOR_OPTION("ENT");
		record.setNS_LEVEL("RU");
		record.setBUILDING_HEIGHT(BigDecimal.valueOf(123.9));
		record.setSHARE_TYPE("2");
		String[] shareComArray = {"FET","TWM"};
//		record.setSHARE_COM("FET,TWM");
		record.setIS_FREEENTER("Y");
		record.setIS_KEY("Y");
		record.setINDOOR_OPTION("Y");
		record.setNEED_MISC_LIC("Y");
		record.setHAS_MISC_LIC("Y");
		record.setSR_ID("SE20152015");
		record.setADDR("１０８ 台北市萬華區長沙街２段 ９６號 ３樓");
		record.setZIP("108");
		record.setCITY("50000001");
		record.setAREA("50000006");
		record.setVILLAGE("村里");
		record.setADJACENT("1");
		record.setROAD("土圍路");
		record.setLANE("2");
		record.setALLEY("3");
		record.setSUB_ALLEY("4");
		record.setDOOR_NO("5");
		record.setDOOR_NO_EX("6");
		record.setFLOOR("7");
		record.setFLOOR_EX("8");
		record.setROOM("9");
		record.setADD_OTHER("其他資訊");
		service.update(record,shareComArray);
		record = service.getSiteLocationByLocationId("A70920140001");
		Assert.assertTrue(record!=null);
		
	}
}
