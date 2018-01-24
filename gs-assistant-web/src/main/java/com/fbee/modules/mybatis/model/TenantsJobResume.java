package com.fbee.modules.mybatis.model;

import com.fbee.modules.mybatis.entity.TenantsJobsEntity;
import com.fbee.modules.mybatis.entity.TenantsStaffJobInfoEntity;
import com.fbee.modules.mybatis.entity.TenantsStaffSerItemsEntity;
import com.fbee.modules.mybatis.entity.TenantsStaffsInfoEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 门店职位应聘信息
 */
public class TenantsJobResume implements Serializable{

    private Integer id;
    /**
     * 职位id
     */
    private Integer jobId;
    /**
     * 订单编号
     */
    private String  orderNo;
    /**
     * 招聘方门店id
     */
    private Integer jobTenantId;
    /**
     * 招聘方门店用户id
     */
    private Integer jobTenantUserId;
    /**
     * 应聘方门店ID
     */
    private Integer resumeTenantId;
    /**
     * 应聘方门店用户id
     */
    private Integer resumeTenantUserId;
    /**
     * 应聘方投递阿姨id
     */
    private Integer resumeTenantStaffId;

    /**
     * 联系人
     */
    private String contactName;
    /**
     * 联系电话
     */
    private String contactPhone;
    /**
     * 应聘方门店名称
     */
    private String resumeTenantName;

    /**
     * 阿姨详情
     */
    private StaffSnapShotInfo staffInfo;

    /**
     * 职位详情
     */
    private TenantsJobs jobInfo;

    /**
     * 投递日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date    applyDate;
    /**
     * 审核日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date    checkDate;
    /**
     * 状态1待处理，2已通过，3已拒绝
     */
    private String  status;

    /**
     * 匹配度
     */
    private Integer match;
    /**
     * 备注
     */
    private String  remarks;
    private String  isUsable;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date    addTime;
    private String  addAccount;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date    modifyTime;
    private String  modifyAccount;

    /**
     * 是否使用保证金1是0否
     */
    private BigDecimal deposit;

    public Integer getMatch() {
        return match;
    }

    public void setMatch(Integer match) {
        this.match = match;
    }

    public TenantsJobs getJobInfo() {
        return jobInfo;
    }

    public void setJobInfo(TenantsJobs jobInfo) {
        this.jobInfo = jobInfo;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getResumeTenantName() {
        return resumeTenantName;
    }

    public void setResumeTenantName(String resumeTenantName) {
        this.resumeTenantName = resumeTenantName;
    }

    public StaffSnapShotInfo getStaffInfo() {
        return staffInfo;
    }

    public void setStaffInfo(StaffSnapShotInfo staffInfo) {
        this.staffInfo = staffInfo;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getJobTenantId() {
        return jobTenantId;
    }

    public void setJobTenantId(Integer jobTenantId) {
        this.jobTenantId = jobTenantId;
    }

    public Integer getJobTenantUserId() {
        return jobTenantUserId;
    }

    public void setJobTenantUserId(Integer jobTenantUserId) {
        this.jobTenantUserId = jobTenantUserId;
    }

    public Integer getResumeTenantId() {
        return resumeTenantId;
    }

    public void setResumeTenantId(Integer resumeTenantId) {
        this.resumeTenantId = resumeTenantId;
    }

    public Integer getResumeTenantUserId() {
        return resumeTenantUserId;
    }

    public void setResumeTenantUserId(Integer resumeTenantUserId) {
        this.resumeTenantUserId = resumeTenantUserId;
    }

    public Integer getResumeTenantStaffId() {
        return resumeTenantStaffId;
    }

    public void setResumeTenantStaffId(Integer resumeTenantStaffId) {
        this.resumeTenantStaffId = resumeTenantStaffId;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getIsUsable() {
        return isUsable;
    }

    public void setIsUsable(String isUsable) {
        this.isUsable = isUsable;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getAddAccount() {
        return addAccount;
    }

    public void setAddAccount(String addAccount) {
        this.addAccount = addAccount;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyAccount() {
        return modifyAccount;
    }

    public void setModifyAccount(String modifyAccount) {
        this.modifyAccount = modifyAccount;
    }
}
