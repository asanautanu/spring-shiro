package com.mpms.dao.auth;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.mpms.common.constant.Constant.Auths;
import com.mpms.common.constant.Constant.Page;
import com.mpms.common.enums.auth.DepartmentType;
import com.mpms.common.po.auth.User;
import com.mpms.dao.BaseDao;
import com.mpms.utils.date.DateTool;
import com.mpms.utils.string.StringUtil;

/**
 * 用户管理DAO
 * 
 * @date 2015年11月10日 下午2:51:53
 * @author luogang
 */
@Repository
public class UserDao extends BaseDao<User, Integer> {

	/**
	 * 根据用户名查询用户
	 * 
	 * @param userName
	 *            用户名
	 * @return 用户实体
	 * @date 2015年11月10日 下午2:52:00
	 * @author luogang
	 */
	public User findUserByUserName(String userName) {
		String hql = "from User where userName = :name and deleteFlag=false ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", userName);
		return this.findByHQLResultSingle(hql, params);
	}

	/**
	 * 用户更新
	 * 
	 * @param user
	 * @return
	 * @date 2015年12月3日 下午3:14:36
	 * @author luogang
	 */
	public User updateUser(User user) {
		this.update(user);
		return user;
	}

	/**
	 * 添加用户
	 * 
	 * @param user
	 * @return
	 * @date 2015年12月3日 下午2:55:13
	 * @author luogang
	 */
	public User add(User user) {

		super.save(user);
		return user;
	}

	/**
	 * 按机构查询用户
	 * 
	 * @param insId
	 * @return
	 * @date 2016年1月20日 下午12:28:30
	 * @author luogang
	 */
	public List<User> getUserByins(int insId) {
		String sql = "SELECT DISTINCT lu.* from mpms_user lu left join mpms_user_ins_position lup on lu.id=lup.user_id WHERE  lup.ins_id =:insId and lup.stutas='NORMAL' and lu.delete_Flag=false ";
		Map<String, Object> params = Maps.newHashMap();
		params.put("insId", insId);
		sql += " and lu.id not in(SELECT id from mpms_user where user_name=:addd)";
		params.put("addd", Auths.INIT_USER_NAME);
		return super.findBySQLResult(sql, params, User.class);
	}

	/**
	 * 查询没有分配机构的用户
	 * 
	 * @param param
	 * @return
	 * @date 2016年2月22日 下午12:45:06
	 * @author luogang
	 */
	public List<User> getUsersByParam(String param) {
		String sql = String
				.format("%s%s",
						"SELECT lu.* FROM mpms_user AS lu  WHERE lu.delete_flag =false and  lu.id not in(SELECT lup.user_id from mpms_user_ins_position lup WHERE lup.stutas='NORMAL')",
						StringUtil.isNotBlank(param) ? " and (lu.number =:param OR lu.`name` =:param) "
								: "");

		Map<String, Object> params = Maps.newHashMap();
		if (StringUtil.isNotBlank(param)) {
			params.put("param", param);
		}
		sql += " and lu.id not in(SELECT id from mpms_user where user_name=:addd)";
		params.put("addd", Auths.INIT_USER_NAME);
		return super.findBySQLResult(sql, params, User.class);
	}

	public List<String> getUsersByRoleId(int param) {
		String sql = "SELECT lu.user_name as userName  FROM mpms_user lu LEFT JOIN mpms_user_role lur on lur.user_id =lu.id where lu.delete_flag=false and lur.role_id="
				+ param
				+ "  and lu.id not in(SELECT id from mpms_user where user_name='"
				+ Auths.INIT_USER_NAME + "')";
		SQLQuery query = super.getSession().createSQLQuery(sql);
		@SuppressWarnings("unchecked")
		List<String> list = query.list();
		return list;
	}

	/**
	 * 按用户名查找 包括删除
	 * 
	 * @param userName
	 * @return
	 * @date 2016年1月20日 下午3:29:27
	 * @author luogang
	 */
	public User findUserByUserNameAll(String userName, String number) {
		String hql = "from User where deleteFlag=false ";
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtil.isNotBlank(userName)) {
			hql += " and userName = :userName ";
			params.put("userName", userName);
		}

		if (StringUtil.isNotBlank(number)) {
			hql += " and number =:number ";
			params.put("number", number);
		}
		return this.findByHQLResultSingle(hql, params);
	}

	/**
	 * 查询运营人员最后操作时间
	 * 
	 * @param institutionId
	 * @param param
	 * @param startDate
	 * @param endDate
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @date 2016年3月4日 下午6:24:39
	 * @author luogang
	 */
	public Map<String, Object> findOperationUserByTime(
			List<Integer> institutionId, String numOrName, Date startDate,
			Date endDate, DepartmentType departmentType, int pageNo,
			int pageSize) {
		String sql = String
				.format("%s%s%s",
						"select DISTINCT lu.* from mpms_user AS lu LEFT JOIN mpms_user_ins_position AS lup ON lu.id = lup.user_id LEFT JOIN   mpms_institution AS li on li.id=lup.ins_id  WHERE lu.delete_flag=false ",
						StringUtil.isNotBlank(numOrName) ? " and (lu.number =:numOrName OR lu.`name` =:numOrName) "
								: "",
						departmentType != null ? " and li.department_type =:departmentType "
								: "");

		Map<String, Object> results = new HashMap<String, Object>();
		Map<String, Object> params = Maps.newHashMap();
		if (departmentType != null) {
			sql += " and lup.stutas='NORMAL' ";
		}
		if (StringUtil.isNotBlank(numOrName)) {
			params.put("numOrName", numOrName);
		}
		if (departmentType != null) {
			params.put("departmentType", departmentType.name());
		}
		if (startDate != null) {
			// startDate != null ? " AND lu.audit_time >= :startDate" : "",
			// endDate != null ? " AND lu.audit_time <= :endDate " : ""
			sql += " AND lu.audit_time >= '"
					+ DateTool.dateToString(
							DateTool.getStartDateTime(startDate),
							DateTool.DATE_PATTERN_DAY) + "'";
		}
		if (endDate != null) {
			// params.put("endDate", DateTool.dateToString(endDate,
			// DateTool.DATE_PATTERN_DAY));
			sql += " AND lu.audit_time <= '"
					+ DateTool.dateToString(DateTool.getEndDateTime(endDate),
							DateTool.DATE_PATTERN_DAY) + "'";
		}
		String sqlCont = "select COUNT(DISTINCT lu.id) from "
				+ StringUtils.substringAfterLast(sql, "from");
		sql += " and lu.id not in(SELECT dd.id from mpms_user dd where dd.user_name=:addd)";
		params.put("addd", Auths.INIT_USER_NAME);
		int count = super
				.findBySQLResultCount(
						sqlCont
								+ " and lu.id not in(SELECT dd.id from mpms_user dd where dd.user_name=:addd)",
						params);
		results.put(Page.RESULT_COUNT, count);
		List<User> objs = this.findBySQLResultMore(sql, params, User.class,
				pageNo, pageSize);
		results.put(Page.RESULT_DATA, objs);
		return results;
	}

	public User getSystemUser(String initUserName) {
		String hql = "from User where userName = :userName";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", initUserName);
		return this.findByHQLResultSingle(hql, params);
	}

	public List<User> findUserByids(List<Integer> userIds) {
		String hql = "from User where id in :userIds";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userIds", userIds);
		return this.findByHQLResultMore(hql, params);
	}

	public Map<String, Object> getUserByTypeOrinsidPage(String numOrName,
			List<Integer> insIds, int pageNo, int pageSize) {
		String sql = String
				.format("%s%s%s",
						"select DISTINCT lu.* from mpms_user AS lu WHERE 1=1 and lu.delete_flag=false ",
						StringUtil.isNotBlank(numOrName) ? " and (lu.number =:numOrName OR lu.`name` =:numOrName) "
								: "",
						CollectionUtils.isNotEmpty(insIds) ? " and li.id in(:insIds) "
								: "");

		Map<String, Object> results = new HashMap<String, Object>();
		Map<String, Object> params = Maps.newHashMap();
		if (StringUtil.isNotBlank(numOrName)) {
			params.put("numOrName", numOrName);
		}
		if (CollectionUtils.isNotEmpty(insIds)) {
			params.put("insIds", insIds);
		}
		String sqlCont = "select COUNT(DISTINCT lu.id) from "
				+ StringUtils.substringAfterLast(sql, "from");
		int count = super.findBySQLResultCount(sqlCont, params);
		results.put(Page.RESULT_COUNT, count);
		List<User> objs = this.findBySQLResultMore(sql, params, User.class,
				pageNo, pageSize);
		results.put(Page.RESULT_DATA, objs);
		return results;
	}
}
