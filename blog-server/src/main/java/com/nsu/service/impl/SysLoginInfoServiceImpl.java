package com.nsu.service.impl;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsu.constants.Constants;
import com.nsu.entity.SysLoginInfo;
import com.nsu.entity.SysUser;
import com.nsu.mapper.SysLoginInfoMapper;
import com.nsu.service.SysLoginInfoService;
import com.nsu.service.SysUserService;
import com.nsu.utils.AddressUtils;
import com.nsu.utils.ServletUtils;
import com.nsu.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 系统访问记录(SysLoginInfo)表服务实现类
 *
 * @author makejava
 * @since 2022-10-12 09:29:27
 */
@Service("sysLoginInfoService")
public class SysLoginInfoServiceImpl extends ServiceImpl<SysLoginInfoMapper, SysLoginInfo> implements SysLoginInfoService {

    @Autowired
    private SysLoginInfoMapper baseMapper;
    @Autowired
    private SysUserService userService;
    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息
     * @param args     列表
     */
    @Override
    public void recordLoginInfo(String username, String status, String message, HttpServletRequest request, Object... args) {
        final UserAgent userAgent = UserAgentUtil.parse(request.getHeader("User-Agent"));
        final String ip = ServletUtils.getClientIP(request);
        String address = AddressUtils.getRealAddressByIP(ip);
        StringBuilder s = new StringBuilder();
        s.append(getBlock(ip));
        s.append(address);
        s.append(getBlock(username));
        s.append(getBlock(status));
        s.append(getBlock(message));
        // 获取客户端操作系统
        String os = userAgent.getOs().getName();
        // 获取客户端浏览器
        String browser = userAgent.getBrowser().getName();
        // 封装对象
        SysLoginInfo loginInfo = new SysLoginInfo();
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUserName,username);
        SysUser sysUser = userService.getOne(wrapper);
        loginInfo.setInfoId(sysUser.getId());
        loginInfo.setUserName(username);
        loginInfo.setIpaddr(ip);
        loginInfo.setLoginLocation(address);
        loginInfo.setBrowser(browser);
        loginInfo.setOs(os);
        loginInfo.setMsg(message);

        // 日志状态
        if (StringUtils.equalsAny(status, Constants.LOGIN_SUCCESS, Constants.LOGOUT, Constants.REGISTER)) {
            loginInfo.setStatus(Constants.SUCCESS);
        } else if (Constants.LOGIN_FAIL.equals(status)) {
            loginInfo.setStatus(Constants.FAIL);
        }
        // 插入数据
        insertLoginInfo(loginInfo);
    }
    private String getBlock(Object msg) {
        if (msg == null) {
            msg = "";
        }
        return "[" + msg.toString() + "]";
    }
    /**
     * 新增系统登录日志
     *
     * @param loginInfo 访问日志对象
     */
    @Override
    public void insertLoginInfo(SysLoginInfo loginInfo) {
        loginInfo.setLoginTime(new Date());
        baseMapper.insert(loginInfo);
    }

}


