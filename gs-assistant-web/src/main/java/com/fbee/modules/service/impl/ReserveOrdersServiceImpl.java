package com.fbee.modules.service.impl;

import com.fbee.modules.basic.WebUtils;
import com.fbee.modules.bean.ErrorMsg;
import com.fbee.modules.bean.MatchingModel;
import com.fbee.modules.bean.OrderMatchBean;
import com.fbee.modules.bean.UserBean;
import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.bean.consts.ErrorCode;
import com.fbee.modules.core.Log;
import com.fbee.modules.core.page.Page;
import com.fbee.modules.core.utils.DateUtils;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.form.CustomerSaveForm;
import com.fbee.modules.form.ReserveOrderDetailsForm;
import com.fbee.modules.form.ReserveOrdersForm;
import com.fbee.modules.form.StaffListForm;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.jsonData.extend.StaffQueryJson;
import com.fbee.modules.mybatis.dao.*;
import com.fbee.modules.mybatis.entity.*;
import com.fbee.modules.mybatis.model.*;
import com.fbee.modules.operation.ReserveOrdersOpt;
import com.fbee.modules.operation.StaffsOpt;
import com.fbee.modules.redis.JedisTemplate;
import com.fbee.modules.redis.JedisUtils;
import com.fbee.modules.service.CommonService;
import com.fbee.modules.service.CustomerService;
import com.fbee.modules.service.ReserveOrdersService;
import com.fbee.modules.service.StaffsService;
import com.fbee.modules.service.basic.BaseService;
import com.fbee.modules.utils.DictionarysCacheUtils;
import com.fbee.modules.utils.JsonUtils;
import com.fbee.modules.utils.SkillMacthUtils;
import com.fbee.modules.wechat.message.config.WechatConfig;
import com.fbee.modules.wechat.message.model.CreatePayOrderModel;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author fry
 * @Description：预约订单service层实现类
 * @date 2017年2月6日 上午10:34:54 预约订单列表 -匹配度，阿姨列表查询
 */
@Service
public class ReserveOrdersServiceImpl extends BaseService implements ReserveOrdersService {

    private static final Logger mqlog = LoggerFactory.getLogger("messageLogger");

    @Autowired
    ReserveOrdersMapper reserveOrdersMapper;

    @Autowired
    ReserveOrdersCustomerInfoMapper reserveOrdersCustomerInfoMapper;

    @Autowired
    TenantsCustomersBaseMapper tenantsCustomersBaseMapper;

    @Autowired
    CustomerService customerService;
    @Autowired
    StaffsService staffsService;

    ReserveOrdersOpt reserveOrdersOpt = new ReserveOrdersOpt();

    @Autowired
    OrdersMapper ordersMapper;

    @Autowired
    OrderCustomersInfoMapper orderCustomersInfoMapper;

    @Autowired
    TenantsStaffsInfoMapper tenantsStaffsInfoMapper;

    @Autowired
    TenantsStaffJobInfoMapper tenantsStaffJobInfoMapper;

    @Autowired
    TenantsStaffSerItemsMapper tenantsStaffSerItemsMapper;


    @Autowired
    TenantsStaffCertsInfoMapper tenantsStaffCertsInfoMapper;
    @Autowired
    CommonService commonService;

    @Autowired
    TenantsFinanceRecordsMapper tenantsFinanceRecordsDao;

    private JedisTemplate mq  = JedisUtils.getJedisMessage();

    /**
     * 查询预约订单列表
     */
    @Override
    public JsonResult getReserveOrdersList(Integer tenantId, UserBean userBean, ReserveOrdersForm reserveOrdersForm, Integer pageNumber,
                                           Integer pageSize) {
        try {

            // 查询全部时，orderStatus 为04

            if ("04".equals(reserveOrdersForm.getOrderStatus())) {
                reserveOrdersForm.setOrderStatus(null);
            }

            // 获取总条数
            Map<Object, Object> map = Maps.newHashMap();
            map.put("tenantId", tenantId);
            map.put("orderStatus", reserveOrdersForm.getOrderStatus());
            map.put("orderNo", reserveOrdersForm.getOrderNo());
            map.put("memberMobile", reserveOrdersForm.getMemberMobile());
            map.put("memberName", reserveOrdersForm.getMemberName());
            map.put("serviceItemCode", reserveOrdersForm.getServiceItemCode());
            map.put("orderTimeLeft", reserveOrdersForm.getOrderTimeLeft());
            map.put("orderTimeRight", reserveOrdersForm.getOrderTimeRight());
            //控制子账户权限 Baron 20170525
            if (!"01".equals(userBean.getUserType()) && !"02".equals(userBean.getUserType()) && !"01".equals(reserveOrdersForm.getOrderStatus())) {
                map.put("addAccount", userBean.getLoginAccount());
                map.put("userId", userBean.getUserId());

                System.out.println(userBean.getLoginAccount() + "=========rBean.getLoginAccount=========");
            }
            Integer totalCount = reserveOrdersMapper.getReserveOrdersCount(map);
            // 分页实体
            Page<Map<String, Object>> page = new Page<Map<String, Object>>();
            page.setPage(pageNumber);
            page.setRowNum(pageSize);
            if (totalCount == null) {
                return JsonResult.success(page);
            }
            // 最大页数判断
            int pageM = maxPage(totalCount, page.getRowNum(), page.getPage());
            if (pageM > 0) {
                page.setPage(pageM);
            }
            if (totalCount > 0) {
                map.put("offset", page.getOffset());
                map.put("pageSize", page.getRowNum());
                List<Map<String, Object>> list = reserveOrdersMapper.getReserveOrdersList(map);
                for (Map<String, Object> map1 : list) {
                    if (map1.containsValue(map1.get("staffId"))) {
                        // 阿姨信息查询
                        TenantsStaffsInfoEntity entity = tenantsStaffsInfoMapper
                                .selectByPrimaryKey((Integer) map1.get("staffId"));
                        // 查询阿姨求职信息
                        TenantsStaffJobInfo tenantsStaffJobInfo = getStaffJobInfoById((Integer) map1.get("staffId"));
                        TenantsStaffSerItemsEntity staffServiceItems = null;
                        if (map1.get("staffSerItemId") != null) {
                            //xiehui根据id查询工种
                            staffServiceItems = tenantsStaffSerItemsMapper
                                    .getStaffServiceItemsByIds((Integer) map1.get("staffSerItemId"));
                        }
                        // 匹配度处理
                        if (map1.get("staffId") != null && map1.get("staffId") != "") {
                            MatchingModel match = getMatchingModel(entity, tenantsStaffJobInfo, staffServiceItems);
                            OrderMatchBean order = getOrderMatchBean((String) map1.get("serviceItemCode"),
                                    (String) map1.get("orderNo"));
                            double matchRate = SkillMacthUtils.getInstance()
                                    .getMatchRate((String) map1.get("serviceItemCode"), match, order);
                            //xiehui
                            BigDecimal b = new BigDecimal(matchRate);
                            BigDecimal b3 = new BigDecimal(100);
                            BigDecimal intType = b.multiply(b3).setScale(2, RoundingMode.HALF_EVEN);
                            int matching = intType.intValue();
                            map1.put("matchingDegree", matching + "");
                        } else {
                            map1.put("matchingDegree", 0);
                        }

                        if (!StringUtils.isBlank((String) map1.get("zodiac"))) {
                            map1.put("zodiac", DictionarysCacheUtils.getZodiacName((String) map1.get("zodiac")));
                        }
                        if (!StringUtils.isBlank((String) map1.get("nativePlace"))) {
                            map1.put("nativePlace",
                                    DictionarysCacheUtils.getNativePlaceName((String) map1.get("nativePlace")));
                        }
                        if (!StringUtils.isBlank((String) map1.get("headImage"))) {
                            String imageUrl = (String) map1.get("headImage");
                            if (com.fbee.modules.core.utils.StringUtils.isNotBlank(imageUrl)
                                    && !imageUrl.startsWith("http")
                                    && !imageUrl.startsWith("data")) {
                                map1.put("headImage", Constants.IMAGE_URL + map1.get("headImage"));
                            } else {
                                map1.put("headImage", map1.get("headImage"));
                            }

                        }

                    }

                    // code转中文
                    if (!StringUtils.isBlank((String) map1.get("serviceItemCode"))) {
                        map1.put("serviceItemCode",
                                DictionarysCacheUtils.getServiceTypeName((String) map1.get("serviceItemCode")));
                    }
                    if (!StringUtils.isBlank((String) map1.get("serviceProvice"))) {
                        map1.put("serviceProvice",
                                DictionarysCacheUtils.getProviceName((String) map1.get("serviceProvice")));
                    }
                    if (!StringUtils.isBlank((String) map1.get("serviceCity"))) {
                        map1.put("serviceCity", DictionarysCacheUtils.getCityName((String) map1.get("serviceCity")));
                    }
                    if (!StringUtils.isBlank((String) map1.get("serviceCounty"))) {
                        map1.put("serviceCounty",
                                DictionarysCacheUtils.getCountyName((String) map1.get("serviceCounty")));
                    }
                    if ((Date) map1.get("orderTime") != null) {
                        map1.put("orderTime", DateUtils.formatDateTime((Date) map1.get("orderTime")));
                    }
                    if(!StringUtils.isBlank((String) map1.get("serviceType"))){
                    	map1.put("serviceType", DictionarysCacheUtils.getServiceType((String) map1.get("serviceType")));
                    }
                }
                page.setRows(list);
                page.setRecords(totalCount.longValue());
            }
            return JsonResult.success(page);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ErrorMsg.STAFF_QUERY_ERR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }

    }

