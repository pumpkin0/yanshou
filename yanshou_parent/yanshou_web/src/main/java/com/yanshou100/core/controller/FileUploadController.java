package com.yanshou100.core.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yanshou100.core.pojo.mydefine.File;
import com.yanshou100.core.pojo.mydefine.FileRecord;
import com.yanshou100.core.service.FileUploadService;

@Controller
@RequestMapping("upload")
public class FileUploadController {
	//这儿是云服务器的域名，如果变化后，这儿得修改
	String URL = "http://p9bvhlxq8.bkt.clouddn.com";
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Autowired
	private FileUploadService fileUploadService;
	//TODO 获取用户
	@RequestMapping("uploadFile")
	@ResponseBody
	public void uploadFile(@RequestParam MultipartFile[] files) throws IOException{
		
		long start = System.currentTimeMillis();
		System.out.println(start);
		FileRecord fileRecord = new FileRecord();
		ArrayList<File> list = new ArrayList<>();
		String userId = "1";
		long totalSize = 0;
		for (MultipartFile file : files) {
			//获取文件的相关属性，包括如下：
			//1、文件的原文件名
			String originalFilename = file.getOriginalFilename();
			String ext = originalFilename.substring(originalFilename.indexOf("."));
			//2、文件的新文件名
			
			final String filename = UUID.randomUUID().toString() + ext;
			String fileUrl = URL + filename;
			list.add(new File(originalFilename,fileUrl));
			
			//3、文件的大小
			totalSize += file.getSize();
			final byte[] filebytes = file.getBytes();
			final InputStream inputStream = file.getInputStream();
			//通过二进制格式上传
			//fileUploadService.uploadAndResponse(filename, filebytes);
			Thread thread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					fileUploadService.uploadAndResponse(filename, inputStream);
				}
			});
			thread.start();
		}
		fileRecord.setUploadFiles(list);
		fileRecord.setFileSize(totalSize);
		long end = System.currentTimeMillis();
		System.out.println(end - start);
		//放入redis缓存中，key值为用户的id,value为一个文件对象
		//返回file对象，用于回显
		redisTemplate.boundHashOps("fileRecord").put(userId, fileRecord);
		//return fileRecord;
		
	}
	
}
