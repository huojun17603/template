<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>组织机构管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<jsp:include page="../common/shareJs.jsp" />
	<script type="text/javascript" src="<%=basePath%>view/admin/employee/positionIndex.js"></script>
	<script type="text/javascript">
		var positionId;
		var organizationTree = "admin/organization/tree";
		
		var positionQuery = "admin/position/query";
		var positionInsert = "admin/position/insert";
		var positionDelete = "admin/position/delete";
		
		var positionMenuPermission = "admin/position/getMenuPermission";
		var positionSaveMenuPermission = "admin/position/saveMenuPermission";
		var positionWindowPermission = "admin/position/getWindowPermission";
		var positionSaveWindowPermission = "admin/position/saveWindowPermission";
		
		var employeList = "admin/employee/list";
		
	</script>
	<style type="text/css">
		.tree-icon {width: 0px;}
	</style>
  </head>
  
  <body class="easyui-layout"  style="width:100%;height:100%;">
  	
 	<div data-options="region:'west',title:'组织机构树',split:false" style="width:200px;">
 		<ul id="org_tree"></ul>
    </div>
    <div data-options="region:'center'" style="background-color: #E0ECFF">
    	<table id="datagrid"></table>
    </div>

	<div id="tool" class="main-tool" >
		<div class="tool-btn" >
			<div style="float: right;padding-right: 20px;margin-top: 5px">
				<a href="javascript:void(0)" class="btn" onclick="openPositionWindow()">新增</a>
				<a href="javascript:void(0)" class="btn" onclick="editPositionWindow()">修改</a>
				<a href="javascript:void(0)" class="btn btn-red" onclick="removePosition()">删除</a>
			</div>
		</div>
	</div>

	<div id="position_save_window" class="easyui-window" title="职位申请表单"
		data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closable:false,closed:true" 
		style="width:600px;height:300px;padding:10px;">
        <div style="padding:5px 20px 5px 20px">
        	<form id="position_save_form">
        		<input id="positionId" name="id" type="hidden">
        		<ul class="fm_s">
        			<li>
						<label>组织机构：</label>
						<input id="orgId_input" name="orgId" class="easyui-combotree" style="width: 220px;" >
					</li>
					<li>
						<label>职位名称：</label>
						<input id="name_input" name="name" class="easyui-validatebox textbox vipt"
							data-options="required:true">
					</li>
					<li>
						<label>数据权限组织：</label>
						<input id="permissionOrgId_input" name="permissionOrgId" class="easyui-combotree" >
					</li>
				</ul>
        		<div class="sgtz_center">
					<a href="javascript:void(0)" class="btn" onclick="addPosition()">确认</a>
					<a href="javascript:void(0)" class="btn btn-red" onclick="closedPositionWindow()">取消</a>
        		</div>
        	</form>
        </div>
    </div>
    
    <div id="positionMenu_save_window" class="easyui-window" title="职位菜单权限配置" 
		data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true" 
		style="width:700px;height:450px;">
        <table id="positionMenu_treegrid"></table>
    </div>
    
    <div id="positionWindow_save_window" class="easyui-window" title="职位窗口权限配置" 
		data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true" 
		style="width:450px;height:300px;">
        <table id="positionWindow_datagrid"></table>
    </div>
  </body>
</html>
