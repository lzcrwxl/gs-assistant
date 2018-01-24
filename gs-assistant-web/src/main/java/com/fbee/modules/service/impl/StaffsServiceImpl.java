package com.fbee.modules.service.impl;

import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.basic.WebUtils;
import com.fbee.modules.bean.ErrorMsg;
import com.fbee.modules.bean.consts.Status;
import com.fbee.modules.bean.UserBean;
import com.fbee.modules.core.Log;
import com.fbee.modules.core.page.Page;
import com.fbee.modules.core.utils.DateUtils;
import com.fbee.modules.core.utils.SessionUtils;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.form.*;
import com.fbee.modules.form.extend.StaffServiceItemform;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.jsonData.extend.*;
import com.fbee.modules.jsonData.json.StaffDetailJsonData;
import com.fbee.modules.mybatis.dao.*;
import com.fbee.modules.mybatis.entity.*;
import com.fbee.modules.mybatis.model.*;
import com.fbee.modules.operation.StaffsOpt;
import com.fbee.modules.service.CommonService;
import com.fbee.modules.service.StaffsService;
import com.fbee.modules.service.basic.BaseService;
import com.fbee.modules.service.basic.ServiceException;
import com.fbee.modules.utils.DictionarysCacheUtils;
import com.fbee.modules.utils.JsonUtils;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * @author 贺章鹏
 * @ClassName: StaffsServiceImpl
 * @Description: 租户员工服务
 * @date 2017年1月3日 上午10:49:56
 */
@Service
public class StaffsServiceImpl extends BaseService implements StaffsService {

    private final static Logger log = LoggerFactory.getLogger(StaffsServiceImpl.class);

    @Autowired
    TenantsStaffsInfoMapper tenantsStaffsInfoDao;

    @Autowired
    TenantsStaffBankMapper tenantsStaffBankDao;

    @Autowired
    TenantsStaffPolicyMapper tenantsStaffPolicyDao;

    @Autowired
    TenantsStaffJobInfoMapper tenantsStaffJobDao;

    @Autowired
    TenantsStaffSerItemsMapper tenantsStaffSerItemsDao;

    @Autowired
    TenantsStaffsMediaMapper tenantsStaffsMediaDao;

    @Autowired
    TenantsStaffCertsInfoMapper tenantsStaffCertsDao;

    @Autowired
    TenantsFinanceRecordsMapper tenantsFinanceRecordsDao;

    @Autowired
    OrdersMapper ordersDao;

    @Autowired
    OrderCustomersInfoMapper orderCustomersDao;

    @Autowired
    TenantsServiceItemsMapper tenantsServiceItemsDao;

    @Autowired
    TenantsRecommendMapper tenantsRecommendDao;

    @Autowired
    CommonService commonService;

    @Autowired
    TenantsStaffJobInfoMapper tenantsStaffJobInfoMapper;
    
    @Autowired
    TenantsUsersMapper tenantsUsersMapper;

    @Autowired
    TenantsJobResumesMapper tenantsJobResumesMapper;

    /**
     * 新增/修改员工信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult addBaseInfo(Integer tenantId, StaffBaseInfoForm staffBaseInfoForm, Integer isAdd) {

        log.info(String.format("add base info : tenant_id[%s], staff [%s]", tenantId, JsonUtils.toJson(staffBaseInfoForm)));
        if (isAdd != null && isAdd.equals(1)) {
            if(StringUtils.isBlank(staffBaseInfoForm.getCertImage())){
                return JsonResult.failure(ResultCode.Staff.STAFF_CERT_IMAGE_IS_NULL,"身份证图片为空，请重新读卡");
            }
            if(StringUtils.isBlank(staffBaseInfoForm.getCertNo())){
                return JsonResult.failure(ResultCode.Staff.STAFF_CERT_CERT_NO_IS_NULL, "证件号码为空");
            }
            //添加用户时， 判断身份证是否为null，提示该用户已存在
            TenantsStaffsInfoEntity entity = new TenantsStaffsInfoEntity();
            entity.setTenantId(tenantId);
            entity.setCertNo(staffBaseInfoForm.getCertNo());
            entity = tenantsStaffsInfoDao.getStaffByCertNo(entity);
            if (entity != null) {
                //身份证不为空，表示用户已存在
                System.out.println("---- user existed  " + entity.getStaffId());
                return JsonResult.failure(ResultCode.DATA_ADD_BASE_INFO_ERROR, Collections.singletonMap("staffNo", entity.getStaffNo()));
            }
            //阿姨没有录入
            TenantsStaffsInfoEntity obj = new TenantsStaffsInfoEntity();
            obj.setTenantId(tenantId);
            obj.setOnOffShelf(Status.OnOffShelf.ON_SHELF);
            obj.setHeadImage(staffBaseInfoForm.getCertImage());
            obj.setWorkStatus("02");
            //新增家政员默认分享状态 02：未分享01：分享中
            obj.setShareStatus("02");
            StaffsOpt.buildAddBrushCardInfo(obj, staffBaseInfoForm);
            tenantsStaffsInfoDao.insertSelective(obj);
            staffBaseInfoForm.setStaffId(obj.getStaffId());

            //add staff recommand
            Integer count = tenantsRecommendDao.getCountByTenantId(tenantId);
            if(count == null || count < 2){
                RecommendForm form = new RecommendForm();
                form.setStaffId(obj.getStaffId());
                form.setTenantId(tenantId);
                form.setAddTime(new Date());
                saveRecommend(form);
            }

        }
        TenantsStaffsInfoEntity entity = tenantsStaffsInfoDao.getById(staffBaseInfoForm.getStaffId());
        if(entity == null){
            System.out.println("---- user not exist  " + staffBaseInfoForm.getStaffId());
            return JsonResult.failure(ResultCode.DATA_IS_NULL, Collections.singletonMap("staffid", staffBaseInfoForm.getStaffId()));
        }
        TenantsStaffsInfoEntity staffInfoParam = new TenantsStaffsInfoEntity();
        staffInfoParam.setStaffId(staffBaseInfoForm.getStaffId());
        staffInfoParam.setTenantId(tenantId);
        TenantsStaffsInfoEntity staffInfo = tenantsStaffsInfoDao.getStaffInfo(staffInfoParam);

        StaffsOpt.buildAddBaseInfo(staffInfo, staffBaseInfoForm);
        tenantsStaffsInfoDao.update(staffInfo);

        return JsonResult.success(Collections.singletonMap("staffId", staffInfo.getStaffId()));
    }

    /**
     * 保存（新增or修改）员工（阿姨）银行卡信息
     */
    @Override
    public JsonResult saveStaffBank(Integer tenantId, StaffBankForm staffBankForm) {
        TenantsStaffBankKey staffBankKey = new TenantsStaffBankKey();
        staffBankKey.setTenantId(tenantId);
        staffBankKey.setStaffId(staffBankForm.getStaffId());
        TenantsStaffBankEntity obj = tenantsStaffBankDao.getBankInfoByKey(staffBankKey);
        if (obj == null) {
            obj = new TenantsStaffBankEntity();
            obj.setTenantId(tenantId);
            obj.setAddTime(new Date());
            StaffsOpt.buildBankInfo(obj, staffBankForm);
            tenantsStaffBankDao.insert(obj);
        } else {
            obj.setModifyTime(new Date());
            StaffsOpt.buildBankInfo(obj, staffBankForm);
            tenantsStaffBankDao.update(obj);
        }
        return JsonResult.success(null);
    }

