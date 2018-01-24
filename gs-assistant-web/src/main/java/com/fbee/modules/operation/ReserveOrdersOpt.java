package com.fbee.modules.operation;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fbee.modules.core.utils.DateUtils;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.form.ReserveOrderDetailsForm;
import com.fbee.modules.mybatis.entity.ReserveOrdersCustomerInfoEntity;
import com.fbee.modules.mybatis.entity.ReserveOrdersEntity;
import com.fbee.modules.mybatis.model.OrderCustomersInfo;
import com.fbee.modules.mybatis.model.Orders;
import com.fbee.modules.mybatis.model.ReserveOrders;
import com.fbee.modules.mybatis.model.ReserveOrdersCustomerInfo;

/**
 * @author fanry
 * @description: 预约订单操作类
 * @date: 2017年2月14日 下午3:17:36
 */
public class ReserveOrdersOpt {

    /**
     * 封装ReserveOrdersEntity
     *
     * @param reserveOrderDetailsForm
     * @return
     */
    public ReserveOrdersEntity buildReserveOrdersEntity(ReserveOrderDetailsForm reserveOrderDetailsForm) {
        ReserveOrdersEntity reserveOrdersEntity = new ReserveOrdersEntity();

        reserveOrdersEntity.setOrderNo(reserveOrderDetailsForm.getOrderNo());
        reserveOrdersEntity.setMemberMobile(reserveOrderDetailsForm.getMemberMobile());
        reserveOrdersEntity.setMemberSex(reserveOrderDetailsForm.getSex());
        reserveOrdersEntity.setMemberName(reserveOrderDetailsForm.getMemberName());
        reserveOrdersEntity.setServiceItemCode(reserveOrderDetailsForm.getServiceItemCode());
        return reserveOrdersEntity;
    }

    /**
     * 封装ReserveOrdersCustomerInfoEntity
     *
     * @param reserveOrderDetailsForm
     * @return
     */
    public ReserveOrdersCustomerInfoEntity buildReserveOrdersCustomerInfoEntity(ReserveOrderDetailsForm reserveOrderDetailsForm) {

        ReserveOrdersCustomerInfoEntity reserveOrdersCustomerInfoEntity = new ReserveOrdersCustomerInfoEntity();

        reserveOrdersCustomerInfoEntity.setOrderNo(reserveOrderDetailsForm.getOrderNo());
        reserveOrdersCustomerInfoEntity.setRemarks(reserveOrderDetailsForm.getRemarks());

        reserveOrdersCustomerInfoEntity.setChildrenCount(reserveOrderDetailsForm.getChildrenCount());
        reserveOrdersCustomerInfoEntity.setChildrenAgeRange(reserveOrderDetailsForm.getChildrenAgeRange());

        reserveOrdersCustomerInfoEntity.setFamilyCount(reserveOrderDetailsForm.getFamilyCount());

        reserveOrdersCustomerInfoEntity.setServiceStart(DateUtils.parseDate(reserveOrderDetailsForm.getServiceStart()));
        reserveOrdersCustomerInfoEntity.setServiceEnd(DateUtils.parseDate(reserveOrderDetailsForm.getServiceEnd()));

        reserveOrdersCustomerInfoEntity.setHouseType(reserveOrderDetailsForm.getHouseType());
        reserveOrdersCustomerInfoEntity.setHouseArea(reserveOrderDetailsForm.getHouseArea());

        reserveOrdersCustomerInfoEntity.setExpectedBirth(DateUtils.parseDate(reserveOrderDetailsForm.getExpectedBirth()));
        reserveOrdersCustomerInfoEntity.setIsBabyBorn(reserveOrderDetailsForm.getIsBabyBorn());

        reserveOrdersCustomerInfoEntity.setOlderCount(reserveOrderDetailsForm.getOlderCount());
        reserveOrdersCustomerInfoEntity.setOlderAgeRange(reserveOrderDetailsForm.getOlderAgeRange());
        reserveOrdersCustomerInfoEntity.setSelfCares(reserveOrderDetailsForm.getSelfCares());


        reserveOrdersCustomerInfoEntity.setSalaryType(reserveOrderDetailsForm.getSalaryType());
        reserveOrdersCustomerInfoEntity.setSalary(reserveOrderDetailsForm.getSalary());

        reserveOrdersCustomerInfoEntity.setServiceProvice(reserveOrderDetailsForm.getServiceProvice());
        reserveOrdersCustomerInfoEntity.setServiceCity(reserveOrderDetailsForm.getServiceCity());
        reserveOrdersCustomerInfoEntity.setServiceCounty(reserveOrderDetailsForm.getServiceCounty());
        reserveOrdersCustomerInfoEntity.setServiceAddress(reserveOrderDetailsForm.getServiceAddress());


        String[] cookingReqirements = reserveOrderDetailsForm.getCookingReqirements();
        String[] languageRequirements = reserveOrderDetailsForm.getLanguageRequirements();
        String[] personalityRequirements = reserveOrderDetailsForm.getPersonalityRequirements();
        String[] salarySkills = reserveOrderDetailsForm.getSalarySkills();


        reserveOrdersCustomerInfoEntity.setCookingReqirements(StringUtils.strAppend(cookingReqirements));
        reserveOrdersCustomerInfoEntity.setLanguageRequirements(StringUtils.strAppend(languageRequirements));
        reserveOrdersCustomerInfoEntity.setPersonalityRequirements(StringUtils.strAppend(personalityRequirements));
        reserveOrdersCustomerInfoEntity.setSalarySkills(StringUtils.strAppend(salarySkills));

        reserveOrdersCustomerInfoEntity.setWageRequirements(reserveOrderDetailsForm.getWageRequirements());

        reserveOrdersCustomerInfoEntity.setExperienceRequirements(reserveOrderDetailsForm.getExperienceRequirements());

        reserveOrdersCustomerInfoEntity.setServiceType(reserveOrderDetailsForm.getServiceType());

        reserveOrdersCustomerInfoEntity.setPetRaising(reserveOrderDetailsForm.getPetRaising());

        return reserveOrdersCustomerInfoEntity;
    }


