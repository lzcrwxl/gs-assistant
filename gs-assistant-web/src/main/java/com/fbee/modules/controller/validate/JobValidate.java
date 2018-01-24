package com.fbee.modules.controller.validate;

import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.mybatis.model.TenantsJobs;

public class JobValidate {

    /**
     * 校验新增or修改租户招聘信息
     *
     * @return
     */
    public static JsonResult validFormInfo(TenantsJobs tenantsJobs) {

        if (StringUtils.isBlank(tenantsJobs.getAge() + "")) {
            return JsonResult.failure(ResultCode.Job.JOB_AGE_IS_NULL);
        }
        if (StringUtils.isBlank(tenantsJobs.getSalaryType())) {
            return JsonResult.failure(ResultCode.Job.JOB_SLALRY_TYPE_IS_NULL);
        }
        if (tenantsJobs.getSalary() == null) {
            return JsonResult.failure(ResultCode.Job.JOB_SLALRY_IS_NULL);
        }
        if (StringUtils.isBlank(tenantsJobs.getOrderNo())) {
            return JsonResult.failure(ResultCode.Job.JOB_ORDER_IS_NULL);
        }
        return JsonResult.success(null);
    }

}
