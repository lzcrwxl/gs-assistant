package com.fbee.modules.core.sms;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.thoughtworks.xstream.core.util.Base64Encoder;

public class SmsUtil {
	private static final String UTF8 = "utf-8";
	private static final String MD5 = "MD5";

	public static String md5Digest(String src) throws NoSuchAlgorithmException, UnsupportedEncodingException {
	    MessageDigest md = MessageDigest.getInstance(MD5);
	    byte[] b = md.digest(src.getBytes(UTF8));
	    return byte2HexStr(b);
	}

	private static String byte2HexStr(byte[] b) {
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < b.length; ++i) {
	      String s = Integer.toHexString(b[i] & 0xFF);
	      if (s.length() == 1)
	        sb.append("0");
	
	      sb.append(s.toUpperCase());
	    }
	    return sb.toString();
	}
  
	public static String base64Encoder(String src) throws UnsupportedEncodingException {
		return new Base64Encoder().encode(src.getBytes(UTF8));
	}
	
	public static String getTimestamp() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		return formatter.format(new Date());
	}
	
	public static boolean isEmpty(String str) {
		return (("".equals(str)) || (str == null));
	}
}
