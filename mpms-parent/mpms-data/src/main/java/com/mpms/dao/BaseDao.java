package com.mpms.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import com.mpms.common.constant.Constant.Page;

/**
 * MYSQL BaseDao
 * 
 * @date 2015年11月25日 上午10:09:55
 * @author maliang
 */
public class BaseDao<T extends Serializable, S extends Serializable> {

	@Autowired
	private SessionFactory sessionFactory;

	private Class<T> classType;

	@SuppressWarnings("unchecked")
	public BaseDao() {
		Type superClass = this.getClass().getGenericSuperclass();
		// 获取参数泛型的类
		if (superClass instanceof ParameterizedType) {
			this.classType = (Class<T>) ((ParameterizedType) superClass)
					.getActualTypeArguments()[0];
		}
	}

	/**
	 * 获取session
	 * 
	 * @return session
	 * @date 2015年11月25日 上午10:12:45
	 * @author maliang
	 */
	public Session getSession() {
		return this.sessionFactory.getCurrentSession();

	}

	/**
	 * 保存实体，返回对应的ID
	 * <p>
	 * 保存或者更新该实体
	 * </p>
	 * <p>
	 * cascade="save-update"
	 * </p>
	 * 
	 * @param t
	 *            保存实体
	 * @return
	 * @date 2015年11月25日 上午10:15:24
	 * @author maliang
	 */
	@SuppressWarnings("unchecked")
	public S save(T t) {
		return (S) getSession().save(t);
	}

	/**
	 * 保存实体
	 * <p>
	 * 保存实体
	 * </p>
	 * <p>
	 * cascade="persist"
	 * </p>
	 * 
	 * @param t
	 * @date 2015年11月25日 上午10:50:13
	 * @author maliang
	 */
	public void persist(T t) {
		getSession().persist(t);
	}

	/**
	 * 更新实体
	 * <p>
	 * 保存或者更新
	 * </p>
	 * <p>
	 * cascade="save-update"
	 * </p>
	 * 
	 * @param t
	 *            更新实体
	 * @date 2015年11月25日 上午10:16:38
	 * @author maliang
	 */
	public void update(T t) {
		getSession().update(t);
	}

	/**
	 * 更新实体
	 * <p>
	 * 更新实体
	 * </p>
	 * <p>
	 * cascade="merge"
	 * <p>
	 * 
	 * @param t
	 *            更新实体
	 * @date 2015年11月25日 上午10:17:35
	 * @author maliang
	 */
	public void merge(T t) {
		getSession().merge(t);
	}

	/**
	 * 删除实体
	 * <p>
	 * cascade="delete"
	 * <p>
	 * 
	 * @param t
	 *            删除实体
	 * @date 2015年11月25日 上午10:19:12
	 * @author maliang
	 */
	public void delete(T t) {
		getSession().delete(t);
	}

	/**
	 * 根据ID删除
	 * 
	 * @param s
	 *            根据ID删除
	 * @date 2015年11月25日 上午10:20:46
	 * @author maliang
	 * @return
	 */
	public int deleteById(S s) {
		/*
		 * T t = this.findById(s); if (t != null) { this.delete(t); }
		 */
		String hql = " delete " + classType.getName() + " tmp where tmp.id=:id";
		return getSession().createQuery(hql).setParameter("id", s)
				.executeUpdate();
	}

	/**
	 * 根据ID获取
	 * 
	 * @param s
	 *            根据ID查找
	 * @return
	 * @date 2015年11月25日 上午10:22:10
	 * @author maliang
	 */
	public T findById(S s) {
		return getSession().get(classType, s);
	}

	/**
	 * 根据HQL 语句返回多条记录
	 * <p>
	 * 没有查询条件
	 * </p>
	 * <p>
	 * 不具有分页条件设置
	 * </p>
	 * 
	 * @param hql
	 *            根据HQL语句查找
	 * @return
	 * @date 2015年11月25日 上午10:24:40
	 * @author maliang
	 */
	public List<T> findByHQL(String hql) {
		if (StringUtil.isBlank(hql)) {
			return null;
		}
		return this.findByHQLResultMore(hql, null);
	}

