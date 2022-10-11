package com.nsu.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;


/**
 * @Author Monica
 * @Date 2022/9/30 11:37
 **/
@Service
public class UserDetailServiceImpl implements UserDetailsService {

//    @Autowired
//    private HttpServletRequest request;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//
//        String clientId = request.getParameter("client_id");


        return null;
    }
}
