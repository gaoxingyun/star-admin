package com.sand.payadmin.service;

import com.sand.payadmin.model.entity.auth.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * 角色服务
 */
@Service
public class RoleService {

    @Autowired
    private AuthDatabaseService authDatabaseService;


    /**
     * 创建角色
     *
     * @param name
     * @param detail
     * @return Role
     */
    public Role createRole(String name, String detail) {
        Role role = new Role();
        role.setName(name);
        role.setDetail(detail);
        return authDatabaseService.saveRole(role);
    }


    /**
     * 查询角色
     *
     * @param id
     * @return Role
     */
    public Role queryRole(Long id) {
        Role role = authDatabaseService.findRoleById(id);
        return role;
    }

    /**
     * 删除角色
     *
     * @param id
     * @return Role
     */
    public Role deleteRole(Long id) {
        Role role = authDatabaseService.findRoleById(id);
        authDatabaseService.deleteRole(role);
        return role;
    }


    /**
     * 查询所有角色
     *
     * @return
     */
    public Page<Role> findAllRole(Integer page, Integer size) {
        Page<Role> rolePage = authDatabaseService.findAllRole(page, size);
        return rolePage;
    }

}
