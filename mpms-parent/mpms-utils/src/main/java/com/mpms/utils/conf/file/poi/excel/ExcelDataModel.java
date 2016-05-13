package com.mpms.utils.conf.file.poi.excel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.mpms.utils.conf.file.poi.DataModel;
import com.mpms.utils.string.StringUtil;

/**
 * excel 数据模型
 * <p>
 * header是表头部分,用于确定单元格的数据.
 * </p>
 * <p>
 * data是数据,填充在单元格内.header是K/V型,data也是K/V型,header和data必须拥有相同K方可确定单元格数据
 * </p>
 * 
 * @date 2015年11月6日 上午11:00:10
 * @author libo
 */
public class ExcelDataModel extends DataModel {

	// 表头数据 key为序号 从0开始
	private Map<String, String> header;

	// 解析时数据存放 Map 的 key为序号 从0开始与header 的key 一一对应
	private List<Map<String, String>> dataList;

	/**
	 * 全参构造
	 * 
	 * @param header
	 * @param data
	 */
	public ExcelDataModel(Map<String, String> header, Map<String, Object> data) {
		this.header = header;
		super.setData(data);
	}

	/**
	 * 全参构造
	 * 
	 * @param header
	 * @param dataList
	 */
	public ExcelDataModel(Map<String, String> header, List<Map<String, String>> dataList) {
		this.header = header;
		this.dataList = dataList;
	}

	/**
	 * @return the header
	 */
	public Map<String, String> getHeader() {
		return header;
	}

	/**
	 * @param header
	 *            the header to set
	 */
	public void setHeader(Map<String, String> header) {
		this.header = header;
	}

	/**
	 * 解析时数据存放
	 * 
	 * @return the dataList
	 */
	public List<Map<String, String>> getDataList() {
		return dataList;
	}

	/**
	 * @param dataList
	 *            the dataList to set
	 */
	public void setDataList(List<Map<String, String>> dataList) {
		this.dataList = dataList;
	}

	/**
	 * 注意顺序性
	 * 
	 * @param key
	 * @return
	 * @date 2016年1月25日 下午12:05:42
	 * @author libo
	 */
	public List<Map<String, String>> getDataListByKey(List<String> keys) {
		if (CollectionUtils.isEmpty(dataList) || CollectionUtils.isEmpty(keys)) {
			return null;
		}
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		for (Map<String, String> data : dataList) {
			Map<String, String> tmp = new LinkedHashMap<String, String>(keys.size());
			Set<Map.Entry<String, String>> entry = data.entrySet();
			Iterator<Map.Entry<String, String>> iterator = entry.iterator();
			int i = 0;
			while (iterator.hasNext()) {
				String tmpKey = null;
				try {
					tmpKey = keys.get(i);
				} catch (IndexOutOfBoundsException e) {
					break;
				}
				if (StringUtil.isBlank(tmpKey)) {
					continue;
				}
				String val = iterator.next().getValue();
				tmp.put(tmpKey, val);
				i++;
			}
			result.add(tmp);
		}

		return result;
	}

}
