package com.fbee.modules.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import com.fbee.modules.bean.ErrorMsg;
import com.fbee.modules.bean.UserBean;
import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.bean.consts.ErrorCode;
import com.fbee.modules.bean.consts.Status;
import com.fbee.modules.core.Log;
import com.fbee.modules.core.bean.SmsCode;
import com.fbee.modules.core.config.Global;
import com.fbee.modules.core.page.form.TenantJobForm;
import com.fbee.modules.core.page.form.TenantJobResumeForm;
import com.fbee.modules.core.utils.CookieUtils;
import com.fbee.modules.core.utils.SessionUtils;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.form.LoginForm;
import com.fbee.modules.form.OrdersForm;
import com.fbee.modules.form.SysMenuForm;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.jsonData.extend.UserMenusJson;
import com.fbee.modules.jsonData.json.UserMenusJsonData;
import com.fbee.modules.mybatis.dao.OrdersMapper;
import com.fbee.modules.mybatis.dao.ReserveOrdersMapper;
import com.fbee.modules.mybatis.dao.SysMenuMapper;
import com.fbee.modules.mybatis.dao.SysPermissionMapper;
import com.fbee.modules.mybatis.dao.TenantsAppsMapper;
import com.fbee.modules.mybatis.dao.TenantsJobResumesMapper;
import com.fbee.modules.mybatis.dao.TenantsJobsMapper;
import com.fbee.modules.mybatis.dao.TenantsMenusMapper;
import com.fbee.modules.mybatis.dao.TenantsOperateRecordsMapper;
import com.fbee.modules.mybatis.dao.TenantsStaffsInfoMapper;
import com.fbee.modules.mybatis.dao.TenantsUsersMapper;
import com.fbee.modules.mybatis.entity.OrdersEntity;
import com.fbee.modules.mybatis.entity.SysMenuEntity;
import com.fbee.modules.mybatis.entity.TenantsMenusEntity;
import com.fbee.modules.mybatis.entity.TenantsOperateRecordsEntity;
import com.fbee.modules.mybatis.entity.TenantsUsersEntity;
import com.fbee.modules.mybatis.model.TenantsApps;
import com.fbee.modules.operation.PermissionOpt;
import com.fbee.modules.operation.StaffsOpt;
import com.fbee.modules.redis.JedisTemplate;
import com.fbee.modules.redis.JedisUtils;
import com.fbee.modules.redis.consts.RedisKey;
import com.fbee.modules.service.UserService;
import com.fbee.modules.service.basic.BaseService;
import com.fbee.modules.utils.EntryptUtils;
import com.fbee.modules.utils.JsonUtils;
import com.fbee.modules.wechat.bean.WechatUserinfoBean;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @ClassName: UserServiceImpl
 * @Description: TODO
 * @author 贺章鹏
 * @date 2016年12月27日 下午5:43:40
 * 
 */
@Service
public class UserServiceImpl extends BaseService implements UserService {

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	TenantsUsersMapper tenantsUsersDao;

	@Autowired
	TenantsOperateRecordsMapper operateRecorDao;

	@Autowired
	TenantsMenusMapper tenantsMenusDao;

	@Autowired
	TenantsAppsMapper tenantsAppsDao;

	@Autowired
	SysPermissionMapper sysPermissionDao;

	@Autowired
	SysMenuMapper sysMenuMapper;

	@Autowired
	ReserveOrdersMapper reserveOrdersMapper;

	@Autowired
	OrdersMapper orderDao;

	@Autowired
	TenantsStaffsInfoMapper tenantsStaffsInfoDao;

	@Autowired
	TenantsJobsMapper tenantsJobsMapper;

	@Autowired
	TenantsJobResumesMapper tenantsJobResumesMapper;

	private static JedisTemplate redis = JedisUtils.getJedisTemplate();

