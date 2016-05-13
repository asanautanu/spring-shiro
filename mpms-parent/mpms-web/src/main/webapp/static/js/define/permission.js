$(function(){
	if (!sessionStorage.permiessions) {
		restAjax(globalPath + "permission/button.do", null, "GET", getPermiesions);
	} else {
		handlePermiession(sessionStorage.permiessions);
	}
});

/**
 * get current user's permiessions
 */
function getPermiesions(data){
	if (data && data.data) {
		sessionStorage.permiessions = JSON.stringify(data.data);
		handlePermiession(sessionStorage.permiessions);
	} else {
		console.log("获取权限信息失败！");
	}
}

/**
 * handle html buttons permiession
 * @param permiessions
 * @returns {Boolean}
 */
function handlePermiession(permiessions) {
	if (!permiessions) {
		return false;
	}
	permiessions = JSON.parse(permiessions);
	// 获取当前页面里面含有permission-data的元素
	$("button[permission-data]").each(function(i,button){
		var key = $(this).attr("permission-data");
		if (permiessions[key]) {
			$(this).removeClass("hide");
		}
	});
}