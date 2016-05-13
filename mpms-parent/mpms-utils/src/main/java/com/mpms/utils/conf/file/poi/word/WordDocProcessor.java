package com.mpms.utils.conf.file.poi.word;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.mpms.utils.conf.file.poi.FileWrapper;
import com.mpms.utils.string.StringUtil;

/**
 * @date 2015年10月28日 下午2:17:13
 * @author libo
 */
public class WordDocProcessor implements WordProcessor {

	@Override
	public FileWrapper doBuildByTemplate(InputStream istream, WordDataModel data) throws IOException {
		HWPFDocument document = processDocxTableAndText(istream, data);
		ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
		document.write(baos);
		return new FileWrapper(StringUtil.gzip(baos.toByteArray()));
	}

	@Override
	public FileWrapper doBuild(String content) throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
		byte[] b = content.getBytes("UTF-8");
		ByteArrayInputStream bais = new ByteArrayInputStream(b);
		POIFSFileSystem poifs = new POIFSFileSystem();
		DirectoryEntry dirEntry = poifs.getRoot();
		dirEntry.createDocument("WordDocument", bais);
		poifs.writeFilesystem(baos);
		bais.close();
		poifs.close();

		return new FileWrapper(StringUtil.gzip(baos.toByteArray()));
	}

	/**
	 * 替换doc模板变量
	 * 
	 * @param templateFilePath
	 * @param map
	 * @throws IOException
	 */
	private HWPFDocument processDocxTableAndText(InputStream istream, WordDataModel data) throws IOException {

		HWPFDocument document = new HWPFDocument(istream);
		// 替换变量
		this.replacePara(document, data.getData());

		// 替换表格
		this.replaceInTable(document, data.getData());

		document.getDocProperties().setFProtEnabled(true);
		document.getDocProperties().setFWCFtnEdn(true);
		document.getDocProperties().setFExactCWords(true);

		return document;
	}

	/**
	 * Doc替换模板里面的变量
	 * 
	 * @param doc
	 *            要替换的文档
	 * @param data
	 *            数据
	 * @date 2015-10-28 上午11:35:21
	 * @author libo
	 */
	private void replacePara(HWPFDocument doc, Map<String, Object> data) {
		Range range = doc.getRange();
		Iterator<Entry<String, Object>> iterator = data.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Object> entry = iterator.next();
			String key = entry.getKey();
			Object value = entry.getValue();
			range.replaceText(key, value == null ? "" : value.toString());
		}
	}

	/**
	 * Doc替换表格里面的变量
	 * <p>
	 * 若是多条数据将在表格后面追加表格行
	 * 
	 * @param doc
	 *            要替换的文档
	 * @param params
	 *            参数
	 * @date 2015-10-28 上午11:33:25
	 * @author libo
	 */
	private void replaceInTable(HWPFDocument doc, Map<String, Object> data) {
		Range range = doc.getRange();
		TableIterator iterator = new TableIterator(range);
		Table table = null;
		TableRow row = null;
		TableCell cell = null;
		Paragraph paragraph = null;
		List<String> tableHeader = new ArrayList<String>();
		// 从表格替换第二行的开始替换,第一行为表头
		while (iterator.hasNext()) {
			table = iterator.next();
			// 总行数
			int numRows = table.numRows();

			// 表格至少包括表头和第一行的占位符
			if (numRows < 1) {
				return;
			}
			for (int i = 1; i < numRows; i++) {
				row = table.getRow(i);
				for (int cn = 0; cn < row.numCells(); cn++) {
					cell = row.getCell(cn);
					for (int pn = 0; pn < cell.numParagraphs(); pn++) {
						paragraph = cell.getParagraph(pn);
						for (int nr = 0; nr < paragraph.numCharacterRuns(); nr++) {
							String text = paragraph.text();
							text = StringUtils.trimToEmpty(text);
							for (String key : data.keySet()) {
								if (text.contains(key)) {
									tableHeader.add(text);
									Object val = data.get(key);
									paragraph.replaceText(key, val == null ? null : val.toString());
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public List<String> getParameters(InputStream istream, String placeholder) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
