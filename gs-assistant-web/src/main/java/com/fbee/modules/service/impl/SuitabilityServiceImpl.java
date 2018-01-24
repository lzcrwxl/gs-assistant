package com.fbee.modules.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fbee.modules.bean.ErrorMsg;
import com.fbee.modules.bean.MatchingModel;
import com.fbee.modules.bean.OrderMatchBean;
import com.fbee.modules.bean.consts.Status;
import com.fbee.modules.core.Log;
import com.fbee.modules.core.config.Global;
import com.fbee.modules.mybatis.dao.TenantsStaffCertsInfoMapper;
import com.fbee.modules.mybatis.dao.TenantsStaffJobInfoMapper;
import com.fbee.modules.mybatis.dao.TenantsStaffSerItemsMapper;
import com.fbee.modules.mybatis.dao.TenantsStaffsInfoMapper;
import com.fbee.modules.mybatis.entity.TenantsStaffCertsInfoEntity;
import com.fbee.modules.mybatis.entity.TenantsStaffJobInfoEntity;
import com.fbee.modules.mybatis.entity.TenantsStaffSerItemsEntity;
import com.fbee.modules.mybatis.entity.TenantsStaffsInfoEntity;
import com.fbee.modules.service.SuitabilityService;
import com.fbee.modules.utils.SkillMacthUtils;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/** 
* @ClassName: SuitabilityServiceImpl 
* @Description: 匹配度处理类
* @author 贺章鹏
* @date 2017年2月8日 下午2:58:42 
*  
*/
@Service
public class SuitabilityServiceImpl implements SuitabilityService {
	
	@Autowired
	TenantsStaffSerItemsMapper tenantsStaffSerItemsDao;
	
	@Autowired
	TenantsStaffJobInfoMapper tenantsStaffJobInfoDao;
	
	@Autowired
	TenantsStaffsInfoMapper tenantsStaffsInfoDao;
	
	@Autowired
	TenantsStaffCertsInfoMapper tenantsStaffCertsInfoDao;

	@Override
	public double getMatchRate(OrderMatchBean order, Integer staffId) {
		try {
			TenantsStaffSerItemsEntity entity=new TenantsStaffSerItemsEntity();
			entity.setStaffId(staffId);
			entity.setServiceItemCode(order.getServiceType());
			TenantsStaffSerItemsEntity staffItem=tenantsStaffSerItemsDao.get(entity);
			MatchingModel match=new MatchingModel();
			if(staffItem==null){
				match.setIsHasTrade(Global.NO);
			}else{
				TenantsStaffJobInfoEntity job=tenantsStaffJobInfoDao.getById(staffId);
				List<TenantsStaffCertsInfoEntity> certs=tenantsStaffCertsInfoDao.getSatffAllCerts(staffId);
				TenantsStaffsInfoEntity staffsInfo=tenantsStaffsInfoDao.getById(staffId);
				
				match.setAge(staffsInfo.getAge()!=null?String.valueOf(staffsInfo.getAge()):"");
				match.setSeviceNature(staffItem.getServiceNature());
				match.setPrice(job.getPrice()!=null?String.valueOf(job.getPrice()):"");
				match.setExperience(job.getWorkExperience());
				match.setNativePlace(staffsInfo.getNativePlace());
				match.setZodiac(staffsInfo.getZodiac());
				match.setSex(staffsInfo.getSex());
				match.setEducation(staffsInfo.getEducarion());
				match.setLanguage(job.getLanguageFeature());
				match.setCooking(job.getCookingFeature());
				match.setCharacter(job.getCharacerFeature());
				match.setIsHasOlder(job.getElderlySupport());
				match.setIsHasPet(job.getPetFeeding());
				//match.setServiceContents(staffItem.getSkills());
				match.setIsHasTrade(Global.YES);
				
				//对证书进行特殊处理--逻辑待确认不同的服务类型
				match.setCerts(getCerts(order.getServiceType(),certs));
				
			}
			return SkillMacthUtils.getInstance().getMatchRate(order.getServiceType(), match, order);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(ErrorMsg.MATCH_ERROR,e);
		}
			
		return 0;
	}

