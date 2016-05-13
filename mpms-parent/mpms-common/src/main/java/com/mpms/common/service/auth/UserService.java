package com.mpms.common.service.auth;

import java.util.List;
import java.util.Map;

import com.mpms.common.enums.auth.DepartmentType;
import com.mpms.common.exception.GlobalException;
import com.mpms.common.po.auth.User;

/**
 * 权限相关
 *
 * @date 2015年11月3日 上午11:18:18
 * @author luogang
 */
public interface UserService {

	/**
	 * 根据用户名查询用户
	 * 
	 * @param username
	 *            用户名
	 * @return 用户实体
	 * @date 2015年11月3日 上午11:18:06
	 * @author luogang
	 */
	public User findUserByLoginName(String username);

	/**
	 * 更新用户
	 * 
	 * @param user
	 * @return
	 * @throws GlobalException
	 * @exception @auth
	 *                lc
	 * @date 2015年11月25日 下午5:42:37
	 */
	User updateUser(int userId, String userName, String name, String number, boolean isLogin, List<Integer> roleIds)
			throws GlobalException;

	/**
	 * 添加用户
	 * 
	 * @param user
	 * @return
	 * @date 2015年12月1日 下午4:00:11
	 * @author luogang
	 * @throws GlobalException
	 */
	public User addUser(User user) throws GlobalException;

	/**
	 * 通过id查询用户
	 * 
	 * @Title:findById
	 * @Description:通过id查询用户
	 * @param id
	 * @return
	 * @exception @auth
	 *                lc
	 * @date 2015年12月3日 下午5:06:34
	 */
	public User findById(int id);

	/**
	 * 用户分页查询
	 * 
	 * @param institutionId
	 *            机构
	 * @param param
	 *            姓名或工号
	 * @param positionType
	 *            职位类型
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            条数
	 * @return 用户列表
	 * @date 2015年12月8日 下午4:26:01
	 * @author luogang
	 */
	public Map<String, Object> findUsers(List<Integer> institutionId, String param, DepartmentType departmentType,
			int pageNo, int pageSize);

	/**
	 * 重置默认密码
	 * 
	 * @param user
	 *            用户
	 * @return
	 * @throws GlobalException
	 * @date 2015年12月11日 下午4:19:52
	 * @author luogang
	 */
	public User resetPassword(User user) throws GlobalException;

	/**
	 * 通过用户名获取权限资源
	 * 
	 * @param userName
	 * @return
	 * @date 2015年12月29日 上午11:25:40
	 * @author luogang
	 */
	public List<String> findPermissionByUserName(String userName);

	/**
	 * 统计机构下用户数量
	 * 
	 * @param insId
	 *            机构 ID
	 * @return
	 * @date 2015年12月30日 上午11:30:43
	 * @author luogang
	 */
	public int getUserCountByInsId(int insId);

	/**
	 * 删除用户
	 * 
	 * @param id
	 * @return
	 * @throws GlobalException
	 * @date 2016年1月11日 上午11:05:32
	 * @author luogang
	 */
	public User delUser(int id) throws GlobalException;

	/**
	 * 根据条件查询无机构用户
	 * 
	 * @param param
	 *            姓名或工号
	 * @return
	 * @date 2016年1月8日 上午11:00:12
	 * @author luogang
	 */
	public List<User> getUsersByParam(String param);

	/**
	 * 通过角色ID查询用户名列表
	 * 
	 * @param param
	 * @return
	 * @date 2016年1月11日 上午11:05:11
	 * @author luogang
	 */
	public List<String> getUsersByRoleId(int param);

	/**
	 * 查询用户
	 * 
	 * @param userName
	 * @return
	 * @date 2016年1月20日 下午3:26:26
	 * @author luogang
	 */
	User findUserByUserNameAll(String userName, String num);

}
