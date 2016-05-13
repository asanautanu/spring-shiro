/**
 * 切换 Tab 间传递 ID 服务
 */
(function (){
	var ids = null;
	
	var tabPassIdService = {
			getIds: getIds,
			setIds: setIds
	};
	
	/**
	 * getIds
	 */
	function getIds(){
		if(ids){
			return ids;
		}
	}
	
	/**
	 * setIds
	 */
	function setIds(id){
		if(id){
			return ids = id;
		}
	}
	
	window.tabPassIdService = tabPassIdService;
}());