package com.mpms.common.exception;

/**
 * 错误，异常编码
 * <p>
 * 目前服务层要求对每个业务只提倡一个编码
 * 
 * @author maliang
 */
public class ExceptionCode {

	/**
	 * 统一成功编码
	 * 
	 * @author tangzhi
	 */
	public static final int COMMON_SUCCESS_CODE = 0;

	/**
	 * 服务错误
	 *
	 * @date 2015年11月2日 下午4:46:14
	 * @author luogang
	 */
	public static final int SERVER_ERROR = 1000;

	/**
	 * @description 日志模块异常编码
	 * @author lc
	 * @createDate 2015年10月27日16:57:16
	 */
	public static final int LOG_CODE = 2000;

	/**
	 * @description 客户模块异常编码
	 * @author lc
	 * @createDate 2015年12月1日17:22:16
	 */
	public static final int CUSTOMER_ERROR_CODE = 3000;

	/**
	 * 参数验证错误
	 */
	public static final int PARAM_VALUE_ERROR = 4000;

	/**
	 * @description 权限模块异常编码
	 * @author luogang
	 * @createDate 2015年12月3日15:01
	 */
	public static final int AUTH_ERROR_CEDE = 5000;

	/**
	 * @description 机构异常编码
	 * @author luogang
	 * @createDate 2015年12月3日15:01
	 */
	public static final int INSTITUTION_ERROR_CEDE = 6000;

	/**
	 * @description 参数验证错误
	 * @author lc
	 * @createDate 2015年12月1日17:22:16
	 */
	public static final int LOAN_ERROR_CODE = 7000;

	/**
	 * @description 系统异常代码
	 * @author lc
	 * @createDate 2015年12月1日17:22:16
	 */
	public static final int SYSTEM_ERROR_CODE = 8000;

	/**
	 * 产品异常编码
	 */
	public static final int LOAN_PRODUCT_CODE = 9000;

	/**
	 * 财务异常代码
	 */
	public static final int FINANCE_ERROR_CODE = 10000;

	/**
	 * 信审异常代码
	 */
	public static final int CREDIT_ERROR_CODE = 11000;
	/**
	 * 运营异常代码
	 */
	public static final int OPERATION_ERROR_CODE = 12000;

	/**
	 * 业务异常代码
	 */
	public static final int BUSINESS_ERROR_CODE = 13000;

	/**
	 * 定时任务相关错误信息
	 */
	public static final int TIMER_TASK_CODE = 50000;

	/**
	 * 换卡申请异常编码
	 */
	public static final int CHANGE_CARD_ERROR_CODE = 14000;

	/**
	 * 资金异常代码
	 */
	public static final int POINT_ERROR_CODE = 15000;

	/**
	 * 催收异常代码
	 */
	public static final int PAYMENT_ERROR_CODE = 16000;

	/**
	 * 进件资料异常代码
	 */
	public static final int LOAN_DATA_ERROR_CODE = 17000;

	/**
	 * 发送短信
	 */
	public static final int SEND_MSG_ERROR_CODE = 18000;

	/**
	 * 进件资料异常代码
	 */
	public static final int ATTACHMENT_ERROR_CODE = 19000;

	/**
	 * 消息异常代码
	 */
	public static final int MESSAGE_ERROR_CODE = 20000;

	/**
	 * 还款异常代码
	 */
	public static final int REPAY_ERROR_CODE = 21000;
}