	//特殊处理证书匹配问题
	public Set<String> getCerts(String serviceType,List<TenantsStaffCertsInfoEntity> certs){
		Set<String> certstr=Sets.newHashSet();
		StringBuffer sb=new StringBuffer();
		Map<String,TenantsStaffCertsInfoEntity>  resultMap=converCertMap(certs);
		TenantsStaffCertsInfoEntity bean=new TenantsStaffCertsInfoEntity();
		if(Status.ServiceTypes.ST_YS.equals(serviceType)){//月嫂
			//母婴护理证（专项）
			bean=resultMap.get(sb.append(Status.CertTypes.CT_MYHL).append(Status.CertLevels.CT_ZX).toString());
			if(bean!=null){
				certstr.add(Status.MatchCertKey.MATCH_MYHL);
			}
			//高级母婴护理证
			bean=null;
			bean=resultMap.get(sb.append(Status.CertTypes.CT_GJMYHL).append(Status.CertLevels.NO_LEVEL).toString());
			if(bean!=null && bean.getCertifiedStatus().equals(Status.CertifiedStatus.CERTIFIED)){//已认证
				certstr.add(Status.MatchCertKey.MATCH_GJMYHL);
			}
			//催乳师证（高级）
			bean=null;
			bean=resultMap.get(sb.append(Status.CertTypes.CT_CRS).append(Status.CertLevels.CT_GAOJI).toString());
			if(bean!=null && bean.getCertifiedStatus().equals(Status.CertifiedStatus.CERTIFIED)){//已认证
				certstr.add(Status.MatchCertKey.MATCH_CRS);
			}
		}
		if(Status.ServiceTypes.ST_YYS.equals(serviceType)){//育婴师
			//育婴师初级 先找是否有高级 再找中级 再找初级
			bean=null;
			bean=resultMap.get(sb.append(Status.CertTypes.CT_YYS).append(Status.CertLevels.CT_GAOJI).toString());
			if(bean!=null){
				certstr.add(Status.MatchCertKey.MATCH_YYSCJ);
				certstr.add(Status.MatchCertKey.MATCH_YYSZJ);
			}
			//中级
			bean=null;
			bean=resultMap.get(sb.append(Status.CertTypes.CT_YYS).append(Status.CertLevels.CT_ZHONGJI).toString());
			if(bean!=null){
				certstr.add(Status.MatchCertKey.MATCH_YYSZJ);
			}
			//初级
			bean=null;
			bean=resultMap.get(sb.append(Status.CertTypes.CT_YYS).append(Status.CertLevels.CT_CHUJI).toString());
			if(bean!=null){
				certstr.add(Status.MatchCertKey.MATCH_YYSCJ);
			}
			
			//早教
			bean=null;
			bean=resultMap.get(sb.append(Status.CertTypes.CT_ZJZ).append(Status.CertLevels.NO_LEVEL).toString());
			if(bean!=null){
				certstr.add(Status.MatchCertKey.MATCH_ZJ);
			}
		}
		if(Status.ServiceTypes.ST_BM.equals(serviceType)){//保姆
			//家政服务 先找是否有高级 再找中级 再找初级 专项
			bean=null;
			bean=resultMap.get(sb.append(Status.CertTypes.CT_JZFW).append(Status.CertLevels.CT_GAOJI).toString());
			if(bean!=null){
				certstr.add(Status.MatchCertKey.MATCH_JZFW);
			}
			//中级
			bean=null;
			bean=resultMap.get(sb.append(Status.CertTypes.CT_JZFW).append(Status.CertLevels.CT_ZHONGJI).toString());
			if(bean!=null){
				certstr.add(Status.MatchCertKey.MATCH_JZFW);
			}
			//初级
			bean=null;
			bean=resultMap.get(sb.append(Status.CertTypes.CT_JZFW).append(Status.CertLevels.CT_CHUJI).toString());
			if(bean!=null){
				certstr.add(Status.MatchCertKey.MATCH_JZFW);
			}
			//专项
			bean=null;
			bean=resultMap.get(sb.append(Status.CertTypes.CT_JZFW).append(Status.CertLevels.CT_ZX).toString());
			if(bean!=null){
				certstr.add(Status.MatchCertKey.MATCH_JZFW);
			}
			
			//育婴师 先找是否有高级 再找中级 再找初级
			bean=null;
			bean=resultMap.get(sb.append(Status.CertTypes.CT_YYS).append(Status.CertLevels.CT_GAOJI).toString());
			if(bean!=null){
				certstr.add(Status.MatchCertKey.MATCH_YYSCJ);
			}
			//中级
			bean=null;
			bean=resultMap.get(sb.append(Status.CertTypes.CT_YYS).append(Status.CertLevels.CT_ZHONGJI).toString());
			if(bean!=null){
				certstr.add(Status.MatchCertKey.MATCH_YYSCJ);
			}
			//初级
			bean=null;
			bean=resultMap.get(sb.append(Status.CertTypes.CT_YYS).append(Status.CertLevels.CT_CHUJI).toString());
			if(bean!=null){
				certstr.add(Status.MatchCertKey.MATCH_YYSCJ);
			}
			
			//家庭营养师证
			bean=null;
			bean=resultMap.get(sb.append(Status.CertTypes.CT_JZFW).append(Status.CertLevels.NO_LEVEL).toString());
			if(bean!=null && bean.getCertifiedStatus().equals(Status.CertifiedStatus.CERTIFIED)){
				certstr.add(Status.MatchCertKey.MATCH_JTYYS);
			}
		}
		
		if(Status.ServiceTypes.ST_BM.equals(serviceType)){//养老陪护
			//养老护理证（初级）
			bean=null;
			bean=resultMap.get(sb.append(Status.CertTypes.CT_YLHL).append(Status.CertLevels.CT_CHUJI).toString());
			if(bean!=null){
				certstr.add(Status.MatchCertKey.MATCH_YLHL);
			}
			//健康（医疗）照护证
			bean=null;
			bean=resultMap.get(sb.append(Status.CertTypes.CT_YLZH).append(Status.CertLevels.CT_CHUJI).toString());
			if(bean!=null){
				certstr.add(Status.MatchCertKey.MATCH_YLZH);
			}
			
			//家政服务 先找是否有高级 再找中级 再找初级 专项
			bean=null;
			bean=resultMap.get(sb.append(Status.CertTypes.CT_JZFW).append(Status.CertLevels.CT_GAOJI).toString());
			if(bean!=null){
				certstr.add(Status.MatchCertKey.MATCH_JZFW);
			}
			//中级
			bean=null;
			bean=resultMap.get(sb.append(Status.CertTypes.CT_JZFW).append(Status.CertLevels.CT_ZHONGJI).toString());
			if(bean!=null){
				certstr.add(Status.MatchCertKey.MATCH_JZFW);
			}
			//初级
			bean=null;
			bean=resultMap.get(sb.append(Status.CertTypes.CT_JZFW).append(Status.CertLevels.CT_CHUJI).toString());
			if(bean!=null){
				certstr.add(Status.MatchCertKey.MATCH_JZFW);
			}
			//专项
			bean=null;
			bean=resultMap.get(sb.append(Status.CertTypes.CT_JZFW).append(Status.CertLevels.CT_ZX).toString());
			if(bean!=null){
				certstr.add(Status.MatchCertKey.MATCH_JZFW);
			}
		}
		if(Status.ServiceTypes.ST_ZDG.equals(serviceType)){//钟点工
			//家政服务 先找是否有高级 再找中级 再找初级 专项
			bean=null;
			bean=resultMap.get(sb.append(Status.CertTypes.CT_JZFW).append(Status.CertLevels.CT_GAOJI).toString());
			if(bean!=null){
				certstr.add(Status.MatchCertKey.MATCH_JZFW);
			}
			//中级
			bean=null;
			bean=resultMap.get(sb.append(Status.CertTypes.CT_JZFW).append(Status.CertLevels.CT_ZHONGJI).toString());
			if(bean!=null){
				certstr.add(Status.MatchCertKey.MATCH_JZFW);
			}
			//初级
			bean=null;
			bean=resultMap.get(sb.append(Status.CertTypes.CT_JZFW).append(Status.CertLevels.CT_CHUJI).toString());
			if(bean!=null){
				certstr.add(Status.MatchCertKey.MATCH_JZFW);
			}
			//专项
			bean=null;
			bean=resultMap.get(sb.append(Status.CertTypes.CT_JZFW).append(Status.CertLevels.CT_ZX).toString());
			if(bean!=null){
				certstr.add(Status.MatchCertKey.MATCH_JZFW);
			}
			//育婴师初级 先找是否有高级 再找中级 再找初级
			bean=null;
			bean=resultMap.get(sb.append(Status.CertTypes.CT_YYS).append(Status.CertLevels.CT_GAOJI).toString());
			if(bean!=null){
				certstr.add(Status.MatchCertKey.MATCH_YYSCJ);
			}
			//中级
			bean=null;
			bean=resultMap.get(sb.append(Status.CertTypes.CT_YYS).append(Status.CertLevels.CT_ZHONGJI).toString());
			if(bean!=null){
				certstr.add(Status.MatchCertKey.MATCH_YYSCJ);
			}
			//初级
			bean=null;
			bean=resultMap.get(sb.append(Status.CertTypes.CT_YYS).append(Status.CertLevels.CT_CHUJI).toString());
			if(bean!=null){
				certstr.add(Status.MatchCertKey.MATCH_YYSCJ);
			}
		}
		if(Status.ServiceTypes.ST_ZDG.equals(serviceType)){//家庭管家暂无匹配
		}
		return certstr;
	}
	
	//将数据转换为map查找
	public Map<String,TenantsStaffCertsInfoEntity> converCertMap(List<TenantsStaffCertsInfoEntity> certs){
		Map<String,TenantsStaffCertsInfoEntity> resultMap=Maps.newHashMap();
		StringBuffer sb=new StringBuffer();
		for(TenantsStaffCertsInfoEntity bean:certs){
			sb.setLength(0);
			sb.append(bean.getCertType()).append(bean.getLevel());
			resultMap.put(sb.toString(), bean);
		}
		return resultMap;
	}
	
}
