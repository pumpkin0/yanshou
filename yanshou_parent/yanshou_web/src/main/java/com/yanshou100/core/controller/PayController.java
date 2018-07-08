package com.yanshou100.core.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yanshou100.core.pojo.log.PayLog;
import com.yanshou100.core.pojo.mydefine.Result;
import com.yanshou100.core.service.PayService;

@Controller
@RequestMapping("pay")
public class PayController {

	@Autowired
	private PayService payService;
	@Autowired
	private RedisTemplate redisTemplate;

	// TODO注意这儿需要获取用户名
	/**
	 * 生成二维码
	 */
	@RequestMapping("payFee")
	@ResponseBody
	public Map<String, String> payFee() {
		Map<String, String> map = payService.payFee();
		return map;
	}

	/**
	 * 页面跳转到付款页面
	 */
	@RequestMapping("toPay")
	public String toPay() {
		return "qrious";
	}
	
	/**
	 * 查询付款结果
	 */
	@RequestMapping("payResult")
	@ResponseBody
	public Result payResult() {
		try {
			int x = 0;
			// 循环10分钟，查询付款结果
			while (true) {
				// 如果付款成功，返回结果
				Map<String, String> map = payService.queryResult();
				if ("FAIL".equals(map.get("return_code"))) {
					return new Result(false, "查询付款结果失败");
				}

				if ("SUCCESS".equals(map.get("trade_state"))) {
					// 注意这儿需要获取用户的id
					String userId = "1";
					PayLog payLog = (PayLog) redisTemplate.boundHashOps("payLog").get(userId);
					// 记录付款的流水号
					payLog.setTransactionid(map.get("transaction_id"));
					//修改支付日志中的数据
					payService.updatePayLog(payLog);
					// 清空缓存数据
					redisTemplate.boundHashOps("payLog").delete(userId);
					return new Result(true, "支付成功");
				}
				Thread.sleep(3000);
				x++;
				if (x > 200) {
					// TODO 返回结果前需要做的事：1关闭订单 2删除数据库中的记录
					payService.closeOrderAndDeletePayLog();
					return new Result(false, "订单已经关闭,请重新提交");
				}
			}
		} catch (Exception e) {
			return new Result(false, "操作发生异常");
		}
	}
}
