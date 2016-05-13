$(function(){
    		var userId, 
    			userKey, 
    			socket, 
    			messages, 
    			messageNum = 0,
    			sendUserName,
    			context,
    			sendTime,
    			msgItem,
    			isRead = false,
    			pageNo = 1,
    			pageSize = 6,
    			msgList;
    		
    		$('#messageNum').removeClass('bounceInDown');
    		
    		// 查询 用户信息
    		restAjax(globalPath + 'user/loginUserInfo.do', null, "get", 
    			function (rep) {
	    			if(rep.code !== 0 ){
	    				return;
	    			}
	    			
	    			userId = rep.data.list.userId,
					userKey = rep.data.list.password;
             }, null, "JSON", null, 'application/json; charset=utf-8', false);
    		
    		
    		// 查询 用户 未读消息
    		restAjax(globalPath + 'msg/findReceiveMsg.do', {isRead:isRead, pageNo:pageNo, pageSize:pageSize} , "get", 
    			function (rep) {
	    			if(rep.code === 0 ){
	    				msgList = rep.data.list;
	    				getMessage(msgList);
	    			}else { 
	    				getMessage(null);
	    			}
             }, null, "JSON", null, 'application/json; charset=utf-8', false);
    		
    		// 获取消息
    		function getMessage(msgList){
    			if(!msgList) {
    				var defaultLi = '<li>'+
						                    '<div class="text-center link-block">'+
						                    '<button class="btn btn-primary J_menuItem A_Item" href="tpl/message/message.html" data-tabTitle="消息" id="btnMessage">'+
						                        '<i class="fa fa-envelope"></i> 暂无消息'+
						                    '</button>'+
						                '</div>'+
						            '<li>';

    				$('.dropdown-messages').append(defaultLi);
    				
    				return;
    			}
    			
    			msgList.reverse(); // 反转循序
    			
    			$('.dropdown-messages').empty();
    			$('#messageNum').toggleClass('bounceInDown').text(msgList.length > 100 ? '99+' : msgList.length);
				$('#btnMessage').text('查看所有消息');
    			
    			if(msgList.length > 0){
        			for(var i=0; i<msgList.length;i++){
    					if(i <= 4){
    						sendUserName = msgList[i].sendUserName,
                			context = msgList[i].context,
                			sendTime = moment(msgList[i].sendTime).format('YYYY-MM-DD HH:mm:ss');
        					
            				msgItem = '<li class="m-t-xs">' +
        			                    '<div class="dropdown-messages-box">'+
        					                '<a href="#" class="pull-left p-xxs"><strong id="sendUserName">'+ sendUserName +'</strong></a>'+
        					                '<div class="media-body">'+ 
        					                    '<span id="context">'+ context +'</span><br/>'+
        					                    '<small class="text-muted" id="sendTime">'+ sendTime +'</small>'+
        					                '</div>'+
        					            '</div>'+
        				        	'</li>';
            				
            				$('.dropdown-messages').append(msgItem);
            				$('.dropdown-messages').append('<li class="divider"></li>');
    					}
    				}
        			var more = '<li>'+
				                    '<div class="text-center link-block">'+
				                        '<button class="btn btn-primary J_menuItem A_Item" href="tpl/message/message.html" data-tabTitle="消息" id="btnMessage">'+
				                            '<i class="fa fa-envelope"></i> 查看所有消息'+
				                        '</button>'+
				                    '</div>'+
				                '<li>';
					
						$('.dropdown-messages').append(more);
        		} else {
        			var defaultLi = '<li>'+
						                    '<div class="text-center link-block">'+
						                    '<button class="btn btn-primary J_menuItem A_Item" href="tpl/message/message.html" data-tabTitle="消息" id="btnMessage">'+
						                        '<i class="fa fa-envelope"></i> 暂无消息'+
						                    '</button>'+
						                '</div>'+
						            '<li>';

        			$('.dropdown-messages').append(defaultLi);
        		}
    		}
    		
    		socket = io.connect('http://192.168.0.50:8888?userId='+userId+'&key='+ userKey);
    		socket.on("connect",function(){
    			console.log("服务器连接中...");
    		});
    		
    		//监听系统信息
    		socket.on("system",function(data,ack){
    			/* console.log(data);
    			console.log("如果系统启动了用户认证，那么这个地方会返回验证信息，已经系统的一些信息");
	    		console.log("auth_s,认证成功");
	    		console.log("auth_f,认证失败,连接将会断开"); */
	    		ack("ok");
    		});

    		
    		//监听服务器发送信息事件
    		socket.on("send_s",function(data,ack){
    			if(data){
    				msgList.push(data);
    				getMessage(msgList);
    				
    				console.log(data);
    			}
	   			
	   			//console.log("这里将会接收到 send_c 事件发送的信息"); console.log(data);
	   			ack("ok");
   			});   
    		
    		    		
    		//客户端发送信息示例
    		/* socket.emit("send_c",sendMsg,function(data){
    			console.log(sendMsg);
    		}); */

    		
    		//客户端监听断线
    		socket.on("disconnect",function(){
    			//TODO
    		});
    		
    		
    		// 获取更多消息点击事件
    		$(document).delegate("#btnMessage", "click", function(){
    			aTab(this);
    		});
    	});