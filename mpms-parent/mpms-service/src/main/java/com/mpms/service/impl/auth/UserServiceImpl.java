package com.mpms.service.impl.auth;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mpms.common.constant.Constant.Auths;
import com.mpms.common.enums.auth.DepartmentType;
import com.mpms.common.exception.ExceptionCode;
import com.mpms.common.exception.GlobalException;
import com.mpms.common.po.auth.Resource;
import com.mpms.common.po.auth.Role;
import com.mpms.common.po.auth.User;
import com.mpms.common.service.auth.UserService;
import com.mpms.dao.auth.UserDao;
import com.mpms.utils.date.DateTool;
import com.mpms.utils.encrypt.EncoderHandler;
import com.mpms.utils.string.StringUtil;

/**
 * 用户接口实现
 * 
 * @date 2015年11月3日 上午11:15:12
 * @author luogang
 */
@Service
public class UserServiceImpl implements UserService {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserDao userDao;

	/**
	 * 根据用户名查询用户
	 * 
	 * @param userName
	 *            用户名
	 * @return 用户
	 * @date 2015年12月1日 下午3:02:11
	 * @author luogang
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public User findUserByLoginName(String userName) {
		return userDao.findUserByUserName(userName);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public User findUserByUserNameAll(String userName, String num) {
		return userDao.findUserByUserNameAll(userName, num);
	}

	/**
	 * 用户分页查询
	 * 
	 * @param institutionId
	 *            机构
	 * @param name
	 *            姓名
	 * @param num
	 *            工号
	 * @param DepartmentType
	 *            部门类型
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            条数
	 * @return 用户列表
	 * @date 2015年12月8日 下午4:26:01
	 * @author luogang
	 */

