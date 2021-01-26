package com.xlh.security.core.handler;

import com.xlh.security.common.Constant;
import com.xlh.security.common.RedisUtil;
import com.xlh.security.common.ResultUtil;
import com.xlh.security.core.SecurityUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author xulihua
 * @date 2021/1/22 11:12
 * 用户注销
 */
@Component
@Slf4j
public class LoginoutSuccessHandler implements LogoutSuccessHandler {
    /**
     * 注销成功处理触发方法
     * @param httpServletRequest
     * @param httpServletResponse
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        log.info("用户登出成功");
        SecurityContextHolder.clearContext();
        ResultUtil.Response(ResultUtil.success("登出成功"),httpServletResponse);
    }
}
