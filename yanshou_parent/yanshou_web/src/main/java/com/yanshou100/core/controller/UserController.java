package com.yanshou100.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("user")
public class UserController {
	@RequestMapping("test")
	public void test(@RequestParam(value="item",required=false, defaultValue = "1") Integer id){
		System.out.println(id);
	}
	@RequestMapping("test2/{id}")
	public void test2(@PathVariable() Integer id){
		System.out.println(id);
	}
}
