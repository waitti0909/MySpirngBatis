package com.foya.noms.security.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import com.foya.noms.GenericTest;

public class UserDetailsServiceImplTest extends GenericTest {

	@Autowired
	private UserDetailsServiceImpl service;
	
	@Test
	public void testLoadUserByUsername(){
		UserDetails ueser = service.loadUserByUsername("junit");
		Assert.assertNotNull(ueser);
		Assert.assertEquals(ueser.getUsername(), "junit");
	}

}
