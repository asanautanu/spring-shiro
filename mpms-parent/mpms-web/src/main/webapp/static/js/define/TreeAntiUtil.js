var setting = {
    check: {
        enable: true,
        chkboxType: {"Y" : "s", "N" : "s"}
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
    v = "";
    var array=new Array();
    for (var i=0, l=nodes.length; i<l; i++) {
        v += nodes[i].name + ",";
        array[i]=nodes[i].id;
    }
    $("#resIds").val(array)
    if (v.length > 0 ) v = v.substring(0, v.length-1);

    console.info(v)
    if (v) {
        $("#organizationInput").val('已选择');
    } else {
        $("#organizationInput").val('请选择');
    }
}

function showMenu() {
    var cityObj = $("#organizationInput");
    var cityOffset = $("#organizationInput").offset();
    $('.organizationTreeWrap').slideDown("fast");
    $("#organizationTree").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
    $("body").bind("mousedown", onBodyDown);
}

function hideMenu() {
    $('.organizationTreeWrap').fadeOut("fast");
    $("#organizationTree").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);
}

function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "organizationInput" || event.target.id == "organizationTree" || $(event.target).parents("#organizationTree").length>0)) {
        hideMenu();
    }
}
