<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../taglib.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'test.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	<script src="<%=basePath%>script/jcrop/js/jquery.Jcrop.js"></script>
	<script src="<%=basePath%>script/jcrop/js/test.js"></script>
	<script type="text/javascript">
	jQuery(function($){
	
		$("#upLoadFile").change(function(){
		var imgUrl = $("#upLoadFile").value==null?window.URL.createObjectURL(document.getElementById("upLoadFile").files.item(0)):"";
		 $("#touxiangForm img").attr('src',$("#upLoadFile").value==null?window.URL.createObjectURL(document.getElementById("upLoadFile").files.item(0)):"");
		$("#target").Jcrop({
		aspectRatio:1,
		boxWidth:400,
		boxHeight:400, 
		setSelect: [0,0,200,200],
		allowResize:false,
		allowSelect:false,
		 onSelect:function(c){
		 $('#x').val(c.x);
   		 $('#y').val(c.y);
   		 $('#x2').val(c.x2);
   		 $('#y2').val(c.y2);
   		 $('#w').val(c.w);
   		 $('#h').val(c.h);
			}
		 });
		
		});
		
	});
	/* 
	,function(){
		this_jrop = this;
		alert(this_jrop.tellScaled().x2);
		}
	
	 $("#testBut").cropperUpload({  
                url: "",  
                fileSuffixs: ["jpg", "png", "bmp"],  
                errorText: "{0}",  
                onComplete: function (msg) {  
                    $("#target").attr("src", msg);  
                },  
                cropperParam: {//Jcrop参数设置，除onChange和onSelect不要使用，其他属性都可用  
                    maxSize: [100, 100],//不要小于50，如maxSize:[40,24]  
                    minSize: [70, 70],//不要小于50，如minSize:[40,24]  
                    bgColor: "black",  
                    bgOpacity: .4,  
                    allowResize: false,  
                    allowSelect: false,  
                    animationDelay:50  
                },  
                perviewImageElementId: "fileList", //设置预览图片的元素id    
                perviewImgStyle: { width: '700px', height: '500px', border: '1px solid #ebebeb' }//设置预览图片的样式    
            });  

 */

  /*   var jcrop_api;

    $('#target').Jcrop({
      onChange:   showCoords,
      onSelect:   showCoords,
      onRelease:  clearCoords
    },function(){
      jcrop_api = this;
    });

    $('#coords').on('change','input',function(e){
      var x1 = $('#x1').val(),
          x2 = $('#x2').val(),
          y1 = $('#y1').val(),
          y2 = $('#y2').val();
      jcrop_api.setSelect([x1,y1,x2,y2]);
    });

  });

  // Simple event handler, called from onChange and onSelect
  // event handlers, as per the Jcrop invocation above
  function showCoords(c)
  {
    $('#x1').val(c.x);
    $('#y1').val(c.y);
    $('#x2').val(c.x2);
    $('#y2').val(c.y2);
    $('#w').val(c.w);
    $('#h').val(c.h);
  };

  function clearCoords()
  {
    $('#coords input').val('');
  }; */

</script>
	<link rel="stylesheet" href="<%=basePath%>pages/test/css/jquery.Jcrop.css" type="text/css" />
  </head>
  
  <body>
  <form id="touxiangForm" action="<%=basePath%>attachfile/cutImgAudit" method="post" enctype="multipart/form-data">
  	<input type="file" name="upLoadFile"  id="upLoadFile" />
    <img style="display: none;" name="imgTest"  src="#" id="target"  />
    <input  name="x" id="x" type="hidden"  /> <input id="y" name="y" type="hidden"  />
    <input id="w" name="width" type="hidden"  />
    <input id="h" name="height" type="hidden"  />
    <input id="auditType1" name="auditType" value="0" type="radio"  />Logo
    <input id="auditType2" name="auditType" value="11" type="radio"  />banner
    <input id="auditType3" name="auditType" value="12" type="radio"  />广告图
    <input type="submit" value="提交" />
    </form>
  </body>
</html>
