package com.xlh.security;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xulihua
 */
@SpringBootApplication
@MapperScan("com.xlh.security.mapper")
public class XlhSecurityJwtApplication {
    public static void main(String[] args) {
        SpringApplication.run(XlhSecurityJwtApplication.class, args);
    }
}
