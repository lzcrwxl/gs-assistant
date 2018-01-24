package com.fbee.modules.mybatis.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class TenantsJobCheck implements Serializable{

    /**
     * 商家id
     */
    private Integer tenantId;

    /**
     * 招聘总次数
     */
    private Integer jobTotalNum;

    /**
     * 招聘已用总数
     */
    private Integer jobUsedNum;

    /**
     * 抢单总次数
     */
    private Integer resumeTotalNum;

    /**
     * 已用抢单次数
     */
    private Integer resumeUsedNum;

    /**
     * 处理中的简历数
     */
    private Integer processResumeNum;

    /**
     * 账户余额
     */
    private BigDecimal balance;

    /**
     * 发布职位缴保证金
     */
    private BigDecimal jobDeposit;
    /**
     * 抢单保证金
     */
    private BigDecimal resumeDeposit;

    public Integer getProcessResumeNum() {
        return processResumeNum;
    }

    public void setProcessResumeNum(Integer processResumeNum) {
        this.processResumeNum = processResumeNum;
    }

    public BigDecimal getResumeDeposit() {
        return resumeDeposit;
    }

    public void setResumeDeposit(BigDecimal resumeDeposit) {
        this.resumeDeposit = resumeDeposit;
    }

    public BigDecimal getJobDeposit() {
        return jobDeposit;
    }

    public void setJobDeposit(BigDecimal jobDeposit) {
        this.jobDeposit = jobDeposit;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getJobTotalNum() {
        return jobTotalNum;
    }

    public void setJobTotalNum(Integer jobTotalNum) {
        this.jobTotalNum = jobTotalNum;
    }

    public Integer getJobUsedNum() {
        return jobUsedNum;
    }

    public void setJobUsedNum(Integer jobUsedNum) {
        this.jobUsedNum = jobUsedNum;
    }

    public Integer getResumeTotalNum() {
        return resumeTotalNum;
    }

    public void setResumeTotalNum(Integer resumeTotalNum) {
        this.resumeTotalNum = resumeTotalNum;
    }

    public Integer getResumeUsedNum() {
        return resumeUsedNum;
    }

    public void setResumeUsedNum(Integer resumeUsedNum) {
        this.resumeUsedNum = resumeUsedNum;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
