<%@ page language="java" import="java.util.*,com.ich.core.listener.SystemConfig" pageEncoding="UTF-8"%>
<%@ include file="common/taglib.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  	<head>
	    <base href="<%=basePath%>">
	    <title>网站后台管理首页面</title>
	   	<jsp:include page="common/shareJs.jsp" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin/home.css">
		<!-- 窗口大小重置控制JS -->
	   	<script type="text/javascript" src="<%=basePath%>script/common/jquery.resizeEnd.min.js"></script>
	   	<script type="text/javascript" src="<%=basePath%>script/common/waterfall.js"></script>
		<script type="text/javascript" src="<%=basePath%>view/admin/common/environmental.js"></script>
 	</head>
 	<script type="text/javascript">
 		var userMenuList = "admin/employeeMenus";
 	</script>
 	<script type="text/javascript" src="<%=basePath%>/view/admin/index.js"></script>
 	<!-- 主框架最小尺寸1280*768。内框架最小尺寸1024*630 -->
	<body class="easyui-layout"  style="width:100%;height:100%;margin: 0 auto;min-height: 768px;min-width:1280px;">

		<div data-options="region:'north',split:false" style="height:60px;">
			<div id="header">
				<div class="headerNav">
					<a class="logo" href="javascript:void(0);">
						<!-- LOGO：布局参数请自定义 -->
						<img style="margin:5 20" alt="LOGO" src="/images/admin/logo.png">
					</a>
					<div style="float:left;height: 100%;vertical-align: middle;">
						<p style="line-height:1px;font-size:16px;color: #FF8C69;font-family:'微软雅黑'"><%=SystemConfig.getParams("ADMIN_HOME_NAME") %></p>
						<p style="color:#999;">${sessionScope.SESSION_ADMIN_NAME}</p>
					</div>
					<ul class="nav">
						<li><a href="javascript:void(0)" onclick="openEditWin()">修改密码</a></li>
						<li><a href="<%=basePath %>admin/loginout" >安全退出</a></li>
					</ul>
				</div>
			</div>
		</div>

		<div data-options="region:'west'" style="width:230px;background-color: #ffffff">
			<div class="nav_top">
				<div class="panel-icon icon-custome-home204"></div>
				<a onclick="alert()">首页</a>
			</div>
			<div id="ac" class="easyui-accordion" data-options="border:false">
				<div title="因技术问题导致的权宜之计" data-options="" style="overflow:auto;padding:10px;"></div>
				<c:forEach items="${topMenu}" var="item" varStatus="i">
					<div title="${item.name}"  style="overflow:auto;padding:10px;" data-options="iconCls:'${item.icon}'">
						<input id="menuId_${i.index}" type="hidden" value="${item.code}" >
						<ul id="user_menu_${i.index}"></ul>
					</div>
				</c:forEach>
			</div>
		</div>

		<div id="home_panel" data-options="region:'center'" style="background-color: #e8e8e8">

		</div>

		<div id="edit_window" class="easyui-window" title="修改密码"
			 data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closable:false,closed:true,iconCls:'icon-more'"
			 style="width:400px;height:250px;padding:10px;">
			<div style="padding:5px 20px 5px 20px">
				<form id="edit_form">
					<ul class="fm_s">
						<li>
							<label>旧密码：</label>
							<input name="oldkey" class="easyui-validatebox textbox vipt" type="password">
						</li>
						<li>
							<label>新密码：</label>
							<input name="newkey" class="easyui-validatebox textbox vipt" type="password">
						</li>
						<li>
							<label>确认密码：</label>
							<input name="rekey"  class="easyui-validatebox textbox vipt" type="password">
						</li>
					</ul>
					<div class="sgtz_center">
						<a href="javascript:void(0)" class="easyui-linkbutton" style="width:100px"  onclick="applyEdit()">确认</a>
						<a href="javascript:void(0)" class="easyui-linkbutton" style="width:100px"  onclick="closeEditWin()">取消</a>
					</div>
				</form>
			</div>
		</div>
	</body>
</html>