	@Override
	public UserMenusJsonData getUserMenus(UserBean userBean) {
		Map<String, Object> params = Maps.newHashMap();
		params.put("tenantId", userBean.getTenantId());
		List<TenantsMenusEntity> userMenuList = tenantsMenusDao.getUserMenus(params);

		if (userMenuList == null || userMenuList.size() < 0) {
			return null;
		}

		List<UserMenusJson> reslutList = Lists.newArrayList();
		sortList(reslutList, userMenuList, 1, true);

		UserMenusJsonData userMenusJsonData = new UserMenusJsonData();
		userMenusJsonData.setUserMenus(reslutList);
		return userMenusJsonData;
	}

	public static void sortList(List<UserMenusJson> list, List<TenantsMenusEntity> sourcelist, Integer parentId,
			boolean cascade) {
		UserMenusJson userMenusBean = null;
		for (int i = 0; i < sourcelist.size(); i++) {
			TenantsMenusEntity e = sourcelist.get(i);
			if (e.getParent() != null && e.getParent().getId() != null && e.getParent().getId().equals(parentId)) {
				userMenusBean = new UserMenusJson();
				userMenusBean.setMenuId(e.getId());
				userMenusBean.setMenuName(e.getName());
				userMenusBean.setIcon(e.getIcon());
				userMenusBean.setHref(e.getHref());
				userMenusBean.setChildrenMenus(new ArrayList<UserMenusJson>());
				list.add(userMenusBean);
				if (cascade) {
					// 判断是否还有子节点, 有则继续获取子节点
					for (int j = 0; j < sourcelist.size(); j++) {
						TenantsMenusEntity child = sourcelist.get(j);
						if (child.getParent() != null && child.getParent().getId() != null
								&& child.getParent().getId().equals(e.getId())) {
							sortList(userMenusBean.getChildrenMenus(), sourcelist, e.getId(), true);
							break;
						}
					}
				}
			}
		}
	}

	@Override
	public JsonResult login(LoginForm loginForm, HttpServletRequest request) {
		JsonResult jsonResult = JsonResult.success(null);

		// 微信用户
		WechatUserinfoBean wechartUser = null;
		String token = SessionUtils.getHeaderValue(Constants.AUTH_KEY.TOKEN);
		if ("wx".equals(loginForm.getSource())) {
			if (token == null) {
				return JsonResult.failure(ErrorCode.WX_AUTH_ERROR);
			}

		}
		// 根据用户账号获取用户的信息
		TenantsUsersEntity user = tenantsUsersDao.getByLoginAccount(loginForm.getLoginAccount());

		if (user == null) {
			return JsonResult.failure(ResultCode.User.ACCOUNT_NOT_EXIST);
		}
		if (!user.getPassword()
				.equals(EntryptUtils.entryptUserPassword(loginForm.getPassword().toUpperCase(), user.getSalt()))) {
			return JsonResult.failure(ResultCode.User.ACCOUNT_PASSWORD_ERROR);
		}
		
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
			String openId = wechartUser.getOpenid();
			List<TenantsUsersEntity> list = tenantsUsersDao.getByOpenId(openId);
			if(list != null && list.size() > 0){
				if(list.size() > 1){
					logger.info("user already bind in another account ");
					return JsonResult.failure(ErrorCode.WX_MOBILE_ALREADY_BIND_ERROR);
				}
				String oldOpenId = list.get(0).getOpenId();
				if(user.getOpenId() != null){
					if(!user.getOpenId().equals(oldOpenId)){
						logger.info("user already bind in another account ");
						return JsonResult.failure(ErrorCode.WX_MOBILE_ALREADY_BIND_ERROR);
					}
					if(!openId.equals(user.getOpenId())){
						logger.info("user already bind in another account ");
						return JsonResult.failure(ErrorCode.WX_MOBILE_ALREADY_BIND_ERROR);
					}
				}else{
					logger.info("user already bind in another account ");
					return JsonResult.failure(ErrorCode.WX_MOBILE_ALREADY_BIND_ERROR);
				}
			}
		}

