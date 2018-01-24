package com.fbee.modules.jsonData.extend;

import java.util.List;
import com.fbee.modules.core.persistence.ModelSerializable;

/** 
* @ClassName: UserMenusJson 
* @Description: 用户菜单--用于返回json
* @author 贺章鹏
* @date 2016年12月27日 下午5:51:25 
*  
*/
public class UserMenusJson implements ModelSerializable{

	private static final long serialVersionUID = 1L;
	
	private Integer menuId;//菜单id
	
	private String menuName;//菜单名称
	
	private String href;//请求链接
	
	private String icon;//菜单图标
	
	private List<UserMenusJson> childrenMenus;//子级菜单

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<UserMenusJson> getChildrenMenus() {
		return childrenMenus;
	}

	public void setChildrenMenus(List<UserMenusJson> childrenMenus) {
		this.childrenMenus = childrenMenus;
	}

}
