package com.yanshou100.core.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.yanshou100.core.pojo.attachment.Attachment;
import com.yanshou100.core.pojo.item.Item;
import com.yanshou100.core.pojo.log.PayLog;
import com.yanshou100.core.pojo.mydefine.ItemVo;
import com.yanshou100.core.service.FileUploadService;
import com.yanshou100.core.service.ItemService;
import com.yanshou100.core.service.PayLogService;

@Controller
@RequestMapping("item")
public class ItemController {
	
	
	@Autowired
	private ItemService itemService;

	@Autowired
	private PayLogService payLogService;

	@Autowired
	private RedisTemplate redisTemplate;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	
	@RequestMapping("itemList")
	@ResponseBody
	/*public String queryItemList(Model model) {
		List<Item> itemList = itemService.queryItemList();
		model.addAttribute(itemList);
		return "itemList";

	}*/
	public List<Item> queryItemList(Model model) {
		List<Item> itemList = itemService.queryItemList();
		return itemList;

	}

	@RequestMapping("add")
	public String add(ItemVo itemVo) throws IOException {
		String id = UUID.randomUUID().toString();
		itemVo.getItem().setId(id);
		itemVo.getItem().setCreateDate(new Date());
		// 0表示未删除
		itemVo.getItem().setIsDelete("0");

		// 这儿是用户的唯一Id，在发布文件前必须先登录，note：这儿只是一个假设值，后期还得修改
		String userId = "1";
		itemVo.getItem().setUserId(userId);
		List<Attachment> attachmentList = new ArrayList<>();
		// 对附件进行处理
		List<MultipartFile> uploadFiles = itemVo.getUploadFiles();
		long totalSize = 0;
		for (MultipartFile multipartFile : uploadFiles) {
			// 生成新的文件名
			String originalFilename = multipartFile.getOriginalFilename();
			String ext = originalFilename.substring(originalFilename.indexOf("."));
			final String filename = UUID.randomUUID().toString() + ext;
			//此代码把文件上传到本地服务器上，现上传到七牛云
			//multipartFile.transferTo(new File("D:/zhangbin/" + filename));

			long size = multipartFile.getSize();
			totalSize += size;
			// 创建附件对象，并存入合适参数
			Attachment attachment = new Attachment();
			attachment.setItemId(id);
			attachment.setIsDelete("0"); // 0代表不删除 1代表逻辑删除
			attachment.setFileName(filename);
			attachment.setFileSize(size);
			attachment.setIsPay("1"); // 1代表未付款 0代表已经付款

			attachmentList.add(attachment);
		}

		itemVo.setAttachmentList(attachmentList);
		itemService.add(itemVo);

		// 生成对应的支付日志(此处日志不完成，待支付完成后，继续补充，目的是记录付款金额)

		PayLog payLog = new PayLog();
		payLog.setItemId(id);
		// 在这儿还需要修改
		payLog.setPayFee(new Double(totalSize * 0.000001).intValue());

		payLog.setPayStatus("1"); // "1"代表未付款，"0"代表已经付款

		payLog.setUserId(userId);

		payLogService.add(payLog);
		
		// 放到redis中，在付款的时候方便进行快速读取
		redisTemplate.boundHashOps("payLog").put(userId, payLog);
		redisTemplate.boundHashOps("payLog").expire(1, TimeUnit.DAYS);

		return "success_add";
	}
}
