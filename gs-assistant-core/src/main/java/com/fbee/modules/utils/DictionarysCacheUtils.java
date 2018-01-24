package com.fbee.modules.utils;

import com.fbee.modules.bean.*;
import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.bean.consts.Status;
import com.fbee.modules.bean.consts.SysSetting;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.redis.JedisTemplate;
import com.fbee.modules.redis.JedisUtils;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author 贺章鹏
 * @ClassName: DictionarysCacheUtils
 * @Description: 字典形参数和常量参数缓存方法
 * @date 2017年1月17日 下午1:26:33
 */
public class DictionarysCacheUtils {

    private static JedisTemplate redis = JedisUtils.getJedisTemplate();

    /**
     * 获取区域列表--省
     *
     * @return
     */
    public static List<AreaCacheBean> getProviceList() {
        return getAreaList(CacheKeys.PROVICE_CACHE, null);
    }

    /**
     * 获取区域列表--市
     *
     * @return
     */
    public static List<AreaCacheBean> getCityList(String pcode) {
        return getAreaList(CacheKeys.CITY_CACHE, pcode);
    }

    /**
     * 获取区域列表--区
     *
     * @return
     */
    public static List<AreaCacheBean> getCountyList(String pcode) {
        return getAreaList(CacheKeys.COUNTY_CACHE, pcode);
    }

    /**
     * 获取服务工种
     *
     * @return
     */
    public static List<DictionarysCacheBean> getServiceTypeList() {
        return getList(CacheKeys.SERVICE_TYPES);
    }

    /**
     * 获取价格List
     *
     * @return
     */
    public static List<DictionarysCacheBean> getServicePirceList() {
        return getList(CacheKeys.SERVICE_PRICE);
    }

    /**
     * 获取经验List
     *
     * @return
     */
    public static List<DictionarysCacheBean> getExperienceList() {
        return getList(CacheKeys.EXPERIENCE);
    }

    /**
     * 获取年龄区间段List
     *
     * @return
     */
    public static List<DictionarysCacheBean> getAgeIntervalList() {
        return getList(CacheKeys.AGE_INTERVAL);
    }

    /**
     * 获取籍贯List
     *
     * @return
     */
    public static List<DictionarysCacheBean> getNativePlaceList() {
        return getList(CacheKeys.NATIVE_PLACE);
    }

    /**
     * 获取属相List
     *
     * @return
     */
    public static List<DictionarysCacheBean> getZodiacList() {
        return getList(CacheKeys.ZODIAC);
    }

    /**
     * 获取学历List
     *
     * @return
     */
    public static List<DictionarysCacheBean> getEducationList() {
        return getList(CacheKeys.EDUCATION);
    }

    /**
     * 获取民族List
     *
     * @return
     */
    public static List<DictionarysCacheBean> getNationList() {
        return getList(CacheKeys.NATION);
    }

    /**
     * 获取省中文
     *
     * @return
     */
    public static String getProviceName(String code) {
        return getAreaName(CacheKeys.PROVICE_CACHE, code);
    }

    /**
     * 获取市中文
     *
     * @return
     */
    public static String getCityName(String code) {
        return getAreaName(CacheKeys.CITY_CACHE, code);
    }

    /**
     * 获取区中文
     *
     * @return
     */
    public static String getCountyName(String code) {
        return getAreaName(CacheKeys.COUNTY_CACHE, code);
    }

    /**
     * 获取服务工种中文
     *
     * @return
     */
    public static String getServiceTypeName(String code) {
        return getName(CacheKeys.SERVICE_TYPES, code);
    }

    /**
     * 获取服务价格中文
     *
     * @return
     */
    public static String getServicePirceName(String code) {
        return getName(CacheKeys.SERVICE_PRICE, code);
    }

    /**
     * 获取民族中文
     *
     * @return
     */
    public static String getNationName(String code) {
        return getName(CacheKeys.NATION, code);
    }

    /**
     * 获取年龄段中文
     *
     * @return
     */
    public static String getAgeIntervalName(String code) {
        return getName(CacheKeys.AGE_INTERVAL, code);
    }