	/**
	 * 获取实体的所有信息
	 * 
	 * @return
	 * @date 2015年12月7日 下午2:51:45
	 * @author maliang
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return getSession().createQuery("from " + classType.getName()).list();
	}

	/**
	 * 根据条件查询返回结果集
	 * <p>
	 * 不具有分页条件设置
	 * </p>
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            过滤条件
	 * @return
	 * @date 2015年11月25日 上午10:29:36
	 * @author maliang
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> findByHQLResultMore(String hql, Map<String, Object> params) {
		if (StringUtil.isBlank(hql)) {
			return null;
		}
		Query query = getSession().createQuery(hql);
		if (MapUtils.isNotEmpty(params)) {
			for (Entry<String, Object> entry : params.entrySet()) {
				if (StringUtils.isNotBlank(entry.getKey())) {
					if (entry.getValue().getClass().isArray()) {
						Object[] objects = (Object[]) entry.getValue();
						query.setParameterList(entry.getKey().toString(),
								objects);
					} else if (List.class.isAssignableFrom(entry.getValue()
							.getClass())) {
						List objects = (List) entry.getValue();
						query.setParameterList(entry.getKey().toString(),
								objects);
					} else {
						query.setParameter(entry.getKey().toString(),
								entry.getValue());
					}
				}
			}
		}
		return query.list();
	}

	/**
	 * 根据条件查询返回结果集
	 * 
	 * @param hql
	 *            查找语句
	 * @param params
	 *            过滤条件
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页长度
	 * @return 返回查找过滤以后，根据分页长度返回结果集
	 * @date 2015年11月25日 上午10:29:36
	 * @author maliang
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByHQLResultMore(String hql, Map<String, Object> params,
			int pageNo, int pageSize) {
		if (StringUtil.isBlank(hql)) {
			return null;
		}
		Query query = getSession().createQuery(hql);
		if (MapUtils.isNotEmpty(params)) {
			for (Entry<String, Object> entry : params.entrySet()) {
				if (StringUtils.isNotBlank(entry.getKey())) {
					if (entry.getValue().getClass().isArray()) {
						Object[] objects = (Object[]) entry.getValue();
						query.setParameterList(entry.getKey().toString(),
								objects);
					} else if (List.class.isAssignableFrom(entry.getValue()
							.getClass())) {
						List objects = (List) entry.getValue();
						query.setParameterList(entry.getKey().toString(),
								objects);
					} else {
						query.setParameter(entry.getKey().toString(),
								entry.getValue());
					}
				}
			}
		}
		query.setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize);
		return query.list();
	}

	/**
	 * 根据条件查询返回结果集
	 * <p>
	 * 对总条数封装 {@link Page#RESULT_COUNT}
	 * </p>
	 * <p>
	 * 对返回集合封装 {@link Page#RESULT_DATA}
	 * </p>
	 * 
	 * @param hql
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @date 2015年12月10日 上午11:32:14
	 * @author maliang
	 */
	public Map<String, Object> findByHQLResultMap(String hql,
			Map<String, Object> params, int pageNo, int pageSize) {
		Map<String, Object> results = new HashMap<String, Object>();

		int count = this.findByHQLResultCount2(hql, params);
		results.put(Page.RESULT_COUNT, count);

		List<T> objs = this.findByHQLResultMore(hql, params, pageNo, pageSize);
		results.put(Page.RESULT_DATA, objs);

		return results;
	}

