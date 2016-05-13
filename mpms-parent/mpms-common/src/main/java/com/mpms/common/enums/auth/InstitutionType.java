package com.mpms.common.enums.auth;

/**
 * 机构类型
 * 
 * @date 2015年11月9日 下午5:28:27
 * @author luogang
 */
public enum InstitutionType {

	CITY("城市"),
	STORE("门店"),
	GROUP("组"),
	OTHER("其它");

	private final String value;

	public String getValue() {
		return value;
	}

	InstitutionType(String value) {
		this.value = value;
	}
}
