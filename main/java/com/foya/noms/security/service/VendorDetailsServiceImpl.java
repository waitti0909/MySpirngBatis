package com.foya.noms.security.service;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.foya.noms.dao.auth.PersonnelDao;
import com.foya.noms.dto.auth.UserDTO;

public class VendorDetailsServiceImpl implements UserDetailsService {

	private static Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	private PersonnelDao personnelDao;
	
//	private UserMapper userMapper;
//	
//	public UserMapper getUserMapper() {
//		return userMapper;
//	}
//
//	public void setUserMapper(UserMapper userMapper) {
//		this.userMapper = userMapper;
//	}

	/**
	 * @param account
	 *            登錄帳號
	 */
	@Override
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		log.info("[LOG]登錄賬號：" + userName);
//		org.springframework.security.core.userdetails.User userDetails = null;
		try {
//			User user = userMapper.selectByAccount(userName);
//			UserDTO user = personnelDao.selectLoginUserByPsnNo(userName, new Date());
			UserDTO user = personnelDao.selectLoginUserByEName(userName, new Date());	// 20150304 modify by Charlie
			if ( user == null || !"V".equals(user.getPsnType())) {
				throw new UsernameNotFoundException("帳號：" + userName + " 不存在！");
			}
			
			Collection<GrantedAuthority> grantedAuthorities = getGrantedAuthorities(user);
			user.setAuthorities(grantedAuthorities);
			return user;
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new UsernameNotFoundException(e.getMessage());
		}
		
	}

	/**
	 * 根據用戶獲取該用戶擁有的角色
	 * 登入成功，就有LOGIN_SUCCESS權限
	 * @param user
	 * @return
	 */
	private Set<GrantedAuthority> getGrantedAuthorities(UserDTO user) {
		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
//		List<Role> roles = roleMapper.selectByUserId(user.getId());
//		if (roles != null) {
//			for (Role role : roles) {
//				grantedAuthorities.add(new SimpleGrantedAuthority(role
//						.getName()));
//			}
//		}
		grantedAuthorities.add(new SimpleGrantedAuthority("LOGIN_SUCCESS"));
		
		return grantedAuthorities;
	}

}
