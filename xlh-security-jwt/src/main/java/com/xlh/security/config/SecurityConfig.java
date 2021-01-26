package com.xlh.security.config;

import com.xlh.security.core.JWTAuthticationFilter;
import com.xlh.security.core.UserAuthenticationProvider;
import com.xlh.security.core.UserPermissionEvaluator;
import com.xlh.security.core.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

/**
 * @author xulihua
 * @date 2021/1/21 16:35
 * security配置类
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)// 开启权限注解模式
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 登陆成功处理类
     */
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;
    /**
     * 登出成功处理类
     */
    @Autowired
    private LoginoutSuccessHandler loginoutSuccessHandler;
    /**
     * 登陆失败处理类
     */
    @Autowired
    private LoginFailedHandler loginFailedHandler;
    /**
     * 未登录处理类
     */
    @Autowired
    private NoLoginHandler noLoginHandler;
    /**
     * 暂无权限处理类
     */
    @Autowired
    private NoPermissionHandler noPermissionHandler;
    /**
     * 自定义登陆处理类
     */
    @Autowired
    private UserAuthenticationProvider userAuthenticationProvider;

    @Autowired
    private UserLogoutHandler logoutHandler;

    /**
     * 注入自定义permissionEvaluator
     * @return
     */
    @Bean
    public DefaultWebSecurityExpressionHandler userAuthenticationProviderBean(){
        DefaultWebSecurityExpressionHandler handler =new DefaultWebSecurityExpressionHandler();
        handler.setPermissionEvaluator(new UserPermissionEvaluator());
        return handler;
    }
    /**
     * 加密方式
     * @return
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    /**
     * 配置登录验证逻辑
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth){
        //这里可启用我们自己的登陆验证逻辑
        auth.authenticationProvider(userAuthenticationProvider);
    }
    /**
     * 配置security的空控制逻辑
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.formLogin() 表单登录方式
        http.authorizeRequests()
                // 不进行权限验证的请求或资源(从配置文件中读取)
                .antMatchers(JWTConfig.antMatchers.split(",")).permitAll()
                // 其他的需要登陆后才能访问
                .anyRequest().authenticated()
                .and()
                // 配置未登录自定义处理类
                .httpBasic().authenticationEntryPoint(noLoginHandler)
                .and()
                // 配置登录地址
                .formLogin()
                .loginProcessingUrl("/login/userLogin")
                // 配置登录成功自定义处理类
                .successHandler(loginSuccessHandler)
                // 配置登录失败自定义处理类
                .failureHandler(loginFailedHandler)
                .and()
                // 配置登出地址
                .logout()
                .logoutUrl("/user/logout")
                .addLogoutHandler(logoutHandler)
                // 配置用户登出自定义处理类
                .logoutSuccessHandler(loginoutSuccessHandler)
                .and()
                // 配置没有权限自定义处理类
                .exceptionHandling().accessDeniedHandler(noPermissionHandler)
                .and()
                // 开启跨域
                .cors()
                .and()
                // 取消跨站请求伪造防护
                .csrf().disable();
        // 基于token登录,不需要session,如果设置了session为无状态那么在登出时获取不到用户的认证信息
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 禁用缓存
        http.headers().cacheControl();
        // 配置jwt过滤器
        http.addFilter(new JWTAuthticationFilter(authenticationManager()));
    }
}
