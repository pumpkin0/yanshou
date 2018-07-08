package com.yanshou100.core.pojo.mydefine;

import java.io.Serializable;

public class File implements Serializable{
	private String originalFilename;
	private String fileUrl;
	public File() {
		super();
	}
	public File(String originalFilename, String fileUrl) {
		super();
		this.originalFilename = originalFilename;
		this.fileUrl = fileUrl;
	}
	public String getOriginalFilename() {
		return originalFilename;
	}
	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	
}
