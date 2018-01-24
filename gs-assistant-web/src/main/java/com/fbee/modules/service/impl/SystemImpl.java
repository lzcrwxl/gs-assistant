package com.fbee.modules.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbee.modules.core.Log;
import com.fbee.modules.form.ResetPasswordForm;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.mybatis.dao.TenantsUsersMapper;
import com.fbee.modules.mybatis.entity.TenantsUsersEntity;
import com.fbee.modules.service.SystemService;
import com.fbee.modules.service.basic.BaseService;
import com.fbee.modules.utils.EntryptUtils;

@Service
public class SystemImpl extends BaseService implements SystemService {
	@Autowired
	TenantsUsersMapper tenantsUsersDao;
    
	@Override
	public JsonResult resetPassword(ResetPasswordForm resetPasswordForm, int id) {
		// 重置用户密码
		try{
			JsonResult jsonResult = JsonResult.success(null);
			TenantsUsersEntity  OldpwdtenantsUsersEntity= tenantsUsersDao.getById(id);
			TenantsUsersEntity tenantsUsersEntity = new TenantsUsersEntity();
			tenantsUsersEntity.setId(id);
			tenantsUsersEntity.setSalt(OldpwdtenantsUsersEntity.getSalt());
			String newPassword = resetPasswordForm.getNewPassword();
			tenantsUsersEntity.setPassword(EntryptUtils.entryptUserPassword(newPassword, OldpwdtenantsUsersEntity.getSalt()));
            if(OldpwdtenantsUsersEntity.getPassword().equals(EntryptUtils.entryptUserPassword(resetPasswordForm.getOldPassword(), OldpwdtenantsUsersEntity.getSalt()))){
				tenantsUsersDao.update(tenantsUsersEntity);
				return jsonResult;
			}else{
				return JsonResult.failure(ResultCode.ERROR_OLD_PERMISSION);
			}
		}catch (Exception e) {
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}
	}

	public static void main(String[] args) {
		System.out.println(EntryptUtils.entryptUserPassword("123456","123456"));
	}

	@Override
	public JsonResult getTelephoneByUserId(int id) {
		String telephone=tenantsUsersDao.getTelephoneByUserId(id);
		Map<String,String> map=new HashMap<String,String>();
		map.put("telephone", telephone);
		return JsonResult.success(map);
	}

}
