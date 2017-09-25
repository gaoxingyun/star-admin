package com.sand.payadmin.model.repository;


import com.sand.payadmin.model.entity.auth.Permission;
import com.sand.payadmin.model.entity.auth.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by xy on 2017/3/27.
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    /**
     * 分页查询角色
     *
     * @param pageable
     * @return
     */
    public Page<Role> findAll(Pageable pageable);
}
