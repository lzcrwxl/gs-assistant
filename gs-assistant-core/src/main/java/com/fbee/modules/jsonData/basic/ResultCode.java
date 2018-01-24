package com.fbee.modules.jsonData.basic;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @Description: 响应返回码
 * @author hzp
 * @date 2016-5-11
 *
 */
public class ResultCode {

	public static final Integer SUCCESS = 0;
	public static final Integer ERROR = 999999;
	public static final Integer SESSION_TIMEOUT = 999010;
	public static final Integer PARAMS_ERROR = 999011;
	public static final Integer DATA_ERROR = 999012;
	public static final Integer DATA_ADD_BASE_INFO_ERROR = 999112;
	public static final Integer ILLEGAL_REQUEST = 999013;
	public static final Integer AUTHORIZE_ERROR = 999014;
	public static final Integer DATA_IS_NULL = 999015;
	public static final Integer SMSCODE_TIMEOUT = 999016;
	public static final Integer NO_PERMISSION = 999017;
	public static final Integer ERROR_OLD_PERMISSION = 999018;
	public static final Integer OPERATION_TOO_FREQUENT = 999019;
	

	public static final class User {
		public static final Integer LOGIN_ACCOUNT_IS_NULL = 100001;
		public static final Integer LOGIN_PASSWORD_IS_NULL = 100002;
		public static final Integer CAPTCHA_IS_NULL = 100003;
		public static final Integer CAPTCHA_FAILURE = 100004;
		public static final Integer CAPTCHA_ERROR = 100005;
		public static final Integer CAPTCHA_TIMEOUT = 100006;
		public static final Integer ACCOUNT_PASSWORD_ERROR = 100007;
		public static final Integer ACCOUNT_NOT_EXIST = 100008;
		public static final Integer SMSSEND_FAILURE = 100010;
		public static final Integer SMSSEND_TIMEOUT = 100009;
		public static final Integer MSG_CAPTCHA_TIMEOUT = 100011;
		public static final Integer MSG_CAPTCHA_ERROR = 100012;
		public static final Integer MSG_NULL_ERROR = 100017;
		public static final Integer TELEPHONE_BUNDED = 100018;
		public static final Integer MSG_ACCOUNT_MAX = 100013;
		public static final Integer ACCOUNT_EXIST = 100014;
		public static final Integer TENANTS_IS_UNABLE = 100015;
		public static final Integer CODE_ERROR = 100016;
	}

	public static final class Banner {
		public static final Integer UPLOAD_SUCCESS = 200001;
		public static final Integer UPLOAD_ERROR = 200002;
	}

	public static final class Staff {
		public static final Integer STAFF_BANK_CODE_IS_NULL = 300001;
		public static final Integer STAFF_BANK_CARD_NO_IS_NULL = 300002;

		public static final Integer STAFF_POLICY_NO_IS_NULL = 300003;
		public static final Integer STAFF_POLICY_NAME_IS_NULL = 300004;
		public static final Integer STAFF_POLICY_AMOUNT_IS_NULL = 300005;
		public static final Integer STAFF_POLICY_AGENCY_IS_NULL = 300006;
		public static final Integer STAFF_CERT_CERT_NO_IS_NULL = 300007;
		public static final Integer STAFF_CERT_CERT_TYPE_IS_NULL = 300008;
		public static final Integer STAFF_EDUCATION_IS_NULL = 300009;
		public static final Integer STAFF_HEIGHT_IS_NULL = 300010;
		public static final Integer STAFF_WEIGHT_IS_NULL = 300011;
		public static final Integer STAFF_MARITAL_STATUS_IS_NULL = 300012;
		public static final Integer STAFF_FERTILITY_SITUATION_IS_NULL = 300013;
		public static final Integer STAFF_LIVE_ADDRESS_IS_NULL = 300014;
		public static final Integer CERT_TYPE_IS_NOT_REPETITION = 300015;
		public static final Integer STAFF_BANK_ACCOUNT_NAME_IS_NULL = 300016;
		public static final Integer CERT_TYPE_IS_REPETITION = 300017;//xiehui
		public static final Integer STAFF_IS_EXISTS = 300018;//zsq
		public static final Integer STAFF_BLOO_DTYPE_IS_NULL=300019;
		public static final Integer STAFF_MOBILE_IS_NULL = 300020;
		public static final Integer STAFF_CERT_IMAGE_IS_NULL = 300021;
	}

