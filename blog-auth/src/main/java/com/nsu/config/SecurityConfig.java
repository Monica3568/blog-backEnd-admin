package com.nsu.config;

import com.nsu.filter.JwtAuthenticationTokenFilter;
import com.nsu.handler.JwtAccessDeniedHandler;
import com.nsu.handler.JwtAuthenticationEntryPoint;
import com.nsu.handler.JwtAuthenticationSuccessHandler;
import com.nsu.handler.LoginFailureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import javax.annotation.Resource;


/**
 * @Author Monica
 * @Date 2022/9/16 16:01
 **/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Resource
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Resource
    private JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;

    @Resource
    private LoginFailureHandler loginFailureHandler;


    /**
     * 白名单, 不做权限效验的url
     */
    private static final String[] AUTH_WHITELIST = {
            "/API/login"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 自定义的Jwt Token过滤器
    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .formLogin()
                //自定义认证成功处理器
                .successHandler(jwtAuthenticationSuccessHandler)
                // 自定义失败拦截器
                .failureHandler(loginFailureHandler)
                // 自定义登录拦截URI
                .loginProcessingUrl("/API/login")
                .and()
                //token的验证方式不需要开启csrf的防护
                .csrf().disable()
                // 自定义认证失败类
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                // 自定义权限不足处理类
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                //设置无状态的连接,即不创建session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/API/login").permitAll()
                // 白名单URL
                .antMatchers(AUTH_WHITELIST).permitAll()
                //配置允许匿名访问的路径
                .anyRequest().authenticated();
        // 解决跨域问题（重要）  只有在前端请求接口时才发现需要这个
        httpSecurity.cors().and().csrf().disable();


        //配置自己的jwt验证过滤器
        httpSecurity
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);


        // disable page caching
        httpSecurity.headers().cacheControl();
    }
}