package com.mpms.utils.conf.file.poi.word;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.mpms.utils.conf.file.poi.FileWrapper;

/**
 * word处理器
 * 
 * @date 2015年10月28日 下午2:22:19
 * @author libo
 */
public interface WordProcessor {

	/**
	 * 根据word模板生成文档
	 * <p>
	 * 若模板文件中存在表格,表格必须包括表头和占符位,即表格第一行为表头,第二行为数据占位符
	 * <p>
	 * 表格多数据替换时,程序会自动追加数据行
	 * 
	 * @param istream
	 *            模板输入流
	 * @param data
	 *            被填充的数据
	 * @return 文件包装类({#link com.mpms.utils.conf.file.poi.FileWrapper})
	 * @throws IOException
	 * @date 2015-10-28 下午2:23:46
	 * @author libo
	 */
	FileWrapper doBuildByTemplate(InputStream istream, WordDataModel data) throws IOException;

	/**
	 * 生成doc
	 * 
	 * @param content
	 *            word内容
	 * @return 文件包装类({#link com.mpms.utils.conf.file.poi.FileWrapper})
	 * @date 2015-11-02 下午3:21:37
	 * @author libo
	 * @throws IOException
	 */
	FileWrapper doBuild(String content) throws IOException;

	/**
	 * 在模板中,查找包含占位符的所有参数<br>
	 * 占位符必须以2个字符作为开始处,结束处来1个字符<br>
	 * 默认占位符为英文字符:${}
	 * 
	 * @param istream
	 *            模板输入流
	 * @param placeholder
	 *            占位符
	 * @return 参数列表
	 * @throws IOException
	 * @date 2015-11-23 下午6:15:36
	 * @author libo
	 */
	public List<String> getParameters(InputStream istream, String placeholder) throws IOException;
}
