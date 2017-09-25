package com.sand.payadmin.model.repository;


import com.sand.payadmin.model.entity.auth.Permission;
import com.sand.payadmin.model.entity.auth.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by xy on 2017/3/27.
 */
@Repository
public interface PermissionRepository extends CrudRepository<Permission, Long> {

    /**
     * 分页查询权限
     *
     * @param pageable
     * @return
     */
    public Page<Permission> findAll(Pageable pageable);
}
