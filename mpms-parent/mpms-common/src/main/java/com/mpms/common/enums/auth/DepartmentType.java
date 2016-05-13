package com.mpms.common.enums.auth;


/**
 * 部门类型
 * 
 * @date 2015年12月10日 下午2:11:43
 * @author luogang
 */
public enum DepartmentType {

	FINANCE("财务"),
	OPERATION("运营"),
	SALESLETTER("信销"),
	CREDITAUDIT("信审"),	
	AFTERLOAN("贷后"),
	OTHER("其它");

	private final String value;

	public String getValue() {
		return value;
	}

	DepartmentType(String value) {
		this.value = value;
	}
}
