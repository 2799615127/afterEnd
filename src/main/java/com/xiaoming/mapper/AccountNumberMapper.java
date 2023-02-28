package com.xiaoming.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.xiaoming.model.AccountNumber;

@Repository
@Mapper
public interface AccountNumberMapper{
	public List<AccountNumber> getAccountNumberList();
	public void insertAccountNumber(AccountNumber accountNumber);
	public List<Map<String, String>> queryUserName(Map<String, String> userName);
	public  List<Map<String, String>> queryEmail(Map<String, String> email);
	public AccountNumber queryAccountNumber(String userName,String passWrod);
}
