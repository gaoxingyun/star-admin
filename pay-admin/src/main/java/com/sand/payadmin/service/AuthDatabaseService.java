package com.sand.payadmin.service;


import com.sand.payadmin.common.contant.AuthConstant;
import com.sand.payadmin.model.entity.auth.*;
import com.sand.payadmin.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 权限模块数据库服务
 * <p>
 * Created by xy on 2017/3/27.
 */
@Service
public class AuthDatabaseService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private RoleGroupRepository roleGroupRepository;

    @Autowired
    private PermissionRoleRepository permissionRoleRepository;


    /**
     * 查找所有用户
     */
    public Page<User> findAllUser(int page, int size) {
        Sort sort = new Sort("id");
        Pageable pageable = new PageRequest(page - 1, size, sort);
        final Page<User> userPage = userRepository.findAll(pageable);
        return userPage;
    }

    /**
     * 更新用户
     */
    public User updateUserStatus(String name, String status) {
        User user = userRepository.findByName(name);
        user.setStatus(status);
        return userRepository.save(user);
    }

    /**
     * 保存用户
     */
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * 通过用户名查找用户
     */
    public User findUserByName(String name) {
        return userRepository.findByName(name);
    }

    /**
     * 通过用户名删除用户
     */
    public User deleteUserByName(String name) {
        User user = userRepository.findByName(name);
        userRepository.delete(user);
        return user;
    }

    /**
     * 通过用户名更新最后登录日期
     */
    public User updateLastLoginTimeByName(String name) {
        final User user = findUserByName(name);
        String lastLoginTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(AuthConstant.USER_TIME_FORMAT));
        user.setLastLoginTime(lastLoginTime);
        return userRepository.save(user);
    }

    /**
     * 通过用户名查找用户组
     */
    public Set<Group> findGroupsByName(String name) {
        final User user = findUserByName(name);

        final Set<UserGroup> userGroups = user.getUserGroups();
        final Set<Group> groups = new HashSet<>();

        userGroups.forEach(userGroup -> groups.add(userGroup.getGroup()));
        return groups;
    }

    /**
     * 通过用户名查找角色
     */
    public Set<Role> findRolesByName(String name) {
        final Set<Group> groups = findGroupsByName(name);

        final Set<RoleGroup> roleGroups = new HashSet<>();
        groups.forEach(group -> roleGroups.addAll(group.getRoleGroups()));

        final Set<Role> roles = new HashSet<>();
        roleGroups.forEach(roleGroup -> roles.add(roleGroup.getRole()));

        return roles;
    }

    /**
     * 通过用户名查找权限
     */
    public Set<Permission> findAuthsByName(String name) {
        final Set<Role> roles = findRolesByName(name);

        final Set<PermissionRole> permissionRoles = new HashSet<>();
        roles.forEach(role -> permissionRoles.addAll(role.getPermissionRoles()));

        final Set<Permission> permissions = new HashSet<>();
        permissionRoles.forEach(permissionRole -> permissions.add(permissionRole.getPermission()));

        return permissions;
    }


    /**
     * 查找所有组
     */
    public Page<Group> findAllGroup(int page, int size) {
        Sort sort = new Sort("id");
        Pageable pageable = new PageRequest(page - 1, size, sort);
        final Page<Group> groups = groupRepository.findAll(pageable);
        return groups;
    }

    /**
     * 查询组通过主键
     */
    public Group findGroupById(Long id) {
        return groupRepository.findOne(id);
    }

    /**
     * 保存组
     */
    public Group saveGroup(Group group) {
        return groupRepository.save(group);
    }


    /**
     * 删除组
     */
    public Group deleteGroup(Group group) {
        groupRepository.delete(group);
        return group;
    }


    /**
     * 查找所有权限
     */
    public Page<Permission> findAllPermission(int page, int size) {
        Sort sort = new Sort("id");
        Pageable pageable = new PageRequest(page - 1, size, sort);
        final Page<Permission> permissionPage = permissionRepository.findAll(pageable);
        return permissionPage;
    }

    /**
     * 查询权限通过主键
     */
    public Permission findPermissionById(Long id) {
        return permissionRepository.findOne(id);
    }

    /**
     * 保存权限
     */
    public Permission savePermission(Permission permission) {
        return permissionRepository.save(permission);
    }


    /**
     * 删除权限
     */
    public Permission deletePermission(Permission permission) {
        permissionRepository.delete(permission);
        return permission;
    }


    /**
     * 查找所有角色
     */
    public Page<Role> findAllRole(int page, int size) {
        Sort sort = new Sort("id");
        Pageable pageable = new PageRequest(page - 1, size, sort);
        final Page<Role> permissionPage = roleRepository.findAll(pageable);
        return permissionPage;
    }

    /**
     * 查询角色通过主键
     */
    public Role findRoleById(Long id) {
        return roleRepository.findOne(id);
    }

    /**
     * 保存角色
     */
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }


    /**
     * 删除角色
     */
    public Role deleteRole(Role role) {
        roleRepository.delete(role);
        return role;
    }

}