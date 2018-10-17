function NewMYJcrop(window,property){
	//构建页面
	var jcrop_img_div = $("<div></div>");
	var jcrop_img = $("<img />");
	var jcrop_img_uploadDiv = $("<div></div>");
	var jcrop_img_uploadButton = $("<input type='file'>");
	
	jcrop_img_div.append(jcrop_img_div);
	
	var img_back_div = $("<div></div>");
	var img_back_ul = $("<ul></ul>");
		
	var img_button_div = $("");
	var img_button_save = $("");
	var img_button_confirm = $("");
	var img_button_cancel = $("");
	
	
	
	if(property.isImgBack){}
	window.append(jcrop_img_div);
	window.append(img_back_div);
	window.append(img_button_div);
	
	////////////////////方法实现//////////////////
	//初始化jcrop
	$(jcrop_img).Jcrop({
		boxWidth:property.jcropWidth,
		boxHeight:property.jcropHeight, 
		aspectRatio:property.aspectRatio,
		minSize:[property.minCutWidth,property.minCutHeight],
		onSelect:function(c){
			property.onJcropSelect(c);
		}
	});
	$(jcrop_img_uploadButton).uploadify({
		'swf'      		 : "script/uploadify/uploadify.swf",
		'uploader'       : basePath + uploadUrl,
		'folder'         : 'files',
		'auto'           : true,//设置为true当选择文件后就直接上传了，为false需要点击上传按钮才上传 。
		'buttonText'	 : '上传',
		'fileObjName'    : 'file', 
		'fileExt' 		 : '*.*',//设置可以选择的文件的类型，格式如：'*.doc;*.pdf;*.rar' 。
		'width'			 : '80',
		'height'		 : '20',
		'onUploadSuccess': function(event,data){
			property.onUploadSuccess(event,data);
		}
	});
	
	$(img_button_save).on("click",{inthis:this},function(e){
		property.onSave();
	});
	
	$(img_button_confirm).on("click",{inthis:this},function(e){
		property.onConfirm();
		window.empty();//注销
	});

	$(img_button_cancel).on("click",{inthis:this},function(e){
		property.onCancel();
		window.empty();//注销
	});
}