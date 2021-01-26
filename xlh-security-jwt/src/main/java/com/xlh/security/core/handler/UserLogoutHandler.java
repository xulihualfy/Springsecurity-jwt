package com.xlh.security.core.handler;

import com.xlh.security.common.Constant;
import com.xlh.security.common.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author xulihua
 * @date 2021/1/26 10:48
 * 自定义登出处理类
 */
@Component
@Slf4j
public class UserLogoutHandler implements LogoutHandler {
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        log.info("用户信息:"+authentication);
        String username = authentication.getName();
        log.info(username+"用户登出");
        Map<Object, Object> tokenMap = redisUtil.hAllGet(Constant.REDIS_TOKEN_NAME);
        // 删除redis中的用户Token
        if (tokenMap.containsKey(username)){
            redisUtil.hdel(Constant.REDIS_TOKEN_NAME,username);
        }
    }
}
