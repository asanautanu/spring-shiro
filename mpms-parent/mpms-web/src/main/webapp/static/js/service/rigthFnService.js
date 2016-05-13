/**
 * 右边功能服务
 */
(function (){
	var rigthFnService = {
		viewProgress: viewProgress
	};
	
	/**
	 * 查看进度
	 */
	function viewProgress (url, data, target){
		$('#viewProgressUl li').remove();
		var list = {}, dataUrl = url? globalPath + url: '', dataObj = data? data: {}, target = target ? $(target): '';
		
		restAjax(dataUrl, dataObj, "POST", function (rep){
			if(rep.code === 0){
				if(rep.data.list.length === 0){
					$('#viewProgressUl').append('<li class="list-group-item">暂无进度数据</li>');
				}else {
					for(var i=0; i<rep.data.list.length; i++){
						var loanLog = rep.data.list[i].loanLog;
						var date = loanLog.createDate,
							opreatorName = "操作人 [" + loanLog.opreatorName+ "] ",
							notes =  loanLog.notes ? " 备注[" + loanLog.notes + "] " : "",
							loanLogType =  loanLog.loanLogType.value;
							week = "("+ moment(loanLog.createDate).format('dddd') +") ";
						
						$('#viewProgressUl').append('<li class="list-group-item">'+ date + week + opreatorName + loanLogType + notes +'</li>');
					}
				}
			}
		}); 
		
		//return list;
	}
	
	window.rigthFnService = rigthFnService;
}());