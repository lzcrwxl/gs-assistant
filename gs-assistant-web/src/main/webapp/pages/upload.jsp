<%@ page pageEncoding="utf-8"%>  
<!DOCTYPE html>  
<html>  
<head>  
<meta charset="utf-8">  
<title>上传图片</title>  
</head>  
<body>  
<form action="/fbeeConsole_web/api/website/aboutUsImgPath" method="post" enctype="multipart/form-data">  
<input type="file" name="file" multiple="true"/> <input type="submit" value="Submit" /></form>  
</body>  
</html>  