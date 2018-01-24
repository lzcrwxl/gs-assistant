package com.fbee.modules.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.fbee.modules.bean.UserBean;
import com.fbee.modules.form.RecommendForm;
import com.fbee.modules.form.ServiceItemsForm;
import com.fbee.modules.form.StaffBankForm;
import com.fbee.modules.form.StaffBaseInfoForm;
import com.fbee.modules.form.StaffBrushCardForm;
import com.fbee.modules.form.StaffCertForm;
import com.fbee.modules.form.StaffFinancialForm;
import com.fbee.modules.form.StaffJobForm;
import com.fbee.modules.form.StaffPolicyForm;
import com.fbee.modules.form.StaffQueryForm;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.extend.StaffWorkInfoJson;

/** 
* @ClassName: StaffsService 
* @Description: 租户员工
* @author 贺章鹏
* @date 2017年1月3日 上午10:49:07 
*  
*/
public interface StaffsService {
	

	/**
	 * 新增员工信息
	 * @param tenantId
	 * @param staffBaseInfoForm
	 * @return
	 */
	JsonResult addBaseInfo(Integer tenantId, StaffBaseInfoForm staffBaseInfoForm, Integer isAdd);

	/**
	 * 保存（新增or修改）员工（阿姨）银行卡信息
	 * @param tenantId
	 * @param staffBankForm
	 * @return
	 */
	JsonResult saveStaffBank(Integer tenantId, StaffBankForm staffBankForm);

	/**
	 * 保存（新增or修改）员工（阿姨）保单信息
	 * @param tenantId
	 * @param staffPolicyForm
	 * @return
	 */
	JsonResult saveStaffPolicy(Integer tenantId, StaffPolicyForm staffPolicyForm);

	/**
	 * 保存（新增or修改）员工（阿姨）求职信息
	 * @param tenantId
	 * @param staffJobForm
	 * @return
	 */
	JsonResult saveStaffJob(Integer tenantId, StaffJobForm staffJobForm);

	/**
	 * 保存（新增or修改）工种信息
	 * @param tenantId
	 * @param staffJobForm
	 * @return
	 */
	JsonResult saveStaffItemsInfo(Integer tenantId, StaffJobForm staffJobForm);


	/**
	 * 删除工种
	 * @param tenantId
	 * @param staffJobForm
	 * @return
	 */
	JsonResult delStaffItemsInfo(Integer tenantId, StaffJobForm staffJobForm);


	/**
	 * 
	 * @param tenantId
	 * @param staffQueryForm
	 * @param pageSize 
	 * @param pageNumber 
	 * @return
	 */
	JsonResult queryStaff(Integer tenantId, StaffQueryForm staffQueryForm, int pageNumber, int pageSize);
	
	/**
	 * 获取员工的服务工种中文
	 * @param tenantId
	 * @param staffId
	 * @return
	 */
	String getStaffServiceItems(Integer tenantId,Integer staffId);

	/**
	 * 根据租户id和staffId获取阿姨的详情
	 * @param tenantId
	 * @param staffId
	 * @return
	 */
	JsonResult getDetails(Integer tenantId, Integer staffId, String orderNo);
	JsonResult getDetailsPartBase(Integer tenantId, Integer staffId, String orderNo);
	JsonResult getDetailsPartMedia(Integer tenantId, Integer staffId);
	JsonResult getDetailsPartFinance(Integer tenantId, Integer staffId);
	JsonResult getDetailsPartRecord(Integer tenantId, Integer staffId);
	
	/**
	 * 根据租户id和staffId获取银行卡信息
	 * @param tenantId
	 * @param staffId
	 * @return
	 */
	JsonResult getBankInfo(Integer tenantId, Integer staffId);
	
	/**
	 * 根据租户id和staffId获取保单信息
	 * @param tenantId
	 * @param staffId
	 * @return
	 */
	JsonResult getPolicyList(Integer tenantId, Integer staffId);
	
	/**
	 * 根据租户id和staffId获取财务记录
	 * @param tenantId
	 * @param staffId
	 * @return
	 */
	JsonResult getFinanceInfo(Integer tenantId, Integer staffId);
	
