<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>  
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>更新招聘信息</title>
	<script type="text/javascript" src="jquery-1.9.1.js" ></script>
	<script type="text/javascript">
		$(function(){
			
				$.ajax({
			        url: "/fbeeConsole_web/api/website/getServiceType",
			        type: "get",
			        //data: {date:new Date()},
			        //dataType: "json",
			        success: function success(data) {
			            if(data.success){
			            	  var serverType = [];
			            	  serverType.push("<option value=''>请选择工种</option>");
			                  for(var i = 0; i < data.jsonData.length; i ++){
			                	  serverType.push("<option value='"+data.jsonData[i].code+"'>"+data.jsonData[i].serviceType+"</option>");
			                  }
			            	$("#serverTypeId").html(serverType);
			            }else{
			                alert(data.msg);
			            }
			        },
			        error:function(err){
			            console.log(err);
			        }
			    });
			
		})
	
	</script>
</head>  
<body>  
	
	<form  id="jobForm" name="jobForm" action="/fbeeConsole_web/api/website/saveTenantsJobsInfo"  method="post">  
		<p>主键:<input name="id" /></p>
		<p>岗位名称:<input name ="positionName" /></p>
		<p>服务工种：
			<select id="serverTypeId" name ="serverType" ></select>
		</p>
		<p>年龄:<input name ="age" /></p>
		<p>工资:<input name ="salary" /></p>
		<p>要求:<textarea  name ="demand" ></textarea></p>
		<p>是否上架: 是：<input type="radio" name="isUsable" value="1" />否：<input type="radio" name="isUsable" value="0"/></p>
		<p><input type="button" value="返回" >&nbsp;&nbsp; <input type="submit" value="保存" ></p>  
	</form>  
	 
</body>  
</html>
