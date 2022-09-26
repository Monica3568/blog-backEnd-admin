package com.nsu.controller;

import com.nsu.comm.Response;
import com.nsu.pojo.SysUser;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Monica
 * @Date 2022/9/23 9:43
 **/
@RestController
@RequestMapping("/API")
public class LoginController {

    @PostMapping("/login")
    public Response loginUser(String username, String password) {
        System.out.println("我是卢豪的爹");
        //TODO 这里登录不用写业务   全部交给springSecurity处理
        return new Response().success();
    }

    @GetMapping("/test")
    public Response test(){
        return new Response().success("我是钟伟的爹");
    }
}


