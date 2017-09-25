package com.sand.payadmin.model.entity.auth;

import com.sand.payadmin.common.util.JacksonUtils;

import javax.persistence.*;

/**
 * 权限角色关联表
 * Created by xy on 2017/3/27.
 */
@Entity(name = "auth_permission_role")
public class PermissionRole {
    @Id
    private long id;
    @Column(insertable = false, updatable = false)
    private long permissionId; // 权限ID
    @Column(insertable = false, updatable = false)
    private long roleId; // 角色ID

    @ManyToOne
    @JoinColumn(name = "permissionId", referencedColumnName = "id")
    private Permission permission;

    @ManyToOne
    @JoinColumn(name = "roleId", referencedColumnName = "id")
    private Role role;

    @Override
    public String toString() {
        return JacksonUtils.beanToJson(this);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(long permissionId) {
        this.permissionId = permissionId;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
