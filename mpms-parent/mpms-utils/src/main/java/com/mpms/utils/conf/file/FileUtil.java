package com.mpms.utils.conf.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

/**
 * 文件解析类
 * @date 2015年10月27日 下午6:21:25
 * @author maliang
 */
public abstract class FileUtil {

	/**
	 * 文件读取
	 * @param path
	 * @return
	 * @date 2015年10月27日 下午6:33:42
	 * @author maliang
	 */
	public static File readFile(String path) {
		return FileUtils.getFile(path);
	}
	
	/**
	 * 解析以英文逗号(,)分隔的文件
	 * @param path	文件路径
	 * @param comma	逗号	,
	 * @return 字符数集合
	 * @throws IOException
	 * @date 2015-11-09 上午11:35:09
	 * @author libo
	 */
	public static List<String[]> parseFileByComma(String path ,String comma) throws IOException{
		return filePaser(path, comma);
	}

	/**
	 * 解析以英文单坚线(|)分隔的文件
	 * @param path	文件路径
	 * @param line	竖线	|
	 * @return	字符数集合
	 * @throws IOException
	 * @date 2015-11-09	上午11:37:24
	 * @author libo
	 */
	public static List<String[]> parseFileByLine(String path,String line) throws IOException {
		return filePaser(path, line);
	}
	
	/**
	 * 解析以英文双竖线(||)分隔的文件
	 * @param path	文件路径
	 * @param doubleLine	双竖线	||
	 * @return	字符数集合
	 * @throws IOException
	 * @date 2015-11-09	上午11:39:12
	 * @author libo
	 */
	public static List<String[]> parseFileByDoubleLine(String path,String doubleLine) throws IOException {
		return filePaser(path, doubleLine);
	}
	
	/**
	 * 解析指定分隔符的文件
	 * @param path	文件路径
	 * @param symbol	分隔符
	 * @return	字符数集合
	 * @throws IOException
	 * @date 2015-11-09 上午11:40:28
	 * @author libo
	 */
	public static List<String[]> filePaser(String path, String symbol) throws IOException {
		File file = readFile(path);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		List<String[]> data = new ArrayList<String[]>();
		String str =null;
		while((str=reader.readLine())!=null ){
			data.add(str.split(symbol));
		}
		reader.close();
		return data;
	}
}