	public static final class Job {
		public static final Integer JOB_ORDER_IS_NULL = 400001;
		public static final Integer JOB_SERVICE_TYPE_IS_NULL = 400002;
		public static final Integer JOB_AGE_IS_NULL = 400003;
		public static final Integer JOB_DEMAND_IS_NULL = 400004;
		public static final Integer JOB_SLALRY_IS_NULL = 400005;
		public static final Integer JOB_IS_USABLE_IS_NULL = 400006;
		public static final Integer JOB_IS_SERVER_MOLD_IS_NULL = 400007;
		public static final Integer JOB_IS_EMS_SIGN_IS_NULL = 400008;
		public static final Integer JOB_SLALRY_TYPE_IS_NULL = 400009;
	}

	public static final class JobResume {
		public static final Integer JOB_FINISHED = 900001;
		public static final Integer JOB_OFF = 900002;
    }
	

	public static class Function {
		public static final Integer LOGIN_DISABLED = 500001;
			
	}

	public static class Product {
		public static final Integer EXIST_B_M = 600001;
	}

	public static class Funds {
		public static final Integer AVAILABLE_AMOUNT_NOT_ENOUGH = 700001;
		public static final Integer FROZEN_AMOUNT_NOT_ENOUGH = 700002;
	}
	
	public static class Order {
		public static final Integer SHARE_INFO_IS_EXIST = 800001;
		public static final Integer ORDER_CONTRACT_IS_UPPER_LIMIT = 800002;
		public static final Integer ORDER_CUSTOMER_IS_NOT_SAVE = 800003;
		public static final Integer ORDER_SHARE_IS_NOT_ALLOW = 800004;
		public static final Integer ORDER_SHARE_IS_UPPER_LIMIT = 800005;
		public static final Integer ORDER_IS_NOT_LOCAL = 800006;
	}
	
	public static final String getMsg(Integer code) {
		return resultMsg.get(code);
	}

	private static Map<Integer, String> resultMsg;