		// 非生产环境不验证图形码
		if ("prod".equalsIgnoreCase(Global.getConfig("env"))) {
			// 微信短信校验
			if ("wx".equals(loginForm.getSource())) {
				// 验证短信验证码
				String mobile = user.getTelephone();
				if (StringUtils.isBlank(mobile)) {
					return JsonResult.failure(ErrorCode.REG_MOBILE_EMPTY);
				}
				String smsJson = redis.get(RedisKey.User.LOGINSMSCODE.getKey(mobile));
				SmsCode smsCode = JsonUtils.fromJson(smsJson, SmsCode.class);

				// session是否过期
				if (smsCode == null) {
					return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
				}

				// 验证码是否过期
				if (smsCode.isExpired()) {
					return JsonResult.failure(ResultCode.User.SMSSEND_TIMEOUT);
				}

				// 验证短信验证码
				String sessionCode = smsCode.getCode();
				if (!loginForm.getCode().toUpperCase().equals(sessionCode.toUpperCase())) {
					return JsonResult.failure(ErrorCode.CODE_ERROR);
				}
			}
		}

		if (wechartUser != null) {
			String openId = wechartUser.getOpenid();
			String oldOpenId = user.getOpenId();
			if (oldOpenId != null && !oldOpenId.equals(openId)) {
				return JsonResult.failure(ErrorCode.WX_OPENID_ALREADY_BIND_ERROR);
			}
			if (StringUtils.isBlank(oldOpenId)) {
				user.setOpenId(openId);
				if("prod".equals(Constants.ENV)){
					user.setUnionId(wechartUser.getUnionid());
				}
				user.setNickName(wechartUser.getNickname());
				tenantsUsersDao.update(user);
			}
		}

		TenantsApps tenantsApps = tenantsAppsDao.selectByPrimaryKey(user.getTenantId());

		if (!"".equals(tenantsApps.getIsUsable()) && null != tenantsApps.getIsUsable()
				&& "0".equals(tenantsApps.getIsUsable())) {
			return JsonResult.failure(ResultCode.User.TENANTS_IS_UNABLE);
		}

		HttpSession session = SessionUtils.getSession(request);
		session.invalidate();
		session = SessionUtils.getSession(request);
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
		if (StringUtils.isBlank(token)) {
			token = RandomStringUtils.random(16, true, true);
		}
		userBean.setToken(token);
		logger.info("========userBean :{}", JsonUtils.toJson(userBean));
		redis.set(RedisKey.User.UA.getKey(token), JsonUtils.toJson(userBean), 30 * 24 * 60 * 60);
		redis.set(RedisKey.User.UA_TOKEN.getKey(user.getId().toString()), token,30 * 24 * 60 * 60);
		CookieUtils.setCookie(Constants.UA, token);
		CookieUtils.setCookie(Constants.UID, userBean.getUserId().toString());

		jsonResult = JsonResult.success(userBean);
		try {
			TenantsOperateRecordsEntity record = new TenantsOperateRecordsEntity();
			record.setOperateAccount(loginForm.getLoginAccount());
			record.setAction(Status.Actions.LOGIN);
			record.setOperateTime(new Date());
			record.setTenantId(user.getTenantId());
			operateRecorDao.insert(record);
		} catch (Exception e) {
			Log.error(String.format(ErrorMsg.SAVE_LOGIN_ERR, e));
		}

		List<SysMenuEntity> sysMenuList = sysPermissionDao.getNoPermissionByUserId(userBean.getUserId());
		List<SysMenuForm> list = new ArrayList<>();
		if (sysMenuList != null && sysMenuList.size() > 0) {
			for (SysMenuEntity menu : sysMenuList) {
				list.add(PermissionOpt.buildSysMenuForm(menu));
			}
		}

		// 用户没有的权限
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
		redis.set(Constants.USER_PERMISSION + userBean.getUserId(), JsonUtils.toJson(list));

