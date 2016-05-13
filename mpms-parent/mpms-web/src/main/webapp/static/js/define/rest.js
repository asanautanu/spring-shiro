/**
 * set global property
 */
$.ajaxSetup({
	timeout : 30000
});

jQuery.support.cors = true;

/**
 * @param url 请求地址
 * @param data 请求参数
 * @param type 请求类型 post,delete,put,get and the default value is "post"
 * @param dataType 返回数据类型 xml,html,script,json,jsonp,text and the default value is "json"
 * @param success 成功回调函数
 * @param error 错误回调函数
 * @param dataType 
 * @param setting 操作对象 {
 * 		disBtn : true, default true
 * 		btn : $("#btn"),
 * 		mask : true, default true
 * 		maskIndex : 遮罩层索引
 * 		}
 * @param contentType default: 'application/x-www-form-urlencoded; charset=UTF-8'
 * @param async default : true
 */
var restAjax = function(url, data, type, success, error, dataType, setting, contentType, async) {
	if (url && success) {
		$.ajax({
			url : url,
			type : getType(type),
//			traditional : true,
			dataType : getDataType(dataType),
			data : getData(data),
			contentType : getContentType(contentType),
			async: getAsync(async),
			beforeSend : function( jqXHR, settings ) {
				handleBtn(setting, true);
				handleMask(setting, true);
			},
			success : function(data, textStatus, jqXHR) {
				handleSuccess(success, data, jqXHR, textStatus, dataType, setting);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				handleError(error, jqXHR, textStatus, errorThrown, setting);
			}
		});
	} else {
		layer.alert("参数错误！");
	}
};

/**
 * @param url 请求地址
 * @param data 请求参数
 * @param form 请求表单
 * @param type 请求类型 post,delete,put,get and the default value is "post" 
 * @param dataType 返回数据类型 xml,html,script,json,jsonp,text and the default value is "json"
 * @param success 成功回调函数
 * @param error 错误回调函数
 * @param setting {
 *      disBtn : true, default true
 * 		btn : $("#btn"),
 * 		mask : true, default true
 * 		maskIndex : 遮罩层索引 
 * }
 */
var restAjaxSubmit = function(url, form, data, type, success, error, setting) {
	if (url && form && success) {
		form.ajaxSubmit({
			url : url,
			type : getType(type),
			dataType : getDataType(dataType),
			data : getData(data),
			beforeSend : function( jqXHR, settings ) {
				handleBtn(setting, true);
				handleMask(setting, true);
			},
			success : function(data, textStatus, jqXHR) {
				handleSuccess(success, data, jqXHR, textStatus, "JSON", setting);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				handleError(error, jqXHR, textStatus, errorThrown, setting);
			}
		});
	} else {
		layer.alert("参数错误！");
	}
};

/**
 * 
 * @param form
 * @param dataType
 * @param success
 * @param error
 * @param setting {
 *      disBtn : true, default true
 * 		btn : $("#btn"),
 * 		mask : true, default true
 * 		maskIndex : 遮罩层索引 
 * }
 */
var ajaxForm = function(form, dataType, success, error, setting) {
	if (form && success) {
		form.ajaxForm({
			dataType : getDataType(dataType),
			beforeSubmit : function(formData, jqForm, options) {
				handleBtn(setting, true);
				handleMask(setting, true);
			},
			success : function(data, textStatus, jqXHR) {
				handleSuccess(success, data, jqXHR, textStatus, "JSON", setting);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				handleError(error, jqXHR, textStatus, errorThrown, setting);
			}
		});
	}
};

/**
 * 处理dataType
 * @param dataType
 * 
 */
function getDataType(dataType) {
	return dataType || "json";
}

/**
 * 处理data
 * @param data
 */
function getData(data) {
	return data || {};
}

/**
 * 处理type
 * @param type
 */
function getType(type) {
	return type || "POST";
}

/**
 * 处理contentType
 * @param contentType
 * @returns {String}
 */
