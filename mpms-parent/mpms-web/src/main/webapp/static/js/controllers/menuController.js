$(function(){
	restAjax(globalPath + 'permission/menu.do', null, "GET", function (data) {
		if(data.code === 0){
			createMenus(data.data.resourceMenu);
		}
		if(data.code === 5000){
			layer.alert('请联系管理员配置权限！',  {icon: 5});
		}
	});
	
	/**
	 * 创建节点
	 */
	function createNode(node, target, index){
		$ul = $('<ul class="nav nav-second-level collapse"></ul>');
		
    	var $li = $('<li id="' + node.id + '"></li>'), 
    		$a = $('<a class="J_menuItem data-index="'+ node.id +'" >' + node.name + '</a>');
    	
    	if(node.url){
    		$a = $('<a class="J_menuItem" href="'+ node.url +'" data-index="'+ node.id +'" data-href="'+ node.url +'">' + node.name + '</a>')
    	}
    	
    	$a.click(handleTab);
    	
    	$li.append($a);		    	
    	$ul.append($li);
    	$(target).append($ul);
    	
    	//处理一级菜单单击事件
    	$li.click(function(e){
    		var _li = $(this),_ul = $("ul", _li);
    		// 收起
    		if (_li.hasClass("active")) {
    			_li.removeClass("active");
    			_ul.removeClass("in");
    			_ul.removeAttr("aria-expanded");
    		} else {// 展开
    			//收起其他展开的选项
    			var _selectedLi = $("#side-menu > li.active");
    			_selectedLi.removeClass("active");
    			var _uled = $("ul", _selectedLi);
    			_uled.removeClass("in");
    			_uled.removeAttr("aria-expanded");
    			
    			//展开
    			_li.addClass("active");
    			_ul.addClass("in");
    			_ul.attr("aria-expanded", "true");
    		}
    	});
	    
	}
	

	/**
	 * 创建父菜单
	 */
	function createMenus (menus, nodeLi) {
		if(!!nodeLi){
			$ul = $('<ul class="nav nav-second-level collapse"></ul>');
			
			for (var i = 0; i < menus.length; i++) {
				var $li = $('<li id="' + menus[i].id + '"></li>');
				
		    	if(menus[i].url && menus[i].node){
		    		$a = $('<a class="J_menuItem" href="'+ menus[i].url +'" data-index="'+ menus[i].id +'" data-href="'+ menus[i].url +'">' + menus[i].name + '<span class="fa arrow"></span> </a>');
		    	}else if(menus[i].node){
		    		$a = $('<a class="J_menuItem" href="'+ menus[i].url +'" data-index="'+ menus[i].id +'" data-href="'+ menus[i].url +'">' + menus[i].name + '</a>');
		    	}else {
		    		$a = $('<a class="J_menuItem data-index="'+ menus[i].id +'" >' + menus[i].name + '</a>');
		    	}
		    	
		    	$a.click(handleTab);
		    	
		    	$li.append($a);		    	
		    	$ul.append($li);
		    	$(nodeLi).append($ul);
		    	
		    	//处理一级菜单单击事件
		    	$li.click(function(e){
		    		var _li = $(this),_ul = $("ul", _li);
		    		// 收起
		    		if (_li.hasClass("active")) {
		    			_li.removeClass("active");
		    			_ul.removeClass("in");
		    			_ul.removeAttr("aria-expanded");
		    		} else {// 展开
		    			//收起其他展开的选项
		    			var _selectedLi = $("#side-menu > li.active");
		    			_selectedLi.removeClass("active");
		    			var _uled = $("ul", _selectedLi);
		    			_uled.removeClass("in");
		    			_uled.removeAttr("aria-expanded");
		    			
		    			//展开
		    			_li.addClass("active");
		    			_ul.addClass("in");
		    			_ul.attr("aria-expanded", "true");
		    		}
		    	});
		    	
		    	//处理下级菜单
		        var nodes = menus[i].node;
		        if (!!nodes) {
		            for (var j = 0; j < nodes.length; j++) {
		            	 createNode(nodes[j], $li, j);
		            }
		        }
		  }
		}else {
			for (var i = 0; i < menus.length; i++) {
		    	var $li = $('<li><a href="javascript:;"><i class="fa fa-table"></i> <span class="nav-label">' + menus[i].name + '</span><span class="fa arrow"></span></a>'+
		        '</li>'), $ul = $('<ul class="nav nav-second-level collapse"></ul>');
		    	// TODO 默认展开一个？
		    	
		    	//处理一级菜单单击事件
		    	$li.click(function(e){
		    		var _li = $(this),_ul = $("ul", _li);
		    		// 收起
		    		if (_li.hasClass("active")) {
		    			_li.removeClass("active");
		    			_ul.removeClass("in");
		    			_ul.removeAttr("aria-expanded");
		    		} else {// 展开
		    			//收起其他展开的选项
		    			var _selectedLi = $("#side-menu > li.active");
		    			_selectedLi.removeClass("active");
		    			var _uled = $("ul", _selectedLi);
		    			_uled.removeClass("in");
		    			_uled.removeAttr("aria-expanded");
		    			
		    			//展开
		    			_li.addClass("active");
		    			_ul.addClass("in");
		    			_ul.attr("aria-expanded", "true");
		    		}
		    	});
		    	$li.append($ul);
		        $('#side-menu').append($li);

		        //处理下级菜单
		        var nodes = menus[i].node;
		        if (!!nodes) {
		            for (var j = 0; j < nodes.length; j++) {
		                createMenuNode(nodes[j], $ul, j);
		            }
		        }
		    }
		}
	}

	/**
	 * 创建子菜单
	 */
	function createMenuNode (node, ul, index) {
		var $li =  $('<li id="' + node.id + '"></li>'),  $a;
		if (!node && !ul) {
	        return;
	    }
	    
	    if(node.url && node.node){
	    	$a = $('<a class="J_menuItem" href="'+ node.url +'" data-index="'+ index +'" data-href="'+ node.url +'">' + node.name + '<span class="fa arrow"></span> </a>');
	    }else if(node.url){
	    	$a = $('<a class="J_menuItem" href="'+ node.url +'" data-index="'+ index +'" data-href="'+ node.url +'">' + node.name + '</a>');
	    }else {
	    	$a = $('<a class="J_menuItem data-index="'+ node.id +'" >' + node.name + '</a>');
	    }
	    
	    $li.append($a);
	    $a.click(handleTab);
	    ul.append($li);
	    
	    if(node.node){
	    	var nodeLi = $li;
	    	createMenus(node.node, nodeLi);
	    }
	}
});