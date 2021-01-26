package com.xlh.security.core.handler;

import com.xlh.security.common.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xulihua
 * @date 2021/1/22 11:08
 * 未登录处理类
 */
@Component
@Slf4j
public class NoLoginHandler implements AuthenticationEntryPoint {
    /**
     * 未登录处理方法
     * @param httpServletRequest
     * @param httpServletResponse
     * @param e
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        log.info("用户未登录:"+e.getMessage());
        ResultUtil.Response(ResultUtil.failed(ResultUtil.NO_LOGIN_CODE,"用户未登录,请先登录"),httpServletResponse);
    }
}
