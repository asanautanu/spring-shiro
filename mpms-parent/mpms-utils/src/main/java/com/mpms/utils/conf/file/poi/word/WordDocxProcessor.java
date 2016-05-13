package com.mpms.utils.conf.file.poi.word;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import com.google.common.collect.Lists;
import com.mpms.utils.conf.file.WordFileUtil;
import com.mpms.utils.conf.file.poi.FileWrapper;
import com.mpms.utils.string.StringUtil;

/**
 * 2007版 word 处理
 * 
 * @date 2015年10月28日 下午2:17:13
 * @author libo
 */
public class WordDocxProcessor implements WordProcessor {

	@Override
	public FileWrapper doBuildByTemplate(InputStream istream, WordDataModel data) throws IOException {

		// 替换占位符
		XWPFDocument document = reploaceTableAndText(istream, data);

		document.enforceReadonlyProtection();
		document.enforceCommentsProtection();
		document.enforceTrackedChangesProtection();
		document.enforceFillingFormsProtection();

		ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
		document.write(baos);
		document.close();
		return new FileWrapper(StringUtil.gzip(baos.toByteArray()));
	}

	@Override
	public FileWrapper doBuild(String content) throws IOException {

		XWPFDocument document = new XWPFDocument();
		XWPFParagraph paragraph = document.createParagraph();
		XWPFRun run = paragraph.createRun();
		run.setText(content);

		document.enforceReadonlyProtection();
		document.enforceCommentsProtection();
		document.enforceTrackedChangesProtection();
		document.enforceFillingFormsProtection();

		ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
		document.write(baos);
		document.close();
		return new FileWrapper(StringUtil.gzip(baos.toByteArray()));
	}

	@Override
	public List<String> getParameters(InputStream istream, String placeholder) throws IOException {

		// 模板解析为XWPFDocument
		XWPFDocument document = new XWPFDocument(istream);
		// 参数存放容器
		List<String> parameters = Lists.newLinkedList();

		// 从段落中抽取
		this.getParametersFromText(document.getParagraphs(), placeholder, parameters);

		// 从表格中抽取
		this.getParametersFromTable(document.getTablesIterator(), placeholder, parameters);

		document.close();

		return parameters;
	}

	/**
	 * 替换指定模板中的表格和段落中的占位符
	 * <p>
	 * data.isMulti() 为true是才会替换表格中的占位符, 请查看
	 * {@linkplain com.mpms.utils.conf.file.poi.word.WordDataModel#isMulti()}
	 * 会自动添加行
	 * </p>
	 * 
	 * @param istream
	 *            模板流
	 * @param data
	 *            数据
	 * @return 替换后的XWPFDocument
	 * @throws IOException
	 * @date 2015-11-19 下午5:46:07
	 * @author libo
	 */
	private XWPFDocument reploaceTableAndText(InputStream istream, WordDataModel data) throws IOException {

		// 模板解析为XWPFDocument
		XWPFDocument document = new XWPFDocument(istream);

		// 替换段落里面的变量
		this.replaceText(document.getParagraphs(), data.getData());

		// 替换表格中的占位符
		this.replaceTable(document, data.getData());

		return document;
	}

	/**
	 * 替换段落内容
	 * 
	 * @param doc
	 * @param params
	 * @date 2015-11-19 下午5:10:47
	 * @author libo
	 */
	private void replaceText(List<XWPFParagraph> paragraphList, Map<String, Object> params) {
		XWPFParagraph paragraph = null;
		for (int i = 0; i < paragraphList.size(); i++) {
			paragraph = paragraphList.get(i);
			List<XWPFRun> runs = paragraph.getRuns();
			// 删除原来内容
			for (int j = runs.size() - 1; j >= 0; j--) {
				XWPFRun run = runs.get(j);
				String runText = run.toString();
				Pattern mpattern = Pattern.compile(WordFileUtil.DEFAULT_VARIALES_PATTERN);
				Matcher mmatcher = mpattern.matcher(runText);
				while (mmatcher.find()) {
					Object obj = params.get(mmatcher.group());
					if (null != obj) {
						runText = obj.toString();
					}
					run.setText(runText == null ? "" : runText, 0);
					paragraph.addRun(run);
				}

			}
		}
	}

	/**
	 * 替换表格中的占位符
	 * 
	 * @param document
	 *            文档
	 * @param data
	 *            数据
	 * @date 2015-11-20 上午10:47:56
	 * @author libo
	 */
	private void replaceTable(XWPFDocument document, Map<String, Object> data) {
		Iterator<XWPFTable> it = document.getTablesIterator();
		XWPFTable table = null;
		List<XWPFTableCell> cells = null;
		while (it.hasNext()) {
			table = it.next();
			List<XWPFTableRow> rows = table.getRows();
			for (XWPFTableRow row : rows) {
				cells = row.getTableCells();
				for (XWPFTableCell cell : cells) {
					// table数据替换
					this.replaceText(cell.getParagraphs(), data);
				}
			}
		}
	}

	/**
	 * 设置样式
	 * 
	 * @param run
	 * @param styleMap
	 *            样式Map
	 * @date 2015-11-20 上午11:53:11
	 * @author libo
	 */
	private void setRunStyle(XWPFRun run, WordStyleModel style) {
		run.setBold(style.isBold());
		run.setItalic(style.isItalic());
		run.setUnderline(style.getUnderline());
		run.setColor(style.getColor());
		run.setTextPosition(style.getTextPosition());
	}

