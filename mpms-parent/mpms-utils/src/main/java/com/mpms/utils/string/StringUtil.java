package com.mpms.utils.string;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类
 * 
 * @date 2015年11月4日 下午3:56:30
 * @author seiya
 */
public final class StringUtil {

	private StringUtil() {
	}

	/**
	 * 手机号校验
	 * <p>
	 * 支持13、15、18、17网段
	 * </p>
	 * 
	 * @param mobile
	 *            手机号
	 * @return
	 * @date 2015-11-04 下午4:08:43
	 * @author seiya
	 */
	public static boolean isMobileNO(String mobile) {
		if (StringUtils.isBlank(mobile)) {
			return false;
		}
		String regex = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9])|170|147)\\d{8}$";
		return mobile.matches(regex);
	}

	/**
	 * 中文字符校验
	 * <p>
	 * 使用GBK编码
	 * </p>
	 * 
	 * @param str
	 * @return
	 * @date 2015-11-04 下午4:11:06
	 * @author seiya
	 */
	public static boolean checkChinese(String str) {
		if (StringUtils.isBlank(str)) {
			return false;
		}
		str = new String(str.getBytes());// 用GBK编码
		String regex = "^[\u4e00-\u9fa5]+$";
		return str.matches(regex);
	}

	/**
	 * 自定义正则校验字符串
	 * 
	 * @param str
	 *            待校验的字符串
	 * @param regex
	 *            正则表达式
	 * @return
	 * @date 2015-11-04 下午4:18:48
	 * @author seiya
	 */
	public static boolean checkByPattern(String str, String regex) {
		if (StringUtils.isBlank(str)) {
			return false;
		}
		return str.matches(regex);
	}

	/**
	 * UUID 生成
	 * 
	 * @return
	 * @date 2015年11月9日 下午2:45:45
	 * @author seiya
	 */
	public static String UUIDGenerate() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * gzip 数据压缩
	 * 
	 * @param msg
	 * @return
	 * @throws IOException
	 * @date 2015年11月2日 下午4:15:22
	 * @author seiya
	 */
	public static byte[] gzip(String msg) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = null;
		try {
			gzip = new GZIPOutputStream(out);
			gzip.write(msg.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (gzip != null) {
				gzip.close();
			}
			IOUtils.closeQuietly(out);
		}
		return out.toByteArray();
	}

	/**
	 * gzip 数据压缩
	 * 
	 * @param msg
	 * @return
	 * @throws IOException
	 * @date 2015年11月2日 下午4:15:22
	 * @author seiya
	 */
	public static byte[] gzip(byte[] msg) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = null;
		try {
			gzip = new GZIPOutputStream(out);
			gzip.write(msg);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (gzip != null) {
				gzip.close();
			}
			IOUtils.closeQuietly(out);
		}
		return out.toByteArray();
	}

	/**
	 * gzip 数据解压缩
	 * 
	 * @param msg
	 * @return
	 * @throws IOException
	 * @date 2015年11月2日 下午4:15:36
	 * @author seiya
	 */
	public static byte[] unZip(String msg) throws IOException {

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(msg.getBytes());
		GZIPInputStream gunzip = new GZIPInputStream(in);
		byte[] buffer = new byte[4096];
		int n;
		while ((n = gunzip.read(buffer)) >= 0) {
			out.write(buffer, 0, n);
		}
		gunzip.close();

		IOUtils.closeQuietly(in);
		IOUtils.closeQuietly(out);

		return out.toByteArray();
	}

	/**
	 * gzip 解压
	 * 
	 * @param msg
	 *            数据
	 * @return
	 * @throws IOException
	 * @date 2015年11月13日 下午4:52:33
	 * @author seiya
	 */
	public static String unZip(byte[] msg) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(msg);
		GZIPInputStream gunzip = new GZIPInputStream(in);
		byte[] buffer = new byte[4096];
		int n;
		while ((n = gunzip.read(buffer)) >= 0) {
			out.write(buffer, 0, n);
		}
		gunzip.close();

		IOUtils.closeQuietly(in);
		IOUtils.closeQuietly(out);

		// return out.toByteArray();
		return new String(out.toByteArray(), "utf-8");
	}

	/**
	 * 解压数据，返回解压过后的字节数组
	 * 
	 * @param msg
	 * @return
	 * @throws IOException
	 * @date 2015年11月16日 上午10:44:10
	 * @author seiya
	 */
	public static byte[] unZip2(byte[] msg) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(msg);
		GZIPInputStream gunzip = new GZIPInputStream(in);
		byte[] buffer = new byte[4096];
		int n;
		while ((n = gunzip.read(buffer)) >= 0) {
			out.write(buffer, 0, n);
		}
		gunzip.close();
		IOUtils.closeQuietly(in);
		IOUtils.closeQuietly(out);
		return out.toByteArray();
	}

	/**
	 * 字符串是否为空
	 * <p>
	 * "" or null result true
	 * </p>
	 * 
	 * @param val
	 * @return boolean
	 * @date 2015年11月6日 下午4:46:01
	 * @author seiya
	 */
	public static boolean isBlank(String val) {
		return StringUtils.isBlank(val);
	}

	/**
	 * 字符串是否为空
	 * 
	 * @param val
	 * @return
	 * @date 2015年11月6日 下午6:46:36
	 * @author seiya
	 */
	public static boolean isNotBlank(String val) {
		return StringUtils.isNotBlank(val);
	}

	/**
	 * 对比字符串
	 * <p>
	 * "a" "A" result true
	 * </p>
	 * <p>
	 * "A" "A" result true
	 * </p>
	 * <p>
	 * "a" "b" result false
	 * </p>
	 * 
	 * @param str1
	 * @param str2
	 * @return boolean
	 * @date 2015年11月6日 下午4:47:53
	 * @author seiya
	 */
	public static boolean equalsIgnoreCase(String str1, String str2) {
		return StringUtils.equalsIgnoreCase(str1, str2);
	}

	/**
	 * 对比字符串
	 * <p>
	 * "a" "A" result false
	 * </p>
	 * <p>
	 * "A" "A" result true
	 * </p>
	 * <p>
	 * "a" "b" result false
	 * </p>
	 * 
	 * @param str1
	 * @param str2
	 * @return boolean
	 * @date 2015年11月6日 下午4:47:53
	 * @author seiya
	 */
	public static boolean equals(String str1, String str2) {
		return StringUtils.equals(str1, str2);
	}

	/**
	 * 截取字符之前的字符串
	 * 
	 * @param str
	 * @param separator
	 * @return
	 * @date 2015年11月30日 下午6:49:16
	 * @author seiya
	 */
	public static String substringBeforeLast(String str, String separator) {
		return StringUtils.substringBeforeLast(str, separator);
	}

	/**
	 * 截取字符之后的字符串
	 * 
	 * @param str
	 * @param separator
	 * @return
	 * @date 2015年12月1日 上午11:00:28
	 * @author seiya
	 */
	public static String substringAfterLast(String str, String separator) {
		return StringUtils.substringAfterLast(str, separator);
	}

	/**
	 * 截取两个字符之间的字符串
	 * 
	 * @param str
	 * @param separator1
	 * @param separator2
	 * @return
	 * @date 2015年12月1日 上午11:02:32
	 * @author seiya
	 */
	public static String substringBetween(String str, String separator1, String separator2) {
		return StringUtils.substringBetween(str, separator1, separator2);
	}

	/**
	 * 字符串是否为数字
	 * 
	 * @param str
	 * @return
	 * @date 2015年12月1日 下午4:22:43
	 * @author seiya
	 */
	public static boolean isDigital(String str) {
		return isBlank(str) ? false : str.matches("^[0-9]*$");
	}

	/**
	 * 字符串为非数字
	 * 
	 * @param str
	 * @return
	 * @date 2016年1月6日 上午11:21:04
	 * @author seiya
	 */
	public static boolean isNotDigital(String str) {
		return (!isDigital(str));
	}

	public static String joinString(List<?> params, String separator) {
		if (params == null) {
			return null;
		}
		return StringUtils.join(params, separator);
	}

	/**
	 * 验证邮箱
	 * 
	 * @param email
	 * @return
	 * @date 2016年2月16日 下午12:49:00
	 * @author seiya
	 */
	public static boolean isEmail(String email) {
		if (StringUtil.isBlank(email)) {
			return false;
		}
		String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
		return Pattern.matches(regex, email);
	}

}
