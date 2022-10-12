package com.nsu.controller;

import com.nsu.comm.Response;
import com.nsu.constants.Constants;
import com.nsu.entity.SysLoginUser;
import com.nsu.service.SysLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Monica
 * @Date 2022/10/11 16:18
 **/
@RestController
@RequestMapping("/admin")
public class SysLoginController {

    @Autowired
    private SysLoginService loginService;

    @PostMapping("/login")
    public Response login(String username,String password){
        Map<String, Object> map = new HashMap<>();
        // 生成令牌
        String token = loginService.login(username,password);
        map.put(Constants.TOKEN,token);
        return new Response().success(map);
    }
}
