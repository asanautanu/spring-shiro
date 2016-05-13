var setting = {
    /*check: {
        enable: true,
        chkboxType: {"Y" : "s", "N" : "s"}
    },
    view: {
        dblClickExpand: false
    },*/
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

var zNodes =[
    { id:1, pId:0, name:"业务管理", open:true},
    { id:11, pId:1, name:"申请借款", open:true},
    { id:111, pId:11, name:"借款列表", open:true},
    { id:1111, pId:111, name:"上传附件", open:true},
    { id:1112, pId:111, name:"下载附件"},
    { id:2, pId:2, name:"风控中心管理", open:true},
    { id:21, pId:2, name:"初审人员"},
    { id:21, pId:2, name:"初审人员"},
    { id:3, pId:3, name:"运行中心管理", open:true}
 ];

className = "dark";
function beforeClick(treeId, treeNode) {
	className = (className === "dark" ? "":"dark");
	$('#salerInstitutionId').val(treeNode.id);
	$("#organizationInput").val(treeNode.name);
	
    return (treeNode.click != false);
    
    /*var zTree = $.fn.zTree.getZTreeObj("organizationTree");
    zTree.checkNode(treeNode, !treeNode.checked, null, true);
    return false;*/
}

function onCheck(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("organizationTree"),
    nodes = zTree.getCheckedNodes(true),
    v = "";
    for (var i=0, l=nodes.length; i<l; i++) {
        v += nodes[i].name + ",";
    }
    if (v.length > 0 ) v = v.substring(0, v.length-1);
    
    if (v) {
        $("#organizationInput").val('已选择组织');
    } else {
        $("#organizationInput").val('请选择组织');
    }
}

function showMenu() {
    var cityObj = $("#organizationInput");
    var cityOffset = $("#organizationInput").offset();
    $('.organizationTreeWrap').toggleClass("show", 1000);
    $("#organizationTree").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).toggleClass("show", 1000);
    $("body").bind("mousedown", onBodyDown);
}

function hideMenu() {
    $('.organizationTreeWrap').toggleClass("show", 1000);
    $("#organizationTree").toggleClass("show", 1000);
    $("body").unbind("mousedown", onBodyDown);
}

function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "organizationInput" || event.target.id == "organizationTree" || $(event.target).parents("#organizationTree").length>0)) {
        hideMenu();
    }
}

$(function(){
    $.fn.zTree.init($("#organizationTree"), setting, zNodes);
});