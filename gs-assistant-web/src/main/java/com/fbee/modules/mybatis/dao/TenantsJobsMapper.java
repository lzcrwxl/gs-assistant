package com.fbee.modules.mybatis.dao;

import com.fbee.modules.core.page.form.TenantJobForm;
import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.model.TenantsJobs;

import java.util.List;

@MyBatisDao
public interface TenantsJobsMapper extends CrudDao<TenantsJobs>{

	/**
	 * 招聘信息总数
	 * @return
	 */
	Integer getTenantsJobsInfoCount(TenantJobForm form);

	/**
	 * 查询招聘总数
	 * @return
	 */
	List<TenantsJobs> getTenantsJobsInfoList(TenantJobForm form);

    int useOneResumeNum(TenantsJobs applyJob);

	TenantsJobs getByOrderNo(String orderNo);

    void resetRefresh();
}