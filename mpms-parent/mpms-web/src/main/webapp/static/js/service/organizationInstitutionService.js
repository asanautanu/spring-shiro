/**
 * 所属组织架构服务
 */
(function() {
	var orgInstitutionIds = [], zNodes = [], orgId;

	var setting = {
		check : {
			enable : true,
			chkboxType : {
				"Y" : "s",
				"N" : ""
			}
		},
		view : {
			dblClickExpand : false
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			beforeClick : beforeClick,
			onCheck : onCheck
		}
	};

	var organizationInstitutionService = {
		organizationInstitutionCreate : organizationInstitutionCreate,
		setOrganizationInstitution : setOrganizationInstitution,
		getOrganizationInstitution : getOrganizationInstitution,
		showMenu : showMenu,
		callback : "",
		data : {}
	};

	/**
	 * organizationInstitutionCreate
	 * 
	 * @param currentId
	 *            不为空时查询当前ID直系机构
	 * @param institutionType
	 *            组织类型
	 * @param departmentType
	 *            部门类型
	 * @param isCurrent
	 *            是否当前登录用户
	 * @param isChildren
	 *            是否只要下级 return [array]
	 */
	function organizationInstitutionCreate(data) {
		data = data || {};
		var orgInstitutionList = [], url = globalPath + 'institution/condition.do';
		// 没有传值，取所有
		if (arguments[0] === 'single' || arguments.length === 0) {
			restAjax(url, data, "GET", function(json) {
				if (json.code === 0) {
					orgInstitutionList = zNodes = setList(json.data.institutions);
				}
			}, null, null, null, null, false);
		}

		// 根据 currentId 取值
		if (typeof (arguments[0]) === "object") {

			var parameter = {};

			$.each(arguments, function(i, v) {
				if (v.currentId) {
					parameter.currentId = v.currentId;
				}
				if (v.institutionType) {
					parameter.institutionType = v.institutionType;
				}
				if (v.departmentType) {
					parameter.departmentType = v.departmentType;
				}
				if (v.isCurrent) {
					parameter.isCurrent = v.isCurrent;
				}
				if (v.isChildren) {
					parameter.isChildren = v.isChildren;
				}
			});

			restAjax(url, parameter, "GET", function(json) {
				if (json.code === 0) {
					orgInstitutionList = zNodes = setList(json.data.institutions);
				} else {
					return;
				}
			}, null, "JSON", null, null, false);
		}

		if (arguments[0] === 'single' || arguments[1] === 'single') {
			delete setting.check
		}

		$.fn.zTree.init($("#organizationInstitutionTree"), setting, zNodes);

		// return orgInstitutionList;
	}

	/**
	 * setList 得到返回数据 array
	 */
	function setList(institutions) {
		var list = [];

		if (institutions.length > 0) {
			for ( var i in institutions) {
				list.push($.extend(institutions[i], {
					open : true
				}));
			}
		}

		return list;
	}

	/**
	 * setId 得到返回数据 id
	 */
	function setId(id) {
		return orgId = id;
	}

	/**
	 * setOrganizationInstitution
	 * 
	 * @param array
	 *            return array
	 */
	function setOrganizationInstitution(list) {
		if (list.length > 0) {
			orgInstitutionIds = list;
		} else {
			orgInstitutionIds = "";
		}
		return orgInstitutionIds;
	}

	/**
	 * getOrganizationInstitution return array
	 */
	function getOrganizationInstitution() {
		if (orgId) {
			return orgId;
		} else {
			return orgInstitutionIds;
		}
	}

	/**
	 * 点击树前
	 */
	function beforeClick(treeId, treeNode) {
		if (setting.check) {
			var zTree = $.fn.zTree.getZTreeObj("organizationInstitutionTree");
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		} else {
			setId(treeNode.id);
			if (treeNode.id) {
				$("#organizationInstitutionInput").attr("value", treeNode.name);
			} else {
				$("#organizationInstitutionInput").attr("value", '未选择');
			}
		}
	}

	/**
	 * 点击树按钮
	 */
	function onCheck(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("organizationInstitutionTree"), nodes = zTree.getCheckedNodes(true), v = "", ids = [], cityObj = $("#organizationInstitutionInput");

		if (nodes.length > 0) {
			for (var i = 0, l = nodes.length; i < l; i++) {
				v += nodes[i].name + ",";
				ids.push(nodes[i].id);
			}

			// 写入 选中的id
			organizationInstitutionService.setOrganizationInstitution(ids);
		} else {
			organizationInstitutionService.setOrganizationInstitution(ids);
		}

		if (v.length > 0) {
			v = v.substring(0, v.length - 1);
			cityObj.attr("value", '已选择');

		} else {
			cityObj.attr("value", '未选择');

		}
		;

		// TODO 触发级联选项
	}

	/**
	 * 显示树方法
	 */
	function showMenu() {
		var cityObj = $("#organizationInstitutionInput");
		var cityOffset = $("#organizationInstitutionInput").offset();
		$('.organizationTreeWrap').slideDown("fast");
		$("#organizationInstitutionTree").focus();
		$("#organizationInstitutionTree").css({
			left : cityOffset.left + "px",
			top : cityOffset.top + cityObj.outerHeight() + "px"
		}).slideDown("fast");
		$("body").bind("mousedown", onBodyDown);
	}

	/**
	 * 隐藏树方法
	 */
	function hideMenu() {
		$('.organizationTreeWrap').fadeOut("fast");
		$("#organizationInstitutionTree").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
		// 回调
		if (organizationInstitutionService.callback && typeof organizationInstitutionService.callback === "function") {
			organizationInstitutionService.callback(organizationInstitutionService.data);
		}
	}

	/**
	 * 关闭树
	 */
	function onBodyDown(event) {
		if (!(event.target.id == "menuBtn" || event.target.id == "organizationInstitutionInput" || event.target.id == "organizationInstitutionTree" || $(
				event.target).parents("#organizationInstitutionTree").length > 0)) {
			hideMenu();
		}
	}

	window.organizationInstitutionService = organizationInstitutionService;
}());
