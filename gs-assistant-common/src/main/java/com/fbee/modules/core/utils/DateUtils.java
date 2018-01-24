package com.fbee.modules.core.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * @author ThinkGem
 * @version 2014-4-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	
	private static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}
	
	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}
	
	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}
	
	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		if(date == null){
			return null;
		}
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}
	
	/**
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
	 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
	 *   "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(24*60*60*1000);
	}

	/**
	 * 获取过去的小时
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*60*1000);
	}
	
	/**
	 * 获取过去的分钟
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*1000);
	}
	
	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * @param timeMillis
	 * @return
	 */
    public static String formatDateTime(long timeMillis){
		long day = timeMillis/(24*60*60*1000);
		long hour = (timeMillis/(60*60*1000)-day*24);
		long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
		long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
		long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
		return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
    }
	
	/**
	 * 获取两个日期之间的天数
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}
	
	/**
	 * 获取年龄
	 * @param birthday
	 * @return
	 */
	public static int getAge(String birthday){
		
		Date birthDate=DateUtils.parseDate(birthday);
		Calendar c=Calendar.getInstance();
		c.setTime(birthDate);
		int birthYear=c.get(Calendar.YEAR);
		int birthMonth=c.get(Calendar.MONTH)+1;
		int birthDay=c.get(Calendar.DATE);
		int returnAge;
		
		c.setTime(DateUtils.parseDate(DateUtils.getDate()));
		int nowYear = c.get(Calendar.YEAR);;
		int nowMonth = c.get(Calendar.MONTH)+1;
		int nowDay = c.get(Calendar.DATE);
		

		if (nowYear == birthYear) {
			returnAge = 0;// 同年 则为0岁
		} else {
			int ageDiff = nowYear - birthYear; // 年之差
			if (ageDiff > 0) {
				if (nowMonth == birthMonth) {
					int dayDiff = nowDay - birthDay;// 日之差
					if (dayDiff < 0) {
						returnAge = ageDiff - 1;
					} else {
						returnAge = ageDiff;
					}
				} else {
					int monthDiff = nowMonth - birthMonth;// 月之差
					if (monthDiff < 0) {
						returnAge = ageDiff - 1;
					} else {
						returnAge = ageDiff;
					}
				}
				
				if(nowMonth==2 && nowDay==28 && birthMonth==2 && birthDay==29){
					returnAge+=getLY(birthday);
				}
				
			} else {
				returnAge = -1;// 返回-1 表示出生日期输入错误 晚于今天
			}
			return returnAge;//返回周岁年龄
		}
		return returnAge;
	}
	
	/**
	 * 计算某人的生日到当前一共有几个闰年，此方法是为了更精确考虑闰年，从而计算出生日。
	 * @param data 数据库传入的 生日
	 */
	public static int getLY(String birthday){
		int leapYear = 0;
		if(!StringUtils.isEmpty(birthday)) {
			Date birthDate=DateUtils.parseDate(birthday);
			Calendar c=Calendar.getInstance();
			c.setTime(birthDate);
			int birthYear=c.get(Calendar.YEAR);
			c.setTime(DateUtils.parseDate(DateUtils.getDate()));
			int currYear = c.get(Calendar.YEAR);;
			for(int year=birthYear;year<currYear;year++) {
				if((year%4==0 && year%100!=0)||(year%400==0)) {
					leapYear++ ;//从出生年到当前年，只有是闰年就+1
				}
			}				
		}
		return leapYear;
	}
	
	/**
	 * 计算两个时间的时间差字符串（时分秒）
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static String getHssOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		long m=(afterTime - beforeTime) / (1000 * 60 * 60);
		long md=((afterTime - beforeTime) % (1000 * 60 * 60)) / (1000 * 60);
		long ms=((afterTime - beforeTime) % (1000 * 60 * 60)) % (1000 * 60)/1000;
		StringBuilder sb=new StringBuilder();
		if(m>0){
			sb.append(m).append("时");
		}
		if(md>0){
			sb.append(md).append("分");
		}
		if(ms>0){
			sb.append(ms).append("秒");
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * @param date
	 * @param pattern<br/>(yyyy/MM/dd HH:mm:ss)
	 * @return
	 */
	public static String dateToStr(Date date,String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);

	}

}