	/**
	 * 更新用户登录信息
	 * 
	 * @param user
	 *            用户
	 * @return 用户
	 * @throws GlobalException
	 * @date 2015年12月1日 下午3:01:58
	 * @author luogang
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { GlobalException.class, RuntimeException.class })
	public User updateUser(int userId, String userName, String name, String number, boolean isLogin,
			List<Integer> roleIds) throws GlobalException {

		User user = this.findById(userId);
		if (user == null) {
			throw new GlobalException(ExceptionCode.AUTH_ERROR_CEDE, "用户不存在 ");
		}
		if (number == null) {
			throw new GlobalException(ExceptionCode.AUTH_ERROR_CEDE, "工号不能为空");
		}
		if (this.findUserByUserNameAll(null, number) != null && !StringUtil.equals(number, user.getNumber())) {
			throw new GlobalException(ExceptionCode.AUTH_ERROR_CEDE, "工号重复");
		}
		if (StringUtil.isBlank(user.getUserName())) {
			user.setUserName(null);
		}
		if (StringUtil.isBlank(name)) {
			LOG.error("姓名不能为空");
			throw new GlobalException(ExceptionCode.AUTH_ERROR_CEDE, "姓名不能为空");
		}
		if (isLogin) {
			if (StringUtil.isBlank(userName)) {
				LOG.error("用户名不能为空");
				throw new GlobalException(ExceptionCode.AUTH_ERROR_CEDE, "用户名不能为空");
			}
			if (this.userDao.findUserByUserName(userName) != null && !userName.equals(user.getUserName())) {
				throw new GlobalException(ExceptionCode.AUTH_ERROR_CEDE, "用户名重复");
			}
		}
		user.setUserName(userName);
		user.setName(name);
		user.setNumber(number);
		user.setLogin(isLogin);
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
		user.setUpdateTime(DateTool.getCurrentDate());
		this.userDao.update(user);
		return user;
	}

	/**
	 * 生成默认密码或修改密码
	 * 
	 * @param user
	 * @date 2015年12月11日 下午4:05:17
	 * @author luogang
	 */
	private void entryptPassword(User user) {
		// 生成随机盐值
		byte[] salt = EncoderHandler.generateSalt(8);
		String saltString = EncoderHandler.encodeHex(salt);
		user.setSalt(saltString);

		String planPas = Auths.DEFAULT_PASSWORD;
		// 前台有传参数就不使用默认密码
		if (StringUtil.isNotBlank(user.getPlanPassWord())) {
			planPas = user.getPlanPassWord();
		}
		byte[] hashPassword = EncoderHandler.sha1(planPas.getBytes(), salt, 1024);
		String password = EncoderHandler.encodeHex(hashPassword);
		user.setPassword(password);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public User findById(int id) {
		return userDao.findById(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { GlobalException.class, RuntimeException.class })
	public User resetPassword(User user) throws GlobalException {
		// 设置默认密码
		entryptPassword(user);
		this.userDao.updateUser(user);
		return user;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getUserCountByInsId(int insId) {
		List<User> users = this.userDao.getUserByins(insId);
		int count = CollectionUtils.isEmpty(users) ? 0 : users.size();
		return count;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<User> getUsersByParam(String param) {
		List<User> users = userDao.getUsersByParam(param);
		return users;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<String> getUsersByRoleId(int param) {
		return this.userDao.getUsersByRoleId(param);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<String> findPermissionByUserName(String userName) {
		List<String> info = Lists.newArrayList();
		User user = userDao.findUserByUserName(userName);
		for (Role userRole : user.getRoles()) {
			for (Resource res : userRole.getResource()) {
				if (StringUtils.isNotBlank(res.getPermission()))
					info.add(res.getPermission());
			}
		}
		return info;
	}

	@Override
	public User addUser(User user) throws GlobalException {

		if (user == null) {
			LOG.error("用户信息为空");
			throw new GlobalException(ExceptionCode.AUTH_ERROR_CEDE, "用户信息为空");
		}
		if (StringUtil.isBlank(user.getName())) {
			LOG.error("姓名不能为空");
			throw new GlobalException(ExceptionCode.AUTH_ERROR_CEDE, "姓名不能为空");
		}
		if (user.getNumber() == null) {
			throw new GlobalException(ExceptionCode.AUTH_ERROR_CEDE, "工号不能为空");
		}
		if (this.findUserByUserNameAll(null, user.getNumber()) != null) {
			throw new GlobalException(ExceptionCode.AUTH_ERROR_CEDE, "工号重复");
		}
		if (user.getLogin()) {
			if (StringUtil.isBlank(user.getUserName())) {
				LOG.error("用户名不能为空");
				throw new GlobalException(ExceptionCode.AUTH_ERROR_CEDE, "用户名不能为空");
			}
			if (this.findUserByLoginName(user.getUserName()) != null) {
				throw new GlobalException(ExceptionCode.AUTH_ERROR_CEDE, "用户名重复");
			}
		} else {
			user.setUserName(null);
		}
		user.setState(true);
		user.setDeleteFlag(false);
		user.setCreateTime(DateTool.getCurrentDate());
		user.setUpdateTime(DateTool.getCurrentDate());
		entryptPassword(user);
		userDao.add(user);
		return user;
	}

	@Override
	public Map<String, Object> findUsers(List<Integer> institutionId, String param, DepartmentType departmentType,
			int pageNo, int pageSize) {
		Map<String, Object> users = userDao.getUserByTypeOrinsidPage(param, null, pageNo, pageSize);
		return users;
	}

	@Override
	public User delUser(int id) throws GlobalException {

		User user = userDao.findById(id);
		if (user == null) {
			throw new GlobalException(ExceptionCode.AUTH_ERROR_CEDE, "用户[" + id + "]不存在 ");
		}
		if ("admin".equals(user.getUserName())) {
			throw new GlobalException(ExceptionCode.AUTH_ERROR_CEDE, "ADMIN用户不能删除 ");
		}
		user.setDeleteFlag(true);
		this.userDao.update(user);
		return user;
	}

}
