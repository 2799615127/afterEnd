package com.xiaoming.model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 账号
 * @author 86155
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountNumber {
	private String userId;
	private String passWrod;
	private String userName;
	private String email;
	private String logonTime;
	
}