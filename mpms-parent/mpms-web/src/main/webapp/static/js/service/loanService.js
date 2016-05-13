(function() {
      var loanVO = {
            id: null,
            number: null,
            loanAmount: null,
            signedAmount: null,
            amount: null,
            monthlyRepay: null,
            currentPhase: null,
            loanMonths: null,
            resetPricipal: null,
            loanStatus: null, 
            loanDistributionRecordId: null,
            loanRepayPayType: null,
//            loanInterestType: null,
//            loanServiceFeeType: null,
            salerId: null,
            serviceUserId: null,
            salerInstitutionId: null,
            serviceInstitutionId: null,
            lenderId: null,
            repayBankCardId: null,
            loanUrgency: null,
            lendTime: null,
            lastUpdateTime: null,
            createTime: null,
            version: null,
            loanProductId: null,
            lendPurpose: null
      }

      var customerInfoVO = {
            id: null,
            customerId: null,
            livingAddressVO: {
                  addressId:null,
                  id: null,
                  province: null,
                  city: null,
                  district: null,
                  street: null,
                  postCode: null
            },
            livingPeriod: null,
            hometownAddressVO: {
                  addressId:null,
                  id: null,
                  province: null,
                  city: null,
                  district: null,
                  street: null,
                  postCode: null
            },
            houseNumber: null,
            carNumber: null,
            acceptRepayAmount: null,
            otherAsset: null,
            houseLoanMoney: null,
            carLoanMoney: null,
            otherLoanMoney: null,
            houseLivingType: null,
            monthlyRental: null,
            monthlyPayment: null,
            otherRemark: null,
            createTime: null,
            lastUpdateTime: null
      }

      var houseInfoVO = {
            id: null,
            //loanId: null,
            customerId: null,
            houseTypeOtherRemark: null,
            purchasedDate: null,
            pledge: null,
            area: null,
            addressVO: {
                  addressId:null,
                  id: null,
                  province: null,
                  city: null,
                  district: null,
                  street: null,
                  postCode: null
            },
            type: null,
            houseProperty: null,
            relationship: null
      }

      var companyInfoVO = {
            id: null,
            //loanId: null,
            customerId: null,
            name: null,
            organizationCode: null,
            registeredCapital: null,
            companyNatureOtherRemark: null,
            companyNature: null,
            registeredMonths: null,
            staffNumber: null,
            address: {
                  addressId:null,
                  id: null,
                  province: null,
                  city: null,
                  district: null,
                  street: null,
                  postCode: null
            },
            entryDate: null,
            contractDueDate: null,
            department: null,
            position: null,
            entryDate: null,
            workYear: null,
            workMonth: null,
            areaTelCode: null,
            phoneNo: null,
            phoneNoextNo: null,
            monthlyWage: null,
            buySocialSecurityDate: null,
            buyHousingFounDate: null,
            jobNature: null,
            createTime: null,
            lastUpdateTime: null
      }

      var enterpriseInfoVOs = [{
            id: null,
            //loanId: null,
            customerId: null,
            name: null,
            organizationCode: null,
            address: null,
            registeredCapital: null,
            registeredMonths: null,
            staffScale: null,
            yearlyTax: null,
            yearlyProfit: null,
            monthlyIncome: null,
            identity: null,
            identityOtherRemark: null,
            enterpriseNature: null,
            enterpriseNatureRemark: null,
            shareholdingRatio: null,
            monthlyWage: null,
            socialSecurity: null,
            regiterDate: null,
            createTime: null,
            lastUpdateTime: null
      }]

      var carInfoVO = {
            id: null,
            customerId: null,
            brand: null,
            model: null,
            plateNumber: null,
            travelledmileage: null,
            purchasedYears: null,
            purchasingMoney: null,
            evaluatedMoney: null,
            drivingLicense: null,
            nameForDrivingLicense: null,
            vehicleFrameNO: null,
            color: null,
            engineNo: null,
            personsCapacity: null,
            createTime: null,
            lastUpdateTime: null
      }

      var customerVO = {
            id: null,
            name: null,
            gender: null,
            age: null,
            idCardNo: null,
            mobile: null,
            marriage: null,
            childrenAmount: null,
            mobileLife: null,
            zoneMobileLife: null,
            mobileLifeExtend: null,
            mobileWork: null,
            qq: null,
            email: null,
            birthday: null,
            education: null,
            source: null,
            keepSecretForRelatives: null,
            keepSecretForFriends: null,
            keepSecretForColleagues: null,
            remark: null,
            createTime: null,
            lastUpdateTime: null
      }

      var firstLinealKinVO = {
            id: null,
            //loanId: null,
            customerId: null,
            name: null,
            gender: null,
            age: null,
            mobile: null,
            relationship: null,
            companyName: null,
            companyAddressVO: {
                  addressId:null,
                  id: null,
                  province: null,
                  city: null,
                  district: null,
                  street: null,
                  postCode: null
            },
            livingAddressVO: {
                  addressId:null,
                  id: null,
                  province: null,
                  city: null,
                  district: null,
                  street: null,
                  postCode: null
            },
            createTime: null,
            lastUpdateTime: null
      }

      var secondLinealKinVO = {
            id: null,
            //loanId: null,
            customerId: null,
            name: null,
            gender: null,
            age: null,
            mobile: null,
            relationship: null,
            companyName: null,
            companyAddressVO: {
                  addressId:null,
                  id: null,
                  province: null,
                  city: null,
                  district: null,
                  street: null,
                  postCode: null
            },
            livingAddressVO: {
                  addressId:null,
                  id: null,
                  province: null,
                  city: null,
                  district: null,
                  street: null,
                  postCode: null
            },
            createTime: null,
            lastUpdateTime: null
      }

      var friendVO = {
            id: null,
            //loanId: null,
            customerId: null,
            name: null,
            gender: null,
            age: null,
            mobile: null,
            relationship: null,
            companyName: null,
            companyAddressVO: null,
            livingAddressVO: null,
            createTime: null,
            lastUpdateTime: null
      }

      var workmateVO = {
            id: null,
            //loanId: null,
            customerId: null,
            name: null,
            gender: null,
            age: null,
            mobile: null,
            relationship: null,
            companyName: null,
            companyAddressVO: null,
            livingAddressVO: null,
            createTime: null,
            lastUpdateTime: null
      }

      var bankCardInfoVO = {
            id: null,
            customerId: null,
            bank: BankVO,
            cardNo: null,
            accountName: null,
            bankName: null,
            address: null,
            createTime: null,
            lastUpdateTime: null
      }

      var creditCardInfoVO = {
            id: null,
            customerId: null,
            bank: null,
            account: null,
            amount: null,
            tempAmount: null,
            endYear: null,
            endMonth: null,
            holder: null,
            type: null,
            createTime: null,
            lastUpdateTime: null
      }

      var BankVO = {
            id: null,
            name: null,
            code: null,
            payCode: null,
            hotLinePhone: null,
            createTime: null,
            lastUpdateTime: null
      }

      var loanService = {
            createLoan: createLoan,
            createCustomerInfo: createCustomerInfo,
            createHouseInfo: createHouseInfo,
            createCompanyInfo: createCompanyInfo,
            createEnterpriseInfo: createEnterpriseInfo,
            createCarInfo: createCarInfo,
            createCustomer: createCustomer,
            createFirstLinealKin: createFirstLinealKin,
            createSecondLinealKin: createSecondLinealKin,
            createFriend: createFriend,
            createWorkmate: createWorkmate,
            createBankCardInfo: createBankCardInfo,
            createCreditCardInfo: createCreditCardInfo
      }

      /**
       * 创建Loan
       */
      function createLoan() {
            var data = arguments[0], loan;

            if (typeof(data) === 'object') {
                  loan = {
                        id: data.id,
                        number: data.number,
                        loanAmount: data.loanAmount,
                        signedAmount: data.signedAmount,
                        amount: data.amount,
                        monthlyRepay: data.monthlyRepay,
                        currentPhase: data.currentPhase,
                        loanMonths: data.loanMonths ? data.loanMonths.name : null,
                        resetPricipal: data.resetPricipal,
                        loanStatus: data.loanStatus.name,
                        loanDistributionRecordId: data.loanDistributionRecordId,
                        loanRepayPayType: data.loanRepayPayType,
//                        loanInterestType: data.loanInterestType,
//                        loanServiceFeeType: data.loanServiceFeeType ?  data.loanServiceFeeType.name : null,
                        salerId: data.salerId,
                        serviceUserId: data.serviceUserId,
                        salerInstitutionId: data.salerInstitutionId,
                        serviceInstitutionId: data.serviceInstitutionId,
                        lenderId: data.lender.id,
                        repayBankCardId: data.repayBankCardId,
                        loanUrgency: data.loanUrgency,
                        lendTime: data.lendTime,
                        lastUpdateTime: data.lastUpdateTime,
                        createTime: data.createTime,
                        version: data.version,
                        loanProductId: data.loanRelatedProductInfo.loanProductId,
                        lendPurpose: data.lendPurpose ? data.lendPurpose.name: null
                  }
            }

            return loan ? loan : loanVO;
      }

      /**
       * 创建CustomerInfo
       */
      function createCustomerInfo() {
            var data = arguments[0], customerInfo;

            if (typeof(data) === 'object' && !!data) {
                  customerInfo = {
                        id: data.id,
                        customerId: data.customerId,
                        livingAddressVO: {
                              addressId:data.livingAddress.addressId,
                              id: data.livingAddress.id,
                              province: data.livingAddress.province,
                              city: data.livingAddress.city,
                              district: data.livingAddress.district,
                              street: data.livingAddress.street,
                              postCode: data.livingAddress.postCode
                        },
                        livingPeriod: data.livingPeriod,
                        hometownAddressVO: {
                              addressId:data.hometownAddress.addressId,
                              id: data.hometownAddress.id,
                              province: data.hometownAddress.province,
                              city: data.hometownAddress.city,
                              district: data.hometownAddress.district,
                              street: data.hometownAddress.street,
                              postCode: data.hometownAddress.postCode
                        },
                        houseNumber: data.houseNumber,
                        carNumber: data.carNumber,
                        acceptRepayAmount: data.acceptRepayAmount,
                        otherAsset: data.otherAsset,
                        houseLoanMoney: data.houseLoanMoney,
                        carLoanMoney: data.carLoanMoney,
                        otherLoanMoney: data.otherLoanMoney,
                        houseLivingType: data.houseLivingType ? data.houseLivingType.name : null,
                        monthlyRental: data.monthlyRental,
                        monthlyPayment: data.monthlyPayment,
                        otherRemark: data.otherRemark,
                        createTime: data.createTime,
                        lastUpdateTime: data.lastUpdateTime
                  }
            };

            return customerInfo ? customerInfo : customerInfoVO;
      }

      /**
       * houseInfoVO
       */
      function createHouseInfo() {
            var data = arguments[0] ? arguments[0][0]: arguments[0],
                  houseInfo;

            if (typeof(data) === 'object') {
                  houseInfo = {
                        id: data.id,
                        //loanId: data.loanId ? data.loanId : null,
                        customerId: data.customerId,
                        houseTypeOtherRemark: data.houseTypeOtherRemark,
                        purchasedDate: data.purchasedDate,
                        pledge: data.pledge?data.pledge.name:null,
                        area: data.area,
                        addressVO: {
                              addressId:data.address.addressId,
                              id: data.address.id,
                              province: data.address.province,
                              city: data.address.city,
                              district: data.address.district,
                              street: data.address.street,
                              postCode: data.address.postCode
                        },
                        type: data.type?data.type.name:null,
                        houseProperty: data.houseProperty ? data.houseProperty.name: null,
                        relationship: data.relationship ? data.relationship.name : null
                  }
            }

            return houseInfo ? houseInfo : houseInfoVO;
      }

      /**
       * createCompanyInfo
       */
      function createCompanyInfo() {
            var data = arguments[0], companyInfo;

            if (typeof(data) === 'object' && !!data) {
                  companyInfo = {
                        id: data.id,
                        //loanId: data.loanId ? data.loanId : null,
                        customerId: data.customerId,
                        name: data.name,
                        organizationCode: data.organizationCode,
                        registeredCapital: data.registeredCapital,
                        companyNatureOtherRemark: data.companyNatureOtherRemark,
                        companyNature: data.companyNature?data.companyNature.name:null,
                        registeredMonths: data.registeredMonths,
                        staffNumber: data.staffNumber,
                        address: {
                              addressId:data.address.addressId,
                              id: data.address.id,
                              province: data.address.province,
                              city: data.address.city,
                              district: data.address.district,
                              street: data.address.street,
                              postCode: data.address.postCode
                        },
                        entryDate: data.entryDate,
                        contractDueDate: data.contractDueDate,
                        department: data.department,
                        position: data.position,
                        entryDate: data.entryDate,
                        workYear: data.workYear,
                        workMonth: data.workMonth,
                        areaTelCode: data.areaTelCode,
                        phoneNo: data.phoneNo,
                        phoneNoextNo: data.phoneNoextNo,
                        monthlyWage: data.monthlyWage,
                        buySocialSecurityDate: data.buySocialSecurityDate,
                        buyHousingFounDate: data.buyHousingFounDate,
                        jobNature: data.jobNature,
                        createTime: data.createTime,
                        lastUpdateTime: data.lastUpdateTime
                  }
            }
            return companyInfo ? companyInfo : companyInfoVO;
      }

      
      /**
       * customerVO
       */
      function createCustomer() {
    	  var data = arguments[0], customer;

		    if (typeof(data) === 'object') {
		    	     customer = {
                              id: data.id,
                              name: data.name,
                              gender: data.gender ? data.gender.name : data.gender,
                              age: data.age,
                              idCardNo: data.idCardNo,
                              mobile: data.mobile,
                              zoneMobileLife: data.zoneMobileLife,
                              mobileLifeExtend: data.mobileLifeExtend,
                              marriage: data.marriage ? data.marriage.name : data.marriage,
                              childrenAmount: data.childrenAmount,
                              mobileLife: data.mobileLife,
                              mobileWork: data.mobileWork,
                              qq: data.qq,
                              email: data.email,
                              birthday: data.birthday,
                              education: data.education ? data.education.name : data.education,
                              source: data.source,
                              keepSecretForRelatives: data.keepSecretForRelatives,
                              keepSecretForFriends: data.keepSecretForFriends,
                              keepSecretForColleagues: data.keepSecretForColleagues,
                              remark: data.remark,
                              createTime: data.createTime,
                              lastUpdateTime: data.lastUpdateTime
		        }
		    }
    	  
            return customer ? customer : customerVO;
      }

      /**
       * enterpriseInfoVO
       */
      function createEnterpriseInfo() {
            var data = arguments[0] ? arguments[0][0]: arguments[0],
                  enterpriseInfo;

            if (typeof(data) === 'object') {
                  enterpriseInfo = [{
                        id: data.id,
                        //loanId: data.loanId ? data.loanId : null,
                        customerId: data.customerId,
                        name: data.name,
                        organizationCode: data.organizationCode,
                        address: data.address,
                        registeredCapital: data.registeredCapital,
                        registeredMonths: data.registeredMonths,
                        staffScale: data.staffScale?data.staffScale.name:null,
                        yearlyTax: data.yearlyTax,
                        yearlyProfit: data.yearlyProfit,
                        monthlyIncome: data.monthlyIncome,
                        identity: data.identity ? data.identity.name : null,
                        identityOtherRemark: data.identityOtherRemark,
                        enterpriseNature: data.enterpriseNature?data.enterpriseNature.name:null,
                        enterpriseNatureRemark: data.enterpriseNatureRemark,
                        shareholdingRatio: data.shareholdingRatio,
                        monthlyWage: data.monthlyWage,
                        socialSecurity: data.socialSecurity,
                        regiterDate: data.regiterDate,
                        createTime: data.createTime,
                        lastUpdateTime: data.lastUpdateTime
                  }]
            }

            return enterpriseInfo ? enterpriseInfo : enterpriseInfoVOs;
      }

      /**
       * firstLinealKinVO
       */
      function createFirstLinealKin() {
            var data, firstLinealKin;

            if(arguments[0]){
            	for (var i = 0; i < arguments[0].length; i++) {
                    if (arguments[0][i].relationship.name === 'PARENT') {
                          data = arguments[0][i];
                          break;
                    }
              }
            }

            if (typeof(data) === 'object') {
                  firstLinealKin = {
                        id: data.id,
                        //loanId: data.loanId ? data.loanId: null,
                        customerId: data.customerId,
                        name: data.name,
                        gender: data.gender,
                        age: data.age,
                        mobile: data.mobile,
                        relationship: data.relationship?data.relationship.name:null,
                        companyName: data.companyName,
                        companyAddressVO: {
                              addressId:data.companyAddress.addressId,
                              id: data.companyAddress.id,
                              province: data.companyAddress.province,
                              city: data.companyAddress.city,
                              district: data.companyAddress.district,
                              street: data.companyAddress.street,
                              postCode: data.companyAddress.postCode
                        },
                        livingAddressVO: {
                              addressId:data.livingAddress.addressId,
                              id: data.livingAddress.id,
                              province: data.livingAddress.province,
                              city: data.livingAddress.city,
                              district: data.livingAddress.district,
                              street: data.livingAddress.street,
                              postCode: data.livingAddress.postCode
                        },
                        createTime: data.createTime,
                        lastUpdateTime: data.lastUpdateTime
                  }
            }

            return firstLinealKin ? firstLinealKin : firstLinealKinVO;
      }


      /**
       * secondLinealKinVO
       */
      function createSecondLinealKin() {
            var data, secondLinealKin, index = 0;
            
            if(arguments[0]) {
            	for (var i = 0; i < arguments[0].length; i++) {
                    if (arguments[0][i].relationship.name === 'PARENT') {
                          index++;
                    }

                    if (index > 1) {
                          data = arguments[0][i];
                          break;
                    }
              }
            }

            if (typeof(data) === 'object') {
                  secondLinealKin = {
                        id: data.id,
                        //loanId: data.loanId ? data.loanId: null,
                        customerId: data.customerId,
                        name: data.name,
                        gender: data.gender,
                        age: data.age,
                        mobile: data.mobile,
                        relationship: data.relationship?data.relationship.name:null,
                        companyName: data.companyName,
                        companyAddressVO: {
                              addressId:data.companyAddress.addressId,
                              id: data.companyAddress.id,
                              province: data.companyAddress.province,
                              city: data.companyAddress.city,
                              district: data.companyAddress.district,
                              street: data.companyAddress.street,
                              postCode: data.companyAddress.postCode
                        },
                        livingAddressVO: {
                              addressId:data.livingAddress.addressId,
                              id: data.livingAddress.id,
                              province: data.livingAddress.province,
                              city: data.livingAddress.city,
                              district: data.livingAddress.district,
                              street: data.livingAddress.street,
                              postCode: data.livingAddress.postCode
                        },
                        createTime: data.createTime,
                        lastUpdateTime: data.lastUpdateTime
                  }
            }

            return secondLinealKin ? secondLinealKin : secondLinealKinVO;
      }

      /**
       * friendVO
       */
      function createFriend() {
            var data, friend;

            if(arguments[0]){
            	for (var i = 0; i < arguments[0].length; i++) {
                    if (arguments[0][i].relationship.name === 'FRIEND') {
                          data = arguments[0][i];
                          break;
                    }
              }
            }

            if (typeof(data) === 'object') {
                  friend = {
                        id: data.id,
                        //loanId: data.loanId ? data.loanId: null,
                        customerId: data.customerId,
                        name: data.name,
                        gender: data.gender,
                        age: data.age,
                        mobile: data.mobile,
                        relationship: data.relationship?data.relationship.name:null,
                        companyName: data.companyName,
                        companyAddressVO: data.companyAddressVO,
                        livingAddressVO: data.livingAddressVO,
                        createTime: data.createTime,
                        lastUpdateTime: data.lastUpdateTime
                  }
            }

            return friend ? friend : friendVO;
      }


      /**
       * workmateVO
       */
      function createWorkmate() {
            var data, workmate;
            
            if(arguments[0]){
            	for (var i = 0; i < arguments[0].length; i++) {
                    if (arguments[0][i].relationship.name === 'COLLEAGUE') {
                          data = arguments[0][i];
                          break;
                    }
                }
            }

            if (typeof(data) === 'object') {
                  workmate = {
                        id: data.id,
                        //loanId: data.loanId ? data.loanId: null,
                        customerId: data.customerId,
                        name: data.name,
                        gender: data.gender,
                        age: data.age,
                        mobile: data.mobile,
                        relationship: data.relationship?data.relationship.name:null,
                        companyName: data.companyName,
                        companyAddressVO: data.companyAddressVO,
                        livingAddressVO: data.livingAddressVO,
                        createTime: data.createTime,
                        lastUpdateTime: data.lastUpdateTime
                  }
            }

            return workmate ? workmate : workmateVO;
      }


      /**
       * carInfoVO
       */
      function createCarInfo() {
            return carInfoVO;
      }


      /**
       * bankCardInfoVO
       */
      function createBankCardInfo() {
            return bankCardInfoVO;
      }

      /**
       * creditCardInfoVO
       */
      function createCreditCardInfo() {
            return creditCardInfoVO;
      }

      window.loanService = loanService;
}());