package com.mpms.service.impl.auth;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpms.common.exception.ExceptionCode;
import com.mpms.common.exception.GlobalException;
import com.mpms.common.po.auth.Role;
import com.mpms.common.service.auth.RoleService;
import com.mpms.dao.auth.RoleDao;
import com.mpms.utils.date.DateTool;
import com.mpms.utils.string.StringUtil;

/**
 * 角色接口实现
 * 
 * @date 2015年12月1日 下午5:29:32
 * @author luogang
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	private RoleDao roleDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { GlobalException.class, RuntimeException.class })
	public Role addRole(Role role) throws GlobalException {
		if (role == null) {
			LOG.error("角色为空");
			throw new GlobalException(ExceptionCode.AUTH_ERROR_CEDE, "角色为空");
		}
		if (StringUtil.isBlank(role.getName())) {
			LOG.error("角色名为空");
			throw new GlobalException(ExceptionCode.AUTH_ERROR_CEDE, "角色名为空");
		}
		role.setCreateDate(DateTool.getCurrentDate());
		role.setUpdateDate(DateTool.getCurrentDate());
		roleDao.add(role);
		return roleDao.add(role);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { GlobalException.class, RuntimeException.class })
	public Role updateRole(Role role) throws GlobalException {
		if (role == null) {
			LOG.error("角色为空");
			throw new GlobalException(ExceptionCode.AUTH_ERROR_CEDE, "角色为空");
		}
		if (StringUtil.isBlank(role.getName())) {
			LOG.error("角色名为空");
			throw new GlobalException(ExceptionCode.AUTH_ERROR_CEDE, "角色名为空");
		}
		role.setUpdateDate(DateTool.getCurrentDate());
		roleDao.update(role);
		return role;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Role> list(String name, int pageNo, int pageSize) {

		return roleDao.list(name, pageNo, pageSize);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Integer listCount(String name) {
		return roleDao.listCount(name);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Role findById(int id) {
		return this.roleDao.findById(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { GlobalException.class, RuntimeException.class })
	public Role delRoleById(int id) throws GlobalException {
		Role role = this.roleDao.findById(id);
		if (role == null) {
			throw new GlobalException(ExceptionCode.AUTH_ERROR_CEDE, "权限[" + id + "]不存在");
		}
		role.setDeleteFlag(true);
		roleDao.update(role);
		return role;

	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Integer> getResourceByRoleId(int roleId) {
		return this.roleDao.getResourceByRoleId(roleId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Role> getRoleAll() {
		return this.roleDao.findAllRole();
	}

}
