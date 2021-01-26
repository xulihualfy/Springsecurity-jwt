package com.xlh.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xlh.security.entity.SysMenu;
import com.xlh.security.entity.SysRole;
import com.xlh.security.entity.SysUser;

import java.util.List;

/**
 * 系统用户表(SysUser)表服务接口
 *
 * @author xulihua
 * @since 2021-01-21 10:49:07
 */
public interface SysUserService extends IService<SysUser> {
    /**角色权限
     * @param userId
     * @return
     */
    List<SysRole> selectUserRoles(Long userId);

    /**
     * 通过用户id查询权限
     * @param userId
     * @return
     */
    List<SysMenu> selectSysMenuByUserId(Long userId);
}