    /**
     * 获取学历中文
     *
     * @return
     */
    public static String getEducationName(String code) {
        if(StringUtils.isBlank(code)){
            return "";
        }
        return getName(CacheKeys.EDUCATION, code);
    }

    /**
     * 获取籍贯中文
     *
     * @return
     */
    public static String getNativePlaceName(String code) {
        return getName(CacheKeys.NATIVE_PLACE, code);
    }

    /**
     * 获取经验中文
     *
     * @return
     */
    public static String getExperienceName(String code) {
        return getName(CacheKeys.EXPERIENCE, code);
    }

    /**
     * BOND
     * 获取属相中文
     *
     * @return
     */
    public static String getZodiacName(String code) {
        if(StringUtils.isBlank(code)){
            return "";
        }
        return getName(CacheKeys.ZODIAC, code);
    }

    /**
     * 获取性别中文
     *
     * @return
     */
    public static String getSexName(String code) {
        return getName(CacheKeys.SEX, code);
    }

    /**
     * 获取婚姻状况中文
     *
     * @return
     */
    public static String getMaritalName(String code) {
        return getName(CacheKeys.MARITAL, code);
    }

    /**
     * 获取生育情况中文
     *
     * @return
     */
    public static String getFertilityName(String code) {
        return getName(CacheKeys.FERTILITY, code);
    }

    /**
     * 获取血型中文
     *
     * @return
     */
    public static String getBloodName(String code) {
        return getName(CacheKeys.BLOOD, code);
    }

    /**
     * 获取住宅类型中文
     *
     * @return
     */
    public static String getHouseType(String code) {
        return getName(CacheKeys.HouseType, code);
    }

    /**
     * 获取管理方式
     *
     * @param code
     * @return
     */
    public static String getManageWay(String code) {
        return getName(CacheKeys.MANAGE_TYPE, code);
    }

    /**
     * 获取儿童年龄中文
     *
     * @return
     */
    public static String getChildrenAge(String code) {
        return getName(CacheKeys.ChildrenAge, code);
    }

    /**
     * 获取老人年龄中文
     *
     * @return
     */
    public static String getOldAge(String code) {
        return getName(CacheKeys.OldAge, code);
    }

    /**
     * 获取老人能否自理中文
     *
     * @return
     */
    public static String getSelfCares(String code) {
        return getName(CacheKeys.SelfCares, code);
    }

    /**
     * 获取收支类型中文
     *
     * @return
     */
    public static String getPayType(String code) {
        return getName(CacheKeys.PayType, code);
    }

    /**
     * 获取服务类型类型中文
     *
     * @return
     */
    public static String getServiceType(String code) {
        return getName(CacheKeys.ServiceType, code);
    }


    /**
     * 区域（表 areas）--根据缓存key值获取List
     *
     * @param cacheKey
     * @return
     */
    public static List<AreaCacheBean> getAreaList(String cacheKey) {
        Collection<String> cos = redis.hgetAll(cacheKey).values();
        List<AreaCacheBean> cc = Lists.newArrayList();
        for (String c : cos) {
            cc.add(JsonUtils.fromJson(c, AreaCacheBean.class));
        }
        return cc;
    }

    /**
     * 区域（表 areas）--根据缓存键值和父级id获取对应的区域list
     *
     * @param cacheKey
     * @param pcode
     * @return
     */
    public static List<AreaCacheBean> getAreaList(String cacheKey, String pcode) {
        List<AreaCacheBean> list = getAreaList(cacheKey);
        if (cacheKey.equals(CacheKeys.PROVICE_CACHE)) {
            return list;
        }
        List<AreaCacheBean> resultList = Lists.newArrayList();
        for (AreaCacheBean bean : list) {
            if (pcode.equals(bean.getParentCode())) {
                resultList.add(bean);
            }
        }
        return resultList;
    }

    /**
     * 区域（表 areas）--获取区域中文名称
     *
     * @param code
     * @return
     */
    public static String getAreaName(String cacheKey, String code) {
        Map<String, String> dbean = redis.hgetAll(cacheKey);
        if (dbean == null) {
            return Constants.EMPTY;
        }
        String str = dbean.get(code);
        if (StringUtils.isBlank(str)) {
            return Constants.EMPTY;
        }
        return JsonUtils.fromJson(str, AreaCacheBean.class).getAreaName();
    }

