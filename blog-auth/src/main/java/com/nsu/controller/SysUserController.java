package com.nsu.controller;

import com.nsu.comm.Response;
import com.nsu.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Monica
 * @Date 2022/9/16 15:37
 **/

@RestController
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/getUserList")
    public Response getUserList(){
        sysUserService.getUserList();
        return new Response().success();
    }
}
