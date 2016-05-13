package com.mpms.auth.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 扩展认证默认过滤
 *
 * @date 2015年11月4日 下午12:24:12
 * @author luogang
 */
public class FormAuthenticationCaptchaFilter extends FormAuthenticationFilter {

	private static final Logger log = LoggerFactory.getLogger(FormAuthenticationFilter.class);

	public static final String DEFAULT_CAPTCHA_PARAM = "captcha";

	private String captchaParam = DEFAULT_CAPTCHA_PARAM;

	public String getCaptchaParam() {

		return captchaParam;
	}

	/**
	 * 获取验证码
	 * 
	 * @param request
	 * @return
	 * @date 2015年11月12日 下午6:26:00
	 * @author luogang
	 */
	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}

	/**
	 * 添加验证码
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @date 2015年11月9日 下午4:20:07
	 * @author luogang
	 */
	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);
		String captcha = getCaptcha(request);
		boolean rememberMe = isRememberMe(request);
		String host = getHost(request);
		return new UsernamePasswordCaptchaToken(username, password.toCharArray(), rememberMe, host, captcha);
	}

	/**
	 * 重写登录地址跳转
	 * 
	 * @param token
	 * @param subject
	 * @param request
	 * @throws Exception
	 * @date 2016年1月18日 上午11:47:56
	 * @author luogang
	 */
	/*
	 * @Override protected boolean onLoginSuccess(AuthenticationToken token,
	 * Subject subject, ServletRequest request, ServletResponse response) throws
	 * Exception { redirectToSavedRequest(request, response, getSuccessUrl());
	 * return false; }
	 */

	/*
	 * @Override protected boolean onLoginFailure(AuthenticationToken token,
	 * AuthenticationException e, ServletRequest request, ServletResponse
	 * response) {
	 * 
	 * log.error("登录失败{}",e);
	 * 
	 * return super.onLoginFailure(token, e, request, response); }
	 */

	/*
	 * private void redirectToSavedRequest(ServletRequest request,
	 * ServletResponse response, String fallbackUrl) throws IOException { String
	 * successUrl = null; boolean contextRelative = true; SavedRequest
	 * savedRequest = WebUtils.getAndClearSavedRequest(request); if
	 * (savedRequest != null &&
	 * savedRequest.getMethod().equalsIgnoreCase(AccessControlFilter.GET_METHOD)
	 * ) { successUrl = savedRequest.getRequestUrl(); if
	 * (StringUtil.isNotBlank(successUrl) && successUrl.contains("logout")) {
	 * successUrl = null; } else { contextRelative = false; }
	 * 
	 * }
	 * 
	 * if (successUrl == null) { successUrl = fallbackUrl; }
	 * 
	 * if (successUrl == null) { throw new IllegalStateException(
	 * "Success URL not available via saved request or via the " +
	 * "successUrlFallback method parameter. One of these must be non-null for "
	 * + "issueSuccessRedirect() to work."); }
	 * 
	 * WebUtils.issueRedirect(request, response, successUrl, null,
	 * contextRelative); }
	 */
	/*
	 * @Override protected boolean onAccessDenied( ServletRequest request,
	 * ServletResponse response) throws Exception {
	 * 
	 * if (isLoginRequest(request, response)) { if (isLoginSubmission(request,
	 * response)) { return executeLogin(request, response); } else { return
	 * true; } } else { if (isAjaxRequest(WebUtils.toHttp(request))) {
	 * HttpServletResponse res = WebUtils.toHttp(response);
	 * res.sendError(HttpServletResponse.SC_UNAUTHORIZED); } else {
	 * redirectToLogin(request, response);
	 * saveRequestAndRedirectToLogin(request, response); } return false; } }
	 */

	/*
	 * private boolean isAjaxRequest(HttpServletRequest request) { String header
	 * = request.getHeader("X-Requested-With"); if (header != null &&
	 * "XMLHttpRequest".equals(header)) return true; else return false; }
	 */
}