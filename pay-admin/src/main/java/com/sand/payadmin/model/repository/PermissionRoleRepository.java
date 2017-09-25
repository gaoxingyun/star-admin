package com.sand.payadmin.model.repository;


import com.sand.payadmin.model.entity.auth.PermissionRole;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by xy on 2017/3/27.
 */
public interface PermissionRoleRepository extends CrudRepository<PermissionRole, Long> {

    /**
     * 通过角色ID查找中间表列表
     */
    public List<PermissionRole> findByRoleId(Long roleId);

    /**
     * 通过权限ID查找中间表列表
     */
    public List<PermissionRole> findByPermissionId(Long permissionId);

}
