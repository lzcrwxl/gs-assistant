package com.fbee.modules.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.core.utils.ZodiacTools;
import com.fbee.modules.validate.IdcardValidate;


/** 
* @ClassName: IdCardInfoUtils 
* @Description: new IdCardInfoUtils(身份证号) 获取用户的身份信息
* @author 贺章鹏
* @date 2016年10月28日 下午12:15:24 
*  
*/
public class IdCardInfoUtils {
	 // 省份    
    private String province;    
    // 城市    
    private String city;    
    // 区县    
    private String region;    
    // 年份    
    private int year;    
    // 月份    
    private int month;    
    // 日期    
    private int day;    
    // 性别    
    private String gender;    
    // 出生日期    
    private Date birthday;   
    //年龄  
    private int age;  
    
    //属相
    private String zodiac;
    
    //星座
    private String constellation;
    
    @SuppressWarnings("serial")
	public Map<String, String> zodiacCodeMap = new HashMap<String, String>() {    
        {    
        	/*
        	this.put("01", "猪");
            this.put("02", "鼠");
            this.put("03", "牛");
            this.put("04", "虎");
            this.put("05", "兔");
            this.put("06", "龙");
            this.put("07", "蛇");
            this.put("08", "马");
            this.put("09", "羊");
            this.put("10", "猴");
            this.put("11", "鸡");
            this.put("12", "狗");*/
        	
            this.put("01", "鼠");
            this.put("02", "牛");
            this.put("03", "虎");
            this.put("04", "兔");
            this.put("05", "龙");
            this.put("06", "蛇");
            this.put("07", "马");
            this.put("08", "羊");
            this.put("09", "猴");
            this.put("10", "鸡");
            this.put("11", "狗");
            this.put("12", "猪");
            
        }    
    };   
    
    @SuppressWarnings("serial")
    public Map<String, String> xzCodeMap = new HashMap<String, String>() {    
        {    
            this.put("01", "水瓶座");    
            this.put("02", "双鱼座");    
            this.put("03", "白羊座");    
            this.put("04", "金牛座");    
            this.put("05", "双子座");    
            this.put("06", "巨蟹座");    
            this.put("07", "狮子座");    
            this.put("08", "处女座");    
            this.put("09", "天秤座");    
            this.put("10", "天蝎座");  
            this.put("11", "射手座");
            this.put("12", "魔羯座");    
        }    
    };   
    
    @SuppressWarnings("serial")
	private Map<String, String> cityCodeMap = new HashMap<String, String>() {    
        {    
            this.put("11", "北京");    
            this.put("12", "天津");    
            this.put("13", "河北");    
            this.put("14", "山西");    
            this.put("15", "内蒙古");    
            this.put("21", "辽宁");    
            this.put("22", "吉林");    
            this.put("23", "黑龙江");    
            this.put("31", "上海");    
            this.put("32", "江苏");    
            this.put("33", "浙江");    
            this.put("34", "安徽");    
            this.put("35", "福建");    
            this.put("36", "江西");    
            this.put("37", "山东");    
            this.put("41", "河南");    
            this.put("42", "湖北");    
            this.put("43", "湖南");    
            this.put("44", "广东");    
            this.put("45", "广西");    
            this.put("46", "海南");    
            this.put("50", "重庆");    
            this.put("51", "四川");    
            this.put("52", "贵州");    
            this.put("53", "云南");    
            this.put("54", "西藏");    
            this.put("61", "陕西");    
            this.put("62", "甘肃");    
            this.put("63", "青海");    
            this.put("64", "宁夏");    
            this.put("65", "新疆");    
            this.put("71", "台湾");    
            this.put("81", "香港");    
            this.put("82", "澳门");    
            this.put("91", "国外");    
        }    
    };
        
    private IdcardValidate validator = null;    
        
    /**  
     * 通过构造方法初始化各个成员属性  
     */    
    public IdCardInfoUtils(String idcard) {    
        try {    
            validator = new IdcardValidate();    
            if (validator.isValidatedAllIdcard(idcard)) {    
                if (idcard.length() == 15) {    
                    idcard = validator.convertIdcarBy15bit(idcard);    
                }    
                // 获取省份    
                String provinceId = idcard.substring(0, 2);    
                Set<String> key = this.cityCodeMap.keySet();    
                for (String id : key) {    
                    if (id.equals(provinceId)) {    
                        this.province = this.cityCodeMap.get(id);    
                        break;    
                    }    
                }    
    
                // 获取性别    
                String id17 = idcard.substring(16, 17);    
                if (Integer.parseInt(id17) % 2 != 0) {    
                    this.gender = "01";//男  
                } else {    
                    this.gender = "02";//女
                }    
    
                // 获取出生日期
                String birthday = idcard.substring(6, 14);    
                Date birthdate = new SimpleDateFormat("yyyyMMdd")    
                        .parse(birthday);    
                this.birthday = birthdate;    
                GregorianCalendar currentDay = new GregorianCalendar();    
                currentDay.setTime(birthdate);    
                this.year = currentDay.get(Calendar.YEAR);    
                this.month = currentDay.get(Calendar.MONTH) + 1;    
                this.day = currentDay.get(Calendar.DAY_OF_MONTH);    
                  
                //获取年龄  
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy");  
                String year=simpleDateFormat.format(new Date());  
                //this.age=Integer.parseInt(year)-this.year;
                this.age = (Integer.parseInt(year) - this.year) + 1;
                
                
                //获取属相new
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(this.birthday);
                ZodiacTools zodiacTools = new ZodiacTools(calendar);
                zodiacTools.animalsYearCode();
                this.zodiac = zodiacTools.animalsYearCode();
                //System.out.println("zodiac:"+zodiac);
                
                /**** 2017-07-19 获取属相 OLD
                int x = (this.year - 3) % 12;
                char m='0';
                //this.zodiac = StringUtils.appendStr(String.valueOf(x+1),m,2);
                this.zodiac = StringUtils.appendStr(String.valueOf(x), m, 2);
                ***/
                //获取星座
                this.constellation=getConstellation(this.month,this.day);
                
                
            }    
        } catch (Exception e) {    
            e.printStackTrace();    
        }    
    }    
    
