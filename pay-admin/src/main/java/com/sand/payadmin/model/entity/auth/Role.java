package com.sand.payadmin.model.entity.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sand.payadmin.common.util.JacksonUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * 角色表
 * Created by xy on 2017/3/27.
 */
@Entity(name = "auth_role")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties({"role_groups", "permission_roles"})
public class Role {
    @Id
    private long id; // 角色ID
    private String name; // 角色名
    private String detail; // 角色描述

    @OneToMany(mappedBy = "roleId")
    private Set<RoleGroup> roleGroups;

    @OneToMany(mappedBy = "roleId")
    private Set<PermissionRole> permissionRoles;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Set<RoleGroup> getRoleGroups() {
        return roleGroups;
    }

    public void setRoleGroups(Set<RoleGroup> roleGroups) {
        this.roleGroups = roleGroups;
    }

    public Set<PermissionRole> getPermissionRoles() {
        return permissionRoles;
    }

    public void setPermissionRoles(Set<PermissionRole> permissionRoles) {
        this.permissionRoles = permissionRoles;
    }
}
