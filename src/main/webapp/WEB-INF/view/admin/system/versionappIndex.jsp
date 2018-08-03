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
	<script type="text/javascript" src="<%=basePath%>view/admin/system/versionappIndex.js"></script>
		
	<script type="text/javascript">
		var versionQueryUrl = "admin/versionapp/list";
		var addVersionUrl = "admin/versionapp/addOrEdit";
		var releaseVersionUrl = "admin/versionapp/release";
        var delVersionUrl = "admin/versionapp/delete";
	</script>
  </head>
  
  <body style="width:100%;height:100%;">

    <table id="datagrid"></table>

    <div id="tool" class="main-tool" >
        <h1>系统版本管理</h1>
        <div class="tool-btn" >
            <div style="float: left;;margin-top: 5px">
                <select id="sk_equipment" class="easyui-combobox"  data-options="height:34,width:120,editable:false">
                    <option value="">全部</option>
                    <option value="android">安卓</option>
                    <option value="ios">IOS</option>
                </select>
                <input id="sk_searchkey" class="easyui-textbox" data-options="height:34,prompt:'搜索关键字（应用名称）'"  style="width:300px"/>
            </div>
            <div style="float: left;margin-top: 5px">
                <a href="javascript:void(0)" class="btn btn-blue"  onclick="doSearch()">搜索</a>
                <a href="javascript:void(0)" class="btn btn-blue"  onclick="doClear()">清空</a>
            </div>
            <div style="float: right;padding-right: 20px;margin-top: 5px">
                <a href="javascript:void(0)" class="btn"  onclick="openAddWin()">新增</a>
                <a href="javascript:void(0)" class="btn"  onclick="openEditWin();">修改</a>
                <a href="javascript:void(0)" class="btn"  onclick="releaseVersion();">发布</a>
                <a href="javascript:void(0)" class="btn btn-red"  onclick="delVersion();">删除</a>
            </div>
        </div>
    </div>

	<div id="window" class="easyui-window" title="版本信息编辑"
        data-options="modal:true,closable:false,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
        style="width:550px;height:520px;padding:10px;">
        <form id="applyForm" action="" method="post">
        	<input type="hidden" id="id_input" name="id">
            <ul class="fm_s" style="overflow: inherit;">
                <li class="fm_1l">
                    <label>应用名称：</label>
                    <input name="appname" class="easyui-validatebox textbox vipt " >
                </li>
                <li class="fm_1l">
                    <label>设备：</label>
                    <select id="equipment_ipt" name="equipment" class="easyui-combobox vipt" data-options="editable:false,required:true" >
                        <option value="android">安卓</option>
                        <option value="ios">IOS</option>
                    </select>
                </li>
                <li class="fm_1l">
                    <label>版本号：</label>
                    <input name="version" class="easyui-validatebox textbox vipt " data-options="required:true">
                </li>
                <li class="fm_1l">
                    <label>下载地址：</label>
                    <input name="http" class="easyui-validatebox textbox vipt "  style="width:350px">
                </li>
                <li class="fm_1l">
                    <label>文件大小（KB）：</label>
                    <input name="filezise" class="easyui-validatebox textbox vipt " >
                </li>
                <li class="fm_1l">
                    <label>强制更新：</label>
                    <input type="radio" name="force" value="true" checked="checked"/>是
                    <input type="radio" name="force" value="false"/>否
                </li>
                <li class="fm_1l">
                    <label>更新说明：</label>
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
