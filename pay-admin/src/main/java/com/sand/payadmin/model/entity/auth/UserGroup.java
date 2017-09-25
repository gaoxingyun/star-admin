package com.sand.payadmin.model.entity.auth;

import com.sand.payadmin.common.util.JacksonUtils;

import javax.persistence.*;

/**
 * 用户组关联表
 * Created by xy on 2017/3/27.
 */
@Entity(name = "auth_user_group")
public class UserGroup {
    @Id
    private long id;
    @Column(insertable = false, updatable = false)
    private long userId; // 用户ID
    @Column(insertable = false, updatable = false)
    private long groupId; // 用户组ID

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
