/**
 * 查看附件树
 */
(function() {
	var zNodes = [],
		setting = {
			view: {
				addHoverDom: addHoverDom,
				removeHoverDom: removeHoverDom
			},
			data: {
				simpleData: {
					enable: true
					//showRemoveBtn: showRemoveBtn
				}
			},
			callback: {
				beforeClick: beforeClick,
		        onCheck: onCheck
		        //onRemove: onRemove,
				//beforeRemove: beforeRemove
			}
		};
	
	var attachmentTree = {
		createAttachmentTree : createAttachmentTree
	};
	
	var suffix, url, spliceUrl, processFilePath, localFilePath = globalPath + 'wopi/files/';  //'http://192.168.0.116:8080/lyc_rest_credit/wopi/files/';
	
	function getNode(node, pid, _i){
		// 判断预览文件是否为单个
		if(node.constructor === Array){
			for(var i=0;i<node.length;i++){
				if(node[i].filePath){
					suffix = node[i].filePath.substring(node[i].filePath.lastIndexOf(".")+1);
					
					// 判断是否为压缩包
					if(suffix !== 'zip' && suffix !== 'rar'){
						
						// 如果为 xlsx or docx
						if(suffix === 'xlsx' || suffix === 'docx' || suffix === 'pptx' || suffix === 'doc' || suffix === 'xls' || suffix === 'ppt'){
							processFilePath = localFilePath + encodeURIComponent(Base64.encode(node[i].filePath)); 
							
							switch(suffix)
							{
								case 'xlsx' || 'xls':
									spliceUrl =  viewGlobalPath_xlsx  + processFilePath + '.' + suffix + '?access_token=23432';
								    break;
								case 'docx' || 'doc':
									spliceUrl =  viewGlobalPath_docx  + processFilePath + '.' + suffix + '?access_token=23432';
								    break;
								case 'pptx' || 'ppt':
									spliceUrl =  viewGlobalPath_pptx  + processFilePath + '.' + suffix + '?access_token=23432';
								    break;
							}
							
							url = spliceUrl;
						}else {
							url = globalPath + 'view/view.' + suffix + '?filePath=' + node[i].filePath;
						}
						
					}else {
						url = null;
					}
					
				} else {
					suffix = node[i].substring(node[i].lastIndexOf(".")+1);
					// 判断是否为压缩包
					if(suffix !== 'zip' && suffix !== 'rar' && node[i].lastIndexOf(".") > 0){
						
						// 如果为 xlsx or docx
						if(suffix === 'xlsx' || suffix === 'docx' || suffix === 'pptx' || suffix === 'doc' || suffix === 'xls' || suffix === 'ppt'){
							processFilePath = localFilePath + encodeURIComponent(Base64.encode(node[i]));
							
							switch(suffix)
							{
								case 'xlsx' || 'xls':
									spliceUrl =  viewGlobalPath_xlsx  + processFilePath + '.' + suffix + '?access_token=23432';
								    break;
								case 'docx' || 'doc':
									spliceUrl =  viewGlobalPath_docx  + processFilePath + '.' + suffix + '?access_token=23432';
								    break;
								case 'pptx' || 'ppt':
									spliceUrl =  viewGlobalPath_pptx  + processFilePath + '.' + suffix + '?access_token=23432';
								    break;
							}
							
							url = spliceUrl;
						}else {
							url = globalPath + 'view/view.' + suffix + '?filePath=' + node[i];
						}
					}else {
						url = null;
					}
				}
				// 判断是否为图片
				if(suffix == 'png' || suffix == 'jpg' || suffix == 'bmp' || suffix == 'jpeg' || suffix == 'gif' || suffix == 'ico' || suffix == ''){
					url = null;
				}
				
				// 判断是否有父集
				if(pid){
					zNodes.push({
						id:  node[i].id ? node[i].id : pid + "-" + _i,
						pId: pid,
						name: node[i].substring(node[i].lastIndexOf("/")+1),
						filePath: node[i],
						url: url,
						relationId: node[i].relationId ? node[i].relationId : null
					});
					
					
					// 如果为文件夹
					if(node[i].lastIndexOf(".") <= 0){
						$.ajax({
							url: globalPath + 'attachment/listFile.do',
							dataType: 'json',
							data:{path: node[i]},
							async: false
						}).done(function (rep){
							if(rep.data.list){
								var listTmp = rep.data.list;
								var list=[];
								for (var count = 0; count < listTmp.length; count++) {
									if(rep.data.list[count] != node[i]){
										// 当为压缩文件时
										if(rep.data.list[count].substring(rep.data.list[count].lastIndexOf(".")+1) !== 'zip'){
											list.push(rep.data.list[count]);
										}
									}
								}
								getNode(list, node[i].id ? node[i].id : pid + "-" + _i);
							}
						});
					}
					
					
				}else {
					var index = i+1;
					
					zNodes.push({
						id: node[i].id ? node[i].id : index,
						pId: null,
						name: node[i].fileName,
						filePath: node[i].filePath,
						open:true,
						url: url,
						relationId: node[i].relationId ? node[i].relationId : null
					});
					
					if(node[i].paths){
						_getNode(node[i].paths, node[i].id ? node[i].id : index);
					}
				}
		    }
		}else {
			if(typeof(node) === "string"){
				zNodes.push({
					id: node.id ? node.id : pid + "-" + _i,
					pId: pid,
					name: node.substring(node.lastIndexOf("/")+1),
					filePath: node,
					url: null, 
					open: true
				});
				
				$.ajax({
					url: globalPath + 'attachment/listFile.do',
					dataType: 'json',
					data:{path: node},
					async: false
				}).done(function (rep){
					if(!rep.data){
						return;
					}
					var listTmp=rep.data.list;
					var list=[];
					for (var count = 0; count < listTmp.length; count++) {
						if(rep.data.list[count]!= node[i]){
							list.push(rep.data.list[count]);
						}
					}
					getNode(list, node.id ? node.id : pid + "-" + _i);
				});
			} else {
				if(node.loanAttachment){
					 suffix = node.loanAttachment.filePath.substring(node.loanAttachment.filePath.lastIndexOf(".")+1);
				}else{
					suffix = suffix = node.filePath.substring(node.filePath.lastIndexOf(".")+1);
				}
				
				// 如果为 xlsx or docx
				if(suffix === 'xlsx' || suffix === 'docx' || suffix === 'pptx' || suffix === 'doc' || suffix === 'xls' || suffix === 'ppt'){
					processFilePath = localFilePath + encodeURIComponent(Base64.encode(node.filePath)); 
					
					switch(suffix)
					{
						case 'xlsx' || 'xls':
							spliceUrl =  viewGlobalPath_xlsx  + processFilePath + '.' + suffix + '?access_token=23432';
						    break;
						case 'docx' || 'doc':
							spliceUrl =  viewGlobalPath_docx  + processFilePath + '.' + suffix + '?access_token=23432';
						    break;
						case 'pptx' || 'ppt':
							spliceUrl =  viewGlobalPath_pptx  + processFilePath + '.' + suffix + '?access_token=23432';
						    break;
					}
					
					url = spliceUrl;
				}else if (suffix == 'png' || suffix == 'jpg' || suffix == 'bmp' || suffix == 'jpeg' || suffix == 'gif' || suffix == 'ico' || suffix == '' || suffix =='zip'){
					url = null;
				}else {
					url = globalPath + 'view/view.' + suffix + '?filePath=' + (!node.loanAttachment ? node.filePath : node.loanAttachment.filePath);
				}
				
				// 判断是否为图片
//				if(suffix == 'png' || suffix == 'jpg' || suffix == 'bmp' || suffix == 'jpeg' || suffix == 'gif' || suffix == 'ico' || suffix == '' || suffix =='zip'){
//					url = null;
//				}else {
//					url = globalPath + 'view/view.' + suffix + '?filePath=' + (!node.loanAttachment ? node.filePath : node.loanAttachment.filePath);
//				}
				
				zNodes.push({
					id: node.loanAttachment ? node.loanAttachment.id : node.id,
					pId: null,
					name: node.loanAttachment ? node.loanAttachment.fileName : node.fileName,
					filePath: node.loanAttachment ?  node.loanAttachment.filePath : node.filePath,
					url: url,
					relationId: node.relationId ? node.relationId : null
				});
			}
		}
		
		$.fn.zTree.init($("#attachmentTree"), setting, zNodes);
	}
	
	function _getNode(data, pid){
		if(pid){
			getNode(data, pid);
		}else{
			getNode(data);
		}
	}
	
	function createAttachmentTree(dataTree){
		zNodes = [];
		getNode(dataTree);
	}
	
	function beforeClick(treeId, treeNode) {
		var suffix = treeNode.name.substring(treeNode.name.lastIndexOf(".")+1);
		
		// 没有url 表示为 图片类型
		if(!treeNode.url && (suffix == 'png' || suffix == 'jpg' || suffix == 'bmp' || suffix == 'jpeg' || suffix == 'gif')){
			layer.open({
	            type: 2,
	            title: '附件预览',
	            shadeClose: true,
	            shade: false,
	            maxmin: true, //开启最大化最小化按钮
	            area: ['893px', '600px'],
	            content: globalPath +'attachment/download.do?path='+treeNode.filePath  
	        });
		}
	}
	
	var newCount = 1,
		indexLoad;
	
	function addHoverDom(treeId, treeNode) {
		var zipType =  treeNode.name.substring(treeNode.name.lastIndexOf(".")+1) === "zip" ? true : false;
				
		if(zipType && !treeNode.children){
			var sObj = $("#" + treeNode.tId + "_span");
			if (treeNode.editNameFlag || $("#viewBtn_"+treeNode.tId).length>0) return;
			var viewStr = "<span class='button view' id='viewBtn_" + treeNode.tId
				+ "' title='解压文件' onfocus='this.blur();'></span>";
			sObj.parent().after(viewStr);
			var btn = $("#viewBtn_"+treeNode.tId);
			
			if (btn) btn.bind("click", function(){
				//$(this).remove();
				$(this).removeClass('view').addClass('ico_loading');
				
				layer.msg(
					'正在解压此文件，请稍后...', 
					{icon : 6, time: 1000}, 
					function (){
						//indexLoad = layer.load(0, {shade: false});
					});
				
				restAjax(globalPath +'loanData/unzip.do', {target:treeNode.filePath}, "POST", function (rep){
					if(rep.code === 0){
						var zTree = $.fn.zTree.getZTreeObj("attachmentTree");
						for(var i=0; i<rep.data.list.length; i++){
							var suffix = rep.data.list[i].substring(rep.data.list[i].lastIndexOf(".")+1);
							
							// 判断为 文件夹 或者 压缩包
							if(suffix != 'png' && suffix != 'jpg' && suffix != 'bmp' && suffix != 'jpeg' && suffix != 'gif' && suffix != 'ico'&& suffix != 'xlsx' && suffix != 'docx' && suffix != 'pptx'){
								var _i = i;
								getNode(rep.data.list[i], treeNode.id, _i);
							} else {
								processFilePath = localFilePath + encodeURIComponent(Base64.encode(rep.data.list[i]));
								
								
								if(suffix == 'xlsx' || suffix == 'docx' || suffix == 'pptx'){
									switch(suffix)
									{
										case 'xlsx':
											spliceUrl =  viewGlobalPath_xlsx  + processFilePath + '.' + suffix + '?access_token=23432';
										    break;
										case 'docx':
											spliceUrl =  viewGlobalPath_docx  + processFilePath + '.' + suffix + '?access_token=23432';
										    break;
										case 'pptx':
											spliceUrl =  viewGlobalPath_pptx  + processFilePath + '.' + suffix + '?access_token=23432';
										    break;
									}
									
									zTree.addNodes(
										treeNode,
										{
											pId: treeNode.id,
											id: treeNode.id + "-" +i,
											name: rep.data.list[i].substring(rep.data.list[i].lastIndexOf("/")+1),
											filePath: rep.data.list[i],
											open:true,
											url: spliceUrl
										}
									);
									
								}else {
									zTree.addNodes(
										treeNode,
										{
											pId: treeNode.id,
											id: treeNode.id + "-" +i,
											name: rep.data.list[i].substring(rep.data.list[i].lastIndexOf("/")+1),
											filePath: rep.data.list[i],
											open:true
										}
									);
								}
							}
						}
						
						layer.alert('解压成功！', {
							icon : 6,
						}, function (){
							$('#appendixManageTable').bootstrapTable('refresh');
							$("#viewBtn_"+treeNode.tId).unbind().remove();
							
							//关闭
							layer.closeAll(); 
							return false;
						});
					}else {
						layer.alert('解压失败！', {
							icon : 5,
							time:1500
						}, function (){
							//关闭
							layer.close(indexLoad); 
						});
					}
				});
			});
		}else {
			if(treeNode.relationId){
				var delObj = $("#" + treeNode.tId + "_span");
				if (treeNode.editNameFlag || $("#delBtn_"+treeNode.tId).length>0) return;
				var delStr = "<span class='button remove' id='delBtn_" + treeNode.tId
					+ "' title='删除文件' onfocus='this.blur();'></span>";
				delObj.parent().after(delStr);
				var delBtn = $("#delBtn_"+treeNode.tId);
				
				if (delBtn) delBtn.bind("click", function(){
					//$(this).remove();
					$(this).removeClass('remove').addClass('ico_loading');
					
					layer.msg(
						'正在删除此文件，请稍后...', 
						{icon : 6, time: 1000}, 
						function (){
							//indexLoad = layer.load(0, {shade: false});
						});
					
					restAjax(globalPath +'attachment/delete.do', {id:treeNode.id}, "POST", function (rep){
						if(rep.code === 0){
							var delzTree = $.fn.zTree.getZTreeObj("attachmentTree");
							delzTree.removeNode(treeNode);
							
							layer.alert('删除成功！', {
								icon : 6,
							}, function (){
								$('#appendixManageTable').bootstrapTable('refresh');
								$("#delBtn_"+treeNode.tId).unbind().remove();
								
								//关闭
								layer.closeAll(); 
								return false;
							});
						}else {
							layer.alert('删除失败！', {
								icon : 5,
								time:1500
							}, function (){
								//关闭
								layer.closeAll(); 
							});
						}
					});
				});
			}
		}
	};
	
	function removeHoverDom(treeId, treeNode) {
		//$("#viewBtn_"+treeNode.tId).unbind().remove();
		//$("#delBtn_"+treeNode.tId).unbind().remove();
	};

	function onCheck(e, treeId, treeNode) {
	    var zTree = $.fn.zTree.getZTreeObj("attachmentTree"),
	    nodes = zTree.getCheckedNodes(true),
	    v = "";
	    
	    for (var i=0, l=nodes.length; i<l; i++) {
	        v += nodes[i].name + ",";
	    }
	    if (v.length > 0 ) v = v.substring(0, v.length-1);
	}
	
	window.attachmentTree = attachmentTree;
}());