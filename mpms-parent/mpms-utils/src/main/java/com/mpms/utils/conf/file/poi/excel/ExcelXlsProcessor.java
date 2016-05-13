package com.mpms.utils.conf.file.poi.excel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;

import com.mpms.utils.conf.file.poi.FileWrapper;
import com.mpms.utils.string.StringUtil;

/**
 * 2003版处理
 *
 * @date 2015年10月28日 下午3:31:12
 * @author libo
 */
public class ExcelXlsProcessor implements ExcelProcessor {

	@Override
	public FileWrapper builder(List<ExcelDataModel> dataModelList) throws IOException {
		if (dataModelList == null || dataModelList.isEmpty()) {
			return null;
		}
		HSSFWorkbook workBook = new HSSFWorkbook();
		for (ExcelDataModel dataModel : dataModelList) {
			Map<String, String> header = dataModel.getHeader();
			Map<String, Object> data = dataModel.getData();
			if (dataModel == null || header == null || header.isEmpty() || data == null || data.isEmpty()) {
				continue;
			}
			HSSFSheet sheet = workBook.createSheet();
			// 创建表头
			HSSFRow titleRow = sheet.createRow(0);
			for (int i = 0; i < header.size(); i++) {
				titleRow.createCell(i).setCellValue(header.get(i));
			}
			// 填充数据
			if (!(data == null || data.isEmpty())) {
				for (int i = 0; i < data.size(); i++) {
					HSSFRow dataRow = sheet.createRow(i + 1);
					if (data == null || data.isEmpty()) {
						continue;
					}
					for (int j = 0; j < header.size(); j++) {
						Object val = data.get(header.get(j));
						dataRow.createCell(j).setCellValue(val == null ? "" : val.toString());
					}
				}
			}
		}
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
		ExcelDataModel data = null;
		Map<String, String> header = null;
		Map<String, Object> dataMap = null;
		HSSFWorkbook workBook = new HSSFWorkbook(istream);
		int sheetNumber = workBook.getNumberOfSheets();
		for (int i = 0; i < sheetNumber; i++) {
			HSSFSheet sheet = workBook.getSheetAt(i);
			int totalRows = sheet.getPhysicalNumberOfRows();
			// 解析表头
			header = this.parseTableHeader(sheet.getRow(0));
			// 跳过标头
			for (int r = 1; r < totalRows; r++) {
				dataMap = new HashMap<String, Object>(10);
				HSSFRow row = sheet.getRow(r);
				for (int cellnum = 0; cellnum < row.getPhysicalNumberOfCells(); cellnum++) {
					if (row.getCell(cellnum) != null) {
						dataMap.put(cellnum + "", row.getCell(cellnum).getStringCellValue());
					}
				}
			}
			data = new ExcelDataModel(header, dataMap);
		}
		List<ExcelDataModel> datas = new ArrayList<ExcelDataModel>(3);
		datas.add(data);
		workBook.close();

		return datas;
	}

	/**
	 * 解析表头
	 * 
	 * @param row
	 *            表头行
	 * @return
	 * @date 2015-11-06 下午12:21:55
	 * @author libo
	 */
	private Map<String, String> parseTableHeader(HSSFRow row) {
		if (row == null) {
			return null;
		}
		Map<String, String> data = new HashMap<String, String>(10);
		int numberCells = row.getPhysicalNumberOfCells();
		if (numberCells < 0) {
			return null;
		}
		for (int cellnum = 0; cellnum < row.getPhysicalNumberOfCells(); cellnum++) {
			HSSFCell cell = row.getCell(cellnum);
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
		HSSFWorkbook workBook = new HSSFWorkbook(istream);
		HSSFSheet sheet = workBook.getSheetAt(sheetNo);
		CellRangeAddress cra = new CellRangeAddress(rowNo, rowNo, cellNo, (cellNo + num - 1));
		sheet.addMergedRegion(cra);

		ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
		workBook.write(baos);
		workBook.close();

		return new FileWrapper(StringUtil.gzip(baos.toByteArray()));
	}

	@Override
	public FileWrapper horizontalMergeRegion(InputStream instream, int sheetNo, int rowNo, int cellNo, int num)
			throws IOException {
		HSSFWorkbook workBook = new HSSFWorkbook(instream);
		HSSFSheet sheet = workBook.getSheetAt(sheetNo);
		CellRangeAddress cra = new CellRangeAddress(rowNo, (rowNo + num - 1), cellNo, cellNo);
		sheet.addMergedRegion(cra);

		ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
		workBook.write(baos);
		workBook.close();

		return new FileWrapper(StringUtil.gzip(baos.toByteArray()));
	}

}
