package com.fbee.modules.match.consts;

/**
 * 匹配属性
 */
public interface MatchConst {

    /**
     * 方式
     */
    enum Pattern{

        ABSOLUTE(1,"绝对匹配"),
        MISTINESS(2, "模糊匹配"),
        REGION(3, "区间匹配");


        private Integer code;
        private String  desc;

        Pattern(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
