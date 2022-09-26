package com.nsu.filter;

import com.nsu.pojo.JwtProperties;
import com.nsu.util.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description 自定义的Token过滤器
 * @Author Monica
 * @Date 2022/9/26 16:00
 **/
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private JwtProperties jwtProperties;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String requestUrl = httpServletRequest.getRequestURI();

        String authToken = httpServletRequest.getHeader(jwtProperties.getHeader());

        String stuId = jwtUtil.getUsernameFromToken(authToken);


        System.out.println("进入自定义过滤器");

        System.out.println("自定义过滤器获得用户名为   " + stuId);

        //当token中的username不为空时进行验证token是否是有效的token
        if (stuId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //token中username不为空，并且Context中的认证为空，进行token验证

            //从数据库得到带有密码的完整user信息
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(stuId);

            if (jwtUtil.validateToken(authToken, userDetails)) { //如username不为空，并且能够在数据库中查到
                /**
                 * UsernamePasswordAuthenticationToken继承AbstractAuthenticationToken实现Authentication
                 * 所以当在页面中输入用户名和密码之后首先会进入到UsernamePasswordAuthenticationToken验证(Authentication)，
                 * 然后生成的Authentication会被交由AuthenticationManager来进行管理
                 * 而AuthenticationManager管理一系列的AuthenticationProvider，
                 * 而每一个Provider都会通UserDetailsService和UserDetail来返回一个
                 * 以UsernamePasswordAuthenticationToken实现的带用户名和密码以及权限的Authentication
                 */
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));


                //将authentication放入SecurityContextHolder中
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
