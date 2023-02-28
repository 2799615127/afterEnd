package com.xiaoming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
 
@SpringBootApplication
//@MapperScan("com.xiaoming.mapper")
public class main {
    public static void main(String[] args) {
        SpringApplication.run(main.class,args);//一定是被@SpringBootApplication标记的类
    }
}