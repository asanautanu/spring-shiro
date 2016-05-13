/**
 * @author Sunset
 * @date 2016/03/10
 */
var tableService = (function(){
	var tableObj = {};
	tableObj.init = function(_) {
		if (!_ || typeof _ !== "object" || !_[ "url" ] || !_[ "columns" ]) return null;
		_[ "table" ] = _[ "table" ] || $("#table");
		_[ "showRefresh" ] = _[ "showRefresh" ] === false ? _[ "showRefresh" ] : true;
		_[ "pagination" ] = _[ "pagination" ] === false ? _[ "pagination" ] : true;
		_[ "striped" ] = _[ "striped" ]  === false ? _[ "striped" ] : true;
		return _[ "table" ].bootstrapTable({
			url 		    : _[ "url" ],
			method 		    : _[ "method" ] || "get",
		    contentType     : _[ "contentType" ] || 'application/x-www-form-urlencoded',
			dataField       : _[ "dataField" ] || "list",
			showRefresh     : _[ "showRefresh" ],// 存在Bug如果传入的是false判断无效
			search			: _[ "search" ] || false,
			sidePagination  : _[ "sidePagination" ] || "server",
			toolbar 	    : _[ "toolbar" ] || "#toolbar",
			singleSelect    : _[ "singleSelect" ] || false,
			pagination      : _[ "pagination" ],
			striped         : _[ "striped" ],
			pageList        : _[ "pageList" ] || [500, 200, 100, 50, 25, 10],
		    pageSize 	    : _[ "pageSize" ] || 50,
		    pageNumber 	    : _[ "pageNumber" ] || 1,
			queryParams     : _[ "queryParams" ],
//			smartDisplay    : _[ "smartDisplay" ] || true,
		    responseHandler : _[ "responseHandler" ],
			columns 		: _[ "columns" ],
			onLoadSuccess   : _[ "onLoadSuccess" ] || function(data) {console.log("loadSuccess");},
			onLoadError     : _[ "onLoadError" ] || function(data) {console.log('loadError');},
			onExpandRow     : _[ "onExpandRow" ] || function(index, row, $detail){console.log('expandRow');}
		});
	};
	return tableObj;
})();