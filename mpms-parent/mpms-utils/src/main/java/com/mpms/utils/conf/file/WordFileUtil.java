package com.mpms.utils.conf.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.mpms.utils.conf.file.poi.FileWrapper;
import com.mpms.utils.conf.file.poi.word.WordDataModel;
import com.mpms.utils.conf.file.poi.word.WordDocxProcessor;
import com.mpms.utils.conf.file.poi.word.WordProcessor;
import com.mpms.utils.string.StringUtil;

/**
 * word 文件工具类
 * <p>
 * 仅支持2007及以上版本
 * 
 * @date 2015年10月28日 上午10:31:32
 * @author libo
 */
public class WordFileUtil extends FileUtil {

	/**
	 * 文档参数变量匹配的正则表达式
	 */
	public static final String DEFAULT_VARIALES_PATTERN = "\\$\\{[\\w]+\\}";

	/**
	 * 根据模板生成2007版(docx)文件,仅支持2007的及以上的版本
	 * <p>
	 * 匹配替换占位符,未匹配的占位符用空值替换
	 * 
	 * @param istream
	 *            模板输入流
	 * @param data
	 *            数据
	 * @return 文件名和输出流
	 * @date 2015年10月28日 上午11:25:57
	 * @author libo
	 * @throws IOException
	 *             文件找不到所产生
	 */
	public static FileWrapper buildByTemplate(InputStream istream, WordDataModel data) throws IOException {
		if (istream == null) {
			throw new IOException("文件流为空");
		}

		WordProcessor docxProcessor = new WordDocxProcessor();
		return docxProcessor.doBuildByTemplate(istream, data);
	}

	/**
	 * 生成 docx文档流,不需要使用模板
	 * 
	 * @param content
	 * @return
	 * @throws IOException
	 * @date 2015-11-02 下午3:50:39
	 * @author libo
	 */
	public static FileWrapper build(String content) throws IOException {
		if (StringUtils.isBlank(content)) {
			return null;
		}
		WordProcessor docxProcessor = new WordDocxProcessor();
		return docxProcessor.doBuild(content);
	}

	/**
	 * 在模板中,查找包含占位符的所有参数<br>
	 * 占位符必须以2个字符作为开始处,结束处来1个字符 <br>
	 * 默认占位符为英文字符:${}
	 * 
	 * @param istream
	 *            模板输入流
	 * @param placeholder
	 *            占位符
	 * @return 参数列表
	 * @date 2015-11-23 下午6:41:08
	 * @author libo
	 * @throws IOException
	 */
	public static List<String> getParameters(InputStream istream, String placeholder) throws IOException {
		if (istream == null) {
			throw new IOException("文件流为空");
		}

		if (StringUtil.isBlank(placeholder)) {
			placeholder = DEFAULT_VARIALES_PATTERN;
		}

		WordProcessor docxProcessor = new WordDocxProcessor();
		return docxProcessor.getParameters(istream, placeholder);
	}

	public static void main(String[] args) {
		InputStream inputStream = null;// 获取文件输入流
		List<String> enNames;
		try {
			inputStream = new FileInputStream(new File("E:\\test.docx"));// hdfsStore.down(target);//
																			// 获取文件输入流
			// enNames = WordFileUtil.getParameters(inputStream, null);
			// System.out.println(enNames);
			WordDataModel data = new WordDataModel();

			Map<String, Object> mapData = new LinkedHashMap<String, Object>();
			mapData.put("无效", "dd");
			data.setData(mapData);
			WordFileUtil.buildByTemplate(inputStream, data);
			System.out.println("结束");
			// String string = "编号：${loanNumber}";
			// Pattern mpattern = Pattern.compile("\\$\\{[\\w]+\\}");
			// Matcher mmatcher = mpattern.matcher(string);
			// while(mmatcher.find()){
			// System.out.println(mmatcher.group());
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
