package com.nsu.handler;

import com.alibaba.fastjson.JSON;
import com.nsu.comm.Response;
import com.nsu.comm.ResultCodeEnum;
import org.springframework.security.core.userdetails.User;
import com.nsu.mapper.SysUserMapper;
import com.nsu.pojo.SysUser;
import com.nsu.util.JwtUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description 自定义认证成功处理器
 * @Author Monica
 * @Date 2022/9/26 15:48
 **/
@Component
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    SysUserMapper userMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        //更新用户表上次登录时间、更新人、更新时间等字段
        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysUser sysUser = userMapper.selectUserByUsername(userDetails.getUsername());
        sysUser.setLastLoginTime(new Date());
        sysUser.setUpdateTime(new Date());
        sysUser.setUpdateUser(sysUser.getId());
        userMapper.update(sysUser);

        // 根据用户的id和username 生成token并返回
        String jwtToken = JwtUtils.getJwtToken(sysUser.getId().toString(), sysUser.getUsername());
        Map<String,String> results = new HashMap<>();
        results.put("token",jwtToken);

        //返回json数据
        Response res = new Response().success(results);
        res.setCode(ResultCodeEnum.SUCCESS_login.getCode());
        res.setMsg(ResultCodeEnum.SUCCESS_login.getMsg());
        //处理编码方式，防止中文乱码的情况
        httpServletResponse.setContentType("text/json;charset=utf-8");
        // 把Json数据放入HttpServletResponse中返回给前台
        httpServletResponse.getWriter().write(JSON.toJSONString(res));
    }
}