    /**  
     * @return the province  
     */    
    public String getProvince() {    
        return province;    
    }    
    
    /**  
     * @return the city  
     */    
    public String getCity() {    
        return city;    
    }    
    
    /**  
     * @return the region  
     */    
    public String getRegion() {    
        return region;    
    }    
    
    /**  
     * @return the year  
     */    
    public int getYear() {    
        return year;    
    }    
    
    /**  
     * @return the month  
     */    
    public int getMonth() {    
        return month;    
    }    
    
    /**  
     * @return the day  
     */    
    public int getDay() {    
        return day;    
    }    
    
    /**  
     * @return the gender  
     */    
    public String getGender() {    
        return gender;    
    }    
    
    /**  
     * @return the birthday  
     */    
    public Date getBirthday() {    
        return birthday;    
    }    
   
    public int getAge() {  
        return age;  
    }  
  
    public void setAge(int age) {  
        this.age = age;  
    }    
    
    
	public String getZodiac() {
		return zodiac;
	}

	public void setZodiac(String zodiac) {
		this.zodiac = zodiac;
	}

	public String getConstellation() {
		return constellation;
	}

	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}

	public static String getConstellation(int month,int day) {
		String strValue = "";
		if ((month == 1 && day >= 20) || (month == 2 && day <= 18)) {
			strValue = "01";//水瓶座
		} else if ((month == 2 && day >= 19) || (month == 3 && day <= 20)) {
			strValue = "02";//双鱼座
		} else if ((month == 3 && day > 20) || (month == 4 && day <= 19)) {
			strValue = "03";//白羊座
		} else if ((month == 4 && day >= 20) || (month == 5 && day <= 20)) {
			strValue = "04";//金牛座
		} else if ((month == 5 && day >= 21) || (month == 6 && day <= 21)) {
			strValue = "05";//双子座
		} else if ((month == 6 && day > 21) || (month == 7 && day <= 22)) {
			strValue = "06";//巨蟹座
		} else if ((month == 7 && day > 22) || (month == 8 && day <= 22)) {
			strValue = "07";//狮子座
		} else if ((month == 8 && day >= 23) || (month == 9 && day <= 22)) {
			strValue = "08";//处女座
		} else if ((month == 9 && day >= 23) || (month == 10 && day <= 23)) {
			strValue = "09";//天秤座
		} else if ((month == 10 && day > 23) || (month == 11 && day <= 22)) {
			strValue = "10";//天蝎座
		} else if ((month == 11 && day > 22) || (month == 12 && day <= 21)) {
			strValue = "11";//射手座
		} else if ((month == 12 && day > 21) || (month == 1 && day <= 19)) {
			strValue = "12";//魔羯座
		}
		return strValue;
	}

    @Override    
    public String toString() {    
        return "省份：" + this.province + ",性别：" + this.gender + ",出生日期："    
                + this.birthday+"属相:"+zodiacCodeMap.get(this.zodiac)+",星座:"+xzCodeMap.get(this.constellation);    
    }
    public static void main(String[] args) {    
    	
    	String[] idcard = {
    			"340826199008241236",
    			"321182199109050519",
    			"321283199306301817",
    			"411081199006124597",
    			"372925199601028010",
    			"320723197611174843",
    			"362426199203040038",
    			"310227198709290018",
    			"35012119920725623X",
    			"132430197711031421",
    			"370826198508214032",
    			"31011219890222563X",
    	};
    	for(int i = 0; i < idcard.length; i++){
    		IdCardInfoUtils ie = new IdCardInfoUtils(idcard[i]);    
            System.out.println(idcard[i]+" : "+ie.zodiac+" : "+ie.toString());
    	}
    	
    	
    	/**
        String idcard = "422325197107170065";    
        IdCardInfoUtils ie = new IdCardInfoUtils(idcard);    
        System.out.println(ie.toString());
        **/ 
    }

}
