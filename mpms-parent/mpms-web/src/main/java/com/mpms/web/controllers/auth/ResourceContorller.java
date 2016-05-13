package com.mpms.web.controllers.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mpms.common.exception.ExceptionCode;
import com.mpms.common.exception.GlobalException;
import com.mpms.common.po.auth.Resource;
import com.mpms.common.service.auth.ResourceService;
import com.mpms.web.view.BaseManager;

@RestController
@Scope("prototype")
@RequestMapping("resource")
public class ResourceContorller extends BaseManager {

	@Autowired
	private ResourceService resourceService;

	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String add(Resource resource, Integer[] resIds) throws GlobalException {

		if (resIds.length > 1) {
			throw new GlobalException(ExceptionCode.AUTH_ERROR_CEDE, "只能选择一个");
		}
		if (resIds.length > 0) {
			resource.setpId(resIds[0]);
		}
		if (resource.getPermission() != null && "".equals(resource.getPermission().trim())) {
			resource.setPermission(null);
		}
		List<Resource> resources = null;
		if (resource.getpId() != null) {
			resources = resourceService.findByPid(resource.getpId());
		}

		if (resources != null && resources.size() > 0) {
			Resource resource2 = resources.get(resources.size() - 1);
			resource.setOrderNo(resource2.getOrderNo() + 1);
		} else {
			resource.setOrderNo(1);
		}
		resourceService.addResource(resource);
		return "OK";
	}
}
