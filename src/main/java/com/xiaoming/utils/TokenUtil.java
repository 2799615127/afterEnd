package com.xiaoming.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt令牌 token
 * @author 
 * @version 1.0
 * @create 2021/2/10 22:23
 */
@Component
public class TokenUtil {
//    @Value("${token.privateKey}")
    private static String privateKey="密码加密加密再加密";
  //token过期时间
    //public static final Long EXPIRE_DATE = 1000*60L;

    /**
     * 加密token.
     */
    public String getToken(String Id, String content,Long EXPIRE_DATE) {
    	Date now = new Date();
        //这个是放到负载payLoad 里面,魔法值可以使用常量类进行封装.
        String token = JWT
                .create().withExpiresAt(new Date(now.getTime() + EXPIRE_DATE))
                .withClaim("Id" ,Id)
                .withClaim("content", content)
                .sign(Algorithm.HMAC256(privateKey));
        return token;
    }

    /**
     * 解析token.
     * (优化可以用常量固定魔法值+使用DTO在 mvc 之前传输数据，而不是 map,这里因为篇幅原因就不做了)
     * {
     * "userId": "3412435312",
     * "userRole": "ROLE_USER",
     * "timeStamp": "134143214"
     * }
     */
    public Map<String, String> parseToken(String token) {
        HashMap<String, String> map = new HashMap<>();
        DecodedJWT decodedjwt = JWT.require(Algorithm.HMAC256(privateKey))
                .build().verify(token);
        Claim Id = decodedjwt.getClaim("Id");
        Claim content = decodedjwt.getClaim("content");
        map.put("Id", Id.asString());
        map.put("content", content.asString());
        return map;
    }
    
    
    /**
     * 解析过期的token
     * @param token token
     * @param key   key
     * @return 结果
     */
    public  Map<String, String> decodeToken(String token) {
        try {
        	HashMap<String, String> map = new HashMap<>();
            DecodedJWT jwt = JWT.decode(token);
            Claim Id = jwt.getClaim("Id");
            Claim content = jwt.getClaim("content");
            map.put("Id", Id.asString());
            map.put("content", content.asString());
            return    map;
                  
        } catch (JWTDecodeException e) {
            throw new RuntimeException(e);
        }
    }
    
}
