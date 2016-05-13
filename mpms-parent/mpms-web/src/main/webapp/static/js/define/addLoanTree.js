(function (){
	var addLoanTree = {
		showMenu: showMenu
	}
	
	
	var zNodes = [];

	restAjax(globalPath+'institution/findCurrentUserLowerSaleInstitution.do', null, "GET", function (group){
		if(group.code === 0 && group.data.institutions){
			var g = group.data.institutions;
			for(var i=0; i<g.length; i++){
				zNodes.push({
					id: g[i].id,
					pId: g[i].pId,
					name: g[i].name,
					level: g[i].level,
					open: true
				});
			}
		}
	}, null, "JSON", null, null, false);


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

	className = "dark";
	function beforeClick(treeId, treeNode) {
		className = (className === "dark" ? "":"dark");
		$('#salerInstitutionId').val(treeNode.id);
		$("#organizationInput").val(treeNode.name);
		
		saleService.createSale($('#salerInstitutionId').val());
		
	    //return (treeNode.click != false);
	    
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

	$(function(){
	    $.fn.zTree.init($("#organizationTree"), setting, zNodes);
	});
	
	
	window.addLoanTree = addLoanTree;
}());