package com.nsu.handler;

import com.alibaba.fastjson.JSON;
import com.nsu.comm.Response;
import com.nsu.comm.ResultCodeEnum;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Monica
 * @Date 2022/9/27 14:00
 **/
@Component
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Response res = new Response();
        res.setCode(ResultCodeEnum.SUCCESS_logout.getCode());
        res.setMsg(ResultCodeEnum.SUCCESS_logout.getMsg());
        // 指定响应格式是json
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(res));
    }
}
