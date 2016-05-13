var institutions = [],
	zNodes = [];

var setting = {
	data: {
		simpleData: {
			enable: true
		}
	},
	callback: {
		beforeClick: beforeClick,
        onCheck: onCheck
	}
};
restAjax(globalPath +'institution/list.do', null, "GET", function (json){
	if(json.code === 0){
		institutions = json.data.institutions;
		
		getNode(institutions);
	}
}, null, "JSON", null, null, false);

function getNode(node){
	for(var i=0;i<node.length;i++){
        zNodes.push({
			id: node[i].id,
			pId: node[i].pId,
			name: node[i].name,
			departmentType:node[i].departmentType,
			institutionType: node[i].institutionType,
			userCount: node[i].userCount,
			address: node[i].address,
			tierCode: node[i].tierCode,
			open:true
		});
        
        _getNode(node[i]);
    }
}

function _getNode(data){
	if(!!data.node){
		getNode(data.node);
	}
}


className = "dark";
function beforeClick(treeId, treeNode) {
	className = (className === "dark" ? "":"dark");
	$("#parentId").val(treeNode.id);
	
	// 取到对应重载table 数据
	$('#table').bootstrapTable('load', randomData(treeNode));
}

//重载table 数据 方法
function randomData(treeNode) {
    var rows = [];

    rows.push({
    	id:treeNode.id,
    	departmentTypeValue:treeNode.departmentType.value,
    	institutionTypeValue: treeNode.institutionType.value,
    	province: treeNode.address.province,
    	city: treeNode.address.city,
    	region:treeNode.address.region,
    	userCount: treeNode.userCount,
    	institutionType: treeNode.institutionType,
    	departmentType: treeNode.departmentType,
    	address: treeNode.address,
    	pId: treeNode.pId,
    	tierCode: treeNode.tierCode
    });
    return rows;
}

function onCheck(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("organizationTree"),
    nodes = zTree.getCheckedNodes(true),
    v = "";
    for (var i=0, l=nodes.length; i<l; i++) {
        v += nodes[i].name + ",";
    }
    if (v.length > 0 ) v = v.substring(0, v.length-1);
}

$(function(){
    $.fn.zTree.init($("#organizationTree"), setting, zNodes);
});