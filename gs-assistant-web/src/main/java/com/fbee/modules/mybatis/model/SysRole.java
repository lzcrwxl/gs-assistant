package com.fbee.modules.mybatis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色
 *
 */
public class SysRole implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private Integer id;

    private String name;	//角色名称

    private Date createDate;

    private Integer state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}