    /**
     * 字典数据型（表 dictionarys）--根据缓存key值获取List
     *
     * @param cacheKey
     * @return
     */
    public static List<DictionarysCacheBean> getList(String cacheKey) {
        DictionarysCacheBean dbean = null;
        Collection<String> list = redis.hgetAll(cacheKey).values();
        List<DictionarysCacheBean> relist = Lists.newArrayList();
        for (String by : list) {
            dbean = JsonUtils.fromJson(by, DictionarysCacheBean.class);
            relist.add(dbean);
        }
        Collections.sort(relist);
        return relist;
    }

    /**
     * 字典数据型（表 dictionarys）--获取参数中文值
     *
     * @param code
     * @return
     */
    public static String getName(String cacheKey, String code) {
        if (StringUtils.isBlank(code) || StringUtils.isBlank(cacheKey)) {
            return Constants.EMPTY;
        }
        Map<String, String> os = redis.hgetAll(cacheKey);
        String o = os.get(code);
        DictionarysCacheBean dbean = JsonUtils.fromJson(o, DictionarysCacheBean.class);
        if (dbean == null) {
            return Constants.EMPTY;
        }
        return dbean.getName();
    }

    /**
     * 服务技能表（表service_skills）--根据键值获取对应服务技能list
     *
     * @param cacheKey
     * @return
     */
    public static List<ServiceKillsCacheBean> getSkillList(String cacheKey) {
        ServiceKillsCacheBean dbean = null;
        Collection<String> list = redis.hgetAll(cacheKey).values();
        List<ServiceKillsCacheBean> relist = Lists.newArrayList();
        for (String by : list) {
            dbean = JsonUtils.fromJson(by, ServiceKillsCacheBean.class);
            relist.add(dbean);
        }
        Collections.sort(relist);
        return relist;
    }

    /**
     * 获取个人特点list
     *
     * @param code
     * @return
     */
    public static List<ServiceKillsCacheBean> getFeaturesList(String code) {
        StringBuffer cacheKey = new StringBuffer();
        cacheKey.append(CacheKeys.PERSON_FEATURES).append(code).append(Status.Level.FIRST_LEVEL);
        List<ServiceKillsCacheBean> child = getSkillList(cacheKey.toString());
        return child;
    }

    /**
     * 获取服务技能list
     *
     * @param code
     * @return
     */
    public static List<ServiceKillsCacheBean> getServiceList(String code) {
        StringBuffer cacheKey = new StringBuffer();
        cacheKey.append(CacheKeys.SERVICE_SKILLS).append(code).append(Status.Level.FIRST_LEVEL);
        List<ServiceKillsCacheBean> child = DictionarysCacheUtils.getSkillList(cacheKey.toString());
        return child;
    }


    /**
     * 获取根据阿姨（或订单存储的个人特点）值对应的值
     *
     * @param code
     * @param features
     * @return
     */
    public static List<ServiceKillsCacheBean> getFeaturesList(String code, String features) {
        return getSkills(CacheKeys.PERSON_FEATURES, code, features);
    }

    /**
     * 对于（01）语言特点、（02）性格特点、（03）烹饪特点  （04）技能特点
     * 获取根据阿姨（或订单存储的个人特点）值对应的值字符串例子：语言 01 01,02,03,04 返回英语,普通话
     *
     * @param code
     * @param features
     * @return
     */
    public static String getFeaturesStr(String code, String features) {
        return splicingName(getFeaturesList(code, features));
    }

    /**
     * 获取根据阿姨（或订单存储的技能特点）值对应的值
     *
     * @param code
     * @param features
     * @return
     */
    public static List<ServiceKillsCacheBean> getSkillsList(String code, String features) {
        return getSkills(CacheKeys.SERVICE_SKILLS, code, features);
    }

    /**
     * 获取根据阿姨（或订单存储的技能特点）值对应值字符串
     *
     * @param code
     * @param features
     * @return
     */
    public static String getSkillsStr(String code, String features) {
        return splicingName(getSkillsList(code, features));
    }

