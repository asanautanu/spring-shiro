$(function() {
	// 定义变量
	var $table, $btnCreate = $('#create'), // 创建按钮
	$btnSave = $("#btnSave"), // 保存按钮
	$btnCancle = $("#btnCancle"), // 
	$btnUpdate = $('#update'), // 修改按钮
	$btnRemove = $('#remove'), // 删除按钮
	$btnRetrieve = $('#retrieve'), // 查看按钮
	$btnOperate = $('#operate'), // 操作按钮
	$btnSearch = $('#search'), // 搜索按钮
	$form = $('#form1'), // 表单
	url = globalPath + "loanProduct/", options = {// option 请求地址和select元素
		"loanInterestType" : url + "enableLoanInterestType.do",
		"loanMonths" : url + "enableMonthsType.do",
		"loanRepayDayType" : url + "enableRepayDayType.do",
		"loanFirstInterestType" : url + "enableFirstInterestType.do",
		"loanServiceFeeType" : url + "enableServiceFeeType.do"
	};

	// 生产下拉选项的值
	generateOptions(options, null, "name", "value");

	/**
	 * 初始化多选下拉选项
	 */
	$('.chzn-select').chosen({
		"disable_search" : true,// 禁用搜索按钮
		"width" : "300px"// 宽度
		/*"no_results_text" : null*/
	});
	/**
	 * 清空选项
	 */
	// 只能设置单个值
	/*$('.chzn-select').val('C');*/
	$('.chzn-select > option[value="A"]').attr("selected", true)
	$('.chzn-select > option[value="B"]').attr("selected", true)
	/**
	 * 设置值后处罚一次
	 */
	$(".chzn-select").trigger("chosen:updated");
	/*$("ul.chosen-choices > li.search-field > input").val('');*/
	/**
	 * 注销多选下拉选项
	$(".chzn-select").chosen("destroy");
	*/
	
	/**
	 * 时间控件初始化
	 */
	$(".input-daterange").datepicker({
		keyboardNavigation : !1,
		forceParse : !1,
		autoclose : !0
	});
	$("#datepicker").datepicker({
		startView : 1,
		todayBtn : "linked",
		keyboardNavigation : !1,
		forceParse : !1,
		calendarWeeks : !0,
		autoclose : !0
	});

	/**
	 * 点击operate按钮 弹出询问框
	 */
	$btnOperate.click(function() {
		layer.confirm('是否执行此操作？', {
			btn : [ '是', '否' ]
		// 按钮文字
		}, function() {
			layer.msg('成功！', {
				icon : 1
			});
		}, function() {
			layer.msg('取消！', {
				icon : 2
			});
		});
	});

	/**
	 * 初始化弹出层
	 */
	function openModal(title) {
		// 禁用 BootStrap Modal 点击空白时自动关闭
		$('#myModal').modal({
			backdrop : 'static',
			keyboard : false
		})
		$form[0].reset();
		$("#myModal .modal-title").text(title);
	}

	/**
	 * 创建按钮点击事件
	 */
	$btnCreate.click(function(e) {
		openModal("新增");
	});

	/**
	 * 修改按钮点击事件
	 */
	$btnUpdate.click(function(e) {
		openModal("修改");

	});

	/**
	 * 删除按钮点击事件
	 */
	$btnRemove.click(function(e) {
		// 获取选table中值
		alert('getSelections: '
				+ JSON.stringify($table.bootstrapTable('getSelections')));
	});

	/**
	 * 查看按钮点击事件
	 */
	$btnRetrieve.click(function(e) {
		openModal("查看");
		// 获取选table中值
		JSON.stringify($table.bootstrapTable('getSelections'));
	})

	/**
	 * 保存按钮点击事件
	 */
	$btnSave.click(function(e) {
		console.log($("#agreementType").val())
		if (validatorSave()) {
			if ($.trim($("#id").val())) {
				$form
						.attr("action", globalPath
								+ "agreementTemplate/update.do");
			} else {
				$form.attr("action", globalPath + "agreementTemplate/add.do");
			}
			$form.submit();
		}
	});

	/**
	 * 表单提交事件
	 */
	ajaxForm($form, null, function(data) {
		if (data.code == "0") {
			$form[0].reset();
			$btnCancle.click();
			$table.bootstrapTable("refresh");
		}
		layer.alert(data.msg);
	},
	function(data) {
		layer.alert(data.msg);
	}, {btn : $btnSave});

	var tableObj = {
		url : globalPath + 'user/list.do',
		queryParams : queryParams,
		responseHandler : responseHandler,
		columns : [ {
			field : 'state',
			checkbox : true
		// 显示 checkbox
		}, {
			title : '序号',
			formatter : addIndex, // 序号
		}, {
			field : 'userName', // 显示属性key
			title : '合同名称',
			sortable : true
		}, {
			field : 'name',
			title : '录入员',
			sortable : true
		} ]
	};
	
	$table = tableService.init(tableObj);
	
	/**
	 * 初始化表格
	 */
	/*$table = $('#table').bootstrapTable({
		url : globalPath + 'user/list.do', // 请求地址
		method : 'post', // 请求方式
		contentType : 'application/x-www-form-urlencoded',
		dataField : "list", // 获取数据key对应名称
		showRefresh : true, // 是否显示刷新
		sidePagination : "server", // 是否使用服务器分页
		toolbar : "#toolbar", // 操作按钮层 对应id为 toolbar 的div
		pagination : true,
		pageSize : 10,
		pageNumber : 1,
		queryParams : queryParams,
		striped : true,
		responseHandler : responseHandler,
		columns : [ {
			field : 'state',
			checkbox : true
		// 显示 checkbox
		}, {
			title : '序号',
			formatter : addIndex, // 序号
		}, {
			field : 'userName', // 显示属性key
			title : '合同名称',
			sortable : true
		}, {
			field : 'name',
			title : '录入员',
			sortable : true
		} ],
		onLoadSuccess : function(data) {
			console.log("--success") // 获取数据成功
		},
		onLoadError : function(data) {
			console.log('--error'); // 获取数据失败
		}
	});*/

	var isInit = false;
	var isSearch = false;

	/**
	 * 搜索按钮
	 */
	$btnSearch.click(function() {
		isInit = true;
		isSearch = true;
		$table.bootstrapTable('refresh');
	});

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

	// table传递的参数
	function queryParams(params) {
		if (isInit) {
			if (isSearch) {// 点击搜索按钮
				isSearch = false;
				return {
					pageSize : params.limit,
					pageNo : 1,
					param1 : $('#name1').val(),
					institurionId : 26
				}
			} else {// 翻页

				return {
					pageSize : params.limit,
					pageNo : params.pageNumber,
					param : null,
					institurionId : null
				}

			}

		} else {
			return {
				pageSize : params.limit,
				pageNo : params.pageNumber
			}
		}
	}
	;

	/**
	 * 列表 序号方法
	 */
	function addIndex(value, row, index) {
		return index += 1;
	}

	/**
	 * 表单验证方法
	 */
	function validatorSave() {
		return $form.validate({
			rules : {
				"name" : {
					required : true
				},
				"agreementType" : {
					required : true
				}
			},
			messages : {
				"name" : {
					required : "请输入合同名称"
				},
				"agreementType" : {
					required : "请选择合同类型"
				}
			}
		}).form();
	}

});
