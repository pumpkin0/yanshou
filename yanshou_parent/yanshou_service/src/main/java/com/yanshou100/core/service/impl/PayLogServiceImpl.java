package com.yanshou100.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanshou100.core.dao.log.PayLogDao;
import com.yanshou100.core.pojo.log.PayLog;
import com.yanshou100.core.service.PayLogService;
@Service
@Transactional
public class PayLogServiceImpl implements PayLogService {
	@Autowired
	private PayLogDao payLogDao;
	@Override
	public void add(PayLog payLog) {
		payLogDao.insertSelective(payLog);
	}

}
