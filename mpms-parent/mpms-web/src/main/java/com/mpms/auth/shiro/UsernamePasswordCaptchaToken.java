package com.mpms.auth.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 扩展添加验证码-继承用户验证类
 *
 * @date 2015年11月4日 下午12:24:21
 * @author luogang
 */
public class UsernamePasswordCaptchaToken extends UsernamePasswordToken {

	private static final long serialVersionUID = 1L;

	private String captcha;

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public UsernamePasswordCaptchaToken() {
		super();
	}

	/**
	 * 封装验证码
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @param rememberMe
	 *            记住
	 * @param host
	 *            IP
	 * @param captcha
	 *            验证码
	 * @date 2015年12月4日 上午11:37:44
	 * @author luogang
	 */
	public UsernamePasswordCaptchaToken(String username, char[] password, boolean rememberMe, String host, String captcha) {
		super(username, password, rememberMe, host);
		this.captcha = captcha;
	}

}