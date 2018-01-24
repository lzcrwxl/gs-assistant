<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="/fbeeConsole_web/pages/jquery-1.9.1.js"></script>
<title>员工测试接口</title>
</head>
<body>
<p>用户基础信息表单</p>
<form action="/fbeeConsole_web/api/staffs/add/baseInfo" method="post">
	身份证号：<input type="text" name="certNo" value="340826199008241234" /><br>
	姓名：<input type="text" name="staffName" value="张三" /><br>
	年龄：<input type="text" name="age" value="26" /><br>
	民族：<input type="text" name="nation" value="08" /><br>
	属相：<input type="text" name="zodiac" value="03" /><br>
	性别：<input type="text" name="sex" value="0" /><br>
	星座：<input type="text" name="constellation" value="01" /><br>
	籍贯：<input type="text" name="nativePlace" value="21" /><br>
	学历：<input type="text" name="education" value="05" /><br>
	专业：<input type="text" name="specialty" value="营养学" /><br>
	血型：<input type="text" name="bloodType" value="01" /><br>
	婚姻状态：<input type="text" name="maritalStatus" value="01" /><br>
	生育状态：<input type="text" name="fertilitySituation" value="01" /><br>
	手机号码：<input type="text" name="mobile" value="15888888888" /><br>
	紧急联系号码：<input type="text" name="contactPhone" value="15888888888" /><br>
	联系地址：<input type="text" name="contactAddress" value="上海市长宁区xx路xx号xxx小区50号402室" /><br>
	现居住地址：<input type="text" name="liveAddress" value="上海市长宁区xx路xx号xxx小区50号402室" /><br>
	户籍地址：<input type="text" name="houseAddress" value="上海市长宁区xx路xx号xxx小区50号402室" /><br>
	工作状态：<input type="text" name="workStatus" value="01"/><br>
	期望工资：<input type="text" name="expectedSalary" value="8000" /><br>
	<input type="submit" value="员工基础信息添加" />
</form>

<p>用户银行卡信息</p>
<form action="/fbeeConsole_web/api/staffs/add/bankInfo" method="post">
	员工id：<input type="text" name="staffId" value="2" /><br>
	所属银行：<input type="text" name="bankCode" value="01" />招商银行<br>
	卡号：<input type="text" name="cardNo" value="123456789012345678" /><br>
	<input type="submit" value="员工银行卡添加or修改" />
</form>

<p>用户保单信息</p>
<form action="/fbeeConsole_web/api/staffs/add/policyInfo" method="post">
	员工id：<input type="text" name="staffId" value="2" /><br>
	保单号：<input type="text" name="policyNo" value="0011111111" /><br>
	保单名称：<input type="text" name="policyName" value="车险" /><br>
	保单机构：<input type="text" name="policyAgency" value="01" />太平洋保险<br>
	保单金额：<input type="text" name="policyAmount" value="1000" />招商银行<br>
	<input type="submit" value="员工保单添加or修改" />
</form>


<p>用户求职信息</p>
<form action="/fbeeConsole_web/api/staffs/add/jobInfo" method="post">
	员工id：<input type="text" name="staffId" value="2" /><br>
	服务工种一：<br>
	服务工种：<input type="text" name="serviceItems[0].serviceItemCode" value="01" /><br>
	服务价格：<input type="text" name="serviceItems[0].price" value="10000" /><br>
	从业经验：<input type="text" name="serviceItems[0].experience" value="01" /><br>
	技能特点：<input type="text" name="serviceItems[0].skills" value="0111110001" /><br>
	服务工种一：<br>
	服务工种：<input type="text" name="serviceItems[1].serviceItemCode" value="02" /><br>
	服务价格：<input type="text" name="serviceItems[1].price" value="20000" /><br>
	从业经验：<input type="text" name="serviceItems[1].experience" value="01" /><br>
	技能特点：<input type="text" name="serviceItems[1].skills" value="0100010001" /><br>
	
	管理方式：<input type="text" name="manageWay" value="02" /><br>
	服务区域：省<input type="text" name="serviceProvice" value="01" /><br>
 	服务区域： 市<input type="text" name="serviceCity" value="01" /><br>
 	服务区域： 区<input type="text" name="serviceCounty" value="02" /><br>
 	工作经历：<input type="text" name="workExperience" value="上海工作两年" /><br>
	自我评价：<input type="text" name="selfEvaluation" value="很熟练" /><br>
	老师评价：<input type="text" name="teacherEvaluation" value="很不错" /><br>
 	个人特点：<br>
 	语言特点<input type="hidden" name="features[0].featureKey" value="01" />：
 		   	<input type="text" name="features[0].featureValue" value="0100010001"/><br>
 	烹饪特点<input type="hidden" name="features[1].featureKey" value="02" />：
 			<input type="text" name="features[1].featureValue" value="0100010001"/><br>
 	性格特点<input type="hidden" name="features[2].featureKey" value="03" />：
 			<input type="text" name="features[2].featureValue" value="0100010001"/><br>
	不做家庭<input type="hidden" name="features[3].featureKey" value="04" />：
			<input type="text" name="features[3].featureValue" value="0100010001"/><br>
	<input type="submit" value="员工求职信息添加or修改" />
</form>

<p>用户查询列表信息</p>
<form action="/fbeeConsole_web/api/staffs/query" method="post">
	<input type="submit" value="用户查询" />
</form>
<script type="text/javascript">
</script>
</body>
</html>