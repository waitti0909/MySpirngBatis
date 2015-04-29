package com.foya.noms.security.handler;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

public class MyLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint{
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
    public MyLoginUrlAuthenticationEntryPoint(String loginFormUrl) {
    	super(loginFormUrl);
    }

	@Override
	public void commence(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		String heardeName = request.getHeader("x-requested-with");
	    if(null != heardeName ){
	      log.info("[LOG][ajax][no auth]");
	      RequestDispatcher dispatcher = request.getRequestDispatcher("/invalidate/");
	      dispatcher.forward(request, response);
	      return;
	    }
	    else {
	    	log.info("[LOG][jsp][no auth]");
	    	super.commence(request, response, authException);
	    }
		
		
		
	}

    
    
    
}