    /**
     * 查询预约订单列表
     */
    @Override
    public JsonResult getReserveOrdersList(Integer tenantId, UserBean userBean) {
        try {
            // 获取总条数
            Map<Object, Object> map = Maps.newHashMap();
            map.put("tenantId", tenantId);
            map.put("orderStatus", "01");
            //控制子账户权限 Baron 20170525
            if (!"01".equals(userBean.getUserType()) && !"02".equals(userBean.getUserType())) {
                map.put("addAccount", userBean.getLoginAccount());
                map.put("userId", userBean.getUserId());
            }
            Integer totalCount = reserveOrdersMapper.getReserveOrdersCount(map);
            return JsonResult.success(Collections.singletonMap("count", totalCount));
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ErrorMsg.STAFF_QUERY_ERR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
    }

    /**
     * 查询预约信息
     */
    @Override
    public JsonResult selectReserveByOrderNo(String orderNo) {
        try {
            Map<String, Object> map = reserveOrdersMapper.selectReserveByOrderNo(orderNo);
            if (map.get("ORDER_TIME") != null) {
                map.put("ORDER_TIME", DateUtils.formatDateTime((Date) map.get("ORDER_TIME")));
            }
            if (map.get("HANDLE_ORDER_TIME") != null) {
                map.put("HANDLE_ORDER_TIME", DateUtils.formatDateTime((Date) map.get("HANDLE_ORDER_TIME")));
            }
            return JsonResult.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ErrorMsg.STAFF_QUERY_ERR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
    }

    /**
     * 查询客户信息
     */
    @Override
    public JsonResult selectMemberByOrderNo(String orderNo) {
        try {
            Map<String, Object> map = reserveOrdersCustomerInfoMapper.selectMemberByOrderNo(orderNo);
            String str = "";
            str = (String) map.get("serviceProvice");
            if (!StringUtils.isBlank(str)) {
                map.put("serviceProvice", str + Constants.COMMA + DictionarysCacheUtils.getProviceName(str));
            }
            str = (String) map.get("serviceCity");
            if (!StringUtils.isBlank(str)) {
                map.put("serviceCity", str + Constants.COMMA + DictionarysCacheUtils.getCityName(str));
            }
            str = (String) map.get("serviceCounty");
            if (!StringUtils.isBlank(str)) {
                map.put("serviceCounty", str + Constants.COMMA + DictionarysCacheUtils.getCountyName(str));
            }
            str = (String) map.get("houseType");
            if (!StringUtils.isBlank(str)) {
                map.put("houseType", str + Constants.COMMA + DictionarysCacheUtils.getHouseTypeName(str));
            }
            str = (String) map.get("childrenAgeRange");
            if (!StringUtils.isBlank(str)) {
                map.put("childrenAgeRange", str + Constants.COMMA + DictionarysCacheUtils.getChildrenAge(str));
            }
            str = (String) map.get("memberSex");
            if (!StringUtils.isBlank(str)) {
                map.put("memberSex", str + Constants.COMMA + DictionarysCacheUtils.getMemberSex(str));
            }
            str = (String) map.get("olderAgeRange");
            if (!StringUtils.isBlank(str)) {
                map.put("olderAgeRange", str + Constants.COMMA + DictionarysCacheUtils.getOldAge(str));
            }
            return JsonResult.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ErrorMsg.STAFF_QUERY_ERR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
    }

    /**
     * 查询服务信息
     */
    @Override
    public JsonResult selectServiceByOrderNo(String orderNo) {
        try {

            Map<String, Object> map = reserveOrdersCustomerInfoMapper.selectServiceByOrderNo(orderNo);

            String str = "";
            str = (String) map.get("serviceType");
            if (!StringUtils.isBlank(str)) {
                map.put("serviceType", str + Constants.COMMA
                        + DictionarysCacheUtils.getServiceNatureStr((String) map.get("serviceItemCode"), str));
            }
            if ((Date) map.get("serviceStart") != null) {
                map.put("serviceStart", DateUtils.formatDateTime((Date) map.get("serviceStart")));
            }
            if ((Date) map.get("serviceEnd") != null) {
                map.put("serviceEnd", DateUtils.formatDateTime((Date) map.get("serviceEnd")));
            }
            if (!StringUtils.isBlank((String) map.get("salarySkills"))) {
                map.put("salarySkills", DictionarysCacheUtils.getSkillsList((String) map.get("serviceItemCode"),
                        (String) map.get("salarySkills")));
            }
            if (!StringUtils.isBlank((String) map.get("languageRequirements"))) {
                map.put("languageRequirements",
                        DictionarysCacheUtils.getFeaturesList("01", (String) map.get("languageRequirements")));
            }
            if (!StringUtils.isBlank((String) map.get("cookingRequirements"))) {
                map.put("cookingRequirements",
                        DictionarysCacheUtils.getFeaturesList("02", (String) map.get("cookingRequirements")));
            }
            if (!StringUtils.isBlank((String) map.get("personalityRequirements"))) {
                map.put("personalityRequirements",
                        DictionarysCacheUtils.getFeaturesList("03", (String) map.get("personalityRequirements")));
            }
            str = (String) map.get("serviceItemCode");
            if (!StringUtils.isBlank(str)) {
                map.put("serviceItemCode", str + Constants.COMMA + DictionarysCacheUtils.getServiceTypeName(str));
            }
            str = (String) map.get("experienceRequirements");
            if (!StringUtils.isBlank(str)) {
                map.put("experienceRequirements", str + Constants.COMMA + DictionarysCacheUtils.getExperienceName(str));
            }
            str = (String) map.get("wageRequirements");
            if (!StringUtils.isBlank(str)) {
                map.put("wageRequirements", str + Constants.COMMA + DictionarysCacheUtils.getAgeIntervalName(str));
            }
            String st = (String)map.get("salaryType");
            if (st != null) {
                map.put("salaryTypeValue", DictionarysCacheUtils.getServicePriceUnit(st));
            }
            return JsonResult.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ErrorMsg.STAFF_QUERY_ERR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
    }

    /**
     * 查询阿姨信息
     */
    @Override
    public JsonResult selectStaffByOrderNo(String orderNo) {
        try {
            ReserveOrders reserveOrders = reserveOrdersMapper.selectByPrimaryKey(orderNo);

            if (reserveOrders.getStaffId() != null) {

                Map<String, Object> map = reserveOrdersMapper.selectStaffByOrderNo(orderNo);

                if (!StringUtils.isBlank((String) map.get("zodiac"))) {
                    map.put("zodiac", DictionarysCacheUtils.getZodiacName((String) map.get("zodiac")));
                }
                String str = (String) map.get("headImage");
                if (!StringUtils.isBlank(str) && !str.startsWith("http") && !str.startsWith("data")) {
                    map.put("headImage", Constants.IMAGE_URL + map.get("headImage"));
                }
                if (!StringUtils.isBlank((String) map.get("nativePlace"))) {
                    map.put("nativePlace", DictionarysCacheUtils.getNativePlaceName((String) map.get("nativePlace")));
                }
                if (!StringUtils.isBlank((String) map.get("constellation"))) {
                    map.put("constellation",
                            DictionarysCacheUtils.getConstellationName((String) map.get("constellation")));
                }
                if (!StringUtils.isBlank((String) map.get("experience"))) {
                    map.put("experienceValue", DictionarysCacheUtils.getExperienceName((String) map.get("experience")));
                }
                if (!StringUtils.isBlank((String) map.get("educarion"))) {
                    map.put("educarion", DictionarysCacheUtils.getEducationName((String) map.get("educarion")));
                }
                if (!StringUtils.isBlank((String) map.get("workStatus"))) {
                    map.put("workStatus", DictionarysCacheUtils.getWorkStatusName((String) map.get("workStatus")));
                }
                if (!StringUtils.isBlank((String) map.get("unit"))) {
                    map.put("unitValue", DictionarysCacheUtils.getServicePriceUnit((String) map.get("unit")));
                }
                map.put("matchValue", getMatchValue(orderNo, (Integer) map.get("tenantId")));

                return JsonResult.success(map);
            } else {
                return JsonResult.success(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ErrorMsg.STAFF_QUERY_ERR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
    }

    /**
     * 编辑保存客户信息到客户管理信息中
     */
    @Override
    public JsonResult saveMemberByOrderNo(String orderNo, ReserveOrderDetailsForm reserveOrderDetailsForm,
                                          String loginAccount) {
        try {

            Map<String, Object> map = new HashMap<String, Object>();
            // 查询订单中客户信息
            map = reserveOrdersCustomerInfoMapper.selectMemberByOrderNo(orderNo);
            Integer tenantId = (Integer) map.get("tenantId");
            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("tenantId", tenantId);
            map1.put("customerMobile", map.get("memberMobile"));

            // 根据手机号查询客户表中该客户信息
            Integer customerId = tenantsCustomersBaseMapper.getCustomerByCustomerMobile(map1);

            CustomerSaveForm customerSaveForm = new CustomerSaveForm();

            customerSaveForm.setChildrenAgeRange(reserveOrderDetailsForm.getChildrenAgeRange());
            customerSaveForm.setChildrenCount(reserveOrderDetailsForm.getChildrenCount());
            customerSaveForm.setCustomerMobile(reserveOrderDetailsForm.getMemberMobile());
            customerSaveForm.setCustomerName(reserveOrderDetailsForm.getMemberName());
            customerSaveForm.setSex(reserveOrderDetailsForm.getSex());
            customerSaveForm.setFamilyCount(reserveOrderDetailsForm.getFamilyCount());
            customerSaveForm.setHouseArea(reserveOrderDetailsForm.getHouseArea());
            customerSaveForm.setHouseType(reserveOrderDetailsForm.getHouseType());
            customerSaveForm.setOlderAgeRange(reserveOrderDetailsForm.getOlderAgeRange());
            customerSaveForm.setOlderCount(reserveOrderDetailsForm.getOlderCount());
            customerSaveForm.setSelfCares(reserveOrderDetailsForm.getSelfCares());
            customerSaveForm.setServiceProvice(reserveOrderDetailsForm.getServiceProvice());
            customerSaveForm.setServiceCity(reserveOrderDetailsForm.getServiceCity());
            customerSaveForm.setServiceCounty(reserveOrderDetailsForm.getServiceCounty());
            customerSaveForm.setContactAddress(reserveOrderDetailsForm.getServiceAddress());

            // 判断客户表中有没有订单中的客户信息(手机号为判断依据)
            if (customerId != null) {
                if (null != reserveOrderDetailsForm.getSelfCares() && "".equals(reserveOrderDetailsForm.getSelfCares())) {
                    reserveOrderDetailsForm.setSelfCares(null);
                }
                if (null != reserveOrderDetailsForm.getHouseType() && "".equals(reserveOrderDetailsForm.getHouseType())) {
                    reserveOrderDetailsForm.setHouseType(null);
                }
                if (null != reserveOrderDetailsForm.getChildrenAgeRange() && "".equals(reserveOrderDetailsForm.getChildrenAgeRange())) {
                    reserveOrderDetailsForm.setChildrenAgeRange(null);
                }
                if (null != reserveOrderDetailsForm.getSex() && "".equals(reserveOrderDetailsForm.getSex())) {
                    reserveOrderDetailsForm.setSex(null);
                }
                if (null != reserveOrderDetailsForm.getServiceAddress() && "".equals(reserveOrderDetailsForm.getServiceAddress())) {
                    reserveOrderDetailsForm.setServiceAddress(null);
                }
                if (null != reserveOrderDetailsForm.getOlderAgeRange() && "".equals(reserveOrderDetailsForm.getOlderAgeRange())) {
                    reserveOrderDetailsForm.setOlderAgeRange(null);
                }
                if (null != reserveOrderDetailsForm.getChildrenCount() && "".equals(reserveOrderDetailsForm.getChildrenCount())) {
                    reserveOrderDetailsForm.setChildrenCount(null);
                }
                return customerService.updateCustomer(customerId + "", customerSaveForm, loginAccount);
            } else {
                return customerService.saveCustomer(tenantId, customerSaveForm, loginAccount);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ErrorMsg.STAFF_QUERY_ERR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
    }

    /**
     * 编辑保存客户信息到预约订单客户信息中
     */
    @Override
    public JsonResult saveMemberToOrder(ReserveOrderDetailsForm reserveOrderDetailsForm) {
        try {

            ReserveOrdersEntity reserveOrdersEntity = reserveOrdersOpt
                    .buildReserveOrdersEntity(reserveOrderDetailsForm);

            reserveOrdersMapper.updateReserveByOrderNo(reserveOrdersEntity);

            ReserveOrdersCustomerInfoEntity reserveOrdersCustomerInfoEntity = reserveOrdersOpt
                    .buildReserveOrdersCustomerInfoEntity(reserveOrderDetailsForm);

            reserveOrdersCustomerInfoMapper.updateResverCustomerInfo(reserveOrdersCustomerInfoEntity);

            return JsonResult.success(ResultCode.SUCCESS);

        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ErrorMsg.STAFF_QUERY_ERR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
    }

    /**
     * 编辑保存服务信息到预约订单信息中
     */
    @Override
    public JsonResult saveServiceToOrder(ReserveOrderDetailsForm reserveOrderDetailsForm) {
        try {
            ReserveOrdersEntity reserveOrdersEntity = reserveOrdersOpt.buildReserveOrdersEntity(reserveOrderDetailsForm);

            ReserveOrdersCustomerInfoEntity reserveOrdersCustomerInfoEntity = reserveOrdersOpt.buildReserveOrdersCustomerInfoEntity(reserveOrderDetailsForm);
            if (reserveOrderDetailsForm.getExpectedBirth() != null && !"".equals(reserveOrderDetailsForm.getExpectedBirth().trim())) {
                reserveOrdersCustomerInfoEntity.setIsBabyBorn("0");
            } else {
                reserveOrdersCustomerInfoEntity.setIsBabyBorn("1");
            }
            ReserveOrders reserveOrder = reserveOrdersMapper.selectByPrimaryKey(reserveOrderDetailsForm.getOrderNo());
            List<TenantsStaffSerItemsEntity> staffSerItems = tenantsStaffSerItemsMapper.getServiceItemsByStaffId(reserveOrder.getStaffId());
            List<String> list = new ArrayList<String>();
            for (TenantsStaffSerItemsEntity staffSerItem : staffSerItems) {
                list.add(staffSerItem.getServiceItemCode());
            }

            if (null == reserveOrdersCustomerInfoEntity.getSalarySkills()) {
                reserveOrdersCustomerInfoEntity.setSalarySkills("");
            }
            if (null == reserveOrdersCustomerInfoEntity.getLanguageRequirements()) {
                reserveOrdersCustomerInfoEntity.setLanguageRequirements("");
            }
            if (null == reserveOrdersCustomerInfoEntity.getCookingReqirements()) {
                reserveOrdersCustomerInfoEntity.setCookingReqirements("");
            }
            if (null == reserveOrdersCustomerInfoEntity.getPersonalityRequirements()) {
                reserveOrdersCustomerInfoEntity.setPersonalityRequirements("");
            }
            //阿姨不存在此工种
            if (!list.contains(reserveOrderDetailsForm.getServiceItemCode())) {
                reserveOrdersEntity.setStaffId(null);
                reserveOrdersEntity.setStaffSerItemId(null);
            } else {
                reserveOrdersEntity.setStaffId(reserveOrder.getStaffId());
            }

            reserveOrdersMapper.updateReserveByOrderNo1(reserveOrdersEntity);
            reserveOrdersCustomerInfoMapper.updateReserveByOrderNo(reserveOrdersCustomerInfoEntity);

            return JsonResult.success(ResultCode.SUCCESS);

        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ErrorMsg.STAFF_QUERY_ERR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
    }

    /**
     * 更新预约订单表（取消／放弃订单）
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult updateReserveByOrderNo(ReserveOrderDetailsForm reserveOrderDetailsForm) {
        try {
            String account = null;
            //权限控制 Baron 20170525
            if (null != reserveOrderDetailsForm.getAddAccount() && !"".equals(reserveOrderDetailsForm.getAddAccount())) {
                account = reserveOrderDetailsForm.getAddAccount();
            }

            ReserveOrdersEntity reserveOrdersEntity = new ReserveOrdersEntity();

            reserveOrdersEntity.setOrderNo(reserveOrderDetailsForm.getOrderNo());
            //已取消
            reserveOrdersEntity.setOrderStatus("03");
            reserveOrdersEntity.setHandleOrderTime(new Date());
            //控制权限 Baron20170525
            if (null != reserveOrderDetailsForm.getAddAccount() && !"".equals(reserveOrderDetailsForm.getAddAccount())) {
                reserveOrdersEntity.setAddAccount(reserveOrderDetailsForm.getAddAccount());
            }
            if (null != reserveOrderDetailsForm.getUserId() && !"".equals(reserveOrderDetailsForm.getUserId())) {
                reserveOrdersEntity.setUserId(reserveOrderDetailsForm.getUserId());
            }
            if (null != reserveOrderDetailsForm.getUserName() && !"".equals(reserveOrderDetailsForm.getUserName())) {
                reserveOrdersEntity.setUserName(reserveOrderDetailsForm.getUserName());
            }
            if (null != reserveOrderDetailsForm.getUserType() && !"".equals(reserveOrderDetailsForm.getUserType())) {
                reserveOrdersEntity.setUserType(reserveOrderDetailsForm.getUserType());
            }
            //订单状态修改为取消
            reserveOrdersMapper.updateReserveByOrderNo(reserveOrdersEntity);

            //生成支付订单(orders)，状态已取消.
            // 向订单表插入订单
            Orders ordersEntity = new Orders();
            ReserveOrders roOrders = reserveOrdersMapper.selectByPrimaryKey(reserveOrderDetailsForm.getOrderNo());
            ordersEntity.setTenantId(roOrders.getTenantId());
            ordersEntity.setStaffId(roOrders.getStaffId());
            ordersEntity.setMemberId(roOrders.getMemberId());
            ordersEntity.setOrderSource("02");//02网络订单
            ordersEntity.setOrderNo(roOrders.getOrderNo());
            ordersEntity.setOrderStatus("05"); //已取消
            ordersEntity.setServiceItemCode(roOrders.getServiceItemCode());
            ordersEntity.setOrderTime(roOrders.getOrderTime());
            ordersEntity.setIsLock("1");
            ordersEntity.setServiceCharge(new BigDecimal(0));
            ordersEntity.setOrderBalance(new BigDecimal(0));
            ordersEntity.setIdepositOver("01"); //定金未支付
            ordersEntity.setIsInterview("01");//未面试
            ordersEntity.setBalanceOver("01");//尾款未支付
            ordersEntity.setModifyTime(new Date());

            //权限控制 Baron 20170525
            if (null != account && !"".equals(account)) {
                ordersEntity.setAddAccount(account);
            }

            ordersEntity.setUserId(reserveOrderDetailsForm.getUserId());
            ordersEntity.setShareId(reserveOrderDetailsForm.getUserId());
            //插入操作员和类型
            ordersEntity.setUserName(reserveOrderDetailsForm.getUserName());
            ordersEntity.setUserType(reserveOrderDetailsForm.getUserType());
            if (roOrders.getStaffId() != null) {
                if (roOrders.getStaffSerItemId() != null) {
                    TenantsStaffSerItemsEntity stafffSerItemId = tenantsStaffSerItemsMapper.getStaffServiceItemsByIds(roOrders.getStaffSerItemId());
                    ordersEntity.setStafffSerItemId(stafffSerItemId.getId());
                } else {
                    //插入关联阿姨服务工种id
                    TenantsStaffSerItemsEntity tenantsStaffSerItems = new TenantsStaffSerItemsEntity();
                    tenantsStaffSerItems.setStaffId(roOrders.getStaffId());
                    tenantsStaffSerItems.setTenantId(roOrders.getTenantId());
                    tenantsStaffSerItems.setServiceItemCode(roOrders.getServiceItemCode());
                    TenantsStaffSerItemsEntity stafffSerItemId = tenantsStaffSerItemsMapper.getStaffServiceItemsByKey(tenantsStaffSerItems);
                    ordersEntity.setStafffSerItemId(stafffSerItemId.getId());//关联阿姨服务工种id
                }

            }
            ordersMapper.insertSelective(ordersEntity);

            ReserveOrdersCustomerInfo recust = reserveOrdersCustomerInfoMapper.selectByPrimaryKey(reserveOrderDetailsForm.getOrderNo());
            OrderCustomersInfo orderCustomersInfo = reserveOrdersOpt.buildOrderCustomersInfoEntity(roOrders, recust);
            orderCustomersInfo.setOrderNo(reserveOrderDetailsForm.getOrderNo());
            if (orderCustomersInfo.getExpectedBirth() != null) {
                orderCustomersInfo.setIsBabyBorn("0");
            } else {
                orderCustomersInfo.setIsBabyBorn("1");
            }
            orderCustomersInfoMapper.insertSelective(orderCustomersInfo);


            return JsonResult.success(ResultCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ErrorMsg.STAFF_QUERY_ERR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }

    }

    /**
     * 保存预约订单为订单,生成订单
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult saveReserveByOrderNo(ReserveOrderDetailsForm reserveOrderDetailsForm) {
        try {
            String account = null;
            //权限控制 Baron 20170525
            if (null != reserveOrderDetailsForm.getAddAccount() && !"".equals(reserveOrderDetailsForm.getAddAccount())) {
                account = reserveOrderDetailsForm.getAddAccount();
            }
            
            if(!"01".equals(reserveOrderDetailsForm.getOrderStatus())){
            	return JsonResult.failure(ErrorCode.ORDER_NOT_EXIST);
            }
            // 如果状态为待处理，直接点击生成订单，那么要先更新预约订单表
            ReserveOrdersEntity reserveOrdersEntity = reserveOrdersOpt.buildReserveOrdersEntity(reserveOrderDetailsForm);

            reserveOrdersEntity.setOrderStatus("02");
            reserveOrdersEntity.setHandleOrderTime(new Date());
            //权限控制 Baron 20170525
            if (null != account && !"".equals(account)) {
                reserveOrdersEntity.setAddAccount(account);
            }
            if (null != reserveOrderDetailsForm.getUserId() && !"".equals(reserveOrderDetailsForm.getUserId())) {
                reserveOrdersEntity.setUserId(reserveOrderDetailsForm.getUserId());
            }
            if (null != reserveOrderDetailsForm.getUserName() && !"".equals(reserveOrderDetailsForm.getUserName())) {
                reserveOrdersEntity.setUserName(reserveOrderDetailsForm.getUserName());
            }
            if (null != reserveOrderDetailsForm.getUserType() && !"".equals(reserveOrderDetailsForm.getUserType())) {
                reserveOrdersEntity.setUserType(reserveOrderDetailsForm.getUserType());
            }

            reserveOrdersMapper.updateReserveByOrderNo(reserveOrdersEntity);

            ReserveOrdersCustomerInfoEntity reserveOrdersCustomerInfoEntity = reserveOrdersOpt.buildReserveOrdersCustomerInfoEntity(reserveOrderDetailsForm);

            reserveOrdersCustomerInfoMapper.updateReserveByOrderNo(reserveOrdersCustomerInfoEntity);
            Map<String,Object> ro = reserveOrdersMapper.selectReserveByOrderNo(reserveOrderDetailsForm.getOrderNo());
            if(ro != null && ro.get("ORDER_TIME")!=null){
                String dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date)ro.get("ORDER_TIME"));
                reserveOrderDetailsForm.setOrderTime(dt);
            }

            // 向订单表插入订单
            Orders orders = reserveOrdersOpt.buildOrdersEntity(reserveOrderDetailsForm);
            logger.info("reserveOrderDetailsForm: "+ JsonUtils.toJson(reserveOrderDetailsForm));
            logger.info("orders: "+ JsonUtils.toJson(orders));

            String orderNo = reserveOrderDetailsForm.getOrderNo();
            orders.setOrderNo(orderNo);

            //权限控制 Baron 20170525
            if (null != account && !"".equals(account)) {
                orders.setAddAccount(account);
            }

            orders.setUserId(reserveOrderDetailsForm.getUserId());
            orders.setShareId(reserveOrderDetailsForm.getUserId());
            //插入操作员和类型
            orders.setUserName(reserveOrderDetailsForm.getUserName());
            orders.setUserType(reserveOrderDetailsForm.getUserType());
            //@zsq
            if (reserveOrderDetailsForm.getStaffId() != null) {
                ReserveOrders roOrders = reserveOrdersMapper.selectByPrimaryKey(reserveOrderDetailsForm.getOrderNo());
                if (roOrders.getStaffSerItemId() != null) {
                    TenantsStaffSerItemsEntity stafffSerItemId = tenantsStaffSerItemsMapper.getStaffServiceItemsByIds(roOrders.getStaffSerItemId());
                    orders.setStafffSerItemId(stafffSerItemId.getId());
                    System.out.println("=========生成订单时候插入服务工种id===============" + orders.getStafffSerItemId());
                } else {
                    //插入关联阿姨服务工种id
                    TenantsStaffSerItemsEntity tenantsStaffSerItems = new TenantsStaffSerItemsEntity();
                    tenantsStaffSerItems.setStaffId(reserveOrderDetailsForm.getStaffId());
                    tenantsStaffSerItems.setTenantId(reserveOrderDetailsForm.getTenantId());
                    tenantsStaffSerItems.setServiceItemCode(reserveOrderDetailsForm.getServiceItemCode());
                    TenantsStaffSerItemsEntity stafffSerItemId = tenantsStaffSerItemsMapper.getStaffServiceItemsByKey(tenantsStaffSerItems);
                    orders.setStafffSerItemId(stafffSerItemId.getId());//关联阿姨服务工种id
                    System.out.println("=========生成订单时候插入服务工种id===============" + orders.getStafffSerItemId());
                }

            }

            orders.setModifyTime(new Date());
            ordersMapper.insertSelective(orders);

            OrderCustomersInfo orderCustomersInfo = reserveOrdersOpt.buildOrderCustomersInfoEntity(reserveOrderDetailsForm);
            logger.info("orderCustomersInfo: "+ JsonUtils.toJson(orderCustomersInfo));
            orderCustomersInfo.setOrderNo(orderNo);
            if (orderCustomersInfo.getExpectedBirth() != null) {
                orderCustomersInfo.setIsBabyBorn("0");
            } else {
                orderCustomersInfo.setIsBabyBorn("1");
            }
            orderCustomersInfoMapper.insertSelective(orderCustomersInfo);

            //当直接生成已完成订单，插入财务流水表
            if ("04".equals(orders.getOrderStatus())) {
                OrderCustomersInfo members = orderCustomersInfoMapper.selectByPrimaryKey(orderNo);
                //财务流水尾款 = 服务价格
                TenantsFinanceRecords tenantsFinanceRecordsRe = new TenantsFinanceRecords();
                String inOutNoRe = commonService.createOrderNo("05");
                tenantsFinanceRecordsRe.setInOutNo(inOutNoRe);//交易流水号
                tenantsFinanceRecordsRe.setTenantId(orders.getTenantId());
                tenantsFinanceRecordsRe.setPayType("01");//交易类型
                tenantsFinanceRecordsRe.setInOutType("01");//收支类型：01收入
                tenantsFinanceRecordsRe.setInOutObject(members.getMemberName());//收支对象
                tenantsFinanceRecordsRe.setInOutAmount(orders.getAmount());//服务价格
                tenantsFinanceRecordsRe.setTransType("01");//交易类型01线上
                tenantsFinanceRecordsRe.setRemarks("尾款");
                tenantsFinanceRecordsRe.setAddTime(new Date());
                tenantsFinanceRecordsRe.setStatus("03");
                tenantsFinanceRecordsRe.setIsUsable("1");
                tenantsFinanceRecordsRe.setDraweeId(orders.getMemberId());//付款方id
                tenantsFinanceRecordsRe.setDraweeType("01");//付款方类型01客户02家政机构03业务员04家政员05平台
                tenantsFinanceRecordsRe.setPayeeId(orders.getTenantId());//收款方id
                tenantsFinanceRecordsRe.setPayeeType("02");//收款方类型01客户02家政机构03业务员04家政员05平台
                tenantsFinanceRecordsRe.setRelatedTrans(orderNo);//关联号：订单编号

                //权限控制 Baron 20170525
                if (null != account && !"".equals(account)) {
                    tenantsFinanceRecordsRe.setAddAccount(account);
                }

                tenantsFinanceRecordsDao.insertSelective(tenantsFinanceRecordsRe);

                //财务流水     服务费
                TenantsFinanceRecords tenantsFinanceRecordsRs = new TenantsFinanceRecords();
                String inOutNoRs = commonService.createOrderNo("05");
                tenantsFinanceRecordsRs.setInOutNo(inOutNoRs);//服务费财务流水号
                tenantsFinanceRecordsRs.setTenantId(orders.getTenantId());
                tenantsFinanceRecordsRs.setPayType("11");//交易类型 01 订单支付 02成单加价 03成单奖励 04账户充值 05账户提现 06会员续费 07报名费 08住宿费 09佣金费10取消订单退定金11:服务费
                tenantsFinanceRecordsRs.setInOutType("01");
                tenantsFinanceRecordsRs.setInOutObject(members.getMemberName());//收支对象
                tenantsFinanceRecordsRs.setInOutAmount(orders.getServiceCharge());//服务费
                tenantsFinanceRecordsRs.setTransType("01");
                tenantsFinanceRecordsRs.setRemarks("服务费");
                tenantsFinanceRecordsRs.setAddTime(new Date());
                tenantsFinanceRecordsRs.setStatus("03");
                tenantsFinanceRecordsRs.setIsUsable("1");
                tenantsFinanceRecordsRs.setDraweeId(orders.getMemberId());//付款方id
                tenantsFinanceRecordsRs.setDraweeType("01");//付款方类型01客户02家政机构03业务员04家政员05平台
                tenantsFinanceRecordsRs.setPayeeId(orders.getTenantId());//收款方id
                tenantsFinanceRecordsRs.setPayeeType("02");//收款方类型01客户02家政机构03业务员04家政员05平台
                tenantsFinanceRecordsRs.setRelatedTrans(orderNo);//关联号：订单编号

                //权限控制 Baron 20170525
                if (null != account && !"".equals(account)) {
                    tenantsFinanceRecordsRs.setAddAccount(account);
                }
                tenantsFinanceRecordsDao.insertSelective(tenantsFinanceRecordsRs);
            }

            //生成订单
            CreatePayOrderModel model = new CreatePayOrderModel();
            model.setTitle("您有一笔订单待支付定金，请及时处理");
            model.setTenantId(WebUtils.getCurrentUser().getTenantId()+"");
            model.setOrderNo(orderNo);
            model.setDepositAmount(orders.getOrderDeposit().setScale(2).toString()+"元");
            model.setMemberId(orders.getMemberId());
            model.setOrderType(DictionarysCacheUtils.getServiceTypeName(orders.getServiceItemCode())+"-"+DictionarysCacheUtils.getServiceNatureStr(orders.getServiceItemCode(), orderCustomersInfo.getServiceType()));
            model.setStatus("待支付定金");
            model.setRemark("请在24小时内完成支付，逾期将自动取消，点击查看详情。");

            String msg = JsonUtils.toJson(model);
            mq.lpush(WechatConfig.Queue.NEW_PAY_ORDER_C.getQueue(), msg);
            mq.publish(WechatConfig.Channel.NEW_PAY_ORDER.getChannel(), msg);

            return JsonResult.success(ResultCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ErrorMsg.STAFF_QUERY_ERR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
    }

    /**
     * 获取阿姨信息列表
     */
    @SuppressWarnings("unused")
    @Override
    public JsonResult getStaffsInfo(StaffListForm form, int pageNumber, int pageSize) {

        try {

            Integer tenantId = form.getTenantId();
            String orderNo = form.getOrderNo();

            // 获取该笔订单对应的服务工种
            Map<String, Object> service = reserveOrdersCustomerInfoMapper.selectServiceByOrderNo(orderNo);
            String serviceItemCode = (String) service.get("serviceItemCode");// 服务工种

            // 查询该笔订单阿姨Id
            Map<String, Object> staff = reserveOrdersCustomerInfoMapper.selectMemberByOrderNo(orderNo);

            // 获取总条数
            Map<Object, Object> map = StaffsOpt.buildQueryMap(tenantId);
            map.put("staffId", (Integer) staff.get("staffId"));
            map.put("serviceItemCode", serviceItemCode);
            map.put("tenantId", form.getTenantId());
            map.put("staffName", StringUtils.strLike(form.getStaffName()));
            map.put("workStatus", form.getWorkStatus());
            map.put("experience", form.getExperience());
            map.put("education", form.getEducation());
            map.put("serviceArea", form.getServiceArea());

            if (StringUtils.isNotBlank(form.getAge())) {
                String ageValue = DictionarysCacheUtils.getAgeIntervalName(form.getAge());
                String[] ages = ageValue.split(",");
                if (ages.length == 2) {
                    map.put("ageMin", ages[0]);
                    map.put("ageMax", ages[1]);
                } else {
                    map.put("ageMin", ages[0]);
                    map.put("ageMax", null);
                }
            }
            map.put("zodiac", form.getZodiac());
            map.put("nativePlace", form.getNativePlace());
            map.put("serviceProvice", form.getServiceProvice());
            map.put("serviceCity", form.getServiceCity());
            map.put("serviceCounty", form.getServiceCounty());
            if(form.getStaffNo() != null){
            	map.put("staffNo", "%"+form.getStaffNo()+"%");
            }
            Integer totalCount = tenantsStaffsInfoMapper.getOrderStaffsCount(map);

            // 分页实体
            Page<StaffQueryJson> page = new Page<StaffQueryJson>();
            page.setPage(pageNumber);
            page.setRowNum(pageSize);
            if (totalCount == null) {
                return JsonResult.success(page);
            }
            // 最大页数判断
            int pageM = maxPage(totalCount, page.getRowNum(), page.getPage());
            if (pageM > 0) {
                page.setPage(pageM);
            }
            if (totalCount > 0) {
                map.put("offset", page.getOffset());
                map.put("pageSize", page.getRowNum());
                List<Map<Object, Object>> list = tenantsStaffsInfoMapper.selectOrderStaffInfoList(map);
                List<StaffQueryJson> resultList = Lists.newArrayList();
                StaffQueryJson staffQueryBean = null;
                for (Map<Object, Object> result : list) {

                    staffQueryBean = new StaffQueryJson();
                    TenantsStaffsInfoEntity entity = tenantsStaffsInfoMapper
                            .selectByPrimaryKey((Integer) result.get("staffId"));

                    staffQueryBean.setStaffId(entity.getStaffId()); // 主键

                    if (!StringUtils.isBlank(entity.getHeadImage())) {
                        String imageUrl = entity.getHeadImage();
                        if (com.fbee.modules.core.utils.StringUtils.isNotBlank(imageUrl)
                                && !imageUrl.startsWith("http")
                                && !imageUrl.startsWith("data")) {
                            staffQueryBean.setHeadImage(Constants.IMAGE_URL + entity.getHeadImage());
                        } else {
                            staffQueryBean.setHeadImage(entity.getHeadImage());
                        }
                    }

                    staffQueryBean.setStaffName(entity.getStaffName()); // 姓名
                    staffQueryBean.setAge(entity.getAge()); // 年龄
                    staffQueryBean.setZodiac(DictionarysCacheUtils.getZodiacName(entity.getZodiac())); // 属相
                    staffQueryBean.setNativePlace(DictionarysCacheUtils.getNativePlaceName(entity.getNativePlace()));// 籍贯
                    staffQueryBean.setEducation(DictionarysCacheUtils.getEducationName(entity.getEducarion()));// 学历
                    staffQueryBean.setSpecialty(entity.getSpecialty());// 专业
//					staffQueryBean.setWorkStatus(Status.getDesc(entity.getWorkStatus()));// 服务状态

                    staffQueryBean.setWorkStatus(staffsService.staffIsWorkNow(entity.getStaffId()));//服务状态
                    staffQueryBean.setStaffNo(entity.getStaffNo());

                    // 查询阿姨求职信息
                    TenantsStaffJobInfo tenantsStaffJobInfo = getStaffJobInfoById(entity.getStaffId());
                    staffQueryBean.setServiceProvice(
                            DictionarysCacheUtils.getProviceName(tenantsStaffJobInfo.getServiceProvice()));
                    staffQueryBean
                            .setServiceCity(DictionarysCacheUtils.getCityName(tenantsStaffJobInfo.getServiceCity()));
                    staffQueryBean.setServiceCounty(
                            DictionarysCacheUtils.getCountyName(tenantsStaffJobInfo.getServiceCounty()));
                    staffQueryBean.setServiceType(DictionarysCacheUtils.getServiceTypeName(serviceItemCode));// 服务工种(只有一个)
                    staffQueryBean.setServicePrice(tenantsStaffJobInfo.getPrice());
                    staffQueryBean.setUnit(tenantsStaffJobInfo.getUnit());
                    staffQueryBean.setUnitValue(DictionarysCacheUtils.getServicePriceUnit(tenantsStaffJobInfo.getUnit()));
                    // 查询阿姨服务工种对应的从业经验
                    TenantsStaffSerItemsKey staffSerItemsKey = new TenantsStaffSerItemsKey();// 连合主键
                    staffSerItemsKey.setStaffId(entity.getStaffId());
                    staffSerItemsKey.setTenantId(tenantId);
                    staffSerItemsKey.setServiceItemCode(serviceItemCode);
                    TenantsStaffSerItemsEntity staffServiceItems = tenantsStaffSerItemsMapper
                            .getStaffServiceItemsByKey(staffSerItemsKey);
                    String experience = tenantsStaffJobInfo.getWorkExperience();// 从业经验
                    staffQueryBean.setWorkExperience(DictionarysCacheUtils.getExperienceName(experience));

                    String shareStatus = entity.getShareStatus();
                    if (null != shareStatus && shareStatus.equals("01")) {
                        staffQueryBean.setShareStatus(shareStatus); // 分享状态:01分享中,02未分享（不显示）
                    } else {
                        staffQueryBean.setShareStatus(null);
                    }

                    //@zsq 服务区域变为:不限和指定区域
                    if (tenantsStaffJobInfo.getServiceArea() != null && !tenantsStaffJobInfo.getServiceArea().equals("")) {
                        staffQueryBean.setServiceArea(DictionarysCacheUtils.getProviceName(tenantsStaffJobInfo.getServiceArea()));
                    } else {
                        staffQueryBean.setServiceArea("");
                    }

                    //匹配度处理
                    MatchingModel match = getMatchingModel(entity, tenantsStaffJobInfo, staffServiceItems);
                    OrderMatchBean order = getOrderMatchBean(serviceItemCode, orderNo);
                    double matchRate = SkillMacthUtils.getInstance().getMatchRate(serviceItemCode, match, order);
                    BigDecimal b = new BigDecimal(matchRate);
                    BigDecimal b3 = new BigDecimal(100);
                    int intValue = b.multiply(b3).intValue();
                    staffQueryBean.setMatchingDegree(intValue + "");
                    resultList.add(staffQueryBean);
                }
                Collections.sort(resultList);
                Iterator<StaffQueryJson> it = resultList.iterator();
                if (StringUtils.isNotBlank(form.getWorkStatus())) {
                    if ("01".equals(form.getWorkStatus())) {
                        while (it.hasNext()) {
                            if (!"服务中".equals(it.next().getWorkStatus())) {
                                it.remove();
                            }
                        }
                    } else {
                        while (it.hasNext()) {
                            if (!"待聘".equals(it.next().getWorkStatus())) {
                                it.remove();
                            }
                        }
                    }
                    page.setRows(resultList);
                    page.setRecords(resultList.size());
                } else {
                    page.setRows(resultList);
                    page.setRecords(totalCount.longValue());
                }
            }
            return JsonResult.success(page);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ErrorMsg.STAFF_QUERY_ERR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
    }

    //xiehui
    private MatchingModel getMatchingModel(TenantsStaffsInfoEntity entity, TenantsStaffJobInfo tenantsStaffJobInfo,
                                           TenantsStaffSerItemsEntity staffServiceItems) {
        MatchingModel match = new MatchingModel();
        Set<String> certs = Sets.newHashSet();
        // List<Integer> list = new ArrayList<Integer>();
        // 查询阿姨证书@xiehui
        List<TenantsStaffCertsInfoEntity> tenantsStaffCertsInfos = tenantsStaffCertsInfoMapper.getSatffAllCerts(entity.getStaffId());
        if (tenantsStaffCertsInfos != null && tenantsStaffCertsInfos.size() > 0) {
            Set<String> cert1 = Sets.newHashSet();
            Set<String> cert2 = Sets.newHashSet();
            Set<String> cert3 = Sets.newHashSet();
            // Set<String> hashSet1 = new HashSet<String>();
            // Set<String> hashSet3 = new HashSet<String>();
            for (TenantsStaffCertsInfoEntity tenantsStaffCertsInfo : tenantsStaffCertsInfos) {
                String certType = tenantsStaffCertsInfo.getCertType();
                String authenticateGrade = tenantsStaffCertsInfo.getAuthenticateGrade();
                String certStatus = tenantsStaffCertsInfo.getCertifiedStatus();
                if (staffServiceItems != null && staffServiceItems.getServiceItemCode() != null) {
                    // 判断证书是否通过02
                    if ("02".equals(certStatus)) {
                        // 判断证书名称
                        if (certType != null) {
                            // 判断鉴定等级是否为空（如果是健康证和驾驶证就是空的）
                            if (authenticateGrade != null) {
                                // 拼接证书名称和鉴定等级
                                String code = certType + authenticateGrade;
                                // 月嫂
                                if ("01".equals(staffServiceItems.getServiceItemCode())) {
                                    // 01母婴护理证
                                    if ("01".equals(certType)) {
                                        cert1.add(code);
                                        // 催乳师证
                                    } else if ("03".equals(certType)) {
                                        cert2.add(code);
                                    } else {
                                        cert3.add(null);
                                    }
                                }

                                // 育婴师
                                if ("02".equals(staffServiceItems.getServiceItemCode())) {
                                    // 育婴师证
                                    if ("02".equals(certType)) {
                                        System.out.println("**************是否走到这里**************");
                                        cert1.add(code);
                                    } else {
                                        cert3.add(null);
                                    }
                                }

                                // 保姆
                                if ("03".equals(staffServiceItems.getServiceItemCode())) {
                                    // 家政服务证
                                    if ("05".equals(certType)) {
                                        cert1.add(code);
                                    } else if ("02".equals(certType)) {// 育婴师证
                                        cert2.add(code);
                                    } else {
                                        cert3.add(null);
                                    }
                                }

                                // 养老陪护
                                if ("04".equals(staffServiceItems.getServiceItemCode())) {
                                    if ("04".equals(certType)) {// 养老护理证
                                        cert1.add(code);
                                    } else if ("05".equals(certType)) {// 家政服务证
                                        cert2.add(code);
                                    } else {
                                        cert3.add(null);
                                    }
                                }

                                // 钟点工
                                if ("05".equals(staffServiceItems.getServiceItemCode())) {
                                    if ("05".equals(certType)) {// 家政服务证
                                        cert1.add(code);
                                    } else if ("02".equals(certType)) {// 育婴师证
                                        cert2.add(code);
                                    } else {
                                        cert3.add(null);
                                    }
                                }

                                // 家庭管家
                                if ("06".equals(staffServiceItems.getServiceItemCode())) {
                                    cert3.add(null);
                                }

                            }
                        }
                    }

                } else {
                    certs.add(null);
                    match.setCerts(certs);
                }
            }
            System.out.println(cert1);
            Set<String> certMax1 = new HashSet<String>();
            if (cert1 != null && cert1.size() > 0) {
                String number1 = Collections.max(cert1);
                certMax1.add(number1);
            } else {
                certMax1.add(null);
            }
            System.out.println(cert2);
            Set<String> certMax2 = new HashSet<String>();
            if (cert2 != null && cert2.size() > 0) {
                String number2 = Collections.max(cert2);
                certMax2.add(number2);
            } else {
                certMax2.add(null);
            }
            certMax1.addAll(certMax2);
            cert3.addAll(certMax1);
            match.setCerts(cert3);
            System.out.println(cert3);
        }
        match.setIsHasTrade("1"); // 是否有工种
        if (staffServiceItems != null && staffServiceItems.getServiceNature() != null) {
            match.setSeviceNature(staffServiceItems.getServiceNature());// 服务类型
        } else {
            match.setSeviceNature(null);// 服务类型
        }
        match.setPrice((null == staffServiceItems || tenantsStaffJobInfo.getPrice() == null) ? ""
                : (tenantsStaffJobInfo.getPrice() + ""));
        match.setExperience((null == staffServiceItems || tenantsStaffJobInfo.getWorkExperience() == null) ? ""
                : (tenantsStaffJobInfo.getWorkExperience()));
        match.setNativePlace(entity.getNativePlace() == null ? "" : (entity.getNativePlace()));
        match.setZodiac(entity.getZodiac() == null ? "" : (entity.getZodiac()));
        match.setAge(entity.getAge() == null ? "" : (entity.getAge() + ""));
        match.setSex(entity.getSex() == null ? "" : (entity.getSex()));
        match.setEducation(entity.getEducarion() == null ? "" : (entity.getEducarion()));
        match.setLanguage(
                tenantsStaffJobInfo.getLanguageFeature() == null ? "" : (tenantsStaffJobInfo.getLanguageFeature()));
        match.setCharacter(
                tenantsStaffJobInfo.getCharacerFeature() == null ? "" : (tenantsStaffJobInfo.getCharacerFeature()));
        match.setCooking(
                tenantsStaffJobInfo.getCookingFeature() == null ? "" : (tenantsStaffJobInfo.getCookingFeature()));
//		match.setIsHasOlder(
//				tenantsStaffJobInfo.getElderlySupport() == null ? "" : (tenantsStaffJobInfo.getElderlySupport()));
//		match.setIsHasPet(tenantsStaffJobInfo.getPetFeeding() == null ? "" : (tenantsStaffJobInfo.getPetFeeding()));
        if (staffServiceItems != null) {
            match.setIsHasOlder(
                    tenantsStaffJobInfo.getElderlySupport() == null ? "" : (tenantsStaffJobInfo.getElderlySupport()));
            match.setIsHasPet(tenantsStaffJobInfo.getPetFeeding() == null ? "" : (tenantsStaffJobInfo.getPetFeeding()));
        }
        // match.setServiceContents(null);// 服务内容
        // xiehui
        if (null != staffServiceItems && null != staffServiceItems.getSkills()
                && !"".equals(staffServiceItems.getSkills())) {
            String[] skin = staffServiceItems.getSkills().split(",");
            if (skin != null && skin.length > 0 && !"".equals(skin[0])) {
                Map<Integer, String> temp = new HashMap<>();
                for (int i = 0; i < skin.length; i++) {
                    temp.put(i, skin[i]);
                }
                match.setServiceContents(temp);// 服务内容
            } else {
                match.setServiceContents(null);
            }
        } else {
            match.setServiceContents(null);
        }
        return match;
    }

    private OrderMatchBean getOrderMatchBean(String serviceItemCode, String orderNo) {
        Map<String, Object> map = reserveOrdersCustomerInfoMapper.selectServiceByOrderNo(orderNo);
        System.out.println("20170623*********************selectServiceByOrderNo" + map);
        if (map != null) {
            OrderMatchBean order = new OrderMatchBean();
            order.setServiceType(serviceItemCode);
            order.setServiceNature(null);
            // xiehui
            order.setPrice(String.valueOf(map.get("salaryMax")));
            order.setExperience((String) map.get("experienceRequirements"));
            order.setNativePlace(null);
            order.setZodiac(null);
            order.setAge((String) map.get("wageRequirements"));
            order.setSex(null);
            order.setEducation(null);
            order.setLanguage((String) map.get("languageRequirements"));
            order.setCharacter((String) map.get("personalityRequirements"));
            order.setCooking((String) map.get("cookingRequirements"));
            System.out.println("********" + order.getCooking());
            order.setIsHasPet((String) map.get("petRaising"));
            order.setIsHasOlder(null);

            if (null != map && null != map.get("salarySkills") && !"".equals(map.get("salarySkills"))) {
                String skills = (String) map.get("salarySkills");
                String[] stem = skills.split(",");
                //服务内容
                if (stem != null && stem.length > 0 && !"".equals(stem[0])) {
                    Map<Integer, String> mapa = new HashMap<>();
                    for (int i = 0; i < stem.length; i++) {
                        mapa.put(i, stem[i]);
                    }
                    order.setServiceContents(mapa);
                } else {
                    order.setServiceContents(null);
                }
            } else {
                order.setServiceContents(null);
            }
            return order;
        }
        return null;
    }


    private TenantsStaffJobInfo getStaffJobInfoById(Integer staffId) {
        return tenantsStaffJobInfoMapper.selectByPrimaryKey(staffId);
    }

    /**
     * 校验阿姨状态
     *
     * @return
     */
    private boolean verifyStaffStatus(Integer staffId) {
        TenantsStaffsInfo tenantsStaffsInfo = tenantsStaffsInfoMapper.selectByPrimaryKey(staffId);
        String status = tenantsStaffsInfo.getShareStatus();
        if (status.equals("01")) {// 01分享中,02未分享（不显示）
            // throw new ServiceException("当前阿姨不可重复分享");
            return false;
        }
        return true;

    }

    /**
     * 更换阿姨
     */
    @Override
    public JsonResult updateStaffByOrderNo(String orderNo, Integer staffId) {
        try {
            ReserveOrders record = new ReserveOrders();
            record.setOrderNo(orderNo);
            record.setStaffId(staffId);
            //@zsq 预约更换阿姨 工种id
            ReserveOrders reserveOrders = reserveOrdersMapper.selectByPrimaryKey(orderNo);
            TenantsStaffSerItemsEntity tenantsStaffSerItems = new TenantsStaffSerItemsEntity();
            tenantsStaffSerItems.setStaffId(staffId);
            tenantsStaffSerItems.setTenantId(reserveOrders.getTenantId());
            tenantsStaffSerItems.setServiceItemCode(reserveOrders.getServiceItemCode());
            TenantsStaffSerItemsEntity tenantsStaffSerItemsId = tenantsStaffSerItemsMapper.getStaffServiceItemsByKey(tenantsStaffSerItems);

            record.setStaffSerItemId(tenantsStaffSerItemsId.getId());

            reserveOrdersMapper.updateByPrimaryKeySelective(record);
            return JsonResult.success(ResultCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ErrorMsg.STAFF_QUERY_ERR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }

    }

    /**
     * 匹配度
     */
    public int getMatchValue(String orderNo, int tenantId) {
        // 获取该笔订单对应的服务工种
        ReserveOrders orders = reserveOrdersMapper.selectByPrimaryKey(orderNo);
        TenantsStaffsInfoEntity entity = tenantsStaffsInfoMapper.selectByPrimaryKey(orders.getStaffId());
        TenantsStaffJobInfo tenantsStaffJobInfo = getStaffJobInfoById(entity.getStaffId());
        TenantsStaffSerItemsKey staffSerItemsKey = new TenantsStaffSerItemsKey();// 连合主键
        staffSerItemsKey.setStaffId(entity.getStaffId());
        staffSerItemsKey.setTenantId(tenantId);
        staffSerItemsKey.setServiceItemCode(orders.getServiceItemCode());
        TenantsStaffSerItemsEntity staffServiceItems = tenantsStaffSerItemsMapper
                .getStaffServiceItemsByKey(staffSerItemsKey);

        // 匹配度处理
        MatchingModel match = getMatchingModel(entity, tenantsStaffJobInfo, staffServiceItems);
        OrderMatchBean order = getOrderMatchBean(orders.getServiceItemCode(), orderNo);
        double matchRate = SkillMacthUtils.getInstance().getMatchRate(orders.getServiceItemCode(), match, order);
        BigDecimal b = new BigDecimal(matchRate);
        BigDecimal b3 = new BigDecimal(100);
        BigDecimal intType = b.multiply(b3).setScale(2, RoundingMode.HALF_EVEN);
        int matching = intType.intValue();
        System.out.println(matching);
        return matching;
    }

    @Override
    public JsonResult modifyRemark(ReserveOrderDetailsForm reserveOrderDetailsForm){
        ReserveOrdersEntity ent = new ReserveOrdersEntity();
        ent.setOrderNo(reserveOrderDetailsForm.getOrderNo());
        ent.setRemark(StringUtils.isBlank(reserveOrderDetailsForm.getRemark())?"":reserveOrderDetailsForm.getRemark());
        reserveOrdersMapper.updateReserveByOrderNo(ent);
        return JsonResult.success("success");
    }
}
