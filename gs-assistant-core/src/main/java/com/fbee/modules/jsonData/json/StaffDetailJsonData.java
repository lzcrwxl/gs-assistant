package com.fbee.modules.jsonData.json;

import java.util.List;
import com.fbee.modules.jsonData.extend.StaffBankInfoJson;
import com.fbee.modules.jsonData.extend.StaffBaseInfoJson;
import com.fbee.modules.jsonData.extend.StaffFinanceInfoJson;
import com.fbee.modules.jsonData.extend.StaffJobInfoJson;
import com.fbee.modules.jsonData.extend.StaffMediaPictureJson;
import com.fbee.modules.jsonData.extend.StaffMediaVideoJson;
import com.fbee.modules.jsonData.extend.StaffPolicyInfoJson;
import com.fbee.modules.jsonData.extend.StaffServiceInfoJson;
import com.fbee.modules.jsonData.extend.StaffWorkInfoJson;

/** 
* @ClassName: StaffDetailJsonData 
* @Description: 员工（阿姨）详情
* 包含：	用户基本信息、求职信息、视频图片秀、银行信息、保单信息、
* 		派工记录、财务流水、*评价记录、服务认证记录
* @author 贺章鹏
* @date 2017年1月5日 下午3:13:45 
*  
*/
public class StaffDetailJsonData {
	
	//用户基本信息
	private StaffBaseInfoJson baseInfo;
	//银行信息
	private StaffBankInfoJson bankInfo;
	//求职信息
	private StaffJobInfoJson jobInfo;
	//保单信息
	private List<StaffPolicyInfoJson> policyList;
	//视频秀
	private StaffMediaVideoJson videoMedia;
	//图片秀
	private List<StaffMediaPictureJson> imageMedia;
	//派工记录
	private List<StaffWorkInfoJson> workList;
	//财务记录
	private List<StaffFinanceInfoJson> financeList;
	//服务认证记录
	private List<StaffServiceInfoJson> serviceList;
	//工作安排表
	private List<StaffWorkInfoJson> worktimeList;
	
	public StaffBaseInfoJson getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(StaffBaseInfoJson baseInfo) {
		this.baseInfo = baseInfo;
	}

	public StaffBankInfoJson getBankInfo() {
		return bankInfo;
	}

	public void setBankInfo(StaffBankInfoJson bankInfo) {
		this.bankInfo = bankInfo;
	}

	public StaffJobInfoJson getJobInfo() {
		return jobInfo;
	}

	public void setJobInfo(StaffJobInfoJson jobInfo) {
		this.jobInfo = jobInfo;
	}

	public List<StaffPolicyInfoJson> getPolicyList() {
		return policyList;
	}

	public void setPolicyList(List<StaffPolicyInfoJson> policyList) {
		this.policyList = policyList;
	}

	public StaffMediaVideoJson getVideoMedia() {
		return videoMedia;
	}

	public void setVideoMedia(StaffMediaVideoJson videoMedia) {
		this.videoMedia = videoMedia;
	}

	public List<StaffMediaPictureJson> getImageMedia() {
		return imageMedia;
	}

	public void setImageMedia(List<StaffMediaPictureJson> imageMedia) {
		this.imageMedia = imageMedia;
	}

	public List<StaffWorkInfoJson> getWorkList() {
		return workList;
	}

	public void setWorkList(List<StaffWorkInfoJson> workList) {
		this.workList = workList;
	}

	public List<StaffFinanceInfoJson> getFinanceList() {
		return financeList;
	}

	public void setFinanceList(List<StaffFinanceInfoJson> financeList) {
		this.financeList = financeList;
	}

	public List<StaffServiceInfoJson> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<StaffServiceInfoJson> serviceList) {
		this.serviceList = serviceList;
	}

	public List<StaffWorkInfoJson> getWorktimeList() {
		return worktimeList;
	}

	public void setWorktimeList(List<StaffWorkInfoJson> worktimeList) {
		this.worktimeList = worktimeList;
	}
}
