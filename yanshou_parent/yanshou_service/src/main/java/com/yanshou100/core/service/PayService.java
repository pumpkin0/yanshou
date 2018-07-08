package com.yanshou100.core.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yanshou100.core.pojo.log.PayLog;

public interface PayService {

	Map<String, String> payFee();

	Map<String, String> queryResult();

	void updatePayLog(PayLog payLog);

	void closeOrderAndDeletePayLog();

}
