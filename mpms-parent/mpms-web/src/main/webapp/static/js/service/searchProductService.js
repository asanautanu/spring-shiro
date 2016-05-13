/**
 * 查询产品服务
 */
(function() {
	var searchProductService = {
		queryProduct : queryProduct
	}
	function queryProduct() {
		restAjax(globalPath + 'loanProduct/list.do', null, "GET", function(data) {
			if (data.code !== 0) {
				return;
			}
			createOption($("select[name='productId']"), data.data.list);
		}, null, "JSON", null, null, false);
		function createOption() {
			var target = arguments[0], list = arguments[1];
			for ( var a in list) {
					$(target).append("<option value='" + list[a]['id'] + "' id='" + list[a]['id'] + "'>" + list[a]['name']+'-'+list[a]['loanMonths'].value+ "</option>");
			}

		}
	}

	window.searchProductService = searchProductService;
}());