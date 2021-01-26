package com.xlh.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xlh.security.entity.SysRole;
import com.xlh.security.mapper.SysRoleMapper;
import com.xlh.security.service.SysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 角色表(SysRole)表服务实现类
 *
 * @author xulihua
 * @since 2021-01-21 10:51:55
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Resource
    private SysRoleMapper sysRoleMapper;
}