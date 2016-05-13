package com.mpms.utils.conf.file.poi.excel;

import java.io.InputStream;

/**
 * excel单元格合并
 * @date 2015年11月6日 下午4:49:13
 * @author libo
 */
public class MergeRegionModel {

	//输入流
	private InputStream stream;
	//工作薄
	private int sheet = 0;
	//行
	private int row = 0;
	//单元格
	private int cell = 0;
	//合并多少行/列
	private int regionNum = 0;
	

	public MergeRegionModel(InputStream stream, int sheet, int row, int cell, int regionNum) {
		super();
		this.stream = stream;
		this.sheet = sheet;
		this.row = row;
		this.cell = cell;
		this.regionNum = regionNum;
	}

	/**
	 * @return the stream
	 */
	public InputStream getStream() {
		return stream;
	}

	/**
	 * @param stream
	 *            the stream to set
	 */
	public void setStream(InputStream stream) {
		this.stream = stream;
	}

	/**
	 * @return the sheet
	 */
	public int getSheet() {
		return sheet;
	}

	/**
	 * @param sheet
	 *            the sheet to set
	 */
	public void setSheet(int sheet) {
		this.sheet = sheet;
	}

	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @param row
	 *            the row to set
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * @return the cell
	 */
	public int getCell() {
		return cell;
	}

	/**
	 * @param cell
	 *            the cell to set
	 */
	public void setCell(int cell) {
		this.cell = cell;
	}

	/**
	 * @return the regionNum
	 */
	public int getRegionNum() {
		return regionNum;
	}

	/**
	 * @param regionNum
	 *            the regionNum to set
	 */
	public void setRegionNum(int regionNum) {
		this.regionNum = regionNum;
	}

}
