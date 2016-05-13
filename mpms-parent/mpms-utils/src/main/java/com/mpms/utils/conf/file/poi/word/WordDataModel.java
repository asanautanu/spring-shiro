package com.mpms.utils.conf.file.poi.word;

import com.mpms.utils.conf.file.poi.DataModel;

/**
 * word数据模型
 * 
 * @date 2015年10月29日 下午3:27:00
 * @author libo
 */
public class WordDataModel extends DataModel {

	/**
	 * 多数据的长度
	 * 
	 * @return
	 * @date 2015-10-29 下午3:25:32
	 * @author libo
	 */
	public int size() {
		if (super.data == null || super.data.isEmpty()) {
			return 0;
		}
		return super.data.size();
	}

}
