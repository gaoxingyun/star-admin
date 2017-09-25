package com.sand.payadmin.model.entity.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sand.payadmin.common.util.JacksonUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.util.Set;

/**
 * 用户表
 * Created by xy on 2017/3/27.
 */
@Entity(name = "auth_user")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties({"user_groups", "password", "salt"})
public class User {
    @Id
    @GeneratedValue()
    private long id; // 用户ID
    private String name; // 用户名
    private String password; // 密码
    private String salt; // 盐
    private long parentId; // 父级用户ID
    private String status; // 状态
    private String lastLoginTime; // 上次登录时间
    @Column(updatable = false)
    private String createTime; // 创建时间


    @OneToMany(mappedBy = "user")
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Set<UserGroup> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(Set<UserGroup> userGroups) {
        this.userGroups = userGroups;
    }

}