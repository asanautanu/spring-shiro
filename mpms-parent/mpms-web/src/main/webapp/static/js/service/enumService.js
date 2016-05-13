
	/**
	 * 获取enum
	 */
	
	(function (){
		var enumService = {
				getAuthListEnum: getAuthListEnum,
				getPreliminaryAuthListEnum: getPreliminaryAuthListEnum,
				getPreliminaryTaskListEnum: getPreliminaryTaskListEnum,
				getFinalAuthListEnum: getFinalAuthListEnum,
				getFinalTaskListEnum: getFinalTaskListEnum,
				getLoanFinalAuditResultEnum: getLoanFinalAuditResultEnum,
				getLoanManageEnum: getLoanManageEnum,
				getQuailtyManageEnum: getQuailtyManageEnum,
				getConstractAuthEnum: getConstractAuthEnum,
				getFianlAuthEnum: getFianlAuthEnum,
				getOperationAuthEnum: getOperationAuthEnum,
				getOperationRepeatAuthEnum:getOperationRepeatAuthEnum,
				getBankChangeRequestEnum: getBankChangeRequestEnum,
				getCreditQuailtyMangeEnum:getCreditQuailtyMangeEnum,
				getApplyListEnum:getApplyListEnum,
				getStopReason:getStopReason,
				getSMSTemplateType:getSMSTemplateType,
		};		
		
		
		/**
		 * 初审人员
		 */
		function getAuthListEnum(){
			restAjax(globalPath +'enum/loanStatus.do', {functionEnumType : "AUTH_MENU_LIST"}, "POST", function (data){
				if(data.code !== 0){
					return;
				}
				createOption($("select[name='loanStatus']"), data.data.list);
			});
		}
		
		function getPreliminaryAuthListEnum(){
			restAjax(globalPath +'enum/loanStatus.do', {functionEnumType : "PRELIMINARY_AUTH_LIST"}, "POST", function (data){
				if(data.code !== 0){
					return;
				}
				createOption($("select[name='loanStatus']"), data.data.list);
			});
		}
		function getPreliminaryTaskListEnum(){
			restAjax(globalPath +'enum/loanStatus.do', {functionEnumType : "PRELIMINARY_TASK_LIST"}, "POST", function (data){
				if(data.code !== 0){
					return;
				}
				createOption($("select[name='loanStatus']"), data.data.list);
			});
		}
		function getFinalAuthListEnum(){
			restAjax(globalPath +'enum/loanStatus.do', {functionEnumType : "FINAL_AUTH_LIST"}, "POST", function (data){
				if(data.code !== 0){
					return;
				}
				createOption($("select[name='loanStatus']"), data.data.list);
			});
		}
		function getFinalTaskListEnum(){
			restAjax(globalPath +'enum/loanStatus.do', {functionEnumType : "FINAL_TASK_LIST"}, "POST", function (data){
				if(data.code !== 0){
					return;
				}
				createOption($("select[name='loanStatus']"), data.data.list);
			});
		}
		function getLoanFinalAuditResultEnum(){
			restAjax(globalPath +'enum/getEnableLoanAuditResult.do', {functionEnumType : "FINAL_AUDIT_RESULT"}, "GET", function (data){
				if(data.code !== 0){
					return;
				}
				createOption($("select[name='loanAuditResult']"), data.data.list);
			});
		}
		function getLoanManageEnum(){
			restAjax(globalPath +'enum/loanStatus.do', {functionEnumType : "LOAN_MANAGE_MENU_LIST"}, "POST", function (data){
				if(data.code !== 0){
					return;
				}
				createOption($("select[name='loanStatus']"), data.data.list);
			});
		}
		function getQuailtyManageEnum(){
			restAjax(globalPath +'enum/loanStatus.do', {functionEnumType : "QUALITY_AUTH_LIST"}, "POST", function (data){
				if(data.code !== 0){
					return;
				}
				createOption($("select[name='loanStatus']"), data.data.list);
			});
		}
		function getConstractAuthEnum(){
			restAjax(globalPath +'enum/loanStatus.do', {functionEnumType : "CONSTRACT_AUTH_LIST"}, "POST", function (data){
				if(data.code !== 0){
					return;
				}
				createOption($("select[name='loanStatus']"), data.data.list);
			});
		}
		
		function getFianlAuthEnum(){
			restAjax(globalPath +'enum/loanStatus.do', {functionEnumType : "FINAL_AUTH_STATU"}, "POST", function (data){
				if(data.code !== 0){
					return;
				}
				createOption($("select[name='loanStatus']"), data.data.list);
			});
		}
		
		function getOperationAuthEnum(){
			restAjax(globalPath +'enum/loanStatus.do', {functionEnumType : "OPEARTION_AUTH_STATU"}, "POST", function (data){
				if(data.code !== 0){
					return;
				}
				createOption($("select[name='loanStatus']"), data.data.list);
			});
		}
		
		function getBankChangeRequestEnum(){
			restAjax(globalPath +'enum/loanStatus.do', {functionEnumType : "BANK_CHANGE_REQUEST_STATU"}, "POST", function (data){
				if(data.code !== 0){
					return;
				}
				createOption($("select[name='loanStatus']"), data.data.list);
			});
		}
		
		function getCreditQuailtyMangeEnum(){
			restAjax(globalPath +'enum/loanStatus.do', {functionEnumType : "CREDIT_QUAILTY_MENU_STATUS"}, "POST", function (data){
				if(data.code !== 0){
					return;
				}
				createOption($("select[name='loanStatus']"), data.data.list);
			});
		}
		function getApplyListEnum() {
			restAjax(globalPath +'enum/loanStatus.do', {functionEnumType : "APPLY_LIST"}, "POST", function (data){
				if(data.code !== 0){
					return;
				}
				createOption($("select[name='loanStatus']"), data.data.list);
			});
		}
		
		function getOperationRepeatAuthEnum(){
			restAjax(globalPath +'enum/loanStatus.do', {functionEnumType : "OPEARTION_REPEATAUTH_STATU"}, "POST", function (data){
				if(data.code !== 0){
					return;
				}
				createOption($("select[name='loanStatus']"), data.data.list);
			});
		}
		
		
		
		
		function getStopReason() {
			restAjax(globalPath +'enum/getStopReason.do', null, "POST", function (data){
				if(data.code !== 0){
					return;
				}
				createOption($("select[name='firstLevelType']"), data.data.list);
			});
		}
		function getSMSTemplateType() {
			restAjax(globalPath +'enum/getSMSTemplateType.do', null, "POST", function (data){
				if(data.code !== 0){
					return;
				}
				createOption($("select[name='smsType']"), data.data.list);
			});
		}
		
		
		function createOption (){
			var target = arguments[0], 
				list = arguments[1];
			
			for(var i=0;i<list.length;i++){
                	$(target).append("<option value='"+ list[i]['name'] +"' id='"+ list[i]['name'] + "'>"+ list[i]['value'] +"</option>");
			}
		}
		
		
		window.enumService = enumService;
	}());
	
