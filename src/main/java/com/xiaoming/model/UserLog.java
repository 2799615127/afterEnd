package com.xiaoming.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户日志
 * @author 86155
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLog {
	private String userlogId;
	private String userlogHtml;
	private String userlogTime;
	private String userlogUpTime;
	private String userlogTitle;
	private String userId;
}

