<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>银行配置</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<jsp:include page="../common/shareJs.jsp" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>script/minicolors/jquery.minicolors.css">
	<script type="text/javascript" src="<%=basePath%>script/minicolors/jquery.minicolors.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>view/admin/dictionary/bankIndex.js"></script>
		
	<script type="text/javascript">
		var bankQueryUrl = "dictionary/bank/query";
		var bankSaveUrl = "dictionary/bank/save";
        var bankUpdateUrl = "dictionary/bank/update";
		var bankAbleUrl = "dictionary/bank/able";
		var bankDisableUrl = "dictionary/bank/disable";
	</script>
  </head>
  
  <body style="width:100%;height:100%;">

    <table id="datagrid"></table>
    
    <div id="tool" style="padding:0px;height:auto;overflow: hidden;">
   		<div style="margin-top: 5px;  padding: 8px 35px 8px 14px;margin-bottom: 10px;text-shadow: 0 1px 0 rgba(255,255,255,0.5);background-color: #fcf8e3;border: 1px solid #fbeed5;-webkit-border-radius: 4px;-moz-border-radius: 4px;border-radius: 4px;color: #666;">
			<div style="line-height:30px">页面说明：本页面管理银行、支付宝、微信的字典信息，包括新增、修改、启用、禁用功能。</div>
			<a  href="javascript:void(0)" class="easyui-linkbutton" style="width:100px"  onclick="addBankWindow()">新增银行</a>
		</div>
		<div class="sgtz_atn">
			<span style="font-weight: bold;">搜索筛选：</span>
			<input id="sk_searchkey" class="easyui-searchbox" data-options="prompt:'搜索关键字（银行名称）',searcher:doSearch" style="width:300px"></input>
		</div>
	</div>
	
	<div id="apply_window" class="easyui-window" title="银行信息表单" 
		data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closable:false,closed:true" 
		style="width:450px;height:350px;padding:10px;">
		<input id="uploadify"  type="file" style="display:none">
		<form id="apply_form">
			<input id="id_input" name="id" type="hidden">
			<ul class="fm_s" style="overflow: inherit;">
				<li>
					<label>银行编码：</label>
					<input id="code_input" name="code" class="easyui-validatebox textbox vipt"
						data-options="required:true">
				</li>
				<li>
					<label>银行名称：</label>
					<input id="name_input" name="name" class="easyui-validatebox textbox vipt"
						data-options="required:true">
				</li>
				<li>
					<label>银行主题色：</label>
					<input id="themeCode_input" name="themecode" class="form-control" style="height:30px; ">
				</li>
				<li style="text-align: center;padding-left:80px;">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"  style="width:100px"  onclick="apply()">确认</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  style="width:100px"  onclick="closeWindow()">取消</a>
				</li>
			</ul>
		</form>
    </div>
  </body>
</html>
