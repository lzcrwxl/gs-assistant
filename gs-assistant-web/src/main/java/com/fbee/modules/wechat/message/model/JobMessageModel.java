package com.fbee.modules.wechat.message.model;


/**
 * arQA_ktRb7qz6-FCVzOfZal85waCwjwapDDnKQWuCj8
 */
public class JobMessageModel extends MessageModel{

    private String jobId;

    /**
     * 职位类型
     */
    private String serviceType;
    /**
     * 职位名称
     */
    private String title;

    /**
     * 工资范围
     */
    private String  salary;

    /**
     * 年龄要求
     */
    private String age;

    /**
     * 服务区域
     */
    private String province;
    private String city;
    private String district;
    private String address;

    /**
     * 订单信息
     */
    private String description;

    /**
     * 服务时间
     */
    private String serviceDate;

    /**
     * remark
     */
    private String remark;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
