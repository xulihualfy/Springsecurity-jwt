package com.xlh.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author xulihua
 * @date 2021/1/21 16:35
 * jwt配置
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")//该注解会根据文件信息调用声明对象的set方法（prefix必须全部为小写，set方法必须为public非static）
public class JWTConfig {
    /**
     * secret: JWTSecret #密钥key
     * tokenHeader: Authorization #token头部
     * tokenPrefix: Xlh- # token前缀字符
     * expiration: 86400 # 过期时间,单位是秒,1天后过期=86400 7天后过期=604800
     * antMatchers: /index/**,/login/**,favion.ico #配置不需要认证的接口/资源
     */
    public static String secret;
    public static String tokenHeader;
    public static String tokenPrefix;
    public static Integer expiration;
    public static String antMatchers;

    public void setAntMatchers(String antMatchers) {
        JWTConfig.antMatchers = antMatchers;
    }

    public void setExpiration(Integer expiration) {
        JWTConfig.expiration = expiration * 1000;
    }

    public void setTokenPrefix(String tokenPrefix) {
        JWTConfig.tokenPrefix = tokenPrefix;
    }

    public void setTokenHeader(String tokenHeader) {
        JWTConfig.tokenHeader = tokenHeader;
    }

    public void setSecret(String secret) {
        JWTConfig.secret = secret;
    }

}
