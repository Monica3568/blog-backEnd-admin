package com.nsu.service;

import com.nsu.comm.Response;
import com.nsu.pojo.SysUser;
import com.nsu.vo.SysUserVo;

/**
 * @Author Monica
 * @Date 2022/9/22 14:02
 **/
public interface SysUserService {
    SysUserVo loginUser(SysUser sysUser);
}
