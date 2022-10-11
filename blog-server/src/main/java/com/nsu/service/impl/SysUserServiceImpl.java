package com.nsu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsu.entity.SysUser;
import com.nsu.mapper.SysUserMapper;
import com.nsu.service.SysUserService;
import org.springframework.stereotype.Service;

/**
 * 用户表(SysUser)表服务实现类
 *
 * @author makejava
 * @since 2022-10-10 14:40:50
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

}

