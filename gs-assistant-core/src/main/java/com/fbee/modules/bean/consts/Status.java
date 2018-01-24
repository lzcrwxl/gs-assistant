package com.fbee.modules.bean.consts;

import java.util.Map;

import com.google.common.collect.Maps;

/** 
* @ClassName: status 
* @Description: TODO
* @author 贺章鹏
* @date 2016年12月28日 上午11:36:20 
*  
*/
public class Status {

	//操作动作
	public static class Actions{
		public static final String LOGIN="01";//登陆
    	public static final String LOGOUT="02";//登出
    	public static final String ADD="03";//添加
    	public static final String MODIFY="04";//修改
    	public static final String DELETE="05";//
	}
	
	public static class OnOffShelf{
		public static final String ON_SHELF="1";//上架
		public static final String OFF_SHELF="0";//下架		
	}
	
	public static class MediaType{
		public static final String VIDEO="01";//视频
		public static final String PICTURE="02";//图片
	}
	
	public static class IsDefault{
		public static final String TRUE="1";//是
		public static final String FALSE="0";//否
	}
	
	public static class Level{
		public static final String BASE_LEVEL="0";//顶级
		public static final String FIRST_LEVEL="1";//一级
	}
	
	public static class AreaLevel{
		public static final String BASE_LEVEL="00";//顶级
		public static final String PROVICE_LEVEL="01";//省
		public static final String CITY_LEVEL="02";//市
		public static final String COUNTY_LEVEL="03";//区
	}
	
	//字典参数
	public static class DictionaryTypeCode{
		public static final String PROVICE_CACHE="c001";//省
		public static final String CITY_CACHE="c002";//市
		public static final String COUNTY_CACHE="c003";//区
		public static final String SERVICE_TYPES="c004";//服务工种
		public static final String SERVICE_SKILLS="c005";//服务技能
		public static final String SERVICE_PRICE="c006";//价格
		public static final String EXPERIENCE="c007";//经验
		public static final String AGE_INTERVAL="c008";//年龄段
		public static final String ZODIAC="c009";//属相
		public static final String NATIVE_PLACE="c010";//籍贯
		public static final String NATION="c011";//民族
		public static final String EDUCATION="c012";//学历
		public static final String PERSON_FEATURES="c013";//个人特点
		public static final String SERVICE_NATURES="c014";//服务类型
		public static final String PIRCE_UNIT="c015";//服务价格单位
		public static final String MEMBER_SEX = "c016";//性别
		public static final String BLOOD_TYPE = "c017";//血型
		public static final String MARITAL_STATUS = "c018";//婚姻
		public static final String FERTILITY_STATUS = "c019";//生育
		public static final String HOUSE_TYPE = "c020";//住宅类型
		public static final String CHILDREN_AGE = "c021";//儿童年龄
		public static final String OLD_AGE = "c022";//老人年龄
		public static final String SELF_CARES = "c023";//老人能否自理
		public static final String PAY_TYPE = "c024";//收支类型
		public static final String MANAGE_TYPE = "c025";//管理方式
		public static final String SERVICE_TYPE = "c026";//服务类型
		public static final String WORK_STATUS = "c027";//工作状态
		public static final String CERT_TYPE = "c028";//证书名称
		public static final String TYPE = "c029";//证书类型
		public static final String CERTIFIED_STATUS = "c030";//证书状态
		public static final String ORDER_STATUS = "c031";//订单状态
		public static final String ORDER_SOURCE = "c032";//订单状态
		public static final String AUTHENTICAT_GRADE="c033";//鉴定等级
		public static final String CERT_AUTHORITY="c034";//认证机构
		public static final String DATE_QUERY_PARAM="c035";//查询类型/按月按天按小时
	}
	
	public static class CertifiedStatus{
		public static final String UN_CERTIFIED="01";//未认证
		public static final String CERTIFIED="02";//已认证
		public static final String UN_PASS="03";//审核不通过
	}
	
	public static class WorkStatus{
		public static final String TO_BE_EMPLOYED="01";//待聘
		public static final String SERVICE="02";//服务中
	}
	
	public static String getDesc(String status) {
		return msg.get(status);
	}
	
	public static Map<String, String> msg = Maps.newHashMap();
	
