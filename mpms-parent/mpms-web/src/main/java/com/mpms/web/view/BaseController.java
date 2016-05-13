package com.mpms.web.view;

import java.util.List;

import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mpms.common.exception.ExceptionCode;
import com.mpms.common.exception.GlobalException;
import com.mpms.common.po.auth.Resource;
import com.mpms.common.service.auth.ResourceService;

/**
 * BaseController
 * 
 * @date 2015年12月1日 上午11:23:08
 * @author maliang
 */
public class BaseController {

	private Logger MPMS_EXCEPTION = LoggerFactory.getLogger("MPMS_EXCEPTION");

	@Autowired
	private ResourceService resourceService;

	/**
	 * 异常信息处理
	 * 统一响应
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler({ Exception.class })
	public MpmsReponse exception(Exception e) {
		if (e instanceof GlobalException) {
			GlobalException ge = (GlobalException) e;
			MPMS_EXCEPTION.info("globaleException code:{},Message:{}", ge.getCode(), ge.getMessage());
			return MpmsReponse.addFailedResponse(ge.getCode(), ge.getMessage());
		} else if (e instanceof UnauthorizedException) {
			String message = e.getMessage();
			int beginIndex = message.indexOf("[");
			int endIndex = message.indexOf("]");
			String permission = message.substring(beginIndex + 1, endIndex);
			if (permission != null) {
				List<Resource> resources = resourceService
						.findResourceBypermission(message.substring(beginIndex + 1, endIndex));
				if (resources != null && resources.size() > 0) {
					message = "该用户没有[" + resources.get(0).getName() + "]权限";
				}
			}
			return MpmsReponse.addFailedResponse(ExceptionCode.AUTH_ERROR_CEDE, message);
		} else {
			e.printStackTrace();
			MPMS_EXCEPTION.error("{} Message:{},{}", e.getClass().getName(), e.getMessage(), e);
			return MpmsReponse.addFailedResponse(ExceptionCode.SERVER_ERROR, e.getMessage());
		}

	}

	/**
	 * 接收日期参数转换
	 * 
	 * @Description: 接收日期参数转换
	 * @param binder
	 * @return void
	 * @throws @author
	 *             tangzhi
	 * @date 2016-1-10
	 */
	/*
	 * @InitBinder public void initBinder(WebDataBinder binder) {
	 * binder.registerCustomEditor(Date.class, new
	 * DefaultDateEditor());//true:允许输入空值，false:不能为空值 }
	 */
}
