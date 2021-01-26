package com.xlh.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xlh.security.entity.SysMenu;
import com.xlh.security.entity.SysRole;
import com.xlh.security.entity.SysUser;
import java.util.List;

/**
 * 系统用户表(SysUser)表数据库访问层
 *
 * @author xulihua
 * @since 2021-01-21 10:49:06
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 根据用户id获取用户的角色集合
     * @param userId
     * @return
     */
    List<SysRole> selectUserRoles(Long userId);

    /**
     * 根据用户id查询用户的权限
     * @param userId
     * @return
     */
    List<SysMenu> selectSysMenuByUserId(Long userId);
}