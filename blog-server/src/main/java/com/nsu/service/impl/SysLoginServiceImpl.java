package com.nsu.service.impl;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nsu.constants.CacheConstants;
import com.nsu.constants.Constants;
import com.nsu.entity.SysLoginUser;
import com.nsu.entity.SysUser;
import com.nsu.enums.LoginType;
import com.nsu.exception.UserException;
import com.nsu.service.SysLoginInfoService;
import com.nsu.service.SysLoginService;
import com.nsu.service.SysUserService;
import com.nsu.utils.MessageUtils;
import com.nsu.utils.RedisUtils;
import com.nsu.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @Author Monica
 * @Date 2022/10/11 16:30
 **/
@Service
public class SysLoginServiceImpl implements SysLoginService {
    @Autowired
    private SysUserService userService;
    @Autowired
    private RedisUtils redisUtils;
    @Value("${user.password.maxRetryCount}")
    private Integer maxRetryCount;
    @Value("${user.password.maxRetryCount}")
    private Integer lockTime;
    @Autowired
    private SysLoginInfoService loginInfoService;

    public static final String LOGIN_USER_KEY = "loginUser";
    @Override
    public String login(String username, String password) {
        HttpServletRequest request = ServletUtils.getRequest();
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUserName,username);
        SysUser sysUser = userService.getOne(wrapper);
        System.out.println(BCrypt.checkpw(password, sysUser.getPassword()));
        System.out.println(BCrypt.hashpw("123456"));
        checkLogin(LoginType.PASSWORD, username, () -> !BCrypt.checkpw(password, sysUser.getPassword()));
        // 此处可根据登录用户的数据不同 自行创建 loginUser
//        SysLoginUser loginUser = buildLoginUser(sysUser);
        // 生成token
//        SaHolder.getStorage().set(LOGIN_USER_KEY, loginUser);
        StpUtil.login(sysUser.getId());

        loginInfoService.recordLoginInfo(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success"), request);
//        recordLoginInfo(sysUser.getId(), username);
        return StpUtil.getTokenValue();
    }

    /**
     * 登录校验
     */
    private void checkLogin(LoginType loginType, String username, Supplier<Boolean> supplier) {
        HttpServletRequest request = ServletUtils.getRequest();
        System.out.println(supplier.get().toString());
        String errorKey = CacheConstants.PWD_ERR_CNT_KEY + username;
        String loginFail = Constants.LOGIN_FAIL;

        // 获取用户登录错误次数(可自定义限制策略 例如: key + username + ip)
        Integer errorNumber = redisUtils.getCacheObject(errorKey);
        // 锁定时间内登录 则踢出
        if (ObjectUtil.isNotNull(errorNumber) && errorNumber.equals(maxRetryCount)) {
            loginInfoService.recordLoginInfo(username, loginFail, MessageUtils.message(loginType.getRetryLimitExceed(), maxRetryCount, lockTime), request);
            throw new UserException(loginType.getRetryLimitExceed(), maxRetryCount, lockTime);
        }

        if (supplier.get()) {
            // 是否第一次
            errorNumber = ObjectUtil.isNull(errorNumber) ? 1 : errorNumber + 1;
            // 达到规定错误次数 则锁定登录
            if (errorNumber.equals(maxRetryCount)) {
                Long millis = Duration.ofMinutes(lockTime).toMillis();
                Integer value = millis.intValue()*1000;
                redisUtils.setCacheObject(errorKey, errorNumber, value,TimeUnit.SECONDS);
                loginInfoService.recordLoginInfo(username, loginFail, MessageUtils.message(loginType.getRetryLimitExceed(), maxRetryCount, lockTime), request);
                throw new UserException(loginType.getRetryLimitExceed(), maxRetryCount, lockTime);
            } else {
                // 未达到规定错误次数 则递增
                redisUtils.setCacheObject(errorKey, errorNumber);
                loginInfoService.recordLoginInfo(username, loginFail, MessageUtils.message(loginType.getRetryLimitCount(), errorNumber), request);
                throw new UserException(loginType.getRetryLimitCount(), errorNumber);
            }
        }

        // 登录成功 清空错误次数
        redisUtils.deleteObject(errorKey);
    }
}
