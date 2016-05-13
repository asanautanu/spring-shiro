package com.mpms.common.enums.auth;

public enum UserPositionStutas {
	
	DELETE("删除"),
	NORMAL("正常");

	private String value;

	public String getValue() {
		return value;
	}

	UserPositionStutas(String value) {
		this.value = value;
	}
}
