package com.nsu.handler;

import com.alibaba.fastjson.JSON;
import com.nsu.comm.Response;
import com.nsu.comm.ResultCodeEnum;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Monica
 * @Date 2022/9/26 15:43
 **/
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        Response res = new Response();
        res.setCode(ResultCodeEnum.NoAuth.getCode());
        res.setMsg(ResultCodeEnum.NoAuth.getMsg());
        // 使用fastjson
        String json = JSON.toJSONString(res);
        // 指定响应格式是json
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(json);
    }
}
