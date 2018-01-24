package com.fbee.modules.service.impl;

import com.fbee.modules.bean.ErrorMsg;
import com.fbee.modules.bean.MatchingModel;
import com.fbee.modules.bean.OrderMatchBean;
import com.fbee.modules.bean.UserBean;
import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.bean.consts.JobConst;
import com.fbee.modules.core.Log;
import com.fbee.modules.core.config.Global;
import com.fbee.modules.core.page.Page;
import com.fbee.modules.core.page.form.TenantJobResumeForm;
import com.fbee.modules.core.utils.DateUtils;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.form.*;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.jsonData.extend.StaffQueryJson;
import com.fbee.modules.mybatis.dao.*;
import com.fbee.modules.mybatis.entity.*;
import com.fbee.modules.mybatis.model.*;
import com.fbee.modules.operation.StaffsOpt;
import com.fbee.modules.redis.JedisTemplate;
import com.fbee.modules.redis.JedisUtils;
import com.fbee.modules.service.CommonService;
import com.fbee.modules.service.OrderService;
import com.fbee.modules.service.StaffsService;
import com.fbee.modules.service.basic.BaseService;
import com.fbee.modules.service.basic.ServiceException;
import com.fbee.modules.utils.DictionariesUtil;
import com.fbee.modules.utils.DictionarysCacheUtils;
import com.fbee.modules.utils.JsonUtils;
import com.fbee.modules.utils.SkillMacthUtils;
import com.fbee.modules.wechat.message.config.WechatConfig;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderServiceImpl extends BaseService implements OrderService {

    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    OrdersMapper orderDao;

    @Autowired
    OrderCustomersInfoMapper orderCustomersInfoDao;

    @Autowired
    OrderContractInfoMapper orderContractInfoDao;

    @Autowired
    TenantsStaffsInfoMapper tenantsStaffsInfoDao;

    @Autowired
    TenantsStaffJobInfoMapper tenantsStaffJobInfoDao;

    @Autowired
    TenantsStaffSerItemsMapper tenantsStaffSerItemsDao;

    @Autowired
    TenantsStaffCertsInfoMapper tenantsStaffCertsInfoDao;

    @Autowired
    OrderChangeStaffInfoMapper orderChangeStaffInfoDao;

    @Autowired
    CommonService commonService;
    @Autowired
    OrderChangehsRecordsMapper orderChangehsRecordsMapper;

    @Autowired
    StaffsService staffsService;

    @Autowired
    TenantsFinanceRecordsMapper tenantsFinanceRecordsDao;

    @Autowired
    TenantsJobsMapper tenantsJobsMapper;

    @Autowired
    TenantsJobResumesMapper tenantsJobResumesMapper;

    private JedisTemplate mq = JedisUtils.getJedisMessage();

    /**
     * 订单列表查询
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public JsonResult selectOrdersList(OrdersForm form) {
        try {

            OrdersEntity entity = new OrdersEntity();
            entity.setStartTime(form.getStartTime());
            entity.setEndTime(form.getEndTime());
            entity.setMemberName(form.getMemberName());
            entity.setMemberMobile(form.getMemberMobile());
            entity.setOrderNo(form.getOrderNo());
            entity.setOrderStatus(form.getOrderStatus());
            entity.setQueryStatus(form.getQueryStatus());
            entity.setServiceItemCode(form.getServiceItemCode());
            entity.setOrderSource(form.getOrderSource());
            entity.setPageNum(form.getPageNum() - 1);
            entity.setPageSize(form.getPageSize());
            entity.setTenantId(form.getTenantId());
            entity.setServiceProvince(form.getServiceProvince());
            //增减权限
            if (null != form.getAddAccount() && !"".equals(form.getAddAccount())) {
                entity.setAddAccount(form.getAddAccount());
            }
            if (null != form.getUserId() && !"".equals(form.getUserId())) {
                entity.setUserId(form.getUserId());
            }
            //xiehui 测试合并版本
            Integer totalCount = this.orderDao.getCount(entity);
            // 分页实体
            Page<Map> page = new Page<Map>();
            page.setPage(form.getPageNum());
            page.setRowNum(form.getPageSize());
            // 最大页数判断
            int pageM = maxPage(totalCount, page.getRowNum(), page.getPage());
            if (pageM > 0) {
                page.setPage(pageM);
            }
            if (totalCount > 0) {
                entity.setPageNum(page.getOffset());
                entity.setPageSize(form.getPageSize());
                List<Map> resultList = orderDao.selectOrdersList(entity);
                String str = "";
                for (Map<String, Object> map : resultList) {
                    if (map.get("orderTime") != null) {
                        map.put("orderTime", DateUtils.formatDateTime((Date) map.get("orderTime")));
                    }
                    str = (String) map.get("serviceType");
                    if (!StringUtils.isBlank(str)) {
                        map.put("serviceType",
                                DictionarysCacheUtils.getServiceNatureStr((String) map.get("serviceItemCode"), str));
                    }
                    str = (String) map.get("serviceItemCode");
                    if (!StringUtils.isBlank(str)) {
                        map.put("serviceItemCode", DictionarysCacheUtils.getServiceTypeName(str));
                    }
                    str = (String) map.get("serviceProvice");
                    if (!StringUtils.isBlank(str)) {
                        map.put("serviceProvice", DictionarysCacheUtils.getProviceName(str));
                    }
                    str = (String) map.get("serviceCity");
                    if (!StringUtils.isBlank(str)) {
                        map.put("serviceCity", DictionarysCacheUtils.getCityName(str));
                    }
                    str = (String) map.get("serviceCounty");
                    if (!StringUtils.isBlank(str)) {
                        map.put("serviceCounty", DictionarysCacheUtils.getCountyName(str));
                    }
                    str = (String) map.get("zodiac");
                    if (!StringUtils.isBlank(str)) {
                        map.put("zodiac", DictionarysCacheUtils.getZodiacName(str));
                    }
                    str = (String) map.get("nativePlace");
                    if (!StringUtils.isBlank(str)) {
                        map.put("nativePlace", DictionarysCacheUtils.getNativePlaceName(str));
                    }
                    str = (String) map.get("headImage");
                    if (!StringUtils.isBlank(str)) {
                        String imageUrl = str;
                        if (StringUtils.isNotBlank(imageUrl)
                                && !imageUrl.startsWith("http")
                                && !imageUrl.startsWith("data")) {
                            map.put("headImage", Constants.IMAGE_URL + str);
                        } else {
                            map.put("headImage", str);
                        }

                    }
                    // 匹配度处理, 取消订单不匹配度
                    if (!"05".equals(form.getQueryStatus()) && map.get("staffId") != null && map.get("staffId") != "") {
                        map.put("matchValue", getMatchValue((String) map.get("orderNo"), (Integer) map.get("staffId")));
                    } else {
                        map.put("matchValue", 0);
                    }
                }
                page.setRows(resultList);
                page.setRecords(totalCount.longValue());
            }
            return JsonResult.success(page);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ErrorMsg.ORDERS_QUERY_ERROR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
    }

    /**
     * 通过订单流水号查询订单信息
     */
    @Override
    public JsonResult selectByOrderNo(String orderNo) {
        try {
            Map<String, Object> map = orderDao.selectByOrderNo(orderNo);
            String str = "";
            map.put("orderTime", DateUtils.formatDateTime((Date) map.get("orderTime")));
            map.put("modifyTime", DateUtils.formatDateTime((Date) map.get("modifyTime")));
            str = (String) map.get("serviceType");
            if (!StringUtils.isBlank(str)) {
                map.put("serviceType",
                        DictionarysCacheUtils.getServiceNatureStr((String) map.get("serviceItemCode"), str));
            }
            str = (String) map.get("serviceItemCode");
            if (!StringUtils.isBlank(str)) {
                map.put("serviceItemCode", DictionarysCacheUtils.getServiceTypeName(str));
            }
            return JsonResult.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ErrorMsg.ORDERS_QUERY_ERROR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
    }

    /**
     * 通过订单流水号查询客户信息
     */
    @Override
    public JsonResult selectMemberInfoByOrderNo(String orderNo) {
        try {
            Map<String, Object> map = orderCustomersInfoDao.selectMemberInfoByOrderNo(orderNo);
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
            Log.error(ErrorMsg.ORDERS_QUERY_ERROR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
    }

    /**
     * 通过订单流水号查询服务信息
     */
    @Override
    public JsonResult selectServiceInfoByOrderNo(String orderNo) {
        try {
            Map<String, Object> map = orderCustomersInfoDao.selectServiceInfoByOrderNo(orderNo);
            String str = "";
            str = (String) map.get("serviceType");
            if (!StringUtils.isBlank(str)) {
                map.put("serviceType", str + Constants.COMMA
                        + DictionarysCacheUtils.getServiceNatureStr((String) map.get("serviceItemCode"), str));
            }
            if (map.get("serviceStart") != null) {
                map.put("serviceStart", DateUtils.formatDateTime((Date) map.get("serviceStart")));
            }
            if (map.get("serviceEnd") != null) {
                map.put("serviceEnd", DateUtils.formatDateTime((Date) map.get("serviceEnd")));
            }
            str = (String) map.get("salarySkills");
            if (!StringUtils.isBlank(str)) {
                map.put("salarySkills", DictionarysCacheUtils.getSkillsList(map.get("serviceItemCode") + "", str));
            }
            str = (String) map.get("languageRequirements");
            if (!StringUtils.isBlank(str)) {
                map.put("languageRequirements", DictionarysCacheUtils.getFeaturesList("01", str));
            }
            str = (String) map.get("cookingRequirements");
            if (!StringUtils.isBlank(str)) {
                map.put("cookingRequirements", DictionarysCacheUtils.getFeaturesList("02", str));
            }
            str = (String) map.get("personalityRequirements");
            if (!StringUtils.isBlank(str)) {
                map.put("personalityRequirements", DictionarysCacheUtils.getFeaturesList("03", str));
            }
            str = (String) map.get("serviceItemCode");
            if (!StringUtils.isBlank(str)) {
                map.put("serviceItemCode", str + Constants.COMMA + DictionarysCacheUtils.getServiceTypeName(str));
            }
            str = (String) map.get("experienceRequirements");
            if (!StringUtils.isBlank(str)) {
                map.put("experienceRequirementsValue", DictionariesUtil.getExperienceValue(str));
            }
            str = (String) map.get("wageRequirements");
            if (!StringUtils.isBlank(str)) {
                map.put("wageRequirementsValue", DictionariesUtil.getAgerange(str));
            }
            str = (String) map.get("salaryType");
            if (!StringUtils.isBlank(str)) {
                map.put("salaryTypeValue", DictionarysCacheUtils.getServicePriceUnit(str));
            }
            return JsonResult.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ErrorMsg.ORDERS_QUERY_ERROR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
    }

    /**
     * 通过订单流水号查询支付信息
     */
    @Override
    public JsonResult selectPayInfoByOrderNo(String orderNo) {
        try {
            Map<String, Object> map = orderDao.selectPayInfoByOrderNo(orderNo);
            if ((Date) map.get("depositDate") != null) {
                map.put("depositDate", DateUtils.formatDateTime((Date) map.get("depositDate")));
            }
            if ((Date) map.get("passViewDate") != null) {
                map.put("passViewDate", DateUtils.formatDateTime((Date) map.get("passViewDate")));
            }
            if ((Date) map.get("balanceDate") != null) {
                map.put("balanceDate", DateUtils.formatDateTime((Date) map.get("balanceDate")));
            }
            return JsonResult.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ErrorMsg.ORDERS_QUERY_ERROR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
    }

    /**
     * 根据订单流水号获取合同详情
     */
    @Override
    public JsonResult selectContractInfoByOrderNo(String orderNo) {
        try {
            List<Map<String, String>> list = orderContractInfoDao.selectContractInfoByOrderNo(orderNo);
            for (Map<String, String> map : list) {
                String imageUrl = map.get("contractUrl");
                if (StringUtils.isNotBlank(imageUrl)
                        && !imageUrl.startsWith("http")
                        && !imageUrl.startsWith("data")) {
                    map.put("contractUrl", Constants.IMAGE_URL + map.get("contractUrl"));
                } else {
                    map.put("contractUrl", map.get("contractUrl"));
                }

            }
            return JsonResult.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ErrorMsg.ORDERS_QUERY_ERROR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
    }

    @Override
    public JsonResult deleteContractInfo(Integer id) {
        if (id == null) {
            throw new ServiceException("未获取到阿姨信息,请联系管理员");
        }
        OrderContractInfo entity = this.orderContractInfoDao.selectByPrimaryKey(id);
        if (entity == null) {
            throw new ServiceException("未获取到阿姨信息,请联系管理员");
        }
        // 删除记录
        this.orderContractInfoDao.deleteByPrimaryKey(id);
        // 删除图片
        String serverPath = Global.getConfig("website_base_path");// 服务器路径
        String imgPath = serverPath + File.separator + entity.getContractUrl();
        File file = new File(imgPath);
        boolean flag = file.delete();
        if (!flag) {
            throw new ServiceException("删除图片信息失败,请联系IT人员");
        }
        return JsonResult.success(null);
    }

    /**
     * 面试通过更新订单信息
     */
    @Override
    public JsonResult passInterviewInfo(String orderNo, String staffId, UserBean userBean) {
        // 根据订单流水号获取订单实体
        Orders order = orderDao.selectByPrimaryKey(orderNo);
        order.setIsInterview("02");
        order.setPassViewDate(new Date());
        order.setOrderStatus("03");
        order.setModifyTime(new Date());
        order.setModifyAccount(userBean.getLoginAccount());
        order.setStaffId(Integer.parseInt(staffId));// 更新原始订单阿姨
        // 面试通过更新订单信息
        orderDao.updateByPrimaryKeySelective(order);
        // 面试通过更新阿姨的工作状态
        TenantsStaffsInfoEntity entity = new TenantsStaffsInfoEntity();
        entity.setStaffId(order.getStaffId());
        entity.setWorkStatus("02");
        tenantsStaffsInfoDao.update(entity);

        //更新职位对应状态
        TenantsJobs job = tenantsJobsMapper.getByOrderNo(orderNo);
        if (job != null) {
            TenantsJobs updateJob = new TenantsJobs();
            updateJob.setId(job.getId());
            updateJob.setOrderNo(orderNo);
            updateJob.setOrderStatus(order.getOrderStatus());
            tenantsJobsMapper.update(updateJob);
        }

        //待支付尾款提醒
        mq.publish(WechatConfig.Channel.ORDER_WAIT_PAY_BALANCE.getChannel(), orderNo);


        return JsonResult.success(null);
    }

    /**
     * 保存订单详情
     */
    @Override
    public JsonResult saveOrder(String orderNo, String amount, String remark, String serviceCharge) {
        // 获取订单信息
        Orders order = orderDao.selectByPrimaryKey(orderNo);
        if (null != amount && !"".equals(amount)) {
            // 订单金额
            order.setAmount(new BigDecimal(amount));

            if (null == order.getOrderDeposit()) {
                order.setOrderDeposit(new BigDecimal("0"));
            }
            // 尾款金额
            order.setOrderBalance(BigDecimal.valueOf(Double.parseDouble(amount)).subtract(order.getOrderDeposit()));

            if (null != serviceCharge && !"".equals(serviceCharge)) {
                order.setServiceCharge(new BigDecimal(serviceCharge));
                order.setOrderBalance(BigDecimal.valueOf(Double.parseDouble(amount)).subtract(order.getOrderDeposit())
                        .add(order.getServiceCharge()));
            } else {
                order.setServiceCharge(new BigDecimal("0.00"));
            }
        }
        order.setRemark(remark);
        return JsonResult.success(orderDao.updateByPrimaryKeySelective(order));
    }

    /**
     * 更换订单阿姨
     */
    @Override
    public JsonResult changeStaff(String orderNo, Integer staffId, Integer isLocalStaff, UserBean userBean) {
        Orders updateOrder = new Orders();

        Orders ordershare = orderDao.selectByPrimaryKey(orderNo);
        //@zsq 根据阿姨id查询出阿姨工种id
        TenantsStaffSerItemsEntity tenantsStaffSerItemsEntity = new TenantsStaffSerItemsEntity();
        tenantsStaffSerItemsEntity.setStaffId(staffId);

        //@zsq 根据阿姨id查询门店id
        TenantsStaffsInfoEntity tenantsStaffsInfo = tenantsStaffsInfoDao.selectByPrimaryKey(staffId);

        tenantsStaffSerItemsEntity.setTenantId(tenantsStaffsInfo.getTenantId());
        tenantsStaffSerItemsEntity.setServiceItemCode(ordershare.getServiceItemCode());
        TenantsStaffSerItemsEntity tenantsStaffSerItems = tenantsStaffSerItemsDao.getStaffServiceItemsByKey(tenantsStaffSerItemsEntity);


        updateOrder.setOrderNo(orderNo);
        updateOrder.setStaffId(staffId);
        updateOrder.setIsLocalStaff(isLocalStaff);
        updateOrder.setStafffSerItemId(tenantsStaffSerItems.getId());
        updateOrder.setModifyTime(new Date());
        updateOrder.setModifyAccount(userBean.getLoginAccount());
        orderDao.updateByPrimaryKeySelective(updateOrder);

        return JsonResult.success(null);
    }

    /**
     * 客户合同图片上传
     */
    @Override
    public JsonResult saveContractInfo(String orderNo, MultipartFile[] file, String imgIds) {
        // 判断合同图片是否已达上限
        int totalCount = orderContractInfoDao.getCountByOrderNo(orderNo);
        if (totalCount > Constants.CONTRACT_NUM || totalCount == Constants.CONTRACT_NUM) {
            return JsonResult.failure(ResultCode.Order.ORDER_CONTRACT_IS_UPPER_LIMIT);
        }
        // 删除图片
        if (StringUtils.isNotBlank(imgIds)) {
            List<String> ids = orderContractInfoDao.getContractsByOrderNo(orderNo);// 获取合同图片的id
            String[] imgId = imgIds.split(",");
            for (int i = 0; i < imgId.length; i++) {
                if (ids.contains(imgId[i])) {
                    deleteContractInfo(Integer.parseInt(imgId[i]));
                }
            }
        }
        List<String> list = new ArrayList<String>();
        String serverPath = Global.getConfig("website_base_path");// 服务器路径
        String basePath = Constants.ORDER_IMAGE_BASE_PATH;// 父路径
        String contactPath = Constants.CONTRACT_IMAGE_BASE_PATH;// 子路径
        String fileSavePath = serverPath + "/" + basePath + "/" + contactPath + "/"; // 服务器路径
        String imgUrl = "/" + basePath + "/" + contactPath + "/"; // 数据库保存的图片路径
        for (int i = 0; i < file.length; i++) {
            if (totalCount > Constants.CONTRACT_NUM) {
                return JsonResult.failure(ResultCode.Order.ORDER_CONTRACT_IS_UPPER_LIMIT);
            }
            String uploadFileName = file[i].getOriginalFilename();
            OrderContractInfo orderContractInfo = new OrderContractInfo();
            if (!file[i].isEmpty()) {
                // 获取文件后缀
                String suffix = uploadFileName.substring(uploadFileName.lastIndexOf("."), uploadFileName.length());
                if (suffix == null || "".equals(suffix)) {
                    throw new ServiceException("未知文件类型，上传失败！");
                }
                // 校验文件类型
                if (!verifyImageType(suffix)) {
                    throw new ServiceException("文件类型不符合要求，上传失败！");
                }

                // 重命名上传后的文件名
                String fileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + suffix;
                File targetFile = new File(fileSavePath, fileName);
                if (!targetFile.exists()) {
                    targetFile.mkdirs();
                }
                // 保存
                try {
                    file[i].transferTo(targetFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                orderContractInfo.setOrderNo(orderNo);
                orderContractInfo.setContractUrl(imgUrl + fileName);
                orderContractInfoDao.insertSelective(orderContractInfo);
                list.add(fileSavePath + fileName);
                totalCount++;
            }
        }
        return JsonResult.success(list);
    }

    /**
     * 校验文件类型 过滤合法的文件类型
     *
     * @param suffix
     * @return
     */
    private boolean verifyImageType(String suffix) {
        suffix = suffix.substring(1);
        String allowSuffixs = "gif,jpg,jpeg,bmp,png,ico";
        if (allowSuffixs.indexOf(suffix) == -1) {
            return false;
        }
        return true;
    }

    /**
     * 客户合同图片删除(单个)
     */
    @Override
    public JsonResult deleteContract(Integer id) {
        deleteContractInfo(id);
        return JsonResult.success(null);
    }

    /**
     * 创建订单
     */
    @Override
    public JsonResult createOrder(OrderCreateForm form, UserBean userBean) {
        form.setOrderNo(commonService.createOrderNo("01"));
        form.setOrderSource("01");
        form.setOrderTime(DateUtils.formatDateTime(new Date()));
        // 将数组转换成字符串
        String languageRequirements = StringUtils.strAppend(form.getLanguageRequirements());
        String cookingReqirements = StringUtils.strAppend(form.getCookingReqirements());
        String personalityRequirements = StringUtils.strAppend(form.getPersonalityRequirements());

        Orders order = new Orders();
        order.setOrderNo(form.getOrderNo());
        /********本地订单添加操作人@zhangsq********/
        order.setUserId(userBean.getUserId());
        order.setUserType(userBean.getUserType());
        order.setUserName(userBean.getUserName());
        order.setModifyTime(new Date());
        order.setModifyAccount(userBean.getLoginAccount());
        /********结束********/
        order.setTenantId(form.getTenantId());
        order.setMemberId(form.getMemberId());
        order.setOrderTime(DateUtils.parseDate(form.getOrderTime()));
        order.setOrderSource(form.getOrderSource());
        order.setServiceItemCode(form.getServiceItemCode());
        //@zsq 本地订单插入服务工种id
        if (form.getStaffId() != null) {
            TenantsStaffSerItemsEntity tenantsStaffSerItemsEntity = new TenantsStaffSerItemsEntity();
            tenantsStaffSerItemsEntity.setStaffId(form.getStaffId());
            tenantsStaffSerItemsEntity.setTenantId(form.getTenantId());
            tenantsStaffSerItemsEntity.setServiceItemCode(form.getServiceItemCode());
            TenantsStaffSerItemsEntity tenantsStaffSerItemsId = tenantsStaffSerItemsDao.getStaffServiceItemsByKey(tenantsStaffSerItemsEntity);
            order.setStafffSerItemId(tenantsStaffSerItemsId.getId());
        }

        //权限管理 Baron 20170525
        if (null != form.getAddAccount() && !"".equals(form.getAddAccount())) {
            order.setAddAccount(form.getAddAccount());
        }

        if (null == form.getAmount() || "".equals(form.getAmount())) {
            order.setAmount(BigDecimal.valueOf(0.00));
        } else {
            order.setAmount(BigDecimal.valueOf(Double.parseDouble(form.getAmount())));
        }

        if (null == form.getServiceCharge() || "".equals(form.getServiceCharge())) {
            order.setServiceCharge(BigDecimal.valueOf(0.00));
            order.setOrderBalance(BigDecimal.valueOf(Double.parseDouble(form.getAmount())));
        } else {
            order.setServiceCharge(BigDecimal.valueOf(Double.parseDouble(form.getServiceCharge())));
            order.setOrderBalance(
                    BigDecimal.valueOf(Double.parseDouble(form.getAmount())).add(order.getServiceCharge()));

        }
        order.setIdepositOver("01");
        order.setIsInterview("01");
        order.setBalanceOver("01");
        order.setIsUsable("1");
        order.setIsLock("0");
        order.setOrderStatus("01");
        order.setOrderDeposit(BigDecimal.valueOf(0));
        order.setStaffId(form.getStaffId());
        order.setRemark(form.getRemark());
        // 保存订单
        orderDao.insertSelective(order);

        OrderCustomersInfo customersInfo = new OrderCustomersInfo();
        customersInfo.setOrderNo(form.getOrderNo());
        customersInfo.setServiceType(form.getServiceType());
        customersInfo.setServiceItemCode(form.getServiceItemCode());
        customersInfo.setMemberName(form.getMemberName());
        customersInfo.setMemberSex(form.getSex());
        customersInfo.setMemberMobile(form.getMemberMobile());
        customersInfo.setServiceProvice(form.getServiceProvice());
        customersInfo.setServiceCity(form.getServiceCity());
        customersInfo.setServiceCounty(form.getServiceCounty());
        customersInfo.setServiceAddress(form.getServiceAddress());
        customersInfo.setFamilyCount(form.getFamilyCount());
        customersInfo.setExpectedBirth(form.getExpectedBirth());
        customersInfo.setHouseType(form.getHouseType());
        if (StringUtils.isNotBlank(form.getHouseArea())) {
            customersInfo.setHouseArea(BigDecimal.valueOf(Double.parseDouble(form.getHouseArea())));
        }
        customersInfo.setChildrenCount(form.getChildrenCount());
        customersInfo.setChildrenAgeRange(form.getChildrenAgeRange());

        customersInfo.setOlderCount(form.getOlderCount());
        customersInfo.setOlderAgeRange(form.getOlderAgeRange());
        customersInfo.setSelfCares(form.getSelfCares());
        customersInfo.setServiceStart(DateUtils.parseDate(form.getServiceStart()));
        customersInfo.setServiceEnd(DateUtils.parseDate(form.getServiceEnd()));
        customersInfo.setIsBabyBorn(form.getIsBabyBorn());
        customersInfo.setSalary(form.getSalary());
        customersInfo.setSalaryType(form.getSalaryType());
        customersInfo.setPetRaising(form.getPetRaising());
        customersInfo.setLanguageRequirements(languageRequirements);
        customersInfo.setCookingReqirements(cookingReqirements);
        customersInfo.setPersonalityRequirements(personalityRequirements);
        customersInfo.setSpecialNeeds(form.getSpecialNeeds());
        customersInfo.setSalarySkills(form.getSalarySkills());// 添加服务技能 Baron

        customersInfo.setWageRequirements(form.getWageRequirements());
        customersInfo.setExperienceRequirements(form.getExperienceRequirements());

        // 保存订单客户信息
        orderCustomersInfoDao.insertSelective(customersInfo);

        return JsonResult.success(null);

    }

    /**
     * 查询订单阿姨详情
     */
    @Override
    public JsonResult selectStaffInfoByOrderNo(String orderNo) {
        try {
            Map<String, Object> map = orderDao.selectStaffInfoByOrderNo(orderNo);
            if (map == null) {// 订单没有阿姨
                return JsonResult.success(null);
            }
            String str = "";
            str = (String) map.get("zodiac");
            if (!StringUtils.isBlank(str)) {
                map.put("zodiac", DictionarysCacheUtils.getZodiacName(str));
            }
            str = (String) map.get("nativePlace");
            if (!StringUtils.isBlank(str)) {
                map.put("nativePlace", DictionarysCacheUtils.getNativePlaceName(str));
            }
            str = (String) map.get("constellation");
            if (!StringUtils.isBlank(str)) {
                map.put("constellation", DictionarysCacheUtils.getConstellationName(str));
            }
            str = (String) map.get("educarion");
            if (!StringUtils.isBlank(str)) {
                map.put("educarion", DictionarysCacheUtils.getEducationName(str));
            }
            str = (String) map.get("experience");
            if (!StringUtils.isBlank(str)) {
                map.put("experienceValue", DictionarysCacheUtils.getExperienceName(str));
            }
            str = (String) map.get("unit");
            if (!StringUtils.isBlank(str)) {
                map.put("unitValue", DictionarysCacheUtils.getServicePriceUnit(str));
            }
            str = (String) map.get("serviceType");
            if (!StringUtils.isBlank(str)) {
                StringBuffer sb = new StringBuffer();
                if (StringUtils.isNotBlank(str)) {
                    for (String st : str.split(",")) {
                        sb.append(DictionarysCacheUtils.getServiceTypeName(st)).append("、");
                    }
                    sb.deleteCharAt(sb.length() - 1);
                }
                map.put("serviceTypeValue", sb.toString());
            }
            str = (String) map.get("headImage");
            if (!StringUtils.isBlank(str) && !str.startsWith("http") && !str.startsWith("data")) {
                map.put("headImage", Constants.IMAGE_URL + str);
            } else {
                map.put("headImage", str);
            }
            long s = System.currentTimeMillis();
            map.put("matchValue", getMatchValue(orderNo, (Integer) map.get("staffId")));
            logger.info("=====" + (System.currentTimeMillis() - s));
            return JsonResult.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ErrorMsg.ORDERS_QUERY_ERROR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
    }

    /**
     * 本地订单支付定金
     */
    @Override
    @Transactional
    public JsonResult payDeposit(String orderNo) {
        try {
            Map<String, Object> map = orderDao.selectByOrderNo(orderNo);
            String str = "";
            str = (String) map.get("orderSource");
            // 判断是否为本地订单
            if (str != null && !str.equals("01")) {
                return JsonResult.failure(ResultCode.Order.ORDER_IS_NOT_LOCAL);
            }
            // 更新本地订单
            Orders order = new Orders();
            order.setIdepositOver("01");
            order.setDepositDate(new Date());
            order.setOrderStatus("02");
            order.setOrderNo(orderNo);
            orderDao.updateByPrimaryKeySelective(order);

            //更新职位对应状态
            TenantsJobs job = tenantsJobsMapper.getByOrderNo(orderNo);
            if (job == null) {
                return JsonResult.success(null);
            }
            TenantsJobs updateJob = new TenantsJobs();
            updateJob.setId(job.getId());
            updateJob.setOrderNo(orderNo);
            updateJob.setOrderStatus(order.getOrderStatus());
            tenantsJobsMapper.update(updateJob);

            return JsonResult.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ErrorMsg.ORDERS_QUERY_ERROR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
    }

    /**
     * 本地订单支付尾款
     */
    @Override
    @Transactional
    public JsonResult payBalance(String orderNo) {
        try {
            Map<String, Object> map = orderDao.selectByOrderNo(orderNo);
            String str = "";
            str = (String) map.get("orderSource");
            // 判断是否为本地订单
            if (str != null && !str.equals("01")) {
                return JsonResult.failure(ResultCode.Order.ORDER_IS_NOT_LOCAL);
            }
            // 更新本地订单
            Orders order = new Orders();
            order.setBalanceOver("01");
            order.setBalanceDate(new Date());
            order.setOrderStatus("04");
            order.setOrderNo(orderNo);
            orderDao.updateByPrimaryKeySelective(order);

            //更新职位对应状态
            TenantsJobs job = tenantsJobsMapper.getByOrderNo(orderNo);
            if (job == null) {
                return JsonResult.success(null);
            }
            TenantsJobs updateJob = new TenantsJobs();
            updateJob.setOrderNo(orderNo);
            updateJob.setId(job.getId());
            updateJob.setOrderStatus(order.getOrderStatus());
            updateJob.setFinishedTime(new Date());
            updateJob.setStatus(JobConst.JobStatus.OFF.getCode());
            tenantsJobsMapper.update(updateJob);
            //解冻保证金
            if (job.getDeposit() != null && job.getDeposit().compareTo(new BigDecimal(0)) > 0) {
                commonService.thawAmount(job.getTenantId(), job.getDeposit(), job.getOrderNo(), "解冻保证金[招聘完成]");
            }

            /**
             * 应聘信息更新
             * 更新应聘表状态
             * 解冻保证金
             */
            Object obj = map.get("staffId");
            if (obj == null) {
                return JsonResult.success(null);
            }
            Integer staffId = Integer.valueOf(obj.toString());
            TenantJobResumeForm pm = new TenantJobResumeForm();
            pm.setJobId(job.getId());
            List<TenantsJobResume> resumes = tenantsJobResumesMapper.getMyResumesBoxList(pm);
            if (resumes == null || resumes.size() == 0) {
                return JsonResult.success(null);
            }
            for (TenantsJobResume resume : resumes) {
                if (resume.getStatus().equals(JobConst.ResumeStatus.REJECTS.getCode())) {
                    continue;
                }
                TenantsJobResume update = new TenantsJobResume();
                String remarks = null;
                if (resume.getResumeTenantStaffId().equals(staffId) && Integer.valueOf(0).equals((Integer) map.get("isLocalStaff"))) {
                    //简历已完成
                    update.setJobId(resume.getJobId());
                    update.setId(resume.getId());
                    update.setStatus(JobConst.ResumeStatus.FINISHED.getCode());
                    remarks = "保证金解冻[招聘支付尾款完成]";
                } else {
                    //其他简历已取消
                    update.setJobId(resume.getJobId());
                    update.setId(resume.getId());
                    update.setStatus(JobConst.ResumeStatus.CANCELED.getCode());
                    update.setRemarks("用户已选择其他阿姨");
                    remarks = "保证金解冻[用户已选择其他阿姨]";
                }
                tenantsJobResumesMapper.update(update);
                //解冻保证金
                if (resume.getDeposit() != null && resume.getDeposit().compareTo(new BigDecimal(0)) > 0) {
                    commonService.thawAmount(resume.getResumeTenantId(), resume.getDeposit(), resume.getOrderNo(), remarks);
                }
            }
            return JsonResult.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ErrorMsg.ORDERS_QUERY_ERROR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
    }

    /**
     * 订单阿姨列表获取
     */
    @Override
    public JsonResult getStaffsInfo(StaffListForm form, int pageNumber, int pageSize) {
        try {
            // 获取该笔订单对应的服务工种
            Orders orders = orderDao.selectByPrimaryKey(form.getOrderNo());
            if (orders == null) {
                Page<StaffQueryJson> page = new Page<StaffQueryJson>();
                page.setPage(pageNumber);
                page.setRowNum(pageSize);
                return JsonResult.success(page);
            }
            String serviceItemCode = orders.getServiceItemCode();// 服务工种
            // 获取总条数
            Map<Object, Object> map = StaffsOpt.buildQueryMap(form.getTenantId());
            map.put("serviceItemCode", serviceItemCode);
            map.put("tenantId", form.getTenantId());
            map.put("staffName", StringUtils.strLike(form.getStaffName()));
            map.put("experience", form.getExperience());
            map.put("education", form.getEducation());
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
            map.put("staffId", orders.getStaffId());
        	map.put("staffNo", StringUtils.strLike(form.getStaffNo()));
            //@zsq 服务区域变为指定区域和不限
            map.put("serviceArea", form.getServiceArea());
            Integer totalCount = tenantsStaffsInfoDao.getOrderStaffsCount(map);

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
                List<Map<Object, Object>> list = tenantsStaffsInfoDao.selectOrderStaffInfoList(map);
                List<StaffQueryJson> resultList = Lists.newArrayList();
                StaffQueryJson staffQueryBean = null;
                for (Map<Object, Object> result : list) {

                    staffQueryBean = new StaffQueryJson();
                    TenantsStaffsInfoEntity entity = tenantsStaffsInfoDao.selectByPrimaryKey((Integer) result.get("staffId"));
                    staffQueryBean.setStaffId(entity.getStaffId());// 主键
                    String imageUrl = entity.getHeadImage();
                    if (StringUtils.isNotBlank(imageUrl)
                            && !imageUrl.startsWith("http")
                            && !imageUrl.startsWith("data")) {
                        staffQueryBean.setHeadImage(Constants.IMAGE_URL + entity.getHeadImage()); // 头像
                    } else {
                        staffQueryBean.setHeadImage(entity.getHeadImage()); // 头像
                    }
                    staffQueryBean.setStaffName(entity.getStaffName());// 姓名
                    staffQueryBean.setAge(entity.getAge());// 年龄
                    staffQueryBean.setZodiac(DictionarysCacheUtils.getZodiacName(entity.getZodiac())); // 属相
                    staffQueryBean.setNativePlace(DictionarysCacheUtils.getNativePlaceName(entity.getNativePlace()));// 籍贯
                    staffQueryBean.setEducation(DictionarysCacheUtils.getEducationName(entity.getEducarion()));// 学历
                    staffQueryBean.setSpecialty(entity.getSpecialty());// 专业
                    staffQueryBean.setConstellation(entity.getConstellation());
                    if ("01".equals(entity.getShareStatus())) {
                        staffQueryBean.setShareStatus("分享中");
                    } else {
                        staffQueryBean.setShareStatus("");
                    }
                    staffQueryBean.setWorkStatus(staffsService.staffIsWorkNow(entity.getStaffId()));// 服务状态
                    staffQueryBean.setStaffNo(entity.getStaffNo());
                    
                    // 查询阿姨求职信息
                    TenantsStaffJobInfo tenantsStaffJobInfo = getStaffJobInfoById((Integer) result.get("staffId"));
                    if (StringUtils.isNotBlank(tenantsStaffJobInfo.getServiceProvice())) {
                        staffQueryBean.setServiceProvice(
                                DictionarysCacheUtils.getProviceName(tenantsStaffJobInfo.getServiceProvice()));
                    }
                    if (StringUtils.isNotBlank(tenantsStaffJobInfo.getServiceCity())) {
                        staffQueryBean
                                .setServiceCity(DictionarysCacheUtils.getCityName(tenantsStaffJobInfo.getServiceCity()));
                    }
                    if (StringUtils.isNotBlank(tenantsStaffJobInfo.getServiceCounty())) {
                        staffQueryBean.setServiceCounty(
                                DictionarysCacheUtils.getCountyName(tenantsStaffJobInfo.getServiceCounty()));
                    }
                    if (StringUtils.isNotBlank(serviceItemCode)) {
                        staffQueryBean.setServiceType(DictionarysCacheUtils.getServiceTypeName(serviceItemCode));// 服务工种(只有一个)
                    }

                    // 查询阿姨服务工种对应的从业经验
                    TenantsStaffSerItemsKey staffSerItemsKey = new TenantsStaffSerItemsKey();// 连合主键
                    staffSerItemsKey.setStaffId((Integer) result.get("staffId"));
                    staffSerItemsKey.setTenantId(form.getTenantId());
                    staffSerItemsKey.setServiceItemCode(serviceItemCode);
                    TenantsStaffSerItemsEntity staffServiceItems = tenantsStaffSerItemsDao.getStaffServiceItemsByKey(staffSerItemsKey);
                    String experience = tenantsStaffJobInfo.getExperience();// 从业经验
                    String serviceNature = staffServiceItems.getServiceNature();// 服务类型
                    BigDecimal servicePrice = tenantsStaffJobInfo.getPrice();// 服务价格
                    Integer stafffSerItemId = staffServiceItems.getId();//阿姨工种Id
                    if (StringUtils.isNotBlank(experience)) {
                        staffQueryBean.setExperience(DictionarysCacheUtils.getExperienceName(experience));
                        staffQueryBean.setExperienceValue(DictionariesUtil.getExperienceValue(experience));
                    }
                    if (StringUtils.isNotBlank(serviceItemCode)) {
                        staffQueryBean.setServiceNature(
                                DictionarysCacheUtils.getServiceNatureStr(serviceItemCode, serviceNature));
                    }
                    staffQueryBean.setServicePrice(servicePrice);
                    staffQueryBean.setStafffSerItemId(stafffSerItemId);
                    //@zsq  服务区域修改 因服务区域变为不限和指定区域
                    if (tenantsStaffJobInfo.getServiceArea() != null && !tenantsStaffJobInfo.getServiceArea().equals("")) {
                        staffQueryBean.setServiceArea(DictionarysCacheUtils.getProviceName(tenantsStaffJobInfo.getServiceArea()));
                    } else {
                        staffQueryBean.setServiceArea("");
                    }

                    // 匹配度处理
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

    /**
     * 创建订单阿姨列表获取
     */
    @SuppressWarnings("unused")
    @Override
    public JsonResult getCreateStaffsInfo(StaffListForm form, int pageNumber, int pageSize) {
        try {

            // 获取总条数
            Map<Object, Object> map = StaffsOpt.buildQueryMap(form.getTenantId());
            map.put("serviceItemCode", form.getServiceItemCode());
            map.put("tenantId", form.getTenantId());
            map.put("staffName", StringUtils.strLike(form.getStaffName()));
            map.put("workStatus", form.getWorkStatus());
            map.put("experience", form.getExperience());
            map.put("education", form.getEducation());
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
            map.put("staffId", form.getStaffId());
            //@zsq 服务区域变为指定区域和不限
            map.put("serviceArea", form.getServiceArea());
            map.put("staffNo", StringUtils.strLike(form.getStaffNo()));
            Integer totalCount = tenantsStaffsInfoDao.getOrderStaffsCount(map);

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
                List<Map<Object, Object>> list = tenantsStaffsInfoDao.selectOrderStaffInfoList(map);
                List<StaffQueryJson> resultList = Lists.newArrayList();
                StaffQueryJson staffQueryBean = null;
                for (Map<Object, Object> result : list) {
                    staffQueryBean = new StaffQueryJson();
                    TenantsStaffsInfoEntity entity = tenantsStaffsInfoDao
                            .selectByPrimaryKey((Integer) result.get("staffId"));
                    staffQueryBean.setStaffId(entity.getStaffId());// 主键
                    if (StringUtils.isNotBlank(entity.getHeadImage()) && !entity.getHeadImage().startsWith("http") && !entity.getHeadImage().startsWith("data")) {
                        staffQueryBean.setHeadImage(Constants.IMAGE_URL + entity.getHeadImage()); // 头像
                    } else {
                        staffQueryBean.setHeadImage(entity.getHeadImage()); // 头像
                    }
                    staffQueryBean.setStaffName(entity.getStaffName());// 姓名
                    staffQueryBean.setAge(entity.getAge());// 年龄
                    staffQueryBean.setZodiac(DictionarysCacheUtils.getZodiacName(entity.getZodiac())); // 属相
                    staffQueryBean.setNativePlace(DictionarysCacheUtils.getNativePlaceName(entity.getNativePlace()));// 籍贯
                    staffQueryBean.setEducation(DictionarysCacheUtils.getEducationName(entity.getEducarion()));// 学历
                    staffQueryBean.setSpecialty(entity.getSpecialty());// 专业
                    staffQueryBean.setWorkStatus(staffsService.staffIsWorkNow(entity.getStaffId()));// 服务状态

                    // @xiehui查询服务工种对应的服务
                    Map<String, Object> mapPrice = new HashMap<>();
                    mapPrice.put("staffId", entity.getStaffId());
                    mapPrice.put("serviceItemCode", form.getServiceItemCode());

                    // 分享状态
                    if (StringUtils.isNotEmpty(entity.getShareStatus())) {
                        if ("02".equals(entity.getShareStatus())) {
                            staffQueryBean.setShareStatus("");
                        } else if ("01".equals(entity.getShareStatus())) {
                            staffQueryBean.setShareStatus("分享中");
                        } else if (null == entity.getShareStatus() || "".equals(entity.getShareStatus())) {
                            staffQueryBean.setShareStatus("");
                        }
                    }

                    // 查询阿姨求职信息
                    TenantsStaffJobInfo tenantsStaffJobInfo = getStaffJobInfoById((Integer) result.get("staffId"));
                    staffQueryBean.setServicePrice(tenantsStaffJobInfo.getPrice());
                    staffQueryBean.setUnit(tenantsStaffJobInfo.getUnit());
                    staffQueryBean.setUnitValue(DictionarysCacheUtils.getServicePriceUnit(tenantsStaffJobInfo.getUnit()));
                    if (StringUtils.isNotBlank(tenantsStaffJobInfo.getServiceProvice())) {
                        staffQueryBean.setServiceProvice(
                                DictionarysCacheUtils.getProviceName(tenantsStaffJobInfo.getServiceProvice()));
                    }
                    if (StringUtils.isNotBlank(tenantsStaffJobInfo.getServiceCity())) {
                        staffQueryBean
                                .setServiceCity(DictionarysCacheUtils.getCityName(tenantsStaffJobInfo.getServiceCity()));
                    }
                    if (StringUtils.isNotBlank(tenantsStaffJobInfo.getServiceCounty())) {
                        staffQueryBean.setServiceCounty(
                                DictionarysCacheUtils.getCountyName(tenantsStaffJobInfo.getServiceCounty()));
                    }

                    TenantsStaffSerItemsEntity tenantsStaffSerItemsEntity = new TenantsStaffSerItemsEntity();
                    tenantsStaffSerItemsEntity.setStaffId((Integer) result.get("staffId"));
                    tenantsStaffSerItemsEntity.setTenantId(form.getTenantId());
                    //@zsq  服务区域修改 因服务区域变为不限和指定区域
                    if (tenantsStaffJobInfo.getServiceArea() != null && !tenantsStaffJobInfo.getServiceArea().equals("")) {
                        staffQueryBean.setServiceArea(DictionarysCacheUtils.getProviceName(tenantsStaffJobInfo.getServiceArea()));
                    } else {
                        staffQueryBean.setServiceArea("");
                    }
                    String experience = tenantsStaffJobInfo.getExperience();// 从业经验@xiehui
                    staffQueryBean.setExperience(DictionarysCacheUtils.getExperienceName(experience));
                    // 查询阿姨服务工种信息
                    if (form.getServiceItemCode() == null) {
                        List<TenantsStaffSerItemsEntity> staffServiceItems = tenantsStaffSerItemsDao
                                .getStaffServiceItems(tenantsStaffSerItemsEntity);
                        String items = "";
                        for (TenantsStaffSerItemsEntity tenantsStaffSerItem : staffServiceItems) {
                            if (null == tenantsStaffSerItem.getServiceItemCode()
                                    || "".equals(tenantsStaffSerItem.getServiceItemCode()))
                                continue;
                            items += DictionarysCacheUtils.getServiceTypeName(tenantsStaffSerItem.getServiceItemCode())
                                    + ",";

                        }
                        staffQueryBean.setServiceItems(items.substring(0, items.length() - 1));

                    } else {
                        tenantsStaffSerItemsEntity.setServiceItemCode(form.getServiceItemCode());
                        TenantsStaffSerItemsEntity staffServiceItem = tenantsStaffSerItemsDao
                                .getStaffServiceItemsByKey(tenantsStaffSerItemsEntity);
                        staffQueryBean
                                .setServiceItems(DictionarysCacheUtils.getServiceTypeName(form.getServiceItemCode()));
                        // add 添加工种名称 Baron
                        staffQueryBean
                                .setServiceType(DictionarysCacheUtils.getServiceTypeName(form.getServiceItemCode()));

                    }
                    resultList.add(staffQueryBean);
                }
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

    /**
     * 蜂享阿姨列表获取
     */
    @SuppressWarnings("unused")
    @Override
    public JsonResult getShareStaffsInfo(StaffListForm form, int pageNumber, int pageSize) {
        try {

            // 获取该笔订单对应的服务工种
            Orders orders = orderDao.selectByPrimaryKey(form.getOrderNo());
            String serviceItemCode = orders.getServiceItemCode();// 服务工种

            // 获取总条数
            Map<Object, Object> map = StaffsOpt.buildQueryMap(form.getTenantId());
            map.put("orderNo", form.getOrderNo());
            // map.put("serviceItemCode", serviceItemCode);
            map.put("tenantId", form.getTenantId());
            // map.put("status", "02");
            map.put("staffName", form.getStaffName());
            map.put("workStatus", form.getWorkStatus());
            map.put("experience", form.getExperience());
            map.put("education", form.getEducation());
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
            // map.put("staffId", orders.getStaffId());
            //@zsq 服务区域变为指定区域和不限
            map.put("serviceArea", form.getServiceArea());
            Integer totalCount = tenantsStaffsInfoDao.getShareStaffsCount(map);

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
                List<Map<Object, Object>> list = tenantsStaffsInfoDao.selectShareStaffInfoList(map);
                List<StaffQueryJson> resultList = Lists.newArrayList();
                StaffQueryJson staffQueryBean = null;
                for (Map<Object, Object> result : list) {

                    staffQueryBean = new StaffQueryJson();
                    TenantsStaffsInfoEntity entity = tenantsStaffsInfoDao
                            .selectByPrimaryKey((Integer) result.get("staffId"));
                    staffQueryBean.setStaffId(entity.getStaffId());// 主键
                    String imageUrl = entity.getHeadImage();
                    if (StringUtils.isNotBlank(imageUrl)
                            && !imageUrl.startsWith("http")
                            && !imageUrl.startsWith("data")) {
                        staffQueryBean.setHeadImage(Constants.IMAGE_URL + entity.getHeadImage()); // 头像
                    } else {
                        staffQueryBean.setHeadImage(entity.getHeadImage()); // 头像
                    }
                    staffQueryBean.setStaffName(entity.getStaffName());// 姓名
                    staffQueryBean.setAge(entity.getAge());// 年龄
                    staffQueryBean.setZodiac(DictionarysCacheUtils.getZodiacName(entity.getZodiac())); // 属相
                    staffQueryBean.setNativePlace(DictionarysCacheUtils.getNativePlaceName(entity.getNativePlace()));// 籍贯
                    staffQueryBean.setEducation(DictionarysCacheUtils.getEducationName(entity.getEducarion()));// 学历
                    staffQueryBean.setSpecialty(entity.getSpecialty());// 专业
                    staffQueryBean.setWorkStatus(staffsService.staffIsWorkNow(entity.getStaffId()));// 服务状态

                    // 查询阿姨求职信息
                    TenantsStaffJobInfo tenantsStaffJobInfo = getStaffJobInfoById((Integer) result.get("staffId"));
                    staffQueryBean.setServiceProvice(
                            DictionarysCacheUtils.getProviceName(tenantsStaffJobInfo.getServiceProvice()));
                    staffQueryBean
                            .setServiceCity(DictionarysCacheUtils.getCityName(tenantsStaffJobInfo.getServiceCity()));
                    staffQueryBean.setServiceCounty(
                            DictionarysCacheUtils.getCountyName(tenantsStaffJobInfo.getServiceCounty()));
                    staffQueryBean.setServiceType(DictionarysCacheUtils.getServiceTypeName(serviceItemCode));// 服务工种(只有一个)
                    // 查询阿姨服务工种对应的从业经验
                    TenantsStaffSerItemsKey staffSerItemsKey = new TenantsStaffSerItemsKey();// 连合主键
                    staffSerItemsKey.setStaffId((Integer) result.get("staffId"));
                    staffSerItemsKey.setTenantId(entity.getTenantId());
                    staffSerItemsKey.setServiceItemCode(serviceItemCode);
                    TenantsStaffSerItemsEntity staffServiceItems = tenantsStaffSerItemsDao
                            .getStaffServiceItemsByKey(staffSerItemsKey);
                    String experience = tenantsStaffJobInfo.getExperience();// 从业经验
                    String serviceNature = staffServiceItems.getServiceNature();// 服务类型
                    staffQueryBean.setExperience(DictionarysCacheUtils.getExperienceName(experience));
                    staffQueryBean.setServiceNature(
                            DictionarysCacheUtils.getServiceNatureStr(serviceItemCode, serviceNature));

                    //@zsq  服务区域修改 因服务区域变为不限和指定区域
                    if (tenantsStaffJobInfo.getServiceArea() != null && !tenantsStaffJobInfo.getServiceArea().equals("")) {
                        staffQueryBean.setServiceArea(DictionarysCacheUtils.getProviceName(tenantsStaffJobInfo.getServiceArea()));
                    } else {
                        staffQueryBean.setServiceArea("");
                    }

                    // 匹配度处理
                    MatchingModel match = getMatchingModel(entity, tenantsStaffJobInfo, staffServiceItems);
                    OrderMatchBean order = getOrderMatchBean(serviceItemCode, form.getOrderNo());
                    double matchRate = SkillMacthUtils.getInstance().getMatchRate(serviceItemCode, match, order);
                    BigDecimal b = new BigDecimal(matchRate);
                    BigDecimal b3 = new BigDecimal(100);
                    int intValue = b.multiply(b3).intValue();
                    staffQueryBean.setMatchingDegree(intValue + "");
                    staffQueryBean.setShareStatus("分享中");
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

    /**
     * 订单结单后更换阿姨
     */
    @Override
    public JsonResult orderChangeStaff(String orderNo, Integer staffId, String tenantRemark, UserBean userBean) {
        try {
            Orders order = orderDao.selectByPrimaryKey(orderNo);
            // 查询更换阿姨处理记录
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("orderNo", orderNo);
            map.put("dealStatus", "01");
            OrderChangeStaffInfo orderChangeStaffInfo = orderChangeStaffInfoDao.selectByOrderNo(map);
            // 保存更换阿姨的id
            orderChangeStaffInfo.setChangeStaff(order.getStaffId());
            // 租户处理信息
            orderChangeStaffInfo.setTenantRemark(tenantRemark);
            // 处理状态更改为已处理
            orderChangeStaffInfo.setDealStatus("02");
            orderChangeStaffInfo.setModifyTime(new Date());
            // 面试通过
            order.setIsInterview("02");
            // 面试通过时间
            order.setPassViewDate(new Date());
            // 订单更换阿姨
            order.setStaffId(staffId);
            // 订单状态已处理
            order.setOrderStatus("04");
            //order.setUserId(userBean.getUserId());
            //order.setUserType(userBean.getUserType());
            //order.setUserName(userBean.getUserName());
            order.setModifyTime(new Date());
            order.setModifyAccount(userBean.getLoginAccount());
            // 更新表
            orderChangeStaffInfoDao.updateByPrimaryKey(orderChangeStaffInfo);
            orderDao.updateByPrimaryKeySelective(order);
            return JsonResult.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ErrorMsg.STAFF_QUERY_ERR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
    }

    /**
     * 匹配度
     */
    @Override
    public Integer getMatchValue(String orderNo, Integer staffId) {
        if (StringUtils.isBlank(orderNo) || staffId == null || staffId == 0) {
            return 0;
        }
        Orders orders = orderDao.selectByPrimaryKey(orderNo);
        //【家政员】信息
        TenantsStaffsInfoEntity entity = tenantsStaffsInfoDao.selectByPrimaryKey(staffId);
        //【家政员】的求职信息
        TenantsStaffJobInfo tenantsStaffJobInfo = getStaffJobInfoById(entity.getStaffId());

        //【订单】工种信息
        TenantsStaffSerItemsEntity staffServiceItems = null;
        if (orders.getStafffSerItemId() != null) {
            //订单的工作工种信息
            staffServiceItems = tenantsStaffSerItemsDao.getStaffServiceItemsByIds(orders.getStafffSerItemId());
        }
        /**
         * 匹配度处理
         * 1. match: 家政员的求职信息匹配模型
         * 2. order: 订单招聘信息匹配模型
         */
        MatchingModel match = getMatchingModel(entity, tenantsStaffJobInfo, staffServiceItems);
        OrderMatchBean order = getOrderMatchBean(orders.getServiceItemCode(), orderNo);
        double matchRate = SkillMacthUtils.getInstance().getMatchRate(orders.getServiceItemCode(), match, order);
        logger.info(String.format("staff match model [%s]", JsonUtils.toJson(match)));
        logger.info(String.format("order match model [%s]", JsonUtils.toJson(order)));
        logger.info(String.format("match rate [%s]", matchRate));
        BigDecimal b = new BigDecimal(matchRate);
        BigDecimal b3 = new BigDecimal(100);
        BigDecimal intType = b.multiply(b3).setScale(2, RoundingMode.HALF_EVEN);
        int matching = intType.intValue();
        return matching;
    }

    private TenantsStaffJobInfo getStaffJobInfoById(Integer staffId) {
        return tenantsStaffJobInfoDao.selectByPrimaryKey(staffId);
    }

    private MatchingModel getMatchingModel(TenantsStaffsInfoEntity entity, TenantsStaffJobInfo tenantsStaffJobInfo,
                                           TenantsStaffSerItemsEntity staffServiceItems) {
        MatchingModel match = new MatchingModel();
        Set<String> certs = Sets.newHashSet();
        // 查询阿姨证书@xiehui
        List<TenantsStaffCertsInfoEntity> tenantsStaffCertsInfos = tenantsStaffCertsInfoDao.getSatffAllCerts(entity.getStaffId());
        if (tenantsStaffCertsInfos != null && tenantsStaffCertsInfos.size() > 0) {
            Set<String> cert1 = Sets.newHashSet();
            Set<String> cert2 = Sets.newHashSet();
            Set<String> cert3 = Sets.newHashSet();
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
            Set<String> certMax1 = new HashSet<String>();
            if (cert1 != null && cert1.size() > 0) {
                String number1 = Collections.max(cert1);
                certMax1.add(number1);
            } else {
                certMax1.add(null);
            }
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
        }
        match.setIsHasTrade("1"); // 是否有工种
        if (staffServiceItems != null && staffServiceItems.getServiceNature() != null) {
            match.setSeviceNature(staffServiceItems.getServiceNature());// 服务类型
        } else {
            match.setSeviceNature(null);// 服务类型
        }
        match.setPrice((null == tenantsStaffJobInfo || tenantsStaffJobInfo.getPrice() == null) ? "" : (tenantsStaffJobInfo.getPrice() + ""));
        match.setExperience((null == tenantsStaffJobInfo || tenantsStaffJobInfo.getExperience() == null) ? "" : (tenantsStaffJobInfo.getExperience()));
        match.setNativePlace(entity.getNativePlace() == null ? "" : (entity.getNativePlace()));
        match.setZodiac(entity.getZodiac() == null ? "" : (entity.getZodiac()));
        match.setAge(entity.getAge() == null ? "" : (entity.getAge() + ""));
        match.setSex(entity.getSex() == null ? "" : (entity.getSex()));
        match.setEducation(entity.getEducarion() == null ? "" : (entity.getEducarion()));
        match.setLanguage(tenantsStaffJobInfo.getLanguageFeature() == null ? "" : (tenantsStaffJobInfo.getLanguageFeature()));
        match.setCharacter(tenantsStaffJobInfo.getCharacerFeature() == null ? "" : (tenantsStaffJobInfo.getCharacerFeature()));
        match.setCooking(tenantsStaffJobInfo.getCookingFeature() == null ? "" : (tenantsStaffJobInfo.getCookingFeature()));
        if (staffServiceItems != null) {
            match.setIsHasOlder(tenantsStaffJobInfo.getElderlySupport() == null ? "" : (tenantsStaffJobInfo.getElderlySupport()));
            match.setIsHasPet(tenantsStaffJobInfo.getPetFeeding() == null ? "" : (tenantsStaffJobInfo.getPetFeeding()));
        }
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
        OrderCustomersInfo orderCustomersInfo = orderCustomersInfoDao.selectByPrimaryKey(orderNo);
        if (orderCustomersInfo != null) {
            OrderMatchBean order = new OrderMatchBean();
            order.setServiceType(serviceItemCode);
            order.setServiceNature(null);
            // xiehui
            order.setPrice(String.valueOf(orderCustomersInfo.getSalary()));
            order.setExperience(orderCustomersInfo.getExperienceRequirements());
            order.setNativePlace(null);
            order.setZodiac(null);
            order.setAge(orderCustomersInfo.getWageRequirements());
            order.setSex(null);
            order.setEducation(null);
            order.setLanguage(orderCustomersInfo.getLanguageRequirements());
            order.setCharacter(orderCustomersInfo.getPersonalityRequirements());
            order.setCooking(orderCustomersInfo.getCookingReqirements());
            order.setIsHasPet(orderCustomersInfo.getPetRaising());
            order.setIsHasOlder(null);

            if (null != orderCustomersInfo && null != orderCustomersInfo.getSalarySkills() && !"".equals(orderCustomersInfo.getSalarySkills())) {
                String[] stem = orderCustomersInfo.getSalarySkills().split(",");
                //服务内容
                if (stem != null && stem.length > 0 && !"".equals(stem[0])) {
                    Map<Integer, String> map = new HashMap<>();
                    for (int i = 0; i < stem.length; i++) {
                        map.put(i, stem[i]);
                    }
                    order.setServiceContents(map);
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

    /**
     * 订单管理保存或更新客户信息
     *
     * @param tenantId
     * @return
     */
    @Override
    public JsonResult saveOrUpdateCustomer(OrderCustomerInfoForm form, Integer tenantId) {
        OrderCustomersInfo orderCustomersInfo = new OrderCustomersInfo();
        orderCustomersInfo.setOrderNo(form.getOrderNo());
        orderCustomersInfo.setMemberName(form.getMemberName());
        orderCustomersInfo.setMemberSex(form.getSex());
        orderCustomersInfo.setMemberMobile(form.getMemberMobile());
        orderCustomersInfo.setServiceProvice(form.getServiceProvice());
        orderCustomersInfo.setServiceCity(form.getServiceCity());
        orderCustomersInfo.setServiceCounty(form.getServiceCounty());
        orderCustomersInfo.setServiceAddress(form.getServiceAddress());
        orderCustomersInfo.setFamilyCount(form.getFamilyCount());
        orderCustomersInfo.setHouseType(form.getHouseType());
        if (null != form.getHouseArea() && !"".equals(form.getHouseArea())) {
            orderCustomersInfo.setHouseArea(BigDecimal.valueOf(Double.parseDouble(form.getHouseArea())));
        }
        orderCustomersInfo.setChildrenCount(form.getChildrenCount());
        orderCustomersInfo.setChildrenAgeRange(form.getChildrenAgeRange());
        orderCustomersInfo.setOlderCount(form.getOlderCount());
        orderCustomersInfo.setOlderAgeRange(form.getOlderAgeRange());
        orderCustomersInfo.setSelfCares(form.getSelfCares());
        if (orderCustomersInfoDao.selectByPrimaryKey(form.getOrderNo()) == null) {
            orderCustomersInfoDao.insertSelective(orderCustomersInfo);
            return JsonResult.success(null);
        } else {
            orderCustomersInfoDao.updateByCustomerInfo(orderCustomersInfo);
            return JsonResult.success(null);
        }
    }

    /**
     * 订单管理保存或更新服务信息
     *
     * @return
     */
    @Override
    public JsonResult saveOrUpdateService(OrderServiceInfoForm form) {
        OrderCustomersInfo orderCustomersInfo = new OrderCustomersInfo();
        Orders order = orderDao.selectByPrimaryKey(form.getOrderNo());
        order.setServiceItemCode(form.getServiceItemCode());
        order.setStafffSerItemId(order.getStafffSerItemId());
        orderCustomersInfo.setOrderNo(form.getOrderNo());
        orderCustomersInfo.setServiceItemCode(form.getServiceItemCode());
        orderCustomersInfo.setServiceType(form.getServiceType());
        orderCustomersInfo.setServiceStart(DateUtils.parseDate(form.getServiceStart()));
        orderCustomersInfo.setServiceEnd(DateUtils.parseDate(form.getServiceEnd()));
        orderCustomersInfo.setIsBabyBorn(form.getIsBabyBorn());
        orderCustomersInfo.setSalary(form.getSalary());
        orderCustomersInfo.setSalaryType(form.getSalaryType());
        orderCustomersInfo.setPetRaising(form.getPetRaising());
        orderCustomersInfo.setSalarySkills(StringUtils.strAppend(form.getSkills()));
        orderCustomersInfo.setExpectedBirth(DateUtils.parseDate(form.getExpectedBirth()));
        orderCustomersInfo.setLanguageRequirements(StringUtils.strAppend(form.getLanguageRequirements()));
        orderCustomersInfo.setCookingReqirements(StringUtils.strAppend(form.getCookingRequirements()));
        orderCustomersInfo.setPersonalityRequirements(StringUtils.strAppend(form.getPersonalityRequirements()));
        orderCustomersInfo.setSpecialNeeds(form.getSpecialNeeds());
        orderCustomersInfo.setWageRequirements(form.getWageRequirements());
        orderCustomersInfo.setExperienceRequirements(form.getExperienceRequirements());
        orderCustomersInfo.setSelfCares(form.getSelfCares());
        List<TenantsStaffSerItemsEntity> staffSerItems = tenantsStaffSerItemsDao.getServiceItemsByStaffId(order.getStaffId());


        List<String> list = new ArrayList<String>();
        for (TenantsStaffSerItemsEntity staffSerItem : staffSerItems) {
            list.add(staffSerItem.getServiceItemCode());
        }

        orderDao.updateByPrimaryKeySelective(order);
        orderCustomersInfoDao.updateByPrimaryKeySelective(orderCustomersInfo);
        return JsonResult.success(null);
    }

    /**
     * 本地订单-定金支付
     *
     * @param orderNo author Baron date 2017-04-27
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult depositPayment(String orderNo, String type, String inOutObject) {
        TenantsJobs updatejob = new TenantsJobs();
        Orders order = new Orders();
        order.setOrderNo(orderNo);
        //获取客户信息
        OrderCustomersInfo orderCustomersInfo = orderCustomersInfoDao.selectByPrimaryKey(orderNo);
        if (type.equals("1")) {// 支付定金
            order.setOrderStatus("02");
            order.setIdepositOver("02");
            order.setDepositDate(new Date());
            // 插入财务流水
            TenantsFinanceRecords tenantsFinanceRecords = new TenantsFinanceRecords();
            String inOutNo = commonService.createOrderNo("05");
            Orders ordersDao = orderDao.selectByPrimaryKey(orderNo);
            tenantsFinanceRecords.setInOutNo(inOutNo);
            tenantsFinanceRecords.setTenantId(ordersDao.getTenantId());
            tenantsFinanceRecords.setPayType("01");
            tenantsFinanceRecords.setInOutType("01");
            tenantsFinanceRecords.setInOutObject(orderCustomersInfo.getMemberName());//收支对象为客户
            tenantsFinanceRecords.setInOutAmount(ordersDao.getOrderDeposit());
            tenantsFinanceRecords.setTransType("02");
            tenantsFinanceRecords.setRemarks("本地订单，定金支付");
            tenantsFinanceRecords.setAddTime(new Date());
            tenantsFinanceRecords.setStatus("03");
            tenantsFinanceRecords.setIsUsable("1");
            tenantsFinanceRecords.setDraweeId(ordersDao.getMemberId());//付款方id
            tenantsFinanceRecords.setDraweeType("01");//付款方类型01客户02家政机构03业务员04家政员05平台
            tenantsFinanceRecords.setPayeeId(ordersDao.getTenantId());//收款方id
            tenantsFinanceRecords.setPayeeType("02");//收款方类型01客户02家政机构03业务员04家政员05平台
            tenantsFinanceRecords.setRelatedTrans(orderNo);//关联号：订单编号
            tenantsFinanceRecordsDao.insertSelective(tenantsFinanceRecords);
        } else if (type.equals("2")) {// 支付尾款
            order.setOrderStatus("04");
            order.setBalanceOver("02");
            order.setBalanceDate(new Date());
            Orders ordersDao = orderDao.selectByPrimaryKey(orderNo);

            int compareTo = ordersDao.getOrderDeposit().compareTo(ordersDao.getAmount()); // 定金小于服务价格
            if (compareTo < 0) {
                // 插入财务流水 尾款
                TenantsFinanceRecords tenantsFinanceRe = new TenantsFinanceRecords();
                String inOutNoRe = commonService.createOrderNo("05");// 生成尾款财务流水号
                tenantsFinanceRe.setInOutNo(inOutNoRe);
                tenantsFinanceRe.setTenantId(ordersDao.getTenantId());
                tenantsFinanceRe.setPayType("01");
                tenantsFinanceRe.setInOutType("01");
                tenantsFinanceRe.setInOutObject(orderCustomersInfo.getMemberName());// 支付对象为客户
                // 尾款= 总金额-(服务费+定金)
                tenantsFinanceRe.setInOutAmount(ordersDao.getAmount().subtract(ordersDao.getOrderDeposit()));// 尾款金额
                tenantsFinanceRe.setTransType("02");
                tenantsFinanceRe.setRemarks("本地订单，尾款支付");
                tenantsFinanceRe.setAddTime(new Date());
                tenantsFinanceRe.setStatus("03");
                tenantsFinanceRe.setIsUsable("1");
                tenantsFinanceRe.setDraweeId(ordersDao.getMemberId());//付款方id
                tenantsFinanceRe.setDraweeType("01");//付款方类型01客户02家政机构03业务员04家政员05平台
                tenantsFinanceRe.setPayeeId(ordersDao.getTenantId());//收款方id
                tenantsFinanceRe.setPayeeType("02");//收款方类型01客户02家政机构03业务员04家政员05平台
                tenantsFinanceRe.setRelatedTrans(orderNo);//关联号：订单编号
                tenantsFinanceRecordsDao.insertSelective(tenantsFinanceRe);

                // 插入财务流水 服务费
                TenantsFinanceRecords tenantsFinanceSe = new TenantsFinanceRecords();
                String inOutNoSe = commonService.createOrderNo("05");// 生成服务费财务流水号
                tenantsFinanceSe.setInOutNo(inOutNoSe);
                tenantsFinanceSe.setTenantId(ordersDao.getTenantId());
                tenantsFinanceSe.setPayType("11");//支付类型：11服务费
                tenantsFinanceSe.setInOutType("01");
                tenantsFinanceSe.setInOutObject(orderCustomersInfo.getMemberName());// 支付对象为客户
                tenantsFinanceSe.setInOutAmount(ordersDao.getServiceCharge());// 服务费金额
                tenantsFinanceSe.setTransType("02");
                tenantsFinanceSe.setRemarks("本地订单，服务费");
                tenantsFinanceSe.setAddTime(new Date());
                tenantsFinanceSe.setStatus("03");
                tenantsFinanceSe.setIsUsable("1");
                tenantsFinanceSe.setDraweeId(ordersDao.getMemberId());//付款方id
                tenantsFinanceSe.setDraweeType("01");//付款方类型01客户02家政机构03业务员04家政员05平台
                tenantsFinanceSe.setPayeeId(ordersDao.getTenantId());//收款方id
                tenantsFinanceSe.setPayeeType("02");//收款方类型01客户02家政机构03业务员04家政员05平台
                tenantsFinanceSe.setRelatedTrans(orderNo);//关联号：订单编号
                tenantsFinanceRecordsDao.insertSelective(tenantsFinanceSe);
                updatejob.setStatus(JobConst.JobStatus.OFF.getCode());
            }
        }
        orderDao.updatedepositPaystate(order);

        //更新职位对应状态
        TenantsJobs job = tenantsJobsMapper.getByOrderNo(orderNo);
        if (job == null) {
            return JsonResult.success(null);
        }
        updatejob.setId(job.getId());
        updatejob.setOrderNo(orderNo);
        updatejob.setOrderStatus(order.getOrderStatus());
        tenantsJobsMapper.update(updatejob);
        if (JobConst.JobStatus.OFF.getCode().equals(updatejob.getStatus())) {
            //解冻保证金
            if (job.getDeposit() != null && job.getDeposit().compareTo(new BigDecimal(0)) > 0) {
                commonService.thawAmount(job.getTenantId(), job.getDeposit(), job.getOrderNo(), "解冻保证金[支付完成]");
            }

            Map<String, Object> map = orderDao.selectByOrderNo(orderNo);
            /**
             * 应聘信息更新
             * 更新应聘表状态
             * 解冻保证金
             */
            if (map == null || map.get("staffId") == null) {
                return JsonResult.success(null);
            }
            Integer staffId = Integer.valueOf(map.get("staffId").toString());
            TenantJobResumeForm pm = new TenantJobResumeForm();
            pm.setJobId(job.getId());
            List<TenantsJobResume> resumes = tenantsJobResumesMapper.getMyResumesBoxList(pm);
            if (resumes == null || resumes.size() == 0) {
                return JsonResult.success(null);
            }
            for (TenantsJobResume resume : resumes) {
                if (resume.getStatus().equals(JobConst.ResumeStatus.REJECTS.getCode())) {
                    continue;
                }
                TenantsJobResume update = new TenantsJobResume();
                String remarks = null;
                if (resume.getResumeTenantStaffId().equals(staffId) && Integer.valueOf(0).equals((Integer) map.get("isLocalStaff"))) {
                    //简历已完成
                    update.setJobId(resume.getJobId());
                    update.setId(resume.getId());
                    update.setStatus(JobConst.ResumeStatus.FINISHED.getCode());
                    remarks = "保证金解冻[招聘支付尾款完成]";
                } else {
                    //其他简历已取消
                    update.setJobId(resume.getJobId());
                    update.setId(resume.getId());
                    update.setStatus(JobConst.ResumeStatus.CANCELED.getCode());
                    update.setRemarks("用户已选择其他阿姨");
                    remarks = "保证金解冻[用户已选择其他阿姨]";
                }
                tenantsJobResumesMapper.update(update);
                //解冻保证金
                if (resume.getDeposit() != null && resume.getDeposit().compareTo(new BigDecimal(0)) > 0) {
                    commonService.thawAmount(resume.getResumeTenantId(), resume.getDeposit(), resume.getOrderNo(), remarks);
                }
            }
        }

        return JsonResult.success(null);
    }

    /**
     * 本地订单-定金修改
     *
     * @param orderNo author Baron date 2017-04-27
     * @return
     */
    @Override
    public JsonResult modifyDeposit(String orderNo, String deposit) {
        Orders order = new Orders();
        order.setOrderNo(orderNo);
        if (null == deposit || "".equals(deposit)) {
            deposit = "0";
        }
        BigDecimal depositbig = new BigDecimal(deposit);
        order.setOrderDeposit(depositbig);
        return JsonResult.success(orderDao.modifyDeposit(order));
    }

    /**
     * 本地订单-更换阿姨
     *
     * @param orderNo
     * @param type
     * @param remark
     * @return
     */
    @Override
    public JsonResult changehs(String orderNo, String type, String remark, UserBean userBean) {
        OrderChangehsRecordsEntity orderChangehsRecordsEntitymxid = orderChangehsRecordsMapper.queryMxid(orderNo);
        if (null != type && "01".equals(type)) {
            // 第一步，更换阿姨原因 修改orderstate=08，hs_application_time=sysdate
            OrderChangehsRecordsEntity orderChangehsRecordsEntity = new OrderChangehsRecordsEntity();
            orderChangehsRecordsEntity.setOrderNo(orderNo);
            orderChangehsRecordsEntity.setHsApplicationTime(new Date());
            orderChangehsRecordsEntity.setHsRemark(remark);
            orderChangehsRecordsEntity.setMxId(orderChangehsRecordsEntitymxid.getMxId());
            orderChangehsRecordsMapper.insertChangehs(orderChangehsRecordsEntity);
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("orderStatus", "08");
            params.put("orderNo", orderNo);
            //params.put("userId",userBean.getUserId());
            //params.put("userType",userBean.getUserType());
            //params.put("userName",userBean.getUserName());
            params.put("modifyTime", new Date());
            params.put("modifyAccount", userBean.getLoginAccount());
            orderDao.updateOrderState(params);
        } else if (null != type && "02".equals(type)) {
            // 第二步，确认更换 修改orderstate=04 add hs_handling_time=sysdate
            OrderChangehsRecordsEntity orderChangehsRecordsEntity = new OrderChangehsRecordsEntity();
            orderChangehsRecordsEntity.setOrderNo(orderNo);
            orderChangehsRecordsEntity.setHsHandlingTime(new Date());
            Integer mxid = orderChangehsRecordsEntitymxid.getMxId();
            mxid = (mxid == 1 ? 2 : mxid);
            orderChangehsRecordsEntity.setMxId(mxid - 1);
            orderChangehsRecordsMapper.updateChangehs(orderChangehsRecordsEntity);

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("orderStatus", "04");
            params.put("orderNo", orderNo);
            //params.put("userId",userBean.getUserId());
            //params.put("userType",userBean.getUserType());
            //params.put("userName",userBean.getUserName());
            params.put("modifyTime", new Date());
            params.put("modifyAccount", userBean.getLoginAccount());
            orderDao.updateOrderState(params);
        }
        return JsonResult.success("");
    }

    /*
     * 状态是01待支付定金，02待面试，03待支付尾款时候取消订单
     *
     * @谢辉
     *
     * @see
     */
    @Override
    @Transactional
    public JsonResult cancleOrderStatus(String orderNo, String inOutObject) {
        Map<String, Object> map = orderDao.selectByOrderNo(orderNo);
        try {
            if (!"".equals(map.get("orderStatus")) || "01".equals(map.get("orderStatus"))
                    || "02".equals(map.get("orderStatus")) || "03".equals(map.get("orderStatus"))) {
                if ("02".equals(map.get("orderStatus")) || "03".equals(map.get("orderStatus"))) {
                    //获取客户信息
                    OrderCustomersInfo orderCustomersInfo = orderCustomersInfoDao.selectByPrimaryKey(orderNo);
                    // 本地订单,退款,退定金
                    TenantsFinanceRecords tenantsFinanceRecords = new TenantsFinanceRecords();
                    String inOutNo = commonService.createOrderNo("05");
                    Orders ordersDao = orderDao.selectByPrimaryKey(orderNo);
                    tenantsFinanceRecords.setInOutNo(inOutNo);
                    tenantsFinanceRecords.setTenantId(ordersDao.getTenantId());
                    tenantsFinanceRecords.setPayType("10");
                    tenantsFinanceRecords.setInOutType("02");
                    tenantsFinanceRecords.setInOutObject(orderCustomersInfo.getMemberName());
                    tenantsFinanceRecords.setTransType("02");
                    tenantsFinanceRecords.setInOutAmount(ordersDao.getOrderDeposit());
                    tenantsFinanceRecords.setRemarks("本地订单，退定金");
                    tenantsFinanceRecords.setAddTime(new Date());
                    tenantsFinanceRecords.setStatus("03");
                    tenantsFinanceRecords.setIsUsable("1");
                    tenantsFinanceRecords.setDraweeId(ordersDao.getMemberId());//付款方id
                    tenantsFinanceRecords.setDraweeType("01");//付款方类型01客户02家政机构03业务员04家政员05平台
                    tenantsFinanceRecords.setPayeeId(ordersDao.getTenantId());//收款方id
                    tenantsFinanceRecords.setPayeeType("02");//收款方类型01客户02家政机构03业务员04家政员05平台
                    tenantsFinanceRecords.setRelatedTrans(orderNo);//关联号：订单编号
                    tenantsFinanceRecordsDao.insertSelective(tenantsFinanceRecords);

                }
                map.put("orderStatus", "05");
                orderDao.cancelOrderStatus(map);

                //如果发布职位， 则取消职位及简历信息
                TenantsJobs job = tenantsJobsMapper.getByOrderNo(orderNo);
                if (job == null) {
                    return JsonResult.success(null);
                }
                TenantsJobs updateJob = new TenantsJobs();
                updateJob.setId(job.getId());
                updateJob.setOrderStatus((String) map.get("orderStatus"));
                updateJob.setStatus(JobConst.JobStatus.OFF.getCode());
                tenantsJobsMapper.update(updateJob);
                if (job.getDeposit() != null && job.getDeposit().compareTo(new BigDecimal(0)) > 0) {
                    commonService.thawAmount(job.getTenantId(), job.getDeposit(), orderNo, "保证金解冻[招聘订单取消]");
                }

                //取消简历信息
                TenantJobResumeForm pm = new TenantJobResumeForm();
                pm.setOrderNo(orderNo);
                pm.setJobId(job.getId());
                List<TenantsJobResume> resumes = tenantsJobResumesMapper.getMyResumesBoxList(pm);
                for (TenantsJobResume resume : resumes) {
                    if (resume.getStatus().equals(JobConst.ResumeStatus.REJECTS.getCode())) {
                        continue;
                    }
                    TenantsJobResume updateResume = new TenantsJobResume();
                    updateResume.setId(resume.getId());
                    updateResume.setStatus(JobConst.ResumeStatus.CANCELED.getCode());
                    updateResume.setRemarks("用户取消订单");
                    tenantsJobResumesMapper.update(updateResume);
                    if (resume.getDeposit() != null && resume.getDeposit().compareTo(new BigDecimal(0)) > 0) {
                        commonService.thawAmount(resume.getResumeTenantId(), resume.getDeposit(), orderNo, "保证金解冻[招聘订单取消]");
                    }
                }

            }
            return JsonResult.success(ResultCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelDelayOrder(Orders order) throws Exception {

        log.info(String.format("cancel order :[%s]", JsonUtils.toJson(order)));
        Orders updateOrder = new Orders();
        updateOrder.setOrderNo(order.getOrderNo());
        updateOrder.setOrderStatus("05");
        updateOrder.setCancleReason("支付超时，订单取消");
        orderDao.updateByPrimaryKeySelective(updateOrder);

        String orderNo = order.getOrderNo();

        //如果发布职位， 则取消职位及简历信息
        TenantsJobs job = tenantsJobsMapper.getByOrderNo(orderNo);
        if (job == null) {
            return;
        }
        TenantsJobs updateJob = new TenantsJobs();
        updateJob.setId(job.getId());
        updateJob.setOrderStatus(updateOrder.getOrderStatus());
        updateJob.setStatus(JobConst.JobStatus.OFF.getCode());
        tenantsJobsMapper.update(updateJob);
        if (job.getDeposit() != null && job.getDeposit().compareTo(new BigDecimal(0)) > 0) {
            commonService.thawAmount(job.getTenantId(), job.getDeposit(), orderNo, "保证金解冻[原始订单超期取消]");
        }

        //取消简历信息
        TenantJobResumeForm pm = new TenantJobResumeForm();
        pm.setOrderNo(orderNo);
        pm.setJobId(job.getId());
        List<TenantsJobResume> resumes = tenantsJobResumesMapper.getMyResumesBoxList(pm);
        for (TenantsJobResume resume : resumes) {
            if (resume.getStatus().equals(JobConst.ResumeStatus.REJECTS.getCode())) {
                continue;
            }
            TenantsJobResume updateResume = new TenantsJobResume();
            updateResume.setId(resume.getId());
            updateResume.setStatus(JobConst.ResumeStatus.CANCELED.getCode());
            updateResume.setRemarks("订单超期取消");
            tenantsJobResumesMapper.update(updateResume);
            if (resume.getDeposit() != null && resume.getDeposit().compareTo(new BigDecimal(0)) > 0) {
                commonService.thawAmount(resume.getResumeTenantId(), resume.getDeposit(), orderNo, "保证金解冻[原始订单超期取消]");
            }
        }

    }

    @Override
    public JsonResult updateOrdersCustomersInfo(Map<String, Object> map) {
        return JsonResult.success(orderCustomersInfoDao.updateOrdersCustomersInfo(map));
    }
}
