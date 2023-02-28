package com.xiaoming.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.xiaoming.config.AuthHandlerInterceptor;

@Configuration
public class WebCofig implements WebMvcConfigurer {
    /**
     * 允许跨域调用的过滤器
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        //允许白名单域名进行跨域调用
        config.addAllowedOrigin("*");
        //允许跨越发送cookie
        config.setAllowCredentials(true);
        //放行全部原始头信息
        config.addAllowedHeader("*");
        //config.addAllowedHeader("setToken");
        config.addExposedHeader("setToken");
        //允许所有请求方法跨域调用
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
    
      @Autowired(required = true)
	  AuthHandlerInterceptor authHandlerInterceptor;
	
	  /**
	   * 给除了 /login 的接口都配置拦截器,拦截转向到 authHandlerInterceptor
	   * 没有登录，只允许访问登录注册请求
	   */
      @Override
	  public void addInterceptors(InterceptorRegistry registry) {
	      registry.addInterceptor(authHandlerInterceptor)
	              .addPathPatterns("/**")
	              .excludePathPatterns("/account/**");
	  }
      
      /**
       * 图片保存路径，自动从yml文件中获取数据
       *   
       */
      @Value("${file.path}")
      private String fileSavePath;

      @Override
      public void addResourceHandlers(ResourceHandlerRegistry registry) {
          /**
           * 配置资源映射
           * 意思是：如果访问的资源路径是以“/images/”开头的，
           * 就给我映射到“${file.path}”这个代表的文件夹，去找你要的资源
           * 注意：${file.path} 里面的文件夹后面 “/”一定要带上
           */
    	  System.err.println(fileSavePath);
      	System.out.println("image");
          registry.addResourceHandler("/images/**")
                  .addResourceLocations("file:"+fileSavePath);
      }

}



