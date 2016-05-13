
	/**
	 * 获取系统设置
	 */
	
	(function (){
		var systemSettingService = {
				getQualityDistribution:getQualityDistribution,
				setQualityDistribution:setQualityDistribution,
				getPreliminaryDistribution: getPreliminaryDistribution,
				setPreliminaryDistribution: setPreliminaryDistribution,
				getFinalDistribution: getFinalDistribution,
				setFinalDistribution: setFinalDistribution,
				getLoanQualityDistribution:getLoanQualityDistribution,
				setLoanQualityDistribution:setLoanQualityDistribution,
				getOperationPreliminaryDistribution:getOperationPreliminaryDistribution,
				setOperationPreliminaryDistribution:setOperationPreliminaryDistribution,
				getOperationRepeatDistribution:getOperationRepeatDistribution,
				setOperationRepeatDistribution:setOperationRepeatDistribution,
		};		
		
		/**
		 * 质检自动分配
		 */
		function getQualityDistribution(){
			restAjax(globalPath +'sys/findAutoDistribution.do', {systemParameterType : "BUSINESS_QT_AUTO_DISTRIBUTUIB"}, "POST", function (data){
				if(data.code !== 0){
					return;
				}
				createOption($("#autoDistribution"), data.data.systemParameters);
			});
		}
		function setQualityDistribution(){
			restAjax(globalPath +'sys/setAutoDistribution.do', {
				systemParameterType:'BUSINESS_QT_AUTO_DISTRIBUTUIB',
				value:function(){
					return !$("#autoDistribution").attr('checked');
				}
			}, "POST", function (data){
				if(data.code !== 0){
					return;
				}
			});
		}
		function getLoanQualityDistribution(){
			restAjax(globalPath +'sys/findAutoDistribution.do', {systemParameterType : 'CREDIT_QT_AUTO_DISTRIBUTUIB'}, "POST", function (data){
				if(data.code !== 0){
					return;
				}
				createOption($("#autoDistribution"), data.data.systemParameters);
			});
		}
		function setLoanQualityDistribution(){
			restAjax(globalPath +'sys/setAutoDistribution.do', {
				systemParameterType:'CREDIT_QT_AUTO_DISTRIBUTUIB',
				value:function(){
					return !$("#autoDistribution").attr('checked');
				}
			}, "POST", function (data){
				if(data.code !== 0){
					return;
				}
			});
		}
		/**
		 * 初审自动分配
		 */
		function getPreliminaryDistribution(){
			restAjax(globalPath +'sys/findAutoDistribution.do', {
				systemParameterType:'CREDIT_FIRST_AUTO_DISTRIBUTUIB'
			}, "POST", function (data){
				if(data.code !== 0){
					return;
				}
				createOption($("#autoDistribution"), data.data.systemParameters);
			});
		}
		function setPreliminaryDistribution(){
			restAjax(globalPath +'sys/setAutoDistribution.do', {
				systemParameterType:'CREDIT_FIRST_AUTO_DISTRIBUTUIB',
				value:function(){
					return !$("#autoDistribution").attr('checked');
				}
			}, "POST", function (data){
				if(data.code !== 0){
					return;
				}
			});
		}
	
		/**
		 * 终审自动分配
		 */
		function getFinalDistribution(){
			restAjax(globalPath +'sys/findAutoDistribution.do', {
				systemParameterType:'CREDIT_FINAL_AUTO_DISTRIBUTUIB'
			}, "POST", function (data){
				if(data.code !== 0){
					return;
				}
				createOption($("#autoDistribution"), data.data.systemParameters);
			});
		}
		function setFinalDistribution(){
			restAjax(globalPath +'sys/setAutoDistribution.do', {
				systemParameterType:'CREDIT_FINAL_AUTO_DISTRIBUTUIB',
				value:function(){
					return !$("#autoDistribution").attr('checked');
				}
			}, "POST", function (data){
				if(data.code !== 0){
					return;
				}
			});
		}
		
		function getOperationPreliminaryDistribution(){
			restAjax(globalPath +'sys/findAutoDistribution.do', {
				systemParameterType:'CONTRACT_FIRST_AUTO_DISTRIBUTUIB'
			}, "POST", function (data){
				if(data.code !== 0){
					return;
				}
				createOption($("#autoDistribution"), data.data.systemParameters);
			});
		}
		function setOperationPreliminaryDistribution(){
			restAjax(globalPath +'sys/setAutoDistribution.do', {
				systemParameterType:'CONTRACT_FIRST_AUTO_DISTRIBUTUIB',
				value:function(){
					return !$("#autoDistribution").attr('checked');
				}
			}, "POST", function (data){
				if(data.code !== 0){
					return;
				}
			});
		}
	
		
		function getOperationRepeatDistribution(){
			restAjax(globalPath +'sys/findAutoDistribution.do', {
				systemParameterType:'CONTRACT_FINAL_AUTO_DISTRIBUTUIB'
			}, "POST", function (data){
				if(data.code !== 0){
					return;
				}
				createOption($("#autoDistribution"), data.data.systemParameters);
			});
		}
		function setOperationRepeatDistribution(){
			restAjax(globalPath +'sys/setAutoDistribution.do', {
				systemParameterType:'CONTRACT_FINAL_AUTO_DISTRIBUTUIB',
				value:function(){
					return !$("#autoDistribution").attr('checked');
				}
			}, "POST", function (data){
				if(data.code !== 0){
					return;
				}
			});
		}
		
		function createOption (){
			var target = arguments[0], 
			systemParameters = arguments[1];
			if(systemParameters.value==="true"){
				$(target).removeAttr("checked");
			}else{
				$(target).attr('checked', true);
			}
		}
		
		
		window.systemSettingService = systemSettingService;
	}());
	
