$(function(){
	$("#datagrid").datagrid({
		url:basePath + versionQueryUrl,
		border:false,
		striped:true,
	    fit:true,
	    pageSize:50,
	    idField:'id',
	    loadMsg:'加载中……',
	    rownumbers:true,//序号
	    pagination:true,//显示底部分页工具栏
	    singleSelect:true,//单选
        fitColumns:true,
	    columns:[[
			{field:'id',title:'id',hidden:true},
			{field:'appname',title:'应用名称',width:150},
			{field:'equipment',title:'设备类型',width:100,
			  formatter: function(value,row,index){
				  switch (value) {
					case "android":return "安卓";
					case "ios":return "IOS";
					default:
						break;
				}
			  }
			},
			{field:'version',title:'版本号',width:100},
			{field:'force',title:'强制更新',width:80,
				formatter: function(value,row,index){
					return value?"是":"否";
				}
			},
			{field:'filezise',title:'文件大小（KB）',width:100},
			{field:'releasetime',title:'发布时间',width:150,
				formatter: function(value,row,index){
					return formatterTime(value,row,index);
				}
			},
            {field:'status',title:'状态',width:100,
                formatter: function(value,row,index){
                    switch (value) {
                        case 1:return "待发布";
                        case 2:return "当前版本";
                        case 3:return "过期版本";
                        default:
                            break;
                    }
                }
            },
            {field:'http',title:'下载地址',width:500,
                formatter: function(value,row,index){
                    return "<a href='"+value+"' target='_blank'>"+value+"</a>";
                }
            }
		]],
		toolbar:'#tool',
		onLoadSuccess:function(){
			$("#datagrid").datagrid('scrollTo',0);
            initPermissionButton();
		}
	});
    var pager = $('#datagrid').datagrid('getPager');    // get the pager of datagrid
    pager.pagination({
        layout:['first','prev','links','next','last']
    });
	
});

function openAddWin(){
	$('#applyForm').form('clear');
	$("#applyForm [name='version']").removeAttr("readonly");
    $("#applyForm [name='appname']").removeAttr("readonly");
    $("#equipment_ipt").combobox({readonly:false});
	$('#window').window('open');
	$('#window').window('center');	
}

function openEditWin(){
	var row = $('#datagrid').datagrid('getSelected');
	if(row == null){
		$.messager.alert("提示框","请选择一条记录！");
		return;
	}
	$('#applyForm').form('clear');
	$("#applyForm [name='version']").attr("readonly","readonly");
    $("#applyForm [name='appname']").attr("readonly","readonly");
    $("#equipment_ipt").combobox({readonly:true});
	$('#applyForm').form('load',{
		id:row.id,
        appname:row.appname,
		equipment:row.equipment,
		version:row.version,
		http:row.http,
        filezise:row.filezise,
        force:row.force,
		remark:row.remark
    });
	$('#window').window('open');
	$('#window').window('center');	
}

function apply(){
	if(!$("#applyForm").form('validate'))return;
 	$.ajax({
	    url:addVersionUrl,
	    data:$("#applyForm").serialize(),
	    success: function (data){
	    	if(data.status==0){
	    		$('#datagrid').datagrid('reload');
                $('#window').window('close');
				$.messager.alert("提示框","完成操作请求！");
            }else if(data.status==1){
            	$.messager.alert("错误",data.msg,'warning');
            }else if(data.status==3){
            	$.messager.alert("警告","无权访问",'warning');
            }
	    }
 	});
}

function closeWin(){
	$('#window').window('close');
}

function releaseVersion(){
    var row = $('#datagrid').datagrid('getSelected');
    if(row == null){
        $.messager.alert("提示框","请选择一条记录！");
        return;
    }
    $.messager.confirm('提示','请确认：是否设置此记录为应用最新版本?',function(r) {
        if (r) {
            $.ajax({
                url:releaseVersionUrl,
                data:{id:row.id},
                success: function (data){
                    if(data.status==0){
                        $('#datagrid').datagrid('reload');
                        $.messager.alert("提示框","完成操作请求！");
                    }else if(data.status==1){
                        $.messager.alert("错误",data.msg,'warning');
                    }else if(data.status==3){
                        $.messager.alert("警告","无权访问",'warning');
                    }
                }
            });
        }
    });
}

function delVersion(){
    var row = $('#datagrid').datagrid('getSelected');
    if(row == null){
        $.messager.alert("提示框","请选择一条记录！");
        return;
    }
    $.messager.confirm('提示','请确认：是否删除此记录?',function(r) {
        if (r) {
            $.ajax({
                url:delVersionUrl,
                data:{id:row.id},
                success: function (data){
                    if(data.status==0){
                        $('#datagrid').datagrid('reload');
                        $.messager.alert("提示框","完成操作请求！");
                    }else if(data.status==1){
                        $.messager.alert("错误",data.msg,'warning');
                    }else if(data.status==3){
                        $.messager.alert("警告","无权访问",'warning');
                    }
                }
            });
        }
    });
}

function doSearch(){
	var appname = $("#sk_searchkey").val();
    var equipment = $("#sk_equipment").combobox("getValue");
    $("#datagrid").datagrid("reload",{"equipment":equipment,"appname":appname});
}

function doClear(){
    $("#sk_searchkey").textbox("clear");
    $("#sk_equipment").combobox("clear");
    $("#datagrid").datagrid("reload",{"equipment":null,"appname":null});
}
