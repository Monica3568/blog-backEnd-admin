package com.nsu.handler;

import com.alibaba.fastjson.JSON;
import com.nsu.comm.Response;
import com.nsu.comm.ResultCodeEnum;
import org.springframework.security.authentication.*;
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
public class JwtLoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        Response res = new Response();
        if (e instanceof AccountExpiredException) {
            //账号过期
            res.setCode(ResultCodeEnum.USER_ACCOUNT_EXPIRED.getCode());
            res.setMsg(ResultCodeEnum.USER_ACCOUNT_EXPIRED.getMsg());
        } else if (e instanceof BadCredentialsException) {
            //密码错误
            res.setCode(ResultCodeEnum.USER_CREDENTIALS_ERROR.getCode());
            res.setMsg(ResultCodeEnum.USER_CREDENTIALS_ERROR.getMsg());
        } else if (e instanceof CredentialsExpiredException) {
            //密码过期
            res.setCode(ResultCodeEnum.USER_CREDENTIALS_EXPIRED.getCode());
            res.setMsg(ResultCodeEnum.USER_CREDENTIALS_EXPIRED.getMsg());
        } else if (e instanceof DisabledException) {
            //账号不可用
            res.setCode(ResultCodeEnum.USER_ACCOUNT_DISABLE.getCode());
            res.setMsg(ResultCodeEnum.USER_ACCOUNT_DISABLE.getMsg());
        } else if (e instanceof LockedException) {
            //账号锁定
            res.setCode(ResultCodeEnum.USER_ACCOUNT_LOCKED.getCode());
            res.setMsg(ResultCodeEnum.USER_ACCOUNT_LOCKED.getMsg());
        } else if (e instanceof InternalAuthenticationServiceException) {
            //用户不存在res.setCode(ResultCodeEnum.USER_ACCOUNT_NOT_EXIST.getCode());
            res.setMsg(ResultCodeEnum.USER_ACCOUNT_NOT_EXIST.getMsg());
        } else {
            //其他错误
            res.setCode(ResultCodeEnum.COMMON_FAIL.getCode());
            res.setMsg(ResultCodeEnum.COMMON_FAIL.getMsg());
        }

        httpServletResponse.setContentType("text/json;charset=utf-8");
        // 把Json数据放入到HttpServletResponse中返回给前台
        httpServletResponse.getWriter().write(JSON.toJSONString(res));
    }
}