	/**
	 * 按指定条数查询数据
	 * 
	 * @Title: findListByHQL
	 * @Description:按指定条数查询数据
	 * @param hql
	 *            查询语句
	 * @param params
	 *            查询条件
	 * @param from
	 *            起始位置
	 * @param size
	 *            查询条数
	 * @return 结果列表
	 * @exception @auth lc
	 * @date 2015年12月1日 下午5:56:14
	 */
	public List<T> findListByHQL(String hql, Map<String, Object> params,
			int from, int size) {
		if (StringUtil.isBlank(hql)) {
			return null;
		}
		Query query = getSession().createQuery(hql);
		if (MapUtils.isNotEmpty(params)) {
			for (Entry<String, Object> entry : params.entrySet()) {
				if (StringUtils.isNotBlank(entry.getKey())) {
					query.setParameter(entry.getKey().toString(),
							entry.getValue());
				}
			}
		}
		query.setFirstResult(from).setMaxResults(size);
		return query.list();
	}

	/**
	 * 获取单个实体
	 * 
	 * @param hql
	 *            查找条件
	 * @param params
	 *            过滤值
	 * @return 返回该查找过滤以后的单个实体
	 * @date 2015年11月25日 上午10:37:10
	 * @author maliang
	 */
	@SuppressWarnings("unchecked")
	public T findByHQLResultSingle(String hql, Map<String, Object> params) {
		Query query = getSession().createQuery(hql);
		if (MapUtils.isNotEmpty(params)) {
			for (Entry<String, Object> entry : params.entrySet()) {
				if (StringUtils.isNotBlank(entry.getKey())) {
					if (entry.getValue().getClass().isArray()) {
						Object[] objects = (Object[]) entry.getValue();
						query.setParameterList(entry.getKey().toString(),
								objects);
					} else if (List.class.isAssignableFrom(entry.getValue()
							.getClass())) {
						List objects = (List) entry.getValue();
						query.setParameterList(entry.getKey().toString(),
								objects);
					} else {
						query.setParameter(entry.getKey().toString(),
								entry.getValue());
					}
				}
			}
		}
		return (T) query.uniqueResult();
	}

	/**
	 * 针对多个返回结果，获取其中一个的情况
	 * 
	 * @param hql
	 * @param params
	 * @return
	 * @date 2015年12月8日 下午3:59:04
	 * @author maliang
	 */
	public T findByHQLMoreResultSingle(String hql, Map<String, Object> params) {
		List<T> ts = this.findByHQLResultMore(hql, params);
		return ts == null || ts.size() == 0 ? null : ts.get(0);
	}

	/**
	 * 根据条件获取总条数
	 * <p>
	 * 需要自行添加 count关键字
	 * </p>
	 * 
	 * @param hql
	 *            查找条件
	 * @param params
	 *            过滤条件
	 * @return 返回该语句过滤以后的总条数
	 * @date 2015年11月25日 上午10:41:54
	 * @author maliang
	 */
	public Integer findByHQLResultCount(String hql, Map<String, Object> params) {
		Object object = this.findByHQLResultSingle(hql, params);
		return object == null ? 0 : Integer.valueOf(object.toString());
	}

	/**
	 * 获取总条数
	 * <p>
	 * 不需要添加 count(*)
	 * </p>
	 * 
	 * @param hql
	 * @param params
	 * @return
	 * @date 2015年12月9日 下午6:35:52
	 * @author maliang
	 */
	public Integer findByHQLResultCount2(String hql, Map<String, Object> params) {
		Integer count = 0;
		if (StringUtils.isNotBlank(hql)) {
			String tmp = "select count(*) from "
					+ StringUtils.substringAfterLast(hql, "from");
			count = this.findByHQLResultCount(tmp, params);
		}
		return count;
	}

	/**
	 * 获取总条数
	 * <p>
	 * 不建议采用该方式
	 * </p>
	 * 
	 * @param hql
	 * @param params
	 * @return
	 * @date 2015年12月9日 下午6:35:35
	 * @author maliang
	 */
	@Deprecated
	public Integer findByHQLResultCount3(String hql, Map<String, Object> params) {
		List<T> objs = this.findByHQLResultMore(hql, params);
		return objs == null ? 0 : objs.size();
	}

