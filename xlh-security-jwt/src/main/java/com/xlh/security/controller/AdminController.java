package com.xlh.security.controller;

import com.xlh.security.common.ResultUtil;
import com.xlh.security.entity.SysMenu;
import com.xlh.security.entity.SysRole;
import com.xlh.security.entity.SysUser;
import com.xlh.security.service.SysMenuService;
import com.xlh.security.service.SysRoleService;
import com.xlh.security.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xulihua
 * @date 2021/1/25 10:57
 *
 */
@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 查询用户列表
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/list")
    public ResultUtil userList(){
        List<SysUser> list = sysUserService.list();
        List<SysUser> collect = list.stream().map(sysUser ->{
            sysUser.setPassword("");
            return sysUser;
        } ).collect(Collectors.toList());
        return ResultUtil.success("拥有admin或者user权限才能查询列表",collect);
    }

    /**
     * 查询权限列表
     * @return
     */
    @PreAuthorize("hasRole('ADMIN') and hasRole('USER')")
    @GetMapping("menu-list")
    public ResultUtil getMenuList(){
        List<SysMenu> list = sysMenuService.list();
        return ResultUtil.success("拥有admin角色和user角色才可以查询权限列表接口",list);
    }

    /**
     * 查看用户list[有sys:user:info权限的用户可以访问]
     * @return
     */
    @PreAuthorize("hasPermission('admin/user-list','sys:user:info')")
    @GetMapping("/user-list")
    public ResultUtil getUserList(){
        List<SysUser> list = sysUserService.list();
        return ResultUtil.success("拥有sys:user:info权限才可以查询用户列表",list);
    }

    /**
     * 查询角色列表,拥有admin角色和sys:role:info权限可以访问
     * @return
     */
    @PreAuthorize("hasRole('ADMIN') and hasPermission('admin/role-list','sys:role:info')")
    @GetMapping("/role-list")
    public ResultUtil getRoleList(){
        List<SysRole> list = sysRoleService.list();
        return ResultUtil.success("拥有拥有admin角色和sys:role:info权限可以查询角色列表",list);
    }
}
