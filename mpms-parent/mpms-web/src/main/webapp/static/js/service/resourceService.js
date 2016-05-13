/**
 * 权限树服务
 */
(function(){
	var resourceList,
		resourceIds = [];
	
	var resourceService = {
			getResource: getResource,
			setResourceIds: setResourceIds,
			getResourceIds: getResourceIds,
	};
	
	
	/**
	 * 	getResource
	 *  return list
	 */
	function getResource (){
		restAjax(globalPath +'permission/resourcelist.do', null, "GET", function (json){
			if(json.data.resourceList){
				for(var i = 0;i<json.data.resourceList.length;i++){
					if(json.data.resourceList[i].url){
						json.data.resourceList[i].url = null;
					}
				}
				
				resourceList = json.data.resourceList;
			}
		}, null, "JSON", null, null, false);
		
		return resourceList;
	}
	
	
	/**
	 * setResourceIds
	 * return list
	 */
	function setResourceIds (list){
			resourceIds = list;
		return resourceIds;
	}
	
	/**
	 * getResourceIds
	 * return list
	 */
	function getResourceIds (){
		return resourceIds ? resourceIds : null;
	}
	
	window.resourceService = resourceService;
}());