package com.mpms.web.manager.auth;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mpms.auth.shiro.UserRealm.ShiroUser;
import com.mpms.auth.shiro.UserUtils;
import com.mpms.common.enums.auth.ResourceType;
import com.mpms.common.exception.ExceptionCode;
import com.mpms.common.exception.GlobalException;
import com.mpms.common.po.auth.Resource;
import com.mpms.common.po.auth.Role;
import com.mpms.common.service.auth.ResourceService;
import com.mpms.common.service.auth.RoleService;
import com.mpms.common.service.auth.UserService;
import com.mpms.common.vo.auth.ResourceVo;
import com.mpms.utils.string.StringUtil;
import com.mpms.web.view.BaseManager;
import com.mpms.web.view.MpmsReponse;

/**
 * 权限MANAGER
 * 
 * @date 2015年12月14日 下午3:27:47
 * @author luogang
 */
@Component
@Scope("prototype")
public class PermissionManager extends BaseManager {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	private RoleService roleService;

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private UserService userService;

	/**
	 * 分页查询
	 * 
	 * @param name
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @date 2015年12月14日 下午3:27:21
	 * @author luogang
	 */
	public MpmsReponse list(String name, @RequestParam(defaultValue = "1") int pageNo,
			@RequestParam(defaultValue = "20") int pageSize) {
		total = roleService.listCount(name);
		if (total > 0) {
			list = roleService.list(name, pageNo, pageSize);
		}
		return MpmsReponse.addListResponse("查询成功", total, pageNo, pageSize, list);
	}

	/**
	 * 添加角色
	 * 
	 * @param name
	 *            角色名
	 * @param description
	 *            备注
	 * @param resourceIds
	 * @return
	 * @throws GlobalException
	 * @date 2015年12月14日 下午3:48:34
	 * @author luogang
	 */
	public MpmsReponse addRole(String name, String description, List<Integer> resourceIds) throws GlobalException {
		if (roleService.listCount(name) > 0) {
			throw new GlobalException(ExceptionCode.AUTH_ERROR_CEDE, "权限名称重复");
		}
		if (StringUtil.isBlank(name)) {
			throw new GlobalException(ExceptionCode.AUTH_ERROR_CEDE, "权限名为空 ");
		}
		if (name.length() > 25) {
			throw new GlobalException(ExceptionCode.AUTH_ERROR_CEDE, "权限名长度超限");
		}
		Role role = new Role();
		role.setName(name);
		role.setDescription(description);
		role.setCreater(UserUtils.getLoginUser().getId());
		role.setCreaterName(UserUtils.getLoginUser().getName());
		role.setCreateDate(new Date());
		role.setUpdateDate(new Date());
		Set<Resource> resources = null;
		if (resourceIds != null) {
			resources = Sets.newHashSet();
			for (Integer resourceId : resourceIds) {
				Resource resource = new Resource();
				resource.setId(resourceId);
				resources.add(resource);
			}
		}
		role.setResource(resources);
		Role data = roleService.addRole(role);
		Map<String, Object> returnData = new HashMap<String, Object>();
		returnData.put("data", data);
		return MpmsReponse.addSuccessResponse("添加成功", returnData);
	}

	/**
	 * 更新角色
	 * 
	 * @param id
	 *            ID
	 * @param name
	 *            角色名
	 * @param description
	 *            备注
	 * @param resourceIds
	 *            资源集合
	 * @return
	 * @throws GlobalException
	 * @date 2015年12月14日 下午4:41:57
	 * @author luogang
	 */
	public MpmsReponse updateRole(int id, String name, String description, Integer[] resourceIds)
			throws GlobalException {
		Role role = roleService.findById(id);
		if (role == null) {
			throw new GlobalException(ExceptionCode.AUTH_ERROR_CEDE, "权限[" + id + "]不存在 ");
		}
		if (StringUtil.isBlank(name)) {
			throw new GlobalException(ExceptionCode.AUTH_ERROR_CEDE, "权限名为空 ");
		}
		if (name.length() > 25) {
			throw new GlobalException(ExceptionCode.AUTH_ERROR_CEDE, "权限名长度超限");
		}
		if (roleService.listCount(name) > 0 && !name.equals(role.getName())) {
			throw new GlobalException(ExceptionCode.AUTH_ERROR_CEDE, "权限名称重复");
		}
		role.setName(name);
		role.setDescription(description);
		Set<Resource> resources = null;
		if (resourceIds != null) {
			resources = Sets.newHashSet();
			for (Integer resourceId : resourceIds) {
				Resource resource = new Resource();
				resource.setId(resourceId);
				resources.add(resource);
			}
		}
		role.setResource(resources);
		role.setUpdateDate(new Date());
		Role data = roleService.updateRole(role);
		// 用户权限更新
		List<String> userNames = userService.getUsersByRoleId(id);
		if (userNames != null && userNames.size() > 0) {
		}

		Map<String, Object> returnData = new HashMap<String, Object>();
		returnData.put("data", data);
		return MpmsReponse.addSuccessResponse("更新成功", returnData);
	}

