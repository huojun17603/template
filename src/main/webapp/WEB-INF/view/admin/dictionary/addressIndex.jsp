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
	<script type="text/javascript" src="<%=basePath%>view/admin/dictionary/addressIndex.js"></script>
		
	<script type="text/javascript">
		var addressQueryUrl = "dictionary/address/query";
		var addressSaveOrUpdateUrl = "dictionary/address/saveOrUpdate";
        var addressSaveOrUpdateUrl = "dictionary/address/saveOrUpdate";
		var addressList = "dictionary/address/list";
	</script>
  </head>
  
  <body style="width:100%;height:100%;">

    <table id="datagrid"></table>
    
    <div id="tool" style="padding:0px;height:auto;overflow: hidden;">
   		<div style="margin-top: 5px;  padding: 8px 35px 8px 14px;margin-bottom: 10px;text-shadow: 0 1px 0 rgba(255,255,255,0.5);background-color: #fcf8e3;border: 1px solid #fbeed5;-webkit-border-radius: 4px;-moz-border-radius: 4px;border-radius: 4px;color: #666;">
			<div style="line-height:30px">页面说明：中国省市区级地址管理字典。注意：暂不开放新增修改功能</div>
<%--			<a href="javascript:void(0)" class="easyui-linkbutton" style="width:100px"  onclick="openAddWindow()">新增</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" style="width:100px"  onclick="openEditWindow()">修改</a>		--%>
		</div>
		<div class="sgtz_atn">
			<span style="font-weight: bold;">搜索筛选：</span>
 			<select id="sk_type" class="easyui-combobox">
				<option value="类型（全部）">类型（全部）</option>
				<option value="1">省级</option>
				<option value="2">市级</option>
				<option value="3">区级</option>
				<option value="4">街道</option>
			</select> 
			<input id="sk_searchkey" class="easyui-searchbox" data-options="prompt:'搜索关键字（区域名称）',searcher:doSearch" style="width:300px"></input>
		</div>
	</div>
	
	<div id="apply_window" class="easyui-window" title="信息表单" 
		data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closable:false,closed:true" 
		style="width:700px;height:350px;padding:10px;">
       	<form id="apply_form">
       		<ul class="fm_s" style="overflow: inherit;">
				<li class="fm_1l">
					<label>编码：</label>
					<input id="id_input" name="id" class="easyui-validatebox textbox vipt" data-options="required:true">
				</li>
				<li>
					<label>省市区级：</label>
					<select id="type_input" name="type" class="easyui-combobox" >
						<option value="1">省级</option>
						<option value="2">市级</option>
						<option value="3">区级</option>
						<option value="4">街道</option>
					</select> 
				</li>
				<li>
					<label>父级区域：</label>
					<input id="parentid_input" name="parentid" type="hidden">
					<input id="sj_combo_input" class="easyui-combobox" />
					<input id="ssj_combo_input" class="easyui-combobox" />
					<input id="ssqj_combo_input" class="easyui-combobox" />
				</li>
				<li class="fm_1l">
					<label>区域名称：</label>
					<input id="name_input" name="name" class="easyui-validatebox textbox vipt" data-options="required:true">
				</li>
				<li class="fm_1l">
					<label>名称首字母：</label>
					<input id="letter_input" name="letter" class="easyui-validatebox textbox vipt" data-options="required:true">
				</li>
				<li style="text-align: center;padding-left:80px;">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"  style="width:100px"  onclick="apply()">确认</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  style="width:100px"  onclick="closeWindow()">取消</a>
				</li>
			</ul>
       	</form>
    </div>
    
    <div id="apply_window2" class="easyui-window" title="信息表单" 
		data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closable:false,closed:true" 
		style="width:400px;height:200px;padding:10px;">
       	<form id="apply_form2">
       		<ul class="fm_s" style="overflow: inherit;">
				<li class="fm_1l">
					<label>区域名称：</label>
					<input name="name" class="easyui-validatebox textbox vipt" data-options="required:true">
				</li>
				<li class="fm_1l">
					<label>名称首字母：</label>
					<input name="letter" class="easyui-validatebox textbox vipt" data-options="required:true">
				</li>
				<li style="text-align: center;padding-left:80px;">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"  style="width:100px"  onclick="applyEdit()">确认</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  style="width:100px"  onclick="closeEditWindow()">取消</a>
				</li>
			</ul>
       	</form>
    </div>
  </body>
</html>
