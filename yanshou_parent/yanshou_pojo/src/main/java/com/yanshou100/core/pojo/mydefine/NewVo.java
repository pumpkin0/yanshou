package com.yanshou100.core.pojo.mydefine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.yanshou100.core.pojo.news.New;

public class NewVo implements Serializable {
	private List<New> itemList = new ArrayList<>();

	public List<New> getItemList() {
		return itemList;
	}

	public void setItemList(List<New> itemList) {
		this.itemList = itemList;
	}
	
}
