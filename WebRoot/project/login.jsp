<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>用户登录</title>
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
<script type="text/javascript"
	src="../js/jquery-easyui-1.3.5/jquery.min.js"></script>
<script type="text/javascript"
	src="../js/jquery-easyui-1.3.5/jquery.easyui.min.js"></script>
<link rel="stylesheet"
	href="../js/jquery-easyui-1.3.5/themes/default/easyui.css"
	type="text/css"></link>
<link rel="stylesheet" href="../js/jquery-easyui-1.3.5/themes/icon.css"
	type="text/css"></link>
<script type="text/javascript"
	src="../js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js"></script>
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
<script type="text/javascript" charset="UTF-8">
	$(function() {
		var regDialog;
		var loginDialog;
		regDialog = $('#regDialog').show().dialog({
						modal : true, //模式化窗口
						closed : true, //先隐藏窗口
						buttons : [{
									text : '提交',
									handler : function() {
									$.ajax({
											url : '../servlet/Reg_Do',
											data : {
												name : $('#regForm input[name=name]').val(),
												password : $('#regForm input[name=password]').val(),
												age : $('#regForm input[name=age]').val(),
											},	
											dataType : 'json',
											success : function(data) {
												if(data.msg=="true"){
													regDialog.dialog('close');
													$.messager.show({
														title : '提示',
														msg : "注册成功"
													});
												}else{
													alert("注册失败");
												}
											},
											error : function() {
												alert("失败");
											}
										});
									}
							}, {
									text : "重置",
									handler : function() {
										regForm.reset();
									}
						} ]
		});
		loginDialog = $('#loginDialog').dialog({
							closable : false, // 组件添加属性：让关闭按钮消失
							modal : true, //模式化窗口
							buttons : [{
										text : '注册',
										handler : function() {
											regDialog.dialog('open');
										}
									},{
										text : '登录',
										handler : function() {
											$.ajax({
													url : '../servlet/Login_Do',
													data : {
														name : $('#loginForm input[name=name]').val(),
														password : $('#loginForm input[name=password]').val()
													},
													dataType : 'json',
													success : function(data) {
														if (data.msg == null) {
															alert("用户名密码错误");
														} else {
															loginDialog.dialog('close');
															window.location.href ='index.html';
														}
													},
													error : function() {
														alert("失败");
													}
											});
									}
							} ]
		});
		$.extend($.fn.validatebox.defaults.rules, {
			equalTo : {
				validator : function(value, param) {
					return $(param[0]).val() == value;
				},
				message : '字段不匹配'
			}
		});
		$.extend($.fn.validatebox.defaults.rules, {
			minLength : {
				validator : function(value, param) {
					return value.length >= param[0];
				},
				message : 'Please enter at least {0} characters.'
			}
		});
	});
</script>
</head>
<body style=”width:100%;height:100%;">
	<div id="loginDialog" title="用户登录" style="width:250px;height:220px;">
		<form id="loginForm" method="post">
			<table>
				<tr>
					<th>用户名:</th>
					<td><input type="text" name="name"><br>
					</td>
				</tr>
				<tr>
					<th>密<span>&nbsp;&nbsp;</span>码:</th>
					<td><input type="password" name="password"><br>
					</td>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="regDialog" title="注册" style="width:300;height:350;display=null;" align="center">
		<form id="regForm" method="post">
			<table>
				<tr>
					<th>用户名:<span>&nbsp;&nbsp;</span></th>
					<td><input type="text" name="name">
					</td>
				</tr>
				<tr>
					<th>年龄:<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
					</th>
					<td><input type="text" name="age">
					</td>
				</tr>
				<tr>
					<th>密码:<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
					</th>
					<td><input type="password" name="password" id="password"
						class="easyui-validatebox" required="true"
						validType:'minLength[5]' value="" />
					</td>
				</tr>
				<tr>
					<th>重复密码:</th>
					<td><input type="password" name="repassword" id="repassword"
						class="easyui-validatebox" required="true"
						validType="equalTo['#password']" invalidMessage="两次密码输入不匹配">
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
