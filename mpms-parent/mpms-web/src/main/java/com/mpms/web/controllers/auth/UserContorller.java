package com.mpms.web.controllers.auth;

import java.util.Arrays;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mpms.auth.shiro.UserUtils;
import com.mpms.common.exception.GlobalException;
import com.mpms.web.manager.auth.UserManager;
import com.mpms.web.view.BaseController;
import com.mpms.web.view.MpmsReponse;

/**
 * 用户controller
 * 
 * @date 2015年12月9日 下午4:53:39
 * @author luogang
 */
@RestController
@RequestMapping("/user")
@Scope("prototype")
public class UserContorller extends BaseController {

	@Autowired
	private UserManager userManager;

	/**
	 * 分页查询
	 * 
	 * @param institurionId
	 *            机构
	 * @param param
	 *            工号或姓名
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @date 2015年12月9日 下午4:58:12
	 * @author luogang
	 * @throws GlobalException
	 */
	@RequestMapping(value = "list", method = { RequestMethod.GET })
	@RequiresPermissions("sys:user:list")
	public MpmsReponse list(String values, @RequestParam(defaultValue = "1") int pageNo,
			@RequestParam(defaultValue = "20") int pageSize) throws GlobalException {

		return userManager.findUserByNameOrNumber(null, values, pageNo, pageSize);
	}

	/**
	 * 修改用户
	 * 
	 * @param userId
	 *            用户ID
	 * @param userName
	 *            用户名
	 * @param name
	 *            姓名
	 * @param number
	 *            工号
	 * @param isLogin
	 *            是否登录
	 * @param roleIds
	 *            权限列表
	 * @return
	 * @date 2015年12月11日 下午2:54:31
	 * @author luogang
	 * @throws GlobalException
	 */
	@RequestMapping(value = "update", method = { RequestMethod.POST })
	@RequiresPermissions("sys:user:update")
	public MpmsReponse updateUser(int userId, String userName, String name, String number, boolean isLogin,
			@RequestParam(value = "roleIds[]", required = false) List<Integer> roleIds) throws GlobalException {
		return userManager.updateUser(userId, userName, name, number, isLogin, roleIds);
	}

	/**
	 * 获取用户信息
	 * 
	 * @param userId
	 *            用户ID
	 * @return
	 * @date 2015年12月11日 下午4:28:30
	 * @author luogang
	 */
	@RequestMapping(value = "info", method = { RequestMethod.GET })
	@RequiresPermissions("sys:user:info")
	public MpmsReponse getUserInfo(int userId) {
		return userManager.getUserInfo(userId);
	}

	/**
	 * 创建用户
	 * 
	 * @param userName
	 *            用户名
	 * @param name
	 *            姓名
	 * @param number
	 *            工号
	 * @param isLogin
	 *            是否登录
	 * @param roleIds
	 *            权限ID组
	 * @return
	 * @date 2015年12月11日 下午3:38:59
	 * @author luogang
	 * @throws GlobalException
	 */
	@RequestMapping(value = "adduser", method = { RequestMethod.POST })
	@RequiresPermissions("sys:user:add")
	public MpmsReponse createUser(String userName, String name, String number, boolean isLogin,
			@RequestParam(value = "roleIds[]", required = false) Integer[] roleIds) throws GlobalException {
		List<Integer> roles = null;
		if (roleIds != null) {
			roles = Arrays.asList(roleIds);
		}
		return userManager.createUser(userName, name, number, isLogin, roles);
	}

	/**
	 * 重置密码
	 * 
	 * @param userId
	 *            用户ID
	 * @return
	 * @date 2015年12月11日 下午4:14:41
	 * @author luogang
	 * @throws GlobalException
	 */
	@RequestMapping(value = "resetpassword", method = { RequestMethod.GET })
	@RequiresPermissions("sys:user:resetpassword")
	public MpmsReponse resetPassWord(int userId) throws GlobalException {
		return userManager.resetPassWord(userId);
	}

	/**
	 * 获取登录用户信息
	 * 
	 * @return
	 * @date 2016年2月29日 下午6:36:15
	 * @author luogang
	 * @throws GlobalException
	 */
	@RequestMapping(value = "loginUserInfo", method = { RequestMethod.GET })
	public MpmsReponse getLoginUserInfo() throws GlobalException {
		return userManager.getLoginUserInfo(UserUtils.getLoginUserId());
	}

	/**
	 * 修改登录用户本人密码
	 * 
	 * @param oldPassword
	 * @param newPassword
	 * @param enterPassword
	 * @return
	 * @throws GlobalException
	 * @date 2016年2月29日 下午6:33:11
	 * @author luogang
	 */
	@RequestMapping(value = "updatePassword", method = { RequestMethod.POST })
	public MpmsReponse updatePassword(String oldPassword, String newPassword, String enterPassword)
			throws GlobalException {
		return userManager.updatePassword(oldPassword, newPassword, enterPassword);
	}

	/**
	 * 删除用户
	 * 
	 * @param userId
	 *            用户ID
	 * @return
	 * @date 2015年12月11日 下午4:14:41
	 * @author luogang
	 * @throws GlobalException
	 */
	@RequestMapping(value = "del", method = { RequestMethod.GET })
	@RequiresPermissions("sys:user:del")
	public MpmsReponse delUser(@RequestParam(value = "userId[]") List<Integer> userId) throws GlobalException {
		return userManager.delUser(userId);
	}

}
