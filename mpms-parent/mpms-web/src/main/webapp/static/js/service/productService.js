/**
 * 查询产品服务
*/
;(function (){
	var productService = {
		createProduct: createProduct,
		queryProduct: queryProduct,
		queryAuthProduct: queryAuthProduct,
	}
	
	var resultProduct;
	restAjax(globalPath+'loanProduct/finds/institution.do', null, "GET", function (reps){
		if(reps.code !== 0){
			return;
		}
		resultProduct = reps.data.products;
	}, null, "JSON", null, null, false);
	
	var loanProductId;
	var loanMonths = [];
	
	$('#loanProductId').on('change', function (){
		if($('#loanProductId').val() === ''){
			$("#loanMonths option:first").siblings().remove();
			return;
		}
		loanProductkey = $('#loanProductId').val();
		$("#loanMonths").find('option').each(function(i, v){
			  $(this).remove();
		});
		// 循环期限
		
		$.each(resultProduct, function (key, value){
			loanMonths = value;
			
			if(loanProductkey === key){
				for(var i=0; i<loanMonths.length; i++){
					var months = loanMonths[i];
					if(i == 0){
						$('#interest').text(months.interest);
						$("#loanMonths").append("<option selected='selected' value='"+ months.id +"' name='"+ months.loanMonths.name + "'>"+ months.loanMonths.value +"</option>");
					} else {
						$("#loanMonths").append("<option value='"+ months.id +"' name='"+ months.loanMonths.name + "'>"+ months.loanMonths.value +"</option>");
					}
				}
			}
		});
	});
	
	function createProduct (selectVal){
		if(resultProduct){
			$.each(resultProduct, function (key, value){
				if (selectVal && selectVal === key) {
					$("#loanProductId").append("<option value='"+ key +"' selected='selected'>"+ key +"</option>");
					$("#loanProductId").val(key);
					$('#loanProductId').trigger("change");
				} else {
					$("#loanProductId").append("<option value='"+ key +"'>"+ key +"</option>");
				}
			});
		}
		
	}
	
	function queryProduct (){
		var loanProductId = arguments[0];
		var productName, product;
		var loanMonths = [];
		
		// 除0 后都删除，重新绑定数据
		$("#loanProductId,#loanMonths").find('option').each(function(i, v){
			  $(this).remove();
		});
		var isCurrentProduct = false;
		var isSelected = false;
		// 循环name
		$.each(resultProduct, function (key, value){
			if(!productName){
				productName = key;
			}
			if(!isCurrentProduct){
				for(var i=0; i<value.length; i++){
					var months = value[i];
					if(loanProductId == months.id){
						isCurrentProduct = true;
						loanMonths = value;
					}
				}
				
			}
			
			if(!isSelected && isCurrentProduct ){
				$("#loanProductId").append("<option selected='selected' value='"+ key +"'>"+ key +"</option>");
				isSelected = true;
			}else {
				$("#loanProductId").append("<option value='"+ key +"'>"+ key +"</option>");
			}
		});
		if(!isCurrentProduct){
			loanMonths = resultProduct[productName];
		}
		// 循环期限
		for(var i=0; i<loanMonths.length; i++){
			var months = loanMonths[i];
			if(loanProductId == months.id){
				$('#interest').text(months.interest);
				$("#loanMonths").append("<option selected='selected' value='"+ months.id +"' name='"+ months.loanMonths.name + "'>"+ months.loanMonths.value +"</option>");
			} else {
				$("#loanMonths").append("<option value='"+ months.id +"' name='"+ months.loanMonths.name + "'>"+ months.loanMonths.value +"</option>");
			}
		}
	}
	
	
	function queryAuthProduct (){
		
		var loanProductId = arguments[0];
		var productName, product;
		var loanMonths = [];
		
		restAjax(globalPath+'loanProduct/getAuthProduct.do', null, "GET", function (reps){
			if(reps.code !== 0){
				return;
			}
			resultProduct = reps.data.products;
		}, null, "JSON", null, null, false);
		
		// 除0 后都删除，重新绑定数据
		$("#loanProductId,#loanMonths").find('option').each(function(i, v){
			  $(this).remove();
		});
		var isCurrentProduct = false;
		var isSelected = false;
		// 循环name
		$.each(resultProduct, function (key, value){
			if(!productName){
				productName = key;
			}
			if(!isCurrentProduct){
				for(var i=0; i<value.length; i++){
					var months = value[i];
					if(loanProductId == months.id){
						isCurrentProduct = true;
						loanMonths = value;
					}
				}
				
			}
			
			if(!isSelected && isCurrentProduct ){
				$("#loanProductId").append("<option selected='selected' value='"+ key +"'>"+ key +"</option>");
				isSelected = true;
			}else {
				$("#loanProductId").append("<option value='"+ key +"'>"+ key +"</option>");
			}
		});
		if(!isCurrentProduct){
			loanMonths = resultProduct[productName];
		}
		// 循环期限
		for(var i=0; i<loanMonths.length; i++){
			var months = loanMonths[i];
			if(loanProductId == months.id){
				$('#interest').text(months.interest);
				$("#loanMonths").append("<option selected='selected' value='"+ months.id +"' name='"+ months.loanMonths.name + "'>"+ months.loanMonths.value +"</option>");
			} else {
				$("#loanMonths").append("<option value='"+ months.id +"' name='"+ months.loanMonths.name + "'>"+ months.loanMonths.value +"</option>");
			}
		}
	}
	
	
	window.productService = productService;
}());