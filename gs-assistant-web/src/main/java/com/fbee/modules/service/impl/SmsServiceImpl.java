package com.fbee.modules.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbee.modules.bean.consts.Status;
import com.fbee.modules.core.Log;
import com.fbee.modules.core.bean.SmsCode;
import com.fbee.modules.core.sms.SmsSendResult;
import com.fbee.modules.core.sms.SmsTemplates;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.mybatis.dao.SmsRecordsMapper;
import com.fbee.modules.mybatis.entity.SmsRecordsEntity;
import com.fbee.modules.redis.JedisTemplate;
import com.fbee.modules.redis.JedisUtils;
import com.fbee.modules.redis.consts.RedisKey;
import com.fbee.modules.service.SendSmsService;
import com.fbee.modules.service.SmsService;
import com.fbee.modules.service.basic.BaseService;
import com.fbee.modules.utils.JsonUtils;

@Service("smsService")
public class SmsServiceImpl extends BaseService implements SmsService {

	SendSmsService sendSms = new SendSmsYTX();
	
	@Autowired
	SmsRecordsMapper smsRecordDao;
	
	private static JedisTemplate redis = JedisUtils.getJedisTemplate();

	@Override
	public SmsCode sendResetPwdSmsCode(String mobile) {
		SmsCode smsCode = null;
		String code = String.valueOf(new Random().nextInt(900000) + 100000); // 重置密码验证码6位随机数字
		int liveMinites = 10; // 有效时间10分钟
		String[] datas = new String[] { code, liveMinites + "分钟" };
		String templateId = SmsTemplates.RESET_PWD_SMS_CODE; // 模板ID
		String content = generateSmsContent(templateId, datas); // 短信内容

		SmsRecordsEntity entity = new SmsRecordsEntity();
		entity.setMobile(mobile);
		entity.setContentType(Status.SmsContentType.RESET_PWD_SMS_CODE);
		entity.setContent(content);

		// 发送短信
		SmsSendResult sendResult = sendSms.send(mobile, templateId, datas);
		if (SmsSendResult.SUCCESS.equals(sendResult.getResultCode())) { // 发送成功
			entity.setResult(Status.SmsResult.SUBMIT_SUCCESS);
			entity.setSmsId(sendResult.getSmsMessageSid());
			entity.setAddTime(new Date());
			smsCode = new SmsCode(mobile, code, liveMinites);
			smsCode.setCreateTime(new Date().getTime());
		} else { // 发送失败
			entity.setResult(Status.SmsResult.SUBMIT_FAILURE);
			entity.setFailedReason(sendResult.getResultMsg());
			entity.setAddTime(new Date());
		}

		try {
			// 保存发送短信记录
			entity.setAddTime(new Date());
			smsRecordDao.insert(entity);
		} catch (Exception e) {
			Log.error("保存发送短信记录异常", e);
		}

		return smsCode;
	}
	
