package com.xiaoming.controller;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xiaoming.mapper.AccountNumberMapper;
import com.xiaoming.model.AccountNumber;
import com.xiaoming.utils.MD5Util;
import com.xiaoming.utils.RandomNum;
import com.xiaoming.utils.TokenUtil;
 

/**
 * 账户登录或注册
 * @author 86155
 *
 */
//@ResponseBody
@RestController
//@CrossOrigin(origins = "http://localhost:5173",allowedHeaders = "{*,Access-Control-Expose-Headers : 'Authorization'}")
public class AccountNumBerController {
	@Autowired
    private AccountNumberMapper accountNumberMapper;
    
	/**
	 * 验证账号用户名存在否
	 * @param userName
	 * @return
	 */
    @GetMapping("/account/queryUserName")
    public List<Map> queryUserName(@RequestParam Map userName){
        List<Map> userList = accountNumberMapper.queryUserName(userName);
        return userList;
    }
    
    /***
     * 验证emll存在否
     * @param email
     * @return
     */
    @GetMapping("/account/queryEmail")
    public List<Map> queryEmail(@RequestParam Map email){
        List<Map> queryEmail = accountNumberMapper.queryEmail(email);
        return queryEmail;
    }
    
    
    /**
     * 增加用户
     * @param accountNumber
     * @return
     */
    @PostMapping("/account/insertAccountNumber")
    public String insertAccountNumber(@RequestBody AccountNumber accountNumber){
    	Date date = new Date(); 
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    	accountNumber.setLogonTime(formatter.format(date));
    	accountNumberMapper.insertAccountNumber(accountNumber);
    	return "success";
    }
    

    /***
     * 验证码，使用jwt的token传输验证
     * @return
     */
    @GetMapping("/account/randomNum")
    public String randomNum() {
    	TokenUtil tokenUtil=new TokenUtil();
    	String uuid=UUID.randomUUID().toString();
    	String random=RandomNum.random();
    	//使用自定义base验证
    	String baseRnum=new String(MD5Util.base64Encrypt(random));
    	String token=tokenUtil.getToken(uuid,baseRnum, 1000*30L);
    	return token;
    }
    
    /***
     * 登录验证
     * @param response
     * @param httpServletRequest
     * @param verifyLogin
     * @return
     */
    @PostMapping("/account/verifyLogin")
    public Map<String,String>  verifyLogin(HttpServletResponse response,HttpServletRequest httpServletRequest,@RequestBody Map<String, String> verifyLogin){
    	Map<String,String> user=new HashMap<String,String> ();
    	TokenUtil tokenUtil=new TokenUtil();
    	String randomNumToken = httpServletRequest.getHeader("randomNum");
    	//验证错误
    	if (null == randomNumToken || "".equals(randomNumToken.trim())) {
    		user.put("tokenNo", "tokenNo");
            return user;
        }
    	//检查验证码错误，错误会报错，应该用日志记入错误信息，但没弄
    	Map<String, String> tokenMap=tokenUtil.parseToken(randomNumToken);
    	String[] randomNum=new String(MD5Util.base64Decrypt(tokenMap.get("content").getBytes())).split(",");
    	String verifyNum="";
    	//解析验证码转换可认识的验证码
    	for(int i=0;i<randomNum.length;i++) {
    		verifyNum+=RandomNum.identifyCodes[Integer.parseInt(randomNum[i])];
    	}
    	//验证验证码进入登录验证
    	if(verifyNum.equals(verifyLogin.get("randomNum"))) {
    		String userName= verifyLogin.get("userName");
    		String passwrod=verifyLogin.get("passWrod");
    		AccountNumber accountNumber=accountNumberMapper.queryAccountNumber(userName,passwrod);
    		//查询用户是否存在
    		if(accountNumber!=null) {
    			user.put("success", "success");
    			//使用jwt存储用户id和名字
    			String token=tokenUtil.getToken(accountNumber.getUserId(),accountNumber.getUserName(), 1000*60L);
    			//发送给前端
    			response.setHeader("setToken", token);
    			return user;
    		}else {
    			//反馈前端没有查询到用户
    			user.put("no", "no");
    	        return user;
    		}
    	}
    	//验证码错误
    	user.put("randomNo", "randomNo");
        return user;
    }
}