package com.nsu.handler;

import com.alibaba.fastjson.JSON;
import com.nsu.comm.Response;
import com.nsu.comm.ResultCodeEnum;
import com.nsu.util.JwtUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @description 自定义认证成功处理器
 * @Author Monica
 * @Date 2022/9/26 15:48
 **/
@Component
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        //生成token
        final String realToken = jwtUtil.generateToken(authentication.getName());
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", realToken);
        Response res = new Response();
        res.setCode(ResultCodeEnum.SUCCESS.getCode());
        res.setMsg(ResultCodeEnum.SUCCESS.getMsg());
        //将生成的authentication放入容器中，生成安全的上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String json = JSON.toJSONString(res);
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(json);
    }
}