    /**
     * 服务类型
     *
     * @param code
     * @param features
     * @return
     */
    public static List<ServiceKillsCacheBean> getServiceNatureList(String code, String features) {
        return getSkills(CacheKeys.SERVICE_NATURES, code, features);
    }

    /**
     * 服务类型--用户根据服务工种获取服务类型list
     *
     * @param code
     * @return
     */
    public static List<ServiceKillsCacheBean> getServiceNatureList(String code) {
        StringBuffer cacheKey = new StringBuffer();
        cacheKey.append(CacheKeys.SERVICE_NATURES).append(code).append(Status.Level.FIRST_LEVEL);
        return DictionarysCacheUtils.getSkillList(cacheKey.toString());
    }

    /**
     * 服务类型中文
     *
     * @param code     服务工种
     * @param features 服务类型
     * @return
     */
    public static String getServiceNatureStr(String code, String features) {
        return splicingName(getServiceNatureList(code, features));
    }

    /**
     * 获取技能特点
     *
     * @param cacheCode
     * @param code
     * @param features
     * @return
     */
    public static List<ServiceKillsCacheBean> getSkills(String cacheCode, String code, String features) {
        StringBuffer cacheKey = new StringBuffer();
        cacheKey.append(cacheCode).append(code).append(Status.Level.FIRST_LEVEL);
        if (StringUtils.isBlank(features)) {
            return null;
        }
        List<ServiceKillsCacheBean> list = Lists.newArrayList();
        for (String key : features.split(",")) {
            String str = redis.hget(cacheKey.toString(), key);
            if (StringUtils.isNotBlank(str)) {
                list.add(JsonUtils.fromJson(str, ServiceKillsCacheBean.class));
            }
        }
        return list;
    }

    public static String splicingName(List<ServiceKillsCacheBean> list) {
        if (list == null || list.size() < 1) {
            return Constants.EMPTY;
        }
        StringBuffer result = new StringBuffer();
        for (ServiceKillsCacheBean bean : list) {
            result.append(bean.getSkillsValue()).append(Constants.COMMA);
        }
        if (result.length() > 0) {
            return result.deleteCharAt(result.length() - 1).toString();
        }
        return Constants.EMPTY;
    }


    /**
     * 根据键值获取匹配得分
     *
     * @param key
     * @return
     */
    public static String getSkillsScores(String key) {
        Map<String, String> relist = getAllSkillsScores();
        return relist.get(key);
    }

    /**
     * 获取技能得分map
     *
     * @return
     */
    public static Map<String, String> getAllSkillsScores() {
        Map<String, String> relist = redis.hgetAll(CacheKeys.MATCH_SCORES);
        return relist;
    }


    /*
     * 对于（01）语言特点、（02）性格特点、（03）烹饪特点
     * 例子 前端传输02,01,04 后端数据 有01到07值转换为二进制表示数据为0001011
     * code个人特点
     */
    public static String featuresCoverBinary(String code, String fontStr) {
        StringBuffer cacheKey = new StringBuffer();
        cacheKey.append(CacheKeys.PERSON_FEATURES).append(code).append(Status.Level.FIRST_LEVEL);
        List<ServiceKillsCacheBean> child = DictionarysCacheUtils.getSkillList(cacheKey.toString());
        int i = 1, n = child.size();
        StringBuffer result = new StringBuffer();
        for (; i <= n; i++) {
            if (containStr(fontStr, StringUtils.appendStrZero(String.valueOf(i), 2))) {
                result.append("1");
            } else {
                result.append("0");
            }
        }
        return StringUtils.strReverse(result.toString());
    }


