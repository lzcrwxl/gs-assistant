package com.fbee.modules.mybatis.model;

public class Bank {
	//银行code
    private String bankCode;

    private String sysCode;

    //银行名称
    private String bankName;

    private String bankTypecode;

    private String bankTypename;

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankTypecode() {
        return bankTypecode;
    }

    public void setBankTypecode(String bankTypecode) {
        this.bankTypecode = bankTypecode;
    }

    public String getBankTypename() {
        return bankTypename;
    }

    public void setBankTypename(String bankTypename) {
        this.bankTypename = bankTypename;
    }
}