var setting = {
    check: {
        enable: true,
        chkboxType: {"Y":"p", "N":"p"}
    },
    view: {
        dblClickExpand: false
    },
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

function beforeClick(treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("organizationTree");
    zTree.checkNode(treeNode, !treeNode.checked, null, true);
    return false;
}

function onCheck(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("organizationTree"),
    nodes = zTree.getCheckedNodes(true),
    v = "",
    list = [];
    
    for (var i=0, l=nodes.length; i<l; i++) {
    	list.push(nodes[i].id);
        v += nodes[i].name + ",";
    }
    
    resourceService.setResourceIds(list);
    
    if (v.length > 0 ) v = v.substring(0, v.length-1);

    var cityObj = $("#organizationInput");
    cityObj.attr("value", v);
}
