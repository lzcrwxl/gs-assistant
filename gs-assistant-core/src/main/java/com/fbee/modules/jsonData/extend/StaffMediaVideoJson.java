package com.fbee.modules.jsonData.extend;

import com.fbee.modules.core.persistence.ModelSerializable;

/** 
* @ClassName: StaffMediaInfo 
* @Description: 媒体信息（视频、图片）
* @author 贺章鹏
* @date 2017年1月5日 下午3:56:32 
*  
*/
public class StaffMediaVideoJson implements ModelSerializable{

	private static final long serialVersionUID = 1L;
	
	private Integer mediaId;//媒体id--主键
	
	private String path;//文件路径
	
	private Integer staffId;//员工id
	
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
}
