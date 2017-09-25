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
 * 组表
 * Created by xy on 2017/3/27.
 */
@Entity(name = "auth_group")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties({"role_groups", "user_groups"})
public class Group {

    @Id
    private long id; // 用户组ID
    private String name; // 用户组名
    private String detail; // 用户组详情
    private long parentId; // 父用户组ID

    @OneToMany(mappedBy = "groupId")
    private Set<RoleGroup> roleGroups;

    @OneToMany(mappedBy = "groupId")
    private Set<UserGroup> userGroups;

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

    public Set<RoleGroup> getRoleGroups() {
        return roleGroups;
    }

    public void setRoleGroups(Set<RoleGroup> roleGroups) {
        this.roleGroups = roleGroups;
    }

    public Set<UserGroup> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(Set<UserGroup> userGroups) {
        this.userGroups = userGroups;
    }
}