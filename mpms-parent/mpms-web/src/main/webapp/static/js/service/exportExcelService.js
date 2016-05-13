(function(){
	var exportExcelService = {
			exportPreliminaryLoan: exportPreliminaryLoan,
			exportCreditLoan:exportCreditLoan,
			exportFinalLoan:exportFinalLoan,
			exportFinalLoan:exportFinalLoan,
			exportReviewLoan:exportReviewLoan,
			exportCreditQualityLoan:exportCreditQualityLoan,
	}
	
	function exportPreliminaryLoan(){
		var loanIds = arguments[0];
		
		var iframe = $('<iframe id="fileDownFrame" src="" style="display:none; visibility:hidden;"></iframe>');//定义一个form表单
		iframe.attr("src",globalPath+"export/exportPreliminaryLoan.do?loanIds="+ loanIds.join(","));
		$("body").append(iframe);
	}
	function exportCreditLoan(){
		var loanIds = arguments[0];
		
		var iframe = $('<iframe id="fileDownFrame" src="" style="display:none; visibility:hidden;"></iframe>');//定义一个form表单
		iframe.attr("src",globalPath+"export/exportCreditLoan.do?loanIds="+ loanIds.join(","));
		$("body").append(iframe);
	}
	function exportFinalLoan(){
		var loanIds = arguments[0];
		
		var iframe = $('<iframe id="fileDownFrame" src="" style="display:none; visibility:hidden;"></iframe>');//定义一个form表单
		iframe.attr("src",globalPath+"export/exportFinalLoan.do?loanIds="+ loanIds.join(","));
		$("body").append(iframe);
	}
	function exportReviewLoan(){
		var loanIds = arguments[0];
		var iframe = $('<iframe id="fileDownFrame" src="" style="display:none; visibility:hidden;"></iframe>');//定义一个form表单
		iframe.attr("src",globalPath+"export/exportCreditReviewLoan.do?loanIds="+ loanIds.join(","));
		$("body").append(iframe);
	}
	function exportCreditQualityLoan(){
		var loanIds = arguments[0];
		var iframe = $('<iframe id="fileDownFrame" src="" style="display:none; visibility:hidden;"></iframe>');//定义一个form表单
		iframe.attr("src",globalPath+"export/exportCreditQualityLoan.do?loanIds="+ loanIds.join(","));
		$("body").append(iframe);
	}
	
	window.exportExcelService = exportExcelService;
}());