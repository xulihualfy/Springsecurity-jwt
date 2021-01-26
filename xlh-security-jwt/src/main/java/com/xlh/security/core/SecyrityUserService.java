package com.xlh.security.core;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xlh.security.entity.SysUser;
import com.xlh.security.service.SysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author xulihua
 * @date 2021/1/21 16:47
 * security -user业务处理类
 */
@Component
public class SecyrityUserService implements UserDetailsService {

    @Autowired
    private SysUserService userService;
    /**
     * 查询用户信息
     * @param userName
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public SecurityUserDetail loadUserByUsername(String userName) throws UsernameNotFoundException {
        SysUser sysUser = userService.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getUsername, userName));
        if (Objects.nonNull(sysUser)){
            // 组装参数
            SecurityUserDetail userDetail = new SecurityUserDetail();
            BeanUtils.copyProperties(sysUser,userDetail);
            return userDetail;
        }
        return null;
    }
}
