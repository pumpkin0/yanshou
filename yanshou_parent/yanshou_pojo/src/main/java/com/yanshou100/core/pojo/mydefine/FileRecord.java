package com.yanshou100.core.pojo.mydefine;

import java.io.Serializable;
import java.util.ArrayList;

public class FileRecord implements Serializable{
	private Long fileSize;
	private ArrayList<File> uploadFiles = new ArrayList<>();
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	public ArrayList<File> getUploadFiles() {
		return uploadFiles;
	}
	public void setUploadFiles(ArrayList<File> uploadFiles) {
		this.uploadFiles = uploadFiles;
	}
	
}
