package com.xlh.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xlh.security.entity.SysRoleMenu;
import com.xlh.security.mapper.SysRoleMenuMapper;
import com.xlh.security.service.SysRoleMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 角色与权限关系表(SysRoleMenu)表服务实现类
 *
 * @author xulihua
 * @since 2021-01-21 10:51:40
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {
    @Resource
    private SysRoleMenuMapper sysRoleMenuDao;
}