		return jsonResult;
	}

	@Override
	public JsonResult controlLogin(LoginForm loginForm, HttpServletRequest request) {
		JsonResult jsonResult = JsonResult.success(null);
		// 根据用户账号获取用户的信息
		TenantsUsersEntity user = tenantsUsersDao.getByLoginAccount(loginForm.getLoginAccount());

		HttpSession session = SessionUtils.getSession(request);
		UserBean userBean = new UserBean();
		userBean.setLoginAccount(user.getLoginAccount());
		userBean.setTenantId(user.getTenantId());
		userBean.setUserId(user.getId());
		userBean.setUserName(user.getLoginName());
		session.setAttribute(Constants.USER_SESSION, userBean);
		jsonResult = JsonResult.success(null);
		try {
			TenantsOperateRecordsEntity record = new TenantsOperateRecordsEntity();
			record.setOperateAccount(loginForm.getLoginAccount());
			record.setAction(Status.Actions.LOGIN);
			record.setOperateTime(new Date());
			record.setTenantId(user.getTenantId());
			operateRecorDao.insert(record);
		} catch (Exception e) {
			Log.error(String.format(ErrorMsg.SAVE_LOGIN_ERR, e));
		}
		return jsonResult;
	}

	@Override
	public JsonResult logout(HttpServletRequest request) {
		HttpSession session = SessionUtils.getSession(request);
		try {
			UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
			TenantsOperateRecordsEntity record = new TenantsOperateRecordsEntity();
			record.setOperateAccount(userBean.getLoginAccount());
			record.setOperateTime(new Date());
			record.setAction(Status.Actions.LOGOUT);
			record.setTenantId(userBean.getTenantId());
			operateRecorDao.insert(record);
			redis.del(RedisKey.User.UA.getKey(CookieUtils.getCookie(request, Constants.UA)));
			CookieUtils.setCookie(Constants.UA, null);
		} catch (Exception e) {
			Log.error(String.format(ErrorMsg.SAVE_LOGOUT_ERR, e));
		}
		session.invalidate();
		return JsonResult.success(null);
	}

	/**
	 * 跨域之后重新设置session
	 */
	public JsonResult setUserSession(int parseInt, String loginAccount, HttpServletRequest request) {
		// 根据用户账号获取用户的信息
		TenantsUsersEntity user = tenantsUsersDao.getByLoginAccount(loginAccount);
		HttpSession session = SessionUtils.getSession(request);
		UserBean userBean = new UserBean();
		userBean.setLoginAccount(user.getLoginAccount());
		userBean.setTenantId(user.getTenantId());
		userBean.setUserId(user.getId());
		userBean.setUserName(user.getLoginName());
		session.setAttribute(Constants.USER_SESSION, userBean);
		return JsonResult.success(null);
	}

	@Override
	public JsonResult getMainPageDate(UserBean userBean) {
		try {
			Map<String, Object> data = new HashMap<>();
			// 新订单总数
			// 获取总条数
			Integer tenantId = userBean.getTenantId();
			String token = SessionUtils.getHeaderValue(Constants.AUTH_KEY.TOKEN);
			if (tenantId == null) {
				String userJson = redis.get(RedisKey.User.UA.getKey(token));
				UserBean user = JsonUtils.fromJson(userJson, UserBean.class);
				tenantId = user.getTenantId();
			}
			logger.info("========getting mainPage info from tenantId : {}", tenantId);
			Map<Object, Object> reserveOrderMap = Maps.newHashMap();
			reserveOrderMap.put("tenantId", tenantId);
			reserveOrderMap.put("orderStatus", "01");
			// 控制子账户权限 Baron 20170525
			if (!"01".equals(userBean.getUserType()) && !"02".equals(userBean.getUserType())) {
				reserveOrderMap.put("addAccount", userBean.getLoginAccount());
				reserveOrderMap.put("userId", userBean.getUserId());
			}
			Integer reserveOrderCount = reserveOrdersMapper.getReserveOrdersCount(reserveOrderMap);
			data.put("reserveOrderCount", reserveOrderCount);

			// 处理中订单
			OrdersEntity ordersEntity = new OrdersEntity();
			OrdersForm ordersForm = new OrdersForm();
			ordersForm.setQueryStatus("process");
			ordersEntity.setQueryStatus(ordersForm.getQueryStatus());
			ordersEntity.setTenantId(tenantId);
			//添加权限 Baron 20170525
            if (!"01".equals(userBean.getUserType()) && !"02".equals(userBean.getUserType())){
            	ordersEntity.setAddAccount(userBean.getLoginAccount());
            }
            if (!"01".equals(userBean.getUserType()) && !"02".equals(userBean.getUserType())){
            	ordersEntity.setUserId(userBean.getUserId());
            }
			Integer orderCount = this.orderDao.getCount(ordersEntity);
			data.put("orderCount", orderCount);

			// 家政员人数
			Map<Object, Object> tenantsStaffMap = StaffsOpt.buildQueryMap(tenantId);
			Integer tenantsStaffCount = tenantsStaffsInfoDao.getStaffQueryCount(tenantsStaffMap);
			data.put("tenantsStaffCount", tenantsStaffCount);

			// 职位招聘数
			TenantJobForm tenantJobForm = new TenantJobForm();
			tenantJobForm.setTenantId(userBean.getTenantId());
			tenantJobForm.setTenantUserId(userBean.getUserId());
			tenantJobForm.setOnlySelf(1);
			tenantJobForm.setVisualExtend("0");
			if ("02".equals(userBean.getUserType()) || "01".equals(userBean.getUserType())) {
				// 如果是管理员
				tenantJobForm.setIsAdmin("1");
			}
			tenantJobForm.setVisualExtend(
					StringUtils.isBlank(tenantJobForm.getVisualExtend()) ? "2" : tenantJobForm.getVisualExtend());
			Integer tenantJobCount = tenantsJobsMapper.getTenantsJobsInfoCount(tenantJobForm);
			data.put("tenantJobCount", tenantJobCount);

			// 简历箱数量
			TenantJobResumeForm tenantJobResumeForm = new TenantJobResumeForm();
			tenantJobResumeForm.setJobTenantId(userBean.getTenantId());
			tenantJobResumeForm.setJobTenantUserId(userBean.getUserId());
			Integer TenantJobResumeCount = tenantsJobResumesMapper.getMyResumesBoxCount(tenantJobResumeForm);
			data.put("TenantJobResumeCount", TenantJobResumeCount);

			// 用户信息
			TenantsApps tenantsApps = (TenantsApps) tenantsAppsDao.getById(tenantId);
			String storeName = tenantsApps.getWebsiteName();
			String userName = userBean.getUserName();
			String mobile = userBean.getMobile();
			data.put("storeName", storeName);
			data.put("userName", userName);
			data.put("mobile", mobile);
			String domain = null;
			if (tenantsApps != null) {
				domain = tenantsApps.getDomain();
			}
			StringBuffer sb = new StringBuffer().append(Global.getConfig("imageUrl")).append("/").append(domain);
			data.put("toUrl", sb.toString());

			String wechartJson = redis.get(RedisKey.User.UA_WECHAT.getKey(token));
			if (wechartJson != null) {
				WechatUserinfoBean wechatUserinfoBean = JsonUtils.fromJson(wechartJson, WechatUserinfoBean.class);
				String headImgUrl = wechatUserinfoBean.getHeadImgUrl();
				data.put("headImgUrl", headImgUrl);
			}
			return JsonResult.success(data);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(ErrorMsg.STAFF_QUERY_ERR, e);
			return JsonResult.failure(ResultCode.DATA_ERROR);
		}
	}

}
