package com.fbee.modules.utils;

import com.fbee.modules.core.utils.MD5;

public class EntryptUtils {
	
	public static String ENTRY_STRING="{[(%s)]}";

	/**
	 * 用户密码
	 * @param plainPassword
	 * @return
	 */
	public static String entryptUserPassword(String plainPassword,String randomSalt) {
		StringBuffer sb=new StringBuffer();
		sb.append(plainPassword.toUpperCase()).append(String.format(ENTRY_STRING, randomSalt));
		return MD5.getStrMD5(sb.toString());
	}

	public static void main(String[] args) {
		System.out.println(entryptUserPassword("123456","123456"));
	}
}
