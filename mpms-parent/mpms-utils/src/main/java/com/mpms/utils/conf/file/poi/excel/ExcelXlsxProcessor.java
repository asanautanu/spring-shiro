package com.mpms.utils.conf.file.poi.excel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mpms.utils.conf.file.poi.FileWrapper;
import com.mpms.utils.date.DateTool;
import com.mpms.utils.string.StringUtil;

/**
 * 2007版处理
 *
 * @date 2015年10月28日 下午3:31:12
 * @author libo
 */
public class ExcelXlsxProcessor implements ExcelProcessor {

	@Override
	public FileWrapper builder(List<ExcelDataModel> dataModels) throws IOException {
		if (CollectionUtils.isEmpty(dataModels)) {
			return null;
		}
		XSSFWorkbook workBook = new XSSFWorkbook();
		for (ExcelDataModel dataModel : dataModels) {
			Map<String, String> header = dataModel.getHeader();
			Map<String, String> data;
			List<Map<String, String>> dataList = dataModel.getDataList();

			// 若不存在表头则不进行处理
			// if (dataModel == null || header == null || header.isEmpty() ||
			// dataList == null || dataList.isEmpty()) {
			if (MapUtils.isEmpty(header)) {
				continue;
			}
			XSSFSheet sheet = workBook.createSheet();

			// 创建表头
			XSSFRow titleRow = sheet.createRow(0);
			Set<String> keySet = header.keySet();
			int hi = 0;
			for (String hk : keySet) {
				titleRow.createCell(hi).setCellValue(header.get(hk) + "");
				hi++;
			}

			// 填充单元格
			if (CollectionUtils.isNotEmpty(dataList)) {
				for (int i = 0; i < dataList.size(); i++) {
					data = dataList.get(i);
					if (MapUtils.isEmpty(data)) {
						continue;
					}
					XSSFRow dataRow = sheet.createRow(i + 1);
					// 按表头的K取数据并填充
					hi = 0;
					for (String hk : header.keySet()) {
						String dv = data.get(hk) + "";
						dataRow.createCell(hi).setCellValue(dv);
						hi++;
					}
				}
			}
		}

		// 转换为字节输出流
		ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
		workBook.write(baos);
		workBook.close();
		return new FileWrapper(StringUtil.gzip(baos.toByteArray()));
	}

	@Override
	public List<ExcelDataModel> parser(InputStream istream) throws IOException {
		if (istream == null) {
			return null;
		}
		List<ExcelDataModel> datas = new ArrayList<ExcelDataModel>(3);
		ExcelDataModel data = null;
		Map<String, String> header = null;
		Map<String, String> dataMap = null;
		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>(5);

		XSSFWorkbook workBook = new XSSFWorkbook(istream);

		// 工作薄数量
		int sheetNumber = workBook.getNumberOfSheets();

		// 解析每个工作薄
		for (int i = 0; i < sheetNumber; i++) {
			XSSFSheet sheet = workBook.getSheetAt(i);

			// 行数
			int totalRows = sheet.getPhysicalNumberOfRows();
			if (totalRows == 0) {
				continue;
			}

			// 解析表头
			header = parseTableHeader(sheet.getRow(0));

			// 跳过标头,解析每行数据
			for (int r = 1; r < totalRows; r++) {
				dataMap = new LinkedHashMap<String, String>();
				XSSFRow row = sheet.getRow(r);
				if (row == null) {
					continue;
				}
				// 每行单元格数量
				int numberCells = row.getPhysicalNumberOfCells();
				if (numberCells < 0) {
					continue;
				}
				for (int cellnum = 0; cellnum < row.getPhysicalNumberOfCells(); cellnum++) {
					XSSFCell cell = row.getCell(cellnum);
					if (cell == null) {
						continue;
					}
					// 以字符串方式获取单元格值
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						if (HSSFDateUtil.isCellDateFormatted(cell)) {
							Date date = cell.getDateCellValue();
							dataMap.put(cellnum + "", DateTool.dateToString(date, DateTool.DATE_PATTERN_DEFAULT));
						} else {

							dataMap.put(cellnum + "", NumberToTextConverter.toText(cell.getNumericCellValue()));
						}
						break;

					default:
						cell.setCellType(Cell.CELL_TYPE_STRING);
						dataMap.put(cellnum + "", cell.getStringCellValue());
						break;
					}
				}
				dataList.add(dataMap);
			}
			data = new ExcelDataModel(header, dataList);
			datas.add(data);
		}
		workBook.close();
		istream.close();

		return datas;
	}

	/**
	 * 解析表头
	 * 
	 * @param row
	 *            表头行
	 * @return 表头数据值
	 * @date 2015-11-06 下午12:21:55
	 * @author libo
	 */
	private Map<String, String> parseTableHeader(XSSFRow row) {
		if (row == null) {
			return null;
		}
		Map<String, String> data = new LinkedHashMap<String, String>(10);
		int numberCells = row.getPhysicalNumberOfCells();
		if (numberCells < 0) {
			return null;
		}

		// 以字符串类型获取单元格值
		for (int cellnum = 0; cellnum < row.getPhysicalNumberOfCells(); cellnum++) {
			XSSFCell cell = row.getCell(cellnum);
			if (cell == null) {
				continue;
			}
			cell.setCellType(Cell.CELL_TYPE_STRING);
			data.put(cellnum + "", cell.getStringCellValue());
		}
		return data;
	}

	@Override
	public FileWrapper verticalMergeRegion(InputStream istream, int sheetNo, int rowNo, int cellNo, int num)
			throws IOException {
		CellRangeAddress cra = new CellRangeAddress(rowNo, rowNo, cellNo, (cellNo + num - 1));
		ByteArrayOutputStream baos = mergeRegion(istream, sheetNo, cra);
		return new FileWrapper(StringUtil.gzip(baos.toByteArray()));
	}

	@Override
	public FileWrapper horizontalMergeRegion(InputStream istream, int sheetNo, int rowNo, int cellNo, int num)
			throws IOException {

		CellRangeAddress cra = new CellRangeAddress(rowNo, (rowNo + num - 1), cellNo, cellNo);
		ByteArrayOutputStream baos = mergeRegion(istream, sheetNo, cra);

		return new FileWrapper(StringUtil.gzip(baos.toByteArray()));
	}

	/**
	 * 单元格合并,会丢失数据
	 * 
	 * @param instream
	 *            输入流
	 * @param sheetNo
	 *            工作序号
	 * @param cra
	 *            合并位置
	 * @return 输出字节流
	 * @throws IOException
	 * @date 2015-11-13 上午10:43:16
	 * @author libo
	 */
	private ByteArrayOutputStream mergeRegion(InputStream istream, int sheetNo, CellRangeAddress cra)
			throws IOException {
		XSSFWorkbook workBook = new XSSFWorkbook(istream);
		XSSFSheet sheet = workBook.getSheetAt(sheetNo);
		sheet.addMergedRegion(cra);

		ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
		workBook.write(baos);
		workBook.close();
		return baos;
	}

}
