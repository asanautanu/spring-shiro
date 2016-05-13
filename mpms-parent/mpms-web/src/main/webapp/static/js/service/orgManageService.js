/**
 * 组织机构服务
 */
(function() {

	var institution = {
		id : null,
		loanProductGroupId : null,
		institutionType : null,
		departmentType : null,
		tierCode : null,
		parentId : null,
		addressId : null,
		name : null,
		institutionUsers : null,
		mob: null,
		mobie: null
	};

	function switchOption(type, target, value) {
		var targetStr = '';
		if (type === 's') {
			targetStr = target + " option";
		} else {
			targetStr = "input[name='" + target + "']";
			$(targetStr).attr('checked', false);
		}

		$(targetStr).each(function(index, option) {
			if (option.value == value) {
				if(type === 's' ){
					$(option).attr("selected", true);
				}else {
					$(option).trigger('click');
				}
			}
		});
	}

	var institutionService = {
		getInstitution : getInstitution,
		setInstitution : setInstitution,
		getPositionList: getPositionList,
		getUserList: getUserList,
		getUsers: getUsers,
		createLoanproduct: createLoanproduct
	}
	
	/**
	 * getUserList
	 */
	function getUserList(){
		var data = {},
			userList = [];
		
		if(arguments.length > 0){
			data = {
					value: arguments[0]
			};
		}
		
		restAjax(globalPath +'institution/userlist.do', data, "GET", function(json) {
			if(json.code === 0){
				userList = json.data.users;
			}
		}, null, "JSON", null, null, false);
		
		return userList;
	}
	
	
	/**
	 * getPositionList
	 */
	function getPositionList(){
		var position = [];
		
		restAjax(globalPath +'position/list.do', null, "GET", function (json){
			if(json.code === 0){
				position = json.data.position;
			}
		}, null, "JSON", null, null, false);
		
		return position;
	}
	

	/**
	 * getInstitutions
	 */
	function getInstitution() {
		var obj = arguments[0] ? arguments[0] : institution;
		var addUserPosition = $('#userPositionTbale').bootstrapTable('getData');
		obj.institutionUsers = [];

		// 产品组
		obj.loanProductGroupId = parseInt($('#loanProductGroupId').find(
		'option:selected').val());
		// 组织类型
		obj.institutionType = $("input[name='institutionType']:checked").val();
		// 部门类型
		obj.departmentType = $("input[name='departmentType']:checked").val();
		// 层级编号
		obj.tierCode = parseInt($('#tierCode').val());
		// 父集ID
		obj.parentId = parseInt($("#parentId").val()) ? parseInt($("#parentId").val()) : null;
		// 城市
		obj.addressId = parseInt($('#address-district').find('option:selected').attr(
				'id'));
		// 用户名
		obj.name = $('#name').val();
		
		// mob
		obj.mob =  parseInt($('#mob').val());
		
		// mobie
		obj.mobie =  parseInt($('#mobie').val());
		
		// 职位
		if(addUserPosition.length>0){
			var userId, postionId;
			for(var i in addUserPosition){
				
				userId = addUserPosition[i].id;
				postionId = addUserPosition[i].position.id;
				
				obj.institutionUsers.push({
					userId : userId,
					postionId: postionId
				});
				postionId = null;
			}
		}
		
		return obj;
	}

	/**
	 * setInstitution
	 */
	function setInstitution(id) {
		var obj = institution;
		
		restAjax(globalPath +'institution/info.do', {'insId':id}, "GET", function(json) {
			if(json.code === 0){
				// 产品
				//switchOption('s','#loanProductGroupId', json.data.institutions.loanProductGroupId);
				createLoanproduct(json.data.institutions.loanProductGroupId);
				
				// 地址
				addressService.queryAddress(json.data.institutions.address.id, '#address-province');
				// 组织
				switchOption('r', 'institutionType', json.data.institutions.institutionType.name);
				// 部门
				switchOption('r', 'departmentType', json.data.institutions.departmentType.name);
				//层级编号
				$('#tierCode').val(json.data.institutions.tierCode);
				
				// 用户名
				$('#name').val(json.data.institutions.name);
				
				// mob
				$('#mob').val(json.data.institutions.mob);
				
				// mobie
				$('#mobie').val(json.data.institutions.mobie);
				
				//父id 
				obj.parentId = json.data.institutions.pId;
				obj.id = json.data.institutions.id;
				obj.loanProductGroupId =  json.data.institutions.loanProductGroupId;
				obj.institutionType = json.data.institutions.institutionType.name;
				obj.departmentType = json.data.institutions.institutionType.name;
				obj.tierCode = json.data.institutions.tierCode;
				obj.addressId = json.data.institutions.address.id;
				obj.name = json.data.institutions.name;
				obj.mobie = json.data.institutions.mobie;
				
				$('#institutionsId').val(json.data.institutions.id);
				
			}
		}, null, "JSON", null, null, false);
		
		return obj;
	}
	
	/**
	 * createLoanproduct
	 */
	function createLoanproduct(){
		var loanproducts = [],loanProductGroupId = arguments[0] ? arguments[0]: undefined;
		
		restAjax(globalPath +'institution/loanproduct.do', null, "GET", function(json) {
			if (json.data.list) {
				var list = json.data.list;
	
				// 除0 后都删除，重新绑定数据
				$('#loanProductGroupId').find('option').each(function(i, v){
					  if(i >= 1){
						  $(this).remove();
					  }
				});
				
				for (var i = 0; i < list.length; i++) {
					$('#loanProductGroupId').append(
							"<option value='" + list[i].id + "' id='"
									+ list[i].id + "'>" + list[i].name
									+ "</option>");
					
					if(loanProductGroupId){
						switchOption('s','#loanProductGroupId', loanProductGroupId);
					}
				}
			}
		}, null, "JSON", null, null, false);
	}
	
	
	/**
	 * getUsers
	 */
	function getUsers(id){
		var users = [];
		restAjax(globalPath + 'user/getUsers.do', {'institurionId':id}, "GET", function(json) {
			if(json.code === 0){
				users = json.data
			}
		}, null, "JSON", null, null, false);
		return users;
	}

	window.institutionService = institutionService;
}());