package com.mpms.auth.shiro;

import org.apache.shiro.SecurityUtils;

import com.mpms.auth.shiro.UserRealm.ShiroUser;

/**
 * 获得用户登录相关信息
 *
 * @date 2015年11月3日 下午12:24:48
 * @author luogang
 */
public class UserUtils {

	/**
	 * 获取登录用户
	 * 
	 * @return
	 * @date 2015年11月3日 下午3:40:45
	 * @author luogang
	 */
	public static ShiroUser getLoginUser() {
		ShiroUser shiroUser = null;
		try {
			shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		} catch (Exception e) {
			return null;
		}
		return shiroUser;
	}

	/**
	 * 获取用户名
	 * 
	 * @return 用户名
	 * @date 2015年11月3日 下午3:40:27
	 * @author luogang
	 */
	public static String getLoginUserName() {
		return getLoginUser().getUserName();
	}

	/**
	 * 获取 登陆用户ID
	 * 
	 * @return
	 * @date 2016年1月20日 下午12:18:43
	 * @author libo
	 */
	public static Integer getLoginUserId() {
		ShiroUser loginedUser = getLoginUser();
		if (null == loginedUser) {
			return null;
		}
		return loginedUser.getId();
	}
}
