package com.nsu.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * @Author Monica
 * @Date 2022/9/22 15:47
 **/
@Data
@Accessors(chain = true)
public class SysUserVo {

    /**
     * 主键
     */
    private Integer id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 登录认证token
     */
    private String token;
    /**
     * 当前用户的权限资源id集合
     */
    private Set<Long> resourceIds;
}
