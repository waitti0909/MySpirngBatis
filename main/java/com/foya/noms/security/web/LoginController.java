package com.foya.noms.security.web;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.foya.exception.AjaxTimeoutException;
import com.foya.noms.resources.AppConstants;
import com.foya.noms.security.service.ChangePasswordService;

@Controller
public class LoginController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ChangePasswordService changePasswordService;

	/**
	 * 跳轉到index頁面
	 * 
	 * @return
	 */

	@RequestMapping(value = "/")
	public String firstIndex() {
		// log.debug("redirect to login page");
		return "redirect:login/";
	}

	/**
	 * login頁面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login/")
	public String login() {
		// log.debug("login page");
//		return "login";
		String loginPage = "login";
		if (StringUtils.equals("FOYA", AppConstants.ENVIRONMENT)) {
			loginPage += "_dev";
		}
		return loginPage;
	}

	/**
	 * loginSuccess頁面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/loginSuccess/")
	public String loginSuccess() {
		// log.debug("loginSuccess page");
		return "loginSuccess";
	}

	/**
	 * logout頁面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/logout/")
	public String logout() {
		// log.debug("logout page");
		return "redirect:login/";
	}

	/**
	 * index頁面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index/")
	public String index() {
		String indexPage = "index";
		// log.debug("index page");
		if (StringUtils.equals("FOYA", AppConstants.ENVIRONMENT)) {
			indexPage += "_dev";
		}
		return indexPage;
	}

	/**
     * dashboard頁面
     * 
     * @return
     */
    @RequestMapping(value = "/ajaxPage/dashboard/")
    public String dashboard() {
        // log.debug("index page");
        return "/ajaxPage/dashboard";
    }
    
	/**
	 * 處理ajax no auth
	 * 
	 * @return
	 */
	@RequestMapping(value = "/invalidate/")
	public Map<String, Object> invalidate() {
		log.debug("[LOG][invalidate]");
		throw new AjaxTimeoutException();
	}

	/**
	 * error頁面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/error/")
	public String error() {
		// log.debug("error page");
		return "error";
	}

	/**
	 * changePassword頁面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/changePassword/", method = RequestMethod.GET)
	public String changePassword(
			@ModelAttribute("changePasswordValidationBean") ChangePasswordValidationBean changePasswordValidationBean) {
		// log.debug("changePassword page GET");
		return "changePassword";
	}

//	@RequestMapping(value = "/changePassword/", method = RequestMethod.POST)
//	public String validate(
//			@Valid ChangePasswordValidationBean changePasswordValidationBean,
//			BindingResult binder) {
//		// log.debug("changePassword page POST");
//		if (binder.hasErrors()) {
//			return "users/changePassword";
//		}
//
//		String oldPassword = changePasswordValidationBean.getOldPassword();
//		String newPassword = changePasswordValidationBean.getNewPassword();
//
//		Object principal = SecurityContextHolder.getContext()
//				.getAuthentication().getPrincipal();
//		String username = principal.toString();
//		if (principal instanceof UserDetails) {
//			username = ((UserDetails) principal).getUsername();
//		}
//
////		boolean change = changePasswordService.changePassword(username, oldPassword, newPassword);
//		boolean change = true;
//
//		if (change) {
//			return "redirect:/";
//		} else {
//			FieldError fieldError = new FieldError(
//					"changePasswordValidationBean", "oldPassword", null, false,
//					new String[] { "validation.oldPassword.not.correct" },
//					null, "Your old password was not correct.");
//
//			binder.addError(fieldError);
//			return "changePassword/";
//		}
//	}

}
