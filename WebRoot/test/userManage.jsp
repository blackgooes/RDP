<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>My JSP '2.jsp' starting page</title>
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
<style type="text/css">
#fm {
	margin: 0;
	padding: 10px 30px;
}

.ftitle {
	font-size: 14px;
	font-weight: bold;
	color: #666;
	padding: 5px 0;
	margin-bottom: 10px;
	border-bottom: 1px solid #ccc;
}

.fitem {
	margin-bottom: 5px;
}

.fitem label {
	display: inline-block;
	width: 80px;
}
</style>
<script type="text/javascript" charset="utf-8">
		//表格按钮事件
		function newUser(){
			$('#dlg').dialog('open').dialog('setTitle','新建');
			$('#fm').form('clear');
			//url = 'save_user.php.htm'/*tpa=http://jeasyui.com/tutorial/app/crud/save_user.php*/;
		}
		function editUser(){
			var row = $('#dg').datagrid('getSelected');
			if (row){
			var row1 = $('#dg').datagrid('getSelected');
				$.ajax({
					url : '../servlet/Del_Do',
					data : {
						name:row1.name
					},	
					dataType : 'json',
					success : function(data) {
						if(data){
							/* $('#dg').datagrid('load',{
				                code: '01',
				                name: 'name01'
				            }); */
						}else{
							alert("删除失败");
						}
					},
					error : function() {
						alert("失败");
					}
				});
				$('#dlg2').dialog('open').dialog('setTitle','编辑');
				$('#fm2').form('load',row);
			}
		}
		function reloadUser(){
			/*  window.location.reload();   */
			$('#dg').datagrid('load',{
                code: '01',
                name: 'name01'
            });
		}
		function saveUser(){
			$('#fm').form('submit',{
				url: '../servlet/Add_Do',
				onSubmit: function(){
					return $(this).form('validate');
				},
				success: function(result){
					var result = eval('('+result+')');  //定义返回类型 相当于  dataType:json
					
						$('#dlg').dialog('close');		// close the dialog
						$.messager.show({
							title: '提示',
							msg: '添加成功'
						});
						 $('#dg').datagrid('load',{
			                code: '01',
			                name: 'name01'
			            });  
				} 
			});
		} 
		 function updateUser(){
			$('#fm2').form('submit',{
				url: '../servlet/Add_Do',
				
				 onSubmit: function(){
					return $(this).form('validate');
				},
				success: function(result){
					var result = eval('('+result+')');  //定义返回类型 相当于  dataType:json
						$('#dlg2').dialog('close');		// close the dialog
						$.messager.show({
							title: '提示',
							msg: '更新成功'
						});
						 $('#dg').datagrid('load',{
			                code: '01',
			                name: 'name01'
			            });
			
				} 
			}); 
		} 
		 function removeUser(){
			var row = $('#dg').datagrid('getSelected');
			if (row){
				$.messager.confirm('确定','确定要删除这一条吗？',function(r){
					if (r){
					var row = $('#dg').datagrid('getSelected');
					var rowIndex=$('#dg').datagrid('getRowIndex',$('#dg').datagrid('getSelected'));
						$.ajax({
							url : '../servlet/Del_Do',
							data : {
								name:row.name
							},	
							dataType : 'json',
							success : function(data) {
								if(data){
									$('#dg').datagrid('load',{
						                code: '01',
						                name: 'name01'
						            });
									$.messager.show({
										title : '提示',
										msg : "删除成功"
									});
								}else{
									alert("删除失败");
								}
							},
							error : function() {
								alert("失败");
							}
						});
						}
				});
			}
		}   
</script>
</head>
<body>
	<!-- 显示列表 -->
	<table id="dg" title="" class="easyui-datagrid"
		style="width:700px;height:250px" toolbar="#toolbar" pagination="true"
		rownumbers="true" fitColumns="true" singleSelect="true" fit="true"
		border="0" pageSize="5" pageList="[5,10,20,50]" url="../servlet/Table_Do">
		<thead>
			<tr>
				<th field="name" width="50">姓名</th>
				<th field="username" width="50">用户名</th>
				<th field="password" width="50">密码</th>

			</tr>
		</thead>
	</table>

	<!-- 显示列表工具栏 -->
	<div id="toolbar">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
			onclick="newUser()">新建</a> <a href="#" class="easyui-linkbutton"
			iconCls="icon-edit" plain="true" onclick="editUser()">更新</a> <a
			href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
			onclick="removeUser()">删除</a> <a href="#" class="easyui-linkbutton"
			iconCls="icon-reload" plain="true" onclick="reloadUser()">刷新</a>
	</div>

	<!-- 添加用户窗口 -->
	<div id="dlg" class="easyui-dialog"
		style="width:400px;height:280px;padding:10px 20px" closed="true"
		buttons="#dlg-buttons">
		<form id="fm" method="post">
			<div class="fitem">
				<label>姓名</label> <input name="name" class="easyui-validatebox"
					required="true">
			</div>
			<div class="fitem">
				<label>用户名</label> <input name="username" class="easyui-validatebox"
					required="true">
			</div>
			<div class="fitem">
				<label>密码</label> <input name="password">
			</div>

		</form>
	</div>

	<!-- 添加窗口工具栏 -->
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
			onclick="saveUser()">保存</a> <a href="#" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>

	<!-- 更新用户窗口 -->
	<div id="dlg2" class="easyui-dialog"
		style="width:400px;height:280px;padding:10px 20px" closed="true"
		buttons="#dlg2-buttons">
		<form id="fm2" method="post" novalidate>

			<div class="fitem">
				<label>用户名</label> <input name="username" class="easyui-validatebox"
					required="true">
			</div>
			<div class="fitem">
				<label>密码</label> <input name="password">
			</div>
			<div class="fitem">
				<label>姓名</label> <input name="name" class="easyui-validatebox"
					required="true">
			</div>
		</form>
	</div>

	<!-- 更新窗口工具栏 -->
	<div id="dlg2-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
			onclick="updateUser()">确定</a> <a href="#" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg2').dialog('close')">取消</a>
	</div>
	</div>
</body>
</html>
