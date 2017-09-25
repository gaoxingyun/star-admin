package com.sand.payadmin.model.repository;


import com.sand.payadmin.model.entity.auth.Group;
import com.sand.payadmin.model.entity.auth.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by xy on 2017/3/27.
 */
@Repository
public interface GroupRepository extends CrudRepository<Group, Long> {

    /**
     * 分页查询组
     *
     * @param pageable
     * @return
     */
    public Page<Group> findAll(Pageable pageable);
}
