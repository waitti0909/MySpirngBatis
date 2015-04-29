package com.foya.noms.service.ls;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.noms.GenericTest;
import com.foya.noms.dto.ls.SiteAlocDetailDTO;
import com.foya.noms.enums.SiteStatus;

public class SiteAlocServiceTest extends GenericTest {
	
	@Autowired
	SiteAlocService siteAlocService;

	@Test
	public void testGetSiteMainByLocationId() {
		String locationId= "S20142014001";
		siteAlocService.getSiteMainByLocationId(locationId, SiteStatus.getAvailableSiteStatus());
	}

	@Test
	public void testGetNormalSiteAlocByLocationId() {
		String locationId= "S20142014001";
		String alocItem = "R";
		List<SiteAlocDetailDTO>  a =siteAlocService.getNormalSiteAlocByLocationId(locationId, alocItem);
		
		assertTrue(a.size()>0);
		
		
		
	}

	@Test
	public void testGetExchangeSiteAlocByLocationId() {
		fail("Not yet implemented");
	}

}
