package com.nsu.controller;

import com.nsu.comm.Response;
import com.nsu.pojo.SysUser;
import com.nsu.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Monica
 * @Date 2022/9/22 11:38
 **/
@RestController
@RequestMapping("/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/login")
    public Response loginUser(@RequestBody @Validated SysUser sysUser){

        return new Response().success(sysUserService.loginUser(sysUser));
    }

    @PostMapping("/register")
    public Response registerUser(SysUser sysUser){

        return new Response().success();
    }

    @PostMapping("/test")
    public String test(){

        return "我是钟伟的爹";
    }

}
