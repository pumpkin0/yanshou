package com.yanshou100.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanshou100.core.dao.news.NewDao;
import com.yanshou100.core.pojo.news.New;
import com.yanshou100.core.service.NewsService;
@Service
@Transactional
public class NewsServiceImpl implements NewsService{
	@Autowired
	private NewDao newDao;
	@Override
	public void add(New mynew) {
		mynew.setCreateDate(new Date());
		newDao.insertSelective(mynew);
	}
	@Override
	public List<New> queryAll() {
		return newDao.selectByExample(null);
	}
	@Override
	public List<New> queryPage() {
		return null;
	}
	@Override
	public void update(New news) {
		newDao.updateByPrimaryKeySelective(news);
	}
	@Override
	public void delete(Integer id) {
		newDao.deleteByPrimaryKey(id);
	}

}
