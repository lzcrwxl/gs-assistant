<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="/fbeeConsole_web/pages/jquery-1.9.1.js"></script>
<title>登陆接口</title>
</head>
<body>
<button onclick="getCaptcha()">获取验证码</button>
<img id="captcha"></img>

<p>登陆表单</p>
<form action="/fbeeConsole_web/api/user/login" method="post">
	<input type="text" name="loginAccount" value="zhangsan"/>
	<input type="text" name="captcha" value=""/>
	<input type="text" name="password" value="123456"/>
	<input type="submit" value="登陆" />
</form>
<!-- 登陆表单 -->
<p>登陆表单</p>
<form action="/fbeeConsole_web/api/user/logout" method="post">
	<input type="submit" value="登出" />
</form>

<!-- 首页信息 -->
<p>首页信息</p>
<a target="_blank" href="/fbeeConsole_web/api/index/info">首页信息</a>

<!-- 首页修改信息 -->
<p>首页修改信息</p>
<form action="/fbeeConsole_web/api/index/modify" method="post">
	<input type="text" name="propertyName" value=""/>
	<input type="text" name="propertyValue" value=""/>
	<input type="submit" value="提交" />
</form>
<script type="text/javascript">
//验证码
function getCaptcha(){
    $.ajax({
        url: "/fbeeConsole_web/api/user/getCaptcha",
        type: "post",
        data: {date:new Date()},
        dataType: "json",
        success: function success(data) {
            if(data.success){
                var img = "data:image/jpeg;base64," + data.jsonData.captcha;
                $("#captcha").attr("src", img);
            }else{
                alert(data.msg);
            }
        },
        error:function(err){
            console.log(err);
        }
    });
}

</script>
</body>
</html>