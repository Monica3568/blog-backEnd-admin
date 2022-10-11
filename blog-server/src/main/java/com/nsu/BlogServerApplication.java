package com.nsu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author Monica
 * @Date 2022/10/9 9:34
 **/

@SpringBootApplication
@MapperScan("com.nsu.mapper")
public class BlogServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogServerApplication.class,args);
    }
}
