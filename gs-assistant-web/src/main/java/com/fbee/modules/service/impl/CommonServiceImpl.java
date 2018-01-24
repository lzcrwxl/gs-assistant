package com.fbee.modules.service.impl;

import com.fbee.modules.basic.WebUtils;
import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.bean.consts.ErrorCode;
import com.fbee.modules.bean.UserBean;
import com.fbee.modules.core.config.Global;
import com.fbee.modules.core.utils.CookieUtils;
import com.fbee.modules.core.utils.DateUtils;
import com.fbee.modules.core.utils.SessionUtils;
import com.fbee.modules.form.SysMenuForm;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.mybatis.dao.SysMenuMapper;
import com.fbee.modules.mybatis.dao.SysPermissionMapper;
import com.fbee.modules.mybatis.dao.TenantsAppsMapper;
import com.fbee.modules.mybatis.dao.TenantsUsersMapper;
import com.fbee.modules.mybatis.dao.TsysMaxNoMapper;
import com.fbee.modules.mybatis.entity.SysMenuEntity;
import com.fbee.modules.mybatis.entity.TenantsUsersEntity;
import com.fbee.modules.mybatis.model.*;
import com.fbee.modules.operation.PermissionOpt;
import com.fbee.modules.redis.JedisTemplate;
import com.fbee.modules.redis.JedisUtils;
import com.fbee.modules.redis.consts.RedisKey;
import com.fbee.modules.service.CommonService;
import com.fbee.modules.service.DealAccountService;
import com.fbee.modules.service.basic.ServiceException;
import com.fbee.modules.utils.JsonUtils;
import com.fbee.modules.wechat.bean.WechatUserinfoBean;
import com.thoughtworks.xstream.mapper.Mapper.Null;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

import javax.servlet.http.HttpSession;

/**
 * 工共实现类
 * @author ZhangJiefrozenAmount
 *
 */

