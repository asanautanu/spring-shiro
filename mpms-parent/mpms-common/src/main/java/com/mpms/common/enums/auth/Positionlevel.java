package com.mpms.common.enums.auth;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 职位等级
 * 
 * @date 2015年12月21日 下午1:00:22
 * @author luogang
 */
public enum Positionlevel {
	ONE(1, "一级（最高级)"), TWO(2, "二级"), THREE(3, "三级"), FOUR(4, "四级"), FIVE(5, "五级"), SIX(6, "六级"), SEVEN(7, "七级"), EIGHT(8, "八级"), NINE(9, "九级"), TEN(10, "十级"), ELEVEN(11, "十一级"), TWELVE(12, "十二级"), THIRTEEN(
			13, "十三级"), FOURTEEN(14, "十四级"), FIFTEEN(15, "十五级"), SIXTEEN(16, "十六级"), SEVENTEEN(17, "十七级"), EIGHTEEN(18, "十八级"), NINETEEN(19, "十九级"), TWENTY(20, "二十级"), ;

	private Integer value;

	private String levelName;

	Positionlevel(Integer value, String levelName) {
		this.value = value;
		this.levelName = levelName;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	/**
	 * 获取当前职位的所有下级职位 包括当前
	 * 
	 * @param level
	 * @return
	 * @date 2016年1月29日 下午1:17:23
	 * @author luogang
	 */
	public static Positionlevel[] getEnumList(Positionlevel level) {
		Positionlevel[] values = Positionlevel.values();
		Positionlevel[] subarray = ArrayUtils.subarray(values, level.ordinal() + 1, values.length);
		return subarray;
	}
}
