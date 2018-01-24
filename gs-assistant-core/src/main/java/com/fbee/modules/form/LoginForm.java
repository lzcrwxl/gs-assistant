package com.fbee.modules.form;

import com.fbee.modules.core.persistence.ModelSerializable;

/** 
* @ClassName: LoginForm 
* @Description: TODO
* @author 贺章鹏
* @date 2016年12月27日 下午6:37:39 
*  
*/
public class LoginForm implements ModelSerializable{

	private static final long serialVersionUID = 1L;
	
	private String loginAccount;//登陆账号
	
	private String captcha;//验证码
	private String captchaKey;

	private String password;//密码
	
	private String source;//登陆方式	
	private String code; //短信校验码
	
	public String getCaptchaKey() {
		return captchaKey;
	}

	public void setCaptchaKey(String captchaKey) {
		this.captchaKey = captchaKey;
	}

	public String getLoginAccount() {
		return loginAccount;
	}

	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString(){
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("user login params,{loginAccount:").append(loginAccount)
			.append(",password:").append(password).append(",captcha:").append(captcha);
		return stringBuilder.toString();
	}
}
