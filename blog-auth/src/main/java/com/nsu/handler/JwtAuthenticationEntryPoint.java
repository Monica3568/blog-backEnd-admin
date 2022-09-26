package com.nsu.handler;

import com.nsu.comm.Response;
import com.nsu.comm.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author Monica
 * @Date 2022/9/23 14:18
 * @deprecated 认证错误处理器
 **/
@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        log.error(e.getMessage());
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        Response res = new Response();
        res.setCode(ResultCodeEnum.NoLogin.getCode());
        res.setMsg(ResultCodeEnum.NoLogin.getMsg());
        out.write(res.toString());
        out.flush();
        out.close();
    }
}
