package com.nsu.service.impl;

import com.nsu.mapper.SysUserMapper;

import com.nsu.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * @Author Monica
 * @Date 2022/9/22 14:02
 **/
@Service
public class SysUserServiceImpl implements SysUserService{

    @Autowired
    private SysUserMapper userMapper;

}
