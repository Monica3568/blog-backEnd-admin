package com.nsu.exception;

import com.nsu.comm.Response;
import com.nsu.comm.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author Monica
 * @Date 2022/9/22 14:14
 **/
@Slf4j
public class MyEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
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
