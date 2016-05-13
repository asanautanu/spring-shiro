/**
 * select utils
 * @author Sunset
 * 
 */
$(function(){
	/**
	 * 生成多个select的option
	 * @param selectObj {} 选项对象 {"name" : "url"} 键值对 key : select元素名称，url : select请求的路径
	 * @param selectedValue 选中的值===需要回显的值
	 * @param optionValue 请求返回JSON数据中的key名，如product.value(赞支持.分割)，获取JSON中指定名称为optionValue的数据作为option的Value
	 * @param optionText 请求返回JSON数据中的key名，如product.name(赞支持.分割)，获取JSON中指定名称为optionText的数据作为option的Text
	 * 
	 */
	window.generateOptions = function (selectObj, selectedValue, optionValue, optionText) {
		for ( var k in selectObj) {
			generateOption(selectObj[k], $("select[name='" + k + "']"), selectedValue, optionValue, optionText);
		}
	};
	
	/**
	 * 生产单个select的option
	 * @param url 请求地址（必填）
	 * @param ele select对象（必填）
	 * @param val 回显的值
	 * @param optionVal option对于的value值
	 * @param optionText option对于的text值
	 * @param key {"code":0,"msg":"查询成功","data":{"list":[{ 对应list 
	 * @param data 
	 * @param func
	 * @param disable : 是否禁用请选择选项
	 */
	window.generateOption = function (url, ele, val, optionVal, optionText, key, data, func, disable) {
		if (url && ele) {
			restAjax(url, data, "get", function(data) {
				if (data) {
					if (data.code === 0) {
						key = key || "list";
						optionVal = optionVal || "id";
						optionText = optionText || "name";
						// 如果元素下已经包含option选项
						ele.empty();
						var _options;
						_options = !disable ? "<option value=''>--请选择--</option>" : "";
						if (data.data[key] && data.data[key].length > 0) {
							data = data.data[key];
							$.each(data, function(index, option) {
								if (option) {
									var _text = getJSONvalue(option, optionText);
									var _value = getJSONvalue(option, optionVal);
									// 处理回显选中
									if (val && val === _value) {
										_options += "<option value='" + _value + "' selected='selected' >" + _text + "</option>";
										ele.val(_value);
									} else {
										_options += "<option value='" + _value + "'>" + _text + "</option>";
									}
								}
							});
							callback(func);
						}
						ele.append(_options);
					} else {
						layer.alert(data.msg);
					}
				} else {
					layer.alert("系统异常！");
				}
			});
		} else {
			callback(func);
			console.log("The warning ocurred while generating options and the message is : parameters were missed");
		}
	};
	
	/**
	 * select 联动查询,将ele的值作为参数请求服务器生成targetEle的option选项,暂时支持一级联动
	 * @param obj {}（必填）
	 * @param obj.ele 当前select对象,父级select对象（必填）
	 * @param obj.targetEle 目标select对象，子级select对象（必填）
	 * @param obj.targetUrl 目标请求地址（必填）
	 * @param obj.param obj.data对于的key, 即父级select的值在目标请求URL中对应的参数名,不传时默认用ele名称作为参数名
	 * @param obj.selectVal 目标回显值
	 * @param obj.optionVal 生成目标select下option value的key
	 * @param obj.optionText 生成目标select下option text的key
	 * @param obj.data 目标请求URL的参数对象
	 * @param obj.key 解析响应数据列表中对于的Key名称
	 * @param obj.callback 回调函数
	 */
	window.selectChange = function(obj) {
		if (obj && typeof obj === "object") {
			if (obj.ele && obj.targetEle && obj.targetUrl) {
				obj["param"] = obj["param"] || obj.ele.attr("name");
				obj.data = obj.data || {};
				obj.ele.on("change", function(e){
					var v = $(this).val();
					if (v) {
						// 处理参数
						obj["data"][obj.param] = v;
						generateOption(obj.targetUrl, obj.targetEle, obj.selectVal, obj.optionVal, obj.optionText, obj.key, obj.data);
					} else {
						obj.targetEle.empty().append("<option value=''>--请选择--</option>");
					}
				});
				// 回显值
				if (obj.selectVal) {
					generateOption(obj.targetUrl, obj.targetEle, obj.selectVal, obj.optionVal, obj.optionText, obj.key, obj.data);
				}
			}
		}
		callback(obj.callback);
	};
	/**
	 * select zTree关闭时联动请求服务端生成下一级select的option
	 * @param obj [] || {}（必填）{}对象为单个，[]数组时为多个
	 * @param obj.zTreeId zTreeId（必填）
	 * @param obj.targetEle 目标select对象，子级select对象（必填）
	 * @param obj.targetUrl 目标请求地址（必填）
	 * @param obj.selectVal 目标回显值
	 * @param obj.param obj.data对于的key, 即父级Ztree的值在目标请求URL中对应的参数名,不传时默认用ZTree名称作为参数名
	 * @param obj.optionVal 生成目标select下option value的key
	 * @param obj.optionText 生成目标select下option text的key
	 * @param obj.data 目标请求URL的参数对象
	 * @param obj.key 解析响应数据列表中对于的Key名称
	 * @param obj.callback 回调函数
	 *   
	 */
	window.zTreeClose = function (obj) {
		if (obj) {
			if (obj.constructor == Array) {
				$.each(obj, function(i, o){
					handleOption(o);
				});
			} else if (typeof obj === "object") {
				handleOption(obj);
			}
		}
	};
	
	
	function handleOption(obj) {
		if (obj && obj.zTreeId && obj.targetUrl && obj.targetEle) {
			//depend on zTree
			var treeObj = $.fn.zTree.getZTreeObj(obj.zTreeId);
			// 获取zTree 选中的值
			var nodes = treeObj.getCheckedNodes(true);
			if (nodes && nodes.length > 0) {
				if (obj.param) {
					var data = [];
					$.each(nodes, function(i, n){
						data.push(n.id);
					});
					// TODO obj.param 如果和obj.data里面重复会覆盖掉原始参数
					obj["data"][obj.param] = data;
				}
				generateOption(obj.targetUrl, obj.targetEle, obj.selectVal, obj.optionVal, obj.optionText, obj.key, obj.data);
			} else {
				obj.targetEle.empty().append("<option value=''>--请选择--</option>");
			}
		}
		callback(obj.callback);
	}
	
	
	/**
	 * 下载 jQuery.download 可以扩展为JQuery的函数
	 * @param url 
	 * @param data "param1=2&param2=2" || {"ids" : ["1", "2"] , "name" : "测试"}
	 * @param type POST || GET 
	 */
	window.download = function(url, data, type) {
		if (url) {
			type = type || "POST";
			var iframe = $("#download_frame");
			iframe = iframe.length > 0 ? iframe : $("<iframe name='download_frame' style='display:none;' id='download_frame' />");
			$('body').append(iframe);
	        var form = $("<form/>");
	        form.attr('target', 'download_frame');// 指向iframe
	        form.attr('method', type);
	        form.attr('action', url);
	        iframe.append(form);
	        if (data) {
	        	data = typeof data == 'string' ? data : decodeURIComponent($.param(data));// 把参数组装成 form的  input
		        $.each(data.split('&'), function(i, d){ 
		            var splits = d.split('=');
		            form.append('<input type="hidden" name="'+ splits[0] +'" value="'+ splits[1] +'" />');
		        });
	        }
	        form.submit();
	        form.remove();
	        iframe.get(0).onload = function() {
				var response, responseStr = iframe.contents().text();
				try {
					response = JSON.parse(responseStr);
				} catch (e) {
					response = responseStr;
				}
				if (response) {
					layer.alert(response.msg);
				}
			};
		}
	};
	
	/**
	 * 验证表格是否选中并返回数据{} || []
	 * @param action 动作
	 * @param table bootstrapTable 对象
	 * @param multiSelect 是否多选 true || false 
	 */
	window.returnSelections = function (action, table, multiSelect) {
		if (action && table) {
			// 获取表格选中的数据
			var rows = table.bootstrapTable('getSelections');
			// 允许多选
			if (multiSelect) {
				if (!rows || rows.length <= 0) {
					layer.alert("请选择需要" + action + "的数据！");
				} else {
					return rows;
				}
			} else {
				if (!rows || rows.length <= 0) {
					layer.alert("请选择需要" + action + "的数据！");
				} else if (rows.length > 1) {
					layer.alert("一次只能" + action + "一条数据！");
				} else {
					return rows[0];
				}
			}
		}
		return null;
	};
	
	/**
	 * 打开弹出层
	 * @param ele弹出层对象
	 * @param title 弹出层的标题
	 * @param func 回调函数
	 * @param form 表单对象
	 */
	window.openModal = function (ele, title, form, func) {
		title = title || "";
		if (ele) {
			// 禁用 BootStrap Modal 点击空白时自动关闭
			ele.modal({
				backdrop : 'static',
				keyboard : false
			});
			if (form) {
				form[0].reset();// reset form
				$("input[type='hidden']", form).val('');// reset input[type='hidden']
			}
			$(".modal-title", ele).text(title);// set modal's title
		}
		callback(func);
	};
	
	/**
	 * 关闭弹出层
	 * @param ele弹出层对象
	 * @param func 回调函数
	 * @param form 表单对象
	 */
	window.closeModal = function (ele, form, func) {
		if (ele) {
			ele.modal('hide');
			if (form) {
				form[0].reset();// reset form
				$("input[type='hidden']", form).val('');// reset input[type='hidden']
			}
			$(".modal-title", ele).text("");// set modal's title
		}
		callback(func);
	};
	
	
	/**
	 * 获取JSON对象的属性值
	 * @param json 
	 * @param key key的命名方式 eg : product.name
	 */
	window.getJSONvalue = function(json, key) {
		if (json && key) {
			var _ks = key.split(".");
			if (_ks && _ks.length > 0) {
					var _k = _ks[0], _co = json[_k], begin = _k.indexOf("["), _length = _k.length;
					// check '_k' is really Array
					if (begin != -1 && _k.charAt(_length - 1) === "]") {
						var i = _k.slice(begin + 1, _length - 1);//get index eg : 'contacts[1]' we got 1
						_k = _k.slice(0, begin);//get key 'eg:contacts[1]' we got 'contacts'
						if (json[_k]) {// check json[_k] is already exist
							_co = json[_k][i];
							// 4 = .[0] 4 '.[0]'
							var index = key.indexOf(_k) + _k.length + 4;
							return getJSONvalue(_co, key.slice(index));
						} else {
							return "";
						}
					} else {
						if (typeof _co === "object") {
							// get next property's name if json[_k] is object
							// get next property'name eg : contacts.name
							var index = key.indexOf(_k) + _k.length + 1;// there got name's index
							return getJSONvalue(_co, key.slice(index));// there send object '_co' and 'index' to function
						} else if (typeof _co === "string" || typeof _co === "number") {
							return _co;
						}
					}
			} else {
				return json[key];
			}
		} else {
			console.log("The warning ocurred while getting getJSONvalue and the message is : parameters were missed");
		} 
	};
	
	/**
	 * 获取URL或者当前window的URL的对应的值
	 * @param key (必填)
	 * @param url 
	 * 
	 */
	window.getValueFromUrl = function(key, url) {
		if (key) {
			// ?A=B
			if (url) {
				url = url.slice(url.indexOf("?"));
			} else {
				url = location.search;
			}
			// ?A= || ?A=B
			if (url && url.length >= 3) {
				url = url.slice(1);
				var paramters = url.split("&");
				var paramObj = {};
				for ( var i in paramters ) {
					var param = paramters[i];
					var ar = param.split("=");
					var k = ar[0];
					var v = ar[1];
					// 判断参数是否是数组
					if (k.indexOf("[]") != -1) {
						// 判断是否存在key
						k = k.replace("[]", "");
						if (paramObj[k]) {
							paramObj[k].push(v);
						} else {
							paramObj[k] = [v];
						}
					} else {
						paramObj[k] = v;
					}
				}
				return paramObj[key];
			} else {
				return "";
			}
		}
	};
	
	/**
	 * 表单回填数据
	 * @param form表单对象
	 * @data 回填JSON数据对象
	 * @param 回调函数
	 */
	window.setFormValue = function(form, data, func) {
		if (form && data && typeof data === "object") {
			$("input,select,textarea", form).each(function(i, ele) {
				// input 分为三种 普通input, radio, checkbox
				var name = ele.name;
				ele = $(ele);
				if (ele.is("input[type='text']") || ele.is("input[type='hidden']") || ele.is("textarea")) {
					ele.val(data[name]);
				} else if (ele.is("input[type='radio']")) {
					$("input[name='" + name + "'][value='" + data[name] + "']").prop("checked", true);
				} else if (ele.is("input[type='checkbox']")) {
					// 返回来[a,b,c];
					if (data[name].constructor === Array) {
						for ( var n in data[name] ) {
							$("input[name='" + name + "'][value='" + data[name][n] + "']").prop("checked", true);
						}
					} else {
						$("input[name='" + name + "'][value='" + data[name] + "']").prop("checked", true);
					}
				} else if (ele.is("select")) {
					ele.val(data[name]);
					$("select[name='" + name + "'] > option[value='" + data[name] + "']").prop("selected", true);
				}
			});
		} else {
			console.log("The error ocurred while setting form value and the message is : parameters were missed");
		}
		callback(func);
	};
	
	/**
	 * 获取表单的JSON对象字符串
	 * @param form
	 * 
	 */
	window.getFormJsonString = function(form) {
		var formJson = {};
		if (form) {
			$("input,select,textarea", form).each(function(i, ele){
				var name = ele.name || ele.id;
				ele = $(ele);
				if (ele.is("input[type='text']") || ele.is("input[type='hidden']") || ele.is("textarea")) {
					formJson[name] = ele.val();
				} else if (ele.is("input[type='radio']")) { 
					if (ele.is(":checked")) {
						formJson[name] = ele.val();
					}
				} else if (ele.is("input[type='checkbox']")) {
					if (ele.is(":checked")) {
						if (formJson[name]) {
							formJson[name].push(ele.val());
						} else {
							formJson[name] = [ele.val];
						}
					}
				} else if (ele.is("select")) {
					formJson[name] = $("select[name='" + name + "']", form).val();
				}
			});
		} else {
			console.log("The error occured while getFromJsonString and the message is : parameter form was empty!");
		}
		return formJson;
	};
	
	/**
	 * 回调函数
	 */
	window.callback = function(func) {
		if (func && typeof func === "function") {
			func();
		}
	};
	
	/**
	 * 格式化long类型的值为时间周期，得到的格式如：01:23:32(一小时23分钟32秒)
	 * @param millisecond
	 * 
	 */
	window.formatLong2seconds = function(millisecond){
		if (millisecond) {
			var hour = 60 * 60 * 1000, minute = 60 * 1000, second = 1000, hours = "00", minutes = "00", seconds = 0, result;
			if (millisecond > hour) {//HH
				hours = millisecond / hour;
				hours = coverNum(parseInt(hours));
				minutes = millisecond % hour / minute;
				minutes = coverNum(parseInt(minutes));
				seconds = millisecond % hour % minute / second;
			} else if (millisecond > minute) {//mm
				minutes = millisecond / minute;
				minutes = coverNum(parseInt(minutes));
				seconds = millisecond % minute / second;
			} else if (millisecond > second) {//ss
				seconds = millisecond / second;
			} else if (millisecond < 1000) {
				seconds = 01;
			}
			seconds = coverNum(parseInt(seconds));
			result = hours ? hours + ":" : hours;
			result += minutes ? minutes + ":" : minutes;
			result += seconds;
			return result;
		}
	};
	
	/**
	 * 验证一个数，如果该数小于10则补十位为0
	 */
	function coverNum(num){
		if (num === 0 || num < 10) {
			num = "0" + num;
		}
		return num;
	};
	
	/**
	 * 获取当前月份最后一天的日期
	 */
	window.getEndDate = function() {
		var d = new Date();
		var year = d.getFullYear();
		var month = d.getMonth() + 1;
		var day = new Date(year, month, 0).getDate();
		month = coverNum(month);
		return year + "-" + month + "-" + day;
	};
	
	/**
	 * 获取当前月份的前几个月份的第一天的日期
	 */
	window.getBeforDate = function(m) {
		try {
			if (m) {
				var d = new Date();
				var month = d.getMonth();
				m = month - m;
				d.setMonth(m);
				m = d.getMonth() + 1;
				m = coverNum(m);
				return d.getFullYear() + "-" + m + "-01";
			}
		} catch (e) {
			console.log("The error occured while getBeforDate and the message is : parameter is not Number!");
		}
	};
	
	/**
	 * 格式化时间
	 * 返回时间格式如：2016-04-11
	 */
	window.getFormatDate = function(date) {
		return date ? date.substring(0, 10) : "";
	};
});

