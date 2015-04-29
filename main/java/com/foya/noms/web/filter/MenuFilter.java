package com.foya.noms.web.filter;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.MDC;

import com.foya.noms.dto.auth.UserDTO;

public class MenuFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		UserDTO user = (UserDTO) request.getSession().getAttribute("loginUser");

		if (user != null) {
			
			Matcher matcher = Pattern.compile("/resources/|/index/|/ajaxChildMenu").matcher(request.getRequestURI());
			if (!matcher.find()) {
				MDC.put("user", user);
				MDC.put("requestUrl", request.getRequestURL().toString());
				
			}
			// record recently loading for menuId
			if (StringUtils.isNotEmpty(request.getParameter("menuId"))) {
//				request.setAttribute("currentMenuId", request.getParameter("menuId"));
				request.getSession().removeAttribute("currentMenuId");
				request.getSession().setAttribute("currentMenuId", request.getParameter("menuId"));
			}
			
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
}