	/**
	 * sql 查询
	 * 
	 * @param sql
	 * @param params
	 * @param type
	 * @return
	 * @date 2016年2月19日 下午6:46:52
	 * @author luogang
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<T> findBySQLResult(String sql, Map<String, Object> params,
			Class<?> type) {
		SQLQuery query = getSession().createSQLQuery(sql).addEntity(type);
		if (MapUtils.isNotEmpty(params)) {
			for (Entry<String, Object> entry : params.entrySet()) {
				if (StringUtils.isNotBlank(entry.getKey())) {
					if (entry.getValue().getClass().isArray()) {
						Object[] objects = (Object[]) entry.getValue();
						query.setParameterList(entry.getKey().toString(),
								objects);
					} else if (List.class.isAssignableFrom(entry.getValue()
							.getClass())) {
						List objects = (List) entry.getValue();
						query.setParameterList(entry.getKey().toString(),
								objects);
					} else {
						query.setParameter(entry.getKey().toString(),
								entry.getValue());
					}
				}
			}
		}
		return query.list();
	}

	/*
	 * public Map<String, Object> findBySQLResultMap(String hql, Map<String,
	 * Object> params, int pageNo, int pageSize) { Map<String, Object> results =
	 * new HashMap<String, Object>();
	 * 
	 * int count = this.findByHQLResultCount2(hql, params);
	 * results.put(Page.RESULT_COUNT, count);
	 * 
	 * List<T> objs = this.findByHQLResultMore(hql, params, pageNo, pageSize);
	 * results.put(Page.RESULT_DATA, objs);
	 * 
	 * return results; }
	 */

	public int findBySQLResultCount(String sql, Map<String, Object> params) {
		SQLQuery query = getSession().createSQLQuery(sql);
		if (MapUtils.isNotEmpty(params)) {
			for (Entry<String, Object> entry : params.entrySet()) {
				if (StringUtils.isNotBlank(entry.getKey())) {
					if (entry.getValue().getClass().isArray()) {
						Object[] objects = (Object[]) entry.getValue();
						query.setParameterList(entry.getKey().toString(),
								objects);
					} else if (List.class.isAssignableFrom(entry.getValue()
							.getClass())) {
						List objects = (List) entry.getValue();
						query.setParameterList(entry.getKey().toString(),
								objects);
					} else {
						query.setParameter(entry.getKey().toString(),
								entry.getValue());
					}
				}
			}
		}
		Object object = query.uniqueResult();
		return object == null ? 0 : Integer.valueOf(object.toString());
	}

	/**
	 * sql 分页查询
	 * 
	 * @param sql
	 * @param params
	 * @param type
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @date 2016年2月19日 下午6:47:07
	 * @author luogang
	 */
	public List<T> findBySQLResultMore(String sql, Map<String, Object> params,
			Class<?> type, int pageNo, int pageSize) {
		SQLQuery query = getSession().createSQLQuery(sql);
		if (MapUtils.isNotEmpty(params)) {
			for (Entry<String, Object> entry : params.entrySet()) {
				if (StringUtils.isNotBlank(entry.getKey())) {
					if (entry.getValue().getClass().isArray()) {
						Object[] objects = (Object[]) entry.getValue();
						query.setParameterList(entry.getKey().toString(),
								objects);
					} else if (List.class.isAssignableFrom(entry.getValue()
							.getClass())) {
						List objects = (List) entry.getValue();
						query.setParameterList(entry.getKey().toString(),
								objects);
					} else {
						query.setParameter(entry.getKey().toString(),
								entry.getValue());
					}
				}
			}
		}
		query.addEntity(type);
		query.setFirstResult((pageNo - 1) * pageSize);
		query.setMaxResults(pageSize);
		return query.list();
	}

	public static void main(String[] args) {
		Integer integers = new Integer(0);
		System.out.println(List.class.isAssignableFrom(integers.getClass()));
	}
}
