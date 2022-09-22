package com.nsu.mapper;

import com.nsu.pojo.SysUser;

/**
 * @Author Monica
 * @Date 2022/9/22 14:03
 **/
public interface SysUserMapper {
    SysUser selectUserByUsername(String username);
}
