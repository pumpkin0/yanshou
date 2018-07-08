package com.yanshou100.core.service;

import java.util.List;

import com.yanshou100.core.pojo.item.Item;
import com.yanshou100.core.pojo.mydefine.ItemVo;

public interface ItemService {

	List<Item> queryItemList();

	void add(Item item);

	void add(ItemVo itemVo);
}
