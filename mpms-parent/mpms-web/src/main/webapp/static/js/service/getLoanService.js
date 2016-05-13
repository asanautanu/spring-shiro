/**
 * 进件获取服务
 */
(function (){
	/**
	 * 初始化进件
	 */
	var loan = {};
	
	var getLoanService = {
		getLoan: getLoan
	}
	
	function switchOption (type, target, value) {
		var targetStr = '';
		if(type === 's'){
			targetStr = target + " option";
		} else {
			targetStr= "input[name='"+ target +"']";
		}
		
		$(targetStr).each(function(index, option){
			if(option.value == value){
				$(option).attr(type === 's'? "selected": "checked", true);
			}
		});
	}
	
	function getLoan (){
		var loanId = arguments[0];
		
		if(loanId === "0" || !loanId){
			return;
		}
		
		
		restAjax(globalPath+'loan/getLoan.do', {id:loanId}, "GET", function (json){
			var loanObj = json.data;
			
			if(!loanObj){
				return;
			}
			
			loan.loanVO = loanService.createLoan(loanObj.loan);
			loan.customerInfoVO = loanService.createCustomerInfo(loanObj.customerInfo);
			loan.houseInfoVO = loanService.createHouseInfo(loanObj.houseInfos);
			loan.companyInfoVO = loanService.createCompanyInfo(loanObj.companyInfo);
			loan.enterpriseInfoVOs = loanService.createEnterpriseInfo(loanObj.enterpriseInfos);
			loan.carInfoVO = loanService.createCarInfo();
			loan.customerVO = loanService.createCustomer(loanObj.loan.lender);
			loan.firstLinealKinVO = loanService.createFirstLinealKin(loanObj.contacts);
			loan.secondLinealKinVO = loanService.createSecondLinealKin(loanObj.contacts);
			loan.friendVO = loanService.createFriend(loanObj.contacts);
			loan.workmateVO = loanService.createWorkmate(loanObj.contacts);
			loan.bankCardInfoVO = loanService.createBankCardInfo();
			loan.creditCardInfoVO = loanService.createCreditCardInfo();
			
			// 姓名
			$('#showName').text(loanObj.loan ? (loanObj.loan.lender ? loanObj.loan.lender.name: ''): '');
			$('#loanCustomer-name').val(loanObj.loan.lender.name).attr("disabled",true);
			
			//qq 
			$('#qq').val(loanObj.loan ? (loanObj.loan.lender ? loanObj.loan.lender.qq : ''): '');
			
			//email
			$('#email').val(loanObj.loan ? (loanObj.loan.lender ? loanObj.loan.lender.email : ''): '');

			// 个人住宅电话
			$('#customerVO-zone').val(loanObj.loan ? (loanObj.loan.lender ? loanObj.loan.lender.zoneMobileLife : ''): '');
	    	$('#customerVO-phone').val(loanObj.loan ? (loanObj.loan.lender ? loanObj.loan.lender.mobileLife : ''): '');
			
			// 身份证
			$('#showId').text(loanObj.loan ? (loanObj.loan.lender ? loanObj.loan.lender.idCardNo : ''): '');
			$('#loanCustomer-idCard').val(loanObj.loan ? loanObj.loan.lender.idCardNo: null).attr("disabled",true);
			$('#validatorUser').attr("disabled",true);
			
			// 期望贷款额度
			$('#loanAmount').val(loanObj.loan.loanAmount);
			// 月接受还款额
			$('#acceptRepayAmount').val(loanObj.customerInfo ? loanObj.customerInfo.acceptRepayAmount: '');
			// 借款用途
			switchOption('s','#lendPurpose', loanObj.loan.lendPurpose.name);
			// 产品
			productService.queryProduct(loanObj.loan.loanRelatedProductInfo.loanProductId);
			// 所属组织架构
			$('#salerInstitutionId').val(loanObj.loan.salerInstitutionId);
			$('#organizationInput').val(loanObj.salerInstitution);
			// 销售
			$('#salerId').append("<option selected='selected' value='"+ loanObj.loan.salerId  +"' id='"+ loanObj.loan.salerId + "'>"+ loanObj.salerName +"</option>");
			
			// 性别
			switchOption('s','#gender', loanObj.loan.lender ? (loanObj.loan.lender.gender ? loanObj.loan.lender.gender.name : null): null);
			// 婚姻
			switchOption('s','#marriage', loanObj.loan.lender ? (loanObj.loan.lender.marriage ? loanObj.loan.lender.marriage.name : null): null);
			// 教育程度
			switchOption('s','#education', loanObj.loan.lender ? (loanObj.loan.lender.education ? loanObj.loan.lender.education.name : null): null);
			// 手机
			$('#mobileLife').val(loanObj.loan.lender.mobile); 
			// 现居住地年限
			$('#livingPeriod').val(loanObj.customerInfo ? loanObj.customerInfo.livingPeriod: '');
			//住宅情况
			switchOption('r', 'houseLivingType', loanObj.customerInfo ? (loanObj.customerInfo.houseLivingType ? loanObj.customerInfo.houseLivingType.name : null) : null);
			// 住宅其他
			$('#otherRemark').val(loanObj.customerInfo ? loanObj.customerInfo.otherRemark : null);
			// 住宅评租
			$('#monthlyRental').val(loanObj.customerInfo ? loanObj.customerInfo.monthlyRental: null);
			// 住宅按揭
			 $('#monthlyPayment').val(loanObj.customerInfo ?  loanObj.customerInfo.monthlyPayment: null);
			
			
			// 直系亲属1
			$('#contactsVO-name').val(loanObj.contacts[0] ? loanObj.contacts[0].name: '');
			$('#contactsVO-mobile').val(loanObj.contacts[0] ? loanObj.contacts[0].mobile :'');
			$('#contactsVO-companyName').val(loanObj.contacts[0] ? loanObj.contacts[0].companyName: '');
			// 直系亲属1 - 居住地址
			addressService.queryAddress(loanObj.contacts[0] ? loanObj.contacts[0].livingAddress.addressId :null, '#contacts-living-province1');
			$('#contacts-living-street1').val(loanObj.contacts[0] ? loanObj.contacts[0].livingAddress.street :'');
			
			// 直系亲属1 - 居住地址邮编
			$('#contacts-living-postCode1').val(loanObj.contacts[0] ? loanObj.contacts[0].livingAddress.postCode: '');
			
			
			// 直系亲属1 - 公司地址
			addressService.queryAddress(loanObj.contacts[0] ? loanObj.contacts[0].companyAddress.addressId: null, '#companyAddress-province1');
			$('#companyAddress-street1').val(loanObj.contacts[0] ? loanObj.contacts[0].companyAddress.street: '');
			// 直系亲属1 - 公司地址 邮编
			$('#contacts-company-postCode1').val(loanObj.contacts[0] ? loanObj.contacts[0].companyAddress.postCode: '');
			
			
			// 直系亲属2
			$('#contactsVO-name2').val(loanObj.contacts[1] ? loanObj.contacts[1].name: '');
			$('#contactsVO-mobile2').val(loanObj.contacts[1] ? loanObj.contacts[1].mobile:'');
			$('#contactsVO-companyName2').val(loanObj.contacts[1] ? loanObj.contacts[1].companyName: '');
			// 直系亲属2 - 居住地址
			addressService.queryAddress(loanObj.contacts[1] ? loanObj.contacts[1].livingAddress.addressId: null, '#contacts-living-province2');
			$('#contacts-living-street2').val(loanObj.contacts[1] ? loanObj.contacts[1].livingAddress.street: '');
			// 直系亲属1 - 居住地址邮编
			$('#contacts-living-postCode2').val(loanObj.contacts[1] ? loanObj.contacts[1].livingAddress.postCode: '');
			
			// 直系亲属2 - 公司地址
			addressService.queryAddress(loanObj.contacts[1] ? loanObj.contacts[1].companyAddress.addressId: null, '#companyAddress-province2');
			$('#companyAddress-street2').val(loanObj.contacts[1] ? loanObj.contacts[1].companyAddress.street: '');
			
			// 直系亲属2 - 公司地址 邮编
			$('#contacts-company-postCode2').val(loanObj.contacts[1] ? loanObj.contacts[1].companyAddress.postCode: '');
			
			// 朋友 
			$('#contactsVO-name3').val(loanObj.contacts[2] ? loanObj.contacts[2].name: '');
			$('#contactsVO-mobile3').val(loanObj.contacts[2] ? loanObj.contacts[2].mobile: '');
			$('#contactsVO-companyName3').val(loanObj.contacts[2] ? loanObj.contacts[2].companyName: '');
			
			// 同事 
			$('#contactsVO-name4').val(loanObj.contacts[3] ? loanObj.contacts[3].name: '');
			$('#contactsVO-mobile4').val(loanObj.contacts[3] ? loanObj.contacts[3].mobile: '');
			$('#contactsVO-companyName4').val(loanObj.contacts[3] ? loanObj.contacts[3].companyName: '');
			
			// 个人资料现居住地
			addressService.queryAddress(loanObj.customerInfo ? loanObj.customerInfo.livingAddress.addressId: null, '#livingAddressVO-province');
			$('#livingAddressVO-street').val(loanObj.customerInfo ? loanObj.customerInfo.livingAddress.street: '');
			
			// 个人资料户口所在地
			addressService.queryAddress(loanObj.customerInfo ? loanObj.customerInfo.hometownAddress.addressId: null, '#hometownAddressVO-province');
			$('#hometownAddressVO-street').val(loanObj.customerInfo ? loanObj.customerInfo.hometownAddress.street: '');
			
			// 个人资料邮政编码
			$('#customer-living-code').val(loanObj.customerInfo ? loanObj.customerInfo.hometownAddress.postCode: '');
			$('#customer-home-code').val(loanObj.customerInfo ? loanObj.customerInfo.livingAddress.postCode: '');
			
			
			
			// 公司全称
			$('#companyInfoVO-name').val(loanObj.companyInfo ? loanObj.companyInfo.name: '');
			// 任职部门
			$('#companyInfoVO-department').val(loanObj.companyInfo ? loanObj.companyInfo.department: '');
			// 职务
			$('#companyInfoVO-position').val(loanObj.companyInfo ? loanObj.companyInfo.position: '');
			// 月收入总额
			$('#monthlyWage').val(loanObj.companyInfo ? loanObj.companyInfo.monthlyWage: '');
			
			//公司地址
			addressService.queryAddress(loanObj.companyInfo ? loanObj.companyInfo.address.addressId: null, '#companyInfoVO-province');
			$('#companyInfoVO-street').val(loanObj.companyInfo ? loanObj.companyInfo.address.street: '');
			// 公司邮编
			$('#companyInfo-postCode').val(loanObj.companyInfo ? loanObj.companyInfo.address.postCode: '');
			
			
			
			// 现公司工龄
			switchOption('s','#companyInfoVO-workYear', loanObj.companyInfo ? loanObj.companyInfo.workYear: null); 
			switchOption('s','#companyInfoVO-workMonth', loanObj.companyInfo ? loanObj.companyInfo.workMonth: null);
			
			
			// 公司电话
			$('#companyInfoVO-areaTelCode').val(loanObj.companyInfo ? loanObj.companyInfo.areaTelCode:'');
			$('#companyInfoVO-phoneNo').val(loanObj.companyInfo ? loanObj.companyInfo.phoneNo:'');
			$('#companyInfoVO-phoneNoextNo').val(loanObj.companyInfo ? loanObj.companyInfo.phoneNoextNo:'');
			
			// 公司性质
			switchOption('r', 'companyNature', loanObj.companyInfo&&loanObj.companyInfo.companyNature ? loanObj.companyInfo.companyNature.name: null);
			// 职业资料其他
			$('#companyNatureOtherRemark').val(loanObj.companyInfo ? loanObj.companyInfo.companyNatureOtherRemark:'');
			
			// 购买时间
			if(loanObj.houseInfos[0]){
				var purchasedDate = loanObj.houseInfos[0]?(loanObj.houseInfos[0].purchasedDate?loanObj.houseInfos[0].purchasedDate.substring(0,loanObj.houseInfos[0].purchasedDate.lastIndexOf("-")+3):""):"";
				
				$('#purchasedDate').val(loanObj.houseInfos[0] ? purchasedDate :'');
			}
			
			
			// 房产面积
			$('#area').val(loanObj.houseInfos[0] ? loanObj.houseInfos[0].area: '');
			
			// 房产--其他
			$('#houseTypeOtherRemark').val(loanObj.houseInfos[0] ? loanObj.houseInfos[0].houseTypeOtherRemark:'');
			
			// 房产-- 共有关系
			switchOption('r', 'houseProperty-relationship',loanObj.houseInfos[0] ? (loanObj.houseInfos[0].relationship? loanObj.houseInfos[0].relationship.name : null) : null);
			
			
			// 房产地址
			addressService.queryAddress(loanObj.houseInfos[0] ? loanObj.houseInfos[0].address.addressId: null, '#houseInfoVO-province');
			$('#houseInfoVO-street').val(loanObj.houseInfos[0] ? loanObj.houseInfos[0].address.street:'');
			
			// 房产邮编
			$('#houseInfoVO-street-postCode').val(loanObj.houseInfos[0] ? loanObj.houseInfos[0].address.postCode: '');
			
						
			// 提供有效房数量
			$('#houseNumber').val(loanObj.customerInfo ? loanObj.customerInfo.houseNumber: '');
			
			// 产权人
			switchOption('r', 'houseProperty', loanObj.houseInfos[0] ? (loanObj.houseInfos[0].houseProperty ? loanObj.houseInfos[0].houseProperty.name : null) :null);
			
			// 房产当期抵押情况
			switchOption('s','#pledge', loanObj.houseInfos[0] ? (loanObj.houseInfos[0].pledge ? loanObj.houseInfos[0].pledge.name:null) : null); 
			
			// 房产类型
			switchOption('r','houseInfoVO-type', loanObj.houseInfos[0] ? (loanObj.houseInfos[0].type ? loanObj.houseInfos[0].type.name: null) :null); 
			
			// 客户身份 
			switchOption('r','identity', loanObj.enterpriseInfos[0] ? (loanObj.enterpriseInfos[0].identity ? loanObj.enterpriseInfos[0].identity.name: null) :null); 
			
			// 企业性质
			switchOption('r','enterpriseNature', loanObj.enterpriseInfos[0] ? (loanObj.enterpriseInfos[0].enterpriseNature ? loanObj.enterpriseInfos[0].enterpriseNature.name:null) :null); 
			
			switchOption('r', 'houseProperty', loanObj.houseInfos[0]&&loanObj.houseInfos[0].houseProperty ? loanObj.houseInfos[0].houseProperty.name : null);
			
			// 房产当期抵押情况
			switchOption('s','#pledge', loanObj.houseInfos[0]&&loanObj.houseInfos[0].pledge ? loanObj.houseInfos[0].pledge.name:null); 
			
			// 房产类型
			switchOption('r','houseInfoVO-type', loanObj.houseInfos[0]&&loanObj.houseInfos[0].type ? loanObj.houseInfos[0].type.name: null); 
			
			// 客户身份 
			switchOption('r','identity', loanObj.enterpriseInfos[0]&&loanObj.enterpriseInfos[0].identity ? loanObj.enterpriseInfos[0].identity.name: null); 
			
			// 企业性质
			switchOption('r','enterpriseNature', loanObj.enterpriseInfos[0]&&loanObj.enterpriseInfos[0].enterpriseNature ? loanObj.enterpriseInfos[0].enterpriseNature.name:null); 
			
			//生意贷--法人--其他
			$('#identityOtherRemark').val(loanObj.enterpriseInfos[0] ? loanObj.enterpriseInfos[0].identityOtherRemark:'');
			
			// 生意贷--企业性质--其他
			$('#enterpriseNatureRemark').val(loanObj.enterpriseInfos[0] ? loanObj.enterpriseInfos[0].enterpriseNatureRemark: '');
			
			// 生意贷--企业性质--比率
			$('#shareholdingRatio').val(loanObj.enterpriseInfos[0] ? loanObj.enterpriseInfos[0].shareholdingRatio: '');
			
			// 注册时间
			if(loanObj.enterpriseInfos[0]){
				var regiterDate = loanObj.enterpriseInfos[0].regiterDate?loanObj.enterpriseInfos[0].regiterDate.substring(0,loanObj.enterpriseInfos[0].regiterDate.lastIndexOf("-")+3):"";
				
				$('#regiterDate').val(loanObj.enterpriseInfos[0] ? regiterDate :'');
			}
			
			
			// 企业员工人数
			switchOption('s','#staffScale', loanObj.enterpriseInfos[0] ? (loanObj.enterpriseInfos[0].staffScale ? loanObj.enterpriseInfos[0].staffScale.name: null):null); 
			// 注册资本
			$('#registeredCapital').val(loanObj.enterpriseInfos[0] ? loanObj.enterpriseInfos[0].registeredCapital:'');
			// 客户身份
			switchOption('r','identity', loanObj.enterpriseInfos[0] ? loanObj.enterpriseInfos[0].identity: null); 
		
		}, null, null, null, null, false);
	
		return loan;
		 
	}
	
	window.getLoanService = getLoanService;
}());