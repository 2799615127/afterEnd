package com.xiaoming.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.xiaoming.utils.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
/**
 * @author 身份验证处理拦截器
 * @version 1.0
 * @create 2021/2/10 22:22
 */
@Slf4j
@Component
public class AuthHandlerInterceptor implements HandlerInterceptor {
    @Autowired
    TokenUtil tokenUtil;
    /**
     * 权限认证的拦截操作.
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        log.info("=======进入拦截器========");
        // 如果不是映射到方法直接通过,可以访问资源.
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        //为空就返回错误
        String token = httpServletRequest.getHeader("setToken");
        if (null == token || "".equals(token.trim())) {
        	log.info("==============空========");
        	httpServletResponse.setHeader("NOToken", "no");
        	httpServletResponse.setStatus(200);
            return false;
        }
       
        try {
        	 log.info("==============token:" + token);
        	 //检查token，如果错误或过期抛出异常
             tokenUtil.parseToken(token);
             return true;
        }catch (TokenExpiredException e) {
        	//过期token重新赋值
        	log.info("========token过期重新赋值============");
        	Map<String, String> map = tokenUtil.decodeToken(token);
        	String Id = map.get("Id");
            String content = map.get("content");
            String newToken=tokenUtil.getToken(Id,content,1000*60L);
        	httpServletResponse.setHeader("setToken",newToken);
        	return true;
			// TODO: handle exception
		}catch (Exception e) {
			//错误的token不被执行
			// TODO: handle exception
			log.info("========token异常============");
			httpServletResponse.setHeader("NOToken", "no");
        	httpServletResponse.setStatus(200);
			return false;
		}
    }

}
