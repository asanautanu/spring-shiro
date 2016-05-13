package com.mpms.web.messageconverter;

import java.io.IOException;
import java.util.Date;

import org.springframework.expression.ParseException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.mpms.utils.date.DateTool;
import com.mpms.utils.string.StringUtil;

/**
 * 日期序列化
 * 
 * @author seiya
 * @date 2016年5月12日
 *
 */
public class DateJsonDeserializer extends JsonDeserializer<Date> {
	private static final String DATE_PATTERN_MINUTE = "yyyy-MM-dd HH:mm";
	private static final String DATE_PATTERN_HOUR = "yyyy-MM-dd HH";
	private static final String DATE_PATTERN_DEFAULT = "yyyy-MM-dd HH:mm:ss";
	private static final String DATE_PATTERN_DAY = "yyyy-MM-dd";
	private static final String DATE_PATTERN_DAY_SLASH = "MM/dd/yyyy";
	private boolean allowEmpty = true;

	@Override
	public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		String text = p.getText();
		if (this.allowEmpty && StringUtil.isBlank(text)) {
			return null;
		} else {
			try {
				Date date = null;
				boolean flag = false;
				try {
					date = stringToDate(text, DATE_PATTERN_DEFAULT);
					flag = true;

				} catch (Exception e) {
				}
				if (!flag) {
					try {
						date = stringToDate(text, DATE_PATTERN_DAY);
						flag = true;
					} catch (Exception e) {
					}
				}
				if (!flag) {
					try {
						date = stringToDate(text, DATE_PATTERN_MINUTE);
						flag = true;
					} catch (Exception e) {
					}
				}
				if (!flag) {
					try {
						date = stringToDate(text, DATE_PATTERN_HOUR);
						flag = true;
					} catch (Exception e) {
					}
				}

				if (!flag) {
					try {
						date = stringToDate(text, DATE_PATTERN_DAY_SLASH);
						flag = true;
					} catch (Exception e) {
					}
				}

				return date;
			} catch (ParseException ex) {
				throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
			}
		}
	}

	/**
	 * 将字符串转化为日期。 字符串格式("YYYY-MM-DD")。
	 * 例如："2012-07-01"或者"2012-7-1"或者"2012-7-01"或者"2012-07-1"是等价的。
	 * 
	 * @param str
	 * @param pattern
	 * @return
	 */
	public static Date stringToDate(String str, String pattern) {
		return DateTool.stringToDate(str, pattern);
	}

}
