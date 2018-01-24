package com.fbee.modules.bean.consts;

/**
 * 系统设置常量
 * 取 dictionary 里的值
 */
public interface SysSetting {

    /**
     * key
     * @author Administrator
     */
    enum Key {
        JOB_COUNT("JOB_COUNT", "可发布职位总数"),
        RESUME_COUNT("RESUME_COUNT", "可申请职位数量"),
        JOB_DEPOSIT("JOB_DEPOSIT", "发布职位应缴保证金"),
        RESUME_DEPOSIT("RESUME_DEPOSIT", "应聘职位应缴保证金");

        private String code;
        private String desc;

        private Key(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

    }

}

