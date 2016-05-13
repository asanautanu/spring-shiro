package com.mpms.common.enums.auth;

/**
 * 资源类型
 * 
 * @date 2015年11月12日 下午1:35:40
 * @author luogang
 */
public enum ResourceType {
	/**
	 * 菜单
	 */
	MENU("菜单"),
	/**
	 * 按钮
	 */
	BUTTON("按钮");

	private final String value;

	public String getValue() {
		return value;
	}

	ResourceType(String value) {
		this.value = value;
	}
}
