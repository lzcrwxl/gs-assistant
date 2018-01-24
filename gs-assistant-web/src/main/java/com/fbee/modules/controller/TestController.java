package com.fbee.modules.controller;

import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.json.TestJson;
import com.fbee.modules.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/** 
* @ClassName: TestController 
* @Description: TODO
* @author 贺章鹏
* @date 2016年12月9日 下午1:41:23 
*  
*/
@Controller
public class TestController {
	
	@Autowired
	TestService testService;
	

	@RequestMapping("/api/test/login")
	@ResponseBody
	public JsonResult testLogin(String userId,String loginPwd){
		testService.test();
		TestJson testJson=new TestJson();
		testJson.setCode( userId);
		testJson.setName(loginPwd);
		return JsonResult.success(testJson);
	}
	
	@RequestMapping("/api/test/views")
	public ModelAndView views(ModelAndView mv){
		StringBuilder redirectUrl = new StringBuilder("/html/index.html");
		mv.setView(new RedirectView(redirectUrl.toString(), false));
		return mv;
	}
	
	@RequestMapping("/api/test/redis1")
	@ResponseBody
	public JsonResult testRedis(){
		return JsonResult.success("");
	}
}
