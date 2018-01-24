package com.fbee.modules.service.impl;

import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.bean.*;
import com.fbee.modules.core.Log;
import com.fbee.modules.core.page.Page;
import com.fbee.modules.core.utils.DateUtils;
import com.fbee.modules.form.StaffListForm;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.jsonData.extend.StaffQueryJson;
import com.fbee.modules.mybatis.dao.*;
import com.fbee.modules.mybatis.entity.*;
import com.fbee.modules.mybatis.model.*;
import com.fbee.modules.operation.StaffsOpt;
import com.fbee.modules.service.CommonService;
import com.fbee.modules.service.DealAccountService;
import com.fbee.modules.service.OrderShareService;
import com.fbee.modules.service.StaffsService;
import com.fbee.modules.service.basic.BaseService;
import com.fbee.modules.utils.DictionarysCacheUtils;
import com.fbee.modules.utils.SkillMacthUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class OrderShareServiceImpl extends BaseService implements OrderShareService {

    @Autowired
    OrderShareInfoMapper orderShareInfoMapper;
    @Autowired
    OrdersMapper ordersMapper;

    @Autowired
    StaffsService staffsService;

    @Autowired
    OrderCustomersInfoMapper orderCustomersInfoMapper;
    @Autowired
    TenantsFundsMapper tenantsFundsMapper;
    @Autowired
    OrderShareStaffInfoMapper orderShareStaffInfoMapper;
    @Autowired
    TenantsStaffsInfoMapper tenantsStaffsInfoMapper;
    @Autowired
    TenantsStaffJobInfoMapper tenantsStaffJobInfoMapper;
    @Autowired
    DeliveryBoxInfoMapper deliveryBoxInfoMapper;
    @Autowired
    TenantsStaffSerItemsMapper tenantsStaffSerItemsMapper;
    @Autowired
    TenantsStaffCertsInfoMapper tenantsStaffCertsInfoMapper;
    @Autowired
    TenantsCustomersBaseMapper tenantsCustomersBaseMapper;
    @Autowired
    CommonService commonService;
    @Autowired
    DealAccountService dealAccountService;
    @Autowired
    TenantsAppsMapper tenantsAppsMapper;
    @Autowired
    TenantsUsersMapper tenantsUsersMapper;

    @SuppressWarnings("rawtypes")
    @Override
    public JsonResult getOrderShareInfoList(int pageNumber, int pageSize) {

        try {
            // 获取总条数
            Map<String, Object> map = new HashMap<String, Object>();

            Integer totalCount = orderShareInfoMapper.getOrderShareInfoCount();
            // 分页实体
            Page<Map> page = new Page<Map>();
            page.setPage(pageNumber);
            page.setRowNum(pageSize);
            // 最大页数判断
            int pageM = maxPage(totalCount, page.getRowNum(), page.getPage());
            if (pageM > 0) {
                page.setPage(pageM);
            }
            if (totalCount > 0) {
                map.put("offset", page.getOffset());
                map.put("pageSize", page.getRowNum());
                List<Map> resultList = orderShareInfoMapper.getOrderShareInfoList(map);
                page.setRows(resultList);
                page.setRecords(totalCount.longValue());
            }
            return JsonResult.success(page);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ErrorMsg.ORDER_SHARE_ERR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }

    }

    @Override
    public JsonResult getOrderDetailInfo(UserBean userBean, String orderNo) {
        try {

            //订单信息
            boolean flag = false;
            Map orderInfo = orderShareInfoMapper.getOrderInfo(orderNo);
            Integer sourceTenantId = (Integer) orderInfo.get("sourceTenantId");
            Log.info("当前登陆用户ID:" + userBean.getTenantId() + "订单来源租户ID:" + sourceTenantId);
            //相等订单分享方
            if (sourceTenantId.toString().equals(userBean.getTenantId().toString())) {
                flag = true;
            }
            orderInfo.put("flag", flag);
            //服务信息
            Orders Orders = ordersMapper.selectByPrimaryKey(orderNo);
            String serviceItemCode = Orders.getServiceItemCode();//服务工种
            String serviceTypeName = DictionarysCacheUtils.getServiceTypeName(serviceItemCode);

            HashMap<String, Object> serviceInfo = Maps.newHashMap();
            OrderCustomersInfo orderCustomersInfo = orderCustomersInfoMapper.selectByPrimaryKey(orderNo);
            // 处理服务信息
            Date serviceStart = orderCustomersInfo.getServiceStart();
            Date serviceEnd = orderCustomersInfo.getServiceEnd();
            String serviceStartDate = DateUtils.dateToStr(serviceStart, "yyyy-MM-dd");//服务开始时间
            String serviceEndDate = DateUtils.dateToStr(serviceEnd, "yyyy-MM-dd");//服务结束时间

            String serviceTypeCode = orderCustomersInfo.getServiceType();
            String serviceNature = DictionarysCacheUtils.getServiceNatureStr(serviceItemCode, serviceTypeCode);


            String serviceProvice = DictionarysCacheUtils.getProviceName(orderCustomersInfo.getServiceProvice());
            String serviceCity = DictionarysCacheUtils.getCityName(orderCustomersInfo.getServiceCity());
            String serviceCounty = DictionarysCacheUtils.getCountyName(orderCustomersInfo.getServiceCounty());

            String wageRequirementCode = orderCustomersInfo.getWageRequirements();//年龄要求code
            String wageRequirementName = "";
            if (StringUtils.isNotBlank(wageRequirementCode)) {
                if (wageRequirementCode.equals("01")) { //0,29
                    wageRequirementName = "29岁以下";
                }
                if (wageRequirementCode.equals("02")) { //0,29
                    wageRequirementName = "29-39岁";
                }
                if (wageRequirementCode.equals("03")) { //0,29
                    wageRequirementName = "39-49岁";
                }
                if (wageRequirementCode.equals("04")) {// 50,
                    wageRequirementName = "50岁以上";
                }
            } else {
                wageRequirementName = "不限";
            }
            //String salaryRequirementCode = orderCustomersInfo.getSalaryRequirements();//工资要求code
            String salaryRequirement = "";
            BigDecimal salary = orderCustomersInfo.getSalary();
            String salaryType = orderCustomersInfo.getSalaryType();
            StringBuffer sb = new StringBuffer();
            if (salaryType == null) {
                salaryRequirement = salary.doubleValue() + DictionarysCacheUtils.getServicePriceUnit(salaryType);

            }

            String languageRequirementCode = orderCustomersInfo.getLanguageRequirements();//语言要求code
            String personalityRequirementCode = orderCustomersInfo.getPersonalityRequirements();//性格要求code
            String cookingReqirementsCode = orderCustomersInfo.getCookingReqirements();//烹饪要求code
            String specialNeedCode = orderCustomersInfo.getSpecialNeeds();//特殊要求code
            String petRaising = orderCustomersInfo.getPetRaising();////饲养宠物code
            String salarySkills = orderCustomersInfo.getSalarySkills();//服务内容code

            serviceInfo.put("serviceType", serviceTypeName);  //服务工种
            serviceInfo.put("serviceNature", serviceNature);  //服务类型
            serviceInfo.put("serviceProvice", serviceProvice);//服务区域省
            serviceInfo.put("serviceCity", serviceCity);      //服务区域市
            serviceInfo.put("serviceCounty", serviceCounty);  //服务区域区
            serviceInfo.put("serviceStartDate", serviceStartDate);//服务开始时间
            serviceInfo.put("serviceEndDate", serviceEndDate);//服务结束时间
            serviceInfo.put("wageRequirement", wageRequirementName);//年龄要求
            serviceInfo.put("salaryRequirement", StringUtils.isBlank(salaryRequirement) ? "" : salaryRequirement);//工资要求

            serviceInfo.put("remark", StringUtils.isNotBlank(orderCustomersInfo.getRemarks()) ? orderCustomersInfo.getRemarks() : "");//备注
            serviceInfo.put("petRaising", petRaising);//饲养宠物
            serviceInfo.put("salarySkills", DictionarysCacheUtils.getSkillsList(serviceItemCode, salarySkills));//服务内容
            serviceInfo.put("languageRequirement", DictionarysCacheUtils.getFeaturesStr("01", languageRequirementCode));//语言要求
            serviceInfo.put("personalityRequirement", DictionarysCacheUtils.getFeaturesStr("02", personalityRequirementCode));//性格要求
            serviceInfo.put("cookingReqirement", DictionarysCacheUtils.getFeaturesStr("03", cookingReqirementsCode));//烹饪要求
            serviceInfo.put("specialNeed", StringUtils.isNotBlank(specialNeedCode) ? specialNeedCode : "");//特殊要求

            //客户信息
            HashMap<Object, Object> customerInfo = Maps.newHashMap();
            OrderCustomersInfo orderCustomersInfo1 = orderCustomersInfoMapper.selectByPrimaryKey(orderNo);
            String houseTypeCode = orderCustomersInfo1.getHouseType();//住宅类型code
            BigDecimal houseArea = orderCustomersInfo1.getHouseArea();//住宅面积
            Integer familyCount = orderCustomersInfo1.getFamilyCount();//家庭人数
            Integer childrenCount = orderCustomersInfo1.getChildrenCount();//儿童数
            Integer olderCount = orderCustomersInfo1.getOlderCount();//老人数
            String childrenAgeRange = orderCustomersInfo1.getChildrenAgeRange();//儿童年龄段
            String olderAgeRange = orderCustomersInfo1.getOlderAgeRange();//老人年龄段
            String selfCares = orderCustomersInfo1.getSelfCares();//老人能都自理

            customerInfo.put("houseType", StringUtils.isBlank(houseTypeCode) ? "" : DictionarysCacheUtils.getHouseTypeName(houseTypeCode));//住宅类型
            customerInfo.put("houseArea", houseArea != null ? houseArea : "");//住宅面积
            customerInfo.put("familyCount", familyCount != null ? familyCount : "");//家庭人数
            customerInfo.put("childrenCount", childrenCount != null ? childrenCount : "");//儿童数
            customerInfo.put("olderCount", olderCount != null ? olderCount : "");//老人数
            customerInfo.put("childrenAgeRange", StringUtils.isBlank(childrenAgeRange) ? "" : DictionarysCacheUtils.getChildrenAge(childrenAgeRange));//儿童年龄段
            customerInfo.put("olderAgeRange", StringUtils.isBlank(olderAgeRange) ? "" : DictionarysCacheUtils.getOldAge(olderAgeRange));//老人年龄段
            customerInfo.put("selfCares", StringUtils.isBlank(selfCares) ? "" : DictionarysCacheUtils.getSelfCares(selfCares));//老人能都自理

            //响应结果
            Map<String, Object> resultMap = Maps.newHashMap();
            resultMap.put("staffInfo", "");
            //非订单分享方查询阿姨信息
            if (!flag) {
                Map<String, Object> paramMap = Maps.newHashMap();
                paramMap.put("orderNo", orderNo);
                paramMap.put("tenantId", userBean.getTenantId());
                //当前登陆租户的id和订单号查询是否提供阿姨
                Map staffInfo = orderShareInfoMapper.getOrderShareStaffInfo(paramMap);
                if (staffInfo != null) {
                    String status = (String) staffInfo.get("status");
                    if (!status.equals("02")) {//01等待对方老师确认，02，已通过,待完成，03已拒绝,04已退回
                        //没有提供阿姨或提供的阿姨没有通过，"订单来源"的内容不可见（仅仅显示“阿姨通过后可查看订单来源信息”）。
                        orderInfo.put("orderSource", "阿姨通过后可查看订单来源信息");
                    }
                    resultMap.put("staffInfo", staffInfo);
                } else {
                    //没有提供阿姨或提供的阿姨没有通过，"订单来源"的内容不可见（仅仅显示“阿姨通过后可查看订单来源信息”）。
                    orderInfo.put("orderSource", "阿姨通过后可查看订单来源信息");
                }
            }


            resultMap.put("orderInfo", orderInfo);
            resultMap.put("serviceInfo", serviceInfo);
            resultMap.put("custInfo", customerInfo);

            return JsonResult.success(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ErrorMsg.GET_ORDER_DETAIL, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
    }

    @SuppressWarnings("unused")
    @Override
    public JsonResult getStaffsInfo(StaffListForm form, UserBean userBean, int pageNumber, int pageSize) {
        Integer tenantId = userBean.getTenantId();

        try {

            //校验分享阿姨数量
            boolean b1 = verifyShareStaffCount(form.getOrderNo());
            if (!b1) {
//                return JsonResult.failure(null);
            }
            //校验租户账户余额
            boolean b2 = verifyTenantAccountbalance(tenantId);
            if (!b2) {
//                return JsonResult.failure(null);
            }
            //获取该笔订单对应的服务工种
            Orders orders = ordersMapper.selectByPrimaryKey(form.getOrderNo());
            String serviceItemCode = orders.getServiceItemCode();//服务工种

            //获取总条数
            Map<Object, Object> map = StaffsOpt.buildQueryMap(userBean.getTenantId());
            map.put("serviceItemCode", serviceItemCode);
            map.put("tenantId", tenantId);
            map.put("staffName", form.getStaffName());
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
            Integer totalCount = tenantsStaffsInfoMapper.getOrderStaffsCount(map);

            //分页实体
            Page<StaffQueryJson> page = new Page<StaffQueryJson>();
            page.setPage(pageNumber);
            page.setRowNum(pageSize);
            if (totalCount == null) {
                return JsonResult.success(page);
            }
            //最大页数判断
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
                StringBuilder sb = new StringBuilder();
                for (Map<Object, Object> result : list) {

                    staffQueryBean = new StaffQueryJson();
                    TenantsStaffsInfoEntity entity = tenantsStaffsInfoMapper.selectByPrimaryKey((Integer) result.get("staffId"));
                    staffQueryBean.setStaffId(entity.getStaffId());//主键
                    staffQueryBean.setHeadImage(entity.getHeadImage()); //头像
                    staffQueryBean.setStaffName(entity.getStaffName());//姓名
                    staffQueryBean.setAge(entity.getAge());//年龄
                    staffQueryBean.setZodiac(DictionarysCacheUtils.getZodiacName(entity.getZodiac())); //属相
                    staffQueryBean.setNativePlace(DictionarysCacheUtils.getNativePlaceName(entity.getNativePlace()));//籍贯
                    staffQueryBean.setEducation(DictionarysCacheUtils.getEducationName(entity.getEducarion()));//学历
                    staffQueryBean.setSpecialty(entity.getSpecialty());//专业
//            		staffQueryBean.setWorkStatus(Status.getDesc(entity.getWorkStatus()));//服务状态

                    staffQueryBean.setWorkStatus(staffsService.staffIsWorkNow(entity.getStaffId()));//服务状态


                    // 查询阿姨求职信息
                    TenantsStaffJobInfo tenantsStaffJobInfo = getStaffJobInfoById((Integer) result.get("staffId"));
                    staffQueryBean.setServiceProvice(DictionarysCacheUtils.getProviceName(tenantsStaffJobInfo.getServiceProvice()));
                    staffQueryBean.setServiceCity(DictionarysCacheUtils.getCityName(tenantsStaffJobInfo.getServiceCity()));
                    staffQueryBean.setServiceCounty(DictionarysCacheUtils.getCountyName(tenantsStaffJobInfo.getServiceCounty()));
                    staffQueryBean.setServiceType(DictionarysCacheUtils.getServiceTypeName(serviceItemCode));//服务工种(只有一个)
                    //查询阿姨服务工种对应的从业经验
                    TenantsStaffSerItemsKey staffSerItemsKey = new TenantsStaffSerItemsKey();//连合主键
                    staffSerItemsKey.setStaffId((Integer) result.get("staffId"));
                    staffSerItemsKey.setTenantId(userBean.getTenantId());
                    staffSerItemsKey.setServiceItemCode(serviceItemCode);
                    TenantsStaffSerItemsEntity staffServiceItems = tenantsStaffSerItemsMapper.getStaffServiceItemsByKey(staffSerItemsKey);
                    String experience = tenantsStaffJobInfo.getWorkExperience();//从业经验
                    String serviceNature = staffServiceItems.getServiceNature();//服务类型
                    staffQueryBean.setWorkExperience(DictionarysCacheUtils.getExperienceName(experience));
                    staffQueryBean.setServiceNature(DictionarysCacheUtils.getServiceNatureStr(serviceItemCode, serviceNature));
                    //@zsq  服务区域修改 因服务区域变为不限和指定区域
                    if (tenantsStaffJobInfo.getServiceArea() != null && !tenantsStaffJobInfo.getServiceArea().equals("")) {
                        staffQueryBean.setServiceArea(DictionarysCacheUtils.getProviceName(tenantsStaffJobInfo.getServiceArea()));
                    } else {
                        staffQueryBean.setServiceArea("");
                    }
                    String shareStatus = entity.getShareStatus();
                    if (shareStatus == null) {
                        shareStatus = "";
                    }
                    if (shareStatus.equals("01")) {
                        staffQueryBean.setShareStatus("分享中"); //分享状态:01分享中,02未分享（不显示）
                    } else {
                        staffQueryBean.setShareStatus("");
                    }
                    //匹配度处理
                    MatchingModel match = getMatchingModel(entity, tenantsStaffJobInfo, staffServiceItems);
                    OrderMatchBean order = getOrderMatchBean(serviceItemCode, form.getOrderNo());
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

    private MatchingModel getMatchingModel(TenantsStaffsInfoEntity entity, TenantsStaffJobInfo tenantsStaffJobInfo, TenantsStaffSerItemsEntity staffServiceItems) {
        MatchingModel match = new MatchingModel();
        Set<String> certs = Sets.newHashSet();
        //查询阿姨证书
        List<TenantsStaffCertsInfoEntity> tenantsStaffCertsInfos = tenantsStaffCertsInfoMapper.getSatffAllCerts(entity.getStaffId());
        if (tenantsStaffCertsInfos != null && tenantsStaffCertsInfos.size() > 0) {
            for (TenantsStaffCertsInfoEntity tenantsStaffCertsInfo : tenantsStaffCertsInfos) {
                String certType = tenantsStaffCertsInfo.getCertType();
                certs.add(certType);
            }
        }
        match.setCerts(certs);
        match.setIsHasTrade("1");    //是否有工种
        match.setSeviceNature(StringUtils.isBlank(staffServiceItems.getServiceNature()) ? "" : staffServiceItems.getServiceNature());//服务类型
        match.setPrice(tenantsStaffJobInfo.getPrice() + "");
        match.setExperience(StringUtils.isBlank(tenantsStaffJobInfo.getWorkExperience()) ? "" : tenantsStaffJobInfo.getWorkExperience());
        match.setNativePlace(StringUtils.isBlank(entity.getNativePlace()) ? "" : entity.getNativePlace());
        match.setZodiac(StringUtils.isBlank(entity.getZodiac()) ? "" : entity.getZodiac());
        match.setAge(entity.getAge() + "");
        match.setSex(StringUtils.isBlank(entity.getSex()) ? "" : entity.getSex());
        match.setEducation(StringUtils.isBlank(entity.getEducarion()) ? "" : entity.getEducarion());
        match.setLanguage(StringUtils.isBlank(tenantsStaffJobInfo.getLanguageFeature()) ? "" : tenantsStaffJobInfo.getLanguageFeature());
        match.setCharacter(StringUtils.isBlank(tenantsStaffJobInfo.getCharacerFeature()) ? "" : tenantsStaffJobInfo.getCharacerFeature());
        match.setCooking(StringUtils.isBlank(tenantsStaffJobInfo.getCookingFeature()) ? "" : tenantsStaffJobInfo.getCookingFeature());
        match.setIsHasOlder(StringUtils.isBlank(tenantsStaffJobInfo.getElderlySupport()) ? "" : tenantsStaffJobInfo.getElderlySupport());
        match.setIsHasPet(StringUtils.isBlank(tenantsStaffJobInfo.getPetFeeding()) ? "" : tenantsStaffJobInfo.getPetFeeding());
        match.setServiceContents(null);//服务内容
        return match;
    }


    private OrderMatchBean getOrderMatchBean(String serviceItemCode, String orderNo) {
        // 部分字段没有封装上
        OrderCustomersInfo orderCustomersInfo = orderCustomersInfoMapper.selectByPrimaryKey(orderNo);
        if (orderCustomersInfo != null) {
            OrderMatchBean order = new OrderMatchBean();
            order.setServiceType(serviceItemCode);
            order.setServiceNature(null);
            order.setPrice(null);
            order.setExperience(null);
            order.setNativePlace(null);
            order.setZodiac(null);
            order.setAge(null);
            order.setSex(null);
            order.setEducation(null);
            order.setLanguage(StringUtils.isBlank(orderCustomersInfo.getLanguageRequirements()) ? "" : orderCustomersInfo.getLanguageRequirements());
            order.setCharacter(StringUtils.isBlank(orderCustomersInfo.getPersonalityRequirements()) ? "" : orderCustomersInfo.getPersonalityRequirements());
            order.setCooking(StringUtils.isBlank(orderCustomersInfo.getCookingReqirements()) ? "" : orderCustomersInfo.getCookingReqirements());
            order.setIsHasPet(StringUtils.isBlank(orderCustomersInfo.getPetRaising()) ? "" : orderCustomersInfo.getPetRaising());
            order.setIsHasOlder(null);
            return order;
        }
        return null;
    }


    private TenantsStaffJobInfo getStaffJobInfoById(Integer staffId) {
        return tenantsStaffJobInfoMapper.selectByPrimaryKey(staffId);
    }

    /**
     * 校验阿姨信息
     *
     * @return
     */
    private boolean verifyShareStaffCount(String orderNo) {
        // 校验当前处于"分享中"的阿姨数是否<=5个，否侧给出错误提示"分享中的阿姨最多为5个"
        Integer staffCount = orderShareStaffInfoMapper.getStaffCount(orderNo);
        if (staffCount >= 5) {
            return false;
        }
        return true;
    }

    /**
     * 校验当前租户账户余额
     *
     * @param tenantId
     * @return
     */
    private boolean verifyTenantAccountbalance(Integer tenantId) {
        // 校验当前账户可用余额是否>=200元，否则给出错误提示"可用余额不足，分享阿姨需冻结200元保证金"
        TenantsFunds tenantsFunds = tenantsFundsMapper.selectByPrimaryKey(tenantId);
        BigDecimal availableAmount = tenantsFunds.getAvailableAmount();
        BigDecimal b2 = new BigDecimal("200");
        int compareTo = availableAmount.compareTo(b2);
        if (compareTo == -1) {
            return false;
        }
        return true;
    }

    /**
     * 非订单分享方-->提供阿姨
     */
    @Override
    @Transactional
    public JsonResult submitStaffInfo(UserBean userBean, String orderNo, Integer staffId, String matchingDegree) {
        // 校验当前阿姨状态是否为"分享中"
        boolean b = verifyStaffStatus(staffId);
        if (!b) {
//            return JsonResult.failure(null);
        }
        // a.冻结200元可用余额
        //commonService.frozenAmount(userBean.getTenantId());// 非订单分享方-->提供阿姨-->冻结非分享方200保证金
        // 根据订单号查询分享池中订单来源
        OrderShareInfoExample example = new OrderShareInfoExample();
        example.createCriteria().andIsUsableEqualTo("1").andOrderNoEqualTo(orderNo);
        List<OrderShareInfo> orderShareInfos = orderShareInfoMapper.selectByExample(example);
        OrderShareInfo orderShareInfo = orderShareInfos.get(0);
        Integer tenantId = orderShareInfo.getTenantId();// 订单来源租户id
        Date date = new Date();
        OrderShareStaffInfo record = new OrderShareStaffInfo();
        record.setId(null);// P
        record.setTenantId(userBean.getTenantId());// F 提供阿姨方-->租户ID
        record.setOrderNo(orderNo);// F 订单号
        record.setStaffId(staffId);// F 阿姨ID
        record.setAddTime(date);// 添加时间
        record.setAddAccount(userBean.getLoginAccount());// 添加账户
        record.setStatus("01");// 01等待对方老师确认，02，已通过,待完成，03已拒绝
        orderShareStaffInfoMapper.insert(record);// 订单分享阿姨信息表
        // b.发送该阿姨信息至对方投递箱
        DeliveryBoxInfo deliveryBoxInfo = new DeliveryBoxInfo();
        deliveryBoxInfo.setId(null);// P
        deliveryBoxInfo.setOrderShareStaffId(record.getId());// F
        deliveryBoxInfo.setMatchingDegree(Integer.parseInt(matchingDegree));// 匹配度
        // -->页面传递
        deliveryBoxInfo.setOrderNo(orderNo);// 订单号
        deliveryBoxInfo.setSourceTenantId(tenantId);// 订单来源租户ID
        deliveryBoxInfo.setTenantId(userBean.getTenantId());// 提供阿姨租户的ID
        deliveryBoxInfo.setStaffId(staffId);// 阿姨id
        deliveryBoxInfo.setHandleResult("01");// 处理结果 01待处理、02已通过、03已拒绝、04已退回
        deliveryBoxInfo.setDeliveryDate(date);// 投递时间
        deliveryBoxInfo.setAddTime(date);// 添加时间
        deliveryBoxInfo.setAddAccount(userBean.getLoginAccount());// 添加账户
        deliveryBoxInfoMapper.insert(deliveryBoxInfo);
        // c.提示提交成功，将当前阿姨状态置为"分享中"，并开始24h倒计时
        //commonService.updateStaffStatus(staffId, "01");
        // 返回服务器时间，及分享阿姨时间
        HashMap<String, Object> returnMap = Maps.newHashMap();
        String shareTime = DateUtils.dateToStr(date, "yyyy-MM-dd HH:mm:ss");
        returnMap.put("shareTime", shareTime);
        returnMap.put("systemTime", shareTime);
        // d.关闭当前阿姨选择框并刷新分享订单详情
        Integer orderSorceTenantId = orderShareInfo.getTenantId();// 订单来源租户id
        // 通知

        //commonService.deliveryBoxNotice(orderSorceTenantId);

        return JsonResult.success(returnMap);
        // e.此时详情页的"选择阿姨"按钮颜色置灰且不可点击
    }

    /**
     * 校验阿姨状态
     *
     * @return
     */
    private boolean verifyStaffStatus(Integer staffId) {
        TenantsStaffsInfo tenantsStaffsInfo = tenantsStaffsInfoMapper.selectByPrimaryKey(staffId);
        String status = tenantsStaffsInfo.getShareStatus();
        if (status == null) {
            return true;
        }
        if (status.equals("01")) {//01分享中,02未分享（不显示）
            return false;
        }
        return true;
    }

    ////////////////////////////// 投递箱//////////////////////////

    /**
     * 投递箱列表
     *
     * @param userBean
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @SuppressWarnings("rawtypes")
    @Override
    public JsonResult getDeliveryBoxInfoList(UserBean userBean, int pageNumber, int pageSize) {
        try {
            // 获取总条数
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("tenantId", userBean.getTenantId());
            Integer totalCount = orderShareInfoMapper.getDeliveryBoxInfoCount(map);
            // 分页实体
            Page<Map> page = new Page<Map>();
            page.setPage(pageNumber);
            page.setRowNum(pageSize);
            // 最大页数判断
            int pageM = maxPage(totalCount, page.getRowNum(), page.getPage());
            if (pageM > 0) {
                page.setPage(pageM);
            }
            if (totalCount > 0) {
                map.put("offset", page.getOffset());
                map.put("pageSize", page.getRowNum());
                List<Map> resultList = orderShareInfoMapper.getDeliveryBoxInfoList(map);

                if (resultList != null && resultList.size() > 0) {
                    StringBuffer sb = null;
                    for (Map map2 : resultList) {
                        sb = new StringBuffer();
                        Integer age = (Integer) map2.get("age");
                        String zodiac = (String) map2.get("zodiac");
                        zodiac = DictionarysCacheUtils.getZodiacName(zodiac);
                        String nativePlace = (String) map2.get("nativePlace");
                        nativePlace = DictionarysCacheUtils.getNativePlaceName(nativePlace);
                        String educarion = (String) map2.get("educarion");
                        educarion = DictionarysCacheUtils.getEducationName(educarion);
                        String specialty = (String) map2.get("specialty");
                        String baseSituation = null;
                        sb.append(age).append("岁/").append(zodiac).append("/").append(nativePlace).append("/")
                                .append(educarion);
                        if (StringUtils.isNotBlank(specialty)) {
                            baseSituation = sb.append("(").append(specialty).append(")").toString();
                        } else {
                            baseSituation = sb.toString();
                        }
                        map2.put("baseSituation", baseSituation);
                        sb = null;
                    }
                }
                page.setRows(resultList);
                page.setRecords(totalCount.longValue());
            }
            return JsonResult.success(page);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ErrorMsg.ORDER_SHARE_ERR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
    }

    /**
     * 投资箱阿姨明细
     *
     * @param userBean
     * @param id
     * @return
     */
    @SuppressWarnings("rawtypes")
    @Override
    public JsonResult getStaffDetailInfo(UserBean userBean, Integer id) {

        //主键查询
        DeliveryBoxInfo deliveryBoxInfo = deliveryBoxInfoMapper.selectByPrimaryKey(id);
        Map<String, Object> map = new HashMap<>();
        if (deliveryBoxInfo.getTenantId() != null && !deliveryBoxInfo.getTenantId().equals("")) {
            map.put("tenantId", deliveryBoxInfo.getTenantId());
        }
        if (deliveryBoxInfo.getAddAccount() != null && !deliveryBoxInfo.getAddAccount().equals("")) {
            map.put("loginAccount", deliveryBoxInfo.getAddAccount());
        }
        Map<String, Object> ayiSource = tenantsUsersMapper.getTenants(map);//获取阿姨来源信息


        //阿姨基本信息
        Integer staffId = deliveryBoxInfo.getStaffId();
        TenantsStaffsInfoEntity tenantsStaffsInfo = tenantsStaffsInfoMapper.selectByPrimaryKey(staffId);
        HashMap<Object, Object> baseInfo = Maps.newHashMap();
        baseInfo.put("headImage", tenantsStaffsInfo.getHeadImage());
        baseInfo.put("matchingDegree", deliveryBoxInfo.getMatchingDegree());
        baseInfo.put("staffName", tenantsStaffsInfo.getStaffName());
        baseInfo.put("age", tenantsStaffsInfo.getAge());
        baseInfo.put("sex", StringUtils.isBlank(tenantsStaffsInfo.getSex()) ? "" : DictionarysCacheUtils.getSexName(tenantsStaffsInfo.getSex()));//02女01男
        baseInfo.put("zodiac", StringUtils.isBlank(tenantsStaffsInfo.getZodiac()) ? "" : DictionarysCacheUtils.getZodiacName(tenantsStaffsInfo.getZodiac()));//属相
        baseInfo.put("nativePlace", StringUtils.isBlank(tenantsStaffsInfo.getNativePlace()) ? "" : DictionarysCacheUtils.getNativePlaceName(tenantsStaffsInfo.getNativePlace()));//籍贯
        baseInfo.put("educarion", StringUtils.isBlank(tenantsStaffsInfo.getEducarion()) ? "" : DictionarysCacheUtils.getEducationName(tenantsStaffsInfo.getEducarion()));//学历
        baseInfo.put("nation", StringUtils.isBlank(tenantsStaffsInfo.getNation()) ? "" : DictionarysCacheUtils.getNationName(tenantsStaffsInfo.getNation()));//民族
        baseInfo.put("constellation", StringUtils.isBlank(tenantsStaffsInfo.getConstellation()) ? "" : DictionarysCacheUtils.getConstellationName(tenantsStaffsInfo.getConstellation()));//星座
        baseInfo.put("maritalStatus", StringUtils.isBlank(tenantsStaffsInfo.getMaritalStatus()) ? "" : DictionarysCacheUtils.getMaritalName(tenantsStaffsInfo.getMaritalStatus()));//婚姻状况
        baseInfo.put("fertilitySituation", StringUtils.isBlank(tenantsStaffsInfo.getFertilitySituation()) ? "" : DictionarysCacheUtils.getFertilityName(tenantsStaffsInfo.getFertilitySituation()));//生育情况
        baseInfo.put("weight", tenantsStaffsInfo.getWeight());//体重
        baseInfo.put("height", tenantsStaffsInfo.getHeight());//身高
        baseInfo.put("bloodType", StringUtils.isBlank(tenantsStaffsInfo.getBloodType()) ? "" : DictionarysCacheUtils.getBloodName(tenantsStaffsInfo.getBloodType()));//血型
        baseInfo.put("cv", tenantsStaffsInfo.getCv());//电子简历
        baseInfo.put("tenantName", ayiSource.get("tenantName"));//投递箱阿姨来源--门店名称
        baseInfo.put("name", ayiSource.get("name"));//投递箱阿姨来源--操作人名称
        baseInfo.put("phone", ayiSource.get("phone"));//投递箱阿姨来源--门店联系电话
        //阿姨求职信息
        TenantsStaffJobInfoEntity tenantsStaffJobInfo = tenantsStaffJobInfoMapper.selectByPrimaryKey(staffId);
        HashMap<Object, Object> jobInfo = Maps.newHashMap();
        String languageFeature = tenantsStaffJobInfo.getLanguageFeature();
        String cookingFeature = tenantsStaffJobInfo.getCookingFeature();
        String characerFeature = tenantsStaffJobInfo.getCharacerFeature();
        String petFeeding = tenantsStaffJobInfo.getPetFeeding();
        String elderlySupport = tenantsStaffJobInfo.getElderlySupport();
        String serviceProvice = tenantsStaffJobInfo.getServiceProvice();
        String workExperience = tenantsStaffJobInfo.getWorkExperience();
        String selfEvaluation = tenantsStaffJobInfo.getSelfEvaluation();
        String teacherEvaluation = tenantsStaffJobInfo.getTeacherEvaluation();

        jobInfo.put("languageFeature", StringUtils.isBlank(languageFeature) ? "" : DictionarysCacheUtils.getFeaturesStr("01", languageFeature));//语言特点
        jobInfo.put("characerFeature", StringUtils.isBlank(characerFeature) ? "" : DictionarysCacheUtils.getFeaturesStr("02", characerFeature));//性格特点
        jobInfo.put("cookingFeature", StringUtils.isBlank(cookingFeature) ? "" : DictionarysCacheUtils.getFeaturesStr("03", cookingFeature));  //烹饪特点
        /*if(StringUtils.isBlank(petFeeding)&&StringUtils.isBlank(elderlySupport)){
            jobInfo.put("doHomeWork", "false");//不做家庭
		}else{
			jobInfo.put("doHomeWork", "true");//做家庭
		}*/

        // @xiehui
        jobInfo.put("elderlySupport", StringUtils.isBlank(tenantsStaffJobInfo.getElderlySupport()) ? "0"
                : tenantsStaffJobInfo.getElderlySupport());// 是否有老人
        jobInfo.put("petFeeding",
                StringUtils.isBlank(tenantsStaffJobInfo.getPetFeeding()) ? "0" : tenantsStaffJobInfo.getPetFeeding());// 是否有宠物

        jobInfo.put("serviceProvice", StringUtils.isBlank(serviceProvice) ? "" : DictionarysCacheUtils.getProviceName(serviceProvice));//服务区域
        jobInfo.put("workExperience", workExperience);//工作经历
        jobInfo.put("selfEvaluation", selfEvaluation);//自我评价
        jobInfo.put("teacherEvaluation", teacherEvaluation);//老师评价

        //阿姨服务工种可能有多个
        List<TenantsStaffSerItemsEntity> tenantsStaffSerItemsEntity = tenantsStaffSerItemsMapper.getServiceItemsByStaffId(staffId);
        if (tenantsStaffSerItemsEntity != null && tenantsStaffSerItemsEntity.size() > 0) {
            StringBuffer sb = new StringBuffer();
            StringBuffer sb1 = new StringBuffer();
            String serviceItemCode_tmp = "";
            String staffId_tmp = "";
            for (TenantsStaffSerItemsEntity tenantsStaffSerItems : tenantsStaffSerItemsEntity) {
                String serviceItemCode = tenantsStaffSerItems.getServiceItemCode();
                serviceItemCode_tmp = serviceItemCode;
                String skill = tenantsStaffSerItems.getSkills();
                //根据code获取value
                String serviceTypeName = DictionarysCacheUtils.getServiceTypeName(serviceItemCode);
                //根据code和skills获取 阿姨服务工种-->对应的技能特点
                String skills = DictionarysCacheUtils.getSkillsStr(serviceItemCode, skill);
                sb.append(serviceTypeName).append("/");
                sb1.append(skills).append(",");
                staffId_tmp = tenantsStaffSerItems.getStaffId() + "";
            }

            String string = sb.toString();
            String string2 = sb1.toString();
            String skills = string2.substring(0, string2.length() - 1);
            String substring = string.substring(0, string.length() - 1);
            jobInfo.put("serviceType", substring);//服务工种
            jobInfo.put("skills", skills);//服务工种对应的-->技能特点
            //Baron 20170420 获取最后一条信息返回前台
            jobInfo.put("serviceItemCode", serviceItemCode_tmp);
            jobInfo.put("staffId", staffId_tmp);


        }
        HashMap<String, Object> resultMap = Maps.newHashMap();

        HashMap<String, Object> tenantsInfo = Maps.newHashMap();
        Integer tenantId = deliveryBoxInfo.getTenantId();

        //TenantsApps tenantsApps = tenantsAppsMapper.selectByPrimaryKey(tenantId);
        //@谢辉：添加loginName
        //tenantsApps.setLoginName(userBean.getUserName());


        //@xiehui
        Map<String, Object> mapLogin = new HashMap<>();
        mapLogin.put("loginAccount", deliveryBoxInfo.getAddAccount());
        mapLogin.put("tenantId", tenantId);
        TenantsApps tenantsApps = tenantsAppsMapper.selectByLoginAccount(mapLogin);

        tenantsInfo.put("company", tenantsApps.getWebsiteName());//家政公司
        tenantsInfo.put("contact", tenantsApps.getContactName());//联系人
        //tenantsInfo.put("mobile", tenantsApps.getContactPhone());//联系电话
        tenantsInfo.put("mobile", tenantsApps.getTenantsPhone());//门店联系电话
        tenantsInfo.put("systemTime", new Date().getTime());//系统时间
        tenantsInfo.put("addTime", deliveryBoxInfo.getAddTime().getTime());//投递阿姨时间
        tenantsInfo.put("status", deliveryBoxInfo.getHandleResult());//状态

//		
        resultMap.put("baseInfo", baseInfo);
        resultMap.put("jobInfo", jobInfo);
        resultMap.put("tenantsInfo", tenantsInfo);
        //@xiehui
        resultMap.put("loginName", tenantsApps.getLoginName());
        return JsonResult.success(resultMap);
    }

    /**
     * 订单分享方
     */
    @Override
    public JsonResult staffPass(UserBean userBean, Integer id) {
        DeliveryBoxInfo deliveryBoxInfo = deliveryBoxInfoMapper.selectByPrimaryKey(id);
        String orderNo = deliveryBoxInfo.getOrderNo();
        // 点击通过，校验该阿姨对应的订单已经接受的阿姨数目是否超过上限5个;
        boolean b = verifyStaffOrderCount(orderNo);
        if (!b) {
//            return JsonResult.failure(null);
        }

        // b、若<5个，则倒计时被清空并将该阿姨加入“蜂享阿姨”列表
        OrderShareStaffInfo orderShareStaffInfo = orderShareStaffInfoMapper
                .selectByPrimaryKey(deliveryBoxInfo.getOrderShareStaffId());
        orderShareStaffInfo.setStatus("02");// 01等待对方老师确认 02 已通过,待完成 03已拒绝 04已退回
        // 05已结单
        orderShareStaffInfoMapper.updateByPrimaryKeySelective(orderShareStaffInfo);
        // 若对方老师同意了该阿姨信息, B)订单列表中生成一笔“淘蜂享”订单，且状态为“待完成”。
        // 根据订单号查询客户信息
        Orders orders = ordersMapper.selectByPrimaryKey(orderNo);
        Orders order = new Orders();
        //生成淘蜂享订单
        String tOrderNo = commonService.createOrderNo(Constants.TFX);// 订单来源 01
        // 本地订单
        // 02
        // 网络订单
        // 03
        // 淘蜂享
        order.setOrderNo(tOrderNo);// 订单号
        order.setShareOrderNo(orderNo);// 分享订单号
        order.setTenantId(deliveryBoxInfo.getTenantId());// 租户id 非分享方租户id
        order.setMemberId(orders.getMemberId());// 客户号
        order.setOrderTime(new Date()); // 提交时间
        order.setOrderSource("03"); // 订单来源 01 本地订单 02 网络订单 03 淘蜂享
        order.setServiceItemCode(orders.getServiceItemCode());// 服务工种
        order.setIdepositOver("02"); // 定金是否支付
        order.setIsInterview("01"); // 是否面试
        order.setIsLock("0"); // 是否锁定(预留字段)
        order.setStaffId(deliveryBoxInfo.getStaffId());// 阿姨id
        order.setOrderStatus("09");// 01待完成 ，02已结单，03已取消 // 订单状态 01 待支付定金 02 待面试
        // 03 待支付尾款 04 已完成 05 已取消 06 待完成 07已结单
        //@zsq 记录原始订单来源
        order.setUserId(orders.getUserId());
        System.out.println("+++++++++++++++++原始订单userId+++++++++++++++" + order.getUserId());
        order.setUserType(orders.getUserType());
        order.setUserName(orders.getUserName());
        System.out.println("+++++++++++++++++原始订单用户名称+++++++++++++++" + order.getUserName());
        order.setModifyTime(new Date());
        order.setModifyAccount(userBean.getLoginAccount());

        //@zsq 淘分享订单保存阿姨服务工种id
        System.out.println("++++++++++++++++淘蜂享订单阿姨id+++++++++++++++++" + deliveryBoxInfo.getStaffId());
        TenantsStaffSerItemsEntity tenantsStaffSerItemsEntity = new TenantsStaffSerItemsEntity();
        tenantsStaffSerItemsEntity.setStaffId(deliveryBoxInfo.getStaffId());
        tenantsStaffSerItemsEntity.setTenantId(deliveryBoxInfo.getTenantId());
        tenantsStaffSerItemsEntity.setServiceItemCode(orders.getServiceItemCode());
        TenantsStaffSerItemsEntity tenantsStaffSerItems = tenantsStaffSerItemsMapper.getStaffServiceItemsByKey(tenantsStaffSerItemsEntity);
        System.out.println("+++++++++++++++++淘蜂享订单阿姨服务工种id+++++++++++++++" + tenantsStaffSerItems.getId());
        order.setStafffSerItemId(tenantsStaffSerItems.getId());
        System.out.println("+++++++++++++++++淘蜂享订单阿姨服务工种id+++++++++++++++" + order.getStafffSerItemId());
        ordersMapper.insertSelective(order);

        //插入客户信息
        OrderCustomersInfo orderCustomersInfo = orderCustomersInfoMapper.selectByPrimaryKey(orderNo);
        OrderCustomersInfoEntity newOrderCustomersInfo = new OrderCustomersInfoEntity();
        BeanUtils.copyProperties(orderCustomersInfo, newOrderCustomersInfo);
        newOrderCustomersInfo.setOrderNo(tOrderNo);// 订单号
        orderCustomersInfoMapper.insert(newOrderCustomersInfo);
        deliveryBoxInfo.setHandleResult("02");// 处理结果
        // 01待处理、02待面试、03已拒绝、04已退回、05已通过
        // 设置淘分享订单号
        deliveryBoxInfo.setShareOrderNo(tOrderNo);
        deliveryBoxInfoMapper.updateByPrimaryKeySelective(deliveryBoxInfo);
		
		/*//@zsq 原始订单记录投递箱阿姨来源    这个不用在这里记录。只有点击更换家政员才会记录阿姨来源。
		Orders shareOrder = new Orders();
		
		shareOrder.setShareTenantId(deliveryBoxInfo.getTenantId());
		System.out.println("++++++++++++++++阿姨投递方门店id+++++++++++++++++++"+shareOrder.getShareTenantId());
		shareOrder.setShareId(userBean.getUserId());
		System.out.println("++++++++++++++++++阿姨投递方用户id+++++++++++++++++++"+shareOrder.getShareId());
		shareOrder.setShareName(userBean.getUserName());
		System.out.println("++++++++++++++++++++阿姨投递方用户名称+++++++++++++++++++++"+shareOrder.getShareName());
		ordersMapper.updateShareId(shareOrder);*/

        // 通知
        //commonService.staffPassNotice(deliveryBoxInfo.getTenantId());
        return JsonResult.success(null);

    }

    /**
     * @param orderNo
     * @return
     */
    private boolean verifyStaffOrderCount(String orderNo) {
        Integer count = deliveryBoxInfoMapper.selectOrderCount(orderNo);
        if (count >= 5) {
            return false;
        }
        return true;
    }

    @Override
    public JsonResult rejectStaff(UserBean userBean, Integer id) {
        DeliveryBoxInfo deliveryBoxInfo = deliveryBoxInfoMapper.selectByPrimaryKey(id);
        deliveryBoxInfo.setHandleResult("03");// 处理结果
        // 01待处理、02待面试、03已拒绝、04已退回、05已通过
        deliveryBoxInfoMapper.updateByPrimaryKey(deliveryBoxInfo);
        OrderShareStaffInfo orderShareStaffInfo = orderShareStaffInfoMapper
                .selectByPrimaryKey(deliveryBoxInfo.getOrderShareStaffId());
        orderShareStaffInfo.setStatus("03");// 01等待对方老师确认，02，已通过,待完成，03已拒绝
        orderShareStaffInfoMapper.updateByPrimaryKeySelective(orderShareStaffInfo);
        // 阿姨提供方的200元保证金被解冻
        //commonService.thawAmount(deliveryBoxInfo.getTenantId());
        // 将阿姨分享状态修改为未分享
        //commonService.updateStaffStatus(deliveryBoxInfo.getStaffId(), "02");
        // 通知
        //commonService.rejectStaffNotice(deliveryBoxInfo.getTenantId());

        return JsonResult.success(null);
    }

    /**
     * 面试不通过退回<obr>
     * 或者没有参加面试
     *
     * @param userBean
     * @return
     */
    @Override
    public JsonResult staffReturn(UserBean userBean, String reason, Integer id) {
        // 点击退回， 弹出退回确认框，填写完"退回原因"，点击确认即将阿姨退回。
        DeliveryBoxInfo deliveryBoxInfo = deliveryBoxInfoMapper.selectByPrimaryKey(id);
        deliveryBoxInfo.setReturnReason(reason);// 退回原因
        deliveryBoxInfo.setHandleResult("04");// 处理结果
        // 01待处理、02待面试、03已拒绝、04已退回、05已通过
        deliveryBoxInfoMapper.updateByPrimaryKeySelective(deliveryBoxInfo);
        OrderShareStaffInfo orderShareStaffInfo = orderShareStaffInfoMapper
                .selectByPrimaryKey(deliveryBoxInfo.getOrderShareStaffId());
        orderShareStaffInfo.setRemark(reason);// 退回原因
        orderShareStaffInfo.setStatus("04");// 01等待对方老师确认，02已通过,待完成，03已拒绝，04已退回
        orderShareStaffInfoMapper.updateByPrimaryKeySelective(orderShareStaffInfo);
        // 阿姨提供方的200元保证金被解冻
        //commonService.thawAmount(deliveryBoxInfo.getTenantId());
        Orders orders = ordersMapper.selectByPrimaryKey(deliveryBoxInfo.getShareOrderNo());
        orders.setOrderStatus("05");// 订单状态 01 待支付定金 02 待面试 03 待支付尾款 04 已完成 05
        // 已取消 06 待完成 07已结单
        ordersMapper.updateByPrimaryKeySelective(orders);
        // 将阿姨分享状态修改为未分享
        //commonService.updateStaffStatus(deliveryBoxInfo.getStaffId(), "02");
        // 通知
        //commonService.returnNotice(deliveryBoxInfo.getTenantId());

        return JsonResult.success(null);
    }

    /**
     * 阿姨面试通过
     */
    @Override
    public JsonResult staffInterviewPass(UserBean userBean, Integer id) {
        DeliveryBoxInfo deliveryBoxInfo = deliveryBoxInfoMapper.selectByPrimaryKey(id);
        deliveryBoxInfo.setModifyAccount(userBean.getLoginAccount());
        deliveryBoxInfo.setModifyTime(new Date());
        deliveryBoxInfo.setHandleResult("05");// 处理结果
        // 01待处理、02待面试、03已拒绝、04已退回、05已通过
        deliveryBoxInfoMapper.updateByPrimaryKeySelective(deliveryBoxInfo);

        // 更新订单号
        String orderNo = deliveryBoxInfo.getOrderNo();
        Orders orders = ordersMapper.selectByPrimaryKey(orderNo);
        orders.setOrderStatus("03");// 01 待支付定金 02 待面试 03 待支付尾款 04 已完成 05 已取消 06
        // 待完成 07已结单 08结单后阿姨更换处理',
        orders.setStaffId(deliveryBoxInfo.getStaffId());
        orders.setModifyTime(new Date());
        ordersMapper.updateByPrimaryKeySelective(orders);
        return JsonResult.success(null);
    }
}
