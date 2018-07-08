package com.yanshou100.core.service;

import java.util.List;

import com.yanshou100.core.pojo.news.New;

public interface NewsService {

	void add(New mynew);

	List<New> queryAll();

	List<New> queryPage();

	void update(New news);

	void delete(Integer id);

}