    /**
     * 封装OrdersEntity
     *
     * @param reserveOrderDetailsForm
     * @return
     */
    public Orders buildOrdersEntity(ReserveOrderDetailsForm reserveOrderDetailsForm) {
        Orders ordersEntity = new Orders();

        ordersEntity.setAddTime(new Date());
        ordersEntity.setAddAccount(reserveOrderDetailsForm.getAddAccount());
        ordersEntity.setTenantId(reserveOrderDetailsForm.getTenantId());
        ordersEntity.setStaffId(reserveOrderDetailsForm.getStaffId());
        ordersEntity.setMemberId(reserveOrderDetailsForm.getMemberId());
        ordersEntity.setOrderStatus(reserveOrderDetailsForm.getNewOrderStatus());
        ordersEntity.setServiceItemCode(reserveOrderDetailsForm.getServiceItemCode());
        ordersEntity.setAmount(reserveOrderDetailsForm.getAmount());
        ordersEntity.setRemark(reserveOrderDetailsForm.getRemark());
        //添加服务价格 Baron 20170426
        ordersEntity.setServiceCharge(reserveOrderDetailsForm.getServiceCharge());

        try {
            if (StringUtils.isNotBlank(reserveOrderDetailsForm.getOrderTime())) {
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(reserveOrderDetailsForm.getOrderTime());
                ordersEntity.setOrderTime(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if ("01".equals(reserveOrderDetailsForm.getNewOrderStatus())) {
            String str = "300";
            BigDecimal bd = new BigDecimal(str);
            bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
            ordersEntity.setOrderDeposit(bd);  //定金金额
            //Baron 尾款=服务价格+服务费-定金 20170428
            if (null == ordersEntity.getServiceCharge()) {
                ordersEntity.setServiceCharge(new BigDecimal(0));
            }
            BigDecimal orderBalance = new BigDecimal("0");//订单价格和定金相减
            if(reserveOrderDetailsForm.getAmount() != null){
            	orderBalance = orderBalance.add(reserveOrderDetailsForm.getAmount());
            }
            if(ordersEntity.getServiceCharge() != null){
            	orderBalance = orderBalance.add(ordersEntity.getServiceCharge());
            }
            orderBalance = orderBalance.subtract(bd);
            ordersEntity.setOrderBalance(orderBalance); //尾款金额
            ordersEntity.setIdepositOver("01"); //定金未支付
            ordersEntity.setIsInterview("01");//未面试
            ordersEntity.setBalanceOver("01");//尾款未支付
        } else if ("03".equals(reserveOrderDetailsForm.getNewOrderStatus())) {
            String str = "0";
            BigDecimal bd = new BigDecimal(str);
            bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
            ordersEntity.setOrderDeposit(bd);  //定金金额
            //Baron 尾款=服务价格+服务费-定金 20170428
            if (null == ordersEntity.getServiceCharge()) {
                ordersEntity.setServiceCharge(new BigDecimal(0));
            }
            //尾款金额不用减定金
            BigDecimal orderBalance = new BigDecimal("0");
            if(reserveOrderDetailsForm.getAmount() != null){
            	orderBalance = orderBalance.add(reserveOrderDetailsForm.getAmount());
            }
            if(ordersEntity.getServiceCharge() != null){
            	orderBalance = orderBalance.add(ordersEntity.getServiceCharge());
            }
            ordersEntity.setOrderBalance(orderBalance);
            ordersEntity.setIdepositOver("02"); //定金已经支付
            ordersEntity.setIsInterview("02");//已经面试
            ordersEntity.setBalanceOver("01");//尾款未支付
        } else {
            String str = "0";
            BigDecimal bd = new BigDecimal(str);
            bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
            ordersEntity.setOrderDeposit(bd);  //定金金额
            //Baron 尾款=服务价格+服务费-定金 20170428
            if (null == ordersEntity.getServiceCharge()) {
                ordersEntity.setServiceCharge(new BigDecimal(0));
            }
            //尾款金额不用减定金
            BigDecimal orderBalance = new BigDecimal("0");
            if(reserveOrderDetailsForm.getAmount() != null){
            	orderBalance = orderBalance.add(reserveOrderDetailsForm.getAmount());
            }
            if(ordersEntity.getServiceCharge() != null){
            	orderBalance = orderBalance.add(ordersEntity.getServiceCharge());
            }
            ordersEntity.setOrderBalance(orderBalance);
            ordersEntity.setIdepositOver("02"); //定金已经支付
            ordersEntity.setIsInterview("02");//已经面试
            ordersEntity.setBalanceOver("02");//尾款已经支付
        }

        ordersEntity.setIsLock("1");
        ordersEntity.setOrderSource("02");//02网络订单

        return ordersEntity;
    }

    /**
     * 封装OrderCustomersInfoEntity
     *
     * @param reserveOrderDetailsForm
     * @return
     */
    public OrderCustomersInfo buildOrderCustomersInfoEntity(ReserveOrderDetailsForm reserveOrderDetailsForm) {
        OrderCustomersInfo orderCustomersInfoEntity = new OrderCustomersInfo();

        orderCustomersInfoEntity.setMemberMobile(reserveOrderDetailsForm.getMemberMobile());
        orderCustomersInfoEntity.setMemberSex(reserveOrderDetailsForm.getSex());
        orderCustomersInfoEntity.setMemberName(reserveOrderDetailsForm.getMemberName());

        orderCustomersInfoEntity.setServiceItemCode(reserveOrderDetailsForm.getServiceItemCode());

        orderCustomersInfoEntity.setRemarks(reserveOrderDetailsForm.getRemark());
        orderCustomersInfoEntity.setSpecialNeeds(reserveOrderDetailsForm.getRemarks());

        orderCustomersInfoEntity.setChildrenCount(reserveOrderDetailsForm.getChildrenCount());
        orderCustomersInfoEntity.setChildrenAgeRange(reserveOrderDetailsForm.getChildrenAgeRange());

        orderCustomersInfoEntity.setFamilyCount(reserveOrderDetailsForm.getFamilyCount());
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            orderCustomersInfoEntity.setServiceStart(parser.parse(reserveOrderDetailsForm.getServiceStart()));
            orderCustomersInfoEntity.setServiceEnd(parser.parse(reserveOrderDetailsForm.getServiceEnd()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        orderCustomersInfoEntity.setHouseType(reserveOrderDetailsForm.getHouseType());
        orderCustomersInfoEntity.setHouseArea(reserveOrderDetailsForm.getHouseArea());

        orderCustomersInfoEntity.setExpectedBirth(DateUtils.parseDate(reserveOrderDetailsForm.getExpectedBirth()));
        orderCustomersInfoEntity.setIsBabyBorn(reserveOrderDetailsForm.getIsBabyBorn());

        orderCustomersInfoEntity.setOlderCount(reserveOrderDetailsForm.getOlderCount());
        orderCustomersInfoEntity.setOlderAgeRange(reserveOrderDetailsForm.getOlderAgeRange());
        orderCustomersInfoEntity.setSelfCares(reserveOrderDetailsForm.getSelfCares());

        orderCustomersInfoEntity.setSalary(reserveOrderDetailsForm.getSalary() == null ? new BigDecimal(0) : reserveOrderDetailsForm.getSalary());
        orderCustomersInfoEntity.setSalaryType(reserveOrderDetailsForm.getSalaryType());

        orderCustomersInfoEntity.setServiceProvice(reserveOrderDetailsForm.getServiceProvice());
        orderCustomersInfoEntity.setServiceCity(reserveOrderDetailsForm.getServiceCity());
        orderCustomersInfoEntity.setServiceCounty(reserveOrderDetailsForm.getServiceCounty());
        orderCustomersInfoEntity.setServiceAddress(reserveOrderDetailsForm.getServiceAddress());


        String[] cookingReqirements = reserveOrderDetailsForm.getCookingReqirements();
        String[] languageRequirements = reserveOrderDetailsForm.getLanguageRequirements();
        String[] personalityRequirements = reserveOrderDetailsForm.getPersonalityRequirements();
        String[] salarySkills = reserveOrderDetailsForm.getSalarySkills();


        orderCustomersInfoEntity.setCookingReqirements(StringUtils.strAppend(cookingReqirements));
        orderCustomersInfoEntity.setLanguageRequirements(StringUtils.strAppend(languageRequirements));
        orderCustomersInfoEntity.setPersonalityRequirements(StringUtils.strAppend(personalityRequirements));
        orderCustomersInfoEntity.setSalarySkills(StringUtils.strAppend(salarySkills));


        orderCustomersInfoEntity.setWageRequirements(reserveOrderDetailsForm.getWageRequirements());
        orderCustomersInfoEntity.setExperienceRequirements(reserveOrderDetailsForm.getExperienceRequirements());

        orderCustomersInfoEntity.setSpecialNeeds(reserveOrderDetailsForm.getRemarks());

        orderCustomersInfoEntity.setServiceType(reserveOrderDetailsForm.getServiceType());

        orderCustomersInfoEntity.setPetRaising(reserveOrderDetailsForm.getPetRaising());

        return orderCustomersInfoEntity;
    }


    /**
     * 封装OrderCustomersInfoEntity
     *
     * @return
     */
    public OrderCustomersInfo buildOrderCustomersInfoEntity(ReserveOrders ro, ReserveOrdersCustomerInfo recust) {
        OrderCustomersInfo orderCustomersInfoEntity = new OrderCustomersInfo();

        orderCustomersInfoEntity.setMemberMobile(ro.getMemberMobile());
        orderCustomersInfoEntity.setMemberSex(ro.getMemberSex());
        orderCustomersInfoEntity.setMemberName(ro.getMemberName());

        orderCustomersInfoEntity.setServiceItemCode(ro.getServiceItemCode());

        orderCustomersInfoEntity.setRemarks(recust.getSpecialNeeds());
        orderCustomersInfoEntity.setSpecialNeeds(ro.getRemark());

        orderCustomersInfoEntity.setChildrenCount(recust.getChildrenCount());
        orderCustomersInfoEntity.setChildrenAgeRange(recust.getChildrenAgeRange());

        orderCustomersInfoEntity.setFamilyCount(recust.getFamilyCount());
        orderCustomersInfoEntity.setServiceStart(recust.getServiceStart());
        orderCustomersInfoEntity.setServiceEnd(recust.getServiceEnd());

        orderCustomersInfoEntity.setHouseType(recust.getHouseType());
        orderCustomersInfoEntity.setHouseArea(recust.getHouseArea());

        orderCustomersInfoEntity.setExpectedBirth(DateUtils.parseDate(recust.getExpectedBirth()));
        orderCustomersInfoEntity.setIsBabyBorn(recust.getIsBabyBorn());

        orderCustomersInfoEntity.setOlderCount(recust.getOlderCount());
        orderCustomersInfoEntity.setOlderAgeRange(recust.getOlderAgeRange());
        orderCustomersInfoEntity.setSelfCares(recust.getSelfCares());

        orderCustomersInfoEntity.setSalary(recust.getSalary() == null ? new BigDecimal(0) : recust.getSalary());
        orderCustomersInfoEntity.setSalaryType(recust.getSalaryType());
        orderCustomersInfoEntity.setSalarySkills(recust.getSalarySkills());

        orderCustomersInfoEntity.setServiceProvice(recust.getServiceProvice());
        orderCustomersInfoEntity.setServiceCity(recust.getServiceCity());
        orderCustomersInfoEntity.setServiceCounty(recust.getServiceCounty());
        orderCustomersInfoEntity.setServiceAddress(recust.getServiceAddress());



        orderCustomersInfoEntity.setWageRequirements(recust.getWageRequirements());
        orderCustomersInfoEntity.setExperienceRequirements(recust.getExperienceRequirements());

        orderCustomersInfoEntity.setSpecialNeeds(recust.getRemarks());

        orderCustomersInfoEntity.setServiceType(recust.getServiceType());

        orderCustomersInfoEntity.setPetRaising(recust.getPetRaising());

        return orderCustomersInfoEntity;
    }
}
