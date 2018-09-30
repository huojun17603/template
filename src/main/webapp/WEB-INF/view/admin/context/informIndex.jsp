<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>系统配置-系统参数</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<jsp:include page="../common/shareJs.jsp" />
	<script type="text/javascript" src="<%=basePath%>view/admin/context/informIndex.js"></script>
	<script type="text/javascript" src="<%=basePath%>script/easyui/datagrid-groupview.js"></script>
	<script type="text/javascript">
		var informList = "inform/list";
		var informAddOrEdit = "inform/addOrEdit";
		var informDelete = "inform/delete";
		var informCancel = "inform/cancel";

	</script>
  </head>
  
  <body style="width:100%;height:100%;">

    <table id="datagrid"></table>

	<div id="tool" class="main-tool" >
		<h1>公告管理</h1>
		<div class="tool-btn" >
			<%--<div style="float: left;;margin-top: 5px">
				<input id="sk_searchkey" class="easyui-textbox" data-options="height:34,prompt:'搜索关键字（区域名称）'"  style="width:300px"/>
			</div>
			<div style="float: left;margin-top: 5px">
				<a href="javascript:void(0)" class="btn btn-blue"  onclick="doSearch()">搜索</a>
				<a href="javascript:void(0)" class="btn btn-blue"  onclick="doClear()">清空</a>
			</div>--%>

			<div style="float: right;padding-right: 20px;margin-top: 5px">
				<a href="javascript:void(0)" class="btn" onclick="openAddWindow()">新增</a>
				<a href="javascript:void(0)" class="btn" onclick="openEditWindow()">修改</a>
				<a href="javascript:void(0)" class="btn" onclick="cancelInform()">取消</a>
				<a href="javascript:void(0)" class="btn btn-red" onclick="deleteInform()">删除</a>
			</div>
		</div>
	</div>

    <div id="apply_window" class="easyui-window" title="信息表单"
		data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closable:false,closed:true" 
		style="width:500px;height:350px;padding:10px;">
       	<form id="apply_form">
       		<input name="id" type="hidden">
       		<ul class="fm_s" style="overflow: inherit;">
				<li class="fm_1l">
					<label>公告内容：</label>
					<input name="info" class="easyui-textbox textbox vipt" data-options="labelPosition:top,multiline:true" style="height:160px;width:300px">
				</li>
				<li class="fm_1l">
					<label>到期时间：</label>
					<input name="etime" class="easyui-datetimebox textbox vipt">
				</li>
				<li class="fm_1l" style="text-align: center;">
					<a href="javascript:void(0)" class="btn"  onclick="applyEdit()">确认</a>
					<a href="javascript:void(0)" class="btn btn-red" onclick="closeWindow()">取消</a>
				</li>
			</ul>
       	</form>
    </div>
  </body>
</html>