	@Override
	public  SmsCode sendPaySuccess(String mobile,String companyName,String serviceName,String serviceStartTime) {
		String[] datas = new String[] {companyName,serviceName,serviceStartTime};
		String templateId = SmsTemplates.PAY_SUCCESS; // 模板ID
		String content = generateSmsContent(templateId, datas); // 短信内容
		SmsRecordsEntity entity = new SmsRecordsEntity();
		entity.setMobile(mobile);
		entity.setContentType(Status.SmsContentType.PAY_SUCCESS);
		entity.setContent(content);

		// 发送短信
		SmsSendResult sendResult = sendSms.send(mobile, templateId, datas);
		if (SmsSendResult.SUCCESS.equals(sendResult.getResultCode())) { // 发送成功
			entity.setResult(Status.SmsResult.SUBMIT_SUCCESS);
			entity.setSmsId(sendResult.getSmsMessageSid());
		} else { // 发送失败
			entity.setResult(Status.SmsResult.SUBMIT_FAILURE);
			entity.setFailedReason(sendResult.getResultMsg());
		}

		try {
			// 保存发送短信记录
			entity.setAddTime(new Date());
			smsRecordDao.insert(entity);
		} catch (Exception e) {
			Log.error("保存发送短信记录异常", e);
		}

		return null;
	}
	//注册系统用户时 发送验证码
		@Override
		public Map<String, Object> sendAddCheckMes(String mobile) {
			Map<String, Object> map=new HashMap<String, Object>();
			SmsCode smsCode = null;
			String code = String.valueOf(new Random().nextInt(900000) + 100000); // 注册用户验证码6位随机数字
			int liveMinites = 10; // 有效时间10分钟
			String[] datas = new String[] { code, liveMinites + "" };
			String templateId = SmsTemplates.REG_ASS_SMS_CODE; // 模板ID
			String content = generateSmsContent(templateId, datas); // 短信内容

			SmsRecordsEntity entity = new SmsRecordsEntity();
			entity.setMobile(mobile);
			entity.setContentType(Status.SmsContentType.REG_ASS_SMS_CODE);
			entity.setContent(content);

			// 发送短信
			SmsSendResult sendResult = sendSms.send(mobile, templateId, datas);
			if (SmsSendResult.SUCCESS.equals(sendResult.getResultCode())) { // 发送成功
				entity.setResult(Status.SmsResult.SUBMIT_SUCCESS);
				entity.setSmsId(sendResult.getSmsMessageSid());
				entity.setAddTime(new Date());
				smsCode = new SmsCode(mobile, code, liveMinites);
				smsCode.setCreateTime(new Date().getTime());
			} else { // 发送失败
				entity.setResult(Status.SmsResult.SUBMIT_FAILURE);
				entity.setFailedReason(sendResult.getResultMsg());
				entity.setAddTime(new Date());
			}

			try {
				// 保存发送短信记录
				entity.setAddTime(new Date());
				smsRecordDao.insert(entity);
			} catch (Exception e) {
				Log.error("保存发送短信记录异常", e);
			}
              
			map.put("smsCode", smsCode);
			map.put("smsRecordsEntity", entity);
			return map;
		}

	/**
	 * 生成短信内容
	 * 
	 * @param templateId
	 *            短信模板ID
	 * @param datas
	 *            参数
	 * @return
	 */
	private String generateSmsContent(String templateId, String[] datas) {
		String content = SmsTemplates.getContent(templateId); // 模板内容
		if (StringUtils.isNotBlank(content)) {
			int len = datas.length;
			String key = null;
			String value = null;
			for (int i = 0; i < len; i++) {
				key = "{" + (i + 1) + "}";
				value = datas[i] == null ? "" : datas[i];
				try {
					content = content.replace(key, value);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return content;
	}

	@Override
	public SmsCode sendLoginSmsCode(String mobile) {
		SmsCode smsCode = null;
		String code = String.valueOf(new Random().nextInt(900000) + 100000); // 重置密码验证码6位随机数字
		//查询旧的短信
        String smsOldJson = redis.get(RedisKey.User.LOGINSMSCODE.getKey(mobile));
        if(StringUtils.isNotBlank(smsOldJson)){
        	SmsCode smsOldCode = JsonUtils.fromJson(smsOldJson, SmsCode.class);
        	redis.set(RedisKey.User.LOGINSMSCODE.getKey(mobile), smsOldJson,60*5);
        	code = smsOldCode.getCode();
        }
		int liveMinites = 10; // 有效时间10分钟
		String[] datas = new String[] { code, liveMinites + "分钟" };
		String templateId = SmsTemplates.LOGIN_SMS_CODE; // 模板ID
		String content = generateSmsContent(templateId, datas); // 短信内容

		SmsRecordsEntity entity = new SmsRecordsEntity();
		entity.setMobile(mobile);
		entity.setContentType(Status.SmsContentType.REG_SMS_CODE);
		entity.setContent(content);

		// 发送短信
		SmsSendResult sendResult = sendSms.send(mobile, templateId, datas);
		if (SmsSendResult.SUCCESS.equals(sendResult.getResultCode())) { // 发送成功
			entity.setResult(Status.SmsResult.SUBMIT_SUCCESS);
			entity.setSmsId(sendResult.getSmsMessageSid());
			entity.setAddTime(new Date());
			smsCode = new SmsCode(mobile, code, liveMinites);
			smsCode.setCreateTime(new Date().getTime());
		} else { // 发送失败
			entity.setResult(Status.SmsResult.SUBMIT_FAILURE);
			entity.setFailedReason(sendResult.getResultMsg());
			entity.setAddTime(new Date());
		}

		try {
			// 保存发送短信记录
			entity.setAddTime(new Date());
			smsRecordDao.insert(entity);
		} catch (Exception e) {
			Log.error("保存发送短信记录异常", e);
		}

		return smsCode;
	}

}
