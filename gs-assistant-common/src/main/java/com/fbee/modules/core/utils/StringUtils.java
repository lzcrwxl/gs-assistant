package com.fbee.modules.core.utils;

import com.fbee.modules.core.servlet.Servlets;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringEscapeUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类, 继承org.apache.commons.lang3.StringUtils类
 * @author ThinkGem
 * @version 2013-05-22
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
	
    private static final char SEPARATOR = '_';
    private static final String CHARSET_NAME = "UTF-8";

	private static NumberFormat nf = NumberFormat.getInstance();

	static {
		nf.setGroupingUsed(false);
		nf.setMaximumIntegerDigits(Integer.SIZE);
		nf.setMinimumIntegerDigits(5);
	}

    /**
     * 转换为字节数组
     * @param str
     * @return
     */
    public static byte[] getBytes(String str){
    	if (str != null){
    		try {
				return str.getBytes(CHARSET_NAME);
			} catch (UnsupportedEncodingException e) {
				return null;
			}
    	}else{
    		return null;
    	}
    }
    
    /**
     * 转换为字节数组
     * @return
     */
    public static String toString(byte[] bytes){
    	try {
			return new String(bytes, CHARSET_NAME);
		} catch (UnsupportedEncodingException e) {
			return EMPTY;
		}
    }
    
    /**
     * 是否包含字符串
     * @param str 验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    public static boolean inString(String str, String... strs){
    	if (str != null){
        	for (String s : strs){
        		if (str.equals(trim(s))){
        			return true;
        		}
        	}
    	}
    	return false;
    }
    
	/**
	 * 替换掉HTML标签方法
	 */
	public static String replaceHtml(String html) {
		if (isBlank(html)){
			return "";
		}
		String regEx = "<.+?>";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(html);
		String s = m.replaceAll("");
		return s;
	}
	
	/**
	 * 替换为手机识别的HTML，去掉样式及属性，保留回车。
	 * @param html
	 * @return
	 */
	public static String replaceMobileHtml(String html){
		if (html == null){
			return "";
		}
		return html.replaceAll("<([a-z]+?)\\s+?.*?>", "<$1>");
	}
	
	/**
	 * 替换为手机识别的HTML，去掉样式及属性，保留回车。
	 * @param txt
	 * @return
	 */
	public static String toHtml(String txt){
		if (txt == null){
			return "";
		}
		return replace(replace(Encodes.escapeHtml(txt), "\n", "<br/>"), "\t", "&nbsp; &nbsp; ");
	}

	/**
	 * 缩略字符串（不区分中英文字符）
	 * @param str 目标字符串
	 * @param length 截取长度
	 * @return
	 */
	public static String abbr(String str, int length) {
		if (str == null) {
			return "";
		}
		try {
			StringBuilder sb = new StringBuilder();
			int currentLength = 0;
			for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()) {
				currentLength += String.valueOf(c).getBytes("GBK").length;
				if (currentLength <= length - 3) {
					sb.append(c);
				} else {
					sb.append("...");
					break;
				}
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String abbr2(String param, int length) {
		if (param == null) {
			return "";
		}
		StringBuffer result = new StringBuffer();
		int n = 0;
		char temp;
		boolean isCode = false; // 是不是HTML代码
		boolean isHTML = false; // 是不是HTML特殊字符,如&nbsp;
		for (int i = 0; i < param.length(); i++) {
			temp = param.charAt(i);
			if (temp == '<') {
				isCode = true;
			} else if (temp == '&') {
				isHTML = true;
			} else if (temp == '>' && isCode) {
				n = n - 1;
				isCode = false;
			} else if (temp == ';' && isHTML) {
				isHTML = false;
			}
			try {
				if (!isCode && !isHTML) {
					n += String.valueOf(temp).getBytes("GBK").length;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			if (n <= length - 3) {
				result.append(temp);
			} else {
				result.append("...");
				break;
			}
		}
		// 取出截取字符串中的HTML标记
		String temp_result = result.toString().replaceAll("(>)[^<>]*(<?)",
				"$1$2");
		// 去掉不需要结素标记的HTML标记
		temp_result = temp_result
				.replaceAll(
						"</?(AREA|BASE|BASEFONT|BODY|BR|COL|COLGROUP|DD|DT|FRAME|HEAD|HR|HTML|IMG|INPUT|ISINDEX|LI|LINK|META|OPTION|P|PARAM|TBODY|TD|TFOOT|TH|THEAD|TR|area|base|basefont|body|br|col|colgroup|dd|dt|frame|head|hr|html|img|input|isindex|li|link|meta|option|p|param|tbody|td|tfoot|th|thead|tr)[^<>]*/?>",
						"");
		// 去掉成对的HTML标记
		temp_result = temp_result.replaceAll("<([a-zA-Z]+)[^<>]*>(.*?)</\\1>",
				"$2");
		// 用正则表达式取出标记
		Pattern p = Pattern.compile("<([a-zA-Z]+)[^<>]*>");
		Matcher m = p.matcher(temp_result);
		List<String> endHTML = Lists.newArrayList();
		while (m.find()) {
			endHTML.add(m.group(1));
		}
		// 补全不成对的HTML标记
		for (int i = endHTML.size() - 1; i >= 0; i--) {
			result.append("</");
			result.append(endHTML.get(i));
			result.append(">");
		}
		return result.toString();
	}
	
	/**
	 * 转换为Double类型
	 */
	public static Double toDouble(Object val){
		if (val == null){
			return 0D;
		}
		try {
			return Double.valueOf(trim(val.toString()));
		} catch (Exception e) {
			return 0D;
		}
	}

	/**
	 * 转换为Float类型
	 */
	public static Float toFloat(Object val){
		return toDouble(val).floatValue();
	}

	/**
	 * 转换为Long类型
	 */
	public static Long toLong(Object val){
		return toDouble(val).longValue();
	}

	/**
	 * 转换为Integer类型
	 */
	public static Integer toInteger(Object val){
		return toLong(val).intValue();
	}
	

	/**
	 * 获得用户远程地址
	 */
	public static String getRemoteAddr(HttpServletRequest request){
		String remoteAddr = request.getHeader("X-Real-IP");
        if (isNotBlank(remoteAddr)) {
        	remoteAddr = request.getHeader("X-Forwarded-For");
        }else if (isNotBlank(remoteAddr)) {
        	remoteAddr = request.getHeader("Proxy-Client-IP");
        }else if (isNotBlank(remoteAddr)) {
        	remoteAddr = request.getHeader("WL-Proxy-Client-IP");
        }
        return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
	}

	/**
	 * 驼峰命名法工具
	 * @return
	 * 		toCamelCase("hello_world") == "helloWorld" 
	 * 		toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 * 		toUnderScoreCase("helloWorld") = "hello_world"
	 */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }

        s = s.toLowerCase();

        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
	 * 驼峰命名法工具
	 * @return
	 * 		toCamelCase("hello_world") == "helloWorld" 
	 * 		toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 * 		toUnderScoreCase("helloWorld") = "hello_world"
	 */
    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = toCamelCase(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
    
    /**
	 * 驼峰命名法工具
	 * @return
	 * 		toCamelCase("hello_world") == "helloWorld" 
	 * 		toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 * 		toUnderScoreCase("helloWorld") = "hello_world"
	 */
    public static String toUnderScoreCase(String s) {
        if (s == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            boolean nextUpperCase = true;

            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }

            if ((i > 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    sb.append(SEPARATOR);
                }
                upperCase = true;
            } else {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }
    
    /**
     * 如果不为空，则设置值
     * @param target
     * @param source
     */
    public static void setValueIfNotBlank(String target, String source) {
		if (isNotBlank(source)){
			target = source;
		}
	}
 
    /**
     * 转换为JS获取对象值，生成三目运算返回结果
     * @param objectString 对象串
     *   例如：row.user.id
     *   返回：!row?'':!row.user?'':!row.user.id?'':row.user.id
     */
    public static String jsGetVal(String objectString){
    	StringBuilder result = new StringBuilder();
    	StringBuilder val = new StringBuilder();
    	String[] vals = split(objectString, ".");
    	for (int i=0; i<vals.length; i++){
    		val.append("." + vals[i]);
    		result.append("!"+(val.substring(1))+"?'':");
    	}
    	result.append(val.substring(1));
    	return result.toString();
    }
    
    /**
     * @description 返回几位随机数字
     * @param length 
     * @return
     */
    public static Long getRandomNumber(int length)
    {
    	Long retI=new Long(0);
    	StringBuffer sb=new StringBuffer();
    	for(int i=0;i<length;i++)
    	{
    		sb.append(new Random().nextInt(9));
    	}
    	retI=Long.parseLong(sb.toString());
    	return retI;
    }
    
    /**
     * @description 返回几位随机字符串
     * @param length 
     * @return
     */
    public static String getRandomStr(int length)
    {
    	String base = "abcdefghijklmnopqrstuvwxyz0123456789";     
        Random random = new Random();     
        StringBuffer sb = new StringBuffer();     
        for (int i = 0; i < length; i++) {     
            int number = random.nextInt(base.length());     
            sb.append(base.charAt(number));     
        }     
        return sb.toString();     
    }
    
    /**
     * java生成随机数字和字母组合
     * @param
     * @return
     */
    public static String getCharAndNumr(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 取得大写字母还是小写字母
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (choice + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }
    
    private final static String[] agent = { "Android", "iPhone", "iPod","iPad", "Windows Phone", "MQQBrowser" };
	
	/**
	 * 判断用户请求是否来时手机端
	 * @return
	 */
	public static Boolean isMobileRequest(String ua){
		Boolean mobileFlag=Boolean.FALSE;
		if (!ua.contains("Windows NT")
				|| (ua.contains("Windows NT") && ua
						.contains("compatible; MSIE 9.0;"))) {
			//排除 苹果桌面系统
			if (!ua.contains("Windows NT") && !ua.contains("Macintosh")) {
				for (String item : agent) {
					if (ua.contains(item)) {
						mobileFlag = true;
						break;
					}
				}
			}
		}
		return mobileFlag;
	}
	
	/**
	 * 获取访问者ip
	 * @param request
	 * @return
	 */
	public static String getRemoteIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static HashSet<?> getRandomArray(List<?>  list ,int num,HashSet newList){
		Random random = new Random();
		for (int i = 0; i < num; i++) {
			int randomNum = random.nextInt(list.size());
			newList.add(list.get(randomNum));
			if(newList.size()==num){
				break;
			}
		}
		
		int setSize = newList.size();  
		if (setSize < num) {  
			getRandomArray(list,num, newList);// 递归  
	    }  
		return newList;
	}
	
	/**
	 * 将手机号中间四位转为*
	 * @param mobile
	 * @return
	 */
	public static String hideMobile(String mobile) {
		if (isBlank(mobile) || mobile.length() != 11) {
			return mobile;
		}
		
		return mobile.substring(0, 3) + " **** " + mobile.substring(7);
	}
	
	/**
	 * 获取用户IP
	 * @return
	 */
	protected String getIpAddr() {   
		String ipAddress = null;
		ipAddress = Servlets.getRequest().getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = Servlets.getRequest().getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = Servlets.getRequest().getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = Servlets.getRequest().getRemoteAddr();
			if (ipAddress.equals("127.0.0.1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}
		}

		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}
	
	/**
	 * 判断当前请求是否为手机端请求
	 * @return
	 */
	protected boolean isMobileRequest() {   
		String ua=Servlets.getRequest().getHeader("User-Agent");
		return StringUtils.isMobileRequest(ua);
	}
	
	/**
	 * 截取卡号后几位
	 * @param certNo
	 * @return
	 */
	public static String leftCardsNo(String certNo,int num) {
		if (isBlank(certNo) || certNo.length()<num) {
			return certNo;
		}
		
		return certNo.substring(certNo.length()-num);
	}
	
	/**
	 * 字符串前补位
	 * @param source 原字符串
	 * @param appender 追加字符串
	 * @param size 最终长度
	 * @return
	 */
	public static String appendStr(String source, char appender, int size) {
		StringBuilder result = new StringBuilder(source);
		int appendSize = size - source.length();
		for (int i = 0; i < appendSize; i++) {
			result.insert(0, appender);
		}
		return result.toString();
	}
	
	/**
	 * 字符串前补位0
	 * @param source 原字符串
	 * @param size 最终长度
	 * @return
	 */
	public static String appendStrZero(String source, int size) {
		char appender='0';
		StringBuilder result = new StringBuilder(source);
		int appendSize = size - source.length();
		for (int i = 0; i < appendSize; i++) {
			result.insert(0, appender);
		}
		return result.toString();
	}
	
	/**
	 * 反转字符串  
	 * @param str
	 * @return
	 */
    public static String strReverse(String str){  
    	StringBuffer sb = new StringBuffer(str);  
        String newstr = sb.reverse().toString();  
        return newstr;
    } 
    
    /**
     * 字符串数组拼接 例：拼成01,02,03
     */
    public static String strAppend(String[] str){
    	if(str != null){
    		String s = "";
    		for(int i = 0; i < str.length; i++){
    			if(i < str.length-1){
    				s = s + str[i] + ",";
    			}else {
    				s = s + str[i] ;
    			}
    		}
    		return s;
    	}else {
			return null;
		}
    }
    
    /**
     * 将数组拼接为模糊查询字段
     * 例：将{"01","02","03","04"}拼为%01%02%03%04
     */
    public static String strLike(String[] str){
    	if(str == null || str.length == 0){
    		return null;
    	}
    	String s = "%";
    	for(int i = 0; i < str.length; i++){
    		if(StringUtils.isBlank(str[i])){
    			return null;
    		}
    		s = s + str[i] + "%";
    	}
    	return s;
    }
    
    /**
     * 将字符串拼接为模糊查询字段
     * 例：将aa拼为%aa%
     */
    public static String strLike(String str){
    	if(isBlank(str)){
    		return null;
    	}
    	String s = "%" + str + "%";
    	return s;
    }
    
    public static void main(String[] args) {
//		String[] s = {"01","02","03","04"};
    	String s = "aa";
		System.out.println(strLike(s));
	}
    
    //判断是否为1
  	public static Boolean isOne(int num, int index) {
  		return ((num & (0x1 << index)) >> index)== 1;
  	}
  	
  	//判断是否是空字符串
  	public static String IsNull(Object str){
  		if(str == null){
  			return "";
  		}else{
  			if(str instanceof Integer){
  				return str.toString();
  			}else if(str instanceof Date){
  				return DateUtils.dateToStr((Date) str, "yyyy-MM-dd HH:mm:ss");
  			}else if(str instanceof Double){
  				return String.valueOf(str);
  			}else{
  				return str.toString();
  			}
  		}
  	}

	public static String fillZero(Integer length, Object number){
		nf.setMinimumIntegerDigits(length);
		return nf.format(number);
	}


}
