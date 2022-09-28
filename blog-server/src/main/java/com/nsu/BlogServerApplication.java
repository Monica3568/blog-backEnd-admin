package com.nsu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author Monica
 * @Date 2022/9/28 16:33
 **/
@SpringBootApplication
@MapperScan("com.nsu.mapper")
public class BlogServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogServerApplication.class,args);
    }
}
