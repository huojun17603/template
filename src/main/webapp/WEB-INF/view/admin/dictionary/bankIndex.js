$(function(){
	$("#datagrid").datagrid({
		url:basePath + bankQueryUrl,
		border:false,
		striped:true,
	    fit:true,
	    pageSize:50,
	    pageList:[50,100],
	    idField:'b_Id',
	    loadMsg:'加载中……',
	    rownumbers:true,//序号
	    pagination:true,//显示底部分页工具栏
	    singleSelect:true,//单选
		columns:[[
		          {field:'code',title:'编码',align:"center",width:120},
		          {field:'name',title:'名称',align:"center",width:150},
		          {field:'themecode',title:'主题色',align:"center",width:100,
		        	  styler: function(value,row,index){
		        		  if(!isEmpty(value)) return 'background-color:'+value+';';
		        	  }
		          },
		          {field:'status',title:'状态',align:"center",width:120,
		        	  formatter: function(value,row,index){
		        		  switch (value) {
							case 0:return "禁用";
							case 1:return "可用";
							default:
								break;
						}
		        	  }
		          },
		          {field:'id',title:'操作',align:"center",width:120,
		        	  formatter: function(value,row,index){
		        		  var html = '';
				          html += '<a href="javascript:void(0)" style="width:80px;margin:5px;"  onclick="editBankWindow(\''+index+'\')">修改</a>';
				          if(row.status==0){
				        	  html += '|<a href="javascript:void(0)" style="width:80px;margin:5px;"  onclick="ableBank(\''+value+'\')">启用</a>';
				          }else{
				        	  html += '|<a href="javascript:void(0)"  style="width:80px;margin:5px;"  onclick="disableBank(\''+value+'\')">禁用</a>';
				          }
				          return html;
		        	  }
		          }
		]],
		toolbar:'#tool',
		onLoadSuccess:function(){
			$("#datagrid").datagrid('scrollTo',0);
		}
	});
	
	$("#themeCode_input").minicolors({
		change: function(hex, opacity) {
			if( !hex ) return;
			if( opacity ) hex += ', ' + opacity;
			try {
				console.log(hex);
			} catch(e) {}
		}
	});
});

function addBankWindow(){
	$('#apply_form').form("clear");
	$('#apply_window').window('open');
	$('#apply_window').window('center');	
}

function editBankWindow(index){
	$('#apply_form').form("clear");
	$("#datagrid").datagrid("selectRow",index);
	var row = $("#datagrid").datagrid("getSelected");
	$("#apply_form").form("load",{
		id:row.id,
		code:row.code,
		name:row.name,
		themecode:row.themecode
	});
	$("#themeCode_input").minicolors('value', row.themecode);
	$('#apply_window').window('open');
	$('#apply_window').window('center');	
}

function closeWindow(){
	$('#apply_window').window('close');
}

function apply(){
	$.ajax({
        url: basePath+bankSaveOrUpdateUrl,
        data: $('#apply_form').serialize(),
        success: function(data){
            if(data.status==0){
            	$('#datagrid').datagrid('reload');
            	$('#apply_window').window('close');
            	$.messager.alert("提示框","完成操作请求！");
            }else if(data.status==1){
            	$.messager.alert("错误",data.msg,'warning');
            }else if(data.status==3){
            	$.messager.alert("警告","无权访问",'warning');
            }
        }
    });
}

function ableBank(id){
	$.ajax({
        url: basePath+bankAbleUrl,
        data: {id:id},
        success: function(data){
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

function disableBank(id){
	$.ajax({
        url: basePath+bankDisableUrl,
        data: {id:id},
        success: function(data){
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
function doSearch(searchkey){
	$("#datagrid").datagrid("reload",{searchkey:searchkey});
}
