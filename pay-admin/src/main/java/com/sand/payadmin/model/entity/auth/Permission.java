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
 * 权限表
 * <p>
 * Created by xy on 2017/3/27.
 */
@Entity(name = "auth_permission")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties({"permission_roles"})
public class Permission {
    @Id
    private long id; // 权限ID
    private String name; // 权限名
    private String detail; // 权限描述
    private long parentId; // 父权限ID

    @OneToMany(mappedBy = "permissionId")
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

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public Set<PermissionRole> getPermissionRoles() {
        return permissionRoles;
    }

    public void setPermissionRoles(Set<PermissionRole> permissionRoles) {
        this.permissionRoles = permissionRoles;
    }
}
