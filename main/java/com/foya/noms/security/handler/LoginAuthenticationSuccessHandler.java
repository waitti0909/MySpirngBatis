package com.foya.noms.security.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.foya.dao.mybatis.model.TbComCompany;
import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.noms.dao.common.CompanyQueryDao;
import com.foya.noms.dao.org.DeptDao;
import com.foya.noms.dao.org.DomainDao;
import com.foya.noms.dao.org.RoleMenuFunDeptDao;
import com.foya.noms.dto.auth.RoleMenuFunDepDTO;
import com.foya.noms.dto.auth.UserDTO;

public class LoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DeptDao deptDao;
	
	@Autowired
	private RoleMenuFunDeptDao roleMenuFunDeptDao;

	@Autowired
	private CompanyQueryDao companyQueryDao;
	
	@Autowired
	private DomainDao domainDao;
	
	
	// 登錄驗證成功後需要跳轉的url
	private String url;

	public void setUrl(String url) {
		this.url = url;
	}

	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName(), pDeptId = null;
		if (auth.getPrincipal() instanceof UserDTO) {
			log.info("[LOG]" + name + "登錄驗證成功");
			UserDTO user = (UserDTO) auth.getPrincipal();

			if (StringUtils.isNotEmpty(user.getPrimaryDeptPosId()) && StringUtils.contains(user.getPrimaryDeptPosId(), "__")) {
				pDeptId = StringUtils.splitByWholeSeparator(user.getPrimaryDeptPosId(), "__")[0];
				TbOrgDept orgDept = deptDao.findByPk(pDeptId);
				user.setDeptId(orgDept.getDEPT_ID());
				user.setDeptName(orgDept.getDEPT_NAME());
				if(orgDept.getDOMAIN()!=null && orgDept.getDOMAIN().length()>0){
					user.setDomain(domainDao.getDomainById(orgDept.getDOMAIN()));
					
				}				
			} else if (user.isVendor()) {
				pDeptId = user.getHrDeptId();
				TbComCompany company = companyQueryDao.findByCoVatNo(user.getCoVatNo());
				user.setDeptId(user.getHrDeptId());	// 廠商會附屬於單一管理單位下
				user.setDeptName(company.getCO_NAME());
			}
			log.debug("Login User DeptID : " + pDeptId);
			Map<Integer, Set<String>> menuDepts = loadAllowedDeptList(user.getUsername(), user.getDeptId());
			log.debug("menuDepts ::" + menuDepts.size());
			user.setAllowDeptList(menuDepts);

			request.getSession().setAttribute("loginUser", user);
		} else {
			request.getSession().setAttribute("loginUser", auth.getPrincipal());
		}
		request.getRequestDispatcher(url).forward(request, response);
		// response.sendRedirect(request.getServletContext().getContextPath()
		// + url);
	}

	private Map<Integer, Set<String>> loadAllowedDeptList(String psnNo, String selfDeptId) {

		Map<Integer, Set<String>> returnMap = new HashMap<Integer, Set<String>>();

		List<RoleMenuFunDepDTO> resultList = roleMenuFunDeptDao.getAccessDeptByUser(psnNo);

		Set<String> depList = null;

		for (RoleMenuFunDepDTO data : resultList) {
			int menu_id = data.getMenuId();
			String dep_id = data.getDepId();
			if (returnMap.containsKey(menu_id)) {
				depList = returnMap.get(menu_id);
				depList.add(dep_id);
				// returnMap.putAll(m);
			} else {
				depList = new LinkedHashSet<>();
				depList.add(selfDeptId);	// 先塞自己部門
				depList.add(dep_id);
				returnMap.put(menu_id, depList);
			}
		}
		log.debug("returnMap.size : " + returnMap.size());
		return returnMap;

	}
}
