package com.foya.noms.web.interceptor;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.foya.dao.mybatis.model.TbSysUserAudit;
import com.foya.noms.dto.auth.UserDTO;
import com.foya.noms.service.system.UserAuditService;
import com.foya.noms.util.DateUtils;

/**
 * Record User Audit
 * 
 * <table>
 * <tr>
 * <th>版本</th>
 * <th>日期</th>
 * <th>詳細說明</th>
 * <th>modifier</th>
 * </tr>
 * <tr>
 * <td>1.0</td>
 * <td>2014/8/6</td>
 * <td>新建檔案</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 */
public class UserAuditInterceptor extends HandlerInterceptorAdapter {

	private final transient Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserAuditService sysUserAuditService;

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {

		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		// ignore resource
		Matcher matcher = Pattern.compile("/resources/|/index/|/ajaxChildMenu|/exception/").matcher(request.getRequestURI());
		if (matcher.find()) {
			log.trace("Find out it's a legal resource path.");
		} else {			
			recordUserAudit(request, handler);
		}
		
		return super.preHandle(request, response, handler);
	}

	/**
	 * 記錄使用者動作
	 * @param request
	 */
	private void recordUserAudit(HttpServletRequest request, Object handler) {
		UserDTO user = (UserDTO) request.getSession().getAttribute("loginUser");
		
		if (user != null && handler instanceof HandlerMethod) {		
			HandlerMethod method = (HandlerMethod) handler;
			
			TbSysUserAudit model = new TbSysUserAudit();
			model.setPSN_ID(new BigDecimal(user.getUserId()));
			model.setPSN_NAME(user.getUsername());
			model.setSESSION_ID(request.getSession().getId());
			model.setUSER_IP(request.getRemoteAddr());
			model.setLOG_TYPE(method.getBeanType().getSimpleName() + "." + method.getMethod().getName());
			model.setLOG_TIME(DateUtils.today());
			String paramsValue = "";
			for (String paramName : request.getParameterMap().keySet()) {
				if (StringUtils.equals("j_password", paramName)) {
					paramsValue += paramName + "=******&";
				} else if (!StringUtils.equals("_", paramName)){					
					paramsValue += paramName + "=" + request.getParameterMap().get(paramName)[0] + "&";
				}
			}
			// DB目前開varchar(1000)
			paramsValue = paramsValue.length() >= 500 ? StringUtils.substring(paramsValue, 0, 500)+"...[Extra infomation please check log]" : StringUtils.substring(paramsValue, 0, paramsValue.length() - 1);
			model.setLOG_DESCRIPTION(paramsValue);
			model.setURL_PATH(request.getRequestURL().toString());
			
			log.debug(model.toString());
			sysUserAuditService.insert(model);
			
			request.getSession().setAttribute("userAction", model.getLOG_TYPE());
		}
	}

}
