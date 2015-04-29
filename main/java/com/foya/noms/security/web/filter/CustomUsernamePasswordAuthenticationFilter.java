package com.foya.noms.security.web.filter;



import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;





//for AD Login 20141125
import com.foya.noms.ad.dao.UserDaoImpl;




public class CustomUsernamePasswordAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	public static final String SPRING_SECURITY_FORM_USEAD_KEY = "useAD";
	private String useADParameter=SPRING_SECURITY_FORM_USEAD_KEY;
	
	public static final String SPRING_SECURITY_FORM_USERTYPE_KEY = "userType";
	private String userTypeParameter=SPRING_SECURITY_FORM_USERTYPE_KEY;
	private AuthenticationManager vendorAuthenticationManager;
	private UserDetailsService userDetailsService;
	
	//for AD Login 20141125
	private UserDaoImpl ldapUser;
	
	public String getUseADParameter() {
		return useADParameter;
	}
	public void setUseADParameter(String useADParameter) {
		this.useADParameter = useADParameter;
	}
	
	
	protected String obtainUseAd(HttpServletRequest request) {
	        return request.getParameter(useADParameter);
	}
	protected String obtainUserType(HttpServletRequest request) {
        return request.getParameter(userTypeParameter);
}
	final Base64 base64 = new Base64();
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        String useAD = obtainUseAd(request);
        String userType = obtainUserType(request);
       

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }
        
        if (useAD == null) {
        	useAD = "N";
        }
        if (userType == null) {
        	userType = "E";
        }
        try {
			byte[] textByte = password.getBytes("UTF-8");
			password = base64.encodeToString(textByte);
		} catch (UnsupportedEncodingException e) {
		
			e.printStackTrace();
		}
        
        password = obtainPassword(request);
        if("Y".equals(useAD)){
        	//for AD Login 20141125 start-目前可登入AD成功,但無法進入index.jsp
        	boolean isSuccess = false;
        	try{
        		//法1
        		//String userDn = ldapUser.getDnForUser(username);  
        	    //log.debug("userDn:" + userDn);  
        	    //isSuccess = ldapUser.authenticate(userDn, password);
        		  
        		log.debug("useAD is Y=>username=" + username+",password="+password);  
        		isSuccess = ldapUser.authenticate(username, password);
        	    log.debug("AD login success:"+isSuccess);
        	    //法2
                //isSuccess = ldapUser.login(username, password); 
                //log.debug("AD login success:"+isSuccess);
                
                //Q:豋入成功後,如何return Authentication物件???     
                if(isSuccess){
                  UserDetails userDetails =getUserDetailsService().loadUserByUsername(username);
              	  Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
//            	  if(authentication==null) throw new InternalAuthenticationServiceException("User:"+username+" not found in noms system ");
            	  return authentication;
                }else{
         
                	throw new InternalAuthenticationServiceException("AD Login Fail");
                }
        	}catch(Exception ex){
        		ex.printStackTrace();
               	throw new InternalAuthenticationServiceException("登入失敗:"+ex.getMessage());   
        	}
        	//check AD
        	
        }else{
        	//use auth_personnal  table to auth
        	  UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

              // Allow subclasses to set the "details" property
              setDetails(request, authRequest);
              
        	if("E".equals(userType)){
        		//means normal employer
        		return getAuthenticationManager().authenticate(authRequest);
        	}else{
        		//means vendor
        		
        		return  getVendorAuthenticationManager().authenticate(authRequest);
        	}
        	
        	
        }
        
		
	}
	public String getUserTypeParameter() {
		return userTypeParameter;
	}
	public void setUserTypeParameter(String userTypeParameter) {
		this.userTypeParameter = userTypeParameter;
	}
	public AuthenticationManager getVendorAuthenticationManager() {
		return vendorAuthenticationManager;
	}
	public void setVendorAuthenticationManager(
			AuthenticationManager vendorAuthenticationManager) {
		this.vendorAuthenticationManager = vendorAuthenticationManager;
	}
	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	//for AD Login 20141125 start
	public UserDaoImpl getLdapUser() {
		return this.ldapUser;
	}
	public void setLdapUser(UserDaoImpl ldapUser) {
		this.ldapUser = ldapUser;
	}
	//for AD Login 20141125 end
}
