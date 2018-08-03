$(function(){
	$("#datagrid").datagrid({
		url:basePath + configList,
		border:false,
		striped:true,
	    fit:true,
	    idField:'aId',
	    loadMsg:'加载中……',
	    rownumbers:true,//序号
	    singleSelect:true,//单选
        fitColumns:true,
	    view:groupview,
        groupField:'groupname',
        groupFormatter:function(value,rows){
            return value;
        },
	    columns:[[
			{field:'ivalue',title:'值',width:100},
			{field:'driver',title:'正则表达式',width:200},
			{field:'docs',title:'说明',width:900},
            {field:'ikey',title:'操作',width:100,
                formatter: function(value,row,index){
                    var html = '';
                    html += '<a href="javascript:void(0)" style="width:80px;font-size: 14px; color: #23527c;text-decoration: none;"  onclick="openEditWindow(\''+index+'\')">修改</a>';
                    return html;
                }
            }

		]],
		toolbar:'#tool',
		onLoadSuccess:function(){
			$("#datagrid").datagrid('scrollTo',0);
		}
	});
});


function openEditWindow(index){
    $("#datagrid").datagrid("selectRow",index);
    var row = $("#datagrid").datagrid("getSelected");
	if(isEmpty(row)){
		$.messager.alert("提示消息",'请选择一条记录！');
		return ;
	}
    $('#apply_form2').form("clear");
	$("#apply_form2").form("load",{
		ikey:row.ikey,
		ivalue:row.ivalue,
		driver:row.driver
	});
	$('#apply_window2').window('open');
	$('#apply_window2').window('center');	
}

function closeEditWindow(){
	$('#apply_window2').window('close');
}

function applyEdit(){
	$.ajax({
        url: basePath+configEdit,
        data: $('#apply_form2').serialize(),
        success: function(data){
            if(data.status==0){
            	$('#datagrid').datagrid('reload');
            	$('#apply_window2').window('close');
            	$.messager.alert("提示框","完成操作请求！");
            }else if(data.status==1){
            	$.messager.alert("错误",data.msg,'warning');
            }else if(data.status==3){
            	$.messager.alert("警告","无权访问",'warning');
            }
        }
    });
}

function doSearch(searchkey){
	var sk_type = $("#sk_type").combobox("getValue");
	if(sk_type=='类型（全部）')sk_type = null;
	$("#datagrid").datagrid("reload",{searchkey:searchkey,type:sk_type});
}
