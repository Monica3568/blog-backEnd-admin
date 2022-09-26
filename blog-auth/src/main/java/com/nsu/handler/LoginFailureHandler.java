package com.nsu.handler;

import com.alibaba.fastjson.JSON;
import com.nsu.comm.Response;
import com.nsu.comm.ResultCodeEnum;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description 自定义认证失败处理类
 * @Author Monica
 * @Date 2022/9/26 15:30
 **/
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        returnFailure(httpServletResponse);
    }

    public void returnFailure(HttpServletResponse response) throws IOException {
        Response res = new Response();
        res.setCode(ResultCodeEnum.AuthFailed.getCode());
        res.setMsg(ResultCodeEnum.AuthFailed.getMsg());
        // 使用fastjson
        String json = JSON.toJSONString(res);
        // 指定响应格式是json
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(json);
    }
}
