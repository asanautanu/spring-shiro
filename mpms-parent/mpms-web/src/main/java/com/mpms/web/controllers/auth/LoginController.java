package com.mpms.web.controllers.auth;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mpms.common.exception.GlobalException;
import com.mpms.web.view.BaseController;

/**
 * 登录
 *
 * @date 2015年11月3日 下午4:34:33
 * @author seiya
 */
@Controller
@RequestMapping(value = "/admin")
@Scope("prototype")
public class LoginController extends BaseController {

	/**
	 * 登录页面
	 * 
	 * @return
	 * @date 2015年11月9日 下午6:02:07
	 * @author seiya
	 * @throws GlobalException
	 */
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login() throws GlobalException {

		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated() || subject.isRemembered()) {
			return "redirect:/index.html";
		}
		return "login";
	}

	/**
	 * * 登录失败
	 * 
	 * @param userName
	 * @param model
	 * @return
	 * @date 2015年11月3日 下午4:37:35
	 * @author seiya
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName, Model model) {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated() || subject.isRemembered()) {
			return "redirect:/index.html";
		}
		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, userName);
		return "login";
	}

	/**
	 * 登出
	 * 
	 * @param model
	 * @return
	 * @date 2015年11月3日 下午4:37:42
	 * @author seiya
	 */
	@RequestMapping(value = "logout", method = { RequestMethod.GET })
	public String logout(Model model) {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "redirect:/admin/login.do";
	}

}
