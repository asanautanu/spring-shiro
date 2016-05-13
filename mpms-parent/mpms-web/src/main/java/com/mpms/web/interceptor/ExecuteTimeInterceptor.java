package com.mpms.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 慢日志记录
 * @date 2015年12月28日 下午4:41:20
 * @author maliang
 */
public class ExecuteTimeInterceptor extends HandlerInterceptorAdapter {

	private Logger log = LoggerFactory.getLogger(getClass());

	private static final String START_TIME = "page_start_time";

	/**
	 * default slow request > 500 MS
	 */
	private long slowTime = 500;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		/*if (log.isDebugEnabled()) {
			log.debug("preHandle :{} queryString:{} data:{}",
					request.getRequestURI(), request.getQueryString());
		}*/
		request.setAttribute(START_TIME, System.currentTimeMillis());
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

		/*if (log.isDebugEnabled()) {
			log.debug("afterCompletion :{}", request.getRequestURI());
		}*/
		long startTime = (Long) request.getAttribute(START_TIME);
		long use = System.currentTimeMillis() - startTime;
		log.debug("{} request {} use {} ms", use > slowTime ? "slow" : "",
				request.getRequestURI(), use);
		super.afterCompletion(request, response, handler, ex);
	}

	public long getSlowTime() {
		return slowTime;
	}

	public void setSlowTime(long slowTime) {
		this.slowTime = slowTime;
	}

	public static String getStartTime() {
		return START_TIME;
	}

}
