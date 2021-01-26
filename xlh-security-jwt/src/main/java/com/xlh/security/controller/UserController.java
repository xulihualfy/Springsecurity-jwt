package com.xlh.security.controller;

import com.xlh.security.common.ResultUtil;
import com.xlh.security.core.SecurityUserDetail;
import com.xlh.security.entity.SysMenu;
import com.xlh.security.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xulihua
 * @date 2021/1/21 16:31
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("/info")
    public ResultUtil getUserInfo(){
        SecurityUserDetail user = (SecurityUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ResultUtil success = ResultUtil.success(user);
        return success;
    }

    /**
     * 拥有user角色或者是sys:user:info权限的用户才可以访问此接口
     * hasRole:拥有什么角色,hasPermission:拥有的权限
     * @return
     */
    @PreAuthorize("hasRole('USER') or hasPermission('/user/menu-list','sys:user:info')")
    @GetMapping("/menu-list")
    public ResultUtil getMenuList(){
        List<SysMenu> list = sysMenuService.list();
        return ResultUtil.success("拥有USER角色和sys:user:info权限可以访问",list);
    }

}
