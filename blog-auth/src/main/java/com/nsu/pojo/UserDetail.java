package com.nsu.pojo;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @Author Monica
 * @Date 2022/9/28 9:48
 **/
@Data
public class UserDetail extends User {
    /**
     * 我们自己的用户实体对象，要调取用户信息时直接获取这个实体对象。（这里我就不写get/set方法了）
     */
    private SysUser sysUser;

    public UserDetail(SysUser sysUser, Collection<? extends GrantedAuthority> authorities) {
        // 必须调用父类的构造方法，以初始化用户名、密码、权限
        super(sysUser.getUsername(), sysUser.getPassword(), authorities);
        this.sysUser = sysUser;
    }
}

