package com.nsu.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author Monica
 * @Date 2022/10/12 9:54
 **/
@Getter
@AllArgsConstructor
public enum LoginType {

    /**
     * 密码登录
     */
    PASSWORD("user.password.retry.limit.exceed", "user.password.retry.limit.count");

    /**
     * 登录重试超出限制提示
     */
    final String retryLimitExceed;

    /**
     * 登录重试限制计数提示
     */
    final String retryLimitCount;
}