	/**
	 * 根据租户id和staffId获取财务记录
	 * @param tenantId
	 * @param staffId
	 * @return
	 */
	JsonResult getWorkList(Integer tenantId, Integer staffId);
	/**
	 * 保存证件信息
	 * @param tenantId
	 * @param staffCertForm
	 * @return
	 */
	JsonResult saveStaffCert(Integer tenantId, StaffCertForm staffCertForm,String valid);

	/**
	 * 设置阿姨默认图片
	 * @param staffId
	 * @param imageId
	 * @return
	 */
	JsonResult addImageDefault(Integer staffId, Integer imageId);

	/**
	 * 阿姨上下架
	 * @param staffId
	 * @param onOff
	 * @return
	 */
	JsonResult staffOnOff(Integer staffId, String onOff);

	/**
	 * 根据租户id获取服务工种列表中文
	 * @param tenantId
	 * @return
	 */
	JsonResult getStaffServiceItemList(Integer tenantId);
	
	/**
	 * 根据租户id获取服务工种中文
	 * @param serviceItemCode
	 * @param tenantId
	 * @return
	 */
	JsonResult selectByPrimaryKey(String serviceItemCode, Integer tenantId);
	
	/**
	 * 更新服务工种
	 * @return
	 */
	JsonResult updateByPrimaryKeySelective(ServiceItemsForm serviceItemsForm);
	
	/**
	 * 获取推荐阿姨列表
	 * @param tenantId
	 * @return
	 */
	JsonResult selectRecommendList(Integer tenantId);
	
	/**
	 * 更新推荐阿姨列表
	 * @return
	 */
	JsonResult updateRecommend(RecommendForm recommendForm);
	/**
	 * 新增推荐阿姨
	 * @return
	 */
	JsonResult saveRecommend(RecommendForm recommendForm);
	
	/**
	 * 获取阿姨列表
	 * @param tenantId
	 * @return
	 */
	JsonResult selectStaffInfoList(Integer tenantId, Integer pageNumber, Integer pageSize);
	
	/**
	 * 获取阿姨个人信息
	 * @param tenantId
	 * @param staffId
	 * @return
	 */
	JsonResult getStaffInfoByStaffId(Integer tenantId, Integer staffId);
	
	/**
	 * 模糊查询获取阿姨列表
	 * @param tenantId
	 * @param staffName
	 * @return
	 */
	JsonResult getStaffInfoLikeStaffName(Integer tenantId, String staffName, int pageNumber, int pageSize);

	/**
	 * 获取租户服务工种serviceItemCode
	 * @param tenantId
	 * @return
	 */
	List<Map<String, String>> getStaffServiceItemCodes(Integer tenantId);

	/**
	 * 编辑服务工种提交
	 * @return
	 */
	JsonResult editServiceItem(ServiceItemsForm serviceItemsForm,MultipartFile file);
	
	/**
	 * 上传照片，视频路径
	 * @param userBean
	 * @param staffId
	 * @return
	 */
	JsonResult uploadVideoPath(UserBean userBean,String[] videoPath,String[] imagePath, Integer staffId, Integer isWhole);
	
	/**
	 * 删除照片墙
	 * @param userBean
	 * @param id
	 * @return 
	 */
	JsonResult deletePhotoWall(UserBean userBean, Integer id);
	/**
	 * 设置封面
	 * @param userBean
	 * @param id
	 * @return
	 */
	JsonResult setCover(UserBean userBean,Integer staffId ,Integer id);
	
	/**
	 * 添加阿姨财务记录
	 * @param staffFinancialForm
	 * @return
	 */
	JsonResult saveFinancialRecord(StaffFinancialForm staffFinancialForm);

	/**
	 * 查询阿姨的工作安排表
	 */
	List<StaffWorkInfoJson> getStaffWorkList(Integer staffId);

	/**
	 * 阿姨当前时间是否在工作中
	 */
	String staffIsWorkNow(Integer staffId);
	
	/**
	 * 删除未通过的证书
	 * @param staffId
	 * @author xiehui
	 * @return 
	 */
	JsonResult deleteCert(Integer staffId,Integer id);
	
	/**
	 * 证书校验
	 * @param staffId
	 * @author xiehui
	 * @return 
	 */
	JsonResult certValid(Integer staffId,String authenticateGrade,String certType);


}
