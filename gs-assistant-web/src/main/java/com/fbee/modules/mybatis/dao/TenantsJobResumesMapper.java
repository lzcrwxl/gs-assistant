package com.fbee.modules.mybatis.dao;

import com.fbee.modules.core.page.form.TenantJobResumeForm;
import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.model.TenantsJobResume;
import com.fbee.modules.mybatis.model.TenantsJobs;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

import java.util.List;

@MyBatisDao
public interface TenantsJobResumesMapper extends CrudDao<TenantsJobResume>{

    Integer getMyResumesBoxCount(TenantJobResumeForm form);

    List<TenantsJobResume> getMyResumesBoxList(TenantJobResumeForm form);

    Integer getMyApplyResumesCount(TenantJobResumeForm form);

    List<TenantsJobResume> getMyApplyResumesList(TenantJobResumeForm form);

    TenantsJobResume getByJobStaff(@Param("jobId") Integer jobId, @Param("staffId") Integer staffId);

    List<TenantsJobResume> getDelayResumeList();

    TenantsJobResume getByOrderStaff(@Param("orderNo") String orderNo, @Param("staffId") Integer staffId);
}
