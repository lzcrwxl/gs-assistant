package com.fbee.modules.mybatis.dao;

import java.util.List;

import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.BankEntity;
import com.fbee.modules.mybatis.model.Bank;

@MyBatisDao
public interface BankMapper {
    int insert(Bank record);

	int insertSelective(Bank record);
	
	List<BankEntity> getBank();

}