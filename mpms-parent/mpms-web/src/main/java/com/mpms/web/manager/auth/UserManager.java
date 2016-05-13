package com.mpms.web.manager.auth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mpms.auth.shiro.UserRealm.ShiroUser;
import com.mpms.auth.shiro.UserUtils;
import com.mpms.common.constant.Constant.Page;
import com.mpms.common.enums.auth.Positionlevel;
import com.mpms.common.exception.ExceptionCode;
import com.mpms.common.exception.GlobalException;
import com.mpms.common.po.auth.Role;
import com.mpms.common.po.auth.User;
import com.mpms.common.service.auth.UserService;
import com.mpms.common.vo.auth.UserVo;
import com.mpms.utils.encrypt.EncoderHandler;
import com.mpms.utils.json.JSONUtil;
import com.mpms.utils.string.StringUtil;
import com.mpms.web.view.BaseManager;
import com.mpms.web.view.MpmsReponse;

/**
 * 用户管理Manager
 * 
 * @date 2015年12月9日 下午4:59:09
 * @author seiya
 */
@Component
@Scope("prototype")
public class UserManager extends BaseManager {

	@Autowired
	private UserService userService;

	/**
	 * 用户分页查询
	 * 
	 * @param insId
	 *            机构ID
	 * @param param
	 *            用户（工号，姓名）
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @date 2015年12月11日 下午2:37:26
	 * @author seiya
	 * @throws GlobalException
	 */
	@SuppressWarnings("unchecked")
	public MpmsReponse findUserByNameOrNumber(List<Integer> insId, String param, int pageNo, int pageSize)
			throws GlobalException {

		Map<String, Object> users = userService.findUsers(insId, param, null, pageNo, pageSize);
		total = users.get(Page.RESULT_COUNT) == null ? 0 : (int) users.get(Page.RESULT_COUNT);
		if (total > 0) {
			List<User> usersList = users.get(Page.RESULT_DATA) == null ? null
					: (List<User>) users.get(Page.RESULT_DATA);
			List<UserVo> userVos = null;
			userVos = Lists.newArrayList();
			for (User user : usersList) {
				UserVo userVo = new UserVo(user);
				userVos.add(userVo);
			}
			list = userVos;
		}
		return MpmsReponse.addListResponse("查询成功", total, pageNo, pageSize, list);
	}

	/**
	 * 获取用户信息
	 * 
	 * @param userId
	 *            用户ID
	 * @return
	 * @date 2015年12月11日 下午4:28:30
	 * @author seiya
	 */
	public MpmsReponse getUserInfo(int userId) {
		User user = userService.findById(userId);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("user", user);
		return MpmsReponse.addSuccessResponse("查询成功", data);
	}

