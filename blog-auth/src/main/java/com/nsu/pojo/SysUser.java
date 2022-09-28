package com.nsu.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author Monica
 * @Date 2022/9/16 15:41
 **/
@Data
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 用户ID
     */
    private Integer id;
    /**
     * 账号
     */
    private String username;
    /**
     * 用户名
     */
    private String nickname;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 上次登录时间
     */
    private Date lastLoginTime;
//    /**
//     * 账号是否可用。默认为1（可用）
//     */
//    private Boolean enabled;
//
//    /**
//     * 是否过期。默认为1（没有过期）
//     */
//    private Boolean notExpired;
//
//    /**
//     * 账号是否锁定。默认为1（没有锁定）
//     */
//    private Boolean accountNotLocked;
//
//    /**
//     * 证书（密码）是否过期。默认为1（没有过期）
//     */
//    private Boolean credentialsNotExpired;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 创建人
     */
    private Integer createUser;

    /**
     * 修改人
     */
    private Integer updateUser;






























}
