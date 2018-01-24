package com.fbee.modules.jsonData.json;

import com.fbee.modules.core.persistence.ModelSerializable;

/** 
* @ClassName: CaptchaJsonData 
* @Description: 用户验证码jsonData
* @author 贺章鹏
* @date 2016年11月15日 上午11:16:15 
*  
*/
public class CaptchaJsonData implements ModelSerializable{
	private static final long serialVersionUID = 1L;
	
	private String captcha;

	private String captchaKey;

	public String getCaptchaKey() {
		return captchaKey;
	}

	public void setCaptchaKey(String captchaKey) {
		this.captchaKey = captchaKey;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
}
