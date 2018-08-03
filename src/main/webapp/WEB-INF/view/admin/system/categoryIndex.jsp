<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>版本管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<jsp:include page="../common/shareJs.jsp" />
	<script type="text/javascript" src="<%=basePath%>view/admin/system/categoryIndex.js"></script>
		
	<script type="text/javascript">
		var categoryTreeUrl = "admin/category/obtain/tree";
		var categoryListUrl = "admin/category/obtain/list";
		var categoryAddOrEditUrl = "admin/category/addOrEdit";
        var categoryEditSUrl = "admin/category/editStatus";
        var categoryDelUrl = "admin/category/delete";
	</script>
  </head>
  
  <body style="width:100%;height:100%;">

    <table id="datagrid"></table>


    <div id="tool" class="main-tool" >
        <h1>系统版本管理</h1>
        <div class="tool-btn" >
            <div style="float: left;;margin-top: 5px">
                <a href="javascript:void(0)" class="btn btn-blue" onclick="goto1()">自定义类目</a>
                <a href="javascript:void(0)" class="btn btn-blue" onclick="goto2()">店铺类目</a>
            </div>
            <div style="float: right;padding-right: 20px;margin-top: 5px">
                <a href="javascript:void(0)" class="btn" onclick="openAddWin()">新增同级</a>
                <a href="javascript:void(0)" class="btn" onclick="openAddSWin()">新增子级</a>
                <a href="javascript:void(0)" class="btn" onclick="openEditWin();">修改</a>
                <a href="javascript:void(0)" class="btn" onclick="editStatus();">启用/禁用</a>
                <a href="javascript:void(0)" class="btn btn-red" onclick="delCategory();">删除</a>
            </div>
        </div>
    </div>

	<div id="window" class="easyui-window" title="系统类目编辑"
        data-options="modal:true,closable:false,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
        style="width:550px;height:450px;padding:10px;">
        <form id="applyForm" action="" method="post">
        	<input type="hidden"  name="id">
            <input type="hidden"  name="pid">
            <input type="hidden"  name="source">
            <ul class="fm_s" style="overflow: inherit;">
                <li class="fm_1l">
                    <label>类目名称：</label>
                    <input name="name" class="easyui-validatebox textbox vipt " data-options="required:true">
                </li>
                <li class="fm_1l">
                    <label>排序值：</label>
                    <input name="onum" class="easyui-validatebox textbox vipt " data-options="min:0,max:100,precision:0">
                </li>
                <li class="fm_1l">
                    <label>链接地址：</label>
                    <input name="http" class="easyui-numberbox textbox vipt "  style="width:350px">
                </li>
                <li class="fm_1l">
                    <label>其他属性：</label>
                    <input name="attr" class="easyui-validatebox textbox vipt "  style="width:350px">
                </li>
                <li class="fm_1l">
                    <label>说明：</label>
                    <input name="remark" class="easyui-textbox"  multiline="true" labelPosition="top" style="width:70%;height:130px">
                </li>
                <li class="fm_1l" style="text-align: center;">
                    <a href="javascript:void(0)" class="btn "  onclick="apply()">提交</a>
                    <a href="javascript:void(0)" class="btn btn-red"   onclick="closeWin()">关闭</a>
                </li>
            </ul>
        </form>
    </div>

  </body>
</html>
