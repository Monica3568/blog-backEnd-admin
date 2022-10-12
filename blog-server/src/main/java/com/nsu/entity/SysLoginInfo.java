package com.nsu.entity;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 系统访问记录(SysLoginInfo)表实体类
 *
 * @author makejava
 * @since 2022-10-12 09:29:27
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_login_info")
public class SysLoginInfo  {
    //访问ID@TableId
    private Long infoId;

    //用户账号
    private String userName;
    //登录IP地址
    private String ipaddr;
    //登录地点
    private String loginLocation;
    //浏览器类型
    private String browser;
    //操作系统
    private String os;
    //登录状态（0成功 1失败）
    private String status;
    //提示消息
    private String msg;
    //访问时间
    private Date loginTime;



}
