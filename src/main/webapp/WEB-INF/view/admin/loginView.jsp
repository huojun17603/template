<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="Pragma" content="no-cache">
	<meta http-equiv="Cache-Control" content="no-cache">
	<meta http-equiv="Expires" content="0">
	<title>运营管理平台</title>
	<link href="/css/admin/login.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="/script/jquery-1.8.3.min.js"></script>
</head>
<script>
    //如果iframe返回到登录页面，则父页面也返回
    if (window != top){
        top.location.href = location.href;
    }
    function login(){
        $.ajax({
            url:'/admin/login',	//登录
            method:'post',
            dataType:'json',
            data:$("#loginForm").serialize(),
            success:function(result){
                if(0 == result.status){
                    window.location.href='/admin/index';
                }else{
                    alert(result.msg);
                }
            }
        });
    }
    //回车键快速登录【兼容】
    document.onkeydown = function keyLogin(e){
        e = e||event;
        if (e.keyCode==13) {  //回车键的键值为13
            login();
        }
    }
</script>
<body>
<div class="login_box">
	<div class="login_l_img"><img src="/images/admin/login-img.png" /></div>
	<div class="login">
		<div class="login_logo"><a href="#"><img src="/images/admin/login_logo.png" /></a></div>
		<div class="login_name">
			<p>运营管理平台</p>
		</div>
		<form id="loginForm" method="post">
			<input name="logincode" type="text" placeholder="请输入账号" >
			<input name="loginkey" type="password" id="password" placeholder="请输入密码"/>
			<input value="登录" style="width:100%;" type="button" onclick="login()">
		</form>
	</div>
	<%--<div class="copyright">某某有限公司 版权所有©2016-2018 技术支持电话：000-00000000</div>--%>
</div>
</body>
</html>