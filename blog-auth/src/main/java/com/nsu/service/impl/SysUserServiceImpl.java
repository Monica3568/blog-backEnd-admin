package com.nsu.service.impl;

import com.nsu.comm.Response;
import com.nsu.comm.ResultCodeEnum;
import com.nsu.exception.ApiException;
import com.nsu.mapper.SysUserMapper;
import com.nsu.pojo.SysUser;
import com.nsu.pojo.UserDetail;
import com.nsu.service.SysUserService;
import com.nsu.util.JwtManager;
import com.nsu.vo.SysUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * @Author Monica
 * @Date 2022/9/22 14:02
 **/
@Service
public class SysUserServiceImpl implements SysUserService, UserDetailsService {

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtManager jwtManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SysUser sysUser = userMapper.selectUserByUsername(username);

        if (sysUser == null){
            throw new UsernameNotFoundException("没有找到该用户");
        }

        return new UserDetail(sysUser, Collections.emptyList());
    }

    @Override
    public SysUserVo loginUser(SysUser sysUser) {
        SysUser loginUser = userMapper.selectUserByUsername(sysUser.getUsername());
        if (null == loginUser || encoder.matches(sysUser.getPassword(),loginUser.getPassword())){
            throw new ApiException(ResultCodeEnum.UserError);
        }
        // 需要返回给前端的VO对象
        SysUserVo userVO = new SysUserVo();
        userVO.setId(loginUser.getId())
                .setUsername(loginUser.getUsername())
                // 生成JWT，将用户名数据存入其中
                .setToken(jwtManager.generate(loginUser.getUsername()));
        return userVO;
    }
}
