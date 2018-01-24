package com.fbee.modules.mybatis.model;

public class OrderContractInfo {
    /**
     * 表：order_contract_info
     * 字段：ID
     * 注释：
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 表：order_contract_info
     * 字段：ORDER_NO
     * 注释：订单流水号
     *
     * @mbggenerated
     */
    private String orderNo;

    /**
     * 表：order_contract_info
     * 字段：CONTRACT_URL
     * 注释：合同图片路径
     *
     * @mbggenerated
     */
    private String contractUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getContractUrl() {
        return contractUrl;
    }

    public void setContractUrl(String contractUrl) {
        this.contractUrl = contractUrl == null ? null : contractUrl.trim();
    }
}