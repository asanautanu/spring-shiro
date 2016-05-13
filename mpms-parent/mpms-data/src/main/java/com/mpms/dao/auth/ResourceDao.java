package com.mpms.dao.auth;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.mpms.common.enums.auth.ResourceType;
import com.mpms.common.po.auth.Resource;
import com.mpms.dao.BaseDao;

/**
 * 资源管理DAO
 * 
 * @date 2015年12月1日 下午5:12:08
 * @author luogang
 */
@Repository
public class ResourceDao extends BaseDao<Resource, Integer> {

	/**
	 * 根据角色查询对应资源
	 * 
	 * @param roleId
	 *            角色ID
	 * @param resourceType
	 *            资源类型
	 * @param pId
	 * @return
	 * @date 2015年12月17日 下午12:04:56
	 * @author luogang
	 */
	public List<Resource> findResourceBytype(List<Integer> roleIds, ResourceType resourceType, Integer pId) {
		String hql = "select distinct(rr) from Role r left join r.resource rr where 1=1 and rr.deleteFlag =false";
		Map<String, Object> params = Maps.newHashMap();
		if (CollectionUtils.isNotEmpty(roleIds)) {
			hql += " and r.id in(:roleIds) ";
			params.put("roleIds", roleIds);
		}
		if (resourceType != null) {
			hql += " and rr.resourceType =:resourceType ";
			params.put("resourceType", resourceType);
		}
		if (pId != null) {
			hql += " and rr.pId =:pId ";
			params.put("pId", pId);
		} else {
			hql += " and  rr.pId is null ";
		}
		hql += " order by rr.orderNo";
		return super.findByHQLResultMore(hql, params);
	}

	public List<Resource> findResourceByButton(List<Integer> roleIds, ResourceType resourceType) {
		String hql = "select distinct(rr) from Role r left join r.resource rr where 1=1 and rr.deleteFlag =false";
		Map<String, Object> params = Maps.newHashMap();
		if (CollectionUtils.isNotEmpty(roleIds)) {
			hql += " and r.id in(:roleIds) ";
			params.put("roleIds", roleIds);
		}
		if (resourceType != null) {
			hql += " and rr.resourceType =:resourceType ";
			params.put("resourceType", resourceType);
		}
		/*
		 * if (pId != null) { hql += " and rr.pId =:pId "; params.put("pId",
		 * pId); } else { hql += " and  rr.pId is null "; }
		 */ hql += " order by rr.orderNo";
		return super.findByHQLResultMore(hql, params);
	}

	public List<Resource> getResources(Integer pId) {
		String hql = String.format("%s%s%s", " from Resource r where 1=1 ",
				pId != null ? " and r.pId =:pId " : " and  r.pId is null ", " order by r.orderNo ");
		Map<String, Object> params = Maps.newHashMap();
		if (pId != null) {
			params.put("pId", pId);
		}
		return super.findByHQLResultMore(hql, params);
	}

	public List<Resource> getResourcesAll() {
		String hql = " from Resource r where 1=1 ";
		Map<String, Object> params = Maps.newHashMap();
		return super.findByHQLResultMore(hql, params);
	}

	/**
	 * 通过权限查询资源
	 * 
	 * @param permission
	 *            权限
	 * @return
	 * @date 2015年12月22日 下午3:19:17
	 * @author luogang
	 */
	public List<Resource> findResourceBypermission(String permission) {
		String hql = " from Resource r where r.permission =:permission";
		Map<String, Object> params = Maps.newHashMap();
		params.put("permission", permission);
		return super.findByHQLResultMore(hql, params);
	}
}
