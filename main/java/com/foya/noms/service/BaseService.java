package com.foya.noms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.foya.noms.dto.auth.UserDTO;

/**
 * The Class BaseService.
 */
public class BaseService {

	/** The log. */
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	/**
	 * Gets the login user.
	 *
	 * @return the login user
	 */
	protected UserDTO getLoginUser() {
		
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		
		if(auth.getPrincipal() instanceof UserDTO){
			UserDTO user = (UserDTO) auth.getPrincipal();
			return user;
		}
		return null;
	}
}
