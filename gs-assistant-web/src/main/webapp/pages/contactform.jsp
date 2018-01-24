<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<p>联系方式修改</p>
	<form action="/fbeeConsole_web/api/website/updateContactInfo"
		method="post">
		联系电话：<input type="text" name="contactPhone" value="" /> <br /> QQ1:<input
			type="text" name="qqOne" value="" /> <br /> QQ2:<input type="text"
			name="qqTwo" value="" /> <br /> QQ3:<input type="text"
			name="qqThree" value="" /> <br /> 微信二维码:<input type="hidden"
			name="qrCode" value="" /> <br /> 前端是否显示电话:<input type="text"
			name="isOpenMobile" value="" /> <br />前端是否显示qq:<input type="text"
			name="isOpenQq" value="" /> <br />前端是否显示微信二维码:<input type="text"
			name="isOpenQrCode" value="" /> <br /> 
	<input type="submit"
			value="提交" />
	</form>
</body>
</html>