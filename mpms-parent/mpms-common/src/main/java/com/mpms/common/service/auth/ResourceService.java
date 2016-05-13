package com.mpms.common.service.auth;

import java.util.List;

import com.mpms.common.enums.auth.ResourceType;
import com.mpms.common.exception.GlobalException;
import com.mpms.common.po.auth.Resource;
import com.mpms.common.vo.auth.ResourceVo;

/**
 * 资源权限接口
 * 
 * @date 2015年12月1日 下午4:51:10
 * @author luogang
 */
public interface ResourceService {

	/**
	 * 添加资源
	 * 
	 * @param resource
	 * @return
	 * @date 2015年12月1日 下午5:05:30
	 * @author luogang
	 * @throws GlobalException
	 */
	public Resource addResource(Resource resource) throws GlobalException;

	/**
	 * 修改资源
	 * 
	 * @param resource
	 * @return
	 * @date 2015年12月7日 上午11:40:10
	 * @author luogang
	 * @throws GlobalException
	 */
	public Resource updateResource(Resource resource) throws GlobalException;

	/**
	 * 按角色查询 资源
	 * 
	 * @param roleId
	 *            角色ID
	 * @param resourceType
	 *            资源类型
	 * @param pId
	 *            父ID
	 * @return
	 * @date 2015年12月22日 下午2:19:03
	 * @author luogang
	 */
	public List<Resource> findResourceBytype(List<Integer> roleIds, ResourceType resourceType, Integer pId);

	/**
	 * 通过权限查询资源
	 * 
	 * @param permission
	 *            权限
	 * @return
	 * @date 2015年12月22日 下午3:19:17
	 * @author luogang
	 */
	public List<Resource> findResourceBypermission(String permission);

	/**
	 * 通过角色级类型查询资源
	 * 
	 * @param roleIds
	 *            角色ID集合
	 * @param resourceType
	 *            资源类型
	 * @param pId
	 *            父ID
	 * @return
	 * @throws GlobalException
	 * @date 2015年12月24日 下午3:17:48
	 * @author luogang
	 */
	public List<ResourceVo> getResourceMenu(List<Integer> roleIds, ResourceType resourceType, Integer pId)
			throws GlobalException;

	/**
	 * 获取所有资源列表数据
	 * 
	 * @param pId
	 * @return
	 * @throws GlobalException
	 * @date 2015年12月25日 下午3:43:28
	 * @author luogang
	 */
	public List<ResourceVo> getResourceList(Integer pId) throws GlobalException;

	/**
	 * 获取所有资源列表
	 * 
	 * @return
	 * @date 2016年1月6日 下午12:14:13
	 * @author luogang
	 */
	public List<Resource> getResourcesAll();

	public Resource findById(int id);

	List<Resource> findByPid(int pId);

	List<Resource> findResourceByButton(List<Integer> roleId, ResourceType resourceType);

}
