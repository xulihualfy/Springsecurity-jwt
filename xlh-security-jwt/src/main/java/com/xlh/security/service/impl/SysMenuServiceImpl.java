package com.xlh.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xlh.security.entity.SysMenu;
import com.xlh.security.mapper.SysMenuMapper;
import com.xlh.security.service.SysMenuService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * 权限表(SysMenu)表服务实现类
 *
 * @author xulihua
 * @since 2021-01-21 10:52:11
 */
@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Resource
    private SysMenuMapper sysMenuMapper;
}