	static {
		resultMsg = new HashMap<Integer, String>();
		resultMsg.put(SUCCESS, "成功");
		resultMsg.put(ERROR, "系统错误");
		resultMsg.put(SESSION_TIMEOUT, "登陆超时");
		resultMsg.put(PARAMS_ERROR, "参数错误");
		resultMsg.put(DATA_ERROR, "数据异常");
		resultMsg.put(ILLEGAL_REQUEST, "非法请求");
		resultMsg.put(AUTHORIZE_ERROR, "授权失败");
		resultMsg.put(DATA_IS_NULL, "获取数据为空");
		resultMsg.put(Function.LOGIN_DISABLED, "无法登陆");
		resultMsg.put(NO_PERMISSION, "没有此权限");
		resultMsg.put(ERROR_OLD_PERMISSION,"原密码错误");
		resultMsg.put(OPERATION_TOO_FREQUENT,"操作过于频繁，请稍后再试");

		resultMsg.put(User.LOGIN_ACCOUNT_IS_NULL, "登陆账号为空，请填写");
		resultMsg.put(User.LOGIN_PASSWORD_IS_NULL, "登陆密码为空，请填写");
		resultMsg.put(User.CAPTCHA_IS_NULL, "图形验证码为空，请填写");
		resultMsg.put(User.CAPTCHA_FAILURE, "图形验证码获取失败，请重新获取");
		resultMsg.put(User.CAPTCHA_ERROR, "图形验证码不正确，请重新填写");
		resultMsg.put(User.CAPTCHA_TIMEOUT, "图形验证码已失效，请重新填写");
		resultMsg.put(User.ACCOUNT_PASSWORD_ERROR, "账号或密码错误");
		resultMsg.put(User.ACCOUNT_NOT_EXIST, "账号不存在");
		resultMsg.put(User.SMSSEND_FAILURE, "信息发送失败，请重新发送");
		resultMsg.put(User.MSG_CAPTCHA_TIMEOUT, "短信验证码已失效，请重新填写");
		resultMsg.put(User.MSG_CAPTCHA_ERROR, "短信验证码不正确，请重新填写");
		resultMsg.put(User.MSG_NULL_ERROR, "未发送验证码，请发送验证码后进行注册");
		resultMsg.put(User.TELEPHONE_BUNDED, "该手机已经注册，请更换手机或使用注册过的账号");
		resultMsg.put(User.MSG_ACCOUNT_MAX, "可使用账号数已达上限");
		resultMsg.put(User.ACCOUNT_EXIST, "账号已存在");
		resultMsg.put(User.TENANTS_IS_UNABLE, "此门店下所有账户已被冻结，请联系工作人员进行解冻");

		resultMsg.put(Product.EXIST_B_M, "对不起，您已报名了该课程");

		resultMsg.put(Banner.UPLOAD_SUCCESS, "Banner图上传成功");
		resultMsg.put(Banner.UPLOAD_ERROR, "Banner图上传失败");

		resultMsg.put(Staff.STAFF_BANK_CODE_IS_NULL, "请选择银行");
		resultMsg.put(Staff.STAFF_BANK_CARD_NO_IS_NULL, "请填写银行卡号");
		resultMsg.put(Staff.STAFF_POLICY_NO_IS_NULL, "请选择保单号");
		resultMsg.put(Staff.STAFF_POLICY_NAME_IS_NULL, "请填写保单名称");
		resultMsg.put(Staff.STAFF_POLICY_AMOUNT_IS_NULL, "请填写保单金额");
		resultMsg.put(Staff.STAFF_POLICY_AGENCY_IS_NULL, "请填写保单机构");
		resultMsg.put(Staff.STAFF_CERT_CERT_NO_IS_NULL, "请填写证件编号");
		resultMsg.put(Staff.STAFF_CERT_CERT_TYPE_IS_NULL, "请选择证件类型");
		resultMsg.put(Staff.STAFF_EDUCATION_IS_NULL, "请选择学历");
		resultMsg.put(Staff.STAFF_HEIGHT_IS_NULL, "请填写身高");
		resultMsg.put(Staff.STAFF_WEIGHT_IS_NULL, "请填写体重");
		resultMsg.put(Staff.STAFF_MARITAL_STATUS_IS_NULL, "请选择婚姻情况");
		resultMsg.put(Staff.STAFF_BLOO_DTYPE_IS_NULL, "请选择血型");
		resultMsg.put(Staff.STAFF_MOBILE_IS_NULL, "请填写手机号码");
		resultMsg.put(Staff.STAFF_FERTILITY_SITUATION_IS_NULL, "请选择生育情况");
		resultMsg.put(Staff.STAFF_LIVE_ADDRESS_IS_NULL, "请填写现居住地址");
		resultMsg.put(Staff.CERT_TYPE_IS_NOT_REPETITION, "此证书已存在");
		resultMsg.put(Staff.STAFF_BANK_ACCOUNT_NAME_IS_NULL, "请填写账户名称");
		resultMsg.put(Staff.CERT_TYPE_IS_REPETITION, "请勿重复添加证书");
		resultMsg.put(Staff.STAFF_IS_EXISTS, "请勿重复录入家政员");

		
		resultMsg.put(Funds.AVAILABLE_AMOUNT_NOT_ENOUGH, "可用额度余额不足");
		resultMsg.put(Funds.FROZEN_AMOUNT_NOT_ENOUGH, "冻结额度余额不足");
		
		resultMsg.put(Order.SHARE_INFO_IS_EXIST, "订单已经被分享，请勿重复操作");
		resultMsg.put(Order.ORDER_CONTRACT_IS_UPPER_LIMIT, "该订单合同上传已达上限");
		resultMsg.put(Order.ORDER_CUSTOMER_IS_NOT_SAVE, "请先保存客户信息");
		resultMsg.put(Order.ORDER_SHARE_IS_NOT_ALLOW, "该订单不能被分享");
		resultMsg.put(Order.ORDER_SHARE_IS_UPPER_LIMIT, "您当前有5笔分享中的订单，请处理完再进行分享");
		resultMsg.put(Order.ORDER_IS_NOT_LOCAL, "该订单不是本地订单");

	}
}
