package com.xlh.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xlh.security.entity.SysUserRole;
import com.xlh.security.mapper.SysUserRoleMapper;
import com.xlh.security.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户与角色关系表(SysUserRole)表服务实现类
 *
 * @author xulihua
 * @since 2021-01-21 10:51:01
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;
}