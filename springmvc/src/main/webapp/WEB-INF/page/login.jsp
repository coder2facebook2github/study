<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<%@include file="common/header.jsp" %>
</head>
<body>
<%@include file="common/nav.jsp" %>
<div class="container">

	<form class="form-signin" action="http://localhost:8880/get/validate/code" method="post" enctype="text/plain">
		<h2 class="form-signin-heading">ERP管理平台</h2>
		<input type="tel" id="mobile" class="form-control" name="mobile" placeholder="mobile" required autofocus>
		<input type="password" id="password" name="password" class="form-control" placeholder="password" required>
		<button class="btn btn-lg btn-primary btn-block" type="submit">登 陆</button>
		<div class="pull-right">
			<a href="#">忘记密码?</a>
		</div>
	</form>

</div>


<style type="text/css">
	body {
		padding-top: 40px;
		padding-bottom: 40px;
		background-color: #eee;
	}

	.form-signin {
		max-width: 330px;
		padding: 15px;
		margin: 0 auto;
	}
	.form-signin .form-signin-heading,
	.form-signin .checkbox {
		margin-bottom: 10px;
	}
	.form-signin .checkbox {
		font-weight: normal;
	}
	.form-signin .form-control {
		position: relative;
		height: auto;
		-webkit-box-sizing: border-box;
		-moz-box-sizing: border-box;
		box-sizing: border-box;
		padding: 10px;
		font-size: 16px;
	}
	.form-signin .form-control:focus {
		z-index: 2;
	}
	.form-signin input[type="tel"] {
		margin-bottom: -1px;
		border-bottom-right-radius: 0;
		border-bottom-left-radius: 0;
	}
	.form-signin input[type="password"] {
		margin-bottom: 10px;
		border-top-left-radius: 0;
		border-top-right-radius: 0;
	}
	.form-signin button{
		margin-top: 30px;
	}
	.pull-right {
		margin-top: 20px;
	}
	.pull-right a {
		text-decoration: none;
	}
</style>
</body>
</html>
