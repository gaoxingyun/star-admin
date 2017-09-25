package com.sand.payadmin.model.entity.auth;

import com.sand.payadmin.common.util.JacksonUtils;

import javax.persistence.*;

/**
 * 角色组关联表
 * Created by xy on 2017/3/27.
 */
@Entity(name = "auth_role_group")
public class RoleGroup {

    @Id
    private long id;
    @Column(insertable = false, updatable = false)
    private long roleId; // 角色ID
    @Column(insertable = false, updatable = false)
    private long groupId; // 用户组ID

    @ManyToOne
    @JoinColumn(name = "roleId", referencedColumnName = "id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "groupId", referencedColumnName = "id")
    private Group group;

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

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
