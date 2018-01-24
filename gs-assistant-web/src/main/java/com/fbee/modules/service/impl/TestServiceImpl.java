package com.fbee.modules.service.impl;

import org.springframework.stereotype.Service;

import com.fbee.modules.core.config.Global;
import com.fbee.modules.service.TestService;
import com.fbee.modules.service.basic.BaseService;

/** 
* @ClassName: TestServiceImpl 
* @Description: TODO
* @author 贺章鹏
* @date 2016年12月9日 下午1:40:06 
*  
*/
@Service
public class TestServiceImpl extends BaseService implements TestService {

	
	@Override
	public void test() {
		System.out.println(Global.getConfig("productName"));
	}

}
