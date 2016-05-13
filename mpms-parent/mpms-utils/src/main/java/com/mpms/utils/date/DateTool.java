package com.mpms.utils.date;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 日期类型工具类
 * 
 * @ClassName: DateTool.java
 * @Description: 日期类型工具类
 * @author tangzhi
 * @version V1.0
 * @Date 2015-11-4 下午3:17:30
 */
public class DateTool {

	private static final Log LOG = LogFactory.getLog(DateTool.class);
	/**
	 * 日期格式：yyyy-MM-dd HH:mm:ss
	 */
	public static final String DATE_PATTERN_DEFAULT = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 日期格式：yyyyMMddHHmmss
	 */
	public static final String DATE_PATTERN_STRING = "yyyyMMddHHmmss";
	/**
	 * 日期格式：【yyyy】年【MM】月【dd】
	 */
	public static final String DATE_PATTERN_DAY_CHINNESS = "【yyyy】年【MM】月【dd】";
	/**
	 * 日期格式：yyyy年MM月dd日
	 */
	public static final String DATE_PATTERN_DAY_CHINNESS_DEFAULT = "yyyy年MM月dd日";
	/**
	 * 日期格式：yyyy-MM-dd
	 */
	public static final String DATE_PATTERN_DAY = "yyyy-MM-dd";
	/**
	 * 日期格式：yyyyMMdd
	 */
	public static final String DATE_PATTERN_NUMBER_DAY = "yyyyMMdd";

	/**
	 * 日期格式：yyyyMMdd
	 */
	public static final String DATE_PATTERN_DAY_NUM = "yyyyMMdd";

	/**
	 * 日期格式：yyyy-MM
	 */
	public static final String DATE_PATTERN_MONTH = "yyyy-MM";

	/**
	 * 日期格式：dd
	 */
	public static final String DATE_PATTERN_JUEST_DAY = "dd";

	/**
	 * 每天的最后时间点： " 23:59:59"
	 */
	public static final String DAY_LAST_TIME = " 23:59:59";

	/**
	 * 每天的最早时间点：" 00:00:00"
	 */
	public static final String DAY_FIRST_TIME = " 00:00:00";

	public static String[] weeks = { "星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };

	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 日期格式，指定格式
	 * 
	 * @Title: dateToString
	 * @Description: 日期格式，指定格式
	 * @param: @param
	 *             date 日期
	 * @param: @param
	 *             pattern 格式
	 * @param: @return
	 *             日期格式之后的字符串
	 * @return: String
	 * @throws @author
	 *             tangzhi
	 * @Date 2015-11-4 下午3:46:02
	 */
	public static String dateToString(Date date, String pattern) {
		SimpleDateFormat formater = new SimpleDateFormat(pattern);
		String str = formater.format(date);
		return str;
	}

	/**
	 * 格式化时间格式
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 * @date 2016年3月10日 上午11:38:51
	 * @author maliang
	 */
	public static Date formatDate(Date date, String pattern) {
		String dateStr = dateToString(new Date(), pattern);
		return stringToDate(dateStr, pattern);
	}

	public static Date formatDate(String date, String pattern) {
		return stringToDate(date, pattern);
	}

	/**
	 * 将字符串转化为日期,需指定字符串日期格式
	 * 
	 * @Title: stringToDate
	 * @Description: 将字符串转化为日期,需指定字符串日期格式
	 * @param: @param
	 *             str 日期字符串
	 * @param: @param
	 *             pattern 日期格式
	 * @param: @return
	 *             转换之后的日期对象
	 * @return: Date
	 * @throws @author
	 *             tangzhi
	 * @Date 2015-11-4 下午5:24:23
	 */
	public static Date stringToDate(String str, String pattern) {
		Date dateTime = null;
		try {
			SimpleDateFormat formater = new SimpleDateFormat(pattern);
			dateTime = formater.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
		}
		return dateTime;
	}

	/**
	 * 给定一个日期和天数，得到这个日期+天数的日期
	 * 
	 * @Title: getNextDay
	 * @Description: 给定一个日期和天数，得到这个日期+天数的日期
	 * @param: @param
	 *             startDate 给定日期
	 * @param: @param
	 *             num 天数
	 * @param: @return
	 *             计算之后的日期
	 * @return: Date
	 * @throws @author
	 *             tangzhi
	 * @Date 2015-11-4 下午5:25:26
	 */
	public static Date getNextDay(Date startDate, int num) {
		Date nextDay = DateUtils.addDays(startDate, num);
		return nextDay;
	}

	/**
	 * 给定一个日期和月数，得到这个日期+月数的日期
	 * 
	 * @Title: getNextMonth
	 * @Description: 给定一个日期和月数，得到这个日期+月数的日期
	 * @param: @param
	 *             startDate 给定的日期
	 * @param: @param
	 *             num 月数
	 * @param: @return
	 *             计算之后的日期
	 * @return: Date
	 * @throws @author
	 *             tangzhi
	 * @Date 2015-11-4 下午5:26:44
	 */
	public static Date getNextMonth(Date startDate, int num) {

		Date nextDay = DateUtils.addMonths(startDate, num);
		return nextDay;
	}

	/**
	 * 取得一个date对象对应的日期的0点0分0秒时刻的Date对象。
	 * 
	 * @Title: getMinDateOfDay
	 * @Description: 取得一个date对象对应的日期的0点0分0秒时刻的Date对象。
	 * @param: @param
	 *             date 给定的日期
	 * @param: @return
	 *             操作后的日期
	 * @return: Date
	 * @throws @author
	 *             tangzhi
	 * @Date 2015-11-4 下午5:38:43
	 */
	public static Date getMinDateOfDay(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
		return calendar.getTime();
	}

	/**
	 * 取得一个date对象对应的日期的23点59分59秒时刻的Date对象。
	 * 
	 * @Title: getMaxDateOfDay
	 * @Description: 取得一个date对象对应的日期的23点59分59秒时刻的Date对象。
	 * @param: @param
	 *             date 给定的日期
	 * @param: @return
	 *             处理之后的日期
	 * @return: Date
	 * @throws @author
	 *             tangzhi
	 * @Date 2015-11-4 下午5:39:24
	 */
	public static Date getMaxDateOfDay(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));

