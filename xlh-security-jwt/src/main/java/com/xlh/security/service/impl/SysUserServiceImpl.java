package com.xlh.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xlh.security.entity.SysMenu;
import com.xlh.security.entity.SysRole;
import com.xlh.security.mapper.SysRoleMapper;
import com.xlh.security.mapper.SysUserMapper;
import com.xlh.security.entity.SysUser;
import com.xlh.security.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统用户表(SysUser)表服务实现类
 *
 * @author xulihua
 * @since 2021-01-21 10:49:08
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    SysUserMapper userMapper;

    @Override
    public List<SysRole> selectUserRoles(Long userId) {
        return userMapper.selectUserRoles(userId);
    }

    @Override
    public List<SysMenu> selectSysMenuByUserId(Long userId) {
        return userMapper.selectSysMenuByUserId(userId);
    }
}