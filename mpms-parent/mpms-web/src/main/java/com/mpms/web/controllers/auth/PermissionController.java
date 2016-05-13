package com.mpms.web.controllers.auth;

import java.util.List;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mpms.common.exception.GlobalException;
import com.mpms.web.manager.auth.PermissionManager;
import com.mpms.web.view.BaseController;
import com.mpms.web.view.MpmsReponse;

/**
 * 权限管理控制器
 * 
 * @date 2015年12月16日 下午4:09:41
 * @author luogang
 */
@RestController
@Scope("prototype")
@RequestMapping("/permission")
public class PermissionController extends BaseController {

	@Autowired
	private PermissionManager permissionManager;

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
	@RequestMapping(value = "list", method = { RequestMethod.GET })
	@RequiresPermissions("sys:permission:list")
	public MpmsReponse list(String name, @RequestParam(defaultValue = "1") int pageNo,
			@RequestParam(defaultValue = "20") int pageSize) {

		return permissionManager.list(name, pageNo, pageSize);
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
	@RequestMapping(value = "add", method = { RequestMethod.POST })
	@RequiresPermissions(value = { "sys:permission:add", "sys:permission:add1" }, logical = Logical.OR)
	public MpmsReponse addRole(String name, String description,
			@RequestParam(value = "resourceIds[]", required = false) List<Integer> resourceIds) throws GlobalException {
		return permissionManager.addRole(name, description, resourceIds);
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
	@RequestMapping(value = "update", method = { RequestMethod.POST })
	@RequiresPermissions("sys:permission:update")
	public MpmsReponse updateRole(int id, String name, String description,
			@RequestParam(value = "resourceIds[]", required = false) Integer[] resourceIds) throws GlobalException {
		return permissionManager.updateRole(id, name, description, resourceIds);

	}

	/**
	 * 查询角色详情
	 * 
	 * @param id
	 * @return
	 * @date 2015年12月14日 下午4:45:29
	 * @author luogang
	 */
	@RequestMapping(value = "info", method = { RequestMethod.GET })
	@RequiresPermissions("sys:permission:info")
	public MpmsReponse roleInfo(int id) {
		return permissionManager.roleInfo(id);

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
	@RequestMapping(value = "del", method = RequestMethod.GET)
	@RequiresPermissions("sys:permission:del")
	public MpmsReponse delRoleById(@RequestParam(value = "id[]") Integer[] id) throws GlobalException {
		return permissionManager.delRoleById(id);
	}

	/**
	 * 获取登录用户的导航菜单
	 * 
	 * @return
	 * @throws GlobalException
	 * @date 2015年12月25日 下午12:23:34
	 * @author luogang
	 */
	@RequestMapping(value = "menu", method = RequestMethod.GET)
	public MpmsReponse getResourceMenu() throws GlobalException {
		return permissionManager.getResourceMenu();
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
	@RequestMapping(value = "button", method = RequestMethod.GET)
	public MpmsReponse getReourceButton() throws GlobalException {
		return permissionManager.getResourceButton();
	}

	/**
	 * 获取所有资源列表
	 * 
	 * @return
	 * @throws GlobalException
	 * @date 2015年12月25日 下午5:16:43
	 * @author luogang permission/resourcelist.do
	 */
	@RequestMapping(value = "resourcelist", method = RequestMethod.GET)
	public MpmsReponse getResourceList() throws GlobalException {
		return permissionManager.getResourceList();
	}

	/**
	 * 获取所有资源列表 无URL数据
	 * 
	 * @return
	 * @throws GlobalException
	 * @date 2015年12月25日 下午5:16:43
	 * @author luogang permission/resourcelist.do
	 */
	@RequestMapping(value = "resourcelistURL", method = RequestMethod.GET)
	public MpmsReponse getResourceListURL() throws GlobalException {
		return permissionManager.getResourceListNoURL();
	}

	/**
	 * 根据角色ID查询资源
	 * 
	 * @param roleId
	 * @return
	 * @date 2016年1月5日 下午6:30:21
	 * @author luogang
	 */
	@RequestMapping(value = "resourcebyrole", method = RequestMethod.GET)
	public MpmsReponse getResourceByRoleId(int roleId) {
		return permissionManager.getResourceByRoleId(roleId);
	}

	/**
	 * 获取所有权限
	 * 
	 * @return
	 * @date 2016年1月5日 下午9:22:30
	 * @author luogang
	 */
	@RequestMapping(value = "all", method = RequestMethod.GET)
	public MpmsReponse getRoleAll() {
		return permissionManager.getRoleAll();
	}
}
