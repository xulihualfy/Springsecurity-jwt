package com.xlh.security.core;

import com.xlh.security.entity.SysMenu;
import com.xlh.security.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.io.Serializable;
import java.util.*;

/**
 * @author xulihua
 * @date 2021/1/22 14:46
 * 权限校验,使用@PreAuthorize("hasPermission('admin/user-list','sys:user:info')")注解的方法,会进到此处理器
 */
@Component
@Slf4j
public class UserPermissionEvaluator implements PermissionEvaluator {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 鉴权方法,可以根据具体的业务场景进行操作
     * @param authentication:用户信息
     * @param targetUrl:请求路径
     * @param permission:请求路径的权限
     * @return 是否通过校验
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetUrl, Object permission) {
        log.info("方法url信息:"+targetUrl);
        log.info("权限信息:"+permission);
        // 用户认证信息
        SecurityUserDetail principal = (SecurityUserDetail) authentication.getPrincipal();
        Long userId = principal.getUserId();
        // 查询用户的权限信息
        List<SysMenu> menus= sysUserService.selectSysMenuByUserId(userId);
        // 获取出权限名称集合(可以保存到缓存中,提高效率)
        Set<String> permissions = new HashSet<>();
        // 非空判断
        Optional<List<SysMenu>> optional = Optional.of(menus);
        if (optional.isPresent()){
            menus.stream().forEach(sysMenu -> permissions.add(sysMenu.getPermission()));
        }
        // 进行权限检验
        if (permissions.contains(String.valueOf(permission))){
            return true;
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        log.info("authentication信息:"+authentication.getPrincipal().toString());
        log.info("s信息:"+ s);
        log.info("o信息:"+ o);
        return false;
    }
}
