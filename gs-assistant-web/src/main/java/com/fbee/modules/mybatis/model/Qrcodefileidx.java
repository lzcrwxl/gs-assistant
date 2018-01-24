package com.fbee.modules.mybatis.model;

public class Qrcodefileidx {
    /**
     * 表：qrcodefileidx
     * 字段：QRID
     * 注释：二维码ID
     *
     * @mbggenerated
     */
    private Integer qrid;

    /**
     * 表：qrcodefileidx
     * 字段：OBJID
     * 注释：二维码类型
     *
     * @mbggenerated
     */
    private String objid;

    /**
     * 表：qrcodefileidx
     * 字段：OBJTYPE
     * 注释：二维码类型
     *
     * @mbggenerated
     */
    private String objtype;

    /**
     * 表：qrcodefileidx
     * 字段：OBJSUBTYPE
     * 注释：二维码子类型
     *
     * @mbggenerated
     */
    private String objsubtype;

    /**
     * 表：qrcodefileidx
     * 字段：FILENAME
     * 注释：文件存储路径
     *
     * @mbggenerated
     */
    private String filename;

    public Integer getQrid() {
        return qrid;
    }

    public void setQrid(Integer qrid) {
        this.qrid = qrid;
    }

    public String getObjid() {
        return objid;
    }

    public void setObjid(String objid) {
        this.objid = objid == null ? null : objid.trim();
    }

    public String getObjtype() {
        return objtype;
    }

    public void setObjtype(String objtype) {
        this.objtype = objtype == null ? null : objtype.trim();
    }

    public String getObjsubtype() {
        return objsubtype;
    }

    public void setObjsubtype(String objsubtype) {
        this.objsubtype = objsubtype == null ? null : objsubtype.trim();
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename == null ? null : filename.trim();
    }
}