function getContentType(contentType) {
	return contentType || "application/x-www-form-urlencoded; charset=UTF-8";
}

/**
 * 处理async
 * @param async
 * @returns {Boolean}
 */
function getAsync(async) {
	async = async === false ? async : true;
	return async;
}

/**
 * 处理按钮业务
 * @param setting {
 *      disBtn : true, default true
 * 		btn : $("#btn"),
 * 		mask : true, default true
 * 		maskIndex : 遮罩层索引 
 * }
 * @param disabled 是否禁用 
 */
function handleBtn(setting, disabled) {
	if (setting) {
		if (disabled) {
			setting.btn.prop("disabled", true);
			setting.btn.addClass("disabled");
		} else {
			setting.btn.prop("disabled", false);
			setting.btn.removeClass("disabled");
		}
	}
}

/**
 * 处理遮罩层业务
 * @param setting {
 *      disBtn : true, default true
 * 		btn : $("#btn"),
 * 		mask : true, default true
 * 		maskIndex : 遮罩层索引 
 * }
 * @param show 是否展示
 * 
 */
function handleMask(setting, show) {
	if (setting) {
		if (show) {
			setting.maskIndex = layer.load(); 
		} else {
			layer.close(setting.maskIndex);
		}
	}
}

/**
 * 错误处理函数
 * @param error 错误回调函数
 * @param jqXHR
 * @param textStatus
 * @param errorThrown
 * @param setting {
 *      disBtn : true, default true
 * 		btn : $("#btn"),
 * 		mask : true, default true
 * 		maskIndex : 遮罩层索引 
 * }
 */
function handleError(error, jqXHR, textStatus, errorThrown, setting) {
	
	handleBtn(setting, false);
	handleMask(setting, false);
	
	if (jqXHR) {
		switch (jqXHR.status) {
		case 0:
			
			layer.alert("请求超时！");
			break;
		
		case 400:
			
			layer.alert("错误的请求！");
			break;
			
		case 401:
			
			handleTimeout();
			break;
		
		case 404:
			
			layer.alert("错误的请求路径！");
			break;

		case 405:
			
			layer.alert("请求类型不被允许！");
			break;
		
		case 408:
			
			layer.alert("请求超时！");
			break;
			
		case 500:
			
			layer.alert("服务器异常！");
			break;
			
		default:
			
			if (error && typeof error === "function") {
				error(jqXHR, textStatus, errorThrown);
			} else {
				layer.alert("系统异常,请与管理员联系！");
			}
			break;
		}
	} else {
		layer.alert("参数错误!");
	}
}

/**
 * 成功处理函数
 * @param success 回调函数
 * @param data 数据
 * @param jqXHR
 * @param textStatus
 * @param dataType
 * @param setting {
 *      disBtn : true, default true
 * 		btn : $("#btn"),
 * 		mask : true, default true
 * 		maskIndex : 遮罩层索引 
 * }
 */
function handleSuccess(success, data, jqXHR, textStatus, dataType, setting) {
	
	handleBtn(setting, false);
	handleMask(setting, false);
	
	if (data) {
		
		if (dataType && dataType.toLowerCase() !== "json" ) {
			success(data);
			return ;
		}
		
		switch (data.code) {
		case 0:
			
			success(data);
			break;

		case 1000:
			
			/*layer.alert("服务异常！");*/
			success(data);
			break;
		
		case 4000:
			
			/*layer.alert("参数错误！");*/
			success(data);
			break;
		
		case 401:
			
			handleTimeout();
			break;
			
		case 50000:
			
			layer.alert("您没有当前操作的权限！");
			sessionStorage.permiessions = "";
			break;
			
		default:
			layer.alert(data.msg || "系统错误！");
			break;
		}
	} else {
		layer.alert("系统异常!");
	}
}


function handleTimeout() {
	layer.alert("会话失效，请重新登录！");
	sessionStorage.permiessions = "";
	window.top.location.href = globalPath + "admin/login.do"; 
}
