package com.xlh.security.core;

import com.xlh.security.entity.SysRole;
import com.xlh.security.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author xulihua
 * @date 2021/1/22 11:16
 * 自定义登陆认证校验器
 */
@Component
@Slf4j
public class UserAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private SecyrityUserService secyrityUserService;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 认证校验
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取提交上来的用户名
        String userName = (String) authentication.getPrincipal();
        log.info("username:"+userName);
        // 获取提交上来的密码
        String password = (String) authentication.getCredentials();
        log.info("password:"+password);
        // 获取数据库用户信息
        SecurityUserDetail securityUserDetail = secyrityUserService.loadUserByUsername(userName);
        // 验证用户是否存在
        Optional<SecurityUserDetail> ops = Optional.ofNullable(securityUserDetail);
        if (!ops.isPresent()){
            throw new UsernameNotFoundException("用户不存在!");
        }
        // 判断用户是否被禁用
        String status = securityUserDetail.getStatus();
        if (StringUtils.equals(status, "PROHIBIT")) {
            throw new LockedException("用户已经被冻结!");
        }
        // 校验密码
        if (!new BCryptPasswordEncoder().matches(password, securityUserDetail.getPassword())) {
            throw new BadCredentialsException("密码错误!");
        }
        // 获取用户角色
        List<SysRole> userRoles = sysUserService.selectUserRoles(securityUserDetail.getUserId());
        Set<GrantedAuthority> roleSet = new HashSet<>();
        Optional<List<SysRole>> userRolesOps = Optional.ofNullable(userRoles);
        if (userRolesOps.isPresent()) {
            userRoles.stream().forEach(sysRole -> {
                roleSet.add(new SimpleGrantedAuthority("ROLE_" + sysRole.getRoleName()));
            });
        }
        // 把数据库中用户的角色设置到userDetail中
        securityUserDetail.setAuthorities(roleSet);
        // 登陆认证
        return new UsernamePasswordAuthenticationToken(securityUserDetail,password,roleSet);
    }

    /**
     * 是否支持当前验证协议
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
