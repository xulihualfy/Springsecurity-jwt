package com.xlh.security.common;

import com.google.gson.Gson;
import com.xlh.security.config.JWTConfig;
import com.xlh.security.core.SecurityUserDetail;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author xulihua
 * @date 2021/1/21 16:31
 * jwt工具类
 */
@Slf4j
public class JWTUtil {
    /**
     * 私有构造器
     */
    private JWTUtil() {
    }
    /**
     * 生成token
     *
     * @param userDetail:用户信息
     * @return 返回token
     */
    public static String createToken(SecurityUserDetail userDetail) {
        Date date = new Date(System.currentTimeMillis() + JWTConfig.expiration);
        String token = null;
        token = Jwts.builder()
                // 用户id
                .setId(String.valueOf(userDetail.getUserId()))
                // 主题(用户名)
                .setSubject(userDetail.getUsername())
                // 过期时间
                .setExpiration(date)
                // 签发时间
                .setIssuedAt(new Date())
                // 签发者
                .setIssuer("xlh")
                // 用户角色存放,自定义authorities属性进行存放
                .claim("authorities", new Gson().toJson(userDetail.getAuthorities()))
                // 加密算法以及密钥
                .signWith(SignatureAlgorithm.HS512, JWTConfig.secret)
                .compact();
        return token;
    }
}
