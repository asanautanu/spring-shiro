package com.mpms.dao.auth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mpms.common.exception.GlobalException;
import com.mpms.common.po.auth.Resource;
import com.mpms.common.po.auth.Role;
import com.mpms.dao.BaseDao;
import com.mpms.utils.string.StringUtil;

/**
 * 角色DAO
 * 
 * @date 2015年11月10日 下午2:52:20
 * @author luogang
 */
@Repository
public class RoleDao extends BaseDao<Role, Integer> {

	/**
	 * 根据 ID查询 角色
	 * 
	 * @param id
	 *            角色ID
	 * @return 角色实体
	 * @date 2015年11月10日 下午2:52:28
	 * @author luogang
	 */
	public Role getRole(Integer id) {
		String hql = "from Role r where r.id = :id and r.deleteFlag=false";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return super.findByHQLResultSingle(hql, params);
	}

	/**
	 * 添加角色
	 * 
	 * @param role
	 *            角色
	 * @return
	 * @throws GlobalException
	 * @date 2015年12月4日 下午4:43:03
	 * @author luogang
	 */
	public Role add(Role role) throws GlobalException {
		super.save(role);
		return role;
	}

	/**
	 * 修改角色
	 * 
	 * @param id
	 * @param name
	 * @param description
	 * @param resources
	 * @return
	 * @date 2015年12月4日 下午4:56:30
	 * @author luogang
	 */
	public Role updateRole(int id, String name, String description, List<Resource> resources) {

		return null;

	}

	/**
	 * 分页查询
	 * 
	 * @param name
	 *            角色名
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @date 2015年12月14日 下午3:18:11
	 * @author luogang
	 */
	public List<Role> list(String name, int pageNo, int pageSize) {
		String hql = String.format("%s%s%s", " from Role r where 1=1 ",
				StringUtil.isNotBlank(name) ? "and r.name =:name " : "",
				"and r.deleteFlag=false order by r.updateDate desc");
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtil.isNotBlank(name)) {
			params.put("name", name);
		}

		return super.findByHQLResultMore(hql, params, pageNo, pageSize);
	}

	/**
	 * 统计总条数
	 * 
	 * @param name
	 * @return
	 * @date 2015年12月14日 下午3:24:16
	 * @author luogang
	 */
	public Integer listCount(String name) {
		String hql = String.format("%s%s%s", " from Role r where 1=1 ",
				StringUtil.isNotBlank(name) ? "and r.name =:name " : "", "and r.deleteFlag=false");
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtil.isNotBlank(name)) {
			params.put("name", name);
		}
		return super.findByHQLResultCount2(hql, params);
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getResourceByRoleId(int roleId) {
		String hql = "select  id FROM mpms_role_resource lr, mpms_resource l  where lr.resource_id=l.id and role_id ="
				+ roleId + " ORDER BY id";
		return super.getSession().createSQLQuery(hql).list();
	}

	public List<Role> findAllRole() {
		String hql = "from Role r where r.deleteFlag=false";
		return super.findByHQL(hql);
	}
}
