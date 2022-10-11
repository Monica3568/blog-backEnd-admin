package com.nsu.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nsu.constants.CacheConstants;
import com.nsu.constants.Constants;
import com.nsu.entity.BizArticle;
import com.nsu.entity.SysUser;
import com.nsu.service.SysLoginService;
import com.nsu.service.SysUserService;
import com.nsu.utils.RedisUtils;
import com.nsu.utils.ServletUtils;
import org.omg.CORBA.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.misc.MessageUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
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
    @Override
    public String login(String username, String password) {

        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUserName,username);
        SysUser sysUser = userService.getOne(wrapper);

        return null;
    }

    /**
     * 登录校验
     */
    private void checkLogin(String username, Supplier<Boolean> supplier) {
        HttpServletRequest request = ServletUtils.getRequest();
        String errorKey = CacheConstants.PWD_ERR_CNT_KEY + username;
        String loginFail = Constants.LOGIN_FAIL;

        // 获取用户登录错误次数(可自定义限制策略 例如: key + username + ip)
        Integer errorNumber = redisUtils.getCacheObject(errorKey);
        // 锁定时间内登录 则踢出
        if (ObjectUtil.isNotNull(errorNumber) && errorNumber.equals(maxRetryCount)) {
            asyncService.recordLogininfor(username, loginFail, MessageUtils.message(loginType.getRetryLimitExceed(), maxRetryCount, lockTime), request);
            throw new UserException(loginType.getRetryLimitExceed(), maxRetryCount, lockTime);
        }

        if (supplier.get()) {
            // 是否第一次
            errorNumber = ObjectUtil.isNull(errorNumber) ? 1 : errorNumber + 1;
            // 达到规定错误次数 则锁定登录
            if (errorNumber.equals(maxRetryCount)) {
                redisUtils.setCacheObject(errorKey, errorNumber, Duration.ofMinutes(lockTime));
                asyncService.recordLogininfor(username, loginFail, MessageUtils.message(loginType.getRetryLimitExceed(), maxRetryCount, lockTime), request);
                throw new UserException(loginType.getRetryLimitExceed(), maxRetryCount, lockTime);
            } else {
                // 未达到规定错误次数 则递增
                redisUtils.setCacheObject(errorKey, errorNumber);
                asyncService.recordLogininfor(username, loginFail, MessageUtils.message(loginType.getRetryLimitCount(), errorNumber), request);
                throw new UserException(loginType.getRetryLimitCount(), errorNumber);
            }
        }

        // 登录成功 清空错误次数
        redisUtils.deleteObject(errorKey);
    }
}
