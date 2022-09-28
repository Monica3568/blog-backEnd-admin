package com.nsu.config;

import com.nsu.filter.JwtAbstractSecurityInterceptor;
import com.nsu.filter.JwtAuthenticationTokenFilter;
import com.nsu.handler.JwtAccessDecisionManager;
import com.nsu.handler.JwtAccessDeniedHandler;
import com.nsu.handler.JwtFilterInvocationSecurityMetadataSource;
import com.nsu.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.web.cors.CorsUtils;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @Author Monica
 * @Date 2022/9/16 16:01
 **/

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl securityUserService;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    @Autowired
    private JwtAbstractSecurityInterceptor customizeAbstractSecurityInterceptor;

    @Autowired
    private JwtAccessDecisionManager customizeAccessDecisionManager;

    @Autowired
    private JwtFilterInvocationSecurityMetadataSource customizeFilterInvocationSecurityMetadataSource;

    @Autowired
    private JwtAccessDeniedHandler customizeAccessDeniedHandler;

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;






    /**
     * 对请求进行鉴权的配置
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors()
                .and().csrf().disable();

        http.authorizeRequests().
                withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setAccessDecisionManager(customizeAccessDecisionManager);//访问决策管理器
                        o.setSecurityMetadataSource(customizeFilterInvocationSecurityMetadataSource);//安全元数据源
                        return o;
                    }
                });

        http.authorizeRequests()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(customizeAccessDeniedHandler)
                .and()
                .formLogin()  // 登录
                .loginProcessingUrl("/API/login")
                .permitAll()  //允许所有用户
                .successHandler(authenticationSuccessHandler)  //登录成功处理逻辑
                .failureHandler(authenticationFailureHandler)  //登录失败处理逻辑
                .and()
                .logout() .logoutUrl("/API/logout")     // 退出
                .permitAll()   //允许所有用户
                .logoutSuccessHandler(logoutSuccessHandler)  //退出成功处理逻辑
                .deleteCookies("JSESSIONID")  //登出之后删除cookie
                .and()
                .sessionManagement()    //会话管理
                .maximumSessions(1)     //同一账号同时登录最大用户数
                .expiredSessionStrategy(sessionInformationExpiredStrategy);

        // 这是配置的关键，决定哪些接口开启防护，哪些接口绕过防护
        http.authorizeRequests()
                // 注意这里，是允许前端跨域联调的一个必要配置
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                // 指定某些接口不需要通过验证即可访问。像登陆、注册接口肯定是不需要认证的
                .antMatchers("/API/login", "/API/register").permitAll()
                // 这里意思是其它所有接口需要认证才能访问
                .antMatchers("/API/**").authenticated();
//                // 指定认证错误处理器
////                .and().exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint()).accessDeniedHandler(new JwtAccessDecisionManager());

        http.addFilterBefore(customizeAbstractSecurityInterceptor, FilterSecurityInterceptor.class);
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.headers().cacheControl();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityUserService);
    }

    /**
     * 默认开启密码加密，前端传入的密码Security会在加密后和数据库中的密文进行比对，一致的话就登录成功
     * 所以必须提供一个加密对象，供security加密前端明文密码使用
     * @return
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}