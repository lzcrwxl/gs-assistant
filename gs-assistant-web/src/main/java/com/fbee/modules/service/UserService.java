package com.fbee.modules.service;

import javax.servlet.http.HttpServletRequest;
import com.fbee.modules.bean.UserBean;
import com.fbee.modules.form.LoginForm;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.json.UserMenusJsonData;

/** 
* @ClassName: UserService 
* @Description: 用户接口服务：用户菜单、用户对象、修改用户密码、获取用户权限
* @author 贺章鹏
* @date 2016年12月27日 下午5:25:04 
*  
*/
public interface UserService {

	/**
	 * 根据用户（租户）缓存对象：用户id和租户id获取用户所拥有的菜单
	 * 一期:所有用户获取所有的菜单
	 * @param userBean
	 * @return
	 */
	public UserMenusJsonData getUserMenus(UserBean userBean);

	/**
	 * 用户（租户）登陆
	 * @param loginForm
	 * @param request
	 * @return
	 */
	public JsonResult login(LoginForm loginForm, HttpServletRequest request);

	/**
	 * 用户（租户）登出
	 * @param request
	 * @return
	 */
	public JsonResult logout(HttpServletRequest request);
	
	/**
	 * 跨域之后重新设置session
	 * @param parseInt
	 * @param loginAccount
	 * @param request
	 * @return
	 */
	public JsonResult setUserSession(int parseInt, String loginAccount, HttpServletRequest request);

	/**
	 * 读取身份证用户登录
	 * @param loginForm
	 * @param request
	 * @return
	 */
	JsonResult controlLogin(LoginForm loginForm, HttpServletRequest request);
	
	JsonResult getMainPageDate(UserBean userBean);
}
