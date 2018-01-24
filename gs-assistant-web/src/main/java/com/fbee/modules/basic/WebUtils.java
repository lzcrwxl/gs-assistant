package com.fbee.modules.basic;

import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.bean.UserBean;
import com.fbee.modules.core.utils.SessionUtils;

public class WebUtils {

    public static UserBean getCurrentUser(){
        UserBean userBean=(UserBean) SessionUtils.getSession().getAttribute(Constants.USER_SESSION);
        return userBean;
    }
}
