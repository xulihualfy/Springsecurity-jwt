package com.xlh.security.core.handler;

import ch.qos.logback.core.net.LoginAuthenticator;
import com.xlh.security.common.Constant;
import com.xlh.security.common.JWTUtil;
import com.xlh.security.common.RedisUtil;
import com.xlh.security.common.ResultUtil;
import com.xlh.security.config.JWTConfig;
import com.xlh.security.core.SecurityUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xulihua
 * @date 2021/1/21 16:34
 * 登陆成功处理类
 */
@Slf4j
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        // 获取认证信息中的用户信息
        SecurityUserDetail userDetail = (SecurityUserDetail) authentication.getPrincipal();
        log.info("用户信息:"+userDetail);
        String username = userDetail.getUsername();
        ResultUtil resultUtil =null;
        // 判断缓存中是否已经存在token
        Map<Object, Object> tokenMap = redisUtil.hAllGet(Constant.REDIS_TOKEN_NAME);
        if (tokenMap.containsKey(username)){
             resultUtil = ResultUtil.failed(username + "已经登录,请勿重新登录");
            ResultUtil.Response(resultUtil,httpServletResponse);
            return;
        }else {
            // 生成token
            String token = JWTUtil.createToken(userDetail);
            token= JWTConfig.tokenPrefix+token;
            Map<String, Object> userToken = new HashMap<>(16);
            userToken.put(username,token);
            // 保存token到redis中
            redisUtil.hmPull(Constant.REDIS_TOKEN_NAME,userToken,Long.parseLong(JWTConfig.expiration.toString()));
            resultUtil = ResultUtil.success("登录成功",token);
            ResultUtil.Response(resultUtil,httpServletResponse);
        }
    }
}
