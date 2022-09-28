package com.nsu.mapper;

import com.nsu.pojo.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author Monica
 * @Date 2022/9/22 14:03
 **/
@Mapper
public interface SysUserMapper {
    SysUser selectUserByUsername(String username);
    void update(SysUser sysUser);
}
