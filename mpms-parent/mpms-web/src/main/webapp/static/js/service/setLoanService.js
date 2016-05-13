/**
 * 进件写入服务
 */
(function (){
	/**
	 * 初始化进件
	 */
	var loanInit= {};
	loanInit.loanVO = loanService.createLoan();
	loanInit.customerInfoVO = loanService.createCustomerInfo();
	loanInit.houseInfoVO = loanService.createHouseInfo();
	loanInit.companyInfoVO = loanService.createCompanyInfo();
	loanInit.enterpriseInfoVOs = loanService.createEnterpriseInfo();
	loanInit.carInfoVO = loanService.createCarInfo();
	loanInit.customerVO = loanService.createCustomer();
	loanInit.firstLinealKinVO = loanService.createFirstLinealKin();
	loanInit.secondLinealKinVO = loanService.createSecondLinealKin();
	loanInit.friendVO = loanService.createFriend();
	loanInit.workmateVO = loanService.createWorkmate();
	loanInit.bankCardInfoVO = loanService.createBankCardInfo();
	loanInit.creditCardInfoVO = loanService.createCreditCardInfo();
	
	
	var setLoanService = {
		setLoan: setLoan
	}
	
	function setLoan (){
		var loanAll = !!arguments[0] ? arguments[0] : loanInit;
        
		loanAll.loanVO.loanAmount = $('#loanAmount').val();
    	loanAll.loanVO.monthlyRepay = $('#monthlyRepay').val();
    	loanAll.loanVO.loanMonths = $('#loanMonths').find('option:selected').attr('name');
    	loanAll.loanVO.salerId = $('#salerId').val();
    	loanAll.loanVO.salerInstitutionId = $('#salerInstitutionId').val();
    	loanAll.loanVO.loanProductId = $('#loanMonths').find('option:selected').val();
    	loanAll.loanVO.lendPurpose = $('#lendPurpose').val();
    	
        loanAll.customerInfoVO.livingAddressVO.addressId = $('#livingAddressVO-district').find('option:selected').attr('id');
    	loanAll.customerInfoVO.livingAddressVO.province = $('#livingAddressVO-province').val();
    	loanAll.customerInfoVO.livingAddressVO.city = $('#livingAddressVO-city').val();
    	loanAll.customerInfoVO.livingAddressVO.district = $('#livingAddressVO-district').val();
    	loanAll.customerInfoVO.livingAddressVO.street = $('#livingAddressVO-street').val();
    	loanAll.customerInfoVO.livingAddressVO.postCode = $('#customer-living-code').val() === '' ? null : $('#customer-living-code').val();
		loanAll.customerInfoVO.livingPeriod = $('#livingPeriod').val();
		loanAll.customerInfoVO.hometownAddressVO.addressId = $('#hometownAddressVO-district').find('option:selected').attr('id');
		loanAll.customerInfoVO.hometownAddressVO.province = $('#hometownAddressVO-province').val();
    	loanAll.customerInfoVO.hometownAddressVO.city = $('#hometownAddressVO-city').val();
    	loanAll.customerInfoVO.hometownAddressVO.district = $('#hometownAddressVO-district').val();
    	loanAll.customerInfoVO.hometownAddressVO.street = $('#hometownAddressVO-street').val();
    	loanAll.customerInfoVO.hometownAddressVO.postCode = $('#customer-home-code').val() === '' ? null : $('#customer-home-code').val();
    	loanAll.customerInfoVO.acceptRepayAmount = $('#acceptRepayAmount').val();
    	loanAll.customerInfoVO.houseLivingType = $("input[name='houseLivingType']:checked").attr("value");
    	loanAll.customerInfoVO.monthlyRental = $('#monthlyRental').val() === '' ? null : $('#monthlyRental').val();
    	loanAll.customerInfoVO.monthlyPayment = $('#monthlyPayment').val() === '' ? null : $('#monthlyPayment').val();
    	loanAll.customerInfoVO.houseNumber = $('#houseNumber').val() === '' ? null : $('#houseNumber').val();
    	
    	
    	loanAll.customerInfoVO.otherRemark = $('#otherRemark').val() === '' ? null : $('#otherRemark').val();

    	loanAll.houseInfoVO.houseTypeOtherRemark = $('#houseTypeOtherRemark').val();
    	loanAll.houseInfoVO.purchasedDate = $('#purchasedDate').val() ?  new Date($('#purchasedDate').val()) : purchasedDate;
    	loanAll.houseInfoVO.pledge = $('#pledge').val();
    	loanAll.houseInfoVO.area = $('#area').val();
    	loanAll.houseInfoVO.addressVO.addressId = $('#houseInfoVO-district').find('option:selected').attr('id');
    	loanAll.houseInfoVO.addressVO.province = $('#houseInfoVO-province').val();
    	loanAll.houseInfoVO.addressVO.city = $('#houseInfoVO-city').val();
    	loanAll.houseInfoVO.addressVO.district = $('#houseInfoVO-district').val();
    	loanAll.houseInfoVO.addressVO.street = $('#houseInfoVO-street').val();
    	loanAll.houseInfoVO.addressVO.postCode = $('#houseInfoVO-street-postCode').val() === '' ? null : $('#houseInfoVO-street-postCode').val();
    	loanAll.houseInfoVO.type = $("input[name='houseInfoVO-type']:checked").attr("value");
    	loanAll.houseInfoVO.houseProperty = $("input[name='houseProperty']:checked").attr("value");
    	loanAll.houseInfoVO.relationship = $("input[name='houseProperty-relationship']:checked").attr("value");
        
    	loanAll.companyInfoVO.name = $('#companyInfoVO-name').val();
    	loanAll.companyInfoVO.companyNatureOtherRemark = $('#companyNatureOtherRemark').val();
    	loanAll.companyInfoVO.companyNature = $("input[name='companyNature']:checked").attr("value");
    	loanAll.companyInfoVO.staffNumber = $('#staffNumber').val();
    	loanAll.companyInfoVO.address.addressId = $('#companyInfoVO-district').find('option:selected').attr('id');
    	loanAll.companyInfoVO.address.province = $('#companyInfoVO-province').val();
    	loanAll.companyInfoVO.address.city = $('#companyInfoVO-city').val();
    	loanAll.companyInfoVO.address.district = $('#companyInfoVO-district').val();
    	loanAll.companyInfoVO.address.street = $('#companyInfoVO-street').val();
    	loanAll.companyInfoVO.address.postCode = $('#companyInfo-postCode').val() === '' ? null : $('#companyInfo-postCode').val();
    	loanAll.companyInfoVO.department = $('#companyInfoVO-department').val();
    	loanAll.companyInfoVO.position = $('#companyInfoVO-position').val();
    	loanAll.companyInfoVO.workYear = $('#companyInfoVO-workYear').val();
    	loanAll.companyInfoVO.workMonth = $('#companyInfoVO-workMonth').val();
    	loanAll.companyInfoVO.areaTelCode = $('#companyInfoVO-areaTelCode').val();
    	loanAll.companyInfoVO.phoneNo = $('#companyInfoVO-phoneNo').val();
    	loanAll.companyInfoVO.phoneNoextNo = $('#companyInfoVO-phoneNoextNo').val();
    	loanAll.companyInfoVO.monthlyWage = $('#monthlyWage').val();
        
    	
    	loanAll.enterpriseInfoVOs[0].staffScale = $('#staffScale').val();
    	loanAll.enterpriseInfoVOs[0].identity = $("input[name='identity']:checked").attr("value");
    	loanAll.enterpriseInfoVOs[0].identityOtherRemark = $('#identityOtherRemark').val() === '' ? null : $('#identityOtherRemark').val();
    	loanAll.enterpriseInfoVOs[0].enterpriseNature = $("input[name='enterpriseNature']:checked").attr("value");
    	loanAll.enterpriseInfoVOs[0].enterpriseNatureRemark = $('#enterpriseNatureRemark').val() === '' ? null : $('#enterpriseNatureRemark').val();
    	loanAll.enterpriseInfoVOs[0].shareholdingRatio = $('#shareholdingRatio').val() === '' ? null : $('#shareholdingRatio').val();
    	loanAll.enterpriseInfoVOs[0].regiterDate = $('#regiterDate').val() ? new Date($('#regiterDate').val()) : regiterDate;
    	loanAll.enterpriseInfoVOs[0].registeredCapital = $('#registeredCapital').val();
    	
    	
        
    	loanAll.customerVO.name = $('#showName').text();
    	loanAll.customerVO.gender = $('#gender').val();
    	loanAll.customerVO.idCardNo = $('#showId').text();
    	loanAll.customerVO.mobile = $('#mobileLife').val();
    	loanAll.customerVO.mobileLife = $('#customerVO-phone').val() === '' ? null : $('#customerVO-phone').val();
    	loanAll.customerVO.zoneMobileLife = $('#customerVO-zone').val() === '' ? null : $('#customerVO-zone').val();
    	loanAll.customerVO.mobileWork = $('#mobileWork').val() ? null : $('#mobileWork').val();
    	loanAll.customerVO.marriage = $('#marriage').val();
    	loanAll.customerVO.education = $('#education').val();
    	loanAll.customerVO.qq = $('#qq').val() === '' ? null : $('#qq').val();
    	loanAll.customerVO.email = $('#email').val() === '' ? null : $('#email').val();
    	

        loanAll.firstLinealKinVO.name = $('#contactsVO-name').val();
        loanAll.firstLinealKinVO.mobile = $('#contactsVO-mobile').val();
        loanAll.firstLinealKinVO.relationship = $('#relationship1').val();
        loanAll.firstLinealKinVO.companyName = $('#contactsVO-companyName').val();
        loanAll.firstLinealKinVO.companyAddressVO.addressId = $('#companyAddress-district1').find('option:selected').attr('id');
        loanAll.firstLinealKinVO.companyAddressVO.province = $('#companyAddress-province1').val();
        loanAll.firstLinealKinVO.companyAddressVO.city = $('#companyAddress-city1').val();
        loanAll.firstLinealKinVO.companyAddressVO.district = $('#companyAddress-district1').val();
        loanAll.firstLinealKinVO.companyAddressVO.street = $('#companyAddress-street1').val();
        loanAll.firstLinealKinVO.companyAddressVO.postCode = $('#contacts-company-postCode1').val() === '' ? null : $('#contacts-company-postCode1').val();
        loanAll.firstLinealKinVO.livingAddressVO.addressId = $('#contacts-living-district1').find('option:selected').attr('id');
        loanAll.firstLinealKinVO.livingAddressVO.province = $('#contacts-living-province1').val();
        loanAll.firstLinealKinVO.livingAddressVO.city = $('#contacts-living-city1').val();
        loanAll.firstLinealKinVO.livingAddressVO.district = $('#contacts-living-district1').val();
        loanAll.firstLinealKinVO.livingAddressVO.street = $('#contacts-living-street1').val();
        loanAll.firstLinealKinVO.livingAddressVO.postCode = $('#contacts-living-postCode1').val() === '' ? null : $('#contacts-living-postCode1').val();


        loanAll.secondLinealKinVO.name = $('#contactsVO-name2').val();
        loanAll.secondLinealKinVO.mobile = $('#contactsVO-mobile2').val();
        loanAll.secondLinealKinVO.relationship = $('#relationship2').val();
        loanAll.secondLinealKinVO.companyName = $('#contactsVO-companyName2').val();
        loanAll.secondLinealKinVO.companyAddressVO.addressId = $('#companyAddress-district2').find('option:selected').attr('id');
        loanAll.secondLinealKinVO.companyAddressVO.province = $('#companyAddress-province2').val();
        loanAll.secondLinealKinVO.companyAddressVO.city = $('#companyAddress-city2').val();
        loanAll.secondLinealKinVO.companyAddressVO.district = $('#companyAddress-district2').val();
        loanAll.secondLinealKinVO.companyAddressVO.street = $('#companyAddress-street2').val();
        loanAll.secondLinealKinVO.companyAddressVO.postCode = $('#contacts-company-postCode2').val() === '' ? null : $('#contacts-company-postCode2').val();
        loanAll.secondLinealKinVO.livingAddressVO.addressId = $('#contacts-living-district2').find('option:selected').attr('id');
        loanAll.secondLinealKinVO.livingAddressVO.province = $('#contacts-living-province2').val();
        loanAll.secondLinealKinVO.livingAddressVO.city = $('#contacts-living-city2').val();
        loanAll.secondLinealKinVO.livingAddressVO.district = $('#contacts-living-district2').val();
        loanAll.secondLinealKinVO.livingAddressVO.street = $('#contacts-living-street2').val();
        loanAll.secondLinealKinVO.livingAddressVO.postCode = $('#contacts-living-postCode2').val() === '' ? null : $('#contacts-living-postCode2').val();

        loanAll.friendVO.name = $('#contactsVO-name3').val();
        loanAll.friendVO.mobile = $('#contactsVO-mobile3').val();
        loanAll.friendVO.relationship = $('#relationship3').val();
        loanAll.friendVO.companyName = $('#contactsVO-companyName3').val();

        loanAll.workmateVO.name = $('#contactsVO-name4').val();
        loanAll.workmateVO.mobile = $('#contactsVO-mobile4').val();
        loanAll.workmateVO.relationship = $('#relationship4').val();
        loanAll.workmateVO.companyName = $('#contactsVO-companyName4').val();
        
        
        return loanAll;
	}
	
	window.setLoanService = setLoanService;
}());