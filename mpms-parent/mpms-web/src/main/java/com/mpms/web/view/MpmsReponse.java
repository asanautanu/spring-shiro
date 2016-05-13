package com.mpms.web.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mpms.common.exception.ExceptionCode;

/**
 * 统一响应
 *
 * @ClassName: LYSReponse.java
 * @Description: 统一响应
 *
 * @author tangzhi
 * @version V1.0
 * @Date 2015-11-30 下午7:05:48
 */
public class MpmsReponse {

	/**
	 * 错误代码
	 */
	private int code;

	/**
	 * 错误信息
	 */
	private String msg;

	/**
	 * 数据
	 */
	private Map<String, Object> data;

	public MpmsReponse(int code, String msg, Map<String, Object> data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public static MpmsReponse addFailedResponse(int code, String message) {
		return new MpmsReponse(code, message, null);
	}

	public static MpmsReponse addSuccessResponse(String message, Map<String, Object> data) {
		return new MpmsReponse(ExceptionCode.COMMON_SUCCESS_CODE, message, data);
	}

	public static MpmsReponse addListResponse(String message, int total, List<?> list) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("total", total);
		data.put("list", list);
		return new MpmsReponse(ExceptionCode.COMMON_SUCCESS_CODE, message, data);
	}

	public static MpmsReponse addListResponse(String message, int total, int pageNo, int pageSize, List<?> list) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("total", total);
		data.put("list", list);
		return new MpmsReponse(ExceptionCode.COMMON_SUCCESS_CODE, message, data);
	}

}
