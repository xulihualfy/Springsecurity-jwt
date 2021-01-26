package com.xlh.security.core.handler;

import com.xlh.security.common.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xulihua
 * 登陆失败处理类
 */
@Component
@Slf4j
public class LoginFailedHandler implements AuthenticationFailureHandler {
    /**
     * 登陆失败处理发方法
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param e
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        ResultUtil resultUtil = ResultUtil.failed("登陆失败");
        // 根据不同的登陆失败原因返回不同的结果
        if (e instanceof UsernameNotFoundException) {
            log.info("[登录失败]:" + e.getMessage());
            resultUtil = ResultUtil.failed("登陆失败,用户名不存在");
        } else if (e instanceof BadCredentialsException) {
            log.info("密码错误:" + e.getMessage());
            resultUtil = ResultUtil.failed("登陆失败,密码错误");
        } else if (e instanceof LockedException) {
            log.info("账户已被锁定:" + e.getMessage());
            resultUtil = ResultUtil.failed("登陆失败,账户已经被锁定");
        }else if (e instanceof CredentialsExpiredException){
            log.info("证书已过期"+e.getMessage());
            resultUtil=ResultUtil.failed("登录失败,证书已过期");
        }
        // 返回信息
        ResultUtil.Response(resultUtil, httpServletResponse);
    }
}
