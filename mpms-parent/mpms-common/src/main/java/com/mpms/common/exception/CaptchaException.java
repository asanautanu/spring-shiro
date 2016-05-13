package com.mpms.common.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 验证码异常类
 *	
 *	@date 2015年11月4日 下午4:33:14
 *  @author luogang
 */
public class CaptchaException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public CaptchaException() {
		super();
	}

	public CaptchaException(String message, Throwable cause) {
		super(message, cause);
	}

	public CaptchaException(String message) {
		super(message);
	}

	public CaptchaException(Throwable cause) {
		super(cause);
	}
}