    /**
     * 保存（新增or修改）员工（阿姨）保单信息
     */
    @Override
    public JsonResult saveStaffPolicy(Integer tenantId, StaffPolicyForm staffPolicyForm) {
        TenantsStaffPolicyKey staffPolicyKey = new TenantsStaffPolicyKey();
        staffPolicyKey.setPolicyNo(staffPolicyForm.getPolicyNo());
        staffPolicyKey.setStaffId(staffPolicyForm.getStaffId());
        staffPolicyKey.setTenantId(tenantId);
        TenantsStaffPolicyEntity obj = tenantsStaffPolicyDao.getPolicyInfoByKey(staffPolicyKey);
        if (obj == null) {
            obj = new TenantsStaffPolicyEntity();
            obj.setTenantId(tenantId);
            obj.setAddTime(new Date());
            StaffsOpt.buildPolicyInfo(obj, staffPolicyForm);
            tenantsStaffPolicyDao.insert(obj);
        } else {
            obj.setModifyTime(new Date());
            StaffsOpt.buildPolicyInfo(obj, staffPolicyForm);
            tenantsStaffPolicyDao.update(obj);
        }

        return JsonResult.success(null);
    }

    /**
     * 保存（新增or修改）员工（阿姨）求职信息
     */
    @Override
    public JsonResult saveStaffJob(Integer tenantId, StaffJobForm staffJobForm) {
        //新增修改阿姨求职信息
        TenantsStaffJobInfoEntity tenantsStaffJob = new TenantsStaffJobInfoEntity();
        tenantsStaffJob.setStaffId(staffJobForm.getStaffId());
        tenantsStaffJob.setTenantId(tenantId);
        TenantsStaffJobInfoEntity staffJob = tenantsStaffJobDao.getStaffJobInfo(tenantsStaffJob);
        if (staffJob == null) {
            staffJob = new TenantsStaffJobInfoEntity();
            staffJob.setTenantId(tenantId);
            staffJob.setAddTime(new Date());

            StaffsOpt.buildStaffJobInfo(staffJob, staffJobForm);

            tenantsStaffJobDao.insert(staffJob);
        } else {
            StaffsOpt.buildStaffJobInfo(staffJob, staffJobForm);
            staffJob.setModifyTime(new Date());
            if(StringUtils.isBlank(SessionUtils.getHeaderValue(Constants.AUTH_KEY.TOKEN))){
            	if (null == staffJob.getLanguageFeature() || "".equals(staffJob.getLanguageFeature())) {
                    staffJob.setLanguageFeature("");
                }
                if (null == staffJob.getCookingFeature() || "".equals(staffJob.getCookingFeature())) {
                    staffJob.setCookingFeature("");
                }
                if (null == staffJob.getCharacerFeature() || "".equals(staffJob.getCharacerFeature())) {
                    staffJob.setCharacerFeature("");
                }
            }
            tenantsStaffJobDao.update(staffJob);
        }

        return JsonResult.success(null);
    }


    /**
     * 删除工种信息
     */
    @Override
    public JsonResult delStaffItemsInfo(Integer tenantId, StaffJobForm staffJobForm) {
        if (null != staffJobForm.getServiceItems() && staffJobForm.getServiceItems().size() > 0) {
            TenantsStaffSerItemsEntity tenantsStaffSerItemsEntity = new TenantsStaffSerItemsEntity();
            tenantsStaffSerItemsEntity.setId(staffJobForm.getId());
            tenantsStaffSerItemsEntity.setIsUsable("0");//逻辑删除不可用
            tenantsStaffSerItemsEntity.setStaffId(staffJobForm.getStaffId());
            tenantsStaffSerItemsEntity.setServiceItemCode(staffJobForm.getServiceItems().get(0).getServiceItemCode() + "");
            tenantsStaffSerItemsEntity.setTenantId(tenantId);
            return JsonResult.success(tenantsStaffSerItemsDao.delete(tenantsStaffSerItemsEntity));
        }
        return JsonResult.success(null);
    }


    /**
     * 保存求职信息
     */
    @Override
    public JsonResult saveStaffItemsInfo(Integer tenantId, StaffJobForm staffJobForm) {

        //保存求职信息
        saveStaffJob(tenantId, staffJobForm);

        //保存工种信息
        TenantsStaffSerItemsKey staffSerItemsKey = null;
        TenantsStaffSerItemsEntity staffSerItemsObj = null;
        //查询工作信息
        if (staffJobForm.getServiceItems() != null) {
            for (StaffServiceItemform staffServiceItemBean : staffJobForm.getServiceItems()) {
                staffSerItemsKey = new TenantsStaffSerItemsKey();
                staffSerItemsKey.setStaffId(staffJobForm.getStaffId());
                staffSerItemsKey.setTenantId(tenantId);
                if (StringUtils.isNotBlank(staffServiceItemBean.getServiceItemCode())) {
                    staffSerItemsKey.setServiceItemCode(staffServiceItemBean.getServiceItemCode());
                    if (staffServiceItemBean.getId() != null) {
                        staffSerItemsObj = tenantsStaffSerItemsDao.getStaffServiceItemsById(staffServiceItemBean.getId());
                        if (staffSerItemsObj != null) {
                            staffSerItemsObj.setModifyTime(new Date());
                            staffSerItemsObj.setId(staffServiceItemBean.getId());
                            StaffsOpt.buildStaffServiceItemInfo(staffSerItemsObj, staffServiceItemBean);
                            tenantsStaffSerItemsDao.update(staffSerItemsObj);
                        }
                    } else {
                        staffSerItemsObj = new TenantsStaffSerItemsEntity();
                        staffSerItemsObj.setTenantId(tenantId);
                        staffSerItemsObj.setStaffId(staffJobForm.getStaffId());
                        staffSerItemsObj.setAddTime(new Date());
                        staffSerItemsObj.setIsUsable("1");
                        StaffsOpt.buildStaffServiceItemInfo(staffSerItemsObj, staffServiceItemBean);
                        tenantsStaffSerItemsDao.insert(staffSerItemsObj);
                    }
                }
            }
        }

        return JsonResult.success(null);
    }


