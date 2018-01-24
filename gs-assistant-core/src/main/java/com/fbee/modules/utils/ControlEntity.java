package com.fbee.modules.utils;

import java.util.List;

public class ControlEntity {

    private String id;
    private String value;


    private List<ControlEntity> ceList;


    public ControlEntity(String id, String value) {
        super();
        this.id = id;
        this.value = value;
    }

    public ControlEntity(String id, String value, List<ControlEntity> ceList) {
        super();
        this.id = id;
        this.value = value;
        this.ceList = ceList;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<ControlEntity> getCeList() {
        return ceList;
    }

    public void setCeList(List<ControlEntity> ceList) {
        this.ceList = ceList;
    }
}
