package com.nsu.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 登录用户信息
 * @Author Monica
 * @Date 2022/10/10 14:39
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class UserVo {
    private Long id;
    private String username;
    private String password;
    private Integer status;
    private String clientId;
    private List<String> roles;
}