/**
 * 验证汉化
 */
jQuery.extend(jQuery.validator.messages, {  
    required: "必选字段",  
	remote: "请修正该字段",  
	email: "请输入正确格式的电子邮件",  
	url: "请输入合法的网址",  
	date: "请输入合法的日期",  
	dateISO: "请输入合法的日期 (ISO).",  
	number: "请输入合法的数字",  
	digits: "只能输入整数",  
	creditcard: "请输入合法的信用卡号",  
	equalTo: "请再次输入相同的值",  
	accept: "请输入拥有合法后缀名的字符串",  
	maxlength: jQuery.validator.format("请输入一个长度最多是 {0} 的字符串"),  
	minlength: jQuery.validator.format("请输入一个长度最少是 {0} 的字符串"),  
	rangelength: jQuery.validator.format("请输入一个长度介于 {0} 和 {1} 之间的字符串"),  
	range: jQuery.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),  
	max: jQuery.validator.format("请输入一个最大为 {0} 的值"),  
	min: jQuery.validator.format("请输入一个最小为 {0} 的值")  
});

//金额验证
jQuery.validator.addMethod("money", function(value, element) {
	var money = /(^[1-9]\d*(\.\d{1,2})?$)|(^[0]{1}(\.\d{1,2})?$)/;
	return this.optional(element) || (money.test(value));
}, "请输入正确的金额");

//对Date的扩展，将 Date 转化为指定格式的String
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
//例子：
//(new Date()).format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
//(new Date()).format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
		// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for (var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
};