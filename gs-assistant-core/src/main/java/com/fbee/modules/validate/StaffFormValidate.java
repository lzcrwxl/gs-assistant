package com.fbee.modules.validate;

import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.form.StaffBankForm;
import com.fbee.modules.form.StaffBaseInfoForm;
import com.fbee.modules.form.StaffCertForm;
import com.fbee.modules.form.StaffJobForm;
import com.fbee.modules.form.StaffPolicyForm;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;

/** 
* @ClassName: StaffFormValidate 
* @Description: TODO
* @author 贺章鹏
* @date 2017年1月3日 上午10:45:01 
*  
*/
public class StaffFormValidate {

	/**
	 * 校验新增员工(阿姨)基础信息
	 * @param staffBaseInfoForm
	 * @return
	 */
	public static JsonResult validAddBaseInfo(StaffBaseInfoForm staffBaseInfoForm) {

		if(StringUtils.isBlank(staffBaseInfoForm.getEducation())){
			return JsonResult.failure(ResultCode.Staff.STAFF_EDUCATION_IS_NULL);
		}
		if(staffBaseInfoForm.getHeight()==null){
			return JsonResult.failure(ResultCode.Staff.STAFF_HEIGHT_IS_NULL);
		}
		if(StringUtils.isBlank(staffBaseInfoForm.getMaritalStatus())){
			return JsonResult.failure(ResultCode.Staff.STAFF_MARITAL_STATUS_IS_NULL);
		}
		if(StringUtils.isBlank(staffBaseInfoForm.getBloodType())) {
			return JsonResult.failure(ResultCode.Staff.STAFF_BLOO_DTYPE_IS_NULL);
		}
		if(StringUtils.isBlank(staffBaseInfoForm.getMobile())) {
			return JsonResult.failure(ResultCode.Staff.STAFF_MOBILE_IS_NULL);
		}
		return JsonResult.success(null);
	}

	/**
	 * 校验新增or修改员工（阿姨）银行卡信息
	 * @param staffBankForm
	 * @return
	 */
	public static JsonResult validBankInfo(StaffBankForm staffBankForm) {
		
		if(StringUtils.isBlank(staffBankForm.getBankCode())){
			return JsonResult.failure(ResultCode.Staff.STAFF_BANK_CODE_IS_NULL);
		}
		
		if(StringUtils.isBlank(staffBankForm.getCardNo())){
			return JsonResult.failure(ResultCode.Staff.STAFF_BANK_CODE_IS_NULL);
		}
		
		if(StringUtils.isBlank(staffBankForm.getAccountName())){
			return JsonResult.failure(ResultCode.Staff.STAFF_BANK_ACCOUNT_NAME_IS_NULL);
		}
		
		if(staffBankForm.getStaffId()==null){
			return JsonResult.failure(ResultCode.PARAMS_ERROR);
		}
		
		return JsonResult.success(null);
	}

	/**
	 * 校验新增or修改员工（阿姨）保单信息
	 * @param staffPolicyForm
	 * @return
	 */
	public static JsonResult validPolicyInfo(StaffPolicyForm staffPolicyForm) {
		if(StringUtils.isBlank(staffPolicyForm.getPolicyNo())){
			return JsonResult.failure(ResultCode.Staff.STAFF_POLICY_NO_IS_NULL);
		}
		
		if(StringUtils.isBlank(staffPolicyForm.getPolicyName())){
			return JsonResult.failure(ResultCode.Staff.STAFF_POLICY_NAME_IS_NULL);
		}
		
		if(staffPolicyForm.getPolicyAmount()==null){
			return JsonResult.failure(ResultCode.Staff.STAFF_POLICY_AMOUNT_IS_NULL);
		}
		
		if(StringUtils.isBlank(staffPolicyForm.getPolicyAgency())){
			return JsonResult.failure(ResultCode.Staff.STAFF_POLICY_AGENCY_IS_NULL);
		}
		
		if(staffPolicyForm.getStaffId()==null){
			return JsonResult.failure(ResultCode.PARAMS_ERROR);
		}
		return JsonResult.success(null);
	}

	/**
	 * 校验新增or修改员工（阿姨）求职信息
	 * @param staffJobForm
	 * @return
	 */
	public static JsonResult validJobInfo(StaffJobForm staffJobForm) {
		
		if(staffJobForm.getStaffId()==null){
			return JsonResult.failure(ResultCode.PARAMS_ERROR);
		}
		return JsonResult.success(null);
	}

	/**
	 * @param staffCertForm
	 * @return
	 */
	public static JsonResult validCertInfo(StaffCertForm staffCertForm) {
		if (staffCertForm.getStaffId() == null) {
			return JsonResult.failure(ResultCode.PARAMS_ERROR);
		}
		if (StringUtils.isBlank(staffCertForm.getCertType())) {
			return JsonResult.failure(ResultCode.Staff.STAFF_CERT_CERT_TYPE_IS_NULL);
		}
		if (StringUtils.isBlank(staffCertForm.getCertNo())) {
			return JsonResult.failure(ResultCode.Staff.STAFF_CERT_CERT_NO_IS_NULL);
		}
		return JsonResult.success(null);
	}

}
