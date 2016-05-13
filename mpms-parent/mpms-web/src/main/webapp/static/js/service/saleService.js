/**
 * 销售服务
 */
(function(){
	var saleService = {
		createSale: createSale
	}
	
	function createSale (){
		var institurionId = arguments[0];
		restAjax(globalPath + 'user/getMarketUser.do', {institurionId: institurionId}, "GET", function (sale){
			var list = sale.data.list;
			
			// 除0 后都删除，重新绑定数据
			$('#salerId').find('option').each(function(i, v){
				  if(i >= 1){
					  $(this).remove();
				  }
			});
			
			for(var i=0;i<list.length; i++){
				$('#salerId').append("<option selected='selected' value='"+ list[i].id + "'>"+ list[i].name +"</option>");
			}
		});
	}
	
	window.saleService = saleService;
}());