    public static Boolean containStr(String aimsStr, String containStr) {
        try {
            return aimsStr.indexOf(containStr) != -1;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    public static String[] coverArry(String str) {
        return StringUtils.split(str, Constants.COMMA);
    }

    /**
     * 获取星座List
     * fan
     *
     * @return
     */
    public static List<DictionarysCacheBean> getConstellationList() {
        return getList(CacheKeys.CONSTELLATION);
    }

    /**
     * 获取工作状态List
     * fan
     *
     * @return
     */
    public static List<DictionarysCacheBean> getWorkStatusList() {
        return getList(CacheKeys.WORK_STATUS);
    }

    /**
     * 获取住宅类型List
     * fan
     *
     * @return
     */
    public static List<DictionarysCacheBean> getHouseTypeList() {
        return getList(CacheKeys.HOUSE_TYPE);
    }

    /**
     * 获取星座中文fanry
     * 获取星座中文fan
     *
     * @return
     */
    public static String getConstellationName(String code) {
        return getName(CacheKeys.CONSTELLATION, code);
    }

    /**
     * 获取工作状态中文fanry
     * 获取工作状态中文fan
     *
     * @return
     */
    public static String getWorkStatusName(String code) {
        return getName(CacheKeys.WORK_STATUS, code);
    }

    /**
     * 获取住宅类型中文fanry
     * 获取住宅类型中文fan
     *
     * @return
     */
    public static String getHouseTypeName(String code) {
        return getName(CacheKeys.HOUSE_TYPE, code);
    }

    /**
     * 获取服务工种价格单位
     *
     * @param code
     * @return
     */
    public static String getServicePriceUnit(String code) {
        return getName(CacheKeys.SERVICE_PRICE_UNIT, code);
    }

    /**
     * 获取婚姻状况
     *
     * @param code
     * @return
     */
    public static String getMaritalStatus(String code) {
        return getName(CacheKeys.MARITAL_STATUS, code);
    }

    /**
     * 获取生育状况
     *
     * @param code
     * @return
     */
    public static String getFertilityStatus(String code) {
        return getName(CacheKeys.FERTILITY_STATUS, code);
    }

    /**
     * 获取血型
     *
     * @param code
     * @return
     */
    public static String getBloodType(String code) {
        return getName(CacheKeys.BLOOD_TYPE, code);
    }

    /**
     * 获取性别
     *
     * @param code
     * @return
     */
    public static String getMemberSex(String code) {
        return getName(CacheKeys.MEMBER_SEX, code);
    }

    /**
     * 获取证书名称
     */
    public static String getCertTypeName(String code) {
        return getName(CacheKeys.CERT_TYPE, code);
    }

    /**
     * 获取证书类型
     */
    public static String getTypeName(String code) {
        return getName(CacheKeys.TYPE, code);
    }

    /**
     * 获取证书状态
     */
    public static String getCertStatusName(String code) {
        return getName(CacheKeys.CERTIFIED_STATUS, code);
    }

    /**
     * 获取民族code
     */
    public static String getNationCode(String nation) {
        //获取民族code
        List<DictionarysCacheBean> nations = getNationList();
        nation = nation + "族";
        for (DictionarysCacheBean bean : nations) {
            if (nation.equals(bean.getName())) {
                return bean.getCode();
            }
        }
        return "";
    }

    /**
     * 获取籍贯code
     */
    public static String getNativePlaceCode(String nativePlace) {
        //获取民族code
        List<DictionarysCacheBean> NativePlaces = getNativePlaceList();
        for (DictionarysCacheBean bean : NativePlaces) {
            if (nativePlace.equals(bean.getName())) {
                return bean.getCode();
            }
        }
        return "";
    }

    /**
     * @param code 鉴定等级
     * @return @
     * @author xiehui
     */
    public static String getAuthenticatGrage(String code) {
        return getName(CacheKeys.AUTHENTICAT_GRADE, code);
    }

    /**
     * @param code 认证机构
     * @return @
     * @author xiehui
     */
    public static String getCertAuthority(String code) {
        return getName(CacheKeys.CERT_AUTHORITY, code);
    }

    /**
     * 系统设置
     * @return
     */
    public static String getSysSetting(SysSetting.Key key) {
        if (key == null) {
            return Constants.EMPTY;
        }
        String str = redis.hget(key.getCode(), "00");
        DictionarysCacheBean dbean = JsonUtils.fromJson(str, DictionarysCacheBean.class);
        if (dbean == null) {
            return Constants.EMPTY;
        }
        return dbean.getName();
    }

}
