package com.fbee.modules.jsonData.json;

import java.math.BigDecimal;
import java.util.Date;

import com.fbee.modules.core.persistence.ModelSerializable;

/** 
* @ClassName: IndexJsonData 
* @Description: 首页信息jsonData
* @author 贺章鹏
* @date 2016年12月29日 上午11:27:53 
*  
*/
public class IndexJsonData implements ModelSerializable{

	private static final long serialVersionUID = 1L;

	private String moblieQrUrl;//手机网站二维码访问地址
	
	private String storeName;//门店名称
	
	private String contactAddress;//联系地址
	
	private String contactPhone;//联系电话
	
	private String websiteUrl;//站点地址
	
	private Date dueDate;//到期时间
	
	private BigDecimal grandTotalAmount;//累计金额
	
	private BigDecimal totalAmount;//账户总额
	
	private BigDecimal  frozenAmount;//账户冻结额度
	
	private BigDecimal  availableAmount;//账户可用额度
	
	private String bankLogo;//银行卡logo

	private String tenantsPhone;//联系电话
	
	private String bankName;//银行卡名称
	
	private String bankTailNumber;//银行卡尾号
	
	private String domain;//二级域名

	public String getTenantsPhone() {
		return tenantsPhone;
	}

	public void setTenantsPhone(String tenantsPhone) {
		this.tenantsPhone = tenantsPhone;
	}

	public String getMoblieQrUrl() {
		return moblieQrUrl;
	}

	public void setMoblieQrUrl(String moblieQrUrl) {
		this.moblieQrUrl = moblieQrUrl;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getWebsiteUrl() {
		return websiteUrl;
	}

	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public BigDecimal getGrandTotalAmount() {
		return grandTotalAmount;
	}

	public void setGrandTotalAmount(BigDecimal grandTotalAmount) {
		this.grandTotalAmount = grandTotalAmount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getFrozenAmount() {
		return frozenAmount;
	}

	public void setFrozenAmount(BigDecimal frozenAmount) {
		this.frozenAmount = frozenAmount;
	}

	public BigDecimal getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(BigDecimal availableAmount) {
		this.availableAmount = availableAmount;
	}

	public String getBankLogo() {
		return bankLogo;
	}

	public void setBankLogo(String bankLogo) {
		this.bankLogo = bankLogo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankTailNumber() {
		return bankTailNumber;
	}

	public void setBankTailNumber(String bankTailNumber) {
		this.bankTailNumber = bankTailNumber;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	
}
