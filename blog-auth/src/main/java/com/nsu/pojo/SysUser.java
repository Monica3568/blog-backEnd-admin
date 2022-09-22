package com.nsu.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Monica
 * @Date 2022/9/16 15:41
 **/
@Data
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String username;

    private String password;
}
