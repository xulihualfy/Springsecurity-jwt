package com.xlh.security.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 角色表(SysRole)实体类
 *
 * @author xulihua
 * @since 2021-01-21 10:51:54
 */
@Data
public class SysRole implements Serializable {
    private static final long serialVersionUID = 223996269163000494L;
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 角色名称
     */
    private String roleName;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}