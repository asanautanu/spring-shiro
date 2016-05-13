
	/**
	 * 创建省 select
	 */
	
	(function (){
		var authorService = {
				getPreliminaryAuthor: getPreliminaryAuthor,
				getFinalAuthor: getFinalAuthor,
				getCreditQuailtyAuthor: getCreditQuailtyAuthor,
		};		
		
		
		/**
		 * 初审人员
		 */
		function getPreliminaryAuthor(){
			restAjax(globalPath +'user/firstaudit.do', null, "POST", function (data){
				if(data.code !== 0){
					return;
				}
				createOption($("select[name='preliminaryAuthor']"), data.data.users);
			});
		}
		/**
		 * 终审人员
		 */
		function getFinalAuthor(){
			restAjax(globalPath + 'user/finalaudit.do', null, "POST", function (data){
				if(data.code !== 0){
					return;
				}
				createOption($("select[name='finalAuthor']"), data.data.users);
			});
		}
		/**
		 * 质检人员
		 */
		function getCreditQuailtyAuthor(){
			restAjax(globalPath + 'user/quailtyudit.do', null, "POST", function (data){
				if(data.code !== 0){
					return;
				}
				createOption($("select[name='qualityAuthor']"), data.data.users);
			});
		}
		function createOption (){
			var target = arguments[0], 
				list = arguments[1];
			
			for(var i=0;i<list.length;i++){
                	$(target).append("<option value='"+ list[i]['id'] +"' id='"+ list[i].id + "'>"+ list[i]['name'] +"</option>");
			}
		}
		
		
		window.authorService = authorService;
	}());
	
