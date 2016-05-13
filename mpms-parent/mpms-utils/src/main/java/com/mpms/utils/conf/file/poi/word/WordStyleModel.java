package com.mpms.utils.conf.file.poi.word;

import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;

/**
 * word 样式实体
 * 
 * @date 2015年11月20日 上午11:56:10
 * @author libo
 */
public class WordStyleModel {

	/**
	 * 是否为粗体
	 */
	private boolean bold;
	/**
	 * 是否为斜体
	 */
	private boolean italic;
	/**
	 * 下线线
	 */
	private UnderlinePatterns underline;
	/**
	 * 颜色
	 */
	private String color;
	/**
	 * 位置
	 */
	private int textPosition;
	/**
	 * 字体尺寸
	 */
	private int fontSize;
	/**
	 * 字体
	 */
	private String fontFamily;

	/**
	 * ctr
	 */
	private CTR ctr;

	public WordStyleModel() {
		super();
	}

	public WordStyleModel(boolean bold, boolean italic, UnderlinePatterns underline, String color,
			int textPosition, int fontSize, String fontFamily, CTR ctr) {
		super();
		this.bold = bold;
		this.italic = italic;
		this.underline = underline;
		this.color = color;
		this.textPosition = textPosition;
		this.fontSize = fontSize;
		this.fontFamily = fontFamily;
		this.ctr = ctr;
	}

	/**
	 * @return the bold
	 */
	public boolean isBold() {
		return bold;
	}

	/**
	 * @param bold
	 *            the bold to set
	 */
	public void setBold(boolean bold) {
		this.bold = bold;
	}

	/**
	 * @return the italic
	 */
	public boolean isItalic() {
		return italic;
	}

	/**
	 * @param italic
	 *            the italic to set
	 */
	public void setItalic(boolean italic) {
		this.italic = italic;
	}

	/**
	 * @return the underline
	 */
	public UnderlinePatterns getUnderline() {
		return underline;
	}

	/**
	 * @param underline
	 *            the underline to set
	 */
	public void setUnderline(UnderlinePatterns underline) {
		this.underline = underline;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return the textPosition
	 */
	public int getTextPosition() {
		return textPosition;
	}

	/**
	 * @param textPosition
	 *            the textPosition to set
	 */
	public void setTextPosition(int textPosition) {
		this.textPosition = textPosition;
	}

	/**
	 * @return the fontSize
	 */
	public int getFontSize() {
		return fontSize;
	}

	/**
	 * @param fontSize
	 *            the fontSize to set
	 */
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	/**
	 * @return the fontFamily
	 */
	public String getFontFamily() {
		return fontFamily;
	}

	/**
	 * @param fontFamily
	 *            the fontFamily to set
	 */
	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}

	/**
	 * @return the ctr
	 */
	public CTR getCtr() {
		return ctr;
	}

	/**
	 * @param ctr
	 *            the ctr to set
	 */
	public void setCtr(CTR ctr) {
		this.ctr = ctr;
	}

}
