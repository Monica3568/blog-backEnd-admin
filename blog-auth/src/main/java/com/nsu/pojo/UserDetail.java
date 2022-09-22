package com.nsu.pojo;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @Author Monica
 * @Date 2022/9/22 13:59
 **/

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class UserDetail extends User {
    /**
     * 我们自己的用户实体对象，这里省略掉get/set方法
     */
    private SysUser sysUser;

    public UserDetail(SysUser sysUser, Collection<? extends GrantedAuthority> authorities) {
        // 必须调用父类的构造方法，初始化用户名、密码、权限
        super(sysUser.getUsername(), sysUser.getPassword(), authorities);
        this.sysUser = sysUser;
    }
}
