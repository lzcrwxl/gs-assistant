package com.fbee.modules.utils;

import java.util.HashMap;
import java.util.Map;

public class TestUtils {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(TestUtils.name("00"));
	}

	public static String name(String code){
		Map map = new HashMap();
		map.put("00", "猪");
		map.put("01", "鼠");
		map.put("02", "牛");
		map.put("03", "虎");
		map.put("04", "兔");
		map.put("05", "龙");
		map.put("06", "蛇");
		map.put("07", "马");
		map.put("08", "羊");
		map.put("09", "猴");
		map.put("10", "鸡");
		map.put("11", "狗");
		
		return map.get(code).toString();
	}
	
}