    /**
     * 保存证件信息
     *
     * @author xiehui
     */
    @Override
    public JsonResult saveStaffCert(Integer tenantId, StaffCertForm staffCertForm, String valid) {
        List<TenantsStaffCertsInfoEntity> certList = tenantsStaffCertsDao.getSatffAllCerts(staffCertForm.getStaffId());
        List<String> list = new ArrayList<String>();
        for (TenantsStaffCertsInfoEntity cert : certList) {
            if ("02".equals(cert.getCertifiedStatus())) {
                list.add(cert.getCertType());
            }
        }
        if (staffCertForm.getId() == null) {
            TenantsStaffCertsInfoEntity obj = new TenantsStaffCertsInfoEntity();
            obj.setTenantId(tenantId);
            obj.setAddTime(new Date());
            obj.setCertImage(staffCertForm.getPhotoUrl());
            StaffsOpt.buildCertInfo(obj, staffCertForm);
            tenantsStaffCertsDao.insert(obj);
        } else {
            TenantsStaffCertsInfoEntity obj = tenantsStaffCertsDao.getById(staffCertForm.getId());
            obj.setId(staffCertForm.getId());
            obj.setIsUsable("02");
            obj.setModifyTime(new Date());
            tenantsStaffCertsDao.updateIsUsable(obj);
            // xiehui未通过的证书修改后重新新增一条记录
            TenantsStaffCertsInfoEntity certBook = new TenantsStaffCertsInfoEntity();
            certBook.setTenantId(tenantId);
            certBook.setAddTime(new Date());
            certBook.setCertNo(staffCertForm.getCertNo());
            certBook.setCertType(staffCertForm.getCertType());
            certBook.setStaffId(staffCertForm.getStaffId());
            certBook.setCertificationBody(staffCertForm.getCertificationBody());
            certBook.setCertificationDate(staffCertForm.getCertificationDate());
            certBook.setCertExpireDate(staffCertForm.getCertExpireDate());
            certBook.setCertifiedStatus(Status.CertifiedStatus.UN_CERTIFIED);
            certBook.setLevel("0");
            certBook.setIsUsable("01");
            obj.setCertImage(staffCertForm.getPhotoUrl());
            if ("03".equals(staffCertForm.getCertificationBody())) {
                certBook.setCertificationBody(staffCertForm.getCertificationBody());// @xiehui
                // 认证机构
                certBook.setOtherCertificationBody(staffCertForm.getOtherCertificationBody());
            } else {
                certBook.setCertificationBody(staffCertForm.getCertificationBody());// @xiehui
                certBook.setOtherCertificationBody(""); // 认证机构
            }
            if (staffCertForm.getCertType().equals("18") || staffCertForm.getCertType().equals("19")) {
                certBook.setType("02");
                certBook.setAuthenticateGrade("");
            } else {
                certBook.setAuthenticateGrade(staffCertForm.getAuthenticateGrade());// @xiehui鉴定等级
                certBook.setType("01");
            }
            // StaffsOpt.buildCertInfo(obj, staffCertForm);
            tenantsStaffCertsDao.insert(certBook);
        }
        return JsonResult.success(staffCertForm.getPhotoUrl());
    }


    /**
     * 保存阿姨财务记录
     */
    public JsonResult saveStaffFinance() {
        return null;
    }

