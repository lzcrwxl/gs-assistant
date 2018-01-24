package com.fbee.modules.utils;

import com.fbee.modules.bean.*;
import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.bean.consts.Status;
import com.fbee.modules.core.config.Global;
import com.fbee.modules.core.utils.Reflections;
import com.fbee.modules.core.utils.StringUtils;
import com.google.common.collect.Sets;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author 贺章鹏
 * @ClassName: SkillMacthUtils
 * @Description: 系统技能匹配模型处理类
 * @date 2017年2月7日 上午10:30:44
 */
public class SkillMacthUtils {

    public SkillMacthUtils() {

    }

    private static volatile SkillMacthUtils instance;

    public static SkillMacthUtils getInstance() {
        if (instance == null) {
            synchronized (SkillMacthUtils.class) {
                if (instance == null) {
                    instance = new SkillMacthUtils();
                }
            }
        }
        return instance;
    }

    public MatchingModel dealMatchingModel(MatchingModel match, OrderMatchBean order, MatchSpecial matchSpecial) {
        /****************** 服务工种 *****************/
        match.setIsHasTrade(Status.ScoresLevel.ALL_MATCH);
        // 这里取的都是macth的服务类型，有疑问，@xiehui
        // match.setSeviceNature(matchNature(match.getSeviceNature(),match.getSeviceNature()));
        match.setSeviceNature(matchNature(match.getSeviceNature(), order.getServiceNature()));
        /****************** 个人特征 *****************/
        // 价格
        match.setPrice(matchPrice(match.getPrice(), order.getPrice()));
        // 年龄
        match.setAge(matchAge(match.getAge(), order.getAge()));
        // 籍贯
        match.setNativePlace(matchNpZo(match.getNativePlace(), order.getNativePlace()));
        // 属相
        match.setZodiac(matchNpZo(match.getZodiac(), order.getZodiac()));
        // 性别
        match.setSex(matchSex(match.getSex(), order.getSex()));
        // 学历
        match.setEducation(matchEducation(match.getEducation(), order.getEducation()));
        // 经验
        match.setExperience(matchExperience(match.getExperience(), order.getExperience()));

        /****************** 个人特点 *****************/
        match.setLanguage(matchLanguage(DictionarysCacheUtils.featuresCoverBinary("01", match.getLanguage()),
                DictionarysCacheUtils.featuresCoverBinary("01", order.getLanguage())));
        match.setCharacter(matchCharacter(DictionarysCacheUtils.featuresCoverBinary("02", match.getCharacter()),
                DictionarysCacheUtils.featuresCoverBinary("02", order.getCharacter())));
        match.setCooking(matchCooking(DictionarysCacheUtils.featuresCoverBinary("03", match.getCooking()),
                DictionarysCacheUtils.featuresCoverBinary("03", order.getCooking())));
        match.setIsHasOlder(matchNature(match.getIsHasOlder(), order.getIsHasOlder()));
        match.setIsHasPet(matchNature(match.getIsHasPet(), order.getIsHasPet()));
        /****************** 服务内容 *****************/
        // 获取阿姨技能
        Map<Integer, String> mapMatch = match.getServiceContents();
        // 获取客户订单技能
        Map<Integer, String> mapOrder = order.getServiceContents();
        // 如果客户订单技能不为空, 阿姨技能不为空
        if (mapOrder == null && mapMatch != null) {
            matchSpecial.setServiceContents(mapMatch);
        }
        return match;

    }

