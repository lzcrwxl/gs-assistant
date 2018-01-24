package com.fbee.modules.service;

import com.fbee.modules.form.TenantsFinanceRecordsForm;
import com.fbee.modules.jsonData.basic.JsonResult;

/**
 * @ClassName: TestService 
 * @Description: TODO
 * @author fwl
 *
 */
public interface FundsService {
	/**
	 * 根据租户id查询账户信息
	 * @param id
	 * @return
	 */
   public JsonResult getByTenantsId(int id);
   /**
    * 根据租户id查询财务总信息
    * @param id
    * @return
    */
   public JsonResult getFinanceTotalByTenantsId(int id);
   /**
    * 根据租户id查询财务流水信息
    * @param tenantsFinanceRecordsForm
    * @return
    */
   
	public JsonResult getFinanceByTenantsId(TenantsFinanceRecordsForm tenantsFinanceRecordsForm,int pageNumber,int pageSize);
}
