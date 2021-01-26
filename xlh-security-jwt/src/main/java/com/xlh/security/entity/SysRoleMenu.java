package com.xlh.security.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 角色与权限关系表(SysRoleMenu)实体类
 *
 * @author xulihua
 * @since 2021-01-21 10:51:40
 */
@Data
public class SysRoleMenu implements Serializable {
    private static final long serialVersionUID = 506613716969172177L;
    /**
     * ID
     */
    private Long id;
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 权限ID
     */
    private Long menuId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

}