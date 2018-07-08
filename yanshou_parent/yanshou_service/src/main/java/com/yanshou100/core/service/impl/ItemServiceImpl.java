package com.yanshou100.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanshou100.core.dao.attachment.AttachmentDao;
import com.yanshou100.core.dao.item.ItemDao;
import com.yanshou100.core.pojo.attachment.Attachment;
import com.yanshou100.core.pojo.item.Item;
import com.yanshou100.core.pojo.item.ItemQuery;
import com.yanshou100.core.pojo.item.ItemQuery.Criteria;
import com.yanshou100.core.pojo.mydefine.ItemVo;
import com.yanshou100.core.service.ItemService;
@Service
@Transactional
public class ItemServiceImpl implements ItemService {
	@Autowired
	private ItemDao itemDao;
	@Autowired
	private AttachmentDao attachmentDao;
	@Override
	public List<Item> queryItemList() {
		ItemQuery query = new ItemQuery();
		query.setOrderByClause("create_date");
		return itemDao.selectByExample(query);
	}

	@Override
	public void add(Item item) {
		itemDao.insertSelective(item);
	}

	@Override
	public void add(ItemVo itemVo) {
		itemDao.insertSelective(itemVo.getItem());
		List<Attachment> attachmentList = itemVo.getAttachmentList();
		for (Attachment attachment : attachmentList) {
			attachmentDao.insertSelective(attachment);
		}
	}
	
	
}
