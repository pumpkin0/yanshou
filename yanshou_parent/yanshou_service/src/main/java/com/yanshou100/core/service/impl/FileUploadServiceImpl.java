package com.yanshou100.core.service.impl;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Recorder;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.persistent.FileRecorder;
import com.qiniu.util.Auth;
import com.yanshou100.core.service.FileUploadService;

@Service
public class FileUploadServiceImpl implements FileUploadService {

	@Value("${accessKey}")
	public String AccessKey;
	@Value("${secretKey}")
	public String SecretKey;
	@Value("${bucket}")
	public String Bucket;

	@Override
	public void uploadAndResponse(String filename, byte[] filebytes) {

		// 构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.zone1());
		// 构造断点记录对象
		Recorder recorder = null;
		try {
			recorder = new FileRecorder("D:/zhangbin/recorder");
		} catch (IOException e) {
			e.printStackTrace();
		}
		UploadManager uploadManager = new UploadManager(cfg,recorder);
		// 默认不指定key的情况下，以文件内容的hash值作为文件名
		String key = filename;
		// byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
		Auth auth = Auth.create(AccessKey, SecretKey);
		String upToken = auth.uploadToken(Bucket);
		try {
			Response response = uploadManager.put(filebytes, key, upToken);
			// 解析上传成功的结果
			DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
			System.out.println(putRet.key);
			//TODO 可以通过判断putRet.key的值 与 filename的值做对比，如果相同说明上传成功，更改文件上传的属性字段
			System.out.println(putRet.hash);
		} catch (QiniuException ex) {
			Response r = ex.response;
			System.err.println(r.toString());
			try {
				System.err.println(r.bodyString());
			} catch (QiniuException ex2) {
				// ignore
			}
		}
	}

	@Override
	public void uploadAndResponse(String filename, InputStream inputStream) {
		// 构造一个带指定Zone对象的配置类
				Configuration cfg = new Configuration(Zone.zone1());
				// 构造断点记录对象
				Recorder recorder = null;
				try {
					recorder = new FileRecorder("D:/zhangbin/recorder");
				} catch (IOException e) {
					e.printStackTrace();
				}
				UploadManager uploadManager = new UploadManager(cfg,recorder);
				// 默认不指定key的情况下，以文件内容的hash值作为文件名
				String key = filename;
				// byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
				Auth auth = Auth.create(AccessKey, SecretKey);
				String upToken = auth.uploadToken(Bucket);
				try {
					Response response = uploadManager.put(inputStream, key, upToken, null, null) ;
					// 解析上传成功的结果
					DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
					System.out.println(putRet.key);
					//TODO 可以通过判断putRet.key的值 与 filename的值做对比，如果相同说明上传成功，更改文件上传的属性字段
					System.out.println(putRet.hash);
				} catch (QiniuException ex) {
					Response r = ex.response;
					System.err.println(r.toString());
					try {
						System.err.println(r.bodyString());
					} catch (QiniuException ex2) {
						// ignore
					}
				}
		
	}

}
