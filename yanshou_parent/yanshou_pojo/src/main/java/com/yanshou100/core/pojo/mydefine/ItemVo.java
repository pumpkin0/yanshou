package com.yanshou100.core.pojo.mydefine;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.yanshou100.core.pojo.attachment.Attachment;
import com.yanshou100.core.pojo.item.Item;

public class ItemVo {
	private Item item;
	private List<MultipartFile> uploadFiles = new ArrayList<>();
	private List<Attachment> attachmentList;
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public List<MultipartFile> getUploadFiles() {
		return uploadFiles;
	}
	public void setUploadFiles(List<MultipartFile> uploadFiles) {
		this.uploadFiles = uploadFiles;
	}
	public List<Attachment> getAttachmentList() {
		return attachmentList;
	}
	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}
	
}
