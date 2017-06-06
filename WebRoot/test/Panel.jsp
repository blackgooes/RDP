<%@ page language="java" import="java.util.*" import="Easyui_Dao.Dao"
	pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%
	String a = String.valueOf(session.getAttribute("name"));
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html charset=gbk">
<title>用户主页</title>
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

<script type="text/javascript" charset="utf-8">
	
	$(function() {
		$.messager.show({
			title : '提示',
			msg : "登录成功"
		});
		/*  var treeData = [ {
			text : "查询列表",
			children : [ {
				text : "功能介绍",
				 attributes : {url : '1.jsp'}
				 
			}, {
				text : "学生信息管理",
				attributes:{
					 url : "2.jsp" 
				}
			} ]
		} ];  */
		// 实例化树菜单
		$("#tree").tree({
			url:'../servlet/Nodes_Do?id=0',
			//data : treeData,
			 onBeforeExpand:function(node,param){  
                     $('#tree').tree('options').url = "../servlet/Nodes_Do?id=" + node.id;
                 },  
               loadFilter: function(data){    
                  if (data.msg){    
                      return data.msg;    
                 } else {    
                     return data;    
                 }    
            }, 
			lines : true,
			onClick : function(node) {
				if (node.attributes) {
					openTab(node.text, node.attributes.url);
				}
			}
		});
	});
		function openTab(text, url) {
			if ($("#tabs").tabs('exists', text)) {
				$("#tabs").tabs('select', text);
			} else {
				var content = "<iframe frameborder='0' scrolling='auto' style='width:100%;height:100%' src="
						+ url + "></iframe>";
				$("#tabs").tabs('add', {
					title : text,
					closable : true,
					content : content
				});
			}
		}		
</script>
</head>
<body class="easyui-layout">
	<jsp:useBean id="user" class="Easyui_Dao.Dao" />
	<jsp:setProperty name="user" property="*" />
	<div region="north" title="欢迎 " split="true" style="height:100px;">
		 <img src="../images/2.gif"> 
		 <%=a%>&nbsp&nbsp您好，欢迎您的登录
	</div>
	<div region="west" title="菜单" split="true" style="width:150px;">
		<div id="tree"></div> 	
	</div>
	<div region="center" title="信息 " iconCls="icon-save"
		style="overflow:hidden">
		<div  class="easyui-tabs" fit="true" border="false" id="tabs">
		</div>	
	</div>
	<div region="south" style="height: 25px;" align="center">版权所有@D_xiao</div>
</body>
</html>
