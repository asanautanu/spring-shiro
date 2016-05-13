package com.mpms.utils.conf.file.poi;

import java.util.Map;

/**
 * POI数据模型
 * 
 * @date 2015年11月6日 上午11:01:09
 * @author libo
 */
public class DataModel {

	protected Map<String, Object> data;

	/**
	 * @return the data
	 */
	public Map<String, Object> getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(Map<String, Object> data) {
		this.data = data;
	}

}
