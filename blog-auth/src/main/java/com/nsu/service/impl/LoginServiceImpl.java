package com.nsu.service.impl;

import com.nsu.filter.MyHttpServletRequestWrapper;
import com.nsu.mapper.SysUserMapper;
import com.nsu.pojo.JwtUser;
import com.nsu.pojo.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;


/**
 * @Author Monica
 * @Date 2022/9/23 9:48
 **/
@Service
public class LoginServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = userMapper.selectUserByUsername(username);
        if (sysUser == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        return new JwtUser(sysUser.getUsername(),sysUser.getPassword(), Collections.emptyList());
    }
}
