package com.foya.noms.service.st;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.TbSiteSearch;
import com.foya.dao.mybatis.model.TbSiteSearchExample;
import com.foya.dao.mybatis.model.TbSiteSearchRing;
import com.foya.noms.GenericTest;
import com.foya.noms.dto.st.SearchRingDTO;
import com.foya.noms.dto.st.SiteSearchDTO;

public class SearchRingServiceTest extends GenericTest {

	@Autowired
	private SearchRingService service;

	@Test
	public void testGetSearchRingList() {
		Map<String ,String> map = new HashMap<String ,String>();
		String srId="1TP00004";
		String coverRange="junitTest";
		String city="50000001";
		String area="50000001";
		String feqId="1";
		double lonValue=123.123188;
		double latValue=123.123188;
		map.put("lonBeing", String.valueOf(lonValue-0.000001));
		map.put("lonEnd", String.valueOf(lonValue+0.000001));
		map.put("latBeing",  String.valueOf(latValue-0.000001));
		map.put("latEnd", String.valueOf(latValue+0.000001));
		map.put("srId", srId);
		map.put("coverRange", coverRange);
		map.put("city", city);
		map.put("area", area);
		map.put("feqId", feqId);
		List<SearchRingDTO> searchRingList = service.getSearchRingList(map);
		Assert.assertTrue(searchRingList.size() == 1);
		SearchRingDTO searchRing = searchRingList.get(0);
		Assert.assertEquals(searchRing.getSR_ID(), srId);
		Assert.assertEquals(searchRing.getSR_COVER_RANGE(), coverRange);
		Assert.assertEquals(searchRing.getSR_CITY(), city);
		Assert.assertEquals(searchRing.getSR_AREA(), area);
		Assert.assertEquals(searchRing.getFEQ_ID(), feqId);
		Assert.assertEquals(searchRing.getSR_LAT(), BigDecimal.valueOf(latValue));
		Assert.assertEquals(searchRing.getSR_LON(), BigDecimal.valueOf(lonValue));
	}

	@Test
	public void testGetSearchRingByPrimaryKey() {
		TbSiteSearchRing searchRing = service.getSearchRingByPrimaryKey("1TP00004");
		Assert.assertNotNull(searchRing);
		Assert.assertEquals(searchRing.getSR_ID(), "1TP00004");
		Assert.assertEquals(searchRing.getSR_COVER_RANGE(), "junitTest");
		Assert.assertEquals(searchRing.getSR_CITY(), "50000001");
		Assert.assertEquals(searchRing.getSR_AREA(), "50000001");
		Assert.assertEquals(searchRing.getFEQ_ID(), "1");
		Assert.assertEquals(searchRing.getSR_LAT(), BigDecimal.valueOf(123.123188));
		Assert.assertEquals(searchRing.getSR_LON(), BigDecimal.valueOf(123.123188));
	}

	@Test
	public void testGetSiteSearchByExample() {
		List<TbSiteSearch> siteSearch = service.getSiteSearchByExample(new TbSiteSearchExample());
		Assert.assertTrue(siteSearch.size()>0);
	}

	@Test
	public void testGetSiteSearchByPk() {
		SiteSearchDTO siteSearch = service.getSiteSearchByPk("1TP00004", "02");
		Assert.assertNotNull(siteSearch);
		Assert.assertEquals(siteSearch.getSR_ID(), "1TP00004");
		Assert.assertEquals(siteSearch.getSR_SEQ(), "02");
	}

