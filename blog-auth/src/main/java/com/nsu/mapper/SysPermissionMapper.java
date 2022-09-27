package com.nsu.mapper;

import com.nsu.pojo.SysPermission;

import java.util.List;

/**
 * @Author Monica
 * @Date 2022/9/27 11:12
 **/
public interface SysPermissionMapper {
    List<SysPermission> selectListByUser(Integer userId);
    List<SysPermission> selectListByPath(String requestUrl);
}
