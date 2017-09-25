package com.sand.payadmin.model.repository;


import com.sand.payadmin.model.entity.auth.RoleGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xy on 2017/3/27.
 */
@Repository
public interface RoleGroupRepository extends CrudRepository<RoleGroup, Long> {
    /**
     * 通过用户组ID查找中间表列表
     */
    public List<RoleGroup> findByGroupId(Long groupId);

    /**
     * 通过角色ID查找中间表列表
     */
    public List<RoleGroupRepository> findByRoleId(Long roleId);


}
