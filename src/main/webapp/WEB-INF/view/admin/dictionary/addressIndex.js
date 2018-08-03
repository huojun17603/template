$(function(){
	$("#datagrid").datagrid({
		url:basePath + addressQueryUrl,
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
	    	      {field:'id',title:'区域编码',width:100},
		          {field:'letter',title:'首字母',width:50},
		          {field:'name',title:'区域名称',width:500},
		          {field:'type',title:'级别',width:100,
		        	  formatter: function(value,row,index){
		        		  switch (value) {
							case 1:return "省级";
							case 2:return "市级";
							case 3:return "区级";
							case 4:return "街道";
							default:
								break;
						}
		        	  }
		          },
		          {field:'parentid',title:'父级编码',width:100},
		          {field:'status',title:'状态',width:50,
		        	  formatter: function(value,row,index){
		        		  switch (value) {
							case 0:return "已删除";
							case 1:return "正常";
							default:
								break;
						}
		        	  }
		          }
		]],
		toolbar:'#tool',
		onLoadSuccess:function(){
			$("#datagrid").datagrid('scrollTo',0);
		}
	});
    var pager = $('#datagrid').datagrid('getPager');    // get the pager of datagrid
    pager.pagination({
        layout:['first','prev','links','next','last','sep','manual']
    });

	$("#type_input").combobox({
		editable:false,
		width:120,
		onSelect:function(val){
			if(val.value==1){
				$("#sj_combo_input").combobox("disable");
				$("#ssj_combo_input").combobox("disable");
				$("#ssqj_combo_input").combobox("disable");
			}else if(val.value==2){
				$("#sj_combo_input").combobox("enable");
				$("#ssj_combo_input").combobox("disable");
				$("#ssqj_combo_input").combobox("disable");
			}else if(val.value==3){
				$("#sj_combo_input").combobox("enable");
				$("#ssj_combo_input").combobox("enable");
				$("#ssqj_combo_input").combobox("disable");
			}else if(val.value==4){
				$("#sj_combo_input").combobox("enable");
				$("#ssj_combo_input").combobox("enable");
				$("#ssqj_combo_input").combobox("enable");
			}
			
		}
	});
	
	$.ajax({
        url: basePath+addressList + "?pid=1",
        success: function(datax){
			$("#sj_combo_input").combobox({
				data:datax,
				valueField: 'id',
				textField: 'name',
				editable:false,
				onSelect:function(data){
					var val = $("#type_input").combobox("getValue");
					if(val==2){
						$("#parentid_input").val(data.id);
					}else{
						$.ajax({
							url: basePath+addressList + "?pid=" + data.id,
							success: function(datax){
								$("#ssj_combo_input").combobox({
									data:datax,
									valueField: 'id',
									textField: 'name',
									editable:false,
									onSelect:function(data){
										var val = $("#type_input").combobox("getValue");
										if(val==3){
											$("#parentid_input").val(data.id);
										}else{
											$.ajax({
												url: basePath+addressList + "?pid=" + data.id,
												success: function(data){
													$("#ssqj_combo_input").combobox({
														data:data,
														valueField: 'id',
														textField: 'name',
														editable:false,
														onSelect:function(data){
															$("#parentid_input").val(data.id);
														}
													});
												}
											});
										}
									}
								});
							}
						});
					}
				}
			});
        }
    });
});

function openAddWindow(){
	$('#apply_form').form("clear");
	$("#sj_combo_input").combobox("disable");
	$("#ssj_combo_input").combobox("disable");
	$("#ssqj_combo_input").combobox("disable");
	$('#apply_window').window('open');
	$('#apply_window').window('center');	
}
function closeWindow(){
	$('#apply_window').window('close');
}

function apply(){
	$.ajax({
        url: basePath+addressSaveOrUpdateUrl,
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

function openEditWindow(index){
	$('#apply_form2').form("clear");
	var row = $("#datagrid").datagrid("getSelected");
	if(isEmpty(row)){
		$.messager.alert("提示消息",'请选择一条记录！');
		return ;
	}
	$("#apply_form2").form("load",{
		id:row.id,
		letter:row.letter,
		name:row.name
	});
	$('#apply_window2').window('open');
	$('#apply_window2').window('center');	
}

function closeEditWindow(){
	$('#apply_window2').window('close');
}

function applyEdit(){
	$.ajax({
        url: basePath+addressSaveOrUpdateUrl,
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

function doSearch(){
    var searchkey = $("#sk_searchkey").val();
	var sk_type = $("#sk_type").combobox("getValue");
	if(sk_type=='类型（全部）')sk_type = null;
	$("#datagrid").datagrid("reload",{searchkey:searchkey,type:sk_type});
}

function doClear(){
    $("#sk_searchkey").textbox("clear");
    $("#sk_type").combobox("clear");
    $("#datagrid").datagrid("reload",{searchkey:null,type:null});
}