package com.xlh.security.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 权限表(SysMenu)实体类
 *
 * @author xulihua
 * @since 2021-01-21 10:52:11
 */
@Data
public class SysMenu implements Serializable {
    private static final long serialVersionUID = -52886431419932976L;
    /**
     * ID
     */
    private Long menuId;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 权限标识
     */
    private String permission;


    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

}