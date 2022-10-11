package com.nsu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @Author Monica
 * @Date 2022/10/11 16:23
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysLoginUser {
    //用户名
    private String userName;
    //密码
    private String password;
}
