package com.fbee.modules.form;

public class ResetPasswordForm {
	private String telPhoneNumber;// 电话号码
	private String captcha;// 图片验证码
	private String messageCaptcha;// 短信验证码
	private String newPassword;// 新密码
	private String oldPassword;//老密码

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getTelPhoneNumber() {
		return telPhoneNumber;
	}

	public void setTelPhoneNumber(String telPhoneNumber) {
		this.telPhoneNumber = telPhoneNumber;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getMessageCaptcha() {
		return messageCaptcha;
	}

	public void setMessageCaptcha(String messageCaptcha) {
		this.messageCaptcha = messageCaptcha;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