@Service
public class CommonServiceImpl implements CommonService{
	
	Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);

	@Autowired
	TenantsUsersMapper tenantsUsersDao;
	
	@Autowired
	SysPermissionMapper sysPermissionDao;
	
	@Autowired
	SysMenuMapper sysMenuMapper;
	
	@Autowired
	TsysMaxNoMapper tSysMaxNoMapper;
	@Autowired
	DealAccountService dealAccountService;

	@Autowired
	TenantsAppsMapper tenantsAppsMapper;

	private JedisTemplate redis = JedisUtils.getJedisTemplate();


	@Autowired
	private RestTemplate restTemplate;

	@Override
	public synchronized String createOrderNo(String orderType) {
		if (StringUtils.isBlank(orderType)) {
			return null;
		}
		String maxNo = "";
		//获取日期
		String currentDate = DateUtils.dateToStr(new Date(), "yyyy-MM-dd"); 
		currentDate = currentDate.replaceAll("-", "");
		StringBuffer sb = new StringBuffer();
		sb.append(orderType);
		sb.append(currentDate);
		TsysMaxNoExample sysMaxNoExample = new TsysMaxNoExample();
		sysMaxNoExample.createCriteria().andMaxNoLike(sb.toString()+"%").andMaxNoTypeEqualTo(orderType);
		List<TsysMaxNo> maxNoList = tSysMaxNoMapper.selectByExample(sysMaxNoExample);
		if (maxNoList!=null&&maxNoList.size()>0){
			TsysMaxNo sysMaxNo = maxNoList.get(0);
			String orderNo =  sysMaxNo.getMaxNo().substring(2);
			maxNo = String.valueOf(Long.parseLong(orderNo)+1);
			sysMaxNo.setMaxNo(orderType+maxNo);
			tSysMaxNoMapper.updateByPrimaryKey(sysMaxNo);
		}else {
			TsysMaxNo sysMaxNo = new TsysMaxNo();
			maxNo = sb.append("00001").toString();
			sysMaxNo.setMaxNo(maxNo);
			sysMaxNo.setMaxNoType(orderType);
			tSysMaxNoMapper.insert(sysMaxNo);
			return maxNo;
		}
		return Constants.ENV.toUpperCase().charAt(0)+orderType+maxNo;
	}

	/**
	 * 冻结保证金
	 * @param tenantId
	 * @param money
	 */
	@Override
	public void frozenAmount(Integer tenantId, BigDecimal money, String orderNo, String remarks)throws ServiceException {
		if(tenantId == null || money==null){
			throw new ServiceException(String.format("冻结资金操作异常:tenantId[%s],money[%s]", String.valueOf(tenantId), money == null ? "":money.toString()));
		}
		String payType = "02" ; //财务类型 01收入 02支出
		String bussType = "03" ;//交易类型 01：充值 02：提现 03:冻结 04:解冻 05：手续费
		String state = "02" ;   //交易状态 01处理中 02已处理 03交易失败
		//冻结200块保证金-->插入租户交易轨迹表
		dealAccountService.dealAccountTrace(tenantId,bussType,payType, state, money, orderNo, remarks);
		//更新账户总表
		dealAccountService.dealAccount(tenantId,bussType, payType,state, money);

	}
	/**
	 * 解冻保证金
	 * @param tenantId
	 * @param money
	 */
	@Override
	public void thawAmount(Integer tenantId,BigDecimal money, String orderNo, String remarks) throws ServiceException {
		if(tenantId == null || money==null){
			throw new ServiceException(String.format("解冻资金操作异常:tenantId[%s],money[%s]", String.valueOf(tenantId), money == null ? "":money.toString()));
		}
		String payType = "01" ; //财务类型 01收入 02支出
		String bussType = "04" ;//交易类型 01：充值 02：提现 03:冻结 04:解冻 05：手续费
		String state = "02" ;   //交易状态 01处理中 02已处理 03交易失败
		//解冻200块保证金-->插入租户交易轨迹表
		dealAccountService.dealAccountTrace(tenantId,bussType,payType, state, money, orderNo, remarks);
		//更新账户总表
		dealAccountService.dealAccount(tenantId,bussType ,payType,  state, money);
	}

	/**
	 * 冻结成单奖励
	 * @param tenantId

	public void singleReward(Integer tenantId, BigDecimal money){

	String bussType = "03" ;//交易类型 01：充值 02：提现 03:冻结 04:解冻 05：手续费
	String payType = "02" ; //财务类型 01收入 02支出
	String state = "02" ;   //交易状态 01处理中 02已处理 03交易失败
	//冻结成单奖励-->插入租户交易轨迹表
	dealAccountService.dealAccountTrace(tenantId, bussType, payType, state, money);
	//更新账户总表
	dealAccountService.dealAccount(tenantId, bussType, payType, state, money);

	}
	 */

	/**
	 * 生成二维码
	 * code: 01：服务认证  02：视频秀 03：照片墙  04：合同 05：阿姨详情 06:简历详情
	 */
	@Override
	public String getQRCode(String info, String code, String domain, String loginAccount){
		String hostUrl = (String) Global.getConfig("hostUrl");
		//String mobileUrl = (String) Global.getConfig("mobile.host.url");
		String imageUrl = (String)  Global.getConfig("imageUrl");
		String mobileUrl=(String)  Global.getConfig("mobileUrl");
		String url = "imageUrl";
		if("02".equals(code)){
			//url = "http://hfw.jiacersxy.com/upd/videoScan.html?";
			url = hostUrl+"/upd/videoScan.html?";
		}
		if("03".equals(code)){
			//url = "http://hfw.jiacersxy.com/upd/photoScan.html?";
			url = hostUrl+"/upd/photoScan.html?";
		}
		if("04".equals(code)){
			//url = "http://hfw.jiacersxy.com/upd/contractScan.html?";
			url = hostUrl+"/upd/contractScan.html?";
		}
		if("05".equals(code)){
			//url = "http://wxcs.jiacer.com/" + domain + "/staffDetails.html?";
			url = imageUrl+"/" + domain + "/staffDetails.html?";
		}
		if("06".equals(code)){
			//url = "http://wxcs.jiacer.com/" + domain + "/certificateScan.html?";
			url = hostUrl+"/upd/certificateScan.html?";
		}
		if("07".equals(code)){
			//url = mobileUrl+"/job/staffDetails.html?";
		}
		if(!"05".equals(code)){
			url = url + "code=" + code + "&loginAccount=" + loginAccount;
		}
		
		String[] parameters = info.split(",");
		String[] parameter = null;
		for(int i = 0; i < parameters.length; i++){
			parameter = parameters[i].split(":");
			String laststr = url.substring(url.length()-1);
			if(null != laststr && laststr.equals("?")){
				url = url + parameter[0] + "=" + parameter[1];
			}else{
				url = url + "&" + parameter[0] + "=" + parameter[1];
			}
		}
		UserBean userBean= WebUtils.getCurrentUser();
		if(userBean!=null){
			String ua = userBean.getUserId()+ "-"+UUID.randomUUID().toString();
			redis.set(RedisKey.User.UA_QR.getKey(ua), JsonUtils.toJson(userBean), 60 * 60);
			url += "&"+ Constants.QR_UA+"="+ua;
		}

		String commonUrl = Global.getConfig("common.url") + "/qrcode";
		try {
			String content = URLEncoder.encode(url, "utf-8");
			commonUrl = commonUrl + "?content="+content;
			Map<String,String> result = restTemplate.getForObject(commonUrl, Map.class);
			return result.get("result");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
     
	/**
	 * 生成简历详情二维码
	 * 
	 */
	@Override
	public String getResumeDetailQRCode(String staffId,String orderNo,String resumeId){
		String mobileUrl=(String)Global.getConfig("mobileUrl");
		String url=mobileUrl+"/#/resume/auntResume/"+staffId+"?"+"orderNo="+orderNo;
		UserBean userBean= WebUtils.getCurrentUser();
		if(userBean!=null){
			String ua = userBean.getUserId()+ "-"+UUID.randomUUID().toString();
			redis.set(RedisKey.User.UA_QR.getKey(ua), JsonUtils.toJson(userBean), 60 * 60);
			url += "&"+ Constants.QR_UA+"="+ua;
		}
         url+="&"+"resumeId="+resumeId;
		String commonUrl = Global.getConfig("common.url") + "/qrcode";
		try {
			String content = URLEncoder.encode(url, "utf-8");
			commonUrl = commonUrl + "?content="+content;
			Map<String,String> result = restTemplate.getForObject(commonUrl, Map.class);
			return result.get("result");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonResult refreshSession(){
		String token = SessionUtils.getHeaderValue(Constants.AUTH_KEY.TOKEN);
		UserBean userBeanOld = WebUtils.getCurrentUser();
		WechatUserinfoBean wechartUser = null;
		if (StringUtils.isNotBlank(token)) {
			String wechatJson = redis.get(RedisKey.User.UA_WECHAT.getKey(token));
			if (StringUtils.isBlank(wechatJson)) {
				logger.info("wechatJson is null in redis");
				return JsonResult.failure(ErrorCode.WX_AUTH_ERROR);
			}
			wechartUser = JsonUtils.fromJson(wechatJson, WechatUserinfoBean.class);
			if (wechartUser == null) {
				logger.info("wechat is null as convert from wechatJson ");
				return JsonResult.failure(ErrorCode.WX_AUTH_ERROR);
			}
		}
		// 根据用户账号获取用户的信息
		TenantsUsersEntity user = tenantsUsersDao.getById(userBeanOld.getUserId());
		HttpSession session = SessionUtils.getSession();
		session.invalidate();
		session = SessionUtils.getSession();
		UserBean userBean = new UserBean();
		userBean.setLoginAccount(user.getLoginAccount());
		userBean.setTenantId(user.getTenantId());
		userBean.setUserId(user.getId());
		userBean.setUserName(user.getLoginName());
		// 增加用户类型 Baron20170524
		userBean.setUserType(user.getUserType());
		userBean.setMobile(user.getTelephone());

		userBean.setOpenid(user.getOpenId());
		userBean.setUnionId(user.getUnionId());

		// 缓存用户登陆信息
		session.setAttribute(Constants.USER_SESSION, userBean);
		
		logger.info("========userBean :{}", JsonUtils.toJson(userBean));
		if(StringUtils.isNotBlank(token)){
			userBean.setToken(token);
			redis.del(RedisKey.User.UA.getKey(token));
			redis.set(RedisKey.User.UA.getKey(token), JsonUtils.toJson(userBean), 30 * 24 * 60 * 60);
			CookieUtils.setCookie(Constants.UA, token);
		}
		CookieUtils.setCookie(Constants.UID, userBean.getUserId().toString());
		List<SysMenuEntity> sysMenuList = sysPermissionDao.getNoPermissionByUserId(userBean.getUserId());
		List<SysMenuForm> list = new ArrayList<>();
		if (sysMenuList != null && sysMenuList.size() > 0) {
			for (SysMenuEntity menu : sysMenuList) {
				list.add(PermissionOpt.buildSysMenuForm(menu));
			}
		}
		// 用户没有的权限
		redis.del(Constants.USER_NO_PERMISSION + userBean.getUserId());
		redis.set(Constants.USER_NO_PERMISSION + userBean.getUserId(), JsonUtils.toJson(list));
		String userType = user.getUserType();
		if (null != userType && ("01".equals(userType) || "02".equals(userType))) {
			sysMenuList = sysMenuMapper.getMenuList();
		} else {
			sysMenuList = sysPermissionDao.getByUserId(userBean.getUserId());
		}

		list = new ArrayList<>();
		if (sysMenuList != null && sysMenuList.size() > 0) {
			for (SysMenuEntity menu : sysMenuList) {
				list.add(PermissionOpt.buildSysMenuForm(menu));
			}
		}

		// 用户权限
		redis.del(Constants.USER_PERMISSION + userBean.getUserId());
		redis.set(Constants.USER_PERMISSION + userBean.getUserId(), JsonUtils.toJson(list));
		user.setRefresh(Constants.REFRESH_FALSE);
		tenantsUsersDao.update(user);
		return JsonResult.success(userBean);
	}
}
