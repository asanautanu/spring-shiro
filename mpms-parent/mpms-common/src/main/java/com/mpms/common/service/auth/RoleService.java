package com.mpms.common.service.auth;

import java.util.List;

import com.mpms.common.exception.GlobalException;
import com.mpms.common.po.auth.Role;

/**
 * 角色 接口
 * 
 * @date 2015年12月1日 下午4:09:46
 * @author luogang
 */
public interface RoleService {

	/**
	 * 添加角色
	 * 
	 * @param role
	 * @return
	 * @date 2015年12月1日 下午4:44:39
	 * @author luogang
	 * @throws GlobalException
	 */
	public Role addRole(Role role) throws GlobalException;

	/**
	 * 按ID查询
	 * 
	 * @param id
	 * @return
	 * @date 2015年12月14日 下午3:52:09
	 * @author luogang
	 */
	public Role findById(int id);

	/**
	 * 更新角色
	 * 
	 * @param role
	 * @return
	 * @date 2015年12月7日 上午11:46:10
	 * @author luogang
	 * @throws GlobalException
	 */
	Role updateRole(Role role) throws GlobalException;

	/**
	 * 
	 * @param name
	 * @return
	 * @date 2015年12月1日 下午4:31:33
	 * @author luogang
	 */
	public List<Role> list(String name, int pageNo, int pageSize);

	/**
	 * 统计总条数
	 * 
	 * @param name
	 * @return
	 * @date 2015年12月14日 下午3:25:36
	 * @author luogang
	 */
	public Integer listCount(String name);

	/**
	 * 删除角色
	 * 
	 * @param id
	 * @date 2016年1月5日 下午1:51:56
	 * @author luogang
	 * @throws GlobalException
	 */
	public Role delRoleById(int id) throws GlobalException;

	/**
	 * 通过角色查询资源
	 * 
	 * @param roleId
	 * @return
	 * @date 2016年1月5日 下午9:14:23
	 * @author luogang
	 */
	public List<Integer> getResourceByRoleId(int roleId);

	/**
	 * 获取所有权限
	 * 
	 * @return
	 * @date 2016年1月5日 下午9:16:06
	 * @author luogang
	 */
	public List<Role> getRoleAll();
}
