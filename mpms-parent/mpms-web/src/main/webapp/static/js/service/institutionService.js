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
		institutionUsers : null
	};

	function switchOption(type, target, value) {
		var targetStr = '';
		if (type === 's') {
			targetStr = target + " option";
		} else {
			targetStr = "input[name='" + target + "']";
		}

		$(targetStr).each(function(index, option) {
			if (option.value == value) {
				$(option).attr(type === 's' ? "selected" : "checked", true);
			}
		});
	}

	var institutionService = {
		getInstitution : getInstitution,
		setInstitution : setInstitution
	}

	/**
	 * getInstitutionss
	 */
	function getInstitution() {
		var obj = arguments[0] ? arguments[0] : institution;

		obj.loanProductGroupId = $('#loanProductGroupId').find(
				'option:selected').val();
		obj.institutionType = $("input[name='institutionType']:checked").val();
		obj.departmentType = $("input[name='departmentType']:checked").val();
		obj.tierCode = $('#tierCode').val();
		obj.parentId = 3;
		obj.addressId = $('#address-district').find('option:selected').attr(
				'id');
		obj.name = $('#name').val();
		
		
		obj.institutionUsers = [ {
			userId : 1,
			postionIds : [ 1, 2 ]
		} ];

		return obj;
	}

	/**
	 * setInstitution
	 */
	function setInstitution() {
		var obj = arguments[0] ? arguments[0] : institution;

		obj.id = 26;
		obj.loanProductGroupId = $('#loanProductGroupId').find(
				'option:selected').val();
		obj.institutionType = $("input[name='institutionType']:checked").val();
		obj.departmentType = $("input[name='departmentType']:checked").val();
		obj.tierCode = $('#tierCode').val();
		obj.parentId = 3;
		obj.addressId = $('#address-district').find('option:selected').attr(
				'id');
		obj.name = $('#name').val();
		obj.institutionUsers = [ {
			userId : 1,
			postionIds : [ 1, 2 ]
		} ];

		return obj;
	}

	window.institutionService = institutionService;
}());