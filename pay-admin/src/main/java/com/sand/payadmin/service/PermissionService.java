package com.sand.payadmin.service;

import com.sand.payadmin.common.contant.AuthConstant;
import com.sand.payadmin.common.shiro.FormPasswordHelper;
import com.sand.payadmin.common.util.RandomStringGeneratorUtils;
import com.sand.payadmin.model.entity.auth.Permission;
import com.sand.payadmin.model.entity.auth.User;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 权限服务
 */
@Service
public class PermissionService {

    @Autowired
    private AuthDatabaseService authDatabaseService;


    /**
     * 创建权限
     *
     * @param name
     * @param detail
     * @param parentId
     * @return Permission
     */
    public Permission createPermission(String name, String detail, Long parentId) {
        Permission permission = new Permission();
        permission.setName(name);
        permission.setDetail(detail);
        permission.setParentId(parentId);
        return authDatabaseService.savePermission(permission);
    }


    /**
     * 查询权限
     *
     * @param id
     * @return Permission
     */
    public Permission queryPermission(Long id) {
        Permission permission = authDatabaseService.findPermissionById(id);
        return permission;
    }

    /**
     * 删除权限
     *
     * @param id
     * @return Permission
     */
    public Permission deletePermission(Long id) {
        Permission permission = authDatabaseService.findPermissionById(id);
        authDatabaseService.deletePermission(permission);
        return permission;
    }


    /**
     * 更新权限
     *
     * @param id
     * @return Permission
     */
    public Permission updatePermission(Long id, String name, String detail) {
        Permission permission = authDatabaseService.findPermissionById(id);
        permission.setName(name);
        permission.setDetail(detail);
        authDatabaseService.savePermission(permission);
        return permission;
    }


    /**
     * 查询所有权限
     *
     * @return
     */
    public Page<Permission> findAllPermission(Integer page, Integer size) {
        Page<Permission> permissionPage = authDatabaseService.findAllPermission(page, size);
        return permissionPage;
    }

}
