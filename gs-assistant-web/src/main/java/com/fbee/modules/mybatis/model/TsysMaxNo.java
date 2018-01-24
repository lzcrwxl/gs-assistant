package com.fbee.modules.mybatis.model;

public class TsysMaxNo {
    /**
     * 表：t_sys_max_no
     * 字段：SYS_MAX_NO_ID
     * 注释：
     *
     * @mbggenerated
     */
    private Long sysMaxNoId;

    /**
     * 表：t_sys_max_no
     * 字段：MAX_NO
     * 注释：
     *
     * @mbggenerated
     */
    private String maxNo;

    /**
     * 表：t_sys_max_no
     * 字段：MAX_NO_TYPE
     * 注释：
     *
     * @mbggenerated
     */
    private String maxNoType;

    public Long getSysMaxNoId() {
        return sysMaxNoId;
    }

    public void setSysMaxNoId(Long sysMaxNoId) {
        this.sysMaxNoId = sysMaxNoId;
    }

    public String getMaxNo() {
        return maxNo;
    }

    public void setMaxNo(String maxNo) {
        this.maxNo = maxNo == null ? null : maxNo.trim();
    }

    public String getMaxNoType() {
        return maxNoType;
    }

    public void setMaxNoType(String maxNoType) {
        this.maxNoType = maxNoType == null ? null : maxNoType.trim();
    }
}