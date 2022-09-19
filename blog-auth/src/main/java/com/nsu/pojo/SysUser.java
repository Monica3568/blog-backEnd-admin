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
    private String userName;
    private String password;
    private Date createTime;
    private Date updateTime;
}