	/**
	 * 封装样式
	 * 
	 * @param run
	 * @return 样式
	 * @date 2015-11-20 下午12:15:38
	 * @author libo
	 */
	private WordStyleModel getRunStyle(XWPFRun run) {
		WordStyleModel style = new WordStyleModel();
		style.setBold(run.isBold());
		style.setItalic(run.isItalic());
		style.setUnderline(run.getUnderline());
		style.setColor(run.getColor());
		style.setTextPosition(run.getTextPosition());

		if (run.getFontSize() != -1) {
			style.setFontSize(run.getFontSize());
		}
		if (run.getFontFamily() != null) {
			style.setFontFamily(run.getFontFamily());
		}
		return style;
	}

	/**
	 * 从段落中抽中占位符
	 * 
	 * @param paragraphList
	 *            段落列表
	 * @param placeholder
	 *            指定占位符样式匹配的正则表达式
	 * @param parameters
	 *            占位符存放的List
	 * @date 2015-11-24 上午10:40:27
	 * @author libo
	 */
	private void getParametersFromText(List<XWPFParagraph> paragraphList, String placeholder, List<String> parameters) {
		XWPFParagraph paragraph = null;

		for (int i = 0; i < paragraphList.size(); i++) {
			paragraph = paragraphList.get(i);
			String tmpPlaceholder = paragraph.getText();
			if (StringUtil.isNotBlank(tmpPlaceholder)) {
				if (StringUtil.isBlank(placeholder)) {
					parameters.add(tmpPlaceholder.substring(1, (tmpPlaceholder.length() - 1)));
				} else {
					Pattern mpattern = Pattern.compile(placeholder);
					Matcher mmatcher = mpattern.matcher(tmpPlaceholder);
					while (mmatcher.find()) {
						parameters.add(mmatcher.group());
					}
				}
			}
		}
	}

	/**
	 * 从表格中抽取占位符
	 * 
	 * @param document
	 *            文档
	 * @param placeholder
	 *            指定占位符
	 * @param parameters
	 *            占位符存放列表
	 * @date 2015-11-24 上午10:43:34
	 * @author libo
	 */
	private void getParametersFromTable(Iterator<XWPFTable> it, String placeholder, List<String> parameters) {
		XWPFTable table = null;
		List<XWPFTableCell> cells = null;
		while (it.hasNext()) {
			table = it.next();
			List<XWPFTableRow> rows = table.getRows();
			for (XWPFTableRow row : rows) {
				cells = row.getTableCells();
				for (XWPFTableCell cell : cells) {
					this.getParametersFromText(cell.getParagraphs(), placeholder, parameters);
				}
			}
		}
	}

	/**
	 * 替换表格中的占位符
	 * <p>
	 * 为了数准确,请保证 multiData 顺序性
	 * 
	 * @param doc
	 * @param multiData
	 * @date 2015-11-19 下午5:12:41
	 * @author libo
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private void replaceTableAndAppendRows(XWPFDocument document, List<WordDataModel> multiData) {
		WordDataModel firstModel = multiData.remove(0);
		Map<String, Object> firstRowData = firstModel.getData();
		// 记录表头顺序
		List<String> header = new ArrayList<String>();

		// 表格内容替换添加
		Iterator<XWPFTable> it = document.getTablesIterator();
		XWPFTable table = null;
		XWPFTableRow row = null;
		String key = null;
		List<XWPFTableCell> cells = null;
		while (it.hasNext()) {
			table = it.next();
			row = table.getRow(1);
			cells = row.getTableCells();
			for (XWPFTableCell cell : cells) {
				// 单元格Text
				String cellText = cell.getText();

				for (Entry<String, Object> e : firstRowData.entrySet()) {
					key = String.format("${%s}", e.getKey());
					if (cellText.equalsIgnoreCase(key)) {
						// 删除原来内容
						cell.removeParagraph(0);
						cell.getColor();
						Object val = e.getValue();
						// 写入新内容
						cell.setText(val == null ? "" : val.toString());
						header.add(e.getKey());
					}
				}
			}

			if (header == null || header.isEmpty()) {
				return;
			}
			// 追加行
			appendTableRows(table, header, multiData);

		}

	}

	/**
	 * 追加行数并填充数据
	 * 
	 * @param header
	 *            表头顺序Key
	 * @param table
	 *            被操作的表格
	 * @param data
	 *            被填充的数
	 * @date 2015-11-19 下午5:19:02
	 * @author libo
	 */
	@Deprecated
	private void appendTableRows(XWPFTable table, List<String> header, List<WordDataModel> data) {

		// 表格行
		XWPFTableRow row;

		// 单元格列表
		List<XWPFTableCell> cells;

		// 迭代数据,生成行数
		for (WordDataModel dataModel : data) {
			// 创建行
			row = table.createRow();
			cells = row.getTableCells();
			if (cells == null || cells.size() == 0) {
				continue;
			}

			Map<String, Object> cellData = dataModel.getData();
			if (cellData == null || cellData.isEmpty()) {
				continue;
			}

			for (int i = 0; i < cells.size(); i++) {
				Object val = cellData.get(header.get(i));
				cells.get(i).setText(val == null ? "" : val.toString());
			}
		}
	}
}
