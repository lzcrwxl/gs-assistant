package com.fbee.modules.bean.consts;

/**
 * 职位招聘相关
 */
public interface JobConst {

    enum JobStatus{
        ON("1", "上架"),
        OFF("0", "下架");

        private String code;
        private String desc;
        private JobStatus(String code, String desc) {
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

    enum ResumeStatus{
        APPLY("1", "待处理"),
        PASSED("2", "待面试"),
        REJECTS("3", "已拒绝"),
        FINISHED("4", "已完成"),
        CANCELED("5", "已取消");

        private String code;
        private String desc;
        private ResumeStatus(String code, String desc) {
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
