package com.mpms.utils.conf.file.poi;

import java.io.IOException;
import java.io.Serializable;

import com.mpms.utils.string.StringUtil;

/**
 * 文件包装类<br>
 * 数据封装转换字节数组<br>
 * 默认文件名为时间戳<br>
 * 
 * @date 2015年10月28日 上午10:52:51
 * @author libo
 */
public class FileWrapper implements Serializable {

	// private Logger LOG = Logger.getLogger(getClass());
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3882204029541516492L;

	// 字节输出流
	// private ByteArrayOutputStream baos;
	private byte[] data;

	/**
	 * 含参构造
	 * 
	 * @param outStream
	 */
	public FileWrapper(byte[] data) {
		this.data = data;
	}

	/**
	 * @return 时间戳
	 */
	public String getFileName() {
		return System.nanoTime() + "";
	}

	/**
	 * @return the data
	 * @throws Exception
	 */
	public byte[] getData() throws Exception {
		try {
			return StringUtil.unZip2(data);
		} catch (IOException e) {
			// LOG.error("文件包装类,获取数据异常,{}", e);
			e.printStackTrace();
		}
		return null;
	}

}