    // 获取匹配度
    @SuppressWarnings({"rawtypes", "unchecked"})
    public double getMatchRate(String serviceType, MatchingModel match, OrderMatchBean order) {

        // 开始计算得分逻辑
        BigDecimal result = BigDecimal.ZERO;
        BigDecimal master = new BigDecimal(100.0);
        if (match == null || Global.NO.equals(match.getIsHasTrade())) {// 工种情况不匹配-直接返回10%的比率
            return 0.1d;
        }

        // 首先处理订单和客户的匹配数据
        MatchSpecial matchSpecial = new MatchSpecial();
        // 处理完返回match
        dealMatchingModel(match, order, matchSpecial);

        StringBuffer sb = new StringBuffer();
        Map<String, String> relist = DictionarysCacheUtils.getAllSkillsScores();
        // 反射机制，可以获取该类的属性和方法
        Class s = match.getClass();
        // 获得某个类的声明字段
        Field[] fields = s.getDeclaredFields();
        for (Field field : fields) {
            if (field.getGenericType().toString().equals("class java.lang.String")) {
                sb.setLength(0);
                sb.append(serviceType).append(field.getName()).append(0)
                        .append(Reflections.getFieldValue(match, field.getName()));
                result = getScoreValue(result, relist, sb.toString(), field.getName(), matchSpecial);
            }
            // 证书Set<String>
            if (field.getGenericType().toString().equals("java.util.Set<java.lang.String>")) {
                Set<String> certs = (Set<String>) Reflections.getFieldValue(match, field.getName());
                if (null != certs && certs.size() > 0) {
                    for (String code : certs) {
                        sb.setLength(0);
                        sb.append(serviceType).append(field.getName()).append(code)
                                .append(Status.ScoresLevel.ALL_MATCH);
                        result = getScoreValue(result, relist, sb.toString(), field.getName(), matchSpecial);
                    }
                }
            }
            // 服务内容
            if (field.getGenericType().toString().equals("java.util.Map<java.lang.Integer, java.lang.String>")) {
                Map<Integer, String> contents = (Map<Integer, String>) Reflections.getFieldValue(match, field.getName());
                if (contents != null) {
                    for (Entry<Integer, String> entry : contents.entrySet()) {
                        sb.setLength(0);
                        String code = entry.getValue();
                        sb.append(serviceType).append(field.getName()).append(code).append(Status.ScoresLevel.ALL_MATCH);
                        result = getScoreValue(result, relist, sb.toString(), field.getName(), matchSpecial);
                    }
                }
            }

        }
        return result.divide(master, 4, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    // 计算的得分值
    public static BigDecimal getScoreValue(BigDecimal result, Map<String, String> relist, String key, String fieldName,
                                           MatchSpecial matchSpecial) {
        try {
            String value = relist.get(key);
            if (Reflections.getAccessibleField(matchSpecial, fieldName) != null) {
                String[] sresult = coverArry(value);// 获取数据库得分数据值
                int i = 0, n = sresult.length;
                for (i = 0; i < n; i++) {
                    result = result.add(new BigDecimal(sresult[i]));
                }
            } else {
                BigDecimal mm = new BigDecimal(value);
                result = result.add(mm);
            }
        } catch (Exception e) {
            result = result.add(BigDecimal.ZERO);
        }
        return result;
    }

    public static Boolean compareExperience(String staffStr, String orderStr) {
        try {
            String[] ages = coverArry(orderStr);
            return !(StringUtils.toInteger(ages[0]).compareTo(StringUtils.toInteger(staffStr)) >= 0);
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    public static Boolean comparePirce(String staffStr, String orderStr) {
        try {
            String[] prices = coverArry(orderStr);
            if (converStr(prices[0]).compareTo(converStr(prices[1])) > 0) {
                return Boolean.TRUE;
            }
            if (converStr(prices[1]).compareTo(converStr(staffStr)) <= 0) {
                return Boolean.FALSE;
            }
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    public static Boolean compareNpZo(String staffStr, String orderStr) {
        try {
            return orderStr.indexOf(staffStr) != -1;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    // 年龄算法
    public static Boolean compareAgeInterval(String staffStr, String orderStr) {
        try {
            // @xiehui 从数据字典查年龄段
            String age = DictionarysCacheUtils.getAgeIntervalName(orderStr);
            String[] ages = coverArry(age);
            Integer a = StringUtils.toInteger(ages[0]);
            if (ages.length > 1) {
                Integer a1 = StringUtils.toInteger(ages[1]);
                if (a > a1) {
                    if (StringUtils.toInteger(a1).compareTo(StringUtils.toInteger(staffStr)) >= 0) {
                        return Boolean.TRUE;
                    } else {
                        return Boolean.FALSE;
                    }
                } else {
                    if (a1 < StringUtils.toInteger(staffStr)) {
                        if (StringUtils.toInteger(a1).compareTo(StringUtils.toInteger(staffStr)) >= 0) {
                            return Boolean.TRUE;
                        } else {
                            return Boolean.FALSE;
                        }
                    } else {
                        if (StringUtils.toInteger(a).compareTo(StringUtils.toInteger(staffStr)) >= 0) {
                            return Boolean.FALSE;
                        } else {

                            return Boolean.TRUE;
                        }
                    }
                }
            } else {
                if (a < StringUtils.toInteger(staffStr)) {
                    return Boolean.TRUE;
                } else {
                    return Boolean.FALSE;
                }
            }

        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    // 匹配年龄
    public static String matchAge(String staffStr, String orderStr) {
        if (StringUtils.isBlank(orderStr)) {// 若为空全匹配
            return Status.ScoresLevel.NO_MATCH;
        } else {
            if (compareAgeInterval(staffStr, orderStr)) {
                return Status.ScoresLevel.ALL_MATCH;
            } else {
                return Status.ScoresLevel.NO_MATCH;
            }
        }
    }

    // 匹配价格
    public static String matchPrice(String staffStr, String orderStr) {

        if (StringUtils.isBlank(staffStr) || StringUtils.isNotBlank(orderStr)) {// 若为空不匹配
            return Status.ScoresLevel.NO_MATCH;
        } else {
            BigDecimal staffPrice = new BigDecimal(staffStr.substring(0, staffStr.indexOf(".")));
            BigDecimal newOrderPirce = new BigDecimal(orderStr);
            if (newOrderPirce.compareTo(staffPrice) >= 0) {
                return Status.ScoresLevel.ALL_MATCH;
            } else {
                return Status.ScoresLevel.NO_MATCH;
            }
        }
    }

    // 匹配籍贯 、属相
    public static String matchNpZo(String staffStr, String orderStr) {
        if (StringUtils.isBlank(orderStr)) {// 若为空全匹配
            return Status.ScoresLevel.ALL_MATCH;
        } else {
            if (compareNpZo(staffStr, orderStr)) {
                return Status.ScoresLevel.ALL_MATCH;
            } else {
                return Status.ScoresLevel.NO_MATCH;
            }
        }
    }

    // 匹配性别
    public static String matchSex(String staffStr, String orderStr) {
        if (StringUtils.isBlank(orderStr)) {// 若为空全匹配
            return Status.ScoresLevel.ALL_MATCH;
        } else {
            if (staffStr.equals(orderStr)) {
                return Status.ScoresLevel.ALL_MATCH;
            } else {
                return Status.ScoresLevel.NO_MATCH;
            }
        }
    }

    // 匹配学历
    public static String matchEducation(String staffStr, String orderStr) {
        if (StringUtils.isBlank(orderStr)) {// 若为空全匹配
            return Status.ScoresLevel.ALL_MATCH;
        } else {

            if (StringUtils.toInteger(staffStr) >= StringUtils.toInteger(orderStr)) {
                return Status.ScoresLevel.ALL_MATCH;
            } else {
                return Status.ScoresLevel.NO_MATCH;
            }
        }
    }

    // 匹配经验
    public static String matchExperience(String staffStr, String orderStr) {
        if (StringUtils.isBlank(orderStr)) {// 若为空全匹配
            return Status.ScoresLevel.NO_MATCH;
        } else {
            if (orderStr.equals(staffStr)) {
                return Status.ScoresLevel.ALL_MATCH;
            } else {
                return Status.ScoresLevel.NO_MATCH;
            }
        }
    }

    // 匹配语言
    public static String matchLanguage(String staffStr, String orderStr) {
        return trait(staffStr, orderStr);
    }

    // 匹配烹饪
    public static String matchCooking(String staffStr, String orderStr) {
        return trait(staffStr, orderStr);
    }

    // 匹配性格
    public static String matchCharacter(String staffStr, String orderStr) {
        return trait(staffStr, orderStr);
    }

    // 性格、语言和烹饪处理方法
    public static String trait(String staffStr, String orderStr) {
        if (StringUtils.isBlank(orderStr)) {// 若为空全匹配
            return Status.ScoresLevel.NO_MATCH;
        } else {
            if (Integer.parseInt(orderStr, 2) == 0) {
                return Status.ScoresLevel.NO_MATCH;
            }

            int r = dealAnd(staffStr, orderStr);
            if (r == 0) {
                return Status.ScoresLevel.NO_MATCH;
            } else if (r >= Integer.parseInt(orderStr, 2)) {
                return Status.ScoresLevel.ALL_MATCH;
            } else {
                return Status.ScoresLevel.PART_MATCH;
            }
        }
    }

    // 匹配服务类型住家不住家、饲养宠物、有老人
    public static String matchNature(String staffStr, String orderStr) {
        if (StringUtils.isBlank(orderStr) || StringUtils.isBlank(staffStr)) {// 若为空全匹配
            return Status.ScoresLevel.ALL_MATCH;
        } else {
            if (staffStr.equals(orderStr)) {
                return Status.ScoresLevel.ALL_MATCH;
            }
            return Status.ScoresLevel.NO_MATCH;
        }
    }

    public static String[] coverArry(String str) {
        return StringUtils.split(str, Constants.COMMA);
    }

    public static int dealAnd(String str1, String str2) {
        if (StringUtils.isBlank(str1) || StringUtils.isBlank(str2)) {
            return 0;
        }
        return Integer.parseInt(str1, 2) & Integer.parseInt(str2, 2);
    }

    // 转换为BigDecimal
    public static BigDecimal converStr(String str) {
        try {
            return new BigDecimal(str);
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    public static void main(String[] args) {
        // 测试匹配度
        OrderMatchBean order = new OrderMatchBean();
        MatchingModel match = new MatchingModel();
        Set<String> certs = Sets.newHashSet();
        certs.add("0101");
        certs.add("0102");
        certs.add("0103");
        certs.add("0104");
        String serviceType = "01";
        match.setIsHasTrade("1");
        match.setCerts(certs);
    }

}
