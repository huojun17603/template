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
	<script type="text/javascript" src="<%=basePath%>view/admin/employee/organizationIndex.js"></script>
	<script type="text/javascript">
		var organizationList = "admin/organization/list";
		var organizationTree = "admin/organization/tree";
		var organizationInsert = "admin/organization/insert";
		var organizationDelete = "admin/organization/delete";
		
		var employeList = "admin/employee/list";
		var employequery = "admin/employee/query";
	</script>
	<style type="text/css">
		.tree-icon {width: 0px;}
	</style>
  </head>
  
  <body style="width: 100%;height: 100%">
    <table id="datagrid"></table>

	<div id="tool" class="main-tool" >
		<h1>组织机构管理</h1>
		<div class="tool-btn" >
			<div style="float: right;padding-right: 20px;margin-top: 5px">
				<a href="javascript:void(0)" class="btn" onclick="addOrgWindow()">新增</a>
				<a href="javascript:void(0)" class="btn" onclick="editOrgWindow()">修改</a>
				<a href="javascript:void(0)" class="btn btn-red" onclick="removeOrg()">删除</a>
			</div>
		</div>
	</div>

	<div id="organization_save_window" class="easyui-window" title="机构信息申请表单"
		data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closable:false,closed:true" 
		style="width:600px;height:320px;padding:10px;">
        <div style="padding:5px 20px 5px 20px">
        	<form id="organization_save_form">
        		<input id="orgid" name="id" type="hidden">
        		<ul class="fm_s">
        			<li>
						<label>上级机构：</label>
						<input id="parentId_input" name="parentId" class="easyui-combotree" style="width: 220px;" >
					</li>
					<li>
						<label>机构名称：</label>
						<input id="orgName_input" name="orgName" class="easyui-validatebox textbox vipt"
							data-options="required:true">
					</li>
					<li>
						<label>领导：</label>
						<input id="leader_input" name="leader" type="hidden"/>
						<input id="leaderName_input" name="leaderName"  onclick="selectLeader()" readonly="readonly" class="easyui-validatebox textbox vipt"/>
					</li>
					<li>
						<label>副领导：</label>
						<input id="deputyLeader_input" name="deputyLeader" type="hidden"/>
						<input id="deputyLeaderName_input" name="deputyLeaderName" onclick="selectDeputyLeader()" readonly="readonly" class="easyui-validatebox textbox vipt">
					</li>
				</ul>
        		<div class="sgtz_center">
					<a href="javascript:void(0)" class="btn" onclick="addOrg()">确认</a>
					<a href="javascript:void(0)" class="btn btn-red" onclick="closedOrgWindow()">取消</a>
        		</div>
        	</form>
        </div>
    </div>
	
	<div id="selector"></div>

  </body>
</html>
