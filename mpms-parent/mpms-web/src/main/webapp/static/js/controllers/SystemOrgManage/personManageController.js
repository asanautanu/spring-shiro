/**
 * 人员管理模块
 */
$(function() {
	var roles = [], rolesLength = 0, getRoles = [], isInit = true, isSearch = false, $form = $('#personManageForm'), $table = $('#table'), $remove = $('#remove'), $create = $('#create'), $update = $('#update'), $retrieve = $('#retrieve'), $btnSearch = $('#btnSearch'), $submit = $('#submit'), $resetPass = $('#resetPass'), $allRoles = $('#allRoles'), $hasRoles = $('#hasRoles'), $right = $('#right'), $left = $('#left'), $roleSubmit = $('#roleSubmit'), searchName, isLogin = true; // 代表已登录

	/**
	 * 初始化弹出层
	 */
	function openModal(title) {
		// 禁用 BootStrap Modal 点击空白时自动关闭
		$('#myModal').modal({
			backdrop : 'static',
			keyboard : false
		});
		$("#myModal .modal-title").text(title);

	}

	/**
	 * 权限管理按钮点击事件
	 */
	$('#btnQxgl')
			.click(
					function() {
						// 左边权限列表赋值
						var roleList = roles, isRepeated;
						$('#allRoles a').remove();

						if (roleList.length > 0) {
							for (var i = 0; i < roleList.length; i++) {
								var isRoleId = roleList[i].id;
								isRepeated = false;

								if ($('.modal-title').eq(0).text() !== "创建人员") {
									for (var j = 0; j < updateObj[0].roles.length; j++) {
										var hasRoleId = updateObj[0].roles[j].id;

										if (isRoleId === hasRoleId) {
											isRepeated = true;
											break;
										}
									}
									if (!isRepeated) {
										$allRoles
												.append('<a href="javascript:;" class="list-group-item p-tb-xxs allRole-item" id="'
														+ roleList[i].id
														+ '" >'
														+ roleList[i].name
														+ '</a>');
									}
								} else {
									$allRoles
											.append('<a href="javascript:;" class="list-group-item p-tb-xxs allRole-item" id="'
													+ roleList[i].id
													+ '" >'
													+ roleList[i].name + '</a>');
								}
							}
						}
					});

	/**
	 * 权限列表点击事件
	 */
	$(document).on("click", '.allRole-item, .hasRole-item', function() {
		if ($(this).hasClass('allRole-item')) {
			$(this).toggleClass('text-info');
		}

		if ($(this).hasClass('hasRole-item')) {
			$(this).toggleClass('text-warning');
		}
	});

	/**
	 * 监听搜索关键字变化
	 */
	$('#inputSearch').bind('input propertychange', function() {
		searchName = $.trim($(this).val());
	});

	/**
	 * 滚动条层初始化
	 */
	$(".full-height-scroll").slimScroll({
		height : "100%"
	});

	/**
	 * 改变登录开关按钮值
	 */
	$('#login').on('click', function() {
		isLogin = !isLogin;

		// 登录否
		if (!isLogin) {
			$('.loginInfoWrap').addClass('hide');
		} else {
			$('.loginInfoWrap').removeClass('hide');
		}
	});

	/**
	 * 创建权限
	 */
	$roleSubmit.click(function() {
		$('.rolesLength').text(getRoles.length);

		layer.alert("您拥有" + getRoles.length + "个权限", {
			icon : 6
		});

		$('#myModal1').modal('hide');
	});

	/**
	 * 权限左按钮点击事件
	 */
	$left.click(function() {
		var ls = [], id, name;

		$('#hasRoles a').each(
				function(index, value) {
					if ($(this).hasClass('text-warning')) {
						$(this).remove();
						id = parseInt($(this).attr('id')), name = $(this)
								.text();
						// ls.push(id);

						$(getRoles).each(function(index, value) {
							if (value == id) {
								getRoles.splice(index, 1);
							}
						});

						// 加入已选择的权限
						$('#allRoles').append(
								'<a href="javascript:;" class="list-group-item p-tb-xxs allRole-item" id="'
										+ id + '" >' + name + '</a>');

						// 去掉权限列表中选中值
						$('#allRoles a').each(function(index, value) {
							if ($(this).attr('id') == id) {
								$(this).removeClass('text-info');
							}
						});
					}
				});

		// getRoles = ls;
	});

	/**
	 * 权限右按钮点击事件
	 */
	$right
			.click(function() {
				var rs = [];

				// 循环权限列表
				$('#allRoles a')
						.each(
								function(index, value) {
									if ($(this).hasClass('text-info')) {
										var id = parseInt($(this).attr('id')), name = $(
												this).text();

										rs.push(id);
										getRoles.push(id);

										// 加入已选择的权限
										$hasRoles
												.append('<a href="javascript:;" class="list-group-item p-tb-xxs hasRole-item" id="'
														+ id
														+ '" >'
														+ name
														+ '</a>');

										$(this).remove();
									}
								});

				if (rs.length > 0) {
					// getRoles = rs;
				}
			});

	/**
	 * 创建按钮点击事件
	 */
	$create.click(function() {
		$('#hasRoles a').remove();
		$('#name').val('');
		$('#userName').val('');
		$('#number').val('');
		$('.rolesLength').text('0');
		$('#login').attr("checked", false);
		$('#login').attr('disabled', false);
		$('.loginInfoWrap').removeClass('hide');

		$('.modal-footer button').show();
		$('.btn-bitbucket').show();
		restAjax(globalPath + 'permission/all.do', null, "GET", function(data) {
			if (data.data.roles) {
				return roles = data.data.roles;
			}
		}, null, "JSON", null, null, false);
		openModal('创建人员');
	});

	/**
	 * 删除按钮点击事件
	 */
	$remove.click(function() {
		removeObj = $.map($table.bootstrapTable('getSelections'),
				function(row) {
					return row;
				});

		if (removeObj.length === 0) {
			layer.alert('请选择一条记录', {
				icon : 5
			});
		} else {

			var delId = [];
			for ( var i in removeObj) {
				delId.push(parseInt(removeObj[i].id));
			}

			layer.confirm('您确定要删除这条信息吗？', {
				btn : [ '是的', '取消' ]
			}, function(index) {
				layer.close(index);
				restAjax(globalPath + 'user/del.do', {
					userId : delId
				}, "GET", function(json) {
					if (json.code === 0) {
						layer.alert(json.msg, {
							icon : 6
						});
						// 删除用户表中对应数据
						$table.bootstrapTable('refresh');
					} else {
						layer.alert(json.msg, {
							icon : 5
						});
					}
				}, null, "JSON", {
					btn : $remove
				});
			}, function() {
				layer.alert("您取消了删除操作！", {
					icon : 2
				});
			});
		}
	});

	/**
	 * 修改按钮点击事件
	 */
	$update
			.click(function() {
				$('.modal-footer button').show();
				$('.btn-bitbucket').show();
				$('#login').attr('disabled', false);

				getRoles = [];

				updateObj = $.map($table.bootstrapTable('getSelections'),
						function(row) {
							return row;
						});

				if (updateObj.length === 0) {
					layer.alert('请选择一条记录', {
						icon : 5
					});
				} else if (updateObj.length > 1) {
					layer.alert('只可选择一条记录', {
						icon : 5
					});
				} else {
					// 用户ID
					$('#userId').val(updateObj[0].id ? updateObj[0].id : null);
					$('#name')
							.val(updateObj[0].name ? updateObj[0].name : null);
					$('#userName').val(
							updateObj[0].userName ? updateObj[0].userName
									: null);
					$('#number').val(
							updateObj[0].number ? updateObj[0].number : null);
					$('.rolesLength').text(updateObj[0].roles.length);

					openModal('修改人员');
					isLogin = updateObj[0].login;

					if (updateObj[0].login) {
						$('.loginInfoWrap').removeClass('hide');
						$('#login').attr("checked", false);
					} else {
						$('.loginInfoWrap').addClass('hide');
						$('#login').prop("checked", true)
						$('#login').attr('checked', true);
					}

					// 添加 已有权限列表
					$('#hasRoles a').remove();

					for (var i = 0; i < updateObj[0].roles.length; i++) {
						getRoles.push(updateObj[0].roles[i].id);
						$hasRoles
								.append('<a href="javascript:;" class="list-group-item p-tb-xxs hasRole-item" id="'
										+ updateObj[0].roles[i].id
										+ '" >'
										+ updateObj[0].roles[i].name + '</a>');
					}

					restAjax(globalPath + 'permission/all.do', null, "GET",
							function(data) {
								if (data.data.roles) {
									return roles = data.data.roles;
								}
							}, null, "JSON", {
								btn : $update
							}, null, false);
				}
			});

	/**
	 * 查看人员
	 */
	$retrieve
			.click(function() {
				$('.modal-footer button').hide();
				$('.btn-bitbucket').hide();

				obj = $.map($table.bootstrapTable('getSelections'), function(
						row) {
					return row;
				});

				if (obj.length === 0) {
					layer.alert('请选择一条记录', {
						icon : 5
					});
				} else if (obj.length > 1) {
					layer.alert('只可选择一条记录', {
						icon : 5
					});
				} else {

					// 用户ID
					$('#userId').val(obj[0].id ? obj[0].id : null);
					$('#name').val(obj[0].name ? obj[0].name : null);
					$('#userName')
							.val(obj[0].userName ? obj[0].userName : null);
					$('#number').val(obj[0].number ? obj[0].number : null);
					$('.rolesLength').text(obj[0].roles.length);

					$('#login').attr('disabled', true);
					isLogin = obj[0].login;

					if (obj[0].login) {
						$('.loginInfoWrap').removeClass('hide');
						$('#login').attr("checked", false);
					} else {
						$('.loginInfoWrap').addClass('hide');
						$('#login').prop("checked", true)
						$('#login').attr('checked', true);
					}

					// 添加 已有权限列表
					$('#hasRoles a').remove();
					for (var i = 0; i < obj[0].roles.length; i++) {
						$hasRoles
								.append('<a href="javascript:;" class="list-group-item p-tb-xxs allRole-item" id="'
										+ obj[0].roles[i].id
										+ '" >'
										+ obj[0].roles[i].name + '</a>');
					}

					openModal('查看人员');
				}
			});

	/**
	 * 查询按钮点击事件
	 */
	$btnSearch.click(function() {
		isInit = true;
		isSearch = true;
		$table.bootstrapTable('refresh');
	});

	/**
	 * 重置密码按钮点击事件
	 */
	$resetPass.click(function() {

		updateObj = $.map($table.bootstrapTable('getSelections'),
				function(row) {
					return row;
				});

		if (updateObj.length === 0) {
			layer.alert('请选择一条记录', {
				icon : 5
			});
		} else if (updateObj.length > 1) {
			layer.alert('只可选择一条记录', {
				icon : 5
			});
		} else {
			layer.confirm('您确定要重置密码？<br/> 注：初始密码为123456', {
				btn : [ '是的', '取消' ]
			}, function(index) {
				layer.close(index);
				restAjax(globalPath + 'user/resetpassword.do', {
					userId : updateObj[0].id
				}, "GET", function(json) {
					if (json.code === 0) {
						layer.alert(json.msg, {
							icon : 6
						});
					} else {
						layer.alert(json.msg, {
							icon : 5
						});
					}
				}, null, "JSON", {
					btn : $resetPass
				});
			}, function() {
				layer.alert("取消密码重置操作！", {
					icon : 2
				});
			});
		}
	});

	// 提交
	$submit.click(function() {
		// 判断为创建人员
		if ($('#myModal .modal-title').text() === "创建人员") {
			var obj = {
				name : $('#name').val(),
				userName : isLogin ? $('#userName').val() : null,
				number : $('#number').val(),
				isLogin : !login.checked ? 1 : 0,
				roleIds : getRoles.length > 0 ? getRoles : []
			}

			// 验证通过
			if (validatorSave()) {
				restAjax(globalPath + 'user/adduser.do', obj, "POST", function(
						json) {
					if (json.code === 0) {
						$('#myModal').modal('hide');
						layer.alert(json.msg, {
							icon : 6
						});
						$table.bootstrapTable('refresh');
						isLogin = true;
						$('.loginInfoWrap').removeClass('hide');
						getRoles = [];
					} else {
						layer.alert(json.msg, {
							icon : 5
						});
					}
				}, null, "JSON", {
					btn : $submit
				});
			}
		}

		if ($('#myModal .modal-title').text() === "修改人员") {
			var obj = {
				userId : parseInt($('#userId').val()),
				name : $('#name').val(),
				userName : isLogin ? $('#userName').val() : null,
				number : $('#number').val(),
				isLogin : !login.checked ? 1 : 0,
				roleIds : getRoles.length > 0 ? getRoles : []
			}
			restAjax(globalPath + 'user/update.do', obj, "POST",
					function(json) {
						if (json.code === 0) {
							$('#myModal').modal('hide');
							layer.alert(json.msg, {
								icon : 6
							});
							$table.bootstrapTable('refresh');
						} else {
							layer.alert(json.msg, {
								icon : 5
							});
						}
					}, null, "JSON", {
						btn : $submit
					});
		}
	});

	/**
	 * 表格序号
	 */
	function addIndex(value, row, index) {
		var index = ++index;
		return index;
	}

	/**
	 * 格式化 登录数据方法
	 * 
	 * @param value
	 * @param row
	 * @param index
	 * @returns
	 */
	function formatLogin(value, row, index) {
		return row.login ? '是' : '否'
	}

	var tableObj = {
		table : $table,
		url : globalPath + 'user/list.do', // globalPath + '/user/list.do'
		queryParams : queryParams,
		responseHandler : responseHandler,
		columns : [ {
			field : 'state',
			checkbox : true
		}, {
			title : '序号',
			formatter : addIndex,
		}, {
			field : 'name',
			title : '姓名',
			sortable : true
		}, {
			field : 'number',
			title : '工号',
			sortable : true
		}, {
			field : 'login',
			title : '是否登录',
			sortable : true,
			formatter : formatLogin,
		}, {
			field : 'createrName',
			title : '操作员',
			sortable : true
		}, {
			field : 'createTime',
			title : '创建时间',
			sortable : true
		}, {
			field : 'updateTime',
			title : '更新时间',
			sortable : true
		} ]
	};

	tableService.init(tableObj);


	/**
	 * 获取回调数据
	 */
	function responseHandler(res) {
		if (isInit) {
			return {
				"list" : res.list,
				"total" : res.total
			};

		} else {
			return {
				"list" : [],
				"total" : 0
			};
		}
	}

	/**
	 * table传递的参数
	 * 
	 * @param params
	 * @returns
	 */
	function queryParams(params) {
		if (isInit) {
			if (isSearch) { // 点击搜索按钮
				isSearch = false;
				return {
					pageSize : params.limit,
					pageNo : 1,
					values : searchName
				}
			} else {// 翻页
				return {
					pageSize : params.limit,
					pageNo : params.pageNumber,
					param : null
				}
			}

		} else {
			return {
				pageSize : params.limit,
				pageNo : params.pageNumber
			}
		}
	}

	jQuery.validator.addMethod("isString", function(value, element) {
		var tel = /^[\u4e00-\u9fa5_a-zA-Z0-9]+$/;
		return this.optional(element) || (tel.test(value));
	}, "请输入正确字符");
	jQuery.validator.addMethod("isNum", function(value, element) {
		var tel = /^\w+$/;
		return this.optional(element) || (tel.test(value));
	}, "请输入正确的工号");

	/**
	 * 表单验证方法
	 */
	function validatorSave() {
		return $form.validate({
			rules : {
				"name" : {
					required : true
				},
				"number" : {
					required : true
				},
				"login" : {
					required : true
				}
			},
			messages : {
				"name" : {
					required : "请输入姓名"
				},
				"number" : {
					required : "请输入工号"
				},
				"login" : {
					required : "请选择是否登录"
				}
			}
		}).form();
	}

});