	@Test
	public void testInsert(){
		TbSiteSearchRing searchRing = new TbSiteSearchRing();
		searchRing.setFEQ_ID("2");
		searchRing.setSR_CITY("50000002");
		searchRing.setSR_AREA("50000021");
		searchRing.setSR_LON(BigDecimal.valueOf(123.123177));
		searchRing.setSR_LAT(BigDecimal.valueOf(123.123177));
		searchRing.setSR_COVER_RANGE("junitTest");
		searchRing = service.getSearchRingByPrimaryKey(service.insert(searchRing,"junit").getSR_ID());
		Assert.assertNotNull(searchRing);
		Assert.assertEquals(searchRing.getSR_COVER_RANGE(), "junitTest");
		Assert.assertEquals(searchRing.getSR_CITY(), "50000002");
		Assert.assertEquals(searchRing.getSR_AREA(), "50000021");
		Assert.assertEquals(searchRing.getFEQ_ID(), "2");
		Assert.assertEquals(searchRing.getSR_LAT(), BigDecimal.valueOf(123.123177));
		Assert.assertEquals(searchRing.getSR_LON(), BigDecimal.valueOf(123.123177));
	}

	@Test
	public void testUpdate(){
		TbSiteSearchRing searchRing = service.getSearchRingByPrimaryKey("1TP00004");
		String srid=searchRing.getSR_ID();
		String coverRange=searchRing.getSR_COVER_RANGE();
		String city=searchRing.getSR_CITY();
		String area=searchRing.getSR_AREA();
		String feqId=searchRing.getFEQ_ID();
		BigDecimal lon=searchRing.getSR_LON();
		BigDecimal lat=searchRing.getSR_LAT();
		Assert.assertNotNull(searchRing);
		Assert.assertEquals(srid, "1TP00004");
		Assert.assertEquals(coverRange, "junitTest");
		Assert.assertEquals(city, "50000001");
		Assert.assertEquals(area, "50000001");
		Assert.assertEquals(feqId, "1");
		Assert.assertEquals(lat, BigDecimal.valueOf(123.123188));
		Assert.assertEquals(lon, BigDecimal.valueOf(123.123188));
		searchRing.setSR_COVER_RANGE("junitTest2");
		searchRing.setSR_CITY("50000002");
		searchRing.setSR_AREA("50000002");
		searchRing.setFEQ_ID("2");
		searchRing.setSR_LAT(BigDecimal.valueOf(123.123138));
		searchRing.setSR_LON(BigDecimal.valueOf(123.123138));
		service.update(searchRing,"junit");
		searchRing = service.getSearchRingByPrimaryKey("1TP00004");
		Assert.assertNotNull(searchRing);
		Assert.assertEquals(searchRing.getSR_ID(), "1TP00004");
		Assert.assertEquals(searchRing.getSR_COVER_RANGE(), "junitTest2");
		Assert.assertEquals(searchRing.getSR_CITY(), "50000002");
		Assert.assertEquals(searchRing.getSR_AREA(), "50000002");
		Assert.assertEquals(searchRing.getFEQ_ID(), "2");
		Assert.assertEquals(searchRing.getSR_LAT(), BigDecimal.valueOf(123.123138));
		Assert.assertEquals(searchRing.getSR_LON(), BigDecimal.valueOf(123.123138));
		searchRing.setSR_COVER_RANGE(coverRange);
		searchRing.setSR_CITY(city);
		searchRing.setSR_AREA(area);
		searchRing.setFEQ_ID(feqId);
		searchRing.setSR_LAT(lat);
		searchRing.setSR_LON(lon);
		service.update(searchRing,"junit");
		searchRing = service.getSearchRingByPrimaryKey("1TP00004");
		Assert.assertNotNull(searchRing);
		Assert.assertEquals(searchRing.getSR_ID(), "1TP00004");
		Assert.assertEquals(searchRing.getSR_COVER_RANGE(), coverRange);
		Assert.assertEquals(searchRing.getSR_CITY(), city);
		Assert.assertEquals(searchRing.getSR_AREA(), area);
		Assert.assertEquals(searchRing.getFEQ_ID(), feqId);
		Assert.assertEquals(searchRing.getSR_LAT(), lat);
		Assert.assertEquals(searchRing.getSR_LON(), lon);
	}

}
