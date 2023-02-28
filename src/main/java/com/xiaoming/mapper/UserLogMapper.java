package com.xiaoming.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.xiaoming.model.AccountNumber;
import com.xiaoming.model.UserLog;

@Repository
@Mapper
public interface UserLogMapper{
	public void insertAccountNumber(UserLog userLog);
	public List<UserLog> getUserLogList(String userId);
	public UserLog getUserLog(String userlogId);
	public void deUserLog(String userlogId);
}
