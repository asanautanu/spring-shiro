
	/**
	 * 创建省 select
	 */
	
	(function (){
		
		var addressService = {
			queryAddress: queryAddress,
			createAddress: createAddress
		};		
		
		
		/**
		 * 创建地址
		 */
		function createAddress(){
			restAjax(globalPath +'address/findByParent.do', null, "POST", function (data){
				if(data.code !== 0){
					return;
				}
				
				createOption($("select[name='province']"), data.data.list);
			});
		}
		
		
		/**
		 * 监听省
		 */
		$("select[name='province']").on('change', function(){
			var target = '#' + $(this).siblings("[name='city']").attr('id');
            var targetDistrict = '#' + $(this).siblings("[name='district']").attr('id');
			
            if($(this).val() === ''){
            	alert('请选择省');
            	$(target).children('option').eq(0).attr('selected', true);
                $(targetDistrict).children('option').eq(0).attr('selected', true);
                return;
            }      
            
            var pid = $(this).find('option:selected').attr('id');
            switchOption(target, pid, 'city');
        });
		
		/**
		 * 监听市
		 */
		$("select[name='city']").on('change', function() {
			var target = '#' + $(this).siblings("[name='district']").attr('id');
			if($(this).val() === ''){
            	alert('请选择市');
            	$(target).children('option').eq(0).attr('selected', true);
                return;
            }
			
            var pid = $(this).find('option:selected').attr('id');
            switchOption(target, pid, 'district');
        });
		
		/**
		 * 监听区
		 */
		$("select[name='district']").on('change', function() {
			if($(this).val() === ''){
            	alert('请选择区');
            	$(this).children('option').eq(0).attr('selected', true);
                return;
            }
        });
		
		
		/**
		 * 查询省
		 */
		function queryAddress (){
			var data = {id: arguments[0]?arguments[0]:null},
				pSelect = $(arguments[1]),
			    cSelect = "#" + $(arguments[1]).siblings("[name='city']").attr('id'),
			    rSelect = "#" +  $(arguments[1]).siblings("[name='district']").attr('id');
			restAjax(globalPath +'address/getDisplayAddress.do', data, "POST", function (data){
				if(data.code !== 0){
					return;
				}
				var address = data.data['address'],
				province = data.data['province'],
					city =	data.data['city'],
				  region = data.data['region'];
		
				createOption(pSelect, province, address?address.province:"", 'province');
				createOption(cSelect, city, address?address.city:"", 'city');
				createOption(rSelect, region, address?address.region:"", 'region');
			});
		}
		
		
		/**
		 * 创建Option
		 */
		function createOption (){
			var target = arguments[0], 
				list = arguments[1], 
				judge,
				type;
			
			if(arguments.length === 2){
				type = 'province';
			}
			if(arguments.length === 3){
				judge = arguments[2];
			}
			if(arguments.length === 4){
				judge = arguments[2];
				type = arguments[3];
			}
			if(list==undefined){
				return;
			}
			
			if(judge){
				// 除0 后都删除，重新绑定数据
				$(target).find('option').each(function(i, v){
					  if(i >= 1){
						  $(this).remove();
					  }
				});
				
				for(var i=0;i<list.length;i++){
					if(list[i][type] == judge){
						$(target).append("<option selected='selected' value='"+ list[i][type] +"' id='"+ list[i].id + "'>"+ list[i][type] +"</option>");
					}else {
	                	$(target).append("<option value='"+ list[i][type] +"' id='"+ list[i].id + "'>"+ list[i][type] +"</option>");
	                }
				}
				
				return;
			}
			
			if(type === 'province' && !judge){
				for(var i=0;i<list.length;i++){
	                $(target).append("<option value='"+ list[i][type] +"' id='"+ list[i].id + "'>"+ list[i][type] +"</option>");
				}
				
				return;
			}
			
			for(var i=0;i<list.length;i++){
				if(i === 0){
                    $(target).append("<option selected='selected' value='"+ list[i][type] +"' id='"+ list[i].id + "'>"+ list[i][type] +"</option>");
                }else {
                	$(target).append("<option value='"+ list[i][type] +"' id='"+ list[i].id + "'>"+ list[i][type] +"</option>");
                }
			}
		}
		
		
		function switchOption (){
			var target = $(arguments[0]), 
				pid = arguments[1], 
				type = arguments[2];
			
			restAjax(globalPath +'address/findByParent.do', {parentId: pid}, "POST", function (data){
				var CityList = data.data.list;
				var citySelectId,
					cityId,
					districtId,
					optionSelectedText;
				
				// 除0 后都删除，重新绑定数据
				$(target).find('option').each(function(i, v){
					  if(i >= 1){
						  $(this).remove();
					  }
				});
				
				if(type === 'city'){
					for(var i=0;i<CityList.length;i++){
						if(i === 0){
		                    $(target).append("<option selected='selected' value='"+ CityList[i][type] +"' id='"+ CityList[i].id + "'>"+ CityList[i][type] +"</option>");
		                } else {
		                	$(target).append("<option value='"+ CityList[i][type] +"' id='"+ CityList[i].id + "'>"+ CityList[i][type] +"</option>");
		                }
					}
					
					citySelectId = $(target).find("option:selected").attr('id');
					districtId = "#" + $(target).siblings("[name='district']").attr('id');
					
					restAjax(globalPath +'address/findByParent.do', {parentId: citySelectId}, "POST", function (data){
						var dList = data.data.list;
						var type = 'region';
						
						// 除0 后都删除，重新绑定数据
						$(districtId).find('option').each(function(i, v){
							  if(i >= 1){
								  $(this).remove();
							  }
						});
						
						for(var i=0;i<dList.length;i++){
							if(i === 0){
								$(districtId).append("<option selected='selected' value='"+ dList[i][type] +"' id='"+ dList[i].id + "'>"+ dList[i][type] +"</option>");
			                } else {
			                	$(districtId).append("<option value='"+ dList[i][type] +"' id='"+ dList[i].id + "'>"+ dList[i][type] +"</option>");
			                }
						}
						
						$(districtId).siblings('input').val('');
					});
					
				}
				
				if(type === 'district'){
					var rList = data.data.list;
					var rtype = 'region';
					
					for(var i=0;i<rList.length;i++){
						if(i === 0){
							$(target).append("<option selected='selected' value='"+ rList[i][rtype] +"' id='"+ rList[i].id + "'>"+ rList[i][rtype] +"</option>");
		                } else {
		                	$(target).append("<option value='"+ rList[i][rtype] +"' id='"+ rList[i].id + "'>"+ rList[i][rtype] +"</option>");
		                }
					}
					
					$(districtId).siblings('input').val('');
				}
			});
		}
		
		window.addressService = addressService;
	}());
	
