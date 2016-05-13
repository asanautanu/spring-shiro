/**
 * 当前用户修改密码
 */
$(function (){
	var $form = $('#updatePasswrodForm'),
		btnSubmit = $('#submitUpdatePasswrod');
	
	$(".right-sidebar-toggle").click(function() {
		//$("#right-sidebar").toggleClass("sidebar-open");
		restAjax(globalPath + 'user/loginUserInfo.do', null, "GET", function (rep){
			if(rep.code === 0 ){ 
				$('#userAccount').text(rep.data.list.userName);
				$('#userName').text(rep.data.list.name);
				$('#userDepartment').text(rep.data.list.departmentType);
				
				// 打开修改密码 弹出层
				$('#updatePasswordModal').modal({
					backdrop : 'static',
					keyboard : false
				});
			}else{
				$('#userAccount').text('');
				$('#userName').text('');
				$('#userDepartment').text('');
			}
		});
	});
	
	
	/**
	 * 提交
	 */
	btnSubmit.click(function (){
		if(validatorSave()){
			restAjax(globalPath + 'user/updatePassword.do', {
				oldPassword:$('#oldPassword').val(),
				newPassword:$('#newPassword').val(),
				enterPassword:$('#enterPassword').val()
			}, "POST", function (rep){
				if(rep.code === 0){
					layer.alert('密码修改成功，请重新登录！', {
						icon : 6
					}, function (){
						$('#updatePasswordModal').modal('hide');
						logout();
					});
				}else {
					layer.alert(rep.msg, {
						icon : 5
					});
				}
			});
		}
	}, null, null, btnSubmit);
	
	
	/**
	 * 表单验证方法
	 */
	function validatorSave() {
		return $form.validate({
			rules : {
				"oldPassword" : {
					required : true
				},
				"newPassword" : {
					required : true
				},
				"enterPassword" : {
					required : true,
					equalTo : "#newPassword",
				}
			},
			messages : {
				"oldPassword" : {
					required : "请输入旧密码",
				},
				"newPassword" : {
					required : "请输入新密码",
				},
				"enterPassword" : {
					required : "请输入确认密码",
					equalTo : "确认密码与新密码必须一致！",
				}
			}
		}).form();
	}
	
	$("#logout").click(function(){
   	 	layer.confirm("您确定要退出吗?", function(i){
   	 		logout();
   	 		layer.close(i);
    	});
    })
    
   
   function logout(){
		sessionStorage.permiessions = "";
		window.top.location.href = globalPath + "admin/logout.do";
	}
});