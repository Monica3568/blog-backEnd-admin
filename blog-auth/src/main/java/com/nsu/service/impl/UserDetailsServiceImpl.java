package com.nsu.service.impl;

import com.nsu.mapper.SysPermissionMapper;
import com.nsu.mapper.SysUserMapper;
import org.springframework.security.core.userdetails.User;
import com.nsu.pojo.SysPermission;
import com.nsu.pojo.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author Monica
 * @Date 2022/9/23 9:48
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private SysPermissionMapper permissionMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || "".equals(username)) {
            throw new RuntimeException("用户不能为空");
        }
        //根据用户名查询用户
        SysUser sysUser = userMapper.selectUserByUsername(username);
        if (sysUser == null) {
            throw new RuntimeException("用户不存在");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (sysUser != null) {
            //获取该用户所拥有的权限
            List<SysPermission> sysPermissions = permissionMapper.selectListByUser(sysUser.getId());
            // 声明用户授权
            for (SysPermission sysPermission : sysPermissions) {
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(sysPermission.getPermissionCode());
                grantedAuthorities.add(grantedAuthority);
            }
//
//            sysPermissions.forEach(sysPermission -> {
//                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(sysPermission.getPermissionCode());
//                grantedAuthorities.add(grantedAuthority);
//            });
        }
        return new User(sysUser.getUsername(), sysUser.getPassword(),
                sysUser.getEnabled(), sysUser.getNotExpired(), sysUser.getCredentialsNotExpired(),
                sysUser.getAccountNotLocked(), grantedAuthorities);
    }

}
