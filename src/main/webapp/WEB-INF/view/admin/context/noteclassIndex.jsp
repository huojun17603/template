<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>..</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<jsp:include page="../common/shareJs.jsp" />
	<script type="text/javascript" src="<%=basePath%>view/admin/context/noteclassIndex.js"></script>
    <script type="text/javascript">
      var basePath = "<%=basePath%>";
      var noteclassListUrl = "noteclass/tree";
      var noteclassDeleteUrl = "noteclass/delete";
      var noteclassSaveOrUpdateUrl = "noteclass/saveOrUpdate";
    </script>
  	<style type="text/css">
  		.tree-icon{display: none}
  	</style>
  </head>

  <body style="width:100%;height:100%;">
	<table id="treegrid" ></table>

    <div id="tool" class="main-tool" >
        <h1>文章分类管理</h1>
        <div class="tool-btn" >
            <div style="float: right;padding-right: 20px;margin-top: 5px">
                <a href="javascript:void(0)" class="btn" onclick="openWindowWithB()">新增同级</a>
                <a href="javascript:void(0)" class="btn" onclick="openWindowWithS()">新增子级</a>
                <a href="javascript:void(0)" class="btn" onclick="openWindowWithU();">修改</a>
                <a href="javascript:void(0)" class="btn btn-red" onclick="delClass()">删除</a>

            </div>
        </div>
    </div>

	<div id="window" class="easyui-window" title="分类信息编辑" data-options="modal:true,closable:false,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
         style="width:600px;height:350px;padding:10px;">
        <div style="padding:5px 60px 5px 20px">
        <form id="applyForm" action="">
        	<input type="hidden" id="id_input" name="id">
        	<input type="hidden" id="pid_input" name="pid">
            <ul class="fm_s" style="overflow: inherit;">
                <li class="fm_1l">
                    <label>分类名称：</label>
                    <input class="easyui-textbox" name="name" />
                </li>
                <li class="fm_1l">
                    <label>备注：</label>
                    <input name="remark" class="easyui-textbox textbox vipt" data-options="labelPosition:top,multiline:true" style="height:160px;width:300px">
                </li>
            </ul>
        </form>
        </div>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="btn" onclick="submitForm()">确认</a>
            <a href="javascript:void(0)" class="btn btn-red" onclick="clearForm()">取消</a>
        </div>
    </div>
  </body>
</html>
