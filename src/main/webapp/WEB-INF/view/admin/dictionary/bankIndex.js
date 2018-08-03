$(function(){
	$("#datagrid").datagrid({
		url:basePath + bankQueryUrl,
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
        sortName:"onum",
        sortOrder:"desc",
		columns:[[
		          {field:'code',title:'编码',width:100},
		          {field:'name',title:'名称',width:500},
		          {field:'themecode',title:'主题色',width:100,
		        	  styler: function(value,row,index){
		        		  if(!isEmpty(value)) return 'background-color:'+value+';';
		        	  }
		          },
                  {field:'onum',title:'排序',width:50,sortable:true},
		          {field:'status',title:'状态',width:100,
		        	  formatter: function(value,row,index){
		        		  switch (value) {
							case 0:return "<input dataid='" + row.id + "' name='datagrid-switchbutton' value='0'>";
							case 1:return "<input dataid='" + row.id + "' name='datagrid-switchbutton' value='1'>";
							default:
								break;
						}
		        	  }
		          },
		          {field:'id',title:'操作',width:100,
		        	  formatter: function(value,row,index){
		        		  var html = '';
				          html += '<a href="javascript:void(0)" style="width:80px;font-size: 14px; color: #23527c;text-decoration: none;"  onclick="editBankWindow(\''+index+'\')">修改</a>';
				          return html;
		        	  }
		          }
		]],
		toolbar:'#tool',
		onLoadSuccess:function(){
            $("input[name='datagrid-switchbutton']").each(function(index,item){
				var checked = item.value==1;
				var dataid = $(item).attr("dataid");
                $(this).switchbutton({
                    onText:'正常',
                    offText:'禁用',
                    checked:checked,
                    onChange: function(checked){
                    	if(checked){
                            ableBank(dataid);
						}else {
                    		disableBank(dataid)
						}
                    }
				});
            });
			$("#datagrid").datagrid('scrollTo',0);
		}
	});
    var pager = $('#datagrid').datagrid('getPager');    // get the pager of datagrid
    pager.pagination({
        layout:['first','prev','links','next','last','sep','manual']
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
var applyUrl = "";

function addBankWindow(){
	$('#apply_form').form("clear");
    applyUrl = bankSaveUrl;
	$('#apply_window').window('open');
	$('#apply_window').window('center');	
}

function editBankWindow(index){
	$('#apply_form').form("clear");
    applyUrl = bankUpdateUrl;
	$("#datagrid").datagrid("selectRow",index);
	var row = $("#datagrid").datagrid("getSelected");
	$("#apply_form").form("load",{
		id:row.id,
		code:row.code,
		name:row.name,
		themecode:row.themecode,
        onum:row.onum
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
        url: basePath+applyUrl,
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

function doSearch(){
    var searchkey = $("#sk_searchkey").val();
    $("#datagrid").datagrid("reload",{searchkey:searchkey});
}

function doClear(){
    $("#sk_searchkey").textbox("clear");
    $("#datagrid").datagrid("reload",{searchkey:null});
}