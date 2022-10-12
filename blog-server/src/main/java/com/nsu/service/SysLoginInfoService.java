package com.nsu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nsu.entity.SysLoginInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统访问记录(SysLoginInfo)表服务接口
 *
 * @author makejava
 * @since 2022-10-12 09:29:27
 */
public interface SysLoginInfoService extends IService<SysLoginInfo> {
    void recordLoginInfo(String username, String status, String message,
                         HttpServletRequest request, Object... args);
    /**
     * 新增系统登录日志
     *
     * @param loginInfo 访问日志对象
     */
    void insertLoginInfo(SysLoginInfo loginInfo);
}

