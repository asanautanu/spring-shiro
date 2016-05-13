<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	String error = (String) request
			.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
	request.setAttribute("error", error);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>计量支付管理系统</title>
<link href="${ctx }/static/css/bootstrap.min.css?v=3.3.5" rel="stylesheet" />
<link href="${ctx }/static/css/font-awesome.min.css?v=4.4.0" rel="stylesheet" />
<link href="${ctx }/static/css/animate.min.css" rel="stylesheet" />
<link href="${ctx }/static/css/style.min.css?v=4.0.0" rel="stylesheet" />
<link href="${ctx }/static/css/define/login.css" rel="stylesheet" />
<link href="${ctx }/static/css/plugins/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css" rel="stylesheet" />
<link rel="shortcut icon" href="${ctx }/static/img/logo/favicon.ico" type="image/x-icon">
</head>
<body class="gray-bg">
	<div class="login">
		<div class="middle-box text-center loginscreen animated fadeInDown">
			<div>
				<div class="ui horizontal divider">计量支付管理系统</div>
				<div class="ibox-content">
					<form class="m-t" action="${ctx}/admin/login.do" method="post">
						<div id="error_div" class="form-group" style="display: none;">
							<span class="help-block m-b-none error"><i class="fa fa-times-circle"></i> <span id="error_text"></span> </span>
						</div>
						<div class="form-group">
							<input type="text" class="form-control input-lg" id="username" name="username"
								<c:if test="${!(requestScope.username eq '')}">value="${requestScope.username}"</c:if> placeholder="请输入用户名" required="" />
						</div>

						<div class="form-group">
							<input type="password" class="form-control input-lg" id="password" name="password" placeholder="请输入密码" required="" />
						</div>
						<div class="form-group">
							<div class="row">
								<div class="col-md-7">
									<input type="text" id="captcha" class="form-control input-lg" name="captcha" placeholder="请输入验证码" />
								</div>
								<div class="col-md-5">
									<div class="validate-img">
										<a href="javascrtip:;"> <img alt="验证码" src="${ctx}/static/images/kaptcha.jpg" title="点击更换" id="img_captcha"
											onclick="javascript:refreshCaptcha();" />
										</a> <a href="javascript:refreshCaptcha();">刷新</a>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="checkbox checkbox-primary">
								<input type="checkbox" id="keepUserName" /> <label for="keepUserName">记住账户</label>
							</div>
						</div>
						<button type="submit" class="btn btn-success block full-width m-b">立 即 登 录</button>
					</form>

				</div>
			</div>
		</div>
	</div>
	<script src="${ctx }/static/js/jquery.min.js?v=2.1.4"></script>
	<script src="${ctx }/static/js/bootstrap.min.js?v=3.3.5"></script>

	<script>
		function refreshCaptcha() {
			$("#img_captcha").attr("src", "${ctx}/static/images/kaptcha.jpg?t=" + Math.random());
		}
		// 错误信息Map
		var errorMap = {
			"org.apache.shiro.authc.UnknownAccountException" : "用户不存在！",
			"org.apache.shiro.authc.DisabledAccountException" : "用户已禁用！",
			"org.apache.shiro.authc.IncorrectCredentialsException" : "密码不正确！",
			"com.mpms.common.exception.CaptchaException" : "验证码错误！",
			"com.mpms.common.exception.SoaVisitException" : "后端访问异常"
		}, errorCode = '${requestScope.shiroLoginFailure}';
		if (errorCode) {
			var text = errorMap[errorCode];
			if (text) {
				$("#error_text").text(text);
			} else {
				$("#error_text").text("系统异常！请联系管理员!");
			}
			$("#error_div").show();
		} else {
			$("#error_text").empty();
			$("#error_div").hide();
		}
	</script>
</body>
</html>
