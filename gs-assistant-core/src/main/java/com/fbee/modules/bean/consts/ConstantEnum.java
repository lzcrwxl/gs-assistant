package com.fbee.modules.bean.consts;

/**
 * 枚举类型
 * @author Administrator
 *
 */
public interface ConstantEnum {
	
	/**
	 * 用户类型
	 * @author Administrator
	 *
	 */
	enum UserType {
		超级管理员("01"), 管理员("02"), 业务员("03");
		
		private String code;
		private UserType(String code) {
			this.code = code;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		
	}

}
