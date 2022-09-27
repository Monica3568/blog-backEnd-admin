package com.nsu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


/**
 * @Author Monica
 * @Date 2022/9/16 10:38
 **/
@SpringBootApplication
@MapperScan("com.nsu.mapper")
public class BlogAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogAuthApplication.class,args);
    }
    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