		return calendar.getTime();
	}

	/**
	 * 取得指定日期的开始始时间
	 * 
	 * @param date
	 * @return
	 * @date 2015年12月7日 下午3:56:42
	 * @author libo
	 */
	public static Date getStartDateTime(Date date) {
		String str = dateToString(date, DATE_PATTERN_DAY);
		str += DAY_FIRST_TIME;
		return stringToDate(str, DATE_PATTERN_DEFAULT);
	}

	/**
	 * 取得指定日期的结束时间
	 * 
	 * @param date
	 * @return
	 * @date 2015年12月7日 下午3:57:27
	 * @author libo
	 */
	public static Date getEndDateTime(Date date) {
		String str = dateToString(date, DATE_PATTERN_DAY);
		str += DAY_LAST_TIME;
		return stringToDate(str, DATE_PATTERN_DEFAULT);
	}

	/**
	 * 获取当前日期
	 * 
	 * @return
	 * @date 2015年12月7日 下午4:28:28
	 * @author libo
	 */
	public static Date getCurrentDate() {
		Date now = new Date();
		return now;
	}

	/**
	 * 获取指定日期的当月天数
	 * 
	 * @param date
	 *            指定日期
	 * @return 天数
	 * @date 2015年12月8日 下午3:58:41
	 * @author libo
	 */
	public static int getDaysOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取指定日期的月份
	 * 
	 * @param date
	 *            指定日期
	 * @return 天数
	 * @date 2015年12月8日 下午3:58:41
	 * @author libo
	 */
	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取指定日期的年份
	 * 
	 * @param date
	 *            指定日期
	 * @return 天数
	 * @date 2015年12月8日 下午3:58:41
	 * @author libo
	 */
	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 计算两个时间之间相隔几天（两个都会转换成 00：00：00的时间）
	 * 
	 * @Title: getBetweenDay
	 * @Description: TODO
	 * @param startDate
	 * @param endDate
	 * @return
	 * @return: int
	 * @throws @author
	 *             tangzhi
	 * @Date 2015-12-8 下午1:48:08
	 */
	public static int getBetweenDay(Date startDate, Date endDate) {

		if (startDate == null || endDate == null) {
			throw new RuntimeException("时间格式不正确");
		}

		// 进行时间转换
		startDate = getStartDateTime(startDate);
		endDate = getStartDateTime(endDate);

		long dayNumber = -1L;
		long DAY = 24L * 60L * 60L * 1000L;

		dayNumber = (endDate.getTime() - startDate.getTime()) / DAY;
		return (int) dayNumber;
	}

	/**
	 * 获取两个时间相差月份
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Throwable
	 * @date 2015年12月22日 下午12:35:47
	 * @author maliang
	 */
	public static long getBetweenMonth(Date startDate, Date endDate) throws Throwable {
		if (startDate == null || endDate == null) {
			throw new Throwable("时间格式不正确");
		}
		Calendar calendar = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar2.setTime(endDate);
		return Math.abs(
				getByField(calendar, calendar2, Calendar.YEAR) * 12 + getByField(calendar, calendar2, Calendar.MONTH));

	}

	private static long getByField(Calendar beginCalendar, Calendar endCalendar, int calendarField) {
		return endCalendar.get(calendarField) - beginCalendar.get(calendarField);
	}

	/**
	 * 获取时间是星期几
	 * 
	 * @param date
	 * @return
	 * @date 2015年12月14日 下午12:38:49
	 * @author maliang
	 */
	public static String getWeekByDate(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0) {
			w = 0;
		}
		return weeks[w];
	}

	public static Date getAfterTime(Date date, int days, int hours, int mins) {
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		if (date == null) {
			calendar.setTime(date);
		} else {
			calendar.setTime(new Date());
		}
		calendar.add(Calendar.DAY_OF_YEAR, days);
		calendar.add(Calendar.HOUR_OF_DAY, hours);
		calendar.add(Calendar.MINUTE, mins);
		return calendar.getTime();
	}

	public static Date getStartSec(Date date) {
		Calendar calendar = Calendar.getInstance();
		if (date == null) {
			return null;
		}
		calendar.setTime(date);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	public static Date getEndSec(Date date) {
		Calendar calendar = Calendar.getInstance();
		if (date == null) {
			return null;
		}
		calendar.setTime(date);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	/**
	 * 当月最后一天
	 * 
	 * @param date
	 * @return
	 * @date 2016年3月8日 下午2:52:02
	 * @author maliang
	 */
	public static String getEndDayOfMonth(Date date) {

		if (date == null) {
			// return null;
			// 当前时间
			date = new Date();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		String day_first = df.format(getEndDateTime(calendar.getTime()));
		StringBuffer str = new StringBuffer().append(day_first);
		// return stringToDate(str.toString(), DATE_PATTERN_DEFAULT);
		return str.toString();
	}

	/**
	 * 当月第一天
	 * 
	 * @param date
	 * @return
	 * @date 2016年3月8日 下午2:51:51
	 * @author maliang
	 */
	public static String getStartDayOfMonth(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		Date theDate = calendar.getTime();
		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		gcLast.setTime(theDate);
		gcLast.set(Calendar.DAY_OF_MONTH, 1);
		String day_first = df.format(getStartDateTime(gcLast.getTime()));
		StringBuffer str = new StringBuffer().append(day_first);
		return str.toString();
	}

	/**
	 * 根据年月日创建时间
	 * 
	 * @Description: 根据年月日创建时间
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 * @return Date
	 * @throws @author
	 *             tangzhi
	 * @date 2016-3-21
	 */
	public static Date getDate(int year, int month, int day) {
		return stringToDate(year + "-" + month + "-" + day, DATE_PATTERN_DAY);
	}

	/**
	 * 计算还款日列表
	 * 
	 * @param lendTime
	 *            放款日期
	 * @param firstRepayDate
	 *            首期还款日
	 * @param totalPhase
	 *            总期数
	 * @return 还款日列表
	 * @date 2015年12月16日 下午3:24:24
	 * @author libo
	 */
	public static List<Date> calculateRepayDays(Date lenDate, Date firstRepayDate, int totalPhase, int repayDay) {

		int day = DateTool.getMonth(firstRepayDate);
		List<Date> repayDayList = new ArrayList<Date>(totalPhase);
		repayDayList.add(firstRepayDate);
		for (int i = 1; i < totalPhase; i++) {
			Date repayDate = DateTool.getNextMonth(firstRepayDate, i);
			if (day != repayDay) {// 如果不相等需要进行处理
				int year = DateTool.getYear(repayDate);
				int month = DateTool.getMonth(repayDate);
				Date reDate = DateTool.getDate(year, month, repayDay);
				int reMonth = DateTool.getMonth(reDate);
				if (month != reMonth) {
					reDate = DateTool.stringToDate(DateTool.getEndDayOfMonth(repayDate), DateTool.DATE_PATTERN_DAY);
				}
				repayDate = reDate;
			}
			repayDayList.add(repayDate);
		}
		return repayDayList;
	}

	/**
	 * 设置给定日期的天数
	 * 
	 * @param date
	 * @param day
	 * @return
	 * @auth lc
	 * @date 2016年3月22日17:03:32
	 */
	public static Date setDayOfDate(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
	}

	/**
	 * 返回2个日期中的小者
	 */
	public static Date min(Date date1, Date date2) {
		if (date1 == null && date2 == null) {
			return null;
		}
		if (date1 == null) {
			return date2;
		}
		if (date2 == null) {
			return date1;
		}
		if (date1.after(date2)) {
			return date2;
		} else {
			return date1;
		}
	}

}
