package com.fbee.modules.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.mybatis.dao.BankMapper;
import com.fbee.modules.service.BankService;

@Service
public class BankServiceImpl implements BankService {
	
	@Autowired
	BankMapper bankDao;

	@Transactional
	public JsonResult getBank() {
		return JsonResult.success(bankDao.getBank());
	}

}
