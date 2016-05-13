package com.mpms.utils.conf.file;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.mpms.utils.conf.file.poi.FileWrapper;
import com.mpms.utils.conf.file.poi.excel.ExcelDataModel;
import com.mpms.utils.conf.file.poi.excel.ExcelProcessor;
import com.mpms.utils.conf.file.poi.excel.ExcelXlsxProcessor;
import com.mpms.utils.conf.file.poi.excel.MergeRegionModel;

/**
 * Excel 文件工具类
 * <p>
 * 仅支持2007及以上版本
 * 
 * @date 2015年10月28日 上午10:33:13
 * @author libo
 */
public class ExcelFileUtil extends FileUtil {

	/**
	 * 生成excle
	 * <p>
	 * 2007版excle处理
	 * </p>
	 * 必须存在表头数据(header)
	 * <p>
	 * 以表头的key取得对应的数据,表头的key与数据(Map)中的key保持一致
	 * 
	 * @param header
	 *            表头
	 * @param data
	 *            数据域
	 * @return 文件二进制包装
	 * @date 2015-10-28 下午 16:07:27
	 * @author libo
	 * @throws IOException
	 */
	public static FileWrapper builder(List<ExcelDataModel> datas) throws IOException {
		ExcelProcessor excelProcessor = new ExcelXlsxProcessor();
		return excelProcessor.builder(datas);
	}

	/**
	 * 2007版 excle 解析
	 * <p>
	 * 解析所有的sheet,每个sheet中的数据都会合并到Map中
	 * </p>
	 * 
	 * @param istream
	 * @return Map格式数据
	 * @date 2015-10-28 下午4:07:51
	 * @author libo
	 * @throws IOException
	 */
	public static List<ExcelDataModel> parser(InputStream istream) throws IOException {
		ExcelProcessor excelProcessor = new ExcelXlsxProcessor();
		return excelProcessor.parser(istream);
	}

	/**
	 * 横向合并 单元格
	 * 
	 * @param istream
	 * @param sheetNo
	 *            工作薄
	 * @param rowNo
	 *            行
	 * @param cellNo
	 *            单元格
	 * @param num
	 *            合并多少个单元格
	 * @return
	 * @throws IOException
	 * @date 2015-11-06 下午4:46:08
	 * @author libo
	 */
	public static FileWrapper verticalMergeRegion(MergeRegionModel regionModel) throws IOException {
		ExcelProcessor excelProcessor = new ExcelXlsxProcessor();
		return excelProcessor.horizontalMergeRegion(regionModel.getStream(), regionModel.getSheet(),
				regionModel.getRow(), regionModel.getCell(), regionModel.getRegionNum());
	}

	/**
	 * 纵向合并 单元格
	 * 
	 * @param istream
	 * @param sheetNo
	 *            工作薄
	 * @param rowNo
	 *            行
	 * @param cellNo
	 *            单元格
	 * @param num
	 *            合并多少个列
	 * @return
	 * @throws IOException
	 * @date 2015-11-06 下午4:46:31
	 * @author libo
	 */
	public static FileWrapper horizontalMergeRegion(MergeRegionModel regionModel) throws IOException {
		ExcelProcessor excelProcessor = new ExcelXlsxProcessor();
		return excelProcessor.horizontalMergeRegion(regionModel.getStream(), regionModel.getSheet(),
				regionModel.getRow(), regionModel.getCell(), regionModel.getRegionNum());
	}
}
