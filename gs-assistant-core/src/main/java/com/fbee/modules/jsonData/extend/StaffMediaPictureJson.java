package com.fbee.modules.jsonData.extend;

import com.fbee.modules.core.persistence.ModelSerializable;

/** 
* @ClassName: StaffMediaPictureJson 
* @Description: TODO
* @author 贺章鹏
* @date 2017年1月10日 下午5:04:48 
*  
*/
public class StaffMediaPictureJson  implements ModelSerializable{

	private static final long serialVersionUID = 1L;
	
	private Integer mediaId;//媒体id--主键
	
	private String path;//文件路径
	
	private Integer staffId;//员工id
	
	private String idDefault;//是否默认

	public Integer getMediaId() {
		return mediaId;
	}

	public void setMediaId(Integer mediaId) {
		this.mediaId = mediaId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getStaffId() {
		return staffId;
	}

	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}

	public String getIdDefault() {
		return idDefault;
	}

	public void setIdDefault(String idDefault) {
		this.idDefault = idDefault;
	}
}