	/**
	 * 获取用户信息
	 * 
	 * @param userId
	 * @return
	 * @throws GlobalException
	 * @date 2016年2月29日 下午6:41:48
	 * @author seiya
	 */
	public MpmsReponse getLoginUserInfo(int userId) throws GlobalException {
		User user = userService.findById(userId);
		Map<String, Object> resultMap = Maps.newHashMap();

		if (user != null) {
			resultMap.put("userName", user.getUserName());
			resultMap.put("name", user.getName());
			resultMap.put("password", user.getPassword());
			resultMap.put("userId", user.getId());

		}
		Map<String, Object> data = Maps.newHashMap();
		data.put("list", resultMap);
		return MpmsReponse.addSuccessResponse("查询成功", data);
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
	 * @author seiya
	 * @throws GlobalException
	 */
	public MpmsReponse updateUser(int userId, String userName, String name, String number, boolean isLogin,
			List<Integer> roleIds) throws GlobalException {
		User user = userService.updateUser(userId, userName, name, number, isLogin, roleIds);
		Map<String, Object> data = Maps.newHashMap();
		data.put("user", user);
		return MpmsReponse.addSuccessResponse("修改成功 ", data);
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
	 * @author seiya
	 * @throws GlobalException
	 */
	public MpmsReponse createUser(String userName, String name, String number, boolean isLogin, List<Integer> roleIds)
			throws GlobalException {
		User user = new User();
		user.setUserName(userName);
		user.setName(name);
		user.setNumber(number);
		user.setLogin(isLogin);
		// 创建用户
		user.setUserId(UserUtils.getLoginUser().getId());
		user.setCreaterName(UserUtils.getLoginUser().getName());
		// 组装权限
		Set<Role> roles = null;
		if (roleIds != null) {
			roles = Sets.newHashSet();
			for (Integer id : roleIds) {
				Role role = new Role();
				role.setId(id);
				roles.add(role);
			}
		}
		user.setRoles(roles);
		try {
			userService.addUser(user);
		} catch (DataIntegrityViolationException e) {
			throw new GlobalException(ExceptionCode.AUTH_ERROR_CEDE, "添加的权限不存在");
		}

		Map<String, Object> data = Maps.newHashMap();
		data.put("user", user);
		return MpmsReponse.addSuccessResponse("创建成功", data);
	}

	/**
	 * 重置密码
	 * 
	 * @param userId
	 *            用户ID
	 * @return
	 * @date 2015年12月11日 下午4:14:41
	 * @author seiya
	 * @throws GlobalException
	 */
	public MpmsReponse resetPassWord(int userId) throws GlobalException {
		User user = userService.findById(userId);
		if (user == null) {
			throw new GlobalException(ExceptionCode.AUTH_ERROR_CEDE, "用户不存在 ");
		}
		userService.resetPassword(user);
		Map<String, Object> data = Maps.newHashMap();
		data.put("user", user);
		return MpmsReponse.addSuccessResponse("重置成功", data);
	}

	public MpmsReponse delUser(List<Integer> userId) throws GlobalException {
		if (CollectionUtils.isEmpty(userId)) {
			throw new GlobalException(ExceptionCode.AUTH_ERROR_CEDE, "用户ID不能为空");
		}
		List<User> users = Lists.newArrayList();
		for (Integer id : userId) {
			users.add(userService.delUser(id));
		}
		Map<String, Object> data = Maps.newHashMap();
		data.put("user", users);
		return MpmsReponse.addSuccessResponse("删除成功", data);
	}

	/**
	 * 修改当前登录用户密码
	 * 
	 * @return
	 * @throws GlobalException
	 * @date 2016年2月23日 上午11:11:12
	 * @author seiya
	 */
	public MpmsReponse updatePassword(String oldPassword, String newPassword, String enterPassword)
			throws GlobalException {
		ShiroUser loginUser = UserUtils.getLoginUser();
		if (loginUser == null) {
			throw new GlobalException(ExceptionCode.AUTH_ERROR_CEDE, "用户未登录");
		}

		if (!StringUtil.equals(newPassword, enterPassword)) {
			throw new GlobalException(ExceptionCode.AUTH_ERROR_CEDE, "两次密码输入不一致");
		}
		User user = userService.findById(loginUser.getId());
		byte[] hashPassword = EncoderHandler.sha1(oldPassword.getBytes(), EncoderHandler.decodeHex(user.getSalt()),
				1024);
		String passwordDB = EncoderHandler.encodeHex(hashPassword);
		if (!StringUtil.equals(user.getPassword(), passwordDB)) {
			throw new GlobalException(ExceptionCode.AUTH_ERROR_CEDE, "旧密码输入不正确");
		}
		user.setPlanPassWord(newPassword);
		userService.resetPassword(user);
		return MpmsReponse.addSuccessResponse("修改成功", null);
	}

	public static void main(String[] args) throws Exception {
		List<Positionlevel> result = Lists.newArrayList();
		Positionlevel[] enumList = Positionlevel.getEnumList(Positionlevel.TWO);
		CollectionUtils.addAll(result, enumList);
		System.out.println(JSONUtil.beanToJson(result));

	}

}
