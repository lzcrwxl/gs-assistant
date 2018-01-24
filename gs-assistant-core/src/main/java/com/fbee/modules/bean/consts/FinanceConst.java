package com.fbee.modules.bean.consts;

/**
 * 资金常量
 */
public interface FinanceConst {

    /**
     * 交易类型 01 订单支付 02成单加价 03成单奖励 04账户充值 05账户提现
     * 06会员续费 07报名费 08住宿费 09佣金费 10取消订单退定金
     * 11招聘保证金
     */
    enum PayType {
        ORDER_PAY("01", "订单支付"),
        FINISH_ORDER_PLUS("02", "成单加价"),
        FINISH_ORDER_REWARD("03", "成单奖励"),
        ACCOUNT_RECHARGE("04","账户充值"),
        ACCOUNT_WITHDRAW("05", "账户提现"),
        MEMBER_RECHARGE("06", "会员续费"),
        SIGN_UP_FEE("07", "报名费"),
        HOTEL_FEE("08", "住宿费"),
        COMMISSION_FEE("09", "佣金费"),
        CANCEL_ORDER_DEPOSIT_BAC("10","取消订单退定金");

        private String code;
        private String desc;

        private PayType(String code, String desc) {
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

    enum InOutType{
        IN("01", "收入"),
        OUT("02","支出");


        private String code;
        private String desc;

        private InOutType(String code, String desc) {
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

    enum TransType{
        ONLINE("01", "线上"),
        OFFLINE("02","线下");


        private String code;
        private String desc;

        private TransType(String code, String desc) {
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

    //状态（01、待处理 02、处理中 03、已处理04、已处理交易失败）',
    enum Status{
        WAIT("01", "待处理"),
        PROCESS("02","处理中"),
        SUCCESS("02","已处理"),
        FAIL("02","已处理交易失败");

        private String code;
        private String desc;

        private Status(String code, String desc) {
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