	static {
		msg.put(OnOffShelf.ON_SHELF, "上架");
		msg.put(OnOffShelf.OFF_SHELF, "下架");
		msg.put(WorkStatus.TO_BE_EMPLOYED, "待聘");
		msg.put(WorkStatus.SERVICE, "服务中");
		
		msg.put(DictionaryTypeCode.PROVICE_CACHE, "PROVICE_CACHE");
		msg.put(DictionaryTypeCode.CITY_CACHE, "CITY_CACHE");
		msg.put(DictionaryTypeCode.COUNTY_CACHE, "COUNTY_CACHE");
		msg.put(DictionaryTypeCode.SERVICE_TYPES, "SERVICE_TYPES");
		msg.put(DictionaryTypeCode.SERVICE_SKILLS, "SERVICE_SKILLS");
		msg.put(DictionaryTypeCode.SERVICE_PRICE, "SERVICE_PRICE");
		msg.put(DictionaryTypeCode.EXPERIENCE, "EXPERIENCE");
		msg.put(DictionaryTypeCode.AGE_INTERVAL, "AGE_INTERVAL");
		msg.put(DictionaryTypeCode.ZODIAC, "ZODIAC");
		msg.put(DictionaryTypeCode.NATION, "NATION");
		msg.put(DictionaryTypeCode.EDUCATION, "EDUCATION");
		msg.put(DictionaryTypeCode.PERSON_FEATURES, "PERSON_FEATURES");
		msg.put(DictionaryTypeCode.SERVICE_NATURES, "SERVICE_NATURES");
		msg.put(DictionaryTypeCode.PIRCE_UNIT, "SERVICE_PRICE_UNIT");
		msg.put(DictionaryTypeCode.NATIVE_PLACE, "NATIVE_PLACE");
		msg.put(DictionaryTypeCode.MEMBER_SEX, "MEMBER_SEX");
		msg.put(DictionaryTypeCode.BLOOD_TYPE, "BLOOD_TYPE");
		msg.put(DictionaryTypeCode.MARITAL_STATUS, "MARITAL_STATUS");
		msg.put(DictionaryTypeCode.FERTILITY_STATUS, "FERTILITY_STATUS");
		msg.put(DictionaryTypeCode.HOUSE_TYPE, "HOUSE_TYPE");
		msg.put(DictionaryTypeCode.CHILDREN_AGE, "CHILDREN_AGE");
		msg.put(DictionaryTypeCode.OLD_AGE, "OLD_AGE");
		msg.put(DictionaryTypeCode.SELF_CARES, "SELF_CARES");
		msg.put(DictionaryTypeCode.PAY_TYPE, "PAY_TYPE");
		msg.put(DictionaryTypeCode.MANAGE_TYPE, "MANAGE_TYPE");
		msg.put(DictionaryTypeCode.SERVICE_TYPE, "SERVICE_TYPE");
		msg.put(DictionaryTypeCode.WORK_STATUS, "WORK_STATUS");
		msg.put(DictionaryTypeCode.CERT_TYPE, "CERT_TYPE");
		msg.put(DictionaryTypeCode.TYPE, "TYPE");
		msg.put(DictionaryTypeCode.CERTIFIED_STATUS, "CERTIFIED_STATUS");
		msg.put(DictionaryTypeCode.ORDER_STATUS, "ORDER_STATUS");
		msg.put(DictionaryTypeCode.ORDER_SOURCE, "ORDER_SOURCE");
		msg.put(DictionaryTypeCode.AUTHENTICAT_GRADE, "AUTHENTICAT_GRADE");//鉴定等级
		msg.put(DictionaryTypeCode.CERT_AUTHORITY, "CERT_AUTHORITY");//认证机构
		msg.put(DictionaryTypeCode.DATE_QUERY_PARAM, "DATE_QUERY_PARAM");//
	}
	public static class SmsContentType{
		public static final String RESET_PWD_SMS_CODE = "01";//密码重置
		public static final String REG_ASS_SMS_CODE = "04";//门店助手注册
		public static final String REG_SMS_CODE = "02";//注册登录
		public static final String PAY_SUCCESS = "03";//支付成功

	}
	
	public static class SmsResult{
		public static final String SUBMIT_SUCCESS = "01";//信息发送成功
		public static final String SUBMIT_FAILURE ="02";//信息发送失败
	}
	
	public static class ScoresLevel{
		public static final String ALL_MATCH = "1";//全匹配
		public static final String NO_MATCH ="0";//不匹配
		public static final String PART_MATCH ="2";//部分匹配
	}
	
	public static class ServiceTypes{
		public static final String ST_YS = "01";//月嫂
		public static final String ST_YYS ="02";//育婴师
		public static final String ST_BM ="03";//保姆
		public static final String ST_YLPH ="04";//养老陪护
		public static final String ST_ZDG ="05";//钟点工
		public static final String ST_JTGJ ="06";//家庭管家
	}
	
	public static class CertTypes{
		public static final String CT_MYHL = "01";//母婴护理
		public static final String CT_GJMYHL ="02";//高级母婴护理
		public static final String CT_YYS ="03";//育婴师证
		public static final String CT_CRS ="04";//催乳师证
		public static final String CT_JZFW ="05";//家政服务证
		public static final String CT_YLZH ="06";//医疗照护证
		public static final String CT_JTYYS="07";//家庭营养师证
		public static final String CT_YLHL="08";//养老护理证
		public static final String CT_ZJZ="09";//早教证
		public static final String CT_JKZ="10";//健康证
		public static final String CT_JSZ="11";//驾驶证
	}
	
	//0无级别1：初级 2 中级 3 高级 4：专项
	public static class CertLevels{
		public static final String NO_LEVEL = "0";
		public static final String CT_CHUJI ="1";
		public static final String CT_ZHONGJI ="2";
		public static final String CT_GAOJI ="3";
		public static final String CT_ZX ="4";
	}
	
	public static class MatchCertKey{
		public static final String MATCH_MYHL = "01";//母婴护理
		public static final String MATCH_GJMYHL ="02";//高级母婴护理
		public static final String MATCH_YYSCJ ="03";//育婴师证初级
		public static final String MATCH_YYSZJ ="04";//育婴师证中级
		public static final String MATCH_CRS ="05";//催乳师证
		public static final String MATCH_JZFW ="06";//家政服务证
		public static final String MATCH_YLZH ="07";//医疗照护证
		public static final String MATCH_JTYYS="08";//家庭营养师证
		public static final String MATCH_YLHL="09";//养老护理证
		public static final String MATCH_ZJ="10";//早教证
	}
	
}