    @Override
    public JsonResult queryStaff(Integer tenantId, StaffQueryForm staffQueryForm, int pageNumber, int pageSize) {
        try {

            //获取总条数
            Map<Object, Object> map = StaffsOpt.buildQueryMap(tenantId);
            map.put("staffName", StringUtils.strLike(staffQueryForm.getStaffName()));
            //阿姨编号、
            map.put("staffNo", StringUtils.strLike(staffQueryForm.getStaffNo()));

            map.put("mobile", StringUtils.strLike(staffQueryForm.getMobile()));
            //服务工种
            map.put("serviceItemCode", staffQueryForm.getServiceItemCode());
            //从业经历
            map.put("experience", staffQueryForm.getExperience());
            //技能点
            map.put("skills", StringUtils.strLike(staffQueryForm.getSkills()));
            //教育经历
            map.put("education", staffQueryForm.getEducation());
            //鉴定等级
            map.put("authenticateGrade", staffQueryForm.getAuthenticateGrade());
            //证书名称
            map.put("certType", staffQueryForm.getCertType());

            if (StringUtils.isNotBlank(staffQueryForm.getAge())) {
                String ageValue = DictionarysCacheUtils.getAgeIntervalName(staffQueryForm.getAge());
                String[] ages = ageValue.split(",");
                if (ages.length == 2) {
                    map.put("ageMin", ages[0]);
                    map.put("ageMax", ages[1]);
                } else {
                    map.put("ageMin", ages[0]);
                    map.put("ageMax", null);
                }
            }
            //属相
            map.put("zodiac", staffQueryForm.getZodiac());
            //工作状态
            map.put("nativePlace", staffQueryForm.getNativePlace());
            map.put("workStatus", staffQueryForm.getWorkStatus());
            map.put("languageFeature", StringUtils.strLike(staffQueryForm.getLanguageFeature()));
            map.put("cookingFeature", StringUtils.strLike(staffQueryForm.getCookingFeature()));
            map.put("characerFeature", StringUtils.strLike(staffQueryForm.getCharacerFeature()));
            map.put("petFeeding", staffQueryForm.getPetFeeding());
            map.put("elderlySupport", staffQueryForm.getElderlySupport());
            map.put("onOffShelf", staffQueryForm.getOnOffShelf());
            Integer totalCount = tenantsStaffsInfoDao.getStaffQueryCount(map);
//			Integer totalCount=tenantsStaffsInfoDao.staffQueryCount(map);
            //分页实体
            Page<StaffQueryJson> page = new Page<StaffQueryJson>();
            page.setPage(pageNumber);
            page.setRowNum(pageSize);
            if (totalCount == null) {
                return JsonResult.success(page);
            }
            //最大页数判断
            int pageM = maxPage(totalCount, page.getRowNum(), page.getPage());
            if (pageM > 0) {
                page.setPage(pageM);
            }
            if (totalCount > 0) {
                map.put("offset", page.getOffset());
                map.put("pageSize", page.getRowNum());
                List<TenantsStaffsInfoEntity> list = tenantsStaffsInfoDao.getStaffQueryList(map);
//            	List<TenantsStaffsInfoEntity> list=tenantsStaffsInfoDao.staffQueryList(map);
                List<StaffQueryJson> resultList = Lists.newArrayList();
                StaffQueryJson staffQueryBean = null;
                StringBuilder sb = new StringBuilder();
                for (TenantsStaffsInfoEntity entity : list) {
                    staffQueryBean = new StaffQueryJson();
                    staffQueryBean.setStaffId(entity.getStaffId());
                    if (entity.getHeadImage().startsWith("http") || entity.getHeadImage().startsWith("data")) {
                        staffQueryBean.setHeadImage(entity.getHeadImage());
                    } else {
                        staffQueryBean.setHeadImage(Constants.IMAGE_URL + entity.getHeadImage());
                    }
                    staffQueryBean.setStaffName(entity.getStaffName());
                    staffQueryBean.setMobile(entity.getMobile());
                    staffQueryBean.setAge(entity.getAge());
                    if (StringUtils.isNotBlank(entity.getZodiac())) {
                        staffQueryBean.setZodiac(DictionarysCacheUtils.getZodiacName(entity.getZodiac()));
                    }
                    if (StringUtils.isNotBlank(entity.getNativePlace())) {
                        staffQueryBean.setNativePlace(DictionarysCacheUtils.getNativePlaceName(entity.getNativePlace()));
                    }
                    sb.setLength(0);
                    if (StringUtils.isNotBlank(entity.getEducarion())) {
                        staffQueryBean.setEducation(DictionarysCacheUtils.getEducationName(entity.getEducarion()));
                    }
                    staffQueryBean.setSpecialty(entity.getSpecialty());
                    if (entity.getTenantId() != null && entity.getStaffId() != null) {
                        staffQueryBean.setServiceItems(getStaffServiceItems(entity.getTenantId(), entity.getStaffId()));
                    }
                    if(StringUtils.isNotBlank(entity.getConstellation())){
                    	staffQueryBean.setConstellation(DictionarysCacheUtils.getConstellationName(entity.getConstellation()));
                    }
                    //工作状态判断
//            		staffQueryBean.setWorkStatus(staffIsWorkNow(entity.getStaffId()));
                    staffQueryBean.setWorkStatus(entity.getWorkStatus());
                    staffQueryBean.setOnOffShelf(Status.getDesc(entity.getOnOffShelf()));
                    // 查询阿姨求职信息
                    TenantsStaffJobInfo tenantsStaffJobInfo = getStaffJobInfoById(entity.getStaffId());
                    staffQueryBean.setServicePrice(tenantsStaffJobInfo.getPrice());
                    staffQueryBean.setUnit(tenantsStaffJobInfo.getUnit());
                    staffQueryBean.setUnitValue(DictionarysCacheUtils.getServicePriceUnit(tenantsStaffJobInfo.getUnit()));
                    resultList.add(staffQueryBean);
                }
                page.setRows(resultList);
                page.setRecords(totalCount.longValue());
            }
            return JsonResult.success(page);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ErrorMsg.STAFF_QUERY_ERR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
    }

    private TenantsStaffJobInfo getStaffJobInfoById(Integer staffId) {
        return tenantsStaffJobInfoMapper.selectByPrimaryKey(staffId);
    }
    
    /**
     * 获取员工的服务工种中文
     */
    @Override
    public String getStaffServiceItems(Integer tenantId, Integer staffId) {
        try {
            TenantsStaffSerItemsEntity staffSerItems = new TenantsStaffSerItemsEntity();
            staffSerItems.setStaffId(staffId);
            staffSerItems.setTenantId(tenantId);
            List<TenantsStaffSerItemsEntity> list = tenantsStaffSerItemsDao.getStaffServiceItems(staffSerItems);
            StringBuilder sb = new StringBuilder();
            for (TenantsStaffSerItemsEntity bean : list) {
                sb.append(DictionarysCacheUtils.getServiceTypeName(bean.getServiceItemCode())).append(Constants.DAYTON);
            }
            int last = sb.length();
            if (last > 0) {
                return StringUtils.substring(sb.toString(), 0, last - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ErrorMsg.STAFF_SERVICE_ITEMS_ERR, e);
        }
        return Constants.EMPTY;
    }

    /**
     * 根据租户id和staffId获取阿姨的详情
     */
    @Override
    public JsonResult getDetails(Integer tenantId, Integer staffId, String orderNo) {

        StaffDetailJsonData detailJsonData = new StaffDetailJsonData();
        //基础信息
        TenantsStaffsInfoEntity staffInfoParam = new TenantsStaffsInfoEntity();
        staffInfoParam.setStaffId(staffId);
        staffInfoParam.setTenantId(tenantId);
        TenantsStaffsInfoEntity staffInfo = tenantsStaffsInfoDao.getStaffInfo(staffInfoParam);
        if (staffInfo == null) {
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
        //  staffInfo.setExpectedSalary(tenantsStaffSerItemsDao.getStaffprice(staffInfoParam));
        StaffBaseInfoJson staffBaseInfoJson = new StaffBaseInfoJson();
        StaffsOpt.buildStaffDetaillBaseInfo(staffBaseInfoJson, staffInfo);

        staffBaseInfoJson.setIsLocalStaff(1);
        if(!tenantId.equals(staffInfo.getTenantId()) && StringUtils.isNotBlank(orderNo)){
            staffBaseInfoJson.setIsLocalStaff(0);
            TenantsJobResume resume = tenantsJobResumesMapper.getByOrderStaff(orderNo, staffId);
            staffBaseInfoJson.setResumeContactName(resume.getContactName());
            staffBaseInfoJson.setResumeContactPhone(resume.getContactPhone());
        }


        detailJsonData.setBaseInfo(staffBaseInfoJson);
        //银行信息
        detailJsonData.setBankInfo(this.getStaffBank(staffId, tenantId));
        //保单信息
        detailJsonData.setPolicyList(this.getStaffPolicys(staffId, tenantId));
        //视频图片秀
        detailJsonData.setVideoMedia(this.getStaffMeidaVideo(staffId));
        detailJsonData.setImageMedia(this.getStaffMeidaPictures(staffId));
        //求职信息
        detailJsonData.setJobInfo(this.getStaffJobs(staffId));
        //服务认证信息
        detailJsonData.setServiceList(this.getSatffAllCerts(staffId));
        //派工记录--涉及到订单
        detailJsonData.setWorkList(this.getStaffOrders(staffId));
        //财务记录
        List<StaffFinanceInfoJson> staffFinanceInfoJsonList = this.getStaffFinance(staffId, tenantId);
        detailJsonData.setFinanceList(staffFinanceInfoJsonList);

        return JsonResult.success(detailJsonData);
    }

    /**
     * 根据租户id和staffId获取阿姨的详情
     */
    @Override
    public JsonResult getDetailsPartBase(Integer tenantId, Integer staffId, String orderNo) {

        StaffDetailJsonData detailJsonData = new StaffDetailJsonData();
        //基础信息
        TenantsStaffsInfoEntity staffInfoParam = new TenantsStaffsInfoEntity();
        staffInfoParam.setStaffId(staffId);
        //staffInfoParam.setTenantId(tenantId);
        TenantsStaffsInfoEntity staffInfo = tenantsStaffsInfoDao.getStaffInfo(staffInfoParam);
        if (staffInfo == null) {
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
        StaffBaseInfoJson staffBaseInfoJson = new StaffBaseInfoJson();
        StaffsOpt.buildStaffDetaillBaseInfo(staffBaseInfoJson, staffInfo);
        staffBaseInfoJson.setIsLocalStaff(1);
        if(!tenantId.equals(staffInfo.getTenantId()) && StringUtils.isNotBlank(orderNo)){
            staffBaseInfoJson.setIsLocalStaff(0);
            TenantsJobResume resume = tenantsJobResumesMapper.getByOrderStaff(orderNo, staffId);
            staffBaseInfoJson.setResumeContactName(resume.getContactName());
            staffBaseInfoJson.setResumeContactPhone(resume.getContactPhone());
        }

        detailJsonData.setBaseInfo(staffBaseInfoJson);
        //求职信息
        detailJsonData.setJobInfo(this.getStaffJobs(staffId));
        //服务认证信息
        detailJsonData.setServiceList(this.getSatffAllCerts(staffId));
        return JsonResult.success(detailJsonData);
    }

    /**
     * 根据租户id和staffId获取阿姨的详情
     */
    @Override
    public JsonResult getDetailsPartMedia(Integer tenantId, Integer staffId) {
        StaffDetailJsonData detailJsonData = new StaffDetailJsonData();
        //视频图片秀
        detailJsonData.setVideoMedia(this.getStaffMeidaVideo(staffId));
        detailJsonData.setImageMedia(this.getStaffMeidaPictures(staffId));

        return JsonResult.success(detailJsonData);
    }

    /**
     * 根据租户id和staffId获取阿姨的详情
     */
    @Override
    public JsonResult getDetailsPartFinance(Integer tenantId, Integer staffId) {
        StaffDetailJsonData detailJsonData = new StaffDetailJsonData();
        //银行信息
        detailJsonData.setBankInfo(this.getStaffBank(staffId, tenantId));
        //保单信息
        detailJsonData.setPolicyList(this.getStaffPolicys(staffId, tenantId));
        //财务记录
        List<StaffFinanceInfoJson> staffFinanceInfoJsonList = this.getStaffFinance(staffId, tenantId);
        detailJsonData.setFinanceList(staffFinanceInfoJsonList);

        return JsonResult.success(detailJsonData);
    }

    /**
     * 根据租户id和staffId获取阿姨的详情
     */
    @Override
    public JsonResult getDetailsPartRecord(Integer tenantId, Integer staffId) {
        StaffDetailJsonData detailJsonData = new StaffDetailJsonData();
        //派工记录--涉及到订单
        detailJsonData.setWorkList(this.getStaffOrders(staffId));
        detailJsonData.setWorktimeList(this.getStaffWorkList(staffId));

        return JsonResult.success(detailJsonData);
    }


    //获取银行卡信息
    @Override
    public JsonResult getBankInfo(Integer tenantId, Integer staffId) {
        StaffDetailJsonData detailJsonData = new StaffDetailJsonData();
        //基础信息
        TenantsStaffsInfoEntity staffInfoParam = new TenantsStaffsInfoEntity();
        staffInfoParam.setStaffId(staffId);
        staffInfoParam.setTenantId(tenantId);
        TenantsStaffsInfoEntity staffInfo = tenantsStaffsInfoDao.getStaffInfo(staffInfoParam);
        if (staffInfo == null) {
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
        //银行信息
        detailJsonData.setBankInfo(this.getStaffBank(staffId, tenantId));
        return JsonResult.success(detailJsonData);
    }

    //获取保单信息
    @Override
    public JsonResult getPolicyList(Integer tenantId, Integer staffId) {
        StaffDetailJsonData detailJsonData = new StaffDetailJsonData();
        //基础信息
        TenantsStaffsInfoEntity staffInfoParam = new TenantsStaffsInfoEntity();
        staffInfoParam.setStaffId(staffId);
        staffInfoParam.setTenantId(tenantId);
        TenantsStaffsInfoEntity staffInfo = tenantsStaffsInfoDao.getStaffInfo(staffInfoParam);
        if (staffInfo == null) {
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
        //保单信息
        detailJsonData.setPolicyList(this.getStaffPolicys(staffId, tenantId));
        return JsonResult.success(detailJsonData);
    }

    //获取财务记录
    @Override
    public JsonResult getFinanceInfo(Integer tenantId, Integer staffId) {
        StaffDetailJsonData detailJsonData = new StaffDetailJsonData();
        //基础信息
        TenantsStaffsInfoEntity staffInfoParam = new TenantsStaffsInfoEntity();
        staffInfoParam.setStaffId(staffId);
        staffInfoParam.setTenantId(tenantId);
        TenantsStaffsInfoEntity staffInfo = tenantsStaffsInfoDao.getStaffInfo(staffInfoParam);
        if (staffInfo == null) {
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
        //财务记录
        List<StaffFinanceInfoJson> staffFinanceInfoJsonList = this.getStaffFinance(staffId, tenantId);
        return JsonResult.success(detailJsonData);
    }

    //获取派工记录
    @Override
    public JsonResult getWorkList(Integer tenantId, Integer staffId) {
        StaffDetailJsonData detailJsonData = new StaffDetailJsonData();
        //基础信息
        TenantsStaffsInfoEntity staffInfoParam = new TenantsStaffsInfoEntity();
        staffInfoParam.setStaffId(staffId);
        staffInfoParam.setTenantId(tenantId);
        TenantsStaffsInfoEntity staffInfo = tenantsStaffsInfoDao.getStaffInfo(staffInfoParam);
        if (staffInfo == null) {
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
        //派工记录--涉及到订单
        detailJsonData.setWorkList(this.getStaffOrders(staffId));
        return JsonResult.success(detailJsonData);
    }

    //银行卡信息
    private StaffBankInfoJson getStaffBank(Integer staffId, Integer tenantId) {
        TenantsStaffBankKey staffBankKey = new TenantsStaffBankKey();
        staffBankKey.setTenantId(tenantId);
        staffBankKey.setStaffId(staffId);
        TenantsStaffBankEntity bankobj = tenantsStaffBankDao.getBankInfoByKey(staffBankKey);
        StaffBankInfoJson bankJson = new StaffBankInfoJson();
        if (bankobj != null) {
            StaffsOpt.buildStaffDetaillBankInfo(bankJson, bankobj);
        }
        return bankJson;
    }

    //保单信息
    private List<StaffPolicyInfoJson> getStaffPolicys(Integer staffId, Integer tenantId) {
        TenantsStaffPolicyEntity staffPolicyParams = new TenantsStaffPolicyEntity();
        staffPolicyParams.setStaffId(staffId);
        staffPolicyParams.setTenantId(tenantId);
        List<TenantsStaffPolicyEntity> policyList = tenantsStaffPolicyDao.getPolicyList(staffPolicyParams);
        List<StaffPolicyInfoJson> policyListJson = Lists.newArrayList();
        StaffsOpt.buildStaffDetaillPolicyList(policyListJson, policyList);
        return policyListJson;
    }

    //视频图片秀
    private List<StaffMediaPictureJson> getStaffMeidaPictures(Integer staffId) {
        //视频图片数据源
        List<TenantsStaffsMedia> mediaList = tenantsStaffsMediaDao.getAllMedias(staffId);
        //图片秀
        List<StaffMediaPictureJson> imageMedia = Lists.newArrayList();
        for (TenantsStaffsMedia media : mediaList) {
            if (Status.MediaType.PICTURE.equals(media.getType())) {
                StaffsOpt.buildStaffDetaillPicture(imageMedia, media);
            }
        }
        return imageMedia;
    }

    //视频图片秀
    private StaffMediaVideoJson getStaffMeidaVideo(Integer staffId) {
        //视频图片数据源
        List<TenantsStaffsMedia> mediaList = tenantsStaffsMediaDao.getAllMedias(staffId);
        //视频秀
        StaffMediaVideoJson videoMedia = new StaffMediaVideoJson();
        //图片秀
        for (TenantsStaffsMedia media : mediaList) {
            if (Status.MediaType.VIDEO.equals(media.getType())) {
                StaffsOpt.buildStaffDetaillVideo(videoMedia, media);
            }
        }
        return videoMedia;
    }


    //服务认证信息
    private List<StaffServiceInfoJson> getSatffAllCerts(Integer staffId) {
        List<StaffServiceInfoJson> serviceList = Lists.newArrayList();
        List<TenantsStaffCertsInfoEntity> certList = tenantsStaffCertsDao.getSatffAllCerts(staffId);
        for (TenantsStaffCertsInfoEntity cert : certList) {
            StaffsOpt.buildStaffDetaillCerts(serviceList, cert);
        }
        return serviceList;
    }

    ;

    //阿姨求职信息
    private StaffJobInfoJson getStaffJobs(Integer staffId) {
        StaffJobInfoJson jobInfo = new StaffJobInfoJson();

        //求职信息
        TenantsStaffJobInfoEntity tenantsStaffJob = tenantsStaffJobDao.getById(staffId);
        StaffsOpt.buildStaffDetaillJobsInfo(jobInfo, tenantsStaffJob);

        //服务工种
        jobInfo.setServiceItemList(Lists.<StaffServiceItemJson>newArrayList());
        List<TenantsStaffSerItemsEntity> tenantsStaffSerItems = tenantsStaffSerItemsDao.getServiceItemsByStaffId(staffId);
        for (TenantsStaffSerItemsEntity serItems : tenantsStaffSerItems) {
            StaffsOpt.buildStaffDetaillJobsSerItems(jobInfo.getServiceItemList(), serItems);
        }
        return jobInfo;
    }

    @Override
    public JsonResult getStaffServiceItemList(Integer tenantId) {
        return JsonResult.success(tenantsServiceItemsDao.getStaffServiceItemList(tenantId));
    }

    @Override
    public JsonResult selectByPrimaryKey(String serviceItemCode, Integer tenantId) {
        TenantsServiceItemsEntity tenantsServiceItemsEntity = new TenantsServiceItemsEntity();
        tenantsServiceItemsEntity.setTenantId(tenantId);
        tenantsServiceItemsEntity.setServiceItemCode(serviceItemCode);
        return JsonResult.success(tenantsServiceItemsDao.selectByPrimaryKey(tenantsServiceItemsEntity));
    }

    @Override
    public JsonResult selectRecommendList(Integer tenantId) {
        List<Map<String, Object>> list = tenantsStaffsInfoDao.selectRecommendList(tenantId);
        String baseInfo = "";
        for (Map<String, Object> map : list) {
            baseInfo = map.get("age") + "/属" + map.get("zodiac") + "/" + map.get("nativePlace") + "人";
            String str = (String)map.get("headImage");
            if(StringUtils.isNotBlank(str) && !str.startsWith("http") && !str.startsWith("data")){
                map.put("headImage", Constants.IMAGE_URL + map.get("headImage"));
            }
            map.put("baseInfo", baseInfo);
            map.put("workStatus", staffIsWorkNow((Integer) map.get("staffId")));
            map.remove("age");
            map.remove("zodiac");
            map.remove("nativePlace");
        }
        return JsonResult.success(list);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public JsonResult selectStaffInfoList(Integer tenantId, Integer pageNumber, Integer pageSize) {
        try {
            //获取总条数
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("tenantId", tenantId);
            Integer totalCount = this.tenantsStaffsInfoDao.selectStaffInfoListCount(tenantId);
            //分页实体
            Page<Map> page = new Page<Map>();
            page.setPage(pageNumber);
            page.setRowNum(pageSize);
            //最大页数判断
            int pageM = maxPage(totalCount, page.getRowNum(), page.getPage());
            if (pageM > 0) {
                page.setPage(pageM);
            }
            if (totalCount > 0) {
                map.put("offset", page.getOffset());
                map.put("pageSize", page.getRowNum());
                System.out.println(tenantId + "====tenantId====selectStaffInfoList===");
                List<Map> resultList = tenantsStaffsInfoDao.selectStaffInfoList(map);
                System.out.println(resultList.size() + "====tenantId====selectStaffInfoList===");
                String baseInfo = "";
                for (Map result : resultList) {
                    baseInfo = result.get("age") + "/属" + result.get("zodiac") + "/" + result.get("nativePlace") + "人";
                    if (result.get("headImage") != null
                            && !result.get("headImage").toString().startsWith("http")
                            && !result.get("headImage").toString().startsWith("data")
                            ) {
                        result.put("headImage", Constants.IMAGE_URL + result.get("headImage"));
                    } else {
                        result.put("headImage", result.get("headImage"));
                    }
                    result.put("baseInfo", baseInfo);
                    result.put("workStatus", staffIsWorkNow((Integer) result.get("staffId")));
                    result.remove("age");
                    result.remove("zodiac");
                    result.remove("nativePlace");
                }
                page.setRows(resultList);
                page.setRecords(totalCount.longValue());
            }
            return JsonResult.success(page);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ErrorMsg.STAFF_QUERY_ERR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
    }

    @Override
    public JsonResult getStaffInfoByStaffId(Integer tenantId, Integer staffId) {
        Map<String, Object> map = tenantsStaffsInfoDao.getStaffInfoByStaffId(tenantId, staffId);
        String baseInfo = "";
        baseInfo = map.get("age") + "/属" + map.get("zodiac") + "/" + map.get("nativePlace") + "人";
        map.put("baseInfo", baseInfo);
        map.remove("age");
        map.remove("zodiac");
        map.remove("nativePlace");
        return JsonResult.success(map);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public JsonResult getStaffInfoLikeStaffName(Integer tenantId, String staffName, int pageNumber, int pageSize) {
        try {
            //获取总条数
            Map map = new HashMap<>();
            map.put("tenantId", tenantId);
            map.put("staffName", staffName);
            Integer totalCount = this.tenantsStaffsInfoDao.getCountLike(map);
            //分页实体
            Page<Map> page = new Page<Map>();
            page.setPage(pageNumber);
            page.setRowNum(pageSize);
            //最大页数判断
            int pageM = maxPage(totalCount, page.getRowNum(), page.getPage());
            if (pageM > 0) {
                page.setPage(pageM);
            }
            if (totalCount > 0) {
                map.put("offset", page.getOffset());
                map.put("pageSize", page.getRowNum());
                List<Map> resultList = tenantsStaffsInfoDao.getStaffInfoLikeStaffName(map);
                String baseInfo = "";
                for (Map<String, Object> result : resultList) {
                    baseInfo = result.get("age") + "/属" + result.get("zodiac") + "/" + result.get("nativePlace") + "人";
                    if (result.get("headImage") != null
                            && !result.get("headImage").toString().startsWith("http")
                            && !result.get("headImage").toString().startsWith("data")
                            ) {
                        result.put("headImage", Constants.IMAGE_URL + result.get("headImage"));
                    } else {
                        result.put("headImage", result.get("headImage"));
                    }
                    result.put("baseInfo", baseInfo);
                    map.put("workStatus", staffIsWorkNow((Integer) result.get("staffId")));
                    result.remove("age");
                    result.remove("zodiac");
                    result.remove("nativePlace");
                }
                page.setRows(resultList);
                page.setRecords(totalCount.longValue());
            }
            return JsonResult.success(page);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ErrorMsg.STAFF_QUERY_ERR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
    }

    @Override
    public JsonResult updateByPrimaryKeySelective(ServiceItemsForm serviceItemsForm) {
        TenantsServiceItems tenantsServiceItems = new TenantsServiceItems();
        tenantsServiceItems.setTenantId(serviceItemsForm.getTenantId());
        tenantsServiceItems.setServiceItemCode(serviceItemsForm.getServiceItemCode());
        tenantsServiceItems.setServiceItemName(serviceItemsForm.getServiceItemName());
        tenantsServiceItems.setImageUrl(serviceItemsForm.getImgUrl());
        tenantsServiceItems.setServicePrice(serviceItemsForm.getServicePrice());
        tenantsServiceItems.setServicePriceUnit(serviceItemsForm.getServicePriceUnit());
        tenantsServiceItems.setIsHot(serviceItemsForm.getIsHot());
        tenantsServiceItems.setServiceDesc(serviceItemsForm.getServiceDesc());
        tenantsServiceItems.setServiceObject(serviceItemsForm.getServiceObject());
        tenantsServiceItems.setServiceContent(serviceItemsForm.getServiceContent());
        tenantsServiceItems.setIsShow(serviceItemsForm.getIsShow());
        tenantsServiceItems.setIsDefault(serviceItemsForm.getIsDefault());
        return JsonResult.success(tenantsServiceItemsDao.updateByPrimaryKeySelective(tenantsServiceItems));
    }

    @Override
    public JsonResult updateRecommend(RecommendForm recommendForm) {
        TenantsRecommendEntity tenantsRecommendEntity = new TenantsRecommendEntity();
        tenantsRecommendEntity.setId(recommendForm.getId());
        tenantsRecommendEntity.setTenantId(recommendForm.getTenantId());
        tenantsRecommendEntity.setStaffId(recommendForm.getStaffId());
        tenantsRecommendEntity.setAddTime(recommendForm.getAddTime());
        tenantsRecommendEntity.setAddAccount(recommendForm.getAddAccount());
        tenantsRecommendEntity.setModifyTime(recommendForm.getModifyTime());
        tenantsRecommendEntity.setModifyAccount(recommendForm.getModifyAccount());
        return JsonResult.success(tenantsRecommendDao.update(tenantsRecommendEntity));
    }

    @Override
    public List<Map<String, String>> getStaffServiceItemCodes(Integer tenantId) {
        return tenantsServiceItemsDao.getStaffServiceItemCodes(tenantId);
    }

    //阿姨求派工信息
    public List<StaffWorkInfoJson> getStaffOrders(Integer staffId) {
        List<StaffWorkInfoJson> workList = Lists.newArrayList();
        List<Orders> orderList = ordersDao.getStaffOrders(staffId);
        OrderCustomersInfo orderCustomers = null;
        for (Orders orderEntity : orderList) {
            orderCustomers = orderCustomersDao.selectByPrimaryKey(orderEntity.getOrderNo());
            StaffsOpt.buildStaffDetaillOrders(workList, orderEntity, orderCustomers);
        }
        return workList;
    }

    //阿姨工作安排表
    @Override
    public List<StaffWorkInfoJson> getStaffWorkList(Integer staffId) {
        List<StaffWorkInfoJson> workList = Lists.newArrayList();
        TenantsStaffsInfoEntity entity = tenantsStaffsInfoDao.getById(staffId);
        List<TenantsStaffsInfoEntity> staffs = tenantsStaffsInfoDao.getStaffListByCertNo(entity.getCertNo());
        OrderCustomersInfo orderCustomers = null;
        List<Orders> orderList = new ArrayList<>();
        for (TenantsStaffsInfoEntity staff : staffs) {
            orderList = ordersDao.getStaffWorkList(staff.getStaffId());
            for (Orders orderEntity : orderList) {
                orderCustomers = orderCustomersDao.selectByPrimaryKey(orderEntity.getOrderNo());
                StaffsOpt.buildStaffDetaillOrders(workList, orderEntity, orderCustomers);
            }
        }
        return workList;
    }

    //阿姨财务记录
    private List<StaffFinanceInfoJson> getStaffFinance(Integer staffId, Integer tenantId) {
        List<StaffFinanceInfoJson> financeList = Lists.newArrayList();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("tenantId", tenantId);
        map.put("staffId", staffId);
        //按时间顺序查询阿姨财务记录5条
        List<TenantsFinanceRecords> list = tenantsFinanceRecordsDao.getStaffFinances(map);
        for (TenantsFinanceRecords financeRecord : list) {
            StaffsOpt.buildStaffDetaillFinance(financeList, financeRecord);
        }
        return financeList;
    }

    @Override
    public JsonResult addImageDefault(Integer staffId, Integer imageId) {
        //1.根据staffid清除阿姨默认图片
        tenantsStaffsMediaDao.clearStaffImageDefault(staffId);
        //2.保存阿姨默认图片
        TenantsStaffsMediaEntity entity = tenantsStaffsMediaDao.getById(imageId);
        entity.setIsDefault(Status.IsDefault.TRUE);
        entity.setModifyTime(new Date());
        tenantsStaffsMediaDao.update(entity);
        //3.保存阿姨默认头像
        TenantsStaffsInfoEntity staffInfo = tenantsStaffsInfoDao.getById(staffId);
        staffInfo.setHeadImage(entity.getPath());
        tenantsStaffsInfoDao.update(staffInfo);
        return JsonResult.success(null);
    }

    @Override
    public JsonResult staffOnOff(Integer staffId, String onOff) {
        TenantsStaffsInfoEntity staffInfo = tenantsStaffsInfoDao.getById(staffId);
        staffInfo.setOnOffShelf(onOff);
//		tenantsStaffsInfoDao.update(staffInfo);
        tenantsStaffsInfoDao.updateStaffOnOffShelf(staffInfo);
        return JsonResult.success(null);
    }

    @Override
    public JsonResult editServiceItem(ServiceItemsForm serviceItemsForm, MultipartFile files) {
        if ("0".equals(serviceItemsForm.getIsDefault())) {//系统默认图处理
            updateByPrimaryKeySelective(serviceItemsForm);
            return JsonResult.success(null);
        }
        TenantsServiceItemsEntity entity = new TenantsServiceItemsEntity();
        entity.setTenantId(serviceItemsForm.getTenantId());
        entity.setServiceItemCode(serviceItemsForm.getServiceItemCode());
        updateByPrimaryKeySelective(serviceItemsForm);
        return JsonResult.success(serviceItemsForm.getImgUrl());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult uploadVideoPath(UserBean userBean, String[] videoPath, String[] imagePath, Integer staffId, Integer isWhole) {
        if(isWhole!=null && isWhole == 1){
            TenantsStaffsMediaExample me = new TenantsStaffsMediaExample();
            me.createCriteria().andStaffIdEqualTo(staffId);
            tenantsStaffsMediaDao.deleteByExample(me);
        }
        if (!imagePath.equals(null) && imagePath.length != 0) {
            for (int i = 0; i < imagePath.length; i++) {
                TenantsStaffsMediaEntity tenantsStaffsMedia = new TenantsStaffsMediaEntity();
                tenantsStaffsMedia.setId(null);//P
                tenantsStaffsMedia.setStaffId(staffId);//F
                tenantsStaffsMedia.setAddTime(new Date());
                tenantsStaffsMedia.setAddAccount(userBean.getLoginAccount());
                tenantsStaffsMedia.setPath(imagePath[i]);
                tenantsStaffsMedia.setType("02");//01：视频 02 图片
                tenantsStaffsMediaDao.insert(tenantsStaffsMedia);
            }
        }
        if (!videoPath.equals(null) && videoPath.length != 0) {
            for (int i = 0; i < videoPath.length; i++) {
                TenantsStaffsMediaEntity tenantsStaffsMedia = new TenantsStaffsMediaEntity();
                tenantsStaffsMedia.setId(null);//P
                tenantsStaffsMedia.setStaffId(staffId);//F
                tenantsStaffsMedia.setAddTime(new Date());
                tenantsStaffsMedia.setAddAccount(userBean.getLoginAccount());
                tenantsStaffsMedia.setPath(videoPath[i]);
                tenantsStaffsMedia.setType("01");//01：视频 02 图片
                tenantsStaffsMediaDao.insert(tenantsStaffsMedia);
            }
        }
        return JsonResult.success(null);

    }


    @Override
    public JsonResult deletePhotoWall(UserBean userBean, Integer id) {
        if (id == null) {
            throw new ServiceException("未获取到阿姨信息,请联系管理员");
        }
        TenantsStaffsMedia tenantsStaffsMedia = this.tenantsStaffsMediaDao.selectByPrimaryKey(id);
        if (tenantsStaffsMedia == null) {
            throw new ServiceException("未获取到阿姨信息,请联系管理员");
        }
        // 删除记录
        this.tenantsStaffsMediaDao.deleteByPrimaryKey(id);
        return JsonResult.success(null);
    }

    @Override
    public JsonResult setCover(UserBean userBean, Integer staffId, Integer id) {
        if (id == null) {
            throw new ServiceException("未获取到阿姨信息,请联系管理员");
        }
        if (staffId == null) {
            throw new ServiceException("未获取到阿姨信息,请联系管理员");
        }

        TenantsStaffsMediaExample tenantsStaffsMediaExample = new TenantsStaffsMediaExample();
        tenantsStaffsMediaExample.createCriteria().andStaffIdEqualTo(staffId).andIsDefaultEqualTo("1");
        List<TenantsStaffsMedia> tenantsStaffsMediaList = tenantsStaffsMediaDao.selectByExample(tenantsStaffsMediaExample);
        if (tenantsStaffsMediaList != null && tenantsStaffsMediaList.size() > 0) {
            for (TenantsStaffsMedia tenantsStaffsMedia : tenantsStaffsMediaList) {
                tenantsStaffsMedia.setIsDefault("0");
                tenantsStaffsMediaDao.updateByPrimaryKeySelective(tenantsStaffsMedia);
            }
        }

        TenantsStaffsMedia tenantsStaffsMedia = this.tenantsStaffsMediaDao.selectByPrimaryKey(id);
        if (tenantsStaffsMedia == null) {
            throw new ServiceException("未获取到阿姨信息,请联系管理员");
        }
        tenantsStaffsMedia.setIsDefault("1");//1为设置为封面0默认
        tenantsStaffsMedia.setModifyTime(new Date());
        tenantsStaffsMedia.setModifyAccount(userBean.getLoginAccount());
        tenantsStaffsMediaDao.updateByPrimaryKeySelective(tenantsStaffsMedia);
        return JsonResult.success(null);
    }

    @Override
    public JsonResult saveFinancialRecord(StaffFinancialForm staffFinancialForm) {


        TenantsFinanceRecords tenantsFinanceRecords = new TenantsFinanceRecords();
        tenantsFinanceRecords.setAddAccount(staffFinancialForm.getAddAccount());
        Date addTime = new Date();
        tenantsFinanceRecords.setAddTime(addTime);
        tenantsFinanceRecords.setInOutAmount(staffFinancialForm.getInOutAmount());
        tenantsFinanceRecords.setRemarks(staffFinancialForm.getRemarks());
        tenantsFinanceRecords.setPayType(staffFinancialForm.getPayType());
        tenantsFinanceRecords.setTenantId(staffFinancialForm.getTenantId());
        tenantsFinanceRecords.setStaffId(staffFinancialForm.getStaffId());

        Date finacetime = (null != staffFinancialForm.getFinanceTime() && !"".equals(staffFinancialForm.getFinanceTime())) ? DateUtils.parseDate(staffFinancialForm.getFinanceTime()) : null;
        tenantsFinanceRecords.setFinanceTime(finacetime);

        String inOutNo = commonService.createOrderNo("09");
        tenantsFinanceRecords.setInOutNo(inOutNo);//交易流水号
        if (staffFinancialForm.getPayType().equals("07") || staffFinancialForm.getPayType().equals("08") || staffFinancialForm.getPayType().equals("09")) {
            tenantsFinanceRecords.setInOutType("01");//01代表收入
        } else {
            tenantsFinanceRecords.setInOutType("02");//收支类型  02代表支出
        }
        tenantsFinanceRecords.setTransType("02");//交易方式 02表示线下
        tenantsFinanceRecords.setIsUsable("1");//1 代表可用

        TenantsStaffsInfoEntity tenantsStaffsInfoEntity = tenantsStaffsInfoDao.selectByPrimaryKey(staffFinancialForm.getStaffId());
        tenantsFinanceRecords.setInOutObject(tenantsStaffsInfoEntity.getStaffName());


        tenantsFinanceRecords.setStatus("03");//状态（01、待处理 02、处理中 03、已处理）
        tenantsFinanceRecords.setDraweeId(staffFinancialForm.getStaffId());//付款方id
        tenantsFinanceRecords.setDraweeType("04");//付款方类型01客户02家政机构03业务员04家政员05平台
        tenantsFinanceRecords.setPayeeId(staffFinancialForm.getTenantId());//收款方id
        tenantsFinanceRecords.setPayeeType("02");//收款方类型01客户02家政机构03业务员04家政员05平台
        return JsonResult.success(tenantsFinanceRecordsDao.insertFinancialRecord(tenantsFinanceRecords));
    }


    @Override
    public JsonResult saveRecommend(RecommendForm recommendForm) {
        TenantsRecommendEntity tenantsRecommendEntity = new TenantsRecommendEntity();
        tenantsRecommendEntity.setId(recommendForm.getId());
        tenantsRecommendEntity.setTenantId(recommendForm.getTenantId());
        tenantsRecommendEntity.setStaffId(recommendForm.getStaffId());
        tenantsRecommendEntity.setAddTime(recommendForm.getAddTime());
        tenantsRecommendEntity.setAddAccount(recommendForm.getAddAccount());
        tenantsRecommendEntity.setModifyTime(recommendForm.getModifyTime());
        tenantsRecommendEntity.setModifyAccount(recommendForm.getModifyAccount());
        return JsonResult.success(tenantsRecommendDao.insert(tenantsRecommendEntity));
    }

    /**
     * 阿姨当前时间是否在工作中
     */
    @Override
    public String staffIsWorkNow(Integer staffId) {
        //工作状态判断
        List<StaffWorkInfoJson> workList = getStaffWorkList(staffId);
        Date now = new Date();
        Date startTime = null;
        Date endTime = null;
        //判断当前时间阿姨是否在服务
        for (StaffWorkInfoJson bean : workList) {
            startTime = DateUtils.parseDate(bean.getBeginWorkTime());
            endTime = DateUtils.parseDate(bean.getEndWorkTime());
            if (now.after(startTime) && now.before(endTime)) {
                //在服务中，工作状态设为01：工作中
                return DictionarysCacheUtils.getWorkStatusName("01");
            }
        }
        //不在服务中，工作状态设为02：空闲
        return DictionarysCacheUtils.getWorkStatusName("02");
    }

    /**
     * @author xiehui
     * @param删除未通过的证书
     */
    @Override
    public JsonResult deleteCert(Integer staffId, Integer id) {
//		TenantsStaffsInfoEntity staffInfo=tenantsStaffsInfoDao.getStaffInfo(staffInfoParam);
//		if(staffInfo==null){
//			return JsonResult.failure(ResultCode.DATA_ERROR);
//		}
        TenantsStaffCertsInfoEntity tenantsStaffCertsInfoEntity = tenantsStaffCertsDao.getById(id);
        if (tenantsStaffCertsInfoEntity == null) {
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
        if (!"03".equals(tenantsStaffCertsInfoEntity.getCertifiedStatus())) {
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
        TenantsStaffCertsInfoEntity CertsInfo = new TenantsStaffCertsInfoEntity();
        CertsInfo.setId(id);
        CertsInfo.setIsUsable("02");
        tenantsStaffCertsDao.updateIsUsable(CertsInfo);
        return JsonResult.success(null);
    }

    /**
     * @author xiehui 校验证书
     */
    @Override
    public JsonResult certValid(Integer staffId, String authenticateGrade, String certType) {
        List<TenantsStaffCertsInfoEntity> certSum = tenantsStaffCertsDao.getSatffAllCerts(staffId);
        List<String> list = new ArrayList<String>();
        for (TenantsStaffCertsInfoEntity temp : certSum) {
            if ("01".equals(temp.getCertifiedStatus()) || "02".equals(temp.getCertifiedStatus())) {
                String newCert = temp.getCertType() + temp.getAuthenticateGrade();
                System.out.println(newCert);
                list.add(newCert);
                System.out.println(list);
            }
        }
        String params = certType + authenticateGrade;
        if (list.contains(params)) {
            return JsonResult.failure(ResultCode.Staff.CERT_TYPE_IS_REPETITION);
        }
        return JsonResult.success(null);
    }

}
