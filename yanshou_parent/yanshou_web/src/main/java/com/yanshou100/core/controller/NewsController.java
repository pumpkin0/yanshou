package com.yanshou100.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yanshou100.core.pojo.mydefine.Result;
import com.yanshou100.core.pojo.news.New;
import com.yanshou100.core.service.NewsService;

@RestController
@RequestMapping("news")
public class NewsController {
	@Autowired
	private NewsService newsService;

	/**
	 * 添加单条新闻
	 * @param mynew
	 * @return
	 */
	@RequestMapping("add")
	public Result add( New mynew) {
		try {
			newsService.add(mynew);
			return new Result(true, "添加成功");
		} catch (Exception e) {
			return new Result(false, "添加失败");
		}
	}
	/**
	 * 查询全部新闻
	 * @return
	 */
	@RequestMapping("queryAll")
	public List<New> queryAll() {
		return newsService.queryAll();
	}
	
	/**
	 * 分页查询  未写
	 * @return
	 */
	@RequestMapping("queryPage")
	public List<New> queryPage() {
		return newsService.queryPage();
	}
	
	/**
	 * 更新单条新闻
	 * @param news
	 * @return
	 */
	@RequestMapping("update")
	public void update( New news) {
		newsService.update(news);
	}
	
	@RequestMapping("delete")
	public void delete(Integer id){
		newsService.delete(id);
	}

}
