package com.mpms.service.impl.auth;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpms.common.enums.auth.ResourceType;
import com.mpms.common.exception.ExceptionCode;
import com.mpms.common.exception.GlobalException;
import com.mpms.common.po.auth.Resource;
import com.mpms.common.service.auth.ResourceService;
import com.mpms.common.vo.auth.ResourceVo;
import com.mpms.dao.auth.ResourceDao;

/**
 * 资源接口实现
 * 
 * @date 2015年12月1日 下午5:28:50
 * @author luogang
 */
@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	private ResourceDao resourceDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { GlobalException.class, RuntimeException.class })
	public Resource addResource(Resource resource) throws GlobalException {
		if (resource == null) {
			LOG.error("资源为空");
			throw new GlobalException(ExceptionCode.AUTH_ERROR_CEDE, "资源为空");
		}
		resourceDao.save(resource);
		return resource;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { GlobalException.class, RuntimeException.class })
	public Resource updateResource(Resource resource) throws GlobalException {
		if (resource == null) {
			LOG.error("资源为空");
			throw new GlobalException(ExceptionCode.AUTH_ERROR_CEDE, "资源为空");
		}
		if (resourceDao.findById(resource.getId()) == null) {
			LOG.error("资源[" + resource.getId() + "]不存在 ");
			throw new GlobalException(ExceptionCode.AUTH_ERROR_CEDE, "资源[" + resource.getId() + "]不存在 ");
		}
		resourceDao.update(resource);
		return resource;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Resource> findResourceBytype(List<Integer> roleId, ResourceType resourceType, Integer pId) {
		return resourceDao.findResourceBytype(roleId, resourceType, pId);

	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Resource> findResourceByButton(List<Integer> roleId, ResourceType resourceType) {
		return resourceDao.findResourceByButton(roleId, resourceType);

	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Resource> findResourceBypermission(String permission) {

		return this.resourceDao.findResourceBypermission(permission);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ResourceVo> getResourceMenu(List<Integer> roleIds, ResourceType resourceType, Integer pId)
			throws GlobalException {
		List<ResourceVo> jsonArray = resourceMenu(roleIds, resourceType, pId);
		return jsonArray;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ResourceVo> getResourceList(Integer pId) throws GlobalException {
		return resourceList(pId);
	}

	/**
	 * 组装导航菜单
	 * 
	 * @param roleId
	 * @param resourceType
	 * @param pId
	 * @return
	 * @throws GlobalException
	 * @date 2015年12月25日 下午3:54:27
	 * @author luogang
	 */
	private List<ResourceVo> resourceMenu(List<Integer> roleId, ResourceType resourceType, Integer pId)
			throws GlobalException {
		List<Resource> resources = this.resourceDao.findResourceBytype(roleId, ResourceType.MENU, pId);
		/*
		 * Set<Resource> resourcesSet =Sets.newHashSet();
		 * resourcesSet.addAll(resources);
		 */
		List<ResourceVo> resourceVos = new ArrayList<ResourceVo>();
		if (resources != null && resources.size() > 0) {
			for (Resource resource : resources) {
				ResourceVo no = new ResourceVo();
				no.setId(resource.getId());
				no.setName(resource.getName());
				if (StringUtils.isNotBlank(resource.getHref())) {
					no.setUrl(resource.getHref());
				}
				List<Resource> resources2 = this.resourceDao.findResourceBytype(roleId, resourceType, resource.getId());
				if (resources2 != null && resources2.size() > 0) {
					no.setNode(resourceMenu(roleId, resourceType, resource.getId()));
				}
				resourceVos.add(no);
			}
		}
		return resourceVos;
	}

	/**
	 * 组装资源数据
	 * 
	 * @param pId
	 * @return
	 * @throws GlobalException
	 * @date 2015年12月25日 下午3:54:10
	 * @author luogang
	 */
	private List<ResourceVo> resourceList(Integer pId) throws GlobalException {
		List<Resource> resources = this.resourceDao.getResources(pId);
		List<ResourceVo> resourceVos = new ArrayList<ResourceVo>();
		if (resources != null && resources.size() > 0) {
			for (Resource resource : resources) {
				ResourceVo no = new ResourceVo();
				no.setId(resource.getId());
				if (pId == null) {
					no.setpId(0);
				} else {
					no.setpId(pId);
				}

				no.setName(resource.getName());
				if (StringUtils.isNotBlank(resource.getHref())) {
					no.setUrl(resource.getHref());
				}
				List<Resource> resources2 = this.resourceDao.getResources(resource.getId());
				if (resources2 != null && resources2.size() > 0) {
					resourceVos.addAll(resourceList(resource.getId()));
				}
				resourceVos.add(no);
			}
		}
		return resourceVos;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Resource> getResourcesAll() {
		return this.resourceDao.findAll();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Resource findById(int id) {
		return this.resourceDao.findById(id);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Resource> findByPid(int pId) {
		return this.resourceDao.getResources(pId);
	}
}
