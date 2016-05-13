package com.mpms.utils.conf.file;

import java.util.Iterator;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * properties 配置文件工具类
 * @date 2015年10月27日 下午6:20:50
 * @author maliang
 */
public class PropertiesFileUtil extends FileUtil {

	/**
	 * 资源文件对象
	 */
	private static PropertiesConfiguration configuration = null;

	static {
		if (configuration == null) {
			configuration = new PropertiesConfiguration();
		}
	}

	/**
	 * 构建properties解析类 
	 * @param filePath
	 * @return
	 * @date 2015年10月27日 下午6:37:18
	 * @author maliang
	 * @throws ConfigurationException 
	 */
	public static PropertiesFileUtil builder(String filePath)
			throws ConfigurationException {
		if (StringUtils.isBlank(filePath)) {
			return null;
		}

		configuration.load(filePath);

		return new PropertiesFileUtil();
	}

	/**
	 * 获取properties文件的指定行的数据 
	 * @param number
	 * @return
	 * @date 2015年10月27日 下午6:44:02
	 * @author maliang
	 */
	public String getFileLine(int number) {
		int i = 1;
		String line = "";
		for (Iterator<String> keys = configuration.getKeys(); keys.hasNext();) {
			if (i == number) {
				line = keys.next();
			}
			i++;
		}
		return line;
	}

	/**
	 * 获取所有key 
	 * @return
	 * @date 2015年10月28日 上午10:14:37
	 * @author maliang
	 */
	public Iterator<String> getKeys() {
		return configuration.getKeys();
	}

	/**
	 * 根据key 获取指定值
	 * @param key
	 * @return
	 * @date 2015年10月27日 下午6:45:04
	 * @author maliang
	 */
	public String getString(String key) {
		return getString(key, "");
	}

	public String getString(String key, String defaultStr) {
		return configuration.getString(key, defaultStr);
	}

	public String[] getStringArray(String key) {
		return configuration.getStringArray(key);
	}

	public int getInt(String key) {
		return getInt(key, 0);
	}

	public int getInt(String key, int defaultValue) {
		return configuration.getInt(key, defaultValue);
	}

	public short getShort(String key) {
		return configuration.getShort(key, (short) 0);
	}

	public long getLong(String key) {
		return configuration.getLong(key, 0l);
	}

	public Boolean getBoolean(String key) {
		return configuration.getBoolean(key, false);
	}

}