	/**
	 * 查询角色详情
	 * 
	 * @param id
	 * @return
	 * @date 2015年12月14日 下午4:45:29
	 * @author luogang
	 */
	public MpmsReponse roleInfo(int id) {
		Role data = roleService.findById(id);
		Map<String, Object> returnData = new HashMap<String, Object>();
		returnData.put("data", data);
		return MpmsReponse.addSuccessResponse("查询成功", returnData);

	}

	/**
	 * 获取登录用户的导航菜单
	 * 
	 * @return
	 * @throws GlobalException
	 * @date 2015年12月25日 上午11:41:44
	 * @author luogang
	 */
	public MpmsReponse getResourceMenu() throws GlobalException {
		ShiroUser user = UserUtils.getLoginUser();
		if (user != null && CollectionUtils.isEmpty(user.getRoleIds())) {
			throw new GlobalException(ExceptionCode.AUTH_ERROR_CEDE, "用户没有登录或没有权限");
		}
		List<ResourceVo> resourceMenu = resourceService.getResourceMenu(user.getRoleIds(), ResourceType.MENU, null);
		Map<String, Object> returnDat = new HashMap<String, Object>();

		returnDat.put("resourceMenu", resourceMenu);
		return MpmsReponse.addSuccessResponse("查询成功", returnDat);
	}

	/**
	 * 获取登录用户的按钮权限
	 * 
	 * @param menuId
	 * @return
	 * @throws GlobalException
	 * @date 2015年12月25日 下午3:24:20
	 * @author luogang
	 */
	public MpmsReponse getResourceButton() throws GlobalException {
		List<Resource> resourceButton = resourceService.findResourceByButton(UserUtils.getLoginUser().getRoleIds(),
				ResourceType.BUTTON);

		Map<String, Object> returnData = Maps.newHashMap();
		for (Resource resource : resourceButton) {
			if (StringUtil.isNotBlank(resource.getPermission())) {
				returnData.put(resource.getPermission(), resource.getPermission());
			}

		}
		return MpmsReponse.addSuccessResponse("查询成功", returnData);
	}

	/**
	 * 获取资源列表
	 * 
	 * @return
	 * @throws GlobalException
	 * @date 2015年12月25日 下午3:57:59
	 * @author luogang
	 */
	public MpmsReponse getResourceList() throws GlobalException {
		List<ResourceVo> resourceList = resourceService.getResourceList(null);
		Map<String, Object> returnData = new HashMap<String, Object>();
		returnData.put("resourceList", resourceList);
		return MpmsReponse.addSuccessResponse("查询成功", returnData);
	}

	public MpmsReponse getResourceListNoURL() throws GlobalException {
		List<ResourceVo> resourceList = resourceService.getResourceList(null);
		List<ResourceVo> resourceResiuList = null;
		if (CollectionUtils.isNotEmpty(resourceList)) {
			resourceResiuList = Lists.newArrayList();
			for (ResourceVo resourceVo : resourceList) {
				resourceVo.setUrl(null);
				resourceResiuList.add(resourceVo);
			}
		}

		Map<String, Object> returnData = new HashMap<String, Object>();
		returnData.put("resourceList", resourceResiuList);
		return MpmsReponse.addSuccessResponse("查询成功", returnData);
	}

	/**
	 * 删除角色
	 * 
	 * @param id
	 * @return
	 * @throws GlobalException
	 * @date 2016年1月5日 下午1:56:00
	 * @author luogang
	 */
	public MpmsReponse delRoleById(Integer[] id) throws GlobalException {
		List<String> list = Lists.newArrayList();
		for (Integer integer : id) {

			try {
				List<String> user = userService.getUsersByRoleId(integer);
				if (CollectionUtils.isNotEmpty(user)) {
					throw new GlobalException(ExceptionCode.AUTH_ERROR_CEDE, "权限正在使用不能删除");
				}
				roleService.delRoleById(integer);

			} catch (Exception e) {
				LOG.error("权限[" + integer + "]删除失败");
				Role findById = roleService.findById(integer);
				if (findById != null) {
					list.add(findById.getName());
				}
				e.printStackTrace();
			}
		}
		for (Integer idInteger : id) {
			List<String> userNames = userService.getUsersByRoleId(idInteger);
			if (userNames != null && userNames.size() > 0) {
			}
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("role", list);
		return MpmsReponse.addSuccessResponse(CollectionUtils.isNotEmpty(list) ? "权限" + list + "删除失败" : "删除成功", data);
	}

	/**
	 * 根据角色ID查询资源
	 * 
	 * @param userId
	 * @return
	 * @date 2016年1月5日 下午6:09:57
	 * @author luogang
	 */
	public MpmsReponse getResourceByRoleId(int roleId) {
		List<Integer> roles = roleService.getResourceByRoleId(roleId);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("roles", roles);
		return MpmsReponse.addSuccessResponse("查询成功", data);
	}

	/**
	 * 获取所有权限
	 * 
	 * @return
	 * @date 2016年1月5日 下午9:16:06
	 * @author luogang
	 */
	public MpmsReponse getRoleAll() {
		List<Role> roles = roleService.getRoleAll();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("roles", roles);
		return MpmsReponse.addSuccessResponse("查询成功", data);
	}

}
