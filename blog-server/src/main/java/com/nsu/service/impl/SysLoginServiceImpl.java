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
        // ?????????????????????????????????????????? ???????????? loginUser
//        SysLoginUser loginUser = buildLoginUser(sysUser);
        // ??????token
//        SaHolder.getStorage().set(LOGIN_USER_KEY, loginUser);
        StpUtil.login(sysUser.getId());

        loginInfoService.recordLoginInfo(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success"), request);
//        recordLoginInfo(sysUser.getId(), username);
        return StpUtil.getTokenValue();
    }

    /**
     * ????????????
     */
    private void checkLogin(LoginType loginType, String username, Supplier<Boolean> supplier) {
        HttpServletRequest request = ServletUtils.getRequest();
        System.out.println(supplier.get().toString());
        String errorKey = CacheConstants.PWD_ERR_CNT_KEY + username;
        String loginFail = Constants.LOGIN_FAIL;

        // ??????????????????????????????(???????????????????????? ??????: key + username + ip)
        Integer errorNumber = redisUtils.getCacheObject(errorKey);
        // ????????????????????? ?????????
        if (ObjectUtil.isNotNull(errorNumber) && errorNumber.equals(maxRetryCount)) {
            loginInfoService.recordLoginInfo(username, loginFail, MessageUtils.message(loginType.getRetryLimitExceed(), maxRetryCount, lockTime), request);
            throw new UserException(loginType.getRetryLimitExceed(), maxRetryCount, lockTime);
        }

        if (supplier.get()) {
            // ???????????????
            errorNumber = ObjectUtil.isNull(errorNumber) ? 1 : errorNumber + 1;
            // ???????????????????????? ???????????????
            if (errorNumber.equals(maxRetryCount)) {
                Long millis = Duration.ofMinutes(lockTime).toMillis();
                Integer value = millis.intValue()*1000;
                redisUtils.setCacheObject(errorKey, errorNumber, value,TimeUnit.SECONDS);
                loginInfoService.recordLoginInfo(username, loginFail, MessageUtils.message(loginType.getRetryLimitExceed(), maxRetryCount, lockTime), request);
                throw new UserException(loginType.getRetryLimitExceed(), maxRetryCount, lockTime);
            } else {
                // ??????????????????????????? ?????????
                redisUtils.setCacheObject(errorKey, errorNumber);
                loginInfoService.recordLoginInfo(username, loginFail, MessageUtils.message(loginType.getRetryLimitCount(), errorNumber), request);
                throw new UserException(loginType.getRetryLimitCount(), errorNumber);
            }
        }

        // ???????????? ??????????????????
        redisUtils.deleteObject(errorKey);
    }
}
