package com.mpms.utils.conf.file.poi.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.mpms.utils.conf.file.poi.FileWrapper;

/**
 * excle处理器
 * 
 * @date 2015年10月28日 下午3:23:20
 * @author libo
 */
public interface ExcelProcessor {

	/**
	 * 构建新的excel
	 * <p>
	 * 可能存在多个sheet
	 * <p>
	 * 必须存在表头数据(header)
	 * <p>
	 * 以表头的key取得对应的数据,表头的key与数据(Map)中的key保持一致
	 * 
	 * @param data
	 * @return
	 * @date 2015-11-06 上午11:41:47
	 * @author libo
	 * @throws IOException
	 */
	public FileWrapper builder(List<ExcelDataModel> dataModels) throws IOException;

	/**
	 * 解析excel
	 * <p>
	 * 可能存在多个sheet
	 * <p>
	 * 文件必须包括表头,否则会跳过第一行数据
	 * <P>
	 * 以表头的key取得对应的数据,表头的key与数据(Map)中的key保持一致
	 * 
	 * @param istream
	 *            输入流
	 * @return
	 * @throws IOException
	 * @date 2015-11-06 上午11:42:34
	 * @author libo
	 */
	public List<ExcelDataModel> parser(InputStream istream) throws IOException;

	/**
	 * 纵向合并
	 * <p>
	 * 合并后被合并单元格数据将丢失
	 * 
	 * @param inStream
	 *            输入流
	 * @param rowNo
	 *            合并开始行号
	 * @param cellNo
	 *            合并开始单元格
	 * @param num
	 *            合并单元格数
	 * @return
	 * @date 2015-11-06 下午2:41:42
	 * @author libo
	 * @throws IOException
	 */
	public FileWrapper verticalMergeRegion(InputStream istream, int sheetNo, int rowNo, int cellNo, int num)
			throws IOException;

	/**
	 * 横向合并
	 * <p>
	 * 合并后被合并单元格数据将丢失
	 * 
	 * @param inStream
	 *            输入流
	 * @param rowNo
	 *            合并开始行号
	 * @param cellNo
	 *            合并开始单元格
	 * @param num
	 *            合并单元格数
	 * @return
	 * @date 2015-11-06 下午2:38:14
	 * @author libo
	 */
	public FileWrapper horizontalMergeRegion(InputStream istream, int sheetNo, int rowNo, int cellNo, int num)
			throws IOException;

}
