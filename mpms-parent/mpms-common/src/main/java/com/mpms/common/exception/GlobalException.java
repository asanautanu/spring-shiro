package com.mpms.common.exception;

/**
 * 异常处理
 * @author lc
 * @createDate 2015年10月27日17:04:30
 *
 */
public class GlobalException extends Exception{

	private static final long serialVersionUID = 1L;
	/**
	 * 异常编码
	 */
	private int code;
	
	public GlobalException(int code,String message) {
		super(message);
		this.code =code;
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	

}
