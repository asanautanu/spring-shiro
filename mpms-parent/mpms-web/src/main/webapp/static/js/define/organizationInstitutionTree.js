function beforeClick(treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("organizationInstitutionTree");
    zTree.checkNode(treeNode, !treeNode.checked, null, true);
    return false;
}

function onCheck(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("organizationInstitutionTree"),
    nodes = zTree.getCheckedNodes(true),
    v = "",
    ids = [],
    cityObj = $("#organizationInstitutionInput");
    
    for (var i=0, l=nodes.length; i<l; i++) {
        v += nodes[i].name + ",";
        ids.push(nodes[i].id);
    }
    
    if (v.length > 0 ){
    	 v = v.substring(0, v.length-1);
    	 cityObj.attr("value", '已选择');
    	 
    	 // 写入 选中的id
    	 organizationInstitutionService.setOrganizationInstitution(ids);
    }else {
    	cityObj.attr("value", '未选择');
    };
}

function showMenu() {
    var cityObj = $("#organizationInstitutionInput");
    var cityOffset = $("#organizationInstitutionInput").offset();
    $('.organizationTreeWrap').slideDown("fast");
    $("#organizationInstitutionTree").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
    $("body").bind("mousedown", onBodyDown);
}

function hideMenu() {
    $('.organizationTreeWrap').fadeOut("fast");
    $("#organizationInstitutionTree").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);
}

function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "organizationInstitutionInput" || event.target.id == "organizationInstitutionTree" || $(event.target).parents("#organizationInstitutionTree").length>0)) {
        hideMenu();
    }
}

//$(function (){$.fn.zTree.init($("#organizationInstitutionTree"), setting, zNodes);});

