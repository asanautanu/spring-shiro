/**
 * 权限管理模块
 */
$(function (){
	var initTtees = [],
    resourceIds = [],
	updateObj,
	isInit = true,
	isSearch = false,
	searchName,
	$table = $('#table'),
    $remove = $('#remove'),
    $create = $('#create'),
    $update = $('#update'),
    $btnSearch = $('#btnSearch'),
	$submit = $('#submit'),
	$retrieve = $('#retrieve'),
	$permissionName = $('#permissionName'),
	resourceIdsUpdate;

/**
 * 初始化弹出层
 */
function openModal(title) {
	// 禁用 BootStrap Modal 点击空白时自动关闭
	$('#myModal').modal({
		backdrop : 'static',
		keyboard : false
	});
	$("#myModal .modal-title").text(title);
}


/**
 * 监听搜索关键字变化
 */
$('#inputSearch').bind('input propertychange', function() {  
	searchName = $.trim($(this).val());
});

/**
 * 初始化树
 * @returns {Array}
 */
function initTteeFn(){
	resourceService.setResourceIds([]);
	var treeData = resourceService.getResource();
	if(treeData.length>0){
		initTtees = treeData;
		
		$.fn.zTree.init($("#organizationTree"), setting, initTtees);
	}
	return initTtees;
}


$(function () {
	/**
	 * 点击创建按钮事件
	 */
    $create.click(function () {
    	$('.modal-footer').find('button').show();
    	$("#permissionName").val("");
    	openModal("创建权限");
    	initTteeFn();
    });
    
    /**
	 * 点击查看按钮事件
	 */
    $retrieve.click(function (){
		var zTrre = initTteeFn();
		// 获取已选中的行
    	updateObj = $.map($table.bootstrapTable('getSelections'), function (row) {
            return row;
        });
		
		if(updateObj.length === 0){
			return layer.alert('请选择一条记录！', {icon: 2});
		}else if(updateObj.length > 1){
			return layer.alert('只能选择一条记录！', {icon: 2});
		}else {
			$permissionName.val(updateObj[0].name);
    		
			restAjax(globalPath +'permission/resourcebyrole.do', {roleId: updateObj[0].id}, "GET", function (json){
           		var resourceIds = json.data.roles;
           		
           		if(json.code === 0){
           			for(var i= 0; i<zTrre.length; i++){
           				
                		for(var j= 0; j<resourceIds.length; j++){
                			
                			if(zTrre[i].id === resourceIds[j]){
                				zTrre[i].checked = true,
                				zTrre[i].chkDisabled = true;
                    		}
                		}
                		
                	}
           		}
           		
           		$.fn.zTree.init($("#organizationTree"), setting, zTrre);
           	}, null, "JSON", {btn : $retrieve});
    		
			// 启动模态框 
			openModal('查看权限');
			$('.modal-footer').find('button').hide();
		}
    });
    
    /**
	 * 点击更新按钮事件
	 */
    $update.click(function () {
    	$('.modal-footer').find('button').show();
    	var zTrre = initTteeFn();
    	$("#permissionName").val("");
    	// 获取已选中的行
    	updateObj = $.map($table.bootstrapTable('getSelections'), function (row) {
            return row;
        });
    	
    	if(updateObj.length === 0){
			return layer.alert('请选择一条记录！', {icon: 2});
		}else if(updateObj.length > 1){
			return layer.alert('只能选择一条记录！', {icon: 2});
		}else{
			$permissionName.val(updateObj[0].name);
			restAjax(globalPath +'permission/resourcebyrole.do', {roleId: updateObj[0].id}, "GET", function (json){
           		var resourceIds = resourceIdsUpdate =  json.data.roles;
           		if(json.code === 0){
           			for(var i= 0; i<zTrre.length; i++){
           				
                		for(var j= 0; j<resourceIds.length; j++){
                			
                			if(zTrre[i].id === resourceIds[j]){
                				zTrre[i].checked = true;
                    		}
                		}
                		
                	}
           		}
           		
           		$.fn.zTree.init($("#organizationTree"), setting, zTrre);
           	}, null, "JSON", {btn : $update});
    		// 启动模态框 
			openModal('修改权限');
		}
    });

    /**
     * 点击删除按钮事件
     */
    $remove.click(function () {
    	// 获取已选中的行
		var delObj = $.map($table.bootstrapTable('getSelections'), function (row) {
            return row;
        });
    	
    	if(delObj.length === 0){
			return layer.alert('请选择一条记录！', {icon: 2});
		} else {				
			var dels = [];
			for(var i in delObj){
				dels.push(delObj[i].id);
			}
			
			
			layer.confirm('您确定要删除这条信息吗', {
			    btn: ['是的','取消']
			}, function(index){
				layer.close(index);
				restAjax(globalPath +'permission/del.do', {id: dels}, "GET", function (json){
            		if(json.code === 0){
            			layer.alert(json.msg,  {icon: 6});
            			$table.bootstrapTable('refresh');
            			
//            			for(var i in delObj){
//    						$table.bootstrapTable('remove', {
//	                            field: 'id',
//	                            values: delObj[i].id
//	                        });
//    					}
            			
            		}else {
            			layer.alert(json.msg,  {icon: 5});
            		}
            	}, null, "JSON", {btn : $remove})
			}, function(){
				layer.alert("您取消了删除操作！",  {icon: 2});
			});
		}
    });

    /**
     * 点击搜索按钮事件
     */
    $btnSearch.click(function () {
    	isInit = true;
		 	isSearch = true;
        $table.bootstrapTable('refresh');
    });
    
    /**
     * 点击提交按钮事件
     */
    $submit.click(function () {
    	var permissionName;
    	
    	// 为创建操作时
    	if($('.modal-title').text() === "创建权限"){
    		var name = $permissionName.val() ? $permissionName.val() : null;
    		var resourceIds = resourceService.getResourceIds();
    		
    		// 添加
    		if(name && resourceIds){
    			$permissionName.removeClass('error').val('');
    			
    			restAjax(globalPath +'permission/add.do', {name:name, resourceIds: resourceIds}, "POST", function (json){
        			if(json.code === 0){
        				$('#myModal').modal('hide');
        				layer.alert(json.msg,  {icon: 6});
        				$table.bootstrapTable('refresh');
        				sessionStorage.permiessions = "";
        			}else {
        				layer.alert(json.msg,  {icon: 5});
        			}
        		}, null, "JSON", {btn : $submit});
    		}else {
    			$permissionName.addClass('error');
    		}
    		
    	}
    	
    	// 为修改操作时
    	if($('.modal-title').text() === "修改权限"){
    		var rIds = [];
    		name = $permissionName.val();
    		var rIds = resourceService.getResourceIds();
    		
    		var tableData = $table.bootstrapTable('getData');
    		
    		// 判断是否更新权限值 
    		if(rIds.length < 1){
    			rIds = resourceIdsUpdate
    		}
    		
    		if(name && updateObj[0].id){
    			$permissionName.removeClass('error');
    			
    			restAjax(globalPath +'permission/update.do', {name:name, id:updateObj[0].id , resourceIds: rIds}, "POST", function (json){
        			if(json.code === 0){
        				sessionStorage.permiessions = "";
        				$('#myModal').modal('hide');
        				layer.alert(json.msg, {icon: 6});
        				$table.bootstrapTable('refresh');
        				// 修改table数据
        				for(var i=0; i<tableData.length; i++) {
        					if(tableData[i].id === updateObj[0].id){
        						$table.bootstrapTable('updateRow', {
            	                    index: i,
            	                    row: {
            	                        name: json.data.data.name,
            	                        creater: json.data.data.creater,
            	                        createDate: json.data.data.createDate,
            	                        updateDate: json.data.data.updateDate
            	                    }
            	                });
        					}
        				}
        			}else {
        				layer.alert(json.msg, {icon: 5});
        			}
        		}, null, "JSON", {btn : $submit});
    			
    		}else {
    			$permissionName.addClass('error');
    		}
    	}
    	
    	// 为修改操作时
    	if($('.modal-title').text() === "修改权限"){
    		permissionName = $permissionName.val();
    	}
    	
    });
});

/**
 * 表格序号
 */
function addIndex (value, row, index) {
    var index = ++index;
    return index;
}

var tableObj = {
	table : $table,
	url : globalPath +'permission/list.do',
	queryParams: queryParams,
    showRefresh: true,
    responseHandler: responseHandler,
    columns: [
		{
            field: 'state',
            checkbox: true
    	}, {
			title : '序号',
			formatter : addIndex, 
		}, {
			field: 'id',
            title: 'id',
			visible: false
		},{
            field: 'name',
            title: '权限名称',
            sortable: true
        }, {
            field: 'createrName',
            title: '创建人',
            sortable: true
        }, {
            field: 'createDate',
            title: '创建时间',
            sortable: true
        }, {
            field: 'updateDate',
            title: '更新时间',
            sortable: true
        }
    ]
};

tableService.init(tableObj);

/**
 * 表格init
 */
/*$table.bootstrapTable({
	url : globalPath +'permission/list.do',
	contentType: 'application/x-www-form-urlencoded',
	method : 'get',
	striped: true,
	toolbar : "#toolbar",
	dataField : "list", // 获取数据key对应名称
    pagination: true,
    pageList: [10, 25, 50, 100],
    pageSize:10,
    pageNumber:1,
    sidePagination : "server", 
    queryParams: queryParams,
    showRefresh: true,
    responseHandler: responseHandler,
    columns: [
		{
            field: 'state',
            checkbox: true
    	}, {
			title : '序号',
			formatter : addIndex, 
		}, {
			field: 'id',
            title: 'id',
			visible: false
		},{
            field: 'name',
            title: '权限名称',
            sortable: true
        }, {
            field: 'createrName',
            title: '创建人',
            sortable: true
        }, {
            field: 'createDate',
            title: '创建时间',
            sortable: true
        }, {
            field: 'updateDate',
            title: '更新时间',
            sortable: true
        }
    ]
});*/

/**
 * 获取回调数据
 */
function responseHandler(res) {
	if(isInit){
		return {
		 "list": res.list,
		 "total": res.total
		 };
		
	} else {
		 return {
			 "list": [],
			 "total": 0
		};
	}
}



/**
 * table传递的参数
 * @param params
 * @returns
 */
function queryParams(params) {
	if(isInit){
		if(isSearch){ //点击搜索按钮
			isSearch = false;
			if(searchName !== ""){ // 当搜索框不为空时
				return {
					pageSize: params.limit,
					pageNo: params.pageNumber,
					name: searchName
				}
			}
			
			return {
				pageSize: params.limit,
				pageNo: 1,
				name:null
			}
		} else {// 翻页
			return {
				pageSize: params.limit,
				pageNo: params.pageNumber,
				name:null
			}
		}
		
	}else {
		return {
			pageSize: params.limit,
			pageNo: params.pageNumber
		}
	}
};


});