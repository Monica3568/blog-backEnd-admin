package com.nsu.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Monica
 * @Date 2022/9/27 11:10
 **/
@Data
public class SysPermission implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 权限ID
     */
    private Integer id;
    /**
     * 权限code
     */
    private String permissionCode;
    /**
     * 权限名称
     */
    private String permissionName;

}
